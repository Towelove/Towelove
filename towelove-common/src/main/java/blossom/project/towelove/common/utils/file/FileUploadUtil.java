package blossom.project.towelove.common.utils.file;


import blossom.project.towelove.common.constant.MinioConstant;
import blossom.project.towelove.common.utils.DateUtils;
import blossom.project.towelove.common.utils.StringUtils;
import blossom.project.towelove.common.utils.uuid.Seq;
import blossom.project.towelove.common.exception.file.FileNameLengthLimitExceededException;
import blossom.project.towelove.common.exception.file.FileSizeLimitExceededException;
import blossom.project.towelove.common.exception.file.InvalidExtensionException;
import cn.hutool.crypto.digest.MD5;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * 文件上传工具类
 * 
 * @author 张锦标
 */
public class FileUploadUtil
{
    /**
     * 默认大小 10M
     */
    public static final long DEFAULT_MAX_SIZE = 10 * 1024 * 1024;

    /**
     * 默认的文件名最大长度 100
     */
    public static final int DEFAULT_FILE_NAME_LENGTH = 100;

    /**
     * 当前方法用于判断文件的类型
     * @param file 上传的文件
     * @return
     */
    public static final String getFileType(MultipartFile file) {
        String contentType = file.getContentType();

        if (contentType != null) {
            if (contentType.startsWith("image/")) {
                return "image";
            } else if (contentType.startsWith("video/")) {
                return "video";
            }
        }
        return "file";
    }

    /**
     * 返回最后要使用的桶名称
     * @param fileType
     * @return
     */
    private static final String determineBucket(String fileType) {
        switch (fileType) {
            case "image":
                return MinioConstant.BUCKET_IMAGES;
            case "video":
                return MinioConstant.BUCKET_VIDEOS;
            default:
                return MinioConstant.BUCKET_FILES;
        }
    }

    /**
     * 返回要使用的桶名称
     * @param file
     * @return
     */
    public static final String getBucketName(MultipartFile file){
        return determineBucket(getFileType(file));
    }

    /**
     * 工具文件扩展名获取文件所在桶名称
     * @param fileName 文件名称
     * @return 返回桶名称
     */
    public static String getBucketNameByFileExtension(String fileName) {
        String extension = "";

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i + 1).toLowerCase();
        }

        switch (extension) {
            case "jpg":
            case "jpeg":
            case "png":
            case "gif":
            case "bmp":
            case "tiff":
            case "svg":
                return MinioConstant.BUCKET_IMAGES;

            case "mp4":
            case "avi":
            case "mov":
            case "wmv":
            case "flv":
            case "mkv":
                return MinioConstant.BUCKET_VIDEOS;

            // 可以根据需要添加更多文件类型
            default:
                return MinioConstant.BUCKET_FILES;
        }
    }

    /**
     * 根据文件路径上传
     *
     * @param baseDir 相对应用的基目录
     * @param file 上传的文件
     * @return 文件名称
     * @throws IOException
     */
    public static final String upload(String baseDir, MultipartFile file) throws IOException
    {
        try
        {
            return upload(baseDir, file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
        }
        catch (Exception e)
        {
            throw new IOException(e.getMessage(), e);
        }
    }

    /**
     * 文件上传
     *
     * @param baseDir 相对应用的基目录
     * @param file 上传的文件
     * @param allowedExtension 上传文件类型
     * @return 返回上传成功的文件名
     * @throws FileSizeLimitExceededException 如果超出最大大小
     * @throws FileNameLengthLimitExceededException 文件名太长
     * @throws IOException 比如读写文件出错时
     * @throws InvalidExtensionException 文件校验异常
     */
    public static final String upload(String baseDir, MultipartFile file, String[] allowedExtension)
            throws FileSizeLimitExceededException, IOException, FileNameLengthLimitExceededException,
            InvalidExtensionException
    {   //判断文件名长度是否过长
        int fileNamelength = Objects.requireNonNull(file.getOriginalFilename()).length();
        if (fileNamelength > FileUploadUtil.DEFAULT_FILE_NAME_LENGTH)
        {
            throw new FileNameLengthLimitExceededException(FileUploadUtil.DEFAULT_FILE_NAME_LENGTH);
        }
        //判断文件的扩展类型是否合理
        assertAllowed(file, allowedExtension);
        //对文件名进行编码
        String fileName = extractFilename(file);
        //获取文件在本机的绝对地址
        String absPath = getAbsoluteFile(baseDir, fileName).getAbsolutePath();
        //将文件放到本机绝对地址
        file.transferTo(Paths.get(absPath));
        //返回文件名
        return getPathFileName(fileName);
    }

    /**
     * 编码文件名
     */
    public static final String extractFilename(MultipartFile file)
    {
        //return StringUtils.format("{}/{}_{}", DateUtils.datePath(),
        return StringUtils.format("{}/{}_{}.{}", DateUtils.datePath(),
                FilenameUtils.getBaseName(file.getOriginalFilename()), Seq.getId(Seq.uploadSeqType),
                FileTypeUtils.getExtension(file));
                //FilenameUtils.getBaseName(file.getOriginalFilename()), Seq.getId(Seq.uploadSeqType));
    }

    private static final File getAbsoluteFile(String uploadDir, String fileName) throws IOException
    {
        File desc = new File(uploadDir + File.separator + fileName);

        if (!desc.exists())
        {
            if (!desc.getParentFile().exists())
            {
                desc.getParentFile().mkdirs();
            }
        }
        return desc.isAbsolute() ? desc : desc.getAbsoluteFile();
    }

    private static final String getPathFileName(String fileName) throws IOException
    {
        String pathFileName = "/" + fileName;
        return pathFileName;
    }

    /**
     * 文件大小校验
     *
     * @param file 上传的文件
     * @throws FileSizeLimitExceededException 如果超出最大大小
     * @throws InvalidExtensionException 文件校验异常
     */
    public static final void assertAllowed(MultipartFile file, String[] allowedExtension)
            throws FileSizeLimitExceededException, InvalidExtensionException
    {
        long size = file.getSize();
        if (size > DEFAULT_MAX_SIZE)
        {
            throw new FileSizeLimitExceededException(DEFAULT_MAX_SIZE / 1024 / 1024);
        }

        String fileName = file.getOriginalFilename();
        String extension = FileTypeUtils.getExtension(file);
        if (allowedExtension != null && !isAllowedExtension(extension, allowedExtension))
        {
            if (allowedExtension == MimeTypeUtils.IMAGE_EXTENSION)
            {
                throw new InvalidExtensionException.InvalidImageExtensionException(allowedExtension, extension,
                        fileName);
            }
            else if (allowedExtension == MimeTypeUtils.FLASH_EXTENSION)
            {
                throw new InvalidExtensionException.InvalidFlashExtensionException(allowedExtension, extension,
                        fileName);
            }
            else if (allowedExtension == MimeTypeUtils.MEDIA_EXTENSION)
            {
                throw new InvalidExtensionException.InvalidMediaExtensionException(allowedExtension, extension,
                        fileName);
            }
            else if (allowedExtension == MimeTypeUtils.VIDEO_EXTENSION)
            {
                throw new InvalidExtensionException.InvalidVideoExtensionException(allowedExtension, extension,
                        fileName);
            }
            else
            {
                throw new InvalidExtensionException(allowedExtension, extension, fileName);
            }
        }
    }

    /**
     * 判断MIME类型是否是允许的MIME类型
     *
     * @param extension 上传文件类型
     * @param allowedExtension 允许上传文件类型
     * @return true/false
     */
    public static final boolean isAllowedExtension(String extension, String[] allowedExtension)
    {
        for (String str : allowedExtension)
        {
            if (str.equalsIgnoreCase(extension))
            {
                return true;
            }
        }
        return false;
    }
}