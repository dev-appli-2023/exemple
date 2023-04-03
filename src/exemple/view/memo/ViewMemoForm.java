package exemple.view.memo;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import exemple.data.Agir;
import exemple.data.Categorie;
import exemple.data.Compte;
import exemple.data.Personne;
import exemple.view.ManagerGui;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import jfox.javafx.util.UtilFX;
import jfox.javafx.util.converter.ConverterBigDecimal;
import jfox.javafx.util.converter.ConverterInteger;
import jfox.javafx.util.converter.ConverterLocalDate;
import jfox.javafx.util.converter.ConverterLocalTime;
import jfox.javafx.view.ConfigView;
import jfox.javafx.view.ControllerAbstract;
import jfox.javafx.view.Mode;


@ConfigView( flagTransient = false )
public class ViewMemoForm extends ControllerAbstract {

	
	// Composants de la vue
	
	@FXML
	private Label			labId;
	@FXML
	private TextField		txfTitre;
	@FXML
	private TextArea		txaDescription;
	@FXML
	private CheckBox		ckbUrgent;
	@FXML
	private ComboBox<Categorie> cmbCategorie;
	
	@FXML
	private ToggleGroup		tggStatut  ;
	@FXML
	private TextField		txfEffectif;
	@FXML
	private TextField		txfBudget;
	@FXML
	private DatePicker		dtpEcheance;
	@FXML
	private TextField		txfHeure;
	
	@FXML
	private ListView<Compte> lsvAbonnes;
	@FXML
	private Button			btnAbonneSupprimer;
	
	@FXML
	private TableView<Agir>	tbvActeurs;
	@FXML
	private TableColumn<Agir, Personne>	colPersonne;
	@FXML
	private TableColumn<Agir, String>	colFonction;
	@FXML
	private Button			btnActeurModifier;
	@FXML
	private Button			btnActeurSupprimer;
	
	@FXML
	private ImageView		imvImage;
	@FXML
	private Button			btnImageOuvrir;
	
	@FXML
	private Button			btnValider;


	// Autres champs
	
	@Inject
	private ManagerGui		managerGui;
	@Inject
	private ModelMemo		modelMemo;
	@Inject
	private ModelMemoActeur	modelActeur;


	// Initialisation du Controller

	@FXML
	private void initialize() {
		
		var draft = modelMemo.getDraft();

		// Id
		bind( labId, draft.idProperty(), new ConverterInteger() );
		
		// Titre
		bindBidirectional( txfTitre, draft.titreProperty()  );
		validator.addRuleNotBlank(txfTitre);
		validator.addRuleMaxLength(txfTitre, 50 );
		validator.addRuleMinLength(txfTitre, 4);

		// Description
		bindBidirectional( txaDescription, draft.descriptionProperty() );
		
		// Urgent
		bindBidirectional( ckbUrgent, draft.flagUrgentProperty() );
		
		// Catégorie
		cmbCategorie.setItems( modelMemo.getCategories() );
		bindBidirectional( cmbCategorie, draft.categorieProperty() );
		UtilFX.setCellFactory( cmbCategorie, "libelle" );
		
		// Statut
		bindBidirectional( tggStatut, draft.statutProperty(), "A", "E", "F" );
		
		// Effectif
		bindBidirectional( txfEffectif, draft.effectifProperty(), new ConverterInteger() );
		validator.addRuleMinValue(txfEffectif, 0 );
		validator.addRuleMaxValue(txfEffectif, 1000 );
		
		// Budget
		bindBidirectional( txfBudget, draft.budgetProperty(), new ConverterBigDecimal("#,##0.00") );
		validator.addRuleMinValue(txfBudget, BigDecimal.valueOf(0) );
		validator.addRuleMaxValue(txfBudget, BigDecimal.valueOf(1000000) );
		
		// Echéance
		bindBidirectional( dtpEcheance, draft.echeanceProperty(), new ConverterLocalDate() );
		validator.addRuleMinValue( dtpEcheance, LocalDate.of(2000, 1, 1) );
		validator.addRuleMaxValue( dtpEcheance, LocalDate.of(2099, 12, 31) );
		
		// Heure
		bindBidirectional( txfHeure, draft.heureProperty(), new ConverterLocalTime() );
		
		// Abonnés
		lsvAbonnes.setItems( draft.getAbonnes() );
		UtilFX.setCellFactory( lsvAbonnes, "pseudo" );
		lsvAbonnes.getSelectionModel().setSelectionMode( SelectionMode.MULTIPLE );
		
		// Acteurs
		tbvActeurs.setItems( draft.getActeurs() );
		UtilFX.setValueFactory( colPersonne, "personne" );
		UtilFX.setValueFactory( colFonction, "fonction" );
		UtilFX.setCellFactory( colPersonne, p -> p.getNom() + " " + p.getPrenom() );		

		// Image
		bindBidirectional(imvImage, modelMemo.imageProperty() );

		// Configuraiton des boutons
		
		lsvAbonnes.getSelectionModel().selectedItemProperty().addListener( obs ->  {
			configurerBoutonsAbonnes();
		});
		configurerBoutonsAbonnes();

		tbvActeurs.getSelectionModel().selectedItemProperty().addListener( obs -> {
			configurerBoutonsActeurs();
		});
		configurerBoutonsActeurs();
		
		// Bouton VAlider
		btnValider.disableProperty().bind( validator.invalidProperty() );
	}
	
	
	@Override
	public void refresh() {
		txfTitre.requestFocus();
		validator.reinit();
		
		if ( modelMemo.getCheminImageCourante().exists() ) {
			btnImageOuvrir.setDisable( false );
		} else {
			btnImageOuvrir.setDisable( true );
		}		
	}
	
	
	// Actions
	
	@FXML
	private void doAnnuler() {
		managerGui.showView( ViewMemoList.class );
	}
	
	@FXML
	private void doValider() {
		modelMemo.saveDraft();
		managerGui.showView( ViewMemoList.class );
	}

	@FXML
	private void doCategorieSupprimer() {
		cmbCategorie.setValue( null );
	}
	
	@FXML
	private void doAbonneAjouter() {
		managerGui.showDialog( ViewMemoAbonner.class );
	}

	@FXML
	private void doAbonneSupprimer() {
		var items = lsvAbonnes.getSelectionModel().getSelectedItems();
		modelMemo.getDraft().getAbonnes().removeAll( items );
	}
	
	@FXML
	private void doActeurAjouter() {
		modelActeur.initDraft( Mode.NEW );
		managerGui.showDialog( ViewMemoActeur.class );
		tbvActeurs.requestFocus();
	}

	@FXML
	private void doActeurModifier() {
		modelActeur.initDraft( Mode.EDIT );
		managerGui.showDialog( ViewMemoActeur.class );
		tbvActeurs.requestFocus();
	}

	@FXML
	private void doActeurSupprimer() {
		modelMemo.getDraft().getActeurs().removeAll( tbvActeurs.getSelectionModel().getSelectedItems() );
	}	

	@FXML
	private void doImageChoisir() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choisissez un fichier image");
		File chemin = fileChooser.showOpenDialog( managerGui.getStage() );
		if ( chemin != null ) {
			imvImage.setImage( new Image( chemin.toURI().toString() ) );
		}		
	}
		
	@FXML
	private void doImageOuvrir() {
		try {
			Desktop.getDesktop().open( modelMemo.getCheminImageCourante() );
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
		
	@FXML
	private void doImageSupprimer() {
		imvImage.setImage(null);
	}
	
	
	//-------
	// Gestion des évènements
	//-------

	// Clic sur la liste des acteurs
	@FXML
	private void gererClicSurListeActeurs( MouseEvent event ) {
		if (event.getButton().equals(MouseButton.PRIMARY)) {
			if (event.getClickCount() == 2) {
				if ( tbvActeurs.getSelectionModel().getSelectedIndex() == -1 ) {
					managerGui.showDialogError( "Aucun élément n'est sélectionné dans la liste.");
				} else {
					doActeurModifier();
				}
			}
		}
	}

	
	//-------
	// Méthodes auxiliaires
	//-------
	
	private void configurerBoutonsAbonnes() {
		
    	if( lsvAbonnes.getSelectionModel().getSelectedItem() == null ) {
			btnAbonneSupprimer.setDisable(true);
		} else {
			btnAbonneSupprimer.setDisable(false);
		}
	}
	
	private void configurerBoutonsActeurs() {
		
    	if( tbvActeurs.getSelectionModel().getSelectedItem() == null ) {
			btnActeurModifier.setDisable(true);
			btnActeurSupprimer.setDisable(true);
		} else {
			btnActeurModifier.setDisable(false);
			btnActeurSupprimer.setDisable(false);
		}
	}
	
}
