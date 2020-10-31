package course.spring.rentacar.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Entity
@Table(name = "USERS")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"cars"})
public class User {
    @Id
    @GeneratedValue(generator = "user_sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 3
    )
    private Long id;

    @NonNull
    @NotNull
    @Size(min=2, max = 50)
    private String firstName;

    @NonNull
    @NotNull
    @Size(min=2, max = 50)
    private String lastName;

    @NonNull
    @NotNull
    @Email
    @Column(nullable = false)
    private String email;

    @NonNull
    @NotNull
    @Size(min=3, max = 30)
    @Column(unique = true, nullable = false)
    private String username;

    @JsonProperty(access = WRITE_ONLY)
    @NonNull
    @NotNull
    @Size(min=5)
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Car> cars;

    @OneToMany(mappedBy = "user")
    private List<Rating> ratings;

    private boolean active = true;

}
