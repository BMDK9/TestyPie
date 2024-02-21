package com.example.testypie.domain.category.entity;

import jakarta.persistence.*;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Entity
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column private String name;

    @JoinColumn(name = "parent_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category parent;

    @Column private Long depth;

    @Builder
    private Category(Long id, String name, Long depth, Category parent) {
        this.id = id;
        this.name = name;
        this.parent = parent;
        if (Objects.isNull(parent)) {
            this.depth = depth;
        } else {
            this.depth = depth + 1;
        }
    }
}
