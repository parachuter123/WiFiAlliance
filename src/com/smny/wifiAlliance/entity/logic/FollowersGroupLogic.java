package com.smny.wifiAlliance.entity.logic;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import smny.MyBatisFactory;

public class FollowersGroupLogic {
	public static Map<String,Object> getGroup(String GroupID) {
		SqlSession sqlSession = MyBatisFactory.openSession();
		try {
				return getGroup(sqlSession,GroupID);
		}finally{
				sqlSession.close();
		}
	}
	public static List<Map<String,Object>> getFollowersGroup(SqlSession sqlSession,int AgentID) {
		return sqlSession.selectList("RouterBatis.FollowersGroupMapper.SelectAgentID",AgentID);
	}
	public static Map<String,Object> getGroup(SqlSession sqlSession,String GroupID) {
		return sqlSession.selectOne("RouterBatis.FollowersGroupMapper.SelectGroupID",GroupID);
	}
	public static List<Map<String,Object>> getFollowersGroup(SqlSession sqlSession,String BusinessShopID) {
		return sqlSession.selectList("RouterBatis.FollowersGroupMapper.SelectShopID", BusinessShopID);
	}
}
