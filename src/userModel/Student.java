package userModel;



public class Student extends User {

	public int idStudent;//contient l'identifient de l'élève
	public int idGroup;//contient l'identifient du groupe
	/**
	 * Constructeur de la classe élève
	 * @param login
	 * Login de l'étudiant
	 * @param firstname
	 * Prénom de l'étudiant
	 * @param surname
	 * Nom de famille de l'étudiant
	 * @param password
	 * Mot de passe de l'étudiant
	 * @param idStudent
	 * Identifient de l'étudiant
	 * @param idGroup
	 * identifiant du groupe auquel appartient l'élève
	 */
	public Student(String login, String firstname, String surname, String password,int idStudent,int idGroup) {
		super(login,firstname,surname,password);
		this.idStudent=idStudent;
		this.idGroup=idGroup;
	}
	/**
	 * Renvoie l'identifiant du groupe de l'élève
	 * @return
	 * un Int
	 */
	public int getIdGroup(){
		return idGroup;
	}
	/**
	 * Change l'élève de groupe
	 * @param GroupId
	 */
	public void addgroup(int GroupId){
		idGroup=GroupId;
	}
	/**Renvoie l'identifiant de l'élève
	 * 
	 * @return
	 */
	public int getID(){
		return idStudent;
	}
	
	@Override
	public String getInfo(){
		String info=login +" "+firstname+" "+surname+" " +idStudent+" " +idGroup;
		return info;
	}
	
}
