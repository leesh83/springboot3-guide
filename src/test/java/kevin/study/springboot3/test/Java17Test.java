package kevin.study.springboot3.test;

import org.junit.jupiter.api.Test;

public class Java17Test {

    @Test
    public void textBlockTest() {
        String textBlock = """
                select * from table
                where status = on_sale
                order by price;
                """;
        System.out.println(textBlock);
    }

    @Test
    public void formattedTest() {
        String formatted = """
                {
                    id : %d
                    name : %s
                }
                """.formatted(2, "juice");

        System.out.println(formatted);
    }

    @Test
    public void recordTest() {
        Beverge juice = new Beverge("juice", 3000);
        System.out.println("juice name = %s".formatted(juice.name()));
        System.out.println("juice price = %d".formatted(juice.price()));
    }
}
