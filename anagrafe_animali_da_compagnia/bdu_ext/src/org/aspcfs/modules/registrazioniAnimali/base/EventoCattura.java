/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.registrazioniAnimali.base;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;




import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.anagrafe_animali.base.Cane;
import org.aspcfs.modules.anagrafe_animali.base.Furetto;
import org.aspcfs.modules.anagrafe_animali.base.Gatto;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.GestoreConnessioni;
//import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.DbUtil;
import org.aspcfs.utils.MethodsUtils;


public class EventoCattura extends Evento{
	
	public static final int idTipologiaDB = 5;
	public static final int idTipologiaDBRicattura = 24;
	
	
	private int id = -1;
	private java.sql.Timestamp dataCattura;
	private int idComuneCattura;
	private String indirizzoCattura = "";
	private String verbaleCattura = "";
	private String luogoCattura = "";
	private int idEvento;
	private boolean flagRicattura = false;
	private int idDetentore = -1;
	private int idProprietarioSindaco = -1;
	private int idMunicipalita = -1;
	
	
	
	




	public EventoCattura() {
		super();
		// TODO Auto-generated constructor stub
	}



	public java.sql.Timestamp getDataCattura() {
		return dataCattura;
	}



	public void setDataCattura(java.sql.Timestamp dataCattura) {
		this.dataCattura = dataCattura;
	}


	public void setDataCattura(String dataCattura){
		this.dataCattura = DateUtils.parseDateStringNew(dataCattura, "dd/MM/yyyy");
	}
	


	public int getIdComuneCattura() {
		return idComuneCattura;
	}



	public void setIdComuneCattura(int idComuneCattura) {
		this.idComuneCattura = idComuneCattura;
	}
	
	public void setIdComuneCattura(String idComuneCattura) {
		this.idComuneCattura = (new Integer (idComuneCattura)).intValue();
	}



	public String getIndirizzoCattura() {
		return indirizzoCattura;
	}



	public void setIndirizzoCattura(String indirizzoCattura) {
		this.indirizzoCattura = indirizzoCattura;
	}



	public String getVerbaleCattura() {
		return verbaleCattura;
	}



	public void setVerbaleCattura(String verbaleCattura) {
		this.verbaleCattura = verbaleCattura;
	}



	public String getLuogoCattura() {
		return luogoCattura;
	}



	public void setLuogoCattura(String luogoCattura) {
		this.luogoCattura = luogoCattura;
	}



	public int getIdEvento() {
		return idEvento;
	}



	public void setIdEvento(int idEvento) {
		this.idEvento = idEvento;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}
	
	
	  public boolean isFlagRicattura() {
			return flagRicattura;
		}



		public void setFlagRicattura(String flagRicattura) {
			this.flagRicattura = DatabaseUtils.parseBoolean(flagRicattura);
		}
		



		public int getIdDetentore() {
			return idDetentore;
		}



		public void setIdDetentore(int idDetentore) {
			this.idDetentore = idDetentore;
		}

		
		public void setIdDetentore(String idDetentore) {
			this.idDetentore = new Integer (idDetentore).intValue();
		}


		public int getIdProprietario() {
			return idProprietarioSindaco;
		}



		public void setIdProprietarioSindaco(int idProprietarioSindaco) {
			this.idProprietarioSindaco = idProprietarioSindaco;
		}
		
		public void setIdProprietarioSindaco(String idProprietarioSindaco) {
			this.idProprietarioSindaco = new Integer (idProprietarioSindaco).intValue();
		}




	public int getIdMunicipalita() {
			return idMunicipalita;
		}



		public void setIdMunicipalita(int idMunicipalita) {
			this.idMunicipalita = idMunicipalita;
		}
		
		
		public void setIdMunicipalita(String idMunicipalita) {
			this.idMunicipalita = Integer.valueOf(idMunicipalita);
		}



	public boolean insert(Connection db) throws SQLException {
		 
		 StringBuffer sql = new StringBuffer();
	  
	    try {
	    	
	    	super.insert(db);
	    	idEvento = super.getIdEvento();
		      
		      id = DatabaseUtils.getNextSeq(db, "evento_cattura_id_seq");
		     // sql.append("INSERT INTO animale (");
		      

		      
		      
		      
		      sql.append("INSERT INTO evento_cattura(id_evento, data_cattura ");
		    		 
	          if ( idComuneCattura != -1){
	        	 sql.append(",id_comune_cattura " );
	          }
	          
	          if (verbaleCattura != null && !"".equals(verbaleCattura)){
	        	  sql.append(",verbale_cattura " );
	          }
	          
	          if (luogoCattura != null && !"".equals(luogoCattura)){
	        	  sql.append(",luogo_cattura " );
	          }
	          
	          sql.append(", flag_ricattura ");
	          
	          
	          if (idDetentore > -1){
	        	  sql.append(", id_detentore_cattura ");
	          }
	          
	          
	          if (idProprietarioSindaco > -1){
	        	  sql.append(", id_proprietario_cattura ");
	          }
	          
	          
	          if (idMunicipalita > -1){
	        	  sql.append(", id_municipalita ");
	          }
	          
		 
		    
       
		      
	
         
         sql.append(")VALUES(?,?");
         
         if ( idComuneCattura != -1){
       	  sql.append(",?");
         }
         
         if (verbaleCattura != null && !"".equals(verbaleCattura)){
       	  sql.append(",?");
         }
         
         if (luogoCattura != null && !"".equals(luogoCattura)){
          	  sql.append(",?");
            }
         
         sql.append(", ? ");
         
         
         if (idDetentore > -1){
       	  sql.append(", ? ");
         }
         
         if (idProprietarioSindaco > -1){
       	  sql.append(", ?");
         }
         
         if (idMunicipalita > -1){
        	 sql.append(", ?");
         }
         
        
         
         sql.append(")");

		      int i = 0;
		      PreparedStatement pst = db.prepareStatement(sql.toString());
		      
		    
		      
		  
		    	  pst.setInt(++i, idEvento);
		      
		      
		      
		    	  pst.setTimestamp(++i, dataCattura);
		    	  
		          if ( idComuneCattura != -1){
		        	  pst.setInt(++i, idComuneCattura);
		          }
		          
		          if (verbaleCattura != null && !"".equals(verbaleCattura)){
		        	  pst.setString(++i, verbaleCattura);
		          }
		          
		          if (luogoCattura != null && !"".equals(luogoCattura)){
		        	  pst.setString(++i, luogoCattura);
		          }
		          
		          pst.setBoolean(++i, flagRicattura);
		          
		          
		          if (idDetentore > -1){
		        	  pst.setInt(++i, idDetentore);
		          }
		          
		          if (idProprietarioSindaco > -1){
		        	  pst.setInt(++i, idProprietarioSindaco);
		          }
		          
		          if (idMunicipalita > -1){
		        	  pst.setInt(++i, idMunicipalita);
		          }

		      
		      pst.execute();
		      pst.close();

		      this.id = DatabaseUtils.getCurrVal(db, "evento_cattura_id_seq", id);
		     
		    	   
		   	    } catch (SQLException e) {
		   	  
		   	      throw new SQLException(e.getMessage());
		   	    } finally {
		   	 
		   	    }
		   	    return true;
		   	  
		   	 }
		
/*	
	
	 public static ArrayList getFields(Connection db){
		 
		 ArrayList fields = new ArrayList();
		 HashMap fields1 = new HashMap();
		 fields1.put("name", "dataCattura");
		 fields1.put("type", "data");
		 fields1.put("label", "Data cattura");
		 fields.add(fields1);
		 
		 
		 
		 String html = "";
		 try {
			 ComuniAnagrafica c = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> listaComuni =  c.buildList(db, -1, -1);
			LookupList comuniList = new LookupList(listaComuni, -1);
			comuniList.addItem(-1, "--Seleziona--");
			 html = 	comuniList.getHtmlSelect("idComuneCattura", -1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 fields1 = new HashMap();
		 fields1.put("name", "idComuneCattura");
		 fields1.put("type", "select");
		 fields1.put("label", "Comune cattura");
		 fields1.put("html", html);
		 fields.add(fields1);
		 
		 
		 fields1 = new HashMap();
		 fields1.put("name", "indirizzoCattura");
		 fields1.put("type", "text");
		 fields1.put("label", "Indirizzo cattura");			 
		 fields.add(fields1);
		 
		 
		 fields1 = new HashMap();
		 fields1.put("name", "verbaleCattura");
		 fields1.put("type", "text-area");
		 fields1.put("label", "Verbale cattura");			 
		 fields.add(fields1);
		 
		 
		 fields1 = new HashMap();
		 fields1.put("name", "luogoCattura");
		 fields1.put("type", "text");
		 fields1.put("label", "Luogo cattura");			 
		 fields.add(fields1);
		 
		 
		

		 
		
		 
		 
		 return fields;
	 }*/
	




	public EventoCattura (ResultSet rs) 
		// TODO Auto-generated constructor stub
	 throws SQLException {
		    buildRecord(rs);
		  }
	  
	  protected void buildRecord(ResultSet rs) throws SQLException {
		  
		  super.buildRecord(rs);
		  this.idEvento = rs.getInt("idevento");
		  this.dataCattura = rs.getTimestamp("data_cattura");
		  this.idComuneCattura = rs.getInt("id_comune_cattura");
		  this.verbaleCattura = rs.getString("verbale_cattura");
		  this.luogoCattura = rs.getString("luogo_cattura");
		  this.idProprietarioSindaco = rs.getInt("id_proprietario_cattura");
		  this.idDetentore = rs.getInt("id_detentore_cattura");
		  this.idMunicipalita = rs.getInt("id_municipalita");
		  
		  
		//  buildSede(rs);
		//  buildRappresentanteLegale(rs);

		  
		  
	  }
	  
	  
		public EventoCattura(Connection db, int idEventoPadre) throws SQLException {

		//	super(db, idEventoPadre);

			PreparedStatement pst = db
					.prepareStatement("Select *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento from evento e left join evento_cattura f on (e.id_evento = f.id_evento) where e.id_evento = ?");
			pst.setInt(1, idEventoPadre);
			ResultSet rs = DatabaseUtils.executeQuery(db, pst);
			if (rs.next()) {
				buildRecord(rs);
			}

			if (idEventoPadre == -1) {
				throw new SQLException(Constants.NOT_FOUND_ERROR);
			}

			rs.close();
			pst.close();
		}
		
		
		public Operatore getProprietario() throws UnknownHostException {
			Connection conn = null;
			int idOperatore = -1;
			Operatore operatore = null;
			try {
				
				Thread t = Thread.currentThread(); conn = GestoreConnessioni.getConnection();

//				String dbName = ApplicationProperties.getProperty("dbnameBdu");
//				String username = ApplicationProperties.getProperty("usernameDbbdu");
//				String pwd = ApplicationProperties.getProperty("passwordDbbdu");
//				String host = InetAddress.getByName("hostDbBdu").getHostAddress();
//
//				conn = DbUtil.getConnection(dbName, username, pwd, host);
				
				idOperatore = this.getIdProprietario();
				

				if (idOperatore != -1 && idOperatore != 0) {
					
					operatore = new Operatore();
					operatore
							.queryRecordOperatorebyIdLineaProduttiva(conn,
									idOperatore);
				}

				//GestoreConnessioni.freeConnection(conn);
//				DbUtil.chiudiConnessioneJDBC(null, null, conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				GestoreConnessioni.freeConnection(conn);
			}

			return operatore;
		}
		
		
		public Operatore getDetentore() throws UnknownHostException {
			Connection conn = null;
			int idOperatore = -1;
			Operatore operatore = null;
			try {
				Thread t = Thread.currentThread(); conn = GestoreConnessioni.getConnection();

//				String dbName = ApplicationProperties.getProperty("dbnameBdu");
//				String username = ApplicationProperties.getProperty("usernameDbbdu");
//				String pwd = ApplicationProperties.getProperty("passwordDbbdu");
//				String host = InetAddress.getByName("hostDbBdu").getHostAddress();
//
//				conn = DbUtil.getConnection(dbName, username, pwd, host);


				idOperatore = this.getIdDetentore();
				

				if (idOperatore != -1 && idOperatore != 0) {
					
					operatore = new Operatore();
					operatore
							.queryRecordOperatorebyIdLineaProduttiva(conn,
									idOperatore);
				}

				//GestoreConnessioni.freeConnection(conn);
//				DbUtil.chiudiConnessioneJDBC(null, null, conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				GestoreConnessioni.freeConnection(conn);
			}

			return operatore;
		}
		public int cercaUltimaReimmissione(Connection db, int idAnimale) throws SQLException {

			//Cerco l'evento reimmissione con l'id maggiore che abbia la data evento minore o uguale a quella di cattura
				int ultimaReimmissione=-1;
				PreparedStatement pst = db.prepareStatement("Select max(reimm.id_evento) from evento_reimmissione reimm inner join evento e on e.id_evento = reimm.id_evento where e.id_animale = ? and reimm.data_reimmissione<= ?");
				pst.setInt(1, idAnimale);
				pst.setTimestamp(2, this.getDataCattura());
				ResultSet rs = DatabaseUtils.executeQuery(db, pst);
				if (rs.next()) {
					ultimaReimmissione=rs.getInt("max");
				}

					rs.close();
				pst.close();
				return ultimaReimmissione;
			}
		
		public EventoCattura salvaRegistrazione(int userId,
				int userRole, int userAsl, Animale thisAnimale, Connection db)
				throws Exception {
			try {

				super.salvaRegistrazione(userId, userRole, userAsl, thisAnimale, db);
				
				Animale oldAnimale = new Animale(db, this.getIdAnimale());

				switch (this.getSpecieAnimaleId()) {
				case Cane.idSpecie:
					thisAnimale = new Cane(db, this.getIdAnimale());
					break;
				case Gatto.idSpecie:
					thisAnimale = new Gatto(db, this.getIdAnimale());
					break;
				case Furetto.idSpecie:
					thisAnimale = new Furetto(db, this.getIdAnimale());
					break;
				default:
					break;
				}
				


				this.insert(db);
				
				switch (oldAnimale.getIdSpecie()) {
				case Cane.idSpecie:{
					Cane thisCane =(Cane) thisAnimale;
					thisCane.setIdDetentore(this.getIdDetentore()); // Aggiorno
					// al nuovo
					// detentore
					thisCane.setFlagRiCattura(true);
					thisCane.setFlagReimmesso(false);

				    thisAnimale = thisCane;
					
					break;
			}				case Gatto.idSpecie:{
				Gatto thisGatto =(Gatto) thisAnimale;
				thisGatto.setIdDetentore(this.getIdDetentore()); // Aggiorno
				// al nuovo
				// detentore
				thisGatto.setFlagRiCattura(true);
				thisGatto.setFlagReimmesso(false);

			    thisAnimale = thisGatto;
				
				break;
		}

				default:
					break;
				}
				
			
				
				aggiornaFlagFuoriDominioAsl(db, thisAnimale,  userAsl,  oldAnimale);
				aggiornaStatoAnimale(db, thisAnimale);

			} catch (Exception e) {
				throw e;
			}

			return this;

		}
		
		public EventoCattura build(ResultSet rs) throws Exception{
			try{	
				
				super.build(rs);
				buildRecord(rs);
			
			}catch (Exception e){
				throw e;
			}
		return this;
			}
			
}
