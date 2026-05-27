package cotato.backend.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "operator")
public class Operator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "operator_id")
    private Long id;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false, unique = true, length = 15)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OperatorRole role;

    @OneToMany(mappedBy = "operator", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    @Builder
    public Operator(String name, Integer age, String phoneNumber, OperatorRole role) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public void update(String name, Integer age, String phoneNumber, OperatorRole role) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }
}
