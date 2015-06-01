package com.smny.wifiAlliance.test;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import smny.MyBatisFactory;


public class Test{
	public static void main(String[] args) {
		/*java.util.Calendar rightNow = java.util.Calendar.getInstance();
        java.text.SimpleDateFormat sim = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //得到当前时间，+3天
//        rightNow.add(java.util.Calendar.DAY_OF_MONTH, 3);   
        //如果是后退几天，就写 -天数 例如：
        rightNow.add(java.util.Calendar.DAY_OF_MONTH, -3);
        //进行时间转换
        String date1 = sim.format(rightNow.getTime()); 
        System.out.println(date1);*/
        
        
        
		Date date = new Date();
		Timestamp ts = new Timestamp(date.getTime());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		System.out.println(df.format(date));
		
		SqlSession sqlSession = MyBatisFactory.openSession();
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("WifiRouterID", "1_ayxsxlr:219949");
//			map.put("AuthTime", new Timestamp(date.getTime()));
			map.put("TotalCount", 200);
			map.put("WeiXinCount", 30);
			map.put("SmsCount", 50);
			map.put("TryCount", 100);
			map.put("DayCount", 30);
			sqlSession.insert("RouterBatis.RouterAuthRecordMapper.insertRouterAuthRecord", map);
//			Map<String,Object> mapsum = sqlSession.selectOne("RouterBatis.RouterAuthRecordMapper.SelectAuthRecordSum", map);
//			Map<String,Object> mapqueryOne = sqlSession.selectOne("RouterBatis.RouterAuthRecordMapper.SelectAuthRecordtest", map);
			sqlSession.commit();
			
//			System.out.println(mapqueryOne);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
	}
	
}

