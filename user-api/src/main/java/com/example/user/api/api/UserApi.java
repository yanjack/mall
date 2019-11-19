package com.example.user.api.api;

import com.example.mallcommon.exception.BaseException;
import com.example.user.api.mode.UserBean;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public interface UserApi {
    @PostMapping("insert")
    UserBean insert(@RequestBody UserBean userBean) ;

    @GetMapping("selectByPhone")
    UserBean selectByPhone(@RequestParam("phone") String phone) throws BaseException;
}
