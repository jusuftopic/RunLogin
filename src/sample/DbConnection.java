package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    private final String url="jdbc:postgresql://localhost/RunLog";
    private final String username="postgres";
    private final String password ="database_123";

    public Connection getConnection(){
        Connection connection = null;
        try{
            Class.forName("org.postgresql.Driver");
            connection=DriverManager.getConnection(url,username,password);

        }
        catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        if(connection!=null){
            System.out.println("Connected");

        }

        return connection;
    }


    public static void main(String[] args) {
        DbConnection dbConnection = new DbConnection();
        dbConnection.getConnection();
    }






}
