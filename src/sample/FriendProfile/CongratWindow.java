package sample.FriendProfile;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class CongratWindow implements Initializable {
    @FXML
    private ImageView partnerImageView;
    @FXML
    private Label username;
    @FXML
    private Label friednUsername;
    @FXML
    private Button goBack;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file = new File("Images/partners.png");
        Image partners = new Image(file.toURI().toString());
        partnerImageView.setImage(partners);
    }

    //set username
    public void setUsername(String name){
        username.setText(name);
    }
    //set friends username
    public void setFriednUsername(String usernameFriend){
        friednUsername.setText(usernameFriend);
    }



    //back to friend profile
    public void goBackToFriendsProfile(ActionEvent actionEvent){
        Stage back = (Stage) goBack.getScene().getWindow();
        back.close();
    }



}
