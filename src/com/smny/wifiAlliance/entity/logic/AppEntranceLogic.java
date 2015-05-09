package com.smny.wifiAlliance.entity.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import smny.MyBatisFactory;

public class AppEntranceLogic {
	 //更新用户数据
    public void Update(Map<String,Object> map) throws Exception {
    	SqlSession sqlSession = MyBatisFactory.openSession();
    	try {
    		sqlSession.update("RouterBatis.AppEntranceMapper.updateEntrance", map);
    		sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			sqlSession.close();
		}
    }
    //商家网店ID (代理商＋_＋登陆帐号)
    //App本地ID (商家网店ID＋:＋App接口登陆ID＋_＋App类型)
    public static AppEntranceLogic AppEntranceGET = new AppEntranceLogic();
    
    
    
    //主键全查询
    public static Map<String,Object> getAppEntrance(String AppEntranceID) {
        SqlSession sqlSession = MyBatisFactory.openSession();
        Map<String,Object> AppEntrance = new HashMap<String,Object>();
    	try {
    		AppEntrance = sqlSession.selectOne("RouterBatis.AppEntranceMapper.SelectEntranceID", AppEntranceID);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
    	return AppEntrance;
    }
    public static List<Map<String,Object>> getDatasBusinessShopID(SqlSession sqlSession,String BusinesShopID) {
		return sqlSession.selectList("RouterBatis.AppEntranceMapper.SelectBusinessShopID",BusinesShopID);
    }
    
    /**
     * 根据原始ID,APP类型查询AppEntrance数据对象
     * @param OriginNumber	原始ID
     * @param AppType		app类型
     * @return
     */
    public static Map<String,Object> getDatasOriginNumber(String OriginNumber, String AppType) {
    	SqlSession sqlSession = MyBatisFactory.openSession();
        Map<String,Object> AppEntrance = new HashMap<String,Object>();
    	try {
    		AppEntrance.put("OriginNumber", OriginNumber);
    		AppEntrance.put("AppType", AppType);
    		AppEntrance = sqlSession.selectOne("RouterBatis.AppEntranceMapper.", AppEntrance);
    		if (AppEntrance.size() == 0) {
    			AppEntrance = null;
            }
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return AppEntrance;
    	
    }
}
