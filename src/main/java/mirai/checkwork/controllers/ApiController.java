package mirai.checkwork.controllers;

import mirai.checkwork.common.AbsentRequest;
import mirai.checkwork.common.ApiResponse;
import mirai.checkwork.common.Geolocation;
import mirai.checkwork.exceptions.OutDistanceException;
import mirai.checkwork.services.AbsentBoardService;
import mirai.checkwork.services.CheckBoardService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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

    @PostMapping("/absent")
    public ApiResponse absentSubmit(@RequestBody AbsentRequest req) {
        ApiResponse res = new ApiResponse();
        absentBoardService.takeAbsent(req);
        res.setData(req);
        return res;
    }
}
