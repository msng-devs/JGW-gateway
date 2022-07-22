package com.jaramgroupware.jaramgateway.domain.userDetails;

import com.jaramgroupware.jaramgateway.domain.major.Major;
import com.jaramgroupware.jaramgateway.domain.member.Member;
import com.jaramgroupware.jaramgateway.domain.rank.Rank;
import com.jaramgroupware.jaramgateway.domain.role.Role;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
public class CustomUser implements UserDetails {

    @Id
    private String id;

    @Column
    private String rank;

    @Column
    private String role;

    public CustomUser(Member member){
        id = member.getId();
        rank = member.getRank().getName();
        role = member.getRole().getName();

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
        auth.add(new SimpleGrantedAuthority(role));
        return auth;

    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
