package com.smny.wifiAlliance.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//�벻Ҫʹ��package���⽫�ᵼ�����Ĵ��벻��ͨ������
class Person{
	  public Boolean turnself = false;
	  public void getStone(){
		  
	  }
}
class Stone{
	  public int firstNumber;
	  public List<Integer> list = new ArrayList<Integer>();
	  
	  public Stone(int firstNumber){
	      this.firstNumber = firstNumber;
	      Random random = new Random();
	      while(firstNumber-->0){
	    	  list.add(random.nextInt());
	      }
	  }
	  public void spliStone(int i){
		  
	  }
	  
  
}
public class TestDemo{
	public static void main(String[] args) {
		Person A = new Person();
		Person B = new Person();
		
	}
}