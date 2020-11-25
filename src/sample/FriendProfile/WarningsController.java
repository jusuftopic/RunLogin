package sample.FriendProfile;

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
import sample.DbConnection;
import sample.MyFriends.MyFriendsControll;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class WarningsController implements Initializable {
    @FXML
    private ImageView warningImage;
    @FXML
    private Button goBack;
    @FXML
    private Button goToList;
    @FXML
    private Label friendsname;
    @FXML
    private Label myusername;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file = new File("Images/warning.png");
        Image image = new Image(file.toURI().toString());
        warningImage.setImage(image);
    }

    //set name of friend
    public void setFriendName(String name){
        friendsname.setText(name);
    }

    //set my username
    public void setMyUsername(String myUsername){
        myusername.setText(myUsername);
    }

    //go to List
    public void toListOfFriendsMenu(ActionEvent actionEvent){
        Stage stage = (Stage) goToList.getScene().getWindow();
        stage.close();

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
                    if(resultSet.getRow()>5){
                        myFriendsControll.getMoreFriends().setVisible(true);
                    }

                }

            }
            catch (SQLException sql){
                System.out.println(sql.getMessage());
                sql.printStackTrace();
            }

            Stage friendprofileStage = new Stage();
            friendprofileStage.setScene(new Scene(root,364,517));
            friendprofileStage.initStyle(StageStyle.UNDECORATED);
            friendprofileStage.show();
        }
        catch (IOException ioe){
            System.out.println(ioe.getMessage());
            ioe.printStackTrace();
        }
    }

    //go back to friend
    public void backToProfile(ActionEvent actionEvent){
        Stage stage = (Stage) goBack.getScene().getWindow();
        stage.close();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(FriendProfileController.class.getResource("friendprofile.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            FriendProfileController friendProfileController = fxmlLoader.getController();
            friendProfileController.setMyUsername(myusername.getText());

            Stage friendprofileStage = new Stage();
            friendprofileStage.setScene(new Scene(root,450,517));
            friendprofileStage.initStyle(StageStyle.UNDECORATED);
            friendprofileStage.show();
        }

        catch (IOException ioe){
            System.out.println(ioe.getMessage());
            ioe.printStackTrace();
        }


    }
}
