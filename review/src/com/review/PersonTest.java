package com.review;

class Person{
	private String name;
	private int age;
	public String getName() {
		return name;//完整的应该是this.name
	}
	public void setName(String name) {
		if (name.length()>6 || name.length()<2) {
			System.out.println("姓名不符合");
			return;
		}else{
			this.name=name;
		}
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		if (age>100||age<0) {
			System.out.println("年龄不符合");
			return;
		}else{
			this.age=age;
		}
	}
	public void shwoinfo() {
		// TODO Auto-generated method stub
		System.out.println(this.getAge()+this.getName());
	}
	public String toString() {
		return "Person [name=" + name + ", age=" + age + "]";
	}
	
}
public class PersonTest{
	public static void main(String[] args) {
		Person p=new Person();
		p.setAge(10);
		p.setName("张年您");
		System.out.println(p);
	}
}
