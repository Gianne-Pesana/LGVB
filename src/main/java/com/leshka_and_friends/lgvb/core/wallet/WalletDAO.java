package com.leshka_and_friends.lgvb.core.wallet;

import com.leshka_and_friends.lgvb.core.admin.WalletWithUser;
import com.leshka_and_friends.lgvb.utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WalletDAO {

    public Wallet addWallet(Wallet wallet) {
        String sql = "INSERT INTO wallets (user_id, account_number, balance, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, wallet.getUserId());
            stmt.setString(2, wallet.getAccountNumber());
            stmt.setDouble(3, wallet.getBalance());
            stmt.setString(4, wallet.getStatus());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating wallet failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    wallet.setWalletId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating wallet failed, no ID obtained.");
                }
            }

            return wallet;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Wallet getWalletById(int walletId) {
        String sql = "SELECT * FROM wallets WHERE wallet_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, walletId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapWallet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Wallet getWalletByUserId(int userId) {
        String sql = "SELECT * FROM wallets WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapWallet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Wallet getWalletByAccountNumber(String accountNumber) {
        String sql = "SELECT * FROM wallets WHERE account_number = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, accountNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapWallet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Wallet> getAllWallets() {
        List<Wallet> wallets = new ArrayList<>();
        String sql = "SELECT * FROM wallets";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                wallets.add(mapWallet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wallets;
    }

    public void updateWallet(Wallet wallet) {
        String sql = "UPDATE wallets SET account_number=?, balance=?, status=? WHERE wallet_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, wallet.getAccountNumber());
            stmt.setDouble(2, wallet.getBalance());
            stmt.setString(3, wallet.getStatus());
            stmt.setInt(4, wallet.getWalletId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<WalletWithUser> getAllWalletApplications() {
        List<WalletWithUser> list = new ArrayList<>();
        String sql = "SELECT w.wallet_id, w.user_id, w.account_number, w.balance, w.status, w.created_at, " +
                "u.email, u.first_name, u.last_name " +
                "FROM wallets w JOIN users u ON w.user_id = u.user_id " +
                "WHERE w.status = 'pending'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                WalletWithUser w = new WalletWithUser();
                w.walletId = rs.getInt("wallet_id");
                w.userId = rs.getInt("user_id");
                w.accountNumber = rs.getString("account_number");
                w.balance = rs.getDouble("balance");
                w.status = rs.getString("status");
                w.createdAt = rs.getTimestamp("created_at");
                w.email = rs.getString("email");
                w.firstName = rs.getString("first_name");
                w.lastName = rs.getString("last_name");
                list.add(w);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean walletExists(int walletId) throws SQLException {
        String query = "SELECT COUNT(*) FROM wallets WHERE wallet_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, walletId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        }

        return false;
    }

    public void updateWalletBalance(int walletId, double newBalance) throws SQLException {
        String sql = "UPDATE wallets SET balance = ? WHERE wallet_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, newBalance);
            stmt.setInt(2, walletId);
            stmt.executeUpdate();
        }
    }


    public void deleteWallet(int walletId) {
        String sql = "DELETE FROM wallets WHERE wallet_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, walletId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Wallet getWalletByUserEmail(String email) {
        String sql = "SELECT w.* FROM wallets w JOIN users u ON w.user_id = u.user_id WHERE u.email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapWallet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Wallet mapWallet(ResultSet rs) throws SQLException {
        Wallet wallet = new Wallet(
                rs.getInt("wallet_id"),
                rs.getInt("user_id"),
                rs.getString("account_number"),
                rs.getDouble("balance"),
                rs.getString("status"),
                null, // Card will be handled separately if needed
                rs.getTimestamp("created_at")
        );
        return wallet;
    }
}
