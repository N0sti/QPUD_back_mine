package com.takima.backskeleton.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "theme")
@Getter
@Setter
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    // Private constructor for the builder pattern
    private Theme(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
    }

    // Default constructor for JPA
    public Theme() {}

    // Constructor for creating Theme with only a name
    public Theme(String themeName) {
        this.name = themeName;
    }

    // Case-insensitive comparison of theme names
    public boolean hasNameIgnoreCase(String otherName) {
        return otherName != null && this.name.equalsIgnoreCase(otherName.trim());
    }

    // Override equals to compare Theme objects based on id and name
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Theme theme = (Theme) o;
        return Objects.equals(id, theme.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Theme{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    // Builder class for constructing Theme instances
    public static class Builder {
        private Long id;
        private String name;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Theme build() {
            if (name == null) {
                throw new IllegalArgumentException("Theme name cannot be null");
            }
            return new Theme(this);
        }
    }
}
