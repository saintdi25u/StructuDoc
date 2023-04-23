package fr.ul.miage.structu.applastfm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

import static com.mongodb.client.model.Filters.*;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.result.InsertOneResult;

public class RequestManager {
    private String appKEY = "dcfdc8018e4fab59a9015539adc6b50e";

    public String request(String url) {
        HTTPTools httpTools = new HTTPTools();
        String jsonResponse = httpTools.sendGet(url);
        // Création du JSON a retourner
        return jsonResponse;
    }

    public MongoCollection<Document> connectAndTestIfCollectionExist(String collectionName) {
        MongoDatabase database = MongoClientConnection.connect();
        // MongoCollection<Document> collection = database.getCollection("test");
        Boolean collectionExists = database.listCollectionNames().into(new ArrayList<String>())
                .contains(collectionName);

        if (!collectionExists) {
            System.out.println("Collection n'existe pas");
            database.createCollection(collectionName);
        }
        MongoCollection<Document> collection = database.getCollection(collectionName);
        return collection;
    }

    // Information sur un style musical
    public ArrayList<String> getTagMusicInfo(String tag) {
    	ArrayList<String> res = new ArrayList<>();
        MongoCollection<Document> collection = connectAndTestIfCollectionExist("GCSTM_tag");
        Bson projectionFields = Projections.fields(Projections.include("name", "summary", "content"),
                Projections.excludeId());
        Document doc = collection.find(eq("name", tag)).projection(projectionFields).first();
        if (doc == null) {
            String url = " http://ws.audioscrobbler.com/2.0/?method=tag.getinfo&tag=" + tag + "&api_key=" + this.appKEY
                    + "&format=json";
            String jsonResponse = request(url);
             
            JSONObject json = new JSONObject(jsonResponse);
            JSONObject jsonObj = json.getJSONObject("tag").getJSONObject("wiki");
            // String summary = "Résumé : \n";
            String summary = jsonObj.getString("summary");
            // String content = "Intégralité : \n";
            String content = jsonObj.getString("content");
           
            InsertOneResult result = collection.insertOne(new Document()
                    .append("_id", new ObjectId())
                    .append("name", tag)
                    .append("summary", summary)
                    .append("content", content));
            System.out.println("Insertion successful");
             res.add(tag);
             res.add(summary);
             res.add(content);
             res.add("API");
        } else {
            //System.out.println(doc.get("name"));
        	res.add((String) doc.get("name"));
        	res.add((String) doc.get("summary"));
        	res.add((String) doc.get("content"));
        	res.add("BDD");
        }
         return res;
    }

    // Information sur un album
    public ArrayList<Object> getAlbumMusicInfo(String nomAlbum, String nomArtiste) {
    	ArrayList<Object> res = new ArrayList<>();
    	
        MongoCollection<Document> collection = connectAndTestIfCollectionExist("GCSTM_album");
        Bson projectionFields = Projections.fields(
        		Projections.include("name", "artist", "nbMusic", "music", "duration","playcount", "publicationDate"),
                Projections.excludeId());
        Document doc = collection.find(and(eq("name", nomAlbum), eq("artist", nomArtiste))).projection(projectionFields)
                .first();

        if (doc == null) {
        
            String url = "http://ws.audioscrobbler.com/2.0/?method=album.getinfo&album=" + nomAlbum + "&api_key=" + this.appKEY + "&format=json&artist="+nomArtiste;
            String jsonResponse = request(url);
            if(jsonResponse!="erreur") {
            	
            	
            	ArrayList<String> nomTitre = new ArrayList<>();
            	JSONObject json = new JSONObject(jsonResponse);
            	if (json.getJSONObject("album").has("tracks")) {
            		if (json.getJSONObject("album").getJSONObject("tracks").get("track") instanceof JSONArray) {
            			JSONArray jsonArray = json.getJSONObject("album").getJSONObject("tracks").getJSONArray("track");
            			int nbrPiste = 0;
            			double duration = 0;
            			
            			
            			for (int i = 0; i < jsonArray.length(); i++) {
            				nbrPiste = i;
            				JSONObject jsonObject = jsonArray.getJSONObject(i);
            				nomTitre.add(jsonObject.getString("name"));
            				duration = duration + jsonObject.optInt("duration", 0);
            			}
            			
            			
            			Integer playcount = json.getJSONObject("album").optInt("playcount", 0);
            			InsertOneResult result = collection.insertOne(new Document()
            					.append("_id", new ObjectId())
            					.append("name", nomAlbum)
            					.append("artist", nomArtiste)
            					.append("nbMusic", nbrPiste)
            					.append("music", nomTitre)
            					.append("duration", duration)
            					.append("playcount", playcount)
            					.append("publicationDate", "")); // Cherche a trouvé la date de publication
            			System.out.println("INSERTION");
            			res.add(nomAlbum);
            			res.add(nomArtiste);
            			res.add(nbrPiste);
            			res.add(nomTitre);
            			res.add(duration);
            			res.add("API");
            			res.add(playcount);
        				

            			
            		}
            	}
            }
      
        } else {
            res.add((String) doc.get("name"));
			res.add((String) doc.get("artist"));
			res.add((Integer) doc.get("nbMusic"));
			res.add((ArrayList<String>) doc.get("music"));
			res.add((Double) doc.get("duration"));
			res.add("BDD");
			res.add((Integer) doc.get("playcount"));
        }
        // Met dans la liste nomTitre le titre de chaques musique de l'album et
        // l'affiche
         return res;
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

    public ArrayList<Object> getInfoArtiste(String nameArtist) {
    	ArrayList<Object> res = new ArrayList<>();
        MongoCollection<Document> collection = connectAndTestIfCollectionExist("GCSTM_artist");
        Bson projectionFields = Projections.fields(
        		Projections.include("name", "albums", "tracks", "tags","playcount", "similarArtists"),
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
                if (!jsonArrayAlbums.getJSONObject(k).getString("name").contains(" ")) {
                	System.out.println(jsonArrayAlbums.getJSONObject(k).getString("name"));
                	getAlbumMusicInfo(jsonArrayAlbums.getJSONObject(k).getString("name"),nameArtist);
				}
            }
          
            
            url = "http://ws.audioscrobbler.com/2.0/?method=artist.getTopTracks&api_key="
                    + this.appKEY + "&artist=" + nameArtist + "&format=json";
            jsonResponseInfo = request(url);
            ArrayList<String> listTracks = new ArrayList<String>();
            JSONObject jsonTracks = new JSONObject(jsonResponseInfo);
            JSONArray jsonArrayTracks = jsonTracks.getJSONObject("toptracks").getJSONArray("track");
            for (int l = 0; l < jsonArrayTracks.length(); l++) {
                listTracks.add(jsonArrayTracks.getJSONObject(l).getString("name"));
                if (!jsonArrayTracks.getJSONObject(l).getString("name").contains(" ")) {
            		getTrack(jsonArrayTracks.getJSONObject(l).getString("name"),nameArtist);
				}            
            }
            
            
            
            Integer playcount = json.getJSONObject("artist").getJSONObject("stats").optInt("playcount", 0);
            InsertOneResult result = collection.insertOne(new Document()
                    .append("_id", new ObjectId())
                    .append("name", nameArtist)
                    .append("albums", listAlbums)
                    .append("tracks", listTracks)
                    .append("tags", listTagsArtist)
                    .append("playcount", playcount)
                    .append("similarArtists", listSimilarArtist));
            System.out.println("INSETION INTO DATABASE");
            res.add(nameArtist);
			res.add(listAlbums);
			res.add(listTracks);
			res.add(listTagsArtist);
			res.add(listSimilarArtist);
			res.add(playcount);
			res.add("API");
			
            
        } else {
            res.add((String) doc.get("name"));
			res.add((ArrayList<String>) doc.get("albums"));
			res.add((ArrayList<String>) doc.get("tracks"));
			res.add((ArrayList<String>) doc.get("listTagsArtist"));
			res.add((ArrayList<String>) doc.get("listSimilarArtist"));
			res.add((Integer) doc.get("playcount"));
			res.add("BDD");
        }
        return res;
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
    public ArrayList<Object> Top10Global(String nomPays, String choix) {
    	ArrayList<Object> res = new ArrayList<>();
        MongoCollection<Document> collection = connectAndTestIfCollectionExist("GCSTM_top10");
        Bson projectionFields = Projections.fields(
                Projections.include("places", "top10song", "top10tags", "top10artists", "nbListening", "nbAuditors",
                        "date"),
                Projections.excludeId());
        
        Document doc;
        if (choix == "global") {
        	  doc = collection.find((eq("places",
        			 "global"))).projection(projectionFields)
			         .first();
        				
		}else {
			  doc = collection.find((eq("places",
			         nomPays))).projection(projectionFields)
			         .first();
		}
        
         
        if (doc == null) {
	        String jsonResponse = "";
	        String url = "";
	        JSONObject json;
	        JSONArray jsonArray;
	        ArrayList<String> top10track = new ArrayList<String>();
	        ArrayList<String> top10tag = new ArrayList<String>();
	        ArrayList<String> top10artists = new ArrayList<String>();
	        String track = "";
	        String tag = "";
	        String artist = "";
	        int nbAuditors = 0;
	        int nbListening = 0;
	        if (choix == "global") {
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
	            // top10 artists
	            url = "http://ws.audioscrobbler.com/2.0/?method=chart.gettopartists&limit=10&api_key=" + this.appKEY
	                    + "&format=json";
	            jsonResponse = request(url);
	            json = new JSONObject(jsonResponse);
	            jsonArray = json.getJSONObject("artists").getJSONArray("artist");
	            for (int l = 0; l < jsonArray.length(); l++) {
	                artist = "" + (l + 1) + " - " + jsonArray.getJSONObject(l).getString("name");
	                top10artists.add(tag);
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
	                // nbAuditors += jsonArray.getJSONObject(i).getInt("listeners");
	                top10track.add(track);
	            }
	            url = "http://ws.audioscrobbler.com/2.0/?method=geo.gettopartists&limit=10&country=" + nomPays
	                    + "&api_key="
	                    + this.appKEY + "&format=json";
	            jsonResponse = request(url);
	            json = new JSONObject(jsonResponse);
	            jsonArray = json.getJSONObject("topartists").getJSONArray("artist");
	            for (int j = 0; j < jsonArray.length(); j++) {
	                artist = "" + (j + 1) + " - " + jsonArray.getJSONObject(j).getString("name");
	                // nbAuditors += jsonArray.getJSONObject(j).getInt("listeners");
	                top10artists.add(artist);
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
	                .append("top10artists", top10artists)
	                .append("nbListening", nbListening)
	                .append("nbAuditors", nbAuditors)
	                .append("date", sysdate)); // trouver solution pour savoir quoi mettre en valeur
	        System.out.println("INSERTION IN DATABASE");
	        
	        res.add(nomPays);
	        res.add(top10track);
	        res.add(top10tag);
	        res.add(top10artists);
	        res.add(nbListening);
	        res.add(nbAuditors);
	        res.add(sysdate);
	        res.add("API");

	        
    }else {
    	res.add((String) doc.get("places"));
		res.add((ArrayList<String>) doc.get("top10song"));
		res.add((ArrayList<String>) doc.get("top10tags"));
		res.add((ArrayList<String>) doc.get("top10artists"));
		res.add((Integer) doc.get("nbListening"));
		res.add((Integer) doc.get("nbAuditors"));
		res.add((String) doc.get("date"));
		res.add("BDD");
    }
        return res;

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

    public String expressOpinionOnTag(String name, int note, String comment) {
    	String res = "";
        MongoCollection<Document> collection = connectAndTestIfCollectionExist("GCSTM_opinion");
        Bson projectionFields = Projections.fields(
                // subject = style, album, chanson, artist...
                Projections.include("subject", "name", "notation", "comment", "publishedOn"),
                Projections.excludeId());
        // Document doc = collection.find((eq("country",
        // nomPays))).projection(projectionFields).first();
        SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String sysdate = s.format(date);
        String url = "http://ws.audioscrobbler.com/2.0/?method=tag.getinfo&api_key=" + this.appKEY
                + "&format=json&tag=" + name;
        String jsonResponse = request(url);
        if (jsonResponse != "404") {
            InsertOneResult result = collection.insertOne(new Document()
                    .append("_id", new ObjectId())
                    .append("subject", "tag")
                    .append("name", name)
                    .append("notation", note)
                    .append("comment", comment)
                    .append("publishedOn", sysdate));
            res = "Commentaire enregistré";

        } else {
            res = "Erreur d'insertion du commentaire";
        }return res;
    }

    public String expressOpinionOnAlbum(String nameAlbum, String nameArtist, int note, String comment) {
    	 String res = "";
    	MongoCollection<Document> collection = connectAndTestIfCollectionExist("GCSTM_opinion");
        Bson projectionFields = Projections.fields(
                // subject = style, album, chanson, artist...
                Projections.include("subject", "name", "notation", "comment", "publishedOn"),
                Projections.excludeId());
        SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String sysdate = s.format(date);
        String url = "http://ws.audioscrobbler.com/2.0/?method=album.getinfo&api_key=" + this.appKEY
                + "&format=json&album=" + nameAlbum + "&artist=" + nameArtist;
        String jsonResponse = request(url);
        if (jsonResponse != "404") {
            InsertOneResult result = collection.insertOne(new Document()
                    .append("_id", new ObjectId())
                    .append("subject", "album")
                    .append("name", nameAlbum)
                    .append("notation", note)
                    .append("comment", comment)
                    .append("publishedOn", sysdate));
            res = "Commentaire enregistré";
        } else {
            res = "Erreur d'insertion du commentaire";
        }
        return res;
    }

    public String expressOpinionOnTrack(String nameTrack, String nameArtist, int note, String comment) {
    	String res = "";
        MongoCollection<Document> collection = connectAndTestIfCollectionExist("GCSTM_opinion");

        Bson projectionFields = Projections.fields(
                // subject = style, album, chanson, artist...
                Projections.include("subject", "name", "notation", "comment", "publishedOn"),
                Projections.excludeId());
        SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String sysdate = s.format(date);
        String url = "http://ws.audioscrobbler.com/2.0/?method=track.getinfo&api_key=" + this.appKEY
                + "&format=json&track=" + nameTrack + "&artist=" + nameArtist;
        String jsonResponse = request(url);
        if (jsonResponse != "404") {
            InsertOneResult result = collection.insertOne(new Document()
                    .append("_id", new ObjectId())
                    .append("subject", "track")
                    .append("name", nameTrack)
                    .append("notation", note)
                    .append("comment", comment)
                    .append("publishedOn", sysdate));
            res = "Commentaire enregistré";
        } else {
            res = "Erreur d'insertion du commentaire";
        }
        return res;
    }

    public String expressOpinionOnArtist(String nameArtist, int note, String comment) {
    	String res = "";
        MongoCollection<Document> collection = connectAndTestIfCollectionExist("GCSTM_opinion");
        Bson projectionFields = Projections.fields(
                // subject = style, album, chanson, artist...
                Projections.include("subject", "name", "notation", "comment", "publishedOn"),
                Projections.excludeId());
        SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String sysdate = s.format(date);
        String url = "http://ws.audioscrobbler.com/2.0/?method=track.getinfo&api_key=" + this.appKEY
                + "&format=json&artist=" + nameArtist;
        String jsonResponse = request(url);
        if (jsonResponse != "404") {
            InsertOneResult result = collection.insertOne(new Document()
                    .append("_id", new ObjectId())
                    .append("subject", "artist")
                    .append("name", nameArtist)
                    .append("notation", note)
                    .append("comment", comment)
                    .append("publishedOn", sysdate));
            res = "Commentaire enregistré";
        } else {
        	res = "Erreur d'insertion du commentaire";
        }
        return res;
    }

    public ArrayList<String> getComment(String subject, String name) {
        MongoCollection<Document> collection = connectAndTestIfCollectionExist("GCSTM_opinion");
        ArrayList<String> results = new ArrayList<String>();
        MongoCursor<Document> cursor = collection.find(and(eq("subject", subject), eq("name", name))).cursor();
        while (cursor.hasNext()) {
            Document doc = cursor.next();
    	    String comment = doc.getString("comment");
    	    results.add(comment);
        }
        return results;
    }
    
    public 	ArrayList<Object> getTrack(String nomMusique, String nomArtist) {
    	ArrayList<Object> res = new ArrayList<>();

        MongoCollection<Document> collection = null;
        MongoDatabase database = MongoClientConnection.connect();
        // MongoCollection<Document> collection = database.getCollection("test");
        Boolean collectionExists = database.listCollectionNames().into(new ArrayList<String>())
                .contains("GCSTM_Tracks");

        if (!collectionExists) {
            System.out.println("La collection n'existe pas");
            database.createCollection("GCSTM_Tracks");
        }
        collection = database.getCollection("GCSTM_Tracks");
        Bson projectionFields = Projections.fields(Projections.include("name", "artist", "listeners","playcount", "tag"),
                Projections.excludeId());
        Document doc = collection.find(eq("name", nomMusique)).projection(projectionFields).first();
        if (doc == null) {
        	
            String url = "http://ws.audioscrobbler.com/2.0/?method=track.getInfo&track="+nomMusique+"&artist="+nomArtist+"&api_key=" + this.appKEY+"&format=json";                                 		
            String jsonResponse = request(url);
            // ArrayList<String> res = new ArrayList<>();
            JSONObject json = new JSONObject(jsonResponse);
            JSONObject jsobj = json.getJSONObject("track");
            String name = jsobj.getString("name");
            String artist = jsobj.getJSONObject("artist").getString("name");
            Integer listeners = jsobj.optInt("listeners", 0);
            Integer playcount = jsobj.optInt("playcount", 0);
            
            ArrayList<String> tags = new ArrayList<String>();
            JSONArray jsonArray = json.getJSONObject("track").getJSONObject("toptags").getJSONArray("tag");
            for (int i = 0; i < jsonArray.length(); i++) {
				tags.add(jsonArray.getJSONObject(i).getString("name"));
			}

           
            //url = "http://ws.audioscrobbler.com/2.0/?method=track.getTags&track="+nomMusique+"&artist="+nomArtist+"&api_key=" + this.appKEY+"&format=json";
            //jsonResponse = request(url);
            
            
            InsertOneResult result = collection.insertOne(new Document()
                    .append("_id", new ObjectId())
                    .append("name", name)
                    .append("artist", artist)
                    .append("listeners", listeners)
            		.append("playcount", playcount)
            		.append("tag", tags)); 
            System.out.println("Insertion successful");
            res.add(name);
            res.add(artist);
            res.add(listeners);
            res.add(playcount);
            res.add(tags);
            res.add("API");
            
        } else {
            res.add((String) doc.get("name"));
            res.add((String) doc.get("artist"));
            res.add((Integer) doc.get("listeners"));
            res.add((Integer) doc.get("playcount"));
            res.add((ArrayList<String>) doc.get("tag"));
            res.add("BDD");
        }
         return res;
    }
    
    
    // typeDeRecherche doit être égale à Tracks, album ou artist
    public ArrayList<String> NbrEcouteSeuil(int seuil, String typeDeRecherche, String supInf) {
    	ArrayList<String> res = new ArrayList<>();
    	MongoCollection<Document> collection = null;
        MongoDatabase database = MongoClientConnection.connect();
        // MongoCollection<Document> collection = database.getCollection("test");
        Boolean collectionExists = database.listCollectionNames().into(new ArrayList<String>())
                .contains("GCSTM_Tracks");

        if (!collectionExists) {
            System.out.println("La collection n'existe pas");     
        }else {
        	Bson projectionFields= Projections.fields(Projections.include("name", "artist", "listeners","playcount", "tag"),
                    Projections.excludeId());
        	switch(typeDeRecherche) {        	
	        	case "Tracks":
	        		collection = database.getCollection("GCSTM_"+typeDeRecherche);
	                projectionFields = Projections.fields(Projections.include("name", "artist", "listeners","playcount", "tag"),
	                        Projections.excludeId());
	        		break;
	        	case "album":
	        		collection = database.getCollection("GCSTM_"+typeDeRecherche);
	                projectionFields = Projections.fields(Projections.include("name", "artist", "nbMusic","music","duration","playcount", "publicationDate"),
	                        Projections.excludeId());
	        		break;
	        	case "artist":
	        		collection = database.getCollection("GCSTM_"+typeDeRecherche);
	                projectionFields = Projections.fields(Projections.include("name", "albums", "tracks","tags", "playcount","similarArtists"),
	                        Projections.excludeId());
	        		break;
	        	default:
	        		System.out.println("Erreur");
	        		
	        		break;
        	}    
        		Bson filter = null;
        		if(supInf == "superieur") {
        			filter = Filters.gt("playcount", seuil);
        		}
            	if(supInf == "inferieur") {
                	 filter = Filters.lt("playcount", seuil);
            	}
            	Bson projection = Projections.include("name");

            	MongoCursor<Document> cursor = collection.find(filter).projection(projection).iterator();

            	while(cursor.hasNext()) {
            	    Document doc = cursor.next();
            	    String name = doc.getString("name");
            	    res.add(name);
            	}
            	
        	}return res;
        	
        }
        
        
    
    

}
