package code_grep;

import java.security.MessageDigest;
import java.sql.*;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class User {

    @FXML
    private TextField userID_box_login;

    @FXML
    private TextField message_box_login;

    @FXML
    private TextField message_box_signup;

    @FXML
    private TextField name_signup;

    @FXML
    private TextField userID_signup;

    @FXML
    private TextArea bio_signup;

    @FXML
    private PasswordField passwordBox_login;

    @FXML
    private PasswordField password_signup;

    @FXML
    private PasswordField reEnterPassword_signup;

    private Connection conn;

    private static String id = null;
    private static String userID = null;
    private static boolean userSelected = false;

    public static String getID(){
        return User.id;
    }

    public static String get_username(){
        return User.userID;
    }

    public static boolean loggedIn(){
        return User.userSelected;
    }

    public static void logout() {
        User.userSelected = false;
        User.userID = null;
        User.id = null;
    }

    public void setConnection(Connection connection){
        this.conn = connection;
    }

    public void login(MouseEvent mouseEvent) throws Exception{

        String u_id = userID_box_login.getText();
        String pwd = passwordBox_login.getText();
        String encrypted = getEncrypted(pwd);
        PreparedStatement check = this.conn.prepareStatement("select * from users where user_id = ? ");
        check.setString(1,u_id);
        ResultSet r = check.executeQuery();
        if (r.next()){
            String expected_pwd = r.getString("Password");
            if (!expected_pwd.equals(encrypted)){
                message_box_login.setText("Wrong Password . Try again.");
                return;
            }
            User.userSelected = true;
            message_box_login.setText(" Welcome "+r.getString("user_id")+" :)");
            User.userID = r.getString("user_id");
            User.id = r.getString("ID");
            return;
        }
        message_box_login.setText("Login Failed. Try again :(");
    }

    public void signup(MouseEvent mouseEvent) throws Exception{

        String u_name = name_signup.getText();
        String u_id = userID_signup.getText();
        PreparedStatement check = this.conn.prepareStatement("select * from users where user_id = ? ");
        check.setString(1, u_id);
        ResultSet r1=check.executeQuery();
        if(r1.next()){
            message_box_signup.setText("User ID already in use. Try Again");
            return;
        }

        String pwd = password_signup.getText();

        String r_pwd = reEnterPassword_signup.getText();
        if (!r_pwd.equals(pwd)){
            message_box_signup.setText("Error. Password does not match. Try again.");
            return;
        }

        //Add to database
        String encrypted = User.getEncrypted(pwd);
        String bio = bio_signup.getText();
        PreparedStatement add = this.conn.prepareStatement("INSERT INTO dbms.users (Name, Bio, Password, user_id) VALUES (?, ?, ?,?)", new String[]{"ID"});
        add.setString(1,u_name);
        add.setString(2,bio);
        add.setString(3,encrypted);
        add.setString(4,u_id);
        int affectedRows = add.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Creating user failed, no rows affected.");
        }

        try (ResultSet generatedKeys = add.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                System.out.println(generatedKeys.getLong(1));
            }
            else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        }
        message_box_signup.setText("Signup succesful , please login.");
    }

    public void clear_message_login(MouseEvent mouseEvent) {
        message_box_login.clear();
    }

    public void clear_message_signup(MouseEvent mouseEvent) {
        message_box_signup.clear();
    }

    private static String getEncrypted(String plain) throws Exception{
        byte[] plainText = plain.getBytes();

        MessageDigest md = MessageDigest.getInstance("SHA");
        md.reset();
        md.update(plainText);

        byte[] encoded = md.digest();
        StringBuilder sb = new StringBuilder();
        for (int i = 0 ; i < encoded.length ; i++){
            if((encoded[i] & 0xff) < 0x10){
                sb.append("0");
            }
            sb.append(Long.toString(encoded[i] & 0xff,16));
        }
        return sb.toString();
    }
}
