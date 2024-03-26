package com.asdeire.oop.practice7;

import com.asdeire.oop.practice7.entity.Delivery;
import com.asdeire.oop.practice7.entity.Letter;
import com.asdeire.oop.practice7.entity.Mailbox;
import com.asdeire.oop.practice7.entity.User;
import com.asdeire.oop.practice7.persistence.DeliveryDao;
import com.asdeire.oop.practice7.persistence.LetterDao;
import com.asdeire.oop.practice7.persistence.MailboxDao;
import com.asdeire.oop.practice7.persistence.UserDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:3305/mail";
        String user = "postgres";
        String password = "root";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            // Створення об'єкту користувача
            User userObj = new User();
            userObj.setUsername("testUser");
            userObj.setEmail("test@example.com");

            // Збереження користувача
            UserDao userDao = new UserDao(connection);
            userDao.save(userObj);

            // Пошук користувача за його ідентифікатором
            User retrievedUser = userDao.findById(userObj.getUserId());
            System.out.println("Retrieved User: " + retrievedUser.getUsername());

            // Створення об'єкту поштової скриньки
            Mailbox mailbox = new Mailbox();
            mailbox.setUserId(retrievedUser.getUserId());
            mailbox.setAddress("example@mailbox.com");

            // Збереження поштової скриньки
            MailboxDao mailboxDao = new MailboxDao(connection);
            mailboxDao.save(mailbox);

            // Створення об'єкту листа
            Letter letter = new Letter();
            letter.setSenderId(retrievedUser.getUserId());
            letter.setRecipientId(retrievedUser.getUserId()); // Припустимо, що лист відправляється самому собі
            letter.setSubject("Test Subject");
            letter.setContent("This is a test letter content");

            // Збереження листа
            LetterDao letterDao = new LetterDao(connection);
            letterDao.save(letter);

            // Створення об'єкту поштового відправлення
            Delivery delivery = new Delivery();
            delivery.setLetterId(letter.getLetterId());
            delivery.setMailboxId(mailbox.getMailboxId());
            delivery.setDeliveryDate(java.time.LocalDateTime.now()); // Поточна дата та час

            // Збереження поштового відправлення
            DeliveryDao deliveryDao = new DeliveryDao(connection);
            deliveryDao.save(delivery);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
