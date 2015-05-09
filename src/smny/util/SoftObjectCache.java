package smny.util;

import java.util.Map;
import java.util.HashMap;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.ref.SoftReference;
import java.lang.ref.ReferenceQueue;

public class SoftObjectCache<V extends SoftObjectReuse> implements Runnable {
    
    private Class<? extends V> CacheObjectClass = null;
    public void setCacheObjectClass(Class<? extends V> cacheObjectClass) throws InstantiationException,IllegalAccessException{
        cacheObjectClass.newInstance();
        this.CacheObjectClass = cacheObjectClass;
    }
    
    private ReferenceQueue<V> WaitQueue = null;
    //<WeakReference.hashCode(),WeakReference<V>>
    private HashMap<Integer,WeakReference<V>> WaitObject = null;
    /*********************************************************
    *                  CacheObject˵��
    *		Key:      V.getClass().hashCode()
    *
    *		Value:    Map<SoftReference.hashCode(),SoftReference<V>>
    *
    *********************************************************/
    private ReferenceQueue<V> CacheQueue = null;
    private Map<Integer,Map<Integer,SoftReference<V>>> CacheObject = null;
    
    private Thread CacheGuardThread = null;
    public SoftObjectCache() {
        WaitQueue = new ReferenceQueue<V>();
        CacheQueue = new ReferenceQueue<V>();
        
        WaitObject = new HashMap<Integer,WeakReference<V>>();
        CacheObject = new HashMap<Integer,Map<Integer,SoftReference<V>>>();
        
        
        CacheGuardThread = new Thread(this);
        try {
            CacheGuardThread.setPriority(4);
        } catch (Exception e) {
            e.printStackTrace();
        }
        CacheGuardThread.start();
    }
    
    
    
    public void run() {
        V temValue = null;
        Reference<? extends V> Refe = null;
        WeakReference<V> Wait = null;
        SoftReference<V> Cache = null;
        while (true) {
            try {
				        //�ͷ��ڴ�
				        while((Refe=CacheQueue.poll()) != null){
					        	Cache = removeCache(Refe);
					        	if(Cache != null){
						        		Cache.clear();
					        	}else{
					        			Refe.clear();
					        	}
				        }
				        Cache = null;
				        //�������
				        while((Refe=WaitQueue.poll()) != null){
					        	Wait = removeWait(Refe);
					        	if(Wait != null){
						        		Wait.clear();
						        		pushCache(Wait.get());
					        	}else{
					        			Refe.clear();
					        	}
				        }
				        Wait = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //����ʹ����ɣ������ȴ��б�
    private WeakReference<V> removeWait(Reference<? extends V> Refe) {
        if(Refe != null){
		        synchronized(WaitObject){
		        		return WaitObject.remove(Integer.valueOf(Refe.hashCode()));
		        }
        }
        return null;
    }
    //�õ��¶��񣬲��ȴ�ʹ����ɺ�����Զ�����
    private V getValueObject(Class<? extends V> ObjectClass) throws InstantiationException,IllegalAccessException{
        V Value = ObjectClass.newInstance();
        ReuseObject(Value);
        return Value;
    }
    //����ȴ�����
    private void ReuseObject(V Value){
    		if(Value == null){
    				return;
    		}
    		WeakReference<V> WeakRef = new WeakReference<V>(Value,WaitQueue);
    		synchronized(WaitObject){
    				WaitObject.put(Integer.valueOf(WeakRef.hashCode()),WeakRef);
    		}
    }
    
    //---------------------------------------------------------------------------------------------------------
    
    
    //�ڴ�Խ����ջ�ָ��������������ڴ�
    private SoftReference<V> removeCache(Reference<? extends V> Refe) {
        if(Refe == null){
        		return null;
        }
        V Value = Refe.get();
        if(Value == null){
        		return null;
        }
        Integer  Value_Class_HashCode = Integer.valueOf(Value.getClass().hashCode());
        Map<Integer,SoftReference<V>>  CacheClass = null;
        synchronized(CacheObject){
        		CacheClass = CacheObject.get(Value_Class_HashCode);
        		if(CacheClass == null){
        				return null;
        		}
        		/*
	        		else if(CacheClass.isEmpty()){
	        				CacheObject.remove(Value_Class_HashCode);
	        				return null;
	        		}
        		*/
        }
        synchronized(CacheClass){
        		return CacheClass.remove(Integer.valueOf(Refe.hashCode()));
        }
    }
    //������뻺��
    protected void pushCache(V Value) {
        if(Value == null){
        		return;
        }
    		Value.clear();
        Integer  Value_Class_HashCode = Integer.valueOf(Value.getClass().hashCode());
        Map<Integer,SoftReference<V>>  CacheClass = null;
        synchronized(CacheObject){
        		CacheClass = CacheObject.get(Value_Class_HashCode);
        		if(CacheClass == null){
        				CacheClass = new HashMap<Integer,SoftReference<V>>();
        				CacheObject.put(Value_Class_HashCode,CacheClass);
        		}
        }
    		SoftReference<V> SoftRef = new SoftReference<V>(Value,WaitQueue);
        synchronized(CacheObject){
    				CacheClass.put(Integer.valueOf(SoftRef.hashCode()),SoftRef);
        }
    }
    //����һ���������
    private SoftReference<V> CachePop(Class<? extends V> ObjectClass){
        Integer  Value_Class_HashCode = Integer.valueOf(ObjectClass.hashCode());
        Map<Integer,SoftReference<V>>  CacheClass = null;
        synchronized(CacheObject){
        		CacheClass = CacheObject.get(Value_Class_HashCode);
        		if(CacheClass == null){
        				return null;
        		}
        }
        synchronized(CacheClass){
        		if(!CacheClass.isEmpty()){
        				Integer KeyInt = CacheClass.keySet().iterator().next();
        				return CacheClass.remove(KeyInt);
        		}
        }
        return null;
    }
    
    //�õ����󣬲�ʹ����ȴ�����
    public V get(Class<? extends V> ObjectClass)throws InstantiationException,IllegalAccessException{
        SoftReference<V> Value = CachePop(ObjectClass);
        if(Value==null || Value.get()==null){
        		return getValueObject(ObjectClass);
        }
        V tem = Value.get();
        Value.clear();
        ReuseObject(tem);
        return tem;
    }
    public V get(){
        try{
        		return get(CacheObjectClass);
        }catch(InstantiationException e){
        		e.printStackTrace();
        }catch(IllegalAccessException e){
        		e.printStackTrace();
        }catch(NullPointerException e){
        		e.printStackTrace();
        		throw new NullPointerException("δ��ʼ�������Զ��������Class:CacheObjectClass");
        }
        return null;
    }


}