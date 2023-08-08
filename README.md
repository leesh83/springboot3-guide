# springboot3-guide
스프링부트3 백엔드 개발자 가이드

```
이 프로젝트는 spring boot 3.1.2 / java17 버전을 사용하고 있습니다.
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
@AutoConfigureMockMvc
//MockMvc를 생성하고 구성해줌. MockMvc 는 컨트롤러를 테스트할때 사용
class MemberControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

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
        //인메모리 디비를 사용하므로 h2가 실행되어 있지않아도 테스트 성공

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
2. application.yml 에 H2 db 접속정보 추가
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
3. 구동 후 H2 실행, 브라우저에서 접속 (jdbc:h2:mem:testdb)
4. POSTMAN에서 POST API 실행, h2 db에 테이블생성 및 데이터 저장 확인   
![postman](https://github.com/ironmask431/springboot3-guide/assets/48856906/840f7c6c-9b5f-4300-a18d-f708b0d8da25)
![h2](https://github.com/ironmask431/springboot3-guide/assets/48856906/ac034e32-0d7f-4d4c-8699-433d20c941cf)






   
