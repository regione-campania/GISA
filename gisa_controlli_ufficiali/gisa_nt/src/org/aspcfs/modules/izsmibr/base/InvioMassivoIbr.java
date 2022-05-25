/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.izsmibr.base;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.aspcfs.modules.gestioneDocumenti.actions.GestioneAllegatiInvii;
import org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegatoInvii;
import org.aspcfs.modules.izsmibr.base.DsESITOIBRIUS.PARAMETERSLIST;
import org.aspcfs.modules.izsmibr.util.ObjectFactory;
import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

public class InvioMassivoIbr extends GenericBean {
	private int id ;
	private Timestamp data;
	private int inviato_da ;
	
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
	
	public void insert(Connection db) throws SQLException
	{
		
		try
		{
			
			this.id = DatabaseUtils.getNextInt(db, "invio_massivo_ibr", "id", 1);
			PreparedStatement pst = db.prepareStatement("INSERT INTO invio_massivo_ibr (id,data,inviato_da) values (?,?,?)");
			pst.setInt(1, id);
			pst.setTimestamp(2, data);
			pst.setInt(3, inviato_da);
			pst.execute();
			
		}catch(SQLException e)
		{
			throw e ;
		}
	}
	
	
	public ArrayList<GisaDsEsitoIBRA> getAllRecords(Connection db) throws ParseException
	{
		ArrayList<GisaDsEsitoIBRA> listaRecordInviati = new ArrayList<GisaDsEsitoIBRA>();
		
		try
		{
			ObjectFactory createObj = new ObjectFactory();
			PreparedStatement pst = db.prepareStatement("select * from import_ibr where id_invio_massivo_ibr=?");
			pst.setInt(1, this.id);
			ResultSet rs = pst.executeQuery();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			while (rs.next())
			{
				
				GisaDsEsitoIBRA recordGisa = new GisaDsEsitoIBRA();
				recordGisa.setData_invio_bdn(rs.getTimestamp("data_invio_bdn"));
				recordGisa.setTracciato_record_richiesta(rs.getString("tracciato_record_richiesta"));
				recordGisa.setTracciato_record_risposta(rs.getString("tracciato_record_risposta"));
				recordGisa.setErrore(rs.getString("errore"));
				recordGisa.setEsito_invio(rs.getString("esito_invio"));
				
				DsESITOIBRIUS record = createObj.createDsESITOIBRIUS();
				PARAMETERSLIST p = new PARAMETERSLIST();
				p.setPEIBRID(rs.getBigDecimal("p_eibr_id"));
				p.setPANNOACCETTAZIONE(new BigDecimal(rs.getString("P_ANNO_ACCETTAZIONE")));
				p.setPANNOCAMPAGNA(new BigDecimal(rs.getString("P_ANNO_CAMPAGNA")));
				p.setPCODICEAZIENDA(rs.getString("P_CODICE_AZIENDA"));
				p.setPCODICECAPO(rs.getString("P_CODICE_CAPO"));
				p.setPCODICEISTITUTO(rs.getString("P_CODICE_ISTITUTO"));
				p.setPCODICEPRELIEVO(rs.getString("P_CODICE_PRELIEVO"));
				p.setPCODICESEDEDIAGNOSTICA(rs.getString("P_CODICE_SEDE_DIAGNOSTICA"));		
				p.setPDATAESITO(rs.getString("P_DATA_ESITO"));
				p.setPDATAPRELIEVO(rs.getString("P_DATA_PRELIEVO"));
				p.setPESITOQUALITATIVO(rs.getString("P_ESITO_QUALITATIVO"));
				p.setPIDFISCALEPROPRIETARIO(rs.getString("P_ID_FISCALE_PROPRIETARIO"));
				p.setPNUMEROACCETTAZIONE(rs.getString("P_NUMERO_ACCETTAZIONE"));
				p.setPSPECIEALLEVATA(rs.getString("P_SPECIE_ALLEVATA"));
				if(rs.getString("P_EIBR_ID")!=null)
					p.setPEIBRID(new BigDecimal(rs.getString("P_EIBR_ID")));
				
				record.getPARAMETERSLIST().add(p);
				recordGisa.setRecord(record);
				listaRecordInviati.add(recordGisa);
				
			}
		}
		catch(SQLException e)
		{
			
		}
		return listaRecordInviati ;
	}
	
	public ArrayList<InvioMassivoIbr> getListaInviiIbr(Connection db,ActionContext context) throws SQLException
	{
		ArrayList<InvioMassivoIbr> listaInvii = new ArrayList<InvioMassivoIbr>();
		
		try
		{
		PreparedStatement pst = db.prepareStatement("SELECT * FROM invio_massivo_ibr order by data desc");
		
		ResultSet rs = pst.executeQuery();
		while(rs.next())
		{
			InvioMassivoIbr invio = new InvioMassivoIbr();
			invio.buildRecord(rs);
			GestioneAllegatiInvii gestione = new GestioneAllegatiInvii();
			gestione.setIdInvio(invio.getId());
			try {
				if (gestione.listaAllegati(context).size()>0)
					invio.setDocUplodato((DocumentaleAllegatoInvii)gestione.listaAllegati(context).get(0));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			listaInvii.add(invio);
			
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
			
			PreparedStatement pst = db.prepareStatement("SELECT * FROM invio_massivo_ibr WHERE id =?");
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
	}

}
