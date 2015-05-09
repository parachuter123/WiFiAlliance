package smny.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import smny.MyBatisFactory;

public class MemberTel implements Runnable {
	private static String path;
	private static Map<String,Object> blackwhite;
	private static String fileName;
	private static final String reg = "(https|http|ftp|rtsp|igmp|file|rtspt|rtspu)://((((25[0-5]|2[0-4]\\d|1?\\d?\\d)\\.){3}(25[0-5]|2[0-4]\\d|1?\\d?\\d))|(([0-9a-z_!~*'()-]*\\.?))([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\.([a-z]{2,6}))(:[0-9]{1,4})?([a-zA-Z0-9/?_=.]*\\.\\w{1,5})";
	public MemberTel(){}
	@SuppressWarnings("static-access")
	public MemberTel(String path,Map<String,Object> blackwhite,String fileName){
		this.path = path;
		this.blackwhite = blackwhite;
		this.fileName = fileName;
	}
	public static List<Map<String,Object>> readXls() throws IOException{
		InputStream is = new FileInputStream(MemberTel.path + "\\" + MemberTel.fileName);
		@SuppressWarnings("resource")
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Set<String> numset = new HashSet<String>();
		Map<String,Object> urlMap = new HashMap<String,Object>();
		for(int numSheet=0;numSheet<=hssfWorkbook.getNumberOfSheets()-1;numSheet++){
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			if (hssfSheet == null) {
                continue;
            }
			for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow != null) {
					HSSFCell num = hssfRow.getCell(0);
					HSSFCell url = hssfRow.getCell(1);
					if(!getValue(num).equals("")){
						numset.add(getValue(num));
						if(!getValue(url).equals("")){
							urlMap.put(getValue(num), getValue(url));
						}else{
							urlMap.put(getValue(num), "");
						}
					}
				}
			}
		}
		Iterator<String> setIterator = numset.iterator();
		while(setIterator.hasNext()){
			Map<String,Object>  map = new HashMap<String,Object>();
			String num = setIterator.next();
			map.put("Mac",  num);
			//判断URL优先级，观察EXCEL中URL是否为空，如果不为空，put，如果为空，put后台url
			if(!urlMap.get(num).equals("")){
				map.put("Remark", urlMap.get(num));
				map.put("ListType", true);
			}else{
				if(((String)MemberTel.blackwhite.get("Remark")).matches(reg)){
					map.put("Remark", MemberTel.blackwhite.get("Remark"));
				}else{
					map.put("Remark", "");
				}
				map.put("ListType", false);
			}
			map.put("BusinesShopID", MemberTel.blackwhite.get("BusinesShopID"));
			list.add(map);
		}
		return list;
	}
	
	@SuppressWarnings("static-access")
	private static String getValue(HSSFCell hssfCell) {
        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
        	DecimalFormat df = new DecimalFormat("00000000000"); 
        	if(df.format(hssfCell.getNumericCellValue()).matches("\\d{11}")){
        		return df.format(hssfCell.getNumericCellValue());
        	}else{
        		return "";
        	}
        } else if(hssfCell.getCellType() == hssfCell.CELL_TYPE_STRING){
            if(hssfCell.getStringCellValue().matches(reg)){
            	return String.valueOf(hssfCell.getStringCellValue());
            }else{
            	return "";
            }
        }else{
            return "";
        }
    }

	@Override
	public void run() {
		SqlSession sqlSession = MyBatisFactory.openSession();
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			List<Map<String,Object>> list = MemberTel.readXls();
			Iterator<Map<String,Object>> iterator = list.iterator();
			while(iterator.hasNext()){
				map = iterator.next();
				sqlSession.insert("RouterBatis.BlackWhiteListMapper.insertMemberTelList", map);
			}
			sqlSession.commit();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
	}
}
