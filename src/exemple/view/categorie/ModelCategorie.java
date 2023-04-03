package exemple.view.categorie;

import java.time.LocalDate;

import exemple.commun.IMapper;
import exemple.dao.DaoCategorie;
import exemple.dao.DaoMemo;
import exemple.dao.DaoPersonne;
import exemple.data.Categorie;
import jakarta.inject.Inject;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfox.exception.ExceptionValidation;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.Mode;


public class ModelCategorie  {
	
	
	// Données observables 
	
	private final ObservableList<Categorie> list 	= FXCollections.observableArrayList(); 
	
	private final BooleanProperty 		flagRefreshingList = new SimpleBooleanProperty();
	
	private final Categorie				draft 		= new Categorie();
	
	private final ObjectProperty<Categorie> 	current 	= new SimpleObjectProperty<>();

	
	// Autres champs
	
	private Mode			mode = Mode.NEW;

	@Inject
	private IMapper			mapper;
    @Inject
	private DaoCategorie	daoCategorie;
    @Inject
    private DaoPersonne		daoPersonne;
    @Inject
    private DaoMemo			daoMemo;
	
	
	// Getters & Setters
	
	public ObservableList<Categorie> getList() {
		return list;
	}

	public BooleanProperty flagRefreshingListProperty() {
		return flagRefreshingList;
	}
	
	public Categorie getDraft() {
		return draft;
	}

	public ObjectProperty<Categorie> currentProperty() {
		return current;
	}

	public Categorie getCurrent() {
		return current.get();
	}

	public void setCurrent(Categorie item) {
		current.set(item);
	}
	
	public Mode getMode() {
		return mode;
	}
	
	
	// Actions
	
	public void refreshList() {
		// flagRefreshingList vaut true pendant la durée  
		// du traitement de mise à jour de la liste
		flagRefreshingList.set(true);
		list.setAll( daoCategorie.listerTout() );
		flagRefreshingList.set(false);
 	}

	public void initDraft(Mode mode) {
		this.mode = mode;
		if( mode == Mode.NEW ) {
			mapper.update( draft, new Categorie() );
			draft.setDebut( LocalDate.now() );
		} else {
			setCurrent( daoCategorie.retrouver( getCurrent().getId() ) );
			mapper.update( draft, getCurrent() );
		}
	}
	
	
	public void saveDraft() {

		// Enregistre les données dans la base
		
		if ( mode == Mode.NEW ) {
			// Insertion
			daoCategorie.inserer( draft );
			// Actualise le courant
			setCurrent( mapper.update( new Categorie(), draft ) );
		} else {
			// modficiation
			daoCategorie.modifier( draft );
			// Actualise le courant
			mapper.update( getCurrent(), draft );
		}
	}
	
	
	public void deleteCurrent() {
		
		// Vérifie l'absence de personnes rattachées à la catégorie
		if ( daoPersonne.compterPourCategorie( getCurrent().getId() ) != 0 ) {
			throw new ExceptionValidation( "Suppression impossible.\nDes personnes sont rattachées à cette catégorie." ) ;
		}
		
		// Vérifie l'absence de mémos rattachés à la catégorie
		if ( daoMemo.compterPourCategorie( getCurrent().getId() ) != 0 ) {
			throw new ExceptionValidation( "Suppression impossible.\nDes mémos sont rattachés à cette catégorie." ) ;
		}
		
		// Effectue la suppression
		daoCategorie.supprimer( getCurrent().getId() );
		// Détermine le nouveau courant
		setCurrent( UtilFX.findNext( list, getCurrent() ) );
	}
	
}
