/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc;

import java.sql.Connection;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.BUtente;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.AttivitaBdr;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.FascicoloSanitario;
import it.us.web.bean.vam.Trasferimento;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;
import it.us.web.constants.IdOperazioniBdr;
import it.us.web.constants.Specie;
import it.us.web.constants.SpecieAnimali;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.vam.AnimaliUtil;
import it.us.web.util.vam.CaninaRemoteUtil;
import it.us.web.util.vam.RegistrazioniUtil;

public class Riapertura extends GenericAction implements Specie {

	
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "DETAIL", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("cc");
	}

	public void execute() throws Exception
	{
		CartellaClinica cc = (CartellaClinica) persistence.find(CartellaClinica.class, interoFromRequest("id"));
		boolean canEliminate = true;
		String errorMessage = "";
		FascicoloSanitario fs = null;

		/**
		 * Controllo se la CC appartiene a un FASCICOLO SANITARIO, in caso
		 * affermativo controllo se trattasi dell'ultima relativa al fascicolo.
		 * Se non trattasi di ultima blocco la cancellazione
		 */

		if (cc.getFascicoloSanitario() != null) {
			fs = cc.getFascicoloSanitario();
			int idUltimaCartellaClinica = fs.getUltimaCC();
			if (idUltimaCartellaClinica > 0 && !(idUltimaCartellaClinica == cc.getId())) {
				canEliminate = false;
				errorMessage = "Non è possibile riaprire la cartella clinica, perchè non si tratta dell'ultima cc relativa al fascicolo sanitario numero "
						+ fs.getNumero();
				// req.setAttribute("specie", SpecieAnimali.getInstance());
				// setErrore("Non è possibile cancellare la cartella clinica, perchè non si tratta dell'ultima cc relativa al fascicolo sanitario numero "
				// + fs.getNumero());
				// redirectTo("vam.cc.Detail.us?id=" + cc.getId());
			}

		}
		
		if (canEliminate) {
		/**
			 * Controllo BDU per recuperare ultima operazione cancellabile da microchip e 
			 * verificare sia quella della riapertura, se la chiusura ha una registrazione in BDU
			 */
			
			//Recupero eventuale id registrazione BDU di chiusura
			
			int dest = 0;
			if (cc.getDestinazioneAnimale()!=null)
				dest = cc.getDestinazioneAnimale().getId();
			
			
			Animale thisAnimale = cc.getAccettazione().getAnimale();
			int tipologiaAnimale = thisAnimale.getLookupSpecie().getId();
			
			LookupOperazioniAccettazione operazione = null;
			
			if (tipologiaAnimale == Specie.CANE) 
			{

				if(dest==3)//Adozione
					operazione = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.adozione);
				if(dest==5)//Re-immissione
					operazione = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.reimmissione);
				if(dest==8)//Ritorno ad asl di origine
					operazione = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.ritornoAslOrigine);
				if(dest==4)//Trasf a canile
					operazione = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.trasfCanile);
				if(dest==1)//Ritorno a proprietario
					operazione = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.ritornoProprietario);
				if(dest==9)//Ritorno a canile origine
					operazione = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.ritornoCanileOrigine);
			}
			
			else if (tipologiaAnimale == Specie.GATTO) 
			{

				operazione = null;
				if(dest==3)//Adozione
					operazione = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.adozione);
				if(dest==5)//Re-immissione
					operazione = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.reimmissione);
				if(dest==8)//Ritorno ad asl di origine
					operazione = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.ritornoAslOrigine);
				if(dest==4)//Trasf a canile
					operazione = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.trasfCanile);
				if(dest==1)//Ritorno a proprietario
					operazione = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.ritornoProprietario);
			}
			
			
			
			int idRegistrazioneBduChiusura = RegistrazioniUtil.getIdRegBdr(cc, operazione);
			
				Context ctx = new InitialContext();
				javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup("java:comp/env/jdbc/bduS");
				Connection connection = ds.getConnection();
				GenericAction.aggiornaConnessioneApertaSessione(req);
				int esito = CaninaRemoteUtil.getRegistrazioneCancellabileBDU(
								thisAnimale.getIdentificativo(), idRegistrazioneBduChiusura, connection,req);
						if (esito == 2 ) {
							canEliminate = false;
							errorMessage = "Non è possibile riaprire la cartella clinica: registrazione con id "
									+ idRegistrazioneBduChiusura
									+ " ancora presente in BDU. Procedere alla cancellazione prima di procedere.";
					}else if (esito == 3){
						canEliminate = false;
						errorMessage = "Non è possibile riaprire la cartella clinica: registrazione con id "
								+ idRegistrazioneBduChiusura
								+ "  presente in BDU e non cancellabile. Contattare l'hd di secondo livello";
					}else if (esito == -1){
						canEliminate = false;
						errorMessage = "Non è possibile riaprire la cartella clinica: si è verificato un errore generico "
								+"Contattare l'hd di secondo livello";
					}
				
				connection.close();
				GenericAction.aggiornaConnessioneChiusaSessione(req);
			}
		
		
		if (canEliminate){
			

			
			if (fs != null){
				//Riapro il fascicolo sanitario se chiuso
				if (fs.getDataChiusura() != null){
					fs.setDataChiusura(null);
					persistence.update(fs);
				//	persistence.commit();
				}
				
			}
			
			if (cc.getTrasferimentiByCcPostTrasf() != null && cc.getTrasferimentiByCcPostTrasf().size() > 0){
				
				Set<Trasferimento> trasfList = cc.getTrasferimentiByCcPostTrasf();
				Iterator iTrasf = trasfList.iterator();
				while (iTrasf.hasNext()){
					Trasferimento trasf = (Trasferimento) iTrasf.next();
//					Set<LookupOperazioniAccettazione> operazioni = trasf.getOperazioniRichieste();
//					Iterator i = operazioni.iterator();
//					while (i.hasNext()){
//						LookupOperazioniAccettazione o = (LookupOperazioniAccettazione) i.next();
//						o.getOperazioniCondizionanti();
//						o.getOperazioniCondizionate();
//					}
					
//					trasf.setCartellaClinicaDestinatario(null);
//					trasf.setDataAccettazioneDestinatario(null);
//					trasf.getOperazioniRichieste();
//					trasf.setNotaDestinatario(null);
					trasf.setDataRiconsegna(null);
					trasf.setDataApprovazioneRiconsegna(null);
					trasf.setDataRifiutoRiconsegna(null);
					persistence.update(trasf);
//				//	persistence.commit();
				
			}
			}
			
			
//			if (cc.getTrasferimentiByCcPostTrasf() != null && cc.getTrasferimentiByCcPostTrasf().size() > 0){
//				Set<Trasferimento> trasfList = cc.getTrasferimentiByCcPostTrasf();
//				Iterator iTrasf = trasfList.iterator();
//				while (iTrasf.hasNext()){
//					Trasferimento trasf = (Trasferimento) iTrasf.next();
//					Set<LookupOperazioniAccettazione> operazioni = trasf.getOperazioniRichieste();
//					Iterator i = operazioni.iterator();
//					while (i.hasNext()){
//						LookupOperazioniAccettazione o = (LookupOperazioniAccettazione) i.next();
//						o.getOperazioniCondizionanti();
//						o.getOperazioniCondizionate();
//					}
//					
//					trasf.setCartellaClinicaDestinatario(null);
//					trasf.setDataAccettazioneDestinatario(null);
//					trasf.getOperazioniRichieste();
//					trasf.setNotaDestinatario(null);
//					persistence.update(trasf);
//				//	persistence.commit();
//				}
//				
////				Accettazione accCollegata = cc.getAccettazione();
////				accCollegata.setTrashedDate(new Date());
////				accCollegata.getOperazioniRichieste();
////				//AGGIUNGERE INFO SU UTENTE CANCELLAZIONE
////				persistence.update(accCollegata);
////			//	persistence.commit();
//			}
			
			
//			if (cc.getTrasferimenti() != null && cc.getTrasferimenti().size() > 0){
//				Set<Trasferimento> trasfListOut = cc.getTrasferimenti();
//				Iterator iTrasfOut = trasfListOut.iterator();
//				while (iTrasfOut.hasNext()){
//					Trasferimento trasfOut = (Trasferimento) iTrasfOut.next();
//					Set<LookupOperazioniAccettazione> operazioni = trasfOut.getOperazioniRichieste();
//					Iterator i = operazioni.iterator();
//					while (i.hasNext()){
//						LookupOperazioniAccettazione o = (LookupOperazioniAccettazione) i.next();
//						o.getOperazioniCondizionanti();
//						o.getOperazioniCondizionate();
//					}
////					trasf.setCartellaClinicaDestinatario(null);
////					trasf.setDataAccettazioneDestinatario(null);
//					trasfOut.setTrashedDate(new Date());
//					persistence.update(trasfOut);
//				//	persistence.commit();
//				}
				
//				Accettazione accCollegata = cc.getAccettazione();
//				accCollegata.setTrashedDate(new Date());
//				//AGGIUNGERE INFO SU UTENTE CANCELLAZIONE
//				persistence.update(accCollegata);
//			//	persistence.commit();
		//	}
			
//			
//			if (cc.getTrasferimentiByCcPostTrasf() != null && cc.getTrasferimentiByCcPostTrasf().size() > 0){
//				
//				Accettazione accCollegata = cc.getAccettazione();
//				accCollegata.setTrashedDate(new Date());
//				accCollegata.getOperazioniRichieste();
//				//AGGIUNGERE INFO SU UTENTE CANCELLAZIONE
//				persistence.update(accCollegata);
//			//	persistence.commit();
//			}
//			
			
		
				
			cc.setDataChiusura(null);
			cc.setModifiedBy(utente);
			cc.setDestinazioneAnimale(null);
			cc.setDimissioniEntered(null);
			BUtente ut = null;
			cc.setDimissioniEnteredBy(ut);
			cc.setNote(cc.getNote() + "  CC riaperta");
			persistence.update(cc);
			
			
			persistence.commit();

			setMessaggio("Cartella clinica riaperta con successo");
			redirectTo("vam.cc.dimissioni.ToEdit.us?id=" + cc.getId());
		
			
			
			
		}else {
			req.setAttribute("specie", SpecieAnimali.getInstance());
			setErrore(errorMessage);
			redirectTo("vam.cc.dimissioni.Detail.us?id=" + cc.getId());
		}
		
		
		
		
		
		

		
	}
}
