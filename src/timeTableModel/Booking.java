package timeTableModel;

import org.jdom2.Element;
import utils.XMLUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

/**
 * Classe représentant une réservation
 */
public class Booking implements XMLUtils.XMLSerializable {

    private static final String
            XML_NAME        = "Book",
            XML_INNER_ID    = "BookingId";

    private static final String
        LOGIN       = "Login",
        DATE_BEGIN  = "DateBegin",
        DATE_END    = "DateEnd",
        ROOM_ID     = "RoomId",
        DATE_FORMAT = "dd/MM/yyyy HH:mm:ss"
    ;

    private int id;
    private Room room;
    private String userLogin;
    private Date dateBegin, dateEnd;

    /**
     * Crée une nouvelle instance de Réservation
     * @param bookingId Identifiant de la réservation
     * @param room Instance de la classe {@link Room} correspondant à la salle réservée
     * @param userLogin Identifiant de l'utilisateur réservant la salle
     * @param dateBegin Date de début de la réservation
     * @param dateEnd Date de fin de la réservation
     */
    public Booking(int bookingId, Room room, String userLogin,
                   Date dateBegin, Date dateEnd) {
        this.id = bookingId;
        this.room = room;
        this.userLogin = userLogin;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
    }

    /**
     * Crée une nouvelle instance de la classe.
     * Ce constructeur ne doit être utiliser que pour utiliser les fonctions
     * non statiques de la classe.
     */
    Booking() {
        this(-1, null, null, null, null);
    }

    /**
     * Récupère l'identifiant de la réservation
     * @return identifiant de la réservation
     */
    public int getId() {
        return id;
    }

    /**
     * Récupère l'objet Room correspondant à la salle réservée
     * @return {@link Room} correspondant à la salle réservée
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Récupère l'identifiant de l'utilisateur ayant fait la réservation
     * @return identifiant de l'utilisateur ayant réservé
     */
    public String getUserLogin() {
        return userLogin;
    }

    /**
     * Récupère la date de début de la réservation
     * @return Date de début de la réservation
     */
    public Date getDateBegin() {
        return dateBegin;
    }

    /**
     * Récupère la date de fin de la réservation
     * @return Date de fin de la réservation
     */
    public Date getDateEnd() {
        return dateEnd;
    }

    /**
     * Permet de savoir si la salle est réservée entre deux dates
     * @param begin Date de début de la période
     * @param end Date de fin de la période
     * @return vrai si la salle est réservée entre les deux dates et faux sinon
     */
    public boolean isBooked(Date begin, Date end) {
        return dateBegin.before(end) && dateEnd.after(end);
    }

    @Override
    public Element getXMLElement() {
        Element e = new Element(XML_NAME);
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);

        Element bid = new Element(XML_INNER_ID);
        bid.setText("" + id);
        e.addContent(bid);

        Element login = new Element(LOGIN);
        login.setText(userLogin);
        e.addContent(login);

        Element dBegin = new Element(DATE_BEGIN);
        dBegin.setText(df.format(dateBegin));
        e.addContent(dBegin);

        Element dEnd = new Element(DATE_END);
        dEnd.setText(df.format(dateEnd));
        e.addContent(dEnd);

        Element rid = new Element(ROOM_ID);
        rid.setText("" + room.getId());
        e.addContent(rid);

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

    public Booking createFromXMLElement(Element e, Object params) {
        if(params == null)
            return null;
        Hashtable<Integer, Room> rooms = (Hashtable<Integer, Room>) params;
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);

        Date db, de;
        try {
            db = df.parse(e.getChildText(DATE_BEGIN));
            de = df.parse(e.getChildText(DATE_END));
        } catch (ParseException e1) {
            e1.printStackTrace();
            return null;
        }

        return new Booking(
            Integer.parseInt(e.getChildText(XML_INNER_ID)),
            rooms.get(Integer.parseInt(e.getChildText(ROOM_ID))),
            e.getChildText(LOGIN),
            db, de
        );
    }













}
