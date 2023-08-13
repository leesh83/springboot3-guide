package kevin.study.springboot3.blog.dto;


import kevin.study.springboot3.blog.domain.Article;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AddArticleRequest {
    private String title;
    private String content;

    public Article toEntity(){
        return Article.builder()
                      .title(title)
                      .content(content)
                      .build();
    }

    @Builder
    public AddArticleRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
