package com.logindemo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ManagerUsers
 */
public class ManagerUsers extends HttpServlet {
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<script type='text/javascript' language='javascript'>");
		out.println("function gotoPageNow(){var pageNow=document.getElementById('pageNow');window.open('/logindemo/ManagerUsers?pageNow='+pageNow.value,'_self');}");
		out.println("</script>");
		out.println("<h1>�����û�</h1>");
		Connection ct = null;
		ResultSet rs = null;
		PreparedStatement ps = null;

		// �����ҳ����
		int pageNow = 1;// ��ǰҳ
		String spageNow = request.getParameter("pageNow");
		if (spageNow != null) {
			pageNow = Integer.parseInt(spageNow);
		}
		int pageSize = 3;// úҵ3��
		int pageCount;// ���������
		int rowCount;// һ���ж�������¼

		try {
			Class.forName("com.mysql.jdbc.Driver");
			ct = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/servlet", "root", "123456");

			ps = ct.prepareStatement("select count(*) from users");
			rs = ps.executeQuery();
			rs.next();
			rowCount = rs.getInt(1);
			// ����1
			if (rowCount % pageSize == 0) {
				pageCount = rowCount / pageSize;
			} else {
				pageCount = rowCount / pageSize + 1;
			}
			// ����2
			// pageCount=rowCount%pageSize==0?rowCount/pageSize:rowCount/pageSize+1;
			// ����3 pageCount=(rowCount-1)/pageSize+1;

			ps = ct.prepareStatement("select * from users where id<="
					+ pageSize * pageNow + " and id>="
					+ (pageSize * (pageNow - 1) + 1));
			rs = ps.executeQuery();
			out.println("<table border='1'>");
			out.println("<tr><th>id</th><th>�û���</th><th>email</th><th>����</th></tr>");
			while (rs.next()) {
				out.println("<tr><td>" + rs.getString(1) + "</td><td>"
						+ rs.getString(2) + "</td><td>" + rs.getString(1)
						+ "</td><td>" + rs.getString(1) + "</td></tr>");
			}

			out.println("</table>");
			if (pageNow != 1) {
				out.print("<a href='/logindemo/ManagerUsers?pageNow="
						+ (pageNow - 1) + "'>" + "��һҳ</a>&nbsp;");
			}
			for (int i = 1; i < pageCount + 1; i++) {
				out.println("<a href='/logindemo/ManagerUsers?pageNow=" + i
						+ "'" + "><" + i + "></a>");
			}
			if (pageNow != pageCount) {
				out.print("&nbsp;<a href='/logindemo/ManagerUsers?pageNow="
						+ (pageNow + 1) + "'>" + "��һҳ</a>");
			}
			// ��ʾ��ҳ��Ϣ
			out.println("&nbsp;&nbsp;&nbsp;��ǰҳ" + pageNow + "/��ҳ��" + pageCount);

			// ҳ����ת
			out.println("<br>��ת��<input type='text' id='pageNow' name='pageNow'/><input type='button' onClick='gotoPageNow()' value='go'/>");
		} catch (Exception e) {

		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				rs = null;
				if (ps != null) {
					ps.close();
				}
				ps = null;
				if (ct != null) {
					ct.close();
				}
				ct = null;
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(request, response);
	}

}
