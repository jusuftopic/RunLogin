package sample.FindFriends;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.DbConnection;
import sample.FriendProfile.FriendProfileController;
import sample.MainController.MainController;
import sample.MyFriends.MyFriendsControll;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class FindFriendsController implements Initializable {
    @FXML
    private ImageView friendsPhotoView;
    @FXML
    private Button backToMain;
    @FXML
    private Label username;
    @FXML
    private Button search;
    @FXML
    private CheckBox nameUnknow;
    @FXML
    private TextField usernameOfFriend;
    @FXML
    private Label blankText;
    @FXML
    private VBox vBox;
    @FXML
    private TextField emailfield;
    @FXML
    private Hyperlink linkTomail;
    @FXML
    private Label hiddenEmailfield;
    @FXML
    private Label trueOrFalse;
    @FXML
    private Hyperlink myFriends;





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file = new File("Images/98882361-friends-running-logo-vector.jpg");
        Image friendsPhoto = new Image(file.toURI().toString());
        friendsPhotoView.setImage(friendsPhoto);
    }

    public void setUsername(String string) {
        username.setText(string);
    }

    //get email of user
   /* public void emailOfUser(){
        DbConnection dbConnection = new DbConnection();
        Connection connection = dbConnection.getConnection();

        try{
            Statement statement = connection.createStatement();
            String abfrage = "select emailofuser from run where nameofuser='"+username.getText()+"'";
            ResultSet resultSet = statement.executeQuery(abfrage);

            while(resultSet.next()){
                if(resultSet.getRow()==1){
                    String email = resultSet.getString(1);
                    hiddenEmailfield.setText(email);
                }
            }


        }
        catch (SQLException sql){
            System.out.println(sql.getMessage());
            sql.printStackTrace();
        }
    }*/

    //find your friend vie email
  /*  public boolean findFriendAsEmail(ActionEvent actionEvent){
      if(linkTomail.isVisited()){
        if(nameUnknow.isSelected()){
            vBox.setVisible(true);
            return true;
        }
        else {
            return false;
        }

    } return false;}*/

  public void findFriendAsEmail(ActionEvent actionEvent){
      if(linkTomail.isVisited()){
          if (nameUnknow.isSelected()){
              vBox.setVisible(true);
              trueOrFalse.setText("true");
          }
          else{
              trueOrFalse.setText("false");
          }
      }

  }



    //find your friend
    public void searchYourFriend(ActionEvent actionEvent) {
        if (!usernameOfFriend.getText().isEmpty() && !nameUnknow.isSelected()) {
            DbConnection dbConnection = new DbConnection();
            Connection connection = dbConnection.getConnection();

            try {
                Statement statement = connection.createStatement();
                String abfrage = "select * from run where nameofuser ='" + usernameOfFriend.getText() + "'";
                ResultSet resultSet = statement.executeQuery(abfrage);

                if (resultSet.next()) {
                    if (resultSet.getRow() == 1) {
                         if(!resultSet.getString(1).equals(username.getText())){
                        Stage stage = (Stage) search.getScene().getWindow();
                        stage.close();
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(FriendProfileController.class.getResource("friendprofile.fxml"));
                            Parent root =(Parent) fxmlLoader.load();

                            FriendProfileController friendProfileController = fxmlLoader.getController();
                            friendProfileController.setUsername(resultSet.getString(1));
                            friendProfileController.setMyUsername(username.getText());
                            friendProfileController.personaldates();
                            friendProfileController.fillCalorieField();
                            friendProfileController.fillTotalTrainingsField();
                            friendProfileController.fillTotalDistanceField();
                            friendProfileController.fillTotalTimeField();

                            Stage friendstage = new Stage();
                            friendstage.setScene(new Scene(root,450,517));
                            friendstage.initStyle(StageStyle.UNDECORATED);
                            friendstage.show();

                        }
                        catch (IOException ioe){
                            System.out.println(ioe.getMessage());
                            ioe.printStackTrace();
                        }
                    }
                         else{
                             blankText.setText("You can't add yourself as a friend");
                         }
                    }

                } else {
                    blankText.setText("There is no user with such a username");
                }
            } catch (SQLException sql) {
                System.out.println(sql.getMessage());
                sql.printStackTrace();

            }
        }
        else if(usernameOfFriend.getText().isEmpty() && trueOrFalse.getText().equals("true")){
            DbConnection dbConnection = new DbConnection();
            Connection connection = dbConnection.getConnection();

            try{
                Statement statement = connection.createStatement();
                String abfrage = "select * from run where emailofuser='"+emailfield.getText()+"'";
                ResultSet resultSet = statement.executeQuery(abfrage);

                if(resultSet.next()){
                    if (resultSet.getRow()==1){
                       Statement statement2 = connection.createStatement();
                       String abfrage2="select emailofuser from run where nameofuser='"+username.getText()+"'";
                       ResultSet resultSet2 = statement2.executeQuery(abfrage2);

                       while(resultSet2.next()){
                           if(resultSet2.getRow()==1){
                               hiddenEmailfield.setText(resultSet2.getString(1));
                               if(!resultSet.getString(3).equals(hiddenEmailfield.getText())){
                                   Stage exit = (Stage) search.getScene().getWindow();
                                   exit.close();

                                   Stage profile = new Stage();
                                   try {
                                       FXMLLoader fxmlLoader = new FXMLLoader(FriendProfileController.class.getResource("friendprofile.fxml"));
                                       Parent root = (Parent) fxmlLoader.load();

                                       FriendProfileController friendProfileController = fxmlLoader.getController();
                                       friendProfileController.setUsername(username.getText());
                                       friendProfileController.personaldates();
                                       friendProfileController.fillCalorieField();
                                       friendProfileController.fillTotalTrainingsField();
                                       friendProfileController.fillTotalDistanceField();
                                       friendProfileController.fillTotalTimeField();

                                       profile.setScene(new Scene(root,450,517));
                                       profile.initStyle(StageStyle.UNDECORATED);
                                       profile.show();
                                   }
                                   catch (IOException ioe){
                                       System.out.println(ioe.getMessage());
                                       ioe.printStackTrace();
                                   }
                               }
                               else if(resultSet.getString(1).equals(hiddenEmailfield.getText())){
                                   blankText.setText("You can't add yourself as a friend");
                               }

                           }
                       }
                }

            }
            else{
                blankText.setText("There is no friend with such an email address");
                }}
            catch (SQLException sql){
                System.out.println(sql.getMessage());
                sql.printStackTrace();
            }
        }
    }

    //see all my friends
    public void listOfFriends(ActionEvent actionEvent){
      Stage stage = (Stage)myFriends.getScene().getWindow();
      stage.close();

      Stage myfriendsstage = new Stage();

      try {
          FXMLLoader fxmlLoader = new FXMLLoader(MyFriendsControll.class.getResource("myfriends.fxml"));
          Parent root = (Parent) fxmlLoader.load();

          MyFriendsControll myFriendsControll = fxmlLoader.getController();
          myFriendsControll.setUsername(username.getText());

          DbConnection dbConnection = new DbConnection();
          Connection connection = dbConnection.getConnection();

          try {
              Statement statement = connection.createStatement();
              String abfrage = "Select * from runfrieds where myname = '"+username.getText()+"'";
              ResultSet resultSet = statement.executeQuery(abfrage);

              while (resultSet.next()){
                  if(resultSet.getRow()==1){
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

          myfriendsstage.setScene(new Scene(root,364,517));
          myfriendsstage.initStyle(StageStyle.UNDECORATED);
          myfriendsstage.show();
      }
      catch (IOException ioe){
          System.out.println(ioe.getMessage());
          ioe.printStackTrace();
      }

    }

    //back to main
    public void backToMain(ActionEvent actionEvent) {
        Stage stage = (Stage) backToMain.getScene().getWindow();
        stage.close();

        Stage main = new Stage();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("maincontroller.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            MainController mainController = fxmlLoader.getController();

            DbConnection dbConnection = new DbConnection();
            Connection connection = dbConnection.getConnection();

            try{
                Statement statement = connection.createStatement();
                String abfrage = "Select * from run where nameofuser='"+username.getText()+"'";
                ResultSet resultSet = statement.executeQuery(abfrage);

                while (resultSet.next()){
                    if(resultSet.getRow()==1){
                        mainController.setNameID(username.getText());
                        mainController.setPassword(resultSet.getString(2));
                        mainController.setEmail(resultSet.getString(3));
                        mainController.setWeightID(resultSet.getString(5));
                        mainController.setHeightID(resultSet.getString(6));
                        mainController.setAgeID(resultSet.getInt(7));
                    }
                }
            }
            catch (SQLException sql){
                System.out.println(sql.getMessage());
                sql.printStackTrace();
            }
            main.setScene(new Scene(root, 364, 517));
            main.initStyle(StageStyle.UNDECORATED);
            main.show();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            ioe.printStackTrace();
        }
    }
}
