package entity.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Column(length = 32)
    private String username;
    @Column(nullable = false, length = 11, unique = true)
    private String mobile;
    @Column(nullable = false, length = 64)
    private String password;
    @Column
    @ElementCollection
    private Set<String> addresses;
}
