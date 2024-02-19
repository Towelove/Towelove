package blossom.project.towelove.common.utils;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author 张锦标
 */
@Slf4j
public class DateUtil {

    public static long getCurrentTimestamp() {
        return (System.currentTimeMillis() / 1000);
    }

    public static long toUnixTimestamp(LocalDateTime date) {
        return date.toEpochSecond(ZoneOffset.UTC);
    }

    public static LocalDateTime fromUnixTimestamp(long timestamp) {
        return LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.UTC);
    }


    public static String toHourMinuteSeconds(int totalSecs) {
        int hours = totalSecs / 3600;
        int minutes = (totalSecs % 3600) / 60;
        int seconds = totalSecs % 60;

        return String.format("%02d hour(s) %02d minute(s) %02d second(s)", hours, minutes, seconds);
    }

    /**
     * 比较两个日期大小并返回较大的日期
     */
    public static Date max(Date date1, Date date2) {
        if (date1 == null) {
            return date2;
        }

        if (date2 == null) {
            return date1;
        }

        if (date1.after(date2)) {
            return date1;
        } else {
            return date2;
        }
    }

    /**
     * 比较两个日期大小并返回较小的日期
     */
    public static Date min(Date date1, Date date2) {
        if (date1 == null) {
            return date2;
        }

        if (date2 == null) {
            return date1;
        }

        if (date1.before(date2)) {
            return date1;
        } else {
            return date2;
        }
    }

    public static int timeOfDay(int timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp * 1000L);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static int getSeconds(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }

    public static int getMinutes(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 按天增加日期
     */
    public static Date addDateByDay(Date date, int days) {
        return addDateByHour(date, days * 24);
    }

    /**
     * 按小时增加日期
     */
    public static Date addDateByHour(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hours);
        return calendar.getTime();
    }

    /**
     * 按分钟增加日期
     */
    public static Date addDateByMinute(Date date, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    /**
     * 按秒增加日期
     */
    public static Date addDateBySeconds(Date date, Integer seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    /**
     * 按天回退日期
     */
    public static Date minusDateByDay(Date date, int days) {
        return addDateByDay(date, (-1) * days);
    }

    /**
     * 按小时回退日期
     */
    public static Date minusDateByHour(Date date, int hours) {
        return addDateByHour(date, (-1) * hours);
    }

    /**
     * 按分钟回退日期
     */
    public static Date minusDateByMinute(Date date, int minutes) {
        return addDateByMinute(date, (-1) * minutes);
    }

    /**
     * 按秒回退日期
     */
    public static Date minusDateBySeconds(Date date, int seconds) {
        return addDateBySeconds(date, (-1) * seconds);
    }

    /**
     * 将 java 日期类型 Date 转换为 Unix 时间戳
     */
    public static Integer getUnixTimestamp(Date date) {
        if (date == null) {
            return null;
        }
        return (int) (date.getTime() / 1000);
    }

    public static Long currentTimestamp() {
        return System.currentTimeMillis() / 1000;
    }

    public static Long toUnixTimestamp(Date date) {
        if (date == null) {
            return null;
        }
        return date.getTime() / 1000;
    }

    public static List<Integer> generateTimestampSequence(int startTimestamp, int interval,
                                                          int number) {
        List<Integer> timestamps = Lists.newArrayList();
        for (int index = 0; index <= number; index++) {
            Integer currentTimestamp = startTimestamp + index * interval;
            timestamps.add(currentTimestamp);
        }

        return timestamps;
    }

    /**
     * return a resource slice boundary according to slice size and direction
     *
     * @param date        时间
     * @param sliceLength 时间片长度（分钟）
     * @param backward    向后粘滞
     * @return Date boundary
     */
    public static Date stickDateTime(Date date, int sliceLength, boolean backward) {
        int slideCount;
        if (backward) {
            slideCount = getMinutes(date) / sliceLength;
        } else {
            slideCount = (int) Math.ceil(getMinutes(date) / (double) sliceLength);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        DateTime dateStart = new DateTime(calendar.getTime());
        return dateStart.plusMinutes(slideCount * sliceLength).toDate();
    }

    /**
     * @param timestamp   某一特定时刻的时间戳
     * @param sliceLength 时间片长度（分钟）
     * @param backward    向前粘滞
     * @return 粘滞后的时间（时间戳）
     */
    public static int stickTimestamp(int timestamp, int sliceLength, boolean backward) {
        Date date = new Date(timestamp * 1000L);
        int slideCount;
        timestamp -= (getMinutes(date) * 60 + getSeconds(date));
        if (backward) {
            slideCount = getMinutes(date) / sliceLength;
        } else {
            slideCount = (int) Math.ceil(getMinutes(date) / (double) sliceLength);
        }
        timestamp += slideCount * sliceLength * 60;
        return timestamp;
    }

    /**
     * 获取以 sliceLength 为分隔的时间点
     *
     * @param from        开始时间
     * @param to          结束时间
     * @param sliceLength 时间间隔（分钟）
     */
    public static List<Date> getSeparatedTimePointsOpen(Date from, Date to, int sliceLength) {
        // 取 >= from 的时间点
        from = stickDateTime(from, sliceLength, false);
        // 取 <= to 的时间点
        to = stickDateTime(to, sliceLength, true);

        return getSeparatedTimePoints(from, to, sliceLength);
    }

    public static List<Date> getSeparatedTimePointsClosed(Date from, Date to, int sliceLength) {
        // 取 < from 的时间点
        from = stickDateTime(from, sliceLength, true);
        // 取 > to 的时间点
        to = stickDateTime(to, sliceLength, false);

        return getSeparatedTimePoints(from, to, sliceLength);
    }

    private static List<Date> getSeparatedTimePoints(Date from, Date to, int sliceLength) {
        List<Date> resultList = Lists.newArrayList();
        while (from.before(to)) {
            resultList.add(from);
            from = addDateByMinute(from, sliceLength);
        }

        return resultList;
    }

    /**
     * 获取两个时间之间的秒数
     */
    public static int getIntervalSeconds(Date startDate, Date endDate) {
        return (int) ((endDate.getTime() - startDate.getTime()) / 1000);
    }


    /**
     * 获取两个时间之间的分钟数
     */
    public static int getIntervalMinutes(Date startDate, Date endDate) {
        return (int) ((endDate.getTime() - startDate.getTime()) / (1000 * 60));
    }

    /**
     * 获取两个时间之间的小时数
     *
     * @param isFloor 是否向下取整
     */
    public static int getIntervalHours(Date startDate, Date endDate, boolean isFloor) {
        double intervalHours =
                (double) (endDate.getTime() - startDate.getTime()) / (double) (1000 * 60 * 60);
        return (int) (isFloor ? intervalHours : Math.ceil(intervalHours));
    }


    /**
     * 获取一天开始的时间 00:00:00
     */
    public static Date getBeginningOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取日期的日部分
     */
    public static int getDayPartOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String[] dateStr = format.format(date).split("-");

        return Integer.parseInt(dateStr[2]);
    }

    /**
     * 获取日期的年部分
     */
    public static int getYearPartOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String[] dateStr = format.format(date).split("-");

        return Integer.parseInt(dateStr[0]);
    }

    /**
     * 获取一天结束的时间 23:59:59
     */
    public static Date getEndOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    public static Date trimTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date toDate(Long timestamp) {
        if(Objects.isNull(timestamp)){
            return null;
        }
        return new Date(timestamp * 1000);
    }

    public static Date convertString2Date(String dateTimeStr){
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime dateTime = DateTime.parse(dateTimeStr, formatter);
        return dateTime.toDate();
    }
}


