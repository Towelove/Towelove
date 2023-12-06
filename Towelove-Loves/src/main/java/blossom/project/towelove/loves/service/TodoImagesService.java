package blossom.project.towelove.loves.service;


import blossom.project.towelove.common.response.todoList.TodoImagesResponse;
import blossom.project.towelove.loves.entity.TodoImages;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 29097
* @description 针对表【todo_images】的数据库操作Service
* @createDate 2023-11-30 17:10:50
*/
public interface TodoImagesService extends IService<TodoImages> {

    void saveBatch(Long id, List<String> images);

    List<TodoImagesResponse> getTodoImagesById(Long todoId);
}