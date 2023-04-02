package fr.ul.miage.structu.applastfm;

import java.util.ArrayList;
import java.util.HashSet;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;



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
    
    // Information sur un style musicale
    public ArrayList<String> getTagMusicInfo(String tag) {
        String url = " http://ws.audioscrobbler.com/2.0/?method=tag.getinfo&tag=" + tag + "&api_key=" + this.appKEY
                + "&format=json";
        
        String jsonResponse = request(url);
        
        ArrayList<String> res = new ArrayList<>();
        JSONObject json = new JSONObject(jsonResponse);
        JSONObject jsonObj = json.getJSONObject("tag").getJSONObject("wiki");
        String summary = "Résumé : \n";
        summary = summary + jsonObj.getString("summary");
        String content = "Intégralité : \n";
        content = content + jsonObj.getString("content");
        res.add(summary);
        res.add(content);
        
        
        return res;

    }

    // Information sur un album
    public  ArrayList<Object> getAlbumMusicInfo(String nomAlbum, String nomArtiste) {
        String url = "http://ws.audioscrobbler.com/2.0/?method=album.getinfo&api_key=" + this.appKEY + "&artist="
                + nomArtiste + "&album=" + nomAlbum + "&format=json";
        String jsonResponse = request(url);

       
        // Met dans la liste nomTitre le titre de chaques musique de l'album et
        // l'affiche
        ArrayList<Object> res = new ArrayList<>();
        ArrayList<String> nomTitre = new ArrayList<>();
        JSONObject json = new JSONObject(jsonResponse);
        JSONArray jsonArray = json.getJSONObject("album").getJSONObject("tracks").getJSONArray("track");
        int nbrPiste = 0;
        int duration = 0;
        for (int i = 0; i < jsonArray.length(); i++) {
            
        	nbrPiste = i;
        	
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            nomTitre.add(jsonObject.getString("name"));
            duration = duration + jsonObject.optInt("duration",0);

        }
        
        res.add(nbrPiste);
        res.add(duration);
        for (int i = 0; i < nomTitre.size(); i++) {
        	res.add(nomTitre.get(i));
		}
        return res;
    }
    
    //Rechercher un artiste à partir d'un album
    public String ArtisteNom(String nomAlbum) {   

        String url = "http://ws.audioscrobbler.com/2.0/?method=album.search&album="+nomAlbum+"&api_key=" + this.appKEY + "&format=json";
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
        String result="voici les artistes ayant un album du nom "+nomAlbum+": \n";
        for (int index = 0; index < nomsArtiste.size(); index++) {
        	result = result+nomsArtiste.get(index)+"\n";
            
           
        }
        return result;
        
        

    }
    
    //recherche les artistes similaire d'un artiste :
    public String ArtristeSimilaire(String nomArtiste) {       
        String url = "http://ws.audioscrobbler.com/2.0/?method=artist.getsimilar&artist="+nomArtiste+"&api_key="+this.appKEY+"&format=json";        
        String jsonResponse = request(url);
        
        ArrayList<String> nomsArtiste = new ArrayList<>();
        
        JSONObject json = new JSONObject(jsonResponse);
        JSONArray jsonArray = json.getJSONObject("similarartists").getJSONArray("artist");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObjectnbr = jsonArray.getJSONObject(i);
            nomsArtiste.add(jsonObjectnbr.getString("name"));

            
        }
        HashSet<String> supDouble = new HashSet<>(nomsArtiste);
        nomsArtiste = new ArrayList<>(supDouble);
        String result="voici les artistes similaires à "+nomArtiste+": \n";
        for (int index = 0; index < nomsArtiste.size(); index++) {
        	result = result+nomsArtiste.get(index)+"\n";
            
          
        }
   
        return result;
        
        

    }
  //recherche le albums d'un artiste :
    public String TopAlbum(String nomArtiste) {       
        String url ="http://ws.audioscrobbler.com/2.0/?method=artist.gettopalbums&artist="+nomArtiste+"&api_key="+this.appKEY+"&format=json ";
        String jsonResponse = request(url);
        
        ArrayList<String> nomsArtiste = new ArrayList<>();
        
        JSONObject json = new JSONObject(jsonResponse);
        JSONArray jsonArray = json.getJSONObject("topalbums").getJSONArray("album");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObjectnbr = jsonArray.getJSONObject(i);
            nomsArtiste.add(jsonObjectnbr.getString("name"));

            
        }
        HashSet<String> supDouble = new HashSet<>(nomsArtiste);
        nomsArtiste = new ArrayList<>(supDouble);
        String result="voici les albums de "+nomArtiste+": \n";
        for (int index = 0; index < nomsArtiste.size(); index++) {
        	result = result+nomsArtiste.get(index)+"\n";
            
          
        }
       
        return result;
        
        

    }
    //Retourne le top 50 d'un pay
    public String Top50pays(String nomPays) {       
        String url ="http://ws.audioscrobbler.com/2.0/?method=geo.gettoptracks&country="+nomPays+"&api_key="+this.appKEY+"&format=json";
        String jsonResponse = request(url);
        
        ArrayList<String> nomsMusiques = new ArrayList<>();
        
        JSONObject json = new JSONObject(jsonResponse);
        JSONArray jsonArray = json.getJSONObject("tracks").getJSONArray("track");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObjectnbr = jsonArray.getJSONObject(i);
            nomsMusiques.add(jsonObjectnbr.getString("name"));

            
        }
        HashSet<String> supDouble = new HashSet<>(nomsMusiques);
        nomsMusiques = new ArrayList<>(supDouble);
        String result="voici le top 50 pour le pays séléctioné : ("+nomPays+") \n";
        for (int index = 0; index < nomsMusiques.size(); index++) {
        	result = result+nomsMusiques.get(index)+"\n";
            
          
        }
       
        return result;
        
        

    }
    

}
