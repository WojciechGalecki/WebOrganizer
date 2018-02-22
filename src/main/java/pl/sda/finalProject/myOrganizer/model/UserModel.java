package pl.sda.finalProject.myOrganizer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import pl.sda.finalProject.myOrganizer.entity.MyUser;
import pl.sda.finalProject.myOrganizer.entity.Note;
import pl.sda.finalProject.myOrganizer.entity.Task;
import pl.sda.finalProject.myOrganizer.entity.UserRole;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {


    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    @Size(max = 20)
    private String userName;

    @Size(min = 5)
    private String password;

    private UserRole userRole;

    private List<Note> notes;

    private List<Task> tasks;

    public UserModel(MyUser userEntity) {
        this.email = userEntity.getEmail();
        this.userName = userEntity.getUserName();
        this.password = userEntity.getPassword();
        this.userRole = userEntity.getUserRole();
        this.notes = userEntity.getNotes();
        this.tasks = userEntity.getTasks();
    }
}
