package fr.ul.miage.structu.applastfm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class AppLastFMController {

	@FXML
	Button choix;

	@FXML
	TextArea reponseAPI;

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
	Text erreur;

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
	public void initialize() {
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
		erreur.setVisible(false);
		reponseAPI.setText("");
		reponseAPI.setWrapText(true);
		RequestManager rm = new RequestManager();
		ArrayList<CheckBox> listCheckBox = new ArrayList();
		listCheckBox.add(choixTag);
		listCheckBox.add(choixAlbum);
		listCheckBox.add(choixAlbumArtiste);
		listCheckBox.add(infoArtiste);
		listCheckBox.add(choixTop50Pays);

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
//					ArrayList<String> resRequet = rm.getTagMusicInfo(nomTagIHM.getText());
//
//					 if (choixResumTag.isSelected() && !choixInteTag.isSelected()) {
//					 reponseAPI.setText(resRequet.get(0));
//					 } else if (choixInteTag.isSelected() && !choixResumTag.isSelected()) {
//					 reponseAPI.setText(resRequet.get(1));
//					 } else {
//					 reponseAPI.setText(resRequet.get(0) + "\n\n\n" + resRequet.get(1));
//					 }

					break;
				case 1:
					reponseAPI.setText(rm.ArtisteNom(nomAlbum1.getText()));
					break;
				case 2:
					// ArrayList<Object> resRequest2 = rm.getAlbumMusicInfo(nomAlbum2.getText(),
					// nomArtiste1.getText());
					// // au 1er element de la liste se trouve le nbombre de piste
					// // au 2eme element de la liste se trouve la durée en seconde de l'album
					// // du 3eme element jusqu'a la fin de la liste les elements sont les noms des
					// // pistes de l'album

					// String affichage = "l'album " + nomAlbum2.getText() + " de " +
					// nomArtiste1.getText()
					// + " contient au totale "
					// + resRequest2.get(0) + " morceaux. Sa durée totale est de " +
					// resRequest2.get(1)
					// + " secondes. \n"
					// + "voici les noms des musiques qui composent l'album : \n";
					// for (int i = 2; i < resRequest2.size(); i++) {
					// affichage = affichage + resRequest2.get(i) + "\n";
					// }

					// reponseAPI.setText(affichage);

					break;
				case 3:
					// if (ArtisteSimilaire.isSelected() && !topAlbum.isSelected()) {
					// reponseAPI.setText(rm.ArtristeSimilaire(nomArtiste2.getText()));
					// } else if (ArtisteSimilaire.isSelected() && !topAlbum.isSelected()) {
					// reponseAPI.setText(rm.TopAlbum(nomArtiste2.getText()));
					// } else {
					// reponseAPI.setText(rm.ArtristeSimilaire(nomArtiste2.getText()) + "\n\n\n"
					// + rm.TopAlbum(nomArtiste2.getText()));
					// }
					break;
				case 4:

					// reponseAPI.setText(rm.Top50pays(nomPays.getValue().toString()));
					// break;

			}

		}

	}
}
