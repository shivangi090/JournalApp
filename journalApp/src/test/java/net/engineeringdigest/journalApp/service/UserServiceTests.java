package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/*
    Only running the tests won't work. We hae to use this annotation because without it,
    there will be no spring context and IOC container so there will be no bean at all.
 */
@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void testFindByUserName(){
        assertNotNull(userRepository.findByUserName("ram"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "rama",
            "shyam",
            "shivangi"
    })
    public void testFindByUserNameDynamic(String username){
        assertNotNull(userRepository.findByUserName(username), "failed for : "+username);
    }

    @ParameterizedTest
    @ArgumentsSource(UserArgumentProvider.class)
    public void testSaveNewUser(User user){
        assertTrue(userService.saveNewUser(user), "failed for : "+user);
    }

    /*
    When you want to provide multiple dynamic values.
    CsvSource/CsvFileSource annotations are used to provide those values
     */
    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,10,12",
            "3,3,9"
    })
    public void test(int a, int b, int expected){
        assertEquals(expected, a + b);
    }
}
