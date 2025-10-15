@Data
public class QuestionDTO {
    
    private long id;
    private int questionNumber;
    private String statement;
    private String answer;
    private Question.Difficulty difficulty ;
    private String tips;
}
