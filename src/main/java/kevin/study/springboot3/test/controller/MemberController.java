package kevin.study.springboot3.test.controller;

import kevin.study.springboot3.test.entity.Member;
import kevin.study.springboot3.test.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members")
    public List<Member> getAllMembers(){
        return memberService.getAllMember();
    }
}
