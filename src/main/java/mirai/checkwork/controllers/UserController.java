package mirai.checkwork.controllers;

import mirai.checkwork.common.Geolocation;
import mirai.checkwork.exceptions.NullGeolocationException;
import mirai.checkwork.exceptions.OutDistanceException;
import mirai.checkwork.models.CheckBoard;
import mirai.checkwork.services.CheckBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;

@Controller
public class UserController {
    @Autowired
    CheckBoardService checkBoardService;

    @GetMapping("/check")
    public String checkIndex(Model model) {
        int checkNum = 0;

        if (checkBoardService.isCheckOut()) {
            checkNum = 2;
        }
        else if (checkBoardService.isCheckIn()) {
            checkNum = 1;
        }

        CheckBoard checkBoard = checkBoardService.getCheckRecord(LocalDate.now());
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
    public String absentIndex() {
        return "user/absent-board";
    }
}
