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
					WriteLogln(RouteName+":������Name��Ϊ��");
					ret = false;
			}else{
					EleImp.setName(Name.getValue());
			}
			
			Attribute dataType = itemEle.attribute("DataType");
			
			if(dataType==null || "".equals(dataType.getValue())){
					WriteLogln(RouteName+"��"+Name.getValue()+"��:������DataType��Ϊ��");
					ret = false;
			}else{
					DataType type = DataType.getDataType(dataType.getValue());
					if(type == null){
							WriteLogln(RouteName+"��"+Name.getValue()+"��:������DataType="+dataType+"������");
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
					WriteLogln(RouteName+":������Name��Ϊ��");
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
					WriteLogln(RouteName+"��"+Name.getValue()+"��:������BodyName��Ϊ��");
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
					WriteLogln(RouteName+":������Name��Ϊ��");
					ret = false;
			}else{
					EleImp.setName(Name.getValue());
			}
			
			Attribute dataType = itemEle.attribute("DataType");
			if(dataType==null || "".equals(dataType.getValue())){
					WriteLogln(RouteName+"��"+Name.getValue()+"��:������DataType��Ϊ��");
					ret = false;
			}else{
					DataType type = DataType.getDataType(dataType.getValue());
					if(type == null){
							WriteLogln(RouteName+"��"+Name.getValue()+"��:������DataType="+dataType+"������");
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
					WriteLogln(RouteName+"��"+Name.getValue()+"��:������Where��Ϊ��");
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
					WriteLogln(RouteName+":������Name��Ϊ��");
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
					WriteLogln(RouteName+"��"+Name.getValue()+"��:������Where��Ϊ��");
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
					WriteLogln(RouteName+"��"+Name.getValue()+"��:������BodyName��Ϊ��");
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
					WriteLogln(RouteName+":������KeyName��Ϊ��");
					ret = false;
			}else{
					UnionImp.setUnionKeyElementName(KeyName.getValue());
			}
			Iterator iters = itemEle.elementIterator(); // ��ȡ�ӽڵ�head�µ��ӽڵ�script
			
			UnionElementImp UnionEleImp;
			UnionBodyElementImp UnionBodyEleImp;
			
			int i=1;
			RouteName += "["+i+"]";
			while (iters.hasNext()) {
					Element item = (Element) iters.next();
					if("UnionEle".equals(item.getName())){
							UnionEleImp = genUnionElement(item,RouteName+"��UnionEle��");
							UnionImp.addElement(UnionEleImp);
					}else if("UnionBodyEle".equals(item.getName())){
							UnionBodyEleImp = genUnionBodyElement(item,RouteName+"��UnionBodyEle��");
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
					WriteLogln(RouteName+":������ID��Ϊ��");
					ret = false;
			}
			Attribute Name = itemEle.attribute("Name");
			if(Name==null || "".equals(Name.getValue())){
					WriteLogln(RouteName+"��"+ID.getValue()+"��:������Name��Ϊ��");
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
					WriteLogln(RouteName+"��"+BodyTypeImp.getName()+"��:������BodyName��Ϊ��");
			}
			
			
			Attribute CallbackName = itemEle.attribute("CallbackName");
			if(CallbackName!=null && !"".equals(CallbackName.getValue())){
					BodyTypeImp.setBodyCallbackName(CallbackName.getValue());
			}else if(CallbackName == null){
					WriteLogln(RouteName+"��"+BodyTypeImp.getName()+"��:������CallbackName��Ϊ��");
			}
			
			Attribute BodyRemark = itemEle.attribute("Remark");
			if(BodyRemark!=null && !"".equals(BodyRemark.getValue())){
					BodyTypeImp.setBodyRemark(BodyRemark.getValue());
			}else if(BodyRemark == null){
					WriteLogln(RouteName+"��"+BodyTypeImp.getName()+"��:������Remark��Ϊ��");
			}
			
			
			return ret?BodyTypeImp:null;
	}
	
	private void LegalCheck(SetElement SetEle,String RouteName){
			for(int i=0;i<SetEle.getSize();i++){
				smny.net.tcp.Element Ele = SetEle.getElement(i);
					if(Ele instanceof PacketUnionImp){
							PacketUnionImp UnionImp = (PacketUnionImp)Ele;
							if(!SetEle.isExist(UnionImp.getUnionKeyElementName())){
									WriteLogln(RouteName+"��PacketUnionImp��:������KeyName="+UnionImp.getUnionKeyElementName()+"��ָʾ����������������");
							}
							for(int j=0;j<UnionImp.getSize();j++){
									UnionElement UnionEle = (UnionElement)UnionImp.getElement(j);
									if(UnionEle instanceof UnionElementImp){
											if(!UnionEle.isDirect() && !SetEle.isExist(UnionEle.getLengthNmae())){
													WriteLogln(RouteName+"��UnionElementImp��:������LengthNmae="+UnionEle.getLengthNmae()+"��ָʾ�ĳ��ȱ���������");
											}
									}else if(UnionEle instanceof UnionBodyElementImp){
											PacketBodyImp UnionBodyImpTest = (PacketBodyImp)StructureImp.getPacketBody(((UnionBodyElementImp)UnionEle).getPacketBodyName());
											if(UnionBodyImpTest == null){
													WriteLogln(RouteName+"��UnionBodyElementImp��:������PacketBodyName="+((UnionBodyElementImp)UnionEle).getPacketBodyName()+"��ָʾ�İ��岻����");
											}else{
													((UnionBodyElementImp)UnionEle).setPacketBody(UnionBodyImpTest);
											}
									}
							}
					}else if(Ele instanceof BodyElementImp){
							PacketBodyImp BodyImpTest = (PacketBodyImp)StructureImp.getPacketBody(((BodyElementImp)Ele).getPacketBodyName());
							if(BodyImpTest == null){
									WriteLogln(RouteName+"��BodyElementImp��:������PacketBodyName="+((BodyElementImp)Ele).getPacketBodyName()+"��ָʾ�İ��岻����");
							}else{
									((BodyElementImp)Ele).setPacketBody(BodyImpTest);
							}
					}else if(Ele instanceof ElementImp){
							if(!Ele.isDirect() && !SetEle.isExist(Ele.getLengthNmae())){
									WriteLogln(RouteName+"��ElementImp��:������LengthNmae="+Ele.getLengthNmae()+"��ָʾ�ĳ��ȱ���������");
							}
					}
			}
	}
	
	
	
	
	
	public boolean ToParse(org.dom4j.Document document){
			
      org.dom4j.Element element=document.getRootElement();
      
      PacketHeadImp HeadImp = new PacketHeadImp();
			/*
				Body.[ext]�������ơ�.�����͡�[Elext]
			*/
			Iterator iter = element.elementIterator("Body"); // ��ȡ���ڵ��µ��ӽڵ�head
			int ext = 1;
			while(iter.hasNext()) {
					Element recordEle = (Element) iter.next();
					PacketBodyImp BodyImp;
					Attribute BodyName = recordEle.attribute("Name");
					if(BodyName==null || "".equals(BodyName.getValue())){
							WriteLogln("Body.["+ext+"]�����ơ�Name��Ϊ�գ�");
							BodyImp = new PacketBodyImp("");
					}else{
							BodyImp = new PacketBodyImp(BodyName.getValue());
							StructureImp.put(BodyName.getValue(),BodyImp);
					}
					int Elext = 1;
					Iterator iters = recordEle.elementIterator(); // ��ȡ�ӽڵ�head�µ��ӽڵ�script
					while (iters.hasNext()) {
							Element itemEle = (Element) iters.next();
							String RouteName = "Body.["+ext+"]��"+BodyName.getValue()+"��.["+Elext+"]";
							if("Ele".equals(itemEle.getName())){
									ElementImp EleImp = genElement(itemEle,RouteName+"��Ele��");
									if(EleImp != null){
											BodyImp.addElement(EleImp);
									}
							}else if("Union".equals(itemEle.getName())){
									PacketUnionImp UnionImp = genUnion(itemEle,RouteName+"��Union��");
									if(UnionImp != null){
											BodyImp.addElement(UnionImp);
									}
							}else if("BodyEle".equals(itemEle.getName())){
									BodyElementImp BodyEle = getBodyElement(itemEle,RouteName+"��BodyEle��");
									if(BodyEle != null){
											BodyImp.addElement(BodyEle);
									}
							}else{
									WriteLogln(RouteName+"��"+itemEle.getName()+"����ǩ�޷��Ƿ����޷�ʶ��");
							}
							Elext++;
					}
					ext++;
			}
			iter = StructureImp.BodySet().iterator(); // ��ȡ���ڵ��µ��ӽڵ�head
			while(iter.hasNext()){
					PacketBodyImp BodyImp = (PacketBodyImp)iter.next();
					LegalCheck(BodyImp,"Body.��"+BodyImp.getBodyName()+"��");
			}
			/*
				Head.[ext]�����͡������ơ�
			*/
			iter = element.elementIterator("Head"); // ��ȡ���ڵ��µ��ӽڵ�head
			if(iter.hasNext()) {
					Element recordEle = (Element) iter.next();
					Attribute LenElementName = recordEle.attribute("LenName");
					if(LenElementName==null || "".equals(LenElementName.getValue())){
							WriteLogln("Head.["+ext+"]�����ȡ�LenName��Ϊ�գ�");
					}
					
					Attribute TypeElementName = recordEle.attribute("TypeName");
					if(TypeElementName==null || "".equals(TypeElementName.getValue())){
							WriteLogln("Head.["+ext+"]���������ơ�TypeName��Ϊ�գ�");
					}
					
					int Elext = 1;
					
					Iterator iters = recordEle.elementIterator(); // ��ȡ�ӽڵ�head�µ��ӽڵ�script
					while (iters.hasNext()) {
							Element itemEle = (Element) iters.next();
							
							String RouteName = "Head.["+Elext+"]";
							
							if("Ele".equals(itemEle.getName())){
									ElementImp EleImp = genElement(itemEle,RouteName+"��Ele��");
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
									PacketUnionImp UnionImp = genUnion(itemEle,RouteName+"��Union��");
									if(UnionImp == null){
											Elext++;
											continue;
									}
									HeadImp.addElement(UnionImp);
							}else if("BodyEle".equals(itemEle.getName())){
									BodyElementImp BodyEle = getBodyElement(itemEle,RouteName+"��BodyEle��");
									if(BodyEle == null){
											Elext++;
											continue;
									}
									HeadImp.addElement(BodyEle);
							}else{
									WriteLogln(RouteName+"��"+itemEle.getName()+"����ǩ�޷��Ƿ����޷�ʶ��");
							}
							Elext++;
					}
					if(HeadImp.getPacketLenElement()==null && LenElementName!=null){
							WriteLogln("Head.��LenName="+LenElementName.getValue()+"��ָʾ�İ����Ȳ�����");
					}
					if(HeadImp.getBodyTypeElement()==null && TypeElementName!=null){
							WriteLogln("Head.��TypeName="+TypeElementName.getValue()+"��ָʾ�İ����Ͳ�����");
					}
			}else{
					WriteLogln("Head:�����ڰ�ͷ");
			}
			LegalCheck(HeadImp,"Head.");
			/*
				
				Neck.[ext]�����ơ�
				
			*/
			iter = element.elementIterator("Neck"); // ��ȡ���ڵ��µ��ӽڵ�head
			if(iter.hasNext()) {
					Element recordEle = (Element) iter.next();
					Iterator iters = recordEle.elementIterator(); // ��ȡ�ӽڵ�head�µ��ӽڵ�script
					int Elext = 1;
					while (iters.hasNext()) {
							Element itemEle = (Element) iters.next();
							String RouteName = "Neck.["+Elext+"]";
							if("BodyType".equals(itemEle.getName())){
									PacketBodyTypeImp BodyType = genBodyType(itemEle,RouteName+"��BodyType��");
									if(BodyType == null){
											Elext++;
											continue;
									}
									HeadImp.putBodyType(BodyType);
									
									if(BodyType.getSendBodyName() != null){
											PacketBodyImp BodyImpTest = (PacketBodyImp)StructureImp.getPacketBody(BodyType.getSendBodyName());
											if(BodyImpTest == null){
													WriteLogln("Neck.��BodyType��(ID="+BodyType.getBodyID()+")��"+BodyType.getName()+"��:������BodyName="+BodyType.getSendBodyName()+"��ָʾ�İ��岻����");
											}
									}else if(BodyType.getReceiveBodyName() != null){
											PacketBodyImp BodyImpTest = (PacketBodyImp)StructureImp.getPacketBody(BodyType.getReceiveBodyName());
											if(BodyImpTest == null){
													WriteLogln("Neck.��BodyType��(ID="+BodyType.getBodyID()+")��"+BodyType.getName()+"��:������ReceiveBodyName="+BodyType.getSendBodyName()+"��ָʾ�İ��岻����");
											}
									}else{
											//WriteLogln("Head.��BodyType��(ID="+BodyType.getBodyID()+")��"+BodyType.getName()+"��:������BodyName���͡�ReceiveBodyName��ͬʱΪ��");
									}
							}else{
									WriteLogln(RouteName+"��"+itemEle.getName()+"����ǩ�޷��Ƿ����޷�ʶ��");
							}
							Elext++;
					}
			}else{
					WriteLogln("Neck:�����ڰ�����");
			}
      
      if(LogRecord.length()>1){
      		throw new RuntimeException(LogRecord.toString());
      }
      StructureImp.setPacketHead(HeadImp);
      return true;
	}
	
	
	
	public static void main(String args[]){
		PacketStructureImp StructureImp = new PacketStructureImp();
			//�����ļ�����  
	    java.io.File file=new java.io.File("TcpProtocol.xml");  
	    if(file.exists()){  
		    	
	      try{  
		        //����һ����ȡXML�ļ��Ķ���  
		        org.dom4j.io.SAXReader reader=new org.dom4j.io.SAXReader();  
		        //����һ���ĵ�����  
		        org.dom4j.Document document=reader.read(file);  
		        
		        System.out.println(new ParsingXml(StructureImp).ToParse(document));
		        
		        
	      }catch(Exception e){  
	          e.printStackTrace();  
	      }  
	    }else{  
	        System.out.println("XML�ļ�������!");  
	    }  
		
	}
}

