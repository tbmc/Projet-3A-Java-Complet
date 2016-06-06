package timeTableModel;

import java.sql.Time;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 *
 * Cette classe gére la base de données d'emplois du temps. Elle doit permettre de sauvegarder et charger les emplois du temps ainsi que les salles à partir d'un fichier XML. 
 * La structure du fichier XML devra être la même que celle du fichier TimeTableDB.xml.
 * @see <a href="../../TimeTableDB.xml">TimeTableDB.xml</a> 
 *
 * @author Jose Mennesson (Mettre à jour)
 * @version 04/2016 (Mettre à jour)
 *
 */
public class TimeTableDB {
    /**
     *
     * Le fichier contenant la base de données.
     *
     */
    private String file = null;

    private Hashtable<Integer, Room> rooms = new Hashtable<>();
    private Hashtable<Integer, TimeTable> timeTables = new Hashtable<>();


    private TimeTableSaver timeTableSaver = new TimeTableSaver(file);
    private TimeTableSaver.SavedState hashLastCommit = null;

    /**
     *
     * Constructeur de TimeTableDB.
     *
     * @param file
     * 		Le nom du fichier contenant la base de données.
     */
    public TimeTableDB(String file){
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
        timeTableSaver.setFile(file);
    }

    /**
     * Fonction qui vérifie si le fichier a bien été sauvegardé dans la base de données
     *
     * @param file Chemin du fichier dans lequel lire et enregistrer la base de données
     * @return vrai si le fichier est enregistré et faux sinon.
     */
    public boolean saveDB(String file) {
        timeTableSaver.setFile(file);
        TimeTableSaver.SavedState ss = timeTableSaver.save(rooms, timeTables, hashLastCommit);
        if(ss != null)
        {
            hashLastCommit = ss;
            return true;
        }
        return false;
    }

    /**
     * Fonction sauvegardant la base de donnée dans un fichier XML.
     * @return
     * 		Un boolean indiquant si la sauvegarde a bien été réalisée.
     */
    public boolean saveDB() {
        return saveDB(file);
    }

    /**
     * Fonction chargeant la base de donnée contenue dans un fichier XML.
     * @return
     * 		Un boolean indiquant si le chargement a bien été réalisée.
     */
    public boolean loadDB() {
        Hashtable<Integer, Room> r = new Hashtable<>();
        Hashtable<Integer, TimeTable> tt = new Hashtable<>();
        TimeTableSaver.SavedState ss = timeTableSaver.load(r, tt);
        System.out.println(ss);
        if(ss != null) {
            this.hashLastCommit = ss;
            rooms = r;
            timeTables = tt;
            return true;
        }
        return false;
    }

    /**
     * * Fonction qui crée une salle et qui la sauvegarde dans la base de données.
     * @param roomId
     * 		Un identifiant de la salle
     * @param maxStudentsNumber
     * 		le nombre maximum d'élèves dans cette salle
     * @return
     * 		boolean faux si la salle existe déjà
     * 				vrai si elle n'existe pas et crée une nouvelle salle
     */
    public boolean addRoom(int roomId, int maxStudentsNumber) {
        if(rooms.containsKey(roomId))
            return false;
        rooms.put(roomId, new Room(roomId, maxStudentsNumber));
        return saveDB();
    }

    /**
     * Fonction qui supprime une salle et qui sauvegarde la base de données.
     * @param roomId
     * 		Un identifiant de la salle
     * @return
     * 		Un boolean si la salle a bien été supprimée
     */
    public boolean removeRoom(int roomId) {
        boolean b = rooms.remove(roomId) != null;
        return b ? saveDB() : false;
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
    public int getRoomId(int timeTableId, int bookId) {
        return timeTables.get(timeTableId).getRoom(bookId);
    }

    /**
     * Fonction permettant de récupérer tous les identifiants des salles sous la forme d'un
     * tableau de chaînes de caractères où chaque ligne contient l'identifiant d'une salle.
     *
     * @return
     * 		Un tableau de String contenant toutes les informations de tous les groupes.
     */
    public String[] roomsIdToString() {
        return TimeTableDB.idKeysToStringArray(rooms);
    }

    /**
     * Fonction permettant de récupérer toutes les informations des salles sous la forme d'un
     * tableau de chaînes de caractères où chaque ligne contient les informations d'une salle.
     *
     * @return
     * 		Un tableau de String contenant toutes les informations de toutes les salles.
     */
    public String[] roomsToString() {
        Enumeration<Integer> keys = rooms.keys();
        String[] out = new String[rooms.size()];
        for(Integer i = 0, k = 0; keys.hasMoreElements(); i++) {
            k = keys.nextElement();
            System.out.println(k);
            System.out.println(rooms.get(k));
            out[i] = rooms.get(k).getInfo();
        }
        return out;
    }

    /**
     * Fonction qui crée un emploi du temps et qui le sauvegarde dans la base de données
     * @param timeTableId
     * 		L'identifiant d'emploi du temps
     * @return
     * 		Un boolean indiquant si l'emploi du temps a bien été créé
     */
    public boolean addTimeTable(int timeTableId) {
        if(timeTables.containsKey(timeTableId))
            return false;
        timeTables.put(timeTableId, new TimeTable(timeTableId));
        return saveDB();
    }

    /**
     * Fonction qui supprime un emploi du temps et qui sauvegarde la base de données
     * @param timeTableId
     * 		L'identifiant d'emploi du temps
     * @return
     * 		Un boolean indiquant si l'emploi du temps a bien été créé
     */
    public boolean removeTimeTable(int timeTableId) {
        boolean b = timeTables.remove(timeTableId) != null;
        return b ? saveDB() : false;
    }

    /**
     * Fonction permettant de récupérer tous les identifiants des emplois du temps sous la forme d'un
     * tableau de chaînes de caractères où chaque ligne contient l'identifiant d'un emploi du temps.
     *
     * @return
     * 		Un tableau de String contenant toutes les identifiants de tous les emplois du temps.
     */
    public String[] timeTablesIDToString() {
        return TimeTableDB.idKeysToStringArray(timeTables);
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
    public boolean addBooking(int timeTableId, int bookingId, String login, Date dateBegin, Date dateEnd, int roomId) {
        Room room = rooms.get(roomId);
        TimeTable tt = timeTables.get(timeTableId);
        if(tt == null || room == null)
            return false;
        if(!isRoomFree(roomId, dateBegin, dateEnd))
            return false;
        boolean b = tt.addBooking(bookingId, login, dateBegin, dateEnd, room);
        return b ? saveDB() : false;
    }

    /**
     * Fonction qui vérifie si la salle est libre ou réservée
     *
     * @param roomId
     * 		L'identifiant de la salle
     * @param begin
     * 		La date de début de réservation
     * @param end
     * 		La date de fin de réservation
     * @return
     * 		Un boolean indiquant si la salle est libre
     */
    public boolean isRoomFree(int roomId, Date begin, Date end) {
        Enumeration<Integer> keys = timeTables.keys();
        while(keys.hasMoreElements()) {
            TimeTable tt = timeTables.get(keys.nextElement());
            if(!tt.isRoomFree(roomId, begin, end))
                return false;
        }
        return true;
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
    public boolean removeBook(int timeTableId, int bookId) {
        boolean b = timeTables.remove(timeTableId) != null;
        return b ? saveDB() : false;
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
    public void getBookingsDate(int timeTableId, Hashtable<Integer, Date> dateBegin, Hashtable<Integer, Date> dateEnd) {
        TimeTable tt = timeTables.get(timeTableId);
        if(tt == null)
            return;
        tt.getBookingsDate(dateBegin, dateEnd);
    }

    /**
     * Fonction qui récupère le plus grand identifiant de réservation dans l'emploi du temps timeTableId.
     *
     * @param timeTableId
     * 		L'identifiant d'emploi du temps
     * @return
     * 		Le plus grand identifiant de réservation
     */
    public int getBookingsMaxId(int timeTableId) {
        TimeTable tt = timeTables.get(timeTableId);
        if(tt == null)
            return 0; // On ne retourne pas vraiment une erreur, renvoyer 0 permet d'ajouter directement à cet index
        return tt.getBookingsMaxId();
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
    public String getUserLogin(int timeTableId, int bookId) {
        TimeTable tt = timeTables.get(timeTableId);
        if(tt == null)
            return null;
        return tt.getUserLogin(bookId);
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
    public String[] booksIdToString(int timeTableId) {
        TimeTable tt = timeTables.get(timeTableId);
        if(tt == null)
            return null;
        return tt.idToString();
    }

    /**Fonction permettant de récupérer tous les identifiants sous la forme d'un tableau de chaînes de caractères.
     * @param in
     * Hashtable qui contient tous les numéros d'identifications
     * @return
     * Un tableau de String contenant toutes les IDs.
     */
    public static String[] idKeysToStringArray(Hashtable<Integer, ?> in) {
        int size = in.size();
        String[] str = new String[size];
        Enumeration<Integer> keys = in.keys();
        for(Integer i = 0, k = 0; keys.hasMoreElements(); i++) {
            k = keys.nextElement();
            str[i] = k.toString();
        }
        return str;
    }


}

