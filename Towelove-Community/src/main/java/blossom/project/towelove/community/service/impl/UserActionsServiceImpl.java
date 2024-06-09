package blossom.project.towelove.community.service.impl;
import blossom.project.towelove.common.exception.EntityNotFoundException;
import blossom.project.towelove.common.exception.errorcode.BaseErrorCode;
import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.community.convert.UserActionsConvert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import blossom.project.towelove.community.entity.UserActions;
import blossom.project.towelove.community.mapper.UserActionsMapper;
import blossom.project.towelove.community.service.UserActionsService;
import blossom.project.towelove.community.dto.UserActionsRespDTO;
import blossom.project.towelove.community.req.UserActionsCreateRequest;
import blossom.project.towelove.community.req.UserActionsPageRequest;
import blossom.project.towelove.community.req.UserActionsUpdateRequest;
import java.util.List;
import java.util.Objects;

/**
 * @author: ZhangBlossom
 * @date: 2024-06-09 00:08:01
 * @contact: QQ:4602197553
 * @contact: WX:zhangblossom0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description: 
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserActionsServiceImpl extends ServiceImpl<UserActionsMapper, UserActions> implements UserActionsService {

    private final UserActionsMapper userActionsMapper;


    @Override
    public UserActionsRespDTO createUserActions(UserActionsCreateRequest createRequest) {
        UserActions userActions = UserActionsConvert.INSTANCE.convert(createRequest);
        if (Objects.isNull(userActions)) {
            log.info("the userActions is null...");
            return null;
        }
        userActionsMapper.insert(userActions);
        UserActionsRespDTO respDTO = UserActionsConvert.INSTANCE.convert(userActions);
        return respDTO;
    }

    @Override
    public UserActionsRespDTO getUserActionsById(Long UserActionsId) {
        return null;
    }

    @Override
    public UserActionsRespDTO getUserActionsDetailById(Long userActionsId) {
        UserActions userActions = userActionsMapper.selectById(userActionsId);
        if (Objects.isNull(userActions)) {
            log.info("the userActions is null...");
            return null;
        }
        UserActionsRespDTO respDTO = UserActionsConvert.INSTANCE.convert(userActions);
        return respDTO;
    }

    @Override
    public PageResponse<UserActionsRespDTO> pageQueryUserActions(UserActionsPageRequest pageRequest) {
        LambdaQueryWrapper<UserActions> lqw = new LambdaQueryWrapper<>();
        Page<UserActions> page = new Page(pageRequest.getPageNo(), pageRequest.getPageSize());
        Page<UserActions> userActionsPage = userActionsMapper.selectPage(page, lqw);
        List<UserActionsRespDTO> respDTOList = null;
        if (CollectionUtil.isEmpty(userActionsPage.getRecords())) {
            respDTOList = UserActionsConvert.INSTANCE.convert(userActionsPage.getRecords());
        }
        return new PageResponse<>(pageRequest.getPageNo(), pageRequest.getPageSize(), respDTOList);
    }

    @Override
    public UserActionsRespDTO updateUserActions(UserActionsUpdateRequest updateRequest) {
        UserActions userActions = userActionsMapper.selectById(updateRequest.getId());
        if (Objects.isNull(userActions)) {
            log.error("the album is null");
            throw new EntityNotFoundException("can not find userActions whick id is: " + updateRequest.getId()
                    , BaseErrorCode.ENTITY_NOT_FOUND);
        }
        try {
            userActionsMapper.updateById(userActions);
            UserActions resp = userActionsMapper.selectById(userActions.getId());
            UserActionsRespDTO respDTO = UserActionsConvert.INSTANCE.convert(resp);
            return respDTO;
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public Boolean deleteUserActionsById(Long userActionsId) {
        return (userActionsMapper.deleteById(userActionsId)) > 0;
    }

    @Override
    public Boolean batchDeleteUserActions(List<Long> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            log.info("ids is null...");
            return true;
        }
        return userActionsMapper.deleteBatchIds(ids) > 0;
    }

}



