package mirai.checkwork.controllers;

import mirai.checkwork.common.AuthDetails;
import mirai.checkwork.common.RegisterRequest;
import mirai.checkwork.common.Role;
import mirai.checkwork.models.User;
import mirai.checkwork.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String index() {
        User user = ((AuthDetails) SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal())
            .getUser();

        if (user.getStatus() == 0) {
            SecurityContextHolder.clearContext();
            return "redirect:login?inactive=true";
        }

        switch (user.getRole()) {
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

    @GetMapping("/register")
    public String registerIndex() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute RegisterRequest req) {
        if (!req.getPassword().equals(req.getConfPassword())) {
            return "redirect:register?invalidPassword=true";
        }

        if (userRepository.findByUsername(req.getUsername()) != null) {
            return "redirect:register?invalidUsername=true";
        }

        User user = new User();
        user.setUsername(req.getUsername());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setName(req.getName());
        user.setRole(Role.ROLE_STAFF);
        user.setStatus(0);
        userRepository.save(user);

        return "redirect:login";
    }
}
