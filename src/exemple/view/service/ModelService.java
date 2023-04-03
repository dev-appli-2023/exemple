package exemple.view.service;

import java.time.LocalDate;

import exemple.commun.IMapper;
import exemple.dao.DaoService;
import exemple.data.Personne;
import exemple.data.Service;
import exemple.view.personne.ModelPersonne;
import jakarta.inject.Inject;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.Mode;


public class ModelService  {
	
	
	//-------
	// Données observables 
	//-------
	
	private final ObservableList<Service> list = FXCollections.observableArrayList(); 
	
	private final BooleanProperty	flagRefreshingList = new SimpleBooleanProperty();
	
	private final Service	draft = new Service();
	
	private final Property<Service> current = new SimpleObjectProperty<>();

	
	//-------
	// Autres champs
	//-------
	
	private Mode			mode = Mode.NEW;

	@Inject
	private IMapper			mapper;
	@Inject
	private ModelPersonne	modelPersonne;
    @Inject
	private DaoService		daoService;
	
	
	//-------
	// Getters & Setters
	//-------
	
	public ObservableList<Service> getList() {
		return list;
	}

	public BooleanProperty flagRefreshingListProperty() {
		return flagRefreshingList;
	}
	
	public Service getDraft() {
		return draft;
	}

	public Property<Service> currentProperty() {
		return current;
	}

	public Service getCurrent() {
		return current.getValue();
	}

	public void setCurrent(Service item) {
		current.setValue(item);
	}
	
	public ObservableList<Personne> getListePersonnes() {
		return modelPersonne.getList();
	}
	
	public Mode getMode() {
		return mode;
	}
	
	
	//-------
	// Actions
	//-------
	
	public void refreshList() {
		// flagRefreshingList est mis à true pendant  
		// le traitement de mise à jour de la liste
		flagRefreshingList.set(true);
		list.setAll( daoService.listerTout() );
		flagRefreshingList.set(false);
 	}
	
	public void initDraft(Mode mode) {
		this.mode = mode;
		modelPersonne.refreshList();
		if( mode == Mode.NEW ) {
			mapper.update( draft, new Service() );
			draft.setFlagSiege(false);
			draft.setAnneeCreation( LocalDate.now().getYear() );
		} else {
			setCurrent( daoService.retrouver( getCurrent().getId() ) );
			mapper.update( draft, getCurrent() );
		}
	}
	
	
	public void saveDraft() {
		
		// Enregistre les données dans la base
		
		if ( mode == Mode.NEW ) {
			// Insertion
			daoService.inserer( draft );
			// Actualise le courant
			setCurrent( mapper.update( new Service(), draft ) );
		} else {
			// modficiation
			daoService.modifier( draft );
			// Actualise le courant
			mapper.update( getCurrent(), draft );
		}
	}
	
	
	public void deleteCurrent() {
		// Effectue la suppression
		daoService.supprimer( getCurrent().getId() );
		// Détermine le nouveau courant
		setCurrent( UtilFX.findNext( list, getCurrent() ) );
	}
	
}
