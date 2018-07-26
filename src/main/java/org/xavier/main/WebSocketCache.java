package org.xavier.main;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 描述信息：<br/>
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.07.26
 * @since Jdk 1.8
 */
@Component
public class WebSocketCache {
    private ConcurrentHashMap<String, WebSocketServiceImpl> cache = new ConcurrentHashMap();
    private AtomicInteger counter = new AtomicInteger(0);

    public WebSocketServiceImpl queryConnectionBySId(String sid) {
        return cache.get(sid);
    }

    public void saveConnectionBySId(String sid, WebSocketServiceImpl temp) {
        if (cache.contains(sid)) {
            //TODO 切断上一个连接
        }
        cache.put(sid, temp);
        counter.addAndGet(1);
    }

    public Boolean removeConnectionBySId(String sid) {
        if (cache.containsKey(sid)) {
            cache.remove(sid);
            counter.addAndGet(-1);
            return true;
        } else {
            return false;
        }
    }

    public Integer queryCurrentConnection() {
        return counter.get();
    }

    public ConcurrentHashMap<String, WebSocketServiceImpl> getCache() {
        return cache;
    }

    public void setCache(ConcurrentHashMap<String, WebSocketServiceImpl> cache) {
        this.cache = cache;
    }

    public AtomicInteger getCounter() {
        return counter;
    }

    public void setCounter(AtomicInteger counter) {
        this.counter = counter;
    }
}
