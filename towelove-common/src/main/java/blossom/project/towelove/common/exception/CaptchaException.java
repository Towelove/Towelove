package blossom.project.towelove.common.exception;

/**
 * 验证码错误异常类
 * 
 * @author: 张锦标
 * @date: 2023/2/23 18:46
 * Description:
 */
public class CaptchaException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public CaptchaException(String msg)
    {
        super(msg);
    }
}
