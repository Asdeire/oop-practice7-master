package com.asdeire.oop.practice7.persistence;
import com.asdeire.oop.practice7.entity.Letter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LetterDao {
    private Connection connection;

    public LetterDao(Connection connection) {
        this.connection = connection;
    }

    public void save(Letter letter) throws SQLException {
        String query = "INSERT INTO Letters (sender_id, recipient_id, subject, content) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, letter.getSenderId());
            statement.setLong(2, letter.getRecipientId());
            statement.setString(3, letter.getSubject());
            statement.setString(4, letter.getContent());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                letter.setLetterId(generatedKeys.getLong(1));
            }
        }
    }

}
