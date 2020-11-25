package sample.MyFriends;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MoreFriendsController implements Initializable {
    @FXML
    private Button cancle;
    @FXML
    private Label username;
    @FXML
    private ImageView imageFriend;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file = new File("Images/frend.jpg");
        Image image = new Image(file.toURI().toString());
        imageFriend.setImage(image);
    }

    //set my username
    public void setUsername(String name){
        username.setText(name);
    }



    //back to my friendscontroller
    public void backtoMyFriends(ActionEvent actionEvent){

    }
}
