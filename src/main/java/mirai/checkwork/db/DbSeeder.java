package mirai.checkwork.db;

import mirai.checkwork.common.Role;
import mirai.checkwork.models.User;
import mirai.checkwork.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DbSeeder {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedUsersTable();
    }

    private void seedUsersTable() {
        if(userRepository.count() == 0) {
            User user = new User();
            user.setName("admin");
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRole(Role.ROLE_ADMIN);
            user.setStatus(1);
            userRepository.save(user);

            User user2 = new User();
            user2.setName("test");
            user2.setUsername("test");
            user2.setPassword(passwordEncoder.encode("123456"));
            user2.setRole(Role.ROLE_STAFF);
            user2.setStatus(1);
            userRepository.save(user2);
        }
    }
}
