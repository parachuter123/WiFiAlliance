package com.smny.wifiAlliance.entity.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import smny.MyBatisFactory;

public class BlackWhiteListLogic {
    public static Map<String,Object> getData(String BlackWhiteListID) {
    	SqlSession sqlSession = MyBatisFactory.openSession();
    	Map<String,Object> BlackWhiteList = new HashMap<String,Object>();
    	try {
    		BlackWhiteList = sqlSession.selectOne("RouterBatis.BlackWhiteListMapper.SelectBlackWhiteListID", BlackWhiteListID);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
    	return BlackWhiteList;
    }
}
