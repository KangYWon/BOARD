<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.board.BoardDAO, com.example.board.BoardVO" %>
<%@ page import="java.io.File" %>
<%@ page import="com.oreilly.servlet.MultipartRequest" %>
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy" %>


<%
  request.setCharacterEncoding("utf-8");

  // 파일 업로드 처리 코드
  String filename = "";
  int sizeLimit = 15 * 1024 * 1024; // 15MB

  String realPath = request.getServletContext().getRealPath("upload");
  File dir = new File(realPath);
  if (!dir.exists()) dir.mkdirs();

  MultipartRequest multipartRequest = new MultipartRequest(request, realPath, sizeLimit, "utf-8", new DefaultFileRenamePolicy());
  filename = multipartRequest.getFilesystemName("attach");

  // 데이터베이스에 저장할 정보 추출
  String title = multipartRequest.getParameter("title");
  String writer = multipartRequest.getParameter("writer");
  String content = multipartRequest.getParameter("content");
  String importance = multipartRequest.getParameter("importance");

  // 데이터베이스에 저장
  BoardVO boardVO = new BoardVO();
  boardVO.setTitle(title);
  boardVO.setWriter(writer);
  boardVO.setContent(content);
  boardVO.setAttach(filename); // 파일명을 저장
  boardVO.setImportance(importance);

  BoardDAO boardDAO = new BoardDAO();
  int result = boardDAO.insertBoard(boardVO);

  String msg = "데이터 추가 성공!";
  if (result == 0) {
    msg = "[에러] 데이터 추가 실패";
  }
%>

<script>
  alert('<%=msg%>');
  location.href='posts.jsp';
</script>
