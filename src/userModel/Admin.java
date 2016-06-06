package userModel;


public class Admin extends User {
	/**
	 * contient l'identifiant de l'administrateur*/
	public int idAdmin;
	/**
	 * Cette fonction est le constructeur de la classe Admin
	 * @param login
	 * login de l'administrateur qui sera créé
	 * @param firstname
	 * prénom de l'administrateur qui sera créé
	 * @param surname
	 * Nom de Famille de l'administrateur qui sera créé
	 * @param password
	 * Mot de passe de l'administrateur qui sera créé
	 * @param idAdmin
	 * Identifiant de l'administrateur qui sera créé
	 */
	public Admin (String login, String firstname, String surname, String password,int idAdmin){
		super(login,firstname,surname,password);
		this.idAdmin=idAdmin;
	}
	/**
	 * Permet de renvoyer l'identifiant de l'administrateur
	 * @return
	 *  un Int
	 */
	public int getID(){
		return idAdmin;
	}
	
	@Override
	public String getInfo(){
		String info=login +" "+firstname+" "+surname+" " +idAdmin;
		return info;
	}
		
}
