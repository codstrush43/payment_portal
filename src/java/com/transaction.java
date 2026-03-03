/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import com.connection.connection_pool;
import java.sql.SQLException;
import java.sql.ResultSet;

@WebServlet("/transaction")
public class transaction extends HttpServlet{
    PreparedStatement ps=null;
    Connection con=null;
    @Override
    public void doPost(HttpServletRequest req,HttpServletResponse res)
    throws ServletException,IOException{
        res.setContentType("text/html");
        PrintWriter pw = res.getWriter();
        HttpSession session=req.getSession(false);
        Integer id=(Integer)session.getAttribute("user_id");
        int sender_user_id=id;
        
        int receiver_user_id=Integer.parseInt(req.getParameter("user_id"));
        int amount=Integer.parseInt(req.getParameter("amount"));
        
        try{
            con=connection_pool.getDataSource().getConnection();
            con.setAutoCommit(false);
            String check_balance="select balance from wallet where user_id=?";
            ps=con.prepareStatement(check_balance);
            ps.setInt(1,sender_user_id);
            
            ResultSet rs=ps.executeQuery();
            int curr_bal=0;
            if(rs.next())
            {
                curr_bal=rs.getInt("balance");
            }
            
            if(curr_bal<amount)
            {
                pw.println("<h1>You Do Not Have That Much Balance<br>Please Add Balance</h1>");
                return;
            }
            
            String sender_query="update wallet set balance = balance - ? where user_id=?";
            String receiver_query="update wallet set balance = balance + ? where user_id = ?";
            ps=con.prepareStatement(sender_query);
            ps.setInt(1,amount);
            ps.setInt(2,sender_user_id);
            int sender_check=ps.executeUpdate();
            ps=con.prepareStatement(receiver_query);
            ps.setInt(1,amount);
            ps.setInt(2,receiver_user_id);
            int receiver_check=ps.executeUpdate();
            
            if(sender_check>0 && receiver_check>0)
            {
                con.commit();
                pw.println("<h1>Transaction Successful!!");
            }
            else
            {
                con.rollback();
                pw.println("<h1>Somthing Went Wrong <br>Please Try Again!!!</h1>");
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        
    }
}
