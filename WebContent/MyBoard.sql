drop table myBoard;

select * from myBoard;

create table myBoard(
	num number(4) primary key,		--index
	author varchar2(20) not null,	--글쓴이
	title varchar2(50) not null,	--제목
	content varchar2(1000) not null,--내용
	writeday date default sysdate,	--날짜
	readcnt number(5) default 0,	--조회수
	repRoot number(4),				--원래글과 댓글을 붙어있게 하기 위한 정보
	repStep number(4),				--댓글에 대한 순서 지정을 위한 컬럼
	repIndent number(4)				--들여쓰기정보
);

insert into myBoard (num, author, title, content, readcnt, repRoot, repStep, repIndent) 
				values(1, '이순신', '난중일기', '임진왜란 중 적은 일기', 0, 1, 0, 0);





--BoardDAO.list()
select num,title,author,writeday,readcnt,repRoot,repStep,repIndent 
		from (select * from myboard order by writeday desc);
