package flaviodeangeelis.u5w2d5.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String name;
    private String surname;
    private String email;
    private String profileImg;
    @OneToOne(mappedBy = "user")
    private Device device;
}
