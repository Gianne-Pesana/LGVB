package com.leshka_and_friends.lgvb.dao;

/**
 * Central factory for DAO creation. Keeps wiring in one place and
 * avoids scattering concrete implementation usage across the codebase.
 */
public final class DAOFactory {

	private DAOFactory() { }

	public static UserDAO createUserDAO() {
		return new UserDAOImpl();
	}

	public static AccountDAO createAccountDAO() {
		return new AccountDAOImpl();
	}

	public static TransactionDAO createTransactionDAO() {
		return new TransactionDAOImpl();
	}
}


