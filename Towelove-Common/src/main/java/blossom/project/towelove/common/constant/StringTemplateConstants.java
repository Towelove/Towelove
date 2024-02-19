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
    private static final String invitedTemplate = "用户：%s邀请您加入towelove，点击链接【%s】同意他的请求吧！";

    public static String getInvitedTemplate(String userName,String shortUrl){
        return String.format(invitedTemplate,userName,shortUrl);
    }
}
