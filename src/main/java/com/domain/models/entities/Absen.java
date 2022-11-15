package com.domain.models.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "absen")
@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class Absen implements Serializable {
    @Id
    private Long id;
    private String username;

    @Column(nullable = false, updatable = false)
    private Date coming_time;

    private Date home_time;

    @PrePersist
    protected void prePersist() {
        if (this.coming_time == null)
            coming_time = new Date();
        if (this.home_time == null)
            home_time = new Date();
    }

    @PreUpdate
    protected void preUpdate() {
        this.home_time = new Date();
    }

}
