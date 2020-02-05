package entity.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true, length = 32)
    private String username;
    @Column(nullable = false, length = 64)
    private String password;
    @Column
    @ElementCollection
    private Set<String> addresses;
}
