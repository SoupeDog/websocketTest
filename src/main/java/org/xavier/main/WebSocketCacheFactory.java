package org.xavier.main;

/**
 * 描述信息：<br/>
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.07.26
 * @since Jdk 1.8
 */
public class WebSocketCacheFactory {
    private static volatile WebSocketCache webSocketCache = null;

    private WebSocketCacheFactory() {
    }

    public static WebSocketCache getInstance_WebSocketCache() {
        if (webSocketCache == null) {
            synchronized (WebSocketCache.class) {
                if (webSocketCache == null) {
                    webSocketCache = new WebSocketCache();
                }
            }
        }
        return webSocketCache;
    }
}
