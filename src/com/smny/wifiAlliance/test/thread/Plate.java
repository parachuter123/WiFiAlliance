package com.smny.wifiAlliance.test.thread;

import java.util.ArrayList;
import java.util.List;

public class Plate {  
    List<Object> eggs = new ArrayList<Object>();  
  
    // 定义一个拿鸡蛋的方法，返回鸡蛋（基本数据类型）  
    public synchronized Object getEggs() {  
        if (eggs.size() == 0) {  
            try {  
                wait();  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
        }  
  
        Object egg = eggs.get(0);// 当放入鸡蛋的时候拿到鸡蛋  
        eggs.clear();// 清空盘子  
        notify();// 唤醒等待的单个线程  
        System.out.println("拿到鸡蛋,你该放鸡蛋了");  
        return egg;  
    }  
  
    //定义一个放鸡蛋的方法，传入鸡蛋参数  
    public synchronized void putEggs(Object egg) {  
        if (eggs.size() >0 ) {  
            try {  
                wait();  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
        }  
        eggs.add(egg);// 往盘子里面放鸡蛋  
        notify();// 唤醒等待的单个线程  
        System.out.println("放入鸡蛋，你可以拿了！");  
  
    }  
  
    public static void main(String[] args) {  
        Plate plate = new Plate();  
        Thread t1 = new PutThread(plate);  
        Thread t2 = new GetThread(plate);  
        t1.start();  
        t2.start();  
        try {  
            t1.join();  
            t2.join();  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
  
    }  
}  
  
// 给放鸡蛋添加单个线程  
class PutThread extends Thread {  
    private Plate plate;  
    private Object egg = new Object();  
  
    public PutThread(Plate plate) {  
        this.plate = plate;  
    }  
  
    public void run() {  
        for (int i = 0; i < 10; i++) {  
            plate.putEggs(egg);  
        }  
    }  
}  
  
// 给拿鸡蛋添加单个线程  
class GetThread extends Thread {  
    private Plate plate;  
  
    public GetThread(Plate plate) {  
        this.plate = plate;  
    }  
  
    public void run() {  
        for (int j = 0; j < 10; j++) {  
            plate.getEggs();  
        }  
  
    }  
}  