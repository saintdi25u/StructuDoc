package fr.ul.miage.structu.applastfm;

import java.io.UnsupportedEncodingException;

import org.bson.Document;

public class RequestManager {
    private String appKEY;

    public Document getTagMusicInfo(String tag) {

        this.appKEY = "dcfdc8018e4fab59a9015539adc6b50e";
        // Préparation de la requete
        String url = " http://ws.audioscrobbler.com/2.0/?method=tag.getinfo&tag=" + tag + "&api_key=" + this.appKEY
                + "&format=json";
        System.out.println(url);
        HTTPTools httpTools = new HTTPTools();
        String jsonResponse = httpTools.sendGet(url);
        Document docLastFm = Document.parse(jsonResponse);

        // Création du JSON a retourner
        Document respDoc = new Document();
        // Exctraction de données de docLastFm et insertion dans respDoc
        // Voir l'API org.bson.Document
        return docLastFm;

    }
}
