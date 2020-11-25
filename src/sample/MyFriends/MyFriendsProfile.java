package sample.MyFriends;

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

import javax.xml.crypto.dsig.dom.DOMValidateContext;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class MyFriendsProfile implements Initializable {
@FXML
private ImageView togetherLogo;
@FXML
private Label myusername;
@FXML
private Label friendsusername;
@FXML
private Button goBack;
@FXML
private Button addFriend;
@FXML
private TextField emailField;
@FXML
private TextField weightField;
@FXML
private TextField heightField;
@FXML
private TextField ageField;
@FXML
private TextField totaldistanceField;
@FXML
private TextField totaltimeField;
@FXML
private TextField totalTrainingsField;
@FXML
private TextField totalCaloriesField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file = new File("Images/togethertraininglogo.jpg");
        Image image = new Image(file.toURI().toString());
        togetherLogo.setImage(image);
    }


    //set my username
    public void setMyusername (String username){
        myusername.setText(username);
    }
    //set friends username
    public void setFriendsusername (String friendsname){
        friendsusername.setText(friendsname);
    }

    //call all methods to fill fields
    public void fillFields(){
        setPersonalDates();
        setTotaldistanceField();
        setTotaltileField();
        setTotalTrainingsField();
        setTotalCaloriesField();
    }

    //set personal dates
    public void setPersonalDates(){
        DbConnection dbConnection = new DbConnection();
        Connection connection = dbConnection.getConnection();

        try {
            Statement statement = connection.createStatement();
            String abfrage = "Select * from run where nameofuser='"+friendsusername.getText()+"'";
            ResultSet resultSet = statement.executeQuery(abfrage);

            while (resultSet.next()){
                emailField.setText(resultSet.getString(3));
                weightField.setText(resultSet.getString(5));
                heightField.setText(resultSet.getString(6));
                ageField.setText(Integer.toString(resultSet.getInt(7)));
            }
        }
        catch (SQLException sql){
            System.out.println(sql.getMessage());
            sql.printStackTrace();
        }
    }

    //set total distance
    public void setTotaldistanceField(){
        DbConnection dbConnection = new DbConnection();
        Connection connection = dbConnection.getConnection();

        try {
            Statement statement = connection.createStatement();
            String abfrage = "Select * from stat where username='"+friendsusername.getText()+"'";
            ResultSet resultSet = statement.executeQuery(abfrage);

            double result = 0;

            while (resultSet.next()){
                String distance = resultSet.getString(2);
                result+=Double.parseDouble(distance);
            }

            totaldistanceField.setText(Double.toString(result));
        }
        catch (SQLException sql){
            System.out.println(sql.getMessage());
            sql.printStackTrace();
        }
    }

    //set total time
    public void setTotaltileField(){
        DbConnection dbConnection = new DbConnection();
        Connection connection = dbConnection.getConnection();

        try {
            Statement statement = connection.createStatement();
            String abfrage = "Select timeofrace from stat where username='"+friendsusername.getText()+"'";
            ResultSet resultSet = statement.executeQuery(abfrage);

            double totalseconds = 0;

            while (resultSet.next()){
                String time = resultSet.getString(1);
                int counter =0;

                for(int i = 0; i < time.length(); i++){
                    if(!Character.isDigit(time.charAt(i))){
                        counter++;
                    }
                }

                if (counter==1){
                    String []divideTime= time.split(":");

                    double minutesToSeconds = Double.parseDouble(divideTime[0]) * 60;
                    double seconds = Double.parseDouble(divideTime[1]) + minutesToSeconds;

                    totalseconds+= seconds;
                }
                else if (counter==2){
                    String[] divideTime = time.split(":");

                    double hourseToMinutes = Double.parseDouble(divideTime[0])*60;
                    double minutesToSeconds = (Double.parseDouble(divideTime[1])+hourseToMinutes)*60;
                    double seconds = Double.parseDouble(divideTime[2]) + minutesToSeconds;

                    totalseconds += seconds;
                }
            }

            if(totalseconds< 3600){
                StringBuilder stringBuilder = new StringBuilder();
                int minutes = (int)totalseconds/60;

                stringBuilder.append(minutes);
                stringBuilder.append(":");

                int seconds = (int) totalseconds%60;
                if(Integer.toString(seconds).length()==1){
                    String secs = 0+Integer.toString(seconds);
                    stringBuilder.append(secs);
                }

                else{
                    stringBuilder.append(seconds);
                }

                totaltimeField.setText(stringBuilder.toString());
            }

            else {
                StringBuilder stringBuilder = new StringBuilder();

                int hours = (int) totalseconds/3600;
                stringBuilder.append(hours);
                stringBuilder.append(":");

                int rest = (int) totalseconds%3600;

                int minutes = rest/60;
                if(Integer.toString(minutes).length()==1){
                    String mnts = 0+Integer.toString(minutes);
                    stringBuilder.append(mnts);
                    stringBuilder.append(":");
                }
                else{
                    stringBuilder.append(minutes);
                    stringBuilder.append(":");
                }

                int seconds = rest%60;
                if(Integer.toString(seconds).length()==1){
                    String secs = 0+Integer.toString(seconds);
                    stringBuilder.append(secs);
                }
                else{
                    stringBuilder.append(seconds);
                }

                totaltimeField.setText(stringBuilder.toString());

            }
        }
        catch (SQLException sql){
            System.out.println(sql.getMessage());
            sql.printStackTrace();
        }

    }

    //set total trainings
    public void setTotalTrainingsField(){
        DbConnection dbConnection = new DbConnection();
        Connection connection = dbConnection.getConnection();

        try {
            Statement statement = connection.createStatement();
            String abfrage = "Select count(*) from stat where username='"+friendsusername.getText()+"'";
            ResultSet resultSet = statement.executeQuery(abfrage);

            while (resultSet.next()){
                totalTrainingsField.setText(Integer.toString(resultSet.getInt(1)));
            }
        }
        catch (SQLException sql){
            System.out.println(sql.getMessage());
            sql.printStackTrace();
        }
    }

    //set total calories
    public void setTotalCaloriesField(){
        DbConnection dbConnection = new DbConnection();
        Connection connection = dbConnection.getConnection();

        try {
            Statement statement = connection.createStatement();
            String abfrage = "Select sum(calories) from stat where username='"+friendsusername.getText()+"'";
            ResultSet resultSet = statement.executeQuery(abfrage);

            while (resultSet.next()){
                totalCaloriesField.setText(Integer.toString(resultSet.getInt(1)));
            }
        }
        catch (SQLException sql){
            System.out.println(sql.getMessage());
            sql.printStackTrace();
        }
    }

    //go and make arrange training together
    public void arrangeTrainingTogether(ActionEvent actionEvent){
        Stage stage = (Stage) addFriend.getScene().getWindow();
        stage.close();

        Stage makeTrainigTogetherStage = new Stage();
         try {
             FXMLLoader fxmlLoader = new FXMLLoader(MeetingController.class.getResource("meeting.fxml"));
             Parent root = (Parent) fxmlLoader.load();

             MeetingController meetingController = fxmlLoader.getController();
             meetingController.setMyusername(myusername.getText());
             meetingController.setFriendsusername(friendsusername.getText());

             makeTrainigTogetherStage.setScene(new Scene(root,500,270));
             makeTrainigTogetherStage.initStyle(StageStyle.UNDECORATED);
             makeTrainigTogetherStage.show();
         }
         catch (IOException ioe){
             System.out.println(ioe.getMessage());
             ioe.printStackTrace();
         }
    }

    //go back to list
    public void goBackToList(ActionEvent actionEvent){
        Stage stage =(Stage) goBack.getScene().getWindow();
        stage.close();

        Stage friendslistStage = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MyFriendsControll.class.getResource("myfriends.fxml"));
            Parent root =(Parent) fxmlLoader.load();

            MyFriendsControll myFriendsControll = fxmlLoader.getController();
            myFriendsControll.setUsername(myusername.getText());

            DbConnection dbConnection = new DbConnection();
            Connection connection = dbConnection.getConnection();

            try {
                Statement statement = connection.createStatement();
                String abfrage = "select * from runfrieds where myname='"+myusername.getText()+"'";
                ResultSet resultSet = statement.executeQuery(abfrage);

                while (resultSet.next()){
                    if (resultSet.getRow()==1){
                        myFriendsControll.setFriend1(resultSet.getString(1));
                    }
                    if(resultSet.getRow()==2){
                        myFriendsControll.setFriend2(resultSet.getString(1));
                    }
                    if(resultSet.getRow()==3){
                        myFriendsControll.setFriend3(resultSet.getString(1));
                    }
                    if(resultSet.getRow()==4){
                        myFriendsControll.setFriend4(resultSet.getString(1));
                    }
                    if(resultSet.getRow()==5){
                        myFriendsControll.setFriend5(resultSet.getString(1));
                    }
                }
            }
            catch (SQLException sql){
                System.out.println(sql.getMessage());
                sql.printStackTrace();
            }

            friendslistStage.setScene(new Scene(root,364,520));
            friendslistStage.initStyle(StageStyle.UNDECORATED);
            friendslistStage.show();
        }
        catch (IOException ioe){
            System.out.println(ioe.getMessage());
            ioe.printStackTrace();
        }
    }
}
