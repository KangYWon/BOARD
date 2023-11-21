<%@ page import="com.oreilly.servlet.MultipartRequest" %>
<%@ page import="java.io.File" %>
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy" %><%--
  Created by IntelliJ IDEA.
  User: kang-yewon
  Date: 11/17/23
  Time: 10:28 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String filename="";
  int sizeLimit = 15*1024*1024; //15Mbyte

  String realPath = request.getServletContext().getRealPath("upload");
  File dir = new File(realPath);
  if(!dir.exists()) dir.mkdirs();

  MultipartRequest multpartRequest = null;
  multpartRequest = new MultipartRequest(request, realPath,sizeLimit, "utf-8", new DefaultFileRenamePolicy());
  filename = multpartRequest.getFilesystemName("image");

%>
파일명 : <%= filename%> <br/>
<img src="${pageContext.request.contextPath}/upload/<%=filename%>"  style="max-width: 100%; height: auto;">

<html>
<head>
  <title>Title</title>
</head>
<body>
fileupload_ok.jsp

</body>
</html>
