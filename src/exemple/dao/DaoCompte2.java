package exemple.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import exemple.data.Compte;
import jakarta.inject.Inject;
import jfox.jdbc.UtilJdbc;


public class DaoCompte2 {

	
	//-------
	// Champs
	//-------

	@Inject
	private DataSource		dataSource;
	@Inject
	private DaoRole			daoRole;

	
	//-------
	// Actions
	//-------

	public void inserer( Compte compte )  {

		Connection			cn		= null;
		CallableStatement	stmt	= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			// Insère le compte
			sql = "{ CALL compte_inserer( ?, ?, ?, ? ) }";
			stmt = cn.prepareCall( sql ); 
			stmt.setObject( 1, compte.getPseudo() );
			stmt.setObject( 2, compte.getMotDePasse() );
			stmt.setObject( 3, compte.getEmail() );
			stmt.registerOutParameter( 4,  Types.INTEGER );
			stmt.executeUpdate();

			// Récupère l'identifiant généré par le SGBD
			compte.setId( stmt.getInt( 4 ) );
	
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}

		// Insère les rôles
		daoRole.mettreAJourPourCompte( compte );
	}
	

	public void modifier( Compte compte )  {

		Connection			cn		= null;
		CallableStatement	stmt	= null;
		String 				sql;

		try {
			cn = dataSource.getConnection();

			// Modifie le compte
			sql = "{ CALL compte_modifier( ?, ?, ?, ? ) }";
			stmt = cn.prepareCall( sql );
			stmt.setObject( 1, compte.getPseudo() );
			stmt.setObject( 2, compte.getMotDePasse() );
			stmt.setObject( 3, compte.getEmail() );
			stmt.setObject( 4, compte.getId() );
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}

		// Met à jour les rôles
		daoRole.mettreAJourPourCompte( compte );

	}
	

	public void supprimer( int idCompte )  {

		Connection			cn		= null;
		CallableStatement	stmt	= null;
		String 				sql;

		try {
			cn = dataSource.getConnection();

			// Supprime le compte
			sql = "{ CALL compte_supprimer( ? ) }";
			stmt = cn.prepareCall( sql );
			stmt.setObject( 1, idCompte );
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}
	

	public Compte retrouver( int idCompte )  {

		Connection			cn		= null;
		CallableStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "{ CALL compte_retrouver( ? ) }";
            stmt = cn.prepareCall( sql );
            stmt.setObject( 1, idCompte );
            rs = stmt.executeQuery();

            if ( rs.next() ) {
				return construireCompte( rs, true );			
            } else {
            	return null;
            }
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	

	public List<Compte> listerTout()   {

		Connection			cn		= null;
		CallableStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "{ CALL compte_lister_tout() }";
            stmt = cn.prepareCall( sql );
			rs = stmt.executeQuery();

			List<Compte> liste = new ArrayList<>();
			while ( rs.next() ) {
				liste.add( construireCompte( rs, false ) );
			}
			return liste;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}


	public Compte validerAuthentification( String pseudo, String motDePasse )  {
		
		Connection			cn		= null;
		CallableStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "{ CALL compte_valider_authentification( ?, ? ) }";
            stmt = cn.prepareCall( sql );
			stmt.setObject( 1, pseudo );
			stmt.setObject( 2, motDePasse );
			rs = stmt.executeQuery();

			if ( rs.next() ) {
				return construireCompte( rs, true );			
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}


	public boolean verifierUnicitePseudo( String pseudo, Integer idCompte )   {

		Connection			cn		= null;
		CallableStatement	stmt	= null;
		String				sql;

		if ( idCompte == null ) idCompte = 0;
		
		try {
			cn = dataSource.getConnection();

			sql = "{ CALL compte_verifier_unicite_pseudo( ?, ?, ? ) }";
            stmt = cn.prepareCall( sql );
			stmt.setObject(	1, pseudo );
			stmt.setObject(	2, idCompte );
			stmt.registerOutParameter( 3, Types.BOOLEAN );
			stmt.execute();
			
			return stmt.getBoolean( 3 );
	
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}
	
	
	//-------
	// Méthodes auxiliaires
	//-------
	
	protected Compte construireCompte( ResultSet rs, boolean flagComplet ) throws SQLException {
		Compte compte = new Compte();
		compte.setId( rs.getObject( "idcompte", Integer.class ) );
		compte.setPseudo( rs.getObject( "pseudo", String.class ) );
		compte.setMotDePasse( rs.getObject( "motdepasse", String.class ) );
		compte.setEmail( rs.getObject( "email", String.class ) );
		if ( flagComplet ) {
			compte.getRoles().setAll( daoRole.listerPourCompte( compte ) );
		}
		return compte;
	}
	
}
