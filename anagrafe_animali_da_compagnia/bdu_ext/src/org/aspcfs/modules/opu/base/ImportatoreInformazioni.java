/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu.base;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;

public class ImportatoreInformazioni extends LineaProduttiva {
	 //Table opu_informazioni_importatore
	
	private int idInformazioni = -1;
	private String codiceUvac = "";
	private int nrControlliAperti = 0;
	private int nrControlliChiusi = 0;
	private java.sql.Timestamp dataProssimoControllo = null;
	private int categoriaRischio = 3;
	private int mqDisponibili = 0;

	public String getCodiceUvac() {
		return codiceUvac;
	}

	public void setCodiceUvac(String codiceUvac) {
		this.codiceUvac = codiceUvac;
	}
	

	public int getIdInformazioni() {
		return idInformazioni;
	}

	public void setIdInformazioni(int idInformazioni) {
		this.idInformazioni = idInformazioni;
	}

	public int getNrControlliAperti() {
		return nrControlliAperti;
	}

	public void setNrControlliAperti(int nrControlliAperti) {
		this.nrControlliAperti = nrControlliAperti;
	}

	public int getNrControlliChiusi() {
		return nrControlliChiusi;
	}

	public void setNrControlliChiusi(int nrControlliChiusi) {
		this.nrControlliChiusi = nrControlliChiusi;
	}

	public java.sql.Timestamp getDataProssimoControllo() {
		return dataProssimoControllo;
	}

	public void setDataProssimoControllo(java.sql.Timestamp dataProssimoControllo) {
		this.dataProssimoControllo = dataProssimoControllo;
	}

	public int getCategoriaRischio() {
		return categoriaRischio;
	}

	public void setCategoriaRischio(int categoriaRischio) {
		this.categoriaRischio = categoriaRischio;
	}


	
	public int getMqDisponibili() {
		return mqDisponibili;
	}

	public void setMqDisponibili(int mqDisponibili) {
		this.mqDisponibili = mqDisponibili;
	}
	
	
	public void setMqDisponibili(String mqDisponibili) {
		if (mqDisponibili != null)
			this.mqDisponibili = Integer.valueOf(mqDisponibili);
	}

	public ImportatoreInformazioni() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	

  
  public boolean insert(Connection db) throws SQLException {
	  
	 // System.out.println("insert informazioni");

		StringBuffer sql = new StringBuffer();
		try {
			

			super.insert(db);

			int idAnimale = super.getId();

			idInformazioni = DatabaseUtils.getNextSeqPostgres(db, "opu_informazioni_importatore_id_seq");
			// sql.append("INSERT INTO animale (");

			sql
					.append("INSERT INTO opu_informazioni_importatore(id_relazione_stabilimento_lp, codice_uvac, mq_disponibili ");

			

			
			sql.append(")");
			sql.append("VALUES ( ?,?,? ");


			sql.append(")");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			pst.setInt(++i, super.getId());
			pst.setString(++i, codiceUvac);
			pst.setInt(++i, mqDisponibili);
			
			pst.execute();
			pst.close();

			this.idInformazioni = DatabaseUtils.getCurrVal(db, "opu_informazioni_importatore_id_seq",
					idInformazioni);

			

		} catch (SQLException e) {
			
			throw new SQLException(e.getMessage());
		} finally {
			
		}
		return true;

	}

  
  public static ImportatoreInformazioni getInfoAddizionali(LineaProduttiva lp, Connection db){
	 ImportatoreInformazioni info = new ImportatoreInformazioni();
	 Field[] campi = lp.getClass().getDeclaredFields();
	 Method metodoGet = null;
	 Method metodoSet= null;
		
	 
	 for (int i = 0; i < campi.length; i++) {
			int k = 0;

			String nameToUpperCase = (campi[i].getName().substring(0, 1)
					.toUpperCase() + campi[i].getName().substring(1,
					campi[i].getName().length()));
			

				// (nameToUpperCase.equals("IdTipoMantello")){
				try {
					
					metodoGet = lp.getClass().getMethod(
							"get" + nameToUpperCase, null);
					metodoSet = info.getClass().getSuperclass().getMethod(
							"set" + nameToUpperCase, String.class);
					
				

				if (metodoGet != null && metodoSet != null)	{		
					if (("DataInizio").equals(nameToUpperCase)){
					//	System.out.println(nameToUpperCase);
					}
						metodoSet.invoke(info, (metodoGet.invoke(lp) != null)?metodoGet.invoke(lp).toString():null);
				}
						
					
				}catch (Exception e) {
				//	System.out.println(metodoGet + "   " + metodoSet + "   " + nameToUpperCase + " Non trovato ");
				//	e.printStackTrace();
				}	
				
				
				
	 
	 
	 
  }
	 
	 info.getInformazioni(db);
	 info.getInformazioniControlli(db);
	 info.getInformazioniStatoControlli(db);
	 
	 return info;
	 
  }
  
	public void getInformazioniControlli(Connection db) {

		// super(db, idEventoPadre);

		PreparedStatement pst;
		try {
			pst = db
					.prepareStatement("select cu_aperti, cu_chiusi " +
			  				  "from ((select count(*) as cu_aperti from cu_operatori_commerciali where id_operatore_commerciale = ? and chiuso is null and trashed_date is null)) aperti," +
			  				  "(select count(*) as cu_chiusi from cu_operatori_commerciali where id_operatore_commerciale = ? and chiuso is not null and trashed_date is null) chiusi");
			pst.setInt(1, this.getId());
			pst.setInt(2, this.getId());
			ResultSet rs = DatabaseUtils.executeQuery(db, pst);
			
			if (rs.next()) {
				this.buildRecordControlli(rs);
			}

			rs.close();
			pst.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void buildRecordControlli(ResultSet rs) throws SQLException {

		this.nrControlliAperti = rs.getInt( "cu_aperti" );
		this.nrControlliChiusi = rs.getInt("cu_chiusi");
	}
  
  
	public void buildRecord(ResultSet rs) throws SQLException {
		this.codiceUvac = rs.getString("codice_uvac");
		this.mqDisponibili = rs.getInt("mq_disponibili");

		//this.categoriaRischio = rs.getInt( "categoria_rischio" );
		//this.dataProssimoControllo = rs.getTimestamp("data_prossimo_controllo");
	}
	
	
	public void getInformazioni(Connection db) {

		// super(db, idEventoPadre);

		PreparedStatement pst;
		try {
			pst = db
					.prepareStatement("Select * from opu_informazioni_importatore  where id_relazione_stabilimento_lp = ?");
			pst.setInt(1, this.getId());
			ResultSet rs = DatabaseUtils.executeQuery(db, pst);
			if (rs.next()) {
				this.buildRecord(rs);
			}
			
			rs.close();
			pst.close();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void buildRecordStatoControlli(ResultSet rs) throws SQLException {

		this.categoriaRischio = rs.getInt( "categoria_rischio" );
		this.dataProssimoControllo = rs.getTimestamp("data_prossimo_controllo");
	}
	
	public void getInformazioniStatoControlli(Connection db) {
	
		// super(db, idEventoPadre);

	PreparedStatement pst;
		try {
			pst = db
					.prepareStatement("Select * from opu_informazioni_controlli  where id_relazione_stabilimento_lp = ?");
			pst.setInt(1, this.getId());
			ResultSet rs = DatabaseUtils.executeQuery(db, pst);
			if (rs.next()) {
				this.buildRecordStatoControlli(rs);
			}
			
			rs.close();
			pst.close();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
