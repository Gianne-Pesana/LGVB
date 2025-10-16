package com.leshka_and_friends.lgvb.card;

import com.leshka_and_friends.lgvb.core.DBConnection;
import com.leshka_and_friends.lgvb.core.EncryptionUtils;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardDAO {

    // CREATE
    public Card createCardForAccount(int accountId) throws Exception {
        String cardNumber = generateCardNumber();
        String last4 = cardNumber.substring(cardNumber.length() - 4);
        int month = generateExpiryMonth();
        int year = generateExpiryYear();
        String encrypted = EncryptionUtils.encrypt(cardNumber);

        String sql = """
            INSERT INTO cards (account_id, card_type, card_token, card_last4, expiry_month, expiry_year, status)
            VALUES (?, 'visa', ?, ?, ?, ?, 'active')
        """;

        int key;
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, accountId);
            ps.setBytes(2, encrypted.getBytes());
            ps.setString(3, last4);
            ps.setInt(4, month);
            ps.setInt(5, year);
            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    key = generatedKeys.getInt(1);
                } else {
                    key = -1;
                    throw new SQLException("Creating card failed, no ID obtained.");
                }
            }
        }

        return getCardById(key);
    }

    // READ (single)
    public Card getCardById(int cardId) {
        String sql = "SELECT * FROM cards WHERE card_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cardId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapToCard(rs);
            }
        } catch (Exception e) {
            System.out.println("Error in getting card by ID: " + cardId);
        }
        return null;
    }

    public Card getCardByAccountId(int accountId) {
        String sql = "SELECT * FROM cards WHERE account_id = ? LIMIT 1";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapToCard(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // READ (by account)
    public List<Card> getCardsByAccount(int accountId) throws Exception {
        List<Card> cards = new ArrayList<>();
        String sql = "SELECT * FROM cards WHERE account_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cards.add(mapToCard(rs));
            }
        }
        return cards;
    }

    // UPDATE (status)
    public boolean updateCardStatus(int cardId, String newStatus) throws SQLException {
        String sql = "UPDATE cards SET status = ? WHERE card_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, cardId);
            return ps.executeUpdate() > 0;
        }
    }

    // UPDATE (expiry)
    public boolean updateCardExpiry(int cardId, int newMonth, int newYear) throws SQLException {
        String sql = "UPDATE cards SET expiry_month = ?, expiry_year = ? WHERE card_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, newMonth);
            ps.setInt(2, newYear);
            ps.setInt(3, cardId);
            return ps.executeUpdate() > 0;
        }
    }

    // DELETE
    public boolean deleteCard(int cardId) throws SQLException {
        String sql = "DELETE FROM cards WHERE card_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cardId);
            return ps.executeUpdate() > 0;
        }
    }

    // =====================
    // Internal helpers
    // =====================
    private Card mapToCard(ResultSet rs) throws Exception {
        Card c = new Card();
        c.setCardId(rs.getInt("card_id"));
        c.setAccountId(rs.getInt("account_id"));
        c.setCardType(rs.getString("card_type"));
        c.setCardLast4(rs.getString("card_last4"));
        c.setExpiryMonth(rs.getInt("expiry_month"));
        c.setExpiryYear(rs.getInt("expiry_year"));
        c.setStatus(rs.getString("status"));

        byte[] tokenBytes = rs.getBytes("card_token");
        if (tokenBytes != null) {
            c.setFullCardNumber(EncryptionUtils.decrypt(new String(tokenBytes)));
        }
        
        c.printInfo();
        return c;
    }

    private String generateCardNumber() {
        StringBuilder sb = new StringBuilder("4"); // simulate Visa prefix
        for (int i = 0; i < 15; i++) {
            sb.append((int) (Math.random() * 10));
        }
        return sb.toString();
    }

    private int generateExpiryMonth() {
        return (int) (Math.random() * 12) + 1;
    }

    private int generateExpiryYear() {
        return java.time.LocalDate.now().getYear() + 5;
    }
}
