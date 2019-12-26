package mirai.checkwork.repositories;

import mirai.checkwork.models.AbsentBoard;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface AbsentBoardRepository extends JpaRepository<AbsentBoard, Long> {
    @Query("SELECT absb FROM AbsentBoard absb WHERE absb.userId = ?2 AND absb.absentDate >= ?1")
    List<AbsentBoard> getListAfterDateByUserLimit(LocalDate date, Long userId, Pageable pageable);

    @Query("SELECT absb FROM AbsentBoard absb WHERE absb.userId = ?2 AND absb.absentDate >= ?1")
    List<AbsentBoard> getListAfterDateByUser(LocalDate date, Long userId);
}
