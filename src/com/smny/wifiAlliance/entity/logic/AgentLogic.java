package com.smny.wifiAlliance.entity.logic;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

public class AgentLogic {
	
	public static List<Map<String,Object>> getFollowersGroup(SqlSession sqlSession,int AgentID) {
        List<Map<String,Object>> AgentDefualtGroup = FollowersGroupLogic.getFollowersGroup(sqlSession,AgentID);
        if (AgentDefualtGroup==null || AgentDefualtGroup.size()==0) {
            AgentDefualtGroup = new LinkedList<Map<String,Object>>(); 
        }
        AgentDefualtGroup.add(FollowersGroupLogic.getGroup(sqlSession,"MyFollList"));
        AgentDefualtGroup.add(FollowersGroupLogic.getGroup(sqlSession,"MyFollBlackList"));
        AgentDefualtGroup.add(FollowersGroupLogic.getGroup(sqlSession,"MyFollGuestList"));
        
        return AgentDefualtGroup;
    }
}
