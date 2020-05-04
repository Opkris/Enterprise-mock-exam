package travel.agency.backend.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import travel.agency.backend.TestApplication;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserServiceTest extends ServiceTestBase {


    @Autowired
    private UserService userService;

    private String userName = "SweetOla";
    private String firstName = "Ola";
    private String lastName = "Nordmann";
    private String password = "Password";
    private String email = "ola@nordmann.no";
    private String role = "user";

    @Test
    public void shouldCreateUser(){

        assertTrue(userService.createUser(
                userName,
                firstName,
                lastName,
                password,
                email,
                role));
    }




}
