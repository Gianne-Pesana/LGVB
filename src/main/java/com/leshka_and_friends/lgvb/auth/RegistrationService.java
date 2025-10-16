/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.auth;

import com.leshka_and_friends.lgvb.account.AccountDAO;
import com.leshka_and_friends.lgvb.account.AccountSQL;
import com.leshka_and_friends.lgvb.card.CardDAO;
import com.leshka_and_friends.lgvb.card.CardSQL;
import com.leshka_and_friends.lgvb.core.DBConnection;
import com.leshka_and_friends.lgvb.user.User;
import com.leshka_and_friends.lgvb.user.UserDAO;
import com.leshka_and_friends.lgvb.user.UserSQL;
import java.sql.Connection;

/**
 *
 * @author giann
 */
public class RegistrationService {

    private final UserDAO userDAO = new UserSQL();
    private final AccountDAO accountDAO = new AccountSQL();
    private final CardDAO cardDAO = new CardSQL();

    public boolean registerNewUser(User user) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // start transaction

            userDAO.addUser(user);
            accountDAO.createDefaultAccount(user.getUserId());
            cardDAO.createCardForAccount(conn, accountId);

            conn.commit(); // all good
            return true;
        } catch (Exception e) {
            if (conn != null) conn.rollback(); // rollback on error
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) conn.setAutoCommit(true);
        }
    }
}

