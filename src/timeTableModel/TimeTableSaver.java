package timeTableModel;

import org.jdom2.Element;
import utils.XMLToFileSaver;
import utils.XMLUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class TimeTableSaver {

    private String file = null;

    public final String
            ROOMS_NAME           = "Rooms",
            TIMETABLES_NAME      = "TimeTables",

            LAST_COMMIT          = "LastCommit",
            COMMIT_HASH          = "Hash",
            COMMIT_DATE          = "Date",
            DATE_FORMAT          = "dd/MM/yyyy HH:mm:ss"
    ;

    /**
     * Crée une nouvelle instance de la classe tout en définissant le chemin du fichier
     * @param file Chemin du fichier dans lequel lire et enregistrer la base de données
     */
    public TimeTableSaver(String file) {
        this.file = file;
    }

    /**
     * Charge la base de données
     * @param rooms Hashtable de sortie dans laquelle les {@link Room} seront ajoutées
     * @param timeTables Hastable de sortie dans laquelle les {@link TimeTable} seront ajoutées
     * @return Une classe permettant de représenter le dernier commit du fichier
     */
    public SavedState load(Hashtable<Integer, Room> rooms, Hashtable<Integer, TimeTable> timeTables) {
        XMLToFileSaver in = new XMLToFileSaver(file);
        Element e = in.loadFromFile();
        SavedState ss = getLastCommit(e);

        boolean b = decodeElements(e, rooms, timeTables);
        return b ? ss : null;
    }

    /**
     * Sauvegarde la base de données dans le fichier
     * @param rooms Hashtable contenant les {@link Room} à sauvegarder
     * @param timeTables Hashtable contenant les {@link TimeTable} à sauvegarder
     * @param ssOld Instance de {@link SavedState} correspondant au commit lu dans le fichier
     *              et permettant de savoir si le fichier a été modifié depuis la dernière fois où il
     *              a été lu
     * @return La nouvelle instance de {@link SavedState} représentant le nouveau commit si celui-ci a réussi ou null sinon
     */
    public SavedState save(Hashtable<Integer, Room> rooms, Hashtable<Integer, TimeTable> timeTables, SavedState ssOld) {
        // Ajout du commit dans le fichier XML
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        String date = df.format(new Date());
        String hash = XMLUtils.md5(System.currentTimeMillis() + date);
        SavedState ssNew = new SavedState(file, hash);
        Element e = new Element(LAST_COMMIT),
                ch = new Element(COMMIT_HASH),
                cd = new Element(COMMIT_DATE);
        cd.setText(date);
        ch.setText(hash);
        e.addContent(ch);
        e.addContent(cd);

        Element dom = domBuild(rooms, timeTables);
        dom.addContent(e);

        // Lecture du commit du fichier
        XMLToFileSaver out = new XMLToFileSaver(file);
        Element old = out.loadFromFile();

        boolean b = false;
        // Vérification du dernier commit du fichier pour savoir si c'est le même
        // que celui que l'on a récupéré lors de la lecture du fichier
        if(file != null && ssOld != null && file.equals(ssOld.getFilePath())) {
            SavedState ss = getLastCommit(old);
            if(!ss.getHash().equals(ssOld.getHash())) {
                b = true;
            }
        }

        if(b) {
            // Ici, il y a un conflit
            // On demande donc à l'utilisateur si il veut écraser le fichier ou non.
            System.out.println("Ecraser le fichier précédent.");
            System.out.println("Oui (O) ou Non (N) ?");
            Scanner scan = new Scanner(System.in);
            String inStr = scan.nextLine();
            Character c = Character.toUpperCase(inStr.charAt(0));
            if(c == 'O') {
                // L'utilisateur veut écraser le fichier
                System.out.println("Le fichier a été écrasé.");
                boolean bb = out.saveToFile(dom);
                return bb ? ssNew : null;
            }
            else {
                // L'utilisateur ne veut pas écraser le fichier
                System.out.println("Le fichier n'a pas été enregistré.");
                return null;
            }
        }
        else {
            // Ici il n'y a pas de conflit
            // Et donc on peut directement écrire dans le fichier
            boolean bb = out.saveToFile(dom);
            return bb ? ssNew : null;
        }
    }

    /**
     * Récupère le dernier commit d'un fichier
     * @param e Element XML à partir duquel récupérer le commit
     * @return Instance de la classe {@link SavedState} représentant le dernier commit du fichier
     */
    protected SavedState getLastCommit(Element e) {
        if(e == null) {
            XMLToFileSaver in = new XMLToFileSaver(file);
            e = in.loadFromFile();
        }
        if(e == null) return null;
        Element commit = e.getChild(LAST_COMMIT);
        if(commit != null) {
            SavedState ss = new SavedState(file, commit.getChildText(COMMIT_HASH));
            return ss;
        }
        else {
            return new SavedState(file, "Il n'y avait pas de hash présent");
        }
    }

    /**
     * Crée un {@link Element} XML à partir des Rooms et des TimeTable
     * @param rooms Hashtable contenant les Rooms à ajouter dans le XML
     * @param timeTables Hashtable contenant les TimeTables à ajouter dans le XML
     * @return {@link Element} créé contenant toutes les données
     */
    private Element domBuild(Hashtable<Integer, Room> rooms, Hashtable<Integer, TimeTable> timeTables) {
        Element root = new Element("TimeTableDB");
        root.addContent(XMLUtils.getXMLFromHashTable(ROOMS_NAME, rooms))
                .addContent(XMLUtils.getXMLFromHashTable(TIMETABLES_NAME, timeTables));
        return root;
    }

    /**
     * Modifie le chemin du fichier
     * @param file Chemin du fichier
     */
    public void setFile(String file) {
        this.file = file;
    }

    /**
     * Rempli les hashtables de Room et TimeTable à partir d'un élément XML
     * @param e Elément XML, racine du document
     * @param rooms Hashtable de sortie contenant les Room
     * @param timeTables Hashtable de sortie contenant les TimeTable
     * @return un boolean qui est vrai si le décodage a réussi et faux sinon
     */
    private boolean decodeElements(Element e, Hashtable<Integer, Room> rooms, Hashtable<Integer, TimeTable> timeTables) {
        if(e == null)
            return false;
        boolean b = false;
        Room r = new Room();
        rooms.clear();
        timeTables.clear();
        // Reconstruction des Room
        b = XMLUtils.getFromElement(e.getChild(ROOMS_NAME), rooms, new Room());
        if(!b) return false;
        // Reconstruction des Emplois du temps
        b = XMLUtils.getFromElement(e.getChild(TIMETABLES_NAME), timeTables, new TimeTable(), rooms);
        if(!b) return false;

        return b;
    }

    /**
     * Classe représentant les données d'un commit
     */
    public class SavedState {
        private String filePath, hash;

        /**
         * Crée une nouvelle instance de la classe
         * @param filePath Chemin du fichier dans lequel le commit a été fait
         * @param hash MD5 du commit
         */
        public SavedState(String filePath, String hash) {
            this.filePath = filePath;
            this.hash = hash;
        }

        /**
         * Permet de comparer un commit avec un autre
         * @param o Object avec lequel comparer le commit, si l'objet n'est pas du type SavedState, cette fonction
         *          renverra toujours faux.
         * @return Vrai si l'objet en paramètre correspond au même commit que l'instance de la classe, faux sinon.
         */
        @Override
        public boolean equals(Object o) {
            if(!(o instanceof SavedState))
                return false;
            return filePath.equals(((SavedState) o).filePath) &&
                    hash.equals(((SavedState) o).hash);
        }

        /**
         * Récupère le chemin du fichier du commit
         * @return Chemin du fichier du dernier commit
         */
        public String getFilePath() {
            return filePath;
        }

        /**
         * Retourne la MD5 du commit
         * @return MD5 du commit
         */
        public String getHash() {
            return hash;
        }
    }

}