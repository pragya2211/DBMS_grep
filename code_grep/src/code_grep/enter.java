package code_grep;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.Connection;

public class enter {

    private final Stage STAGE;
    public User user;
    private String id = null;
    private String userID = null;
    private boolean userSelected = false;

    public String getID(){
        return this.id;
    }

    public String get_username(){
        return this.userID;
    }

    public boolean loggedIn(){
        return userSelected;
    }

    public enter(Stage main , Parent Root , Connection connection , boolean login) throws Exception{

        this.STAGE = new Stage();
        this.STAGE.initStyle(StageStyle.UNDECORATED);
        this.STAGE.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                this.STAGE.close();
                Root.setEffect(null);
            }
        });
        this.STAGE.initModality(Modality.APPLICATION_MODAL);
        this.STAGE.initOwner(main);

        //See main menu
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/code_grep/signup.fxml"));
        Parent root = loader.load();
        if (login){
            loader = new FXMLLoader(getClass().getResource("/code_grep/login.fxml"));
            root = loader.load();
        }
        this.user = (User) loader.getController();
        this.user.setConnection(connection);

        Scene s = new Scene(root);

        this.STAGE.setScene(s);
        BoxBlur blur = new BoxBlur();
        blur.setHeight(1080.0);
        blur.setWidth(1920.0);
        blur.setInput(new GaussianBlur(100));
        this.STAGE.show();
        Root.setEffect(blur);
    }
}

