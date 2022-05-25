/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcf.modules.checklist_benessere.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.aspcfs.modules.accounts.base.OrganizationAddress;
import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

public class Risposta extends GenericBean{
	 
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	  private int idModIst;
	  private int idCap;
	  private int idCap_fKey;
	  private int idDom;
	  private int punteggioA;
	  private int punteggioB;
	  private int punteggioC;
	  private int punteggioTotale;
	  
	  private String tipoCapitolo ;
	  private String tipoDomanda ;
	  
	  private String descCap;
	  private String descDom;
	  private String osservazioni;
	  private int classews ;
	  private Boolean  esito = null;
	  private boolean  domanda_non_pertinente = false;
	  
	  
	  
	  
	public int getClassews() {
		return classews;
	}

	public void setClassews(int classews) {
		this.classews = classews;
	}

	public String getTipoCapitolo() {
		return tipoCapitolo;
	}

	public void setTipoCapitolo(String tipoCapitolo) {
		this.tipoCapitolo = tipoCapitolo;
	}

	public String getTipoDomanda() {
		return tipoDomanda;
	}

	public void setTipoDomanda(String tipoDomanda) {
		this.tipoDomanda = tipoDomanda;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdModIst() {
		return idModIst;
	}

	public void setIdModIst(int idModIst) {
		this.idModIst = idModIst;
	}

	public int getIdCap() {
		return idCap;
	}

	public void setIdCap(int idCap) {
		this.idCap = idCap;
	}

	public int getIdDom() {
		return idDom;
	}

	public void setIdDom(int idDom) {
		this.idDom = idDom;
	}

	public int getPunteggioA() {
		return punteggioA;
	}

	public void setPunteggioA(int punteggioA) {
		this.punteggioA = punteggioA;
	}

	public int getPunteggioB() {
		return punteggioB;
	}

	public void setPunteggioB(int punteggioB) {
		this.punteggioB = punteggioB;
	}

	public int getPunteggioC() {
		return punteggioC;
	}

	public void setPunteggioC(int punteggioC) {
		this.punteggioC = punteggioC;
	}

	public int getPunteggioTotale() {
		return punteggioTotale;
	}

	public void setPunteggioTotale(int punteggio) {
		this.punteggioTotale = punteggio;
	}
	
	public String getDescCap() {
		return descCap;
	}

	public int getIdCap_fKey() {
		return idCap_fKey;
	}

	public void setIdCap_fKey(int idCap_fKey) {
		this.idCap_fKey = idCap_fKey;
	}

	public void setDescCap(String descCap) {
		this.descCap = descCap;
	}

	public String getDescDom() {
		return descDom;
	}

	public void setDescDom(String descDom) {
		this.descDom = descDom;
	}

	public String getOsservazioni() {
		return osservazioni;
	}

	public void setOsservazioni(String osservazioni) {
		this.osservazioni = osservazioni;
	}

	public Boolean isEsito() {
		return esito;
	}

	public void setEsito(boolean esito) {
		this.esito = esito;
	}

	
	
	public boolean isDomanda_non_pertinente() {
		return domanda_non_pertinente;
	}

	public void setDomanda_non_pertinente(boolean domanda_non_pertinente) {
		this.domanda_non_pertinente = domanda_non_pertinente;
	}

	public Risposta(){ }
	
	public void buildRecord(HttpServletRequest request, int parseCap, int parseDom) {
		
		String idRisposta = request.getParameter("idRisposta_" + parseCap + "_"+ parseDom);
		String puntA = request.getParameter("punteggioA_" + parseCap + "_"+ parseDom);
		String puntB = request.getParameter("punteggioB_" + parseCap + "_"+ parseDom);
		String puntC = request.getParameter("punteggioC_" + parseCap + "_"+ parseDom);
		String puntTot = request.getParameter("tot_" + parseCap + "_"+ parseDom);
		String oss = request.getParameter("oss_" + parseCap + "_"+ parseDom);
		String cap = request.getParameter("descCap_" + parseCap);
		String dom = request.getParameter("descDom_" + parseCap + "_"+parseDom);
		//Esito favorevole = si = on
		String es = request.getParameter("esito_" + parseCap + "_"+parseDom);
		String tipoCapitolo = request.getParameter("tipocapitolo_" + parseCap + "_"+parseDom);
		String tipoDomanda= request.getParameter("tipodomanda_" + parseCap + "_"+parseDom);

		if(idRisposta != null && idRisposta != "" && !idRisposta.equals("")){
			id = Integer.parseInt(idRisposta);
		}
		
		if(puntA != null && puntA != "" && !puntA.equals("")){
			punteggioA = Integer.parseInt(puntA);
		}
		
		if(puntB != null && puntB != "" && !puntB.equals("")){
			punteggioB = Integer.parseInt(puntB);
		}
		
		if(puntC != null && puntC != "" && !puntC.equals("")){
		    punteggioC = Integer.parseInt(puntC);
		}
		
		if(puntTot != null && puntTot != "" && !puntTot.equals("")){
		    punteggioTotale = Integer.parseInt(puntTot);
		}
		
		if(oss != null && oss != "" && !oss.equals("")){
		  	osservazioni = oss;		
		}
		
		if(cap != null && cap != "" && !cap.equals("")){
		  	descCap = cap;		
		}
		
		if(dom != null && dom != "" && !dom.equals("")){
		  	descDom = dom;		
		}  
		
		this.tipoCapitolo = tipoCapitolo;
		this.tipoDomanda = tipoDomanda;
		//Gestire l'esito
		if(es != null && es != "" && !es.equals("")){
			if(es.equals("si")){
				esito = true;
				domanda_non_pertinente = false ;
			}else {
				if(es.equals("no")){
					esito = false;
					domanda_non_pertinente = false ;
				}
				else
				{
					if(es.equals("nd")){
					domanda_non_pertinente = true ;
					esito = false ;
					}
				}
					
				
			}
		}
		else
			esito = null;
	  
	    this.setIdCap(parseCap);
	    this.setIdDom(parseDom);
	    
	
	}

	
	public void insert(Connection db,ActionContext context) {
		
		StringBuffer sql = new StringBuffer();
	    
	    try {
	    	
		      id = DatabaseUtils.getNextSeqInt(db, context,"chk_bns_risposte","id");
		      
		      sql.append(
		          "INSERT INTO chk_bns_risposte (idmodist, idcap, iddom, desccap, descdom, punteggioa,punteggiob,punteggioc,punteggiototale,idcap_fkey,tipo_capitolo,tipo_domanda,domanda_non_pertinente,classe_ws ");
		      if (id > -1) {
		        sql.append(",id ");
		      }
		      if (esito!=null) {
		        sql.append(",esito ");
		      }
		      if (osservazioni != null) {
			        sql.append(",osservazioni ");
			  }
	
		      
		      sql.append(")");
		      
		      sql.append("VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,? ");
		     
		      if (id > -1) {
		        sql.append(",?");
		      }
		      if (esito!=null) {
		        sql.append(",?");
		      }
		      if (osservazioni != null) {
			    sql.append(",?");
			  }
		      sql.append(")");
	      
		      int i = 0;
		      PreparedStatement pst = db.prepareStatement(sql.toString());
		      pst.setInt(++i, this.idModIst);
		      pst.setInt(++i, this.idCap);
		      pst.setInt(++i, this.idDom);
		      pst.setString(++i,this.descCap);
		      pst.setString(++i,this.descDom);
		      pst.setInt(++i, this.punteggioA);
		      pst.setInt(++i, this.punteggioB);
		      pst.setInt(++i, this.punteggioC);
		      pst.setInt(++i, this.punteggioTotale);
		      pst.setInt(++i,this.idCap_fKey);
		      pst.setString(++i, tipoCapitolo);
		      pst.setString(++i, tipoDomanda);
		      pst.setBoolean(++i, domanda_non_pertinente);
		      pst.setInt(++i, this.classews);

		      if (id > -1) {
		        pst.setInt(++i, id);
		      }
		      
		      
		      if (esito!=null) {
		        pst.setBoolean(++i, esito);
		      }

		      if (osservazioni != null) {
		    	pst.setString(++i, osservazioni);
			  }
		      
		 
		      pst.execute();
		      pst.close();
		      
	      } catch (SQLException e) {
	    	 	e.printStackTrace();
	      }	
	    
	    
	    
	}

	 public void update(Connection db, int modifiedBy) throws SQLException {

		 StringBuffer sql = new StringBuffer();
		 
		 sql.append(" UPDATE chk_bns_risposte SET punteggioa = ?, punteggiob = ?, punteggioc = ?, punteggiototale = ?, esito = ? ,domanda_non_pertinente=? ");
		 
		if (osservazioni != null) {
		   sql.append(", osservazioni = ?");
		 }
		 
		 sql.append("WHERE iddom = ?  and idcap = ? and id = ? ");
		 
		 int i = 0;
		 PreparedStatement pst = db.prepareStatement(sql.toString());		 
	     pst.setInt(++i, this.punteggioA);
	     pst.setInt(++i, this.punteggioB);
	     pst.setInt(++i, this.punteggioC);
	     pst.setInt(++i, this.punteggioTotale);
	     if (this.esito!=null)
	    	 pst.setBoolean(++i, this.esito);
	     else
	    	 pst.setObject(++i, null);
		 pst.setBoolean(++i, this.domanda_non_pertinente);
		 if (this.osservazioni != null) {
		   pst.setString(++i,this.osservazioni);
		 }
	     
	  	 pst.setInt(++i, this.idDom);
	     pst.setInt(++i, this.idCap);
	     pst.setInt(++i, this.id);
	     pst.execute();
		 pst.close();
	 }

	
	public ArrayList<Risposta> buildList(Connection db, int idControllo, int codice_specie) throws SQLException {
		// TODO Auto-generated method stub
		
		ArrayList<Risposta> risposteList = new ArrayList<Risposta>();
		PreparedStatement pst = db.prepareStatement(
        " select risp.id as idRisposta,* from chk_bns_risposte risp " +
		" left join chk_bns_mod_ist ist on ist.id = risp.idmodist " +
		" left join lookup_chk_bns_mod mod on mod.code = ist.id_alleg " +
		" where ist.trashed_date is null and ist.idcu = ? and mod.codice_specie = ?" +
		" order by risp.id ");	
		pst.setInt(1, idControllo);
		pst.setInt(2, codice_specie);
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
		   Risposta thisRisposta = new Risposta(rs);
		   risposteList.add(thisRisposta);
		}
		
		return risposteList;
	}
	
	  
	protected  void buildRecord(ResultSet rs) throws SQLException {
		 
		 //chk_bns_mod_ist table
		 id = rs.getInt("idRisposta");
		 idModIst = rs.getInt("idmodist");
		 idDom = rs.getInt("iddom");
		 idCap = rs.getInt("idcap");
		 descCap = rs.getString("desccap");
		 descDom = rs.getString("descdom");
				 
		 Object o = null;
		 o = rs.getObject("esito");
		 if (o!=null)
			 esito = rs.getBoolean("esito");
			 
		 this.domanda_non_pertinente = rs.getBoolean("domanda_non_pertinente");
		 osservazioni = rs.getString("osservazioni");
		 punteggioA = rs.getInt("punteggioa");
		 punteggioB = rs.getInt("punteggiob");
		 punteggioC = rs.getInt("punteggioc");
		 punteggioTotale = rs.getInt("punteggiototale");
		 idCap_fKey = rs.getInt("idCap_fKey");
		 this.tipoCapitolo = rs.getString("tipo_capitolo");
			this.tipoDomanda = rs.getString("tipo_domanda");;
		    
	 }
	  
	 public Risposta(ResultSet rs) throws SQLException {
		    buildRecord(rs);
	 }
	
}
