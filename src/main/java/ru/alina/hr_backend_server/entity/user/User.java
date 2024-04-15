package ru.alina.hr_backend_server.entity.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.alina.hr_backend_server.entity.profile.Profile;

import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 150)
    private String email;
    @Column(length = 25)
    private String password;


    @ManyToOne(cascade = CascadeType.ALL)
    private Profile profile;

    public User(String email, String password, Profile profile) {
        this.email = email;
        this.password = password;
        this.profile = profile;
        this.role = Role.USER;
    }

    @Enumerated(EnumType.STRING)
    private Role role;

    @Transient
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Transient
    @Override
    public String getUsername() {
        return email;
    }

    @Transient
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Transient
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Transient
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Transient
    @Override
    public boolean isEnabled() {
        return false;
    }
}
