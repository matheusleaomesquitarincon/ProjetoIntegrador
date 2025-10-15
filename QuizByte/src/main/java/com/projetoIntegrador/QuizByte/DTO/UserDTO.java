

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class UserDTO {
    
    private long id;
    private String name;
    private String email;
    private String passwordHash;
}
