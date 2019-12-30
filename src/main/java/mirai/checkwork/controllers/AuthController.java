package mirai.checkwork.controllers;

import mirai.checkwork.common.AuthDetails;
import mirai.checkwork.common.Role;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    @GetMapping("/")
    public String index() {
        Role role = ((AuthDetails) SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal())
            .getUser()
            .getRole();

        switch (role) {
            case ROLE_ADMIN: return "redirect:admin";
            case ROLE_STAFF: return "redirect:check";
            default: return "";
        }
    }

    @GetMapping("/admin")
    public String indexAdmin() {
        return "admin/home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
