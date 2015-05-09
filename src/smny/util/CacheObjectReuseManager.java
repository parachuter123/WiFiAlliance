package smny.util;
//Tcp通讯主接口工厂类
public class CacheObjectReuseManager{
    
    private static SoftDataCache<SoftObjectDataReuse> DataCache;
    private static SoftObjectCache<SoftObjectReuse> ObjectCache;
    
    
    
    private static synchronized SoftDataCache<SoftObjectDataReuse> getDataCacheImp(){
        if(DataCache == null){
        		DataCache = new SoftDataCache<SoftObjectDataReuse>();
        }
        return DataCache;
    }
    private static synchronized SoftObjectCache<SoftObjectReuse> getObjectCacheImp(){
        if(ObjectCache == null){
        		ObjectCache = new SoftObjectCache<SoftObjectReuse>();
        }
        return ObjectCache;
    }
    
    
    public static SoftDataCache<SoftObjectDataReuse> getDataCache(){
        if(DataCache == null){
        		return getDataCacheImp();
        }
        return DataCache;
    }
    public static SoftObjectCache<SoftObjectReuse> getObjectCache(){
        if(ObjectCache == null){
        		return getObjectCacheImp();
        }
        return ObjectCache;
    }
    
    public static void main(String args[]){
	    	
	    	
	    	
	    	
	    	
    }
}
