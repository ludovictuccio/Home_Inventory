package com.home.inventory.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.home.inventory.util.Constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(unique = true)
    @NotBlank(message = "Username is mandatory")
    private String username;

    @Pattern(regexp = Constants.PASSWORD_PATTERN, message = "The password must include a digit, an uppercase and lowercase letter and a special character, without space.")
    @Column(name = "password")
    @NotBlank(message = "Password is mandatory")
    private String password;

    @Column(name = "fullname")
    @NotBlank(message = "FullName is mandatory")
    private String fullname;

    @Column(name = "role")
    @NotBlank(message = "Role is mandatory")
    private String role;

    public User(
            @NotBlank(message = "Username is mandatory") final String usernameUser,
            @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*-])(?=\\S+$).{8,}$", message = "The password must include a digit, an uppercase and lowercase letter and a special character, without space.") @NotBlank(message = "Password is mandatory") final String passwordUser,
            @NotBlank(message = "FullName is mandatory") final String fullnameUser,
            @NotBlank(message = "Role is mandatory") final String roleUser) {
        super();
        this.username = usernameUser;
        this.password = passwordUser;
        this.fullname = fullnameUser;
        this.role = roleUser;
    }

}
