## Spring boot
> 1. 스프링부트란?
> 2. 프로젝트 생성
> 3. RequestMapping, ResponseBody

### 스프링부트란?
- 자바의 웹 프레임워크로 기존 스프링 프레임워크에 톰캣 서버를 내장하고 여러 편의 기능들을 추가하여 꾸준한 인기를 누리고 있는 프레임 워크이다.
- 웹 프로그램을 쉽고 빠르게 만들어 주는 웹 프레임워크다.
  - 웹 프레임워크란?
  
    <img src="../md/images/image1.png" width="700px">

    - 예를 들어 쿠키나 세션 처리, 로그인/ 로그아웃 처리, 권한 처리, 데이터베이스 처리 등 웹 프로그램을 위해 만들어야 할 기능이 전말 산더미처럼 많다.
    - 하지만 웹 프레임워크를 사용하면 이런 기능들을 일일이 만들 필요가 없다.
    - 웹 프레임워크에는 그런 기능들이 이미 만들어져 있기 때문이다.
    - 그저 웹 프레임워크에 있는 기능을 익혀서 사용하기만 하면 된다.
    - 웹 프레임워크는 웹ㅈ 프로그램을 만들기 위한 스타터 키트라고 생각하면 된다.
    - 자바로 만들어진 웹 프레임워크 중 하나가 스프링 부트이다.
- 스프링부트 설정
  - 스프링부트는 스프링의 복잡한 설정을 자동화 하고 단순화 하여 누구나 스프링을 쉽게 사용할 수 있게 만들었다.
---
### 프로젝트 생성
- New Project
  - Generators -> Spring Boot 선택
    - 첫 학습으로 Dependencies는 `Spring Web`, `Spring Boot DevTools`, `Lombok`, `H2 Database`, `Spring Data JPA`선택
      - Spring Web
        - 웹을 더 편리하고 빠르게 만들 수 있으며 다양한 기능과 도구를 제공하여 개발자의 부담을 덜어주는 도구
      - Spring Boot DevTools
        - 소스 변경이 발생할 때마다 빠르게 자동 빌드해줘서 바로바로 반영 결과를 확인할 수 있도록 도와주는 유용한 툴
        ```java
        - 설정
        Build, Exeution, Deployment > Compiler > Build project autiomaically 체크
        Advanced Settings > Allow auto-make to start even if developed application is currently running 체크
        ```
      - Lombok
        - Settings 내 plugins lombok 설치(기본적으로 설치가 되어있으나 다시 확인)
        - 코드를 자동완성 해주는 라이브러리
        - Getter, Setter, Equlas, ToString, 생성자 등 코드를 자동완성 시킬수 있다.
      - Spring Data JPA
        - JPA를 사용하여 데이터베이스를 처리한다.
        - JPA는 자바 진영에서 ORM(Object-Relational Mapping)의 기술 표준으로 사용하는 인터페이스의 모음
        ```java
        //JPA LOG
        spring.jpa.show-sql=true    //콘솔창에 sql 보여주기

        //JPA
        spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect
        spring.jpa.hibernate.ddl-auto=update    //테이블 자체 업데이트
        ```
      - H2 Database
        - 주로 개발용이나 소규모 프로젝트에서 사용되는 파일 기반의 경량 데이터베이스이다.
        - H2를 사용하여 빠르게 개발하고 실제 운영시스템은 좀 더 규모있는 DB를 사용하는 것이 일반적인 개발 패턴
        ```java
        application.properties
        spring.h2.console.enabled=true
        spring.h2.console.path=/h2-console    //url 주소
        spring.datasource.url=jdbc:h2:C:/java/local   //jdbc:h2: 뒤에 local.mv.db 파일 있는 경로 작성
        spring.datasource.driverClassName=org.h2.Driver
        spring.datasource.username=sa
        spring.datasource.password=
        ```
---

### RequestMapping, ResponseBody
- RequestMapping은 '특정한 경로의 요청을 지정'하기 위해 사용된다.
- 컨트롤러 클래스의 선언부에도 사용할 수 있고, 컨트롤러의 메소드에도 사용할 수 있다.
- @Controller 어노테이션 사용이 필수!!!
- ResponseBody은 단순 메서드의 응답 결과가 문자열 그 자체임을 나타낸다.
```java
    @RequestMapping("/hello")   //localhost8080/hello
    @ResponseBody   // 기본 MVC는 컨트롤러가 화면을 전달하는데 여기서는 바로 표시
    public String hello() {
        return "Hello World";
    }
```

