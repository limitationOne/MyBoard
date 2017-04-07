package board.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import board.DTO.BoardDTO;
import board.pageTO.PageTO;

public class BoardDAO {
	private static BoardDAO dao = new BoardDAO();
	private DataSource dataFactiory;
	
	private BoardDAO() {
		try {
			Context ctx = new InitialContext();
			dataFactiory = (DataSource) ctx.lookup("java:comp/env/jdbc/oracle11g");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 게시글 숫자
	public int totalCount(){
		int totalCount = -1;
		
		String sql = "select count(*) from myboard";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataFactiory.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			if(rs.next()) totalCount = rs.getInt(1);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, null, pstmt, conn);
		}
		return totalCount;
	}
	
	// 게시판
	public PageTO list(int num){
		System.out.println("list()");
		
		PageTO to = new PageTO();
		int lostEnd = totalCount() / to.getContentCount();
		
		if(totalCount() % to.getContentCount() != 0) lostEnd += 1;
		
		to.setPageNum(num);
		if(num % 10 == 0) num -= 1;
		
		to.setFirstPage(num / 10 * 10 + 1);
		to.setEndPage(num / 10 * 10 + 10);
		
		// 마지막 페이지 처리
		if(to.getEndPage() > lostEnd) to.setEndPage(lostEnd);
		
		to.setTotalCount(totalCount() / to.getContentCount() + 1);
		
		if(totalCount()%to.getContentCount() == 0) to.setTotalCount(to.getTotalCount() - 1); 
		
		String sql = "select * from ("
						+ "select myboard.*, rownum as rnum from ("
						+ "select num,author,title,content,writeday,readcnt,repRoot,repStep,repIndent "
						+ "from myboard order by repRoot desc, repStep asc) myboard	"
						+ "where rownum<" + (num * to.getContentCount() + 1) + " ) "
						+ "where " + (num * to.getContentCount() - to.getContentCount()) + "<rnum";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataFactiory.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				to.getList().add(new BoardDTO(rs.getInt("num"), 
						rs.getString("author"), 
						rs.getString("title"), 
						null,
						rs.getString("writeday"), 
						rs.getInt("readcnt"), 
						rs.getInt("repRoot"), 
						rs.getInt("repStep"), 
						rs.getInt("repIndent")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, null, pstmt, conn);
		}
		return to;
	}
	
	// 글쓰기
	public void write(String author, String title, String content){
		System.out.println("write()");
		
		// index, 글쓴이, 제목, 내용, 날짜, 조회수, 원래글과 댓글을 붙어있게 하기 위한 정보, 댓글에 대한 순서 지정을 위한 컬럼, 들여쓰기정보
		int num = -1;
		if((num = makeNum()+1) == 0) return;
		
		String sql = "insert into myBoard (num,author,title,content,"
				+ "readcnt,repRoot,repStep,repIndent) "
				+ "values (?,?,?,?, 0,?,0,0)";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataFactiory.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			pstmt.setString(2, author);
			pstmt.setString(3, title);
			pstmt.setString(4, content);
			pstmt.setInt(5, num);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(null, null, pstmt, conn);
		}
	}
	
	// 글 상세보기
	public BoardDTO retrieve(int num){
		System.out.println("retrieve()");
		
		String sql = "select * from myBoard where num=?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardDTO dto = null;
		
		addReadcon(num);
		
		try {
			conn = dataFactiory.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new BoardDTO(rs.getInt("num"), 
						rs.getString("author"), 
						rs.getString("title"), 
						rs.getString("content"), 
						rs.getString("writeday"), 
						rs.getInt("readcnt"), 
						rs.getInt("repRoot"), 
						rs.getInt("repStep"), 
						rs.getInt("repIndent"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, null, pstmt, conn);
		}
		return dto;
	}
	
	// 수정UI 페이지
	public BoardDTO updateUI(int num) {
		System.out.println("updateUI");
		
		BoardDTO dto = null;
		String sql = "select * from myboard where num=?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataFactiory.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new BoardDTO(rs.getInt("num"), 
						rs.getString("author"), 
						rs.getString("title"), 
						rs.getString("content"), 
						rs.getString("writeday"), 
						rs.getInt("readcnt"), 
						rs.getInt("repRoot"), 
						rs.getInt("repStep"), 
						rs.getInt("repIndent"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, null, pstmt, conn);
		}
		return dto;
	}
	
	// 수정
	public BoardDTO update(int num, String title, String content) {
		System.out.println("update()");
		
		BoardDTO dto = null;
		String sql = "update myboard set title=?, content=? where num=?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataFactiory.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setInt(3, num);
			
			rs = pstmt.executeQuery();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, null, pstmt, conn);
		}
		return dto;
	}
	
	// 삭제
	public void delete(int num) {
		System.out.println("delete()");
		
		String sql = "delete from myboard where num=?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataFactiory.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(null, null, pstmt, conn);
		}
	}
	
	// 검색
	public PageTO search(String type, String value) {
		if((value == null || "".equals(value) || type == null || "".equals(type)) 
				|| !("title".equals(type) || "author".equals(type))) { 
			return list(1);
		}
		String sql = "select * from myboard where upper(" + type + ") like upper(?) order by repRoot desc, repStep asc";
		PageTO to = new PageTO();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataFactiory.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+value+"%");
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				to.getList().add(new BoardDTO(rs.getInt("num"), 
						rs.getString("author"), 
						rs.getString("title"), 
						rs.getString("content"), 
						rs.getString("writeday"), 
						rs.getInt("readcnt"), 
						rs.getInt("repRoot"), 
						rs.getInt("repStep"), 
						rs.getInt("repIndent")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, null, pstmt, conn);
		}
		return to;
	}
	
	// 답글 작성 UI
	public BoardDTO replyUI(int num) {
		System.out.println("replyUI");
		
		BoardDTO dto = null;
		String sql = "select * from myboard where num=?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataFactiory.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				dto = new BoardDTO(rs.getInt("num"), 
						rs.getString("author"), 
						rs.getString("title"), 
						rs.getString("content"), 
						rs.getString("writeday"), 
						rs.getInt("readcnt"), 
						rs.getInt("repRoot"), 
						rs.getInt("repStep"), 
						rs.getInt("repIndent"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, null, pstmt, conn);
		}
		return dto;
	}
	
	// 답글 작성
	public int reply(String author, String title, String content, int repRoot, int repStep, int repIndent){
		System.out.println("reply()");
		
		// index, 글쓴이, 제목, 내용, 날짜, 조회수, 
		// 원래글과 댓글을 붙어있게 하기 위한 정보, 댓글에 대한 순서 지정을 위한 컬럼, 들여쓰기정보
		int num = -1;
		if((num = makeNum()+1) == 0) return -1;
		
		String sql = "insert into myBoard (num,author,title,content,"
				+ "readcnt,repRoot,repStep,repIndent) "
				+ "values (?,?,?,?, 0,?,?,?)";
		
		addRepStep(repRoot, repStep);
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataFactiory.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			pstmt.setString(2, author);
			pstmt.setString(3, title);
			pstmt.setString(4, content);
			pstmt.setInt(5, repRoot);
			pstmt.setInt(6, repStep + 1);
			pstmt.setInt(7, repIndent + 1);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(null, null, pstmt, conn);
		}
		return num;
	}
	
	// 답글 순서
	private void addRepStep(int repRoot, int repStep){
		String sql = "update myboard set repStep=repStep+1 where repRoot=? and ? < repStep";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataFactiory.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, repRoot);
			pstmt.setInt(2, repStep);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, null, pstmt, conn);
		}
	}
	
	private int makeNum(){
		String sql = "select max(num) from myBoard";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataFactiory.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) return rs.getInt(1);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, null, pstmt, conn);
		}
		return -1;
	}
	
	// 조회수
	private void addReadcon(int num){
		String sql = "update myboard set readcnt=readcnt+1 where num=?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataFactiory.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(null, null, pstmt, conn);
		}
	}
	
	private void closeAll(ResultSet rs, CallableStatement cstmt, PreparedStatement pstmt, Connection conn){
		try {
			if(rs != null) rs.close();
			if(cstmt != null) cstmt.close();
			if(pstmt != null) pstmt.close();
			if(conn != null) conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static BoardDAO getDao() {
		return dao;
	}
}