package com.towelove.monitor.test.thread;//package com.towelove.core;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import org.apache.dolphinscheduler.api.controller.WorkThread;
//import org.apache.dolphinscheduler.common.enums.DbType;
//import org.springframework.web.bind.annotation.*;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.TimeZone;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.atomic.AtomicBoolean;
//
//
//@RestController
//@RequestMapping("importDatabase")
//public class Entry {
//
//    /**
//     * @param dbid 数据库的id
//     * @param tablename 表名
//     * @param sftpFileName 文件名称
//     * @param head 是否有头文件
//     * @param splitSign 分隔符
//     * @param type 数据库类型
//     */
//    private static String SFTP_HOST = "192.168.1.92";
//    private static int SFTP_PORT = 22;
//    private static String SFTP_USERNAME = "root";
//    private static String SFTP_PASSWORD = "rootroot";
//    private static String SFTP_BASEPATH = "/opt/testSFTP/";
//    @PostMapping("/thread")
//    @ResponseBody
//    public static JSONObject importDatabase(@RequestParam("dbid") int dbid
//            ,@RequestParam("tablename") String tablename
//            ,@RequestParam("sftpFileName") String sftpFileName
//            ,@RequestParam("head") String head
//            ,@RequestParam("splitSign") String splitSign
//            ,@RequestParam("type") DbType type
//            ,@RequestParam("heads") String heads
//            ,@RequestParam("scolumns") String scolumns
//            ,@RequestParam("tcolumns") String tcolumns ) throws Exception {
//        JSONObject obForRetrun = new JSONObject();
//
//        try {
//
//            JSONArray jsonArray = JSONArray.parseArray(tcolumns);
//            JSONArray scolumnArray = JSONArray.parseArray(scolumns);
//            JSONArray headsArray = JSONArray.parseArray(heads);
//            List<Integer> listInteger = getRrightDataNum(headsArray,scolumnArray);
//            JSONArray bodys = SFTPUtils.getSftpContent(SFTP_HOST,SFTP_PORT,SFTP_USERNAME,SFTP_PASSWORD,SFTP_BASEPATH,sftpFileName,head,splitSign);
//            int total  = bodys.size();
//            int num = 20; //一个批次的数据有多少
//            int count = total/num;//周期
//            int lastNum =total- count*num;//余数
//
//            List<Thread> list = new ArrayList<Thread>();
//            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SS");
//            TimeZone t = sdf.getTimeZone();
//            t.setRawOffset(0);
//            sdf.setTimeZone(t);
//            Long startTime=System.currentTimeMillis();
//
//
//            int countForCountDownLatch = 0;
//            if(lastNum==0){//整除
//                countForCountDownLatch= count;
//            }else{
//                countForCountDownLatch= count + 1;
//            }
//            //子线程
//            CountDownLatch rollBackLatch  = new CountDownLatch(1);
//            //主线程
//            CountDownLatch mainThreadLatch = new CountDownLatch(countForCountDownLatch);
//
//            AtomicBoolean rollbackFlag = new AtomicBoolean(false);
//            StringBuffer message = new StringBuffer();
//            message.append("报错信息：");
//
//            //子线程
//            for(int i=0;i<count;i++) {//这里的count代表有几个线程
//                Thread g = new Thread(new WorkThread(i,num,tablename,jsonArray,dbid,type,bodys,listInteger,mainThreadLatch,rollBackLatch,rollbackFlag,message ));
//                g.start();
//                list.add(g);
//            }
//
//            if(lastNum!=0){//有小数的情况下
//                Thread g = new Thread(new WorkThread(0,lastNum,tablename,jsonArray,dbid,type,bodys,listInteger,mainThreadLatch,rollBackLatch,rollbackFlag,message ));
//                g.start();
//                list.add(g);
//            }
//
////            for(Thread thread:list){
////                System.out.println(thread.getState());
////                thread.join();//是等待这个线程结束;
////            }
//
//            mainThreadLatch.await();
//            //所有等待的子线程全部放开
//            rollBackLatch.countDown();
//
//            //是主线程等待子线程的终止。也就是说主线程的代码块中，如果碰到了t.join()方法，此时主线程需要等待（阻塞），等待子线程结束了(Waits for this thread to die.),才能继续执行t.join()之后的代码块。
//
//
//            Long endTime=System.currentTimeMillis();
//            System.out.println("总共用时: "+sdf.format(new Date(endTime-startTime)));
//
//            if(rollbackFlag.get()){
//                obForRetrun.put("code",500);
//                obForRetrun.put("msg",message);
//            }else{
//                obForRetrun.put("code",200);
//                obForRetrun.put("msg","提交成功！");
//            }
//            obForRetrun.put("data",null);
//        }catch (InterruptedException e){
//            e.printStackTrace();
//            obForRetrun.put("code",500);
//            obForRetrun.put("msg",e.getMessage());
//            obForRetrun.put("data",null);
//        }
//        return obForRetrun;
//
//    }
//
//    /**
//     * 文件里第几列被作为导出列
//     * @param headsArray
//     * @param scolumnArray
//     * @return
//     */
//    public static List<Integer> getRrightDataNum(JSONArray headsArray, JSONArray scolumnArray){
//        List<Integer> list = new ArrayList<Integer>();
//        String arrayA [] = new String[headsArray.size()];
//        for(int i=0;i<headsArray.size();i++){
//            JSONObject ob  = (JSONObject)headsArray.get(i);
//            arrayA[i] =String.valueOf(ob.get("title"));
//        }
//
//        String arrayB [] = new String[scolumnArray.size()];
//        for(int i=0;i<scolumnArray.size();i++){
//            JSONObject ob  = (JSONObject)scolumnArray.get(i);
//            arrayB[i] =String.valueOf(ob.get("columnName"));
//        }
//
//        for(int i =0;i<arrayA.length;i++){
//            for(int j=0;j<arrayB.length;j++){
//                if(arrayA[i].equals(arrayB[j])){
//                    list.add(i);
//                    break;
//                }
//            }
//        }
//
//        return list;
//    }
//}