package exemple.view.memo;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import exemple.commun.IMapper;
import exemple.dao.DaoCompte;
import exemple.dao.DaoMemo;
import exemple.data.Categorie;
import exemple.data.Compte;
import exemple.data.Memo;
import exemple.view.categorie.ModelCategorie;
import exemple.view.systeme.ModelConfig;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.Mode;


public class ModelMemo2  {
	
	
	//-------
	// Données observables 
	//-------
	
	private final ObservableList<Memo>	list = FXCollections.observableArrayList(); 
	
	private final BooleanProperty 		flagRefreshingList = new SimpleBooleanProperty();
	
	private final ObjectProperty<Image>	image 		= new SimpleObjectProperty<>();
	
	private final Memo					draft 		= new Memo();
	
	private final ObjectProperty<Memo>	current 	= new SimpleObjectProperty<>();
	
	private final ObservableList<Compte> comptesAbonnables = FXCollections.observableArrayList();

	
	//-------
	// Autres champs
	//-------
	
	private Mode		mode = Mode.NEW;

	@Inject
	private IMapper			mapper;
    @Inject
	private DaoMemo			daoMemo;
    @Inject
    private ModelCategorie	modelCategorie;
    @Inject
    private ModelConfig		modelConfig;
    @Inject
    private DaoCompte		daoCompte;
    
    private boolean			flagModifImage;
	
	
	//-------
	// Getters & Setters
	//-------
	
	public ObservableList<Memo> getList() {
		return list;
	}

	public BooleanProperty flagRefreshingListProperty() {
		return flagRefreshingList;
	}
	
	public Memo getDraft() {
		return draft;
	}

	public ObjectProperty<Image> imageProperty() {
		return image;
	}
	
	public ObjectProperty<Memo> currentProperty() {
		return current;
	}

	public Memo getCurrent() {
		return current.get();
	}

	public void setCurrent(Memo item) {
		current.set(item);
	}
	
	public Mode getMode() {
		return mode;
	}
	
	public ObservableList<Categorie> getCategories() {
		return modelCategorie.getList();
	}
	
	public ObservableList<Compte> getComptesAbonnables() {
		return comptesAbonnables;
	}
	
	//-------
	// Initialisations
	//-------

	@PostConstruct
	public void init() {
		image.addListener( obs -> flagModifImage = true );
	}	
	
	//-------
	// Actions
	//-------
	
	public void refreshComptesAbonnables() {
		comptesAbonnables.setAll( daoCompte.listerTout() );
		comptesAbonnables.removeAll( draft.getAbonnes() );
 	}
	
	public void refreshList() {
		// flagRefreshingList vaut true pendant la durée  
		// du traitement de mise à jour de la liste
		flagRefreshingList.set(true);
		list.setAll( daoMemo.listerTout() );
		flagRefreshingList.set(false);
 	}

	public void initDraft(Mode mode) {
		this.mode = mode;
		modelCategorie.refreshList();
		if( mode == Mode.NEW ) {
			mapper.update( draft, new Memo() );
			draft.setFlagUrgent( false );
			draft.setStatut("A");
		} else {
			setCurrent( daoMemo.retrouver( getCurrent().getId() ) );
			mapper.update( draft, getCurrent() );
		}

		File chemin = getCheminImageCourante();
		if ( chemin.exists() ) {
			image.set( new Image( chemin.toURI().toString() ) );
		} else {
			image.set( null );
		}
		flagModifImage = false;
	}
	
	
	public void saveDraft() {

		// Enregistre les données dans la base
		
		if ( mode == Mode.NEW ) {
			// Insertion
			daoMemo.inserer( draft );
			// Actualise le courant
			setCurrent( mapper.update( new Memo(), draft ) );
		} else {
			// modficiation
			daoMemo.modifier( draft );
			// Actualise le courant
			mapper.update( getCurrent(), draft );
		}
		
		if (flagModifImage) {
			if (image.get() == null) {
				getCheminImageCourante().delete();
			} else {
				try {
					ImageIO.write(SwingFXUtils.fromFXImage(image.get(), null), "JPG", getCheminImageCourante());
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			} 
		}
	}
	
	
	public void deleteCurrent() {
		
		// Effectue la suppression
		daoMemo.supprimer( getCurrent().getId() );
		// Détermine le nouveau courant
		setCurrent( UtilFX.findNext( list, getCurrent() ) );
		
		getCheminImageCourante().delete();
	}

	//-------
	// Méthodes auxiliaires
	//-------

	public File getCheminImageCourante() {
		String nomFichier = String.format( "%06d.jpg", draft.getId() );
		File dossierImages = modelConfig.getDossierImages();
		return new File( dossierImages, nomFichier );
	}	
}
