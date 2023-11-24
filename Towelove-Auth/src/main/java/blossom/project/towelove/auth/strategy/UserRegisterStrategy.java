package blossom.project.towelove.auth.strategy;

import blossom.project.towelove.common.request.auth.AuthLoginRequest;
import blossom.project.towelove.common.request.auth.AuthRegisterRequest;
import org.springframework.beans.factory.InitializingBean;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.user.strategy
 * @className: UserRegisterStrategy
 * @author: Link Ji
 * @description: TODO
 * @date: 2023/11/24 21:16
 * @version: 1.0
 */
public interface UserRegisterStrategy extends InitializingBean {

    boolean valid(AuthLoginRequest authLoginRequest);
}
