package DAO;
import java.sql.Blob;
import beans.User;

public interface UserDao {
	public static String USERTABLE = "Users";
	public static String COLUMN_ID = "id";
	public static String COLUMN_EMAIL = "email";
	public static String COLUMN_PWD = "password";
	public static String COLUMN_PSEUDO = "pseudo";
	public static String COLUMN_BIRTH = "birthday";
	public static String COLUMN_PIC = "picture";
	public static String COLUMN_INSC = "inscription";
	
	
	public void insert(User user) throws DAOException;
	public User find(String identifiant) throws DAOException; /*find by pseudo or email*/
	public void updatePwd(Long id,String newPwd); 
	public void updatePic(Long id,Blob pic);
	public void updatePseudo(Long id,String pseudo);
	public void updateEmail(Long id,String email);
	public void deleteAccount(String ident);
}
