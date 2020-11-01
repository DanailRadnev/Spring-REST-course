package course.spring.rentacar.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@Table(name = "CARS")
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"ratings", "user"})
public class Car {

    @Id
    @GeneratedValue(generator = "car_sequence", strategy = GenerationType.SEQUENCE)
    private Long id;

    @NonNull
    @NotNull
    @Size(min=3, max = 64)
    private String make;

    @NonNull
    @NotNull
    @Size(min=3, max = 64)
    private String model;

    @NonNull
    @NotNull
    @Size(min=8, max = 8)
    private String licensePlane;

    @NonNull
    @NotNull
    @Size(min = 3, max = 32)
    private String colour;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Transient
    private Double rating;

    private boolean available = true;

    private boolean dirty = false;
    private boolean broke = false;
}
