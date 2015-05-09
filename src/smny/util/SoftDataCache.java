package smny.util;

import java.util.Set;
import java.util.HashMap;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.ReferenceQueue;

public class SoftDataCache<V extends SoftObjectDataReuse> extends HashMap<Long,SoftReference<V>> implements Runnable {
    private Thread CacheGuardThread = null;
    private ReferenceQueue<V> CacheQueue = null;
    
    
    
    private SoftObjectCache<SoftObjectReuse> CacheObject = null;
    
    public void ObjectReuse(SoftObjectCache<SoftObjectReuse> cacheObject) {
        this.CacheObject = cacheObject;
    }
    
    
    
    public SoftDataCache() {
        CacheQueue = new ReferenceQueue<V>();
        CacheGuardThread = new Thread(this);
        try {
            CacheGuardThread.setPriority(4);
        } catch (Exception e) {
            e.printStackTrace();
        }
        CacheGuardThread.start();
    }
    public void run() {
        while (true) {
            try {
            		V temValue = RecoveryProcessing(CacheQueue.remove());
            		if(CacheObject!=null && temValue!=null && temValue instanceof SoftObjectReuse){
            				CacheObject.pushCache((SoftObjectReuse)temValue);
            		}
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    
    
    //返回指定键所映射的值；如果此映射不包含该键的映射关系，则返回 null。
    private V RecoveryProcessing(Reference<? extends V> value){
        if(value == null){
        		return null;
        }
    		V TemValue = value.get();
        if(TemValue == null){
        		return null;
        }
        value.clear();
    		this.remove(TemValue.DataReuseKey());
        return TemValue;
    }
    
    
    
    
    public synchronized SoftReference<V> get(Object key){
        return super.get(key);
    }
    public V get(Long key) {
        SoftReference<V> tem = this.get((Object)key);
        if (tem == null) {
            return null;
        }
        return tem.get();
    }
    public V get(long key){
        return this.get(Long.valueOf(key));
    }

    //将指定的值与此映射中的指定键关联（可选操作）。
    public V put(V value) {
        if (value == null){
            return null;
        }
        SoftReference<V> tem = null;
        synchronized(this) {
            tem = super.put(value.DataReuseKey(),new SoftReference<V>(value,CacheQueue));
        }
        if (tem == null) {
            return null;
        }
        V vtem = tem.get();
        tem.clear();
        return vtem;
    }

    //如果存在一个键的映射关系，则将其从此映射中移除（可选操作）。
    public synchronized SoftReference<V> remove(Object key) {
        return super.remove(key);
    }
    public V remove(Long key) {
        SoftReference<V> tem = this.remove((Object)key);
        if (tem == null) {
            return null;
        }
        V vtem = tem.get();
        tem.clear();
        return vtem;
    }

    //如果此映射包含指定键的映射关系，则返回 true。
    public synchronized boolean containsKey(Object key) {
        return super.containsKey(key);
    }

    //如果此映射未包含键-值映射关系，则返回 true。
    public synchronized boolean isEmpty() {
        return super.isEmpty();
    }

    //返回此映射中的键-值映射关系数。
    public synchronized int size() {
        return super.size();
    }

    //从此映射中移除所有映射关系（可选操作）。
    public synchronized void clear() {
        super.clear();
    }
    //从此映射中移除所有映射关系（可选操作）。
    public synchronized Set<Long> keySet() {
        return super.keySet();
    }



}