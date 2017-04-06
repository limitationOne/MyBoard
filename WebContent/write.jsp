<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>write</title>
<LINK rel="stylesheet" type="text/css" href="./css/write.css"/>
</head>
<body>
	<DIV>
		<H1>글쓰기</H1>
		<FORM action="write.do" method="post">
			<INPUT name="author" type="text" placeholder="작성자"/><br/>
			<INPUT name="title" type="text" placeholder="제목"/><br/>
			<TEXTAREA name="content" placeholder="내용"></TEXTAREA><br/>
			<INPUT name="repRoot" type="hidden" value="0"/><br/>
			<INPUT type="submit" value="등록하기" />
		</FORM>
		<INPUT type="button" value="취소" onclick="location.href='list.do'" />
	</DIV>
</body>
</html>