package example.org.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // 컨트롤러를 json을 반환하는 컨트롤러로 만들어준다. @ResponseBody를 각 메서드에 붙여주는것과 같은 기능을 하는 것이다.
public class HelloController {

    @GetMapping("/hello")
    public String HelloController(){
        return "hello";
    }
}
