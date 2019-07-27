package sample.Controllers;

import sample.Controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.stage=primaryStage;
        FXMLLoader fxml = new FXMLLoader();
        fxml.setLocation(getClass().getResource("../Views/Login.fxml"));
        primaryStage.setTitle("login page ");
        primaryStage.setScene(new Scene(fxml.load(),700, 500));
        LoginController controller=fxml.getController();
        controller.Main(stage);
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
        LoginController.loginStage=primaryStage;

////////////////////////////////////////////////////////////////
        /*try {
            com.mysql.jdbc.Connection conn = null;
            com.mysql.jdbc.Statement stmt = null;
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to database...");

            conn = (com.mysql.jdbc.Connection) DriverManager.getConnection(DB_CNX.getDbUrl(), DB_CNX.getUSER(), DB_CNX.getPASS());
            stmt = (com.mysql.jdbc.Statement) conn.createStatement();

            String sql;
            //////validation needed here !
            sql = "LOAD DATA INFILE 'D:/testImport2.csv' INTO TABLE employe  FIELDS TERMINATED BY ',' ENCLOSED BY '\"' LINES TERMINATED BY '\\r\\n'";//"insert into login (username,password,super)values('bakir','9iw9iw',0)";
           // ResultSet res = stmt.executeQuery(sql);
            stmt.execute(sql);

        } catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (SQLException e) {
        e.printStackTrace();
    }*/


        /*
        Parent root = FXMLLoader.load(getClass().getResource("../Views/Login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 700   , 500));
        primaryStage.setResizable(false);
        primaryStage.show();*/
    }


    public static void main(String[] args) {
        launch(args);
    }
}
