package kevin.study.springboot3.user.dto;

import lombok.*;

@Getter
@Setter //form-data 방식으로 매핑받으려면 setter 가 필요하다.
@ToString
public class AddUserRequest {
    private String email;
    private String password;
}
