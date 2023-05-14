package fr.ul.miage.structu.applastfm;

import java.text.ParseException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws ParseException {
        RequestManager rm = new RequestManager();
        ArrayList<Object> tmp = rm.getTrack("Coloris", "Bekar");
        // rm.getTrack("Business", "Eminem");
        // rm.getAlbumMusicInfo("Enna","PLK");
        // rm.getInfoArtiste("Nekfeu");

        // typeDeRecherche doit être égale à Tracks, album ou artist
        // rm.ecouteSup(8000000, "artist", "inferieur");
        // rm.expressOpinionOnTag("dkfnsdfksl", 0, "Oui");
        // rm.getInfoArtiste("Kaaris");
        // ArrayList<String> list = rm.searchSimilarMusicBetween2Artist("Zamdane",
        // "Bekar");
    }
}
