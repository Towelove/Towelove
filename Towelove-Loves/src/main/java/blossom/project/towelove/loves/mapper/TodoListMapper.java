package blossom.project.towelove.loves.mapper;


import blossom.project.towelove.loves.entity.TodoList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 29097
* @description 针对表【todolist】的数据库操作Mapper
* @createDate 2023-11-30 17:10:50
* @Entity blossom.project.towelove.loves.entity.Todolist
*/
@Mapper
public interface TodoListMapper extends BaseMapper<TodoList> {

    List<TodoList> selectAllByCoupleId(@Param("coupleId")Long coupleId);

    int selectWidgetCounts(@Param("coupleId") Long coupleId);
}




