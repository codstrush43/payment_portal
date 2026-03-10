/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.*;
import com.connection.connection_pool;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import jakarta.servlet.RequestDispatcher;
//import jakarta.servlet.HttpSession;


@WebServlet("/login")
public class login extends HttpServlet{
    PreparedStatement ps;
    Connection con=null;
    PrintWriter pw;
    
    @Override
    public void doPost(HttpServletRequest req,HttpServletResponse res)
    throws ServletException,IOException{
        res.setContentType("text/html");
        pw=res.getWriter();
        try{
       int user_id=Integer.parseInt(req.getParameter("user_id"));
       String pass=req.getParameter("password");
       
       con=connection_pool.getDataSource().getConnection();
       String query="select password from user where user_id=?";
       ps=con.prepareStatement(query);
       ps.setInt(1,user_id);
       ResultSet rs=ps.executeQuery();
       
       if(rs.next())
       {
           if(rs.getString("password").equals(pass))
           {
               HttpSession session=req.getSession(true);
               session.setAttribute("user_id",user_id);
               session.setAttribute("pass",pass);
               RequestDispatcher rd=req.getRequestDispatcher("index.html");
               rd.forward(req,res);
           }
           else
            {
                pw.println("<html><head>");
                pw.println("<style>");
                pw.println("body{font-family:Arial;background:linear-gradient(120deg,#74ebd5,#ACB6E5);height:100vh;display:flex;justify-content:center;align-items:center;margin:0;}");
                pw.println(".card{background:white;padding:40px;border-radius:10px;box-shadow:0 4px 15px rgba(0,0,0,0.2);text-align:center;}");
                pw.println("a{display:inline-block;margin-top:15px;margin-right:10px;padding:10px 15px;background:#e74c3c;color:white;text-decoration:none;border-radius:5px;}");
                pw.println("a:hover{background:#c0392b;}");
                pw.println("</style>");
                pw.println("</head><body>");

                pw.println("<div class='card'>");
                pw.println("<h1>User not found<br>Please login again!!</h1>");
                pw.println("<a href='login.html'>Login</a>");
                pw.println("</div>");

                pw.println("</body></html>");
            }
       }
       else
        {
            pw.println("<html><head>");
            pw.println("<style>");
            pw.println("body{font-family:Arial;background:linear-gradient(120deg,#74ebd5,#ACB6E5);height:100vh;display:flex;justify-content:center;align-items:center;margin:0;}");
            pw.println(".card{background:white;padding:40px;border-radius:10px;box-shadow:0 4px 15px rgba(0,0,0,0.2);text-align:center;}");
            pw.println("a{display:inline-block;margin-top:15px;margin-right:10px;padding:10px 15px;background:#4CAF50;color:white;text-decoration:none;border-radius:5px;}");
            pw.println("a:hover{background:#45a049;}");
            pw.println("</style>");
            pw.println("</head><body>");

            pw.println("<div class='card'>");
            pw.println("<h1>User not found<br>Please login again!!</h1>");
            pw.println("<a href='login.html'>Login</a>");
            pw.println("<a href='sign-up.html'>Sign Up</a>");
            pw.println("</div>");

            pw.println("</body></html>");
        }
      }
        catch(Exception e)
        {
            pw.println("<html><head>");
            pw.println("<style>");
            pw.println("body{font-family:Arial;background:linear-gradient(120deg,#74ebd5,#ACB6E5);height:100vh;display:flex;justify-content:center;align-items:center;margin:0;}");
            pw.println(".card{background:white;padding:40px;border-radius:10px;box-shadow:0 4px 15px rgba(0,0,0,0.2);text-align:center;}");
            pw.println("</style>");
            pw.println("</head><body>");

            pw.println("<div class='card'>");
            pw.println("<h1>Something went wrong!!!</h1>");
            pw.println("</div>");

            pw.println("</body></html>");
        }
    }
}
