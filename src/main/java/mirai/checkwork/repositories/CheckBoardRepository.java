package mirai.checkwork.repositories;

import mirai.checkwork.dto.CheckWorkDTO;
import mirai.checkwork.models.CheckBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface CheckBoardRepository extends JpaRepository<CheckBoard, Long> {
    /*@Query(
        "SELECT new mirai.checkwork.dto.CheckWorkDTO(u.id, u.name, ckb.checkDate, ckb.checkInTime, ckb.checkOutTime, COUNT(asb))" +
        "FROM User u LEFT JOIN CheckBoard ckb LEFT JOIN AbsentBoard asb WHERE ckb.checkDate = ?1")
    List<CheckWorkDTO> getCheckList(Date checkDate);*/
    @Query("SELECT ckb FROM CheckBoard ckb WHERE ckb.checkDate = ?1 AND ckb.userId = ?2")
    CheckBoard findByCheckDateAndUserId(Date date, Long userId);
}
