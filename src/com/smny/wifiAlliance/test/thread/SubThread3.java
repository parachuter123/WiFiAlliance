package com.smny.wifiAlliance.test.thread;

public class SubThread3 implements Runnable {

	public Factory factory;
	public SubThread3(Factory factory){
		this.factory = factory;
	}
	@Override
	public void run() {
		for(int i=0;i<5;i++){
			factory.sub();
		}
	}

}
