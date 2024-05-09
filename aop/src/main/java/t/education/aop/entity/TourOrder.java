package t.education.aop.entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsExclude;
import org.apache.commons.lang3.builder.HashCodeExclude;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tour_orders")
public class TourOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String country;

    private String city;

    private String hotel;

    @Column(name = "check_in_date", nullable = false)
    private Date checkInDate;

    @Column(name = "rest_duration", columnDefinition = "integer default 7")
    private int restDuration;

    @Column(name = "meals_type", nullable = false)
    private String mealsType;

    @Column(name = "adult_count", columnDefinition = "integer default 2")
    private int adultsCount;

    @Column(name = "child_count", columnDefinition = "integer default 0")
    private int childrenCount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tourist_id")
    private Tourist tourist;
}
