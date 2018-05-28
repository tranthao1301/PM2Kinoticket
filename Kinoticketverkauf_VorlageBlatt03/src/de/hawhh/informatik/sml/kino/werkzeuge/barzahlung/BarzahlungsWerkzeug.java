package de.hawhh.informatik.sml.kino.werkzeuge.barzahlung;

import javafx.event.EventHandler;


import javafx.event.ActionEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class BarzahlungsWerkzeug {

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
	
	public void startUp()
		{
			_ui.getPreisLabel().setText("Preis: " + _preisZuZahlen + "Eurocent");
			this.registriereUIAktionen();
			_ui.zeigeAn();
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
				_ui.schliesseFenster();
			}
		});
		_ui.getBezahlEingabe().setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode() == KeyCode.ENTER) {
					int bezahlt = Integer.parseInt(_ui.getBezahlEingabe().getText());
					double difference = bezahlt - _preisZuZahlen;

					System.out.println();
					String output = "";
					if (difference > 0) {
						output = (difference  + "€");
					}
					else if (difference < 0)
					{
						output = (difference + "€");
					}
					else
					{
						output = "0";
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
