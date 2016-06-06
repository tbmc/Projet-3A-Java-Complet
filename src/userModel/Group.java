package userModel;

import java.util.Hashtable;


public class Group {


	public int idGroup;//contient l'identifiant du groupe
	public int nmbStudent;//contient le nombre d'étudiant dans le groupe
	public Hashtable<Integer, String> studentList;//contient les logins des étudiants dans le groupe
/**
 * Constructeur de la classe Group
 * @param idGroup
 * 
 */
	public Group(int idGroup){
		this.idGroup=idGroup;
		this.studentList = new Hashtable<>();
	}
	/**
	 * Ajoute un étudient dans la table de hachage du groupe
	 * @param StudentLogin
	 */
	public void addStudent(String StudentLogin){
		studentList.put(idGroup, StudentLogin);  
	}
	/**
	 * Renvoie l'identifiant du groupe
	 * @return
	 * un Int
	 */
	public int getID(){
		return idGroup;
	}
}