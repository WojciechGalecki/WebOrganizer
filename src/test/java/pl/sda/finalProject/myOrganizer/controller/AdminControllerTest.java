package pl.sda.finalProject.myOrganizer.controller;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.sda.finalProject.myOrganizer.dao.IUserRepository;
import pl.sda.finalProject.myOrganizer.entity.MyUser;
import pl.sda.finalProject.myOrganizer.service.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(AdminController.class)
@WithMockUser
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserRepository userRepository;
    @MockBean
    private UserService userService;
    @MockBean
    private PasswordEncoder passwordEncoder;

    @Ignore
    // Not working?
    @Test
    public void deleteExistingUser() throws Exception {
        BDDMockito.given(userRepository.findOne("user@user.pl")).willReturn(new MyUser());

        mockMvc.perform(MockMvcRequestBuilders.get("/organizer/users/delete/user@user.pl"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/organizer/users"));
    }

    @Test
    public void deleteNotExistingUser() throws Exception {
        BDDMockito.given(userRepository.findOne("user@user.com")).willReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/organizer/users/delete/user@user.com"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("userNotFound"));
    }
}
