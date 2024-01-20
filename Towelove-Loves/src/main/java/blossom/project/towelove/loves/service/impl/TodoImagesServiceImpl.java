package blossom.project.towelove.loves.service.Impl;

import blossom.project.towelove.common.response.todoList.TodoImagesResponse;
import blossom.project.towelove.loves.convert.TodoImagesConvert;
import blossom.project.towelove.loves.entity.TodoImages;
import blossom.project.towelove.loves.mapper.TodoImagesMapper;
import blossom.project.towelove.loves.service.TodoImagesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author 29097
* @description 针对表【todo_images】的数据库操作Service实现
* @createDate 2023-11-30 17:10:50
*/
@Service
public class TodoImagesServiceImpl extends ServiceImpl<TodoImagesMapper, TodoImages>
    implements TodoImagesService{

    @Override
    @Transactional
    public void saveBatch(Long todoId, List<String> images) {
        List<TodoImages> Todoimages = images.stream().map(image -> TodoImagesConvert.INSTANCE.convert(todoId, image)).toList();
        this.saveBatch(Todoimages);
    }

    @Override
    public List<TodoImagesResponse> getTodoImagesById(Long todoId) {
        return TodoImagesConvert.INSTANCE.convert(this.lambdaQuery().eq(TodoImages::getTodoId, todoId).list());
    }
}




