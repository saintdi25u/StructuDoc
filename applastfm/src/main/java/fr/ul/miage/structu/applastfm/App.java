package fr.ul.miage.structu.applastfm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws JsonMappingException, JsonProcessingException {
        RequestManager rm = new RequestManager();
        //rm.getTagMusicInfo("disco");
        //rm.getAlbumMusicInfo("Recovery", "Eminem");
        ConnexionIHM.main(args);
    	//rm.ArtisteNom("Cosmopolitanie");
        //rm.ArtristeSimilaire(null);

    }
    // API-KEY : dcfdc8018e4fab59a9015539adc6b50e
} 
