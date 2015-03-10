package com.review;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Gobang {
	private String [] [] board;
	private static int BOARD_SIZE=15;
	private static int a=0;
	private void initBoard() {
		// TODO Auto-generated method stub
		board=new String [BOARD_SIZE][BOARD_SIZE];
		for(int i=0;i<BOARD_SIZE;i++){
			for(int j=0;j<BOARD_SIZE;j++){
				board[i][j]="+";
			}
		}
	}
	private void printBoard() {
		// TODO Auto-generated method stub
		for(int i=0;i<BOARD_SIZE;i++){
			for(int j=0;j<BOARD_SIZE;j++){
				System.out.print(board[i][j]);
			}
			System.out.print("\n");
		}
	}
	public static void main(String[] args){
		Gobang gb=new Gobang();
		gb.initBoard();
		gb.printBoard();
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		String inputStr=null;
		try {
			while((inputStr=br.readLine())!=null){
				String [] posStrArr=inputStr.split(",");
				int xPos=Integer.parseInt(posStrArr[0]);
				int yPos=Integer.parseInt(posStrArr[1]);
				if(a%2==0){
					gb.board[yPos-1][xPos-1]="●";
				}else{
					gb.board[yPos-1][xPos-1]="△";
				}
				a++;
				gb.printBoard();
				System.out.println("请输入你下棋的坐标x,y:");
				
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("game is over!");
		}
	}
}
