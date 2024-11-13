package com.takima.backskeleton.models;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "users")
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "is_admin", nullable = false)
    private boolean isAdmin;

    // Constructeur par défaut
    public User() {}

    // Constructeur avec paramètres
    public User(String username, boolean isAdmin) {
        this.username = username;
        this.isAdmin = isAdmin;
    }

    // Constructeur privé pour le Builder
    private User(Builder builder) {
        this.id = builder.id;
        this.username = builder.username;
        this.isAdmin = builder.isAdmin;
    }

    // Builder pour la création d'instances User
    public static class Builder {
        private Long id;
        private String username;
        private boolean isAdmin;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder isAdmin(boolean isAdmin) {
            this.isAdmin = isAdmin;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
