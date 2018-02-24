package pl.sda.finalProject.myOrganizer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.sda.finalProject.myOrganizer.dao.IUserRepository;
import pl.sda.finalProject.myOrganizer.entity.MyUser;
import pl.sda.finalProject.myOrganizer.service.UserService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MyOrganizerApplicationTests {

	@Autowired
	private UserService userService;
	@Autowired
	private IUserRepository userRepository;

	@Before
	public void initDB(){

		MyUser user = MyUser.builder().email("user@user.pl").password("12345")
				.userName("user").build();

		MyUser admin = MyUser.builder().email("admin@user.pl").password("12345")
				.userName("admin").build();

		userRepository.save(user);
		userRepository.save(admin);

	}

	@Test
	public void findUserByEmail(){
		MyUser user1 = userRepository.findOne("user@user.pl");
		assertNotNull(user1);
		MyUser user2 = userService.findUserByEmail("admin@user.pl");
		assertEquals(user2.getEmail(),"admin@user.pl");
	}

}
