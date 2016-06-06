package timeTableModel;

import org.jdom2.Element;
import utils.XMLUtils;

import java.util.Hashtable;

/**
 * Classe représentant une salle
 */
public class Room implements XMLUtils.XMLSerializable {

    private int id, maxStudentNumber;

    /**
     * Crée une nouvelle instance de Room
     * @param roomId Identifiant de la salle
     * @param maxStudentsNumber Nombre maximum d'élèves possibles dans la salle
     */
    public Room(int roomId, int maxStudentsNumber) {
        this.id = roomId;
        this.maxStudentNumber = maxStudentsNumber;
    }

    /**
     * Crée une nouvelle instance de la classe sans paramètre.
     * Ce constructeur est uniquement destiné à instancier la classe
     * pour accéder aux fonctions non statiques, notamment pour cette fonction
     * {@link XMLUtils#getFromElement(Element, Hashtable, XMLUtils.XMLSerializable, Object)}
     */
    Room() {
        this(-1, 0);
    }

    /**
     * Récupère l'identifiant de la salle
     * @return identifiant de la salle
     */
    public int getId() {
        return id;
    }

    /**
     * Récupère le nombre maximum d'élèves dans la salle
     * @return nombre maximum d'élèves dans la salle
     */
    public int getMaxStudentsNumber() {
        return maxStudentNumber;
    }

    /**
     * Retourne toutes les informations de la classe sous la forme d'une chaine de caractères
     * @return Chaine de caractères contenant toutes les informations de la classe
     */
    public String getInfo() {
        return "Room : " + id + " | Capacité : " + maxStudentNumber;
    }

    @Override
    public Element getXMLElement() {
        Element e = new Element(getXML_NAME()),
                rid = new Element(getXML_INNER_ID()),
                rcp = new Element("Capacity");
        rid.setText("" + id);
        rcp.setText("" + maxStudentNumber);
        e.addContent(rid);
        e.addContent(rcp);
        return e;
    }

    @Override
    public Room createFromXMLElement(Element e, Object params) {
        Integer a, b;

        try {
            a = Integer.parseInt(e.getChildText(getXML_INNER_ID()));
            b = Integer.parseInt(e.getChildText("Capacity"));
        }
        catch(NumberFormatException exc) {
            exc.printStackTrace();
            return null;
        }

        return new Room(a, b);
    }

    @Override
    public String getXML_NAME() { return "Room"; }

    @Override
    public String getXML_INNER_ID() { return "RoomId"; }

}
