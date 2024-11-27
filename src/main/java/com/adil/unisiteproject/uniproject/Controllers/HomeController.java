package com.adil.unisiteproject.uniproject.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping({"", "/"})
    public String home(){
        return  "index";
    }

    @GetMapping("/contact")
    public String contact(){
        return  "contact";
    }

}
