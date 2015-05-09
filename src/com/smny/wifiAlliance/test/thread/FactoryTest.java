package com.smny.wifiAlliance.test.thread;

public class FactoryTest {
	public static void main(String[] args) {
		Factory factory = new Factory();
		Thread th1 = new Thread(new AddThread2(factory),"th1");
		Thread th2 = new Thread(new SubThread3(factory),"th2");
		th1.start();
		th2.start();
	}
	
}
