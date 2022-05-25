/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.tamponi.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.HashMap;
import java.util.Iterator;

import org.aspcfs.modules.accounts.base.OrganizationHistory;
import org.aspcfs.modules.accounts.base.OrganizationList;
import org.aspcfs.modules.actionlist.base.ActionItemLog;
import org.aspcfs.modules.actionplans.base.ActionPlan;
import org.aspcfs.modules.actionplans.base.ActionPlanWorkList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.CustomFieldRecordList;

import org.aspcfs.modules.contacts.base.ContactHistory;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.FileItemList;

public class Ticket extends org.aspcfs.modules.troubletickets.base.Ticket
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6000595345635487713L;


	public Ticket()
	{
		
	}
	
	protected HashMap<Integer, Tampone> listaTamponi=new HashMap<Integer, Tampone>();
	
	
	
	
		

	protected String tipo_richiesta = "";
	protected int tipologia = -1;
	//protected String dati_extra = "";
	protected String pippo = "";
	protected int tipoTampone = -1;
	protected int esitoTampone = -1;
	protected int destinatarioTampone= -1;
	protected Timestamp dataAccettazione = null;
	protected String dataAccettazioneTimeZone = null;
	
	/* aggiunti da d.dauria */
	private boolean alimentiOrigineAnimale = false;
	private boolean alimentiOrigineVegetale = false;
	private boolean alimentiComposti = false;
	private int alimentiOrigineAnimaleNonTrasformati = -1;
	private int alimentiOrigineAnimaleTrasformati = -1;
	private int alimentiOrigineVegetaleValori = -1;
	private int alimentiOrigineAnimaleNonTrasformatiValori = -1;
	private boolean allerta = false;
	private boolean nonConformita = false;
	private int conseguenzePositivita = -1;
	private int responsabilitaPositivita = -1;
	private String noteEsito = null;
	private int nucleoIspettivo = -1;
	private int nucleoIspettivoDue = -1;
	private int nucleoIspettivoTre = -1;
	private String componenteNucleo = null;
	private String componenteNucleoDue = null;
	private String componenteNucleoTre = null;
	private String testoAppoggio = "";
	private String testoAlimentoComposto = null;
	private int nucleoIspettivoQuattro = -1;
	private int nucleoIspettivoCinque = -1;
	private int nucleoIspettivoSei = -1;
	private int nucleoIspettivoSette = -1;
	private int nucleoIspettivoOtto = -1;
	private int nucleoIspettivoNove = -1;
	private int nucleoIspettivoDieci = -1;
	private String componenteNucleoQuattro = null;
	private String componenteNucleoCinque = null;
	private String componenteNucleoSei = null;
	private String componenteNucleoSette = null;
	private String componenteNucleoOtto = null;
	private String componenteNucleoNove = null;
	private String componenteNucleoDieci = null;
	private int punteggio = 0;
	private String idControlloUfficiale = null;
	private String identificativo = null;
	private int tamponi=-1;
	private int ricercaTamponi=-1;
	
	
	public int getTamponi() {
		return tamponi;
	}
	public void setTamponi(int tamponi) {
		this.tamponi = tamponi;
	}
	public int getRicercaTamponi() {
		return ricercaTamponi;
	}
	public void setRicercaTamponi(int ricercaTamponi) {
		this.ricercaTamponi = ricercaTamponi;
	}
	//fine campi
	public void setPunteggio(String temp)
	{
		this.punteggio = Integer.parseInt(temp);
	}
	public void setPunteggio(int temp)
	{
		this.punteggio = temp;
	}
	public int getPunteggio() {
		return punteggio;
	}
	
	public String getIdentificativo() {
		return identificativo;
	}
	
	public void setIdentificativo(String identificativo) {
		this.identificativo = identificativo;
	}
	public String getIdControlloUfficiale() {
		return idControlloUfficiale;
	}

	public void setIdControlloUfficiale(String idControlloUfficiale) {
		this.idControlloUfficiale = idControlloUfficiale;
	}
	public void setNucleoIspettivoQuattro(String temp)
	{
		this.nucleoIspettivoQuattro = Integer.parseInt(temp);
	}
	
	public void setNucleoIspettivoCinque(String temp)
	{
		this.nucleoIspettivoCinque = Integer.parseInt(temp);
	}
	
	public void setNucleoIspettivoSei(String temp)
	{
		this.nucleoIspettivoSei = Integer.parseInt(temp);
	}
	
	public void setNucleoIspettivoSette(String temp)
	{
		this.nucleoIspettivoSette = Integer.parseInt(temp);
	}
	
	public void setNucleoIspettivoOtto(String temp)
	{
		this.nucleoIspettivoOtto = Integer.parseInt(temp);
	}
	
	public void setNucleoIspettivoNove(String temp)
	{
		this.nucleoIspettivoNove = Integer.parseInt(temp);
	}
	
	public void setNucleoIspettivoDieci(String temp)
	{
		this.nucleoIspettivoDieci = Integer.parseInt(temp);
	}
	
	
	public void setNucleoIspettivo(String temp)
	{
		this.nucleoIspettivo = Integer.parseInt(temp);
	}
	
	public void setNucleoIspettivoDue(String temp)
	{
		this.nucleoIspettivoDue = Integer.parseInt(temp);
	}
	
	public void setNucleoIspettivoTre(String temp)
	{
		this.nucleoIspettivoTre = Integer.parseInt(temp);
	}
	
	public int getNucleoIspettivo() {
		return nucleoIspettivo;
	}



	public void setNucleoIspettivo(int nucleoIspettivo) {
		this.nucleoIspettivo = nucleoIspettivo;
	}



	public int getNucleoIspettivoDue() {
		return nucleoIspettivoDue;
	}



	public void setNucleoIspettivoDue(int nucleoIspettivoDue) {
		this.nucleoIspettivoDue = nucleoIspettivoDue;
	}



	public int getNucleoIspettivoTre() {
		return nucleoIspettivoTre;
	}



	public void setNucleoIspettivoTre(int nucleoIspettivoTre) {
		this.nucleoIspettivoTre = nucleoIspettivoTre;
	}



	public String getComponenteNucleo() {
		return componenteNucleo;
	}



	public void setComponenteNucleo(String componenteNucleo) {
		this.componenteNucleo = componenteNucleo;
	}



	public String getComponenteNucleoDue() {
		return componenteNucleoDue;
	}



	public void setComponenteNucleoDue(String componenteNucleoDue) {
		this.componenteNucleoDue = componenteNucleoDue;
	}



	public String getComponenteNucleoTre() {
		return componenteNucleoTre;
	}



	public void setComponenteNucleoTre(String componenteNucleoTre) {
		this.componenteNucleoTre = componenteNucleoTre;
	}



	public String getNoteEsito() {
		return noteEsito;
	}



	public void setNoteEsito(String noteEsito) {
		this.noteEsito = noteEsito;
	}
	
	



	public void setConseguenzePositivita(String temp)
	{
		this.conseguenzePositivita = Integer.parseInt(temp);
	}
	

	
	public void setNonConformita(String tmp){
        this.nonConformita = DatabaseUtils.parseBoolean(tmp);	
    }
	
	public void setAllerta(String tmp){
        this.allerta = DatabaseUtils.parseBoolean(tmp);	
    }
	
	
	public void setResponsabilitaPositivita(String temp)
	{
		this.responsabilitaPositivita = Integer.parseInt(temp);
	}
	
	public boolean getAllerta()
	{
		return allerta;
	}
	
	public boolean getNonConformita()
	{
		return nonConformita;
	}
	
	public boolean isAllerta() {
		return allerta;
	}

	public void setAllerta(boolean allerta) {
		this.allerta = allerta;
	}

	public boolean isNonConformita() {
		return nonConformita;
	}

	public void setNonConformita(boolean nonConformita) {
		this.nonConformita = nonConformita;
	}

	public int getConseguenzePositivita() {
		return conseguenzePositivita;
	}

	public void setConseguenzePositivita(int conseguenzePositivita) {
		this.conseguenzePositivita = conseguenzePositivita;
	}

	public int getResponsabilitaPositivita() {
		return responsabilitaPositivita;
	}

	public void setResponsabilitaPositivita(int responsabilitaPositivita) {
		this.responsabilitaPositivita = responsabilitaPositivita;
	}

	public int getAlimentiOrigineAnimaleNonTrasformatiValori() {
		return alimentiOrigineAnimaleNonTrasformatiValori;
	}

	public void setAlimentiOrigineAnimaleNonTrasformatiValori(int alimentiOrigineAnimaleNonTrasformatiValori) {
		this.alimentiOrigineAnimaleNonTrasformatiValori = alimentiOrigineAnimaleNonTrasformatiValori;
	}

	public void setAlimentiOrigineAnimaleNonTrasformati(String temp)
	{
		this.alimentiOrigineAnimaleNonTrasformati = Integer.parseInt(temp);
	}
	
	public void setAlimentiOrigineAnimaleTrasformati(String temp)
	{
		this.alimentiOrigineAnimaleTrasformati = Integer.parseInt(temp);
	}
	
	public void setAlimentiOrigineVegetaleValori(String temp)
	{
		this.alimentiOrigineVegetaleValori = Integer.parseInt(temp);
	}
	
	public void setAlimentiOrigineAnimaleNonTrasformatiValori(String temp)
	{
		this.alimentiOrigineAnimaleNonTrasformatiValori = Integer.parseInt(temp);
	}
	
	public void setAlimentiOrigineAnimale(String tmp){
	         this.alimentiOrigineAnimale = DatabaseUtils.parseBoolean(tmp);	
	}
	
	public void setAlimentiComposti(String tmp){
        this.alimentiComposti = DatabaseUtils.parseBoolean(tmp);	
    }
	
	public void setAlimentiOrigineVegetale(String tmp){
        this.alimentiOrigineVegetale = DatabaseUtils.parseBoolean(tmp);	
    }
	
	
	public boolean isAlimentiOrigineAnimale() {
		return alimentiOrigineAnimale;
	}

	public boolean getAlimentiOrigineAnimale(){
		return alimentiOrigineAnimale;
	}
	
	public void setAlimentiOrigineAnimale(boolean alimentiOrigineAnimale) {
		this.alimentiOrigineAnimale = alimentiOrigineAnimale;
	}

	public boolean isAlimentiOrigineVegetale() {
		return alimentiOrigineVegetale;
	}
 
	public boolean getAlimentiOrigineVegetale(){
		return alimentiOrigineVegetale;
	}
	
	public void setAlimentiOrigineVegetale(boolean alimentiOrigineVegetale) {
		this.alimentiOrigineVegetale = alimentiOrigineVegetale;
	}

	public boolean isAlimentiComposti() {
		return alimentiComposti;
	}

	public boolean getAlimentiComposti(){
		return alimentiComposti;
	}
	
	public void setAlimentiComposti(boolean alimentiComposti) {
		this.alimentiComposti = alimentiComposti;
	}

	public int getAlimentiOrigineAnimaleNonTrasformati() {
		return alimentiOrigineAnimaleNonTrasformati;
	}

	public void setAlimentiOrigineAnimaleNonTrasformati(
			int alimentiOrigineAnimaleNonTrasformati) {
		this.alimentiOrigineAnimaleNonTrasformati = alimentiOrigineAnimaleNonTrasformati;
	}

	public int getAlimentiOrigineAnimaleTrasformati() {
		return alimentiOrigineAnimaleTrasformati;
	}

	public void setAlimentiOrigineAnimaleTrasformati(
			int alimentiOrigineAnimaleTrasformati) {
		this.alimentiOrigineAnimaleTrasformati = alimentiOrigineAnimaleTrasformati;
	}

	public int getAlimentiOrigineVegetaleValori() {
		return alimentiOrigineVegetaleValori;
	}

	public void setAlimentiOrigineVegetaleValori(int alimentiOrigineVegetaleValori) {
		this.alimentiOrigineVegetaleValori = alimentiOrigineVegetaleValori;
	}

	/*fine modifiche apportate da d.dauria */
	
	public String getPippo() {
		return pippo;
	}

	public void setPippo(String pippo) {
		this.pippo = pippo;
	}

	public int getTipologia() {
		return tipologia;
	}

	public void setTipologia(int tipologia) {
		this.tipologia = tipologia;
	}
	
	public void setDataAccettazioneTimeZone(String tmp) {
	    this.dataAccettazioneTimeZone = tmp;
	  }
	
	 public String getDataAccettazioneTimeZone() {
		    return dataAccettazioneTimeZone;
		  }
	
	 public void setDataAccettazione(java.sql.Timestamp tmp) {
		    this.dataAccettazione = tmp;
		  }


		  /**
		   *  Sets the assignedDate attribute of the Ticket object
		   *
		   * @param  tmp  The new assignedDate value
		   */
		  public void setDataAccettazione(String tmp) {
		    this.dataAccettazione = DatabaseUtils.parseDateToTimestamp(tmp);
		  }
	 
	public java.sql.Timestamp getDataAccettazione() {
			    return dataAccettazione;
			  }

		  
	public int getTipoTampone() {
		return tipoTampone;
	}
	
	public void setTipoTampone(String tipoTampone) {
	    try {
	      this.tipoTampone = Integer.parseInt(tipoTampone);
	    } catch (Exception e) {
	      this.tipoTampone = -1;
	    }
	  }
	
	public int getDestinatarioTampone() {
		return destinatarioTampone;
	}
	
	public void setDestinatarioTampone(String destinatarioTampone) {
	    try {
	      this.destinatarioTampone = Integer.parseInt(destinatarioTampone);
	    } catch (Exception e) {
	      this.destinatarioTampone = -1;
	    }
	  }

	public void setEsitoTampone(String esitoTampone) {
	    try {
	      this.esitoTampone = Integer.parseInt(esitoTampone);
	    } catch (Exception e) {
	      this.esitoTampone = -1;
	    }
	  }

	public void setTipoTampone(int tipoTampone) {
		this.tipoTampone = tipoTampone;
	}
	
	public void setDestinatarioTampone(int destinatarioTampone) {
		this.destinatarioTampone = destinatarioTampone;
	}
	
	public int getEsitoTampone() {
		return esitoTampone;
	}

	public void setEsitoTampone(int esitoTampone) {
		this.esitoTampone = esitoTampone;
	}

	
	public String getTipo_richiesta() {
		return tipo_richiesta;
	}

	public void setTipo_richiesta(String tipo_richiesta) {
		this.tipo_richiesta = tipo_richiesta;
	}

	  public Ticket(ResultSet rs) throws SQLException {
	    buildRecord(rs);
	  }

	  public Ticket(Connection db, int id) throws SQLException {
		
	    queryRecord(db, id);
	  }

	  public void queryRecord(Connection db, int id) throws SQLException {
		    if (id == -1) {
		      throw new SQLException("Invalid Ticket Number");
		    }
		    PreparedStatement pst = db.prepareStatement(
		        "SELECT t.*, " +
		        "o.name AS orgname, " +
		        "o.enabled AS orgenabled, " +
		        "o.site_id AS orgsiteid, o.tipologia as tipologia_operatore, " +
		       
		        "a.serial_number AS serialnumber, " +
		        "a.manufacturer_code AS assetmanufacturercode, " +
		        "a.vendor_code AS assetvendorcode, " +
		        "a.model_version AS modelversion, " +
		        "a.location AS assetlocation, " +
		        "a.onsite_service_model AS assetonsiteservicemodel " +
		       
		        "FROM ticket t " +
		        "LEFT JOIN organization o ON (t.org_id = o.org_id) " +
		        "LEFT JOIN asset a ON (t.link_asset_id = a.asset_id) " +
		     
		        "WHERE t.ticketid = ? AND t.tipologia = 7 ");
		    pst.setInt(1, id);
		    ResultSet rs = pst.executeQuery();
		    if (rs.next()) {
		      buildRecord(rs);
		      this.setListaTamponi(db);
		      
		    }
		    rs.close();
		    pst.close();
		    if (this.id == -1) {
		      throw new SQLException(Constants.NOT_FOUND_ERROR);
		    }
		   
		   
		  
		  }
	  
	  public HashMap<Integer, Tampone> getListaTamponi() {
			return listaTamponi;
		}
		public void setListaTamponi(Connection db) throws SQLException {
			
		String sql="select * from tamponi where idticket="+id;
		int progressivo=1;
		PreparedStatement pst =db.prepareStatement(sql);
		ResultSet rs=pst.executeQuery();
		
		while(rs.next()){
			Tampone t=new Tampone();
			int idTampone=rs.getInt("idtampone");
			int superficeintero=rs.getInt("superficeintero");
			String descrizioneSuperficeIntero="";
			
			String superficeTesto=rs.getString("superficetesto");
			
			
			
			
			int tipo=rs.getInt("tipo");
			if(tipo==1){
				String sql3="select description from lookup_tamponi where code="+superficeintero;
				PreparedStatement pst3=db.prepareStatement(sql3);
				ResultSet rs3= pst3.executeQuery();
				if(rs3.next()){
					descrizioneSuperficeIntero=rs3.getString("description");
				}
			}

			
			
	String sql2="select idricerca,description,esito from ricerca_esito_tamponi,lookup_ricerca_tamponi where ricerca_esito_tamponi.idricerca="+
				"lookup_ricerca_tamponi.code and  idtampone=? and idticket=?";
				PreparedStatement pst1=db.prepareStatement(sql2);
				pst1.setInt(1, idTampone);
				pst1.setInt(2, id);
				ResultSet rs2=pst1.executeQuery();
				while(rs2.next()){
					
				int idRicerca=rs2.getInt("idricerca");
				String valueRicerca=rs2.getString("description");
				String esito=rs2.getString("esito");
				
			
				t.addEsito(idRicerca, esito);
				t.addRicerca(idRicerca, valueRicerca);
				t.setIdTampone(idTampone);
				t.setIdTicket(id);
				t.setTipo(tipo);
				if(tipo==1){
				t.setSuperfice(superficeintero);
				t.setSuperficeStringa(descrizioneSuperficeIntero);
				
				}
				else{
					if(tipo==2){	
					t.setSuperficeStringa(superficeTesto);
				}
					}
				
			}
			
			listaTamponi.put(progressivo, t);
			progressivo++;
		}
		}
		
		private int getIdTampone(Connection db) throws SQLException{
			
			int idTampone=0;
			String sql="select max(idtampone) as idtampone from tamponi ";
			PreparedStatement pst=db.prepareStatement(sql);
			ResultSet rs=pst.executeQuery();
			if(rs.next()){
				idTampone=rs.getInt("idtampone");
				
			}
			idTampone+=1;
			return idTampone;
		}
		
		 public void aggiornaTamponi(Connection db)throws SQLException
		  {
			  
			 
			String   sql="delete from tamponi where idticket="+id;
			PreparedStatement pst=db.prepareStatement(sql);
			   
			   pst.execute();
			  
		
		  
		  }
		
	  public int insertTamponi(Connection db,String tipo,String superfice,int idSuperfice)throws SQLException
	  {
		  
		  int idTampone = this.getIdTampone(db);
		  String sql="";
		  if(tipo.equals("1")){
		   sql="insert into tamponi (idticket,superficeintero,idtampone,tipo) values (?,?,?,1)";
		   PreparedStatement pst=db.prepareStatement(sql);
		   pst.setInt(1, id);
		   pst.setInt(2, idSuperfice);
		   pst.setInt(3, idTampone);
		   pst.execute();
		   
		  }else{
			  if(tipo.equals("2")){
				  sql="insert into tamponi (idticket,superficetesto,idtampone,tipo) values (?,?,?,2)";
				   PreparedStatement pst=db.prepareStatement(sql);
				   pst.setInt(1, id);
				   pst.setString(2, superfice);
				   pst.setInt(3, idTampone);
				   pst.execute();
				  
			  }
		  }
		  
		  return idTampone;
	  }
	  
	  
	  public void aggiornaRicerca_EsitoTampone(Connection db) throws SQLException
	  {
		 String sql="delete from ricerca_esito_tamponi where idticket="+id;
		 PreparedStatement pst=db.prepareStatement(sql);
		 pst.execute();
		 
		 
	  }
	  
	  public void insertRicerca_EsitoTampone(Connection db,String idRicerca,String esito,int idtampone) throws SQLException
	  {
		 String sql="insert into ricerca_esito_tamponi (idticket,idtampone,idricerca,esito) values(?,?,?,?)";
		 PreparedStatement pst=db.prepareStatement(sql);
		 pst.setInt(1, id);
		 pst.setInt(2,idtampone );
		 pst.setInt(3, Integer.parseInt(idRicerca));
		 pst.setString(4, esito);
		 pst.execute();
		  
	  }  
	  
	  public synchronized boolean insert(Connection db,ActionContext context) throws SQLException {
		    StringBuffer sql = new StringBuffer();
		    boolean commit = db.getAutoCommit();
		    try {
		      if (commit) {
		        db.setAutoCommit(false);
		      }
		      
		  	UserBean user = (UserBean)context.getSession().getAttribute("User");
			int livello=1 ;
			if (user.getUserRecord().getGruppo_ruolo()==2)
				livello=2;
//			id = DatabaseUtils.getNextInt( db, "ticket","ticketid",livello);
			sql.append(
		          "INSERT INTO ticket (contact_id, problem, pri_code, " +
		          "department_code, cat_code, scode, org_id,id_stabilimento, id_apiario,link_contract_id, " +
		          "link_asset_id, expectation, product_id, customer_product_id, " +
		          "key_count, status_id, trashed_date, user_group_id, cause_id, " +
		          "resolution_id, defect_id, escalation_level, resolvable, " +
		          "resolvedby, resolvedby_department_code, state_id, site_id,ip_entered,ip_modified, ");
		      
		        sql.append("ticketid, ");
		      
		      if (entered != null) {
		        sql.append("entered, ");
		      }
		      if (modified != null) {
		        sql.append("modified, ");
		      }
		      sql.append("tipo_richiesta, custom_data, enteredBy, modifiedBy, " +
		      		"tipologia, provvedimenti_prescrittivi, sanzioni_amministrative, sanzioni_penali ");
		      if (dataAccettazione != null) {
			        sql.append(", data_accettazione ");
			      }
		      if (dataAccettazioneTimeZone != null) {
			        sql.append(",data_accettazione_timezone ");
			      }
		      
		      //aggiunto da d.dauria
		     // Commentato da Giuseppe
		      /*sql.append(", alimenti_origine_animale");
		      sql.append(", alimenti_origine_vegetale");
		      sql.append(", alimenti_composti");*/
		     /* if (alimentiOrigineAnimaleNonTrasformati > -1) {
			        sql.append(", alimenti_origine_animale_non_trasformati");
			      }
		      if (alimentiOrigineAnimaleTrasformati > -1) {
			        sql.append(", alimenti_origine_animale_trasformati");
			      }
		      if (alimentiOrigineVegetaleValori > -1) {
			        sql.append(", alimenti_origine_vegetale_valori");
			      }
		      if (alimentiOrigineAnimaleNonTrasformatiValori > -1) {
			        sql.append(", alimenti_origine_animale_non_trasformati_valori");
			      }*/
		      
		     
		      //sql.append(", allerta");
		      //sql.append(", non_conformita");
		      /*if (conseguenzePositivita > -1) {
			        sql.append(", conseguenze_positivita");
			      }
		      if (responsabilitaPositivita > -1) {
			        sql.append(", responsabilita_positivita");
			      }*/
		      if (noteEsito != null) {
			        sql.append(", note_esito");
			      }
		      if ( nucleoIspettivo > -1) {
			        sql.append(", nucleo_ispettivo");
			      }
		      if ( nucleoIspettivoDue > -1) {
			        sql.append(", nucleo_ispettivo_due");
			      }
		      if ( nucleoIspettivoTre > -1) {
			        sql.append(", nucleo_ispettivo_tre");
			      }
		      if (componenteNucleo != null) {
			        sql.append(", componente_nucleo");
			      }
		      if (componenteNucleoDue != null) {
			        sql.append(", componente_nucleo_due");
			      }
		      if (componenteNucleoTre != null) {
			        sql.append(", componente_nucleo_tre");
			      }
		      if (testoAlimentoComposto != null) {
			        sql.append(", testo_alimento_composto");
			      }
		      if ( nucleoIspettivoQuattro > -1) {
			        sql.append(", nucleo_ispettivo_quattro");
			      }
		      if ( nucleoIspettivoCinque > -1) {
			        sql.append(", nucleo_ispettivo_cinque");
			      }
		      if ( nucleoIspettivoSei > -1) {
			        sql.append(", nucleo_ispettivo_sei");
			      }
		      if ( nucleoIspettivoSette > -1) {
			        sql.append(", nucleo_ispettivo_sette");
			      }
		      if ( nucleoIspettivoOtto > -1) {
			        sql.append(", nucleo_ispettivo_otto");
			      }
		      if ( nucleoIspettivoNove > -1) {
			        sql.append(", nucleo_ispettivo_nove");
			      }
		      if ( nucleoIspettivoDieci > -1) {
			        sql.append(", nucleo_ispettivo_dieci");
			      }
		      if (componenteNucleoQuattro != null) {
			        sql.append(", componente_nucleo_quattro");
			      }
		      if (componenteNucleoCinque != null) {
			        sql.append(", componente_nucleo_cinque");
			      }
		      if (componenteNucleoSei != null) {
			        sql.append(", componente_nucleo_sei");
			      }
		      if (componenteNucleoSette != null) {
			        sql.append(", componente_nucleo_sette");
			      }
		      if (componenteNucleoOtto != null) {
			        sql.append(", componente_nucleo_otto");
			      }
		      if (componenteNucleoNove != null) {
			        sql.append(", componente_nucleo_nove");
			      }
		      if (componenteNucleoDieci != null) {
			        sql.append(", componente_nucleo_dieci");
			      }
		      sql.append(", id_controllo_ufficiale");
		      if (punteggio != -1) {
			        sql.append(", punteggio");
			      }
		      sql.append(")");
		      sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?, ");
		      sql.append("?, ?, ?, ");
		      
				sql.append( DatabaseUtils.getNextIntSql("ticket", "ticketid", livello)+",");
		      
		      if (entered != null) {
		        sql.append("?, ");
		      }
		      if (modified != null) {
		        sql.append("?, ");
		      }
		      sql.append("?, ?, ?, ?, " +
		      		     "7, ?, ?, ? ");
		      if (dataAccettazione != null) {
			        sql.append(", ? ");
			      }
			  if (dataAccettazioneTimeZone != null) {
			        sql.append(", ? ");
			      }
			  
			  
		      if (noteEsito != null) {
			        sql.append(", ?");
			      }
		      if (nucleoIspettivo > -1) {
			        sql.append(", ?");
			      }
		      if (nucleoIspettivoDue > -1) {
			        sql.append(", ?");
			      }
		      if (nucleoIspettivoTre > -1) {
			        sql.append(", ?");
			      }
		      if (componenteNucleo != null) {
			        sql.append(", ? ");
			      }
		      if (componenteNucleoDue != null) {
			        sql.append(", ? ");
			      }
		      if (componenteNucleoTre != null) {
			        sql.append(", ? ");
			      }
		      if (testoAlimentoComposto != null) {
			        sql.append(", ?");
			      }
		      if (nucleoIspettivoQuattro > -1) {
			        sql.append(", ?");
			      }
		      if (nucleoIspettivoCinque > -1) {
			        sql.append(", ?");
			      }
		      if (nucleoIspettivoSei > -1) {
			        sql.append(", ?");
			      }
		      if (nucleoIspettivoSette > -1) {
			        sql.append(", ?");
			      }
		      if (nucleoIspettivoOtto > -1) {
			        sql.append(", ?");
			      }
		      if (nucleoIspettivoNove > -1) {
			        sql.append(", ?");
			      }
		      if (nucleoIspettivoDieci > -1) {
			        sql.append(", ?");
			      }
		      if (componenteNucleoQuattro != null) {
			        sql.append(", ? ");
			      }
		      if (componenteNucleoCinque != null) {
			        sql.append(", ? ");
			      }
		      if (componenteNucleoSei != null) {
			        sql.append(", ? ");
			      }
		      if (componenteNucleoSette != null) {
			        sql.append(", ? ");
			      }
		      if (componenteNucleoOtto != null) {
			        sql.append(", ? ");
			      }
		      if (componenteNucleoNove != null) {
			        sql.append(", ? ");
			      }
		      if (componenteNucleoDieci != null) {
			        sql.append(", ? ");
			      }
		      String asl = null;
		      if(siteId==201){
					asl = "AV";	
				}else if(siteId == 202){
					asl = "BN";
				}else if(siteId ==203){
					asl = "CE";
				}else if(siteId ==204){
					asl = "NA1";
				}else if(siteId == 205){
					asl = "NA2Nord";
				}else if(siteId == 206){
					asl = "NA3Sud";
				}else if(siteId ==207){
					asl = "SA";
				}
				else{
					if(siteId ==16){
						asl = "FuoriRegione";
					}
				
					
				}
		      sql.append(", ? ");
		      if (punteggio != -1) {
			        sql.append(", ? ");
			      }
			  sql.append(") RETURNING ticketid ");
		      int i = 0;
		      PreparedStatement pst = db.prepareStatement(sql.toString());
		      DatabaseUtils.setInt(pst, ++i, this.getContactId());
		      pst.setString(++i, this.getProblem());
		        pst.setNull(++i, java.sql.Types.INTEGER);
		        pst.setNull(++i, java.sql.Types.INTEGER);
		        pst.setNull(++i, java.sql.Types.INTEGER);
		        pst.setNull(++i, java.sql.Types.INTEGER);

		      DatabaseUtils.setInt(pst, ++i, this.orgId);
				DatabaseUtils.setInt(pst, ++i, this.idStabilimento);
				DatabaseUtils.setInt(pst, ++i, this.idApiario);

		        pst.setNull(++i, java.sql.Types.INTEGER);
		      DatabaseUtils.setInt(pst, ++i, assetId);
		        pst.setNull(++i, java.sql.Types.INTEGER);
		        pst.setNull(++i, java.sql.Types.INTEGER);
		        pst.setNull(++i, java.sql.Types.INTEGER);
		        pst.setNull(++i, java.sql.Types.INTEGER);

		      DatabaseUtils.setInt(pst, ++i, statusId);
		      DatabaseUtils.setTimestamp(pst, ++i, trashedDate);
		        pst.setNull(++i, java.sql.Types.INTEGER);
		      DatabaseUtils.setInt(pst, ++i, causeId);
		      DatabaseUtils.setInt(pst, ++i, resolutionId);
		        pst.setNull(++i, java.sql.Types.INTEGER);
		        pst.setNull(++i, java.sql.Types.INTEGER);

		        pst.setNull(++i, java.sql.Types.BOOLEAN);
		        pst.setNull(++i, java.sql.Types.INTEGER);
		        pst.setNull(++i, java.sql.Types.INTEGER);

		      DatabaseUtils.setInt(pst, ++i, this.getStateId());
		      DatabaseUtils.setInt(pst, ++i, this.getSiteId());
		      pst.setString(++i, super.getIpEntered());
		      pst.setString(++i, super.getIpModified());
		      
		      if (entered != null) {
		        pst.setTimestamp(++i, entered);
		      }
		      if (modified != null) {
		        pst.setTimestamp(++i, modified);
		      } 
		      
		      /**
		       * 
		       */
		      pst.setString( ++i, this.getTipo_richiesta() );
		     
		      pst.setString( ++i, this.getPippo() );
		      
		      pst.setInt(++i, this.getEnteredBy());
		      pst.setInt(++i, this.getModifiedBy());
		      DatabaseUtils.setInt(pst, ++i, tipoTampone);
		      DatabaseUtils.setInt(pst, ++i, esitoTampone);
		      DatabaseUtils.setInt(pst, ++i, destinatarioTampone);
		      if (dataAccettazione != null) {
			        pst.setTimestamp(++i, dataAccettazione);
			      }
		      if (dataAccettazioneTimeZone != null) {
			        pst.setString(++i, dataAccettazioneTimeZone);
			      }
		  
		      //aggiunto da d.dauria
		    /*  pst.setBoolean(++i, this.getAlimentiOrigineAnimale());
		      pst.setBoolean(++i, this.getAlimentiOrigineVegetale());
		      pst.setBoolean(++i, this.getAlimentiComposti());
		      
		      if(alimentiOrigineAnimaleNonTrasformati > -1)
		      pst.setInt(++i , alimentiOrigineAnimaleNonTrasformati);
		      if(alimentiOrigineAnimaleTrasformati > -1)
		      pst.setInt(++i , alimentiOrigineAnimaleTrasformati);
		      if(alimentiOrigineVegetaleValori > -1)
		      pst.setInt(++i , alimentiOrigineVegetaleValori);
		      if(alimentiOrigineAnimaleNonTrasformatiValori > -1)
		      pst.setInt(++i , alimentiOrigineAnimaleNonTrasformatiValori);
		      
		    
		        pst.setBoolean(++i, allerta);
		        pst.setBoolean(++i, nonConformita);*/
		      /*  if(conseguenzePositivita > -1)
				      pst.setInt(++i , conseguenzePositivita);
		        if(responsabilitaPositivita > -1)
				      pst.setInt(++i , responsabilitaPositivita);
		        pst.setString(++i, noteEsito);
		        */
		        if(nucleoIspettivo > -1)
				      pst.setInt(++i , nucleoIspettivo);
		        if(nucleoIspettivoDue > -1)
				      pst.setInt(++i , nucleoIspettivoDue);
		        if(nucleoIspettivoTre > -1)
				      pst.setInt(++i , nucleoIspettivoTre);
		        if (componenteNucleo != null) {
			        pst.setString(++i, componenteNucleo);
			      }
		        if (componenteNucleoDue != null) {
			        pst.setString(++i, componenteNucleoDue);
			      }
		        if (componenteNucleoTre != null) {
			        pst.setString(++i, componenteNucleoTre);
			      }     
		        if (testoAlimentoComposto != null) {
			        pst.setString(++i, testoAlimentoComposto);
			      }  
		        if(nucleoIspettivoQuattro > -1)
				      pst.setInt(++i , nucleoIspettivoQuattro);
		        if(nucleoIspettivoCinque > -1)
				      pst.setInt(++i , nucleoIspettivoCinque);     
		        if(nucleoIspettivoSei > -1)
				      pst.setInt(++i , nucleoIspettivoSei);
		        if(nucleoIspettivoSette > -1)
				      pst.setInt(++i , nucleoIspettivoSette);
		        if(nucleoIspettivoOtto > -1)
				      pst.setInt(++i , nucleoIspettivoOtto);
		        if(nucleoIspettivoNove > -1)
				      pst.setInt(++i , nucleoIspettivoNove);
		        if(nucleoIspettivoDieci > -1)
				      pst.setInt(++i , nucleoIspettivoDieci);
		        if (componenteNucleoQuattro != null) {
			        pst.setString(++i, componenteNucleoQuattro);
			      }
		        if (componenteNucleoCinque != null) {
			        pst.setString(++i, componenteNucleoCinque);
			      }
		        if (componenteNucleoSei != null) {
			        pst.setString(++i, componenteNucleoSei);
			      }
		        if (componenteNucleoSette != null) {
			        pst.setString(++i, componenteNucleoSette);
			      }
		        if (componenteNucleoOtto != null) {
			        pst.setString(++i, componenteNucleoOtto);
			      }
		        if (componenteNucleoNove != null) {
			        pst.setString(++i, componenteNucleoNove);
			      }
		        if (componenteNucleoDieci != null) {
			        pst.setString(++i, componenteNucleoDieci);
			      }
		        pst.setString(++i, idControlloUfficiale);
		        if (punteggio != -1) {
			        pst.setInt(++i, punteggio);
			      }
		        
		      ResultSet rs = pst.executeQuery();
		      if ( rs.next())
		    	  this.id = rs.getInt("ticketid");
		      pst.close();
				db.prepareStatement("UPDATE TICKET set identificativo = '"+asl+idControlloUfficiale+"' || trim(to_char( "+id+", '"+DatabaseUtils.getPaddedFromId(id)+"' )) where ticketid ="+this.getId()).execute();
		      
		      //OrganizationHistory orgHisory = new OrganizationHistory();
		      /*
		      TicketLog thisEntry = new TicketLog();
				thisEntry.setEnteredBy(this.getModifiedBy());
				thisEntry.setDepartmentCode(this.getDepartmentCode());
				thisEntry.setAssignedTo(this.getAssignedTo());
				thisEntry.setPriorityCode(this.getPriorityCode());
				thisEntry.setSeverityCode(this.getSeverityCode());
				thisEntry.setEscalationCode(this.getEscalationLevel());
				thisEntry.setEntryText(this.getComment());
				thisEntry.setTicketId(this.getId());
				thisEntry.setStateId(this.getStateId());
				thisEntry.process(db, this.getId(), this.getEnteredBy(), this
						.getModifiedBy());
				*/
		      //orgHisory.setOrgId( this.getOrgId() );
		      //orgHisory.setMessage( "Aperta richiesta n. " + this.getId() );
		      //orgHisory.insert( db );
		      
		      //Update the rest of the fields
		      this.update(db, true);
		      if (this.getEntered() == null) {
		        this.updateEntry(db);
		      }
		     
		      if (commit) {
		        db.commit();
		      }
		    } catch (SQLException e) {
		      if (commit) {
		        db.rollback();
		      }
		      throw new SQLException(e.getMessage());
		    } finally {
		      if (commit) {
		        db.setAutoCommit(true);
		      }
		    }
		    return true;
		  }
	 /* public boolean insert(Connection db) throws SQLException {
		    StringBuffer sql = new StringBuffer();
		    boolean commit = db.getAutoCommit();
		    try {
		      if (commit) {
		        db.setAutoCommit(false);
		      }
		      if (projectId > -1 && projectTicketCount == -1) {
		        updateProjectTicketCount(db, projectId);
		      }
		      id = DatabaseUtils.getNextSeq(db, "ticket_ticketid_seq");
		      sql.append(
		          "INSERT INTO ticket (contact_id, problem, pri_code, " +
		          "department_code, cat_code, scode, org_id, link_contract_id, " +
		          "link_asset_id, expectation, product_id, customer_product_id, " +
		          "key_count, status_id, trashed_date, user_group_id, cause_id, " +
		          "resolution_id, defect_id, escalation_level, resolvable, " +
		          "resolvedby, resolvedby_department_code, state_id, site_id, ");
		      if (id > -1) {
		        sql.append("ticketid, ");
		      }
		      if (entered != null) {
		        sql.append("entered, ");
		      }
		      if (modified != null) {
		        sql.append("modified, ");
		      }
		      sql.append("tipo_richiesta, custom_data, enteredBy, modifiedBy, " +
		      		"tipologia, provvedimenti_prescrittivi, sanzioni_amministrative, sanzioni_penali ");
		      if (dataAccettazione != null) {
			        sql.append(", data_accettazione ");
			      }
		      if (dataAccettazioneTimeZone != null) {
			        sql.append(",data_accettazione_timezone ");
			      }
		      
		      //aggiunto da d.dauria
		      sql.append(", alimenti_origine_animale");
		      sql.append(", alimenti_origine_vegetale");
		      sql.append(", alimenti_composti");
		    
		      /*if (alimentiOrigineAnimaleNonTrasformati > -1) {
			        sql.append(", alimenti_origine_animale_non_trasformati");
			      }
		      if (alimentiOrigineAnimaleTrasformati > -1) {
			        sql.append(", alimenti_origine_animale_trasformati");
			      }
		      if (alimentiOrigineVegetaleValori > -1) {
			        sql.append(", alimenti_origine_vegetale_valori");
			      }
		      if (alimentiOrigineAnimaleNonTrasformatiValori > -1) {
			        sql.append(", alimenti_origine_animale_non_trasformati_valori");
			      }*/
		   /*   
		     
		      sql.append(", allerta");
		      sql.append(", non_conformita");
		      if (conseguenzePositivita > -1) {
			        sql.append(", conseguenze_positivita");
			      }
		      if (responsabilitaPositivita > -1) {
			        sql.append(", responsabilita_positivita");
			      }
		      if (noteEsito != null) {
			        sql.append(", note_esito");
			      }
		      if ( nucleoIspettivo > -1) {
			        sql.append(", nucleo_ispettivo");
			      }
		      if ( nucleoIspettivoDue > -1) {
			        sql.append(", nucleo_ispettivo_due");
			      }
		      if ( nucleoIspettivoTre > -1) {
			        sql.append(", nucleo_ispettivo_tre");
			      }
		      if (componenteNucleo != null) {
			        sql.append(", componente_nucleo");
			      }
		      if (componenteNucleoDue != null) {
			        sql.append(", componente_nucleo_due");
			      }
		      if (componenteNucleoTre != null) {
			        sql.append(", componente_nucleo_tre");
			      }
		      if (testoAlimentoComposto != null) {
			        sql.append(", testo_alimento_composto");
			      }
		      if ( nucleoIspettivoQuattro > -1) {
			        sql.append(", nucleo_ispettivo_quattro");
			      }
		      if ( nucleoIspettivoCinque > -1) {
			        sql.append(", nucleo_ispettivo_cinque");
			      }
		      if ( nucleoIspettivoSei > -1) {
			        sql.append(", nucleo_ispettivo_sei");
			      }
		      if ( nucleoIspettivoSette > -1) {
			        sql.append(", nucleo_ispettivo_sette");
			      }
		      if ( nucleoIspettivoOtto > -1) {
			        sql.append(", nucleo_ispettivo_otto");
			      }
		      if ( nucleoIspettivoNove > -1) {
			        sql.append(", nucleo_ispettivo_nove");
			      }
		      if ( nucleoIspettivoDieci > -1) {
			        sql.append(", nucleo_ispettivo_dieci");
			      }
		      if (componenteNucleoQuattro != null) {
			        sql.append(", componente_nucleo_quattro");
			      }
		      if (componenteNucleoCinque != null) {
			        sql.append(", componente_nucleo_cinque");
			      }
		      if (componenteNucleoSei != null) {
			        sql.append(", componente_nucleo_sei");
			      }
		      if (componenteNucleoSette != null) {
			        sql.append(", componente_nucleo_sette");
			      }
		      if (componenteNucleoOtto != null) {
			        sql.append(", componente_nucleo_otto");
			      }
		      if (componenteNucleoNove != null) {
			        sql.append(", componente_nucleo_nove");
			      }
		      if (componenteNucleoDieci != null) {
			        sql.append(", componente_nucleo_dieci");
			      }
		      
		      sql.append(")");
		      sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
		      sql.append("?, ?, ?, ");
		      if (id > -1) {
		        sql.append("?,");
		      }
		      if (entered != null) {
		        sql.append("?, ");
		      }
		      if (modified != null) {
		        sql.append("?, ");
		      }
		      sql.append("?, ?, ?, ?, " +
		      		     "7, ?, ?, ? ");
		      if (dataAccettazione != null) {
			        sql.append(", ? ");
			      }
			  if (dataAccettazioneTimeZone != null) {
			        sql.append(", ? ");
			      }
			  
			  //aggiunto da d.dauria
			  sql.append(", ? "); //questo e' per alimentiOrigineAnimale
			  sql.append(", ? "); //questo e' per alimentiOrigineVegetale
			  sql.append(", ? "); //questo e' per alimentiComposti  
		*/	/*  if (alimentiOrigineAnimaleNonTrasformati > -1) {
			        sql.append(", ?");
			      }
			  if (alimentiOrigineAnimaleTrasformati > -1) {
			        sql.append(", ?");
			      }
		      if (alimentiOrigineVegetaleValori > -1) {
			        sql.append(", ?");
			      }
		      if (alimentiOrigineAnimaleNonTrasformatiValori > -1) {
			        sql.append(", ?");
			      }*/
		      
		    
		    /*  sql.append(", ?"); //questo e' per allerta
		      sql.append(", ?"); //questo e' per nonconformita
		      if (conseguenzePositivita > -1) {
			        sql.append(", ?");
			      }
		      if (responsabilitaPositivita > -1) {
			        sql.append(", ?");
			      }
		      if (noteEsito != null) {
			        sql.append(", ?");
			      }
		      if (nucleoIspettivo > -1) {
			        sql.append(", ?");
			      }
		      if (nucleoIspettivoDue > -1) {
			        sql.append(", ?");
			      }
		      if (nucleoIspettivoTre > -1) {
			        sql.append(", ?");
			      }
		      if (componenteNucleo != null) {
			        sql.append(", ? ");
			      }
		      if (componenteNucleoDue != null) {
			        sql.append(", ? ");
			      }
		      if (componenteNucleoTre != null) {
			        sql.append(", ? ");
			      }
		      if (testoAlimentoComposto != null) {
			        sql.append(", ?");
			      }
		      if (nucleoIspettivoQuattro > -1) {
			        sql.append(", ?");
			      }
		      if (nucleoIspettivoCinque > -1) {
			        sql.append(", ?");
			      }
		      if (nucleoIspettivoSei > -1) {
			        sql.append(", ?");
			      }
		      if (nucleoIspettivoSette > -1) {
			        sql.append(", ?");
			      }
		      if (nucleoIspettivoOtto > -1) {
			        sql.append(", ?");
			      }
		      if (nucleoIspettivoNove > -1) {
			        sql.append(", ?");
			      }
		      if (nucleoIspettivoDieci > -1) {
			        sql.append(", ?");
			      }
		      if (componenteNucleoQuattro != null) {
			        sql.append(", ? ");
			      }
		      if (componenteNucleoCinque != null) {
			        sql.append(", ? ");
			      }
		      if (componenteNucleoSei != null) {
			        sql.append(", ? ");
			      }
		      if (componenteNucleoSette != null) {
			        sql.append(", ? ");
			      }
		      if (componenteNucleoOtto != null) {
			        sql.append(", ? ");
			      }
		      if (componenteNucleoNove != null) {
			        sql.append(", ? ");
			      }
		      if (componenteNucleoDieci != null) {
			        sql.append(", ? ");
			      }
		      
			  sql.append(")");
			  
		      int i = 0;
		      PreparedStatement pst = db.prepareStatement(sql.toString());
		      DatabaseUtils.setInt(pst, ++i, this.getContactId());
		      pst.setString(++i, this.getProblem());
		      if (this.getPriorityCode() > 0) {
		        pst.setInt(++i, this.getPriorityCode());
		      } else {
		        pst.setNull(++i, java.sql.Types.INTEGER);
		      }
		      if (this.getDepartmentCode() > 0) {
		        pst.setInt(++i, this.getDepartmentCode());
		      } else {
		        pst.setNull(++i, java.sql.Types.INTEGER);
		      }
		     
		      if (this.getCatCode() > 0) {
		        pst.setInt(++i, this.getCatCode());
		      } else {
		        pst.setNull(++i, java.sql.Types.INTEGER);
		      }
		      if (this.getSeverityCode() > 0) {
		        pst.setInt(++i, this.getSeverityCode());
		      } else {
		        pst.setNull(++i, java.sql.Types.INTEGER);
		      }*/
		     /* DatabaseUtils.setInt(pst, ++i, orgId);
		      DatabaseUtils.setInt(pst, ++i, contractId);
		      DatabaseUtils.setInt(pst, ++i, assetId);
		      DatabaseUtils.setInt(pst, ++i, expectation);
		      DatabaseUtils.setInt(pst, ++i, productId);
		      DatabaseUtils.setInt(pst, ++i, customerProductId);
		      DatabaseUtils.setInt(pst, ++i, projectTicketCount);
		      DatabaseUtils.setInt(pst, ++i, statusId);
		      DatabaseUtils.setTimestamp(pst, ++i, trashedDate);
		      DatabaseUtils.setInt(pst, ++i, userGroupId);
		      DatabaseUtils.setInt(pst, ++i, causeId);
		      DatabaseUtils.setInt(pst, ++i, resolutionId);
		      DatabaseUtils.setInt(pst, ++i, defectId);
		      DatabaseUtils.setInt(pst, ++i, escalationLevel);
		      pst.setBoolean(++i, resolvable);
		      if (resolvedBy > 0) {
		        pst.setInt(++i, resolvedBy);
		      } else {
		        pst.setNull(++i, java.sql.Types.INTEGER);
		      }
		      if (this.getResolvedByDeptCode() > 0) {
		        pst.setInt(++i, this.getResolvedByDeptCode());
		      } else {
		        pst.setNull(++i, java.sql.Types.INTEGER);
		      }
		      DatabaseUtils.setInt(pst, ++i, this.getStateId());
		      DatabaseUtils.setInt(pst, ++i, this.getSiteId());
		      if (id > -1) {
		        pst.setInt(++i, id);
		      }
		      if (entered != null) {
		        pst.setTimestamp(++i, entered);
		      }
		      if (modified != null) {
		        pst.setTimestamp(++i, modified);
		      } */
		      
		      /**
		       * 
		       */
		    /*  pst.setString( ++i, this.getTipo_richiesta() );
		     
		      pst.setString( ++i, this.getPippo() );
		      
		      pst.setInt(++i, this.getEnteredBy());
		      pst.setInt(++i, this.getModifiedBy());
		      DatabaseUtils.setInt(pst, ++i, tipoTampone);
		      DatabaseUtils.setInt(pst, ++i, esitoTampone);
		      DatabaseUtils.setInt(pst, ++i, destinatarioTampone);
		      if (dataAccettazione != null) {
			        pst.setTimestamp(++i, dataAccettazione);
			      }
		      if (dataAccettazioneTimeZone != null) {
			        pst.setString(++i, dataAccettazioneTimeZone);
			      }
		  
		      //aggiunto da d.dauria
		      pst.setBoolean(++i, this.getAlimentiOrigineAnimale());
		      pst.setBoolean(++i, this.getAlimentiOrigineVegetale());
		      pst.setBoolean(++i, this.getAlimentiComposti());
		      
		      if(alimentiOrigineAnimaleNonTrasformati > -1)
		      pst.setInt(++i , alimentiOrigineAnimaleNonTrasformati);
		      if(alimentiOrigineAnimaleTrasformati > -1)
		      pst.setInt(++i , alimentiOrigineAnimaleTrasformati);
		      if(alimentiOrigineVegetaleValori > -1)
		      pst.setInt(++i , alimentiOrigineVegetaleValori);
		      if(alimentiOrigineAnimaleNonTrasformatiValori > -1)
		      pst.setInt(++i , alimentiOrigineAnimaleNonTrasformatiValori);
		      
		    
		        pst.setBoolean(++i, allerta);
		        pst.setBoolean(++i, nonConformita);
		        if(conseguenzePositivita > -1)
				      pst.setInt(++i , conseguenzePositivita);
		        if(responsabilitaPositivita > -1)
				      pst.setInt(++i , responsabilitaPositivita);
		        pst.setString(++i, noteEsito);
		        
		        if(nucleoIspettivo > -1)
				      pst.setInt(++i , nucleoIspettivo);
		        if(nucleoIspettivoDue > -1)
				      pst.setInt(++i , nucleoIspettivoDue);
		        if(nucleoIspettivoTre > -1)
				      pst.setInt(++i , nucleoIspettivoTre);
		        if (componenteNucleo != null) {
			        pst.setString(++i, componenteNucleo);
			      }
		        if (componenteNucleoDue != null) {
			        pst.setString(++i, componenteNucleoDue);
			      }
		        if (componenteNucleoTre != null) {
			        pst.setString(++i, componenteNucleoTre);
			      }     
		        if (testoAlimentoComposto != null) {
			        pst.setString(++i, testoAlimentoComposto);
			      }  
		        if(nucleoIspettivoQuattro > -1)
				      pst.setInt(++i , nucleoIspettivoQuattro);
		        if(nucleoIspettivoCinque > -1)
				      pst.setInt(++i , nucleoIspettivoCinque);     
		        if(nucleoIspettivoSei > -1)
				      pst.setInt(++i , nucleoIspettivoSei);
		        if(nucleoIspettivoSette > -1)
				      pst.setInt(++i , nucleoIspettivoSette);
		        if(nucleoIspettivoOtto > -1)
				      pst.setInt(++i , nucleoIspettivoOtto);
		        if(nucleoIspettivoNove > -1)
				      pst.setInt(++i , nucleoIspettivoNove);
		        if(nucleoIspettivoDieci > -1)
				      pst.setInt(++i , nucleoIspettivoDieci);
		        if (componenteNucleoQuattro != null) {
			        pst.setString(++i, componenteNucleoQuattro);
			      }
		        if (componenteNucleoCinque != null) {
			        pst.setString(++i, componenteNucleoCinque);
			      }
		        if (componenteNucleoSei != null) {
			        pst.setString(++i, componenteNucleoSei);
			      }
		        if (componenteNucleoSette != null) {
			        pst.setString(++i, componenteNucleoSette);
			      }
		        if (componenteNucleoOtto != null) {
			        pst.setString(++i, componenteNucleoOtto);
			      }
		        if (componenteNucleoNove != null) {
			        pst.setString(++i, componenteNucleoNove);
			      }
		        if (componenteNucleoDieci != null) {
			        pst.setString(++i, componenteNucleoDieci);
			      }
		        
		      pst.execute();
		      pst.close();
		      */
		      
		      //OrganizationHistory orgHisory = new OrganizationHistory();
		      /*
		      TicketLog thisEntry = new TicketLog();
				thisEntry.setEnteredBy(this.getModifiedBy());
				thisEntry.setDepartmentCode(this.getDepartmentCode());
				thisEntry.setAssignedTo(this.getAssignedTo());
				thisEntry.setPriorityCode(this.getPriorityCode());
				thisEntry.setSeverityCode(this.getSeverityCode());
				thisEntry.setEscalationCode(this.getEscalationLevel());
				thisEntry.setEntryText(this.getComment());
				thisEntry.setTicketId(this.getId());
				thisEntry.setStateId(this.getStateId());
				thisEntry.process(db, this.getId(), this.getEnteredBy(), this
						.getModifiedBy());
				*/
		      //orgHisory.setOrgId( this.getOrgId() );
		      //orgHisory.setMessage( "Aperta richiesta n. " + this.getId() );
		      //orgHisory.insert( db );
		      
		  /*  
		      //Update the rest of the fields
		      this.update(db, true);
		      if (this.getEntered() == null) {
		        this.updateEntry(db);
		      }
		      if (actionId > 0) {
		        updateLog(db);
		      }
		      if (commit) {
		        db.commit();
		      }
		    } catch (SQLException e) {
		      if (commit) {
		        db.rollback();
		      }
		      throw new SQLException(e.getMessage());
		    } finally {
		      if (commit) {
		        db.setAutoCommit(true);
		      }
		    }
		    return true;
		  }*/

	  protected void buildRecord(ResultSet rs) throws SQLException {
		    //ticket table
		    this.setId(rs.getInt("ticketid"));
		    orgId = DatabaseUtils.getInt(rs, "org_id");
		    contactId = DatabaseUtils.getInt(rs, "contact_id");
		    problem = rs.getString("problem");
		    entered = rs.getTimestamp("entered");
		    enteredBy = rs.getInt("enteredby");
		    modified = rs.getTimestamp("modified");
		    modifiedBy = rs.getInt("modifiedby");
		    closed = rs.getTimestamp("closed");
		  
		    idStabilimento = DatabaseUtils.getInt(rs, "id_stabilimento");
		    idApiario = DatabaseUtils.getInt(rs, "id_apiario");

		    solution = rs.getString("solution");
		    location = rs.getString("location");
		    assignedDate = rs.getTimestamp("assigned_date");
		    estimatedResolutionDate = rs.getTimestamp("est_resolution_date");
		    resolutionDate = rs.getTimestamp("resolution_date");
		    cause = rs.getString("cause");
			tipologia_operatore = rs.getInt("tipologia_operatore");
			if (idStabilimento>0)
				tipologia_operatore = Ticket.TIPO_OPU;
			if(idApiario > 0)
			   tipologia_operatore = Ticket.TIPO_API;
		    assetId = DatabaseUtils.getInt(rs, "link_asset_id");
		    estimatedResolutionDateTimeZone = rs.getString(
		        "est_resolution_date_timezone");
		    assignedDateTimeZone = rs.getString("assigned_date_timezone");
		    resolutionDateTimeZone = rs.getString("resolution_date_timezone");
		    statusId = DatabaseUtils.getInt(rs, "status_id");
		    trashedDate = rs.getTimestamp("trashed_date");
		    causeId = DatabaseUtils.getInt(rs, "cause_id");
		    resolutionId = DatabaseUtils.getInt(rs, "resolution_id");
		     stateId = DatabaseUtils.getInt(rs, "state_id");
		    siteId = DatabaseUtils.getInt(rs, "site_id");
		    tipo_richiesta = rs.getString( "tipo_richiesta" );
		    pippo = rs.getString( "custom_data" );
		    tipologia = rs.getInt( "tipologia" );
		    tipoTampone = DatabaseUtils.getInt(rs, "provvedimenti_prescrittivi");
		    esitoTampone = DatabaseUtils.getInt(rs, "sanzioni_amministrative");
		    destinatarioTampone = DatabaseUtils.getInt(rs, "sanzioni_penali");
		    dataAccettazione = rs.getTimestamp( "data_accettazione" );
		    dataAccettazioneTimeZone = rs.getString("data_accettazione_timezone");
		    //organization table
		  

		  
		    
		    
		    //aggiunto da d.dauria
		    alimentiOrigineAnimale = rs.getBoolean("alimenti_origine_animale");
		    alimentiOrigineVegetale = rs.getBoolean("alimenti_origine_vegetale");
		    alimentiComposti = rs.getBoolean("alimenti_composti");
		    alimentiOrigineAnimaleNonTrasformati = DatabaseUtils.getInt(rs, "alimenti_origine_animale_non_trasformati");
		    alimentiOrigineAnimaleTrasformati = DatabaseUtils.getInt(rs, "alimenti_origine_animale_trasformati");
		    alimentiOrigineVegetaleValori= DatabaseUtils.getInt(rs, "alimenti_origine_vegetale_valori");
		    alimentiOrigineAnimaleNonTrasformatiValori = DatabaseUtils.getInt(rs, "alimenti_origine_animale_non_trasformati_valori");
		    allerta = rs.getBoolean("allerta");
		    nonConformita = rs.getBoolean("non_conformita");
		    conseguenzePositivita = DatabaseUtils.getInt(rs, "conseguenze_positivita");
		    responsabilitaPositivita = DatabaseUtils.getInt(rs, "responsabilita_positivita");
		    noteEsito = rs.getString("note_esito");
		    nucleoIspettivo = DatabaseUtils.getInt(rs, "nucleo_ispettivo");
		    nucleoIspettivoDue = DatabaseUtils.getInt(rs, "nucleo_ispettivo_due");
		    nucleoIspettivoTre = DatabaseUtils.getInt(rs, "nucleo_ispettivo_tre");
		    componenteNucleo = rs.getString("componente_nucleo");
		    componenteNucleoDue = rs.getString("componente_nucleo_due");
		    componenteNucleoTre = rs.getString("componente_nucleo_tre");
		    testoAlimentoComposto = rs.getString("testo_alimento_composto");
		    nucleoIspettivoQuattro = DatabaseUtils.getInt(rs, "nucleo_ispettivo_quattro");
		    nucleoIspettivoCinque = DatabaseUtils.getInt(rs, "nucleo_ispettivo_cinque");
		    nucleoIspettivoSei = DatabaseUtils.getInt(rs, "nucleo_ispettivo_sei");
		    nucleoIspettivoSette = DatabaseUtils.getInt(rs, "nucleo_ispettivo_sette");
		    nucleoIspettivoOtto = DatabaseUtils.getInt(rs, "nucleo_ispettivo_otto");
		    nucleoIspettivoNove = DatabaseUtils.getInt(rs, "nucleo_ispettivo_nove");
		    nucleoIspettivoDieci = DatabaseUtils.getInt(rs, "nucleo_ispettivo_dieci");
		    componenteNucleoQuattro = rs.getString("componente_nucleo_quattro");
		    componenteNucleoCinque = rs.getString("componente_nucleo_cinque");
		    componenteNucleoSei = rs.getString("componente_nucleo_sei");
		    componenteNucleoSette = rs.getString("componente_nucleo_sette");
		    componenteNucleoOtto = rs.getString("componente_nucleo_otto");
		    componenteNucleoNove = rs.getString("componente_nucleo_nove");
		    componenteNucleoDieci = rs.getString("componente_nucleo_dieci");
		    idControlloUfficiale = rs.getString("id_controllo_ufficiale");		    
		    identificativo = rs.getString("identificativo");
		    punteggio = rs.getInt("punteggio");
		    
	    

		    
		  }
	  
	  public int update(Connection db, boolean override) throws SQLException {
		    int resultCount = 0;
		    PreparedStatement pst = null;
		    StringBuffer sql = new StringBuffer();
		    sql.append(
		        "UPDATE ticket " +
		        "SET link_contract_id = ?, link_asset_id = ?, department_code = ?, " +
		        "pri_code = ?, scode = ?, " +
		        "cat_code = ?, assigned_to = ?, " +
		        "subcat_code1 = ?, subcat_code2 = ?, subcat_code3 = ?, " +
		        "source_code = ?, contact_id = ?, problem = ?, " +
		        "status_id = ?, trashed_date = ?, site_id = ?, ip_modified='"+super.getIpModified()+"', ");
		    if (!override) {
		      sql.append(
		          "modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", modifiedby = ?, ");
		    }
		   		    if (orgId != -1) {
		      sql.append(" org_id = ?, ");
		    }
		    sql.append(
		        "solution = ?, custom_data = ?, location = ?, assigned_date = ?, assigned_date_timezone = ?, " +
		        "est_resolution_date = ?, est_resolution_date_timezone = ?, resolution_date = ?, resolution_date_timezone = ?, " +
		        "cause = ?, expectation = ?, product_id = ?, customer_product_id = ?, " +
		        "user_group_id = ?, cause_id = ?, resolution_id = ?, defect_id = ?, state_id = ?, " +
		        "escalation_level = ?, resolvable = ?, resolvedby = ?, resolvedby_department_code = ?, provvedimenti_prescrittivi = ?, sanzioni_amministrative = ?, sanzioni_penali = ?, data_accettazione = ?, data_accettazione_timezone = ?, alimenti_origine_animale = ?, alimenti_origine_vegetale = ?, alimenti_composti = ?, alimenti_origine_animale_non_trasformati = ?, alimenti_origine_animale_trasformati = ?, alimenti_origine_vegetale_valori = ?, alimenti_origine_animale_non_trasformati_valori = ?, allerta = ?, non_conformita = ?, conseguenze_positivita = ?, responsabilita_positivita = ?, note_esito = ?  " +
		        ", nucleo_ispettivo = ?, nucleo_ispettivo_due = ?, nucleo_ispettivo_tre = ?, componente_nucleo = ?, componente_nucleo_due = ?, componente_nucleo_tre = ?, testo_alimento_composto = ?, nucleo_ispettivo_quattro = ?, nucleo_ispettivo_cinque = ?, nucleo_ispettivo_sei = ?, nucleo_ispettivo_sette = ?, nucleo_ispettivo_otto = ?, nucleo_ispettivo_nove = ?, nucleo_ispettivo_dieci = ?,"+
		        "componente_nucleo_quattro = ?, componente_nucleo_cinque = ?, componente_nucleo_sei = ?, componente_nucleo_sette = ?, componente_nucleo_otto = ?, componente_nucleo_nove = ?, componente_nucleo_dieci = ?, punteggio = ?"+
		     
		        "WHERE ticketid = ? AND tipologia = 7");
		   /* if (!override) {
		      sql.append("AND modified " + ((this.getModified() == null)?"IS NULL ":"= ? "));
		    }*/
		    int i = 0;
		    pst = db.prepareStatement(sql.toString());
	        pst.setNull(++i, java.sql.Types.INTEGER);
		    DatabaseUtils.setInt(pst, ++i, this.getAssetId());
	        pst.setNull(++i, java.sql.Types.INTEGER);
	        pst.setNull(++i, java.sql.Types.INTEGER);
	        pst.setNull(++i, java.sql.Types.INTEGER);
	        pst.setNull(++i, java.sql.Types.INTEGER);
	        pst.setNull(++i, java.sql.Types.INTEGER);
	        pst.setNull(++i, java.sql.Types.INTEGER);
	        pst.setNull(++i, java.sql.Types.INTEGER);
	        pst.setNull(++i, java.sql.Types.INTEGER);
	        pst.setNull(++i, java.sql.Types.INTEGER);

		    DatabaseUtils.setInt(pst, ++i, this.getContactId());
		    pst.setString(++i, this.getProblem());
		    DatabaseUtils.setInt(pst, ++i, this.getStatusId());
		    DatabaseUtils.setTimestamp(pst, ++i, this.getTrashedDate());
		    DatabaseUtils.setInt(pst, ++i, this.getSiteId());
		    if (!override) {
		      pst.setInt(++i, this.getModifiedBy());
		    }
		  
		    if (orgId != -1) {
		      DatabaseUtils.setInt(pst, ++i, orgId);
		    }
		    pst.setString(++i, this.getSolution());
		    if( pippo != null )
		    {
		    	pst.setString( ++i, pippo );
		    }
		    else
		    {
		    	pst.setNull( i++, Types.VARCHAR );
		    }
		    pst.setString(++i, location);
		    DatabaseUtils.setTimestamp(pst, ++i, assignedDate);
		    pst.setString(++i, this.assignedDateTimeZone);
		    DatabaseUtils.setTimestamp(pst, ++i, estimatedResolutionDate);
		    pst.setString(++i, estimatedResolutionDateTimeZone);
		    DatabaseUtils.setTimestamp(pst, ++i, resolutionDate);
		    pst.setString(++i, this.resolutionDateTimeZone);
		    pst.setString(++i, cause);
	        pst.setNull(++i, java.sql.Types.INTEGER);
	        pst.setNull(++i, java.sql.Types.INTEGER);
	        pst.setNull(++i, java.sql.Types.INTEGER);
	        pst.setNull(++i, java.sql.Types.INTEGER);

		    DatabaseUtils.setInt(pst, ++i, causeId);
		    DatabaseUtils.setInt(pst, ++i, resolutionId);
	        pst.setNull(++i, java.sql.Types.INTEGER);
		    DatabaseUtils.setInt(pst, ++i, this.getStateId());
	        pst.setNull(++i, java.sql.Types.INTEGER);
	        pst.setNull(++i, java.sql.Types.BOOLEAN);
	        pst.setNull(++i, java.sql.Types.INTEGER);
	        pst.setNull(++i, java.sql.Types.INTEGER);

		    
		    DatabaseUtils.setInt(pst, ++i, tipoTampone);
		    DatabaseUtils.setInt(pst, ++i, esitoTampone);
		    DatabaseUtils.setInt(pst, ++i, destinatarioTampone);
		    	pst.setTimestamp(++i, dataAccettazione );		    
		    	pst.setString(++i, dataAccettazioneTimeZone);
		    	pst.setBoolean(++i, alimentiOrigineAnimale);
		    	pst.setBoolean(++i, alimentiOrigineVegetale);
		    	pst.setBoolean(++i, alimentiComposti);
		    	pst.setInt(++i, alimentiOrigineAnimaleNonTrasformati);
		    	pst.setInt(++i, alimentiOrigineAnimaleTrasformati);
		    	pst.setInt(++i, alimentiOrigineVegetaleValori);
		    	pst.setInt(++i, alimentiOrigineAnimaleNonTrasformatiValori);
		    	pst.setBoolean(++i, allerta);
		    	pst.setBoolean(++i, nonConformita);
		    	pst.setInt(++i, conseguenzePositivita);
		    	pst.setInt(++i, responsabilitaPositivita);
		    	pst.setString(++i, noteEsito);
		    	pst.setInt(++i, nucleoIspettivo);
		    	pst.setInt(++i, nucleoIspettivoDue);
		    	pst.setInt(++i, nucleoIspettivoTre);
		    	pst.setString(++i, componenteNucleo);
		    	pst.setString(++i, componenteNucleoDue);
		    	pst.setString(++i, componenteNucleoTre);
		    	pst.setString(++i, testoAlimentoComposto);
		    	pst.setInt(++i, nucleoIspettivoQuattro);
		    	pst.setInt(++i, nucleoIspettivoCinque);
		    	pst.setInt(++i, nucleoIspettivoSei);
		    	pst.setInt(++i, nucleoIspettivoSette);
		    	pst.setInt(++i, nucleoIspettivoOtto);
		    	pst.setInt(++i, nucleoIspettivoNove);
		    	pst.setInt(++i, nucleoIspettivoDieci);
		    	pst.setString(++i, componenteNucleoQuattro);
		    	pst.setString(++i, componenteNucleoCinque);
		    	pst.setString(++i, componenteNucleoSei);
		    	pst.setString(++i, componenteNucleoSette);
		    	pst.setString(++i, componenteNucleoOtto);
		    	pst.setString(++i, componenteNucleoNove);
		    	pst.setString(++i, componenteNucleoDieci);
			    
		    	pst.setInt(++i, punteggio);
			    
		    	
		    	
		    	
		    	
		    
		    pst.setInt(++i, id);
		    /*if (!override && this.getModified() != null) {
		      pst.setTimestamp(++i, this.getModified());
		    }*/
		    resultCount = pst.executeUpdate();
		    pst.close();
	
		    return resultCount;
		  }
	  
	  public int chiudi(Connection db) throws SQLException {
		    int resultCount = 0;
		    try {
		      db.setAutoCommit(false);
		      PreparedStatement pst = null;
		      String sql =
		          "UPDATE ticket " +
		          "SET closed = ?, modified = " + DatabaseUtils.getCurrentTimestamp(
		          db) + ", modifiedby = ? " +
		          "WHERE ticketid = ? ";
		      int i = 0;
		      pst = db.prepareStatement(sql);
		      pst.setTimestamp( ++i, new Timestamp( System.currentTimeMillis() ) );
		      pst.setInt(++i, this.getModifiedBy());
		      pst.setInt(++i, this.getId());
		      resultCount = pst.executeUpdate();
		      pst.close();
		      this.setClosed((java.sql.Timestamp) null);
		      //Update the ticket log
		   
		      db.commit();
		    } catch (SQLException e) {
		      db.rollback();
		      throw new SQLException(e.getMessage());
		    } finally {
		      db.setAutoCommit(true);
		    }
		    return resultCount;
		  }

		public void updateEntry(Connection db) throws SQLException {
			
		}

		public boolean delete(Connection db, String baseFilePath)
		throws SQLException {
	if (this.getId() == -1) {
		throw new SQLException("Ticket ID not specified.");
	}
	boolean commit = db.getAutoCommit();
	try {
		if (commit) {
			db.setAutoCommit(false);
		}
		PreparedStatement pst = null ;

		// Logic Delete the ticket
		/*pst = db.prepareStatement("DELETE FROM ticket WHERE ticketid = ?");
		pst.setInt(1, this.getId());*/
		pst = db.prepareStatement("UPDATE ticket SET trashed_date = ? , modified=now() , modifiedby = ? WHERE ticketid = ?");
		pst.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
		pst.setInt(2, this.getModifiedBy());
		pst.setInt(3, this.getId());
		pst.execute();
		pst.close();
		
		/*Ricalcolo punteggio controllo ufficiale*/
		pst = db.prepareStatement("select id_controllo_ufficiale from ticket where ticketid = ?");
		pst.setInt(1, this.getId());
		ResultSet rs = pst.executeQuery();
		
		int seleziona_id_controllo_ufficiale = -1;
		
		if(rs.next())
		seleziona_id_controllo_ufficiale = Integer.parseInt(rs.getString("id_controllo_ufficiale"));
		
		
		
		pst = db.prepareStatement("UPDATE ticket SET punteggio = (select sum(punteggio) " +
				"from ticket where trashed_date is null and tipologia in (2,8,7) and id_controllo_ufficiale = " +
				"(select id_controllo_ufficiale from ticket where ticketid = ?)) " +
				"WHERE ticketid = ("+seleziona_id_controllo_ufficiale+");");
		
		pst.setInt(1, this.getId());
		
		
		pst.execute();
		pst.close();
		
		
		if (commit) {
			db.commit();
		}
	} catch (SQLException e) {
		if (commit) {
			db.rollback();
		}
		throw new SQLException(e.getMessage());
	} finally {
		if (commit) {
			db.setAutoCommit(true);
		}
	}
	return true;
}
		
		public int reopen(Connection db) throws SQLException {
			int resultCount = 0;
			try {
				db.setAutoCommit(false);
				PreparedStatement pst = null;
				String sql = "UPDATE ticket " + "SET closed = ?, modified = "
						+ DatabaseUtils.getCurrentTimestamp(db)
						+ ", modifiedby = ? " + "WHERE ticketid = ? ";
				int i = 0;
				pst = db.prepareStatement(sql);
				pst.setNull(++i, java.sql.Types.TIMESTAMP);
				pst.setInt(++i, this.getModifiedBy());
				pst.setInt(++i, this.getId());
				resultCount = pst.executeUpdate();
				pst.close();
				this.setClosed((java.sql.Timestamp) null);
				// Update the ticket log
			
				db.commit();
			} catch (SQLException e) {
				db.rollback();
				throw new SQLException(e.getMessage());
			} finally {
				db.setAutoCommit(true);
			}
			return resultCount;
		}

		public String getTestoAppoggio() {
			return testoAppoggio;
		}

		public void setTestoAppoggio(String testoAppoggio) {
			this.testoAppoggio = testoAppoggio;
		}

		public String getTestoAlimentoComposto() {
			return testoAlimentoComposto;
		}

		public void setTestoAlimentoComposto(String testoAlimentoComposto) {
			this.testoAlimentoComposto = testoAlimentoComposto;
		}

		public int getNucleoIspettivoQuattro() {
			return nucleoIspettivoQuattro;
		}

		public void setNucleoIspettivoQuattro(int nucleoIspettivoQuattro) {
			this.nucleoIspettivoQuattro = nucleoIspettivoQuattro;
		}

		public int getNucleoIspettivoCinque() {
			return nucleoIspettivoCinque;
		}

		public void setNucleoIspettivoCinque(int nucleoIspettivoCinque) {
			this.nucleoIspettivoCinque = nucleoIspettivoCinque;
		}

		public int getNucleoIspettivoSei() {
			return nucleoIspettivoSei;
		}

		public void setNucleoIspettivoSei(int nucleoIspettivoSei) {
			this.nucleoIspettivoSei = nucleoIspettivoSei;
		}

		public int getNucleoIspettivoSette() {
			return nucleoIspettivoSette;
		}

		public void setNucleoIspettivoSette(int nucleoIspettivoSette) {
			this.nucleoIspettivoSette = nucleoIspettivoSette;
		}

		public int getNucleoIspettivoOtto() {
			return nucleoIspettivoOtto;
		}

		public void setNucleoIspettivoOtto(int nucleoIspettivoOtto) {
			this.nucleoIspettivoOtto = nucleoIspettivoOtto;
		}

		public int getNucleoIspettivoNove() {
			return nucleoIspettivoNove;
		}

		public void setNucleoIspettivoNove(int nucleoIspettivoNove) {
			this.nucleoIspettivoNove = nucleoIspettivoNove;
		}

		public int getNucleoIspettivoDieci() {
			return nucleoIspettivoDieci;
		}

		public void setNucleoIspettivoDieci(int nucleoIspettivoDieci) {
			this.nucleoIspettivoDieci = nucleoIspettivoDieci;
		}

		public String getComponenteNucleoQuattro() {
			return componenteNucleoQuattro;
		}

		public void setComponenteNucleoQuattro(String componenteNucleoQuattro) {
			this.componenteNucleoQuattro = componenteNucleoQuattro;
		}

		public String getComponenteNucleoCinque() {
			return componenteNucleoCinque;
		}

		public void setComponenteNucleoCinque(String componenteNucleoCinque) {
			this.componenteNucleoCinque = componenteNucleoCinque;
		}

		public String getComponenteNucleoSei() {
			return componenteNucleoSei;
		}

		public void setComponenteNucleoSei(String componenteNucleoSei) {
			this.componenteNucleoSei = componenteNucleoSei;
		}

		public String getComponenteNucleoSette() {
			return componenteNucleoSette;
		}

		public void setComponenteNucleoSette(String componenteNucleoSette) {
			this.componenteNucleoSette = componenteNucleoSette;
		}

		public String getComponenteNucleoOtto() {
			return componenteNucleoOtto;
		}

		public void setComponenteNucleoOtto(String componenteNucleoOtto) {
			this.componenteNucleoOtto = componenteNucleoOtto;
		}

		public String getComponenteNucleoNove() {
			return componenteNucleoNove;
		}

		public void setComponenteNucleoNove(String componenteNucleoNove) {
			this.componenteNucleoNove = componenteNucleoNove;
		}

		public String getComponenteNucleoDieci() {
			return componenteNucleoDieci;
		}

		public void setComponenteNucleoDieci(String componenteNucleoDieci) {
			this.componenteNucleoDieci = componenteNucleoDieci;
		}
		
		
	  
}

