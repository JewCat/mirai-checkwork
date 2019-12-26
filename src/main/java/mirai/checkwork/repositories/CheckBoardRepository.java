package mirai.checkwork.repositories;

import mirai.checkwork.dto.CheckWorkDTO;
import mirai.checkwork.models.CheckBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CheckBoardRepository extends JpaRepository<CheckBoard, Long> {
    @Query("SELECT ckb FROM CheckBoard ckb WHERE ckb.checkDate = ?1 AND ckb.userId = ?2")
    CheckBoard findByCheckDateAndUserId(LocalDate date, Long userId);

    @Query("SELECT new mirai.checkwork.dto.CheckWorkDTO(usr.id, usr.name, ckb.checkInTime, ckb.checkOutTime, ckb.id) FROM User usr LEFT JOIN CheckBoard ckb ON usr.id = ckb.userId WHERE ckb.checkDate = ?1")
    List<CheckWorkDTO> getOnCheckWorkDTO(LocalDate date);
}
