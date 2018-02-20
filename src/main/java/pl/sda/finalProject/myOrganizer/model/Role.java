package pl.sda.finalProject.myOrganizer.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @Enumerated(value = EnumType.STRING)
    private UserRoleType userRoleType;

    @ManyToMany(mappedBy = "roles")
    private List<MyUser> users;
}
