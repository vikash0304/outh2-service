package com.uniken.outh2_service.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String root() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    public String home(Authentication authentication, Model model) {
        if (authentication != null) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof OAuth2User oAuth2User) {
                // Google OAuth2 login
                model.addAttribute("username", oAuth2User.getAttribute("name"));
                model.addAttribute("email", oAuth2User.getAttribute("email"));
                model.addAttribute("picture", oAuth2User.getAttribute("picture"));
                model.addAttribute("loginMethod", "Google OAuth2");
            } else if (principal instanceof UserDetails userDetails) {
                // Form-based login
                model.addAttribute("username", userDetails.getUsername());
                model.addAttribute("email", "");
                model.addAttribute("picture", "");
                model.addAttribute("loginMethod", "Form Login");
            }
        }
        return "home";
    }
}
