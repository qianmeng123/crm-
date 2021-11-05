<%--
  Created by IntelliJ IDEA.
  User: annm
  Date: 2021/7/24
  Time: 17:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" +
            request.getServerName() + ":" + request.getServerPort() +
            request.getContextPath() + "/";
%>
<html>
<head>
    <title>Title</title>
    <base href="<%=basePath%>">
</head>
<body>
<form action="panduan.do">
    账号：<input type="text" name="user"><br>
    密码：<input type="text" name="password">
    <br>
    <input type="submit">
    <h1>登陆失败</h1>
</form>
<a href="zhuce.jsp">注册账号</a>
</table>
</body>
</html>
