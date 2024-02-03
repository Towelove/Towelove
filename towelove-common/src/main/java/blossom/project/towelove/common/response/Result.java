package blossom.project.towelove.common.response;

   


import blossom.project.towelove.common.constant.Constant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 响应信息主体
 *
 * @author: 张锦标
 * @date: 2023/2/23 18:36
 * Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 成功 */
    public static final int SUCCESS = Constant.SUCCESS;

    /** 失败 */
    public static final int FAIL = Constant.FAIL;

    private int code;

    private String msg;

    private T data;

    private String requestId;

    public static <T> Result<T> ok()
    {
        return restResult(null, SUCCESS, null);
    }

    public static <T> Result<T> ok(T data)
    {
        return restResult(data, SUCCESS, null);
    }

    public static <T> Result<T> ok(T data, String msg)
    {
        return restResult(data, SUCCESS, msg);
    }
    public static <T> Result<T> ok(T data, String msg,String requestId)
    {
        return restResult(data, SUCCESS, msg,requestId);
    }

    public static <T> Result<T> fail(String msg)
    {
        return restResult(null, FAIL, msg,null);
    }

    public static <T> Result<T> fail(String msg,String requestId)
    {
        return restResult(null, FAIL, msg,requestId);
    }

    public static <T> Result<T> fail(T data,String requestId)
    {
        return restResult(data, FAIL, null,requestId);
    }

    public static <T> Result<T> fail(T data, String msg,String requestId)
    {
        return restResult(data, FAIL, msg,requestId);
    }

    public static <T> Result<T> fail(T data,int code, String msg,String requestId)
    {
        return restResult(data, code, msg,requestId);
    }

    private static <T> Result<T> restResult(T data, int code, String msg)
    {
        Result<T> apiResult = new Result<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }
    private static <T> Result<T> restResult(T data, int code, String msg,String requestId)
    {
        Result<T> apiResult = new Result<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        apiResult.setRequestId(requestId);
        return apiResult;
    }

    public static <T> Boolean isError(Result<T> ret)
    {
        return !isSuccess(ret);
    }

    public static <T> Boolean isSuccess(Result<T> ret)
    {
        return Result.SUCCESS == ret.getCode();
    }
}
