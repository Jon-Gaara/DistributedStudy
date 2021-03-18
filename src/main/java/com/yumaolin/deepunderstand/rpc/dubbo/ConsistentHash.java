package com.yumaolin.deepunderstand.rpc.dubbo;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;

/**
 * @author yml
 * @Description 一致性hash
 * @Date 2021-03-18 11:27
 */
public class ConsistentHash {

    private TreeMap<Long,Object> treeMap;
    /**
     * 真实机器节点数
     */
    private List<Object> shards;
    /**
     * 虚拟机器节点数
     */
    private volatile int node_num = 100;

    public ConsistentHash(List<Object> shards,int node_num){
        this.shards = shards;
        this.node_num = node_num;
    }

    public void init(){
        treeMap = new TreeMap<>();
        for(int i=0;i<shards.size();i++){
            for(int j=0;j<node_num;j++){
                String key = "shard-"+i+"-node-"+j;
                treeMap.put(hash(key),shards.get(i));
            }
        }
    }

    public Object selectForKey(String key){
        Long longKey = hash(key);
        Map.Entry<Long,Object> entry = treeMap.ceilingEntry(longKey);
        //如果为空，就去第一个节点
        if(entry == null ){
            entry = treeMap.firstEntry();
        }
        return entry.getValue();
    }

    public void printMap() {
        System.out.println(treeMap);
    }

    /**
     *  MurMurHash算法，是非加密HASH算法，性能很高，
     *  比传统的CRC32,MD5，SHA-1（这两个算法都是加密HASH算法，复杂度本身就很高，带来的性能上的损害也不可避免）
     *  等HASH算法要快很多，而且据说这个算法的碰撞率很低.
     *  http://murmurhash.googlepages.com/
     */
    private Long hash(String key) {
        ByteBuffer buf = ByteBuffer.wrap(key.getBytes());
        int seed = 0x1234ABCD;
        ByteOrder byteOrder = buf.order();
        buf.order(ByteOrder.LITTLE_ENDIAN);
        long m = 0xc6a4a7935bd1e995L;
        int r = 47;
        long h = seed ^ (buf.remaining() * m);
        long k;
        while (buf.remaining() >= 8) {
            k = buf.getLong();
            k *= m;
            k ^= k >>> r;
            k *= m;
            h ^= k;
            h *= m;
        }
        if (buf.remaining() > 0) {
            ByteBuffer finish = ByteBuffer.allocate(8).order(
                    ByteOrder.LITTLE_ENDIAN);
            // for big-endian version, do this first:
            // finish.position(8-buf.remaining());
            finish.put(buf).rewind();
            h ^= finish.getLong();
            h *= m;
        }
        h ^= h >>> r;
        h *= m;
        h ^= h >>> r;
        buf.order(byteOrder);
        return h;
    }

    public static void main(String[] args) {
        Random ran = new Random();
        List<Object> objectList = new ArrayList<>();
        objectList.add("192.168.0.0-服务器0");
        objectList.add("192.168.0.1-服务器1");
        objectList.add("192.168.0.2-服务器2");
        objectList.add("192.168.0.3-服务器3");
        objectList.add("192.168.0.4-服务器4");
        ConsistentHash hash = new ConsistentHash(objectList,4);
        hash.init();
        hash.printMap();
        //循环50次，是为了取50个数来测试效果，当然也可以用其他任何的数据来测试
        for(int i=0; i<20; i++) {
            System.out.println(hash.selectForKey(String.valueOf(i)+ran.nextInt(100)));
        }
    }
}
