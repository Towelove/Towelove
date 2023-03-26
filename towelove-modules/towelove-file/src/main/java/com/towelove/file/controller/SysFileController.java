package com.towelove.file.controller;


import com.towelove.common.core.constant.HttpStatus;
import com.towelove.common.core.domain.R;
import com.towelove.common.core.utils.file.FileUtils;
import com.towelove.file.domain.LoveLogs;
import com.towelove.file.domain.SysFile;
import com.towelove.file.service.ISysFileService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;

/**
 * 文件请求处理
 *
 * @author 张锦标
 */
@RequestMapping("/file")
@RestController
public class SysFileController {
    private static final Logger log = LoggerFactory.getLogger(SysFileController.class);

    @Autowired
    private ISysFileService sysFileService;

    /**
     * 单文件上传请求
     */
    @PostMapping("/upload")
    public R<SysFile> upload(@RequestParam("file") MultipartFile file) {
        try {
            // 上传并返回访问地址
            String url = sysFileService.uploadFile(file);
            SysFile sysFile = new SysFile();
            sysFile.setName(FileUtils.getName(url));
            sysFile.setUrl(url);
            return R.ok(sysFile);
        } catch (Exception e) {
            log.error("上传文件失败", e);
            return R.fail(e.getMessage());
        }
    }

    /**
     * 实现多文件上传
     * @param files 要上传的文件
     * @return 返回恋爱日志信息
     */
    @ApiOperation(value = "多附件上传-纯附件上传", notes = "多附件上传")
    @ResponseBody
    @PostMapping("/uploadFiles")
    public R<LoveLogs> handleFileUpload(@RequestParam("files") MultipartFile[] files) {
        LoveLogs loveLogs = new LoveLogs();
        String[] urls = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            try {
                //// 获取文件名
                //String fileName = file.getOriginalFilename();
                //// 拼接文件保存路径
                //String filePath = "D:/uploads/" + fileName;
                //// 保存文件到本地
                //file.transferTo(new File(filePath));
                MultipartFile file = files[i];
                String url = sysFileService.uploadFile(file);
                urls[i] = url;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        String photoUrls = String.join(",", urls);
        loveLogs.setUrls(photoUrls);
        return R.ok(loveLogs);
    }


    /**
     * 文件下载
     *
     * @param name
     * @param response
     */
    @GetMapping("/download")
    public void download(@RequestParam String name, HttpServletResponse response) {
        //FileInputStream fis = null;
        //ServletOutputStream os = null;
        try (FileInputStream fis = new FileInputStream(new File(name));
             ServletOutputStream os = response.getOutputStream()) {
            //输入流,通过输入流读取文件内容
            //fis = new FileInputStream(new File( name));
            //输出流,通过输出流将文件写回浏览器,在浏览器展示图片
            //os = response.getOutputStream();
            //设置响应的数据的格式
            response.setContentType("image/jpeg");
            int len = 0;
            byte[] buffre = new byte[1024 * 10];
            while ((len = fis.read(buffre)) != -1) {
                os.write(buffre, 0, len);
                os.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}