package com.leshka_and_friends.lgvb.dao;

import com.leshka_and_friends.lgvb.model.CardType;
import com.leshka_and_friends.lgvb.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardTypeSQL implements CardTypeDAO {

    @Override
    public CardType findById(int id) {
        String sql = "SELECT * FROM card_types WHERE card_type_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new CardType(
                        rs.getInt("card_type_id"),
                        rs.getString("name")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CardType> getAll() {
        String sql = "SELECT * FROM card_types";
        List<CardType> types = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                types.add(new CardType(
                    rs.getInt("card_type_id"),
                    rs.getString("name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return types;
    }
}