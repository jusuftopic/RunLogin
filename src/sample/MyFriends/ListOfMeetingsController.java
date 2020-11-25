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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ListOfMeetingsController implements Initializable {
    @FXML
    private Button back;
    @FXML
    private Label myusername;
    @FXML
    private Label friendusername;
    @FXML
    private Label meeting1;
    @FXML
    private Label meeting2;
    @FXML
    private Label meeting3;
    @FXML
    private Label distance1;
    @FXML
    private Label distance2;
    @FXML
    private Label distance3;
    @FXML
    private Label date1;
    @FXML
    private Label date2;
    @FXML
    private Label date3;
    @FXML
    private TextField distanceField1;
    @FXML
    private TextField distanceField2;
    @FXML
    private TextField distanceField3;
    @FXML
    private TextField dateField1;
    @FXML
    private TextField dateField2;
    @FXML
    private TextField dateField3;
    @FXML
    private ImageView image;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file = new File("Images/togethertraininglogo.jpg");
        Image img = new Image(file.toURI().toString());
        image.setImage(img);
    }

    // set my username
    public void  setMyusername(String userneme){
        myusername.setText(userneme);
    }
    //set friendusername
    public void setFriendUsername(String username){
        friendusername.setText(username);
    }
    //go back
    public void goBack(ActionEvent actionEvent){
        Stage stage = (Stage) back.getScene().getWindow();
        stage.close();

        Stage friendsStage=new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MyFriendsControll.class.getResource("myfriends.fxml"));
            Parent root = (Parent) fxmlLoader.load();

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

                friendsStage.setScene(new Scene(root,364,520));
                friendsStage.initStyle(StageStyle.UNDECORATED);
                friendsStage.show();

        }
            catch (SQLException sql){
                System.out.println(sql.getMessage());
            sql.printStackTrace();}
        }
        catch (IOException ioe){
            System.out.println(ioe.getMessage());
            ioe.printStackTrace();
        }
    }

    //make LabelsVisible und set text
    public void setDistance1(){
        distance1.setText("Distance");
    }
    public void setDistance2(){
        distance2.setText("Distance");
    }
    public void setDistance3(){
        distance3.setText("Distance");
    }

    //set Labels date visible und text
    public void setDate1(){
        date1.setText("Date");
    }
    public void setDate2(){
        date2.setText("Date");
    }
    public void setDate3(){
        date2.setText("Date");
    }

    //set meetings
    public void setMeeting1(){
        meeting1.setText("Meeting 1");
    }
    public void setMeeting2(){
        meeting2.setText("Meeting 2");
    }
    public void setMeeting3(){
        meeting3.setText("Meeting 3");
    }

    //set distance fields
    public void setDistanceField11(String distance){
        distanceField1.setVisible(true);
        distanceField1.setText(distance);

    }
    public void setDistanceField2(String distance){
        distanceField2.setVisible(true);
        distanceField2.setText(distance);

    }
    public void setDistanceField3(String distance){
        distanceField3.setVisible(true);
        distanceField3.setText(distance);
    }

    //setDateFields
    public void  setDateField1(String date){
        dateField1.setVisible(true);
        dateField1.setText(date);
    }
    public void  setDateField2(String date){
        dateField2.setVisible(true);
        dateField2.setText(date);
    }
    public void  setDateField3(String date){
        dateField3.setVisible(true);
        dateField3.setText(date);
    }
}
