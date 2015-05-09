package com.smny.wifiAlliance.test.thread;

import java.util.ArrayList;
import java.util.List;

public class Plate {  
    List<Object> eggs = new ArrayList<Object>();  
  
    // ����һ���ü����ķ��������ؼ����������������ͣ�  
    public synchronized Object getEggs() {  
        if (eggs.size() == 0) {  
            try {  
                wait();  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
        }  
  
        Object egg = eggs.get(0);// �����뼦����ʱ���õ�����  
        eggs.clear();// �������  
        notify();// ���ѵȴ��ĵ����߳�  
        System.out.println("�õ�����,��÷ż�����");  
        return egg;  
    }  
  
    //����һ���ż����ķ��������뼦������  
    public synchronized void putEggs(Object egg) {  
        if (eggs.size() >0 ) {  
            try {  
                wait();  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
        }  
        eggs.add(egg);// ����������ż���  
        notify();// ���ѵȴ��ĵ����߳�  
        System.out.println("���뼦������������ˣ�");  
  
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
  
// ���ż�����ӵ����߳�  
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
  
// ���ü�����ӵ����߳�  
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