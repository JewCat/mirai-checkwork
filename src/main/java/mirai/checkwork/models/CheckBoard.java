package mirai.checkwork.models;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "CHECK_BOARD")
@Data
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
