package com.home.inventory.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.home.inventory.util.Constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements Serializable {

    private static final long serialVersionUID = 3912523952803094237L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(unique = true)
    @NotBlank(message = "Username is mandatory")
    private String username;

    @Column(unique = true, name = "email")
    @NotBlank(message = "Email is mandatory")
    @Email
    private String email;

    @Pattern(regexp = Constants.PASSWORD_PATTERN, message = "The password must include a digit, an uppercase and lowercase letter and a special character, without space.")
    @Column(name = "password")
    @NotBlank(message = "Password is mandatory")
    private String password;

    @Column(name = "role")
    @NotBlank(message = "Role is mandatory")
    private String role;

    public User(@NotBlank(message = "Username is mandatory") String username,
            @NotBlank(message = "Email is mandatory") @Email String email,
            @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*-])(?=\\S+$).{8,}$", message = "The password must include a digit, an uppercase and lowercase letter and a special character, without space.") @NotBlank(message = "Password is mandatory") String password,
            @NotBlank(message = "Role is mandatory") String role) {
        super();
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

}
