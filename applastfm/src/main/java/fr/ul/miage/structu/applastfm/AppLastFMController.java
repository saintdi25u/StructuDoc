package fr.ul.miage.structu.applastfm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class AppLastFMController {

	@FXML
	Button choix;

	@FXML
	TextArea reponseAPI;
	@FXML
	TextArea HBalbums;
	@FXML
	TextArea HBTracks;
	@FXML
	TextArea HBListTAG;
	@FXML
	TextArea HBSimilar;
	
	@FXML
	TextArea Top10S;
	@FXML
	TextArea top10t;
	@FXML
	TextArea top10a;
	@FXML
	TextArea infoTop10;
	@FXML
	TextArea Tag1;
	
	
	
	
	
	@FXML
	TextField nomTagIHM;
	@FXML
	TextField nomAlbum1;
	@FXML
	TextField nomAlbum2;
	@FXML
	TextField nomArtiste1;
	@FXML
	TextField nomArtiste2;
	@FXML
	TextField nomChoixTrack;
	@FXML
	TextField nomChoixArtist;
	@FXML
	TextField seuil;
	
	@FXML
	TextField nomTCom;
	@FXML
	TextField NoteCom;
	@FXML
	TextField ContenueCom;
	@FXML
	TextField nomARCom;
	@FXML
	TextField nomALCom;
	@FXML
	TextField nomTagCom;
	
	@FXML
	TextField nomTCom1;
	@FXML
	TextField nomARCom1;
	@FXML
	TextField nomALCom1;
	@FXML
	TextField nomTagCom1;
	
	@FXML
	Text erreur;
	@FXML
	Text TagSelectNom;
	@FXML
	Text TagSelectNom1;
	@FXML
	Text typeRequette;
	@FXML
	Text infoAlbumNomArtiste;
	@FXML
	Text infoAlbumNomAlbum;
	@FXML
	Text repNomArtiste;	
	@FXML
	Text playcountA;
	@FXML
	Text playcountA1;
	@FXML
	Text albumA;
	@FXML
	Text TracksA;
	@FXML
	Text TagsA;
	@FXML
	Text simA;	
	@FXML
	Text Top10songA;
	@FXML
	Text Top10TagA;
	@FXML
	Text Top10ArtistA;
	@FXML
	Text InfoA;	
	@FXML
	Text Tracks2;
	@FXML
	Text Tracks1;
	@FXML
	Text Listeners;
	@FXML
	Text Listeners1;
	@FXML
	Text Tag;
	@FXML
	Text ResCom;
	
	
	
	

	@FXML
	ComboBox nomPays;

	// CB principal
	@FXML
	CheckBox choixTag;
	@FXML
	CheckBox choixAlbum;
	@FXML
	CheckBox choixAlbumArtiste;
	@FXML
	CheckBox infoArtiste;
	@FXML
	CheckBox choixTop50Pays;
	@FXML
	CheckBox choixTracks;
	@FXML
	CheckBox choixsSeuil;
	@FXML
	CheckBox choixCom;
	@FXML
	CheckBox choixCom1;
	

	// CB secondaire
	@FXML
	CheckBox choixResumTag;
	@FXML
	CheckBox choixInteTag;
	@FXML
	CheckBox ArtisteSimilaire;
	@FXML
	CheckBox topAlbum;
	@FXML
	CheckBox choixTop;
	@FXML
	CheckBox seuilTrack;
	@FXML
	CheckBox seuilArtist;
	@FXML
	CheckBox seuilAlbum;
	@FXML
	CheckBox seuilSup;
	@FXML
	CheckBox seuilInf;
	@FXML
	CheckBox comTrack;
	@FXML
	CheckBox comArtist;
	@FXML
	CheckBox comAlbum;
	@FXML
	CheckBox comTag;
	@FXML
	CheckBox comTrack1;
	@FXML
	CheckBox comArtist1;
	@FXML
	CheckBox comAlbum1;
	@FXML
	CheckBox comTag1;
	
	
	
	@FXML
	HBox InfoArtisteHbox;
	@FXML
	HBox TopHbox;

	@FXML
	public void initialize() {		
		ResCom.setVisible(false);
		typeRequette.setVisible(false);
		TagSelectNom.setVisible(false);
		TagSelectNom1.setVisible(false);
		infoAlbumNomAlbum.setVisible(false);
		infoAlbumNomArtiste.setVisible(false);
		repNomArtiste.setVisible(false);
		InfoArtisteHbox.setVisible(false);
		playcountA.setVisible(false);
		playcountA1.setVisible(false);
		albumA.setVisible(false);
		TracksA.setVisible(false);
		TagsA.setVisible(false);
		simA.setVisible(false);
		TopHbox.setVisible(false);
		Top10songA.setVisible(false);
		Top10TagA.setVisible(false);
		Top10ArtistA.setVisible(false);
		InfoA.setVisible(false);
		
		Tracks2.setVisible(false);
		Tracks1.setVisible(false);
		Listeners.setVisible(false);
		Listeners1.setVisible(false);
		Tag.setVisible(false);
		Tag1.setVisible(false);
		

		reponseAPI.setVisible(false);
		List<String> nomsPays = Arrays.asList("United States", "China", "Japan", "Germany", "United Kingdom", "India",
				"France", "Italy", "Canada", "South Korea", "Russia", "Australia", "Spain", "Brazil", "Netherlands",
				"Switzerland", "Mexico", "Indonesia", "Turkey", "Saudi Arabia", "Taiwan", "Iran", "Poland", "Sweden",
				"Thailand", "Belgium", "Argentina", "Norway", "United Arab Emirates", "Pakistan", "Israel", "Egypt",
				"Denmark", "Malaysia", "Philippines", "Singapore", "Greece", "Finland", "Iraq", "Vietnam", "Chile",
				"South Africa", "Algeria", "Colombia", "Nigeria", "Austria", "Ukraine", "Romania", "Czech Republic",
				"Portugal", "Peru", "Morocco", "Venezuela", "Serbia", "Hungary", "Slovakia", "New Zealand", "Ecuador",
				"Oman", "Belarus", "Bulgaria", "Azerbaijan", "Dominican Republic", "Tunisia", "Kazakhstan", "Libya",
				"Croatia", "Sudan", "Lebanon", "Uzbekistan", "Panama", "Costa Rica", "Lithuania", "Uruguay", "Slovenia",
				"Latvia", "Kenya");
		nomPays.getItems().addAll(nomsPays);

	}

	@FXML
	private void ReponseRequette() throws Exception {
		ResCom.setVisible(false);
		typeRequette.setVisible(true);
		TagSelectNom.setVisible(false);
		TagSelectNom1.setVisible(false);
		infoAlbumNomAlbum.setVisible(false);
		infoAlbumNomArtiste.setVisible(false);
		repNomArtiste.setVisible(false);
		InfoArtisteHbox.setVisible(false);
		playcountA.setVisible(false);
		playcountA1.setVisible(false);
		albumA.setVisible(false);
		TracksA.setVisible(false);
		TagsA.setVisible(false);
		simA.setVisible(false);
		TopHbox.setVisible(false);
		Top10songA.setVisible(false);
		Top10TagA.setVisible(false);
		Top10ArtistA.setVisible(false);
		InfoA.setVisible(false);
		InfoArtisteHbox.setVisible(false);
		reponseAPI.setVisible(false);
		
		Tracks2.setVisible(false);
		Tracks1.setVisible(false);
		Listeners.setVisible(false);
		Listeners1.setVisible(false);
		Tag.setVisible(false);
		Tag1.setVisible(false);

		
		erreur.setVisible(false);
		reponseAPI.setText("");
		reponseAPI.setWrapText(true);
		RequestManager rm = new RequestManager();
		ArrayList<CheckBox> listCheckBox = new ArrayList();
		listCheckBox.add(choixTag);
		listCheckBox.add(choixAlbumArtiste);
		listCheckBox.add(infoArtiste);
		listCheckBox.add(choixTop50Pays);
		listCheckBox.add(choixTracks);
		listCheckBox.add(choixsSeuil);
		listCheckBox.add(choixCom);
		listCheckBox.add(choixCom1);


		int nbrCheckBox = 0;
		int choix = 0;
		for (int i = 0; i < listCheckBox.size(); i++) {
			if (listCheckBox.get(i).isSelected()) {
				nbrCheckBox++;
				choix = i;
			}
		}
		if (nbrCheckBox < 1) {
			erreur.setText("Veuillez faire un choix");
			erreur.setVisible(true);
		} else if (nbrCheckBox > 1) {
			erreur.setText("Veuillez séléctionner qu'une seul proposition");
			erreur.setVisible(true);
		} else {

			switch (choix) {
				case 0:
					TagSelectNom.setVisible(true);
					TagSelectNom1.setVisible(true);
					reponseAPI.setVisible(true);
					ArrayList<String> resRequet = rm.getTagMusicInfo(nomTagIHM.getText());
					typeRequette.setText(resRequet.get(3));
					TagSelectNom1.setText(resRequet.get(0));
					 if (choixResumTag.isSelected() && !choixInteTag.isSelected()) {
					 reponseAPI.setText("Résumé : \n\n"+resRequet.get(1));
					 } else if (choixInteTag.isSelected() && !choixResumTag.isSelected()) {			 
					 reponseAPI.setText("Contenue : \n\n" +resRequet.get(2));
					 } else {
					 reponseAPI.setText("Résumé : \n\n"+resRequet.get(1) + "\n\n\n"+"Contenue : \n\n" + resRequet.get(2));
					 }

					break;
				case 1:
					infoAlbumNomAlbum.setVisible(true);
					infoAlbumNomArtiste.setVisible(true);
					repNomArtiste.setVisible(true);
					TagSelectNom1.setVisible(true);
					reponseAPI.setVisible(true);

					
					 ArrayList<Object> resRequest2 = rm.getAlbumMusicInfo(nomAlbum2.getText(),nomArtiste1.getText());
					 typeRequette.setText((String) resRequest2.get(5));
					 TagSelectNom1.setText((String) resRequest2.get(0));
					 repNomArtiste.setText((String) resRequest2.get(1));
					 
					 

					 String affichage = "l'album " + nomAlbum2.getText() + " de " +
					 nomArtiste1.getText()
					 + " contient au totale "
					 + resRequest2.get(2) + " morceaux. Sa durée totale est de " +
					 resRequest2.get(4)
					 + " secondes. \n" 
					 +"Son nombre d'écoute est de : "+resRequest2.get(6)+"\n"
					 + "voici les noms des musiques qui composent l'album : \n";
					 ArrayList<String> nameTracks = (ArrayList<String>) resRequest2.get(3); 
					 for (int i = 0; i < nameTracks.size(); i++) {
					 affichage = affichage + nameTracks.get(i) + "\n";
					 }

					 reponseAPI.setText(affichage);

					break;
				case 2:
					infoAlbumNomArtiste.setVisible(true);
					repNomArtiste.setVisible(true);
					InfoArtisteHbox.setVisible(true);
					
					playcountA.setVisible(true);
					playcountA1.setVisible(true);
					albumA.setVisible(true);
					TracksA.setVisible(true);
					TagsA.setVisible(true);
					simA.setVisible(true);

					ArrayList<Object> resRequest3 = rm.getInfoArtiste(nomArtiste2.getText());
					 typeRequette.setText((String) resRequest3.get(6));
					 String affichageAlbum ;
					repNomArtiste.setText((String) resRequest3.get(0));
					ArrayList<String> albums = (ArrayList<String>) resRequest3.get(1); 
					if (albums != null) {
						 affichageAlbum = "Voici la liste des albums : \n";

						for (int i = 0; i < albums.size(); i++) {
							 affichageAlbum = affichageAlbum +albums.get(i)+"\n";
						}
					}else affichageAlbum = "L'artiste n'a pas d'album référencé : \\n";

					 
					HBalbums.setText(affichageAlbum);
					
					
					String affichageTracks;
					ArrayList<String> Tracks = (ArrayList<String>) resRequest3.get(2); 
					if (Tracks != null) {
						 affichageTracks = "Voici la liste des Tracks : \n";
						for (int i = 0; i < Tracks.size(); i++) {
							 affichageTracks = affichageTracks +Tracks.get(i)+"\n";
						}
					}else affichageTracks = "L'artiste n'a pas de tracks référencé : \n";
					 HBTracks.setText(affichageTracks);
					 
					 String affichageTag; 
						ArrayList<String> Tags = (ArrayList<String>) resRequest3.get(3); 
						if (Tags!=null) {
							affichageTag = "Voici la liste des Tags : \n";
							for (int i = 0; i < Tags.size(); i++) {
								 affichageTag = affichageTag +Tags.get(i)+"\n";
							}
						}else affichageTag = "L'artiste n'a pas de tag référencé : \n";
						 
						 HBListTAG.setText(affichageTag);
						 
						 
					String affichagesSim ;
					ArrayList<String> sim = (ArrayList<String>) resRequest3.get(4); 
					if (sim !=null) {
						 affichagesSim = "Voici la liste des artistes similaires : \n";
						for (int i = 0; i < sim.size(); i++) {
							affichagesSim = affichagesSim +sim.get(i)+"\n";
						}
					}else affichagesSim = "L'artiste n'a pas de d'artistes similaire référencé  : \n";

					HBSimilar.setText(affichagesSim);	 
					playcountA1.setText(""+(Integer) resRequest3.get(5));
					 
					 
					
					
					
					 
					break;
				case 3:
					TopHbox.setVisible(true);
					Top10songA.setVisible(true);
					Top10TagA.setVisible(true);
					Top10ArtistA.setVisible(true);
					InfoA.setVisible(true);
					String choixtop;
					if (choixTop.isSelected()) {
						choixtop = "global";
					}else choixtop = "";
					
					ArrayList<Object> resRequest4;
					if (nomPays.getValue() == null) {
						 resRequest4 = rm.Top10Global("",choixtop);
					}else resRequest4 = rm.Top10Global(nomPays.getValue().toString(),choixtop);

					 typeRequette.setText((String) resRequest4.get(7));

					String affichagetops;
					ArrayList<String> topS = (ArrayList<String>) resRequest4.get(1); 
					if (topS != null) {
						 affichagetops = "Voici le top song : \n";

						for (int i = 0; i < topS.size(); i++) {
							affichagetops = affichagetops +topS.get(i)+"\n";
						}
					}else affichagetops = "Ce Top n'est pas référencé \\n";					 
					Top10S.setText(affichagetops);
					
					String affichagetopT;
					ArrayList<String> topT = (ArrayList<String>) resRequest4.get(2); 
					if (topT != null) {
						affichagetopT = "Voici le top tag : \n";

						for (int i = 0; i < topT.size(); i++) {
							affichagetopT = affichagetopT +topT.get(i)+"\n";
						}
					}else affichagetopT = "Ce Top n'est pas référencé \\n";					 
					top10t.setText(affichagetopT);
					
					String affichagetopA;
					ArrayList<String> topA = (ArrayList<String>) resRequest4.get(3); 
					if (topA != null) {
						affichagetopA = "Voici le top tag : \n";

						for (int i = 0; i < topA.size(); i++) {
							affichagetopA = affichagetopA +topA.get(i)+"\n";
						}
					}else affichagetopA = "Ce Top n'est pas référencé \\n";					 
					top10a.setText(affichagetopA);
					
					String info;
					if (choixtop == "global") {
						info = "Top global : \n\n";
					}else info = "Top " +nomPays.getValue().toString() + " : \n\n ";
					info = info +"Playcount : " + (Integer) resRequest4.get(4)+"\n";
					info = info +"Listners : " + (Integer) resRequest4.get(5)+"\n";
					info = info +"Date : " + (String) resRequest4.get(6)+"\n";
					infoTop10.setText(info);
					
					
					
					 break;
				case 4:
					Tracks2.setVisible(true);
					Tracks1.setVisible(true);
					Listeners.setVisible(true);
					Listeners1.setVisible(true);
					Tag.setVisible(true);
					Tag1.setVisible(true);
					infoAlbumNomArtiste.setVisible(true);
					repNomArtiste.setVisible(true);
					playcountA.setVisible(true);
					playcountA1.setVisible(true);
					
					ArrayList<Object> resRequest5 = rm.getTrack(nomChoixTrack.getText(),nomChoixArtist.getText());
					typeRequette.setText((String) resRequest5.get(5));
					
					Tracks1.setText((String)resRequest5.get(0) );
					repNomArtiste.setText((String)resRequest5.get(1) );
					Listeners1.setText(""+(Integer)resRequest5.get(2) );
					playcountA1.setText(""+(Integer)resRequest5.get(3) );
					
					ArrayList<String>resTag = ((ArrayList<String>) resRequest5.get(4));
					String contenue="";
					if (resTag != null) {
						for (int i = 0; i < resTag.size(); i++) {
							contenue = contenue + resTag.get(i)+"\n";
						}
					}else contenue = "Aucun Tag référencé pour ce Track";
					Tag1.setText( contenue);

					
					break;
				case 5:
					ArrayList<CheckBox> typerecherche = new ArrayList<>();					
					typerecherche.add(seuilTrack);
					typerecherche.add(seuilArtist);
					typerecherche.add(seuilAlbum);
					int nbrSelect = 0;
					int choixType1=0;
					String choixType2 = "";
					String choixSupInf;
					for (int i = 0; i < typerecherche.size(); i++) {
						if (typerecherche.get(i).isSelected()) {
							nbrSelect++;
							choixType1 = i;
						}
					}
					if (nbrSelect >1) {
						erreur.setVisible(true);
						erreur.setText("Veuillez séléctionner qu'un seuil type de recherche");

					}else if(nbrSelect == 0) {
						erreur.setVisible(true);
						erreur.setText("Veuillez faire un choix");
					}else {
						if (seuilInf.isSelected() && seuilSup.isSelected()) {
							erreur.setVisible(true);
							erreur.setText("Veuillez séléctionner qu'un seuil type de seuil");
						}else if(!seuilInf.isSelected() && !seuilSup.isSelected()){
							erreur.setVisible(true);
							erreur.setText("Veuillez séléctionner le type de seuil");
						}else {
							if(seuil.getText()=="") {
								erreur.setVisible(true);
								erreur.setText("Veuillez entrer un seuil");
							}else {
								typeRequette.setText("BDD");

								if (seuilInf.isSelected()) {
									choixSupInf = "inferieur";
								}else choixSupInf = "superieur";
								
								if (choixType1 == 0) {
									choixType2 = "Tracks"; 
								}else if (choixType1 == 1) {
									choixType2 = "artist"; 
								}else if(choixType1 == 2) {
									choixType2 = "album";
								}
									
								
								ArrayList<String> resRequest6 = rm.NbrEcouteSeuil(Integer.parseInt(seuil.getText()), choixType2,choixSupInf );
								reponseAPI.setVisible(true);
								String rep = "Voici le nom des " + choixType2 + "ayant un nombre d'écoute "+choixType2+" à "+seuil.getText()+" :\n";
								for (int i = 0; i < resRequest6.size(); i++) {
									rep = rep + resRequest6.get(i)+"\n";
								}
								reponseAPI.setText(rep);

							}
						}
							
						

						
					}

					
					
					
					break;
				case 6:
					ArrayList<CheckBox> typerechercheCom = new ArrayList<>();					
					typerechercheCom.add(comTrack);
					typerechercheCom.add(comArtist);
					typerechercheCom.add(comAlbum);
					typerechercheCom.add(comTag);
					
					
					int nbrSelectCom = 0;
					int choixType1Com=0;
					String choixSupInfCom;
					for (int i = 0; i < typerechercheCom.size(); i++) {
						if (typerechercheCom.get(i).isSelected()) {
							nbrSelectCom++;
							choixType1Com = i;
						}
					}
					if (nbrSelectCom >1) {
						erreur.setVisible(true);
						erreur.setText("Veuillez séléctionner le sujet du commentaire");

					}else if(nbrSelectCom == 0) {
						erreur.setVisible(true);
						erreur.setText("Veuillez choisir un sujet de commentaire");
					}else {
						
						if (choixType1Com == 0) {
							if (nomARCom.getText()=="") {
								erreur.setVisible(true);
								erreur.setText("Veuillez entrer le nom de l'artiste correspondant à cette Track");
							}else {
								ResCom.setVisible(true);
								ResCom.setText(rm.expressOpinionOnTrack(nomTCom.getText(),nomARCom.getText(),Integer.parseInt(NoteCom.getText()), ContenueCom.getText()));
							}
							
						}else if (choixType1Com == 1) {
							ResCom.setVisible(true);
							ResCom.setText(rm.expressOpinionOnArtist(nomARCom.getText(),Integer.parseInt(NoteCom.getText()), ContenueCom.getText()));
							 
						}else if(choixType1Com == 2) {
							if (nomARCom.getText()=="") {
								erreur.setVisible(true);
								erreur.setText("Veuillez entrer le nom de l'artiste correspondant cette album");
							}else {
								ResCom.setVisible(true);
								ResCom.setText(rm.expressOpinionOnAlbum(nomALCom.getText(),nomARCom.getText(),Integer.parseInt(NoteCom.getText()), ContenueCom.getText()));
							}
						}else if(choixType1Com == 3) {
							ResCom.setVisible(true);
							ResCom.setText(rm.expressOpinionOnTag(nomTagCom.getText(),Integer.parseInt(NoteCom.getText()), ContenueCom.getText()));
						}				
						typeRequette.setText("BDD");
						
					}
					
					
					
					break;
				case 7:
					ArrayList<CheckBox> typerechercheCom1 = new ArrayList<>();					
					typerechercheCom1.add(comTrack1);
					typerechercheCom1.add(comArtist1);
					typerechercheCom1.add(comAlbum1);
					typerechercheCom1.add(comTag1);
					
					
					int nbrSelectCom1 = 0;
					int choixType1Com1=0;					
					for (int i = 0; i < typerechercheCom1.size(); i++) {
						if (typerechercheCom1.get(i).isSelected()) {
							nbrSelectCom1++;
							choixType1Com1 = i;
						}
					}
					if (nbrSelectCom1 >1) {
						erreur.setVisible(true);
						erreur.setText("Veuillez séléctionner le sujet du commentaire");

					}else if(nbrSelectCom1 == 0) {
						erreur.setVisible(true);
						erreur.setText("Veuillez choisir un sujet de commentaire");
					}else {
						reponseAPI.setVisible(true);
						if (choixType1Com1 == 0) {
							if (nomARCom1.getText()=="") {
								erreur.setVisible(true);
								erreur.setText("Veuillez entrer le nom de l'artiste correspondant à cette Track");
							}else {
								ArrayList<String> com = rm.getComment("track",nomTCom1.getText());
								String res = "";
								for (int i = 0; i < com.size(); i++) {
									res = res+"Commentaire "+(i+1)+ ":\n" + com.get(i)+"\n";
								}
								reponseAPI.setText(res);
							}
							
						}else if (choixType1Com1 == 1) {
							ArrayList<String> com = rm.getComment("artist",nomARCom1.getText());
							String res = "";
							for (int i = 0; i < com.size(); i++) {
								res =res+ "Commentaire "+(i+1)+ ":\n" + com.get(i)+"\n";
							}
							reponseAPI.setText(res);
							 
						}else if(choixType1Com1 == 2) {
							if (nomARCom.getText()=="") {
								erreur.setVisible(true);
								erreur.setText("Veuillez entrer le nom de l'artiste correspondant cette album");
							}else {
								ArrayList<String> com = rm.getComment("album",nomALCom1.getText());
								String res = "";
								for (int i = 0; i < com.size(); i++) {
									res =res+ "Commentaire "+(i+1)+ ":\n" + com.get(i)+"\n";
								}
								reponseAPI.setText(res);
							}
						}else if(choixType1Com1 == 3) {
							ArrayList<String> com = rm.getComment("tag",nomTagCom1.getText());
							String res = "";
							for (int i = 0; i < com.size(); i++) {
								res = res+"Commentaire "+(i+1)+ ":\n" + com.get(i)+"\n";
							}
							reponseAPI.setText(res);
							
						}				
						typeRequette.setText("BDD");
						
					}
					break;

			}

		}

	}
}
