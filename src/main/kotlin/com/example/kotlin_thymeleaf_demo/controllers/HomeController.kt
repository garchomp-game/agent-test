package com.example.kotlin_thymeleaf_demo.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController {

    @GetMapping("/")
    fun home(model: Model): String {
        model.addAttribute("message", "Kotlinとthymeleafを使ったデモアプリケーションへようこそ！")
        return "home"
    }
}
