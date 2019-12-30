package mirai.checkwork.controllers;

import mirai.checkwork.dto.AbsentBoardDTO;
import mirai.checkwork.dto.CheckWorkDTO;
import mirai.checkwork.models.User;
import mirai.checkwork.repositories.UserRepository;
import mirai.checkwork.services.AbsentBoardService;
import mirai.checkwork.services.CheckBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    CheckBoardService checkBoardService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AbsentBoardService absentBoardService;

    @GetMapping("/check-board")
    public String displayCheckBoard(Model model) {
        LocalDate date = LocalDate.now();
        List<CheckWorkDTO> checkList = checkBoardService.getCheckList(date);
        model.addAttribute("checkList", checkList);
        model.addAttribute("dateNow", date);

        return "admin/check-board";
    }

    @GetMapping("/absent-board")
    public String displayAbsentBoard(Model model) {
        LocalDate date = LocalDate.now();
        List<AbsentBoardDTO> rs = absentBoardService.getListAbsentBoardAdmin(date);
        model.addAttribute("absentList", rs);
        model.addAttribute("dateNow", date);

        return "admin/absent-board";
    }

    @GetMapping("/staffs/info")
    public String staffsInfoShow(Model model) {
        List<User> userList = userRepository.findAll();
        model.addAttribute("userList", userList);
        return "admin/staffs-info-board";
    }
}
