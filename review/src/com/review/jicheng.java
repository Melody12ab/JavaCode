package com.review;

class Animal01{
	private void beat(){
		System.out.println("心跳...");
	}
	public void breath() {
		// TODO Auto-generated method stub
		beat();
		System.out.println("吸气，放屁。。。");
	}
}
class Bird extends Animal01{
	public void fly(){
		System.out.println("我在天空飞翔....");
	}
}
class Wolf extends Animal01{
	public void run(){
		System.out.println("我在奔跑...");
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
