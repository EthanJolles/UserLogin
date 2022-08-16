package com.jolles.userlogin.model;

import com.jolles.userlogin.enums.AppUserRole;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class AppUser implements UserDetails {

    @SequenceGenerator(
            name = "appUser_sequence",
            sequenceName = "appUser_sequence",
            allocationSize = 1)
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "appUser_sequence")
    private Long id;
    @Column
    private String name;
    @Column
    private String username;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    @Enumerated(EnumType.STRING)
    private AppUserRole appUserRole;
    @Column
    private Boolean locked;
    @Column
    private Boolean enabled;

    public AppUser(String name,
                   String username,
                   String email,
                   String password,
                   AppUserRole appUserRole,
                   Boolean locked,
                   Boolean enabled) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.appUserRole = appUserRole;
        this.locked = locked;
        this.enabled = enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority auth = new SimpleGrantedAuthority(this.appUserRole.name());
        return Collections.singletonList(auth);
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    //Care later
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
