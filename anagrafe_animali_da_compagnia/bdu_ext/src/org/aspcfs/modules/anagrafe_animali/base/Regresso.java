/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.anagrafe_animali.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;


import com.darkhorseventures.framework.beans.GenericBean;

public class Regresso extends GenericBean
{
	private static final long serialVersionUID = 1L;
	
	private String microchip;
	private String data;
	private String tipo;
	private String dataInserimento;
	private String ultima;
	private String proprietarioCognome;
	private String proprietarioNome;
	private String proprietarioCodiceFiscale;
	private String sesso;
	private String taglia;
	private String dataNascita;
	private String mantello;
	private String nome;
	private String segniParticolari;
	private String tatuaggio;
	private String stato;
	private String passaporto;
	private String dataPassaporto;
	private String razza;
	private String detentoreCognome;
	private String detentoreNome;
	private String detentoreCodiceFiscale;
	private String catturaComune;
	private String proprietarioComuneResidenza;
	private String proprietarioProvinciaResidenza;
	private String detentoreComuneResidenza;
	private String detentoreProvinciaResidenza;
	private String canileNome;
	private String canileComune;
	private String canileProvincia;
	private String canileDistretto;
	private String operatore;
	private String chippatore;
	private String descrizioneOperazione;
	
	public static Vector<Regresso> get( Connection db, String microchip )
	{
		Vector<Regresso> ret = new Vector<Regresso>();
		
		String sql = "SELECT * FROM regresso " +
				"WHERE \"Microchip\" = ? OR \"Tatuaggio\" = ? ORDER BY \"Microchip\", \"Data\" ";
		
		PreparedStatement stat = null;
		
		try
		{
			stat = db.prepareStatement( sql );
			stat.setString( 1, microchip );
			stat.setString( 2, microchip );
			
			ResultSet res = stat.executeQuery();
			while( res.next() )
			{
				ret.add( loadBean( res ) );
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if( stat != null )
			{
				try
				{
					stat.close();
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
		}
		
		return ret;
	}

	private static Regresso loadBean(ResultSet res)
	{
		Regresso ret = new Regresso();
		
		try
		{
			ret.setCanileComune( res.getString( "CanileComune" ) );
			ret.setCanileDistretto( res.getString( "CanileDistretto" ) );
			ret.setCanileNome( res.getString( "CanileNome" ) );
			ret.setCanileProvincia( res.getString( "CanileProvincia" ) );
			ret.setCatturaComune( res.getString( "CatturaComune" ) );
			ret.setData( res.getString( "Data" ) );
			ret.setDataInserimento( res.getString( "DataInserimento" ) );
			ret.setDataNascita( res.getString( "DataNascita" ) );
			ret.setDataPassaporto( res.getString( "DataPassaporto" ) );
			ret.setDetentoreCodiceFiscale( res.getString( "DetentoreCodiceFiscale" ) );
			ret.setDetentoreCognome( res.getString( "DetentoreCognome" ) );
			ret.setDetentoreComuneResidenza( res.getString( "DetentoreComuneResidenza" ) );
			ret.setDetentoreNome( res.getString( "DetentoreNome" ) );
			ret.setDetentoreProvinciaResidenza( res.getString( "DetentoreProvinciaResidenza" ) );
			ret.setMantello( res.getString( "Mantello" ) );
			ret.setMicrochip( res.getString( "Microchip" ) );
			ret.setNome( res.getString( "Nome" ) );
			ret.setPassaporto( res.getString( "Passaporto" ) );
			ret.setProprietarioCodiceFiscale( res.getString( "ProprietarioCodiceFiscale" ) );
			ret.setProprietarioCognome( res.getString( "ProprietarioCognome" ) );
			ret.setProprietarioCognome( res.getString( "ProprietarioCognome" ) );
			ret.setProprietarioComuneResidenza( res.getString( "ProprietarioComuneResidenza" ) );
			ret.setProprietarioNome( res.getString( "ProprietarioNome" ) );
			ret.setProprietarioProvinciaResidenza( res.getString( "ProprietarioProvinciaResidenza" ) );
			ret.setRazza( res.getString( "Razza" ) );
			ret.setSegniParticolari( res.getString( "SegniParticolari" ) );
			ret.setSesso( res.getString( "Sesso" ) );
			ret.setStato( res.getString( "Stato" ) );
			ret.setTaglia( res.getString( "Taglia" ) );
			ret.setTatuaggio( res.getString( "Tatuaggio" ) );
			ret.setTipo( res.getString( "Tipo" ) );
			ret.setUltima( res.getString( "Ultima" ) );
			ret.setOperatore( res.getString( "OperatoreCognome" ) );
			ret.setChippatore( res.getString( "ChippatoreCognome" ) );
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return ret;
	}

	public String getMicrochip() {
		return microchip;
	}

	public void setMicrochip(String microchip) {
		this.microchip = microchip;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(String dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public String getUltima() {
		return ultima;
	}

	public void setUltima(String ultima) {
		this.ultima = ultima;
	}

	public String getProprietarioCognome() {
		return proprietarioCognome;
	}

	public void setProprietarioCognome(String proprietarioCognome) {
		this.proprietarioCognome = proprietarioCognome;
	}

	public String getProprietarioNome() {
		return proprietarioNome;
	}

	public void setProprietarioNome(String proprietarioNome) {
		this.proprietarioNome = proprietarioNome;
	}

	public String getProprietarioCodiceFiscale() {
		return proprietarioCodiceFiscale;
	}

	public void setProprietarioCodiceFiscale(String proprietarioCodiceFiscale) {
		this.proprietarioCodiceFiscale = proprietarioCodiceFiscale;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getTaglia() {
		return taglia;
	}

	public void setTaglia(String taglia) {
		this.taglia = taglia;
	}

	public String getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getMantello() {
		return mantello;
	}

	public void setMantello(String mantello) {
		this.mantello = mantello;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSegniParticolari() {
		return segniParticolari;
	}

	public void setSegniParticolari(String segniParticolari) {
		this.segniParticolari = segniParticolari;
	}

	public String getTatuaggio() {
		return tatuaggio;
	}

	public void setTatuaggio(String tatuaggio) {
		this.tatuaggio = tatuaggio;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getPassaporto() {
		return passaporto;
	}

	public void setPassaporto(String passaporto) {
		this.passaporto = passaporto;
	}

	public String getDataPassaporto() {
		return dataPassaporto;
	}

	public void setDataPassaporto(String dataPassaporto) {
		this.dataPassaporto = dataPassaporto;
	}

	public String getRazza() {
		return razza;
	}

	public void setRazza(String razza) {
		this.razza = razza;
	}

	public String getDetentoreCognome() {
		return detentoreCognome;
	}

	public void setDetentoreCognome(String detentoreCognome) {
		this.detentoreCognome = detentoreCognome;
	}

	public String getDetentoreNome() {
		return detentoreNome;
	}

	public void setDetentoreNome(String detentoreNome) {
		this.detentoreNome = detentoreNome;
	}

	public String getDetentoreCodiceFiscale() {
		return detentoreCodiceFiscale;
	}

	public void setDetentoreCodiceFiscale(String detentoreCodiceFiscale) {
		this.detentoreCodiceFiscale = detentoreCodiceFiscale;
	}

	public String getCatturaComune() {
		return catturaComune;
	}

	public void setCatturaComune(String catturaComune) {
		this.catturaComune = catturaComune;
	}

	public String getProprietarioComuneResidenza() {
		return proprietarioComuneResidenza;
	}

	public void setProprietarioComuneResidenza(String proprietarioComuneResidenza) {
		this.proprietarioComuneResidenza = proprietarioComuneResidenza;
	}

	public String getProprietarioProvinciaResidenza() {
		return proprietarioProvinciaResidenza;
	}

	public void setProprietarioProvinciaResidenza(
			String proprietarioProvinciaResidenza) {
		this.proprietarioProvinciaResidenza = proprietarioProvinciaResidenza;
	}

	public String getDetentoreComuneResidenza() {
		return detentoreComuneResidenza;
	}

	public void setDetentoreComuneResidenza(String detentoreComuneResidenza) {
		this.detentoreComuneResidenza = detentoreComuneResidenza;
	}

	public String getDetentoreProvinciaResidenza() {
		return detentoreProvinciaResidenza;
	}

	public void setDetentoreProvinciaResidenza(String detentoreProvinciaResidenza) {
		this.detentoreProvinciaResidenza = detentoreProvinciaResidenza;
	}

	public String getCanileNome() {
		return canileNome;
	}

	public void setCanileNome(String canileNome) {
		this.canileNome = canileNome;
	}

	public String getCanileComune() {
		return canileComune;
	}

	public void setCanileComune(String canileComune) {
		this.canileComune = canileComune;
	}

	public String getCanileProvincia() {
		return canileProvincia;
	}

	public void setCanileProvincia(String canileProvincia) {
		this.canileProvincia = canileProvincia;
	}

	public String getCanileDistretto() {
		return canileDistretto;
	}

	public void setCanileDistretto(String canileDistretto) {
		this.canileDistretto = canileDistretto;
	}

	public String getOperatore() {
		return operatore;
	}

	public void setOperatore(String operatore) {
		this.operatore = operatore;
	}

	public String getChippatore() {
		return chippatore;
	}

	public void setChippatore(String chippatore) {
		this.chippatore = chippatore;
	}

	public String getDescrizioneOperazione() {
		return descrizioneOperazione;
	}

	public void setDescrizioneOperazione(String descrizioneOperazione) {
		this.descrizioneOperazione = descrizioneOperazione;
	}
	
	
}

