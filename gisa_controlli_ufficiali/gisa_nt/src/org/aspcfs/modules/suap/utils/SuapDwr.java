/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.suap.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.aspcfs.modules.gestioneml.util.MasterListImportUtil;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.UserUtils;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.extend.LoginRequiredException;

import com.darkhorseventures.framework.actions.ActionContext;

public class SuapDwr {



	
	public static final int UGP_PERMESSO_VIEW = 1;
	public static final int UGP_PERMESSO_ALL = 2;
	
	
	
		    
		    public boolean verificaEsistenzaCodiceNazionaleConComuneVia(String via,String comune,String codiceNazionale)
	{
		Connection db = null;
		boolean valido = true ;
		try
		{
			db = GestoreConnessioni.getConnection();
			String sql="select * from public_functions.suap_verifica_codice_azienda(?,?,?)" ;
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setString(1, via);
			pst.setString(2, comune);
			pst.setString(3, codiceNazionale);
			ResultSet rs = pst.executeQuery();

			if (rs.next())
			{
				valido=rs.getBoolean(1);
			}

		}
		catch(LoginRequiredException e)
		{

			throw e ;
		}
		catch(SQLException e)
		{e.printStackTrace();}
		finally
		{GestoreConnessioni.freeConnection(db);}
		return valido ;

	}
	
		    
	public boolean verificaEsistenzaCodiceNazionale(int idIndirizzoSedeStab,String codiceNazionale)
	{
		Connection db = null;
		boolean valido = true ;
		try
		{
			db = GestoreConnessioni.getConnection();
			String sql="select * from public_functions.suap_verifica_codice_azienda(?,?)" ;
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setInt(1, idIndirizzoSedeStab);
			pst.setString(2, codiceNazionale);
			ResultSet rs = pst.executeQuery();

			if (rs.next())
			{
				valido=rs.getBoolean(1);
			}

		}
		catch(LoginRequiredException e)
		{

			throw e ;
		}
		catch(SQLException e)
		{e.printStackTrace();}
		finally
		{GestoreConnessioni.freeConnection(db);}
		return valido ;

	}
    
	public ArrayList<TipoImpresaSuap> onChangeTipoImpresa(int idTipoImpresa)
	{
		Connection db = null;

		ArrayList<TipoImpresaSuap> listaTipiSocieta= new ArrayList<TipoImpresaSuap>();
		try
		{
			db = GestoreConnessioni.getConnection();
			String sql="select distinct lookup_opu_tipo_impresa_societa.description,lookup_opu_tipo_impresa_societa.code,tipo.description as tipo_impresa,tipo.code as id_tipo_impresa,"+ 
					"required_ragione_sociale,"+ 
					"label_ragione_sociale,"+
					"required_sede_legale,required_partita_iva,required_codice_fiscale "+
					"from "+
					"lookup_opu_tipo_impresa_societa "+
					"join lookup_opu_tipo_impresa tipo on code_lookup_opu_tipo_impresa =tipo.code and lookup_opu_tipo_impresa_societa.enabled "+
					"where tipo.code =" +idTipoImpresa;
			PreparedStatement pst = db.prepareStatement(sql);

			ResultSet rs = pst.executeQuery();

			while (rs.next())
			{
				TipoImpresaSuap tipoImpresa = new TipoImpresaSuap();
				tipoImpresa.setIdTipoImpresa(rs.getInt("id_tipo_impresa"));
				tipoImpresa.setRequiredPartitaIva(rs.getBoolean("required_partita_iva"));
				tipoImpresa.setRequiredCodiceFiscale(rs.getBoolean("required_codice_fiscale"));
				tipoImpresa.setLabelRagioneSociale(rs.getString("label_ragione_sociale"));
				tipoImpresa.setRequiredRagioneSociale(rs.getBoolean("required_ragione_sociale"));
				tipoImpresa.setRequiredSedeLegale(rs.getBoolean("required_sede_legale"));
				tipoImpresa.setTipoSocieta(rs.getString("description"));
				tipoImpresa.setTipoImpresa(rs.getString("tipo_impresa"));
				tipoImpresa.setCodeTipoSocieta(rs.getInt("code"));
				listaTipiSocieta.add(tipoImpresa);
			}

		}
		catch(LoginRequiredException e)
		{

			throw e ;
		}
		catch(SQLException e)
		{e.printStackTrace();}
		finally
		{GestoreConnessioni.freeConnection(db);}
		return listaTipiSocieta ;

	}


	public AttivitaProduttive mostraAttivitaProduttive(int livello,int idSelezionato,int idTipoInserimentoFlusso,int idTipoAttivita,int tipoImpresa)
	{
		
		System.out.println(" *** MASTER LIST SELECT livello: "+livello);
		System.out.println(" *** MASTER LIST SELECT idSelezionato: "+idSelezionato);
		System.out.println(" *** MASTER LIST SELECT idTipoInserimentoFlusso: "+idTipoInserimentoFlusso);
		System.out.println(" *** MASTER LIST SELECT idTipoAttivita: "+idTipoAttivita);
		System.out.println(" *** MASTER LIST SELECT tipoImpresa: "+tipoImpresa);
		
		Connection db = null;
		AttivitaProduttive attivita = new AttivitaProduttive();
		attivita.setBgcolorPrec("black");
		attivita.setIdAttivita(idSelezionato);
		
		
	        WebContext wctx = WebContextFactory.get();
	       HttpServletRequest request = wctx.getHttpServletRequest();
			UserBean user = (UserBean) wctx.getSession().getAttribute("User");

	       System.out.println("TIPO IMPRESA SELEZIONATO : "+request.getParameter("tipo_impresa"));
		
		try
		{
			
			
			db = GestoreConnessioni.getConnection();
						
			String select ="";
			String from=" from master_list_macroarea m join master_list_aggregazione a on m.id = a.id_macroarea join master_list_linea_attivita l on l.id_aggregazione=a.id ";
			String where="where 1=1 ";
			String order = " order by descrizione";
			
			if (idTipoAttivita>0 && idTipoAttivita <3 && idTipoInserimentoFlusso!=MasterListImportUtil.FLUSSO_RICONOSCIUTI)
				where+=" and l.id_lookup_tipo_attivita = "+idTipoAttivita; 
			
			where+= " and a.id_flusso_originale not in ("+ MasterListImportUtil.FLUSSO_ALLEVAMENTI+ ", "+ MasterListImportUtil.FLUSSO_SINTESIS_RICONOSCIUTI +") ";
					
			if (idTipoInserimentoFlusso>0)
				where+=" and a.id_flusso_originale = "+idTipoInserimentoFlusso;
			
			if (tipoImpresa==9)
				where+=" and l.flag_b_e_b is true ";
			
			if (tipoImpresa==10) 
				where+=" and l.flag_home_food is true ";
			
			if (tipoImpresa==12) 
				where+=" and l.flag_struttura_veterinaria_pubblica is true ";
			
			//gruppi linee
			if (idTipoInserimentoFlusso!=MasterListImportUtil.FLUSSO_APICOLTURA)
				where+=" and l.codice_univoco in (select codice_univoco from ugp_gruppi_permessi_linee where id_gruppo = "+ UserUtils.getUgpIdGruppoFromRole(user.getRoleId()) +" and id_permesso = " + UGP_PERMESSO_ALL  +")";

			
			if (livello==1){
				select = "select distinct m.id, m.macroarea as descrizione";
				attivita.setLabel("MACROAREA");

			}
			else if (livello==2){
				select = "select distinct a.id, a.aggregazione as descrizione"; 
				where+=" and a.id_macroarea = "+idSelezionato;
				attivita.setLabel("AGGREGAZIONE");

			}
			else if (livello==3){
				select = "select distinct l.id, l.linea_attivita as descrizione";
				where+=" and l.id_aggregazione = "+idSelezionato;
				attivita.setLabel("LINEA ATTIVITA");

			}
//			else if (livello==4){ //pezza per allegati
//				select = "select "+idSelezionato+" as id , get_master_list_allegati_linea as descrizione ";
//				from = " from get_master_list_allegati_linea( "+idSelezionato+");";
//				where="";
//				order = "";
//				attivita.setLabel("DOCUMENTAZIONE SUPPLEMENTARE");
//
//			}
			
			PreparedStatement pst = db.prepareStatement(select+from+where+order);

			if (livello<=3){
				System.out.println(" *** MASTER LIST SELECT LINEE ATTIVITA: "+pst.toString());
				ResultSet rs = pst.executeQuery();

				while (rs.next())
				{
					Item item =   new Item();
					item.setId(rs.getInt("id"));
					item.setDescizione(rs.getString("descrizione"));
					attivita.getListaItem().add(item);

				}
				attivita.setNextLivello(livello+1);
			}
		}catch(LoginRequiredException e)
		{

			throw e ;
		}
		catch(SQLException e)
		{e.printStackTrace();}
		finally
		{GestoreConnessioni.freeConnection(db);}
		return attivita ;

	}    

	public static String getNumeroRegistrazione852(String campo)
	{
		String ret	= ""; ; 
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try
		{
			db = GestoreConnessioni.getConnection()	;

			String select 	= 	"select account_number from organization where tipologia=1 and "
					+ "trashed_date is null and account_number ilike ? "	;

			pst = db.prepareStatement(select);
			pst.setString(1, "%"+campo+"%");
			rs = pst.executeQuery();
			int i = 0;

			if ( rs.next() )
			{
				ret="PRESENTE";
			}else
			{
				ret="NONPRESENTE";
			}

		}catch(LoginRequiredException e)
		{

			throw e ;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			GestoreConnessioni.freeConnection(db);
		}

		return ret;

	}
	
	public static String[] getCap(int idcomune, int topon, String indir, String campo)
	{
		String[] ret	= new String[2] ; 
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try
		{
			db = GestoreConnessioni.getConnection()	;

			String select = "select cap.* from public.calcola_cap_da_indirizzo(?, ?,?) cap";
			pst = db.prepareStatement(select);
			pst.setInt(1, idcomune);
			pst.setInt(2, topon);
			pst.setString(3, indir);
			rs = pst.executeQuery();
			int i = 0;

			if ( rs.next() )
			{
				ret[0] = rs.getString("cap")				;
				ret[1] = campo;
			}else
			{
				ret[0] = "Specificarre il Comune per poter Calcolare il Capo"				;
				ret[1] = "1";
			}

		}catch(LoginRequiredException e)
		{

			throw e ;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			GestoreConnessioni.freeConnection(db);
		}

		return ret;

	}


	public static String[] getCapDaComuneTesto(String comune, String campo)
	{
		String[] ret	= new String[2] ; 
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try
		{
			db = GestoreConnessioni.getConnection()	;

			String select 	= 	"select cap from comuni1 where nome ilike ? and cap <> '80100'"	;

			pst = db.prepareStatement(select);
			pst.setString(1, comune);
			rs = pst.executeQuery();
			int i = 0;

			if ( rs.next() )
			{
				ret[0] 		= rs.getString("cap")				;
				ret[1] = campo;
			}else
			{
				ret[0] 		= "";
				ret[1] = campo;
			}

		}catch(LoginRequiredException e)
		{

			throw e ;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			GestoreConnessioni.freeConnection(db);
		}

		return ret;

	}




	public static List<String> verificaEsistenzaRichiesta(String pIva, String comuneRichiesta,int idTipoRichiesta,String viaSedeStab,String civicoSedeStab,int toponimoSedeStab,String numReg)
	{
		
		List<String> listaOutput = new ArrayList<String>();
		boolean ret	= false ; 
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try
		{
			db = GestoreConnessioni.getConnection()	;

			//String select 	= 	"select * from opu_stabilimento_mobile where targa ilike ? and enabled"	;

			String select  = "" ;
			boolean esito  = false ;
			if (numReg != null && !numReg.equals(""))
			{
				 select 	= "select * from suap_get_lista_richieste(null,null,null) where numero_registrazione_variazione ilike ? "
						+"and id_tipo_richiesta=? ";


				pst = db.prepareStatement(select);
				pst.setString(1, numReg);
				pst.setInt(2, idTipoRichiesta);
				rs = pst.executeQuery();
				if ( rs.next() )
				{
					esito = true ;
					listaOutput.add(0,"true");
					listaOutput.add(1,"NUMERO REGISTRAZIONE");
					listaOutput.add(2,"TIPO RICHIESTA");
					
				}

			}
			
			if (esito==false || (numReg.equals("")))
			{
			
				select 	= "select * from suap_get_lista_richieste(?,?,?) "

							+"where id_tipo_richiesta=? "
							+"and via_sede_stab ilike ? "
							+"and civico_sede_stab ilike ? "
							+"and toponimo_sede_stab= ? " ;

				int idComuneRichiesta =getIdComune(db,comuneRichiesta) ;



				pst = db.prepareStatement(select);
				pst.setString(1, pIva);
				pst.setString(2, "");
				pst.setInt(3, idComuneRichiesta);
				pst.setInt(4, idTipoRichiesta);
				pst.setString(5, viaSedeStab);
				pst.setString(6, civicoSedeStab);
				pst.setInt(7, toponimoSedeStab);
			

			rs = pst.executeQuery();

			if ( rs.next() )
			{
				listaOutput.add(0,"true");
				listaOutput.add(1,"PARTITA IVA");
				listaOutput.add(2,"TIPO RICHIESTA");
				listaOutput.add(3,"COMUNE STABILIMENTO");
				listaOutput.add(4,"INDIRIZZO STABILIMENTO");
				listaOutput.add(5,"CIVCO STABILIMENTO");
				listaOutput.add(6,"TOPONIMO STABILIMENTO");
			}
			}

		}catch(LoginRequiredException e)
		{

			throw e ;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			GestoreConnessioni.freeConnection(db);
		}
		if (listaOutput.size()==0)
			listaOutput.add(0,"false");

		return listaOutput;

	}

	private static int getIdComune(Connection db,String comune)
	{
		int idComune= -1 ;
		try
		{
			PreparedStatement pst = db.prepareStatement("select id from comuni1 where nome ilike ?");
			pst.setString(1, comune);
			ResultSet rs = pst.executeQuery();
			if (rs.next())
				idComune=rs.getInt(1);

		}catch(LoginRequiredException e)
		{

			throw e ;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return idComune ;


	}



	public static boolean esisteKey(String key, String campo, int idStabilimento, int idOperatore)
	{
		boolean ret	= false ; 
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try
		{
			db = GestoreConnessioni.getConnection()	;

			String select 	= "";
			
			if (campo.equalsIgnoreCase("matricola")) {
				select="select nome_campo, valore_campo, get_dismissione_campo_esteso(v.id) as data_dismissione from linee_mobili_html_fields f inner join linee_mobili_fields_value v on v.id_linee_mobili_html_fields = f.id "
						+ "where v.enabled and nome_campo = ? and valore_campo = ? ;";
				pst = db.prepareStatement(select);
				pst.setString(1, campo);
				pst.setString(2, key);
				System.out.println("Check matricola: "+pst.toString());
				rs = pst.executeQuery();

				if ( rs.next() )
				{
					if (rs.getString("data_dismissione")==null || rs.getString("data_dismissione").equals(""))
						ret = true;				
					}
			}
			
			else {
			    select="select o.id, o.targa, get_dismissione_campo_esteso(o.id) as data_dismissione from opu_stabilimento_mobile o join opu_stabilimento s on s.id=o.id_stabilimento and s.trashed_date is null and s.stato = 0 where o.targa ilike ? and s.id_operatore not in (select id from opu_operatore where partita_iva ilike (select partita_iva from opu_operatore where id = ?)) ";
			    pst = db.prepareStatement(select);
				pst.setString(1, key);	
				pst.setInt(2, idOperatore);	
				System.out.println("Check targa: "+pst.toString());
				rs = pst.executeQuery();

				while ( rs.next() )
				{
					if (rs.getString("data_dismissione")==null || rs.getString("data_dismissione").equals(""))
						ret = true;
				}
			}

		}catch(LoginRequiredException e)
		{

			throw e ;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			GestoreConnessioni.freeConnection(db);
		}

		return ret;

	}
	
	public boolean mostraCodiceNazionaleRichiesto(int livello,int idSelezionato)
	{
		Connection db = null;
		
		WebContext wctx = WebContextFactory.get();
	       HttpServletRequest request = wctx.getHttpServletRequest();
		
		try
		{
			
			
			db = GestoreConnessioni.getConnection();
						
			if (livello!=4)
				return false;
			
			int i = getTipoCodiceRichiesto(idSelezionato);
			
			if (i>0)
				return true;
			
		}catch(LoginRequiredException e)
		{

			throw e ;
		}
		catch(SQLException e)
		{e.printStackTrace();}
		finally
		{GestoreConnessioni.freeConnection(db);
		}
		return false;

	}    
	
	public static int getTipoCodiceRichiesto(int idLinea)
	{
		int output = -1; 
		Connection db = null; 
		try
		{
			db = GestoreConnessioni.getConnection()	;
			 
			PreparedStatement pst = db.prepareStatement("select * from get_codice_richiesto_ml8(?)");
			pst.setInt(1,  idLinea);
			ResultSet rs = pst.executeQuery();
			if (rs.next()){
				output = rs.getInt(1);
			}
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			GestoreConnessioni.freeConnection(db);
		}
		 
		return output;
	}
	
	public static String[] getCheckCodiceLinea(String idLinea, String partitaIva, String idAsl, String idProvincia, String idComune)
	{
		String codiceUnivoco = "";
		String select = "";
		String[] ret	= new String[2] ; 
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try
		{
			db = GestoreConnessioni.getConnection()	;
			
			select = "select codice_attivita from ml8_linee_attivita_nuove_materializzata where id_nuova_linea_attivita = "+idLinea;
			pst = db.prepareStatement(select);
			rs = pst.executeQuery();
			
			if (rs.next())
				codiceUnivoco = rs.getString(1);
			
			select = "select * from public.check_codice_linea(?, ?, ?, ?, ?) ";
			pst = db.prepareStatement(select);
			pst.setString(1, codiceUnivoco);
			pst.setString(2, partitaIva);
			pst.setInt(3, Integer.parseInt(idAsl));
			pst.setInt(4, Integer.parseInt(idProvincia));
			pst.setInt(5, Integer.parseInt(idComune));

			rs = pst.executeQuery();
			int i = 0;

			if ( rs.next() )
			{
				ret[0] = rs.getString("esito_check")				;
				ret[1] = rs.getString("esito_error")				;
			}

		}catch(LoginRequiredException e)
		{

			throw e ;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			GestoreConnessioni.freeConnection(db);
		}

		return ret;

	}
	
	
	public static String[] getCheckCodiceLineaGins(String codiceUnivoco, String partitaIva, String idAsl, String idProvincia, String idComune)
	{
		
		String select = "";
		String[] ret	= new String[2] ; 
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try
		{
			db = GestoreConnessioni.getConnection()	;
			
			select = "select * from public.check_codice_linea(?, ?, ?, ?, ?) ";
			pst = db.prepareStatement(select);
			pst.setString(1, codiceUnivoco);
			pst.setString(2, partitaIva);
			pst.setInt(3, Integer.parseInt(idAsl));
			pst.setInt(4, Integer.parseInt(idProvincia));
			pst.setInt(5, Integer.parseInt(idComune));

			rs = pst.executeQuery();
			int i = 0;

			if ( rs.next() )
			{
				ret[0] = rs.getString("esito_check")				;
				ret[1] = rs.getString("esito_error")				;
			}

		}catch(LoginRequiredException e)
		{

			throw e ;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			GestoreConnessioni.freeConnection(db);
		}

		return ret;

	}
	
	public static int getMaxRevMl()
	{
		int output = -1; 
		Connection db = null; 
		try
		{
			db = GestoreConnessioni.getConnection()	;
			 
			PreparedStatement pst = db.prepareStatement("select * from get_max_rev_ml()");
			ResultSet rs = pst.executeQuery();
			if (rs.next()){
				output = rs.getInt(1);
			}
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			GestoreConnessioni.freeConnection(db);
		}
		 
		return output;
	}
	
}
