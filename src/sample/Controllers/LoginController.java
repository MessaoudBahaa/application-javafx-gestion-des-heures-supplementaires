package sample.Controllers;

import com.jfoenix.controls.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;


import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController implements Initializable {
    public static Stage loginStage;


    @FXML
    private JFXTextField usernameField;

    @FXML
    private JFXTextField passwordFieldShown;

    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private JFXButton connect;

    @FXML
    private JFXCheckBox checkpassword;

    @FXML
    private Label echecLabal;

    public void checkPassword(){
        // System.out.println(verifyChars(usernameField.getText(),"=+\"':/\\-"));
        if (checkpassword.isSelected()){
            passwordField.setVisible(false);
            passwordFieldShown.setVisible(true);
            passwordFieldShown.setText(passwordField.getText());
        }
        else{
            passwordFieldShown.setVisible(false);
            passwordField.setText(passwordFieldShown.getText());
            passwordField.setVisible(true);

        }
    }

    public void connecter() throws IOException {

        if (checkpassword.isSelected()){passwordField.setText(passwordFieldShown.getText());}

        String username = usernameField.getText();
        String password = MD5(passwordField.getText()).toUpperCase();

        try {
                com.mysql.jdbc.Connection conn = null;
                com.mysql.jdbc.Statement stmt = null;
                Class.forName("com.mysql.jdbc.Driver");
                System.out.println("Connecting to database...");

                conn = (com.mysql.jdbc.Connection) DriverManager.getConnection(DB_CNX.getDbUrl(), DB_CNX.getUSER(), DB_CNX.getPASS());
                stmt = (com.mysql.jdbc.Statement) conn.createStatement();

                String sql;
            //////validation needed here !
            sql = "SELECT * FROM USER WHERE username='" + username + "' AND  password='" + password + "'";//"insert into login (username,password,super)values('bakir','9iw9iw',0)";
            System.out.println(sql);
            ResultSet res = stmt.executeQuery(sql);

            if (res.next()){
                boolean admin = (res.getInt("admin") == 1);

                HomeController.SetUser(username, admin);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/Home.fxml"));
                HomeController.rt = loader.load();
                usernameField.clear();
                passwordFieldShown.clear();
                passwordField.clear();
                Stage s = new Stage();
                s.setScene(new Scene(HomeController.rt));

                stage.close();
                HomeController.HomeStage=s;
                s.initStyle(StageStyle.UNDECORATED);
                s.show();


                System.out.println("Bienvenue Mr " + username + " !");
            }else {
                usernameField.setUnFocusColor(Color.RED);
                passwordField.setUnFocusColor(Color.RED);
                echecLabal.setText("nom d'utilisateur ou mot de passe incorrecte");

            }

            stmt.close();
            conn.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void enterPressed(KeyEvent event) throws IOException {
        if(event.getCode()== KeyCode.ENTER){
            connecter();
        }
    }
    public void closeStage(){
        System.out.println("closeing");
        loginStage.close();
    }

    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }
        @Override
    public void initialize(URL location, ResourceBundle resources) {
            Tooltip usertp=new Tooltip();
            usernameField.setTooltip(usertp);
            Tooltip passtp=new Tooltip();
            passwordFieldShown.setTooltip(passtp);
            passwordField.setTooltip(passtp);
            passwordField.getTooltip().setText("Entrez le mot de passe");
            usernameField.getTooltip().setText("Entrez le nom d'utilisateur");
            passwordFieldShown.setVisible(false);

        DB_CNX.restoreDbInfo();

    }



    Stage stage;
    public  void Main(Stage stage){
        this.stage=stage;

    }
}
