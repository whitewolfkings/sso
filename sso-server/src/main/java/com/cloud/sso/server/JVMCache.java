package com.cloud.sso.server;
import java.util.HashMap;
import java.util.Map;
/**
 * 放在虚拟机的内存
 * @author mmk
 *
 */
public class JVMCache {
	//存放username，再通过HttpClient获取（在实际项目应该放到Memcached中）
    public static Map<String, String> TICKET_AND_NAME = new HashMap<String, String>();
}