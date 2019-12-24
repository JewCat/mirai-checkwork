package mirai.checkwork.models;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
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

    @Temporal(TemporalType.DATE)
    @Column(name = "CHECK_DATE")
    private Date checkDate;

    @Temporal(TemporalType.TIME)
    @Column(name = "CHECK_IN_TIME")
    private Date checkInTime;

    @Temporal(TemporalType.TIME)
    @Column(name = "CHECK_OUT_TIME")
    private Date checkOutTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", insertable = false, updatable = false)
    @Fetch(FetchMode.JOIN)
    private User user;
}
