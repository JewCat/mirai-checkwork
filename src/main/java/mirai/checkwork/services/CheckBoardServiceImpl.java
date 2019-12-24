package mirai.checkwork.services;

import mirai.checkwork.common.AuthDetails;
import mirai.checkwork.common.Geolocation;
import mirai.checkwork.dto.CheckWorkDTO;
import mirai.checkwork.exceptions.NullGeolocationException;
import mirai.checkwork.exceptions.OutDistanceException;
import mirai.checkwork.models.CheckBoard;
import mirai.checkwork.models.User;
import mirai.checkwork.repositories.CheckBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class CheckBoardServiceImpl implements CheckBoardService {
    @Autowired
    CheckBoardRepository checkBoardRepository;

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
            Date date = java.sql.Date.valueOf(LocalDate.now());
            Time time = Time.valueOf(LocalTime.now());
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
            Date date = java.sql.Date.valueOf(LocalDate.now());
            Long userId = ((AuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getUser()
                .getId();
            CheckBoard checkBoard = checkBoardRepository.findByCheckDateAndUserId(date, userId);
            Time time = Time.valueOf(LocalTime.now());
            checkBoard.setCheckOutTime(time);
            checkBoardRepository.save(checkBoard);
        }
    }

    @Override
    public boolean isCheckIn() {
        Date date = java.sql.Date.valueOf(LocalDate.now());
        Long userId = ((AuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
            .getUser()
            .getId();
        CheckBoard checkBoard = checkBoardRepository.findByCheckDateAndUserId(date, userId);
        return checkBoard != null && checkBoard.getCheckInTime() != null;
    }

    @Override
    public boolean isCheckOut() {
        Date date = java.sql.Date.valueOf(LocalDate.now());
        Long userId = ((AuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
            .getUser()
            .getId();
        CheckBoard checkBoard = checkBoardRepository.findByCheckDateAndUserId(date, userId);
        return checkBoard != null && checkBoard.getCheckOutTime() != null;
    }

    @Override
    public CheckBoard getCheckRecord(LocalDate checkDate) {
        Long userId = ((AuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
            .getUser()
            .getId();
        return checkBoardRepository.findByCheckDateAndUserId(java.sql.Date.valueOf(checkDate), userId);
    }

    @Override
    public List<CheckWorkDTO> getCheckList(LocalDate checkDate) {
        return null;
//        return checkBoardRepository.getCheckList(checkDate);
    }

    @Override
    public CheckWorkDTO getCheckDetail(Long userId, LocalDate checkDate) {
        return null;
    }
}
