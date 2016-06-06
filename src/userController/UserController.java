package userController;

import userModel.UserDB;
/**
 * Cette classe est le contrôleur d'utilisateurs que vous devez implémenter. 
 * Elle contient un attribut correspondant à la base de données utilisateurs que vous allez créer.
 * Elle contient toutes les fonctions de l'interface IUserController que vous devez implémenter.
 * 
 * @author Jose Mennesson (Mettre à jour)
 * @version 04/2016 (Mettre à jour)
 * 
 */

//TODO Classe à modifier

public class UserController implements IUserController
{
	
	/**
	 * Contient une instance de base de données d'utilisateurs
	 * 
	 */
	private UserDB userDB=null;
	
	
	/**
	 * Constructeur de controleur d'utilisateurs créant la base de données d'utilisateurs
	 * 
	 * @param userfile
	 * 		Fichier XML contenant la base de données d'utilisateurs
	 */
	public UserController(String userfile){
		UserDB userDB=new UserDB(userfile);
		this.setUserDB(userDB);
	}

	@Override
	public String getUserName(String userLogin) {
		// TODO Auto-generated method stub
		return userDB.getUserName(userLogin);
	}

	@Override
	public String getUserClass(String userLogin, String userPwd) {
		// TODO Auto-generated method stub
		return userDB.getUserClass(userLogin, userPwd);
	}

	@Override
	public int getStudentGroup(String studentLogin) {
		// TODO Auto-generated method stub
		return userDB.getStudentGroup(studentLogin);
	}

	@Override
	public boolean addAdmin(String adminLogin, String newAdminlogin, int adminID, String firstname, String surname,
			String pwd) {
		// TODO Auto-generated method stub
		return userDB.addAdmin(adminLogin, newAdminlogin, adminID, firstname, surname, pwd);
	}

	@Override
	public boolean addTeacher(String adminLogin, String newteacherLogin, int teacherID, String firstname,
			String surname, String pwd) {
		// TODO Auto-generated method stub
		return userDB.addTeacher(adminLogin, newteacherLogin, teacherID, firstname, surname, pwd);
	}

	@Override
	public boolean addStudent(String adminLogin, String newStudentLogin, int studentID, String firstname,
			String surname, String pwd) {
		// TODO Auto-generated method stub
		return userDB.addStudent(adminLogin, newStudentLogin, studentID, firstname, surname, pwd);
	}

	@Override
	public boolean removeUser(String adminLogin, String userLogin) {
		// TODO Auto-generated method stub
		return userDB.removeUser(adminLogin, userLogin);
	}

	@Override
	public boolean addGroup(String adminLogin, int groupId) {
		// TODO Auto-generated method stub
		return userDB.addGroup(adminLogin, groupId);
	}

	@Override
	public boolean removeGroup(String adminLogin, int groupId) {
		// TODO Auto-generated method stub
		return userDB.removeGroup(adminLogin, groupId);
	}

	@Override
	public boolean associateStudToGroup(String adminLogin, String studentLogin, int groupId) {
		// TODO Auto-generated method stub
		return userDB.associateStudToGroup(adminLogin, studentLogin, groupId);
	}

	@Override
	public String[] usersToString() {
		// TODO Auto-generated method stub
		return userDB.usersToString();
	}

	@Override
	public String[] usersLoginToString() {
		// TODO Auto-generated method stub
		return userDB.usersLoginToString();
	}

	@Override
	public String[] studentsLoginToString() {
		// TODO Auto-generated method stub
		return userDB.studentsLoginToString();
	}

	@Override
	public String[] groupsIdToString() {
		// TODO Auto-generated method stub
		return userDB.groupsIdToString();
	}

	@Override
	public String[] groupsToString() {
		// TODO Auto-generated method stub
		return userDB.groupsToString();
	}

	@Override
	public boolean loadDB() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveDB() {
		// TODO Auto-generated method stub
		return false;
	}

	public UserDB getUserDB() {
		return userDB;
	}

	public void setUserDB(UserDB userDB) {
		this.userDB = userDB;
	}
	
	

}

