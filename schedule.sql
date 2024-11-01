
테이블 만들기
CREATE TABLE schedule(
  id BIGINT(11) NOT NULL AUTO_INCREMENT,
  title VARCHAR(20) NOT NULL,
  content VARCHAR(100) NOT NULL,
  numberid INT(25) NULL,
  newdata DATETIME NOT NULL,
  lastdate DATETIME NULL,
  password varchar(100) NULL,
  PRIMARY KEY(id)
);

일정 생성을 하는 query를 작성
insert into schedule (title,content,newdata)
values ('test','123','2024-01.02');

전체 일정을 조회하는 query를 작성
SELECT * from schedule
선택 일정을 조회하는 query를 작성

SELECT * from schedule
WHERE id =""

선택한 일정을 수정하는 query를 작성
UPDATE schedule
SET title = ""

선택한 일정을 삭제하는 query를 작성
DELETE
FROM schedule
WHERE id=1