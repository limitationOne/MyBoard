<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>retrieve</title>
<LINK rel="stylesheet" type="text/css" href="./css/retrieve.css"/>
</head>
<body>
	<DIV>
		<H1>글 보기</H1>
		<c:if test="${ dto != null }">
			<TABLE>
				<TR>
					<TD colspan="4">${ dto.title }</TD>
					<TD colspan="1">글번호</TD>
					<TD colspan="1">${ dto.num }</TD>
				</TR>
				<TR>
					<TD colspan="1">작성자</TD>
					<TD colspan="1">${ dto.author }</TD>
					<TD colspan="1">조회수</TD>
					<TD colspan="1">${ dto.readcnt }</TD>
					<TD colspan="1">작성일</TD>
					<TD colspan="1">${ dto.writeday }</TD>
				</TR>
				<TR>
					<TD class="pageContent" colspan="6">${ dto.content }</TD>
				</TR>
				<TR>
					<TD colspan="2">${ dto.repRoot }</TD>
					<TD colspan="2">${ dto.repStep }</TD>
					<TD colspan="2">${ dto.repIndent }</TD>
				</TR>
			</TABLE>
			<INPUT type="button" value="목록" onclick="location.href='list.do?num=1'"/>
			<INPUT type="button" value="수정" onclick="location.href='updateUI.do?num=${dto.num}'"/>
			<INPUT type="button" value="삭제" onclick="location.href='delete.do?num=${dto.num}'"/>
			<INPUT type="button" value="답글" onclick="location.href='replyUI.do?num=${dto.num}'"/>
		</c:if>
		<c:if test="${ dto == null }">
			잘못된 접근입니다.
			<INPUT type="button" onclick="location.href='list.do'" value="뒤로" />
		</c:if>
	</DIV>
</body>
</html>