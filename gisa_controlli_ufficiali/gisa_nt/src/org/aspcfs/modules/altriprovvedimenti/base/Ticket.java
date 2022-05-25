/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.altriprovvedimenti.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import oracle.net.TNSAddress.Description;

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
import org.aspcfs.utils.DwrNonConformita;
import org.aspcfs.utils.PunteggiNonConformita;
import org.postgresql.util.PSQLException;

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.FileItemList;

public class Ticket extends org.aspcfs.modules.troubletickets.base.Ticket
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -357873719517180996L;
	private String paddedControlloUfficiale="";
	private boolean soa = false;
	private String ncGraviValutazioni;
	private int tipoControllo ;
	private boolean haveOggettoControllo ;
	protected ArrayList<ElementoNonConformita> non_conformita_gravi			=	new ArrayList<ElementoNonConformita>();
	protected String tipo_richiesta = "";
	protected int tipologia = -1;
	//protected String dati_extra = "";
	protected String pippo = "";
	protected int provvedimenti = -1;
	protected int nonconformitaAmministrative = -1;
	protected int nonconformitaPenali = -1;
	protected int nonconformita = -1;
	protected String descrizione1 = "";
	protected String descrizione2 = "";
	protected String descrizione3 = "";
	private String idControlloUfficiale = null;
	private String identificativo = null;
	private boolean farmacosorveglianza = false;
	private boolean tipoNonConformitaDue = false;
    private boolean tipoNonConformitaTre = false;
    private boolean tipoNonConformitaQuattro = false; 
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
    private String nonConformitaGravi="";
    private String puntiGravi=null;
   private int punteggio=0;
   private String sottoAttivita ;
   
   private int idTipologiaImpresaSanzionata ;
   private int idImpresaSanzionata ;
   private String ragioneSocialeImpresaSanzionata ;
   
   private boolean fuoriRegioneImpresaSanzionata = false ;
   
   
  
   public int getIdTipologiaImpresaSanzionata() {
	return idTipologiaImpresaSanzionata;
}

public void setIdTipologiaImpresaSanzionata(int idTipologiaImpresaSanzionata) {
	this.idTipologiaImpresaSanzionata = idTipologiaImpresaSanzionata;
}

public int getIdImpresaSanzionata() {
	return idImpresaSanzionata;
}

public void setIdImpresaSanzionata(int idImpresaSanzionata) {
	this.idImpresaSanzionata = idImpresaSanzionata;
}
public void setIdImpresaSanzionata(String idImpresaSanzionata) {
	if (idImpresaSanzionata!=null && !idImpresaSanzionata.equals("null") && !idImpresaSanzionata.equals(""))
	this.idImpresaSanzionata = Integer.parseInt(idImpresaSanzionata);
}

public String getRagioneSocialeImpresaSanzionata() {
	return ragioneSocialeImpresaSanzionata;
}

public void setRagioneSocialeImpresaSanzionata(String ragioneSocialeImpresaSanzionata) {
	this.ragioneSocialeImpresaSanzionata = ragioneSocialeImpresaSanzionata;
}
private boolean ignoraPunteggi = false; //ACQUE DI RETE NON CONFORMITA'
   
   
   
   public void setIgnoraPunteggi(boolean ignoraPunteggi) {
		this.ignoraPunteggi = ignoraPunteggi;
	}
   
   public String getSottoAttivita() {
	return sottoAttivita;
}
public void setSottoAttivita(String sottoAttivita) {
	this.sottoAttivita = sottoAttivita;
}
public Ticket ()
   {
	   
   }
	public boolean isHaveOggettoControllo() {
		return haveOggettoControllo;
	}

	public void setHaveOggettoControllo(boolean haveOggettoControllo) {
		this.haveOggettoControllo = haveOggettoControllo;
	}


	public int getTipoControllo() {
		return tipoControllo;
	}


	public void setTipoControllo(int tipoControllo) {
		this.tipoControllo = tipoControllo;
	}


	public void setNcGraviValutazioni(String ncGravi) {
		this.ncGraviValutazioni = ncGravi;
	}

	public String getNcGraviValutazioni() {
		return ncGraviValutazioni;
	}
	
	
	
	public boolean isSoa() {
		return soa;
	}


	public void setSoa(boolean soa) {
		this.soa = soa;
	}
	
	
	
	
	public ArrayList<ElementoNonConformita> getNon_conformita_gravi() {
		return non_conformita_gravi;
	}


	public boolean isFarmacosorveglianza() {
		return farmacosorveglianza;
	}


	public void setFarmacosorveglianza(boolean farmacosorveglianza) {
		this.farmacosorveglianza = farmacosorveglianza;
	}
	//aggiunto da d.dauria per gestire i nonconformita
    private boolean tipoNonConformita = false;
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

	
	

	public String getNonConformitaGravi() {
		return nonConformitaGravi;
	}

	public void setNonConformitaGravi(String nonConformitaGravi) {
		this.nonConformitaGravi = nonConformitaGravi;
	}

	

	public String getPuntiGravi() {
		return puntiGravi;
	}

	public void setPuntiGravi(String puntiGravi) {
		this.puntiGravi = puntiGravi;
	}

	public int getPunteggio() {
		return punteggio;
	}

	public void setPunteggio(int punteggio) {
		this.punteggio = punteggio;
	}

	public void setNucleoIspettivoQuattro(String temp)
	{
		this.nucleoIspettivoQuattro = Integer.parseInt(temp);
	}
	public void setPunteggio(String temp)
	{
		if(temp!=null){
			if(!temp.equals(""))
		this.punteggio = Integer.parseInt(temp);
		}
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

    public boolean getTipoNonConformita() {
		return tipoNonConformita;
	}

	public void setTipoNonConformita(boolean tipoNonConformita) {
		this.tipoNonConformita = tipoNonConformita;
	}
	
	public void setTipoNonConformita(String temp) {
		this.tipoNonConformita = DatabaseUtils.parseBoolean(temp);
	}

	public boolean getTipoNonConformitaDue() {
		return tipoNonConformitaDue;
	}

	public void setTipoNonConformitaDue(boolean tipoNonConformitaDue) {
		this.tipoNonConformitaDue = tipoNonConformitaDue;
	}
	
	public void setTipoNonConformitaDue(String temp) {
		this.tipoNonConformitaDue = DatabaseUtils.parseBoolean(temp);
	}

	public boolean getTipoNonConformitaTre() {
		return tipoNonConformitaTre;
	}

	public void setTipoNonConformitaTre(boolean tipoNonConformitaTre) {
		this.tipoNonConformitaTre = tipoNonConformitaTre;
	}

	public void setTipoNonConformitaTre(String temp) {
		this.tipoNonConformitaTre = DatabaseUtils.parseBoolean(temp);
	}
	
	public boolean getTipoNonConformitaQuattro() {
		return tipoNonConformitaQuattro;
	}

	public void setTipoNonConformitaQuattro(boolean tipoNonConformitaQuattro) {
		this.tipoNonConformitaQuattro = tipoNonConformitaQuattro;
	}
	
	public void setTipoNonConformitaQuattro(String temp) {
		this.tipoNonConformitaQuattro = DatabaseUtils.parseBoolean(temp);
	}

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
	
	public int getProvvedimenti() {
		return provvedimenti;
	}
	
	public void setProvvedimenti(String provvedimenti) {
	    try {
	      this.provvedimenti = Integer.parseInt(provvedimenti);
	    } catch (Exception e) {
	      this.provvedimenti = -1;
	    }
	  }

	public void setNonConformitaAmministrative(String nonconformitaAmministrative) {
	    try {
	      this.nonconformitaAmministrative = Integer.parseInt(nonconformitaAmministrative);
	    } catch (Exception e) {
	      this.nonconformitaAmministrative = -1;
	    }
	  }
	
	public void setNonConformitaPenali(String nonconformitaPenali) {
	    try {
	      this.nonconformitaPenali = Integer.parseInt(nonconformitaPenali);
	    } catch (Exception e) {
	      this.nonconformitaPenali = -1;
	    }
	  }
	
	public void setNonConformita(String nonconformita) {
	    try {
	      this.nonconformita = Integer.parseInt(nonconformita);
	    } catch (Exception e) {
	      this.nonconformita = -1;
	    }
	  }

	public void setProvvedimenti(int provvedimenti) {
		this.provvedimenti = provvedimenti;
	}
	
	public int getNonConformitaAmministrative() {
		return nonconformitaAmministrative;
	}

	public void setNonConformitaAmministrative(int nonconformitaAmministrative) {
		this.nonconformitaAmministrative = nonconformitaAmministrative;
	}
	
	public int getNonConformitaPenali() {
		return nonconformitaPenali;
	}

	public void setSequestrPenali(int nonconformitaPenali) {
		this.nonconformitaPenali = nonconformitaPenali;
	}
	
	public int getNonConformita() {
		return nonconformita;
	}

	public void setNonConformita(int nonconformita) {
		this.nonconformita = nonconformita;
	}

	
	public String getTipo_richiesta() {
		return tipo_richiesta;
	}

	public void setTipo_richiesta(String tipo_richiesta) {
		this.tipo_richiesta = tipo_richiesta;
	}
	

	  public Ticket(ResultSet rs , Connection db) throws SQLException {
		
	    buildRecord(rs);
	    org.aspcfs.modules.vigilanza.base.Ticket cu = new org.aspcfs.modules.vigilanza.base.Ticket();
  	  	cu.setPianoispezioniSelezionate(db,Integer.parseInt(idControlloUfficiale));
  	  cu.setTipoCampione(rs.getInt("tipo_controllo_uff"));
  	  cu.setAssignedDate(rs.getTimestamp("data_controllo"));
	    haveOggettoControllo = false ;
  	  	if (cu.getLisaElementi_Ispezioni().size()>0)
  	  	{
  	  		haveOggettoControllo = true ;
  	  	}
	    
	    
  	  	this.setNon_conformita_gravi(db,cu.getTipoCampione(),cu.getAssignedDate());
	  }
	  
	  public Ticket(ResultSet rs , Connection db, int ignoraPunteggi) throws SQLException {
			//ACQUE DI RETE: IGNORA PUNTEGGI
		    buildRecord(rs);
		    org.aspcfs.modules.vigilanza.base.Ticket cu = new org.aspcfs.modules.vigilanza.base.Ticket();
	  	  	cu.queryRecord(db,Integer.parseInt(this.idControlloUfficiale));
	  	  	tipoControllo = cu.getTipoCampione();
		    haveOggettoControllo = false ;
	  	  	if (cu.getLisaElementi_Ispezioni().size()>0)
	  	  	{
	  	  		haveOggettoControllo = true ;
	  	  	}
		    
		    
//		    this.set_NonConformitaFormali(db);
//	  	  	this.setNon_conformita_gravi(db);
//	  	  	this.setNon_conformita_significative(db);
		  }

	  public Ticket(Connection db, int id,boolean soa,boolean flag) throws SQLException {
		this.soa = soa;
	    queryRecord(db, id);
	  }
	  
	  public Ticket(Connection db, int id) throws SQLException {
		    queryRecord(db, id);
		  }
	  
	  public Ticket(Connection db, int id, int ignoraPunteggi) throws SQLException {
		  	this.ignoraPunteggi=true;
		    queryRecord(db, id);
		  }
	 
	  
	  public Ticket(Connection db, int id,boolean isTrasporto) throws SQLException {
		    queryRecordTrasporti(db, id);
		  }
	  
	  public String getDescrizione1() {
			return descrizione1;
		}

		public void setDescrizione1(String descrizione1) {
			this.descrizione1 = descrizione1;
		}
		
		  public String getDescrizione2() {
				return descrizione2;
			}

			public void setDescrizione2(String descrizione2) {
				this.descrizione2 = descrizione2;
			}
			  public String getDescrizione3() {
					return descrizione3;
				}

				public void setDescrizione3(String descrizione3) {
					this.descrizione3 = descrizione3;
				}

	  public void queryRecord(Connection db, int id) throws SQLException {
		    if (id == -1) {
		      throw new SQLException("Invalid Ticket Number");
		    }
		   
		      String sql =   "SELECT t.*,tic.provvedimenti_prescrittivi as tipo_controllo_uff,tic.chiusura_attesa_esito , tic.assigned_date as data_controllo," +
		        "o.site_id AS orgsiteid," +
		        "o.tipologia as tipologia_operatore FROM ticket t ";
		      	sql += "LEFT JOIN organization o ON (t.org_id = o.org_id) ";
		      	sql +="INNER JOIN ticket tic ON ((tic.tipologia = 3)and(tic.id_controllo_ufficiale = t.id_controllo_ufficiale)) " +
		      	"WHERE t.ticketid = ? AND t.tipologia = 10";
		        PreparedStatement pst = db.prepareStatement(sql);
		        
		    pst.setInt(1, id);
		    ResultSet rs = pst.executeQuery();
		    if (rs.next()) {
		      buildRecord(rs);
		      org.aspcfs.modules.vigilanza.base.Ticket cu = new org.aspcfs.modules.vigilanza.base.Ticket();
	    	  cu.setPianoispezioniSelezionate(db,Integer.parseInt(this.idControlloUfficiale));
	    	  cu.setTipoCampione(rs.getInt("tipo_controllo_uff"));
	    	  cu.setAssignedDate(rs.getTimestamp("data_controllo"));
	    	  //tipoControllo = cu.getTipoCampione();
	    	  haveOggettoControllo = false ;
	    	  if (cu.getLisaElementi_Ispezioni().size()>0)
	    	  {
	    		  haveOggettoControllo = true ;
	    	  }
		    	  
		    	  this.setNon_conformita_gravi(db,cu.getTipoCampione(),cu.getAssignedDate());
		    	  super.controlloBloccoCu(db, Integer.parseInt(this.idControlloUfficiale));
		    }
		    rs.close();
		    pst.close();
		    if (this.id == -1) {
		      throw new SQLException(Constants.NOT_FOUND_ERROR);
		    }
		  
		 
		  }
	  
	  
	  
	  public void queryRecordTrasporti(Connection db, int id) throws SQLException {
		    if (id == -1) {
		      throw new SQLException("Invalid Ticket Number");
		    }
		    PreparedStatement pst = db.prepareStatement(
		        "SELECT t.*,tic.provvedimenti_prescrittivi as tipo_controllo_uff,tic.assigned_date as dataInizioControllo,  " +
		        "o.name AS orgname, " +
		        "o.enabled AS orgenabled, " +
		        "o.site_id AS orgsiteid "+
		       		        "FROM ticket t " +
		        "LEFT JOIN organization o ON (t.org_id = o.org_id) " +
		    	"INNER JOIN ticket tic ON ((tic.tipologia = 3)and(tic.org_id = t.org_id)and(tic.id_controllo_ufficiale = t.id_controllo_ufficiale)) "+
		       
		     
		        "LEFT JOIN asset a ON (t.link_asset_id = a.asset_id) " +
		      
		        "WHERE t.ticketid = ? AND t.tipologia = 10");
		    pst.setInt(1, id);
		    ResultSet rs = pst.executeQuery();
		    org.aspcfs.modules.vigilanza.base.Ticket cu = new org.aspcfs.modules.vigilanza.base.Ticket();
		   
		    if (rs.next()) {
		      buildRecord(rs);
		      cu.setTipoCampione(rs.getInt("tipo_controllo_uff"));
			    cu.setAssignedDate(rs.getTimestamp("dataInizioControllo"));
		      this.setNon_conformita_gravi(db,cu.getTipoCampione(),cu.getAssignedDate());
		      
		    }
		    rs.close();
		    pst.close();
		    if (this.id == -1) {
		      throw new SQLException(Constants.NOT_FOUND_ERROR);
		    }
		   
		
		  }
	  
	  public void delAllNc(Connection db) throws SQLException
	  {
		  String del = "delete from salvataggio_nc_note where idticket = ?";
		  
		  PreparedStatement pst_del =	db.prepareStatement(del) ;
		  pst_del.setInt(1, id);
		  pst_del.execute();
		 
		  
	  }
	  
	  public void delAttivitaAppese(Connection db,String idattivita)
	  {
		  try
		  {
			  
			  String sql = "delete from ticket where id_controllo_ufficiale= ? and tipologia in (15) and id_nonconformita is null ";
			
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setString(1, idControlloUfficiale);
			pst.execute();
			  
		  }catch(SQLException e)
			  {
				  e.printStackTrace();
			  }
			  
		  
	  }
	  public boolean insertTipiNonConformita(Connection db,String nc , int tipologia , String note  , String idSottoattivita, String idLineaNc, String idSottoattivitaBenessereMacellazione, String idSottoattivitaBenessereTrasporto, String idOperatoreMercato ) throws SQLException {
		  
		if (nc!= null && ! nc.equals("-1"))
		{

			String insert_note = "insert into salvataggio_nc_note (idticket,note,tipologia,id_non_conformita, id_linea_nc, id_non_conformita_benessere_macellazione, id_non_conformita_benessere_trasporto, id_operatore_mercato) values (?,?,?,?, ?, ?, ?, ?)";
			PreparedStatement pst_note = db.prepareStatement(insert_note);
			pst_note.setInt(1, id);
			pst_note.setString(2, note);
			pst_note.setInt(3, tipologia);
			pst_note.setInt(4, Integer.parseInt(nc));
			pst_note.setInt(5, Integer.parseInt((idLineaNc!=null && !idLineaNc.equals("") && !idLineaNc.equals("null")) ? idLineaNc : "-1"));
			pst_note.setInt(6, Integer.parseInt((idSottoattivitaBenessereMacellazione!=null && !idSottoattivitaBenessereMacellazione.equals("") && !idSottoattivitaBenessereMacellazione.equals("null")) ? idSottoattivitaBenessereMacellazione : "-1"));
			pst_note.setInt(7, Integer.parseInt((idSottoattivitaBenessereTrasporto!=null && !idSottoattivitaBenessereTrasporto.equals("") && !idSottoattivitaBenessereTrasporto.equals("null")) ? idSottoattivitaBenessereTrasporto : "-1"));
			pst_note.setInt(8, Integer.parseInt((idOperatoreMercato!=null && !idOperatoreMercato.equals("") && !idOperatoreMercato.equals("null")) ? idOperatoreMercato : "-1"));
			pst_note.execute();
			pst_note.close();

		}
		
		/**
		 * 	AGGANCIO LE SOTTOATTIVITA ALLA NC INSERITA TRAMITE IDENTIFICATIVO NC E ID_NONCONFORMITA
		 */
		
		PreparedStatement pst = null ;
		String[] sottoattivita = idSottoattivita.split(";");
		String sql = "update ticket set id_nonconformita = ?,identificativonc = ? where  ticketid in (";
		for(int j =0;j<sottoattivita.length-1;j++)
		{
			sql+=sottoattivita[j]+",";
		}
		sql+=sottoattivita[sottoattivita.length-1]+") and tipo_nc = ? ";
		
		pst = db.prepareStatement(sql);
		pst.setInt(1, this.id);
		pst.setString(2, this.identificativo);
		pst.setInt(3, tipologia);
		pst.execute();
		  
		
		  

		  return true;
	  }
	  
	  public boolean updateControllo(Connection db, int ticketId) throws SQLException {
		  String insert="UPDATE ticket SET ncrilevate = TRUE WHERE ticketId = ?";
		  PreparedStatement pst=db.prepareStatement(insert);
		  pst.setInt(1, ticketId);
		  pst.execute();
		  pst.close();
		  return true;
	  }
	  
	  public synchronized boolean insert(Connection db,ActionContext context) throws SQLException {
		    StringBuffer sql = new StringBuffer();
		 
		    try {
		      
		     
		    	UserBean user = (UserBean)context.getSession().getAttribute("User");
				int livello=1 ;
				if (user.getUserRecord().getGruppo_ruolo()==2)
					livello=2;
//				id = DatabaseUtils.getNextInt( db, "ticket","ticketid",livello);
				
				sql.append(
		          "INSERT INTO ticket (contact_id, problem, pri_code, " +
		          "department_code, cat_code, scode,  link_contract_id, " +
		          "link_asset_id, expectation, product_id, customer_product_id, " +
		          "key_count, status_id, trashed_date, user_group_id, cause_id, " +
		          "resolution_id, defect_id, escalation_level, resolvable, " +
		          "resolvedby, resolvedby_department_code, state_id, site_id,ip_entered,ip_modified, ");
				
				if (assignedDate!=null)
				{
			    	 sql.append("assigned_date, ");

				}
		  
				if (idTipologiaImpresaSanzionata>0)
				{
			    	 sql.append("tipologia_impresa_sanzionata, ");

				}
				
				if (idImpresaSanzionata>0)
				{
			    	 sql.append("id_impresa_sanzionata, ");

				}
			
				if (ragioneSocialeImpresaSanzionata != null )
				{
			    	 sql.append("ragione_sociale_impresa_sanzionata, ");

				}
				
				
			    	 sql.append("fuori_regione_impresa_sanzionata, ");

			    	 sql.append("alt_id, ");
				
				if (farmacosorveglianza == true)
		     {
		    	 sql.append("id_farmacia, ");
		     }
		     else
		     {
		    	 sql.append("org_id,id_stabilimento, id_apiario, ");
		     }
		     
		     sql.append("nc_gravi_valutazione, ");
		     
		     
		     
		        sql.append("ticketid, ");
		     
		      if (entered != null) {
		        sql.append("entered, ");
		      }
		      if (modified != null) {
		        sql.append("modified, ");
		      }
		      if ( nucleoIspettivo > -1) {
			        sql.append("nucleo_ispettivo,");
			      }
		      if ( nucleoIspettivoDue > -1) {
			        sql.append(" nucleo_ispettivo_due,");
			      }
		      if ( nucleoIspettivoTre > -1) {
			        sql.append(" nucleo_ispettivo_tre,");
			      }
		      if (componenteNucleo != null) {
			        sql.append(" componente_nucleo,");
			      }
		      if (componenteNucleoDue != null) {
			        sql.append(" componente_nucleo_due,");
			      }
		      if (componenteNucleoTre != null) {
			        sql.append(" componente_nucleo_tre,");
			      }
		      if (testoAlimentoComposto != null) {
			        sql.append("testo_alimento_composto,");
			      }
		      if ( nucleoIspettivoQuattro > -1) {
			        sql.append(" nucleo_ispettivo_quattro,");
			      }
		      if ( nucleoIspettivoCinque > -1) {
			        sql.append(" nucleo_ispettivo_cinque,");
			      }
		      if ( nucleoIspettivoSei > -1) {
			        sql.append("nucleo_ispettivo_sei,");
			      }
		      if ( nucleoIspettivoSette > -1) {
			        sql.append(" nucleo_ispettivo_sette,");
			      }
		      if ( nucleoIspettivoOtto > -1) {
			        sql.append(" nucleo_ispettivo_otto,");
			      }
		      if ( nucleoIspettivoNove > -1) {
			        sql.append(" nucleo_ispettivo_nove,");
			      }
		      if ( nucleoIspettivoDieci > -1) {
			        sql.append(" nucleo_ispettivo_dieci,");
			      }
		      if (componenteNucleoQuattro != null) {
			        sql.append(" componente_nucleo_quattro,");
			      }
		      if (componenteNucleoCinque != null) {
			        sql.append(" componente_nucleo_cinque,");
			      }
		      if (componenteNucleoSei != null) {
			        sql.append(" componente_nucleo_sei,");
			      }
		      if (componenteNucleoSette != null) {
			        sql.append("componente_nucleo_sette,");
			      }
		      if (componenteNucleoOtto != null) {
			        sql.append(" componente_nucleo_otto,");
			      }
		      if (componenteNucleoNove != null) {
			        sql.append(" componente_nucleo_nove,");
			      }
		      if (componenteNucleoDieci != null) {
			        sql.append(" componente_nucleo_dieci,");
			      }
		      
		    
		      
		      
		      if (nonConformitaGravi != null) {
			        sql.append(" nonconformitagravi,");
			      }
		      
		      
		    
		      
		      if (puntiGravi != null) {
			        sql.append(" puntigravi,");
			      }
		      sql.append(" id_controllo_ufficiale,");
		      if (punteggio != 0) {
			        sql.append(" punteggio,");
			      }
		      
		      
		      sql.append("tipo_richiesta, custom_data, enteredBy, modifiedBy, " +
		      		"tipologia, provvedimenti_prescrittivi, sanzioni_amministrative, sanzioni_penali, sequestri, descrizione1, descrizione2, descrizione3  ");
		     
		      sql.append(" , tipo_sequestro");
		      sql.append(" , tipo_sequestro_due");
		      sql.append(" , tipo_sequestro_tre");
		      sql.append(" , tipo_sequestro_quattro");
		      sql.append(")");
		      sql.append("VALUES (?, ?, ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?, ");
		      sql.append("?, ?, ?, ");
		    
		      
		    if (assignedDate!=null)
				{
			    	 sql.append("?, ");

				}
		      
		  	if (idTipologiaImpresaSanzionata>0)
			{
		    	 sql.append("?, ");

			}
			
			if (idImpresaSanzionata>0)
			{
		    	 sql.append("?, ");

			}
		
			if (ragioneSocialeImpresaSanzionata != null )
			{
		    	 sql.append("?, ");

			}
		
		    	 sql.append("?, ");

		    	 sql.append("?, ");

		      if (farmacosorveglianza == true)
			     {
		    	  sql.append("?,");
			     }
			     else
			     {
			    	  sql.append("?,?,?,");
			     }
		      sql.append("?,"); //per valutazioni gravi ho aggiunto un ?
		      
		        sql.append(DatabaseUtils.getNextIntSql("ticket", "ticketid", livello)+",");
		      
		      if (entered != null) {
		        sql.append("?, ");
		      }
		      if (modified != null) {
		        sql.append("?, ");
		      }
		      if (nucleoIspettivo > -1) {
			        sql.append("?,");
			      }
		      if (nucleoIspettivoDue > -1) {
			        sql.append("?,");
			      }
		      if (nucleoIspettivoTre > -1) {
			        sql.append("?,");
			      }
		      if (componenteNucleo != null) {
			        sql.append("?, ");
			      }
		      if (componenteNucleoDue != null) {
			        sql.append("?, ");
			      }
		      if (componenteNucleoTre != null) {
			        sql.append("?, ");
			      }
		      if (testoAlimentoComposto != null) {
			        sql.append("?,");
			      }
		      if (nucleoIspettivoQuattro > -1) {
			        sql.append("?,");
			      }
		      if (nucleoIspettivoCinque > -1) {
			        sql.append("?,");
			      }
		      if (nucleoIspettivoSei > -1) {
			        sql.append("?,");
			      }
		      if (nucleoIspettivoSette > -1) {
			        sql.append("?,");
			      }
		      if (nucleoIspettivoOtto > -1) {
			        sql.append("?,");
			      }
		      if (nucleoIspettivoNove > -1) {
			        sql.append(", ?");
			      }
		      if (nucleoIspettivoDieci > -1) {
			        sql.append("?,");
			      }
		      if (componenteNucleoQuattro != null) {
			        sql.append("?, ");
			      }
		      if (componenteNucleoCinque != null) {
			        sql.append("?, ");
			      }
		      if (componenteNucleoSei != null) {
			        sql.append("?, ");
			      }
		      if (componenteNucleoSette != null) {
			        sql.append("?, ");
			      }
		      if (componenteNucleoOtto != null) {
			        sql.append("?, ");
			      }
		      if (componenteNucleoNove != null) {
			        sql.append("?, ");
			      }
		      if (componenteNucleoDieci != null) {
			        sql.append("?, ");
			      }
		      
		      
		      
		      
		      if (nonConformitaGravi != null) {
			        sql.append(" ?,");
			      }
		      
		    
		      
		      
		      if (puntiGravi != null) {
			        sql.append(" ?,");
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
		      sql.append(" ?, ");
		      
		      if (punteggio != 0) {
			        sql.append(" ?,");
			      }
		      
		      sql.append("?, ?, ?, ?, " +
		      		     "10, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING ticketid "); //ho aggiunto 2 punti interrogativi
		      
		      int i = 0;
		      PreparedStatement pst = db.prepareStatement(sql.toString());
		      DatabaseUtils.setInt(pst, ++i, this.getContactId());
		      pst.setString(++i, this.getProblem());
		        pst.setNull(++i, java.sql.Types.INTEGER);
		        pst.setNull(++i, java.sql.Types.INTEGER);
		        pst.setNull(++i, java.sql.Types.INTEGER);
		        pst.setNull(++i, java.sql.Types.INTEGER);

		   
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
		      
			   	if (assignedDate!=null)
				{
			    	  pst.setTimestamp(++i, assignedDate);

				}
		      
		      
		   	if (idTipologiaImpresaSanzionata>0)
			{
		    	  pst.setInt(++i, idTipologiaImpresaSanzionata);

			}
			
			if (idImpresaSanzionata>0)
			{
		    	  pst.setInt(++i, idImpresaSanzionata);

			}
		
			if (ragioneSocialeImpresaSanzionata != null )
			{
		    	  pst.setString(++i, ragioneSocialeImpresaSanzionata);

			}
			
				 pst.setBoolean(++i, fuoriRegioneImpresaSanzionata);

				  pst.setInt(++i, altId);
				  
		      if (farmacosorveglianza == true)
			     {
		    	  pst.setInt(++i, orgId);
			     }
			     else
			     {
			    	 pst.setInt(++i, orgId);
					DatabaseUtils.setInt(pst, ++i, idStabilimento);
					DatabaseUtils.setInt(pst, ++i, idApiario);
			     }
		     
		      pst.setString(++i, ncGraviValutazioni);
//		      if (id > -1) {
//		        pst.setInt(++i, id);
//		      }
		      if (entered != null) {
		        pst.setTimestamp(++i, entered);
		      }
		      if (modified != null) {
		        pst.setTimestamp(++i, modified);
		      } 
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
	     
	      
	      
	      if (nonConformitaGravi != null) {
	    	  pst.setString(++i, nonConformitaGravi);
		      }
	      
	     
	      
	      
	      if (puntiGravi != null) {
	    	  pst.setString(++i, puntiGravi);
		      }
	      pst.setString(++i, idControlloUfficiale);
	      if (punteggio != 0) {
	    	  pst.setInt(++i, punteggio);
		      }
		      
		      
		      
		      /**
		       * 
		       */
		      pst.setString( ++i, this.getTipo_richiesta() );
		     
		      pst.setString( ++i, this.getPippo() );
		      
		      pst.setInt(++i, this.getEnteredBy());
		      pst.setInt(++i, this.getModifiedBy());
		      DatabaseUtils.setInt(pst, ++i, provvedimenti);
		      DatabaseUtils.setInt(pst, ++i, nonconformitaAmministrative);
		      DatabaseUtils.setInt(pst, ++i, nonconformitaPenali);
		      DatabaseUtils.setInt(pst, ++i, nonconformita);
		      pst.setString( ++i, this.getDescrizione1() );
		      pst.setString( ++i, this.getDescrizione2() );
		      pst.setString( ++i, this.getDescrizione3() );
		      pst.setBoolean(++i , this.getTipoNonConformita());
		      pst.setBoolean(++i , this.getTipoNonConformitaDue());
		      pst.setBoolean(++i , this.getTipoNonConformitaTre());
		      pst.setBoolean(++i , this.getTipoNonConformitaQuattro());
		    
		      /* pezzo aggiunto da d.dauria 
		      pst.setTimestamp(++i,new Timestamp( System.currentTimeMillis() ));
		      pst.setString(++i, "x");
		      this.setCloseIt(true);
		         */
		      ResultSet rs = pst.executeQuery();
		      if (rs.next())
		    	  this.id = rs.getInt("ticketid");
		      
		      
				db.prepareStatement("UPDATE TICKET set identificativo = '"+asl+idControlloUfficiale+"' || trim(to_char( "+id+", '"+DatabaseUtils.getPaddedFromId(id)+"' )) where ticketid ="+this.getId()).execute();
		      
		      pst.close();
		      		      
		      //Update the rest of the fields
		      //this.update(db, true);
		    
		    
		    } catch (SQLException e) {
		     
		      throw new SQLException(e.getMessage());
		    } finally {
		      
		    }
		    return true;
		  }


	  protected void buildRecord(ResultSet rs) throws SQLException {
		    //ticket table
		    this.setId(rs.getInt("ticketid"));
		    
		    	orgId = DatabaseUtils.getInt(rs, "org_id");
		    	idStabilimento = DatabaseUtils.getInt(rs, "id_stabilimento");
		    	idApiario = DatabaseUtils.getInt(rs, "id_apiario");
		    	altId = DatabaseUtils.getInt(rs, "alt_id");
		    ncGraviValutazioni = rs.getString("nc_gravi_valutazione");
		    contactId = DatabaseUtils.getInt(rs, "contact_id");
		    problem = rs.getString("problem");
		    entered = rs.getTimestamp("entered");
		    enteredBy = rs.getInt("enteredby");
		    modified = rs.getTimestamp("modified");
		    modifiedBy = rs.getInt("modifiedby");
		    closed = rs.getTimestamp("closed");
		    try
		    {
		    tipoControllo = rs.getInt("tipo_controllo_uff");
		    }
		    catch(PSQLException e)
		    {
		    	
		    }
		    idTipologiaImpresaSanzionata =rs.getInt("tipologia_impresa_sanzionata");
		    idImpresaSanzionata =rs.getInt("id_impresa_sanzionata");
		    ragioneSocialeImpresaSanzionata =rs.getString("ragione_sociale_impresa_sanzionata");
		    fuoriRegioneImpresaSanzionata =rs.getBoolean("fuori_regione_impresa_sanzionata");
			
		    chiusura_attesa_esito = rs.getBoolean("chiusura_attesa_esito");
		     solution = rs.getString("solution");
		    location = rs.getString("location");
		    assignedDate = rs.getTimestamp("assigned_date");
		    estimatedResolutionDate = rs.getTimestamp("est_resolution_date");
		    resolutionDate = rs.getTimestamp("resolution_date");
		    cause = rs.getString("cause");
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
		    provvedimenti = DatabaseUtils.getInt(rs, "provvedimenti_prescrittivi");
		    nonconformitaAmministrative = DatabaseUtils.getInt(rs, "sanzioni_amministrative");
		    nonconformitaPenali = DatabaseUtils.getInt(rs, "sanzioni_penali");
		    nonconformita = DatabaseUtils.getInt(rs, "sequestri");
		    
		    descrizione1 = rs.getString( "descrizione1" );
		    descrizione2 = rs.getString( "descrizione2" );
		    descrizione3 = rs.getString( "descrizione3" );
		    
			tipoNonConformita = rs.getBoolean("tipo_sequestro");
			tipoNonConformitaDue = rs.getBoolean("tipo_sequestro_due");
			tipoNonConformitaTre = rs.getBoolean("tipo_sequestro_tre");
			tipoNonConformitaQuattro = rs.getBoolean("tipo_sequestro_quattro");
			
		    orgSiteId = DatabaseUtils.getInt(rs, "site_id");
		   
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

		    nonConformitaGravi=rs.getString("nonconformitagravi");
		    puntiGravi=rs.getString("puntigravi");
		    idControlloUfficiale = rs.getString("id_controllo_ufficiale");		    
		    identificativo = rs.getString("identificativo");
		    punteggio=rs.getInt("punteggio");
		    tipologia_operatore =rs.getInt("tipologia_operatore");
		    if (idStabilimento>0)
				   tipologia_operatore = Ticket.TIPO_OPU;
		 
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
		        "status_id = ?, trashed_date = ?, site_id = ? ,ip_modified='"+super.getIpModified()+"', ");
		    if (!override) {
		      sql.append(
		          "modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", modifiedby = ?, ");
		    }
		
		      if (closed != null) {
		        sql.append("closed = ?, ");
		      }
		    
		    
		    
		
	      
	      sql.append(" nc_gravi_valutazione=?,");
	      
	      
	      if (nonConformitaGravi != null) {
		        sql.append(" nonconformitagravi= ?, ");
		      }
	      
	      if (idImpresaSanzionata >0 ) {
		        sql.append(" id_impresa_sanzionata= ?, ");
		      }
	      
	      if (ragioneSocialeImpresaSanzionata !=null ) {
		        sql.append(" ragione_sociale_impresa_sanzionata= ?, ");
		      }
	    
		        sql.append(" fuori_regione_impresa_sanzionata= ?, ");
		 
	      if (puntiGravi != null) {
		        sql.append(" puntigravi= ?, ");
		      }
	      
	      if (punteggio != 0) {
		        sql.append(" punteggio= ? , ");
		      }
	      
		    
		    
		    sql.append(
		        "solution = ?, custom_data = ?, location = ?, assigned_date = ?, assigned_date_timezone = ?, " +
		        "est_resolution_date = ?, est_resolution_date_timezone = ?, resolution_date = ?, resolution_date_timezone = ?, " +
		        "cause = ?, expectation = ?, product_id = ?, customer_product_id = ?, " +
		        "user_group_id = ?, cause_id = ?, resolution_id = ?, defect_id = ?, state_id = ?, " +

		        //"escalation_level = ?, resolvable = ?, resolvedby = ?, resolvedby_department_code = ?, provvedimenti_prescrittivi = ?, nonconformita_amministrative = ?, nonconformita_penali = ?, nonconformita = ?, descrizione1 = ?, descrizione2 = ?, descrizione3 = ? , tipo_sequestro = ?, tipo_sequestro_due = ?, tipo_sequestro_tre = ?, tipo_sequestro_quattro = ? " +

		        "escalation_level = ?, resolvable = ?, resolvedby = ?, resolvedby_department_code = ?, tipo_richiesta = ?, " +
		        "provvedimenti_prescrittivi = ?, sanzioni_amministrative = ?, sanzioni_penali = ?, sequestri = ?, descrizione1 = ?, " +
		        "descrizione2 = ?, descrizione3 = ?, tipo_sequestro = ?, tipo_sequestro_due = ?, tipo_sequestro_tre = ?, tipo_sequestro_quattro = ? ," +
		        "nucleo_ispettivo = ?, nucleo_ispettivo_due = ?, nucleo_ispettivo_tre = ?, componente_nucleo = ?, componente_nucleo_due = ?, componente_nucleo_tre = ?, testo_alimento_composto = ?, nucleo_ispettivo_quattro = ?, nucleo_ispettivo_cinque = ?, nucleo_ispettivo_sei = ?, nucleo_ispettivo_sette = ?, nucleo_ispettivo_otto = ?, nucleo_ispettivo_nove = ?, nucleo_ispettivo_dieci = ?,"+
				        "componente_nucleo_quattro = ?, componente_nucleo_cinque = ?, componente_nucleo_sei = ?, componente_nucleo_sette = ?, componente_nucleo_otto = ?, componente_nucleo_nove = ?, componente_nucleo_dieci = ?"+

		         " WHERE ticketid = ? ");
		   /* if (!override) {
		      sql.append("AND modified " + ((this.getModified() == null)?"IS NULL ":"= ? "));
		    }*/
		    int i = 0;
		    pst = db.prepareStatement(sql.toString());
		      pst.setNull(++i, java.sql.Types.INTEGER);
		      pst.setNull(++i, java.sql.Types.INTEGER);
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
		    if (closed != null) {
		      pst.setTimestamp(++i, closed);
		    }
		  
	    
	      pst.setString(++i,ncGraviValutazioni);
	      
	      if (nonConformitaGravi != null) {
	    	  pst.setString(++i, nonConformitaGravi);
		      }
	      
	      if (idImpresaSanzionata >0 ) {
	    	  pst.setInt(++i, idImpresaSanzionata);
		      }
	      
	      if (ragioneSocialeImpresaSanzionata !=null ) {
	    	  pst.setString(++i, ragioneSocialeImpresaSanzionata);
		      }
	    
	    	  pst.setBoolean(++i, fuoriRegioneImpresaSanzionata);
		   
	      
	      if (puntiGravi != null) {
	    	  pst.setString(++i, puntiGravi);
		      }
	      
	      if (punteggio != 0) {
	    	  pst.setInt(++i, punteggio);
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

		    pst.setString( ++i, tipo_richiesta );
		    DatabaseUtils.setInt(pst, ++i, provvedimenti);
		    DatabaseUtils.setInt(pst, ++i, nonconformitaAmministrative);
		    DatabaseUtils.setInt(pst, ++i, nonconformitaPenali);
		    DatabaseUtils.setInt(pst, ++i, nonconformita);
		    
		    pst.setString(++i, descrizione1);
		    pst.setString(++i, descrizione2);
		    pst.setString(++i, descrizione3);
		    
		    pst.setBoolean(++i, this.getTipoNonConformita());
			pst.setBoolean(++i, this.getTipoNonConformitaDue());
			pst.setBoolean(++i, this.getTipoNonConformitaTre());
			pst.setBoolean(++i, this.getTipoNonConformitaQuattro());
			
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
		  
		      db.commit();
		    } catch (SQLException e) {
		      db.rollback();
		      throw new SQLException(e.getMessage());
		    } finally {
		      db.setAutoCommit(true);
		    }
		    return resultCount;
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
		
		
		// delete all history data
		PreparedStatement pst = null;
		
		

		// Delete the ticket
		pst = db.prepareStatement("UPDATE ticket SET trashed_date = now(), modified=now() , modifiedby = ? WHERE ticketid = ? OR id_controllo_ufficiale = ? ;");
		pst.setInt(1, this.getModifiedBy());
		pst.setInt(2, this.getId());
		pst.setString (3, this.getPaddedId());
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
				"(select id_controllo_ufficiale from ticket where ticketid = ?) and trashed_date is null ) " +
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
		
		
		public void updateNonConformita(Connection db,String[] nc_formali,String[] nc_significative,String[] nc_gravi ) throws SQLException{
			String sql="delete from salvataggio_nonconformita where idticket="+id;
			PreparedStatement pst=db.prepareStatement(sql);
			pst.execute();
			//this.insertNonConformita(db, nc_formali, nc_significative, nc_gravi);
			
			
		}
		
		 private static void aggiornaPunteggio1(Connection db, int idNononformita) throws SQLException
			{
			  
			  String selselectIdCu = "select id_controllo_ufficiale from ticket where ticketid = ?";
				String update = "update ticket set punteggio = (select sum (punteggio) from ticket where id_controllo_ufficiale= ? and tipologia not in (3)) where ticketid = ?";
				PreparedStatement pst = db.prepareStatement(selselectIdCu);
				pst.setInt(1, idNononformita);
				ResultSet rs = pst.executeQuery();
				String idCU = "";
				if(rs.next())
				{
					idCU = rs.getString(1);
				}
				String padd = idCU;
				int id_cu = -1;
				while(idCU.startsWith("0"))
				{
					idCU = idCU.substring(1);
				}
				id_cu = Integer.parseInt(padd);
				pst =db.prepareStatement(update);
				
				pst.setString(1, padd);
				pst.setInt(2, id_cu);
				pst.execute();
			}
		
		  public int update(Connection db) throws SQLException {
			    int i = -1;
			    try {
			      db.setAutoCommit(false);
			      i = this.update(db, false);
			     
			      db.commit();
			    } catch (SQLException e) {
			      db.rollback();
			      throw new SQLException(e.getMessage());
			    } finally {
			      db.setAutoCommit(true);
			    }
			    return i;
			  }

		public String getTestoAppoggio() {
			return testoAppoggio;
		}

		public void setTestoAppoggio(String testoAppoggio) {
			this.testoAppoggio = testoAppoggio;
		}
		  
		
		
		
		
		

		
		public void setNon_conformita_gravi(Connection db,int tipoControllo,Timestamp dataInizioControllo) throws SQLException 
		{
			for (int i = 0 ; i < non_conformita_gravi.size(); i++)
				non_conformita_gravi.remove(non_conformita_gravi.get(i));
			String sql="";
			

			if (tipoControllo == 4 || (tipoControllo==3 && dataInizioControllo.after(java.sql.Timestamp.valueOf(org.aspcf.modules.controlliufficiali.base.ApplicationProperties.getProperty("TIMESTAMP_NUOVA_GESTIONE_OGGETTO_DEL_CONTROLLO_AUDIT")))))
			
			{
				if (haveOggettoControllo == true )
				{
					sql = "select idticket,id_non_conformita,lnc.description as descrizione ,note,tipologia, id_linea_nc, id_non_conformita_benessere_macellazione, id_non_conformita_benessere_trasporto, id_operatore_mercato from salvataggio_nc_note join lookup_ispezione lnc on ( id_non_conformita=lnc.code) where idticket = ? and tipologia = ? order by tipologia";
					PreparedStatement pst_ver = db.prepareStatement(sql);
					pst_ver.setInt(1, id);
					pst_ver.setInt(2, ElementoNonConformita.NC_GRAVI);
					ResultSet rs_ver = pst_ver.executeQuery();
					if (!rs_ver.next())
						sql = "select idticket,id_non_conformita,lnc.description as descrizione ,note,tipologia, id_linea_nc, id_non_conformita_benessere_macellazione, id_non_conformita_benessere_trasporto, id_operatore_mercato from salvataggio_nc_note join lookup_provvedimenti lnc on ( id_non_conformita=lnc.code) where idticket = ? and tipologia = ? order by tipologia" ;
				
				
				}
				else
				{
					sql = "select idticket,id_non_conformita,lnc.description as descrizione ,note,tipologia, id_linea_nc, id_non_conformita_benessere_macellazione, id_non_conformita_benessere_trasporto, id_operatore_mercato from salvataggio_nc_note join lookup_provvedimenti lnc on ( id_non_conformita=lnc.code) where idticket = ? and tipologia = ? order by tipologia" ;
				}
			}
			else
			{
				sql = "select idticket,id_non_conformita,lnc.description as descrizione ,note,tipologia, id_linea_nc, id_non_conformita_benessere_macellazione, id_non_conformita_benessere_trasporto, id_operatore_mercato from salvataggio_nc_note join lookup_provvedimenti lnc on ( id_non_conformita=lnc.code) where idticket = ? and tipologia = ? order by tipologia";

					
			}
			PreparedStatement pst_note = db.prepareStatement(sql);
			pst_note.setInt(1, id);
			pst_note.setInt(2, ElementoNonConformita.NC_GRAVI);
			ResultSet rs_note = pst_note.executeQuery();
			int i = 1 ;
			while ( rs_note.next() )
			{
				int ticketid = rs_note.getInt("idticket")				;
				int tipo = rs_note.getInt("tipologia")					;
				String note = rs_note.getString("note")					;
				int id_non_conformita = rs_note.getInt("id_non_conformita")		;
				String descrizione = rs_note.getString("descrizione")			;
				int id_linea_nc = rs_note.getInt("id_linea_nc")		;
				int id_non_conformita_benessere_macellazione = rs_note.getInt("id_non_conformita_benessere_macellazione");
				int id_non_conformita_benessere_trasporto = rs_note.getInt("id_non_conformita_benessere_trasporto");
				int id_operatore_mercato = rs_note.getInt("id_operatore_mercato");

								ElementoNonConformita new_nc = new ElementoNonConformita(ticketid,i,tipo,note, id_linea_nc);
				new_nc.setId_nc(id_non_conformita);
				new_nc.setId_nc_benessere_macellazione(id_non_conformita_benessere_macellazione);
				new_nc.setId_nc_benessere_trasporto(id_non_conformita_benessere_trasporto);
				new_nc.setId_operatore_mercato(id_operatore_mercato);

				new_nc.setNote(note);
				if (descrizione!=null && ! descrizione.equals("null"))
					new_nc.setDescrizione_nc(descrizione);
				else
					new_nc.setDescrizione_nc("");				
				non_conformita_gravi.add(new_nc);
				i++;
			}
			if (non_conformita_gravi.isEmpty())
			{
				puntiGravi= "0";
				ElementoNonConformita e = new ElementoNonConformita(id,1,3,"", -1);
				e.setId_nc(-1);
				e.setNote("INSERIRE QUI LA DESCRIZIONE PER LA SINGOLA NON CONFORMITA");
				non_conformita_gravi.add(e);
			}
			else
			{
				
				DwrNonConformita dwrnc = new DwrNonConformita();
				PunteggiNonConformita p = dwrnc.get_punteggio_non_conformita(tipoControllo, dataInizioControllo+"");
				puntiGravi = ""+non_conformita_gravi.size()*p.getPuntigravi();
			}
			
			if (ignoraPunteggi) //ACQUE DI RETE NON CONFORMITA': NON CALCOLARE PUNTEGGI
			{
				puntiGravi = "0";
			}
					
				
		}

		public boolean isFuoriRegioneImpresaSanzionata() {
			return fuoriRegioneImpresaSanzionata;
		}

		public void setFuoriRegioneImpresaSanzionata(boolean fuoriRegioneImpresaSanzionata) {
			this.fuoriRegioneImpresaSanzionata = fuoriRegioneImpresaSanzionata;
		}

	
		

		  
		
	  
}

