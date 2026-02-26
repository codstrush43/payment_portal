/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.connection;

import org.apache.commons.dbcp2.BasicDataSource;
//import java.sql.DriverManager;
public class connection_pool {
    private static final BasicDataSource ds=new BasicDataSource();
    
    static{
       
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/payment_portal");
        ds.setUsername("root");
        ds.setPassword("user1");
        
        ds.setInitialSize(5);
        ds.setMaxTotal(10);
        ds.setMinIdle(2);
        ds.setMaxIdle(5);
    }
    
    public static BasicDataSource getDataSource()
    {
        return ds;
    }
}
