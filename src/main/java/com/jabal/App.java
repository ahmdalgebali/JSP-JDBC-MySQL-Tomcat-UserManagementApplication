package com.jabal;

import com.jabal.dao.USerDaoI;
import com.jabal.dao.UserDao;
import com.jabal.model.User;

public class App {
    public static void main(String[] args) {
        UserDao userDao = new USerDaoI();
        User user = new User();
        // user.setId(20);
        user.setCountry("Misr");
        user.setName("Mos3ad");
        user.setEmail("ahmedelgebaly44@gmail.com");
        //userDao.save(user);
        
        
        //System.out.println(userDao.selectUser(12));
        // for(int i=1; i<20;i++){userDao.deleteUser(i);}
        userDao.deleteUser(20);
        userDao.selectUser(30);
        userDao.selectAllUsers().forEach(System.out::println);
        
        // 
        //System.out.println("Done!");
    }
}