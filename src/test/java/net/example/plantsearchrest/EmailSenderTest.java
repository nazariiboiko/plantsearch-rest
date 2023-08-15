package net.example.plantsearchrest;

import net.example.plantsearchrest.service.MailSender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EmailSenderTest {

    @Autowired
    private MailSender mailSender;
    @Test
    public void sendEmailTest() {
        mailSender.send("nazidokss1@gmail.com", "Hello world!", "Hello world!");
    }
}
