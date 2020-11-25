package sample.MyFriends;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.DbConnection;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class MeetingController implements Initializable {

    @FXML
    private Button confirmButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label blankLabel;
    @FXML
    private Label myusername;
    @FXML
    private Label friendsusername;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField distance;
    @FXML
    private ImageView togetherImage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file = new File("Images/tgt.png");
        Image tgtImage = new Image(file.toURI().toString());
        togetherImage.setImage(tgtImage);
    }

    //set myusetname
    public void setMyusername(String username){
        myusername.setText(username);
    }
    //set friends username
    public void setFriendsusername(String friendsname){
        friendsusername.setText(friendsname);
    }

    //create meeting
    public void createMeeting(ActionEvent actionEvent){
        String dstnc = distance.getText();

        if(Character.isDigit(dstnc.charAt(0)) && Character.isDigit(dstnc.charAt(dstnc.length()-1))){
            if(dstnc.contains(".")){
                String splited[] = dstnc.split("\\.");
                if (splited.length==2){
                    boolean isDigit=true;
                    breakeloop:
                    for (int i = 0 ; i < splited.length; i++){
                        for (int j = 0; j < splited[i].length(); j++){
                            if(Character.isDigit(splited[i].charAt(j))){
                                isDigit = true;
                            }
                            else{
                                isDigit = false;
                                break breakeloop;
                            }
                        }
                    }
                    if(isDigit){
                        DbConnection dbConnection = new DbConnection();
                        Connection connection = dbConnection.getConnection();

                        try {
                            Statement statement = connection.createStatement();
                            String update = "insert into meeting(myname, friendname,datum,distance) values ('"+myusername.getText()+"','"+friendsusername.getText()+"','"+datePicker.getValue()+"','"+distance.getText()+"')";
                            statement.executeUpdate(update);

                          Stage stage = (Stage) confirmButton.getScene().getWindow();
                          stage.close();

                          Stage seccessfulmeetingStage = new Stage();

                          try {
                              FXMLLoader fxmlLoader = new FXMLLoader(SuccessfulMeetingController.class.getResource("successfulmeeting.fxml"));
                              Parent root = (Parent) fxmlLoader.load();

                              SuccessfulMeetingController successfulMeetingController = fxmlLoader.getController();
                              successfulMeetingController.setMyusername(myusername.getText());
                              successfulMeetingController.setFriendusername(friendsusername.getText());

                              seccessfulmeetingStage.setScene(new Scene(root,450,250));
                              seccessfulmeetingStage.initStyle(StageStyle.UNDECORATED);
                              seccessfulmeetingStage.show();
                          }
                          catch (IOException ioe){
                              System.out.println(ioe.getMessage());
                              ioe.printStackTrace();
                          }

                        }
                        catch (SQLException sql){
                            System.out.println(sql.getMessage());
                            sql.printStackTrace();
                        }
                    }
                    else {

                       blankLabel.setText("Unexpected format for distance, hold mouse on field and see right format");
                    }

                }
                else{

                   blankLabel.setText("Unexpected format for distance, hold mouse on field and see right format");
                }

            }
            else {

               blankLabel.setText("Unexpected format for distance, hold mouse on field and see right format");
            }
        }
        else{

            blankLabel.setText("Unexpected format for distance, hold mouse on field and see right format");
        }
    }


    //go back to friends
    public void goBackToFriends(ActionEvent actionEvent){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();

        Stage listOfFriendsStage = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MyFriendsControll.class.getResource("myfriends.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            MyFriendsControll myFriendsControll = fxmlLoader.getController();
            myFriendsControll.setUsername(myusername.getText());

            DbConnection dbConnection = new DbConnection();
            Connection connection = dbConnection.getConnection();

            try {
                Statement statement = connection.createStatement();
                String abfrage = "Select * from runfrieds where myname='"+myusername.getText()+"'";
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

            listOfFriendsStage.setScene(new Scene(root,364,520));
            listOfFriendsStage.initStyle(StageStyle.UNDECORATED);
            listOfFriendsStage.show();

        }
        catch (IOException ioe){
            System.out.println(ioe.getMessage());
            ioe.printStackTrace();
        }
    }

}
