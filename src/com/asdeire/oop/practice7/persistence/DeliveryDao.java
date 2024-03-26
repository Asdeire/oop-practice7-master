package com.asdeire.oop.practice7.persistence;
import com.asdeire.oop.practice7.entity.Delivery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeliveryDao {
    private Connection connection;

    public DeliveryDao(Connection connection) {
        this.connection = connection;
    }

    public void save(Delivery delivery) throws SQLException {
        String query = "INSERT INTO Deliveries (letter_id, mailbox_id, delivery_date) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, delivery.getLetterId());
            statement.setLong(2, delivery.getMailboxId());
            statement.setObject(3, delivery.getDeliveryDate());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                delivery.setDeliveryId(generatedKeys.getLong(1));
            }
        }
    }


}
