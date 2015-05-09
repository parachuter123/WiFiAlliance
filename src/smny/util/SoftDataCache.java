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
    
    
    
    //����ָ������ӳ���ֵ�������ӳ�䲻�����ü���ӳ���ϵ���򷵻� null��
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

    //��ָ����ֵ���ӳ���е�ָ������������ѡ��������
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

    //�������һ������ӳ���ϵ������Ӵ�ӳ�����Ƴ�����ѡ��������
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

    //�����ӳ�����ָ������ӳ���ϵ���򷵻� true��
    public synchronized boolean containsKey(Object key) {
        return super.containsKey(key);
    }

    //�����ӳ��δ������-ֵӳ���ϵ���򷵻� true��
    public synchronized boolean isEmpty() {
        return super.isEmpty();
    }

    //���ش�ӳ���еļ�-ֵӳ���ϵ����
    public synchronized int size() {
        return super.size();
    }

    //�Ӵ�ӳ�����Ƴ�����ӳ���ϵ����ѡ��������
    public synchronized void clear() {
        super.clear();
    }
    //�Ӵ�ӳ�����Ƴ�����ӳ���ϵ����ѡ��������
    public synchronized Set<Long> keySet() {
        return super.keySet();
    }



}