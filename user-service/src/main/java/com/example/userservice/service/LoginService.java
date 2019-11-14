package com.example.userservice.service;

import com.example.mallcache.RedisUtil;
import com.example.user.api.mode.UserBean;
import com.example.user.api.mode.dto.TokenInfoDto;
import com.example.userservice.constant.RedisConstants;
import com.example.userservice.dao.UserDao;
import com.example.userservice.util.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);

    @Resource
    UserDao userDao;

    @Resource
    RedisUtil redisUtil;

    public TokenInfoDto dologin(String phone){
        TokenInfoDto tokenInfoDto = new TokenInfoDto();
        UserBean userBean = userDao.selectByPhone(phone);
        if(null != userBean){
            //int 类型没有cope过来，boolean 值get方法成is??
            BeanUtils.copy(tokenInfoDto,userBean);
            tokenInfoDto.setId(userBean.getId());
            tokenInfoDto.setEnable(userBean.isEnable());
            //分布式id token令牌 redis递增 规律性太强易被攻击，待考虑
            long l=redisUtil.incr("token",1);
            tokenInfoDto.setToken("token"+l);
            //清除之前的token
            Object preToken = redisUtil.get(RedisConstants.TOKEN_LINK+userBean.getPhone());
            if(null!=preToken){
                redisUtil.del(RedisConstants.TOKEN+preToken.toString());
            }
            //缓存用户phone与token
            redisUtil.set(RedisConstants.TOKEN_LINK+userBean.getPhone(),"token"+l,RedisConstants.TOKEN_EXPIRE);
            //存缓存token与用户信息
            redisUtil.set(RedisConstants.TOKEN+"token"+l,tokenInfoDto,RedisConstants.TOKEN_EXPIRE);
        }
        return tokenInfoDto;
    }

}
