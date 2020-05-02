package code_grep;


import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.sql.*;


public class Main extends Application{

    Stage stage;
    public void start(Stage stage ) throws Exception{

        this.stage = stage;
        this.stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                this.stage.close();
            }
        });

        try {
            String mysql_login_userName = "root";//When you run sql from terminal
            String mysql_login_passWord = "mullerandgomez";
            final Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/dbms?" +
                    "user="+mysql_login_userName+"&"+"password="+mysql_login_passWord);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/code_grep/Menu.fxml"));
            Parent root = loader.load();
            Menu menu = (Menu) loader.getController();
            menu.main = this.stage;
            menu.connection = conn;
            menu.Root = root;
            menu.initializeBox();
            Scene s = new Scene(root);
            stage.setScene(s);
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    try {
                        stop();
                        conn.close();
                        System.out.println("SQL Connection closed");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}