package com.asdeire.oop.practice7.persistence;
import com.asdeire.oop.practice7.entity.Mailbox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MailboxDao {
    private Connection connection;

    public MailboxDao(Connection connection) {
        this.connection = connection;
    }

    public void save(Mailbox mailbox) throws SQLException {
        String query = "INSERT INTO mailboxes (user_id, address) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, mailbox.getUserId());
            statement.setString(2, mailbox.getAddress());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                mailbox.setMailboxId(generatedKeys.getLong(1));
            }
        }
    }

    public List<Mailbox> findByUserId(Long userId) throws SQLException {
        List<Mailbox> mailboxes = new ArrayList<>();
        String query = "SELECT * FROM Mailboxes WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Mailbox mailbox = new Mailbox();
                    mailbox.setMailboxId(resultSet.getLong("mailbox_id"));
                    mailbox.setUserId(resultSet.getLong("user_id"));
                    mailbox.setAddress(resultSet.getString("address"));
                    mailboxes.add(mailbox);
                }
            }
        }
        return mailboxes;
    }
}
