drop table myBoard;

create table myBoard(
	num number(4) primary key,		--index
	author varchar2(20) not null,	--�۾���
	title varchar2(50) not null,	--����
	content varchar2(1000) not null,--����
	writeday date default sysdate,	--��¥
	readcnt number(5) default 0,	--��ȸ��
	repRoot number(4),				--�����۰� ����� �پ��ְ� �ϱ� ���� ����
	repStep number(4),				--��ۿ� ���� ���� ������ ���� �÷�
	repIndent number(4)				--�鿩��������
);

select * from myBoard;

insert into myBoard (num, author, title, content, readcnt, repRoot, repStep, repIndent) 
				values(1, '�̼���', '�����ϱ�', '�����ֶ� �� ���� �ϱ�', 0, 1, 0, 0);

--num, author, title, content, writeday, readcnt, repRoot, repStep, repIndent
select * from (
	select myboard.*, rownum as rnum from (
		select num,author,title,content,writeday,readcnt,repRoot,repStep,repIndent 
			from myboard order by repRoot desc, repStep asc) myboard
		where rownum<11 ) 
where 5<rnum

--BoardDAO.list()
select num,title,author,writeday,readcnt,repRoot,repStep,repIndent 
		from (select * from myboard order by writeday desc);


select count(*) from myboard;