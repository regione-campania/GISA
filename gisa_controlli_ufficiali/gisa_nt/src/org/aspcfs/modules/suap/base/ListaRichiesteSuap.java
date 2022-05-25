/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.suap.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ListaRichiesteSuap {
	
	ArrayList<Richiesta> richieste = new ArrayList<Richiesta>();
	
	public static final int RICHIESTE_VALIDABILI  =1;
	public static final int RICHIESTE_ARCHIVIATE  =2;
	public static final int RICHIESTE_EVASE  =3;
	
	public ArrayList<Richiesta> getRichieste() { return richieste; }
	public void setRichieste(ArrayList<Richiesta> ric) { richieste=ric; }
	
	public ListaRichiesteSuap() {}
	
	public int getNumeroRichiesteDaValidare(Connection db,int idAsl) throws SQLException
	{
		int numScia = 0 ;
		
		String sql = "select count(*) "+
"from suap_ric_scia_operatore op "+
"join suap_ric_scia_stabilimento  st on op.id = st.id_operatore "+
"where st.stato_validazione !=2 and "+
"(validato = false or validato is null) "+ 
"and op.trashed_Date is null and st.trashed_date is null ";
		
		if(idAsl>0)
			sql +=" and st.id_asl = ? "; 
	PreparedStatement pst = db.prepareStatement(sql);
	if(idAsl>0)
		pst.setInt(1, idAsl);
	
	ResultSet rs = pst.executeQuery();
	if(rs.next())
	{
		numScia = rs.getInt(1);
	}
	return numScia;
		
	}
	
	
	public ListaRichiesteSuap(Connection db,int idAslUtente) throws Exception{
		this(db, idAslUtente, RICHIESTE_VALIDABILI);
	}
	
	public ListaRichiesteSuap(Connection db,int idAslUtente, int tipoRichieste) throws Exception
	{

		//devo allegare alla request tutte le richieste
		//esistono 2 livelli di controllo sui permessi per la visualizzazione richieste:
		//se l'id asl non e' settato le visualizziamo tutte
		//se l'id asl e' settata visualizziamo solo le richieste appartenenti a quell'asl (collegamento tramite comune) 
		//e all'interno di queste il bottone valida appare solo se il permesso, associato alla linea di attivita' del bottone valida, e' lo stesso
		//associato all'utente che e' loggato

		String query0 = "select * from suap_get_lista_richieste(null,null,null)  ";
		String filter = creaFiltro(tipoRichieste);
		String order = creaOrder(tipoRichieste);
				
		query0+=filter ;

		if (idAslUtente>0)
			query0+=" and id_asl=?";
		
		query0+=order;
		
		PreparedStatement pst = null,pst2 = null;
		ResultSet rs = null, rs2 = null;

		try
		{
			pst = db.prepareStatement( query0 );
			if(idAslUtente > 0)
			{	
				pst.setInt(1,idAslUtente);
			}
			

			rs = pst.executeQuery();
			while(rs.next())
			{
				Richiesta t = new Richiesta(rs);
				
				
				
				//ottengo da lookup la descr del tipo richiesta
				int idTipoRich = t.getIdTipoRichiesta();
				pst2 = db.prepareStatement("select description from suap_lookup_tipo_richiesta where code =?");
				pst2.setInt(1, idTipoRich);
				rs2 = pst2.executeQuery();

				try
				{
					rs2.next();
					t.setDescrTipoRichiesta(rs2.getString("description"));
				}
				catch(Exception ex)
				{
					t.setDescrTipoRichiesta("");
					//							System.out.println("Eccezione lookup tipo richiesta per "+idTipoRich);
				}

				rs2.close();
				pst2.close();


				//e lo stesso per tipo impresa e tipo societa
				rs2.close();
				pst2.close();
				int idTipoImpresa = t.getTipoImpresa();
				pst2 = db.prepareStatement("select description from lookup_opu_tipo_impresa where code = ?");
				pst2.setInt(1, idTipoImpresa);
				rs2 = pst2.executeQuery();

				try
				{
					rs2.next();
					t.setDescrTipoImpresa(rs2.getString("description"));
				}
				catch(Exception ex)
				{
					//							System.out.println("Eccezione lookup tipo impresa "+idTipoImpresa);
					t.setDescrTipoImpresa("");
				}

				rs2.close();
				pst2.close();


				int idTipoSocieta = t.getTipoSocieta();
				pst2 = db.prepareStatement("select description from lookup_opu_tipo_impresa_societa where code = ?");
				pst2.setInt(1,idTipoSocieta);
				rs2 = pst2.executeQuery();
				try
				{
					rs2.next();
					t.setDescrTipoSocieta(rs2.getString("description"));
				}
				catch(Exception ex)
				{
					//							System.out.println("Eccezione lookup tipo societa "+idTipoSocieta);
					t.setDescrTipoSocieta("");
				}


				richieste.add(t);

			}


		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new Exception();
		}
		
				
				
	}
	
	private String creaFiltro(int tipoRichiesta){
		String where = "where 1=1 ";
				
				if (tipoRichiesta==RICHIESTE_VALIDABILI)
					where+= "and (validato = false or validato is null) and stato_validazione!="+Stabilimento.STATO_RICHIESTA_NON_VALIDABILE;
				else if (tipoRichiesta==RICHIESTE_ARCHIVIATE)
					where+= " and stato_validazione="+Stabilimento.STATO_RICHIESTA_NON_VALIDABILE;
				else if (tipoRichiesta==RICHIESTE_EVASE)
					where+= "and validato=true";
				return where;
	}
	
	private String creaOrder(int tipoRichiesta){
		String order =  " order by id desc ";
		 if (tipoRichiesta==RICHIESTE_EVASE)
					order= " order by id desc limit 100";
		return order;
	}
}
