package com.review;

class Animal01{
	private void beat(){
		System.out.println("����...");
	}
	public void breath() {
		// TODO Auto-generated method stub
		beat();
		System.out.println("��������ƨ������");
	}
}
class Bird extends Animal01{
	public void fly(){
		System.out.println("������շ���....");
	}
}
class Wolf extends Animal01{
	public void run(){
		System.out.println("���ڱ���...");
	}
}

public class jicheng {
	public static void main(String[] args) {
		Bird b=new Bird();
		b.breath();b.fly();
		Wolf w=new Wolf();
		w.breath();
		w.run();
	}
}
