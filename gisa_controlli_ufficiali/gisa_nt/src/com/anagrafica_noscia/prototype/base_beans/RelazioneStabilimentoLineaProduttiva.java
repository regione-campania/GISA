/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package com.anagrafica_noscia.prototype.base_beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.anagrafica_noscia.prototype.masterlist.Aggregazione;
import com.anagrafica_noscia.prototype.masterlist.Attivita;
import com.anagrafica_noscia.prototype.masterlist.Macroarea;
import com.sun.xml.internal.bind.v2.TODO;

public class RelazioneStabilimentoLineaProduttiva {

	public Integer getId()
	{return this.id;}
	public void setId( Integer id)
	{
		this.id = id;
	}
	public Integer getIdMacroarea() {
		return idMacroarea;
	}
	public void setIdMacroarea(Integer idMacroarea) {
		this.idMacroarea = idMacroarea;
	}
	public Macroarea getMacroarea() {
		return macroarea;
	}
	public void setMacroarea(Macroarea macroarea) {
		this.macroarea = macroarea;
	}
	public Integer getIdAggregazione() {
		return idAggregazione;
	}
	public void setIdAggregazione(Integer idAggregazione) {
		this.idAggregazione = idAggregazione;
	}
	public Aggregazione getAggregazione() {
		return aggregazione;
	}
	public void setAggregazione(Aggregazione aggregazione) {
		this.aggregazione = aggregazione;
	}
	public Integer getIdLineaAttivita() {
		return idLineaAttivita;
	}
	public void setIdLineaAttivita(Integer idLineaAttivita) {
		this.idLineaAttivita = idLineaAttivita;
	}
	public Attivita getLineaAttivita() {
		return lineaAttivita;
	}
	public void setLineaAttivita(Attivita lineaAttivita) {
		this.lineaAttivita = lineaAttivita;
	}
	public Integer getIdStabilimento() {
		return idStabilimento;
	}
	public void setIdStabilimento(Integer idStabilimento) {
		this.idStabilimento = idStabilimento;
	}
	public Stabilimento getStabilimento() {
		return stabilimento;
	}
	public void setStabilimento(Stabilimento stabilimento) {
		this.stabilimento = stabilimento;
	}
	public String getNumeroRegistrazione(){return this.numeroRegistrazione;}
	public void setNumeroRegistrazione(String numReg){this.numeroRegistrazione = numReg;}
	public String getCun(){return this.cun;}
	public void setCun(String cun){this.cun = cun;}
	public Integer getIdStato() {
		return idStato;
	}
	public void setIdStato(Integer idStato) {
		this.idStato = idStato;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public Timestamp getDataInserimento() {
		return dataInserimento;
	}
	public void setDataInserimento(Timestamp dataInserimento) {
		this.dataInserimento = dataInserimento;
	}
	
	private Integer id; /*id rel stab lp */
	
	private Integer idMacroarea;
	private Macroarea macroarea;
	
	private Integer idAggregazione;
	private Aggregazione aggregazione;
	
	private Integer idLineaAttivita;
	private Attivita lineaAttivita;
	
	private Integer idStabilimento;
	private Stabilimento stabilimento;
	
	private String numeroRegistrazione;
	private String cun;

	private Integer idStato;
	private String stato; /*desc usando idStato da lookup_stati */
	
	private Timestamp dataInserimento;
	
	
	
	public RelazioneStabilimentoLineaProduttiva(){}
	
	public RelazioneStabilimentoLineaProduttiva(ResultSet rs , Connection db )
	{
		try{ setIdMacroarea(rs.getInt("id_macroarea")); }catch(Exception ex){System.out.println("Campo non presente");}
		try{setMacroarea(Macroarea.getByOid(getIdMacroarea(), db)); }catch(Exception ex){System.out.println("Campo non presente");}
		try{ setIdAggregazione(rs.getInt("id_aggregazione")); }catch(Exception ex){System.out.println("Campo non presente");}
		try{setAggregazione(Aggregazione.getByOid(getIdAggregazione(), db)); }catch(Exception ex){System.out.println("Campo non presente");}
		try{ setIdLineaAttivita(rs.getInt("id_attivita")); }catch(Exception ex){System.out.println("Campo non presente");}
		try{setLineaAttivita(Attivita.getByOid(getIdLineaAttivita(), db)); }catch(Exception ex){System.out.println("Campo non presente");}
		try{setIdStabilimento(rs.getInt("id_stabilimento")); }catch(Exception ex){System.out.println("Campo non presente");}
		try{setStabilimento(Stabilimento.getByOid( db,getIdStabilimento(), false)); }catch(Exception ex){System.out.println("Campo non presente");}
		try{setNumeroRegistrazione(rs.getString("numero_registrazione")); }catch(Exception ex){System.out.println("Campo non presente");}
		try{setCun(rs.getString("cun")); }catch(Exception ex){System.out.println("Campo non presente");}
		
		try{setIdStato(rs.getInt("id_stato"));} catch(Exception ex){System.out.println("Campo non presente");}
		try{ /*da id stato usando lookup_stati */
			String descStato = null;
			descStato = LookupPair.buildByCode(db, "lookup_stati", "code", "description", getIdStato(), "enabled = true").getDesc();
			setStato(descStato);
		}
		catch(Exception ex){ System.out.println("Campo non presente");}
		/*TODO ISTANZE VALORI CAMPI ESTESI ASSOCIATI */
		try{setDataInserimento(rs.getTimestamp("data_inserimento")); }catch(Exception ex){System.out.println("Campo non presente");}
		
	}
	
	
	
	/*ritorna tutte le istanze di linee per un dato stabilimento */
	public static ArrayList<RelazioneStabilimentoLineaProduttiva> getAllRelazioniPerStabilimento(Integer idStabilimento, Connection conn) throws Exception 
	{
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		ArrayList<RelazioneStabilimentoLineaProduttiva> toRet = new ArrayList<RelazioneStabilimentoLineaProduttiva>();
		
		try
		{
			pst = conn.prepareStatement("select * from public_functions.anagrafica_cerca_rel_stabilimento_linea_attivita(NULL,NULL,NULL,?)");
			pst.setInt(1,idStabilimento);
			rs = pst.executeQuery();
			while(rs.next())
			{
				toRet.add(new RelazioneStabilimentoLineaProduttiva(rs,conn));
			}
			return toRet;
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			try{pst.close();}catch(Exception ex){}
			try{rs.close();}catch(Exception ex){}
		}
	}
	
	
	
	
	
	
	
}
