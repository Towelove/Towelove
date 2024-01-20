package blossom.project.towelove.common.request.todoList;

import lombok.Data;

import java.util.List;

/**
 * @author wangLele
 * @Mail 1819220754@qq.com
 * @date 15:53 2024/1/19
 */
@Data
public class UpdateWidget {

    private Long coupleId;

    private List<Long> ids;
}
