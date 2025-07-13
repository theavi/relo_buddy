    package com.rlb.api.model;

    import com.fasterxml.jackson.annotation.JsonIgnore;
    import jakarta.persistence.*;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.util.HashSet;
    import java.util.Set;

    @Data
    @Entity
    @Table(name = "role")
    public class Role {
        @Id
        private Integer id;

        private String name;

        @ManyToMany(mappedBy = "roles")
        @JsonIgnore
        private Set<User> users = new HashSet<>();

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Role)) return false;
            return id != null && id.equals(((Role) o).id);
        }

        @Override
        public int hashCode() {
            return getClass().hashCode();
        }
    }
