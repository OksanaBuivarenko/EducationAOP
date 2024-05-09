package t.education.aop.dto.responce;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class TourOrderRs {
    private String country;

    private String city;

    private String hotel;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date checkInDate;

    private int restDuration;

    private String mealsType;

    private int adultsCount;

    private int childrenCount;

    private TouristRs tourist;
}
