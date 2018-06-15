package de.hawhh.informatik.sml.kino.werkzeuge.barzahlung;

import javafx.event.EventHandler;

import java.util.Optional;

import de.hawhh.informatik.sml.kino.fachwerte.Geldbetrag;
import de.hawhh.informatik.sml.kino.werkzeuge.ObservableSubwerkzeug;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class BarzahlungsWerkzeug extends ObservableSubwerkzeug {

	private BarzahlungsWerkzeugUI _ui;
//	TODO: _preisZuZahlen als Geldbetrag zu initialisieren
	private Geldbetrag _preisZuZahlen;
	private boolean _ausreichenderBetrag;
	

//	public BarzahlungsWerkzeug(int preis, String titel, String kinosaal) {
//		_preisZuZahlen = preis;
//		_ui = new BarzahlungsWerkzeugUI(preis, titel, datum);
//		_ui = new BarzahlungsWerkzeugUI(preis,titel,kinosaal);
//	}
//	
	public BarzahlungsWerkzeug()
	{
		_ui = new BarzahlungsWerkzeugUI();
	
	}
	
//	public void startUp()
	public boolean fuehreBezahlungDurch()
		{
			_ui.getPreisLabel().setText("Preis: " + _preisZuZahlen.toBetragsstring() + "€");
			_ausreichenderBetrag = false;
			registriereUIAktionen();
			_ui.zeigeAn();
			return istGueltig();
		}

	public void registriereUIAktionen() {
		_ui.getAbbrechenButton().setOnAction(
				event -> {
					
					_ui.showConfirm();
					
//				new EventHandler<ActionEvent>() {
//			@Override
//			public void handle(ActionEvent ae) {
//				_ui.schliesseFenster();
//			}
		});
		_ui.getOKButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent ae) {
				
				if(_ausreichenderBetrag)
				{
				_ui.showEnd();
				}
				else
				{
					_ui.showNotEnough();
				}
			}
		});
		// TODO: Eingabe als Fachwerttyp und die Berechnungen mit Fachwerttyp durchführen
		_ui.getBezahlEingabe().setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode() == KeyCode.ENTER) {
					
					Geldbetrag eingabeBetrag = Geldbetrag.strconv(_ui.getBezahlEingabe().getText());
					_ausreichenderBetrag = (_preisZuZahlen.kleinerGleich(eingabeBetrag));
					Geldbetrag differenz = eingabeBetrag.sub(_preisZuZahlen);
					
					
					String output = "";
					if(_ui.getBezahlEingabe().getText().contains("-"))
					{
						Alert negBetrag = new Alert(AlertType.WARNING);
						negBetrag.setTitle("Warning");
						negBetrag.setContentText("Bitte nur positive Beträge eingeben");
						Optional<ButtonType> result = negBetrag.showAndWait();
						if(result.get() == ButtonType.OK)
						{
							negBetrag.close();
						}
						
					}
					
					
					else if (differenz.eurocent() > 0) {
						output = (differenz.toBetragsstring()  + "€");
					}
					else if (differenz.eurocent() < 0)
					{
						output = ("-" + differenz.toBetragsstring() + "€");
					}
					else
					{
						output = "0 €";
					}
					_ui.getRueckGeldTextFeld().setText(output);
					_ui.getBezahlEingabe().setText("");
				}
			}
		});
	}
	
	public int getPreis()
	{
		return _preisZuZahlen.eurocent();
	}
	
	public void setPreis(Geldbetrag preis)
	{
		this._preisZuZahlen = preis;
	}
	public boolean istGueltig()
	{
		return _ui.istGueltig();
	}
}
