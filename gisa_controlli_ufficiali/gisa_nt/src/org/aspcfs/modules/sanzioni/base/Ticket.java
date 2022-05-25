/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.sanzioni.base;

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
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.FileItemList;

public class Ticket extends org.aspcfs.modules.troubletickets.base.Ticket
{
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 5026330838840957695L;
	
	public Ticket()
	{
		
	}
	private Competenza competenza = null;
	private String numVerbaleSequestro ;
	private int sequestroRiduzione=0 ;
	private boolean farmacosorveglianza= false;
	private int idFarmacia = -1;
	private int idTicketNonConformita=-1;
	private boolean soa = false;
	private int idRuoloUtente ;
	
	private ArrayList<String[] > listaAllegatiPV = new ArrayList<String[] >();
	private ArrayList<String[] > listaAllegatiAL = new ArrayList<String[] >();

	public int getIdRuoloUtente() {
		return idRuoloUtente;
	}
	
	


	public String getNumVerbaleSequestro() {
		return numVerbaleSequestro;
	}




	public void setNumVerbaleSequestro(String numVerbaleSequestro) {
		this.numVerbaleSequestro = numVerbaleSequestro;
	}




	public void setIdRuoloUtente(int idRuoloUtente) {
		this.idRuoloUtente = idRuoloUtente;
	}
	private ArrayList<HashMap> attributi_registro_ordinanze = new ArrayList<HashMap> ();
	
	public boolean isSoa() {
		return soa;
	}
	public void setSoa(boolean soa) {
		this.soa = soa;
	}
	public int getIdTicketNonConformita() {
		return idTicketNonConformita;
	}
	 private int tipo_nc ;
	    
	    
		public int getTipo_nc() {
			return tipo_nc;
		}
		public void setTipo_nc(int tipo_nc) {
			this.tipo_nc = tipo_nc;
		}
	public void setIdTicketNonConformita(int idTicketNonConformita) {
		this.idTicketNonConformita = idTicketNonConformita;
	}

	private String articoloviolato;
	public String getArticoloviolato() {
		return articoloviolato;
	}
	public void setArticoloviolato(String articoloviolato) {
		this.articoloviolato = articoloviolato;
	}
	public String getSanzione() {
		return sanzione;
	}
	public void setSanzione(String sanzione) {
		this.sanzione = sanzione;
	}
	
	public boolean isFarmacosorveglianza() {
		return farmacosorveglianza;
	}
	public void setFarmacosorveglianza(boolean farmacosorveglianza) {
		this.farmacosorveglianza = farmacosorveglianza;
	}
	public int getIdFarmacia() {
		return idFarmacia;
	}
	public void setIdFarmacia(int idFarmacia) {
		this.idFarmacia = idFarmacia;
	}
	private String sanzione;
	private String normaviolata="";
	public String getNormaviolata() {
		return normaviolata;
	}
	public void setNormaviolata(String normaviolata) {
		this.normaviolata = normaviolata;
	}
	private String identificativonc="";
	public String getIdentificativonc() {
		return identificativonc;
	}
	public void setIdentificativonc(String identificativonc) {
		this.identificativonc = identificativonc;
	}
	private int id_nonconformita=-1;
	public int getId_nonconformita() {
		return id_nonconformita;
	}
	public void setId_nonconformita(int id_nonconformita) {
		this.id_nonconformita = id_nonconformita;
	}
	protected String trasgressore="";
	protected String trasgressore2="";
	protected String trasgressore3="";
	protected String obbligatoinSolido="";
	public String getObbligatoinSolido2() {
		return obbligatoinSolido2;
	}
	public void setObbligatoinSolido2(String obbligatoinSolido2) {
		this.obbligatoinSolido2 = obbligatoinSolido2;
	}
	public String getObbligatoinSolido3() {
		return obbligatoinSolido3;
	}
	public void setObbligatoinSolido3(String obbligatoinSolido3) {
		this.obbligatoinSolido3 = obbligatoinSolido3;
	}
	protected String obbligatoinSolido2="";
	protected String obbligatoinSolido3="";
	HashMap<Integer, String> azioninonConformePer=new HashMap<Integer, String>();
	HashMap<Integer, String> listaNorme =new HashMap<Integer, String>();

	
	public String getTrasgressore() {
		return trasgressore;
	}
	
	public void setTrasgressore(String trasgressore) {
		this.trasgressore = trasgressore;
	}
	public String getTrasgressore2() {
		return trasgressore2;
	}
	public void setTrasgressore2(String trasgressore2) {
		this.trasgressore2 = trasgressore2;
	}
	public String getTrasgressore3() {
		return trasgressore3;
	}
	public void setTrasgressore3(String trasgressore3) {
		this.trasgressore3 = trasgressore3;
	}
	public String getObbligatoinSolido() {
		return obbligatoinSolido;
	}
	public void setObbligatoinSolido(String obbligatoinSolido) {
		this.obbligatoinSolido = obbligatoinSolido;
	}
	public HashMap<Integer, String> getAzioninonConformePer() {
		return azioninonConformePer;
	}
	public void setAzioninonConformePer(
			HashMap<Integer, String> azioninonConformePer) {
		this.azioninonConformePer = azioninonConformePer;
	}

	public HashMap<Integer, String> getListaNorme() {
		return listaNorme;
	}
	public void setListaNorme(
			HashMap<Integer, String> listaNorme) {
		this.listaNorme = listaNorme;
	}

	private double pagamento=0;
	public double getPagamento() {
		return pagamento;
	}
	public void setPagamento(double pagamento) {
		this.pagamento = pagamento;
	}

	protected String tipo_richiesta = "";
	protected int tipologia = -1;
	//protected String dati_extra = "";
	protected String pippo = "";
	protected int provvedimenti = -1;
	protected int sanzioniAmministrative = -1;
	protected int sanzioniPenali = -1;
	protected int sequestri = -1;
	protected String descrizione1 = "";
	protected String descrizione2 = "";
	protected String descrizione3 = "";
	
	//aggiunto da d.dauria per gestire i sequestri
    private boolean tipoSequestro = false;
    private boolean tipoSequestroDue = false;
    private boolean tipoSequestroTre = false;
    private boolean tipoSequestroQuattro = false; 
    private String testoAppoggio = "";
    private int punteggio = 0;
	private String idControlloUfficiale = null;
	private String identificativo = null;
	
    
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
    public boolean getTipoSequestro() {
		return tipoSequestro;
	}

	public void setTipoSequestro(boolean tipoSequestro) {
		this.tipoSequestro = tipoSequestro;
	}
	
	public void setTipoSequestro(String temp) {
		this.tipoSequestro = DatabaseUtils.parseBoolean(temp);
	}

	public boolean getTipoSequestroDue() {
		return tipoSequestroDue;
	}

	public void setTipoSequestroDue(boolean tipoSequestroDue) {
		this.tipoSequestroDue = tipoSequestroDue;
	}
	
	public void setTipoSequestroDue(String temp) {
		this.tipoSequestroDue = DatabaseUtils.parseBoolean(temp);
	}

	public boolean getTipoSequestroTre() {
		return tipoSequestroTre;
	}

	public void setTipoSequestroTre(boolean tipoSequestroTre) {
		this.tipoSequestroTre = tipoSequestroTre;
	}

	public void setTipoSequestroTre(String temp) {
		this.tipoSequestroTre = DatabaseUtils.parseBoolean(temp);
	}
	
	public boolean getTipoSequestroQuattro() {
		return tipoSequestroQuattro;
	}

	public void setTipoSequestroQuattro(boolean tipoSequestroQuattro) {
		this.tipoSequestroQuattro = tipoSequestroQuattro;
	}
	
	public void setTipoSequestroQuattro(String temp) {
		this.tipoSequestroQuattro = DatabaseUtils.parseBoolean(temp);
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

	public void setSanzioniAmministrative(String sanzioniAmministrative) {
	    try {
	      this.sanzioniAmministrative = Integer.parseInt(sanzioniAmministrative);
	    } catch (Exception e) {
	      this.sanzioniAmministrative = -1;
	    }
	  }
	
	public void setSanzioniPenali(String sanzioniPenali) {
	    try {
	      this.sanzioniPenali = Integer.parseInt(sanzioniPenali);
	    } catch (Exception e) {
	      this.sanzioniPenali = -1;
	    }
	  }
	
	public void setSequestri(String sequestri) {
	    try {
	      this.sequestri = Integer.parseInt(sequestri);
	    } catch (Exception e) {
	      this.sequestri = -1;
	    }
	  }

	public void setProvvedimenti(int provvedimenti) {
		this.provvedimenti = provvedimenti;
		
	}
	
	public int getSanzioniAmministrative() {
		return sanzioniAmministrative;
	}

	public void setSanzioniAmministrative(int sanzioniAmministrative) {
		this.sanzioniAmministrative = sanzioniAmministrative;
		
	}
	
	public int getSanzioniPenali() {
		return sanzioniPenali;
	}

	public void setSanzioniPenali(int sanzioniPenali) {
		this.sanzioniPenali = sanzioniPenali;
		
	}
	
	public int getSequestri() {
		return sequestri;
	}

	public void setSequestri(int sequestri) {
		this.sequestri = sequestri;
		
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
	    this.setFieldsRegistro(db);
	  }
	  public Ticket(Connection db, int id,boolean soa) throws SQLException {
			this.soa = soa;
		    queryRecord(db, id);
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
				private int idControlloUfficialeTicket=-1;
				
			
				
				
				
				
				
				
				public int getIdControlloUfficialeTicket() {
					return idControlloUfficialeTicket;
				}

	  public void queryRecord(Connection db, int id) throws SQLException {
		    if (id == -1) {
		      throw new SQLException("Invalid Ticket Number");
		    }
		   
		       String sql =  "SELECT t.*,cu.ticketid as id_cu,nonconformita.tipologia as tipologia_nonconformita, " +
		        "o.site_id AS orgsiteid,o.tipologia as tipo_operatore,cu.chiusura_attesa_esito, cu.data_fine_controllo as cu_data_fine_controllo " +
		        "FROM ticket t ";
		    	sql +=   "left join ticket nonconformita on nonconformita.ticketid=t.id_nonconformita "
		    			+ "LEFT JOIN organization o ON (t.org_id = o.org_id) ";
		        sql +="LEFT JOIN ticket cu ON (t.id_controllo_ufficiale = cu.id_controllo_ufficiale and cu.tipologia = 3) " +
		        "WHERE t.ticketid = ? AND t.tipologia = 1 ";
		        PreparedStatement pst = db.prepareStatement(sql);
		    pst.setInt(1, id);
		    ResultSet rs = pst.executeQuery();
		    if (rs.next()) {
		      buildRecord(rs);
		      this.setNormeViolate(db);
		      if (soa == false)
		      {
		    	  this.setAzioniNonConforme(db);
		      }
		      else
		      {
		    	  this.setAzioniNonConformeSoa(db);
		      }
		      super.controlloBloccoCu(db, Integer.parseInt(this.idControlloUfficiale));
		      setListaAllegati(db);
		      setCompetenza(db);
		      //  this.setIdTicketNConformita(db);
		      //this.setIdControlloUfficialeTicket(db);
		    }
		    rs.close();
		    pst.close();
		    if (this.id == -1) {
		      throw new SQLException(Constants.NOT_FOUND_ERROR);
		    }
		   
		  }
	  
	
	  
	  public void insertAzioniNonConforme(Connection db , String[] valori)throws SQLException{
		  
		String sql="insert into oggettisequestrati_azioninonconformi (idticket,azione_nonconforme) values (?,?)";
		PreparedStatement pst=db.prepareStatement(sql);
		if(valori!=null)
		{
			for(int i=0;i<valori.length;i++){
				int key=Integer.parseInt(valori[i]);
				
				pst.setInt(1, id);
				pst.setInt(2, key);
				pst.execute();
				
				
			}
			
		}
		  
	  }
	  
	  public int disableInsertNormeViolate(Connection db , String[] valori)throws SQLException{
		  
		  int count = 0;
		  String sql="select distinct diffide.ticketid as idDiffida from norme_violate_sanzioni "+ 
				  "join ticket diffide on diffide.ticketid = idticket and tipologia=11 "
				  + "join ticket cu on cu.id_controllo_ufficiale=diffide.id_controllo_ufficiale and cu.tipologia=3 "
				  + "left join ticket nc on nc.ticketid=diffide.id_nonconformita and nc.tipologia=8 "+
				  "where diffide.id_nonconformita>0 and norme_violate_sanzioni.id_norma=? "
				  + "and diffide.trashed_date is null and cu.trashed_date is null  and nc.trashed_date is null "
				  + " and norme_violate_sanzioni.stato_diffida = 0 ";
		  
		  if (orgId > 0)
			  sql+=" and diffide.org_id = ? ";
		  if (idStabilimento > 0)
			  sql+=" and diffide.id_stabilimento = ? ";
		  if (idApiario > 0)
			  sql+=" and diffide.id_apiario = ? ";
		  if (altId > 0)
			  sql+=" and diffide.alt_id = ? ";
		  
		  PreparedStatement pst=db.prepareStatement(sql);
		  if( this.idStabilimento == 0){
			  this.idStabilimento =-1;
		  }
		  if( this.idApiario == 0){
			  this.idApiario =-1;
		  }
		  if(valori!=null)
			{
				for(int i=0;i<valori.length;i++){
					int key=Integer.parseInt(valori[i]);
					pst.setInt(1, key);
					
					 if (orgId > 0)
							pst.setInt(2, this.orgId);
					  if (idStabilimento > 0)
						  pst.setInt(2, this.idStabilimento);
					  if (idApiario > 0)
						  pst.setInt(2, this.idApiario);
					  if (altId > 0)
						  pst.setInt(2, this.altId);
					
					 ResultSet rs = pst.executeQuery();
					//Se esiste la norma cancellala altrimenti inseriscila
					 boolean esisteNorma = false;
					while(rs.next()){
						esisteNorma = true;
						String sql2="update norme_violate_sanzioni set stato_diffida = 1 where org_id = ? and id_norma = ? and  idticket = ? ";
						PreparedStatement pst2=db.prepareStatement(sql2);
						if(orgId > 0){
							pst2.setInt(1, this.orgId);
						}else if(idStabilimento > 0){
							pst2.setInt(1, this.idStabilimento);
						}
						else if(altId > 0){
						pst2.setInt(1, this.altId);
						}
						else{
							pst2.setInt(1, this.idApiario);
						}
							
						pst2.setInt(2, key);
						pst2.setInt(3, rs.getInt("idDiffida"));
						pst2.execute();
						this.insertNormeViolateSanzioni(db, key);
					}
					
					if (!esisteNorma){
						this.insertNormeViolateSanzioni(db, key);
						++count;
					}
						
					}
					
				}
		  
		  return count;
				
			}
	  
 public void disableDiffida(Connection db , String[] valori)throws SQLException{
		  
		  
		  String sql="select distinct diffide.ticketid as idDiffida from norme_violate_sanzioni "+ 
				  "join ticket diffide on diffide.ticketid = idticket and tipologia=11 "
				  + "join ticket cu on cu.id_controllo_ufficiale=diffide.id_controllo_ufficiale and cu.tipologia=3 "
				  + "left join ticket nc on nc.ticketid=diffide.id_nonconformita and nc.tipologia=8 "+
				  "where (diffide.org_id =? or diffide.id_stabilimento= ? or diffide.id_apiario = ?) and diffide.id_nonconformita>0 and  norme_violate_sanzioni.id_norma=? "
				  + "and diffide.trashed_date is null and cu.trashed_date is null  and nc.trashed_date is null "
				  + " and norme_violate_sanzioni.stato_diffida = 0 ";
		  PreparedStatement pst=db.prepareStatement(sql);
		  if(idStabilimento == 0){
			  idStabilimento = -1;
		  }
		  if(idApiario == 0){
			  idApiario = -1;
		  }
		  if(valori!=null)
			{
				for(int i=0;i<valori.length;i++){
					int key=Integer.parseInt(valori[i]);
					pst.setInt(1, this.orgId);
					pst.setInt(2, this.idStabilimento);
					pst.setInt(3, this.idApiario);
					pst.setInt(4, key);
					ResultSet rs = pst.executeQuery();
					//Se esiste la norma cancellala altrimenti inseriscila
					if(rs.next()){
						String sql2="update norme_violate_sanzioni set stato_diffida = 1 where org_id = ? and id_norma = ? and  idticket = ? ";
						PreparedStatement pst2=db.prepareStatement(sql2);
						if(orgId > 0){
							pst2.setInt(1, orgId);
						}else {
							pst2.setInt(1, idStabilimento);
						}
						pst2.setInt(2, key);
						pst2.setInt(3, rs.getInt("idDiffida"));
						pst2.execute();
					}
						
				}
					
			}
	
			}
		
		public void insertNormeViolateSanzioni(Connection db , int key)throws SQLException{
	  
				String sql="insert into norme_violate_sanzioni (idticket, org_id, id_norma, stato_diffida) values (?,?,?,?)";
				PreparedStatement pst=db.prepareStatement(sql);
				pst.setInt(1, id);
				if(orgId > 0){
					pst.setInt(2, this.orgId);
				}else if(this.idStabilimento > 0){
					pst.setInt(2, this.idStabilimento);
				}else{
					pst.setInt(2, this.idApiario);
				}
				pst.setInt(3, key);
				pst.setInt(4, 0);
				pst.execute();
				pst.close();
			
		}

	  
	  public void updateAzioniNonConforme(Connection db , String[] valori)throws SQLException{
		  
			String sql="delete from oggettisequestrati_azioninonconformi where idticket=? ";
			PreparedStatement pst=db.prepareStatement(sql);
			pst.setInt(1, id);
			pst.execute();
			this.insertAzioniNonConforme(db, valori);
			pst.close();
					
			
			
			  
		  }

	  public void updateNormeViolate(Connection db , String[] valori)throws SQLException{
		  
			String sql="delete from norme_violate_sanzioni where idticket=? ";
			PreparedStatement pst=db.prepareStatement(sql);
			pst.setInt(1, id);
			pst.execute();
			this.insertNormeViolate(db, valori);
			pst.close();
						  
	  }
	  
	  public void insertNormeViolate(Connection db , String[] valori)throws SQLException{
		  
			String sql="insert into norme_violate_sanzioni (idticket,org_id,id_norma) values (?,?,?)";
			PreparedStatement pst=db.prepareStatement(sql);
			if(valori!=null)
			{
				for(int i=0;i<valori.length;i++){
					int key=Integer.parseInt(valori[i]);
					
					pst.setInt(1, id);
					if(orgId > 0){
						pst.setInt(2, orgId);
					}else {
						pst.setInt(2, idStabilimento);
					}
					pst.setInt(3, key);
					pst.execute();
					
					
				}
				
			}
			  
		  }
	  
	  public void cancellaDiffida(Connection db, int org_id)throws SQLException{
		  
			String sql=" update ticket set trashed_date = now() where tipologia = 11 "
					+ " and ticketid in (select idticket from norme_violate_sanzioni where stato_diffida = 1) ";
			if(orgId > 0){
				sql += " and org_id = ? ";
			} else {
				sql += " and id_stabilimento = ? ";
			}
			PreparedStatement pst=db.prepareStatement(sql);
			if(orgId > 0){
				pst.setInt(1, org_id);
			}else {
				pst.setInt(1, idStabilimento);
			}
			
			pst.execute();
			  
		  }
	 
	  public void setNormeViolate(Connection db)throws SQLException{
		  
			String sql="select code,description from lookup_norme, norme_violate_sanzioni where lookup_norme.enabled = true and norme_violate_sanzioni.id_norma=lookup_norme.code and norme_violate_sanzioni.idticket= ?";
			PreparedStatement pst=db.prepareStatement(sql);
			pst.setInt(1, id);
		ResultSet rs=pst.executeQuery();
		while(rs.next()){
			int code=rs.getInt("code");
			String value=rs.getString("description");
			listaNorme.put(code, value);
			
		}
	  }
	  
	  public void setAzioniNonConforme(Connection db)throws SQLException{
		  
			String sql="select azione_nonconforme,description from oggettisequestrati_azioninonconformi,lookup_provvedimenti where oggettisequestrati_azioninonconformi.azione_nonconforme=lookup_provvedimenti.code and oggettisequestrati_azioninonconformi.idticket=?";
			PreparedStatement pst=db.prepareStatement(sql);
			pst.setInt(1, id);
		ResultSet rs=pst.executeQuery();
		while(rs.next()){
			int code=rs.getInt("azione_nonconforme");
			String value=rs.getString("description");
			azioninonConformePer.put(code, value);
			
		}
	  	  
		  }
	  public void setAzioniNonConformeSoa(Connection db)throws SQLException{
		  
			String sql="select azione_nonconforme,description from oggettisequestrati_azioninonconformi,lookup_provvedimenti_soa where oggettisequestrati_azioninonconformi.azione_nonconforme=lookup_provvedimenti_soa.code and oggettisequestrati_azioninonconformi.idticket=?";
			PreparedStatement pst=db.prepareStatement(sql);
			pst.setInt(1, id);
		ResultSet rs=pst.executeQuery();
		while(rs.next()){
			int code=rs.getInt("azione_nonconforme");
			String value=rs.getString("description");
			azioninonConformePer.put(code, value);
			
		}
	  	  
		  }
	  
	  public synchronized boolean insert(Connection db,ActionContext context ) throws SQLException {
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
			
			sql.append(
		          "INSERT INTO ticket (contact_id, problem, pri_code, " +
		          "department_code, cat_code, scode,link_contract_id, " +
		          "link_asset_id, expectation, product_id, customer_product_id, " +
		          "key_count, status_id, trashed_date, user_group_id, cause_id, " +
		          "resolution_id, defect_id, escalation_level, resolvable, " +
		          "resolvedby, resolvedby_department_code, state_id, site_id,ip_entered,ip_modified,flag_posticipato,flag_campione_non_conforme,");
		      
		      if (tipo_nc != -1)
		      {
		    	  sql.append("tipo_nc,");
		      }

		      
		      if (idControlloUfficiale != null)
		      {
		    	  sql.append("id_controllo_ufficiale,");
		      }
		      
		      if (altId>0){sql.append("alt_id,");}
		      
		      if (farmacosorveglianza == true)
		      {
		    	  sql.append("id_farmacia,");
		      }
		      else
		      {
		    	  sql.append("org_id,id_stabilimento,id_apiario,");
		      }
		      
		      
		        sql.append("ticketid, ");
		      
		      if (entered != null) {
		        sql.append("entered, ");
		      }
		      if (modified != null) {
		        sql.append("modified, ");
		      }
		      
		   
		    	  sql.append("trasgressore, ");
		    	  if(!trasgressore2.equals("")){
		    		  sql.append("trasgressore2, ");
		    	  }
		    	  if(!trasgressore3.equals("")){
		    		  sql.append("trasgressore3, ");
		    	  }
		   
		    	  sql.append("obbligatoinsolido, ");
		      
		    	  if(obbligatoinSolido2!=null){
		    		  sql.append("obbligatoinsolido2, ");
		    	  }
		    	  if(obbligatoinSolido3!=null){
		    		  sql.append("obbligatoinsolido3, ");
		    	  }
		    	  
		    	  if(articoloviolato!=null){
		    		  sql.append("articoloviolato, ");
		    	  }
		    	  if(sanzione!=null){
		    		  sql.append("sanzione, ");
		    	  }
		    	  
		    	  
		    	  
		    	  sql.append("normaviolata, ");
		      
		      sql.append("pagamento, ");
		      
		      sql.append("verbale_sequestro,sequestro_riduzione, tipo_richiesta, custom_data, enteredBy, modifiedBy, " +
		      		"tipologia, provvedimenti_prescrittivi, sanzioni_amministrative, sanzioni_penali, sequestri, descrizione1, descrizione2, descrizione3  ");
		     
		      sql.append(" , tipo_sequestro");
		      sql.append(" , tipo_sequestro_due");
		      sql.append(" , tipo_sequestro_tre");
		      sql.append(" , tipo_sequestro_quattro");
		      sql.append(", id_nonconformita ");
		      if (punteggio != -1) {
			        sql.append(", punteggio");
			      }
		      		      
		      sql.append(")");
		      sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
		      sql.append("?, ?, ?,?,?, ");
		          
		      if (tipo_nc != -1)
		      {
		    	  sql.append("?,");
		      }

		      
		      if (idControlloUfficiale != null)
		      {
		    	  sql.append("?,");
		      }
		      if (altId>0){sql.append("?,");}
		      if (farmacosorveglianza == true)
		      {
		    	  sql.append("?,");
		      }
		      else
		      {
		    	  sql.append("?,?,?,");
		      }
		      
				sql.append( DatabaseUtils.getNextIntSql("ticket", "ticketid", livello)+",");
		      
		      if (entered != null) {
		        sql.append("?, ");
		      }
		      if (modified != null) {
		        sql.append("?, ");
		      }
		      sql.append("?, ");
		      //AGGIUNTA 2 CAMPI TRASGRESSORE
		      if(trasgressore2!="" ){
		    	  sql.append("?, ");
	    	  }
	    	  if(trasgressore3!=""){
	    		  sql.append("?, ");
	    	  }

		    	  sql.append("?, ");
		    	  
		    	  sql.append("?, ");
		    	  
		    	  sql.append("?, ");
		    	  
		    	  if(articoloviolato!=null){
		    		  sql.append("?, ");
		    	  }
		    	  if(sanzione!=null){
		    		  sql.append("?, ");
		    	  }
		    	  
		    	  
		    	  sql.append("?, ");
		      sql.append("?, ");
		      
		      sql.append("?,?, ?, ?, ?, ?, " +
		      		     "1, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? "); //ho aggiunto 2 punti interrogativi
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
		      sql.append(", ?");
		      if (punteggio != -1) {
			        sql.append(", ? ");
			      }
		  
		      sql.append(") RETURNING ticketid");
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
		      
		      pst.setBoolean(++i, super.isFlag_posticipato());
		      pst.setBoolean(++i, super.isFlag_campione_non_conforme());
		      
		      
		      if (tipo_nc != -1)
		      {
		    	 pst.setInt(++i, tipo_nc);
		      }

		      
		      if (idControlloUfficiale != null)
		      {
		    	  pst.setString(++i,idControlloUfficiale);
		      }
		      if (altId>0){ DatabaseUtils.setInt(pst, ++i, altId);}
		      if (farmacosorveglianza == true)
		      {
		    	  DatabaseUtils.setInt(pst, ++i, orgId);
		      }
		      else
		      {
		    	  DatabaseUtils.setInt(pst, ++i, orgId);
		    	  DatabaseUtils.setInt(pst, ++i, idStabilimento);
		    	  DatabaseUtils.setInt(pst, ++i, idApiario);
		      }
		      
		      
		      if (entered != null) {
		        pst.setTimestamp(++i, entered);
		      }
		      if (modified != null) {
		        pst.setTimestamp(++i, modified);
		      } 
		      
		    
		    	  pst.setString(++i, trasgressore);
		    	  //AGGIUNTA DEI 3 TRASGRESSORI
		    	  if(!trasgressore2.equals("")){
		    		  pst.setString(++i, trasgressore2);
		    	  }
		    	  
		    	  if(!trasgressore3.equals("")){
		    		  pst.setString(++i, trasgressore3);
		    	  }
		    	 //
		    	  pst.setString(++i, obbligatoinSolido);
		    	  
		    	  if(obbligatoinSolido2!=null){
		    		  pst.setString(++i, obbligatoinSolido2);
		    	  }
		    	  
		    	  if(obbligatoinSolido3!=null){
		    		  pst.setString(++i, obbligatoinSolido3);
		    	  }
		    	  
		    	  if(articoloviolato!=null){
		    		  pst.setString(++i, articoloviolato);
		    	  }
		    	  if(sanzione!=null){
		    		  pst.setString(++i, sanzione);
		    	  }
		      
		      pst.setString(++i, normaviolata);
		      pst.setDouble(++i, pagamento);
		      /**
		       * 
		       */
		      
		      
		      pst.setString( ++i, this.getNumVerbaleSequestro() );
		      pst.setInt( ++i, this.getSequestroRiduzione() );

		      pst.setString( ++i, this.getTipo_richiesta() );
		     
		      pst.setString( ++i, this.getPippo() );
		      
		      pst.setInt(++i, this.getEnteredBy());
		      pst.setInt(++i, this.getModifiedBy());
		      DatabaseUtils.setInt(pst, ++i, provvedimenti);
		      DatabaseUtils.setInt(pst, ++i, sanzioniAmministrative);
		      DatabaseUtils.setInt(pst, ++i, sanzioniPenali);
		      DatabaseUtils.setInt(pst, ++i, sequestri);
		      pst.setString( ++i, this.getDescrizione1() );
		      pst.setString( ++i, this.getDescrizione2() );
		      pst.setString( ++i, this.getDescrizione3() );
		      pst.setBoolean(++i , this.getTipoSequestro());
		      pst.setBoolean(++i , this.getTipoSequestroDue());
		      pst.setBoolean(++i , this.getTipoSequestroTre());
		      pst.setBoolean(++i , this.getTipoSequestroQuattro());
		      pst.setInt(++i, id_nonconformita);
		        if (punteggio != -1) {
			        pst.setInt(++i, punteggio);
			      }
		        
			   
		      /* pezzo aggiunto da d.dauria 
		      pst.setTimestamp(++i,new Timestamp( System.currentTimeMillis() ));
		      pst.setString(++i, "x");
		      this.setCloseIt(true);
		         */
		      ResultSet rs = pst.executeQuery();
		      if (rs.next())
		    	  this.id = rs.getInt("ticketid");
		      pst.close();
		      
		      db.prepareStatement("UPDATE TICKET set identificativo = '"+asl+idControlloUfficiale+"' || trim(to_char( "+id+", '"+DatabaseUtils.getPaddedFromId(id)+"' )) where ticketid ="+this.getId()).execute();
		      
		      
		      //Update the rest of the fields
		      this.update(db, true);
		     
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

	  protected void buildRecord(ResultSet rs) throws SQLException {
		  
		    //ticket table
		    this.setId(rs.getInt("ticketid"));
		    altId = DatabaseUtils.getInt(rs, "alt_id");
		    if (farmacosorveglianza == false)
		    {
		    	orgId = DatabaseUtils.getInt(rs, "org_id");
		    	idStabilimento = DatabaseUtils.getInt(rs, "id_stabilimento");
		    	idApiario = DatabaseUtils.getInt(rs, "id_apiario");
		    }
		    else
		    {
		    	orgId = DatabaseUtils.getInt(rs, "id_farmacia");
		    	idStabilimento = DatabaseUtils.getInt(rs, "id_stabilimento");
		    	idApiario = DatabaseUtils.getInt(rs, "id_apiario");

		    }
		    
		    try
		    {
		    	super.setIdDistributore(rs.getInt("id_distributore"));
		    }
		    catch
		    (SQLException e)
		    {
		    	
		    }
		    try
		    {
			    super.setTipologiaNonConformita(rs.getInt("tipologia_nonconformita"));
		    }
		    catch
		    (SQLException e)
		    {
		    	
		    }
		    
		   tipologia_operatore = rs.getInt("tipo_operatore");
		   if (idStabilimento>0)
			   tipologia_operatore = Ticket.TIPO_OPU;
		   chiusura_attesa_esito = rs.getBoolean("chiusura_attesa_esito");
		    contactId = DatabaseUtils.getInt(rs, "contact_id");
		    problem = rs.getString("problem");
		    entered = rs.getTimestamp("entered");
		    enteredBy = rs.getInt("enteredby");
		    modified = rs.getTimestamp("modified");
		    modifiedBy = rs.getInt("modifiedby");
		    closed = rs.getTimestamp("closed");
		  
		    tipo_nc = rs.getInt("tipo_nc") ;
		    id_nonconformita = rs.getInt("id_nonconformita");
			idTicketNonConformita = id_nonconformita ;
			idControlloUfficialeTicket = rs.getInt("id_cu");
		    articoloviolato=rs.getString("articoloviolato");
		    sanzione=rs.getString("sanzione");
		    identificativonc=rs.getString("identificativonc");
		    normaviolata=rs.getString("normaviolata");
		    pagamento=rs.getDouble("pagamento");
		    trasgressore=rs.getString("trasgressore");
		    trasgressore2=rs.getString("trasgressore2");
		    trasgressore3=rs.getString("trasgressore3");
		    
		    obbligatoinSolido=rs.getString("obbligatoinsolido");
		    obbligatoinSolido2=rs.getString("obbligatoinsolido2");
		    obbligatoinSolido3=rs.getString("obbligatoinsolido3");
		    location = rs.getString("location");
		    assignedDate = rs.getTimestamp("assigned_date");
		    
		    try{  dataFineControllo = rs.getTimestamp("cu_data_fine_controllo"); } catch (SQLException e) { }
		    
		    estimatedResolutionDate = rs.getTimestamp("est_resolution_date");
		    resolutionDate = rs.getTimestamp("resolution_date");
		    cause = rs.getString("cause");
		    assetId = DatabaseUtils.getInt(rs, "link_asset_id");
		    trashedDate = rs.getTimestamp("trashed_date");
		    causeId = DatabaseUtils.getInt(rs, "cause_id");
		    resolutionId = DatabaseUtils.getInt(rs, "resolution_id");
		    siteId = DatabaseUtils.getInt(rs, "site_id");
		    numVerbaleSequestro = rs.getString("verbale_sequestro");
		    sequestroRiduzione = rs.getInt("sequestro_riduzione");
		    tipo_richiesta = rs.getString( "tipo_richiesta" );
		    pippo = rs.getString( "custom_data" );
		    tipologia = rs.getInt( "tipologia" );
		    provvedimenti = DatabaseUtils.getInt(rs, "provvedimenti_prescrittivi");
		    sanzioniAmministrative = DatabaseUtils.getInt(rs, "sanzioni_amministrative");
		    sanzioniPenali = DatabaseUtils.getInt(rs, "sanzioni_penali");
		    sequestri = DatabaseUtils.getInt(rs, "sequestri");
		    
		    descrizione1 = rs.getString( "descrizione1" );
		    descrizione2 = rs.getString( "descrizione2" );
		    descrizione3 = rs.getString( "descrizione3" );
		    
			tipoSequestro = rs.getBoolean("tipo_sequestro");
			tipoSequestroDue = rs.getBoolean("tipo_sequestro_due");
			tipoSequestroTre = rs.getBoolean("tipo_sequestro_tre");
			tipoSequestroQuattro = rs.getBoolean("tipo_sequestro_quattro");
			idControlloUfficiale = rs.getString("id_controllo_ufficiale");		    
		    identificativo = rs.getString("identificativo");
		    punteggio = rs.getInt("punteggio");

		   setPermission();
		    
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
		        "status_id = ?, trashed_date = ?,  ip_modified='"+super.getIpModified()+"',");
		    if (!override) {
		      sql.append(
		          "modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", modifiedby = ?, ");
		    }
		 
		    
		    sql.append("site_id=?,");
		    
	    	  if(articoloviolato!=null){
	    		  sql.append("articoloviolato=?, ");
	    	  }
	    	  if(sanzione!=null){
	    		  sql.append("sanzione=?, ");
	    	  }
		    
		    if (!trasgressore.equals("")) {
			      sql.append(" trasgressore= ?, ");
			    }
		    
		    if (trasgressore2!=null) {
		    	sql.append(" trasgressore2= ?, ");
		    }
		    
		    if (trasgressore3!=null) {
		    	sql.append(" trasgressore3= ?, ");
		    }
		    
		    if (obbligatoinSolido!=null) {
			      sql.append(" obbligatoinsolido= ?, ");
			    }
		    
		    if (obbligatoinSolido2!=null) {
			      sql.append(" obbligatoinsolido2= ?, ");
			    }
		    
		    if (obbligatoinSolido3!=null) {
			      sql.append(" obbligatoinsolido3= ?, ");
			    }
		    
		    
		    sql.append(" normaviolata= ?, ");
		    sql.append(" pagamento= ?, ");
		    
		    
		    sql.append(
		        "solution = ?, custom_data = ?, location = ?, assigned_date = ?, assigned_date_timezone = ?, " +
		        "est_resolution_date = ?, est_resolution_date_timezone = ?, resolution_date = ?, resolution_date_timezone = ?, " +
		        "cause = ?, expectation = ?, product_id = ?, customer_product_id = ?, " +
		        "user_group_id = ?, cause_id = ?, resolution_id = ?, defect_id = ?, state_id = ?, " +

		        //"escalation_level = ?, resolvable = ?, resolvedby = ?, resolvedby_department_code = ?, provvedimenti_prescrittivi = ?, sanzioni_amministrative = ?, sanzioni_penali = ?, sequestri = ?, descrizione1 = ?, descrizione2 = ?, descrizione3 = ? , tipo_sequestro = ?, tipo_sequestro_due = ?, tipo_sequestro_tre = ?, tipo_sequestro_quattro = ? " +

		        "escalation_level = ?, resolvable = ?, resolvedby = ?, resolvedby_department_code = ?,verbale_sequestro=?, sequestro_riduzione=?, tipo_richiesta = ?, " +
		        "provvedimenti_prescrittivi = ?, sanzioni_amministrative = ?, sanzioni_penali = ?, sequestri = ?, descrizione1 = ?, " +
		        "descrizione2 = ?, descrizione3 = ?, tipo_sequestro = ?, tipo_sequestro_due = ?, tipo_sequestro_tre = ?, tipo_sequestro_quattro = ? , punteggio = ?" +

		        "WHERE ticketid = ? ");
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
		
		    if (!override) {
		      pst.setInt(++i, this.getModifiedBy());
		    }
		  
		   
		    pst.setInt(++i, siteId);
		    
	    	  if(articoloviolato!=null){
	    		  pst.setString(++i, articoloviolato);
	    	  }
	    	  if(sanzione!=null){
	    		  pst.setString(++i, sanzione);
	    	  }
		    
		    if (!trasgressore.equals("")) {
			     pst.setString(++i, trasgressore);
			    }
		    if (trasgressore2!=null) {
		    	  pst.setString(++i, trasgressore2);
			    }
		    if (trasgressore3!=null) {
		    	pst.setString(++i, trasgressore3);
		    }
		    
		    /*if (obbligatoinSolido3!=null) {
		    	  pst.setString(++i, obbligatoinSolido3);
			    }*/
		    if (obbligatoinSolido!=null) {
		    	  pst.setString(++i, obbligatoinSolido);
			    }
		    
		    if (obbligatoinSolido2!=null) {
		    	  pst.setString(++i, obbligatoinSolido2);
			    }
		    
		    if (obbligatoinSolido3!=null) {
		    	  pst.setString(++i, obbligatoinSolido3);
			    }
		    pst.setString(++i, normaviolata);
		  pst.setDouble(++i, pagamento);
		    
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
		    pst.setString( ++i, numVerbaleSequestro );
		    pst.setInt( ++i, sequestroRiduzione );
		    pst.setString( ++i, tipo_richiesta );
		    DatabaseUtils.setInt(pst, ++i, provvedimenti);
		    DatabaseUtils.setInt(pst, ++i, sanzioniAmministrative);
		    DatabaseUtils.setInt(pst, ++i, sanzioniPenali);
		    DatabaseUtils.setInt(pst, ++i, sequestri);
		    
		    pst.setString(++i, descrizione1);
		    pst.setString(++i, descrizione2);
		    pst.setString(++i, descrizione3);
		    
		    pst.setBoolean(++i, this.getTipoSequestro());
			pst.setBoolean(++i, this.getTipoSequestroDue());
			pst.setBoolean(++i, this.getTipoSequestroTre());
			pst.setBoolean(++i, this.getTipoSequestroQuattro());
			pst.setInt(++i, punteggio);
		    
		    pst.setInt(++i, id);
		    /*if (!override && this.getModified() != null) {
		      pst.setTimestamp(++i, this.getModified());
		    }*/
		    resultCount = pst.executeUpdate();
		    
		    pst.close();
		    
		    //controllo di
		
		   
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
		
		// Delete any documents
		FileItemList fileList = new FileItemList();
		fileList.setLinkModuleId(Constants.DOCUMENTS_TICKETS);
		fileList.setLinkItemId(this.getId());
		fileList.buildList(db);
		fileList.delete(db, getFileLibraryPath(baseFilePath, "tickets"));
		fileList = null;


		logicdelete(db, baseFilePath);
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
		
		public ArrayList<HashMap>  getAttributi_registro_ordinanze() {
			return attributi_registro_ordinanze;
		}
		public void setAttributi_registro_ordinanze(ArrayList<HashMap>  attributi_registro_ordinanze) {
			this.attributi_registro_ordinanze = attributi_registro_ordinanze;
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
		
		
		  
		public  void setFieldsRegistro(Connection db) throws SQLException{
			/*StringBuffer sqlSelect = new StringBuffer();
			PreparedStatement pst = null;
			String html = "";
			HashMap hashReg = null ;new HashMap();
			sqlSelect.append("SELECT htmlfield.* ,  reg.valore,case when role_id>0 then true else false end as permission_edit  " +
"from " +
"regordinanze_html_fields htmlfield " +
"left join regordinanze_html_fields_permission_edit permission on permission.id_regordinanze_html_fields =htmlfield.id and role_id = ?" +
"left join  registro_ordinanze reg on reg.id_html_field = htmlfield.id and id_sanzione = ? and reg.enabled  " +
"order by ordine_campo ");
			
			pst = db.prepareStatement(sqlSelect.toString());
			pst.setInt(1, idRuoloUtente);
			pst.setInt(2, this.getId());
			ResultSet rs = pst.executeQuery();
			 while (rs.next()) {
				 hashReg = new HashMap();
		    		 
					 html = "";
					 hashReg.put("permesso_edit", rs.getBoolean("permission_edit"));
					 hashReg.put("default", rs.getString("valore"));
					 hashReg.put("name", "registro_"+rs.getString("nome_campo")+"-"+rs.getInt("id"));
					 hashReg.put("valore", rs.getString("valore"));
					 hashReg.put("type", rs.getString("tipo_campo"));
					 hashReg.put("label", rs.getString("label_campo"));
					 hashReg.put("label_data", rs.getString("label_campo_controlli_date"));
					 if (rs.getInt("maxlength") > 0){
						 hashReg.put("size", rs.getInt("maxlength"));
					 }else{
						 hashReg.put("size", 500);
					 }
					 
					 if (rs.getString("javascript_function") != null && rs.getString("javascript_function") != "" ){
						 hashReg.put("javascript", rs.getString("javascript_function"));
					 }else{
						 hashReg.put("javascript", "");
					 }
			    	  	if ("select".equals(rs.getString("tipo_campo"))){
			    	  		
			    				LookupList lookuplist = new LookupList(db, rs.getString("tabella_lookup"));
			    				lookuplist.addItem(-1, "--Seleziona--");
			    				if (rs.getString("javascript_function") != null && !("").equals(rs.getString("javascript_function")))
			    					lookuplist.setJsEvent(rs.getString("javascript_function"));
			    				if (("lookup_asl_rif").equals(rs.getString("tabella_lookup"))){
			    					lookuplist.remove(1); //Tolgo asl f. regione
			    				}
			    			
			    				html = 	lookuplist.getHtmlSelect(rs.getString("nome_campo"), -1);
			    				
			    	  		
			    	  		
			    	  	}else if ("link".equals(rs.getString("tipo_campo"))){
			    	  		if (rs.getString("link_value") == null || "".equals(rs.getString("link_value")) ){
			    	  			html = "<a href=\"javascript:"+rs.getString("javascript_function")+"\" >" +rs.getString("label_campo") + "</a>";
			    	  		}else{
			    	  			html = "<a href=\""+rs.getString("link_value")+"\" >" +rs.getString("label_campo") + "</a>";
			    	  		}
			    	  		
			    	  	}
			    	  	hashReg.put("html", html);
			    	  	attributi_registro_ordinanze.add(hashReg);
					 
		      }
		      rs.close();
		      pst.close();*/
			
		}




		public int getSequestroRiduzione() {
			return sequestroRiduzione;
		}




		public void setSequestroRiduzione(int sequestroRiduzione) {
			this.sequestroRiduzione = sequestroRiduzione;
		}
		
		public void setSequestroRiduzione(String sequestroRiduzione) {
			if (sequestroRiduzione!=null && !sequestroRiduzione.equals("null") && !sequestroRiduzione.equals(""))
			this.sequestroRiduzione = Integer.parseInt(sequestroRiduzione);
		}




		public ArrayList<String[] > getListaAllegatiPV() {
			return listaAllegatiPV;
		}




		public void setListaAllegatiPV(ArrayList<String[] > listaAllegatiPV) {
			this.listaAllegatiPV = listaAllegatiPV;
		}

		public void setListaAllegati(Connection db) throws SQLException {
			
			PreparedStatement pst = db.prepareStatement("select * from sanzioni_allegati where id_sanzione = ? and trashed_date is null");
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()){
				
				String[] all = {rs.getString("header_allegato"), rs.getString("oggetto"), rs.getString("nome_client")};
				
				if (rs.getString("tipo_allegato").equalsIgnoreCase("SanzionePV"))
					this.listaAllegatiPV.add(all);
				else if (rs.getString("tipo_allegato").equalsIgnoreCase("SanzioneAL"))
					this.listaAllegatiAL.add(all);
			}
		}



		public ArrayList<String[] > getListaAllegatiAL() {
			return listaAllegatiAL;
		}




		public void setListaAllegatiAL(ArrayList<String[] > listaAllegatiAL) {
			this.listaAllegatiAL = listaAllegatiAL;
		}  
		
		public void setCompetenza(Connection db) throws SQLException {
			PreparedStatement pst = db.prepareStatement("select * from sanzioni_competenze where id_sanzione = ? and trashed_date is null");
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()){
				this.competenza = new Competenza(rs);
			}
		}

		
		public String isSanzioneCancellabilePagoPa(Connection db) throws SQLException{
			
			String messaggio = "";
			String sql="select * from is_cu_nc_sa_cancellabile_pagopa(-1, -1, ?)";
			PreparedStatement pst=db.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet rst=pst.executeQuery();

			while(rst.next()){
				messaggio=rst.getString(1);
			}
			return messaggio;
		}




		public Competenza getCompetenza() {
			return competenza;
		}




		public void setCompetenza(Competenza competenza) {
			this.competenza = competenza;
		}



	
}

