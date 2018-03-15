package pl.sda.finalProject.myOrganizer.controller;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.sda.finalProject.myOrganizer.dao.IUserRepository;
import pl.sda.finalProject.myOrganizer.entity.MyUser;
import pl.sda.finalProject.myOrganizer.service.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@WithMockUser
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @MockBean
    private IUserRepository userRepository;


    @Before
    public void initTestH2_DB() {

        MyUser user = MyUser.builder().email("user@user.pl").password("12345")
                .userName("user").build();

        MyUser admin = MyUser.builder().email("admin@user.pl").password("12345")
                .userName("admin").build();

        userRepository.save(user);
        userRepository.save(admin);
    }
}
