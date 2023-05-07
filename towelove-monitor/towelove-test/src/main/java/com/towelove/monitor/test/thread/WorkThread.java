package com.towelove.monitor.test.thread;//package com.towelove.core;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import org.apache.dolphinscheduler.api.service.DataSourceService;
//import org.apache.dolphinscheduler.common.enums.DbType;
//import org.apache.dolphinscheduler.dao.entity.DataSource;
//import org.apache.dolphinscheduler.dao.mapper.DataSourceMapper;
//import org.apache.dolphinscheduler.service.bean.SpringApplicationContext;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.TimeZone;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.atomic.AtomicBoolean;
//
//
///**
// * 多线程
// */
//public class WorkThread implements Runnable{ //建立线程的两种方法 1 实现Runnable 接口 2 继承 Thread 类
//
//    private DataSourceService dataSourceService;
//
//    private DataSourceMapper dataSourceMapper;
//
//    private Integer begin;
//    private Integer end;
//    private String tableName;
//    private JSONArray columnArray;
//    private Integer dbid;
//    private DbType type;
//    private JSONArray bodys;
//    private  List<Integer> listInteger;
//    private PlatformTransactionManager transactionManager;
//    private CountDownLatch mainThreadLatch;
//    private CountDownLatch rollBackLatch;
//    private AtomicBoolean rollbackFlag;
//    private StringBuffer message;
//
//
//
//    /**
//     * @param i
//     * @param num
//     * @param tableFrom
//     * @param columnArrayFrom
//     * @param dbidFrom
//     * @param typeFrom
//     */
//    public WorkThread(int i, int num, String tableFrom, JSONArray columnArrayFrom, int dbidFrom
//            , DbType typeFrom, JSONArray bodysFrom, List<Integer> listIntegerFrom
//            ,CountDownLatch mainThreadLatch,CountDownLatch rollBackLatch,AtomicBoolean rollbackFlag
//            ,StringBuffer messageFrom) {
//        begin=i*num;
//        end=begin+num;
//        tableName = tableFrom;
//        columnArray = columnArrayFrom;
//        dbid = dbidFrom;
//        type = typeFrom;
//        bodys = bodysFrom;
//        listInteger = listIntegerFrom;
//        this.dataSourceMapper = SpringApplicationContext.getBean(DataSourceMapper.class);
//        this.dataSourceService = SpringApplicationContext.getBean(DataSourceService.class);
//        this.transactionManager = SpringApplicationContext.getBean(PlatformTransactionManager.class);
//        this.mainThreadLatch = mainThreadLatch;
//        this.rollBackLatch = rollBackLatch;
//        this.rollbackFlag = rollbackFlag;
//        this.message = messageFrom;
//    }
//
//    public void run() {
//
//        DataSource dataSource = dataSourceMapper.queryDataSourceByID(dbid);
//        String cp = dataSource.getConnectionParams();
//        Connection con=null;
//        con =  dataSourceService.getConnection(type,cp);
//        if(con!=null)
//        {
//            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SS");
//            TimeZone t = sdf.getTimeZone();
//            t.setRawOffset(0);
//            sdf.setTimeZone(t);
//            Long startTime = System.currentTimeMillis();
//            try {
//                con.setAutoCommit(false);
//
////---------------------------- 获取字段和类型
//                String columnString = null;//活动的字段
//                int intForType = 0;
//                String type[] = new String[columnArray.size()];//类型集合
//                for(int i=0;i<columnArray.size();i++){
//                    JSONObject ob = (JSONObject)columnArray.get(i);
//                    if(columnString==null){
//                        columnString = String.valueOf(ob.get("name"));
//                    }else{
//                        columnString = columnString + ","+ String.valueOf(ob.get("name"));
//                    }
//                    type[intForType] = String.valueOf(ob.get("type"));
//                    intForType = intForType + 1;
//                }
//                intForType = 0;
//
//                //这一步是为了形成 insert into "+tableName+"(id,name，age) values (?,?,?);
//                String dataString  = null;
//                for(int i=0;i<columnArray.size();i++){
//                    if(dataString==null){
//                        dataString = "?";
//                    }else{
//                        dataString = dataString +","+"?";
//                    }
//                }
//
////--------------------------------
//
//                StringBuffer sql = new StringBuffer();
//                sql = sql.append("insert into "+tableName+"("+columnString+") values ("+dataString+")") ;
//                PreparedStatement pst= (PreparedStatement)con.prepareStatement(sql.toString());
//                for(int i=begin;i<end;i++) {
//                    JSONObject ob = (JSONObject)bodys.get(i);
//                    if(ob!=null){
//                        String [] array = ob.get(i).toString().split("\\,");
//                        String [] arrayFinal = getFinalData(listInteger,array);
//                        for(int j=0;j<type.length;j++){
//                            String typeString  = type[j].toLowerCase();
//                            int z = j+1;
//                            if("string".equals(typeString)||"varchar".equals(typeString)){
//                                pst.setString(z,arrayFinal[j]);//这里的第一个参数 是指 替换第几个？
//                            }else if("int".equals(typeString)||"bigint".equals(typeString)){
//                                pst.setInt(z,Integer.valueOf(arrayFinal[j]));//这里的第一个参数 是指 替换第几个？
//                            }else if("long".equals(typeString)){
//                                pst.setLong(z,Long.valueOf(arrayFinal[j]));//这里的第一个参数 是指 替换第几个？
//                            }else if("double".equals(typeString)){
//                                pst.setDouble(z,Double.parseDouble(arrayFinal[j]));
//                            }else if("date".equals(typeString)||"datetime".equals(typeString)){
//                                pst.setDate(z, setDateback(arrayFinal[j]));
//                            }else if("Timestamp".equals(typeString)){
//                                pst.setTimestamp(z, setTimestampback(arrayFinal[j]));
//                            }
//                        }
//                    }
//                    pst.addBatch();
//                }
//                pst.executeBatch();
//
//                mainThreadLatch.countDown();
//                rollBackLatch.await();
//
//                if(rollbackFlag.get()){
//                    con.rollback();//表示回滚事务；
//                }else{
//                    con.commit();//事务提交
//                }
//                con.close();
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//                message = message.append(e.getMessage());
//                rollbackFlag.set(true);
//                mainThreadLatch.countDown();
//                try {
//                    con.close();
//                } catch (SQLException throwables) {
//                    throwables.printStackTrace();
//                }
//            }
//            Long endTime = System.currentTimeMillis();
//            System.out.println(Thread.currentThread().getName()+":startTime= "+sdf.format(new Date(startTime))+",endTime= "+sdf.format(new Date(endTime))
//                    +" 用时："+sdf.format(new Date(endTime - startTime)));
//
//        }
//    }
//
//
//    public java.sql.Date setDateback(String dateString) throws ParseException {
//        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
//        java.util.Date date = sdf.parse( "2015-5-6 10:30:00" );
//        long lg = date.getTime();// 日期 转 时间戳
//        return new java.sql.Date( lg );
//    }
//
//    public java.sql.Timestamp setTimestampback(String dateString) throws ParseException {
//        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
//        java.util.Date date = sdf.parse( "2015-5-6 10:30:00" );
//        long lg = date.getTime();// 日期 转 时间戳
//        return new java.sql.Timestamp( lg );
//    }
//
//    public String [] getFinalData(List<Integer> listInteger,String[] array){
//        String [] arrayFinal = new String [listInteger.size()];
//        for(int i=0;i<listInteger.size();i++){
//            int a = listInteger.get(i);
//            arrayFinal[i] = array[a];
//        }
//        return arrayFinal;
//    }
//}