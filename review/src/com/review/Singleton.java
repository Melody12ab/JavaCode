package com.review;

class SingletonTest {
	private static SingletonTest instance;
	private SingletonTest(){}
	public static SingletonTest getInstance(){
		if (instance==null) {
			instance=new SingletonTest();
		}
		return instance;
	}
}
public class Singleton{
	public static void main(String[] args) {
		SingletonTest s1=SingletonTest.getInstance();
		SingletonTest s2=SingletonTest.getInstance();
		System.out.println(s1==s2);
		//System.out.println(s1.hashCode()+" ;; "+s2.hashCode());
	}
}
