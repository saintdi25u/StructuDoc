package fr.ul.miage.structu.applastfm;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        RequestManager rm = new RequestManager();
        rm.getTrack("Business","Eminem") ;
        //rm.getAlbumMusicInfo("Enna","PLK");
        //rm.getInfoArtiste("Nekfeu");
        
        // typeDeRecherche doit être égale à Tracks, album ou artist
        //rm.ecouteSup(8000000, "artist", "inferieur");
    }
}
