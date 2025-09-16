/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.dao;

import com.leshka_and_friends.lgvb.model.Account;
import com.leshka_and_friends.lgvb.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;

import java.util.List;

public class AccountSQL implements AccountDAO {

    @Override
    public void addAccount(Account account) {
        String sql = "INSERT INTO accounts (user_id, account_type, account_number, balance, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, account.getUserId());
            stmt.setString(2, account.getAccountType());
            stmt.setString(3, account.getAccountNumber());
            stmt.setDouble(4, account.getBalance());
            stmt.setString(5, account.getStatus());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Account getAccountById(int id) {
        String sql = "SELECT * FROM accounts WHERE account_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapAccount(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Account> getAccountsByUserId(int userId) {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                accounts.add(mapAccount(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts";
        try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                accounts.add(mapAccount(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public void updateAccount(Account account) {
        String sql = "UPDATE accounts SET account_type=?, account_number=?, balance=?, status=? WHERE account_id=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, account.getAccountType());
            stmt.setString(2, account.getAccountNumber());
            stmt.setDouble(3, account.getBalance());
            stmt.setString(4, account.getStatus());
            stmt.setInt(5, account.getAccountId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAccount(int id) {
        String sql = "DELETE FROM accounts WHERE account_id=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Account mapAccount(ResultSet rs) throws SQLException {
        Account acc = new Account();
        acc.setAccountId(rs.getInt("account_id"));
        acc.setUserId(rs.getInt("user_id"));
        acc.setAccountType(rs.getString("account_type"));
        acc.setAccountNumber(rs.getString("account_number"));
        acc.setBalance(rs.getDouble("balance"));
        acc.setStatus(rs.getString("status"));
        acc.setCreatedAt(rs.getTimestamp("created_at"));
        return acc;
    }
}
