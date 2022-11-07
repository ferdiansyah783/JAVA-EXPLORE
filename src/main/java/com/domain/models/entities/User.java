package com.domain.models.entities;

import java.io.Serializable;
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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class User implements Serializable {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "name", length = 100, nullable = false)
        private String name;

        @Column(name = "email", length = 100, nullable = false)
        private String email;

        @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
        private List<Hobi> hobies;

        @ManyToMany(fetch = FetchType.LAZY, cascade = {
                        CascadeType.PERSIST,
                        CascadeType.MERGE
        })
        @JoinTable(name = "user_roles", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
                        @JoinColumn(name = "role_id") })
        private List<Role> roles;
}
