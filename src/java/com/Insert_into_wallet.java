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
        HttpSession session=req.getSession(false);
        Integer id=(Integer)session.getAttribute("user_id");
        int user_id=id;
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
            pw.print("<html><head>");
            pw.print("<style>");
            pw.print("body{font-family:Arial;background:linear-gradient(120deg,#74ebd5,#ACB6E5);height:100vh;display:flex;justify-content:center;align-items:center;margin:0;}");
            pw.print(".card{background:white;padding:40px;border-radius:10px;box-shadow:0 4px 15px rgba(0,0,0,0.2);text-align:center;}");
            pw.print("a{display:inline-block;margin-top:15px;padding:10px 15px;background:#e74c3c;color:white;text-decoration:none;border-radius:5px;}");
            pw.print("a:hover{background:#c0392b;}");
            pw.print("</style>");
            pw.print("</head><body>");

            pw.print("<div class='card'>");
            pw.print("<h1>Something went wrong. Please try again!</h1>");
            pw.print("<a href='insert_into_wallet.html'>Do Transaction</a>");
            pw.print("</div>");

            pw.print("</body></html>");
        }
        else
        {
            pw.print("<html><head>");
            pw.print("<style>");
            pw.print("body{font-family:Arial;background:linear-gradient(120deg,#74ebd5,#ACB6E5);height:100vh;display:flex;justify-content:center;align-items:center;margin:0;}");
            pw.print(".card{background:white;padding:40px;border-radius:10px;box-shadow:0 4px 15px rgba(0,0,0,0.2);text-align:center;}");
            pw.print("a{display:inline-block;margin-top:15px;padding:10px 15px;background:#4CAF50;color:white;text-decoration:none;border-radius:5px;}");
            pw.print("a:hover{background:#45a049;}");
            pw.print("</style>");
            pw.print("</head><body>");

            pw.print("<div class='card'>");
            pw.print("<h1>Transaction Successful</h1>");
            pw.print("<a href='index.html'>Go To Home</a>");
            pw.print("</div>");

            pw.print("</body></html>");
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
