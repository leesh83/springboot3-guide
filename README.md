# springboot3-guide
스프링부트3 백엔드 개발자 가이드

```
이 프로젝트는 spring boot 3.0.2 / java17 버전을 사용하고 있습니다.
```


### 2장 스프링부트3 시작하기
- 스프링부트3 : java 17 버전 사용 필수

- java 17 주요변화    
- `Java17Test.java`
1. 텍스트 블록
```java
 String test = """
            select * from table
            where status = on_sale
            order by price;
            """;
```
2. formatted() 메소드
```java
String formatted = """
            {
                id : %d
                name : %s
            }
            """.formatted(2, "juice");
```
3. 레코드
- 레코드는 데이터전달을 목적으로 객체를 빠르고 간편하게 만들어줌.   
- 레코드는 상속을 할 수 없고, 파라미터에 정의된 필드는 private final로 정의됨. getter 자동생성   
- `Beverge.record`
```java 
public record Beverge(String name, int price) {
    //name, price 파라미터가 private final 로 정의됩니다.
}

Beverge juice = new Beverge("juice",3000);
juice.price() // 3000 
```
4. switch - case 문에서 매개변수의 자료형으로 case 분류가능.

### 3. 스프링부트3 구조 이해하기
1. 인메모리 DB 에 더미데이터 입력 -> `resources/data.sql`
2. `resources/application.yml` 에 인메모리 db 관련 설정
```java
spring:
  jpa:
    #콘솔에 SQL 출력
    show-sql: true
    properties:
      hibernate:
        format_sql: true
    # 테이블 생성 후 data.sql 실행
    defer-datasource-initialization: true
```
4. entity, repository, service, controller 클래스 생성
5. 구동 후 postnman 에서 데이터 확인

### 4. 스프링부트3 와 테스트
1. assertThat 메소드들   
- `JunitTest.java`
```java
        boolean flag = true;
        assertThat(flag).isEqualTo(true);
        assertThat(flag).isNotEqualTo(false);

        List list = Arrays.asList("송하영","박지원");
        assertThat(list).contains("송하영");
        assertThat(list).doesNotContain("이나경");
        assertThat(list).isNotEmpty();

        String str = "Promise";
        assertThat(str).startsWith("P");
        assertThat(str).endsWith("e");

        List emptyList = new ArrayList();
        assertThat(emptyList).isEmpty();

        int a = 15;
        assertThat(a).isPositive();
        int b = -15;
        assertThat(b).isNegative();

        assertThat(a).isGreaterThan(10);
        assertThat(a).isLessThan(20);
```
2. MockMvc 를 이용하여 api 테스트
- MockMvc를 생성하고 RestController api 검증
- mockMvc.perform(), resultAction.andExpect() 사용
- `MemberControllerTest.java`
```java
@SpringBootTest
//@SpringBootApplication 이 있는 클래스 기준 빈들을 생성한다음 테스트용 애플리케이션 컨텍스트 만듬.
//spring context에 등록된 빈 객체들을 테스트에서 사용하려면 이 어노테이션이 필요하다.
@AutoConfigureMockMvc
//MockMvc를 생성하고 구성해줌. MockMvc 는 컨트롤러를 테스트할때 사용
class MemberControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;
    //mockMvc를 초기화 할때 필요함.

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    public void mockMvcSetUp(){
        //mockMvc 세팅
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                                      .build();
    }

    @AfterEach
    public void cleanUp(){
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("회원 조회 api 검증")
    void getAllMembers() throws Exception{
        //given
        final String url = "/members";
        Member savedMember = memberRepository.save(new Member(1L, "이나경"));
        //h2 database dependencies를 추가하였기 때문에 인메모리 DB 저장 테스트 가능.

        //when
        final ResultActions resultActions =
            mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));
        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(savedMember.getId()))
                .andExpect(jsonPath("$[0].name").value(savedMember.getName()));
    }
}
```

   
### 5. 블로그 기획하고 API만들기 

1. domain(entity), requestDto, repository, service, restController 생성
2. contorller 메소드의 반환 타입을 ResponseEntity로 감싸서 reponse 의 httpStatus를 설정할 수 있다.
```java
public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request){
        Article savedArticle = blogService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(savedArticle);
}
```
3. application.yml 에 H2 db 접속정보 추가
```java
spring:
  # h2 DB 접속정보
  datasource:
    url: jdbc:h2:mem:testdb

  # h2 DB툴을 브라우저에서 사용가능
  h2:
    console:
      enable: true
```
3. 구동 후 H2 실행, 브라우저에서 접속
```
h2 브라우저 콘솔 접속 시 주의점.
구동된 서버와 동일한 localhost:port/h2-console 로 접속해서 로그인해야함.
ex) localhost:8080/h2-console
```
5. POSTMAN에서 POST API 실행, h2 db에 테이블생성 및 데이터 저장 확인   
![postman](https://github.com/ironmask431/springboot3-guide/assets/48856906/840f7c6c-9b5f-4300-a18d-f708b0d8da25)
![h2](https://github.com/ironmask431/springboot3-guide/assets/48856906/ac034e32-0d7f-4d4c-8699-433d20c941cf)

6. api(controller) 테스트코드 작성
7. ObjectMapper 를 이용해 java객체 직렬화 (api요청 시 request 객체를 JSON타입 String으로 직렬화하여 request body(content)에 넣음)
```java
final AddArticleRequest request = AddArticleRequest.builder()
                .title(title)
                .content(content)
                .build();

//request 객체를 String (JSON 형태)으로 직렬화 
final String requestBody = objectMapper.writeValueAsString(request);

//when
ResultActions result = mockMvc.perform(post(url)
                               .contentType(MediaType.APPLICATION_JSON)
                               .content(requestBody));
```
8. blog CRUD Api 생성 + 테스트코드 완료

   
### 7. 스프링 시큐리티로 로그인,로그아웃 회원가입 만들기

1. 사전지식
* 스프링 시큐리티는 스프링키반의 애플리케이션 보안(인증,인가,권한)을 담당하는 스프링 하위 프레임워크이다.
* 인증(authentication) : 로그인시 사용자 확인 /  인가(authorization) : 사용자의 권한 확인
* 스프링시큐리티는 다음 공격을 방어 가능
* CSRF공격 : 사용자의 권한을 가지고 특정 동작 유도, 세션고정공격 : 사용자의 인증정보 탈취, 변조
* 스프링시큐리티는 다양한 필터구조로 동작   

4. build.gradle 의존성 추가

```java
implementation 'org.springframework.boot:spring-boot-starter-security'
testImplementation 'org.springframework.security:spring-security-test'
```
2. 스프링시큐리티 UserDetails를 implements 한 User 엔티티 만들기 > `User.java`
3. UserRepository 인터페이스 생성 > `UserRepository`
4. 스프링시큐리티 UserDetailsService 를 구현한 UserDetailService 생성 > `UserDetailService.java`
5. 스프링시큐리티 설정 파일 생성 > `WebSecurityConfig.java`
* 이 파일은 스프링부트 버전별로 각각 다른 부분들이 있다. 이프로젝트는 부트 `3.0.2` 기준!
* `configure()` > 시큐리티 기능 비활성화 할 경로 지정 (로그인,회원가입페이지 + 정적리소스 파일 경로)
* `filterChain()`  > url별, 권한별 인증, 인가 설정, 로그인, 로그아웃 후 이동할 url 지정
* `authenticationManager()` > 인증관리자 관련 설정 : 사용자정보를 가져올 서비스지정. 패스워드 인코더 지정
* 패스워드 인코더를 Bean으로 등록

7. 회원가입을 처리할 `AddUserRequest.java`, `UserService.java`, `UserApiController.java` 생성
8. 회원가입 뷰 컨트롤러, 뷰 파일 생성, `UserViewController.java`, `login.html`, `signup.html`, `articles.html`   
9. 회원가입, 로그인, 로그아웃, 로그아웃 후 인가 필요한 페이지 접근 시 login.html 로 redirect 확인
![로그인](https://github.com/ironmask431/springboot3-guide/assets/48856906/36137eb5-1d53-4273-982b-846b31530117)
![회원가입](https://github.com/ironmask431/springboot3-guide/assets/48856906/a63bc89e-deed-471e-9826-9e99619ae8d4)
![로그인성공](https://github.com/ironmask431/springboot3-guide/assets/48856906/4dee8e64-6227-438c-be13-018a0eaefc27)


   
