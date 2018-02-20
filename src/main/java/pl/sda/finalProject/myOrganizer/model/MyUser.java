package pl.sda.finalProject.myOrganizer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyUser {

    @Id
    @Email
    @NotEmpty
    @Column(unique = true)
    private String email;

    @NotEmpty
    private String userName;

    @Size(min = 5)
    private String password;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "userRoles",
            joinColumns = {@JoinColumn(name = "userEmail", referencedColumnName = "email")},
            inverseJoinColumns = {@JoinColumn(name = "roleType", referencedColumnName = "userRoleType")}
    )
    private List<Role> roles;
}
