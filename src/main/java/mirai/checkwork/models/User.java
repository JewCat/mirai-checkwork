package mirai.checkwork.models;

import lombok.Data;
import mirai.checkwork.common.Role;

import javax.persistence.*;

@Entity
@Table(name = "USER")
@Data
public class User {
    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "USER_NAME")
    private String name;

    @Column(name = "USERNAME", nullable = false, unique = true)
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ROLE")
    private Role role;

    @Column(name = "STATUS")
    private int status;
}
