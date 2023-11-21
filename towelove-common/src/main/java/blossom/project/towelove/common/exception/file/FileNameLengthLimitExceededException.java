package blossom.project.towelove.common.exception.file;

/**
 * 文件名称超长限制异常类
 * 
 * @author: 张锦标
 * @date: 2023/2/23 18:46
 * Description:
 */
public class FileNameLengthLimitExceededException extends FileException
{
    private static final long serialVersionUID = 1L;

    public FileNameLengthLimitExceededException(int defaultFileNameLength)
    {
        super("upload.filename.exceed.length", new Object[] { defaultFileNameLength });
    }
}
