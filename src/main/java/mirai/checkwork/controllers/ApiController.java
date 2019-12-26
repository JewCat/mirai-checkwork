package mirai.checkwork.controllers;

import mirai.checkwork.common.AbsentRequest;
import mirai.checkwork.common.ApiResponse;
import mirai.checkwork.common.Geolocation;
import mirai.checkwork.dto.CheckWorkDTO;
import mirai.checkwork.exceptions.OutDistanceException;
import mirai.checkwork.services.AbsentBoardService;
import mirai.checkwork.services.CheckBoardService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ApiController {
    @Autowired
    CheckBoardService checkBoardService;

    @Autowired
    AbsentBoardService absentBoardService;

    @PostMapping("/check")
    public ApiResponse check(@RequestBody Geolocation geolocation) throws OutDistanceException {
        ApiResponse res = new ApiResponse();
        Map<String, Object> data = new HashMap<>();

        if (checkBoardService.isCheckIn()) {
            checkBoardService.checkOut(geolocation);
            data.put("checkNum", 2);
            res.setData(data);
            return res;
        }

        checkBoardService.checkIn(geolocation);
        data.put("checkNum", 1);
        res.setData(data);
        return res;
    }

    @PostMapping("/absent/add")
    public ApiResponse addAbsent(@RequestBody AbsentRequest req) {
        ApiResponse res = new ApiResponse();
        absentBoardService.addAbsent(req);
        res.setData(req);
        return res;
    }

    @PostMapping("/absent/remove")
    public ApiResponse removeAbsent(@RequestBody Long absentId) {
        ApiResponse res = new ApiResponse();
        absentBoardService.removeAbsent(absentId);
        res.setMessage("Successful !");
        return res;
    }

    @GetMapping("/admin/check-board/{date}")
    public ApiResponse getCheckList(@PathVariable("date") Long date) {
        ApiResponse res = new ApiResponse();
        LocalDate localDate = Instant.ofEpochMilli(date).atZone(ZoneId.systemDefault()).toLocalDate();
        List<CheckWorkDTO> checkList = checkBoardService.getCheckList(localDate);
        res.setData(checkList);
        return res;
    }
}
