package sample.FriendProfile;

import javafx.event.ActionEvent;
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
import sample.DbConnection;
import sample.FindFriends.FindFriendsController;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class FriendProfileController implements Initializable {
    @FXML
    private ImageView avatarView;
    @FXML
    private Label friendusername;
    @FXML
    private Button cancel;
    @FXML
    private TextField email;
    @FXML
    private Label username;
    @FXML
    private TextField weight;
    @FXML
    private TextField height;
    @FXML
    private TextField age;
    @FXML
    private Label blankLabel;
    @FXML
    private TextField totaldistance;
    @FXML
    private TextField totaltime;
    @FXML
    private TextField totaltrainings;
    @FXML
    private TextField calorieField;
    @FXML
    private Button addFriend;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file = new File("Images/avatar2.png");
        Image image = new Image(file.toURI().toString());
        avatarView.setImage(image);
    }

    public void setUsername(String usernameoffriend) {
        friendusername.setText(usernameoffriend);
    }

    public void setMyUsername(String name) {
        username.setText(name);
    }

    //set personal dates
    public void personaldates() {
        DbConnection dbConnection = new DbConnection();
        Connection connection = dbConnection.getConnection();

        try {
            Statement statement = connection.createStatement();
            String abfrage = "select * from run where nameofuser='" + friendusername.getText() + "'";
            ResultSet resultSet = statement.executeQuery(abfrage);

            if (resultSet.next()) {
                if (resultSet.getRow() == 1) {
                    email.setText(resultSet.getString(3));
                    weight.setText(resultSet.getString(5));
                    height.setText(resultSet.getString(6));
                    age.setText(Integer.toString(resultSet.getInt(7)));
                }
            } else {
                blankLabel.setText("Error!");
            }


        } catch (SQLException sql) {
            System.out.println(sql.getMessage());
            sql.printStackTrace();
        }
    }

    //set field for total distance
    public void fillTotalDistanceField(){
        DbConnection dbConnection = new DbConnection();
        Connection connection = dbConnection.getConnection();

        try {
            Statement statement = connection.createStatement();
            String abfrage ="select kilometers from stat where username='"+friendusername.getText()+"'";
            ResultSet resultSet = statement.executeQuery(abfrage);

            double result=0;
            while(resultSet.next()){
                String distance = resultSet.getString(1);
                double distanceInDouble= Double.parseDouble(distance);
                result+=distanceInDouble;
            }

            totaldistance.setText(Double.toString(result));
        }
        catch (SQLException sql){
            System.out.println(sql.getMessage());
            sql.printStackTrace();
        }
    }

    //set field for tatal trainings
    public void fillTotalTrainingsField() {
        DbConnection dbConnection = new DbConnection();
        Connection connection = dbConnection.getConnection();

        try {
            Statement statement = connection.createStatement();
            String abfrage = "Select * from stat where username='" + friendusername.getText() + "'";
            ResultSet resultSet = statement.executeQuery(abfrage);

            int trainings = 0;

            while (resultSet.next()) {
                trainings = resultSet.getRow();
            }

            totaltrainings.setText(Integer.toString(trainings));
        } catch (SQLException sql) {
            System.out.println(sql.getMessage());
            sql.printStackTrace();
        }
    }

    //set total time field
    public void fillTotalTimeField(){
        DbConnection dbConnection = new DbConnection();
        Connection connection = dbConnection.getConnection();

        try{
            Statement statement = connection.createStatement();
            String abfrage = "Select timeofrace from stat where username='"+friendusername.getText()+"'";
            ResultSet resultSet = statement.executeQuery(abfrage);

            int result = 0;

            while (resultSet.next()){
                int counter = 0;
                String time = resultSet.getString(1);

                for(int i = 0; i < time.length(); i++){
                    if(!Character.isDigit(time.charAt(i)))
                    counter++;
                }

                if(counter==1){
                    String[] splitMinutes = time.split("\\:");
                    int minute = Integer.parseInt(splitMinutes[0]);
                    int seconds = Integer.parseInt(splitMinutes[1]);

                    minute = minute * 60;
                    seconds = minute+seconds;

                    result+=seconds;
                }
                else if(counter==2){
                    String[] splitedHourseMinutes = time.split("\\:");

                    int hourse = Integer.parseInt(splitedHourseMinutes[0]);
                    int minutes = Integer.parseInt(splitedHourseMinutes[1]);
                    int seconds = Integer.parseInt(splitedHourseMinutes[2]);

                    hourse = hourse * 60;
                    minutes = minutes+hourse;
                    int minutesToSeconds = minutes * 60;
                    seconds = seconds+minutesToSeconds;

                    result+=seconds;
                }
            }

          //  totaltime.setText(Integer.toString(result));

            if(result<3600){
                StringBuilder stringBuilder = new StringBuilder();

                double minutes = result/60;
                double seconds = result%60;

                String sec = Integer.toString((int)seconds);
                if(sec.length()==1){
                    sec = "0"+sec;
                }



                stringBuilder.append((int)minutes);
                stringBuilder.append(":");
                stringBuilder.append(sec);


                totaltime.setText(stringBuilder.toString());
            }
            else{
               StringBuilder stringBuilder = new StringBuilder();
                double hours = result/3600;
                stringBuilder.append((int)hours);
                stringBuilder.append(":");

                double restOfHourse = result%3600;
                double minutes = restOfHourse/60;

                String mins = Integer.toString((int)minutes);

                if(mins.length()==1){
                    mins=0+mins;
                    stringBuilder.append(mins);
                    stringBuilder.append(":");
                }
                else{
                    stringBuilder.append((int) minutes);
                    stringBuilder.append(":");
                }

                double seconds = restOfHourse%60;
                String secs = Integer.toString((int) seconds);

                if(secs.length()==1){
                    secs=0+secs;
                    stringBuilder.append(secs);
                }
                else{
                    stringBuilder.append((int) seconds);

                }



               totaltime.setText(stringBuilder.toString());
              //totaltime.setText(Double.toString(result));

            }



        }
        catch (SQLException sql){
            System.out.println(sql.getMessage());
            sql.printStackTrace();
        }
    }

    //set field for total calories
    public void fillCalorieField() {
        DbConnection dbConnection = new DbConnection();
        Connection connection = dbConnection.getConnection();

        try {
            Statement statement = connection.createStatement();
            String abfrage = "Select calories from stat where username='" + friendusername.getText() + "'";
            ResultSet resultSet = statement.executeQuery(abfrage);

            int calorieCounter = 0;

            while (resultSet.next()) {
                calorieCounter += resultSet.getInt(1);
            }

            calorieField.setText(Integer.toString(calorieCounter));

        } catch (SQLException sql) {
            System.out.println(sql.getMessage());
            sql.printStackTrace();
        }

    }

    //back to controller to find friend
    public void backToFindFriendController(ActionEvent actionEvent) {
        Stage backtofindfriendstage = (Stage) cancel.getScene().getWindow();
        backtofindfriendstage.close();

        Stage findfriendstage = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(FindFriendsController.class.getResource("findfriends.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            FindFriendsController findFriendsController = fxmlLoader.getController();
            findFriendsController.setUsername(username.getText());


            findfriendstage.setScene(new Scene(root, 364, 517));
            findfriendstage.initStyle(StageStyle.UNDECORATED);
            findfriendstage.show();

        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            ioe.printStackTrace();
        }
    }

    //add friends
    public void addFriends(ActionEvent actionEvent){
        DbConnection dbConnection = new DbConnection();
        Connection connection = dbConnection.getConnection();

        try {
            Statement statement1 = connection.createStatement();
            String check = "Select * from runfrieds where friendname='"+friendusername.getText()+"' and myname='"+username.getText()+"'";
            ResultSet resultSet = statement1.executeQuery(check);

            if (resultSet.next()){
                try {

                    Stage exit = (Stage)addFriend.getScene().getWindow();
                    exit.close();

                    FXMLLoader fxmlLoader = new FXMLLoader(WarningsController.class.getResource("warning.fxml"));
                    Parent root = (Parent) fxmlLoader.load();

                    WarningsController warningsController = fxmlLoader.getController();
                    warningsController.setFriendName(friendusername.getText());
                    warningsController.setMyUsername(username.getText());


                    Stage warningStage = new Stage();
                    warningStage.setScene(new Scene(root,500,300));
                    warningStage.initStyle(StageStyle.UNDECORATED);
                    warningStage.show();
                }
                catch (IOException ioe){
                    System.out.println(ioe.getMessage());
                    ioe.printStackTrace();
                }
            }

            else {
                Statement updateStatement = connection.createStatement();
                String update ="Insert into runfrieds (friendname, myname, totaltrainings, totaldistance,totalcalories, totaltime) values ('"+friendusername.getText()+"','"+username.getText()+"','"+totaltrainings.getText()+"','"+totaldistance.getText()+"','"+calorieField.getText()+"','"+totaltime.getText()+"')";
               updateStatement.executeUpdate(update);
                getMessage();
            }


        }
        catch (SQLException sql){
            System.out.println(sql.getMessage());
            sql.printStackTrace();
        }
    }

    //get Congats message
    public void getMessage(){
        Stage congratsMessage = new Stage();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CongratWindow.class.getResource("congrat.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            CongratWindow congratWindow = fxmlLoader.getController();
            congratWindow.setFriednUsername(friendusername.getText());
            congratWindow.setUsername(username.getText());

            congratsMessage.setScene(new Scene(root,500,300));
            congratsMessage.initStyle(StageStyle.UNDECORATED);
            congratsMessage.show();
        }
        catch (IOException ioe){
            System.out.println(ioe.getMessage());
            ioe.printStackTrace();
        }
    }

    //set total distance
    public void setTotaldistance(String distance) {
        totaldistance.setText(distance);
    }

    //set text for total time
    public void setTotaltime(String totaltimes) {
        totaltime.setText(totaltimes);
    }

    //set text for total trainigs
    public void setTotaltrainings(String totaltraining) {
        totaltrainings.setText(totaltraining);
    }


}
