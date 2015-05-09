package smny.net.tcp;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Attribute;
import java.util.Iterator;
public class ParsingXml{
	PacketStructureImp StructureImp;
	public ParsingXml(PacketStructureImp StructureImp){
			this.StructureImp = StructureImp;
	}
	StringBuilder LogRecord = new StringBuilder();
	private void WriteLog(String Record){
		LogRecord.append(Record);
	}
	private void WriteLogln(String Record){
		LogRecord.append(Record);
		LogRecord.append("\r\n");
	}
	private void print(){
		System.out.print(LogRecord);
	}
	
	
	
	
	private ElementImp genElement(Element itemEle,String RouteName){
			boolean ret = true;
			ElementImp EleImp = new ElementImp();
			
			Attribute Name = itemEle.attribute("Name");
			if(Name==null || "".equals(Name.getValue())){
					WriteLogln(RouteName+":参数“Name”为空");
					ret = false;
			}else{
					EleImp.setName(Name.getValue());
			}
			
			Attribute dataType = itemEle.attribute("DataType");
			
			if(dataType==null || "".equals(dataType.getValue())){
					WriteLogln(RouteName+"【"+Name.getValue()+"】:参数“DataType”为空");
					ret = false;
			}else{
					DataType type = DataType.getDataType(dataType.getValue());
					if(type == null){
							WriteLogln(RouteName+"【"+Name.getValue()+"】:参数“DataType="+dataType+"”错误");
							ret = false;
					}else{
							EleImp.setDataType(type);
					}
			}
			
			Attribute Length = itemEle.attribute("Length");
			Attribute LengthName = itemEle.attribute("LengthName");
			
			if(Length==null || "".equals(Length.getValue())){
					if(LengthName==null || "".equals(LengthName.getValue())){
							EleImp.setLength(1);
					}else{
							EleImp.setLengthNmae(LengthName.getValue());
					}
			}else{
					try{
							EleImp.setLength(Integer.parseInt(Length.getValue()));
					}catch(Exception e){
							EleImp.setLengthNmae(Length.getValue());
					}
			}
			try{
					EleImp.setDefaultValue(Integer.valueOf(itemEle.attribute("DefaultValue").getValue()));
			}catch(Exception e){
					EleImp.setDefaultValue(null);
			}
			return ret?EleImp:null;
	}
	private BodyElementImp getBodyElement(Element itemEle,String RouteName){
			boolean ret = true;
			BodyElementImp EleImp = new BodyElementImp();
			Attribute Name = itemEle.attribute("Name");
			if(Name==null || "".equals(Name.getValue())){
					WriteLogln(RouteName+":参数“Name”为空");
					ret = false;
			}else{
					EleImp.setName(Name.getValue());
			}
			Attribute Length = itemEle.attribute("Length");
			Attribute LengthName = itemEle.attribute("LengthName");
			
			if(Length==null || "".equals(Length.getValue())){
					if(LengthName==null || "".equals(LengthName.getValue())){
							EleImp.setLength(1);
					}else{
							EleImp.setLengthNmae(LengthName.getValue());
					}
			}else{
					try{
							EleImp.setLength(Integer.parseInt(Length.getValue()));
					}catch(Exception e){
							EleImp.setLengthNmae(Length.getValue());
					}
			}
			Attribute BodyName = itemEle.attribute("BodyName");
			if(BodyName==null || "".equals(BodyName.getValue())){
					WriteLogln(RouteName+"【"+Name.getValue()+"】:参数“BodyName”为空");
					ret = false;
			}else{
					EleImp.setPacketBodyName(BodyName.getValue());
			}
			return ret?EleImp:null;
	}
	private UnionElementImp genUnionElement(Element itemEle,String RouteName){
			boolean ret = true;
			UnionElementImp EleImp = new UnionElementImp();
			Attribute Name = itemEle.attribute("Name");
			if(Name==null || "".equals(Name.getValue())){
					WriteLogln(RouteName+":参数“Name”为空");
					ret = false;
			}else{
					EleImp.setName(Name.getValue());
			}
			
			Attribute dataType = itemEle.attribute("DataType");
			if(dataType==null || "".equals(dataType.getValue())){
					WriteLogln(RouteName+"【"+Name.getValue()+"】:参数“DataType”为空");
					ret = false;
			}else{
					DataType type = DataType.getDataType(dataType.getValue());
					if(type == null){
							WriteLogln(RouteName+"【"+Name.getValue()+"】:参数“DataType="+dataType+"”错误");
							ret = false;
					}else{
							EleImp.setDataType(type);
					}
			}
			
			Attribute Length = itemEle.attribute("Length");
			Attribute LengthName = itemEle.attribute("LengthName");
			
			if(Length==null || "".equals(Length.getValue())){
					if(LengthName==null || "".equals(LengthName.getValue())){
							EleImp.setLength(1);
					}else{
							EleImp.setLengthNmae(LengthName.getValue());
					}
			}else{
					try{
							EleImp.setLength(Integer.parseInt(Length.getValue()));
					}catch(Exception e){
							EleImp.setLengthNmae(Length.getValue());
					}
			}
			try{
					EleImp.setDefaultValue(Integer.valueOf(itemEle.attribute("DefaultValue").getValue()));
			}catch(Exception e){
					EleImp.setDefaultValue(null);
			}
			Attribute Where = itemEle.attribute("Where");
			if(Where==null || "".equals(Where.getValue())){
					WriteLogln(RouteName+"【"+Name.getValue()+"】:参数“Where”为空");
					ret = false;
			}else{
					for(String Wher:Where.getValue().split(",")){
							try{
									EleImp.add(Integer.valueOf(Wher));
							}catch(Exception e){
									EleImp.add(Wher);
							}
					}
			}
			return ret?EleImp:null;
	}
	private UnionBodyElementImp genUnionBodyElement(Element itemEle,String RouteName){
			boolean ret = true;
			UnionBodyElementImp EleImp = new UnionBodyElementImp();
			Attribute Name = itemEle.attribute("Name");
			if(Name==null || "".equals(Name.getValue())){
					WriteLogln(RouteName+":参数“Name”为空");
					ret = false;
			}else{
					EleImp.setName(Name.getValue());
			}
			Attribute Length = itemEle.attribute("Length");
			Attribute LengthName = itemEle.attribute("LengthName");
			
			if(Length==null || "".equals(Length.getValue())){
					if(LengthName==null || "".equals(LengthName.getValue())){
							EleImp.setLength(1);
					}else{
							EleImp.setLengthNmae(LengthName.getValue());
					}
			}else{
					try{
							EleImp.setLength(Integer.parseInt(Length.getValue()));
					}catch(Exception e){
							EleImp.setLengthNmae(Length.getValue());
					}
			}
			Attribute Where = itemEle.attribute("Where");
			if(Where==null || "".equals(Where.getValue())){
					WriteLogln(RouteName+"【"+Name.getValue()+"】:参数“Where”为空");
					ret = false;
			}else{
					for(String Wher:Where.getValue().split(",")){
							try{
									EleImp.add(Integer.valueOf(Wher));
							}catch(Exception e){
									EleImp.add(Wher);
							}
					}
			}
			Attribute BodyName = itemEle.attribute("BodyName");
			if(BodyName==null || "".equals(BodyName.getValue())){
					WriteLogln(RouteName+"【"+Name.getValue()+"】:参数“BodyName”为空");
					ret = false;
			}else{
					EleImp.setPacketBodyName(BodyName.getValue());
			}
			return ret?EleImp:null;
	}
	private PacketUnionImp genUnion(Element itemEle,String RouteName){
			boolean ret = true;
			PacketUnionImp UnionImp = new PacketUnionImp();
			
			Attribute KeyName = itemEle.attribute("KeyName");
			if(KeyName==null || "".equals(KeyName.getValue())){
					WriteLogln(RouteName+":参数“KeyName”为空");
					ret = false;
			}else{
					UnionImp.setUnionKeyElementName(KeyName.getValue());
			}
			Iterator iters = itemEle.elementIterator(); // 获取子节点head下的子节点script
			
			UnionElementImp UnionEleImp;
			UnionBodyElementImp UnionBodyEleImp;
			
			int i=1;
			RouteName += "["+i+"]";
			while (iters.hasNext()) {
					Element item = (Element) iters.next();
					if("UnionEle".equals(item.getName())){
							UnionEleImp = genUnionElement(item,RouteName+"〖UnionEle〗");
							UnionImp.addElement(UnionEleImp);
					}else if("UnionBodyEle".equals(item.getName())){
							UnionBodyEleImp = genUnionBodyElement(item,RouteName+"〖UnionBodyEle〗");
							UnionImp.addElement(UnionBodyEleImp);
					}
			}
			
			return ret?UnionImp:null;
	}
	private PacketBodyTypeImp genBodyType(Element itemEle,String RouteName){
			boolean ret = true;
			PacketBodyTypeImp BodyTypeImp = new PacketBodyTypeImp();
			Attribute ID = itemEle.attribute("ID");
			try{
					BodyTypeImp.setBodyID(Integer.parseInt(ID.getValue()));
			}catch(Exception e){
					WriteLogln(RouteName+":参数“ID”为空");
					ret = false;
			}
			Attribute Name = itemEle.attribute("Name");
			if(Name==null || "".equals(Name.getValue())){
					WriteLogln(RouteName+"【"+ID.getValue()+"】:参数“Name”为空");
					ret = false;
			}else{
					BodyTypeImp.setName(Name.getValue());
			}
			
			
			Attribute BodyName = itemEle.attribute("BodyName");
			Attribute ReceiveBodyName = itemEle.attribute("ReceiveBodyName");
			
			
			if(BodyName!=null && !"".equals(BodyName.getValue())){
					BodyTypeImp.setSendBodyName(BodyName.getValue());
			}
			
			if(ReceiveBodyName!=null && !"".equals(ReceiveBodyName.getValue())){
					BodyTypeImp.setReceiveBodyName(ReceiveBodyName.getValue());
			}else if(BodyTypeImp.getSendBodyName() != null){
					BodyTypeImp.setReceiveBodyName(BodyTypeImp.getSendBodyName());
			}
			
			if(BodyName==null && ReceiveBodyName == null){
					WriteLogln(RouteName+"【"+BodyTypeImp.getName()+"】:参数“BodyName”为空");
			}
			
			
			Attribute CallbackName = itemEle.attribute("CallbackName");
			if(CallbackName!=null && !"".equals(CallbackName.getValue())){
					BodyTypeImp.setBodyCallbackName(CallbackName.getValue());
			}else if(CallbackName == null){
					WriteLogln(RouteName+"【"+BodyTypeImp.getName()+"】:参数“CallbackName”为空");
			}
			
			Attribute BodyRemark = itemEle.attribute("Remark");
			if(BodyRemark!=null && !"".equals(BodyRemark.getValue())){
					BodyTypeImp.setBodyRemark(BodyRemark.getValue());
			}else if(BodyRemark == null){
					WriteLogln(RouteName+"【"+BodyTypeImp.getName()+"】:参数“Remark”为空");
			}
			
			
			return ret?BodyTypeImp:null;
	}
	
	private void LegalCheck(SetElement SetEle,String RouteName){
			for(int i=0;i<SetEle.getSize();i++){
				smny.net.tcp.Element Ele = SetEle.getElement(i);
					if(Ele instanceof PacketUnionImp){
							PacketUnionImp UnionImp = (PacketUnionImp)Ele;
							if(!SetEle.isExist(UnionImp.getUnionKeyElementName())){
									WriteLogln(RouteName+"【PacketUnionImp】:参数“KeyName="+UnionImp.getUnionKeyElementName()+"”指示的条件变量不存在");
							}
							for(int j=0;j<UnionImp.getSize();j++){
									UnionElement UnionEle = (UnionElement)UnionImp.getElement(j);
									if(UnionEle instanceof UnionElementImp){
											if(!UnionEle.isDirect() && !SetEle.isExist(UnionEle.getLengthNmae())){
													WriteLogln(RouteName+"【UnionElementImp】:参数“LengthNmae="+UnionEle.getLengthNmae()+"”指示的长度变量不存在");
											}
									}else if(UnionEle instanceof UnionBodyElementImp){
											PacketBodyImp UnionBodyImpTest = (PacketBodyImp)StructureImp.getPacketBody(((UnionBodyElementImp)UnionEle).getPacketBodyName());
											if(UnionBodyImpTest == null){
													WriteLogln(RouteName+"【UnionBodyElementImp】:参数“PacketBodyName="+((UnionBodyElementImp)UnionEle).getPacketBodyName()+"”指示的包体不存在");
											}else{
													((UnionBodyElementImp)UnionEle).setPacketBody(UnionBodyImpTest);
											}
									}
							}
					}else if(Ele instanceof BodyElementImp){
							PacketBodyImp BodyImpTest = (PacketBodyImp)StructureImp.getPacketBody(((BodyElementImp)Ele).getPacketBodyName());
							if(BodyImpTest == null){
									WriteLogln(RouteName+"【BodyElementImp】:参数“PacketBodyName="+((BodyElementImp)Ele).getPacketBodyName()+"”指示的包体不存在");
							}else{
									((BodyElementImp)Ele).setPacketBody(BodyImpTest);
							}
					}else if(Ele instanceof ElementImp){
							if(!Ele.isDirect() && !SetEle.isExist(Ele.getLengthNmae())){
									WriteLogln(RouteName+"【ElementImp】:参数“LengthNmae="+Ele.getLengthNmae()+"”指示的长度变量不存在");
							}
					}
			}
	}
	
	
	
	
	
	public boolean ToParse(org.dom4j.Document document){
			
      org.dom4j.Element element=document.getRootElement();
      
      PacketHeadImp HeadImp = new PacketHeadImp();
			/*
				Body.[ext]【包名称】.〖类型〗[Elext]
			*/
			Iterator iter = element.elementIterator("Body"); // 获取根节点下的子节点head
			int ext = 1;
			while(iter.hasNext()) {
					Element recordEle = (Element) iter.next();
					PacketBodyImp BodyImp;
					Attribute BodyName = recordEle.attribute("Name");
					if(BodyName==null || "".equals(BodyName.getValue())){
							WriteLogln("Body.["+ext+"]包名称“Name”为空！");
							BodyImp = new PacketBodyImp("");
					}else{
							BodyImp = new PacketBodyImp(BodyName.getValue());
							StructureImp.put(BodyName.getValue(),BodyImp);
					}
					int Elext = 1;
					Iterator iters = recordEle.elementIterator(); // 获取子节点head下的子节点script
					while (iters.hasNext()) {
							Element itemEle = (Element) iters.next();
							String RouteName = "Body.["+ext+"]【"+BodyName.getValue()+"】.["+Elext+"]";
							if("Ele".equals(itemEle.getName())){
									ElementImp EleImp = genElement(itemEle,RouteName+"〖Ele〗");
									if(EleImp != null){
											BodyImp.addElement(EleImp);
									}
							}else if("Union".equals(itemEle.getName())){
									PacketUnionImp UnionImp = genUnion(itemEle,RouteName+"〖Union〗");
									if(UnionImp != null){
											BodyImp.addElement(UnionImp);
									}
							}else if("BodyEle".equals(itemEle.getName())){
									BodyElementImp BodyEle = getBodyElement(itemEle,RouteName+"〖BodyEle〗");
									if(BodyEle != null){
											BodyImp.addElement(BodyEle);
									}
							}else{
									WriteLogln(RouteName+"〖"+itemEle.getName()+"〗标签无法非法或无法识别");
							}
							Elext++;
					}
					ext++;
			}
			iter = StructureImp.BodySet().iterator(); // 获取根节点下的子节点head
			while(iter.hasNext()){
					PacketBodyImp BodyImp = (PacketBodyImp)iter.next();
					LegalCheck(BodyImp,"Body.【"+BodyImp.getBodyName()+"】");
			}
			/*
				Head.[ext]〖类型〗【名称】
			*/
			iter = element.elementIterator("Head"); // 获取根节点下的子节点head
			if(iter.hasNext()) {
					Element recordEle = (Element) iter.next();
					Attribute LenElementName = recordEle.attribute("LenName");
					if(LenElementName==null || "".equals(LenElementName.getValue())){
							WriteLogln("Head.["+ext+"]包长度“LenName”为空！");
					}
					
					Attribute TypeElementName = recordEle.attribute("TypeName");
					if(TypeElementName==null || "".equals(TypeElementName.getValue())){
							WriteLogln("Head.["+ext+"]包类型名称“TypeName”为空！");
					}
					
					int Elext = 1;
					
					Iterator iters = recordEle.elementIterator(); // 获取子节点head下的子节点script
					while (iters.hasNext()) {
							Element itemEle = (Element) iters.next();
							
							String RouteName = "Head.["+Elext+"]";
							
							if("Ele".equals(itemEle.getName())){
									ElementImp EleImp = genElement(itemEle,RouteName+"〖Ele〗");
									if(EleImp == null){
											Elext++;
											continue;
									}
									HeadImp.addElement(EleImp);
									if(LenElementName!=null && LenElementName.getValue().equals(EleImp.getName())){
										HeadImp.setPacketLenElement(EleImp);
									}
									if(TypeElementName!=null && TypeElementName.getValue().equals(EleImp.getName())){
										HeadImp.setBodyTypeElement(EleImp);
									}
							}else if("Union".equals(itemEle.getName())){
									PacketUnionImp UnionImp = genUnion(itemEle,RouteName+"〖Union〗");
									if(UnionImp == null){
											Elext++;
											continue;
									}
									HeadImp.addElement(UnionImp);
							}else if("BodyEle".equals(itemEle.getName())){
									BodyElementImp BodyEle = getBodyElement(itemEle,RouteName+"〖BodyEle〗");
									if(BodyEle == null){
											Elext++;
											continue;
									}
									HeadImp.addElement(BodyEle);
							}else{
									WriteLogln(RouteName+"〖"+itemEle.getName()+"〗标签无法非法或无法识别");
							}
							Elext++;
					}
					if(HeadImp.getPacketLenElement()==null && LenElementName!=null){
							WriteLogln("Head.〖LenName="+LenElementName.getValue()+"〗指示的包长度不存在");
					}
					if(HeadImp.getBodyTypeElement()==null && TypeElementName!=null){
							WriteLogln("Head.〖TypeName="+TypeElementName.getValue()+"〗指示的包类型不存在");
					}
			}else{
					WriteLogln("Head:不存在包头");
			}
			LegalCheck(HeadImp,"Head.");
			/*
				
				Neck.[ext]【名称】
				
			*/
			iter = element.elementIterator("Neck"); // 获取根节点下的子节点head
			if(iter.hasNext()) {
					Element recordEle = (Element) iter.next();
					Iterator iters = recordEle.elementIterator(); // 获取子节点head下的子节点script
					int Elext = 1;
					while (iters.hasNext()) {
							Element itemEle = (Element) iters.next();
							String RouteName = "Neck.["+Elext+"]";
							if("BodyType".equals(itemEle.getName())){
									PacketBodyTypeImp BodyType = genBodyType(itemEle,RouteName+"〖BodyType〗");
									if(BodyType == null){
											Elext++;
											continue;
									}
									HeadImp.putBodyType(BodyType);
									
									if(BodyType.getSendBodyName() != null){
											PacketBodyImp BodyImpTest = (PacketBodyImp)StructureImp.getPacketBody(BodyType.getSendBodyName());
											if(BodyImpTest == null){
													WriteLogln("Neck.〖BodyType〗(ID="+BodyType.getBodyID()+")【"+BodyType.getName()+"】:参数“BodyName="+BodyType.getSendBodyName()+"”指示的包体不存在");
											}
									}else if(BodyType.getReceiveBodyName() != null){
											PacketBodyImp BodyImpTest = (PacketBodyImp)StructureImp.getPacketBody(BodyType.getReceiveBodyName());
											if(BodyImpTest == null){
													WriteLogln("Neck.〖BodyType〗(ID="+BodyType.getBodyID()+")【"+BodyType.getName()+"】:参数“ReceiveBodyName="+BodyType.getSendBodyName()+"”指示的包体不存在");
											}
									}else{
											//WriteLogln("Head.〖BodyType〗(ID="+BodyType.getBodyID()+")【"+BodyType.getName()+"】:参数“BodyName”和“ReceiveBodyName”同时为空");
									}
							}else{
									WriteLogln(RouteName+"〖"+itemEle.getName()+"〗标签无法非法或无法识别");
							}
							Elext++;
					}
			}else{
					WriteLogln("Neck:不存在包类型");
			}
      
      if(LogRecord.length()>1){
      		throw new RuntimeException(LogRecord.toString());
      }
      StructureImp.setPacketHead(HeadImp);
      return true;
	}
	
	
	
	public static void main(String args[]){
		PacketStructureImp StructureImp = new PacketStructureImp();
			//创建文件对象  
	    java.io.File file=new java.io.File("TcpProtocol.xml");  
	    if(file.exists()){  
		    	
	      try{  
		        //创建一个读取XML文件的对象  
		        org.dom4j.io.SAXReader reader=new org.dom4j.io.SAXReader();  
		        //创建一个文档对象  
		        org.dom4j.Document document=reader.read(file);  
		        
		        System.out.println(new ParsingXml(StructureImp).ToParse(document));
		        
		        
	      }catch(Exception e){  
	          e.printStackTrace();  
	      }  
	    }else{  
	        System.out.println("XML文件不存在!");  
	    }  
		
	}
}

