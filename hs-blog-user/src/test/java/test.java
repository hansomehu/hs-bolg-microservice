import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class test {

    @Test
    public void passwordEncoderTest(){
        String encode = new BCryptPasswordEncoder().encode("123456");
        System.out.println(
                encode
        );
        // $2a$10$.b9CCj4TJrNzXG1Izk344.wX5SyrIoJi/Fas4mKxwLa8n9rsM9SlS

    }
}
