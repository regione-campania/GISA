/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.registrazioniAnimali.base;

import java.net.UnknownHostException;
import java.sql.*;
import java.text.SimpleDateFormat;

import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.anagrafe_animali.base.Cane;
import org.aspcfs.modules.anagrafe_animali.base.Furetto;
import org.aspcfs.modules.anagrafe_animali.base.Gatto;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;

public class EventoAggressione extends Evento {

	public static final int idTipologiaDB = 68;

	private java.sql.Timestamp dataAggressione;
	private int id;
	private int idCu;
	private String misureFormative;
	private String misureRiabilitative;
	private String misureRestrittive;
	private int idEvento;
	private int alterazioniComportamentali;
	private int analisiGestione;
	private int prevedibilitaEvento;
	private int tagliaAggressore;
	private int categoriaVittima;
	private int tagliaVittima;
	private int patologie;
	private int tipologia;
	private int aggressioneRipetuta;
	private int idAslProprietario;
	private String[] veterinari ;
	
	/**
	 * @return the data_evento
	 */
	
	
	
	public void setDataAggressione(String dataAggressione) {
		this.dataAggressione = DateUtils.parseDateStringNew(dataAggressione, "dd/MM/yyyy");
	}

	public java.sql.Timestamp getDataAggressione() {
		return dataAggressione;
	}

	public void setDataAggressione(java.sql.Timestamp dataAggressione) {
		this.dataAggressione = dataAggressione;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getMisureRestrittive() {
		return misureRestrittive;
	}

	public void setMisureRestrittive(String misureRestrittive) {
		this.misureRestrittive = misureRestrittive;
	}
	
	public String getMisureRiabilitative() {
		return misureRiabilitative;
	}

	public void setMisureRiabilitative(String misureRiabilitative) {
		this.misureRiabilitative = misureRiabilitative;
	}
	
	public String getMisureFormative() {
		return misureFormative;
	}

	public void setMisureFormative(String misureFormative) {
		this.misureFormative = misureFormative;
	}
	
	public int getIdCu() {
		return idCu;
	}

	public void setIdCu(int idCu) {
		this.idCu = idCu;
	}

	public void setIdCu(String idCu) {
		this.idCu = new Integer(idCu).intValue() ;
	}
	
	public int getAlterazioniComportamentali() {
		return alterazioniComportamentali;
	}

	public void setAlterazioniComportamentali(int alterazioniComportamentali) {
		this.alterazioniComportamentali = alterazioniComportamentali;
	}
	
	public void setAlterazioniComportamentali(String alterazioniComportamentali) {
		this.alterazioniComportamentali = new Integer(alterazioniComportamentali).intValue() ;
	}
	
	public String[] getVeterinari() {
		return veterinari;
	}

	public void setVeterinari(String[] veterinari) {
		this.veterinari = veterinari;
	}
	

	public int getAnalisiGestione() {
		return analisiGestione;
	}

	public void setAnalisiGestione(int analisiGestione) {
		this.analisiGestione = analisiGestione;
	}
	
	public void setAnalisiGestione(String analisiGestione) {
		this.analisiGestione = new Integer(analisiGestione).intValue() ;
	}

	public int getPrevedibilitaEvento() {
		return prevedibilitaEvento;
	}

	public void setPrevedibilitaEvento(int prevedibilitaEvento) {
		this.prevedibilitaEvento = prevedibilitaEvento;
	}
	
	public void setPrevedibilitaEvento(String prevedibilitaEvento) {
		this.prevedibilitaEvento = new Integer(prevedibilitaEvento).intValue() ;
	}

	public int getTagliaAggressore() {
		return tagliaAggressore;
	}

	public void setTagliaAggressore(int tagliaAggressore) {
		this.tagliaAggressore = tagliaAggressore;
	}
	public void setTagliaAggressore(String tagliaAggressore) {
		this.tagliaAggressore = new Integer(tagliaAggressore).intValue() ;
	}

	public int getCategoriaVittima() {
		return categoriaVittima;
	}

	public void setCategoriaVittima(int categoriaVittima) {
		this.categoriaVittima = categoriaVittima;
	}
	public void setCategoriaVittima(String categoriaVittima) {
		this.categoriaVittima = new Integer(categoriaVittima).intValue() ;
	}

	public int getTagliaVittima() {
		return tagliaVittima;
	}

	public void setTagliaVittima(int tagliaVittima) {
		this.tagliaVittima = tagliaVittima;
	}
	public void setTagliaVittima(String tagliaVittima) {
		this.tagliaVittima = new Integer(tagliaVittima).intValue() ;
	}
	
	public int getPatologie() {
		return patologie;
	}

	public void setPatologie(int patologie) {
		this.patologie = patologie;
	}
	
	public void setPatologie(String patologie) {
		this.patologie = new Integer(patologie).intValue() ;
	}
	
	public int getAggressioneRipetuta() {
		return aggressioneRipetuta;
	}

	public void setAggressioneRipetuta(int aggressioneRipetuta) {
		this.aggressioneRipetuta = aggressioneRipetuta;
	}
	
	public void setAggressioneRipetuta(String aggressioneRipetuta) {
		this.aggressioneRipetuta = new Integer(aggressioneRipetuta).intValue();
	}
	
	public int getIdAslProprietario() {
		return idAslProprietario;
	}

	public void setIdAslProprietario(int idAslProprietario) {
		this.idAslProprietario = idAslProprietario;
	}
	
	public void setIdAslProprietario(String idAslProprietario) {
		this.idAslProprietario = new Integer(idAslProprietario).intValue();
	}
	
	public int getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(int idEvento) {
		this.idEvento = idEvento;
	}
	
	public int getTipologia() {
		return tipologia;
	}

	public void setTipologia(int tipologia) {
		this.tipologia = tipologia;
	}
	
	public void setTipologia(String tipologia) {
		this.tipologia = new Integer(tipologia).intValue();
	}


	public static int getIdTipologiaDB() {
		return idTipologiaDB;
	}

	public EventoAggressione() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EventoAggressione(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}

	protected void buildRecord(ResultSet rs) throws SQLException {

		super.buildRecord(rs);
		this.idEvento = rs.getInt("idevento");
		this.dataAggressione = rs.getTimestamp("data_aggressione");
		this.alterazioniComportamentali = rs.getInt("alterazioni_comportamentali");
		this.idCu = rs.getInt("id_cu");
		this.misureFormative = rs.getString("misure_formative");
		this.misureRiabilitative = rs.getString("misure_riabilitative");
		this.misureRestrittive = rs.getString("misure_restrittive");
		this.analisiGestione = rs.getInt("analisi_gestione");
		this.categoriaVittima = rs.getInt("categoria_vittima");
		this.aggressioneRipetuta = rs.getInt("aggressione_ripetuta");
		this.patologie = rs.getInt("patologie");
		this.prevedibilitaEvento = rs.getInt("prevedibilita_evento");
		this.tagliaAggressore = rs.getInt("taglia_aggressore");
		this.tagliaVittima = rs.getInt("taglia_vittima");
		this.tipologia = rs.getInt("tipologia");

		// buildSede(rs);
		// buildRappresentanteLegale(rs);

	}
	
	
	protected void buildVeterinari(Connection db) throws SQLException {

		
		
				PreparedStatement pst = db.prepareStatement("Select count(*) as conta from evento_aggressione_veterinari where id_evento = ?");
				pst.setInt(1, idEvento);
				ResultSet rs = DatabaseUtils.executeQuery(db, pst);
		
				if(rs.next())
					veterinari = new String[rs.getInt("conta")];
		
				pst = db.prepareStatement("Select * from evento_aggressione_veterinari where id_evento = ?");
				pst.setInt(1, idEvento);
				rs = DatabaseUtils.executeQuery(db, pst);
						
				int j=0;
				while(rs.next()) 
				{
					veterinari[j] = rs.getString("id_veterinario");
					j++;
				}


				rs.close();
				pst.close();
				
	}

	public EventoAggressione(Connection db, int idEventoPadre) throws SQLException {

		// super(db, idEventoPadre);

		PreparedStatement pst = db
				.prepareStatement("Select *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento from evento e left join evento_aggressione f on (e.id_evento = f.id_evento) where e.id_evento = ?");
		pst.setInt(1, idEventoPadre);
		ResultSet rs = DatabaseUtils.executeQuery(db, pst);
		if (rs.next()) {
			buildRecord(rs);
			buildVeterinari(db);
		}

		if (idEventoPadre == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}

		rs.close();
		pst.close();
	}

	public boolean insert(Connection db) throws SQLException {

		StringBuffer sql = new StringBuffer();
		
		try {

	    	  
			super.insert(db);
			idEvento = super.getIdEvento();
			

			id = DatabaseUtils.getNextSeq(db, "evento_aggressione_id_seq");
			// sql.append("INSERT INTO animale (");

			sql.append("INSERT INTO evento_aggressione(id_evento, data_aggressione, aggressione_ripetuta, patologie, alterazioni_comportamentali, analisi_gestione, prevedibilita_evento, taglia_aggressore, categoria_vittima, taglia_vittima, tipologia, id_asl_proprietario,id_cu,misure_restrittive,misure_riabilitative,misure_formative ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			pst.setInt(++i, idEvento);
			pst.setTimestamp(++i, dataAggressione);
			pst.setInt(++i, aggressioneRipetuta);
			pst.setInt(++i, patologie);
			pst.setInt(++i, alterazioniComportamentali);
			pst.setInt(++i, analisiGestione);
			pst.setInt(++i, prevedibilitaEvento);
			pst.setInt(++i, tagliaAggressore);
			pst.setInt(++i, categoriaVittima);
			pst.setInt(++i, tagliaVittima);
			pst.setInt(++i, tipologia);
			pst.setInt(++i, idAslProprietario);
			pst.setInt(++i, idCu);
			pst.setString(++i, misureRestrittive);
			pst.setString(++i, misureRiabilitative);
			pst.setString(++i, misureFormative);

			pst.execute();
			pst.close();
			
			int j=0;
			while(j< veterinari.length)
			{
				String sqlVeterinari = "INSERT INTO evento_aggressione_veterinari VALUES (?,?) ";
				PreparedStatement pstVeterinari = db.prepareStatement(sqlVeterinari);

				pstVeterinari.setInt(1, idEvento);
				pstVeterinari.setInt(2, Integer.parseInt(veterinari[j]));
				pstVeterinari.execute();
				pstVeterinari.close();
				j++;
			}
			

			this.id = DatabaseUtils.getCurrVal(db, "evento_aggressione_id_seq", id);

		} catch (SQLException e) {
			
			throw new SQLException(e.getMessage());
		} finally {
	
		}
		return true;

	}

	public EventoAggressione salvaRegistrazione(int userId, int userRole, int userAsl, Animale thisAnimale,
			Connection db) throws Exception {
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

			Operatore proprietario = thisAnimale.getProprietario();
			Stabilimento stab = null;
			if (proprietario != null && proprietario.getIdOperatore() > 0) 
			{
				stab = (Stabilimento) proprietario.getListaStabilimenti().get(0);
				if(stab!=null)
					this.setIdAslProprietario(stab.getIdAsl());
			}
			
			this.insert(db);

			aggiornaFlagFuoriDominioAsl(db, thisAnimale, userAsl, oldAnimale);
			aggiornaStatoAnimale(db, thisAnimale);
			
			oldAnimale.updateFlagAggressivo(db,true);

		} catch (Exception e) {
			throw e;
		}

		return this;

	}
	
	
	public EventoAggressione build(ResultSet rs) throws Exception{
		try{	
			
			super.build(rs);
			buildRecord(rs);
		
		}catch (Exception e){
			throw e;
		}
	return this;
		}
	
	
	
	
	public String getVeterinariEstesi()
	{
		String toReturn = "";
		Connection db = null;
		try
		{

		Thread t = Thread.currentThread();
		db = GestoreConnessioni.getConnection();
		
		
		
		int j=0;
		while(j<this.getVeterinari().length)		
		{
			User user = new User();
			user.setBuildContact(true);
			
			if(j>0)
				toReturn+="<br/>";
			
			if (Integer.parseInt(this.getVeterinari()[j])>-1)
				user.buildRecord(db, Integer.parseInt(this.getVeterinari()[j]));
			else
				return "";
			toReturn += user.getContact().getNameFull() ;
			j++;
		}
		
		
		
		
		
		}catch (Exception e) 
		{
			e.printStackTrace();
		}finally
		{
			GestoreConnessioni.freeConnection(db);
		}
		
		
		return toReturn;
		
		
		
		
		
		
		
		
		
		
		
	}
}
