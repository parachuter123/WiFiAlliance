package com.smny.wifiAlliance.entity.logic;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

public class BusinessShopLogic {
    /**
     * 获取客户分组
     * @param AgentID 			代理ID
     * @param BusinesShopID		商家ID
     * @return
     */
    public static List<Map<String,Object>> getFollowersGroup(SqlSession sqlSession,int AgentID,String BusinesShopID) {
    	List<Map<String,Object>> AgentDefualtGroup = AgentLogic.getFollowersGroup(sqlSession,AgentID);
    	if(AgentDefualtGroup==null || AgentDefualtGroup.size()==0){
			AgentDefualtGroup = FollowersGroupLogic.getFollowersGroup(sqlSession,BusinesShopID);
		}else{
			List<Map<String,Object>> BusinesGroup = FollowersGroupLogic.getFollowersGroup(sqlSession,BusinesShopID);
			if(BusinesGroup!=null && BusinesGroup.size()>0){
					AgentDefualtGroup.addAll(BusinesGroup);
			}
		}
		return AgentDefualtGroup;
    }
}
