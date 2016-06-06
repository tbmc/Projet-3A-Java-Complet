package userModel;


public class Teacher extends User{
	/**
	 * contient l'identifiant de la classe enseignant
	 */
	public int idTeacher;
	/**
	 * Constructeur de la classe enseignent
	 * @param login
	 * Login de l'enseignant 
	 * @param firstname
	 * Prénom de l'enseignant 
	 * @param surname
	 * Nom de Famille de l'enseignant 
	 * @param password
	 * Mot de passe de l'enseignant 
	 * @param idTeacher
	 * Identifiant de l'enseignant 
	 */
	public Teacher(String login, String firstname, String surname, String password,int idTeacher) {
		super(login,firstname,surname,password);
		this.idTeacher=idTeacher;
	}
	
	
	@Override
	public String getInfo(){
		String info=login +" "+firstname+" "+surname+" " +idTeacher;
		return info;
	}

}
