<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.board.BoardDAO, com.example.board.BoardVO" %>
<%@ page import="java.util.*" %>

<%
  // URL에서 게시글의 식별자를 읽어옴
  String seqParam = request.getParameter("seq");
  if (seqParam != null && !seqParam.isEmpty()) {
    int seq = Integer.parseInt(seqParam);
    BoardDAO boardDAO = new BoardDAO();
    BoardVO post = boardDAO.getPostBySeq(seq);

    if (post != null) {

%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>View Post</title>
</head>
<body>

<h1>View Post</h1>
<table border="1">
  <tr>
    <th>Title</th>
    <th>Writer</th>
    <th>Content</th>
    <th>Importance</th>
    <th>Attach</th>
    <th>Regulated</th>
  </tr>
  <tr>
    <td><%= post.getTitle() %></td>
    <td><%= post.getWriter() %></td>
    <td><%= post.getContent() %></td>
    <td><%= post.getImportance() %></td>
    <td>
      <%
        String attach = post.getAttach();
        if (attach != null && !attach.isEmpty()) {
          // 이미지가 비어 있지 않은 경우에만 표시
      %>
      <img src="<%= attach %>" alt="Attachment" style="max-width: 300px; max-height: 300px;">
      <%
        }
      %>
    </td>
    <td><%= post.getRegdate() %></td>
  </tr>
</table>

<a href="posts.jsp">돌아가기</a>

</body>
</html>
<%
} else {

%><p>해당하는 게시글을 찾을 수 없습니다.</p><%
  }
} else {

%><p>게시글 식별자가 전달되지 않았습니다.</p><%
  }
%>
