/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package ext.aspcfs.modules.apicolture.util;

import java.sql.Timestamp;
import java.util.Iterator;

import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.web.LookupList;

import ext.aspcfs.modules.apiari.base.Operatore;
import ext.aspcfs.modules.apiari.base.Stabilimento;

public class ApiUtil {

	public static String getInfoRichiesta(Operatore op, String esito, String errore, Timestamp timestamp, UserBean thisUser, LookupList SiteList, LookupList LookupStati, LookupList TipoAttivitaApi, LookupList ApicolturaClassificazione, LookupList ApicolturaSottospecie, LookupList ApicolturaModalita){
		String msg = "";
		
		msg+="     *** VALIDAZIONE IMPRESA ***      \n\n";
		
		msg+="      *** ESITO : "+esito+" ***      \n";
		if (errore!=null)
			msg+="      *** ERRORE : "+errore+" ***      \n";
		msg+="     *** DATA INVIO :" + timestamp  + " ***      \n"; 
		msg+="      *** INVIATA DA :" + thisUser.getUsername()+" ***      \n\n";

		msg+=" [CODICE AZIENDA] "+op.getCodiceAzienda()+" \n";
		msg+=" [PARTITA IVA] "+ op.getPartitaIva()+" \n";
		msg+=" [DENOMINAZIONE] "+op.getRagioneSociale()+" \n";
		msg+=" [ASL] "+SiteList.getSelectedValue(op.getIdAsl())+" \n";
		
		if (op.getSedeLegale()!=null){
			msg+=" [PROVINCIA SEDE LEGALE] "+ op.getSedeLegale().getDescrizione_provincia()+" \n";
			msg+=" [COMUNE SEDE LEGALE] "+ op.getSedeLegale().getDescrizioneComune()+" \n";
			msg+=" [CAP SEDE LEGALE] "+op.getSedeLegale().getCap()+" \n";
			msg+=" [INDIRIZZO SEDE LEGALE] "+op.getSedeLegale().getVia()+" "+ op.getSedeLegale().getCivico()+" \n";
		}
		
		msg+=" [STATO] "+LookupStati.getSelectedValue(op.getStato())+" \n";
		
		if (op.getRappLegale()!=null){
			msg+=" [CODICE FISCALE PROPRIETARIO] "+ op.getRappLegale().getCodFiscale()+" \n";
			msg+=" [PROPRIETARIO] "+ op.getRappLegale().getCognome()+" "+op.getRappLegale().getNome() +" \n";
		}
		
		msg+=" [DATA INIZIO ATTIVITA] "+ op.getDataInizio()+" \n";
		msg+=" [TIPO ATTIVITA] "+TipoAttivitaApi.getSelectedValue(op.getIdTipoAttivita())+" \n";
		msg+=" [DOMICILIO DIGITALE] "+op.getDomicilioDigitale()+" \n";
		msg+=" [TELEFONO FISSO] "+op.getTelefono1()+" \n";
		msg+=" [TELEFONO CELLULARE] "+op.getTelefono2()+" \n";
		msg+=" [FAX] "+op.getFax()+" \n\n";
		
		msg+=" *** LISTA APIARI ***\n\n";
			
		Iterator<Stabilimento> itStab = op.getListaStabilimenti().iterator();
		while (itStab.hasNext())
		{
			Stabilimento thisStab = itStab.next();
			if (thisStab.isFlagLaboratorio()==false)
			{
				msg+=" [APIARIO] "+thisStab.getProgressivoBDA()+" \n";
				
				if (thisStab.getDetentore()!=null){
					msg+=" [DETENTORE] "+ thisStab.getDetentore().getCognome()  + " "+thisStab.getDetentore().getNome()+" \n";
					msg+=" [CF DETENTORE] "+ thisStab.getDetentore().getCodFiscale()+" \n";
				}
				
				msg+=" [CLASSIFICAZIONE] "+ApicolturaClassificazione.getSelectedValue(thisStab.getIdApicolturaClassificazione()+" \n");
				msg+=" [SOTTOSPECIE] "+ApicolturaSottospecie.getSelectedValue(thisStab.getIdApicolturaSottospecie())+" \n";
				msg+=" [MODALITA] "+ApicolturaModalita.getSelectedValue(thisStab.getIdApicolturaModalita())+" \n";
				msg+=" [NUM ALVEARI] "+thisStab.getNumAlveari()+" \n";
				msg+=" [NUM SCIAMI / NUCLEI] "+thisStab.getNumSciami()+" \n";
				msg+=" [DATA INIZIO] "+thisStab.getDataApertura()+" \n";
				msg+=" [DATA CHIUSURA] "+thisStab.getDataChiusura()+" \n";
				
				if (thisStab.getSedeOperativa()!=null)
					msg+=" [UBICAZIONE] "+thisStab.getSedeOperativa().getDescrizioneComune() +" - "+ thisStab.getSedeOperativa().getVia()+" \n";
				
				msg+=" [STATO] "+LookupStati.getSelectedValue(thisStab.getStato())+" \n\n";
			}
		
			}
		
		
		try {
			System.out.println("\n\n # APICOLTURA MSG CALLER: "+getCallerClass(2)+"\n\n");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("# APICOLTURA MSG: \n\n\n\n"+msg);
		return msg;
	}

	
	public static Class getCallerClass(int level) throws ClassNotFoundException {
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        String rawFQN = stElements[level+1].toString().split("\\(")[0];
        return Class.forName(rawFQN.substring(0, rawFQN.lastIndexOf('.')));
    }
	
	
	
}
