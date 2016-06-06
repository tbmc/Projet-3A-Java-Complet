package utils;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Classe permettant d'enregister un Element XML dans un fichier
 */
public class XMLToFileSaver {

    /**
     * String contenant le chemin du fichier
     */
    private String file = null;

    /**
     * Crée une nouvel instance de la classe
     * @param file Chemin du fichier dans lequel lire et écrire la base de données
     */
    public XMLToFileSaver(String file) {
        this.file = file;
    }

    /**
     * Récupère le chemin du fichier
     * @return Chemin du fichier
     */
    public String getFile() {
        return file;
    }

    /**
     * Défini le chemin du fichier dans lequel lire et écrire la base de données
     * @param file chaine contenant le chemin du fichier
     */
    public void setFile(String file) {
        this.file = file;
    }

    /**
     * Enregistre l'élément XML dans le fichier
     * @param root {@link Element} XML à enregistrer dans le fichier
     * @return vrai en cas de succès ou faux sinon
     */
    public boolean saveToFile(Element root) {
        boolean outBool = false;
        try {
            Document dom = new Document(root);
            XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
            out.output(dom, new FileOutputStream(file));
            outBool = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outBool;
    }

    /**
     * Charge un {@link Element} XML à partir du fichier
     * @return {@link Element} XML contenant les données du fichier
     */
    public Element loadFromFile() {
        Document dom = null;
        try {
            SAXBuilder sxb = new SAXBuilder();
            File f = new File(file);
            if(!f.exists())
                return null;
            dom = sxb.build(new File(file));
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        if(dom == null)
            return null;
        else
            return dom.getRootElement();
    }


}
