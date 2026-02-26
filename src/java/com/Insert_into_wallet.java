/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.*;
import java.sql.*;
import java.sql.PreparedStatement;
import com.connection.connection_pool;

@WebServlet("/insert-amount")
public class Insert_into_wallet extends HttpServlet {
    PreparedStatement ps;
    Connection con=null;
    @Override
    public void doPost(HttpServletRequest req,HttpServletResponse res)
            throws ServletException,IOException{
        res.setContentType("text/html");
        PrintWriter pw=res.getWriter();
        int user_id = Integer.parseInt(req.getParameter("user_id"));
        double amount = Double.parseDouble(req.getParameter("amt"));
        try{
//        Class.forName("com.mysql.cj.jdbc.Driver");
//         pw.print("<h1>Successful</h1>");
        con=connection_pool.getDataSource().getConnection();
        String query="update wallet set balance = balance + ? where user_id=?";
        ps = con.prepareStatement(query);
        
        ps.setDouble(1,amount);
        ps.setInt(2,user_id);
        int n=ps.executeUpdate();
        if(n<=0)
        {
            pw.print("<h1> Something went Wrong please try again!!");
        }
        else
        {
            pw.print("<h1>Successful</h1>");
        }
        con.close();
        
        }
        catch(SQLException e)
        {
//             pw.print("<h1>Successful</h1>");
            System.out.println(e.getMessage());
        }        
    }
}
