package exemple.view.personne;

import java.util.ArrayList;
import java.util.HashMap;

import exemple.data.Categorie;
import exemple.report.EnumReport;
import exemple.report.ManagerReport;
import exemple.view.ManagerGui;
import exemple.view.categorie.ModelCategorie;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.ControllerAbstract;


public class ViewEtatParCategorie extends ControllerAbstract {
	
	//-------
	// Composants de la vue
	//-------

	@FXML
	private ListView<Categorie>	lsvCategories;
	@FXML
	private Button				btnEtat1;


	//-------
	// Autres champs
	//-------
	
	@Inject
	private ManagerGui			managerGui;
	@Inject
	private ModelCategorie		modelCategorie;
	@Inject
	private ManagerReport		managerReport;
	
	
	//-------
	// Initialisation du Controller
	//-------

	@FXML
	private void initialize() {

		// ListView
		lsvCategories.setItems( modelCategorie.getList() );
		UtilFX.setCellFactory( lsvCategories, "libelle" );
		bindBidirectional( lsvCategories, modelCategorie.currentProperty(), modelCategorie.flagRefreshingListProperty() );
		
		lsvCategories.getSelectionModel().setSelectionMode( SelectionMode.MULTIPLE );
		
		// Configuraiton des boutons
		lsvCategories.getSelectionModel().selectedItemProperty().addListener(obs -> {
			configurerBoutons();
		});
		configurerBoutons();
		
	}
	
	@Override
	public void refresh() {
		modelCategorie.refreshList();
		lsvCategories.requestFocus();
	}

	
	//-------
	// Actions
	//-------
	
	@FXML
	private void doEtat1() {
		var params = new HashMap<String,Object>();
		params.put( "idCategorie", 
				lsvCategories.getSelectionModel().getSelectedItem().getId() );
		managerReport.showViewer( EnumReport.PersonneParCategorie1, params);
	}
	
	@FXML
	private void doEtat2() {
		var params = new HashMap<String,Object>();
		var idCategories = new ArrayList<Integer>();
		for ( var item : lsvCategories.getSelectionModel().getSelectedItems() ) {
			idCategories.add( item.getId() );
		}
		params.put( "idCategories", idCategories );
		managerReport.showViewer( EnumReport.PersonneParCategorie2, params );
	}	
	
	//-------
	// Gestion des évènements
	//-------

	// Clic sur la liste
	@FXML
	private void gererClicSurListe( MouseEvent event ) {
		if (event.getButton().equals(MouseButton.PRIMARY)) {
			if (event.getClickCount() == 2) {
				if ( lsvCategories.getSelectionModel().getSelectedIndex() == -1 ) {
					managerGui.showDialogError( "Aucun élément n'est sélectionné dans la liste.");
				} else {
					doEtat1();
				}
			}
		}
	}


	//-------
	// Méthodes auxiliaires
	//-------
	
	private void configurerBoutons() {
		var flagDisable = lsvCategories.getSelectionModel().getSelectedItem() == null;
		btnEtat1.setDisable(flagDisable);
	}

}
