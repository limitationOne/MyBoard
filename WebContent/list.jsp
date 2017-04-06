<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Board List</title>
<LINK rel="stylesheet" type="text/css" href="./css/list.css"/>
</head>
<body>
	<DIV style="text-align: center;">
		<h1>게시판</h1>
		<TABLE>
			<THEAD>
				<TR>
					<TH>번호</TH>
					<TH>제목</TH>
					<TH>작성자</TH>
					<TH>작성일시</TH>
					<TH>조회수</TH>
				</TR>
			</THEAD>
			<TBODY>
				<c:forEach items="${ list }" var="boardDTO">
					<TR>
						<TD><A href="retrieve.do?num=${ boardDTO.num }">${ boardDTO.num }</A></TD>
						<TD style="text-align: left;">
							<c:if test="${ boardDTO.repIndent != 0 }">
								<c:forEach begin="2" end="${ boardDTO.repIndent - 1 }">&nbsp;&nbsp;</c:forEach>
								<c:forEach begin="${ boardDTO.repIndent }" end="${ boardDTO.repIndent }"> ㄴ </c:forEach>
							</c:if>
							<A href="retrieve.do?num=${ boardDTO.num }">${ boardDTO.title }</A>
						</TD>
						<TD><A href="">${ boardDTO.author }</A></TD>
						<TD>${ boardDTO.writeday }</TD>
						<TD>${ boardDTO.readcnt }</TD>
					</TR>
				</c:forEach>
			</TBODY>
		</TABLE>
		<br/>
		<DIV>
			<INPUT type="button" value="전체" onclick="location.href='list.do'"/>
			<INPUT type="button" onclick="location.href='write.jsp'" value="글쓰기"/>
			<!-- <INPUT type="button" onclick="location.href='index.jsp'" value="메인화면"/> -->
			<FORM action="search.do" method="post">
				<SELECT name="searchType">
					<OPTION value="title">제목</OPTION>
					<OPTION value="author">이름</OPTION>
				</SELECT>
				<INPUT type="text" name="searchValue" placeholder="검색" />
				<INPUT type="submit" value="검색" />
			</FORM>
		</DIV>
	</DIV>
</body>
</html>