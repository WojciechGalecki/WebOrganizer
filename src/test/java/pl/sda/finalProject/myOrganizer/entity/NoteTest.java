package pl.sda.finalProject.myOrganizer.entity;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class NoteTest {

    Note note;
    LocalDate now;

    @Before
    public void setUp() {
        note = new Note();
        now = LocalDate.now();
        note.setCreationDate(now);
    }

    @Test
    public void getId() {
        Long idValue = 1L;

        note.setId(idValue);

        assertEquals(idValue, note.getId());
    }

    @Test
    public void getName() {
        String name = "name";

        note.setName(name);

        assertTrue(note.getName().equals(name));
    }

    @Test
    public void getCreationDate() {

        assertTrue(note.getCreationDate().isEqual(now));
    }

    @Test
    public void getDescription() {
        String description = "My first note";

        note.setDescription(description);

        assertEquals(description, note.getDescription());
    }

    @Test
    public void getUser() {

        MyUser user = new MyUser();

        note.setUser(user);

        assertEquals(user, note.getUser());
    }
}