import cn.hutool.core.date.LocalDateTimeUtil;
import com.alibaba.fastjson2.util.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.util.Calendar;

/**
 * @projectName: Towelove
 * @package: PACKAGE_NAME
 * @className: TimeTest
 * @author: Link Ji
 * @description: TODO
 * @date: 2023/11/26 18:03
 * @version: 1.0
 */
public class TimeTest {
    @Test
    void test(){
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.DAY_OF_MONTH, 1); // 设置为本月的第一天
//        int month = calendar.get(Calendar.MONTH);
//        int firstDay = calendar.get(Calendar.DAY_OF_MONTH);
//        calendar.add(Calendar.MONTH, 1); // 将日期增加一个月
//        calendar.add(Calendar.DAY_OF_MONTH, -1); // 将日期减去一天，即为本月的最后一天
//        int lastDay = calendar.get(Calendar.DAY_OF_MONTH);
//
//        System.out.printf("本月的第一天：%s%s%n",month,firstDay);
//        System.out.printf("本月的最后一天：%s%s%n",month,lastDay);
//        LocalDate currentDate = LocalDate.now();
//        LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);
//        LocalDate lastDayOfMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth());
//        System.out.println("本月第一天：" + firstDayOfMonth.toString().replaceAll("-","").substring(4,8));
//        System.out.println("本月最后一天：" + lastDayOfMonth);
//        Calendar calendar = Calendar.getInstance();
//        int mouth = calendar.get(Calendar.MONTH);
//        calendar.add(Calendar.DAY_OF_MONTH,-1);
//        int firstDay = mouth * 100 + 1;
//        int lastDay = mouth * 100 + calendar.get(Calendar.DAY_OF_MONTH);
//        System.out.println(firstDay);
//        System.out.println(lastDay);

    }

    volatile int count = 0;
    public final Object lock = new Object();
    @Test
    public void test1(){
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                while (count < 100) {
                    synchronized (lock) {
                        if (count % 3 != Integer.parseInt(Thread.currentThread().getName())) {
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            System.out.printf("【线程：%s】打印%s%n", Thread.currentThread().getName(), Integer.parseInt(Thread.currentThread().getName()) + 1);
                            count++;
                            //唤醒其他线程
                            lock.notifyAll();
                        }
                    }
                }
            }, String.valueOf(i)).start();
        }
        while (true);
    }
}
