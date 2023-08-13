package kevin.study.springboot3.blog.controller;


import kevin.study.springboot3.blog.domain.Article;
import kevin.study.springboot3.blog.dto.AddArticleRequest;
import kevin.study.springboot3.blog.dto.ArticleResponse;
import kevin.study.springboot3.blog.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BlogApiController {
    private final BlogService blogService;

    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request){
        Article savedArticle = blogService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(savedArticle);
        //반환 타입을 ResponseEntity로 감싸서 reponse 의 httpStatus를 설정할 수 있다.
    }

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles(){
        List<ArticleResponse> list = blogService.findAll().stream()
                                                .map(ArticleResponse::new)
                                                .toList();
        return ResponseEntity.ok()
                             .body(list);
    }

}
