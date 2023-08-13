package kevin.study.springboot3.blog.dto;


import kevin.study.springboot3.blog.domain.Article;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ArticleResponse {
    private final String title;
    private final String content;

    @Builder
    public ArticleResponse(Article article){
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}
