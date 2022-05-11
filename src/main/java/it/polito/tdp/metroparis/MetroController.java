/**
 * Sample Skeleton for 'Metro.fxml' Controller Class
 */

package it.polito.tdp.metroparis;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.metroparis.model.Fermata;
import it.polito.tdp.metroparis.model.Model;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

public class MetroController {
	Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="cmbArrivo"
    private ComboBox<Fermata> cmbArrivo; // Value injected by FXMLLoader

    @FXML // fx:id="cmbPartenza"
    private ComboBox<Fermata> cmbPartenza; // Value injected by FXMLLoader
    
    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    @FXML
    private TableColumn<Fermata, String> colFermata;

    @FXML
    private TableView<Fermata> tblPercorso;
    
    @FXML
    void handleCerca(ActionEvent event) {
    	Fermata partenza = cmbPartenza.getValue();
    	Fermata arrivo = cmbArrivo.getValue();
    	
    	if(partenza!=null && arrivo!=null && !partenza.equals(arrivo)) {
    		List<Fermata> percorso = model.calcolaPercorso(partenza, arrivo);
    		
    		tblPercorso.setItems(FXCollections.observableArrayList(percorso));
    		
    		txtResult.setText("Percorso trovato con tot stazioni: "+percorso.size());
    	} else {
    		txtResult.setText("Inserisci due stazioni diverse");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert cmbArrivo != null : "fx:id=\"cmbArrivo\" was not injected: check your FXML file 'Metro.fxml'.";
        assert cmbPartenza != null : "fx:id=\"cmbPartenza\" was not injected: check your FXML file 'Metro.fxml'.";
        
        //Istruiamo la colonna in modo che estragga la stringa corretta da visualizzare
        colFermata.setCellValueFactory(new PropertyValueFactory<Fermata,String>("nome"));
        
    }

	public void setModel(Model model) {
		this.model = model;
		List<Fermata> fermate = model.getAllFermate();
	
		cmbPartenza.getItems().addAll(fermate);
		cmbArrivo.getItems().addAll(fermate);
		
	}
 
}
