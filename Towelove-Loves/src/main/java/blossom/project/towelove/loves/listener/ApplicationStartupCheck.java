package blossom.project.towelove.loves.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;
/**
 * @author: ZhangBlossom
 * @date: 2023/12/6 18:08
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * 自定义监听器模板
 * 1：如果泛型指定的是ApplicationEvent类型
 * 那么表示监听所有的事件
 */
@Component
public class ApplicationStartupCheck implements ApplicationListener<ApplicationReadyEvent> {

    private final DataSource dataSource;

    public ApplicationStartupCheck(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try (Connection connection = dataSource.getConnection()) {
            // 检查数据库连接是否成功
            if (connection.isValid(1)) {
                System.out.println("Application started successfully with a valid database connection!");
            } else {
                System.err.println("Application started but the database connection is not valid!");
            }
        } catch (Exception e) {
            System.err.println("Application started but failed to connect to the database: " + e.getMessage());
        }
    }
}