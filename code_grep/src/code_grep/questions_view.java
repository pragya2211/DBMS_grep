package code_grep;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class questions_view {

    @FXML
    private TextArea Answer;

    @FXML
    private TextArea Question;

    @FXML
    private TextArea Description;

    @FXML
    private TextField upvotes_Q;

    @FXML
    private TextField downvotes_Q;

    @FXML
    private TextField downvotes_A;

    @FXML
    private TextField upvotes_A;

    @FXML
    private TextField askedBy_Q;

    @FXML
    private TextField answeredBy;

    private String q_id;
    private String a_id;
    private Connection connection;
    public ResultSet result;

    public void cleanUp(){
        this.q_id = null;
        this.a_id = null;
    }

    public void addConnection(Connection conn){
        this.connection = conn;
    }

    public void previousQuestion(MouseEvent mouseEvent) throws Exception {
        if(result.previous()){
            addQuestion(result.getString("Question") ,
                    result.getString("Description") ,
                    result.getString("Asked_by") ,
                    result.getString("q.Downvotes") ,
                    result.getString("q.Upvotes") ,
                    result.getString("q.ID"));

            addAnswer( result.getString("Answered_by"),
                    result.getString("Answer"),
                    result.getString("a.Upvotes"),
                    result.getString("a.Downvotes"),
                    result.getString("a.ID"));
        }
    }

    public void nextQuestion(MouseEvent mouseEvent) throws Exception {
        if (result.next()){
            addQuestion(result.getString("Question") ,
                    result.getString("Description") ,
                    result.getString("Asked_by") ,
                    result.getString("q.Downvotes") ,
                    result.getString("q.Upvotes") ,
                    result.getString("q.ID"));

            addAnswer( result.getString("Answered_by"),
                    result.getString("Answer"),
                    result.getString("a.Upvotes"),
                    result.getString("a.Downvotes"),
                    result.getString("a.ID"));
        }
    }

    public void downvoteAnswer(MouseEvent mouseEvent) throws Exception{
        PreparedStatement check = this.connection.prepareStatement("select * from answers where ID = ? ");
        check.setString(1,this.a_id);
        ResultSet r = check.executeQuery();
        if (r.next()){
            String d = r.getString("Downvotes");
            int curr = Integer.valueOf(d);
            curr++;
            PreparedStatement up = this.connection.prepareStatement("UPDATE answers SET Downvotes = ? WHERE ID = ?");
            up.setString(1,Integer.toString(curr));
            up.setString(2,a_id);
            up.execute();
            downvotes_A.setText(Integer.toString(curr));
            return;
        }
    }

    public void upvoteQuestion(MouseEvent mouseEvent) throws Exception{
        PreparedStatement check = this.connection.prepareStatement("select * from questions where ID = ? ");
        check.setString(1,this.q_id);
        ResultSet r = check.executeQuery();
        if (r.next()){
            String d = r.getString("Upvotes");
            int curr = Integer.valueOf(d);
            curr++;
            PreparedStatement up = this.connection.prepareStatement("UPDATE questions SET Upvotes = ? WHERE ID = ?");
            up.setString(1,Integer.toString(curr));
            up.setString(2,q_id);
            up.execute();
            this.upvotes_Q.setText(Integer.toString(curr));
            return;
        }
    }

    public void downvoteQuestion(MouseEvent mouseEvent) throws Exception{
        PreparedStatement check = this.connection.prepareStatement("select * from questions where ID = ? ");
        check.setString(1,this.q_id);
        ResultSet r = check.executeQuery();
        if (r.next()){
            String d = r.getString("Downvotes");
            int curr = Integer.valueOf(d);
            curr++;
            PreparedStatement up = this.connection.prepareStatement("UPDATE questions SET Downvotes = ? WHERE ID = ?");
            up.setString(1,Integer.toString(curr));
            up.setString(2,q_id);
            up.execute();
            this.downvotes_Q.setText(Integer.toString(curr));
            return;
        }
    }

    public void addQuestion(String Question , String Description , String asked_by , String downvotes , String upvotes , String ID){
        this.q_id = ID;
        this.Question.setText(Question);
        this.askedBy_Q.setText(asked_by);
        this.Description.setText(Description);
        this.downvotes_Q.setText(downvotes);
        this.upvotes_Q.setText(upvotes);
    }

    public void addAnswer( String answeredBy , String answer , String upvotes , String downvotes , String ID){
        this.a_id = ID;
        this.answeredBy.setText(answeredBy);
        this.Answer.setText(answer);
        this.downvotes_A.setText(downvotes);
        this.upvotes_A.setText(upvotes);
    }

    public void upvoteAnswer(MouseEvent mouseEvent) throws Exception {
        PreparedStatement check = this.connection.prepareStatement("select * from answers where ID = ? ");
        check.setString(1,this.a_id);
        ResultSet r = check.executeQuery();
        if (r.next()){
            String d = r.getString("Upvotes");
            int curr = Integer.valueOf(d);
            curr++;
            PreparedStatement up = this.connection.prepareStatement("UPDATE answers SET Upvotes = ? WHERE ID = ?");
            up.setString(1,Integer.toString(curr));
            up.setString(2,a_id);
            up.execute();
            this.upvotes_A.setText(Integer.toString(curr));
            return;
        }
    }
}
