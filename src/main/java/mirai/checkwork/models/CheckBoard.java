package mirai.checkwork.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "CHECK_BOARD")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CheckBoard {
    @Id
    @GeneratedValue
    @Column(name = "CHECK_ID")
    private Long id;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "CHECK_DATE", columnDefinition = "DATE")
    private LocalDate checkDate;

    @Column(name = "CHECK_IN_TIME", columnDefinition = "TIME")
    private LocalTime checkInTime;

    @Column(name = "CHECK_OUT_TIME", columnDefinition = "TIME")
    private LocalTime checkOutTime;
}
