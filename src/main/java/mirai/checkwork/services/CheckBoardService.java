package mirai.checkwork.services;

import mirai.checkwork.common.Geolocation;
import mirai.checkwork.dto.CheckWorkDTO;
import mirai.checkwork.exceptions.NullGeolocationException;
import mirai.checkwork.exceptions.OutDistanceException;
import mirai.checkwork.models.CheckBoard;

import java.time.LocalDate;
import java.util.List;

public interface CheckBoardService {
    double getDistance(Geolocation geolocation1, Geolocation geolocation2);
    void checkIn(Geolocation geolocation) throws OutDistanceException;
    void checkOut(Geolocation geolocation) throws OutDistanceException;
    boolean isCheckIn();
    boolean isCheckOut();
    CheckBoard getCheckRecord(LocalDate checkDate);
    List<CheckWorkDTO> getCheckList(LocalDate checkDate);
    CheckWorkDTO getCheckDetail(Long userId, LocalDate checkDate);
}