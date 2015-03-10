package com.lee.mysql.util;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;

import com.lee.mysql.exception.CommandException;

/**
 * �������
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>��վ: <a href="http://www.crazyit.org">���Java����</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class CommandUtil {

	public final static String WINDOWS_COMMAND = "cmd /c ";
	
	/**
	 * ִ��������ؽ���������
	 * @param command
	 * @return
	 */
	public static InputStream executeCommand(String command) {
		try {
			//��windows�½���������һ��bat�ļ�, ��ִ�и�bat�ļ�
			File batFile = new File("temp/dump.bat");
			if (!batFile.exists()) batFile.createNewFile();
			//������д���ļ���
			FileWriter writer = new FileWriter(batFile);
			writer.write(command);
			writer.close();
			//ִ�и�bat�ļ�
			Process process =Runtime.getRuntime().exec(WINDOWS_COMMAND + batFile.getAbsolutePath());
			process.waitFor();
			//��bat�ļ�ɾ��
			batFile.delete();
			return process.getInputStream();
		} catch (Exception e) {
			throw new CommandException("ִ���������" + command);
		}
	}
}
