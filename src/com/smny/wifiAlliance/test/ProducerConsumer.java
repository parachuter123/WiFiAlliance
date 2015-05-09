package com.smny.wifiAlliance.test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;



public class ProducerConsumer  {
    public static void main(String[] args) {
    	ProducerConsumer pc = new ProducerConsumer();
    	Storage s = pc.new Storage();
    	ExecutorService  service = Executors.newCachedThreadPool();
    	
    	Producer p = pc.new Producer("����", s);
        Producer p2 = pc.new Producer("����", s);
        Consumer c = pc.new Consumer("����", s);
        Consumer c2 = pc.new Consumer("����", s);
        Consumer c3 = pc.new Consumer("����", s);
        service.submit(p);
        service.submit(p2);
        service.submit(c);
        service.submit(c2);
        service.submit(c3);
    }
    
    /**
     * ������
     * 
     */
    class Consumer implements Runnable {
        private String name;
        private Storage s = null;
 
        public Consumer(String name, Storage s) {
            this.name = name;
            this.s = s;
        }
 
        public void run() {
            try {
                while (true) {
                    System.out.println(name + "׼�����Ѳ�Ʒ.");
                    Product product = s.pop();
                    System.out.println(name + "������(" + product.toString() + ").");
                    System.out.println("===============");
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
 
        }
 
    }
 
    /**
     * ������
     * 
     */
    class Producer implements Runnable {
        private String name;
        private Storage s = null;
 
        public Producer(String name, Storage s) {
            this.name = name;
            this.s = s;
        }
 
        public void run() {
            try {
                while (true) {
                    Product product = new Product((int) (Math.random() * 10000)); // ����0~9999�������
                    System.out.println(name + "׼������(" + product.toString() + ").");
                    s.push(product);
                    System.out.println(name + "������(" + product.toString() + ").");
                    System.out.println("===============");
                    Thread.sleep(500);
                }
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
 
        }
    }
 
    /**
     * �ֿ⣬������Ų�Ʒ
     * 
     */
    public class Storage {
        BlockingQueue queues = new LinkedBlockingQueue(10);
 
        /**
         * ����
         * 
         * @param p
         *            ��Ʒ
         * @throws InterruptedException
         */
        public void push(Product p) throws InterruptedException {
            queues.put(p);
        }
 
        /**
         * ����
         * 
         * @return ��Ʒ
         * @throws InterruptedException
         */
        public Product pop() throws InterruptedException {
            return (Product) queues.take();
        }
    }
 
    /**
     * ��Ʒ
     * 
     */
    public class Product {
        private int id;
 
        public Product(int id) {
            this.id = id;
        }
 
        public String toString() {// ��дtoString����
            return "��Ʒ��" + this.id;
        }
    }
}