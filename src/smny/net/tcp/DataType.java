package smny.net.tcp;
//Tcp��������
public enum DataType{
		ByteType(1,1,"Byte"),			//����������
		ShortType(2,2,"Short"),			//����������
		IntType(3,4,"Int"),			//����������
		LongType(4,8,"Long"),			//����������
		UnsignedByteType(5,1,"UnsignedByte"),			//����������
		UnsignedShortType(6,2,"UnsignedShort"),			//����������
		UnsignedIntType(7,4,"UnsignedInt"),			//����������
		UnionType(8,"Union"),			//����������
		PacketBodyType(9,"Body");			//����������
		
    private int ID;
    private int Bytes;
    private String Remarks;
    
    private DataType(int id,String remarks){
    	this.ID = id;
    	this.Remarks = remarks;
    }
    private DataType(int id,int bytes,String remarks){
    	this.ID = id;
    	this.Bytes = bytes;
    	this.Remarks = remarks;
    }
    
    
		public int getID(){
			return ID;
		}
		public int getByteNumber(){
			return Bytes;
		}
		public String getRemarks(){
			return Remarks;
		}
    
		public String toString(){
			return Remarks;
		}
    
    
    
		public static DataType getDataType(String DataTypeName){
				if("Byte".equalsIgnoreCase(DataTypeName)){
						return ByteType;
				}else if("Short".equalsIgnoreCase(DataTypeName)){
						return ShortType;
				}else if("Int".equalsIgnoreCase(DataTypeName)){
						return IntType;
				}else if("Long".equalsIgnoreCase(DataTypeName)){
						return LongType;
				}else if("UnsignedByte".equalsIgnoreCase(DataTypeName)){
						return UnsignedByteType;
				}else if("UnsignedShort".equalsIgnoreCase(DataTypeName)){
						return UnsignedShortType;
				}else if("UnsignedInt".equalsIgnoreCase(DataTypeName)){
						return UnsignedIntType;
				}else if("Union".equalsIgnoreCase(DataTypeName)){
						return null;
				}else if("PacketBody".equalsIgnoreCase(DataTypeName)){
						return null;
				}else{
						return null;
				}
		}
    
    
    
}