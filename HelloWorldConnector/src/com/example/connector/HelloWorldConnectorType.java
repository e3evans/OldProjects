package com.example.connector;

import com.google.enterprise.connector.spi.AuthenticationManager;
import com.google.enterprise.connector.spi.AuthorizationManager;
import com.google.enterprise.connector.spi.Connector;
import com.google.enterprise.connector.spi.RepositoryException;
import com.google.enterprise.connector.spi.RepositoryLoginException;
import com.google.enterprise.connector.spi.Session;
import com.google.enterprise.connector.spi.TraversalManager;

public class HelloWorldConnectorType implements Connector{

	@Override
	public Session login() throws RepositoryLoginException, RepositoryException {
		// TODO Auto-generated method stub
		return new HelloSession();
	}
	public class HelloSession implements Session{

		@Override
		public AuthenticationManager getAuthenticationManager()
				throws RepositoryException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public AuthorizationManager getAuthorizationManager()
				throws RepositoryException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public TraversalManager getTraversalManager()
				throws RepositoryException {
			// TODO Auto-generated method stub
			return null;
		}
		
	}

}
