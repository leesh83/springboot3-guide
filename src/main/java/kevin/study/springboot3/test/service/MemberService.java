package kevin.study.springboot3.test.service;

import kevin.study.springboot3.test.entity.Member;
import kevin.study.springboot3.test.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public List<Member> getAllMember() {
        return memberRepository.findAll();
    }
}
