package sample.MyFriends;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TrainingOrProfileController implements Initializable {
    @FXML
    private Button makeWorkoutButton;
    @FXML
    private Button goToProfileButton;
    @FXML
    private Button goBack;
    @FXML
    private Label friendsusername;
    @FXML
    private Label myusername;
    @FXML
    private ImageView avatarImage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file = new File("Images/avatar2.png");
        Image image = new Image(file.toURI().toString());
        avatarImage.setImage(image);
    }

    //set username from friend
    public void setFriendsusername(String name){
        friendsusername.setText(name);
    }
    //set my username
    public void setMyusername(String username){
        myusername.setText(username);
    }

    //go to profile of friend
    public void goToProfile(ActionEvent actionEvent){
        Stage stage = (Stage) goToProfileButton.getScene().getWindow();
        stage.close();

        Stage profileStage = new Stage();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MyFriendsProfile.class.getResource("myfriendsprofile.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            MyFriendsProfile myFriendsProfile = fxmlLoader.getController();
            myFriendsProfile.setMyusername(myusername.getText());
            myFriendsProfile.setFriendsusername(friendsusername.getText());
            myFriendsProfile.fillFields();

            profileStage.setScene(new Scene(root,450,517));
            profileStage.initStyle(StageStyle.UNDECORATED);
            profileStage.show();

        }
        catch (IOException ioe){
            System.out.println(ioe.getMessage());
            ioe.printStackTrace();
        }
    }





}
