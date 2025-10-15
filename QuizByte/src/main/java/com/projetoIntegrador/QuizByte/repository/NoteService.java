import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteService extends JpaRepository<Note, Long> {
    
}
