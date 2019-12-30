package mirai.checkwork.controllers;

import mirai.checkwork.common.Geolocation;
import mirai.checkwork.exceptions.NullGeolocationException;
import mirai.checkwork.exceptions.OutDistanceException;
import mirai.checkwork.models.AbsentBoard;
import mirai.checkwork.models.CheckBoard;
import mirai.checkwork.services.AbsentBoardService;
import mirai.checkwork.services.CheckBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    CheckBoardService checkBoardService;

    @Autowired
    AbsentBoardService absentBoardService;

    @GetMapping("/check")
    public String checkIndex(Model model) {
        int checkNum = 0;

        if (checkBoardService.isCheckOut()) {
            checkNum = 2;
        }
        else if (checkBoardService.isCheckIn()) {
            checkNum = 1;
        }

        CheckBoard checkBoard = checkBoardService.getCheckRecordOfCurrentUser(LocalDate.now());
        String checkInTime = checkBoardService.isCheckIn()
            ? checkBoard.getCheckInTime().toString()
            : "NULL";
        String checkOutTime = checkBoardService.isCheckOut()
            ? checkBoard.getCheckOutTime().toString()
            : "NULL";

        model.addAttribute("checkInTime", checkInTime);
        model.addAttribute("checkOutTime", checkOutTime);
        model.addAttribute("checkNum", checkNum);
        return "user/check-board";
    }

    @GetMapping("/absent")
    public String absentIndex(Model model) {
        List<AbsentBoard> absentList = absentBoardService.getListAfterCurrentDateAndUser();
        model.addAttribute("absentList", absentList);
        return "user/absent-board";
    }

    @GetMapping("/profile")
    public String profileIndex() {
        return "user/profile";
    }
}
