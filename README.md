# 🗓️ Schedular 

### 📌 프로젝트 설명
***Spring Personal Task from the NBC***

개인 일정을 관리하는 스케쥴러의 백엔드 작성하기

### 📌 과제 진행 시 조건사항
*	일정 작성, 수정, 조회 시 반환 받은 일정 정보에 비밀번호는 제외
*	일정 수정, 삭제 시 선택한 일정의 비밀번호와 요청할 때 함께 보낸 비밀번호가 일치할 경우에만 가능 (비밀번호가 일치하지 않을 경우 적절한 오류 코드 및 메세지를 반환)
*	CRUD 필수 기능은 모두 데이터베이스 연결 및 JDBC 를 사용해서 개발
*	3 Layer Architecture 에 따라 각 Layer의 목적에 맞게 개발

---
<br></br>

## 📝 API 명세서
| 기능 | Method | URL | Request | Response | 상태 코드 |
|:---|:---|:---|:---|:---|:---|
| 일정 등록 | POST | http://localhost:8080/api/schedules |  | ID, Writer, Contents, Posting_date, Editing_date | 200(성공 상태) |
| 일정 조회 | GET | http://localhost:8080/api/schedules/id |  | ID, Writer, Contents, Posting_date, Editing_date  | 200(성공 상태) |
| 일정 목록 조회 | GET | http://localhost:8080/api/schedules?editing_date=date&writer=name |  | [ID, Writer, Contents, Posting_date, Editing_date]...[]  | 200(성공 상태) |
| 일정 수정 | PUT | http://localhost:8080/api/schedules/id | password | id | 200(성공 상태) |
| 일정 삭제 | DELETE | http://localhost:8080/api/schedules/id | password | id | 200(성공 상태) |

---
<br></br>

## 📅 ERD

![NBC Schedular (4)](https://github.com/user-attachments/assets/38cc181a-d410-4168-8779-ef71adb858b2)
