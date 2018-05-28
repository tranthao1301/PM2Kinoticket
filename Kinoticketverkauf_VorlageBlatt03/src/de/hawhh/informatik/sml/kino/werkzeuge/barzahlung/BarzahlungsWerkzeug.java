package de.hawhh.informatik.sml.kino.werkzeuge.barzahlung;

import javafx.event.EventHandler;

import java.util.Optional;

import de.hawhh.informatik.sml.kino.werkzeuge.ObservableSubwerkzeug;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class BarzahlungsWerkzeug extends ObservableSubwerkzeug {

	private BarzahlungsWerkzeugUI _ui;
//	private int _preisZuZahlen = -1;
	private int _preisZuZahlen;
	

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
			_ui.getPreisLabel().setText("Preis: " + _preisZuZahlen + "Eurocent");
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
				int bezahlt = Integer.parseInt(_ui.getBezahlEingabe().getText());
				if(_preisZuZahlen <= bezahlt)
				{
				_ui.showEnd();
				}
				else
				{
					_ui.showNotEnough();
				}
			}
		});
		_ui.getBezahlEingabe().setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode() == KeyCode.ENTER) {
					int bezahlt = Integer.parseInt(_ui.getBezahlEingabe().getText());
					int difference = bezahlt - _preisZuZahlen;
					
					String output = "";
					if(bezahlt < 0)
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
					
					else if (difference > 0) {
						output = (difference  + "Eurocent");
					}
					else if (difference < 0)
					{
						output = (difference + "Eurocent");
					}
					else
					{
						output = "0 Eurocent";
					}
					_ui.getRueckGeldTextFeld().setText(output);
				}
			}
		});
	}
	
	public int getPreis()
	{
		return _preisZuZahlen;
	}
	
	public void setPreis(int preis)
	{
		this._preisZuZahlen = preis;
	}
	public boolean istGueltig()
	{
		return _ui.istGueltig();
	}
}
