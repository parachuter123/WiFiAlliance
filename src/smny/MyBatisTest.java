package smny;
  
import java.io.IOException;  
  
import org.apache.ibatis.io.Resources;  
import org.apache.ibatis.session.SqlSession;  
import org.apache.ibatis.session.SqlSessionFactory;  
import org.apache.ibatis.session.SqlSessionFactoryBuilder;  
  
import java.util.Map;
import java.util.List;  
import java.util.HashMap;  
  
/** 
 * myBatis数据库连接测试 
 *  
 * @author db2admin 
 *  
 */  
public class MyBatisTest {  
    /** 
     * 获得MyBatis SqlSessionFactory   
     * SqlSessionFactory负责创建SqlSession，一旦创建成功，就可以用SqlSession实例来执行映射语句，commit，rollback，close等方法。 
     * @return 
     */  
    private static SqlSessionFactory getSessionFactory() {  
        SqlSessionFactory sessionFactory = null;  
        String resource = "configuration.xml";  
        try {  
            sessionFactory = new SqlSessionFactoryBuilder().build(Resources  
                    .getResourceAsReader(resource));  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        return sessionFactory;  
    }  
  
    public static void main(String[] args) {  
        SqlSession sqlSession = getSessionFactory().openSession();  
        
        
        try {
        		//List<Map> ListMap = sqlSession.selectList("myBatis.AgentMapper.SelectAgentAll");
        		
        		
        		
        		
        		HashMap map = sqlSession.selectOne("myBatis.AgentMapper.SelectAgentID",1);
        		
        		//System.out.println(map.get("AgentID").getClass().getName());
        		
        		//map.put("AgentName",map.get("AgentName")+":Updat");
        		
        		
        		//sqlSession.update("myBatis.AgentMapper.updateAgent",map);
        		
        		//map.put("AgentName",map.get("AgentName")+":Inser");
        		
        		//sqlSession.insert("myBatis.AgentMapper.insertAgent",map);
        		
        		
        		System.out.println(map.get("AgentID")+":"+map.get("AgentID").getClass().getName());
        		
        		System.out.println(map.get("WifiShare")+":"+map.get("WifiShare").getClass().getName());
        		
        		
        		
        		
        		//sqlSession.commit();
        } finally {
        		sqlSession.close();
        }
        
  
    }  
  
}