package course.spring.rentacar.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "RATINGS", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "carId"})})
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
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
