package fr.ul.miage.structu.applastfm;

import java.util.ArrayList;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.google.gson.JsonArray;

public class RequestManager {
    private String appKEY;

    // Information sur un style musicale
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

    // Information sur un album
    public void getAlbumMusicInfo(String nomAlbum, String nomArtiste)
            throws JsonMappingException, JsonProcessingException {

        this.appKEY = "dcfdc8018e4fab59a9015539adc6b50e";
        // Préparation de la requete

        String url = "http://ws.audioscrobbler.com/2.0/?method=album.getinfo&api_key=" + this.appKEY + "&artist="
                + nomArtiste + "&album=" + nomAlbum + "&format=json";
        System.out.println(url);
        HTTPTools httpTools = new HTTPTools();
        String jsonResponse = httpTools.sendGet(url);
        Document docLastFm = Document.parse(jsonResponse);

        // Met dans la liste nbrTitre le numéro de chaques titre de l'album et l'affiche
        // Met dans la liste nomTitre le titre de chaques musique de l'album et
        // l'affiche
        ArrayList<Integer> nbrTitre = new ArrayList<>();
        ArrayList<String> nomTitre = new ArrayList<>();
        JSONObject json = new JSONObject(jsonResponse);
        JSONArray jsonArray = json.getJSONObject("album").getJSONObject("tracks").getJSONArray("track");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObjectnbr = jsonArray.getJSONObject(i).getJSONObject("@attr");
            nbrTitre.add(jsonObjectnbr.getInt("rank"));

            JSONObject jsonObjectnom = jsonArray.getJSONObject(i);
            nomTitre.add(jsonObjectnom.getString("name"));

        }

        for (int index = 0; index < nbrTitre.size(); index++) {
            System.out.println(nbrTitre.get(index));
            System.out.println(nomTitre.get(index));
        }

    }

}
