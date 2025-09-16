package com.leshka_and_friends.lgvb.card;

import com.leshka_and_friends.lgvb.card.Card;
import com.leshka_and_friends.lgvb.core.DBConnection;
import com.leshka_and_friends.lgvb.card.CardDAO;

import java.sql.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class CardSQL implements CardDAO {

    @Override
    public Card getCardById(int cardId) {
        String sql = "SELECT * FROM cards WHERE card_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cardId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToCard(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Card> getCardsByAccountId(int accountId) {
        List<Card> cards = new ArrayList<>();
        String sql = "SELECT * FROM cards WHERE account_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cards.add(mapResultSetToCard(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }

    @Override
    public boolean addCard(Card card) {
        String sql = "INSERT INTO cards (account_id, card_number, expiry_date, cvv, card_type_id, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, card.getAccountId());
            stmt.setString(2, card.getCardNumber());
            stmt.setDate(3, Date.valueOf(card.getExpiryDate().atDay(1))); // store as first day of month
            stmt.setString(4, card.getCvv());
            stmt.setInt(5, card.getCardTypeId()); // ✅ use CardType object
            stmt.setString(6, card.getStatus());

            int affected = stmt.executeUpdate();
            if (affected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    card.setCardId(rs.getInt(1));
                }
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateCard(Card card) {
        String sql = "UPDATE cards SET account_id = ?, card_number = ?, expiry_date = ?, cvv = ?, " +
                     "card_type_id = ?, status = ? WHERE card_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, card.getAccountId());
            stmt.setString(2, card.getCardNumber());
            stmt.setDate(3, Date.valueOf(card.getExpiryDate().atDay(1)));
            stmt.setString(4, card.getCvv());
            stmt.setInt(5, card.getCardTypeId()); // ✅ use CardType object
            stmt.setString(6, card.getStatus());
            stmt.setInt(7, card.getCardId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteCard(int cardId) {
        String sql = "DELETE FROM cards WHERE card_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cardId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // --- Helper method ---
    private Card mapResultSetToCard(ResultSet rs) throws SQLException {
        Card card = new Card();
        card.setCardId(rs.getInt("card_id"));
        card.setAccountId(rs.getInt("account_id"));
        card.setCardNumber(rs.getString("card_number"));

        // ✅ Convert DATE -> YearMonth
        Date expiry = rs.getDate("expiry_date");
        if (expiry != null) {
            card.setExpiryDate(YearMonth.from(expiry.toLocalDate()));
        }

        card.setCvv(rs.getString("cvv"));

        card.setCardTypeId(rs.getInt("card_type_id"));

        card.setStatus(rs.getString("status"));
        card.setIssuedAt(rs.getTimestamp("issued_at"));
        return card;
    }
}
