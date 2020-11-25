package sample.MyFriends;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.DbConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SuccessfulMeetingController {
    @FXML
    private Button cancle;

    @FXML
    private Label myuser;
    @FXML
    private Label friendusername;


    //set myusername
    public void setMyusername(String name){
        myuser.setText(name);
    }
    //set friends username
    public void setFriendusername(String name){
        friendusername.setText(name);
    }


    // go back
    public void  goBack(ActionEvent actionEvent){
        Stage stage = (Stage) cancle.getScene().getWindow();
        stage.close();

        Stage backStage = new Stage();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MyFriendsProfile.class.getResource("myfriendsprofile.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            MyFriendsProfile myFriendsProfile = fxmlLoader.getController();
            myFriendsProfile.setMyusername(myuser.getText());
            myFriendsProfile.setFriendsusername(friendusername.getText());
            myFriendsProfile.fillFields();

            backStage.setScene(new Scene(root,450,517));
            backStage.initStyle(StageStyle.UNDECORATED);
            backStage.show();
        }
        catch (IOException ioe){
            System.out.println(ioe.getMessage());
            ioe.printStackTrace();
        }
    }
}
