/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package ext.aspcfs.modules.apiari.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.beans.GenericBean;

public class OperatoreFuoriRegione extends GenericBean {
	
	
	private int id ;
	private boolean inNazione ;
	private String codiceAzienda ;
	private String nominativoProprietario ;
	private String cfProprietario ;
	private String comune;
	private int idPaese ;
	
	public OperatoreFuoriRegione() {}
	public OperatoreFuoriRegione (Connection db,int id)
	{
		queryRecord(db, id);
	}
	
	
	public int getIdPaese() {
		return idPaese;
	}
	public void setIdPaese(int idPaese) {
		this.idPaese = idPaese;
	}
	public void setIdPaese(String idPaese) {
		if (idPaese!=null && ! idPaese.equals(""))
		this.idPaese = Integer.parseInt(idPaese);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isInNazione() {
		return inNazione;
	}
	public void setInNazione(boolean inNazione) {
		this.inNazione = inNazione;
	}
	
	public void setInNazione(String in_nazione) {
		if (in_nazione!= null && in_nazione.equals("2"))
			this.inNazione = true;
		else
			this.inNazione=false;
	}
	
	public String getCodiceAzienda() {
		return codiceAzienda;
	}
	public void setCodiceAzienda(String codiceAzienda) {
		this.codiceAzienda = codiceAzienda;
	}
	public String getNominativoProprietario() {
		return nominativoProprietario;
	}
	public void setNominativoProprietario(String nominativoProprietario) {
		this.nominativoProprietario = nominativoProprietario;
	}
	public String getCfProprietario() {
		return cfProprietario;
	}
	public void setCfProprietario(String cfProprietario) {
		this.cfProprietario = cfProprietario;
	}
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = comune;
	}
	
	public void insert (Connection db)
	{
		PreparedStatement pst = null ;
		String insert = "INSERT INTO apicoltura_aziende_fuori_regione (id,in_nazione,codice_azienda,nominativo_proprietario,cf_proprietario,comune,id_nazione) values (?,?,?,?,?,?,?)" ;
		try
		{
			id = DatabaseUtils.getNextInt(db, "apicoltura_aziende_fuori_regione", "id", 1)+1;
			int i=0 ;
			pst  = db.prepareStatement(insert);
			pst.setInt(++i, id);
			pst.setBoolean(++i, inNazione);
			pst.setString(++i, codiceAzienda);
			pst.setString(++i, nominativoProprietario);
			pst.setString(++i, cfProprietario);
			pst.setString(++i, comune);
			pst.setInt(++i, idPaese);

			pst.execute();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	
	
	public void queryRecord (Connection db,int id)
	{
		PreparedStatement pst = null ;
		ResultSet rs = null ;
		String select = "SELECT * FROM  apicoltura_aziende_fuori_regione WHERE id = ? " ;
		try
		{

			pst  = db.prepareStatement(select);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if (rs.next())
				buildRecord(rs);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void buildRecord(ResultSet rs) throws SQLException
	{
		
		this.setId(rs.getInt("id"));
		this.setInNazione(rs.getBoolean("in_nazione"));
		this.setCodiceAzienda(rs.getString("codice_azienda"));
		this.setNominativoProprietario(rs.getString("nominativo_proprietario"));
		this.setCfProprietario(rs.getString("cf_proprietario"));
		this.setComune(rs.getString("comune"));
		this.setIdPaese(rs.getInt("id_nazione"));
				
	}
	
	
	

}
