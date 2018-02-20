package pl.sda.finalProject.myOrganizer.dao;

        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.stereotype.Repository;
        import pl.sda.finalProject.myOrganizer.model.MyUser;
        import pl.sda.finalProject.myOrganizer.model.Note;

        import java.util.List;

@Repository
public interface INoteRepository extends JpaRepository<Note, Long> {

    List<Note> findByUser(MyUser user);
}
