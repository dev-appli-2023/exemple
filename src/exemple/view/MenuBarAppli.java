package exemple.view;

import exemple.commun.Roles;
import exemple.report.EnumReport;
import exemple.report.ManagerReport;
import exemple.view.categorie.ViewCategorieList;
import exemple.view.compte.ViewCompteCombo;
import exemple.view.memo.ViewMemoList;
import exemple.view.personne.ViewEtatParCategorie;
import exemple.view.personne.ViewPersonneList;
import exemple.view.service.ViewServiceList;
import exemple.view.systeme.ModelConnexion;
import exemple.view.systeme.ViewAbout;
import exemple.view.systeme.ViewConnexion;
import exemple.view.test.ViewTestDaoCategorie;
import exemple.view.test.ViewTestDaoCompte;
import exemple.view.test.ViewTestDaoMemo;
import exemple.view.test.ViewTestDaoPersonne;
import exemple.view.test.ViewTestDaoService;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Menu;
import jfox.context.Dependent;
import jfox.javafx.control.MenuBarAbstract;


@Dependent
public class MenuBarAppli extends MenuBarAbstract {

	
	// Champs 
	
	private final BooleanProperty flagConnexion	= new SimpleBooleanProperty();
	private final BooleanProperty flagRoleUtil	= new SimpleBooleanProperty();
	private final BooleanProperty flagRoleAdmin	= new SimpleBooleanProperty();
	
	@Inject
	private ManagerGui 		managerGui;
	@Inject
	private ModelConnexion	modelConnexion;	
	@Inject
	private ManagerReport 	managerReport;
	
	
	// Initialisation
	
	@PostConstruct
	public void init() {
		
		// Variables de travail
		Menu menu;
		
		
		// Menu Système
		
		menu = addMenu( "Système" );

		addMenuItem( "Se déconnecter", menu, flagConnexion,
				e -> managerGui.showView( ViewConnexion.class ) );

		addMenuItem( "Quitter", menu, 
				e -> managerGui.exit() );
		

		
		// Menu Données
		
		menu = addMenu( "Donnees", flagRoleUtil.or(flagRoleAdmin) );
		
		addMenuItem( "Services", menu,
				e -> managerGui.showView( ViewServiceList.class ) );
		
		addMenuItem( "Mémos", menu,
				e -> managerGui.showView( ViewMemoList.class ) );
		
		addMenuItem( "Personnes", menu,
				e -> managerGui.showView( ViewPersonneList.class ) );
		
		addMenuItem( "Catégories", menu, flagRoleAdmin, 
				e -> managerGui.showView( ViewCategorieList.class ) );
		
		addMenuItem( "Comptes ", menu, flagRoleAdmin, 
				e -> managerGui.showView( ViewCompteCombo.class ) );

		
		// Menu Etats
		
		menu = addMenu( "Etats", flagRoleUtil.or(flagRoleAdmin) );
		
		addMenuItem( "Liste des personnes (PDF)", menu,
				e -> managerReport.openFilePdf( EnumReport.PersonneListeSimple ) );
		
		addMenuItem( "Liste des personnes (Viewer)", menu,
				e -> managerReport.showViewer( EnumReport.PersonneListeSimple ) );
		
		addMenuItem( "Annuaire", menu,
				e -> managerReport.showViewer( EnumReport.PersonneAnnuaire ) );
		
		addMenuItem( "Personnes par catégorie", menu,
				e -> managerGui.showDialog( ViewEtatParCategorie.class ) );
		
		addMenuItem( "Annuaire", menu,
				 e -> managerReport.showViewer( EnumReport.Annuaire ) );
		
		addMenuItem( "Graphiques", menu,
				 e -> managerReport.showViewer( EnumReport.Graphiques ) );
		
		addMenuItem( "QR Codes", menu,
				 e -> managerReport.showViewer( EnumReport.QRCodes ) );

		
		// Menu Tests
		
		menu = addMenu( "Test", flagRoleAdmin );
		
		addMenuItem( "DaoService", menu,
				e -> managerGui.showView( ViewTestDaoService.class ) );
		
		addMenuItem( "DaoMemo", menu,
				e -> managerGui.showView( ViewTestDaoMemo.class ) );
		
		addMenuItem( "DaoCategorie", menu,
				e -> managerGui.showView( ViewTestDaoCategorie.class ) );
		
		addMenuItem( "DaoPersonne", menu,
				e -> managerGui.showView( ViewTestDaoPersonne.class ) );
		
		addMenuItem( "DaoCompte", menu,
				e -> managerGui.showView( ViewTestDaoCompte.class ) );

		
		// Menu Aide
		
		menu = addMenu( "?" );
		
		addMenuItem( "A propos", menu,
				e -> managerGui.showDialog( ViewAbout.class ) );

		
		// Gestion des droits d'accès
		
		final var compteActif = modelConnexion.compteActifProperty();
		flagConnexion.bind( compteActif.isNotNull() );
		flagRoleUtil.bind( Bindings.createBooleanBinding( () -> flagConnexion.get() && compteActif.get().isInRole(Roles.UTILISATEUR), compteActif ) );
		flagRoleAdmin.bind( Bindings.createBooleanBinding( () -> flagConnexion.get() && compteActif.get().isInRole(Roles.ADMINISTRATEUR), compteActif ) );
		
	}
}
