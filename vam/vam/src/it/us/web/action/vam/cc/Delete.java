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

import org.hibernate.Hibernate;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.AttivitaBdr;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.EsameCitologico;
import it.us.web.bean.vam.EsameCoprologico;
import it.us.web.bean.vam.EsameIstopatologico;
import it.us.web.bean.vam.EsameObiettivo;
import it.us.web.bean.vam.EsameSangue;
import it.us.web.bean.vam.FascicoloSanitario;
import it.us.web.bean.vam.Trasferimento;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;
import it.us.web.constants.Specie;
import it.us.web.constants.SpecieAnimali;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.vam.CaninaRemoteUtil;

public class Delete extends GenericAction implements Specie {

	@Override
	public void can() throws AuthorizationException {
		BGuiView gui = GuiViewDAO.getView("CC", "DELETE", "MAIN");
		can(gui, "w");
	}

	@Override
	public void setSegnalibroDocumentazione() {
		setSegnalibroDocumentazione("CC");
	}

	@Override
	public void execute() throws Exception {
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
				errorMessage = "Non è possibile cancellare la cartella clinica, perchè non si tratta dell'ultima cc relativa al fascicolo sanitario numero "
						+ fs.getNumero();
				// req.setAttribute("specie", SpecieAnimali.getInstance());
				// setErrore("Non è possibile cancellare la cartella clinica, perchè non si tratta dell'ultima cc relativa al fascicolo sanitario numero "
				// + fs.getNumero());
				// redirectTo("vam.cc.Detail.us?id=" + cc.getId());
			}

		}

		if (canEliminate) {
			/**
			 * Controllo BDU per recuperare presenza registrazioni collegate a
			 * questa CC, in caso affermativo blocco la cancellazione
			 */
			Set<AttivitaBdr> attivitaBdr = cc.getAttivitaBdrs();
			Iterator i = attivitaBdr.iterator();
			if (i.hasNext()) {
				Context ctx = new InitialContext();
				javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup("java:comp/env/jdbc/bduS");
				Connection connection = ds.getConnection();
				GenericAction.aggiornaConnessioneApertaSessione(req);
				while (i.hasNext()) {
					AttivitaBdr thisAttivita = (AttivitaBdr) i.next();
					if (thisAttivita.getIdRegistrazioneBdr()!=null && thisAttivita.getIdRegistrazioneBdr() > 0) {
						int idUltimaRegistrazione = CaninaRemoteUtil.getRegistrazioneBduByID(
								thisAttivita.getIdRegistrazioneBdr(), connection,req);
						if (idUltimaRegistrazione > 0) {
							canEliminate = false;
							errorMessage = "Non è possibile cancellare la cartella clinica: registrazione con id "
									+ idUltimaRegistrazione
									+ " ancora presente in BDU. Procedere alla cancellazione prima di procedere";
							// req.setAttribute("specie",
							// SpecieAnimali.getInstance());
							// setErrore("Non è possibile cancellare la cartella clinica, registrazione con id "
							// + idUltimaRegistrazione
							// +
							// " ancora presente in BDU. Procedere con la cancellazione prima di procedere");
							// redirectTo("vam.cc.Detail.us?id=" + cc.getId());
						}
					}
				}
				connection.close();
				GenericAction.aggiornaConnessioneChiusaSessione(req);
			}
		}

		if (canEliminate) {
			
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
					Set<LookupOperazioniAccettazione> operazioni = trasf.getOperazioniRichieste();
					Iterator i = operazioni.iterator();
					while (i.hasNext()){
						LookupOperazioniAccettazione o = (LookupOperazioniAccettazione) i.next();
						o.getOperazioniCondizionanti();
						o.getOperazioniCondizionate();
					}
					
					trasf.setCartellaClinicaDestinatario(null);
					trasf.setDataAccettazioneDestinatario(null);
					trasf.getOperazioniRichieste();
					trasf.setNotaDestinatario(null);
					persistence.update(trasf);
				//	persistence.commit();
				}
				
//				Accettazione accCollegata = cc.getAccettazione();
//				accCollegata.setTrashedDate(new Date());
//				accCollegata.getOperazioniRichieste();
//				//AGGIUNGERE INFO SU UTENTE CANCELLAZIONE
//				persistence.update(accCollegata);
//			//	persistence.commit();
			}
			
			
			if (cc.getTrasferimenti() != null && cc.getTrasferimenti().size() > 0){
				Set<Trasferimento> trasfListOut = cc.getTrasferimenti();
				Iterator iTrasfOut = trasfListOut.iterator();
				while (iTrasfOut.hasNext()){
					Trasferimento trasfOut = (Trasferimento) iTrasfOut.next();
					Set<LookupOperazioniAccettazione> operazioni = trasfOut.getOperazioniRichieste();
					Iterator i = operazioni.iterator();
					while (i.hasNext()){
						LookupOperazioniAccettazione o = (LookupOperazioniAccettazione) i.next();
						o.getOperazioniCondizionanti();
						o.getOperazioniCondizionate();
					}
//					trasf.setCartellaClinicaDestinatario(null);
//					trasf.setDataAccettazioneDestinatario(null);
					trasfOut.setTrashedDate(new Date());
					persistence.update(trasfOut);
				//	persistence.commit();
				}
				
//				Accettazione accCollegata = cc.getAccettazione();
//				accCollegata.setTrashedDate(new Date());
//				//AGGIUNGERE INFO SU UTENTE CANCELLAZIONE
//				persistence.update(accCollegata);
//			//	persistence.commit();
			}
			
			
			if (cc.getTrasferimentiByCcPostTrasf() != null && cc.getTrasferimentiByCcPostTrasf().size() > 0){
				
				Accettazione accCollegata = cc.getAccettazione();
				accCollegata.setTrashedDate(new Date());
				accCollegata.getOperazioniRichieste();
				//AGGIUNGERE INFO SU UTENTE CANCELLAZIONE
				persistence.update(accCollegata);
			//	persistence.commit();
			}
			
			if (cc.getEsameIstopatologicos() != null){
				Set<EsameIstopatologico> istopatologici = cc.getEsameIstopatologicos();
				Iterator i = istopatologici.iterator();
				while (i.hasNext()){
					EsameIstopatologico isto = (EsameIstopatologico) i.next();
					isto.setTrashedDate(new Date());
					persistence.update(isto);
				}
				
				
			}
			
			
			if (cc.getEsameCitologicos() != null){
				Set<EsameCitologico> citologici = cc.getEsameCitologicos();
				Iterator i = citologici.iterator();
				while (i.hasNext()){
					EsameCitologico cito = (EsameCitologico) i.next();
					cito.setTrashedDate(new Date());
					persistence.update(cito);
				}
				
			}
			
			if (cc.getEsameCoprologicos() != null){
				Set<EsameCoprologico> coprologico = cc.getEsameCoprologicos();
				Iterator i = coprologico.iterator();
				while (i.hasNext()){
					EsameCoprologico copro = (EsameCoprologico) i.next();
					copro.setTrashedDate(new Date());
					persistence.update(copro);
				}
				
			}
			
			
			
			if (cc.getEsameObiettivos() != null){
				Set<EsameObiettivo> obiettivo = cc.getEsameObiettivos();
				Iterator i = obiettivo.iterator();
				while (i.hasNext()){
					EsameObiettivo ob = (EsameObiettivo) i.next();
					ob.setTrashedDate(new Date());
					persistence.update(ob);
				}
				
			}
			
			
			
			if (cc.getEsameSangues() != null){
				Set<EsameSangue> sangue = cc.getEsameSangues();
				Iterator i = sangue.iterator();
				while (i.hasNext()){
					EsameSangue sang = (EsameSangue) i.next();
					sang.setTrashedDate(new Date());
					persistence.update(sang);
				}
				
			}
			
			
		
				
			cc.setTrashedDate(new Date());
			cc.setDeletedBy(utente);
			persistence.update(cc);
			
			
			persistence.commit();

			setMessaggio("Cartella clinica eliminata con successo");
			redirectTo("vam.cc.ToFind.us");
		} else {
			req.setAttribute("specie", SpecieAnimali.getInstance());
			setErrore(errorMessage);
			redirectTo("vam.cc.Detail.us?id=" + cc.getId());
		}
	}

}
