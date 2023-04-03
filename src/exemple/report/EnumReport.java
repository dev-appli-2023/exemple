package exemple.report;

import jfox.jasperreports.IEnumReport;

public enum EnumReport implements IEnumReport {

	// -------
	// Valeurs
	// -------

	Annuaire				( "personne/Annuaire.jrxml" ),
	PersonneListeSimple		( "personne/ListeSimple.jrxml"), PersonneAnnuaire("personne/Annuaire.jrxml"),
	PersonneParCategorie1	( "personne/ListeParCategorie1.jrxml" ),
	PersonneParCategorie2	( "personne/ListeParCategorie2.jrxml" ),
	QRCodes					( "personne/QRCodes.jrxml" ),
	Graphiques				( "personne/Graphiques.jrxml" ),
	;

	// -------
	// Champs
	// -------

	private String path;

	// -------
	// Constructeur
	// -------

	EnumReport(String path) {
		this.path = path;
	}

	// -------
	// Getters & setters
	// -------

	@Override
	public String getPath() {
		return path;
	}

}
