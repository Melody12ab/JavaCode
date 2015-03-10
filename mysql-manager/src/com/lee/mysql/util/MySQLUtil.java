package com.lee.mysql.util;

import java.io.File;

/**
 * �������ڴ��MySQL����
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>��վ: <a href="http://www.crazyit.org">���Java����</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class MySQLUtil {


	//mysql��װĿ¼�µ�binĿ¼
	public final static String MYSQL_HOME_BIN = File.separator + "bin";
	
	//mysqldump����
	public final static String MYSQLDUMP_COMMAND = "mysqldump";
	
	//mysql����
	public final static String MYSQL_COMMAND = "mysql";
	
	//������
	public final static String TABLE_TYPE = "BASE TABLE";
	
	//ϵͳ��ͼ
	public final static String SYSTEM_VIEW_TYPE = "SYSTEM VIEW";

	//�洢����
	public final static String PROCEDURE_TYPE = "PROCEDURE";
	
	//����
	public final static String FUNCTION_TYPE = "FUNCTION";
	
	//�����ַ���
	public final static String DESC = "DESC";
	
	//�����ַ���
	public final static String ASC = "ASC";
	
	//������ͼ����
	public final static String CREATE_VIEW = "create view ";
	
	//AS�ַ���
	public final static String AS = "AS";
	
	//�޸���ͼ����
	public final static String ALTER_VIEW = "alter view ";
	
	//ɾ����ͼ����
	public final static String DROP_VIEW = "drop view if exists ";
	
	//�����洢��������
	public final static String CREATE_PROCEDURE = "create procedure ";
	
	//�޸Ĵ洢����
	public final static String ALTER_PROCEDURE = "alter procedure ";
	
	//ɾ���洢����
	public final static String DROP_PROCEDURE = "drop procedure ";
	
	//������������
	public final static String CREATE_FUNCTION = "create function ";
	
	//�޸ĺ���
	public final static String ALTER_FUNCTION = "alter function ";
	
	//ɾ����������
	public final static String DROP_FUNCTION = "drop function ";
	
}
