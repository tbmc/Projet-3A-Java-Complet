package timeTableModel;

import org.jdom2.Element;
import utils.XMLUtils;

import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Classe représentant un emploi du temps
 */
public class TimeTable implements XMLUtils.XMLSerializable {

    private int id;
    private Hashtable<Integer, Booking> bookings = new Hashtable<>();

    private static final String
            XML_NAME        = "TimeTable",
            XML_INNER_ID    = "GroupId";

    /**
     * Uniquement pour utiliser les fonctions non statiques de la classe
     */
    TimeTable() {
        this(-1);
    }

    /**
     * Crée une nouvelle instance
     * @param bookingId identifiant de l'emploi du temps
     */
    public TimeTable(int bookingId) {
        this(bookingId, null);
    }

    /**
     * Permet de recréer un emploi du temps en lui fournissant la liste des réservations.
     * Cette fonction est utilisée uniquement pour créer une instance à partir du XML
     * @param bookingId identifiant de l'emploi du temps
     * @param bookings Hashtable contenant toutes les réservations
     */
    TimeTable(int bookingId, Hashtable<Integer, Booking> bookings) {
        this.id = bookingId;
        if(bookings != null)
            this.bookings = bookings;

    }

    /**
     * Ajoute une réservation
     * @param bookingId Identifiant de la nouvelle réservation
     * @param userLogin Identifiant de l'utilisateur faisant la réservation
     * @param dateBegin Date de début de la réservation
     * @param dateEnd Date de fin de la réservation
     * @param room Instance de {@link Room} correspondant à la salle à réserver
     * @return vrai en cas de succès, faux sinon
     */
    public boolean addBooking(int bookingId, String userLogin, Date dateBegin, Date dateEnd, Room room) {
        if(bookings.containsKey(bookingId) || !isRoomFree(room.getId(), dateBegin, dateEnd))
            return false;
        bookings.put(bookingId, new Booking(bookingId, room, userLogin, dateBegin, dateEnd));
        return true;
    }

    /**
     * Permet de savoir si une salle est libre dans toute la période indiquée dans cet emploi du temps
     * @param roomId identifiant de la salle à tester
     * @param begin Date de début de la période
     * @param end Date de fin de la période
     * @return Vrai si la salle est libre durant la période dans cet emploi du temps
     */
    public boolean isRoomFree(int roomId, Date begin, Date end) {
        Enumeration<Integer> keys = bookings.keys();
        while(keys.hasMoreElements()) {
            Booking b = bookings.get(keys.nextElement());
            if(b.getRoom().getId() != roomId)
                continue;
            if(b.isBooked(begin, end))
                return false;
        }
        return true;
    }

    /**
     * Permet de supprimer une réservation
     * @param bookingId Identifiant de la réservation à supprimer
     * @return Vrai si la suppression a réussi et faux sinon
     */
    public boolean removeBooking(int bookingId) {
        return bookings.remove(bookingId) != null;
    }

    /**
     * Retourne l'identifiant de la salle correspondante à la réservation
     * @param bookId Identifiant de la réservation
     * @return Identifiant de la salle correspondante à la réservation
     */
    public int getRoom(int bookId) {
        return bookings.get(bookId).getRoom().getId();
    }

    /**
     * Récupère toutes les dates de toutes les réservations de l'emploi du temps
     * @param dateBegin Hashtable de sortie contenant toutes les dates de début de réservation
     * @param dateEnd Hashtable de sortie contenant toutes les dates de fin de réservation
     */
    public void getBookingsDate(Hashtable<Integer, Date> dateBegin, Hashtable<Integer, Date> dateEnd) {
        Enumeration<Integer> keys = bookings.keys();
        int v;
        Booking b;
        while(keys.hasMoreElements()) {
            v = keys.nextElement();
            b = bookings.get(v);
            dateBegin.put(v, b.getDateBegin());
            dateEnd.put(v, b.getDateEnd());
        }
    }

    /**
     * Retourne l'identifiant le plus grand utilisé pour les réservations
     * @return identifiant le plus grand
     */
    public int getBookingsMaxId() {
        Enumeration<Integer> keys = bookings.keys();
        int max = 0, v;
        while(keys.hasMoreElements()) {
            v = keys.nextElement();
            if(v > max)
                max = v;
        }
        return max;
    }

    /**
     * Récupère l'identifiant de l'utilisateur ayant effectué une réservation
     * @param bookId identifiant de la réservation
     * @return Identifiant de l'utilisateur ayant fait la réservation ou
     *         null si la réservation n'existe pas dans cet emploi du temps
     */
    public String getUserLogin(int bookId) {
        Booking book = bookings.get(bookId);
        if(book == null)
            return null;
        return book.getUserLogin();
    }

    /**
     * Récupère l'identifiant de toutes les réservations
     * @return Tableau de chaines de caractères contenant les identifiants de toutes les réservations
     */
    public String[] idToString() {
        return TimeTableDB.idKeysToStringArray(bookings);
    }

    @Override
    public Element getXMLElement() {
        Element e = new Element(XML_NAME);

        Element gid = new Element(XML_INNER_ID);
        gid.setText("" + id);
        e.addContent(gid);

        e.addContent(XMLUtils.getXMLFromHashTable("Books", bookings));

        return e;
    }

    @Override
    public String getXML_NAME() {
        return XML_NAME;
    }

    @Override
    public String getXML_INNER_ID() {
        return XML_INNER_ID;
    }

    @Override
    public TimeTable createFromXMLElement(Element e, Object params) {
        Hashtable<Integer, Room> rooms = (Hashtable<Integer, Room>) params;
        Integer id = Integer.parseInt(e.getChildText(XML_INNER_ID));
        Hashtable<Integer, Booking> bookings = new Hashtable<>();
        boolean b = XMLUtils.getFromElement(e.getChild("Books"), bookings, new Booking(), rooms);
        if(!b) return null;
        return new TimeTable(id, bookings);
    }

}
