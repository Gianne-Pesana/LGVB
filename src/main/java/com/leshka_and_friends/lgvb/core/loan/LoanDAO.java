package com.leshka_and_friends.lgvb.core.loan;


import com.leshka_and_friends.lgvb.core.loan.types.CarLoan;
import com.leshka_and_friends.lgvb.core.loan.types.HousingLoan;
import com.leshka_and_friends.lgvb.core.loan.types.PersonalLoan;
import com.leshka_and_friends.lgvb.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanDAO {

    // =============================
    // CREATE
    // =============================
    public void insertLoan(Loan loan) {
        String sql = "INSERT INTO loans (wallet_id, reference_number, principal, remaining_balance, interest_rate, status, loan_type, term_in_months) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, loan.getWalletId());
            ps.setString(2, loan.getReferenceNumber());
            ps.setDouble(3, loan.getPrincipal());
            ps.setDouble(4, loan.getRemainingBalance());
            ps.setDouble(5, loan.getInterestRate());
            ps.setString(6, loan.getStatus());
            ps.setString(7, getLoanType(loan));
            ps.setInt(8, loan.getTermInMonths());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int loanId = rs.getInt(1);
                    loan.setLoanId(loanId);
                    insertSubLoan(conn, loan); // Insert car/housing specific data
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertSubLoan(Connection conn, Loan loan) throws SQLException {
        if (loan instanceof CarLoan carLoan) {
            String sql = "INSERT INTO car_loans (loan_id, dealership_name, vehicle_model, vehicle_year) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, carLoan.getLoanId());
                ps.setString(2, carLoan.getDealershipName());
                ps.setString(3, carLoan.getCarModel());
                ps.setInt(4, carLoan.getCarYear());
                ps.executeUpdate();
            }
        } else if (loan instanceof HousingLoan housingLoan) {
            String sql = "INSERT INTO housing_loans (loan_id, developer_name, property_address) VALUES (?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, housingLoan.getLoanId());
                ps.setString(2, housingLoan.getDeveloperName());
                ps.setString(3, housingLoan.getPropertyAddress());
                ps.executeUpdate();
            }
        }
    }

    private String getLoanType(Loan loan) {
        if (loan instanceof CarLoan) return "car";
        if (loan instanceof HousingLoan) return "housing";
        return "personal";
    }

    // =============================
    // READ
    // =============================

    public Loan getById(int loanId) {
        String sql = "SELECT * FROM loans WHERE loan_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, loanId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Loan loan = mapLoan(rs, conn);
                    return loan;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Loan> getByWalletId(int walletId) {
        String sql = "SELECT * FROM loans WHERE wallet_id = ?";
        List<Loan> loans = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, walletId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    loans.add(mapLoan(rs, conn));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loans;
    }

    // Assuming you can link wallet_id → user email in another table
    public List<Loan> getByEmail(String email) {
        String sql = """
            SELECT l.*
            FROM loans l
            JOIN wallets w ON l.wallet_id = w.wallet_id
            JOIN users u ON w.user_id = u.user_id
            WHERE u.email = ?
            """;
        List<Loan> loans = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    loans.add(mapLoan(rs, conn));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loans;
    }

    // =============================
    // UPDATE
    // =============================
    public boolean updateStatus(int loanId, String newStatus) {
        String sql = "UPDATE loans SET status = ? WHERE loan_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newStatus);
            ps.setInt(2, loanId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateRemainingBalance(int loanId, double newBalance) {
        String sql = "UPDATE loans SET remaining_balance = ? WHERE loan_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, newBalance);
            ps.setInt(2, loanId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // =============================
    // DELETE
    // =============================
    public boolean deleteLoan(int loanId) {
        String sql = "DELETE FROM loans WHERE loan_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, loanId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // =============================
    // MAPPING METHOD
    // =============================
    private Loan mapLoan(ResultSet rs, Connection conn) throws SQLException {
        String loanType = rs.getString("loan_type");

        Loan loan;
        switch (loanType) {
            case "car" -> loan = mapCarLoan(rs, conn);
            case "housing" -> loan = mapHousingLoan(rs, conn);
            default -> loan = new PersonalLoan();
        }

        loan.setLoanId(rs.getInt("loan_id"));
        loan.setWalletId(rs.getInt("wallet_id"));
        loan.setReferenceNumber(rs.getString("reference_number"));
        loan.setPrincipal(rs.getDouble("principal"));
        loan.setRemainingBalance(rs.getDouble("remaining_balance"));
        loan.setInterestRate(rs.getDouble("interest_rate"));
        loan.setStatus(rs.getString("status"));
        loan.setCreatedAt(rs.getTimestamp("created_at"));
        loan.setTermInMonths(rs.getInt("term_in_months"));

        return loan;
    }

    private CarLoan mapCarLoan(ResultSet rs, Connection conn) throws SQLException {
        CarLoan carLoan = new CarLoan();

        String sql = "SELECT * FROM car_loans WHERE loan_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, rs.getInt("loan_id"));
            try (ResultSet subRs = ps.executeQuery()) {
                if (subRs.next()) {
                    carLoan.setDealershipName(subRs.getString("dealership_name"));
                    carLoan.setCarModel(subRs.getString("vehicle_model"));
                    carLoan.setCarYear(subRs.getInt("vehicle_year"));
                }
            }
        }
        return carLoan;
    }

    private HousingLoan mapHousingLoan(ResultSet rs, Connection conn) throws SQLException {
        HousingLoan housingLoan = new HousingLoan();

        String sql = "SELECT * FROM housing_loans WHERE loan_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, rs.getInt("loan_id"));
            try (ResultSet subRs = ps.executeQuery()) {
                if (subRs.next()) {
                    housingLoan.setDeveloperName(subRs.getString("developer_name"));
                    housingLoan.setPropertyAddress(subRs.getString("property_address"));
                }
            }
        }
        return housingLoan;
    }

    // =============================
    // ADMIN / SPECIAL QUERIES
    // =============================

    public List<Loan> getPendingLoans() {
        String sql = "SELECT * FROM loans WHERE status = ?";
        List<Loan> loans = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, Loan.PENDING);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    loans.add(mapLoan(rs, conn));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loans;
    }

    public String getStatusLatest(int walletId) throws SQLException{
        String sql = "SELECT status FROM loans WHERE wallet_id = ? ORDER BY loan_id DESC LIMIT 1;";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, walletId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString(1);
                }
            }
        }
        return null;
    }

}
