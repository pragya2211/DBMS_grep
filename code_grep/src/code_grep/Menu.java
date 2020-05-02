package code_grep;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Menu {
    
    @FXML
    private TextField Search_box;

    @FXML
    private TextField messageFromSystem;

    public Stage main ;
    public Parent Root ;
    public Connection connection;
    public static List<String> list = new ArrayList();

    public static void updateList(String s){
        list.add(s);
    }

    public void initializeBox() throws Exception {

        PreparedStatement find = this.connection.prepareStatement("select Name from tags");
        ResultSet r = find.executeQuery();
        while (r.next()){
            Menu.updateList(r.getString("Name"));
        }

        TextFields.bindAutoCompletion(Search_box, t -> {
            return list.stream().filter(elem
                    -> {
                return elem.toLowerCase().startsWith(t.getUserText().toLowerCase());
            }).collect(Collectors.toList());
        });
    }

    public void ShowQuestions(MouseEvent mouseEvent) throws Exception{
        PreparedStatement find = this.connection.prepareStatement("select a.ID , q.ID , a.Answer, a.Answered_by, a.Upvotes , a.Downvotes , q.Question , q.Asked_by , Q.Description , Q.Upvotes , Q.Downvotes from answers a inner join questions q on a.Question_ID =q.ID Where q.id in ( select r.QuestionID from tagsofquestions r where r.TagID = ?  );");
        find.setString(1,this.Search_box.getText());
        ResultSet r = find.executeQuery();
        if (r.next()){
            new questions(this.main , this.Root , this.connection , r);
            return;
        }
        messageFromSystem.setText("Question Not answered");
    }

    public void Login(MouseEvent mouseEvent) throws Exception {
        new enter(this.main,this.Root,this.connection,true);
    }

    public void Signup(MouseEvent mouseEvent) throws Exception{
        new enter(this.main,this.Root,this.connection,false);
    }

    public void askQuestion(MouseEvent mouseEvent) throws Exception{
        if (!User.loggedIn()){
            messageFromSystem.setText("Login First");
            return;
        }
        new ASK_Question(this.main,this.Root,this.connection);
    }

    public void logout(MouseEvent mouseEvent) {
        messageFromSystem.setText("Bye "+User.get_username());
        User.logout();
    }
}
