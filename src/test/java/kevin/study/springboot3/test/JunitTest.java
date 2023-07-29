package kevin.study.springboot3.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class JunitTest {

    @Test
    @DisplayName("assertThat 테스트")
    public void testAssertThat(){
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

    }
}
