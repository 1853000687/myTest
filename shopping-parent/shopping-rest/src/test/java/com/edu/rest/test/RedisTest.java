package com.edu.rest.test;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

public class RedisTest {
    @Test
   public void test() {
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("101.201.155.26",6379);
        System.out.println("连接成功");
        //设置 redis 字符串数据
        jedis.set("runoobkey", "www.runoob.com");
        // 获取存储的数据并输出
        System.out.println("redis 存储的字符串为: "+ jedis.get("runoobkey"));
        jedis.close();
   }
    @Test
    public void test2() {
        JedisPool pool = new JedisPool("101.201.155.26",6379);
        //连接本地的 Redis 服务
        Jedis jedis = pool.getResource();
        System.out.println("连接成功");
        //设置 redis 字符串数据
        jedis.set("k1", "www.runoob.com");
        // 获取存储的数据并输出
        System.out.println("redis 存储的字符串为: "+ jedis.get("runoobkey"));
        jedis.close();
    }
}
