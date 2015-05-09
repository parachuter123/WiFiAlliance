package com.smny.wifiAlliance.test.thread;

public class Factory {
	public int i;
	public synchronized void add(){
		if(i==1){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		i++;
		notify();
		System.out.println(Thread.currentThread().getName() + "--->" + i);
	}
	public synchronized void sub(){
		if(i==0){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		i--;
		notify();
		System.out.println(Thread.currentThread().getName() + "--->" + i);
	}
}
