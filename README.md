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
3. entity, repository, service, controller 클래스 생성
4. 구동 후 postnman 에서 데이터 확인

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
   
### 5. 블로그 기획하고 API만들기 





   
