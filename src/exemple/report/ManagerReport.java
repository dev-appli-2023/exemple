package exemple.report;

import javax.sql.DataSource;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jfox.jasperreports.ManagerReportAbstract;
import jfox.javafx.view.IManagerGui;

public class ManagerReport extends ManagerReportAbstract {

	// -------
	// Champs
	// -------

	@Inject
	private IManagerGui managerGui;
	@Inject
	private DataSource dataSource;

	// -------
	// Initialisations
	// -------

	@PostConstruct
	private void init() {
		super.setManagerGui(managerGui);
		super.setDataSource(dataSource);
	}
	
	
}
