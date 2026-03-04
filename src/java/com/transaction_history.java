package com;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import jakarta.servlet.annotation.WebServlet;
import com.connection.connection_pool;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/transaction_history")
public class transaction_history extends HttpServlet {
    
    PreparedStatement ps=null;
    Connection con=null;
    
    @Override
    public void doPost(HttpServletRequest req,HttpServletResponse res)
    throws ServletException,IOException
    {
        res.setContentType("text/html");
        PrintWriter pw=res.getWriter();
        
        HttpSession hs=req.getSession(false);
        
        if(hs==null)
        {
            res.sendRedirect("/payment_portal/login.html");
            return;
        }
        
        Integer id=(Integer)hs.getAttribute("user_id");
        String pass=(String)hs.getAttribute("password");
        int user_id=id;
        try
        {
           
            con=connection_pool.getDataSource().getConnection();
            String query="SELECT * FROM transaction WHERE sender_id=? ORDER BY transaction_id DESC";
            ps=con.prepareStatement(query);
            ps.setInt(1,user_id);
          
            ResultSet rs=ps.executeQuery();
//            pw.println("------------------------------------------------------------");
//            pw.println("|<h1>transaction_id<h1>|<h1>sender_id</h1>|<h1>receiver_id</h1>|<h1>amount</h1>|<h1></h1>");
          
            while(rs.next())
            {
                int transaction_id=rs.getInt("transaction_id");
                int sender_id=rs.getInt("sender_id");
                int receiver_id=rs.getInt("receiver_id");
                int amount=rs.getInt("amount");
                String status=rs.getString("status");
                
                pw.print(""+transaction_id+""+" "+""+sender_id+""+" "+""+receiver_id+""+" "+""+amount+""+" "+""+status+"");
                pw.println("<br>");
            }
        }
        catch(SQLException e)
        {
            pw.println("<h1>something went wrong !!</h1>");
            System.out.println(e.getMessage());
        }
        
    }
}
