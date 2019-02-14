package DAO;

import java.util.List;

import beans.Partie;

public interface PartieDAO {
		
	public static String GAMETABLE = "games";
	public static String COLUMN_ID = "id";
	public static String COLUMN_SCORE = "score";
	public static String COLUMN_DATE = "date";
	public static String COLUMN_PLAYER = "player";
	public static String COLUMN_VICTORY = "victory";
	
	
	public void insert(Partie partie);
	public Partie find(Long id);
	public void updateScore(Long id,int newScore);
	public void delete(Long id);
	public List<Partie> getGames(Long id);
	public List<Partie> getWinnedGames(Long id);
}
