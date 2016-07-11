package sample;

import java.sql.*;

public class Controller {
    public void st(){
        MysqlCon.run();
    }

}


class MysqlCon{
    public static void run(){
        try{
            Class.forName("com.mysql.jdbc.Driver");

            Connection con=DriverManager.getConnection(
                    "jdbc:mysql://192.168.2.0:3306/DBMS","hacker9500","Heha@123");

//here sonoo is database name, root is username and password

//            Statement stmt=con.createStatement();
//
//            ResultSet rs=stmt.executeQuery("select * from emp");
//
//            while(rs.next())
//                System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));

            System.out.println("connected");

            con.close();

        }catch(Exception e){ System.out.println(e);
            System.out.println("error");
        }

    }
}