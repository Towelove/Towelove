package blossom.project.towelove.loves.convert;

import blossom.project.towelove.common.request.todoList.InsertTodoRequest;
import blossom.project.towelove.loves.entity.TodoList;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author wangLele
 * @Mail 1819220754@qq.com
 * @date 上午10:14 1/12/2023
 */
@Mapper
public interface TodoListConvert {
    TodoListConvert INSTANCE = Mappers.getMapper(TodoListConvert.class);

    TodoList convert(InsertTodoRequest insertTodoRequest);

}
