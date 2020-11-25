package sample.StatisticsMenu.AverageScore;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.postgresql.core.SqlCommand;
import sample.DbConnection;
import sample.StatisticsMenu.StatisticsMenu;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AverageScoreController implements Initializable {

    @FXML
    private ImageView averages;
    @FXML
    private Label user;
    private String userName;
    @FXML
    private Button backToStatisticsMenu;
    @FXML
    private TextField averageDistance;
    @FXML
    private TextField averageCalories;
    @FXML
    private TextField averageTime;
    @FXML
    private TextField averageTimePerKilometer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file = new File("Images/average.png");
        Image image = new Image(file.toURI().toString());
        averages.setImage(image);
    }

    //set average distance
    public void setAverageDistance() {
        DbConnection dbConnection = new DbConnection();
        Connection connection = dbConnection.getConnection();
        double result = 0;

        try {
            Statement statement = connection.createStatement();
            String abfrage = "select s.kilometers from stat s where username='" + user.getText() + "'";
            ResultSet resultSet = statement.executeQuery(abfrage);
            double counter = 0;


            while (resultSet.next()) {
                try {
                    Statement statement1 = connection.createStatement();
                    String count = "select count(*) from stat where username='" + user.getText() + "'";
                    ResultSet resultSet1 = statement1.executeQuery(count);

                    while (resultSet1.next()) {
                        counter = Double.parseDouble(resultSet1.getString(1));
                    }
                    double avg = result / counter;
                    String averagekilometers = String.format("%.2f", avg);
                    averageDistance.setText(averagekilometers);
                } catch (SQLException sql) {
                    System.out.println(sql.getMessage());
                    sql.printStackTrace();
                }
                double distance = Double.parseDouble(resultSet.getString(1));
                result += distance;
            }

            double avg = result / counter;
            String averagekilometers = String.format("%.2f", avg);
            averageDistance.setText(averagekilometers);
        } catch (SQLException sql) {
            System.out.println(sql.getMessage());
            sql.printStackTrace();
        }
    }

    //set average calories
    public void setAverageCalories() {
        DbConnection dbConnection = new DbConnection();
        Connection connection = dbConnection.getConnection();
        double result = 0;

        try {
            Statement statement = connection.createStatement();
            String abfrage = "select avg(calories) from stat where username ='" + user.getText() + "'";
            ResultSet resultSet = statement.executeQuery(abfrage);

            while (resultSet.next()) {
                result = resultSet.getInt(1);
            }
            // String roundedResult = String.format("%.2f", result);
            averageCalories.setText(Integer.toString((int) result));
        } catch (SQLException sql) {
            System.out.println(sql.getMessage());
            sql.printStackTrace();
        }

    }

    public void setAverageTime() {
        DbConnection dbConnection = new DbConnection();
        Connection connection = dbConnection.getConnection();

        try {
            Statement statement = connection.createStatement();
            String abfrage = "Select timeofrace from stat where username='" + user.getText() + "'";
            ResultSet resultSet = statement.executeQuery(abfrage);

            int result = 0;
            int trainings = 0;
            while (resultSet.next()) {
                int counter = 0;
                String time = resultSet.getString(1);

                for (int i = 0; i < time.length(); i++) {
                    if (!Character.isDigit(time.charAt(i))) {
                        counter++;
                    }

                }
                if (counter == 1) {
                    String[] getSeconds = time.split("\\:");
                    double minutes = Double.parseDouble(getSeconds[0]);
                    double minutesToSeconds = minutes * 60;
                    double seconds = Double.parseDouble(getSeconds[1]) + minutesToSeconds;
                    result += seconds;
                } else if (result == 2) {
                    String getSeconds[] = time.split(":");
                    double hours = Double.parseDouble(getSeconds[0]);
                    double hourseToMinutes = hours * 60;
                    double minutes = Double.parseDouble(getSeconds[1]) + hourseToMinutes;
                    double minutesToSecs = minutes * 60;
                    double seconds = Double.parseDouble(getSeconds[2]) + minutesToSecs;
                    result += seconds;
                }

            }

            try {
                Statement statement2 = connection.createStatement();
                String abfrage2 = "Select * from run where nameofuser='" + user.getText() + "'";
                ResultSet resultSet2 = statement2.executeQuery(abfrage2);
                while (resultSet2.next()) {
                    trainings = resultSet2.getRow();
                }
            } catch (SQLException sql) {
                System.out.println(sql.getMessage());
                sql.printStackTrace();
            }

            result = result / trainings;

            if (result < 3600) {
                StringBuilder stringBuilder = new StringBuilder();
                double minutes = result / 60;
                String mins = Integer.toString((int) minutes);

                if (mins.length() == 1) {
                    mins = 0 + mins;
                    stringBuilder.append(mins);
                    stringBuilder.append(":");
                } else {
                    stringBuilder.append((int) minutes);
                    stringBuilder.append(":");
                }

                double seconds = result % 60;
                String secs = Integer.toString((int) seconds);

                if (secs.length() == 1) {
                    secs = 0 + secs;
                    stringBuilder.append(secs);
                } else {
                    stringBuilder.append((int) seconds);
                }

                averageTime.setText(stringBuilder.toString());
            }

            else if(result>3600){
                StringBuilder stringBuilder = new StringBuilder();

                double hours = result/3600;
                stringBuilder.append((int)hours);
                stringBuilder.append(":");

                double minutes = (result%3600)/60;
                String minutesString = (Integer.toString((int)minutes));

                if(minutesString.length()==1){
                    minutesString=0+minutesString;
                    stringBuilder.append(minutesString);
                    stringBuilder.append(":");
                }
                else{
                    stringBuilder.append((int) minutes);
                    stringBuilder.append(":");
                }

                double seconds = (result%3600)%60;
                String secondsString = Integer.toString((int)seconds);

                if(secondsString.length()==1){
                    secondsString=0+secondsString;
                    stringBuilder.append(secondsString);
                }
                else{
                    stringBuilder.append((int)seconds);
                }

                averageTime.setText(stringBuilder.toString());
            }



        } catch (SQLException sql) {
            System.out.println(sql.getMessage());
            sql.printStackTrace();
        }
    }

    public void setAverageTimePerKilometer() {
        DbConnection dbConnection = new DbConnection();
        Connection connection = dbConnection.getConnection();

        try {
            Statement statement = connection.createStatement();
            String abfrage = "select timeperkilometers from stat where username='" + user.getText() + "'";
            ResultSet resultSet = statement.executeQuery(abfrage);
            int result = 0;

            while (resultSet.next()) {
                String perKilometer = resultSet.getString(1);
                String[] splitedTime = perKilometer.split(":");
                int minute = Integer.parseInt(splitedTime[0]);
                int intoSecond = minute * 60;
                int second = Integer.parseInt(splitedTime[1]);
                result += second + intoSecond;
            }
            try {
                Statement statement1 = connection.createStatement();
                String count = "select count(*) from stat where username='" + user.getText() + "'";
                ResultSet resultSet1 = statement1.executeQuery(count);

                while (resultSet1.next()) {
                    int counter = resultSet1.getInt(1);
                    double doubleresult = result / (double) counter;
                    if (doubleresult > 60) {
                        StringBuilder stringBuilder = new StringBuilder();
                        double intoMinutes = doubleresult / 60.0;
                        String average = String.format("%.2f", intoMinutes);
                        String[] avg = average.split(",");
                        stringBuilder.append(avg[0]);
                        stringBuilder.append(":");
                        stringBuilder.append(avg[1]);
                        averageTimePerKilometer.setText(stringBuilder.toString());
                    } else {
                        averageTimePerKilometer.setText(Double.toString(doubleresult));
                    }

                }
            } catch (SQLException sql) {
                System.out.println(sql.getMessage());
                sql.printStackTrace();
            }
        } catch (SQLException sql) {
            System.out.println(sql.getMessage());
            sql.printStackTrace();
        }
    }


    //exit to statistics menu
    public void exit() {
        Stage stage = (Stage) backToStatisticsMenu.getScene().getWindow();
        stage.close();

        Stage statisticsMenu = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StatisticsMenu.class.getResource("statisticsmenu..fxml"));
            Parent root = (Parent) fxmlLoader.load();

            StatisticsMenu statistics = fxmlLoader.getController();
            statistics.setUsername(user.getText());


            statisticsMenu.setScene(new Scene(root, 364, 517));
            statisticsMenu.initStyle(StageStyle.UNDECORATED);
            statisticsMenu.show();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            ioe.printStackTrace();
        }
    }


    //Set username
    public void setUserName(String username) {
        user.setText(username);
    }
}
