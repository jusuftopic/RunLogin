package sample.StatisticsMenu.TotalScore;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.postgresql.core.SqlCommand;
import sample.DbConnection;
import sample.FriendProfile.FriendProfileController;
import sample.StatisticsMenu.StatisticsMenu;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class TotalScoreController {
    @FXML
    private Button cancleOnStatistics;
    @FXML
    private TextField totalkilometers;
    @FXML
    private Label userID;
    @FXML
    private TextField calories;
    @FXML
    private TextField totaltrainings;
    @FXML
    private TextField totaltime;


    //set username unvisible
    public void setVisible(){
        userID.setVisible(false);
    }
    //to statistics menu
    public void cancleOnStatisticsOnAction(ActionEvent actionEvent){
        Stage stage = (Stage) cancleOnStatistics.getScene().getWindow();
        stage.close();

        Stage statisticsMenuStage = new Stage();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StatisticsMenu.class.getResource("statisticsmenu..fxml"));
            Parent root = (Parent) fxmlLoader.load();

            StatisticsMenu statisticsMenu = fxmlLoader.getController();
            statisticsMenu.setUsername(userID.getText());

            statisticsMenuStage.setScene(new Scene(root,364,517));
            statisticsMenuStage.initStyle(StageStyle.UNDECORATED);
            statisticsMenuStage.show();

        }
        catch (IOException ioe){
            System.out.println(ioe.getMessage());
        }
    }

    //set distance
         public void setTotalKilometers(){
       DbConnection dbConnection = new DbConnection();
       Connection connection = dbConnection.getConnection();

       try {
           Statement statement = connection.createStatement();
           String abfrage = "Select * from stat where username='"+userID.getText()+"'";
           ResultSet resultSet = statement.executeQuery(abfrage);
           double distanceinall=0;
           while(resultSet.next()){
               double distance = Double.parseDouble(resultSet.getString(2));
               distanceinall+=distance;
           }

           String result= String.format("%.2f",distanceinall);

           totalkilometers.setText(result);

        }
        catch (SQLException sql){
            System.out.println("aa");
            System.out.println(sql.getMessage());
            sql.printStackTrace();
        }

    }

    //set calories
    public void setCalories(){
        DbConnection dbConnection = new DbConnection();
        Connection connection = dbConnection.getConnection();


        try {
            String user = userID.getText();
            Statement statement = connection.createStatement();
            String abfrage="select sum(calories) from stat where username='"+user+"'";
            ResultSet resultSet = statement.executeQuery(abfrage);
            if (resultSet.next()){
                calories.setText(resultSet.getString(1));
            }
        }
        catch (SQLException sql){
            System.out.println(sql.getMessage());
            sql.printStackTrace();
        }
    }
    public void setCal(){
        calories.setText("aa");
    }

    //set total trainings
    public void setTotalTrainings(){
        DbConnection dbConnection = new DbConnection();
        Connection connection = dbConnection.getConnection();

        try {
            String user = userID.getText();

            Statement statement = connection.createStatement();
            String abfrage = "select count(*) from stat where username='"+user+"'";
            ResultSet resultSet = statement.executeQuery(abfrage);
            if (resultSet.next()){
                totaltrainings.setText(resultSet.getString(1));

            }
        }
        catch (SQLException sql){
            System.out.println(sql.getMessage());
            sql.printStackTrace();
        }

    }

    //set total time
    public void setTotaltime(){
        DbConnection dbConnection = new DbConnection();
        Connection connection = dbConnection.getConnection();

        try {
            Statement statement = connection.createStatement();
            String abfrage = "select timeofrace  from stat where username='"+userID.getText()+"'";
            ResultSet resultSet = statement.executeQuery(abfrage);
             double result=0;
            while (resultSet.next()){
                int counter = 0;
                String time = resultSet.getString(1);

                for(int i = 0; i < time.length(); i++){
                    if (!Character.isDigit(time.charAt(i))){
                        counter++;
                    }
                }
                totaltime.setText(Integer.toString(counter));

              if (counter==1){
                    String [] splitedString = time.split(":");
                    double minuteToSeconds = Double.parseDouble(splitedString[0]) * 60;
                    double seconds = Double.parseDouble(splitedString[1]) + minuteToSeconds;

                    result+=seconds;
                }
                else if(counter==2){
                    String[] aplitedString = time.split(":");
                    double hoursToMinutes = Double.parseDouble(aplitedString[0]) * 60;
                    double minutes = Double.parseDouble(aplitedString[1]) + hoursToMinutes;
                    minutes = minutes * 60;
                    double seconds = Double.parseDouble(aplitedString[2])+minutes;

                    result+=seconds;
                }
            }

            if(result<3600){
                StringBuilder stringBuilder = new StringBuilder();
                int minutes = (int) result / 60;
                int seconds = (int) result%60;
                stringBuilder.append(minutes);
                stringBuilder.append(":");

                if(Integer.toString(seconds).length()==1){
                    String scs = 0+Integer.toString(seconds);
                    stringBuilder.append(scs);
                }
                else{
                    stringBuilder.append(seconds);
                }

                totaltime.setText(stringBuilder.toString());

            }
            else if(result>3600){
                StringBuilder stringBuilder = new StringBuilder();
                int hours = (int) result/3600;
                stringBuilder.append(hours);
                stringBuilder.append(":");

                int rest =(int) result%3600;

                int minutes = rest/60;
                if(Integer.toString(minutes).length()==1){
                    String mins = 0+Integer.toString(minutes);
                    stringBuilder.append(mins);
                    stringBuilder.append(":");
                }
                else{
                    stringBuilder.append(minutes);
                    stringBuilder.append(":");
                }

                int seconds = rest%60;
                if (Integer.toString(seconds).length()==1){
                    String secs = 0+Integer.toString(seconds);
                    stringBuilder.append(secs);
                }
                else{
                    stringBuilder.append(seconds);}
                totaltime.setText(stringBuilder.toString());
            }
        }
        catch (SQLException sql){
            System.out.println(sql.getMessage());
            sql.printStackTrace();
        }


    }


    //set username
    public void setUser(String user){
        userID.setText(user);
    }

    public Button getCancleOnStatistics() {
        return cancleOnStatistics;
    }

    public TextField getTotalkilometers() {
        return totalkilometers;
    }

    public Label getUserID() {
        return userID;
    }

    public TextField getCalories() {
        return calories;
    }

    public TextField getTotaltrainings() {
        return totaltrainings;
    }

    public TextField getTotaltime() {
        return totaltime;
    }
}
