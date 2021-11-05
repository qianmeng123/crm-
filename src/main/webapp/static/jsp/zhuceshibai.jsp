<%--
  Created by IntelliJ IDEA.
  User: annm
  Date: 2021/7/28
  Time: 17:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="zhuce.do">
    账号：<input type="text" name="user"><br>
    密码：<input type="text" name="password"><br>
    请重复密码：<input type="text">
    <br>
    <input type="submit">
    <h1>注册失败,账号有重复，请重新注册</h1>
</form>
</body>
</html>
