package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import beans.Partie;

public class PartieDaoImpl implements PartieDAO {
	 private DAOFactory daoFactory;
	 private static final String SQL_CREATE = "CREATE TABLE IF NOT EXISTS "
	    		+ GAMETABLE 		+ "("
	    		+ COLUMN_ID			+ " INTEGER NOT NULL PRIMARY KEY, "
	    		+ COLUMN_SCORE		+ " INTEGER NOT NULL, "
	    		+ COLUMN_PLAYER		+ " INTEGER NOT NULL,"
	    		+ COLUMN_VICTORY	+  " INTEGER NOT NULL,"
	 			+ COLUMN_DATE + "TIMESTAMP NOT NULL,"
	 			+ "FOREIGN KEY (" + COLUMN_PLAYER + ") REFERENCES"
	 			+ UserDao.USERTABLE + "(" +UserDao.COLUMN_ID +")"	
	 			+")";
	 private static final String SQL_INSERT = "INSERT INTO "
	    		+ GAMETABLE 		+ "("
	    		+ COLUMN_SCORE 	+ ", "
	    		+ COLUMN_PLAYER	+ ","
	    		+ COLUMN_VICTORY + ","
	    		+ COLUMN_DATE  +") VALUES(?, ?, ?,?)";
	 private static final String SQL_SELECT = "SELECT "
	    		+ COLUMN_ID 		+ ", "
	    		+ COLUMN_SCORE		+ ", "
	    		+ COLUMN_PLAYER	+ ", "
	    		+ COLUMN_VICTORY + ", "
	    		+ COLUMN_DATE + " FROM "
	    		+ GAMETABLE		+ " WHERE "
	    	    + COLUMN_ID		+ " = ?";
	 private static final String SQL_DELETE = "DELETE FROM "
	    		+ GAMETABLE		+ " WHERE "
	    	    + COLUMN_ID		+ " = ?";
	 private static final String SQL_UPDATE_SCORE = "UPDATE "
				+ GAMETABLE		+ " "
			    + COLUMN_ID			+ " = ? WHERE "
				+ COLUMN_SCORE		+ " = ?";
	 private static final String SQL_SELECT_GAMES_BYPLAYER = "SELECT "
	    		+ COLUMN_ID 		+ ", "
	    		+ COLUMN_SCORE		+ ", "
	    		+ COLUMN_PLAYER	+ ", "
	    		+ COLUMN_VICTORY + ", "
	    		+ COLUMN_DATE + " FROM "
	    		+ GAMETABLE		+ " WHERE "
	    	    + COLUMN_PLAYER		+ " = ?";
	 private static final String SQL_SELECT_WINNEDGAMES_BYPLAYER = "SELECT "
	    		+ COLUMN_ID 		+ ", "
	    		+ COLUMN_SCORE		+ ", "
	    		+ COLUMN_PLAYER	+ ", "
	    		+ COLUMN_VICTORY + ", "
	    		+ COLUMN_DATE + " FROM "
	    		+ GAMETABLE		+ " WHERE "
	    	    + COLUMN_PLAYER		+ " = ? AND"
	    	    + COLUMN_VICTORY  + " = 1 ";
	public PartieDaoImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	     
	     try {
		        connexion = daoFactory.getConnection();
		        preparedStatement = DAOUTILITY.initRequetePreparee(connexion, SQL_CREATE, false);
		        int statut = preparedStatement.executeUpdate();
		        if(statut != 0) {   /*The statement executed was a DDL statement if table created it returns 0.*/
		            throw new DAOException("Echec de la création de la table partie.");
		        }
		 } catch(SQLException e) {
		        throw new DAOException(e);
		 } finally {
		        DAOUTILITY.fermeturesSilencieuses(preparedStatement, connexion);
		 }
	}

	@Override
	public void insert(Partie partie) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet autoGeneratedValues = null;
		
		try {
			connexion = daoFactory.getConnection();
			Object objets[] = { partie.getScore(),partie.getIdPlayer(),partie.getDate() ,partie.isWinned()};
			preparedStatement = DAOUTILITY.initRequetePreparee(connexion, SQL_INSERT, true, objets);
			int statut = preparedStatement.executeUpdate();
			if(statut == 0) //aucune ligne insérée 
				throw new DAOException("Echec de la création de la partie, aucune ligne ajoutée dans la table.");
			autoGeneratedValues = preparedStatement.getGeneratedKeys();
			if(autoGeneratedValues.next())
					partie.setId(autoGeneratedValues.getLong(COLUMN_ID));
			else 
				throw new DAOException("Échec de la création de la partie en base, aucun ID auto-généré retourné.");
		}catch(SQLException e) {
			 throw new DAOException(e);
		}finally {
			 DAOUTILITY.fermeturesSilencieuses(autoGeneratedValues, preparedStatement, connexion);
		}
	}

	@Override
	public Partie find(Long id) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resSet = null;
		Partie game = null;
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = DAOUTILITY.initRequetePreparee(connexion, SQL_SELECT, false, id);
			resSet = preparedStatement.executeQuery();
			if(resSet.next())
				game = map(resSet);
		}catch(SQLException e) {
			throw new DAOException(e);
		}finally {
			DAOUTILITY.fermeturesSilencieuses(resSet, preparedStatement, connexion);
		}
		return game;
	}


	@Override
	public void updateScore(Long id,int newScore) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet autoGeneratedValues = null;
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = DAOUTILITY.initRequetePreparee(connexion, SQL_UPDATE_SCORE, true, id , newScore);
			int statut = preparedStatement.executeUpdate();
			if(statut == 0) //aucune ligne insérée 
				throw new DAOException("Echec modication score");
		}catch(SQLException e) {
			 throw new DAOException(e);
		}finally {
			 DAOUTILITY.fermeturesSilencieuses(autoGeneratedValues, preparedStatement, connexion);
		}
	}

	@Override
	public void delete(Long id) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet autoGeneratedValues = null;
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = DAOUTILITY.initRequetePreparee(connexion, SQL_DELETE, true, id);
			int statut = preparedStatement.executeUpdate();
			if(statut == 0) //aucune ligne insérée 
				throw new DAOException("Echec suppression partie");
		}catch(SQLException e) {
			 throw new DAOException(e);
		}finally {
			 DAOUTILITY.fermeturesSilencieuses(autoGeneratedValues, preparedStatement, connexion);
		}
		
	}
	
	@SuppressWarnings("null")
	@Override
	public List<Partie> getGames(Long id) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resSet = null;
		List<Partie> game = null;
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = DAOUTILITY.initRequetePreparee(connexion, SQL_SELECT_GAMES_BYPLAYER, false, id);
			resSet = preparedStatement.executeQuery();
			if(resSet.next())
				game.add(map(resSet));
		}catch(SQLException e) {
			throw new DAOException(e);
		}finally {
			DAOUTILITY.fermeturesSilencieuses(resSet, preparedStatement, connexion);
		}
		return game;
	}
	@SuppressWarnings("null")
	@Override
	public List<Partie> getWinnedGames(Long id) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resSet = null;
		List<Partie> game = null;
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = DAOUTILITY.initRequetePreparee(connexion, SQL_SELECT_WINNEDGAMES_BYPLAYER, false, id);
			resSet = preparedStatement.executeQuery();
			if(resSet.next())
				game.add(map(resSet));
		}catch(SQLException e) {
			throw new DAOException(e);
		}finally {
			DAOUTILITY.fermeturesSilencieuses(resSet, preparedStatement, connexion);
		}
		return game;
	}
	
	private Partie map(ResultSet resSet) throws SQLException {
		Partie game = new Partie();
		game.setId(resSet.getLong(COLUMN_ID));
		game.setScore(resSet.getInt(COLUMN_SCORE));
		game.setIdPlayer(resSet.getLong(COLUMN_PLAYER));
		game.setWinned(resSet.getBoolean(COLUMN_VICTORY));
		game.setDate(resSet.getTimestamp(COLUMN_DATE));
		return game;
	}

}
