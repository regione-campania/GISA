/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.vam;

import it.us.web.bean.BUtente;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.DiarioClinico;
import it.us.web.bean.vam.DiarioClinicoEsitoEO;
import it.us.web.bean.vam.DiarioClinicoTipoEO;
import it.us.web.bean.vam.EsameObiettivo;
import it.us.web.bean.vam.EsameObiettivoEsito;
import it.us.web.bean.vam.lookup.LookupEsameObiettivoApparati;
import it.us.web.dao.hibernate.Persistence;

import java.util.ArrayList;
import java.util.Date;

public class DiarioClinicoUtil 
{
	
	public static void save( 
				CartellaClinica cc, 
				LookupEsameObiettivoApparati apparato, 
				Date data,
				String temperatura,
				ArrayList<EsameObiettivo> esami,
				BUtente operatore,
				Persistence persistence ) throws Exception
	{
		DiarioClinico dc = new DiarioClinico();

		dc.setCartellaClinica	( cc );
		dc.setApparato			( apparato );
		dc.setData				( data );
		dc.setEntered			( new Date() );
		dc.setEnteredBy			( operatore );
		dc.setTemperatura		( temperatura );
		
		persistence.insert( dc );
		
		for( EsameObiettivo eo: esami )
		{
			DiarioClinicoTipoEO dcteo = new DiarioClinicoTipoEO();
			dcteo.setDiarioClinico( dc );
			dcteo.setNormale( eo.getNormale() );
			dcteo.setTipo	( eo.getLookupEsameObiettivoTipo() );
			
			persistence.insert( dcteo );
			
			for( EsameObiettivoEsito eoe: eo.getEsameObiettivoEsitos() )
			{
				DiarioClinicoEsitoEO dceeo = new DiarioClinicoEsitoEO();
				dceeo.setEsito( eoe.getLookupEsameObiettivoEsito() );
				dceeo.setDiarioClinicoTipoEO( dcteo );
				
				persistence.insert( dceeo );
			}

		}
		
	}
	
}
