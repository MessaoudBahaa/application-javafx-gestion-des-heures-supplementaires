package sample.Controllers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by MB_Be on 4/20/2017.
 */
public class DB_CNX {
    private static String filePath= "DB_Infos.info";
    public static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static String DB_URL = "jdbc:mysql://127.0.0.1/drh_test";
    //  Database credentials
    private static String USER = "root";
    private static String PASS = "";
    public static Connection connection;
    public static Statement statement;



    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        connection = DriverManager.getConnection(DB_URL, USER, PASS);
        System.out.println("connecting to database : " + DB_URL );
        statement=connection.createStatement();
        return connection;
    }
    public static void closeConnection() throws SQLException {
        connection.close();
        statement.close();
    }



    public static void saveDbInfo(){

        FileOutputStream out = null;

        try {
            out = new FileOutputStream(filePath);
            int i=0;
            while (i<DB_URL.length()) {
                out.write(DB_URL.charAt(i));
                i++;
            }
            out.write(35);
            while (i<(DB_URL.length()+USER.length())) {
                out.write(USER.charAt(i-DB_URL.length()));
                i++;
            }
            out.write(35);
            while (i<(DB_URL.length()+USER.length()+PASS.length())) {
                out.write(PASS.charAt(i-(DB_URL.length()+USER.length())));
                i++;
            }
            out.write(35);
            if (out != null) {
                out.close();
            }
        }catch (IOException e){

        }
    }
    public static void restoreDbInfo(){

        FileInputStream in = null;

        try {
            in = new FileInputStream(filePath);
            int c;

            DB_URL="";
            while ((c =in.read())!= 35 ) {
                DB_URL=DB_URL+(char)c;
            }
            USER="";
            while ((c =in.read())!= 35 ) {
                USER=USER+(char)c;
            }
            PASS="";
            while ((c =in.read())!= 35 ) {
                PASS=PASS+(char)c;
            }
        }catch (IOException e){
            System.out.println("error in reading DB server data");
        }
    }


    //setters

    public static void setIp(String ip){
        DB_URL = "jdbc:mysql://"+ip+"/projets2";
    }
    public static void setUSER(String username){
        USER = username;
    }
    public static void setPASS(String password){
        PASS = password;
    }
    // getters
    public static String getUSER() {
        return USER;
    }

    public static String getFilePath() {
        return filePath;
    }

    public static String getDbUrl() {
        return DB_URL;
    }

    public static String getPASS() {
        return PASS;
    }

}
