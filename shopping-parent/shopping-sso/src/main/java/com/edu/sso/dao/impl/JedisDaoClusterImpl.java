package com.edu.sso.dao.impl;

import com.edu.sso.dao.JedisDao;
import redis.clients.jedis.JedisCluster;

public class JedisDaoClusterImpl implements JedisDao {

    private JedisCluster cluster;
    @Override
    public String set(String key, String value) {
        return cluster.set(key,value);
    }

    @Override
    public String get(String key) {
        return cluster.get(key);
    }

    @Override
    public long hset(String hkey, String key, String value) {
        return cluster.hset(hkey,key,value);
    }

    @Override
    public String hget(String hkey, String key) {
        return cluster.hget(hkey,key);
    }

    @Override
    public long incr(String key) {
        return cluster.incr(key);
    }

    @Override
    public long del(String key) {
        return cluster.del(key);
    }

    @Override
    public long hdel(String hkey, String key) {
        return cluster.hdel(hkey,key);
    }

    @Override
    public long expire(String key, int seconds) {
        return cluster.expire(key,seconds);
    }
}
