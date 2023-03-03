package com.towelove.spring.cloud.mq.domain;

public class MessageConstant {

    //生产者-集群消息主题
    public static String CLUSTER_MESSAGE_OUTPUT="cluster-out-0";
    //生产者-广播消息主题
    public static String BROADCAST_MESSAGE_OUTPUT="broadcast-out-0";
    //生产者-延时消息主题
    public static String DELAYED_MESSAGE_OUTPUT="delayed-out-0";


    //消费者-集群消息主题
    public static String CLUSTER_MESSAGE_INPUT="cluster-in-0";
    //消费者-广播消息主题
    public static String BROADCAST_MESSAGE_INPUT="broadcast-in-0";
    //消费者-延时消息主题
    public static String DELAYED_MESSAGE_INPUT="delayed-in-0";

}