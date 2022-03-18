package com.pengffe.pte.modules.authServer.service;

/**
 * @author pengffe  Email: pengffe@gmail.com
 * @descrption spring security jwt
 */

import cn.hutool.core.util.RandomUtil;
import com.pengffe.pte.modules.authServer.dao.UserDao;
import com.pengffe.pte.modules.authServer.entity.JwtUser;
import com.pengffe.pte.modules.practice.service.PracticeServiceImpl;
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

    @Autowired
    private UserDao userDao;

    @Autowired
    private PracticeServiceImpl practiceService;

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
                return new JwtUser(user.getId(), username, pass,user.getAuthorities().toString(), user.isEnabled());
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
        JwtUser user = new JwtUser(RandomUtil.randomString(8), username, password,"USER", false);

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
            practiceService.saveCollectRecord(user.getId());
            practiceService.savePracticeRecord(user.getId());

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
    
    public String findEmailByUsername(String username){
        return userDao.findByUsername(username).getEmail();
    }
    
    public boolean activateUser(String email){
        return userDao.enableUser(email);
    }

}
