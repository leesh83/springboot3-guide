package kevin.study.springboot3.blog.service;


import kevin.study.springboot3.blog.domain.Article;
import kevin.study.springboot3.blog.dto.AddArticleRequest;
import kevin.study.springboot3.blog.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;

    public Article save(AddArticleRequest request){
        return blogRepository.save(request.toEntity());
    }

    public List<Article> findAll(){
        return blogRepository.findAll();
    }




}
