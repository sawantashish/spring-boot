package com.spring.oauth2.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_role_xref")
public class UserRole implements Serializable {

    private Integer id;
    private User user;
    private Role role;

    @Id
    @Column(name = "id" , insertable = false, updatable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "user=" + user +
                ", role=" + role +
                '}';
    }
}
