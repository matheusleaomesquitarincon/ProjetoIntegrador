import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminService extends JpaRepository<Admin, Long> {
    
}
