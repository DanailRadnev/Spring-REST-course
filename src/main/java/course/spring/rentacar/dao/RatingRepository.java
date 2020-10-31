package course.spring.rentacar.dao;

import course.spring.rentacar.model.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByCarId(Long carId);

    @Query("SELECT AVG(r.rating) FROM Rating r WHERE r.carId = :carId")
    Double getCarRating(@Param("carId") Long carId);
}
