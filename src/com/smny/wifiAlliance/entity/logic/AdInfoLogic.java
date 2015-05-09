package com.smny.wifiAlliance.entity.logic;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import smny.MyBatisFactory;

public class AdInfoLogic {

    //获取商家所有广告
    public static List<Object> getDatasByBusinessShopID(String BusinessShopID) {
    	SqlSession sqlSession = MyBatisFactory.openSession();
    	List<Object> list = null;
		try {
			list = sqlSession.selectList("RouterBatis.AdInfoMapper.SelectShopID", BusinessShopID);
			if (list == null || list.size() == 0) {
	            return null;
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			sqlSession.close();
		} 
        return list;
    }
    
    /**
     * @param Adkey  广告关键词
     * @return	当前广告对象
     */
    public static List<Object> getData(String Adkey) {
    	SqlSession sqlSession = MyBatisFactory.openSession();
    	List<Object> AdInfo = null;
		try {
			AdInfo = sqlSession.selectList("RouterBatis.AdInfoMapper.SelectInfoID", Adkey);
			if (AdInfo == null || AdInfo.size() == 0) {
	            return null;
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}        
        return AdInfo;
    }
}	
