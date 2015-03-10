<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
Connection conn = null;
try {
  Class.forName("com.mysql.jdbc.Driver");
  String url = "jdbc:mysql://localhost:3306/Teamintro";
  conn = DriverManager.getConnection(url, "root", "123456");
}
catch (Exception e) {
  e.printStackTrace();
}
String sql="select * from userinfo";
Statement st=conn.createStatement();
ResultSet rs=st.executeQuery(sql);
ResultSetMetaData rsmd=rs.getMetaData();
int columnCount = rsmd.getColumnCount(); 
while (rs.next()){   
    for (int i=1; i<=columnCount; i++){  
 %>
        <%=rs.getString(i) + " | " %>
 <%   
    }
} 
rs.close();   
st.close();   
conn.close(); 
%>
</body>
</html>