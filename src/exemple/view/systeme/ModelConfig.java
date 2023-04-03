package exemple.view.systeme;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import jakarta.annotation.PostConstruct;
import jfox.exception.ExceptionValidation;
import jfox.javafx.util.UtilFX;

public class ModelConfig {
	
	//-------
	// Champs
	//-------
	
	private File	dossierImages;
	
	//-------
	// Initialisation
	//-------
	
	@PostConstruct
	public void init() {
		// Lecture du fichier de configuration
		var props = new Properties();
		var in = UtilFX.getInputStram( "classpath:/META-INF/config.properties" );
		try {
			props.load( in );
			in.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		var valeur = props.getProperty( "dossier.images" );
		if ( valeur == null ) {
			throw new ExceptionValidation(
				"Paramètre absent dans le fichier de configuration :\n"
				+ "dossier.images" );
		}		
		dossierImages = new File( valeur );		
		if ( ! dossierImages.exists() ) {
			dossierImages.mkdirs();
		}
		if ( ! dossierImages.exists() ) {
			throw new ExceptionValidation( 
				"Le dossier des images n'existe pas :\n"
				+ dossierImages.toString() );
		}	}

	//-------
	// Getters
	//-------
	
	public File getDossierImages() {
		return dossierImages;
	}
	
}
