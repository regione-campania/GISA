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
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegato;
import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.beans.GenericBean;

import ext.aspcfs.modules.apicolture.actions.StabilimentoAction;

public class Delega extends GenericBean {
	
	  private final static Logger log = Logger.getLogger(ext.aspcfs.modules.apiari.base.Delega.class);

	  
	private int id ;
	private String codice_fiscale_delegato ;
	private int id_utente_access_ext_delegato ;
	private String  codice_fiscale_delegante ;
	private int id_soggetto_fisico_delegante ;
	private SoggettoFisico soggetto_fisico_delegante  ;
	private Timestamp data_assegnazione_delega ;
	private int entered_by ;
	private int modified_by ;
	private Timestamp entered ;
	private Timestamp modified ;
	private boolean  enabled  ;
	private String codiceAziendaRegionale ;
	private int idAsl ;
	private int idStatoAttivita = StabilimentoAction.API_STATO_INCOMPLETO ;
	private int idAttivita ;
	private User delegato ;
	
	private Timestamp dataCessazione ;
	private DocumentaleAllegato delegaAllegta  ;
	
	
	
	public DocumentaleAllegato getDelegaAllegta() {
		return delegaAllegta;
	}
	public void setDelegaAllegta(DocumentaleAllegato delegaAllegta) {
		this.delegaAllegta = delegaAllegta;
	}
	public Timestamp getDataCessazione() {
		return dataCessazione;
	}
	public void setDataCessazione(Timestamp dataCessazione) {
		this.dataCessazione = dataCessazione;
	}
	public User getDelegato() {
		return delegato;
	}
	public void setDelegato(User delegato) {
		this.delegato = delegato;
	}
	public int getIdStatoAttivita() {
		return idStatoAttivita;
	}
	public void setIdStatoAttivita(int idStatoAttivita) {
		this.idStatoAttivita = idStatoAttivita;
	}
	public int getIdAsl() {
		return idAsl;
	}
	public void setIdAsl(int idAsl) {
		this.idAsl = idAsl;
	}
	public int getIdAttivita() {
		return idAttivita;
	}
	public void setIdAttivita(int idAttivita) {
		this.idAttivita = idAttivita;
	}
	public SoggettoFisico getSoggetto_fisico_delegante() {
		return soggetto_fisico_delegante;
	}
	public void setSoggetto_fisico_delegante(SoggettoFisico soggetto_fisico_delegante) {
		this.soggetto_fisico_delegante = soggetto_fisico_delegante;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setId(String id) {
		if (id != null && !id.equals(""))
		this.id = Integer.parseInt(id);
	}
	
	public String getCodice_fiscale_delegato() {
		return codice_fiscale_delegato;
	}
	
	
	public void setCodice_fiscale_delegato(String codice_fiscale_delegato) {
		this.codice_fiscale_delegato = codice_fiscale_delegato;
	}
	public int getId_utente_access_ext_delegato() {
		return id_utente_access_ext_delegato;
	}
	public void setId_utente_access_ext_delegato(int id_utente_access_ext_delegato) {
		this.id_utente_access_ext_delegato = id_utente_access_ext_delegato;
	}
	
	public void setId_utente_access_ext_delegato(String id_utente_access_ext_delegato) {
		if (id_utente_access_ext_delegato != null && !id_utente_access_ext_delegato.equals(""))
			this.id_utente_access_ext_delegato = Integer.parseInt(id_utente_access_ext_delegato);	
		}
	
	public String getCodice_fiscale_delegante() {
		return codice_fiscale_delegante;
	}
	public void setCodice_fiscale_delegante(String codice_fiscale_delegante) {
		this.codice_fiscale_delegante = codice_fiscale_delegante;
	}
	public Timestamp getData_assegnazione_delega() {
		return data_assegnazione_delega;
	}
	public void setData_assegnazione_delega(Timestamp data_assegnazione_delega) {
		this.data_assegnazione_delega = data_assegnazione_delega;
	}
	
	public void setData_assegnazione_delega(String data_assegnazione_delega) throws ParseException {
		if (data_assegnazione_delega!= null && !"".equals(data_assegnazione_delega))
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			this.data_assegnazione_delega =new Timestamp(sdf.parse(data_assegnazione_delega).getTime());
			
		}
	}
	
	public int getEntered_by() {
		return entered_by;
	}
	public void setEntered_by(int entered_by) {
		this.entered_by = entered_by;
	}
	public int getModified_by() {
		return modified_by;
	}
	public void setModified_by(int modified_by) {
		this.modified_by = modified_by;
	}
	public Timestamp getEntered() {
		return entered;
	}
	public void setEntered(Timestamp entered) {
		this.entered = entered;
	}
	public Timestamp getModified() {
		return modified;
	}
	public void setModified(Timestamp modified) {
		this.modified = modified;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public int getId_soggetto_fisico_delegante() {
		return id_soggetto_fisico_delegante;
	}
	public void setId_soggetto_fisico_delegante(int id_soggetto_fisico_delegante) {
		this.id_soggetto_fisico_delegante = id_soggetto_fisico_delegante;
	}
	
	public void setId_soggetto_fisico_delegante(String id_soggetto_fisico_delegante) {
		if (id_soggetto_fisico_delegante!=null && !"".equals(id_soggetto_fisico_delegante))
			this.id_soggetto_fisico_delegante = Integer.parseInt(id_soggetto_fisico_delegante);
	}
	
	
	public boolean verificaEsistenzaDelega (Connection db)
	{
		
		try
		{
			PreparedStatement pst = db.prepareStatement("select * from apicoltura_deleghe where id_utente_access_ext_delegato = ? and codice_fiscale_delegante = ? and trashed_date is null ");
			pst.setInt(1, id_utente_access_ext_delegato);
			pst.setString(2, codice_fiscale_delegante);
			ResultSet rs = pst.executeQuery();
			if (rs.next())
				return true ;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public void revocaDelega(Connection db,int modifiedBy) throws SQLException
	{
		String insert = "update apicoltura_deleghe set enabled=false,modified=current_timestamp,modified_by=?,data_cessazione_delega=current_timestamp where id = ? ";
		PreparedStatement pst = db.prepareStatement(insert);
		pst.setInt(1, modifiedBy);
		pst.setInt(2, this.getId());
		pst.execute();
		
		
	}
	//table : apicoltura_deleghe
	public boolean insert(Connection db) throws SQLException
	{
		String insert = "update apicoltura_deleghe set enabled=false,modified=current_timestamp,modified_by=?,data_cessazione_delega=current_timestamp where id_soggetto_fisico_delegante = ? ;INSERT INTO apicoltura_deleghe (id,codice_fiscale_delegato,id_utente_access_ext_delegato,"
						+ "codice_fiscale_delegante,id_soggetto_fisico_delegante,data_assegnazione_delega,enabled,entered_by,modified_by,entered,modified,id_asl)"
						+ "values(?,?,?,?,?,?,true,?,?,current_timestamp,null::timestamp,?)";
		
		try
		{	
			int i = 0 ;
			
			id = DatabaseUtils.getNextInt(db,  "apicoltura_deleghe", "id", 2);
			
			PreparedStatement pst = db.prepareStatement(insert);
			pst.setInt(++i, modified_by);
			pst.setInt(++i, id_soggetto_fisico_delegante);


			pst.setInt(++i, id);

			pst.setString(++i, codice_fiscale_delegato);
			pst.setInt(++i, id_utente_access_ext_delegato);
			pst.setString(++i, codice_fiscale_delegante);
			pst.setInt(++i, id_soggetto_fisico_delegante);
			pst.setTimestamp(++i, data_assegnazione_delega);
			pst.setInt(++i, entered_by);
			pst.setInt(++i, modified_by);
			pst.setInt(++i, idAsl);
			pst.execute();
		
		
		}
		catch(SQLException e)
		{
			log.error("####GESTIONE DELEGHE APICOLTURA## ERRORNE ININSERIMENTO "+e.getMessage());
			return false ; 
		
	}
		return true ;
	}
	
	public boolean delete(Connection db , int id)
	{
		String select = "UPDATE apicoltura_deleghe set enabled = false,modified_by=?,modified=current_timestamp where id = ?";
		
		try
		{	
			int i = 0 ;
			PreparedStatement pst = db.prepareStatement(select);
			pst.setInt(++i, modified_by);
			pst.setInt(++i, id);
			pst.execute();
			
		}
		catch(SQLException e)
		{
			log.error("####GESTIONE DELEGHE APICOLTURA## ERRORE DELETE "+e.getMessage());
			return false ; 
			
		}
		return true ;
	}
	
	public void buildRecord(ResultSet rs) throws SQLException
	{
		id = rs.getInt("id");
		codice_fiscale_delegato = rs.getString("codice_fiscale_delegato");
		enabled = rs.getBoolean("enabled");
		id_utente_access_ext_delegato = rs.getInt("id_utente_access_ext_delegato");
		codice_fiscale_delegante = rs.getString("codice_fiscale_delegante");
		id_soggetto_fisico_delegante = rs.getInt("id_soggetto_fisico_delegante");
		data_assegnazione_delega=rs.getTimestamp("data_assegnazione_delega");
		entered_by = rs.getInt("entered_by");
		modified_by = rs.getInt("modified_by");
		entered=rs.getTimestamp("entered");
		modified=rs.getTimestamp("modified");
		codiceAziendaRegionale=rs.getString("codice_azienda_regionale");
		idAttivita=rs.getInt("id_attivita");
		idAsl = rs.getInt("id_asl");
		dataCessazione = rs.getTimestamp("data_cessazione_delega");
		idStatoAttivita = rs.getInt("stato_impresa");
		if (idStatoAttivita<=0)
			idStatoAttivita = StabilimentoAction.API_STATO_INCOMPLETO;
		
	}
	
	public void queryRecord(Connection db,int id)
	{
		String select = "select del.*,imp.codice_azienda_regionale,imp.id as id_attivita,imp.stato as stato_impresa from apicoltura_deleghe del "
				+ " left join apicoltura_imprese imp on imp.codice_fiscale_impresa ilike  del.codice_fiscale_delegante "
				+ " where 1=1 and imp.trashed_date is null and del.trashed_date is null and del.id = ? ";
		
		try
		{	
			int i = 0 ;
			PreparedStatement pst = db.prepareStatement(select);
			pst.setInt(++i, id);
			ResultSet rs = pst.executeQuery();
			if (rs.next())
			{
				buildRecord(rs);
				soggetto_fisico_delegante =new SoggettoFisico(db,id_soggetto_fisico_delegante);
				
			}
		
		}
		catch(SQLException e)
		{
			log.error("####GESTIONE DELEGHE APICOLTURA## ERRORE QUERY RECORD "+e.getMessage());
			
		}
	}
	public String getCodiceAziendaRegionale() {
		return codiceAziendaRegionale;
	}
	public void setCodiceAziendaRegionale(String codiceAziendaRegionale) {
		this.codiceAziendaRegionale = codiceAziendaRegionale;
	}
	

}
