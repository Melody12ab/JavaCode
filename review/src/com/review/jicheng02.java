package com.review;

class Animal02{
	private void beat() {
		// TODO Auto-generated method stub
		System.out.println("心跳...");
	}
	public void breath(){
		beat();
		System.out.println("吸一口气,吐一口气,bearth...");
	}
}
class Bird01{
	private Animal02 a;
	public Bird01(Animal02 a) {
		this.a=a;
	}
	public void breath(){
		a.breath();
	}
	public void fly(){
		System.out.println("我在天上飞...");
	}
}
class Wolf01{
	private Animal02 a;
	public Wolf01(Animal02 a) {
		this.a=a;
	}
	public void breath(){
		a.breath();
	}
	public void run(){
		System.out.println("我在自由的奔跑...");
	}
}
public class jicheng02 {
	public static void main(String[] args) {
		Animal02 a1=new Animal02();
		Bird01 b=new Bird01(a1);
		b.breath();
		b.fly();
		
		Animal02 a2=new Animal02();
		Wolf01 w=new Wolf01(a2);
		w.breath();
		w.run();
	}
}
