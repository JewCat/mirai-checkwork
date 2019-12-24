package mirai.checkwork.repositories;

import mirai.checkwork.models.AbsentBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbsentBoardRepository extends JpaRepository<AbsentBoard, Long> {
}
