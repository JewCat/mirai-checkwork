package mirai.checkwork.services;

import mirai.checkwork.common.AbsentRequest;
import mirai.checkwork.common.AuthDetails;
import mirai.checkwork.dto.AbsentBoardDTO;
import mirai.checkwork.models.AbsentBoard;
import mirai.checkwork.models.User;
import mirai.checkwork.repositories.AbsentBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AbsentBoardServiceImpl implements AbsentBoardService {
    @Autowired
    AbsentBoardRepository absentBoardRepository;

    @Override
    public User getAuthUser() {
        return ((AuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
    }

    @Override
    public void addAbsent(AbsentRequest req) {
        List<AbsentBoard> absentBoardList = new ArrayList<>();

        List<AbsentBoard> checkList = getListAfterCurrentDateAndUser();
        List<LocalDate> dateCheckList = checkList
            .stream()
            .map(AbsentBoard::getAbsentDate)
            .collect(Collectors.toList());

        req.getAbsentFirstShift().forEach(absentTime -> {
            AbsentBoard absentBoard = null;
            LocalDate date = Instant.ofEpochMilli(absentTime).atZone(ZoneId.systemDefault()).toLocalDate();
            if (!date.isAfter(LocalDate.now())) {

            }
            else if (!dateCheckList.contains(date)) {
                absentBoard = new AbsentBoard();
                absentBoard.setAbsentDate(date);
                absentBoard.setUserId(getAuthUser().getId());
                absentBoardList.add(absentBoard);
            }
            else {
                absentBoard = checkList
                    .stream()
                    .filter(item -> item.getAbsentDate().isEqual(date))
                    .findFirst()
                    .get();
            }
            absentBoard.setShift(1);
            absentBoardList.add(absentBoard);
        });

        req.getAbsentSecondShift().forEach(absentTime -> {
            AbsentBoard absentBoard = null;
            LocalDate date = Instant.ofEpochMilli(absentTime).atZone(ZoneId.systemDefault()).toLocalDate();
            if (!date.isAfter(LocalDate.now())) {

            }
            else if (!dateCheckList.contains(date)) {
                absentBoard = new AbsentBoard();
                absentBoard.setAbsentDate(date);
                absentBoard.setUserId(getAuthUser().getId());
                absentBoardList.add(absentBoard);
            }
            else {
                absentBoard = checkList
                    .stream()
                    .filter(item -> item.getAbsentDate().isEqual(date))
                    .findFirst()
                    .get();
            }
            absentBoard.setShift(2);
            absentBoardList.add(absentBoard);
        });

        req.getAbsentAllDay().forEach(absentTime -> {
            AbsentBoard absentBoard = null;
            LocalDate date = Instant.ofEpochMilli(absentTime).atZone(ZoneId.systemDefault()).toLocalDate();
            if (!date.isAfter(LocalDate.now())) {

            }
            else if (!dateCheckList.contains(date)) {
                absentBoard = new AbsentBoard();
                absentBoard.setAbsentDate(date);
                absentBoard.setUserId(getAuthUser().getId());
                absentBoardList.add(absentBoard);
            }
            else {
                absentBoard = checkList
                    .stream()
                    .filter(item -> item.getAbsentDate().isEqual(date))
                    .findFirst()
                    .get();
            }
            absentBoard.setShift(3);
            absentBoardList.add(absentBoard);
        });

        absentBoardRepository.saveAll(absentBoardList);
    }

    @Override
    public List<AbsentBoard> getListAfterCurrentDateAndUser() {
        LocalDate date = LocalDate.now();
        return absentBoardRepository
            .getListAfterDateByUser(date, getAuthUser().getId())
            .stream()
            .sorted((i, j) -> i.getAbsentDate().isAfter(j.getAbsentDate()) ? 1 : -1)
            .collect(Collectors.toList());
    }

    @Override
    public List<AbsentBoard> getListAfterCurrentDateAndUser(int limit) {
        LocalDate date = LocalDate.now();
        return absentBoardRepository
            .getListAfterDateByUserLimit(date, getAuthUser().getId(),
                PageRequest.of(0, limit,
                    Sort.by(Sort.Direction.ASC, "absentDate")));
    }

    @Override
    public void removeAbsent(Long absentId) {
        AbsentBoard absentBoard = absentBoardRepository.getOne(absentId);
        absentBoardRepository.delete(absentBoard);
    }

    @Override
    public List<AbsentBoardDTO> getListAbsentBoardAdmin(LocalDate date) {
        return absentBoardRepository.getAbsentDTOList(date);
    }
}
