package com.leshka_and_friends.lgvb.service;

import com.leshka_and_friends.lgvb.dao.DAOFactory;
import com.leshka_and_friends.lgvb.dao.UserDAO;

/**
 * Factory for Services. Encapsulates how services get their dependencies.
 */
public final class ServiceFactory {

	private ServiceFactory() { }

	public static AuthService createAuthService() {
		UserDAO userDAO = DAOFactory.createUserDAO();
		return new AuthService(userDAO);
	}
}


