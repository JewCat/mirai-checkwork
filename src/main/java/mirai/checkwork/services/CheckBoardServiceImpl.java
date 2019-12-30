package mirai.checkwork.services;

import mirai.checkwork.common.AuthDetails;
import mirai.checkwork.common.CheckEditRequest;
import mirai.checkwork.common.Geolocation;
import mirai.checkwork.common.Role;
import mirai.checkwork.dto.CheckWorkDTO;
import mirai.checkwork.exceptions.NullGeolocationException;
import mirai.checkwork.exceptions.OutDistanceException;
import mirai.checkwork.models.CheckBoard;
import mirai.checkwork.models.User;
import mirai.checkwork.repositories.CheckBoardRepository;
import mirai.checkwork.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CheckBoardServiceImpl implements CheckBoardService {
    @Autowired
    CheckBoardRepository checkBoardRepository;

    @Autowired
    UserRepository userRepository;

    @Value("${geolocation.longitude}")
    double masterLongitude;

    @Value("${geolocation.latitude}")
    double masterLatitude;

    @Override
    public double getDistance(Geolocation geolocation1, Geolocation geolocation2) {
        int earthRadiusKm = 6371;
        double 
            lat1 = geolocation1.getLatitude(), 
            lon1 = geolocation1.getLongitude(), 
            lat2 = geolocation2.getLatitude(), 
            lon2 = geolocation2.getLongitude();
                
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        
        return earthRadiusKm * c;
    }

    @Override
    public void checkIn(Geolocation geolocation) throws OutDistanceException {
        double distance = getDistance(new Geolocation(masterLongitude, masterLatitude), geolocation);

        if (distance > 2) {
            throw new OutDistanceException();
        }
        else if (!isCheckIn()) {
            User currentUser = ((AuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getUser();
            CheckBoard checkBoard = new CheckBoard();
            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now();
            checkBoard.setCheckDate(date);
            checkBoard.setCheckInTime(time);
            checkBoard.setUserId(currentUser.getId());
            checkBoardRepository.save(checkBoard);
        }
    }

    @Override
    public void checkOut(Geolocation geolocation) throws OutDistanceException {
        double distance = getDistance(new Geolocation(masterLongitude, masterLatitude), geolocation);

        if (distance > 2) {
            throw new OutDistanceException();
        }
        else if (!isCheckOut()) {
            LocalDate date = LocalDate.now();
            Long userId = ((AuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getUser()
                .getId();
            CheckBoard checkBoard = checkBoardRepository.findByCheckDateAndUserId(date, userId);
            LocalTime time = LocalTime.now();
            checkBoard.setCheckOutTime(time);
            checkBoardRepository.save(checkBoard);
        }
    }

    @Override
    public boolean isCheckIn() {
        LocalDate date = LocalDate.now();
        Long userId = ((AuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
            .getUser()
            .getId();
        CheckBoard checkBoard = checkBoardRepository.findByCheckDateAndUserId(date, userId);
        return checkBoard != null && checkBoard.getCheckInTime() != null;
    }

    @Override
    public boolean isCheckOut() {
        LocalDate date = LocalDate.now();
        Long userId = ((AuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
            .getUser()
            .getId();
        CheckBoard checkBoard = checkBoardRepository.findByCheckDateAndUserId(date, userId);
        return checkBoard != null && checkBoard.getCheckOutTime() != null;
    }

    @Override
    public CheckBoard getCheckRecordOfCurrentUser(LocalDate checkDate) {
        Long userId = ((AuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
            .getUser()
            .getId();
        return checkBoardRepository.findByCheckDateAndUserId(checkDate, userId);
    }

    @Override
    public List<CheckWorkDTO> getCheckList(LocalDate checkDate) {
        List<CheckWorkDTO> resultList = checkBoardRepository.getOnCheckWorkDTO(checkDate);
        List<Long> checkIdList = resultList
            .stream()
            .map(CheckWorkDTO::getUserId)
            .collect(Collectors.toList());
        List<CheckWorkDTO> uncheckList = userRepository.findAll()
            .stream()
            .filter(user -> !checkIdList.contains(user.getId()) && user.getRole() != Role.ROLE_ADMIN)
            .map(user -> {
                CheckWorkDTO checkWorkDTO = new CheckWorkDTO();
                checkWorkDTO.setUserId(user.getId());
                checkWorkDTO.setUserName(user.getName());
                checkWorkDTO.setCheckId(0L);
                return checkWorkDTO;
            })
            .collect(Collectors.toList());
        resultList.addAll(uncheckList);
        resultList.sort((i, j) -> i.getCheckId() < j.getCheckId() ? 1 : -1);
        return resultList;
    }

    @Override
    public void editById(CheckEditRequest req) {
        CheckBoard checkBoard = checkBoardRepository.existsById(req.getCheckId())
            ? checkBoardRepository.getOne(req.getCheckId())
            : null;
        LocalTime checkInTime = Instant.ofEpochMilli(req.getCheckInTime()).atZone(ZoneId.systemDefault()).toLocalTime();
        LocalTime checkOutTime = Instant.ofEpochMilli(req.getCheckOutTime()).atZone(ZoneId.systemDefault()).toLocalTime();

        if (checkBoard != null) {
            checkBoard.setCheckInTime(checkInTime);
            checkBoard.setCheckOutTime(checkOutTime);
            checkBoardRepository.save(checkBoard);
        }

    }

    @Override
    public CheckBoard getById(Long id) {
        if (checkBoardRepository.existsById(id)) {
            return checkBoardRepository.getOne(id);
        }

        return null;
    }

}
