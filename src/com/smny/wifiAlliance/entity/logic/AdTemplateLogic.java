package com.smny.wifiAlliance.entity.logic;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import smny.MyBatisFactory;

public class AdTemplateLogic {
	public Map<String,Object> getData(int TemplateID){
		SqlSession sqlSession = MyBatisFactory.openSession();
    	Map<String,Object> AdTemplate = null;
		try {
			AdTemplate = sqlSession.selectOne("RouterBatis.AdTemplateMapper.SelectTemplateID", TemplateID);
			if (AdTemplate == null) {
	            return null;
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			sqlSession.close();
		} 
		return AdTemplate;
	}
}
