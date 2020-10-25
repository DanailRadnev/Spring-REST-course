package course.spring.rentacar.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Entity
@Table(name = "USERS")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
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
    private String email;

    @NonNull
    @NotNull
    @Size(min=3, max = 30)
    private String username;

    @JsonProperty(access = WRITE_ONLY)
    @NonNull
    @NotNull
    @Size(min=5)
    private String password;

    private boolean active = true;


}
