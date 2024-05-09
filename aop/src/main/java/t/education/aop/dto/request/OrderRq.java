package t.education.aop.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrderRq {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String country;
    private String city;
    private String hotel;
    private Date checkInDate;
    private int restDuration;
    private String mealsType;
    private int adultsCount;
    private int childrenCount;
}
