package blossom.project.towelove.loves.convert;

import blossom.project.towelove.common.response.todoList.TodoImagesResponse;
import blossom.project.towelove.loves.entity.TodoImages;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author wangLele
 * @Mail 1819220754@qq.com
 * @date 下午2:32 4/12/2023
 */
@Mapper
public interface TodoImagesConvert {
    TodoImagesConvert INSTANCE = Mappers.getMapper(TodoImagesConvert.class);

    /**
     * 将图片地址转换为TodoImages
     *
     * @param todoId
     * @param image
     * @return
     */
    default TodoImages convert(Long todoId, String image) {
        TodoImages todoImages = new TodoImages();
        todoImages.setTodoId(todoId);
        todoImages.setImageUrl(image);
        todoImages.setDeleted(Boolean.FALSE);
        return todoImages;
    }

    /**
     * 将TodoImages转换为TodoImagesResponse
     *
     * @param todoImages
     * @return
     */
    List<TodoImagesResponse> convert(List<TodoImages> todoImages);
}
