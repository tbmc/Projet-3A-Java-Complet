package utils;

import org.jdom2.Element;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * Classe pour gérer le XML
 */
public class XMLUtils {

    /**
     * Interface pour gérer l'enregistrement des éléments XML à partir des données de la classe
     * et inversement
     */
    public interface XMLSerializable extends Serializable {
        /**
         * Récupère le nom de l'élément XML contenant les données
         * @return Le nom du tag XML
         */
        String getXML_NAME();

        /**
         * Récupère le nom du tag XML contenant l'ID dans l'élément
         * @return tag XML
         */
        String getXML_INNER_ID();

        /**
         * Crée l'élément XML contenant toutes les informations de la classe
         * @return Elément XML contenant toutes les informations de la classe
         */
        Element getXMLElement();

        /**
         * Crée un élément de la classe T (la classe implémentant l'interface) et le rempli avec les
         * données contenu dans le XML
         * @param e {@link Element} XML à partir du quel on récupère les informations
         * @param params Paramètre optionnel permettant de passer des informations supplémentaires
         *               par exemple pour la classe booking qui nécessite d'avoir la hastable des Room
         * @param <T> Type définissant une classe implémentant l'interface XMLSerializable
         * @return La classe instanciée contenant toutes les informations du XML la concernant
         */
        <T extends XMLSerializable> T createFromXMLElement(Element e, Object params);

    }

    /**
     * Génère un {@link Element} XML à partir des données contenu dans une hashtable
     * @param xmlName nom du tag XML
     * @param in Hashtable ayant pour clé un {@link Integer} et en valeur, une classe implémentant {@link XMLSerializable}
     * @return L'{@link Element} XML
     */
    public static Element getXMLFromHashTable(String xmlName, Hashtable<Integer, ? extends XMLSerializable> in) {
        Element e = new Element(xmlName);
        Enumeration<Integer> keys = in.keys();
        while(keys.hasMoreElements()) {
            e.addContent(in.get(keys.nextElement()).getXMLElement());
        }
        return e;
    }

    /**
     * Même fonction que {@link #getFromElement(Element, Hashtable, XMLSerializable, Object)}
     * mais le paramètre params est mis à null
     * @param e
     * @param out
     * @param s
     * @param <T>
     * @return
     */
    public static <T extends XMLSerializable> boolean getFromElement(Element e, Hashtable<Integer, T> out, XMLSerializable s) {
        return getFromElement(e, out, s, null);
    }

    /**
     * Rempli une hashtable à partir des données dans un XML
     * @param e {@link Element} XML contenant les données
     * @param out Hashtable de sortie contenant les données, la clé doit être un {@link Integer} et la valeur doit être un {@link T}
     * @param s Instance de la classe en valeur de la hashtable, cela permet d'accéder aux fonctions non statiques
     *          Ce parmètre est rendu obligatoire car on ne peut pas forcer les classes à implémenter des fonctions statiques
     *          à l'aide d'une interface
     * @param params Paramètre obtionnel permettant de passer des données supplémentaires
     * @param <T> Classe implémentant l'interface {@link XMLSerializable}
     * @return vrai en cas de succès et faux en cas d'échec
     */
    public static <T extends XMLSerializable> boolean getFromElement(Element e, Hashtable<Integer, T> out, XMLSerializable s,  Object params) {

        if(s.getXML_NAME() == null || s.getXML_INNER_ID() == null)
            return false;
        Iterator<Element> i = e.getChildren(s.getXML_NAME()).iterator();
        Integer id;
        T newClass;
        while(i.hasNext()) {
            Element n = i.next();
            id = Integer.parseInt(n.getChildText(s.getXML_INNER_ID()));
            newClass = s.createFromXMLElement(n, params);
            if(id == null || newClass == null)
                return false;
            out.put(id, newClass);
        }
        return true;

    }

    /**
     * Génère le hash MD5 d'une chaine de caractère
     * @param in Chaine à hasher
     * @return Hash de la chaine en entré
     */
    public static String md5(String in) {
        String out = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(in.getBytes());
            out = new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return out;
    }

}