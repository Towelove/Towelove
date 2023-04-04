package com.towelove.common.redis.service;

/**
 * @author: 张锦标
 * @date: 2023/4/4 11:58
 * MyBloomFilter类
 * 自己实现的布隆过滤器
 * 也可以直接使用guava 效果更好
 */
import com.google.common.hash.Funnels;

import java.util.BitSet;

public class MyBloomFilter {
    //public static void main(String[] args) {
    //    //我们创建了一个最多存放 最多 1500 个整数的布隆过滤器，
    //    // 并且我们可以容忍误判的概率为百分之（0.01）
    //    com.google.common.hash.BloomFilter<Integer> bloomFilter = com.google.common.hash.BloomFilter.create(
    //            Funnels.integerFunnel(),
    //            1500,0.01
    //    );
    //}

    /**
     * 位数组的大小
     */
    private static final int DEFAULT_SIZE = 2 << 24;
    /**
     * 通过这个数组可以创建 6 个不同的哈希函数
     */
    private static final int[] SEEDS = new int[]{3, 13, 46, 71, 91, 134};

    /**
     * 位数组。数组中的元素只能是 0 或者 1
     */
    private BitSet bits = new BitSet(DEFAULT_SIZE);

    /**
     * 存放包含 hash 函数的类的数组
     */
    private SimpleHash[] func = new SimpleHash[SEEDS.length];

    /**
     * 初始化多个包含 hash 函数的类的数组，每个类中的 hash 函数都不一样
     */
    public MyBloomFilter() {
        // 初始化多个不同的 Hash 函数
        for (int i = 0; i < SEEDS.length; i++) {
            func[i] = new SimpleHash(DEFAULT_SIZE, SEEDS[i]);
        }
    }

    /**
     * 添加元素到位数组
     */
    public void add(Object value) {
        for (SimpleHash f : func) {
            bits.set(f.hash(value), true);
        }
    }

    /**
     * 判断指定元素是否存在于位数组
     */
    public boolean contains(Object value) {
        boolean ret = true;
        for (SimpleHash f : func) {
            ret = ret && bits.get(f.hash(value));
        }
        return ret;
    }

    /**
     * 静态内部类。用于 hash 操作！
     */
    public static class SimpleHash {
        //容量
        private int cap;
        //hash种子
        private int seed;

        public SimpleHash(int cap, int seed) {
            this.cap = cap;
            this.seed = seed;
        }

        /**
         * 计算 hash 值
         */
        public int hash(Object value) {
            int h;
            return (value == null) ? 0 : Math.abs(seed * (cap - 1)
                    & ((h = value.hashCode()) ^ (h >>> 16)));
        }

    }
}
