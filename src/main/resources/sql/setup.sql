-- =================================================================
-- LGVB Digital Banking System - Database Setup Script
-- =================================================================
-- This script will:
-- 1. Drop the existing database (if it exists) and create a new one.
-- 2. Create a dedicated user for the application.
-- 3. Grant the necessary permissions to the user.
-- 4. Create all the required tables.
-- =================================================================

-- Create and use the database
DROP DATABASE IF EXISTS lgvb_banking;
CREATE DATABASE IF NOT EXISTS lgvb_banking;
USE lgvb_banking;

-- Create the application user and grant privileges
CREATE USER IF NOT EXISTS 'lgvb_app'@'localhost' IDENTIFIED BY 'lgvb_password123';
GRANT ALL PRIVILEGES ON lgvb_banking.* TO 'lgvb_app'@'localhost';
FLUSH PRIVILEGES;

-- ==============================
-- Dynamic "enum" tables
-- ==============================
-- (not used anymore)
CREATE TABLE account_types (
    account_type_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE card_types (
    card_type_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE transaction_types (
    transaction_type_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- ==============================
-- USERS
-- ==============================
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    phone_number VARCHAR(13),
    date_of_birth DATE NOT NULL,
    role ENUM('customer', 'admin') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    profile_image VARCHAR(255) DEFAULT '/profile/default.jpg',
    totp_secret VARCHAR(64) DEFAULT NULL
);

-- ==============================
-- WALLETS
-- ==============================
CREATE TABLE wallets (
    wallet_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL UNIQUE, -- one wallet per user
    account_number VARCHAR(10) NOT NULL UNIQUE,
    balance DECIMAL(15,2) DEFAULT 0.00,
    status ENUM('active', 'pending', 'closed') DEFAULT 'active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_wallets_user FOREIGN KEY (user_id) REFERENCES users(user_id)
        ON DELETE CASCADE ON UPDATE CASCADE
);


-- ==============================
-- CARDS
-- ==============================
CREATE TABLE cards (
    card_id INT AUTO_INCREMENT PRIMARY KEY,
    wallet_id INT,
    card_type VARCHAR(20),
    card_token VARBINARY(255),
    card_last4 CHAR(4),
    expiry_month TINYINT,
    expiry_year SMALLINT,
    status ENUM('active', 'blocked', 'expired') DEFAULT 'active',
    FOREIGN KEY (wallet_id) REFERENCES wallets(wallet_id)
);


-- ==============================
-- TRANSACTIONS
-- ==============================
CREATE TABLE transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    wallet_id INT NOT NULL,
    transaction_type_id INT NOT NULL,
    amount DECIMAL(15,2) NOT NULL CHECK (amount > 0),
    related_wallet_id INT,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('success', 'failed', 'pending') NOT NULL,
    CONSTRAINT fk_transactions_wallet FOREIGN KEY (wallet_id) REFERENCES wallets(wallet_id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_transactions_related FOREIGN KEY (related_wallet_id) REFERENCES wallets(wallet_id)
        ON DELETE SET NULL ON UPDATE CASCADE,
    CONSTRAINT fk_transactions_type FOREIGN KEY (transaction_type_id) REFERENCES transaction_types(transaction_type_id)
        ON DELETE RESTRICT ON UPDATE CASCADE
);

-- ==============================
-- LOANS
-- ==============================
CREATE TABLE loans (
    loan_id INT AUTO_INCREMENT PRIMARY KEY,
    wallet_id INT NOT NULL,
    amount_requested DECIMAL(15,2) NOT NULL,
    amount_approved DECIMAL(15,2),
    status ENUM('pending', 'approved', 'rejected', 'paid') NOT NULL,
    requested_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    approved_by INT,
    CONSTRAINT fk_loans_wallet FOREIGN KEY (wallet_id) REFERENCES wallets(wallet_id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_loans_approver FOREIGN KEY (approved_by) REFERENCES users(user_id)
        ON DELETE SET NULL ON UPDATE CASCADE
);

-- ==============================
-- INQUIRIES
-- ==============================
CREATE TABLE inquiries (
    inquiry_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    message TEXT NOT NULL,
    status ENUM('open', 'resolved', 'closed') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    resolved_by INT,
    CONSTRAINT fk_inquiries_user FOREIGN KEY (user_id) REFERENCES users(user_id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_inquiries_resolver FOREIGN KEY (resolved_by) REFERENCES users(user_id)
        ON DELETE SET NULL ON UPDATE CASCADE
);

-- ==============================
-- Insert default data for dynamic tables (not used)
-- ==============================
INSERT INTO account_types (name) VALUES ('savings'), ('checking');
INSERT INTO card_types (name) VALUES ('debit'), ('credit'), ('prepaid'), ('virtual');
INSERT INTO transaction_types (name) VALUES ('deposit'), ('withdrawal'), ('transfer');