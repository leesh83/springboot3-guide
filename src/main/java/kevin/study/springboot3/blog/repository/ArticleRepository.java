package kevin.study.springboot3.blog.repository;

import kevin.study.springboot3.blog.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
