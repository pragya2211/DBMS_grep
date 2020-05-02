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

public class ASK_Question {

    private final Stage STAGE;

    public ASK_Question(Stage main , Parent Root , Connection connection ) throws Exception{

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/code_grep/addQuestion.fxml"));
        Parent root = loader.load();
        addQuestion Q = (addQuestion) loader.getController();
        addQuestion.connection = connection;
        Q.initialize();
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

