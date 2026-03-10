/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import java.sql.Connection;
//import java.sql.Statement;
//import java.sql.DriverManager;
import java.sql.PreparedStatement;
import com.connection.connection_pool;

@WebServlet("/sign-up")
public class sign_up extends HttpServlet
{
    private Connection con;
//    private Statement stmt;
    private PreparedStatement ps;
     PrintWriter pw;
    @Override
    public void doPost(HttpServletRequest req,HttpServletResponse res)
    throws ServletException, IOException{
        try
        {
            res.setContentType("text/html");
           pw=res.getWriter();
            
            
            String username= req.getParameter("userName");
            String useremail=req.getParameter("userEmail");
            String userpassword=req.getParameter("userpassword");
            int user_id=Integer.parseInt(req.getParameter("user_id"));
             
//            Class.forName("com.mysql.cj.jdbc.Driver");
             
            con=connection_pool.getDataSource().getConnection();
            
            String query = "insert into user(user_id,name,email,password) values (?,?,?,?)";
            ps=con.prepareStatement(query);
            ps.setInt(1,user_id);
            ps.setString(2,username);
            ps.setString(3,useremail);
            ps.setString(4,userpassword);
            
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
                pw.print("<h1>Something went wrong! Please try again.</h1>");
                pw.print("<a href='signup.html'>Try Again</a>");
                pw.print("</div>");

                pw.print("</body></html>");
            }
            else 
            {
//                ps=con.prepareStatement("insert into wallet(user_id) values(?)");
//                ps.setInt(1,user_id);
//                ps.executeUpdate();

                pw.print("<html><head>");
                pw.print("<style>");
                pw.print("body{font-family:Arial;background:linear-gradient(120deg,#74ebd5,#ACB6E5);height:100vh;display:flex;justify-content:center;align-items:center;margin:0;}");
                pw.print(".card{background:white;padding:40px;border-radius:10px;box-shadow:0 4px 15px rgba(0,0,0,0.2);text-align:center;}");
                pw.print("a{display:inline-block;margin-top:15px;padding:10px 15px;background:#4CAF50;color:white;text-decoration:none;border-radius:5px;}");
                pw.print("a:hover{background:#45a049;}");
                pw.print("</style>");
                pw.print("</head><body>");

                pw.print("<div class='card'>");
                pw.print("<h1>Signup Successful</h1>");
                pw.print("<a href='index.html'>Go To Home</a>");
                pw.print("</div>");

                pw.print("</body></html>");
            }
//            ps=con.prepareStatement("select user_id ");
//            req.setAttribute("user_id",user_id);
//            RequestDispatcher rd=req.getRequestDispatcher("insert_into_wallet.html");
            con.close();
//            rd.forward(req,res);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
