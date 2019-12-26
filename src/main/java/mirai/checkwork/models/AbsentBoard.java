package mirai.checkwork.models;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "ABSENT_BOARD")
@Data
public class AbsentBoard {
    @Id
    @GeneratedValue
    @Column(name = "ABSENT_ID")
    private Long id;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "ABSENT_DATE", columnDefinition = "DATE")
    private LocalDate absentDate;

    @Column(name = "ABSENT_SHIFT")
    private int shift;
}
