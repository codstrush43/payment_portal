/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;


public class AuthFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request,ServletResponse response,FilterChain fc)
    throws ServletException,IOException{
        HttpServletRequest req=(HttpServletRequest)request;
        HttpServletResponse res=(HttpServletResponse)response;
        
        HttpSession session = req.getSession(false);
        if(session!=null)
        {
        Integer id = (Integer)(session.getAttribute("user_id"));
        String pass=(String)(session.getAttribute("pass"));
        if(id!=null && pass!=null)
        {
            fc.doFilter(request,response);
        }
        }
        else
        {
//            pw.println("<h1>User not founded<br> please login again!!</h1>");
//            pw.println("<a href='login.html'>login</a> <span> <a href='sign-up.html'>sign-up</a>  ");
            RequestDispatcher rd=req.getRequestDispatcher("login.html");
            rd.forward(req,res);
        }
    }
//    @Override
    public void destroy() {
        Filter.super.destroy(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

//    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
}
