/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mc.dao;

import com.mc.model.Circle;
import java.sql.*;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 *
 * @author Administrator
 */
@Component  //this will automatically create a bean 'jbbdDaoImpl' for this class
public class JdbcDaoImpl {

     // @Autowired  //this will automatically set its dataSource property  
       private DataSource dataSource;
      
      private JdbcTemplate jdbcTemplate ;//= new JdbcTemplate();

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
      
    public DataSource getDataSource(){
        return dataSource;        
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
  //      this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    public void createTriangleTable(){
        String sql ="Create table Triangle (id integer, name char(50))";
        jdbcTemplate.execute(sql);
    }
    
    public void insertCircle(Circle circle){
        String sql ="Insert into circle(id,name) values(?,?)";
        jdbcTemplate.update(sql, new Object[]{circle.getId(),circle.getName()});
    }
    
 
    
    
    //that use "org.apache.commons.dbcp2.BasicDataSource"  and JdbcTemplate jdbcTemplate  -> much less code
    public int getCircleCount(){
        //jdbcTemplate.setDataSource(getDataSource());   
        String query ="Select count(*) from circle";
        //return jdbcTemplate.queryForInt(query);  
        return jdbcTemplate.queryForObject(query,Integer.class); //
    }
    
    public String getCircleName(int circleId){
         String sql ="Select name from circle where id=?";
        //return jdbcTemplate.queryForInt(query);  
        return jdbcTemplate.queryForObject(sql,new Object[]{circleId},String.class); //
    }
    
    public List<Circle> getAllCircles(){
        String sql = "Select * from circle";
        return jdbcTemplate.query(sql, new CircleMapper());
    }
    
    public Circle getCircleForId(int circleId){
        String sql = "Select * from circle where id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{circleId}, new CircleMapper());
    }
    
    private static final class CircleMapper implements RowMapper<Circle>{
        public Circle mapRow(ResultSet resultSet, int rowNumber) throws SQLException{
            Circle circle = new Circle();
            circle.setId(resultSet.getInt("ID"));
            circle.setName(resultSet.getString("name"));
            return circle;
        }
    }
    
    
    //that use org.springframework.jdbc.datasource.DriverManagerDataSource 
    // OR not used JdbcTemplate -> a lot of code
//    public Circle getCircle(int circleId){    
//        Connection connect = null;
//         try {
////            String driver = "org.apache.derby.jdbc.ClientDriver";
////            Class.forName(driver).newInstance();
////            connect = DriverManager.getConnection("jdbc:derby://localhost:1527/db");
//            connect= dataSource.getConnection();
//            
//            Circle circle;
//            PreparedStatement ps = connect.prepareStatement("SELECT * FROM circle where id = ?");
//            ps.setInt(1, circleId);
//            circle = null;
//            ResultSet rs = ps.executeQuery();
//
//            if (rs.next()) {
//                circle = new Circle(circleId, rs.getString("name"));
//            }
//
//            return circle;
//            
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        } finally {
//            try {
//                connect.close();
//            } catch (SQLException e) {
//            }
//        }
//    }       
}
    

