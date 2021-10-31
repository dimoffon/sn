package io.dimoffon.sn;

import com.github.javafaker.Faker;
import io.dimoffon.sn.dto.UserFilter;
import io.dimoffon.sn.entity.User;
import io.dimoffon.sn.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class SnApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    void contextLoads() {
    }

    @Profile("datagen")
    @Test
    void generateUserData() {
        Faker faker = new Faker();
        for (int i = 0; i < 999_999; i++) {
            Integer age = faker.random().nextInt(16, 90);
            String username = faker.name().username() + age;
            if (userRepository.getUsers(UserFilter.builder().username(username).build()).isEmpty()) {
                User user = User.builder()
                        //.password(faker.crypto().sha1())
                        .password("$2a$12$icwVWJ9h0BRV88klNnL.MOFCKEFx9sVPwGTxFbQ.PD9f3pZflRXIK") // 123
                        .username(username)
                        .firstName(faker.name().firstName())
                        .lastName(faker.name().lastName())
                        .city(faker.address().cityName())
                        .age(age)
                        .gender(faker.dog().gender())
                        .build();
                userRepository.addUser(user);
            } else {
                i--;
            }
        }
    }

}
