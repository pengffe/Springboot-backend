package com.sj.pte.modules.authServer.service;/**
 * Created by TUTEHUB on 2021-06-01 13:11.
 * Copyright © 2021 TUTEHUB. All rights reserved.
 * ------------------------
 * Non-disclosure Terms
 * -------------------------
 * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
 * Developer fengpeng agrees with above terms.
 * Technique Support: jobyme88.com
 */

/**
 * @author pengffe  Email: pengffe@gmail.com
 * @descrption spring security jwt
 */

import cn.hutool.core.util.RandomUtil;
import com.sj.pte.modules.authServer.dao.UserDao;
import com.sj.pte.modules.authServer.entity.JwtUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
/**
 * JwtUserDetailsService
 *	 	实现UserDetailsService,重写loadUserByUsername方法
 *  	返回随机生成的user,pass是密码,这里固定生成的
 *  	如果你自己需要定制查询user的方法,请改造这里
 * @author zhengkai.blog.csdn.net
 */
@Service
public class UserService implements UserDetailsService {

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        JwtUser user;

        if (StringUtils.isNotEmpty(username)){
            if (username.contains("@")){
                user = userDao.findByEmail(username);
            }
            else if (username.contains("+")) {
                user = userDao.findByPhone(username);
            }
            else{
                user = userDao.findByUsername(username);
            }
            if (null != user){
                String pass = new BCryptPasswordEncoder().encode(user.getPassword());
                return new JwtUser(user.getId(), username, pass,"USER", true);
            }
            else{
                throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
            }
        }
        else{
            throw new UsernameNotFoundException(String.format("Username is empty '%s'.", username));
        }
    }

    public ResponseEntity<?> userRegister(String username, String password, String contact){
        if (null != userDao.findByUsername(username)) {
            return new ResponseEntity<>("Username exists.", HttpStatus.CONFLICT);
        }

        //创建用户
        JwtUser user = new JwtUser(RandomUtil.randomString(8), username, password,"USER", true);

        if (contact.contains("@")){
            if (null != userDao.findByEmail(contact)){
                return new ResponseEntity<>("Email has been bound.", HttpStatus.CONFLICT);
            }
            user.setEmail(contact);
        }
        else{
            if (null != userDao.findByPhone(contact)){
                return new ResponseEntity<>("Phone has been bound.", HttpStatus.CONFLICT);
            }
            user.setPhone(contact);
        }
        if (userDao.save(user)){


            return new ResponseEntity<>("Success to register.", HttpStatus.OK);
        }
        else return new ResponseEntity<>("Fail to register.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<?> restPassword(String username, String newPassword){
        try {
            userDao.resetPassword(username, newPassword);
            return new ResponseEntity<>("Success to reset password", HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("Fail to reset password.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
