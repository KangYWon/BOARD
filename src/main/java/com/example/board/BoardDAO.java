//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.board;

import com.example.board.util.JDBCUtil;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BoardDAO {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    private final String BOARD_INSERT = "insert into BOARD (title, writer, content,attach,importance) values (?,?,?,?,?)";
    private final String BOARD_UPDATE = "update BOARD set title=?, writer=?, content=?, attach=?, importance=?,where seq=?";
    private final String BOARD_DELETE = "delete from BOARD  where seq=?";
    private final String BOARD_GET = "select * from BOARD  where seq=?";
    private final String BOARD_LIST = "select * from BOARD order by seq desc";

    public BoardDAO() {
    }

    public int insertBoard(BoardVO vo) {
        System.out.println("===> JDBC로 insertBoard() 기능 처리");

        try {
            this.conn = com.example.board.util.JDBCUtil.getConnection();
            this.stmt = this.conn.prepareStatement("insert into BOARD (title, writer, content,attach,importance) values (?,?,?,?,?)");
            this.stmt.setString(1, vo.getTitle());
            this.stmt.setString(2, vo.getWriter());
            this.stmt.setString(3, vo.getContent());
            this.stmt.setString(4, vo.getAttach());
            this.stmt.setString(5, vo.getImportance());
            this.stmt.executeUpdate();
            return 1;
        } catch (Exception var3) {
            var3.printStackTrace();
            return 0;
        }
    }

    public void deleteBoard(BoardVO vo) {
        System.out.println("===> JDBC로 deleteBoard() 기능 처리");

        try {
            this.conn = com.example.board.util.JDBCUtil.getConnection();
            this.stmt = this.conn.prepareStatement("delete from BOARD  where seq=?");
            this.stmt.setInt(1, vo.getSeq());
            this.stmt.executeUpdate();
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public int updateBoard(BoardVO boardVO) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int result = 0;

        try {
            conn = com.example.board.util.JDBCUtil.getConnection();
            String query;
            if (boardVO.getAttach() != null && !boardVO.getAttach().isEmpty()) {
                query = this.uploadFile(boardVO.getAttach());
                boardVO.setAttach(query);
            }

            query = "UPDATE BOARD SET title=?, writer=?, content=?, attach=?, importance=? WHERE seq=?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, boardVO.getTitle());
            stmt.setString(2, boardVO.getWriter());
            stmt.setString(3, boardVO.getContent());
            stmt.setString(4, boardVO.getAttach());
            stmt.setString(5, boardVO.getImportance());
            stmt.setInt(6, boardVO.getSeq());
            result = stmt.executeUpdate();
        } catch (SQLException var6) {
            var6.printStackTrace();
        }

        return result;
    }

    private String uploadFile(String attach) {
        String uploadDirectory = "/upload";
        String fileExtension = attach.substring(attach.lastIndexOf("."));
        String newFileName = UUID.randomUUID().toString() + fileExtension;

        try {
            File uploadDir = new File(uploadDirectory);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            Path filePath = uploadDir.toPath().resolve(newFileName);
            Files.copy((new File(attach)).toPath(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return newFileName;
        } catch (IOException var7) {
            var7.printStackTrace();
            return null;
        }
    }

    public BoardVO getBoard(int seq) {
        BoardVO one = new BoardVO();
        System.out.println("===> JDBC로 getBoard() 기능 처리");

        try {
            this.conn = com.example.board.util.JDBCUtil.getConnection();
            this.stmt = this.conn.prepareStatement("select * from BOARD  where seq=?");
            this.stmt.setInt(1, seq);
            this.rs = this.stmt.executeQuery();
            if (this.rs.next()) {
                one.setSeq(this.rs.getInt("seq"));
                one.setTitle(this.rs.getString("title"));
                one.setWriter(this.rs.getString("writer"));
                one.setContent(this.rs.getString("content"));
                one.setAttach(this.rs.getString("attach"));
                one.setImportance(this.rs.getString("importance"));
                one.setCnt(this.rs.getInt("cnt"));
            }

            this.rs.close();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return one;
    }

    public List<BoardVO> getBoardList() {
        List<BoardVO> list = new ArrayList();
        System.out.println("===> JDBC로 getBoardList() 기능 처리");

        try {
            this.conn = com.example.board.util.JDBCUtil.getConnection();
            this.stmt = this.conn.prepareStatement("select * from BOARD order by seq desc");
            this.rs = this.stmt.executeQuery();

            while(this.rs.next()) {
                BoardVO one = new BoardVO();
                one.setSeq(this.rs.getInt("seq"));
                one.setTitle(this.rs.getString("title"));
                one.setWriter(this.rs.getString("writer"));
                one.setContent(this.rs.getString("content"));
                one.setAttach(this.rs.getString("attach"));
                one.setImportance(this.rs.getString("importance"));
                one.setRegulated(this.rs.getDate("regdate"));
                one.setCnt(this.rs.getInt("cnt"));
                list.add(one);
            }

            this.rs.close();
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return list;
    }

    public BoardVO getPostBySeq(int seq) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        BoardVO post = null;

        try {
            conn = com.example.board.util.JDBCUtil.getConnection();
            String query = "SELECT * FROM BOARD WHERE seq = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, seq);
            rs = stmt.executeQuery();
            if (rs.next()) {
                post = new BoardVO();
                post.setSeq(rs.getInt("seq"));
                post.setTitle(rs.getString("title"));
                post.setWriter(rs.getString("writer"));
                post.setContent(rs.getString("content"));
                post.setAttach(rs.getString("attach"));
                post.setImportance(rs.getString("importance"));
            }
        } catch (SQLException var7) {
            var7.printStackTrace();
        }

        return post;
    }
}
