package de.hawhh.informatik.sml.kino.werkzeuge.platzverkauf;

import java.util.Set;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import de.hawhh.informatik.sml.kino.fachwerte.Geldbetrag;
import de.hawhh.informatik.sml.kino.fachwerte.Platz;
import de.hawhh.informatik.sml.kino.materialien.Kinosaal;
import de.hawhh.informatik.sml.kino.materialien.Vorstellung;
import de.hawhh.informatik.sml.kino.werkzeuge.barzahlung.BarzahlungsWerkzeug;

/**
 * Mit diesem Werkzeug können Plätze verkauft und storniert werden. Es arbeitet
 * auf einer Vorstellung als Material. Mit ihm kann angezeigt werden, welche
 * Plätze schon verkauft und welche noch frei sind.
 * 
 * Dieses Werkzeug ist ein eingebettetes Subwerkzeug. Es kann nicht beobachtet
 * werden.
 * 
 * @author SE2-Team (Uni HH), PM2-Team
 * @version SoSe 2018
 */
public class PlatzVerkaufsWerkzeug {
	// TODO: _preisFuerAuswahl als Geldbetrag initialisieren
	private Geldbetrag _preisFuerAuswahl;
	// Die aktuelle Vorstellung, deren Plätze angezeigt werden. Kann null sein.
	private Vorstellung _vorstellung;

	private PlatzVerkaufsWerkzeugUI _ui;

	private BarzahlungsWerkzeug _barzahlung;

	/**
	 * Initialisiert das PlatzVerkaufsWerkzeug.
	 */
	public PlatzVerkaufsWerkzeug() {
		_ui = new PlatzVerkaufsWerkzeugUI();
		registriereUIAktionen();
		// Am Anfang wird keine Vorstellung angezeigt:
		setVorstellung(null);

	}

	/**
	 * Gibt das Panel dieses Subwerkzeugs zurück. Das Panel sollte von einem
	 * Kontextwerkzeug eingebettet werden.
	 * 
	 * @ensure result != null
	 */
	public Pane getUIPane() {
		return _ui.getUIPane();
	}

	/**
	 * Fügt der UI die Funktionalität hinzu mit entsprechenden Listenern.
	 */
	private void registriereUIAktionen() {

		_ui.getVerkaufenButton().setOnAction(event -> {
			// new EventHandler<ActionEvent>()
			// {
			// @Override
			// public void handle(ActionEvent ae)
			// {
			_barzahlung = new BarzahlungsWerkzeug();
			_barzahlung.setPreis(_preisFuerAuswahl);
			boolean result = _barzahlung.fuehreBezahlungDurch();

			// System.out.println("Ist gültig: " + _barzahlung.istGueltig());

			if (!result) {
				stornierePlaetze(_vorstellung);
			} else {
				verkaufePlaetze(_vorstellung);
			}
			// }
		});

		_ui.getStornierenButton().setOnAction(event -> {
			// new EventHandler<ActionEvent>()
			// {
			// @Override
			// public void handle(ActionEvent ae)
			// {
			stornierePlaetze(_vorstellung);
			// }
		});
	
		_ui.getPlatzplan().addPlatzSelectionListener(new PlatzSelectionListener() {
			@Override
			public void auswahlGeaendert(PlatzSelectionEvent event) {
				reagiereAufNeuePlatzAuswahl(event.getAusgewaehltePlaetze());
				
			}
		});
	}

	/**
	 * Reagiert darauf, dass sich die Menge der ausgewählten Plätze geändert hat.
	 * 
	 * @param plaetze
	 *            die jetzt ausgewählten Plätze.
	 */
	private void reagiereAufNeuePlatzAuswahl(Set<Platz> plaetze) {	
		_ui.getVerkaufenButton().setDisable(!istVerkaufenMoeglich(plaetze));
		
		_ui.getStornierenButton().setDisable(!istStornierenMoeglich(plaetze));
		if(_vorstellung!=null)
		{
			_vorstellung.reservierePlaetze(plaetze);
		}
		aktualisierePreisanzeige(plaetze);
	}

	/**
	 * Aktualisiert den anzuzeigenden Gesamtpreis
	 */
	private void aktualisierePreisanzeige(Set<Platz> plaetze) {

		if (istVerkaufenMoeglich(plaetze)) {
			// TODO: Der Preis mit Geldbetrag als Typ
			Geldbetrag preis = Geldbetrag.intconv(_vorstellung.getPreisFuerPlaetze(plaetze));
			_ui.getPreisLabel().setText("Gesamtpreis: " + preis + " €");
			_preisFuerAuswahl = preis;
		} else {
			_ui.getPreisLabel().setText("Gesamtpreis:");
		}
	}

	/**
	 * Prüft, ob die angegebenen Plätze alle storniert werden können.
	 */
	private boolean istStornierenMoeglich(Set<Platz> plaetze) {
		return !plaetze.isEmpty() && _vorstellung.sindStornierbar(plaetze);
	}

	/**
	 * Prüft, ob die angegebenen Plätze alle verkauft werden können.
	 */
	private boolean istVerkaufenMoeglich(Set<Platz> plaetze) {
		boolean ergebnis = !plaetze.isEmpty() && _vorstellung.sindVerkaufbar(plaetze);
		
		return ergebnis;
	}
	
//	private boolean istReservierenMoeglich(Set<Platz> plaetze)
//	{
//		return !plaetze.isEmpty() && _vorstellung.sindReservierbar(plaetze);
//	}
	
//	private boolean istUnreservierenMoeglich(Set<Platz> plaetze)
//	{
//		return !plaetze.isEmpty() && _vorstellung.sindUnreservierbar(plaetze);
//	}

	/**
	 * Setzt die Vorstellung. Sie ist das Material dieses Werkzeugs. Wenn die
	 * Vorstellung gesetzt wird, muss die Anzeige aktualisiert werden. Die
	 * Vorstellung darf auch null sein.
	 */
	public void setVorstellung(Vorstellung vorstellung) {
		_vorstellung = vorstellung;
		aktualisierePlatzplan();
	}

	/**
	 * Aktualisiert den Platzplan basierend auf der ausgwählten Vorstellung.
	 */
	private void aktualisierePlatzplan() {
		if (_vorstellung != null) {
			Kinosaal saal = _vorstellung.getKinosaal();
			_ui.getPlatzplan().setAnzahlPlaetze(saal.getAnzahlReihen(), saal.getAnzahlSitzeProReihe());

			for (Platz platz : saal.getPlaetze()) {
				if (_vorstellung.istPlatzVerkauft(platz)) {
					_ui.getPlatzplan().markierePlatzAlsVerkauft(platz);
				}
				else if(_vorstellung.istPlatzReserviert(platz)) {
					_ui.getPlatzplan().markierePlatzsAlsReserviert(platz);
				}
				
			}
		} else {
			_ui.getPlatzplan().setAnzahlPlaetze(0, 0);
		}
	}

	/**
	 * Verkauft die ausgewählten Plaetze.
	 */
	private void verkaufePlaetze(Vorstellung vorstellung) {
		Set<Platz> plaetze = _ui.getPlatzplan().getAusgewaehltePlaetze();
		vorstellung.verkaufePlaetze(plaetze);

		// _barzahlung = new BarzahlungsWerkzeug(_preisFuerAuswahl/100,
		// _vorstellung.getFilm().getTitel(), _vorstellung.getKinosaal().toString());
		// _barzahlung.registriereUIAktionen();
		// _barzahlung.startUp();
		aktualisierePlatzplan();
	}

	/**
	 * Storniert die ausgewählten Plaetze.
	 */
	private void stornierePlaetze(Vorstellung vorstellung) {
		Set<Platz> plaetze = _ui.getPlatzplan().getAusgewaehltePlaetze();
		vorstellung.stornierePlaetze(plaetze);
		aktualisierePlatzplan();
	}

	
}
