/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package ext.aspcfs.modules.apiari.base;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.darkhorseventures.framework.beans.GenericBean;

public class StabilimentoVariazioneDetentore extends GenericBean {
	
	private Timestamp dataVariazione ;
	private int idSoggettoFisico ;
	
	private SoggettoFisico soggettoFisico ;

	public Timestamp getDataVariazione() {
		return dataVariazione;
	}

	public void setDataVariazione(Timestamp dataVariazione) {
		this.dataVariazione = dataVariazione;
	}

	public int getIdSoggettoFisico() {
		return idSoggettoFisico;
	}

	public void setIdSoggettoFisico(int idSoggettoFisico) {
		this.idSoggettoFisico = idSoggettoFisico;
	}

	public SoggettoFisico getSoggettoFisico() {
		return soggettoFisico;
	}

	public void setSoggettoFisico(SoggettoFisico soggettoFisico) {
		this.soggettoFisico = soggettoFisico;
	}
	
	public void buildRecord(Connection db , ResultSet rs) throws SQLException
	{
		idSoggettoFisico = rs.getInt("id_soggetto_fisico_nuovo_detentore");
		dataVariazione = rs.getTimestamp("data_assegnazione_detentore");
		
		soggettoFisico=new SoggettoFisico(db,idSoggettoFisico);
	}
	

}
