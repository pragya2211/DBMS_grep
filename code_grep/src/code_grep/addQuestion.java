package code_grep;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.textfield.TextFields;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class addQuestion {

    @FXML
    private TextArea Q_Description;

    @FXML
    private TextArea Question;

    @FXML
    private TextField searchTag;

    @FXML
    private TextField messageFromSystem;

    @FXML
    private TextField showUserID;

    @FXML
    private TextField newTagName;

    @FXML
    private TextArea newTagDescription;

    private List<String> tags;

    public addQuestion() {
    }

    public  void initialize(){
        this.showUserID.setText("Welcome user : " + User.get_username()+" :)");
        TextFields.bindAutoCompletion(this.searchTag, t -> {
            return Menu.list.stream().filter(elem
                    -> {
                return elem.toLowerCase().startsWith(t.getUserText().toLowerCase());
            }).collect(Collectors.toList());
        });
        tags = new ArrayList<>();
    }

    public static Connection connection;

    public void addQuestion(MouseEvent mouseEvent) throws Exception{

        String Question = this.Question.getText();
        String description = this.Q_Description.getText();
        String askedBy = User.get_username();

        PreparedStatement check = this.connection.prepareStatement("select * from questions where Question = ? ");
        check.setString(1, Question);
        ResultSet r1=check.executeQuery();
        if(r1.next()){
            messageFromSystem.setText("question already asked. Try Again");
            return;
        }

        //Add to database
        PreparedStatement add = this.connection.prepareStatement("INSERT INTO dbms.questions (Question, Asked_by, Description) VALUES (?, ?, ?)", new String[]{"ID"});
        add.setString(1,Question);
        add.setString(2,askedBy);
        add.setString(3,description);

        int affectedRows = add.executeUpdate();

        if (affectedRows == 0) {
            messageFromSystem.setText("Creating user failed, no rows affected.");
        }
        String ID  = null;

        try (ResultSet generatedKeys = add.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                long q_id = (generatedKeys.getLong(1));
                ID = Long.toString(q_id);
                for(int i = 0 ; i < tags.size() ; i++){
                    PreparedStatement addTag = this.connection.prepareStatement("INSERT INTO dbms.tagsofquestions (TagID, QuestionID) VALUES (?, ?)");
                    addTag.setString(1,tags.get(i));
                    addTag.setString(2,ID);
                    int a = addTag.executeUpdate();

                    if (a == 0) {
                        messageFromSystem.setText("Adding tag : "+tags.get(i)+" failed, no rows affected. Try again");
                        return;
                    }
                }
            }
            else {
                messageFromSystem.setText("Creating Question failed, no ID obtained.");
                return;
            }
        }
        messageFromSystem.setText("Question added succesfully  :)");
        Q_Description.clear();
        this.Question.clear();
        searchTag.clear();
        messageFromSystem.clear();
        showUserID.clear();
        newTagName.clear();
        newTagDescription.clear();
    }

    public void createNewTag(MouseEvent mouseEvent) throws Exception{
        String tagName = this.newTagName.getText();
        String tagDescription = this.newTagDescription.getText();
        PreparedStatement check = this.connection.prepareStatement("select * from tags where Name = ? ");
        check.setString(1, tagName);
        ResultSet r1=check.executeQuery();
        if(r1.next()){
            messageFromSystem.setText("Tag already in use. Try Again");
            return;
        }
      //Add to database
        PreparedStatement add = this.connection.prepareStatement("INSERT INTO dbms.tags (Name, Description) VALUES (?, ?)");
        add.setString(1,tagName);
        add.setString(2,tagDescription);
        int affectedRows = add.executeUpdate();

        if (affectedRows == 0) {
            messageFromSystem.setText("Creating tag failed, Try again.");
            return;
        }
        Menu.updateList(tagName);
        messageFromSystem.setText("Tag : "+tagName+" created successfully. You can add it now");
    }

    public void addTag(MouseEvent mouseEvent) throws Exception {
        String tagName = searchTag.getText();
        PreparedStatement check = this.connection.prepareStatement("select * from tags where NAME = ? ");
        check.setString(1,tagName);
        ResultSet r = check.executeQuery();
        if (r.next()){
            this.tags.add(tagName);
            messageFromSystem.setText("Tag : "+tagName+" added succesfully. Add more tags.");
            this.searchTag.clear();
            return;
        }
        messageFromSystem.setText("Tag does not exits :( Please create new Tag");
    }
}
