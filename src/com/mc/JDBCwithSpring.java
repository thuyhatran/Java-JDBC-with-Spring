/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mc;

import com.mc.dao.JdbcDaoImpl;
import com.mc.model.Circle;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Administrator
 */
public class JDBCwithSpring {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //WITHOUT SPRING
//        JdbcDaoImpl circleDao = new JdbcDaoImpl();
//        System.out.println(circleDao.getCircle(1));
//        System.out.println(circleDao.getCircle(2).getName());

        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");      
        JdbcDaoImpl circleDao = context.getBean("jdbcDaoImpl",JdbcDaoImpl.class);
        System.out.println(circleDao.getCircleForId(1));
        System.out.println("Circle count" + circleDao.getCircleCount());
        System.out.println(circleDao.getCircleName(2));
      //  circleDao.insertCircle(new Circle(5,"Fifth Circle"));
        System.out.println(circleDao.getAllCircles());
        
    }
    
}
