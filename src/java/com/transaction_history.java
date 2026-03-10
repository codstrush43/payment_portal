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
pw.println("<html>");
pw.println("<head>");
pw.println("<title>Transaction History</title>");

pw.println("<style>");
pw.println("body{margin:0;font-family:Arial;background:linear-gradient(120deg,#74ebd5,#ACB6E5);min-height:100vh;display:flex;justify-content:center;align-items:center;}");
pw.println(".card{background:white;padding:40px;border-radius:10px;box-shadow:0 4px 15px rgba(0,0,0,0.2);text-align:center;}");
pw.println("table{border-collapse:collapse;width:100%;}");
pw.println("th,td{padding:12px;border-bottom:1px solid #ddd;text-align:center;}");
pw.println("th{background:#4CAF50;color:white;}");
pw.println("tr:hover{background:#f5f5f5;}");
pw.println("a{display:inline-block;margin-top:20px;padding:10px 15px;background:#4CAF50;color:white;text-decoration:none;border-radius:5px;}");
pw.println("a:hover{background:#45a049;}");
pw.println("</style>");

pw.println("</head>");
pw.println("<body>");

pw.println("<div class='card'>");
pw.println("<h2>Transaction History</h2>");

pw.println("<table>");
pw.println("<tr>");
pw.println("<th>Transaction ID</th>");
pw.println("<th>Sender</th>");
pw.println("<th>Receiver</th>");
pw.println("<th>Amount</th>");
pw.println("<th>Status</th>");
pw.println("</tr>");
          
            while(rs.next())
            {
                int transaction_id=rs.getInt("transaction_id");
                int sender_id=rs.getInt("sender_id");
                int receiver_id=rs.getInt("receiver_id");
                int amount=rs.getInt("amount");
                String status=rs.getString("status");
                
//                pw.print(""+transaction_id+""+" "+""+sender_id+""+" "+""+receiver_id+""+" "+""+amount+""+" "+""+status+"");
//pw.println("<br>");
pw.println("<tr>");
pw.println("<td>"+transaction_id+"</td>");
pw.println("<td>"+sender_id+"</td>");
pw.println("<td>"+receiver_id+"</td>");
pw.println("<td>"+amount+"</td>");
pw.println("<td>"+status+"</td>");
pw.println("</tr>");
            }
            pw.println("</table>");
pw.println("<a href='index.html'>Go To Home</a>");
pw.println("</div>");
pw.println("</body>");
pw.println("</html>");
        }
        catch(SQLException e)
        {
            pw.println("<h1>something went wrong !!</h1>");
            System.out.println(e.getMessage());
        }
        
    }
}
