package kevin.study.springboot3.blog.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BlogViewController {

    @GetMapping("/articles")
    public String article() {
        return "articles";
    }
}
