package smny;
  
  
import org.apache.ibatis.io.Resources;  
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;  
import org.apache.ibatis.session.SqlSessionFactory;  
import org.apache.ibatis.session.SqlSessionFactoryBuilder;  
  
import java.util.Map;
import java.util.List;  
import java.util.HashMap;
import java.util.Iterator;
  
/** 
 * myBatis数据库连接测试 
 *  
 * @author db2admin 
 *  
 */  
public class MyBatisFactory {
		private static String FactoryXmlConfig = "RouterBatis/configuration.xml";
		private static SqlSessionFactory sessionFactory = null;
		public static synchronized SqlSessionFactory getSessionFactory() {
				if(sessionFactory != null){
						return sessionFactory;
				}
				try {
						sessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader(FactoryXmlConfig));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException("配置文件错误",e);
        }
        return sessionFactory;
    }
    /*
	    SqlSession openSession()
			SqlSession openSession(boolean autoCommit)
			SqlSession openSession(Connection connection)
			SqlSession openSession(TransactionIsolationLevel level)
			SqlSession openSession(ExecutorType execType,TransactionIsolationLevel level)
			SqlSession openSession(ExecutorType execType)
			SqlSession openSession(ExecutorType execType, boolean autoCommit)
			SqlSession openSession(ExecutorType execType, Connection connection)
			Configuration getConfiguration();
    */
    public static SqlSession openSession() {
    		if(sessionFactory == null){
    				return getSessionFactory().openSession();
    		}
    		return sessionFactory.openSession();
    }
    public static int insert(String statement) {
    		int ret;
    		SqlSession sqlSession = openSession();
        try {
        		ret = sqlSession.insert(statement);
        		sqlSession.commit();
        }finally{
        		sqlSession.close();
        }
    		return ret;
    }
    public static int insert(String statement,Object parameter) {
    		int ret;
    		SqlSession sqlSession = openSession();
        try {
        		ret = sqlSession.insert(statement,parameter);
        		sqlSession.commit();
        }finally{
        		sqlSession.close();
        }
    		return ret;
    }
    public static int update(String statement) {
    		int ret;
    		SqlSession sqlSession = openSession();
        try {
        		ret = sqlSession.update(statement);
        		sqlSession.commit();
        }finally{
        		sqlSession.close();
        }
    		return ret;
    }
    public static int update(String statement,Object parameter) {
    		int ret;
    		SqlSession sqlSession = openSession();
        try {
        		ret = sqlSession.update(statement,parameter);
        		sqlSession.commit();
        }finally{
        		sqlSession.close();
        }
    		return ret;
    }
    public static int delete(String statement) {
    		int ret;
    		SqlSession sqlSession = openSession();
        try {
        		ret = sqlSession.delete(statement);
        		sqlSession.commit();
        }finally{
        		sqlSession.close();
        }
    		return ret;
    }
    public static int delete(String statement,Object parameter) {
    		int ret;
    		SqlSession sqlSession = openSession();
        try {
        		ret = sqlSession.delete(statement,parameter);
        		sqlSession.commit();
        }finally{
        		sqlSession.close();
        }
    		return ret;
    }
    /*
			<T> T selectOne(String statement)
			<T> T selectOne(String statement, Object parameter)
    */
    
    public static Object selectOne(String statement) {
    		Object retObject;
    		SqlSession sqlSession = openSession();
        try {
        		retObject = sqlSession.selectOne(statement);
        }finally{
        		sqlSession.close();
        }
    		return retObject;
    }
    public static Object selectOne(String statement,Object parameter) {
    		Object retObject;
    		SqlSession sqlSession = openSession();
        try {
        		retObject = sqlSession.selectOne(statement,parameter);
        }finally{
        		sqlSession.close();
        }
    		return retObject;
    }
    /*
			<E> List<E> selectList(String statement)
			<E> List<E> selectList(String statement, Object parameter)
			<E> List<E> selectList (String statement, Object parameter, RowBounds rowBounds)
    */
    public static List selectList(String statement) {
    		List ListObject;
    		SqlSession sqlSession = openSession();
        try {
        		ListObject = sqlSession.selectList(statement);
        }finally{
        		sqlSession.close();
        }
    		return ListObject;
    }
    public static List selectList(String statement,Object parameter) {
    		List ListObject;
    		SqlSession sqlSession = openSession();
        try {
        		ListObject = sqlSession.selectList(statement,parameter);
        }finally{
        		sqlSession.close();
        }
    		return ListObject;
    }
    public static List selectList(String statement,Object parameter,RowBounds rowBounds) {
    		List ListObject;
    		SqlSession sqlSession = openSession();
        try {
        		ListObject = sqlSession.selectList(statement,parameter,rowBounds);
        }finally{
        		sqlSession.close();
        }
    		return ListObject;
    }
    /*
			*****************暂未实现×××××××××××××××××××××××××××
			<K,V> Map<K,V> selectMap(String statement, String mapKey)
			<K,V> Map<K,V> selectMap(String statement, Object parameter, String mapKey)
			<K,V> Map<K,V> selectMap(String statement, Object parameter, String mapKey, RowBounds rowbounds)
    */
    /*
			*****************暂未实现×××××××××××××××××××××××××××
			
    */
    public static <T> T getMapper(Class<T> type) {
    		T ListObject;
    		SqlSession sqlSession = openSession();
        try {
        		ListObject = sqlSession.getMapper(type);
        }finally{
        		sqlSession.close();
        }
    		return ListObject;
    }
    /*
			*****************SqlSession使用方法×××××××××××××××××××××××××××
				×默认情况下 MyBatis  不会自动提交事务,除非它侦测到有插入,更新或删除操作改变了 数据库。
					如果你已经做出了一些改变而没有使用这些方法,那么你可以传递 true 到 commit和  rollback 方法来保证它会被提交(注意,你不能在自动提交模式下强制 session,或者使用 了外部事务管理器时)。
					很多时候你不用调用  rollback(),因为如果你没有调用 commit 时MyBatis 会替你完成。然而,如果你需要更多对多提交和回滚都可能的 session 的细粒度控  制,你可以使用回滚选择来使它成为可能。 
			void commit()
			void commit(boolean force)
			void rollback()
			void rollback(boolean force)
			
				×SqlSession  实例有一个本地缓存在执行 update,commit,rollback 和 close 时被清理。要 明确地关闭它(获取打算做更多的工作) ,你可以调用  clearCache()。
			void clearCache()
				×必须保证的最重要的事情是你要关闭所打开的任何  session
			void close()
    */
    public static void main(String[] args) {
        SqlSession sqlSession = MyBatisFactory.openSession();
        try {
        		Object WhereValue = "C0-F8-DA-36-5D-06";
        		
        		Map<String,Object> Router = (Map<String,Object>)MyBatisFactory.selectOne("RouterBatis.WifiRouterMapper.SelectAuthorizeCode","abcabc");
        		
        		
        		
        		/*
        		List<Map<String,Object>> ListMap = sqlSession.selectList("RouterBatis.WifiRouterMapper.SelectAuthorizeCode","aabbcc");
        		if(ListMap!=null && ListMap.size()>0){
        				Display(ListMap.get(0));
        		}
        		
        		
        		Map<String,Object> AppEntrance = sqlSession.selectOne("RouterBatis.AppEntranceMapper.SelectOriginNumber",WhereValue);
        		Display(AppEntrance);
        		
        		System.out.println("************************************************************************");
        		AppEntrance.put("FansID","o9GQdt5XwWS7l0X80a3RI8GqPoXo");
        		Map<String,Object> Followers = sqlSession.selectOne("RouterBatis.FollowersMapper.SelectOpenID",AppEntrance);
        		Display(Followers);
        		
        		System.out.println("************************************************************************");
        		Map<String,Object> WifiRouter = sqlSession.selectOne("RouterBatis.WifiRouterMapper.SelectHardSeq","22201402286AE914D");
        		Display(WifiRouter);
        		
										Followers.put("TerminalMac","90-18-7C-4F-D5-88");
										Followers.put("WifiRouterID",WifiRouter.get("WifiRouterID"));
        		System.out.println("************************************************************************");
        		Map<String,Object> WifiTerminal = sqlSession.selectOne("RouterBatis.WifiTerminalMapper.SelectFollowersID",Followers);
        		Display(WifiTerminal);
        		*/
        		
        		
        		
        		
        		
        		
        		
        		
        		
        		
        		
        		
        		
        		
        		
        		
        		
        		
        		
        		
        		
        		
        		
        		
        		
        		
        		
        		//sqlSession.update("myBatis.AgentMapper.updateAgent",map);
        		
        		//map.put("AgentName",map.get("AgentName")+":Inser");
        		
        		//sqlSession.insert("myBatis.AgentMapper.insertAgent",map);
        		
        		
        		//sqlSession.commit();
        } finally {
        		sqlSession.close();
        }
        
  
    }
    public static void Display(Map<String,Object> map) {
    		Iterator<Map.Entry<String,Object>> ite = map.entrySet().iterator();
    		while(ite.hasNext()){
	    			Map.Entry<String,Object> EntEle = ite.next();
	    			System.out.print(EntEle.getKey());
	    			if(EntEle.getValue() != null)
	    				System.out.print("("+EntEle.getValue().getClass().getName()+") = ");
	    			else
		    			System.out.print(" = ");
	    			System.out.println(EntEle.getValue());
    		}
    }
}