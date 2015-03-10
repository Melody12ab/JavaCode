package com.lee.mysql.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import com.lee.mysql.exception.FileException;

/**
 * �ļ�������
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>��վ: <a href="http://www.crazyit.org">���Java����</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class FileUtil {

	
	//ȫ�������ļ���Ŀ¼
	public final static String MYSQL_PROPERTIES_FILE = "config" + File.separator 
		+ "mysql.properties";
	
	//������ݿ����ӵ�Ŀ¼
	public final static String CONNECTIONS_FOLDER = "connections" + File.separator;
	
	public final static String MYSQL_HOME = "mysql.home";
	
	public final static String HOST = "host";
	
	public final static String PORT = "port";
	
	public final static String USERNAME = "username";
	
	public final static String PASSWORD = "password";
	
	//���������ļ���MYSQL_HOME����
	public static String getMySQLHome() {
		try {
			File configFile = new File(MYSQL_PROPERTIES_FILE);
			Properties props = getProperties(configFile);
			return props.getProperty(MYSQL_HOME);
		} catch (Exception e) {
			return "";
		}
	}
	
	/**
	 * ����MySQL Home�����ý������ļ�
	 * @param mysqlHome
	 */
	public static void saveMysqlHome(String mysqlHome) {
		try {
			File configFile = new File(MYSQL_PROPERTIES_FILE);
			Properties props = getProperties(configFile);
			props.setProperty(MYSQL_HOME, mysqlHome);
			FileOutputStream fos = new FileOutputStream(configFile);
			props.store(fos, "MySQL Home config");
			fos.close();
		} catch (Exception e) {
			throw new FileException("ϵͳ�ļ�����" + MYSQL_PROPERTIES_FILE);
		}
	}
	
	/**
	 * �����Ա��浽�������ļ���, ������Ӧ��ע��
	 * @param propertiesFile
	 * @param props
	 * @param comment
	 */
	public static void saveProperties(File propertiesFile, Properties props, 
			String comment) {
		try {
			//д��properties�ļ���
			FileOutputStream fos = new FileOutputStream(propertiesFile);
			props.store(fos, comment);
			fos.close();
		} catch (Exception e) {
			throw new FileException("���ݿ������ļ�д�����" + 
					propertiesFile.getAbsolutePath());
		}
	}
	
	/**
	 * ����һ�����ļ��Ĺ��߷���
	 * @param file
	 */
	public static void createNewFile(File file) {
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
		} catch (Exception e) {
			throw new FileException("д���ļ�����" + file.getAbsolutePath());
		}
	}
	
	/**
	 * �����ļ�����contentд��File��
	 * @param file
	 * @param content
	 */
	public static void writeToFile(File file, String content) {
		try {
			//�������ļ�
			createNewFile(file);
			//д���ļ�
			FileWriter writer = new FileWriter(file);
			writer.write(content);
			writer.close();
		} catch (Exception e) {
			throw new FileException("д���ļ�����" + file.getAbsolutePath());
		}
	}
	
	
	/*
	 * ����properties�ļ��õ���Ӧ��Properties����
	 */
	public static Properties getProperties(File propertyFile) throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(propertyFile);
		prop.load(fis);
		fis.close();
		return prop;
	}
	
	/**
	 * �õ��ļ����ļ���, ������׺
	 * @param file
	 * @return
	 */
	public static String getFileName(File file) {
		String fileName = file.getName();
		if (fileName.lastIndexOf(".") != -1)
		{
			return fileName.substring(0, fileName.lastIndexOf("."));
		}
		return fileName;
	}
}
