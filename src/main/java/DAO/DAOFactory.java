package DAO;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DAOFactory {
	private static final String Fichier_Properties="/WEB-INF/dao.properties";
	private static final String PROPERTY_URL = "url";
	private static final String PROPERTY_DRIVER = "driver";
	
	private String url;
	
	DAOFactory(String url){
		this.url=url;
	}
	
	public static DAOFactory getInstance() throws DAOException{
		Properties properties = new Properties();
		String url,driver;
		
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream fichierProperties = classLoader.getResourceAsStream(Fichier_Properties);
		
		if(fichierProperties == null) {
			throw new DAOConfigurationException("le fichier properties"+Fichier_Properties+"est introuvable");
		}
		
		try {
			properties.load(fichierProperties);
			url = properties.getProperty(PROPERTY_URL);
			driver = properties.getProperty(PROPERTY_DRIVER);
		}catch(IOException e) {
			throw new DAOConfigurationException("Impossible de charger le fichier properties "+Fichier_Properties,e);
		}
		
		try {
			Class.forName(driver);
		}catch(ClassNotFoundException e) {
			throw new DAOConfigurationException("Le driver est introuvable ",e);
		}
		
		
		return new DAOFactory(url);
	}
	
	Connection getConnection() throws SQLException{
		return DriverManager.getConnection(url);
	}
	
	public UserDao getUserDao() {
		return new UserDaoImpl(this);
	}
	
	public PartieDAO getPartieDAO() {
		return new PartieDaoImpl(this);
	}
	
	
}
