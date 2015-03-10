package com.index;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DealIndex extends HttpServlet
{
  public static void appendString(String file, String conent)
  {
    BufferedWriter out = null;
    try {
      out = new BufferedWriter(new OutputStreamWriter(
        new FileOutputStream(file, true)));
      out.write(conent);
    } catch (Exception e) {
      e.printStackTrace();
      try
      {
        if (out != null)
          out.close();
      }
      catch (IOException e1) {
        e1.printStackTrace();
      }
    }
    finally
    {
      try
      {
        if (out != null)
          out.close();
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    doPost(request, response);
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    response.setContentType("text/html;charset=utf-8");
    response.setCharacterEncoding("utf-8");

    String name1 = request.getParameter("name");
    String name = new String(name1.getBytes("iso-8859-1"), "UTF-8");

    String email2 = request.getParameter("email");
    String email = new String(email2.getBytes("iso-8859-1"), "UTF-8");

    String subject1 = request.getParameter("subject");
    String subject = new String(subject1.getBytes("iso-8859-1"), "UTF-8");

    String message1 = request.getParameter("message");
    String message = new String(message1.getBytes("iso-8859-1"), "UTF-8");
    if ((name != null) && (email != null) && (subject != null) && (message != null))
    {
      Connection conn = DataBaseUtil.getConnection();
      String sql = "insert into userinfo(name,email,subject,message) values(?,?,?,?)";
      try {
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, email);
        ps.setString(3, subject);
        ps.setString(4, message);
        ps.executeUpdate();
        ps.close();
      }
      catch (Exception e) {
        e.printStackTrace();
      } finally {
        DataBaseUtil.closeConnection(conn);
      }
      request.getRequestDispatcher("home.jsp").forward(request, response);
    }
  }
}