package pl.sda.finalProject.myOrganizer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.finalProject.myOrganizer.model.MyUser;

@Repository
public interface IUserRepository extends JpaRepository<MyUser,String> {
}
