/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mc.dao;

import com.mc.model.Circle;
import java.sql.*;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 *
 * @author Administrator
 */
@Component  //this will automatically create a bean 'jbbdDaoImpl' for this class
public class JdbcDaoImpl {

      @Autowired  //this will automatically set its dataSource property  
       private DataSource dataSource;
      
      private JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
      
    public DataSource getDataSource(){
        return dataSource;        
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    //that use "org.apache.commons.dbcp2.BasicDataSource"  and JdbcTemplate jdbcTemplate  -> much less code
    public int getCircleCount(){
        jdbcTemplate.setDataSource(getDataSource());
        String query ="Select count(*) from circle";
        //return jdbcTemplate.queryForInt(query);  
        return jdbcTemplate.queryForObject(query,Integer.class); //
    }
    
    //that use org.springframework.jdbc.datasource.DriverManagerDataSource 
    // OR not used JdbcTemplate -> a lot of code
    public Circle getCircle(int circleId){    
        Connection connect = null;
         try {
//            String driver = "org.apache.derby.jdbc.ClientDriver";
//            Class.forName(driver).newInstance();
//            connect = DriverManager.getConnection("jdbc:derby://localhost:1527/db");
            connect= dataSource.getConnection();
            
            Circle circle;
            PreparedStatement ps = connect.prepareStatement("SELECT * FROM circle where id = ?");
            ps.setInt(1, circleId);
            circle = null;
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                circle = new Circle(circleId, rs.getString("name"));
            }

            return circle;
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connect.close();
            } catch (SQLException e) {
            }
        }
    }       
}
    

