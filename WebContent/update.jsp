<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>update</title>
<LINK rel="stylesheet" type="text/css" href="./css/retrieve.css"/>
</head>
<body>
	<DIV>
		<h1>글 수정</h1>
		<c:if test="${ dto != null }">
			<FORM action="update.do" method="post">
				<TABLE>
					<TR>
						<TD colspan="4">
							<INPUT class="updateForm" name="title" value="${ dto.title }" />
						</TD>
						<TD class="notUpdateForm" colspan="1">글번호</TD>
						<TD class="notUpdateForm" colspan="1">
							<INPUT class="notUpdateForm" name="num" value="${ dto.num }" style="border: 0;" />
						</TD>
					</TR>
					<TR>
						<TD class="notUpdateForm" colspan="1">작성자</TD>
						<TD class="notUpdateForm" colspan="1">${ dto.author }</TD>
						<TD class="notUpdateForm" colspan="1">조회수</TD>
						<TD class="notUpdateForm" colspan="1">${ dto.readcnt }</TD>
						<TD class="notUpdateForm" colspan="1">작성일</TD>
						<TD class="notUpdateForm" colspan="1">${ dto.writeday }</TD>
					</TR>
					<TR>
						<TD class="pageContent" colspan="6">
							<TEXTAREA class="updateForm" name="content">${ dto.content }</TEXTAREA>
						</TD>
					</TR>
					<TR>
						<TD class="notUpdateForm" colspan="2">${ dto.repRoot }</TD>
						<TD class="notUpdateForm" colspan="2">${ dto.repStep }</TD>
						<TD class="notUpdateForm" colspan="2">${ dto.repIndent }</TD>
					</TR>
				</TABLE>
				<DIV>
					<INPUT type="submit" value="수정" />
					<INPUT type="button" value="취소" onclick="location.href='retrieve.do?num=${ dto.num }'" />
				</DIV>
			</FORM>
		</c:if>
		<c:if test="${ dto == null }">
			잘못된 접근입니다.
			<INPUT type="button" onclick="location.href='list.do'" value="뒤로" />
		</c:if>
	</DIV>
</body>
</html>