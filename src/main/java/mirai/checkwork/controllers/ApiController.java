package mirai.checkwork.controllers;

import mirai.checkwork.common.AbsentAddRequest;
import mirai.checkwork.common.ApiResponse;
import mirai.checkwork.common.CheckEditRequest;
import mirai.checkwork.common.Geolocation;
import mirai.checkwork.dto.AbsentBoardDTO;
import mirai.checkwork.dto.CheckWorkDTO;
import mirai.checkwork.exceptions.OutDistanceException;
import mirai.checkwork.models.CheckBoard;
import mirai.checkwork.models.User;
import mirai.checkwork.repositories.UserRepository;
import mirai.checkwork.services.AbsentBoardService;
import mirai.checkwork.services.CheckBoardService;
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

    @Autowired
    UserRepository userRepository;

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
    public ApiResponse addAbsent(@RequestBody AbsentAddRequest req) {
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

    @GetMapping("/admin/absent-board/{date}")
    public ApiResponse getAbsentList(@PathVariable("date") Long date) {
        ApiResponse res = new ApiResponse();
        LocalDate localDate = Instant.ofEpochMilli(date).atZone(ZoneId.systemDefault()).toLocalDate();
        List<AbsentBoardDTO> checkList = absentBoardService.getListAbsentBoardAdmin(localDate);
        res.setData(checkList);
        return res;
    }

    @PostMapping("/admin/check-board/edit")
    public ApiResponse editCheckBoard(@RequestBody CheckEditRequest req) {
        ApiResponse res = new ApiResponse();
        checkBoardService.editById(req);
        return res;
    }

    @GetMapping("/admin/check-board/id/{id}")
    public ApiResponse getCheckRecord(@PathVariable("id") Long id) {
        ApiResponse res = new ApiResponse();

        CheckBoard ckb = checkBoardService.getById(id);
        res.setData(ckb);

        return res;
    }

    @PostMapping("/admin/check-board/update-status/{id}")
    public ApiResponse changeStatus(@PathVariable("id") Long id) {
        ApiResponse res = new ApiResponse();

        if (userRepository.existsById(id)) {
            User user = userRepository.getOne(id);
            user.setStatus(user.getStatus() == 0 ? 1 : 0);
            userRepository.save(user);
        }

        return res;
    }
}
