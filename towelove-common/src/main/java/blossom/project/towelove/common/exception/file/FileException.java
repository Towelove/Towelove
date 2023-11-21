package blossom.project.towelove.common.exception.file;


import blossom.project.towelove.common.exception.base.BaseException;

/**
 * 文件信息异常类
 * 
 * @author: 张锦标
 * @date: 2023/2/23 18:46
 * Description:
 */
public class FileException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public FileException(String code, Object[] args)
    {
        super("file", code, args, null);
    }

}
