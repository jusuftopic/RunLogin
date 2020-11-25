package sample.RunLog;

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
import sample.MainController.MainController;
import sample.Sample.Controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class RunLogController implements Initializable {
    @FXML
    private ImageView logImageID;
    @FXML
    private Button cancleID;
    @FXML
    private TextField distanceID;
    @FXML
    private TextField timeID;
    @FXML
    private TextField calorieID;
    @FXML
    private TextField minkmID;
    @FXML
    private Button insertID;
    @FXML
    private Label blankID;
    @FXML
    private TextField getuserID;
    @FXML
    private TextField warningtext;
    private static int intcount = 1;
    private String username;
    private String weight;
    private String height;
    private int age;
    private String email;
    private String password;
    @FXML
    private Label blankUsername;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file = new File("Images/log.jpg");
        Image image = new Image(file.toURI().toString());
        logImageID.setImage(image);
    }

    public void transferMessage(String message) {
        getuserID.setText(message);
    }

    //set username unvisible
    public void setVisible(){
        blankUsername.setVisible(false);
    }


    public void cancleIDOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) cancleID.getScene().getWindow();
        stage.close();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("maincontroller.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            MainController mainController = fxmlLoader.getController();

            DbConnection dbConnection = new DbConnection();
            Connection connection = dbConnection.getConnection();

            try{
                Statement statement = connection.createStatement();
                String abfrage = "select * from run where nameofuser='"+blankUsername.getText()+"'";
                ResultSet resultSet = statement.executeQuery(abfrage);

                while(resultSet.next()){
                    if (resultSet.getRow()==1){
                        mainController.setNameID(blankUsername.getText());
                        mainController.setWeightID(resultSet.getString(5));
                        mainController.setHeightID(resultSet.getString(6));
                        mainController.setAgeID(resultSet.getInt(7));
                        mainController.setEmail(resultSet.getString(3));
                        mainController.setPassword(resultSet.getString(2));
                    }
                }
            }
            catch (SQLException sql){
                System.out.println(sql.getMessage());
                sql.printStackTrace();
            }


            Stage main = new Stage();
            main.setScene(new Scene(root, 364, 517));
            main.initStyle(StageStyle.UNDECORATED);
            main.show();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            ioe.printStackTrace();
        }

    }

    public void insertData(ActionEvent actionEvent) {
        if (distanceID.getText().isBlank() || timeID.getText().isBlank() || calorieID.getText().isBlank() || minkmID.getText().isBlank()) {
            blankID.setText("Enter all fields!");
        } else {
            DbConnection dbConnection = new DbConnection();
            Connection connection = dbConnection.getConnection();
            String name = getuserID.getText();
            String distance = distanceID.getText();
            String time = timeID.getText();
            int calories = Integer.parseInt(calorieID.getText());
            String proKm = minkmID.getText();


            try {
                if (isNumber(distance) && rightTimeFormat(time) && setTimeProMinute(proKm)) {

                    Statement statement = connection.createStatement();
                    String update = "INSERT into stat(username,kilometers,timeofrace,timeperkilometers,calories) values ('"+ name + "','" + distance + "','" + time + "','" + proKm + "','" + calories + "')";
                    statement.executeUpdate(update);
                    blankID.setText("Another race behind you. Let's move on!");
                } else if (!isNumber(distance) && rightTimeFormat(time)) {
                    blankID.setText("Format for distance -> x.xx");
                } else if (isNumber(distance) && !rightTimeFormat(time)) {
                    blankID.setText("Format for time-> hh:mm:ss");
                } else if (isNumber(distance) && rightTimeFormat(time) && !setTimeProMinute(proKm)) {
                    blankID.setText("Format for time per kilometer -> mm:ss");
                } else {
                    blankID.setText("More fields don't have right format");
                }
            } catch (SQLException sql) {
                System.out.println(sql.getMessage());
                sql.printStackTrace();
            }

        }

    }

    //check if distance fields only nummbers are
    public boolean isNumber(String string) {
        boolean isDigit = true;

        if (string.contains(".")) {

            for (int i = 0; i < string.length(); i++) {
                if (Character.isDigit(string.charAt(i)) || string.charAt(i) == '.') {
                    if (string.charAt(i) == '.' && string.indexOf(string.charAt(i)) == 0) {
                        isDigit = false;
                        break;
                    } else if (string.charAt(i) == '.' && string.indexOf(string.charAt(i)) == string.length() - 1) {
                        isDigit = false;
                        break;
                    } else {
                        isDigit = true;
                    }

                } else {
                    isDigit = false;
                    break;
                }
            }
            return isDigit;
        } else {
            isDigit = false;
        }
        return isDigit;
    }

    //set format of time
    public boolean rightTimeFormat(String time) {
        boolean isRight = true;
        int counter = 0;

        for (int i = 0; i < time.length(); i++) {
            if (time.charAt(i) == ':') {
                counter++;
            }
        }

        if (counter == 0) {
            blankID.setText("Set the correct time format- (hh: mm: ss)");
        } else if (counter == 1 || counter == 2) {
            String[] minSec = time.split("\\:");
            outerloop:
            for (int i = 0; i < minSec.length; i++) {
                for (int j = 0; j < minSec[i].length(); j++) {
                    if (Character.isDigit(minSec[i].charAt(j))) {
                        isRight = true;
                    } else{
                        isRight = false;
                        break outerloop;
                    }
                }
            }
        }
        return isRight;
    }

    //set right time per minute
    public boolean setTimeProMinute(String timeprominute) {
        boolean isRight = true;
        if (timeprominute.contains(":")) {
            if (timeprominute.indexOf(":") != 0 && timeprominute.indexOf(":") != timeprominute.length() - 1) {
                String[] splitTime = timeprominute.split(":");
                digit:
                for (int i = 0; i < splitTime.length; i++) {
                    for (int j = 0; j < splitTime[i].length(); j++) {
                        if (Character.isDigit(splitTime[i].charAt(j))) {
                            isRight = true;
                        } else {
                            isRight = false;
                            break digit;
                        }
                    }
                }

            } else {
                isRight = false;
            }

        } else {
            isRight = false;
        }
        return isRight;
    }

    //set blank username
    public void setBlankUsername(String user){
        blankUsername.setText(user);
    }

    //increce public method
    public static int increse() {
        return intcount++;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}



