package smny.net.tcp;
import java.util.ArrayList;
public class UnionBodyElementImp extends BodyElementImp implements UnionElement{
	private ArrayList<Object> WhereList = new ArrayList<Object>();
	public UnionBodyElementImp(){
		
	}
	/*
	*ʵ�ֽӿ�:UnionElement
	*/
	public boolean isHit(Object Where){
		for(Object WhereEle:WhereList){
				if(Where instanceof Number){
						if(WhereEle instanceof Number && ((Number)WhereEle).intValue() == ((Number)Where).intValue()){
								return true;
						}
				}else if(!(WhereEle instanceof Number)){
						if(Where.equals(WhereEle)){
								return true;
						}
				}
		}
		return false;
		//return WhereList.contains(Where);
	}
	/*
	*���෽��
	*/
	public void add(String Where){
		WhereList.add(Where);
	}
	public void add(Number Where){
		WhereList.add(Where);
	}
	
}