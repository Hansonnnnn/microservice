package cn.edu.nju.user;

import cn.edu.nju.user.dao.UserRepository;
import entity.user.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testJPAFindNotExistEntity() {
        User user = userRepository.findByUsername("hdsus");
        Assert.assertNull(user);
    }
}
