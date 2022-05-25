/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.variazionestati.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.aspcfs.modules.vigilanza.base.Ticket;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.RicercheAnagraficheTab;
import org.aspcfs.utils.DateUtils;

public class LineaVariazione {

	private LineaProduttiva linea = null;
	private ArrayList<Operazione> listaOperazioni = null;
	private ArrayList<Integer> listaCU = null;
	
	private int idRelStabLp = -1;
	private int idOperazione = -1;
	private int idCu = -1;
	private Timestamp dataVariazione = null;
	private int idStato = -1;
	private int idUtente = -1;
	private int idStabilimento = -1;
	private int idOperazioneOrigine = -1;
	private String errore = "";
	
	public LineaProduttiva getLinea() {
		return linea;
	}
	public void setLinea(LineaProduttiva linea) {
		this.linea = linea;
	}
	public ArrayList<Operazione> getListaOperazioni() {
		return listaOperazioni;
	}
	public void setListaOperazioni(ArrayList<Operazione> listaOperazioni) {
		this.listaOperazioni = listaOperazioni;
	}
	public void setListaOperazioni(Connection db)  {
		
		VariazioneWk wk = new VariazioneWk();
		wk.setIdStato(linea.getStato());
		ArrayList<Operazione> listaOperazioni;
		listaOperazioni = wk.getListaOperazioniDaStato(db);
		this.listaOperazioni = listaOperazioni;
	}
	public ArrayList<Integer> getListaCU() {
		return listaCU;
	}
	public void setListaCU(ArrayList<Integer> listaCU) {
		this.listaCU = listaCU;
	}
	public int getIdRelStabLp() {
		return idRelStabLp;
	}
	public void setIdRelStabLp(int idRelStabLp) {
		this.idRelStabLp = idRelStabLp;
	}
	public void setIdRelStabLp(String idRelStabLp) {
		try {this.idRelStabLp = Integer.parseInt(idRelStabLp); } catch (Exception e){}
	}
	public int getIdOperazione() {
		return idOperazione;
	}
	public void setIdOperazione(int idOperazione) {
		this.idOperazione = idOperazione;
	}
	public void setIdOperazione(String idOperazione) {
		try {this.idOperazione = Integer.parseInt(idOperazione); } catch (Exception e){}
	}
	public int getIdCu() {
		return idCu;
	}
	public void setIdCu(int idCu) {
		this.idCu = idCu;
	}
	public void setIdCu(String idCu) {
		try {this.idCu = Integer.parseInt(idCu); } catch (Exception e){}
	}
	public int getIdStato() {
		return idStato;
	}
	public void setIdStato(int idStato) {
		this.idStato = idStato;
	}
	public void setIdStato(String idStato) {
		try {this.idStato = Integer.parseInt(idStato); } catch (Exception e){}
	}
	public Timestamp getDataVariazione() {
		return dataVariazione;
	}
	public void setDataVariazione(Timestamp dataVariazione) {
		this.dataVariazione = dataVariazione;
	}
	public void setDataVariazione(String dataVariazione) {
		this.dataVariazione =   DateUtils.parseDateStringNew(dataVariazione, "dd/MM/yyyy");
	}
	
	public int getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}
	
	public int getIdStabilimento() {
		return idStabilimento;
	}
	public void setIdStabilimento(int idStabilimento) {
		this.idStabilimento = idStabilimento;
	}
	public boolean controlloCoerenzaDate(Connection db) throws SQLException{
	boolean ok = true;
	if (idCu == -1)
		return ok;
	
	Ticket t = new Ticket(db, idCu);
	if (t.getId()>0)
		if(dataVariazione.before(t.getAssignedDate())){
			ok = false;
			setErrore(getErrore() + "Impossibile aggiornare lo stato per la linea associata al controllo "+idCu+". Data di variazione indicata ("+dataVariazione+") risulta antecedente alla data del CU ("+t.getAssignedDate()+"). ");
		}
	
	return ok;
	
	}
	
	public void aggiornaStato(Connection db){
		
		Ticket t;
		try {
			if (idCu == -1)
				t = new Ticket();
			else
				t = new Ticket(db, idCu);
		
			VariazioneWk wk = new VariazioneWk();
			wk.setIdStato(idStato);
			wk.setIdOperazione(idOperazione);
			int prossimoStato = wk.getNuovoStato(db);
			salvaStorico(db, prossimoStato, t);
			
			
			if (idStabilimento>0) {
				String sql = "";
				sql = "update opu_relazione_stabilimento_linee_produttive set stato = ? , modified = now(), modifiedby = ? where id = ?";
				PreparedStatement pst= db.prepareStatement(sql);
				pst.setInt(1, prossimoStato);
				pst.setInt(2, idUtente);
				pst.setInt(3, idRelStabLp);
				pst.executeUpdate();
				
				sql = "select * from public_functions.opu_aggiorna_stato_stabilimento(?)";
				pst= db.prepareStatement(sql);
				pst.setInt(1, idStabilimento);
				ResultSet rs = pst.executeQuery();
				
			}
			RicercheAnagraficheTab.inserOpu(db, idStabilimento);
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	private void salvaStorico (Connection db, int prossimoStato, Ticket t){
		String sql ="insert into variazione_stato_operazioni_storico (id_utente, data, id_operazione, id_rel_stab_lp, id_stato_precedente, id_stato_nuovo, data_variazione, id_cu, id_stato_cu, id_stabilimento)"
				+ "values (?, now(), ?, ?, ?, ?, ?, ?, ?, ?);";
		PreparedStatement pst;
		try {
			pst = db.prepareStatement(sql);
		
		int i = 0;
		pst.setInt(++i, idUtente);
		pst.setInt(++i, idOperazione);
		pst.setInt(++i, idRelStabLp);
		pst.setInt(++i, idStato);
		pst.setInt(++i, prossimoStato);
		pst.setTimestamp(++i, dataVariazione);
		pst.setInt(++i, idCu);
		pst.setInt(++i, t.getStatusId());
		pst.setInt(++i, idStabilimento);
		pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getErrore() {
		return errore;
	}
	public void setErrore(String errore) {
		this.errore = errore;
	}
	public int getIdOperazioneOrigine() {
		return idOperazioneOrigine;
	}
	public void setIdOperazioneOrigine(int idOperazioneOrigine) {
		this.idOperazioneOrigine = idOperazioneOrigine;
	}
	
	
	public void setOperazioneOrigine(Connection db) throws SQLException{
		int idOperazione = -1;
		String sql ="select id_operazione from variazione_stato_operazioni_storico where id_rel_stab_lp = ? and id_stato_nuovo = ? and trashed_date is null order by data desc limit 1";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setInt(1, linea.getId_rel_stab_lp());
		pst.setInt(2, linea.getStato());
		ResultSet rs = pst.executeQuery();
		while (rs.next())
			idOperazione = rs.getInt("id_operazione");
		this.idOperazioneOrigine=idOperazione;
	}
}
