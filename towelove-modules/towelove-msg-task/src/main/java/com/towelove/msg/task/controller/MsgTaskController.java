package com.towelove.msg.task.controller;
import cn.hutool.http.server.HttpServerRequest;
import com.towelove.common.core.constant.TokenConstants;
import com.towelove.common.core.domain.PageResult;
import com.towelove.common.core.domain.R;
import com.towelove.common.core.utils.JwtUtils;
import com.towelove.msg.task.convert.MsgTaskConvert;
import com.towelove.msg.task.domain.MsgTask;
import com.towelove.msg.task.domain.vo.*;
import com.towelove.msg.task.mq.producer.MsgTaskProducer;
import com.towelove.msg.task.service.MsgTaskService;
import com.towelove.system.api.RemoteSysMailAccountService;
import com.towelove.system.api.domain.SysMailAccount;
import com.towelove.system.api.model.MailAccountDO;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: 张锦标
 * @date: 2023/3/12 9:37
 * MsgTaskController类
 */
@RestController
@RequestMapping("/msg/task")
public class MsgTaskController {
    @Autowired
    private MsgTaskService msgTaskService;
    @Autowired
    private RemoteSysMailAccountService sysMailAccountService;
    private List<MailAccountDO> getAccountId(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        Claims claims = JwtUtils.parseToken(token);
        Long userId = Long.valueOf(JwtUtils.getUserId(claims));
        //根据userId远程调用获取accountId
        List<MailAccountDO> data = sysMailAccountService
                .getMailAccountByUserId(userId).getData();
        return data;
    }
    private List<MailAccountDO> getAccountId(Long userId){
        //根据userId远程调用获取accountId
        List<MailAccountDO> data = sysMailAccountService
                .getMailAccountByUserId(userId).getData();
        return data;
    }
    //TODO 消息返回的时候需要返回当前创建消息的json以及创建后的id
    /**
     * 创建消息任务
     *
     * @param createReqVO 前端传来的消息任务信息
     * @return 创建成功返回id
     * 失败返回500
     */
    @PostMapping("/create")
    @Operation(summary = "创建消息任务")
    public R<Long> createMsgTask(@Valid @RequestBody MsgTaskCreateReqVO createReqVO,
                                 @Autowired HttpServletRequest request){
        Long userId = getUserIdByHeader(request);
        if(msgTaskService.getMsgTaskList().size()>5){
            return R.fail(200,"创建消息任务的数量达到上限了,请删除原有的");
        }
        List<MailAccountDO> mailAccountDOList = getAccountId(userId);
        if (Objects.isNull(mailAccountDOList)){
            return R.fail(500,"当前用户还没有创建他的邮箱账户信息，请创建邮箱账户信息");
        }
        System.out.println("当前accountList为："+mailAccountDOList);
        MailAccountDO accountDO = mailAccountDOList.get(0);
        createReqVO.setAccountId(accountDO.getId());
        createReqVO.setUserId(userId);
        return R.ok(msgTaskService.createMsgTask(createReqVO));
    }

    /**
     * 从请求头中获取当前用户id
     * @param request
     * @return
     */
    private Long getUserIdByHeader(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        String userId = JwtUtils.getUserId(header);
        return Long.valueOf(userId);
    }
    /**
     * 修改消息任务
     * @param updateReqVO 要修改的消息任务消息
     * @return boolean值 返回是否修改成功
     */
    @PutMapping("/update")
    @Operation(summary = "修改消息任务")
    public R<Boolean> updateMailAccount(@Valid @RequestBody MsgTaskUpdateReqVO updateReqVO) {
        return R.ok( msgTaskService.updateMsgTask(updateReqVO),"消息修改成功");
    }

    /**
     * 删除消息任务
     * @param id 要删除的任务id
     * @return 返回修改是否成功
     */
    @DeleteMapping("/delete")
    @Operation(summary = "删除消息任务")
    @Parameter(name = "id", description = "编号", required = true)
    public R<Boolean> deleteMailAccount(@RequestParam Long id) {
        return R.ok(msgTaskService.deleteMsgTask(id));
    }

    /**
     * 获得消息任务根据id
     * @param id 消息任务id
     * @return 返回消息任务消息
     */
    @GetMapping("/get")
    @Operation(summary = "获得消息任务")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public R<MsgTaskRespVO> getMailAccount(@RequestParam("id") Long id) {
        MsgTask msgTask = msgTaskService.getMsgTask(id);
        MsgTaskRespVO msgTaskRespVO = new MsgTaskRespVO();
        BeanUtils.copyProperties(msgTask,msgTaskRespVO);
        return R.ok(msgTaskRespVO);
    }

    /**
     * 根据查询条件进行分页
     * @param pageReqVO 分页查询条件
     * @return 返回分页数据
     */
    @GetMapping("/page")
    @Operation(summary = "获得消息任务分页")
    public R<PageResult<MsgTaskRespVO>> getMailAccountPage(@Valid MsgTaskPageReqVO pageReqVO) {
        PageResult<MsgTask> msgTaskPage = msgTaskService.getMsgTaskPage(pageReqVO);
        List<MsgTask> list = msgTaskPage.getList();
        List<MsgTaskRespVO> collect = list.stream().map(msgTask -> {
            MsgTaskRespVO msgTaskBaseVO = new MsgTaskRespVO();
            BeanUtils.copyProperties(msgTask, msgTaskBaseVO);
            return msgTaskBaseVO;
        }).collect(Collectors.toList());
        PageResult<MsgTaskRespVO> msgTaskBaseVOPageResult = new PageResult<>();
        msgTaskBaseVOPageResult.setList(collect);
        return R.ok(msgTaskBaseVOPageResult);
    }

    /**
     * 快速查询所有的消息任务各表关系
     * @return 消息任务各表关系
     */
    @GetMapping("/list-all-simple")
    @Operation(summary = "获得消息任务精简列表")
    public R<List<MsgTaskSimpleRespVO>> getSimpleMailAccountList(HttpServletRequest request) {
        Long userId = getUserIdByHeader(request);
        List<MsgTaskSimpleRespVO> simpleMailAccountList =
                msgTaskService.getSimpleMailAccountListByUserId(userId);
        return R.ok(simpleMailAccountList);
    }

    @GetMapping("/getSechelTask")
    public R<List<MsgTask>> getSechelTask(){
        List<MsgTask> msgTaskList = msgTaskService.getMsgTaskList();
        return R.ok(msgTaskList);
    }
}
