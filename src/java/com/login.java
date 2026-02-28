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
               RequestDispatcher rd=req.getRequestDispatcher("insert_into_wallet.html");
               rd.forward(req,res);
           }
           else
           {
               pw.println("<h1>User not founded<br> please login again!!</h1>");
               pw.println("<a href='login.html'>login</a>");
               
           }
       }else
       {
           pw.println("<h1>User not founded<br> please login again!!</h1>");
           pw.println("<a href='login.html'>login</a> <span> <a href='sign-up.html'>sign-up</a>  ");
//           pw.println("");?
       }
      }
        catch(Exception e)
        {
            pw.println("<h1>Something went wrong!!!</h1>");
        }
    }
}
