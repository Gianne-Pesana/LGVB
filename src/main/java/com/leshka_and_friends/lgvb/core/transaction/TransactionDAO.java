package com.leshka_and_friends.lgvb.core.transaction;

import com.leshka_and_friends.lgvb.utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    // CREATE
    public boolean createTransaction(Transaction transaction) throws SQLException {
        try (Connection connection = DBConnection.getConnection()) {
            int typeId = getTransactionTypeId(connection, transaction.getTransactionType().getStrValue());
            if (typeId == -1) {
                throw new SQLException("Invalid transaction type: " + transaction.getTransactionType());
            }

            String sql = "INSERT INTO transactions (wallet_id, transaction_type_id, amount, related_wallet_id, status) "
                       + "VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, transaction.getWalletId());
                stmt.setInt(2, typeId);
                stmt.setDouble(3, transaction.getAmount());
                if (transaction.getRelatedWalletId() != -1) {
                    stmt.setInt(4, transaction.getRelatedWalletId());
                } else {
                    stmt.setNull(4, Types.INTEGER);
                }
                stmt.setString(5, transaction.getStatus().name());
                return stmt.executeUpdate() > 0;
            }
        }
    }

    // READ: by ID (includes account names)
    public Transaction getTransactionById(int transactionId) throws SQLException {
        String sql = """
            SELECT
                t.*,
                tt.name AS transaction_type,
                u1.first_name AS account_first_name,
                u1.last_name AS account_last_name,
                u2.first_name AS related_first_name,
                u2.last_name AS related_last_name
            FROM transactions t
            JOIN transaction_types tt ON t.transaction_type_id = tt.transaction_type_id
            JOIN wallets a1 ON t.wallet_id = a1.wallet_id
            JOIN users u1 ON a1.user_id = u1.user_id
            LEFT JOIN wallets a2 ON t.related_wallet_id = a2.wallet_id
            LEFT JOIN users u2 ON a2.user_id = u2.user_id
            WHERE t.transaction_id = ?
        """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, transactionId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTransaction(rs);
                }
            }
        }

        return null;
    }

    // READ: all transactions for an account (includes account names)
    public List<Transaction> getTransactionsByWallet(int walletId)  {
        List<Transaction> transactions = new ArrayList<>();

        String sql = """
            SELECT
                t.*,
                tt.name AS transaction_type,
                u1.first_name AS account_first_name,
                u1.last_name AS account_last_name,
                u2.first_name AS related_first_name,
                u2.last_name AS related_last_name
            FROM transactions t
            JOIN transaction_types tt ON t.transaction_type_id = tt.transaction_type_id
            JOIN wallets a1 ON t.wallet_id = a1.wallet_id
            JOIN users u1 ON a1.user_id = u1.user_id
            LEFT JOIN wallets a2 ON t.related_wallet_id = a2.wallet_id
            LEFT JOIN users u2 ON a2.user_id = u2.user_id
            WHERE t.wallet_id = ? AND t.status = 'success'
            ORDER BY t.timestamp DESC
        """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, walletId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(mapResultSetToTransaction(rs));
                }
            }
        } catch (SQLException se) {
            System.out.println("Error in fetching transactions!");
            se.printStackTrace();
        } 

        return transactions;
    }

    // UPDATE: amount or status
    public boolean updateTransaction(Transaction transaction) throws SQLException {
        try (Connection connection = DBConnection.getConnection()) {
            String sql = "UPDATE transactions SET amount = ?, status = ? WHERE transaction_id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setDouble(1, transaction.getAmount());
                stmt.setString(2, transaction.getStatus().name());
                stmt.setInt(3, transaction.getTransactionId());
                return stmt.executeUpdate() > 0;
            }
        }
    }

    // DELETE
    public boolean deleteTransaction(int transactionId) throws SQLException {
        try (Connection connection = DBConnection.getConnection()) {
            String sql = "DELETE FROM transactions WHERE transaction_id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, transactionId);
                return stmt.executeUpdate() > 0;
            }
        }
    }

    // Helper: get transaction_type_id from name
    private int getTransactionTypeId(Connection connection, String typeName) throws SQLException {
        String sql = "SELECT transaction_type_id FROM transaction_types WHERE name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, typeName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("transaction_type_id");
                }
            }
        }
        return -1; // not found
    }

    // Map ResultSet to Transaction POJO, including account names
    private Transaction mapResultSetToTransaction(ResultSet rs) throws SQLException {
        Transaction t = new Transaction();
        t.setTransactionId(rs.getInt("transaction_id"));
        t.setWalletId(rs.getInt("wallet_id"));
        t.setTransactionType(TransactionType.fromString(rs.getString("transaction_type")));
        t.setAmount(rs.getDouble("amount"));

        int relatedId = rs.getInt("related_wallet_id");
        t.setRelatedWalletId(rs.wasNull() ? -1 : relatedId);

        t.setStatus(TransactionStatus.fromString(rs.getString("status")));
        t.setTimestamp(rs.getTimestamp("timestamp"));

        // Main account name

        // Related account name (may be null)
        String relatedFirst = rs.getString("related_first_name");
        String relatedLast = rs.getString("related_last_name");
        if (relatedFirst != null && relatedLast != null) {
            t.setRelatedAccountName(relatedFirst + " " + relatedLast);
        } else {
            t.setRelatedAccountName(null);
        }

        return t;
    }
}
