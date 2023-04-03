package exemple.view.service;

import exemple.data.Personne;
import exemple.data.Service;
import exemple.view.ManagerGui;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import jfox.javafx.util.UtilFX;
import jfox.javafx.util.converter.ConverterInteger;
import jfox.javafx.view.ControllerAbstract;


public class ViewServiceForm extends ControllerAbstract {

	
	//-------
	// Composants de la vue
	//-------
	
	@FXML
	private Label			labId;
	@FXML
	private TextField		txfNom;
	@FXML
	private TextField		txfAnneeCreation;
	@FXML
	private CheckBox		ckbSiege;
	@FXML
	private ComboBox<Personne>	cmbPersonne;
	@FXML
	private Button			btnValider;


	//-------
	// Autres champs
	//-------
	
	@Inject
	private ManagerGui			managerGui;
	@Inject
	private ModelService		modelService;


	//-------
	// Initialisations
	//-------

	@FXML
	private void initialize() {
		
		Service courant = modelService.getDraft();

		// Identifiant
		bind( labId,  courant.idProperty(), new ConverterInteger() );
		
		// Nom
		bindBidirectional( txfNom, courant.nomProperty()  );
		validator.addRuleNotBlank( txfNom );
		validator.addRuleMinLength( txfNom, 3 );
		validator.addRuleMaxLength( txfNom, 50 );
		
		// Année de création
		bindBidirectional( txfAnneeCreation, courant.anneeCreationProperty(), new ConverterInteger("0000") );
		validator.addRuleMinValue( txfAnneeCreation, 2000 );
		validator.addRuleMaxValue( txfAnneeCreation, 2099 );
		
		// Siège
		bindBidirectional( ckbSiege, courant.flagSiegeProperty() );
		
		// Responsable		
		cmbPersonne.setItems( modelService.getListePersonnes() );
		bindBidirectional( cmbPersonne, courant.personneProperty() );
		UtilFX.setCellFactory( cmbPersonne, item -> item.getNom() + " " +item.getPrenom() );
		
		// Bouton VAlider
		btnValider.disableProperty().bind( validator.invalidProperty() );
	}
	
	
	@Override
	public void refresh() {
		txfNom.requestFocus();
	}
	
	
	//-------
	// Actions
	//-------
	
	@FXML
	private void doAnnuler() {
		managerGui.showView( ViewServiceList.class );
	}
	
	@FXML
	private void doValider() {
		modelService.saveDraft();
		managerGui.showView( ViewServiceList.class );
	}

	@FXML
	private void doPersonneSupprimer() {
		cmbPersonne.setValue( null );
	}
}
