package smny.net.tcp;
//Tcp����Ԫ�ṹ
public interface Element{
	//��������
	String getName();
	//��������
	DataType getDataType();
	//������������
	boolean isDirect();
	//�����������������Ա����Ա����
	String getLengthNmae();
	//��������
	int getLength();
	//����Ĭ��ֵ
	Number getDefaultValue();
	//�������ֽ���
	int getByteNumber();
}