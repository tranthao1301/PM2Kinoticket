package de.hawhh.informatik.sml.kino.werkzeuge.barzahlung;

import java.util.Optional;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BarzahlungsWerkzeugUI {
	
	private static final String TITEL = "Barzahlung";
	
	private Stage _stage;
	private Button _okButton;
	private Button _abbrechenButton;
	private Text _preisZuZahlen;
	private Text _rueckGeld;
	private TextField _bezahlEingabe;
	private boolean _gueltig;

//	private int _preis = -1;
	
//	private String _titel = "";
//	private String _datum = "";
//	private String _kinosaal = "";
	
	private Label _preisLabel = new Label("Preis: ");
	private Label _difference = new Label("Rueckgeld: ");
	
	public BarzahlungsWerkzeugUI()
	{
//		_preis = preis;
//		_titel = titel;
//		_datum = datum;
//		_kinosaal = kinosaal;
		
//		start(preis, titel, datum);
		_gueltig = true;
		start();
	}
	
	
	private void start()
	{

		_stage = new Stage();	
		_stage.setTitle(TITEL);
		_bezahlEingabe = new TextField();
		_bezahlEingabe.setPromptText("Betrag eingeben!");
		_rueckGeld = new Text("");
		_okButton = new Button("OK");
		_abbrechenButton = new Button("Abbrechen");

	
		GridPane grid = new GridPane();
		grid.setHgap(15);
		grid.setVgap(15);
		grid.setPadding(new Insets(5,5,5,5));
		
		grid.add(_preisLabel, 1, 0);
		grid.add(_rueckGeld, 3, 0);
		grid.add(_okButton, 1, 2);
		grid.add(_abbrechenButton, 2, 2);
		grid.add(_bezahlEingabe, 1, 1);
		grid.add(_difference, 2, 0);
		
		Scene scene = new Scene(grid);
		_stage.initModality(Modality.WINDOW_MODAL);
		_stage.setMinHeight(250);
		_stage.setMinWidth(400);
		_stage.setScene(scene);
//		_stage.show();

	}
	
	public void showConfirm()
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Abbrechen");
		alert.setContentText("Sind Sie sicher?");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK)
		{
			_gueltig = false;
			schliesseFenster();
		}
	}
	
	public void showEnd()
	{
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Abschließen");
		alert.setContentText("Viel Spaß!");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK)
		{
			schliesseFenster();
		}
	}
	
	public void showNotEnough()
	{
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning");
		alert.setContentText("Nicht genug Geld bezahlt!");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK)
		{
			alert.close();
		}
	}
	
	
	public Button getOKButton()
	{
		return _okButton;
	}
	
	public Button getAbbrechenButton()
	{
		return _abbrechenButton;
	}
	
	public TextField getBezahlEingabe()
	{
		return _bezahlEingabe;
	}
	
	public Text getRueckGeldTextFeld()
	{
		return _rueckGeld;
	}
	
	public Text getPreis()
	{
		return _preisZuZahlen;
	}
		
	public void zeigeAn()
	{
		_stage.showAndWait();
	}
	
	public void schliesseFenster()
	{
		_stage.close();
	}
	
	public Label getPreisLabel()
	{
		return _preisLabel;
	}
	
	public boolean istGueltig()
	{
		return _gueltig;
	}
//	public Pane erstelleButtonPanel()
//	{
//		FlowPane button = new FlowPane();
//		return button;
//	}
}
