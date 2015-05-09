package com.smny.wifiAlliance.entity.logic;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import smny.MyBatisFactory;

public class InfoTemplateLogic {
	public static List<Map<String,Object>> getWatchTemplate(SqlSession sqlSession,String BusinesShopID) {
		return sqlSession.selectList("RouterBatis.InfoTemplateMapper.WatchTemplate",BusinesShopID);
	}
	public static Map<String,Object> getInfoTemplateByID(SqlSession sqlSession,String id) {
		return sqlSession.selectOne("RouterBatis.InfoTemplateMapper.SelectKey",id);
	}
	
	public void create(Map<String,Object> map) throws Exception{
        SqlSession sqlSession = MyBatisFactory.openSession();
        try {
        	sqlSession.insert("RouterBatis.InfoTemplateMapper.insertInfo", map);
        	sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
	}
	public void updateInfoTemplate(Map<String,Object> map){
        SqlSession sqlSession = MyBatisFactory.openSession();
        try {
        	sqlSession.update("RouterBatis.InfoTemplateMapper.updateInfo", map);
        	sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
	}
}
