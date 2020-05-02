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
import java.sql.ResultSet;

public class questions {

    private final Stage STAGE;
    private questions_view Q;
    public questions(Stage main , Parent Root , Connection connection , ResultSet r) throws Exception{

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/code_grep/Question.fxml"));
        Parent root = (Parent) loader.load();
        this.Q = (questions_view) loader.getController();
        this.Q.addConnection(connection);
        r.next();
        this.Q.addQuestion(r.getString("Question") ,
                           r.getString("Description") ,
                           r.getString("Asked_by") ,
                           r.getString("q.Downvotes") ,
                           r.getString("q.Upvotes") ,
                           r.getString("q.ID"));

        this.Q.addAnswer( r.getString("Answered_by"),
                          r.getString("Answer"),
                          r.getString("a.Upvotes"),
                          r.getString("a.Downvotes"),
                          r.getString("a.ID"));
        this.Q.result = r;
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

