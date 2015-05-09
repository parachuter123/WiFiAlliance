package com.smny.wifiAlliance.test.thread;

public class AddThread2 implements Runnable {
	
	public Factory factory;
	public AddThread2(Factory factory){
		this.factory = factory;
	}
	@Override
	public void run() {
		for(int i=0;i<5;i++){
			factory.add();
		}
	}

}
