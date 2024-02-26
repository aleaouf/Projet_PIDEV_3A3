package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class myconnection {

    private final  String url="jdbc:mysql://localhost:3306/viragecom";
    private final  String login="root";
    private final  String mdp="";
    public static myconnection instance;
    Connection cnx;
    private myconnection(){

        try {
            cnx = DriverManager.getConnection(url,login,mdp);
            System.out.println("connexion établie!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Connection getCnx() {
        return cnx;
    }
    public static myconnection getInstance(){
        if (instance==null){
            instance=new myconnection();
        }
        return instance;
    }
}