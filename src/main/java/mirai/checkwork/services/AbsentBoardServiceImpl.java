package mirai.checkwork.services;

import mirai.checkwork.common.AbsentRequest;
import mirai.checkwork.common.AuthDetails;
import mirai.checkwork.models.AbsentBoard;
import mirai.checkwork.repositories.AbsentBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Date;

@Service
@Transactional
public class AbsentBoardServiceImpl implements AbsentBoardService {
    @Autowired
    AbsentBoardRepository absentBoardRepository;

    @Autowired
    EntityManager em;

    @Override
    public void takeAbsent(AbsentRequest req) {
        Long userId = ((AuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
            .getUser()
            .getId();

        em.getTransaction().begin();

        req.getAbsentFirstShift().forEach(i -> {
            AbsentBoard absentBoard = new AbsentBoard();
            absentBoard.setAbsentDate(new Date(i));
            absentBoard.setShift(1);
            absentBoard.setUserId(userId);
            em.persist(absentBoard);
        });

        req.getAbsentSecondShift().forEach(i -> {
            AbsentBoard absentBoard = new AbsentBoard();
            absentBoard.setAbsentDate(new Date(i));
            absentBoard.setShift(2);
            absentBoard.setUserId(userId);
            em.persist(absentBoard);
        });

        req.getAbsentAllDay().forEach(i -> {
            AbsentBoard absentBoard = new AbsentBoard();
            absentBoard.setAbsentDate(new Date(i));
            absentBoard.setShift(3);
            absentBoard.setUserId(userId);
            em.persist(absentBoard);
        });

        em.getTransaction().commit();
    }
}
