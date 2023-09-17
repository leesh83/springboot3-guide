package kevin.study.springboot3.blog.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BlogViewController {

    @GetMapping("/article")
    public String article(){
        return "article";
    }
}
