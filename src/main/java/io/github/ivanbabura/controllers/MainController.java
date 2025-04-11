package io.github.ivanbabura.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping("/")
    public String index1() {
        return "index";
    }

    @GetMapping("/index")
    public String index2() {
        return "index";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }

    // вот ↓ это ↓ под вопросом, мб как-то по-другому надо
    @GetMapping("/crudScripts.js")
    public String selectRbt() {
        return "crudScripts.js";
    }
}
