package com.review2;

import java.io.File;
import java.util.Scanner;

public class ScannerFile {
	public static void main(String[] args) throws Exception {
		Scanner sc=new Scanner(new File("CharTest.txt"));
		while(sc.hasNextLine()){
			System.out.println(sc.nextLine());
		}
	}
}
