# nbc-todo-list-server

<aside>
❓ **Why: 과제 제출시에는 아래 질문을 고민해보고 답변을 함께 제출해주세요.**

</aside>

1. 처음 설계한 API 명세서에 변경사항이 있었나요?
   변경 되었다면 어떤 점 때문 일까요? 첫 설계의 중요성에 대해 작성해 주세요! <br>
프로젝트를 새로 만들어서 변경한건 아니지만, 변경했다고 생각하고 답변하자면 API의 명세의 큰 틀(URI)은 달라지지 않았고 세부적인 값(요청, 응답)만 변경되었습니다.

2. ERD를 먼저 설계한 후 Entity를 개발했을 때 어떤 점이 도움이 되셨나요?<br>
Entity를 설계하고 연관관계를 설정하기 쉬웠습니다.

3. JWT를 사용하여 인증/인가를 구현 했을 때의 장점은 무엇일까요?<br>
Stateless인게 가장 큰 장점입니다. 서버가 상태를 저장/관리할 필요가 없습니다.

4. 반대로 JWT를 사용한 인증/인가의 한계점은 무엇일까요?<br>
토큰을 탈취당했을 때, 대처할 수 있는 방법이 없습니다. 그래서 access token, refreshtoken을 사용하면 결국 세션보단 덜하지만 상태를 갖게 됩니다. 그리고 payload가 커질수록 네트워크 비용이 커집니다.

5. 만약 댓글이 여러개 달려있는 할일을 삭제하려고 한다면 무슨 문제가 발생할까요?<br>
무결성 제약 조건에 위배됩니다.

6. Database 테이블 관점에서 해결방법이 무엇일까요?<br>
할일과 연관관계를 갖고 있는 댓글을 삭제하려고 한다면 데이터베이스 설정에 따라 댓글을 모두 삭제한 후에 할일을 삭제하거나, 자동으로 연관된 모든 댓글을 삭제하거나 합니다.

8. IoC / DI 에 대해 간략하게 설명해 주세요!<br>
IoC는 제어의 역전으로 개발자가 객체를 생성하고 사용하는게 아니라, 프레임워크에 객체의 라이프사이클 관리를 위임하고, 객체를 주입받아 사용하는 것을 말합니다.
DI는 IoC의 한 종류로, 생성자 주입, 필드 주입, 메서드 주입같은 방법으로 객체를 갖지 않고 주입받아 사용하는 것을 말합니다.

## ERD
<img width="735" alt="image" src="https://github.com/OuOHoon/nbc-todo-list-server/assets/17760465/c5878edb-0c89-41fa-b638-1c6bd5f3bce6">

## API 명세서
Swagger로 생성한 API 명세입니다. Jwt Authorize 설정은 추가하려고 찾아보는 중입니다 ㅎㅎ..
<img width="1461" alt="image" src="https://github.com/OuOHoon/nbc-todo-list-server/assets/17760465/2e2b21e0-cdfa-47ff-87b8-351bec68a26c">

<img width="1399" alt="image" src="https://github.com/OuOHoon/nbc-todo-list-server/assets/17760465/5e73d88b-bfe7-40b8-afb3-045a80916234">
