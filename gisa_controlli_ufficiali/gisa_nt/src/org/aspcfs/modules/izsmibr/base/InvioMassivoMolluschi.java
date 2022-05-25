/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.izsmibr.base;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.aspcfs.modules.gestioneDocumenti.actions.GestioneAllegatiInvii;
import org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegatoInvii;
import org.aspcfs.modules.izsmibr.util.ObjectFactory;
import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

public class InvioMassivoMolluschi extends GenericBean {
	private int id ;
	private Timestamp data;
	private int inviato_da ;
	private String erroreElaborazione ;
	
	private DocumentaleAllegatoInvii docUplodato ;
	
	
	
	public DocumentaleAllegatoInvii getDocUplodato() {
		return docUplodato;
	}
	public void setDocUplodato(DocumentaleAllegatoInvii docUplodato) {
		this.docUplodato = docUplodato;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Timestamp getData() {
		return data;
	}
	public void setData(Timestamp data) {
		this.data = data;
	}
	public int getInviato_da() {
		return inviato_da;
	}
	public void setInviato_da(int inviato_da) {
		this.inviato_da = inviato_da;
	}
	
	public String getErroreElaborazione()
	{
		return this.erroreElaborazione;
	}
	
	public void setErroreElaborazione(String erroreElaborazione)
	{
		this.erroreElaborazione = erroreElaborazione;
	}
	
	public void insert(Connection db) throws SQLException
	{
		
		try
		{
			
			this.id = DatabaseUtils.getNextInt(db, "invio_massivo_molluschi", "id", 1);
			if(id==0)
				id=1;
			PreparedStatement pst = db.prepareStatement("INSERT INTO invio_massivo_molluschi (id,data,inviato_da,stato_esecuzione,time_start) values (?,?,?,0,current_timestamp)");
			pst.setInt(1, id);
			pst.setTimestamp(2, data);
			pst.setInt(3, inviato_da);
			pst.execute();
			
		}catch(SQLException e)
		{
			throw e ;
		}
	}
	
	
	public void setStatoEsecuzione(Connection db) throws SQLException
	{
		
		try
		{
			

			PreparedStatement pst = db.prepareStatement("update invio_massivo_molluschi set stato_esecuzione=1,time_end =current_timestamp where id = ?");
			pst.setInt(1, id);
			pst.execute();
			
		}catch(SQLException e)
		{
			throw e ;
		}
	}
	
	
	public ArrayList<CampioneMolluschi> getAllRecords(Connection db) throws ParseException
	{
		ArrayList<CampioneMolluschi> listaRecordInviati = new ArrayList<CampioneMolluschi>();
		
		try
		{
			ObjectFactory createObj = new ObjectFactory();
			PreparedStatement pst = db.prepareStatement("select * from import_ca_molluschi where id_invio_massivo_molluschi=?");
			pst.setInt(1, this.id);
			ResultSet rs = pst.executeQuery();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			while (rs.next())
			{
				
				CampioneMolluschi recordGisa = new CampioneMolluschi();
				recordGisa.setData_invio_bdn(rs.getTimestamp("data_invio_bdn"));
				recordGisa.setTracciato_record_richiesta(rs.getString("tracciato_record_richiesta"));
				recordGisa.setTracciato_record_risposta(rs.getString("tracciato_record_risposta"));
				recordGisa.setErrore(rs.getString("errore"));
				recordGisa.setEsito_invio(rs.getString("esito_invio"));
				recordGisa.setFoodexCodice(rs.getString("foodexcodice"));
		
				recordGisa.setPianoCodice(rs.getString("pianoCodice"));
				recordGisa.setNumeroSchedaPrelievo(rs.getString("numeroSchedaPrelievo"));
				recordGisa.setDataPrel(rs.getString("dataPrel"));
				recordGisa.setLuogoPrelievoCodice(rs.getString("luogoPrelievoCodice"));
				recordGisa.setMetodoCampionamentoCodice(rs.getString("metodoCampionamentoCodice"));
				recordGisa.setMotivoCodice(rs.getString("motivoCodice"));
				recordGisa.setPrelNome(rs.getString("prelNome"));
				recordGisa.setPrelCognome(rs.getString("prelCognome"));
				recordGisa.setPrelCodFiscale(rs.getString("prelCodFiscale"));
				recordGisa.setSitoCodice(rs.getString("sitoCodice"));
				recordGisa.setComuneCodiceIstatParziale(rs.getString("comuneCodiceIstatParziale"));
				recordGisa.setSiglaProvincia(rs.getString("siglaProvincia"));
				recordGisa.setLaboratorioCodice(rs.getString("laboratorioCodice"));
				recordGisa.setLatitudine(rs.getString("latitudine"));
				recordGisa.setLongitudine(rs.getString("longitudine"));
				recordGisa.setCodiceContaminante(rs.getString("codiceContaminante"));
				recordGisa.setProgressivoCampione(rs.getString("progressivoCampione"));
				recordGisa.setProfFondale(rs.getString("ProfFondale"));
				recordGisa.setClassificazioneDellaZonaDiMareCe8542004(rs.getString("classificazioneDellaZonaDiMareCe8542004"));
				
				
				
				
				listaRecordInviati.add(recordGisa);
				
			}
		}
		catch(SQLException e)
		{
			
		}
		return listaRecordInviati ;
	}
	
	public ArrayList<InvioMassivoMolluschi> getListaInviMolluschi(Connection db,ActionContext context) throws SQLException
	{
		ArrayList<InvioMassivoMolluschi> listaInvii = new ArrayList<InvioMassivoMolluschi>();
		
		try
		{
		//Prendo sia quelli che sono stati eseguiti che quelli che sono andati in errore per dati errati nel file
		PreparedStatement pst = db.prepareStatement("SELECT * FROM invio_massivo_molluschi where stato_esecuzione in(1,2) order by data desc");
		
		ResultSet rs = pst.executeQuery();
		while(rs.next())
		{
			InvioMassivoMolluschi invio = new InvioMassivoMolluschi();
			invio.buildRecord(rs);
			
			GestioneAllegatiInvii gestione = new GestioneAllegatiInvii();
			gestione.setIdInvioMolluschi(invio.getId());
			
			try {
				if (gestione.listaAllegati(context).size()>0)
					invio.setDocUplodato((DocumentaleAllegatoInvii)gestione.listaAllegati(context).get(0));
				
				listaInvii.add(invio);
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
			
			
			
		}
		
		}catch(SQLException e)
		{
			throw e ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listaInvii;
	}
	
	public void queryRecord(Connection db,int id) throws SQLException
	{
		
		try
		{
			
			PreparedStatement pst = db.prepareStatement("SELECT * FROM invio_massivo_molluschi WHERE id =?");
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			if(rs.next())
				buildRecord(rs);
			
		}catch(SQLException e)
		{
			throw e ;
		}
	}
	public void buildRecord(ResultSet rs) throws SQLException
	{
		id = rs.getInt("id");
		data = rs.getTimestamp("data");
		inviato_da = rs.getInt("inviato_da");
		erroreElaborazione = rs.getString("errore_elaborazione");
	}

}
