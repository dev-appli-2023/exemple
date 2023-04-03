package exemple.view.personne;

import exemple.commun.IMapper;
import exemple.dao.DaoPersonne;
import exemple.dao.DaoService;
import exemple.data.Categorie;
import exemple.data.Personne;
import exemple.view.categorie.ModelCategorie;
import jakarta.inject.Inject;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfox.exception.ExceptionValidation;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.Mode;


public class ModelPersonne {
	
	
	//-------
	// Données observables 
	//-------
	
	private final ObservableList<Personne> list		= FXCollections.observableArrayList();
	
	private final BooleanProperty 		flagRefreshingList = new SimpleBooleanProperty();
	
	private final Personne				draft 		= new Personne();
	
	private final Property<Personne> 	current 	= new SimpleObjectProperty<>();
	
	
	//-------
	// Autres champs
	//-------
	
	private Mode			mode = Mode.NEW;

	@Inject
	private IMapper			mapper;
    @Inject
	private ModelCategorie	modelCategorie;
    @Inject
	private DaoPersonne		daoPersonne;
    @Inject
    private DaoService		daoService;

    
	//-------
	// Getters & Setters
	//-------
	
	public ObservableList<Personne> getList() {
		return list;
	}

	public BooleanProperty flagRefreshingListProperty() {
		return flagRefreshingList;
	}
	
	public Personne getDraft() {
		return draft;
	}

	public Property<Personne> currentProperty() {
		return current;
	}

	public Personne getCurrent() {
		return current.getValue();
	}

	public void setCurrent(Personne item) {
		current.setValue(item);
	}
	
	public ObservableList<Categorie> getCategories() {
		return modelCategorie.getList();
	}
	
	public Mode getMode() {
		return mode;
	}

	
	//-------
	// Actions
	//-------
	
	public void refreshList() {
		// flagRefreshingList vaut true pendant la durée  
		// du traitement de mise à jour de la liste
		flagRefreshingList.set(true);
		list.setAll( daoPersonne.listerTout() );
		flagRefreshingList.set(false);
 	}

	public void initDraft(Mode mode) {
		this.mode = mode;
		modelCategorie.refreshList();
		if( mode == Mode.NEW ) {
			mapper.update( draft, new Personne() );
		} else {
			setCurrent( daoPersonne.retrouver( getCurrent().getId() ) );
			mapper.update( draft, getCurrent() );
		}
	}

	
	public void saveDraft() {
		
		// Enregistre les données dans la base
		
		if ( mode == Mode.NEW ) {
			// Insertion
			daoPersonne.inserer( draft );
			// Actualise le courant
			setCurrent( mapper.update( new Personne(), draft ) );
		} else {
			// modficiation
			daoPersonne.modifier( draft );
			// Actualise le courant
			mapper.update( getCurrent(), draft );
		}
	}
	

	public void deleteCurrent() {
		
		// Vérifie l'abence de services rattachés à la personne
		if ( daoService.compterPourPersonne( getCurrent().getId() ) != 0 ) {
			throw new ExceptionValidation( "Des services sont rattachés à cette personne." ) ;
		}
		
		// Effectue la suppression
		daoPersonne.supprimer( getCurrent().getId() );
		// Détermine le nouveau courant
		setCurrent( UtilFX.findNext( list, getCurrent() ) );
	}
	
}