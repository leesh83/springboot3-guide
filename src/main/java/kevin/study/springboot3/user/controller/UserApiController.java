package kevin.study.springboot3.user.controller;

import kevin.study.springboot3.user.dto.AddUserRequest;
import kevin.study.springboot3.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;

    @PostMapping("/user")
    //form-data 방식으로 request 객체에 매핑받으려면 request 객체에 setter 가 필요하다.
    public String signup(AddUserRequest request) {
        userService.save(request);
        return "redirect:/login";
    }

    // logout 은 스프링시큐리티에서 제공하기때문에 아래는 코드는 없어도 노상관 인듯..
//    @GetMapping("/logout")
//    public String logout(HttpServletRequest request, HttpServletResponse response) {
//        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
//        return "redirect:/login";
//    }
}
