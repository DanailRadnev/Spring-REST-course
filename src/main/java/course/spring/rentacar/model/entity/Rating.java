package course.spring.rentacar.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "RATINGS", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "carId"})})
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"user"})
public class Rating {

    @Id
    @GeneratedValue(generator = "rating_sequence", strategy = GenerationType.SEQUENCE)
    private Long id;

    @NonNull
    @NotNull
    private Long carId;

    @NonNull
    @NotNull
    private Long rating;

    @ManyToOne
    private User user;
}
