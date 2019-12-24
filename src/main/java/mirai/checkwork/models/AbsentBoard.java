package mirai.checkwork.models;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
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

    @Temporal(TemporalType.DATE)
    @Column(name = "ABSENT_DATE")
    private Date absentDate;

    @Column(name = "ABSENT_SHIFT")
    private int shift;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", insertable = false, updatable = false)
    @Fetch(FetchMode.JOIN)
    private User user;
}
