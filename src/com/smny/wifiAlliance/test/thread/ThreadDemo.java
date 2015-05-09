package com.smny.wifiAlliance.test.thread;

public class ThreadDemo {

	public static void main(String[] args) {
		ObjDemo thread =new ObjDemo();
		Thread th1 = new ThreadObj2(thread);
		Thread th2 = new ThreadObj3(thread);
		th1.start();
		th2.start();
	}

}
class ThreadObj2 extends Thread{
	private ObjDemo obj;
	public ThreadObj2(ObjDemo obj){
		this.obj = obj;
	}
	@Override
	public void run() {
		obj.test2();
	}
}
class ThreadObj3 extends Thread{
	private ObjDemo obj;
	public ThreadObj3(ObjDemo obj){
		this.obj = obj;
	}
	@Override
	public void run() {
		obj.test3();
	}
}
class ObjDemo{
	Object lock = new Object();
	public void test2(){
		synchronized (lock) {
			for(int i=0;i<5;i++){
				System.out.println(Thread.currentThread().getName() + "---" + i);
			}
		}
	}
	public void test3(){
		synchronized (lock) {
			for(int i=0;i<5;i++){
				System.out.println(Thread.currentThread().getName() + "---" + i);
			}
		}
	}
}
