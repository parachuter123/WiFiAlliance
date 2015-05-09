package com.smny.wifiAlliance.entity.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import smny.MyBatisFactory;
import smny.util.StringToolkit;

public class FollowersLogic {
    //主键全查询
    public static Map<String,Object> getData(String FollowersID) {
    	SqlSession sqlSession = MyBatisFactory.openSession();
    	Map<String,Object> Followers = new HashMap<String,Object>();
    	try {
    		Followers = sqlSession.selectOne("RouterBatis.FollowersMapper.SelectFollowersID", FollowersID);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
        return Followers;
    }

    //分页获取关注着列表***********************没做完********************************
    public static List<Object> getDatasByPageIndex(String AppEntranceID, int StatusID, String GroupID, String nickname, int PageIndex, int Pagesize) {
    	SqlSession sqlSession = MyBatisFactory.openSession();
    	List<Object> AppEntrance = null;
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("AppEntranceID", AppEntranceID);
			map.put("StatusID", StatusID);
			map.put("GroupID", GroupID);
			map.put("nickname", nickname);
			map.put("PageIndex",PageIndex);
			map.put("Pagesize",Pagesize);
			AppEntrance = sqlSession.selectList("RouterBatis.AppEntranceMapper.SelectBusinessShopID", map);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
    	StringBuilder querystr = new StringBuilder("FollowersID like '" + AppEntranceID + ";%' AND StatusID=" + StatusID + " AND GroupID ='" + GroupID + "'");
        if (!StringToolkit.isNullOrEmpty(nickname)) {
            querystr.append("AND nickname like '%" + nickname + "%'");
        }
        return AppEntrance;
    }

    //获取数目
    public static int getCount(String AppEntranceID, int StatusID, String GroupID, String nickname) {
    	SqlSession sqlSession = MyBatisFactory.openSession();
    	int count = 0;
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("AppEntranceID", AppEntranceID);
			map.put("StatusID", StatusID);
			map.put("GroupID", GroupID);
			map.put("nickname", nickname);
			count = Integer.parseInt(sqlSession.selectOne("RouterBatis.FollowersMapper.SelectCount", map).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
        return count;
    }
}
