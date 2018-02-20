package pl.sda.finalProject.myOrganizer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.finalProject.myOrganizer.model.Role;

@Repository
public interface IRoleRepository extends JpaRepository<Role,String> {
}
