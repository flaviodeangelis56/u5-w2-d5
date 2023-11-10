package flaviodeangeelis.u5w2d5.entities;

import flaviodeangelis.u6w2d2.enumType.DeviceStatus;
import flaviodeangelis.u6w2d2.enumType.DeviceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    private DeviceType deviceType;
    @Enumerated(EnumType.STRING)
    private DeviceStatus deviceStatus;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
