package fr.ul.miage.structu.applastfm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import static com.mongodb.client.model.Filters.*;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.result.InsertOneResult;

public class RequestManager {
    private String appKEY = "dcfdc8018e4fab59a9015539adc6b50e";

    public String request(String url) {
        HTTPTools httpTools = new HTTPTools();
        String jsonResponse = httpTools.sendGet(url);
        Document docLastFm = Document.parse(jsonResponse);

        // Création du JSON a retourner
        Document respDoc = new Document();
        return jsonResponse;
    }

    // Information sur un style musical
    public void getTagMusicInfo(String tag) {
        MongoCollection<Document> collection = null;
        MongoDatabase database = MongoClientConnection.connect();
        // MongoCollection<Document> collection = database.getCollection("test");
        Boolean collectionExists = database.listCollectionNames().into(new ArrayList<String>())
                .contains("GCSTM_tag");

        if (!collectionExists) {
            System.out.println("Existe pas");
            database.createCollection("GCSTM_tag");
        }
        collection = database.getCollection("GCSTM_tag");
        Bson projectionFields = Projections.fields(Projections.include("name", "summary", "content"),
                Projections.excludeId());
        Document doc = collection.find(eq("name", tag)).projection(projectionFields).first();
        if (doc == null) {
            String url = " http://ws.audioscrobbler.com/2.0/?method=tag.getinfo&tag=" + tag + "&api_key=" + this.appKEY
                    + "&format=json";
            String jsonResponse = request(url);
            // ArrayList<String> res = new ArrayList<>();
            JSONObject json = new JSONObject(jsonResponse);
            JSONObject jsonObj = json.getJSONObject("tag").getJSONObject("wiki");
            // String summary = "Résumé : \n";
            String summary = jsonObj.getString("summary");
            // String content = "Intégralité : \n";
            String content = jsonObj.getString("content");
            // res.add(summary);
            // res.add(content);
            InsertOneResult result = collection.insertOne(new Document()
                    .append("_id", new ObjectId())
                    .append("name", tag)
                    .append("summary", summary)
                    .append("content", content));
            System.out.println("Insertion successful");
        } else {
            System.out.println(doc.get("name"));
        }
        // return res;
    }

    // Information sur un album
    public void getAlbumMusicInfo(String nomAlbum, String nomArtiste) {
        MongoCollection<Document> collection = null;
        MongoDatabase database = MongoClientConnection.connect();
        // MongoCollection<Document> collection = database.getCollection("test");
        Boolean collectionExists = database.listCollectionNames().into(new ArrayList<String>())
                .contains("GCSTM_album");
        if (!collectionExists) {
            System.out.println("Existe pas");
            database.createCollection("GCSTM_album");
        }
        collection = database.getCollection("GCSTM_album");
        Bson projectionFields = Projections.fields(
                Projections.include("name", "artist", "nbMusic", "music", "duration", "publicationDate"),
                Projections.excludeId());
        Document doc = collection.find(and(eq("name", nomAlbum), eq("artist", nomArtiste))).projection(projectionFields)
                .first();

        if (doc == null) {
            String url = "http://ws.audioscrobbler.com/2.0/?method=album.getinfo&api_key=" + this.appKEY + "&artist="
                    + nomArtiste + "&album=" + nomAlbum + "&format=json";
            String jsonResponse = request(url);
            ArrayList<Object> res = new ArrayList<>();
            ArrayList<String> nomTitre = new ArrayList<>();
            JSONObject json = new JSONObject(jsonResponse);
            JSONArray jsonArray = json.getJSONObject("album").getJSONObject("tracks").getJSONArray("track");
            int nbrPiste = 0;
            double duration = 0;

            // a revoir
            for (int i = 0; i < jsonArray.length(); i++) {
                nbrPiste = i;
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                nomTitre.add(jsonObject.getString("name"));
                duration = duration + jsonObject.optInt("duration", 0);
            }
            res.add(nbrPiste);
            res.add(duration);
            for (int i = 0; i < nomTitre.size(); i++) {
                res.add(nomTitre.get(i));
            }
            InsertOneResult result = collection.insertOne(new Document()
                    .append("_id", new ObjectId())
                    .append("name", nomAlbum)
                    .append("artist", nomArtiste)
                    .append("nbMusic", nbrPiste)
                    .append("music", nomTitre)
                    .append("duration", duration)
                    .append("publicationDate", "")); // Cherche a trouvé la date de publication
            System.out.println("INSERTION");
        } else {
            System.out.println(doc.toJson());
        }
        // Met dans la liste nomTitre le titre de chaques musique de l'album et
        // l'affiche
        // return res;
    }

    // Rechercher unartiste à partir d'un album
    // >>CSD : Pas compris.
    public String ArtisteNom(String nomAlbum) {

        String url = "http://ws.audioscrobbler.com/2.0/?method=album.search&album=" +
                nomAlbum + "&api_key="
                + this.appKEY + "&format=json";
        String jsonResponse = request(url);

        ArrayList<String> nomsArtiste = new ArrayList<>();

        JSONObject json = new JSONObject(jsonResponse);
        JSONArray jsonArray = json.getJSONObject("results").getJSONObject("albummatches").getJSONArray("album");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObjectnbr = jsonArray.getJSONObject(i);
            nomsArtiste.add(jsonObjectnbr.getString("artist"));

        }
        HashSet<String> supDouble = new HashSet<>(nomsArtiste);

        nomsArtiste = new ArrayList<>(supDouble);
        String result = "voici les artistes ayant un album du nom " + nomAlbum + ":\n";
        for (int index = 0; index < nomsArtiste.size(); index++) {
            result = result + nomsArtiste.get(index) + "\n";

        }
        return result;
    }

    public void getInfoArtiste(String nameArtist) {
        MongoCollection<Document> collection = null;
        MongoDatabase database = MongoClientConnection.connect();
        Boolean collectionExists = database.listCollectionNames().into(new ArrayList<String>())
                .contains("GCSTM_artist");
        if (!collectionExists) {
            System.out.println("Existe pas --> Creation collection");
            database.createCollection("GCSTM_artist");
        }
        collection = database.getCollection("GCSTM_artist");
        Bson projectionFields = Projections.fields(
                Projections.include("name", "albums", "tracks", "tags", "similarArtists"),
                Projections.excludeId());
        Document doc = collection.find((eq("name", nameArtist))).projection(projectionFields)
                .first();
        if (doc == null) {
            // traitement tags et similar
            String url = "http://ws.audioscrobbler.com/2.0/?method=artist.getinfo&api_key="
                    + this.appKEY + "&artist=" + nameArtist + "&format=json";
            String jsonResponseInfo = request(url);
            ArrayList<String> listSimilarArtist = new ArrayList<String>();
            ArrayList<String> listTagsArtist = new ArrayList<String>();
            JSONObject json = new JSONObject(jsonResponseInfo);
            JSONArray jsonArray = json.getJSONObject("artist").getJSONObject("similar").getJSONArray("artist");
            for (int i = 0; i < jsonArray.length(); i++) {
                // JSONObject tmp = jsonArraySimilar.getJSONObject(i);
                listSimilarArtist.add(jsonArray.getJSONObject(i).getString("name"));
            }
            jsonArray = json.getJSONObject("artist").getJSONObject("tags").getJSONArray("tag");
            for (int j = 0; j < jsonArray.length(); j++) {
                listTagsArtist.add(jsonArray.getJSONObject(j).getString("name"));
            }
            // Traitement liste albums
            url = "http://ws.audioscrobbler.com/2.0/?method=artist.gettopalbums&api_key="
                    + this.appKEY + "&artist=" + nameArtist + "&format=json";
            jsonResponseInfo = request(url);
            ArrayList<String> listAlbums = new ArrayList<String>();
            JSONObject jsonAlbums = new JSONObject(jsonResponseInfo);
            JSONArray jsonArrayAlbums = jsonAlbums.getJSONObject("topalbums").getJSONArray("album");
            for (int k = 0; k < jsonArrayAlbums.length(); k++) {
                listAlbums.add(jsonArrayAlbums.getJSONObject(k).getString("name"));
            }
            url = "http://ws.audioscrobbler.com/2.0/?method=artist.getTopTracks&api_key="
                    + this.appKEY + "&artist=" + nameArtist + "&format=json";
            jsonResponseInfo = request(url);
            ArrayList<String> listTracks = new ArrayList<String>();
            JSONObject jsonTracks = new JSONObject(jsonResponseInfo);
            JSONArray jsonArrayTracks = jsonTracks.getJSONObject("toptracks").getJSONArray("track");
            for (int l = 0; l < jsonArrayTracks.length(); l++) {
                listTracks.add(jsonArrayTracks.getJSONObject(l).getString("name"));
            }
            InsertOneResult result = collection.insertOne(new Document()
                    .append("_id", new ObjectId())
                    .append("name", nameArtist)
                    .append("albums", listAlbums)
                    .append("tracks", listTracks)
                    .append("tags", listTagsArtist)
                    .append("similarArtists", listSimilarArtist));
            System.out.println("INSETION INTO DATABASE");
        } else {
            System.out.println(doc.toJson());
        }
    }

    // // recherche les artistes similaire d'un artiste :
    // public String ArtristeSimilaire(String nomArtiste) {
    // String url =
    // "http://ws.audioscrobbler.com/2.0/?method=artist.getsimilar&artist=" +
    // nomArtiste + "&api_key="
    // + this.appKEY + "&format=json";
    // String jsonResponse = request(url);

    // ArrayList<String> nomsArtiste = new ArrayList<>();

    // JSONObject json = new JSONObject(jsonResponse);
    // JSONArray jsonArray =
    // json.getJSONObject("similarartists").getJSONArray("artist");

    // for (int i = 0; i < jsonArray.length(); i++) {
    // JSONObject jsonObjectnbr = jsonArray.getJSONObject(i);
    // nomsArtiste.add(jsonObjectnbr.getString("name"));

    // }
    // HashSet<String> supDouble = new HashSet<>(nomsArtiste);
    // nomsArtiste = new ArrayList<>(supDouble);
    // String result = "voici les artistes similaires à " + nomArtiste + ": \n";
    // for (int index = 0; index < nomsArtiste.size(); index++) {
    // result = result + nomsArtiste.get(index) + "\n";

    // }

    // return result;

    // }

    // // recherche le albums d'un artiste :
    // public String TopAlbum(String nomArtiste) {
    // String url =
    // "http://ws.audioscrobbler.com/2.0/?method=artist.gettopalbums&artist=" +
    // nomArtiste + "&api_key="
    // + this.appKEY + "&format=json ";
    // String jsonResponse = request(url);

    // ArrayList<String> nomsArtiste = new ArrayList<>();

    // JSONObject json = new JSONObject(jsonResponse);
    // JSONArray jsonArray = json.getJSONObject("topalbums").getJSONArray("album");

    // for (int i = 0; i < jsonArray.length(); i++) {
    // JSONObject jsonObjectnbr = jsonArray.getJSONObject(i);
    // nomsArtiste.add(jsonObjectnbr.getString("name"));

    // }
    // HashSet<String> supDouble = new HashSet<>(nomsArtiste);
    // nomsArtiste = new ArrayList<>(supDouble);
    // String result = "voici les albums de " + nomArtiste + ": \n";
    // for (int index = 0; index < nomsArtiste.size(); index++) {
    // result = result + nomsArtiste.get(index) + "\n";

    // }

    // return result;

    // }

    // Retourne le top 10 ( a revoir je pense)
    public void Top10Global(String nomPays) {
        MongoCollection<Document> collection = null;
        MongoDatabase database = MongoClientConnection.connect();
        Boolean collectionExists = database.listCollectionNames().into(new ArrayList<String>())
                .contains("GCSTM_top10");
        if (!collectionExists) {
            System.out.println("Existe pas --> Creation collection");
            database.createCollection("GCSTM_top10");
        }

        collection = database.getCollection("GCSTM_top10");
        Bson projectionFields = Projections.fields(
                Projections.include("places", "top10song", "top10tags", "nbListening", "nbAuditors", "date"),
                Projections.excludeId());
        // Document doc = collection.find((eq("country",
        // nomPays))).projection(projectionFields)
        // .first();
        String jsonResponse = "";
        String url = "";
        JSONObject json;
        JSONArray jsonArray;
        ArrayList<String> top10track = new ArrayList<String>();
        ArrayList<String> top10tag = new ArrayList<String>();
        String track = "";
        String tag = "";
        int nbAuditors = 0;
        int nbListening = 0;
        if (nomPays == "") {
            nomPays = "global";
            // top 10 son
            url = "http://ws.audioscrobbler.com/2.0/?method=chart.gettoptracks&limit=10&api_key=" + this.appKEY
                    + "&format=json";
            jsonResponse = request(url);
            json = new JSONObject(jsonResponse);
            jsonArray = json.getJSONObject("tracks").getJSONArray("track");
            for (int i = 0; i < jsonArray.length(); i++) {
                track = "" + (i + 1) + " - " + jsonArray.getJSONObject(i).getString("name");
                top10track.add(track);
            }
            // top 10 tags
            url = "http://ws.audioscrobbler.com/2.0/?method=chart.gettoptags&limit=10&api_key=" + this.appKEY
                    + "&format=json";
            jsonResponse = request(url);
            json = new JSONObject(jsonResponse);
            jsonArray = json.getJSONObject("tags").getJSONArray("tag");
            for (int j = 0; j < jsonArray.length(); j++) {
                tag = "" + (j + 1) + " - " + jsonArray.getJSONObject(j).getString("name");
                top10tag.add(tag);
            }
        } else {
            url = "http://ws.audioscrobbler.com/2.0/?method=geo.gettoptracks&limit=10&country=" + nomPays
                    + "&api_key="
                    + this.appKEY + "&format=json";
            jsonResponse = request(url);
            json = new JSONObject(jsonResponse);
            jsonArray = json.getJSONObject("tracks").getJSONArray("track");
            for (int i = 0; i < jsonArray.length(); i++) {
                track = "" + (i + 1) + " - " + jsonArray.getJSONObject(i).getString("name");
                nbAuditors += jsonArray.getJSONObject(i).getInt("listeners");
                top10track.add(track);
            }
        }

        SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String sysdate = s.format(date);

        InsertOneResult result = collection.insertOne(new Document()
                .append("_id", new ObjectId())
                .append("places", nomPays)
                .append("top10song", top10track)
                .append("top10tags", top10tag)
                .append("nbListening", nbListening)
                .append("nbAuditors", nbAuditors)
                .append("date", sysdate)); // trouver solution pour savoir quoi mettre en valeur
        System.out.println("INSERTION IN DATABASE");

        // Traitement des données

        // return result;

    }

    // ArrayList<String> nomsMusiques = new ArrayList<>();

    // JSONObject json = new JSONObject(jsonResponse);
    // JSONArray jsonArray = json.getJSONObject("tracks").getJSONArray("track");

    // for (int i = 0; i < jsonArray.length(); i++) {
    // JSONObject jsonObjectnbr = jsonArray.getJSONObject(i);
    // nomsMusiques.add(jsonObjectnbr.getString("name"));

    // }
    // HashSet<String> supDouble = new HashSet<>(nomsMusiques);
    // nomsMusiques = new ArrayList<>(supDouble);
    // String result = "voici le top 50 pour le pays séléctioné : (" + nomPays + ")
    // \n";
    // for (int index = 0; index < nomsMusiques.size(); index++) {
    // result = result + nomsMusiques.get(index) + "\n";

    // }

    public void searchSimilarMusicBetween2Artist(String nameArtist1, String nameArtist2) {
        /**
         * ALGO EN GROS
         * // On recherche si les artistes existe dans la base de données (collection
         * // artist)
         * // Si oui, on récupére les tags commun entre les deux artistes
         * // On recherche les musiques de l'artiste 2 dans la base local si existe
         * // Pour chaque musique, on recherche dans la base local (si existe pas on
         * fait
         * // requete)
         * // On compare le/les tag/tags de la musique avec les tags communs
         * // Si c'est commun, on insère dans la liste, sinon, on passe à la prochaine
         * // musiques
         * // fin pour
         * // Meme chose pour les albums.
         */

        // MongoCollection<Document> collection = null;
        // MongoDatabase database = MongoClientConnection.connect();
        // Boolean collectionExists = database.listCollectionNames().into(new
        // ArrayList<String>())
        // .contains("GCSTM_similarMusicArtist");
        // if (!collectionExists) {
        // System.out.println("Existe pas --> Creation collection");
        // database.createCollection("GCSTM_similarMusicArtist");
        // }

        // collection = database.getCollection("GCSTM_similarMusicArtist");
        // Bson projectionFields = Projections.fields(
        // Projections.include("artist1", "artist2", "tagCommun", "musicCommun",
        // "albumCommun"),
        // Projections.excludeId());

    }

}
