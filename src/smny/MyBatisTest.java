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
 * myBatis���ݿ����Ӳ��� 
 *  
 * @author db2admin 
 *  
 */  
public class MyBatisTest {  
    /** 
     * ���MyBatis SqlSessionFactory   
     * SqlSessionFactory���𴴽�SqlSession��һ�������ɹ����Ϳ�����SqlSessionʵ����ִ��ӳ����䣬commit��rollback��close�ȷ����� 
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