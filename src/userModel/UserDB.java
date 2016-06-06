package userModel;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * 
 * Cette classe gére la base de données d'utilisateurs. Elle doit permettre de sauvegarder et charger les utilisateurs et les groupes à partir d'un fichier XML. 
 * La structure du fichier XML devra être la même que celle du fichier userDB.xml.
 * @see <a href="../../userDB.xml">userDB.xml</a> 
 * 
 * @author Jose Mennesson (Mettre à jour)
 * @version 04/2016 (Mettre à jour)
 * 
 */

//TODO Classe à modifier

public class UserDB {

	/**
	 * 
	 * Le fichier contenant la base de données.
	 * 
	 */
	private String file;
	private Hashtable<String,User> utilisateur=new Hashtable<>();
	private Hashtable<Integer,Group> groupe=new Hashtable<>();
	public static Element root;
	public static org.jdom2.Document document;

	/**
	 * 
	 * Constructeur de UserDB. 
	 * 
	 * !!!!!!!!!!!! PENSEZ À AJOUTER UN ADMINISTRATEUR (su par exemple) QUI VOUS PERMETTRA DE CHARGER LA BASE DE DONNÉES AU DEMARRAGE DE L'APPLICATION !!!!!!
	 * 
	 * @param file
	 * 		Le nom du fichier qui contient la base de données.
	 */
	public UserDB(String file){
		//TODO Fonction à modifier
		super();
		this.setFile(file);
		loadDB();
	}

	/**
	 * Getter de file
	 * 
	 * @return 
	 * 		Le nom du fichier qui contient la base de données.
	 */

	public String getFile() {
		return file;
	}

	/**
	 * Setter de file
	 * 
	 * @param file
	 * 		Le nom du fichier qui contient la base de données.
	 */

	public void setFile(String file) {
		this.file = file;
	}
	/**
	 * Fonction permettant de récupérer le nom et le prénom de l'utilisateur à partir de son login
	 * @param userLogin
	 * 		Le login de l'utilisateur
	 * @return
	 * 		Une chaine de caractère contenant le prénom et le nom de l'utilisateur
	 */
	public String getUserName(String userLogin){
		if (!utilisateur.containsKey(userLogin)){//v�rifie si l'utilisateur existe
			return "cet utilisateur n'existe pas";
		}
		return utilisateur.get(userLogin).getUserName();
	}
	/**
	 * Fonction permettant de récupérer la classe de l'utilisateur à partir de son login et de son mot de passe. 
	 * Elle renvoie :
	 * 			- "" si l'utilisateur n'est pas reconnu (vérification du login et mdp).
	 * 			- "Student" si l'utilisateur est un étudiant 
	 *			- "Teacher" si l'utilisateur est un professeur
	 *			- "Administrator" si l'utilisateur est un administrateur 
	 * @param userLogin
	 * 		Le login de l'utilisateur
	 * @param userPwd
	 * 		Le mot de passe de l'utilisateur
	 * @return
	 * 		Une chaine de caractère contenant la classe de l'utilisateur
	 */
	public String getUserClass(String userLogin, String userPwd){
		if (!utilisateur.containsKey(userLogin)){//v�rifie si l'utilisateur existe
			return "";
		}
		else if (utilisateur.get(userLogin) instanceof Student){//teste la classe
			if(userPwd.compareTo(utilisateur.get(userLogin).password) == 0){
				return "Student";
			}
			else return "";
		}
		else if (utilisateur.get(userLogin) instanceof Admin){
			if(userPwd.compareTo(utilisateur.get(userLogin).password) == 0){
				return "Administrator";
			}
			else return "";
		}
		else if (utilisateur.get(userLogin) instanceof Teacher){
			if (userPwd.compareTo(utilisateur.get(userLogin).password) == 0){
				return "Teacher";
			}
			else return "";
		}
		return "";
	}


	/**
	 * Fonction permettant de récupérer l'identifiant de groupe de l'étudiant à partir de son login. Elle renvoie l'identifiant du groupe de l'étudiant s'il existe et -1 sinon.
	 * @param studentLogin
	 * 		Le login de l'étudiant
	 * @return
	 * 		L'identifiant de groupe de l'étudiant 
	 */
	public int getStudentGroup(String studentLogin){
		if (!utilisateur.containsKey(studentLogin)){
			return -1;
		}
		Student reserve=(Student) utilisateur.get(studentLogin);
		return reserve.getIdGroup();
	}

	/**
	 * Fonction permettant d'ajouter un administrateur. Elle renvoie true si l'administrateur a été créé et false sinon. 
	 * Cette fonction devra tester si l'administrateur existe déjà ou non, puis elle devra le sauvegarder dans la base de donnée.
	 * @param adminLogin
	 * 				Le login de l'administrateur qui va créer le nouvel administrateur.
	 * @param newAdminlogin
	 * 				Le login du nouvel administrateur.
	 * @param adminID
	 * 				L'identifiant du nouvel administrateur.
	 * @param firstname
	 * 				Le prénom du nouvel administrateur.
	 * @param surname
	 * 				Le nom du nouvel administrateur.
	 * @param pwd
	 * 				Le mot de passe du nouvel administrateur.
	 * @return
	 * 		Un boolean indiquant si l'administrateur a bien été créé
	 */

	public boolean addAdmin(String adminLogin, String newAdminlogin,int adminID, String firstname, String surname, String pwd){
		if(utilisateur.containsKey(adminLogin)){//v�rifie que l'admin existe
			if (utilisateur.containsKey(newAdminlogin)){//�vite de cr�er des doublons
				System.out.println("cet utilisateur existe d�j�");
				return false;
			}
			if(utilisateur.get(adminLogin) instanceof Admin){
			utilisateur.put(newAdminlogin, new Admin(newAdminlogin,firstname,surname,pwd,adminID));
			return true;
		}}
		return false;

	}
	/**
	 * Fonction permettant d'ajouter un professeur. Elle renvoie true si le professeur a été créé et false sinon. 
	 * Cette fonction devra tester si le professeur existe déjà ou non, puis elle devra le sauvegarder dans la base de donnée.
	 * @param adminLogin
	 * 				Le login de l'administrateur qui va créer le nouveau professeur.
	 * @param newteacherLogin
	 * 				Le login du nouveau professeur.
	 * @param teacherID
	 * 				L'identifiant du nouveau professeur.
	 * @param firstname
	 * 				Le prénom du nouveau professeur.
	 * @param surname
	 * 				Le nom du nouveau professeur.
	 * @param pwd
	 * 				Le mot de passe du nouveau professeur.
	 * @return
	 * 		Un boolean indiquant si le nouveau professeur a bien été créé
	 */
	public boolean addTeacher(String adminLogin, String newteacherLogin,int teacherID, String firstname, String surname, String pwd){
		if(utilisateur.containsKey(adminLogin)){
			if (utilisateur.containsKey(newteacherLogin)){//evite de cr�er des doublons
				System.out.println("cet utilisateur existe d�j�");
				return false;
			}
			if(utilisateur.get(adminLogin) instanceof Admin){//cr�er un utilisateur si la commande vient d'un administrateur
			utilisateur.put(newteacherLogin, new Teacher(newteacherLogin,firstname,surname,pwd,teacherID));
			return true;}
		}
		return false;
	}
	/**
	 * Fonction permettant d'ajouter un étudiant. Elle renvoie true si l'étudiant a été créé et false sinon. 
	 * Cette fonction devra tester si l'étudiant existe déjà ou non, puis elle devra le sauvegarder dans la base de donnée.
	 * @param adminLogin
	 * 				Le login de l'administrateur qui va créer le nouvel étudiant.
	 * @param newStudentLogin
	 * 				Le login du nouvel étudiant.
	 * @param studentID
	 * 				L'identifiant du nouvel étudiant.
	 * @param firstname
	 * 				Le prénom du nouvel étudiant.
	 * @param surname
	 * 				Le nom du nouvel étudiant.
	 * @param pwd
	 * 				Le mot de passe du nouvel étudiant.
	 * @return
	 * 		Un boolean indiquant si le nouvel étudiant a bien été créé
	 */
	public boolean addStudent(String adminLogin, String newStudentLogin,int studentID, String firstname, String surname, String pwd){
		if(utilisateur.containsKey(adminLogin)){//v�rifie l'existence de l'administrateur
			if (utilisateur.containsKey(newStudentLogin)){//�vite les doublons
				System.out.println("cet utilisateur existe d�j�");
				return false;
			}
			if(utilisateur.get(adminLogin) instanceof Admin){
			utilisateur.put(newStudentLogin, new Student(newStudentLogin,firstname,surname,pwd,studentID, -1));
			return true;}
			}
		return false;
	}

	/**
	 * Fonction permettant de supprimer un utilisateur. Elle renvoie true si l'utilisateur a été supprimé et false sinon. 
	 * Cette fonction devra tester si l'utilisateur existe ou non, puis elle devra le retirer de la base de donnée. 
	 * !!!!!! Si c'est un étudiant, il faudra penser à le retirer du groupe auquel il appartient. !!!
	 * @param adminLogin
	 * 				Le login de l'administrateur qui va supprimer l'utilisateur.
	 * @param userLogin
	 * 				Le login d'utilisateur à supprimer.
	 * @return
	 * 		Un boolean indiquant si l'utilisateur a bien été supprimé.
	 */
	public boolean removeUser(String adminLogin, String userLogin){
		if(utilisateur.get(adminLogin) instanceof Admin&&utilisateur.containsKey(userLogin)){//v�rification de l'existence de l'administrateur et de l'utilisateur
			utilisateur.remove(userLogin);
			return true;
		}
		return false;
	}
	/**
	 * Fonction permettant d'ajouter un groupe. Elle renvoie true si le groupe a été ajouté et false sinon. 
	 * Cette fonction devra tester si le groupe existe déjà ou non, puis elle devra le créer et le sauvegarder dans la base de donnée. 
	 * @param adminLogin
	 * 				Le login de l'administrateur qui va créer le groupe.
	 * @param groupId
	 * 				L'identifiant du groupe à créer.
	 * @return
	 * 		Un boolean indiquant si le groupe a été créé.
	 */
	public boolean addGroup(String adminLogin, int groupId){
		if(utilisateur.get(adminLogin) instanceof Admin&& !groupe.containsKey(groupId)){//v�rification de l'existence de l'administrateur et du groupe
			groupe.put(groupId, new Group(groupId));
			return true;
		}
		return false;
	}	

	/**
	 * Fonction permettant de supprimer un groupe. Elle renvoie true si le groupe a été supprimé et false sinon. 
	 * Cette fonction devra tester si le groupe existe ou non, puis elle devra le retirer de la base de donnée. 
	 * !!!!!! Pensez à retirer tous les étudiants de ce groupe !!!
	 * @param adminLogin
	 * 				Le login de l'administrateur qui va supprimer le groupe.
	 * @param groupId
	 * 				Identifiant du groupe à supprimer.
	 * @return
	 * 		Un boolean indiquant si le groupe a bien été supprimé.
	 */
	public boolean removeGroup(String adminLogin, int groupId){
		if(utilisateur.get(adminLogin) instanceof Admin&&groupe.containsKey(groupId)){//v�rification de l'existence de l'administrateur et du groupe
			groupe.remove(groupId);
			return true;
		}
		return false;
	}
	/**
	 * Fonction permettant d'associer un étudiant à un groupe. Elle renvoie true si l'association a été réalisée et false sinon. 
	 * Cette fonction devra tester si l'étudiant et le groupe existent ou non, puis elle devra sauvegarder la base de donnée. 
	 * @param adminLogin
	 * 				Le login de l'administrateur qui va associer un étudiant à un groupe.
	 * 
	 * @param studentLogin
	 * 				Login de l'étudiant
	 * @param groupId
	 * 				Identifiant du groupe.
	 * @return
	 * 		Un boolean indiquant si l'association a bien été réalisée.
	 */
	public boolean associateStudToGroup(String adminLogin, String studentLogin, int groupId){
		if(groupe.containsKey(groupId)&&utilisateur.containsKey(adminLogin)&&utilisateur.containsKey(studentLogin)){//v�rification de l'existence de l'administrateur, de l'�tudiant et du groupe
		if(utilisateur.get(adminLogin) instanceof Admin){
			Student buvardStudent=(Student) utilisateur.get(studentLogin);
			buvardStudent.addgroup(groupId);
			Group buvardGroup= groupe.get(groupId);
			buvardGroup.addStudent(studentLogin);
			return true;
		}}
		return false;
	}
	/**
	 * Fonction permettant de récupérer toutes les informations des utilisateurs sous la forme d'un 
	 * tableau de chaînes de caractères où chaque ligne contient toutes les informations d'un utilisateur.
	 * 
	 * @return
	 * 		Un tableau de String contenant toutes les infos de tous les utilisateurs.
	 */
	public String[] usersToString(){
		Integer i=0;
		String[] infoUser = new String[utilisateur.size()+1];
		Enumeration<User> elements = utilisateur.elements();//charge la base de donn�e dans une �num�ration
		while (elements.hasMoreElements()) {//parcours l'�num�ration
			User reserve = elements.nextElement();
			try {
				infoUser[i] = reserve.getInfo();//enregistre dans un tableau les informations de chaque utilisateur
				i++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return infoUser;
	}
	/**
	 * Fonction permettant de récupérer les logins des utilisateurs sous la forme d'un 
	 * tableau de chaînes de caractères où chaque ligne contient le login d'un utilisateur.
	 * 
	 * @return
	 * 		Un tableau de String contenant le login de tous les utilisateurs.
	 */
	public String[] usersLoginToString(){
		String[] infoLogin = new String[utilisateur.size()+1];
		Enumeration<User> elements = utilisateur.elements();//charge la base de donn�e dans une �num�ration
		int i=0;
		while (elements.hasMoreElements()){//parcours l'�num�ration
			User reserve = elements.nextElement();
			try {
				infoLogin[i] = reserve.getUserLogin();//enregistre dans un tableau les informations de chaque utilisateur
				i++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return infoLogin;
	}
	/**
	 * Fonction permettant de récupérer les logins des étudiants sous la forme d'un 
	 * tableau de chaînes de caractères où chaque ligne contient le login d'un étudiant.
	 * 
	 * @return
	 * 		Un tableau de String contenant le login de tous les étudiants.
	 */
	public String[] studentsLoginToString(){
		int size =utilisateur.size();
		String[] infoLoginStudent=new String[size];
		Enumeration<String> ID=utilisateur.keys();//charge la base de donn�e dans une �num�ration
		String k="as";
		for(Integer i=0;ID.hasMoreElements();k=ID.nextElement()){//parcours l'�num�ration
			if(utilisateur.get(k) instanceof Student){
				infoLoginStudent[i]=utilisateur.get(k).getUserLogin();//enregistre dans un tableau les informations de chaque �tudiant
				i++;
			}
		}
		return infoLoginStudent;
	}

	/**
	 * Fonction permettant de récupérer les identifiants des groupes sous la forme d'un 
	 * tableau de chaînes de caractères où chaque ligne contient l'identifiant d'un groupe.
	 * 
	 * @return
	 * 		Un tableau de String contenant l'identifiant de tous les groupes.
	 */
	public String[] groupsIdToString(){
		String[] groupId = new String[groupe.size()+1];
		Enumeration<Group> elements = groupe.elements();//charge les groupes dans une �num�ration
		int i=0;
		while (elements.hasMoreElements()){//parcours l'�num�ration
			Group reserve = elements.nextElement();
			try {
				Integer ID=reserve.getID();
				groupId[i] = ID.toString();//enregistre dans un tableau les informations de chaque groupe
				i++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return groupId;
	}

	/**
	 * Fonction permettant de récupérer toutes les informations des groupes sous la forme d'un 
	 * tableau de chaînes de caractères où chaque ligne contient les informations d'un groupe.
	 * 
	 * @return
	 * 		Un tableau de String contenant toutes les informations de tous les groupes.
	 */

	public String[] groupsToString(){
		String[] infoGroup = new String[groupe.size()+1];
		Enumeration<Group> elements = groupe.elements();//charge les groupes dans une �num�ration
		int i=0;
		while (elements.hasMoreElements()){
			Group reserve = elements.nextElement();
			Integer nmbStudent=0;
			String login="";
			Enumeration<String> keys=utilisateur.keys();//charge les utilisateurs dans une �num�ration
			String k="as";
			
			while(keys.hasMoreElements()){//parcours l'�num�ration des utilisateurs
				if(utilisateur.get(k) instanceof Student){
					Student studentm=(Student) utilisateur.get(k);
					if (studentm.getIdGroup()==reserve.getID()){
						login+=" "+utilisateur.get(k).getUserLogin();//enregistre le login de l'�l�ve si il est dans le groupe
						nmbStudent++;//compte le nombre d'�l�ve dans le groupe
					}
			}
				k=keys.nextElement();
			}
			try {
				Integer ID=reserve.getID();
				infoGroup[i] =ID.toString()+" \n"+nmbStudent.toString()+" \n"+login;
				i++;
			}catch (Exception e) {
			e.printStackTrace();
		}
			}
		return infoGroup;
	}


	/**
	 * Fonction chargeant la base de donnée contenue dans un fichier XML.
	 * @return
	 * 		Un boolean indiquant si le chargement a bien été réalisée.
	 */
	public boolean loadDB(){

		SAXBuilder sx= new SAXBuilder();
		try{
			document=sx.build(new File("userDB.xml"));			//on convertit le fichier XML en une arborescense jdom.
		}catch(Exception e){}

		if(document==null){								// si le document est vide, on va cr�er une arborescence jdom pour la convertir par la suite.
			root = new Element ("UsersDB");
			Element param = new Element ("Groups");
			root.addContent(param);
			param = new Element ("Students");
			root.addContent(param);								// on cr�e les diff�rentes cat�gories
			param = new Element ("Teachers");
			root.addContent(param);
			param = new Element ("Administrators");	
			root.addContent(param);
			//-----------------------------------------------------------
			Element user = new Element ("Administrator");
			param = new Element ("login");						//puis on ajoute un administrateur. Ainsi on pourra acc�der � la base de donn�es
			param.setText("su");
			user.addContent(param);
			param = new Element ("firstname");
			param.setText("Salom�");
			user.addContent(param);
			param = new Element ("surname");
			param.setText("Ururan");
			user.addContent(param);
			param = new Element ("pwd");
			param.setText("superUser");
			user.addContent(param);
			param = new Element ("adminId");
			param.setText("0");
			user.addContent(param);
			root.getChild("Administrators").addContent(user);
		}
		//-----------------------------------------------------------------------------------------------
		root=document.getRootElement();
		Element groupsInter = root.getChild("Groups");
		List<Element> groups= groupsInter.getChildren("Group");	
		Iterator<Element> oneGroup=groups.iterator();

		Element studentsInter = root.getChild("Students");
		List<Element> students= studentsInter.getChildren("Student");		//Dans cette partie on r�cup�re dans des listes diff�rentes les groupes et les diff�rents utilisateurs.
		Iterator<Element> onestudent=students.iterator();					//Ainsi on a d�j� tri� les classes

		Element teachersInter = root.getChild("Teachers");
		List<Element> teachers= teachersInter.getChildren("Teacher");
		Iterator<Element> oneteacher=teachers.iterator();

		Element adminsInter = root.getChild("Administrators");
		List<Element> administrators= adminsInter.getChildren("Administrator");
		Iterator<Element> oneadministrator=administrators.iterator();
		//---------------------------------------------------------------------------------------
		while(oneGroup.hasNext()){
			Element aGroup=(Element)oneGroup.next();
			String IdGroup=aGroup.getChild("groupId").getText();
			int groupid=Integer.parseInt(IdGroup);							//ici, on va parcourir chacune de ces listes une � une et pour chaque �l�ment on va cr�er un objet. 
			Group reserve=new Group(groupid);//modifier						// Ainsi tous les �l�ments de l'arborescence ont �t� transform� en �lement d'une table de hachage.
			groupe.put(groupid, reserve);
		}
		while(onestudent.hasNext()){
			Element astudent=(Element)onestudent.next();
			String login=astudent.getChild("login").getText();
			String firstname=astudent.getChild("firstname").getText();
			String surname=astudent.getChild("surname").getText();
			String password=astudent.getChild("pwd").getText();
			String ID=astudent.getChild("studentId").getText();
			String IdGroup=astudent.getChild("groupId").getText();
			int studentid=Integer.parseInt(ID);
			int groupid=Integer.parseInt(IdGroup);
			Student reserve=new Student(login,firstname,surname,password,studentid, groupid);
			utilisateur.put(login, reserve);
		}
		while(oneteacher.hasNext()){
			Element ateacher=(Element)oneteacher.next();				
			String login=ateacher.getChild("login").getText();
			String firstname=ateacher.getChild("firstname").getText();
			String surname=ateacher.getChild("surname").getText();
			String password=ateacher.getChild("pwd").getText();
			String ID=ateacher.getChild("teacherId").getText();
			int teacherid=Integer.parseInt(ID);
			Teacher reserve=new Teacher(login,firstname,surname,password,teacherid);
			utilisateur.put(login, reserve);
		}
		while(oneadministrator.hasNext()){
			Element aadministrator=(Element)oneadministrator.next();
			String login=aadministrator.getChild("login").getText();
			String firstname=aadministrator.getChild("firstname").getText();
			String surname=aadministrator.getChild("surname").getText();
			String password=aadministrator.getChild("pwd").getText();
			String ID=aadministrator.getChild("adminId").getText();
			int administratorid=Integer.parseInt(ID);
			Admin reserve=new Admin(login,firstname,surname,password,administratorid);
			utilisateur.put(login, reserve);
		}
		return true;
	}



	/**
	 * Fonction sauvegardant la base de donnée dans un fichier XML.
	 * @return
	 * 		Un boolean indiquant si la sauvegarde a bien été réalisée.
	 */

	public boolean saveDB(){

		List listUsers = root.getChildren();					//r�initialise l'arborescense jdom en gardant les racines
		Iterator i = listUsers.iterator();
		while(i.hasNext())// mise � 0 du fichier
		{
			Element courant = (Element)i.next();
			courant.removeContent();
		}														
		//----------------------------------------------------------------
		//remplis l'arborescence avec tous les �lements de la BD
		Enumeration<User> elements = utilisateur.elements();
		Enumeration<Group> elementsGroups = groupe.elements();
		while (elements.hasMoreElements()) {			
			while (elementsGroups.hasMoreElements())//enregistrement des groupes
			{
				Group groupe = elementsGroups.nextElement();
				try {
					Element group;
					Element param;
					if (groupe instanceof Group)
					{
						group = new Element ("Group");
						param = new Element ("groupId");
						param.setText(Integer.toString(groupe.idGroup));
						group.addContent(param);
						root.getChild("Groups").addContent(group);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			User utilisateur = elements.nextElement();
			try {
				Element user;
				Element param;
				if (utilisateur instanceof Admin)//v�rifie la class pour adapter l'enregistrement des donn�es
				{
					Admin admin = (Admin) utilisateur;
					user = new Element ("Administrator");
					param = new Element ("login");
					param.setText(utilisateur.login);
					user.addContent(param);
					param = new Element ("firstname");
					param.setText(utilisateur.firstname);
					user.addContent(param);
					param = new Element ("surname");
					param.setText(utilisateur.surname);
					user.addContent(param);
					param = new Element ("pwd");
					param.setText(utilisateur.password);
					user.addContent(param);
					param = new Element ("adminId");
					param.setText(Integer.toString(admin.idAdmin));
					user.addContent(param);
					root.getChild("Administrators").addContent(user);
				}
				else if (utilisateur instanceof Teacher)
				{
					Teacher teacher = (Teacher) utilisateur;
					user = new Element ("Teacher");
					param = new Element ("login");
					param.setText(utilisateur.login);
					user.addContent(param);
					param = new Element ("firstname");
					param.setText(utilisateur.firstname);
					user.addContent(param);
					param = new Element ("surname");
					param.setText(utilisateur.surname);
					user.addContent(param);
					param = new Element ("pwd");
					param.setText(utilisateur.password);
					user.addContent(param);
					param = new Element ("teacherId");
					param.setText(Integer.toString(teacher.idTeacher));
					user.addContent(param);
					root.getChild("Teachers").addContent(user);
				}
				else if (utilisateur instanceof Student)
				{
					Student student = (Student) utilisateur;
					user = new Element ("Student");
					param = new Element ("login");
					param.setText(utilisateur.login);
					user.addContent(param);
					param = new Element ("firstname");
					param.setText(utilisateur.firstname);
					user.addContent(param);
					param = new Element ("surname");
					param.setText(utilisateur.surname);
					user.addContent(param);
					param = new Element ("pwd");
					param.setText(utilisateur.password);
					user.addContent(param);
					param = new Element ("studentId");
					param.setText(Integer.toString(student.idStudent));
					user.addContent(param);
					param = new Element ("groupId");
					param.setText(Integer.toString(student.idGroup));
					user.addContent(param);
					root.getChild("Students").addContent(user);
				}


			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//-----------------------------------------------------------------
		try
		{														// sauvegarde l'arborescence dans le fichier XML
			XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
			sortie.output(document, new FileOutputStream(file));
		}
		catch (java.io.IOException e){}
		return true;
	}





}
