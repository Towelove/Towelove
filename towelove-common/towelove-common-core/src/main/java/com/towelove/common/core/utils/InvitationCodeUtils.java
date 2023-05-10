package com.towelove.common.core.utils;

import com.towelove.common.core.utils.uuid.UUID;

/**
 * @author 季台星
 * @Date 2023 04 28 16 43
 */
public class InvitationCodeUtils {
    /**
     * 通过Id生成邀请码
     * @return
     */
    public static String getInvitationCode(Long id){
        String randomStr = UUID.randomUUID().toString();
        String trim = id.toString().concat(randomStr).substring(0,6);
        return trim;
    }

    public static Long parseInvitationCode(String code){
        return Long.valueOf(code.substring(0,code.length() - 4));
    }
}
