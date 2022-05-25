/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam;

public class StatoTrasferimento
{
	public static final int ND							= -1;
	public static final int	ATTESA_CRIUV				= 1;
	public static final int ACCETTATO_CRIUV				= 2;
	public static final int ACCETTATO_DESTINATARIO		= 3;
	public static final int ATTESA_DESTINATARIO_URGENZA	= 4;
	public static final int RICONSEGNATO				= 5;
	public static final int RIFIUTATO_CRIUV				= 6;
	public static final int ATTESA_DESTINATARIO			= 7;
	public static final int ATTESA_RINCONSEGNA_CRIUV	= 8;
	public static final int RIFIUTATO_RINCONSEGNA_CRIUV	= 9;
	
	public static final int ND_ORDER							= -1;
	public static final int	ATTESA_CRIUV_ORDER					= 9;
	public static final int ATTESA_DESTINATARIO_ORDER			= 8;
	public static final int ATTESA_DESTINATARIO_URGENZA_ORDER	= 7;
	public static final int RIFIUTATO_CRIUV_ORDER				= 6;
	public static final int ACCETTATO_CRIUV_ORDER				= 5;
	public static final int ACCETTATO_DESTINATARIO_ORDER		= 4;
	public static final int ATTESA_RINCONSEGNA_CRIUV_ORDER		= 3;
	public static final int RIFIUTATO_RINCONSEGNA_CRIUV_ORDER	= 2;
	public static final int RICONSEGNATO_ORDER					= 1;
	
	public int stato      = ND;
	public int statoOrder = ND_ORDER;

	public int getStato() {
		return stato;
	}

	public void setStato( int stato )
	{
		this.stato = stato;
	}
	
	public int getStatoOrder() {
		return statoOrder;
	}

	public void setStatoOrder( int statoOrder )
	{
		this.statoOrder = statoOrder;
	}
	
	public void setStato( Trasferimento trasf )
	{
		//prima versione semplificata
		stato = ATTESA_CRIUV;
		statoOrder = ATTESA_CRIUV_ORDER;
		
		if( trasf.getDataRiconsegna() != null && !trasf.getDaApprovareRiconsegna() && trasf.getDataRifiutoRiconsegna() == null )
		{
			stato = RICONSEGNATO;
			statoOrder = RICONSEGNATO_ORDER;
		}
		else if( trasf.getDataRiconsegna() != null && !trasf.getDaApprovareRiconsegna() && trasf.getDataRifiutoRiconsegna() != null )
		{
			stato = RIFIUTATO_RINCONSEGNA_CRIUV;
			statoOrder = RIFIUTATO_RINCONSEGNA_CRIUV_ORDER;
		}
		else if( trasf.getDataRiconsegna() != null && trasf.getDaApprovareRiconsegna() && trasf.getUrgenza() )
		{
			stato = RICONSEGNATO;
			statoOrder = RICONSEGNATO_ORDER;
		}
		else if( trasf.getDataRiconsegna() != null && trasf.getDaApprovareRiconsegna() && !trasf.getUrgenza() )
		{
			stato = ATTESA_RINCONSEGNA_CRIUV;
			statoOrder = ATTESA_RINCONSEGNA_CRIUV_ORDER;
		}
		else if( trasf.getDataAccettazioneDestinatario() != null )
		{
			stato = ACCETTATO_DESTINATARIO;
			statoOrder = ACCETTATO_DESTINATARIO_ORDER;
		}
		else if( trasf.getDataAccettazioneCriuv() != null )
		{
			stato = ACCETTATO_CRIUV;
			statoOrder = ACCETTATO_CRIUV_ORDER;
		}
		else if( trasf.getDataRifiutoCriuv() != null )
		{
			stato = RIFIUTATO_CRIUV;
			statoOrder = RIFIUTATO_CRIUV_ORDER;
		}
		else if( trasf.getUrgenza() )
		{
			stato = ATTESA_DESTINATARIO_URGENZA;
			statoOrder = ATTESA_DESTINATARIO_URGENZA_ORDER;
		}
		else if( 
					trasf.getClinicaDestinazione() != null && 
					trasf.getClinicaDestinazione().getLookupAsl().getId() == trasf.getClinicaOrigine().getLookupAsl().getId() )
		{
			stato = ATTESA_DESTINATARIO;
			statoOrder = ATTESA_DESTINATARIO_ORDER;
		}
		
	}
	
	public void setStato( TrasferimentoNoH trasf )
	{
		//prima versione semplificata
		stato = ATTESA_CRIUV;
		statoOrder = ATTESA_CRIUV_ORDER;
		
		if( trasf.getDataRiconsegna() != null && !trasf.getDaApprovareRiconsegna() && trasf.getDataRifiutoRiconsegna() == null )
		{
			stato = RICONSEGNATO;
			statoOrder = RICONSEGNATO_ORDER;
		}
		else if( trasf.getDataRiconsegna() != null && !trasf.getDaApprovareRiconsegna() && trasf.getDataRifiutoRiconsegna() != null )
		{
			stato = RIFIUTATO_RINCONSEGNA_CRIUV;
			statoOrder = RIFIUTATO_RINCONSEGNA_CRIUV_ORDER;
		}
		else if( trasf.getDataRiconsegna() != null && trasf.getDaApprovareRiconsegna() && trasf.getUrgenza() )
		{
			stato = RICONSEGNATO;
			statoOrder = RICONSEGNATO_ORDER;
		}
		else if( trasf.getDataRiconsegna() != null && trasf.getDaApprovareRiconsegna() && !trasf.getUrgenza() )
		{
			stato = ATTESA_RINCONSEGNA_CRIUV;
			statoOrder = ATTESA_RINCONSEGNA_CRIUV_ORDER;
		}
		else if( trasf.getDataAccettazioneDestinatario() != null )
		{
			stato = ACCETTATO_DESTINATARIO;
			statoOrder = ACCETTATO_DESTINATARIO_ORDER;
		}
		else if( trasf.getDataAccettazioneCriuv() != null )
		{
			stato = ACCETTATO_CRIUV;
			statoOrder = ACCETTATO_CRIUV_ORDER;
		}
		else if( trasf.getDataRifiutoCriuv() != null )
		{
			stato = RIFIUTATO_CRIUV;
			statoOrder = RIFIUTATO_CRIUV_ORDER;
		}
		else if( trasf.getUrgenza() )
		{
			stato = ATTESA_DESTINATARIO_URGENZA;
			statoOrder = ATTESA_DESTINATARIO_URGENZA_ORDER;
		}
		else if( 
					trasf.getClinicaDestinazione() != null && 
					trasf.getClinicaDestinazione().getAsl().equals(trasf.getClinicaOrigine().getAsl()) )
		{
			stato = ATTESA_DESTINATARIO;
			statoOrder = ATTESA_DESTINATARIO_ORDER;
		}
		
	}
	
	@Override
	public String toString()
	{
		String ret = "ND";
		
		switch ( stato )
		{
		case ATTESA_CRIUV:
			ret = "Attesa CRIUV";
			break;
		case ACCETTATO_CRIUV:
			ret = "Accettato CRIUV";
			break;
		case ACCETTATO_DESTINATARIO:
			ret = "Accettato Clinica Destinazione";
			break;
		case ATTESA_DESTINATARIO_URGENZA:
			ret = "Attesa Clinica Destinazione (trasferimento in urgenza)";
			break;
		case RICONSEGNATO: 
			ret = "Riconsegnato";
			break;
		case RIFIUTATO_CRIUV: 
			ret = "Rifiutato CRIUV";
			break;
		case ATTESA_DESTINATARIO: 
			ret = "Attesa Clinica Destinazione (trasferimento stessa Asl)";
			break;
		case ATTESA_RINCONSEGNA_CRIUV:
			ret = "Riconsegna in attesa di approvazione del CRIUV";
			break;
		case RIFIUTATO_RINCONSEGNA_CRIUV:
			ret = "Riconsegna rifiutata dal CRIUV";
			break;
		default:
			break;
		}
		
		return ret;
	}
	
}
