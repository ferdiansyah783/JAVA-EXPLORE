package com.domain.models.entities;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class User implements UserDetails {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "name", length = 100, nullable = false)
        private String name;

        @Column(name = "username", length = 100, nullable = false)
        private String username;

        @Column(name = "email", length = 100, nullable = false)
        private String email;

        @Column(name = "password", length = 100, nullable = false)
        private String password;

        @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
        private List<Hobi> hobies;

        @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
        private List<Role> roles;

        @ManyToMany(fetch = FetchType.LAZY, cascade = {
                        CascadeType.PERSIST,
                        CascadeType.MERGE
        })
        @JoinTable(name = "user_projects", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
                        @JoinColumn(name = "project_id") })
        private List<Project> projects;

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(username);
                return Collections.singletonList(authority);
        }

        @Override
        public String getUsername() {
                return username;
        }

        @Override
        public String getPassword() {
                return password;
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
