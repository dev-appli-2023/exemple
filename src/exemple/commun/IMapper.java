package exemple.commun;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import exemple.data.Agir;
import exemple.data.Categorie;
import exemple.data.Compte;
import exemple.data.Memo;
import exemple.data.Personne;
import exemple.data.Service;


@Mapper
public interface IMapper {
	
	Compte update( @MappingTarget Compte target, Compte source  );
	
	Categorie update( @MappingTarget Categorie target, Categorie source );

	@Mapping( target="categorie", expression="java( source.getCategorie() )" )
	Personne update( @MappingTarget Personne target, Personne source );

	@Mapping( target="categorie", expression="java( source.getCategorie() )" )
	Memo update( @MappingTarget Memo target, Memo source );

	@Mapping( target="personne", expression="java( source.getPersonne() )" )
	Service update( @MappingTarget Service target, Service source );
	
	@Mapping( target="memo", expression="java( source.getMemo() )" )
	@Mapping( target="personne", expression="java( source.getPersonne() )" )
	Agir update( @MappingTarget Agir target, Agir source );
	
}
