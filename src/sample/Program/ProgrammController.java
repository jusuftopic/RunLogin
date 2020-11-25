package sample.Program;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.DbConnection;
import sample.MainController.MainController;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;


public class ProgrammController implements Initializable {
    @FXML
    private Button backToMain;
    @FXML
    private TextField numOfMonthsOrYears;
    @FXML
    private TextField monthOrYear;
    @FXML
    private CheckBox beginner;
    @FXML
    private Label blank;
    @FXML
    private TextField timeInDays;
    @FXML
    private Button submit;
    @FXML
    private ImageView programImage;
    @FXML
    private Label user;

    //implement photo
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file = new File("Images/program.png");
        Image imageForProgramController = new Image(file.toURI().toString());
        programImage.setImage(imageForProgramController);
    }

    //set user
    public void setUsername(String username){
        user.setText(username);
    }

    //Chooes one programm
    public void setProgram(ActionEvent actionEvent){
        String getTimesPerWeek=timeInDays.getText();
        int timesInWeek = Integer.parseInt(getTimesPerWeek);

        if(beginner.isSelected()==false && monthOrYear.getText().isBlank()==false && timeInDays.getText().isBlank()==false && numOfMonthsOrYears.getText().isBlank()==false){
        if(incorrentMonthOrYear()==false  && correctNumberOfMonthsOrYears()==true && yearsOrMonths()==true){
            int years = Integer.parseInt(numOfMonthsOrYears.getText());
            if(years>=3){
                if(Integer.parseInt(getTimesPerWeek)==3){
                WebView webView = new WebView();
                webView.getEngine().load("https://fruitionfitness.com/3-day-a-week-running-plan/");

                BorderPane borderPane = new BorderPane(webView);
                Stage stage = new Stage();
                stage.setScene(new Scene(borderPane));
                stage.show();
            }

                else   if(timesInWeek ==4){
                    WebView webView = new WebView();
                    webView.getEngine().load("https://www.runnersworld.com/uk/training/marathon/a776459/marathon-training-plans/");
                    BorderPane borderPane = new BorderPane(webView);
                    Stage marathon = new Stage();
                    marathon.setScene(new Scene(borderPane));
                    marathon.show();
                }

               else  if (timesInWeek < 3 && timesInWeek>=0) {
                    blank.setText("Don't be so lazy. Run more then "+getTimesPerWeek+" times");}


             else if(timesInWeek<0){
                 blank.setText("You can not run less then 0 times");
                }

            }

        //set intermadiate stage
           if(years>=1 && years<3){
               if(timesInWeek ==3){
                   WebView webViewForIntermadiateThreeTimes = new WebView();
                   webViewForIntermadiateThreeTimes.getEngine().load("https://www.runnersworld.com/training/a20799308/3-day-half-marathon-training-plan/");
                   BorderPane borderPane = new BorderPane(webViewForIntermadiateThreeTimes);
                   Stage intermadiateStage = new Stage();
                   intermadiateStage.setScene(new Scene(borderPane));
                   intermadiateStage.show();
               }
               else if(timesInWeek==4){
                   WebView foruTimesaWeek= new WebView();
                   foruTimesaWeek.getEngine().load("https://www.halhigdon.com/training-programs/half-marathon-training/novice-1-half-marathon/");
                   BorderPane borderPane = new BorderPane(foruTimesaWeek);
                   Stage stage = new Stage();
                   stage.setScene(new Scene(borderPane));
                   stage.show();
               }
              else  if(timesInWeek<0){
                   blank.setText("You can not run less then 0 timea a week");
               }

              else if(timesInWeek>=0 && timesInWeek<3){
                  blank.setText("Don't be so lazy. Run more then "+getTimesPerWeek+" times");
               }
           }

        }
        else if(correctNumberOfMonthsOrYears() && incorrentMonthOrYear()== false && yearsOrMonths()==false){
            if(timesInWeek==3){
                WebView webView = new WebView();
                webView.getEngine().load("https://www.runnersblueprint.com/couch-to-10k-training/");
                BorderPane borderPane = new BorderPane(webView);
                Stage stage = new Stage();
                stage.setScene(new Scene(borderPane));
                stage.show();
            }
            else if(timesInWeek==4){
                WebView webView = new WebView();
                webView.getEngine().load("https://www.runnersworld.com/uk/training/10km/a760081/rws-2-week-10k-schedule-3-4-days-per-week/");
                BorderPane borderPane = new BorderPane(webView);
                Stage stage = new Stage();
                stage.setScene(new Scene(borderPane));
                stage.show();
            }
            else if(timesInWeek>=0 && timesInWeek< 3){
                blank.setText("Don't be so lazy. Run more then "+getTimesPerWeek+" times");
            }
            else if(timesInWeek<0){
                blank.setText("You can not run less then 0 times");
            }
        }
        }
        else if(beginner.isSelected() && monthOrYear.getText().isBlank() && timeInDays.getText().isBlank() && numOfMonthsOrYears.getText().isBlank()){
            WebView webView = new WebView();
            webView.getEngine().load("https://www.runnersworld.com/uk/training/beginners/a772727/how-to-start-running-today/");
            BorderPane borderPane = new BorderPane(webView);
            Stage stage = new Stage();
            stage.setScene(new Scene(borderPane));
            stage.show();
        }
    }


    //check if in second field is month/months/year/years
    public boolean incorrentMonthOrYear() {
        if (monthOrYear.getText().equalsIgnoreCase("year") || monthOrYear.getText().equalsIgnoreCase("years") || monthOrYear.getText().equalsIgnoreCase("month") || monthOrYear.getText().equalsIgnoreCase("months")) {
            return false;
        } else {
            return true;
        }
    }


    //proof if year or number is
    public boolean yearsOrMonths() {
        boolean isYear = false;
        String time = monthOrYear.getText();
        if (time.equalsIgnoreCase("Year") || time.equalsIgnoreCase("years")) {
            isYear = true;
        } else if (time.equalsIgnoreCase("month") || time.equalsIgnoreCase("months")) {
            isYear = false;
        }

        return isYear;
    }

    //check if corrent number of years or month is
    public boolean correctNumberOfMonthsOrYears() {
        boolean isNumber = false;
        String monthYearNums = numOfMonthsOrYears.getText();
        for (int i = 0; i < monthYearNums.length(); i++) {
            if (Character.isDigit(monthYearNums.charAt(i))) {
                isNumber = true;
            } else {
                blank.setText("Invalid number for months or years-> Example: for months: 8; for years: 1");
                isNumber = false;
                break;
            }
        }

        boolean correctYearOrMonth = true;

        if (isNumber) {
            if (yearsOrMonths() == true) {
                if (Integer.parseInt(monthYearNums) < 0) {
                    correctYearOrMonth = false;
                } else {
                    correctYearOrMonth = true;
                }
            } else {
                if (Integer.parseInt(monthYearNums) < 0 || Integer.parseInt(monthYearNums) > 12) {
                    blank.setText("Incorrect number for months. More then 0 and smaller then 12");
                    correctYearOrMonth = false;
                } else {
                    correctYearOrMonth = true;
                }
            }
        }
        return correctYearOrMonth;
    }

    //check if good number of time per week is
    public boolean runsPerWeek() {
        boolean isNumber;
        String perWeek = timeInDays.getText();

        for (int i = 0; i < perWeek.length(); i++) {
            if (Character.isDigit(perWeek.charAt(i))) {
                isNumber = true;
            } else {
                blank.setText("Enter only number of days per week. Example: 1,3,5");
                isNumber = false;
                break;
            }

            if (isNumber) {
                if (Integer.parseInt(perWeek) < 1 || Integer.parseInt(perWeek) > 7) {
                    blank.setText("Enter correct number of days you want to run");
                    return false;
                } else {
                    return true;
                }

            } else {
                return false;
            }
        }
        return false;
    }


    //Back to main menu
    public void backToMain(ActionEvent actionEvent) {
        Stage back = (Stage) backToMain.getScene().getWindow();
        back.close();
        Stage main = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("maincontroller.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            MainController mainController = fxmlLoader.getController();

            DbConnection dbConnection = new DbConnection();
            Connection connection = dbConnection.getConnection();

            try {
                Statement statement = connection.createStatement();
                String abfrage = "Select * from run where nameofuser='"+user.getText()+"'";
                ResultSet resultSet = statement.executeQuery(abfrage);

                while(resultSet.next()){
                    if(resultSet.getRow()==1){
                        mainController.setNameID(user.getText());
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
