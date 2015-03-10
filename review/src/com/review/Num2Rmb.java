package com.review;

import java.util.Arrays;

public class Num2Rmb {
	private String[] hanArr = { "��", "Ҽ", "�E", "��","��","��","½","��","��","��"};
	private String[] unitArr = { "ʮ", "��", "ǧ" };

	private String[] divide(double num) {
		long zheng = (long) num;
		long xiao = Math.round((num - zheng) * 100);//������λ��Ч����
		return new String[] { zheng + "", String.valueOf(xiao) };
	}

	private String toHanStr(String numStr) {
		// TODO Auto-generated method stub
		String result="";
		int numLen=numStr.length();
		for(int i=0;i<numLen;i++){
			int num=numStr.charAt(i)-48;
			System.out.println(num);
			if(i!=numLen-1 && num!=0){
				result+=hanArr[num]+unitArr[numLen-2-i];
			}else{
				result+=hanArr[num];
			}
		}
		return result;
	}
	public static void main(String[] args) {
		Num2Rmb nr=new Num2Rmb();
		System.out.println(Arrays.toString(nr.divide(7874636.12)));
		System.out.println(nr.toHanStr("7809"));
	}
}
