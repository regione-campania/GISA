/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcf.modules.controlliufficiali.base;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import org.aspcfs.modules.lineeattivita.base.LineeAttivita;
import org.aspcfs.modules.macellazioni.base.TipoRicerca;
import org.aspcfs.modules.soa.base.LineaAttivitaSoa;
import org.aspcfs.modules.vigilanza.base.Ticket;
import org.aspcfs.utils.DatabaseUtils;


public class ModTamponiMAcellazione {

	
	
	String tipoModulo;
	int id ;
	String descrizioneTipoCarcassa ;
	String descrizioneTipoRicerca ; 
	String descrizionePianoMonitoraggio ;
	String barcodePrelievo = "";
	private ArrayList<TipoRicerca> listaTipoRicerca = new ArrayList<TipoRicerca>();
	int idTipoCarcassa ;
	int idPianoMonitoraggio ;
	public String getBarcodePrelievo() {
		return barcodePrelievo;
	}



	Timestamp dataMAcellazione ;
	int sessioneMacellazione ; 
	int idMacello ;
	boolean metodo ;
	
	ArrayList<String> matricoleCarcasse =new  ArrayList<String>();
	
	public void setBarcodePrelievo(String barcode) {
		// TODO Auto-generated method stub
		this.barcodePrelievo = barcode;
	}
	
	public ArrayList<TipoRicerca> getListaTipoRicerca() {
		return listaTipoRicerca;
	}



	public void setListaTipoRicerca(ArrayList<TipoRicerca> listaTipoRicerca) {
		this.listaTipoRicerca = listaTipoRicerca;
	}



	public boolean isMetodo() {
		return metodo;
	}



	public void setMetodo(boolean metodo) {
		this.metodo = metodo;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getDescrizioneTipoCarcassa() {
		return descrizioneTipoCarcassa;
	}



	public void setDescrizioneTipoCarcassa(String descrizioneTipoCarcassa) {
		this.descrizioneTipoCarcassa = descrizioneTipoCarcassa;
	}



	public String getDescrizioneTipoRicerca() {
		return descrizioneTipoRicerca;
	}



	public void setDescrizioneTipoRicerca(String descrizioneTipoRicerca) {
		this.descrizioneTipoRicerca = descrizioneTipoRicerca;
	}



	public String getDescrizionePianoMonitoraggio() {
		return descrizionePianoMonitoraggio;
	}



	public void setDescrizionePianoMonitoraggio(String descrizionePianoMonitoraggio) {
		this.descrizionePianoMonitoraggio = descrizionePianoMonitoraggio;
	}





	public int getIdTipoCarcassa() {
		return idTipoCarcassa;
	}



	public void setIdTipoCarcassa(int idTipoCarcassa) {
		this.idTipoCarcassa = idTipoCarcassa;
	}



	public int getIdPianoMonitoraggio() {
		return idPianoMonitoraggio;
	}



	public void setIdPianoMonitoraggio(int idPianoMonitoraggio) {
		this.idPianoMonitoraggio = idPianoMonitoraggio;
	}



	public Timestamp getDataMAcellazione() {
		return dataMAcellazione;
	}



	public void setDataMAcellazione(Timestamp dataMAcellazione) {
		this.dataMAcellazione = dataMAcellazione;
	}



	public int getSessioneMacellazione() {
		return sessioneMacellazione;
	}



	public void setSessioneMacellazione(int sessioneMacellazione) {
		this.sessioneMacellazione = sessioneMacellazione;
	}



	public int getIdMacello() {
		return idMacello;
	}



	public void setIdMacello(int idMacello) {
		this.idMacello = idMacello;
	}



	public ArrayList<String> getMatricoleCarcasse() {
		return matricoleCarcasse;
	}



	public void setMatricoleCarcasse(ArrayList<String> matricoleCarcasse) {
		this.matricoleCarcasse = matricoleCarcasse;
	}



	public String getTipoModulo() {
		return tipoModulo;
	}



	public ModTamponiMAcellazione(){}
	
	

	public void setTipoModulo(String tipoModulo) {
		this.tipoModulo = tipoModulo;
	}


	
	public ModTamponiMAcellazione(ResultSet rs,Connection db) throws SQLException{
		
		if(rs.next()) {	
			 id = rs.getInt("id");
			 descrizioneTipoCarcassa = rs.getString("descrizioneTipoCarcassa") ;

			 descrizionePianoMonitoraggio  = rs.getString("descrizionePianoMonitoraggio") ;
			 
			 idTipoCarcassa = rs.getInt("idTipoCarcassa");;
			 idPianoMonitoraggio = rs.getInt("idPianoMonitoraggio");;
			 dataMAcellazione = rs.getTimestamp("dataMAcellazione");;
			 sessioneMacellazione = rs.getInt("sessioneMacellazione"); 
			 idMacello = rs.getInt("idMacello");;
			 metodo = rs.getBoolean("distruttivo");
			 PreparedStatement pst = db.prepareStatement("select m_capi.cd_matricola from m_vpm_capi_tamponi join m_capi on id_m_capo = m_capi.id " +
			 		"where m_vpm_capi_tamponi.id_m_vpm_tamponi = ? and m_vpm_capi_tamponi.trashed_date is null ");
			 
			 pst.setInt(1, id);
			 ResultSet res =  pst.executeQuery();
			 while (res.next())
				 matricoleCarcasse.add(res.getString("cd_matricola"));
			 
			 rs = null ;
				pst = db.prepareStatement("select an.*,l.description as descrizione_ricerca from m_vpm_tamponi_analiti an join lookup_ricerca_tamponi_macelli l on l.code =an.id_tipo_ricerca where id_tampone = ?");
				pst.setInt(1, id);
				rs = pst.executeQuery() ;
				while (rs.next())
				{
					TipoRicerca rr = new TipoRicerca();
					rr.setId(rs.getInt("id_tipo_ricerca"));
					rr.setDescrizione(rs.getString("descrizione_ricerca"));
					listaTipoRicerca.add(rr);
				}
				
			 
			 
		
		}
		
	}
		
  public String  getBarcode(Connection db, String orgId, int idCU , int id_tampone) {
		
	    StringBuffer sql = new StringBuffer();
		//Identificativo prelievo suffisso
		String idpvsuf = "vpsac";
		String codicePrelievo = "";
		
		try{
					
				if (id_tampone != 0){
					 PreparedStatement check_barcode = db.prepareStatement("select * from barcode_osa where org_id = ? and id_campione = ? and barcode ilike ?");
					 check_barcode.setInt(1,Integer.parseInt(orgId));
					 check_barcode.setString(2,Integer.toString(id_tampone));
					 check_barcode.setString(3,"%"+idpvsuf);
					 ResultSet rs = check_barcode.executeQuery();
					 while(rs.next()){	 
						 codicePrelievo = rs.getString("barcode");
					 }

					 
					 //non c'e' barcode
					 if(codicePrelievo.equals("") || codicePrelievo==null){
						 	int sequence = -1, j=0;
							sequence = DatabaseUtils.getNextSeqTipo(db, "barcode_osa_serial_id_seq");
							PreparedStatement pst = null;
							//inserisco il tampone dei macelli in barcode_osa
							codicePrelievo = ""+id_tampone+sequence+idpvsuf;	
							sql.append("INSERT INTO barcode_osa(serial_id, org_id,id_campione,barcode,ticket_id) VALUES(?,?,?,?,?)"); 
							pst=db.prepareStatement(sql.toString());
							pst.setInt(++j,sequence);
							pst.setInt(++j,Integer.parseInt(orgId));
							pst.setString(++j,Integer.toString(id_tampone));
							pst.setString(++j, codicePrelievo);
							pst.setInt(++j, idCU);		
							pst.execute();
							pst.close();
					 }
					 rs.close();
					 check_barcode.close();
				}
				
		}catch (Exception e) {
			// TODO: handle exception
			
		}
				
		return codicePrelievo;
		
				          
	}
	
	public boolean contieneTipoRiceca(int tipoRicerca)
	{
		
		boolean contenuto = false ;
		for (TipoRicerca t : listaTipoRicerca)
		{
			if (t.getId()==tipoRicerca)
			{
				contenuto = true;
				break ;
			}
		}
		
		return contenuto ;
	}

	public void getFieldsModuli(Connection db, String orgId, int idControllo, Organization org) {

		try {
			// SETFIELD
			if (org.getData_referto() != null) {
				org.setAnnoReferto(org.getData_referto().substring(0, 4));
				org.setGiornoReferto(org.getData_referto().substring(8, 11));
				org.setMeseReferto(this.getMeseFromData(org.getData_referto()));		
			}
			
		} catch (Exception errorMessage) {
			errorMessage.printStackTrace();
		} 
	}

	
	
	public String getMeseFromData(String data_referto){
		String mese = data_referto.substring(5,7);
		
		switch (Integer.parseInt(mese)) {
			case 01 : mese = "Gennaio"    ;  break;
			case 02 : mese = "Febbraio"   ;  break;
			case 03 : mese = "Marzo"      ;  break;
			case 04 : mese = "Aprile"     ;  break;
			case 05 : mese = "Maggio"     ;  break;
			case 06 : mese = "Giugno"     ;  break;
			case 07 : mese = "Luglio"     ;  break;
			//case 08 : mese = "Agosto"     ;  break;
			//case 09 : mese = "Settembre"  ;  break;
			case 10 : mese = "Ottobre"    ;  break;
			case 11 : mese = "Novembre"   ;  break;
			case 12 : mese = "Dicembre"   ;  break;
		}
		if (mese.equals("08")){
			mese = "Agosto"; 
		}
		if (mese.equals("09")){
			mese = "Settembre";
		}
		
		return mese;
	}



	
	


}

