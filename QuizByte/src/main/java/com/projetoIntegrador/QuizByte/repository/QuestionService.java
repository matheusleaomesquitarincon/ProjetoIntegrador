import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionService extends JpaRepository<Question, Long>{
    
}
