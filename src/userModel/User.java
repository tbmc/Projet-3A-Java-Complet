package userModel;



public class User {

	public String login, firstname,surname;//contient le login, le prénom et le nom de famille de l'utilisateur
	protected String password;//contient le mot de passe de l'utilisateur
	/**
	 * Construsteur d'utilisateur
	 * @param login
	 * Login de l'utilisateur
	 * @param firstname
	 * Prénom de l'utilisateur
	 * @param surname
	 * Nom de famille de l'utilisateur
	 * @param password
	 * Mot de passe de l'utilisateur
	 */
	public User(String login, String firstname, String surname, String password){
		this.login=login;
		this.firstname=firstname;
		this.surname=surname;
		this.password=password;
	}
	/**
	 * Renvoie le nom et le prénom de l'utilisateur
	 * @return
	 *le prénom et le nom en chaine de caractère
	 */
	public String getUserName(){
		return firstname+" "+surname;
	}
	/**
	 * Renvoie le login de l'utilisateur
	 * @return 
	 * le login en chaine de caractère
	 */
	public String getUserLogin(){
		return login;
	}
	/**
	 * Renvoie les informations de l'utilisateur(fonction @Override dans les sous-classes pour avoir toutes les informations)
	 * @return
	 * Une chaine de caractère avec toutes les informations de l'utilisateur
	 */
	public String getInfo(){
		String info="";
		return info;
	}
}
