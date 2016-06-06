package timeTableController;

import java.util.Date;
import java.util.Hashtable;

import timeTableModel.TimeTableDB;
/**
 * Cette classe est le contrôleur d'emplois du temps que vous devez implémenter. 
 * Elle contient un attribut correspondant à la base de données d'emplois du temps que vous allez créer.
 * Elle contient toutes les fonctions de l'interface ITimeTableController que vous devez implémenter.
 *
 * @author Jose Mennesson (Mettre à jour)
 * @version 04/2016 (Mettre à jour)
 *
 */
public class TimeTableController implements ITimeTableController{

	/**
	 * Contient une instance de base de données d'emplois du temps
	 *
	 */
	TimeTableDB tTDB;
	/**
	 * Constructeur de controleur d'emplois du temps créant la base de données d'emplois du temps
	 *
	 * @param tTfile
	 * 		Fichier XML contenant la base de données d'emplois du temps
	 */
	public TimeTableController(String tTfile) {
		this.tTDB = new TimeTableDB(tTfile);
	}

	/**
	 * Fonction permettant de récupérer le login du professeur qui a réalisé la réservation dont l'identifiant est bookId dans l'emploi du temps dont l'identifiant est timeTableId.
	 * @param timeTableId
	 * 		L'identifiant de l'emploi du temps
	 * @param bookId
	 * 		L'identifiant de réservation
	 * @return
	 * 		Le login du professeur qui a fait la réservation.
	 */
	@Override
	public String getTeacherLogin(int timeTableId, int bookId) {
		return tTDB.getUserLogin(timeTableId, bookId);
	}

	/**
	 * Fonction permettant de récupérer tous les identifiants des salles sous la forme d'un
	 * tableau de chaînes de caractères où chaque ligne contient l'identifiant d'une salle.
	 *
	 * @return
	 * 		Un tableau de String contenant toutes les informations de tous les groupes.
	 */
	@Override
	public String[] roomsIdToString() {
		return tTDB.roomsIdToString();
	}

	/**
	 * Fonction permettant de récupérer toutes les informations des salles sous la forme d'un
	 * tableau de chaînes de caractères où chaque ligne contient les informations d'une salle.
	 *
	 * @return
	 * 		Un tableau de String contenant toutes les informations de toutes les salles.
	 */
	@Override
	public String[] roomsToString() {
		return tTDB.roomsToString();
	}

	/**
	 * Fonction permettant de récupérer tous les identifiants des emplois du temps sous la forme d'un
	 * tableau de chaînes de caractères où chaque ligne contient l'identifiant d'un emploi du temps.
	 *
	 * @return
	 * 		Un tableau de String contenant toutes les identifiants de tous les emplois du temps.
	 */
	@Override
	public String[] timeTablesIDToString() {
		return tTDB.timeTablesIDToString();
	}

	/**
	 * Fonction permettant de récupérer tous les identifiants des réservations de l'emploi du temps timeTableId sous la forme d'un
	 * tableau de chaînes de caractères où chaque ligne contient l'identifiant d'une réservation.
	 *
	 * @param timeTableId
	 * 			Un identifiant d'emploi du temps
	 * @return
	 * 		Un tableau de String contenant toutes les informations de tous les groupes.
	 */
	@Override
	public String[] booksIdToString(int timeTableId) {
		return tTDB.booksIdToString(timeTableId);
	}

	/**
	 * Fonction qui crée une salle et qui la sauvegarde dans la base de données.
	 * @param roomId
	 * 		L'identifiant de la salle
	 * @param capacity
	 * 		La capacité de la salle
	 * @return
	 * 		Un boolean indiquant si la salle a bien été créée
	 */
	@Override
	public boolean addRoom(int roomId, int capacity) {
		return tTDB.addRoom(roomId, capacity);
	}

	/**
	 * Fonction qui supprime une salle et qui sauvegarde la base de données.
	 * @param roomId
	 * 		L'identifiant de la salle
	 * @return
	 * 		Un boolean indiquant si la salle a bien été supprimée
	 */
	@Override
	public boolean removeRoom(int roomId) {
		return tTDB.removeRoom(roomId);
	}

	/**
	 * Fonction qui récupère l'identifiant de la salle réservée dans l'emploi du temps dont l'identifiant est timeTableId et dont l'identifiant de réservation est bookId
	 * @param timeTableId
	 * 		L'identifiant d'emploi du temps
	 * @param bookId
	 * 		L'identifiant de réservation
	 * @return
	 * 		L'identifiant de la salle réservée
	 */
	@Override
	public int getRoom(int timeTableId, int bookId) {
		return tTDB.getRoomId(timeTableId, bookId);
	}

	/**
	 * Fonction qui crée un emploi du temps et qui le sauvegarde dans la base de données
	 * @param timeTableId
	 * 		L'identifiant d'emploi du temps
	 * @return
	 * 		Un boolean indiquant si l'emploi du temps a bien été créé
	 */
	@Override
	public boolean addTimeTable(int timeTableId) {
		return tTDB.addTimeTable(timeTableId);
	}

	/**
	 * Fonction qui supprime un emploi du temps et qui sauvegarde la base de données
	 * @param timeTableId
	 * 		L'identifiant d'emploi du temps
	 * @return
	 * 		Un boolean indiquant si l'emploi du temps a bien été créé
	 */
	@Override
	public boolean removeTimeTable(int timeTableId) {
		return tTDB.removeTimeTable(timeTableId);
	}

	/**
	 * Fonction qui ajoute une réservation dans l'emploi du temps TimeTableId et qui la sauvegarde dans la base de données
	 *
	 * @param timeTableId
	 * 		L'identifiant d'emploi du temps
	 * @param bookingId
	 * 		L'identifiant de réservation
	 * @param login
	 * 		Le login du professeur faisant la réservation
	 * @param dateBegin
	 * 		La date de début de réservation
	 * @param dateEnd
	 * 		La date de fin de réservation
	 * @param roomId
	 * 		L'identifiant de la salle réservée
	 * @return
	 * 		Un boolean indiquant si la réservation a bien été faite
	 */
	@Override
	public boolean addBooking(int timeTableId, int bookingId, String login, Date dateBegin, Date dateEnd, int roomId) {
		return tTDB.addBooking(timeTableId, bookingId, login, dateBegin, dateEnd, roomId);
	}

	/**
	 * Fonction qui retourne les dates de début et de fin des réservations de l'emploi du temps dont l'identifiant est timeTableId.
	 *
	 * @param timeTableId
	 * 		L'identifiant d'emploi du temps
	 * @param dateBegin
	 * 		Hashtable qui contiendra les dates de début des réservations. La clé de la Hashtable correspond à l'identifiant de réservation.
	 * @param dateEnd
	 * 		Hashtable qui contiendra les dates de fin des réservations. La clé de la Hashtable correspond à l'identifiant de réservation.
	 */
	@Override
	public void getBookingsDate(int timeTableId, Hashtable<Integer, Date> dateBegin, Hashtable<Integer, Date> dateEnd) {
		tTDB.getBookingsDate(timeTableId, dateBegin, dateEnd);
	}

	/**
	 * Fonction qui supprime la réservation dont l'identifiant est bookId dans l'emploi du temps timeTableId.
	 *
	 * @param timeTableId
	 * 		L'identifiant d'emploi du temps
	 * @param bookId
	 * 		L'identifiant de réservation à supprimer
	 * @return
	 * 		Un boolean indiquant si la réservation a bien été supprimée
	 */
	@Override
	public boolean removeBook(int timeTableId, int bookId) {
		return tTDB.removeBook(timeTableId, bookId);
	}

	/**
	 * Fonction qui récupère le plus grand identifiant de réservation dans l'emploi du temps timeTableId.
	 *
	 * @param timeTableId
	 * 		L'identifiant d'emploi du temps
	 * @return
	 * 		Le plus grand identifiant de réservation
	 */
	@Override
	public int getBookingsMaxId(int timeTableId) {
		return tTDB.getBookingsMaxId(timeTableId);
	}

	/**
	 * Fonction sauvegardant la base de donnée dans un fichier XML.
	 * @return
	 * 		Un boolean indiquant si la sauvegarde a bien été réalisée.
	 */
	@Override
	public boolean saveDB() {
		return tTDB.saveDB();
	}

	/**
	 * Fonction qui vérifie si le fichier a bien été sauvegardé dans la base de données
	 *
	 * @param file Chemin du fichier dans lequel enregistrer la base de données
	 * @return vrai si le fichier est enregistré et faux sinon.
	 */
	public boolean saveDB(String file) {
		return tTDB.saveDB(file);
	}

	/**
	 * Fonction chargeant la base de donnée contenue dans un fichier XML.
	 * @return
	 * 		Un boolean indiquant si le chargement a bien été réalisée.
	 */
	@Override
	public boolean loadDB() {
		return tTDB.loadDB();
	}



}

