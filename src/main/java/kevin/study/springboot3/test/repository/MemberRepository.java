package kevin.study.springboot3.test.repository;

import kevin.study.springboot3.test.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
