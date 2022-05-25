/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.oia.base;

import java.lang.reflect.Field; 
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.dpat2019.base.DpatStrumentoCalcoloNominativi;
import org.aspcfs.modules.dpat2019.base.DpatStrumentoCalcoloNominativiList;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.vigilanza.base.ComponenteNucleoIspettivoList;
import org.aspcfs.modules.vigilanza.blocchicu.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.LookupListList;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

public class OiaNodo extends GenericBean {

	
	public static final int AREA_A = 1;
	private static final long serialVersionUID = -1037354311030631984L;

	public static final int STATO_DEFINITIVO=2;
	public static final int STATO_TEMPORANEO=1;
	
	
	private int sommaUiArea;
	private int sommaUiAreaInizialeBloccata ;
	private static final int INT		= Types.INTEGER;
	private static final int STRING		= Types.VARCHAR;
	private static final int DOUBLE		= Types.DOUBLE;
	private static final int FLOAT		= Types.FLOAT;
	private static final int TIMESTAMP	= Types.TIMESTAMP;
	private static final int DATE		= Types.DATE;
	private static final int BOOLEAN	= Types.BOOLEAN;
	private int anno ; 
	private ComponenteNucleoIspettivoList listaComponentiNucleoIspettivo = new ComponenteNucleoIspettivoList();
	
	private int tipologia_operatore = 6 ;
	private int idLookupAreaStruttura;
	private int 	id;
	private int 	id_padre;
	private int 	id_asl;		
	private String 		asl_stringa;					
	private String 	descrizione_lunga;
	private int 	n_livello;								// Il dominio e' : 1,2,3
	private int orgId ;

	private String 	descrizione_tipologia_struttura;
	private int tipologia_struttura;
	
	private String descrizionePadre = "" ;
	private int siteId ;
	private boolean enabled ;
	private boolean confermato;
	private boolean obsoleto;
	
	private int idStrumentoCalcolo ;
	private int caricoLavoroAnnuo ;
	private String fattoriIncidentiSuCarico ;
	private int percentualeDaSottrarre ;
	private int caricoLavoroEffettivo ;
	
	private DpatStrumentoCalcoloNominativiList listaNominativi = new DpatStrumentoCalcoloNominativiList();
	private HashMap<Integer, Boolean> competenzeIndicatori = new HashMap<Integer, Boolean> ();
	private ArrayList<OiaNodo> lista_nodi = new ArrayList<OiaNodo>();

	
	private Timestamp	entered;
	private int			entered_by;
	private Timestamp	modified;
	private int			modified_by;
	private Timestamp	trashed_date;
	
	private int codiceInternoFK;
	
	private Timestamp dataScadenza ;
	
	private int stato=-1 ;
	private String descrizioneAreaStruttureComplesse ;
	
	private int uba_ui ;
	private int idUtenteEdit ;
	
	private int percentuale_area_a ;
	
	private int stato_all2 ;
	private int stato_all6 ;
	private int codiceInternoUnivoco ;
	
	
	
	public int getCodiceInternoUnivoco() {
		return codiceInternoUnivoco;
	}
	public void setCodiceInternoUnivoco(int codiceInternoUnivoco) {
		this.codiceInternoUnivoco = codiceInternoUnivoco;
	}
	public int getStato_all2() {
		return stato_all2;
	}
	public void setStato_all2(int stato_all2) {
		this.stato_all2 = stato_all2;
	}
	public int getStato_all6() {
		return stato_all6;
	}
	public void setStato_all6(int stato_all6) {
		this.stato_all6 = stato_all6;
	}
	public int getIdUtenteEdit() {
		return idUtenteEdit;
	}
	public void setIdUtenteEdit(int idUtenteEdit) {
		this.idUtenteEdit = idUtenteEdit;
	}
	public int getSommaUiArea() {
		return sommaUiArea;
	}
	public void setSommaUiArea(int sommaUiArea) {
		this.sommaUiArea = sommaUiArea;
	}
	public int getSommaUiAreaInizialeBloccata() {
		return sommaUiAreaInizialeBloccata;
	}
	public void setSommaUiAreaInizialeBloccata(int sommaUiAreaInizialeBloccata) {
		this.sommaUiAreaInizialeBloccata = sommaUiAreaInizialeBloccata;
	}
	public int getUba_ui() {
		return uba_ui;
	}
	public void setUba_ui(int uba_ui) {
		this.uba_ui = uba_ui;
	}
	public String getDescrizioneAreaStruttureComplesse() {
		return descrizioneAreaStruttureComplesse;
	}
	public void setDescrizioneAreaStruttureComplesse(String descrizioneAreaStruttureComplesse) {
		this.descrizioneAreaStruttureComplesse = descrizioneAreaStruttureComplesse;
	}
	public Timestamp getDataScadenza() {
		return dataScadenza;
	}
	public void setDataScadenza(Timestamp dataScadenza) {   
		this.dataScadenza = dataScadenza;
	}
	
	
	
	public int getIdLookupAreaStruttura() {
		return idLookupAreaStruttura;
	}
	public void setIdLookupAreaStruttura(int idLookupAreaStruttura) {
		this.idLookupAreaStruttura = idLookupAreaStruttura;
	}
	
	


	public int getPercentuale_area_a() {
		return percentuale_area_a;
	}
	public void setPercentuale_area_a(int percentuale_area_a) {
		this.percentuale_area_a = percentuale_area_a;
	}




	private int tipologia;
	
	
	
	public int getTipologia() {
		return tipologia;
	}
	public void setTipologia(int tipologia) {
		this.tipologia = tipologia;
	}
	public int getAnno() {
		return anno;
	}
	public void setAnno(int anno) {
		this.anno = anno;
	}
	public int getStato() {
		return stato;
	}
	public void setStato(int stato) {
		this.stato = stato;
	}
	public void setDataScadenza(String dataScadenza) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			this.dataScadenza = new Timestamp (sdf.parse(dataScadenza).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int getCodiceInternoFK() {
		return codiceInternoFK;
	}
	public void setCodiceInternoFK(int codiceInternoFK) {
		this.codiceInternoFK = codiceInternoFK;
	}
	public int getIdStrumentoCalcolo() {
		return idStrumentoCalcolo;
	}
	public void setIdStrumentoCalcolo(int idStrumentoCalcolo) {
		this.idStrumentoCalcolo = idStrumentoCalcolo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_padre() {
		return id_padre;
	}

	public void setId_padre(int idPadre) {
		id_padre = idPadre;
	}
	public int getId_asl() {
		return id_asl;
	}
	public void setId_asl(int idAsl) {
		id_asl = idAsl;
	}
	
	public String getDescrizione_lunga() {
		return descrizione_lunga;
	}
	public void setDescrizione_lunga(String descrizioneLunga) {
		descrizione_lunga = descrizioneLunga;
	}
	public int getN_livello() {
		return n_livello;
	}
	public void setN_livello(int nLivello) {
		n_livello = nLivello;
	}



	public Timestamp getEntered() {
		return entered;
	}
	public void setEntered(Timestamp entered) {
		this.entered = entered;
	}
	public int getEntered_by() {
		return entered_by;
	}
	public void setEntered_by(int enteredBy) {
		entered_by = enteredBy;
	}
	public Timestamp getModified() {
		return modified;
	}
	public void setModified(Timestamp modified) {
		this.modified = modified;
	}
	public int getModified_by() {
		return modified_by;
	}
	public void setModified_by(int modifiedBy) {
		modified_by = modifiedBy;
	}
	public Timestamp getTrashed_date() {
		return trashed_date;
	}
	public void setTrashed_date(Timestamp trashedDate) {
		trashed_date = trashedDate;
	}

	// GETTER e SETTER PER ATTRIBUTI NON IN TABELLA.....
	public String getAsl_stringa() {
		return asl_stringa;
	}

	public void setAsl_stringa(String aslStringa) {
		asl_stringa = aslStringa;
	}



	public int getTipologia_struttura() {
		return tipologia_struttura;
	}


	public void setTipologia_struttura(int tipologia_struttura) {
		this.tipologia_struttura = tipologia_struttura;
	}


	public String getDescrizione_tipologia_struttura() {
		return descrizione_tipologia_struttura;
	}


	public void setDescrizione_tipologia_struttura(
			String descrizione_tipologia_struttura) {
		this.descrizione_tipologia_struttura = descrizione_tipologia_struttura;
	}

	public boolean isConfermato() {
		return confermato;
	}

	public void setConfermato(boolean confermato) {
		this.confermato = confermato;
	}

	public boolean isObsoleto() {
		return obsoleto;
	}

	public void setObsoleto(boolean obsoleto) {
		this.obsoleto = obsoleto;
	}
	
	
	public String getDescrizionePadre() {
		return descrizionePadre;
	}

	public ComponenteNucleoIspettivoList getListaComponentiNucleoIspettivo() {
		return listaComponentiNucleoIspettivo;
	}

	public void setListaComponentiNucleoIspettivo(
			ComponenteNucleoIspettivoList listaComponentiNucleoIspettivo) {
		this.listaComponentiNucleoIspettivo = listaComponentiNucleoIspettivo;
	}

	public void setDescrizionePadre(String descrizionePadre) {
		this.descrizionePadre = descrizionePadre;
	}

	public HashMap<Integer, Boolean> getCompetenzeIndicatori() {
		return competenzeIndicatori;
	}

	public void setCompetenzeIndicatori(
			HashMap<Integer, Boolean> competenzeIndicatori) {
		this.competenzeIndicatori = competenzeIndicatori;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	
	public int getTipologia_operatore() {
		return tipologia_operatore;
	}

	public void setTipologia_operatore(int tipologia_operatore) {
		this.tipologia_operatore = tipologia_operatore;
	}

	
	
	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	


	public ArrayList<OiaNodo> getLista_nodi() {
		return lista_nodi;
	}


	public void setLista_nodi(ArrayList<OiaNodo> lista_nodi) {
		if(lista_nodi!=null)
			this.lista_nodi = lista_nodi;
	}

	

	
	public int getCaricoLavoroAnnuo() {
		return caricoLavoroAnnuo;
	}
	public void setCaricoLavoroAnnuo(int caricoLavoroAnnuo) {
		this.caricoLavoroAnnuo = caricoLavoroAnnuo;
	}
	public String getFattoriIncidentiSuCarico() {
		return fattoriIncidentiSuCarico;
	}
	public void setFattoriIncidentiSuCarico(String fattoriIncidentiSuCarico) {
		this.fattoriIncidentiSuCarico = fattoriIncidentiSuCarico;
	}
	public int getPercentualeDaSottrarre() {
		return percentualeDaSottrarre;
	}
	public void setPercentualeDaSottrarre(int percentualeDaSottrarre) {
		this.percentualeDaSottrarre = percentualeDaSottrarre;
	}
	public int getCaricoLavoroEffettivo() {
		return caricoLavoroEffettivo;
	}
	public void setCaricoLavoroEffettivo(int caricoLavoroEffettivo) {
		this.caricoLavoroEffettivo = caricoLavoroEffettivo;
	}
	public DpatStrumentoCalcoloNominativiList getListaNominativi() {
		return listaNominativi;
	}
	public void setListaNominativi(DpatStrumentoCalcoloNominativiList listaNominativi) {
		this.listaNominativi = listaNominativi;
	}
	public OiaNodo (){}

	public String getName()
	{
		return descrizione_lunga;
	}
	
	

	public  void loadResultSet(ResultSet rs) throws SQLException
	{
		this.setIdLookupAreaStruttura(rs.getInt("id_lookup_area_struttura_asl"));
		this.setIdStrumentoCalcolo(rs.getInt("id_strumento_calcolo"));
		this.setId(rs.getInt("id"));
		
		this.setId_padre(rs.getInt("id_padre"));
		this.setId_asl(rs.getInt("id_asl"));
		this.setIdAsl(rs.getInt("id_asl"));
		this.setSiteId(rs.getInt("id_asl"));
		this.setN_livello(rs.getInt("n_livello"));
		this.setTipologia_struttura(rs.getInt("tipologia_struttura"));
		this.setDescrizione_lunga(rs.getString("descrizione_struttura"));
		this.setCodiceInternoFK(rs.getInt("codice_interno_fk"));
		this.setDataScadenza(rs.getTimestamp("data_scadenza"));
		try
		{
			this.setAnno(rs.getInt("anno"));
			this.setDescrizionePadre(rs.getString("descrizione_padre"));
			
		}
		catch(SQLException e)
		{
			
		}
		
		try
		{
			this.setPercentuale_area_a(rs.getInt("percentuale_area_a"));
		}
		catch(SQLException e)
		{
			
		}
		
		try
		{
			this.setTipologia(rs.getInt("tipologia"));
			this.setOrgId(rs.getInt("org_id"));
		}
		catch(SQLException e1)
		{
			
		}
		
		
		try
		{
			this.setIdUtenteEdit(rs.getInt("id_utente_edit"));
		}
		catch(SQLException e)
		{
			
		}
		
		try
		{
			this.setSommaUiArea(rs.getInt("somma_ui_area"));
			this.setSommaUiAreaInizialeBloccata(rs.getInt("ui_struttura_foglio_att_iniziale"));
			
		}
		catch(SQLException e)
		{
			
		}
		
		try
		{
			setStato(rs.getInt("stato"));
		}
		catch(SQLException e)
		{
			
		}
		try
		{
			setStato_all2(rs.getInt("stato_all2"));
		}
		catch(SQLException e)
		{
			
		}
		
		try
		{
			setStato_all6(rs.getInt("stato_all6"));
		}
		catch(SQLException e)
		{
			
		}
		try
		{
			descrizioneAreaStruttureComplesse=rs.getString("descrizione_area_struttura");
		}
		catch(SQLException e)
		{
			
		}
		this.setDescrizione_tipologia_struttura(rs.getString("descrizione_tipologia_struttura"));
		
		try
		{
			this.setCodiceInternoUnivoco(rs.getInt("codice_interno_univoco"));
		}
		catch(SQLException e)
		{
			
		}
		
		
	}
	
	

	public ArrayList<OiaNodo> loadbyidAsl(String id,int anno, Connection db)
	{

		ArrayList<OiaNodo>				ret		=  new ArrayList<OiaNodo>();
		PreparedStatement	stat	= null;
		ResultSet			res		= null;

		if( (id != null) && ! id.equals("") )
		{
			try
			{
				int iid = Integer.parseInt( id );
				
				String sql = "select distinct on (a.codice_interno_fk) a.codice_interno_fk as cifk  ,a.* from ( ";
						 

				 sql +="SELECT distinct  oia_n.*,regexp_replace(oia_n.descrizione_struttura, E'[\\n\\r]+', ' ', 'g' ) as descrizione_struttura, asl.short_description as asl_stringa" +
				",o.org_id,tipooia.code as tipologia_struttura,tipooia.description as descrizione_tipologia_struttura,o.tipologia " +
				"FROM dpat_strutture_asl oia_n " +
				"LEFT JOIN organization o on (oia_n.id_asl = o.site_id and o.tipologia =6) " +
				"LEFT JOIN lookup_tipologia_nodo_oia tipooia ON oia_n.tipologia_struttura = tipooia.code " +
				"LEFT JOIN lookup_site_id asl ON oia_n.id_asl = asl.code " +
				"WHERE   oia_n.n_livello =1 and oia_n.stato =" +STATO_DEFINITIVO+
				"AND asl.enabled=true and disabilitato=false  ";

				if (Integer.parseInt(id)>0)
				{
					sql+=" AND asl.code = " +id;
				
				}
				
				 sql +=")a ORDER BY a.codice_interno_fk, a.data_scadenza " ;

				stat	= db.prepareStatement( sql );
				
				res=stat.executeQuery();
			

				while( res.next() )
				{
					OiaNodo n = new OiaNodo();
					n.loadResultSet( res ) ;
					if(n.n_livello<=3)
						load_figli(n,anno,db);
					ret.add(n);
				}

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					if( res != null )
					{
						res.close();
						res = null;
					}

					if( stat != null )
					{
						stat.close();
						stat = null;
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}

		return ret;

	}
	
	
	public  ArrayList<OiaNodo> loadbyidRegione(int idregione, Connection db)
	{

		ArrayList<OiaNodo>				ret		=  new ArrayList<OiaNodo>();
		PreparedStatement	stat	= null;
		ResultSet			res		= null;

		
			try
			{

				String sql ="SELECT distinct  oia_n.*,regexp_replace(oia_n.descrizione_lunga, E'[\\n\\r]+', ' ', 'g' ) as descrizione_struttura, asl.short_description as asl_stringa" +
				",o.org_id,tipooia.code as tipologia_struttura,tipooia.description as descrizione_tipologia_struttura " +
				"FROM oia_nodo oia_n " +
				"LEFT JOIN organization o on (oia_n.id_asl = o.site_id and o.tipologia =6) " +
				"LEFT JOIN lookup_tipologia_nodo_oia tipooia ON oia_n.tipologia_struttura = tipooia.code " +
				"LEFT JOIN lookup_site_id asl ON oia_n.id_asl = asl.code " +
				"WHERE  oia_n.n_livello =0 AND oia_n.id = "+idregione+" and id_asl =14  order by oia_n.descrizione_lunga ";

				stat	= db.prepareStatement( sql );

				res		= stat.executeQuery();
				while( res.next() )
				{
					OiaNodo n = new OiaNodo();
					n.loadResultSet( res ) ;
					if(n.n_livello<=3)
						load_figli(n,-1,db);
					ret.add(n);

				}

				
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					if( res != null )
					{
						res.close();
						res = null;
					}

					if( stat != null )
					{
						stat.close();
						stat = null;
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			
		}

		return ret;

	}
	
	public  ArrayList<OiaNodo> loadbyRegione(Connection db)
	{

		ArrayList<OiaNodo>				ret		=  new ArrayList<OiaNodo>();
		PreparedStatement	stat	= null;
		ResultSet			res		= null;

		
			try
			{

				String sql ="SELECT distinct  oia_n.*,regexp_replace(oia_n.descrizione_lunga, E'[\\n\\r]+', ' ', 'g' ) as descrizione_struttura, asl.short_description as asl_stringa" +
				",o.org_id,tipooia.code as tipologia_struttura,tipooia.description as descrizione_tipologia_struttura " +
				"FROM oia_nodo oia_n " +
				"LEFT JOIN organization o on (oia_n.id_asl = o.site_id and o.tipologia =6) " +
				"LEFT JOIN lookup_tipologia_nodo_oia tipooia ON oia_n.tipologia_struttura = tipooia.code " +
				"LEFT JOIN lookup_site_id asl ON oia_n.id_asl = asl.code " +
				"WHERE  oia_n.n_livello =0 and id_asl =14  order by oia_n.descrizione_lunga ";

				stat	= db.prepareStatement( sql );

				res		= stat.executeQuery();
				while( res.next() )
				{
					OiaNodo n = new OiaNodo();
					n.loadResultSet( res ) ;
					if(n.n_livello<=3)
						load_figli(n,-1,db);
					ret.add(n);

				}

				
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					if( res != null )
					{
						res.close();
						res = null;
					}

					if( stat != null )
					{
						stat.close();
						stat = null;
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			
		}

		return ret;

	}
	
	
	
	public  OiaNodo load_figli( OiaNodo oia_n,int anno , Connection db )
	{
		ArrayList<OiaNodo>	ret	= new ArrayList<OiaNodo>();
		PreparedStatement	stat= null;
		ResultSet			res	= null;

		String sql ="SELECT distinct oia_n.*,regexp_replace(oia_n.descrizione_struttura, E'[\\n\\r]+', ' ', 'g' ) as descrizione_struttura ,asl.short_description as asl_stringa," +
		" tipooia.code as tipologia_struttura,tipooia.description as descrizione_tipologia_struttura" +
		" FROM dpat_strutture_asl oia_n " +
		"left join  dpat_strutture_asl oia_n_p on oia_n.id_padre = oia_n_p.id " +
//		"LEFT JOIN organization o on (oia_n.id_asl = o.site_id and o.tipologia =6) " +
		"LEFT JOIN lookup_site_id asl ON oia_n.id_asl = asl.code AND asl.enabled=true " +
		"LEFT JOIN lookup_tipologia_nodo_oia tipooia ON oia_n.tipologia_struttura = tipooia.code " +
		"WHERE  oia_n.id_padre= ? and oia_n.stato=2 and oia_n.disabilitato=false "+ ((anno>0) ? " and oia_n.id_asl = ?  and oia_n.anno = ? " : "and oia_n.id_asl =14" );
		 
		
	
		sql += " order by oia_n.descrizione_struttura ";

		try
		{
			stat = db.prepareStatement( sql );
			stat.setInt( 1,oia_n.getCodiceInternoFK()  );
			if (anno>0)
			{
				stat.setInt(2, oia_n.getIdAsl());
				stat.setInt(3, anno);
			}
					
			res		= stat.executeQuery();
			while( res.next() )
			{
				OiaNodo oia_n_f = new OiaNodo();
				oia_n_f.loadResultSet( res ) ;

				load_figli( oia_n_f,anno,  db );
				ret.add( oia_n_f )	;
				oia_n.setLista_nodi(ret);

			}
		}
		catch (Exception e)
		{ 
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if( res != null )
				{
					res.close();
					res = null;
				}

				if( stat != null )
				{
					stat.close();
					stat = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		return oia_n;
	}
	





	
	
	
	public void aggiornaDatiStruttura(Connection db,boolean isFlagDpatCongelato,int coefficienteUba)
	{
		PreparedStatement pst = null ;
		try
		{
			
			
//			if (stato==2)
//			{
//				Timestamp time = new Timestamp(System.currentTimeMillis());
//				this.setDataScadenza(time);
//				this.update(db, 1);
//				this.insert(db);
//				wait(2000);
//			}
			
			String insert ="update oia_nodo set "
					+ " carico_lavoro_annuo = ? ,carico_lavoro_effettivo = ? , fattori_incidenti_su_carico = regexp_replace(?, E'[\\n\\r]+', ' ', 'g' ) ,percentuale_da_sottrarre= ?,modified = current_timestamp , modified_by = ?,uba_ui=? "
					+ "where id = ? ";

			
			this.uba_ui =(caricoLavoroEffettivo*coefficienteUba) ;
			pst = db.prepareStatement(insert);
			pst.setInt(1,caricoLavoroAnnuo );
			pst.setInt(2,caricoLavoroEffettivo );
			pst.setString(3, fattoriIncidentiSuCarico);
			pst.setInt(4, percentualeDaSottrarre);
			pst.setInt(5,modified_by );
			pst.setInt(6,uba_ui);
			pst.setInt(7,id );
			pst.execute();
			
			
			String update  = "select * from public_functions.dbi_dpat_aggiorna_carico_lavoro_su_area( ?)";
			PreparedStatement pst1 = db.prepareStatement(update);
			
			if (this.getId_padre()>0)
				pst1.setInt(1, this.getId_padre());
			else
				pst1.setInt(1, id);
			pst1.execute();
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public ArrayList<OiaNodo> getStruttureAutoritaCompetentiDaControllare(Connection db,int orgId)
	{
ArrayList<OiaNodo> listaStrutture = new ArrayList<OiaNodo>();

PreparedStatement pst = null;
		ResultSet rs = null;
		try
		{
				
			
			Calendar calCorrente = GregorianCalendar.getInstance();
			Date dataCorrente = new Date(System.currentTimeMillis());
			int tolleranzaGiorni = Integer.parseInt(ApplicationProperties.getProperty("TOLLERANZA_MODIFICA_CU"));
			dataCorrente.setDate(dataCorrente.getDate()- tolleranzaGiorni);
			calCorrente.setTime(new Timestamp(dataCorrente.getTime()));
			int anno = calCorrente.get(Calendar.YEAR);
				
				String t = "(select oia_nodo.*,o.org_id,o.tipologia,tipooia.description as descrizione_tipologia_struttura " +
						" from dpat_strutture_asl oia_nodo join organization o on o.site_id = oia_nodo.id_asl and o.tipologia=6 "
						+ " LEFT JOIN lookup_tipologia_nodo_oia tipooia ON oia_nodo.tipologia_struttura = tipooia.code  " +
						" where n_livello !=3  and coalesce(oia_nodo.tipologia_struttura,0)!=39 and coalesce(oia_nodo.tipologia_struttura,0) !=14   and n_livello !=1 and (oia_nodo.disabilitato) = false and (anno ="+anno+" or anno=-1) and o.org_id ="+orgId +")"		;				
						
				pst = db.prepareStatement(t);
				rs = pst.executeQuery();
				int i = 0;

				while ( rs.next() )
				{
					OiaNodo n = new OiaNodo();
					n.loadResultSet(rs);
					listaStrutture.add(n);

				}
			

		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		
		return listaStrutture  ;
	}
	
	
	
	private int getPesoUiQualifica(Connection db  , int idQualifica)
	{
		int peso = 0 ;
		try
		{

			Calendar calCorrente = GregorianCalendar.getInstance();
			Date dataCorrente = new Date(System.currentTimeMillis());
			calCorrente.setTime(new Timestamp(dataCorrente.getTime()));
			int anno_corrente = calCorrente.get(Calendar.YEAR);
			
			
			if (idLookupAreaStruttura<=0)
			{
			PreparedStatement pst1 = db.prepareStatement("select id_lookup_area_struttura_asl from oia_nodo where id = "+id_padre);
			ResultSet rs1 = pst1.executeQuery();
			if (rs1.next())
				idLookupAreaStruttura = rs1.getInt(1);
			}
			
			PreparedStatement pst = db.prepareStatement("select peso_per_somma_ui from lookup_qualifiche where code = ?");
			pst.setInt(1, idQualifica);
			ResultSet rs = pst.executeQuery();
			if (rs.next())
			{
				peso = rs.getInt(1);
				
				
				if (peso>0 && idLookupAreaStruttura==AREA_A && percentuale_area_a>0) // per la sanita animale la percentuale di detrazione puo essere diversa , per cui lapesco dal db
					peso=this.getPercentuale_area_a();
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return peso ;
	}
	

	public void update(Connection db,int tipoOperazione) throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		String sql = "UPDATE oia_nodo SET data_scadenza = ?,modified_by=?,modified=current_timestamp where id = ? ";
		
		
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setTimestamp(1,dataScadenza);
		pst.setInt(2, modified_by);

		pst.setInt(3, id);
		pst.execute();
				
		
	}
	
	
	
	
	public int calcolaStato(Connection db) throws SQLException 
	{
		String sql = "select max(stato) from dpat_Strutture_asl where id_strumento_calcolo = ?";
		int stato = 1 ;
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setInt(1, idStrumentoCalcolo);
		ResultSet rs =pst.executeQuery();
		if (rs.next())
			stato = rs.getInt(1);
		
		stato = stato%2 ;
		
		return stato ;
	}
	

	public void insert( Connection db ) throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		
		int statoCalcolato = 1 ;
		
//		if (stato>=0)
//			statoCalcolato =stato%2 ;
//		else
//			statoCalcolato=calcolaStato(db);
		
		this.entered	= new Timestamp( System.currentTimeMillis() );
		this.modified	= this.entered;
		int i = 0 ;
		String sql = "INSERT INTO oia_nodo(id, id_padre, id_asl, descrizione_lunga, n_livello, entered, entered_by, modified, modified_by, trashed_date, tipologia_struttura, " +
             " id_strumento_calcolo, " +
            "carico_lavoro_annuo, percentuale_da_sottrarre, carico_lavoro_effettivo,fattori_incidenti_su_carico,codice_interno_fk,stato,anno,codice_interno_univoco)" +
            "VALUES (?, ?, ?, regexp_replace(?, E'[\\n\\r]+', ' ', 'g' ),  ?, current_timestamp, ?, current_timestamp, ?, null::timestamp, ?, ?, ?, ?,?,regexp_replace(?, E'[\\n\\r]+', ' ', 'g' ),?,?,?,?);";
		
		PreparedStatement pst = db.prepareStatement(sql);
		int idPK = DatabaseUtils.getNextInt(db, "oia_nodo", "id", 1);
		this.id = idPK;
		pst.setInt(++i, idPK);
		pst.setInt(++i,id_padre);	
		pst.setInt(++i,idAsl);	
		pst.setString(++i,descrizione_lunga);	
		pst.setInt(++i,n_livello);	
		pst.setInt(++i,entered_by);	
		pst.setInt(++i,modified_by);	
		pst.setInt(++i,tipologia_struttura);
		pst.setInt(++i,idStrumentoCalcolo);
		pst.setInt(++i,caricoLavoroAnnuo);	
		pst.setInt(++i,percentualeDaSottrarre);	
		pst.setInt(++i,caricoLavoroEffettivo);	
		pst.setString(++i,fattoriIncidentiSuCarico);	
		if (codiceInternoFK<=0)
			pst.setInt(++i,idPK);	
		else
			pst.setInt(++i,codiceInternoFK);
		pst.setInt(++i,stato);	
		pst.setInt(++i,anno);	
		if (codiceInternoUnivoco<=0)
		{
			pst.setInt(++i,idPK);	
			codiceInternoUnivoco = idPK;
			
		}
		else
			pst.setInt(++i,codiceInternoUnivoco);

		pst.execute();

		
	}
	
	public void congelaStruttura(Connection db) throws SQLException
	{
		
		
		String update ="";
		
		if (this.getTipologia_struttura()!=13 && this.getTipologia_struttura()!=14)
			update= "update oia_nodo set stato = 2,ui_struttura_foglio_att_iniziale = carico_lavoro_effettivo where id = ?" ;
		else
			update= "update oia_nodo set stato = 2,ui_struttura_foglio_att_iniziale = somma_ui_area where id = ?" ;
		PreparedStatement pst = null ;
		pst=  db.prepareStatement(update);
		pst.setInt(1, this.getId());
		pst.execute();
		
	}
	

	
	public OiaNodo (Connection db,int id,SystemStatus system) throws SQLException
	{
		ResultSet rs = this.queryRecord(db, id);
		if (rs.next())
		{	this.loadResultSet(rs);
			listaNominativi.buildList(db, id,system);
		}
		
	}
	
	
	public OiaNodo (Connection db,int id) throws SQLException
	{
		ResultSet rs = this.queryRecord(db, id);
		if (rs.next())
		{	this.loadResultSet(rs);
		}
		
	}
	public ResultSet queryRecord(Connection db,int id)
	{
		PreparedStatement pst = null ;
		ResultSet rs = null ;
		try
		{ 
			String sql ="SELECT v1.*, regexp_replace(v1.descrizione_struttura, E'[\\n\\r]+', ' ', 'g' ) as descrizione_struttura,o.org_id,o.tipologia,tipooia.description as descrizione_tipologia_struttura  " +
					" FROM dpat_strutture_asl v1 join organization o on v1.id_asl = o.site_id and o.tipologia=6 " 
					+ " LEFT JOIN lookup_tipologia_nodo_oia tipooia ON v1.tipologia_struttura = tipooia.code  " +

					"where v1.codice_interno_fk =? and disabilitato = false ;";
			pst = db.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return rs ;
	}
	
	
	
	
	public OiaNodo (Connection db,int id,boolean filtroId) throws SQLException
	{
		ResultSet rs = this.queryRecordId(db, id);
		if (rs.next())
		{	this.loadResultSet(rs);
		}
		
	}
	public ResultSet queryRecordId(Connection db,int id)
	{
		PreparedStatement pst = null ;
		ResultSet rs = null ;
		try
		{ 
			String sql ="SELECT v1.*, regexp_replace(v1.descrizione_struttura, E'[\\n\\r]+', ' ', 'g' ) as descrizione_struttura,o.org_id,o.tipologia,tipooia.description as descrizione_tipologia_struttura " +
					" FROM dpat_strutture_asl v1 join organization o on v1.id_asl = o.site_id and o.tipologia=6 " 
					+ " LEFT JOIN lookup_tipologia_nodo_oia tipooia ON v1.tipologia_struttura = tipooia.code  " +

					"where v1.id =?  ;";
			pst = db.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return rs ;
	}




}
