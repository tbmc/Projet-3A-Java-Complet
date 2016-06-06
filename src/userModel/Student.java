package userModel;



public class Student extends User {

	public int idStudent;//contient l'identifient de l'�l�ve
	public int idGroup;//contient l'identifient du groupe
	/**
	 * Constructeur de la classe �l�ve
	 * @param login
	 * Login de l'�tudiant
	 * @param firstname
	 * Pr�nom de l'�tudiant
	 * @param surname
	 * Nom de famille de l'�tudiant
	 * @param password
	 * Mot de passe de l'�tudiant
	 * @param idStudent
	 * Identifient de l'�tudiant
	 * @param idGroup
	 * identifiant du groupe auquel appartient l'�l�ve
	 */
	public Student(String login, String firstname, String surname, String password,int idStudent,int idGroup) {
		super(login,firstname,surname,password);
		this.idStudent=idStudent;
		this.idGroup=idGroup;
	}
	/**
	 * Renvoie l'identifiant du groupe de l'�l�ve
	 * @return
	 * un Int
	 */
	public int getIdGroup(){
		return idGroup;
	}
	/**
	 * Change l'�l�ve de groupe
	 * @param GroupId
	 */
	public void addgroup(int GroupId){
		idGroup=GroupId;
	}
	/**Renvoie l'identifiant de l'�l�ve
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
