package beans;

import java.sql.Timestamp;

public class Partie{
	private Long id;
	private int score;
	private Timestamp date;
	private Long idPlayer;
	private boolean victory;
	
	public Partie(Long id, int score, Timestamp date, Long idPlayer, boolean winned) {
		super();
		this.id = id;
		this.score = score;
		this.date = date;
		this.idPlayer = idPlayer;
		this.victory = winned;
	}

	public Partie() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public Long getIdPlayer() {
		return idPlayer;
	}

	public void setIdPlayer(Long idPlayer) {
		this.idPlayer = idPlayer;
	}

	public boolean isWinned() {
		return victory;
	}

	public void setWinned(boolean winned) {
		this.victory = winned;
	}
	
}
