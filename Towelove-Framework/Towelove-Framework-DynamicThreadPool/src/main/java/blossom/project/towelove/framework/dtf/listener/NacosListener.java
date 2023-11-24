package blossom.project.towelove.framework.dtf.listener;
 
import blossom.project.towelove.framework.dtf.config.ThreadPoolProperty;
import blossom.project.towelove.framework.dtf.core.DynamicThreadPool;
import blossom.project.towelove.framework.dtf.core.ResizableCapacityLinkedBlockIngQueue;
import blossom.project.towelove.framework.dtf.service.SendMailService;
import com.alibaba.fastjson2.JSON;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 项目启动后添加监听，实现ApplicationRunner，在项目成功后会自动执行run方法
 *
 * @author zhang.blossom
 */
@Slf4j
@AutoConfiguration
public class NacosListener implements ApplicationRunner {
    @Resource
    private ConfigService configService;
    @Value("${spring.cloud.nacos.config.group:DEFAULT_GROUP}")
    private String groupId;

    public static final String DATA_ID = "towelove-dynamic-threadpool-dev.yaml";
    @Autowired
    @Qualifier("dynamicThreadPool")
    private ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    private SendMailService sendMailService;

    @Autowired
    private ThreadPoolProperty threadPoolProperty;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //添加nacos配置文件监听
        listenerNacosConfig();
    }
 
    /**
     * 监听数据源变化
     *
     * @throws Exception 异常
     */
    private void listenerNacosConfig() throws Exception {
        configService.addListener(DATA_ID, groupId, new Listener() {
            @Override
            public void receiveConfigInfo(String configInfo) {
                System.out.println("-------改变之前---------");
                System.out.println(threadPoolExecutor);
                System.out.println("------改变之后--------");
                threadPoolExecutor.setCorePoolSize(threadPoolProperty.getCorePoolSize());
                threadPoolExecutor.setMaximumPoolSize(threadPoolProperty.getMaximumPoolSize());
                ResizableCapacityLinkedBlockIngQueue<Runnable> queue = (ResizableCapacityLinkedBlockIngQueue)threadPoolExecutor.getQueue();
                queue.setCapacity(threadPoolProperty.getQueueCapacity());
                System.out.println(threadPoolExecutor);
                System.out.println("---------------");
                sendMailService.sendThreadPoolChangeMail(DynamicThreadPool.threadPoolStatus(threadPoolExecutor));
                System.out.println("线程池修改成功，发送邮件成功");
            }
 
            @Override
            public Executor getExecutor() {
                return null;
            }
        });
    }
 
    /**
     * 向nacos发布内容
     *
     * @param content 内容
     * @throws Exception 异常
     */
    private void publishConfig(String content) throws Exception {
        //发布内容
        configService.publishConfig(DATA_ID, groupId, content);
    }
}