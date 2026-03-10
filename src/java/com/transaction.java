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
                String fail_transaction_query="INSERT INTO transaction (sender_id,receiver_id,amount,status) values (?,?,?,?);";
                ps=con.prepareStatement(fail_transaction_query);
                ps.setInt(1,sender_user_id);
                ps.setInt(2,receiver_user_id);
                ps.setInt(3,amount);
                ps.setString(4,"Failed");
                int check=ps.executeUpdate();
                if(check>0)
                {
                    con.commit();
                    pw.println("<html><head>");
pw.println("<style>");
pw.println("body{margin:0;font-family:Arial;background:linear-gradient(120deg,#74ebd5,#ACB6E5);height:100vh;display:flex;justify-content:center;align-items:center;}");
pw.println(".card{background:white;padding:40px;border-radius:10px;box-shadow:0 4px 15px rgba(0,0,0,0.2);text-align:center;}");
pw.println("a{display:inline-block;margin-top:15px;padding:10px 15px;background:#e74c3c;color:white;text-decoration:none;border-radius:5px;}");
pw.println("a:hover{background:#c0392b;}");
pw.println("</style>");
pw.println("</head><body>");

pw.println("<div class='card'>");
pw.println("<h1>Insufficient Balance</h1>");
pw.println("<p>Please add balance to continue</p>");
pw.println("<a href='index.html'>Go To Home</a>");
pw.println("</div>");

pw.println("</body></html>");
                    return;
                }
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
                
                String transaction_history_query="INSERT INTO transaction (sender_id,receiver_id,amount,status) values (?,?,?,?);";
                ps=con.prepareStatement(transaction_history_query);
                ps.setInt(1,sender_user_id);
                ps.setInt(2,receiver_user_id);
                ps.setInt(3,amount);
                ps.setString(4,"Successful");
                int check=ps.executeUpdate();
                if(check>0)
                {
                    con.commit();
                    pw.println("<html><head>");
pw.println("<style>");
pw.println("body{margin:0;font-family:Arial;background:linear-gradient(120deg,#74ebd5,#ACB6E5);height:100vh;display:flex;justify-content:center;align-items:center;}");
pw.println(".card{background:white;padding:40px;border-radius:10px;box-shadow:0 4px 15px rgba(0,0,0,0.2);text-align:center;}");
pw.println("a{display:inline-block;margin-top:15px;padding:10px 15px;background:#4CAF50;color:white;text-decoration:none;border-radius:5px;}");
pw.println("a:hover{background:#45a049;}");
pw.println("</style>");
pw.println("</head><body>");

pw.println("<div class='card'>");
pw.println("<h1>Transaction Successful</h1>");
pw.println("<a href='index.html'>Go To Home</a>");
pw.println("</div>");

pw.println("</body></html>");
                    
                }
                else
                {
                    con.rollback();
                    pw.println("<html><head>");
pw.println("<style>");
pw.println("body{margin:0;font-family:Arial;background:linear-gradient(120deg,#74ebd5,#ACB6E5);height:100vh;display:flex;justify-content:center;align-items:center;}");
pw.println(".card{background:white;padding:40px;border-radius:10px;box-shadow:0 4px 15px rgba(0,0,0,0.2);text-align:center;}");
pw.println("a{display:inline-block;margin-top:15px;padding:10px 15px;background:#e74c3c;color:white;text-decoration:none;border-radius:5px;}");
pw.println("a:hover{background:#c0392b;}");
pw.println("</style>");
pw.println("</head><body>");

pw.println("<div class='card'>");
pw.println("<h1>Something Went Wrong</h1>");
pw.println("<p>Please try again</p>");
pw.println("<a href='index.html'>Go To Home</a>");
pw.println("</div>");

pw.println("</body></html>");
                }
            }       
            else
            {
                con.rollback();
                pw.println("<html><head>");
pw.println("<style>");
pw.println("body{margin:0;font-family:Arial;background:linear-gradient(120deg,#74ebd5,#ACB6E5);height:100vh;display:flex;justify-content:center;align-items:center;}");
pw.println(".card{background:white;padding:40px;border-radius:10px;box-shadow:0 4px 15px rgba(0,0,0,0.2);text-align:center;}");
pw.println("a{display:inline-block;margin-top:15px;padding:10px 15px;background:#e74c3c;color:white;text-decoration:none;border-radius:5px;}");
pw.println("a:hover{background:#c0392b;}");
pw.println("</style>");
pw.println("</head><body>");

pw.println("<div class='card'>");
pw.println("<h1>Something Went Wrong</h1>");
pw.println("<p>Please try again</p>");
pw.println("<a href='index.html'>Go To Home</a>");
pw.println("</div>");

pw.println("</body></html>");
                
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        
    }
}
