package kevin.study.springboot3.test.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", updatable = false)
    private Long id;

    @Column(name="name", nullable = false)
    private String name;


    //엔티티는 반드시 기본생성자가 있어야 하며 접근 제어자는 public, protected 만 가능하다.
    //public 보다는 protected가 안전하다.
    protected Member() {
    }

    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
