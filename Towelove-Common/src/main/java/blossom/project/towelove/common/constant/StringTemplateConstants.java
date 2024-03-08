package blossom.project.towelove.common.constant;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.common.constant
 * @className: StringTemplateConstants
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/1/18 22:47
 * @version: 1.0
 * 模板字符串
 */
public class StringTemplateConstants {
    private static final String COMMON_INVITED_TEMPLATE = "用户：%s邀请您加入towelove，点击链接【https://web.towelove.cn/?invitedCode=%s】同意他的请求吧！";
    private static final String QRCODE_INVITED_TEMPLATE = "https://web.towelove.cn/?invited=%s";

    public static final String QRCODE_TYPE = "qrCode";

    public static final String COMMON_TYPE = "common";





    public static String getInvitedTemplate(String userName,String invitedCode,String type){
        String template = "";
        switch (type){
            case QRCODE_TYPE -> template = String.format(QRCODE_INVITED_TEMPLATE,invitedCode);
            case COMMON_TYPE -> template = String.format(COMMON_INVITED_TEMPLATE,userName,invitedCode);
            default -> template = String.format(COMMON_INVITED_TEMPLATE,userName,invitedCode);
        }
        return template;
    }
}
