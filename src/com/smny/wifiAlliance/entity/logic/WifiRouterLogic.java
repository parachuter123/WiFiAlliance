package com.smny.wifiAlliance.entity.logic;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import smny.MyBatisFactory;

public class WifiRouterLogic {
	/**
	 * 商户平台添加路由
	 * @param map			
	 * @throws Exception 
	 */
    public void Create(Map<String,Object> map) throws Exception{
    	SqlSession sqlSession = MyBatisFactory.openSession();
    	try {
    		sqlSession.insert("RouterBatis.WifiRouterMapper.insertRouterByBussess", map);
    		sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			sqlSession.close();
		}
    }
  //更新用户数据
    public void Update(Map<String,Object> map) throws SQLException{
    	SqlSession sqlSession = MyBatisFactory.openSession();
    	try {
    		sqlSession.update("RouterBatis.WifiRouterMapper.updateRouter", map);
    		sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
    }
    /**
     * 删除路由器
     * @param map 路由器map数据对象
     * @throws SQLException
     */
    public void Del(Map<String,Object> map) throws SQLException{
    	SqlSession sqlSession = MyBatisFactory.openSession();
    	try {
    		sqlSession.delete("RouterBatis.WifiRouterMapper.deleteRouterID", map);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
    }
}
