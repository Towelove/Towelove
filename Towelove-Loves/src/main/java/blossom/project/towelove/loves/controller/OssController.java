package blossom.project.towelove.loves.controller;

import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.framework.log.annotation.LoveLog;
import blossom.project.towelove.framework.oss.service.OssService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author: ZhangBlossom
 * @date: 2023/12/1 13:06
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * OssController类
 * 当前类用于提供给项目统一的进行文件上传操作
 *
 */
@LoveLog
@RestController
@RequestMapping("/oss")
@RequiredArgsConstructor
public class OssController {

    private final OssService ossService;




    /**
     * 单文件上传接口
     *
     * @param file 文件
     * @return url
     */
    @PostMapping("/file")
    public Result<String> uploadFile(MultipartFile file) {
        return Result.ok(ossService.uploadFile(file, null));
    }


    /**
     * 实现多文件上传，得到文件URL集合
     *
     * @param files
     * @return
     */
    @PostMapping("/files")
    public Result<List<String>> uploadFiles(List<MultipartFile> files) {
        List<String> fileNames = ossService.uploadFilesAsync(files, null, 0);
        return Result.ok(fileNames);
    }


    /**
     * 实现多文件上传，得到文件Map集合
     * 并且升序排列
     *
     * @param files
     * @return
     */
    @PostMapping("/files/map")
    public Result<Map<Integer, String>> uploadFilesMap(List<MultipartFile> files) {
        List<String> fileNames = ossService.uploadFilesAsync(files, null, 0);
        Map<Integer, String> result =
                IntStream.range(0, fileNames.size()).boxed().collect(Collectors.toMap(Function.identity(), //
                        // 键映射函数，返回当前元素（索引）
                fileNames::get,      // 值映射函数，返回索引对应的文件名
                (oldValue, newValue) -> oldValue, // 合并函数，处理键冲突，这里我们可以忽略，因为键不会重复
                TreeMap::new         // 提供 TreeMap 实例
        ));
        return Result.ok(result);
    }

    /**
     * 删除urls 多个文件则以 英文逗号分隔
     * @param urls 删除url
     * @return
     */
    @DeleteMapping("/files")
    public Result<String> removeFiles(@RequestParam("urls") String urls){
        return Result.ok(ossService.removeFiles(urls,null));
    }

}
