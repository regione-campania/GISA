/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.accettazione;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.sinantropi.Sinantropo;
import it.us.web.bean.sinantropi.lookup.LookupSinantropiEta;
import it.us.web.bean.sinantropi.lookup.LookupSpecieSinantropi;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.lookup.LookupCMI;
import it.us.web.bean.vam.lookup.LookupComuni;
import it.us.web.bean.vam.lookup.LookupMantelli;
import it.us.web.bean.vam.lookup.LookupRazze;
import it.us.web.bean.vam.lookup.LookupSpecie;
import it.us.web.bean.vam.lookup.LookupTaglie;
import it.us.web.constants.Specie;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.criterion.Order;

public class AddDecedutoNonAnagrafe extends GenericAction  implements Specie
{

	private static final SimpleDateFormat sdf = new SimpleDateFormat( "yyMMddHHmmss" );
	
	@Override
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "ACCETTAZIONE", "ADD", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("accettazione");
	}

	@Override
	public void execute() throws Exception
	{	
		Animale animale = new Animale();
		Accettazione accettazione = new Accettazione();
		
		BeanUtils.populate( animale, req.getParameterMap() );
		BeanUtils.populate( accettazione, req.getParameterMap() );

		//NUOVA GESTIONE
		String sql1 = "insert into animale (id," ;
		String sql2 = " values (nextval('animale_id_seq'),";
		//
		
		if(animale.getDataMortePresunta()==null){
			animale.setDataMortePresunta(false);
			
			//NUOVA GESTIONE
			sql1+="deceduto_non_anagrafe_data_morte_presunta,";
			sql2+=false+",";		
			
		}
		else{
			animale.setDataMortePresunta(true);
			sql1+="deceduto_non_anagrafe_data_morte_presunta,";
			sql2+=true+",";	
		}
		//
		
		animale.setIdentificativo( "D" + utente.getClinica().getId() + sdf.format( new Date() ) );
		
		// NUOVA GESTIONE		
		sql1+="identificativo,";
		sql2+="'"+animale.getIdentificativo()+"',";
		Context ctx3 = new InitialContext();
		javax.sql.DataSource ds3 = (javax.sql.DataSource)ctx3.lookup("java:comp/env/jdbc/vamM");
		Connection connection3 = ds3.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		Statement st1 = connection3.createStatement();
		ResultSet rs1;
		//
		
		int idEta = interoFromRequest( "idEta" );
		if(idEta>0){
			
			// NUOVA GESTIONE
			sql1+="eta,";
			sql2+=interoFromRequest( "idEta" )+",";
			rs1 = st1.executeQuery("select description from lookup_sinantropi_eta where id="+interoFromRequest( "idEta" ));
			if (rs1.next()){
				LookupSinantropiEta lse = new LookupSinantropiEta();
				lse.setId(interoFromRequest( "idEta" ));
				lse.setDescription(rs1.getString("description"));
				animale.setEta(lse);
			}
			//
			
		// VECCHIA GESTIONE	
		// animale.setEta((LookupSinantropiEta) persistence.find( LookupSinantropiEta.class, interoFromRequest( "idEta" )));
		}
		else
			animale.setEta( null );
		
		int idTaglia = interoFromRequest( "idTaglia" );
		if(idTaglia>0){
			animale.setTaglia(idTaglia);
			
			//NUOVA GESTIONE
			sql1+="taglia,";
			sql2+=interoFromRequest( "idTaglia" )+",";
			//
		}
		
		// VECCHIA GESTIONE
		// LookupSpecie ls = (LookupSpecie) persistence.find( LookupSpecie.class, interoFromRequest( "idSpecie" ) );
		// animale.setLookupSpecie( ls );
		
		
		// NUOVA GESTIONE
		LookupSpecie ls = new LookupSpecie();
		rs1 = st1.executeQuery("select * from lookup_specie where id="+interoFromRequest( "idSpecie" )); 
		if (rs1.next()){
			ls.setId(rs1.getInt("id"));
			ls.setDescription(rs1.getString("description"));
			ls.setEnabled(rs1.getBoolean("enabled"));
			ls.setLevel(rs1.getInt("level"));
		}
		animale.setLookupSpecie(ls);
		sql1+="specie,";
		sql2+=interoFromRequest( "idSpecie" )+",";
		//
		
		
		//Se è un Cane o un Gatto la razza ed il mantello vanno presi dalla combo, altrimenti è già stata settata col populate 
		//tramite attributi razzaSinantropo e mantelloSinantropo
		if(animale.getLookupSpecie().getId()==Specie.CANE || animale.getLookupSpecie().getId()==Specie.GATTO)
		{
			int idRazza = interoFromRequest( "idRazza" );
			if(idRazza>=0){
				animale.setRazza(idRazza);
				
				// NUOVA GESTIONE
				sql1+="razza,";
				sql2+=interoFromRequest( "idRazza" )+",";
				//
			}
			
			int idMantello=interoFromRequest("idMantello");
			if(idMantello>=0){
				animale.setMantello(idMantello);
				
				// NUOVA GESTIONE
				sql1+="mantello,";
				sql2+=interoFromRequest( "idMantello" )+",";
				//
			}
		}
		else
		{
			String idSottotipologia = stringaFromRequest("idSottotipologia");
			String u = null;
			String m = null;
			String r = null;
			Integer specieSinantropo = null;
			
			if(idSottotipologia.equals("sin"))
			{
				animale.setSpecieSinantropo(stringaFromRequest("specieSinantropoS"));
				u = stringaFromRequest("tipologiaSinantropoU");
				m = stringaFromRequest("tipologiaSinantropoM");
				r = stringaFromRequest("tipologiaSinantropoRA");
				specieSinantropo = interoFromRequest("specieSinantropoS");
			}
			else if(idSottotipologia.equals("mar"))
			{
				animale.setSpecieSinantropo(stringaFromRequest("specieSinantropoM"));
				u = stringaFromRequest("tipologiaSinantropoS");
				m = stringaFromRequest("tipologiaSinantropoMC");
				r = stringaFromRequest("tipologiaSinantropoRT");
				specieSinantropo = interoFromRequest("specieSinantropoM");
			} 
			else 
			{
				animale.setSpecieSinantropo(stringaFromRequest("specieSinantropoZ"));
				u = stringaFromRequest("tipologiaSinantropoUZ");
				m = stringaFromRequest("tipologiaSinantropoMZ");
				r = stringaFromRequest("tipologiaSinantropoRAZ");
				specieSinantropo = interoFromRequest("specieSinantropoZ");
			}
			
			String razzaSinantropo="";
			LookupSpecieSinantropi lss;
			if (!u.equals("0")){
				lss = (LookupSpecieSinantropi)persistence.find(LookupSpecieSinantropi.class, Integer.parseInt(u));
				razzaSinantropo = getRazzaSinantropo(lss);
			} else	if (!m.equals("0")){
				lss = (LookupSpecieSinantropi)persistence.find(LookupSpecieSinantropi.class, Integer.parseInt(m));
				razzaSinantropo = getRazzaSinantropo(lss);
			} else 	if (!r.equals("0")){
				lss = (LookupSpecieSinantropi)persistence.find(LookupSpecieSinantropi.class, Integer.parseInt(r));
				razzaSinantropo = getRazzaSinantropo(lss);
			}
			
			// NUOVA GESTIONE
			sql1+="specie_sinantropo,razza_sinantropo,";
			sql2+="'"+ specieSinantropo +"','"+razzaSinantropo+"',";
			//
		}
		String nomeSin ="";
		if (accettazione.getRandagio() ){		
			
			String provinciaRitrovamento = stringaFromRequest("provinciaRitrovamento");
			int comuneRitrovamento = 0; 
			
			if (provinciaRitrovamento.equals("BN"))		
				comuneRitrovamento = interoFromRequest("comuneRitrovamentoBN");
			else if (provinciaRitrovamento.equals("NA"))	
				comuneRitrovamento = interoFromRequest("comuneRitrovamentoNA");
			else if (provinciaRitrovamento.equals("SA"))	
				comuneRitrovamento = interoFromRequest("comuneRitrovamentoSA");
			else if (provinciaRitrovamento.equals("CE"))	
				comuneRitrovamento = interoFromRequest("comuneRitrovamentoCE");
			else if (provinciaRitrovamento.equals("AV"))	
				comuneRitrovamento = interoFromRequest("comuneRitrovamentoAV");
					
			LookupComuni comuneSin = (LookupComuni) persistence.find(LookupComuni.class, Integer.parseInt(stringaFromRequest("comuneSindaco")));
			nomeSin = "Sindaco del comune di "+comuneSin.getDescription();
			/*ArrayList<LookupComuni> listComuni = (ArrayList<LookupComuni>) persistence.createCriteria( LookupComuni.class ).addOrder( Order.asc( "level" ) ).list();
			
			LookupComuni lc = null;
			
			Iterator listComuniIterator = listComuni.iterator();
			
			while(listComuniIterator.hasNext()) {			
				lc = (LookupComuni) listComuniIterator.next();			
				if (lc.getId() == comuneRitrovamento) {
					System.out.println(lc.getDescription());
					animale.setComuneRitrovamento(lc);
				}
			}*/
			if(comuneRitrovamento>0) {
			//  VECCHIA GESTIONE	
			//	animale.setComuneRitrovamento((LookupComuni)persistence.find(LookupComuni.class, comuneRitrovamento));
				
			//  NUOVA GESTIONE	
				LookupComuni lc = new LookupComuni();
				rs1 = st1.executeQuery("select * from lookup_comuni where id="+comuneRitrovamento);
				if (rs1.next()){
					lc.setId(rs1.getInt("id"));
					lc.setDescription(rs1.getString("description"));
					lc.setLevel(rs1.getInt("level"));
					lc.setEnabled(rs1.getBoolean("enabled"));
					lc.setAv(rs1.getBoolean("av"));
					lc.setBn(rs1.getBoolean("bn"));
					lc.setCe(rs1.getBoolean("ce"));
					lc.setNa(rs1.getBoolean("na"));
					lc.setSa(rs1.getBoolean("sa"));
					lc.setCap(rs1.getString("cap"));
					lc.setCodiceIstat(rs1.getString("codice_istat"));
					animale.setComuneRitrovamento(lc);
				}
				sql1+="comune_ritrovamento,provincia_ritrovamento,indirizzo_ritrovamento,";
				sql2+=comuneRitrovamento+",'"+animale.getProvinciaRitrovamento()+"','"+animale.getIndirizzoRitrovamento().replaceAll("'", "''")+"',";
				if (animale.getNoteRitrovamento()!=null && !animale.getIndirizzoRitrovamento().equals("")){
					sql1+="note_ritrovamento,";
					sql2+="'"+animale.getNoteRitrovamento().replace("'", "''")+"',";
				}
			//
			}	
		}
		
		if (animale.getLookupSpecie().getId()==Specie.SINANTROPO ){		
			
			String provinciaRitrovamento = stringaFromRequest("provinciaRitrovamento");
			int comuneRitrovamento = 0; 
			
			if (provinciaRitrovamento.equals("BN"))		
				comuneRitrovamento = interoFromRequest("comuneRitrovamentoBN");
			else if (provinciaRitrovamento.equals("NA"))	
				comuneRitrovamento = interoFromRequest("comuneRitrovamentoNA");
			else if (provinciaRitrovamento.equals("SA"))	
				comuneRitrovamento = interoFromRequest("comuneRitrovamentoSA");
			else if (provinciaRitrovamento.equals("CE"))	
				comuneRitrovamento = interoFromRequest("comuneRitrovamentoCE");
			else if (provinciaRitrovamento.equals("AV"))	
				comuneRitrovamento = interoFromRequest("comuneRitrovamentoAV");
				
			if(animale.getLookupSpecie().getId()!=Specie.SINANTROPO)
			{
				LookupComuni comuneSin = (LookupComuni) persistence.find(LookupComuni.class, Integer.parseInt(stringaFromRequest("comuneSindaco")));
				nomeSin = "Sindaco del comune di "+comuneSin.getDescription();
			}
			/*ArrayList<LookupComuni> listComuni = (ArrayList<LookupComuni>) persistence.createCriteria( LookupComuni.class ).addOrder( Order.asc( "level" ) ).list();
			
			LookupComuni lc = null;
			
			Iterator listComuniIterator = listComuni.iterator();
			
			while(listComuniIterator.hasNext()) {			
				lc = (LookupComuni) listComuniIterator.next();			
				if (lc.getId() == comuneRitrovamento) {
					System.out.println(lc.getDescription());
					animale.setComuneRitrovamento(lc);
				}
			}*/
			if(comuneRitrovamento>0) {
			//  VECCHIA GESTIONE	
			//	animale.setComuneRitrovamento((LookupComuni)persistence.find(LookupComuni.class, comuneRitrovamento));
				
			//  NUOVA GESTIONE	
				LookupComuni lc = new LookupComuni();
				rs1 = st1.executeQuery("select * from lookup_comuni where id="+comuneRitrovamento);
				if (rs1.next()){
					lc.setId(rs1.getInt("id"));
					lc.setDescription(rs1.getString("description"));
					lc.setLevel(rs1.getInt("level"));
					lc.setEnabled(rs1.getBoolean("enabled"));
					lc.setAv(rs1.getBoolean("av"));
					lc.setBn(rs1.getBoolean("bn"));
					lc.setCe(rs1.getBoolean("ce"));
					lc.setNa(rs1.getBoolean("na"));
					lc.setSa(rs1.getBoolean("sa"));
					lc.setCap(rs1.getString("cap"));
					lc.setCodiceIstat(rs1.getString("codice_istat"));
					animale.setComuneRitrovamento(lc);
				}
				sql1+="comune_ritrovamento,provincia_ritrovamento,indirizzo_ritrovamento,";
				sql2+=comuneRitrovamento+",'"+animale.getProvinciaRitrovamento()+"','"+animale.getIndirizzoRitrovamento().replaceAll("'", "''")+"',";
				if (animale.getNoteRitrovamento()!=null && !animale.getIndirizzoRitrovamento().equals("")){
					sql1+="note_ritrovamento,";
					sql2+="'"+animale.getNoteRitrovamento().replace("'", "''")+"',";
				}
			//
			}	
		}

		animale.setDecedutoNonAnagrafe( true );
		animale.setInColonia( false );
		int idCdM = interoFromRequest("causaMorteIniziale");
		
		//NUOVA GESTIONE
		sql1+="deceduto_non_anagrafe,in_colonia,";
		sql2+=true+","+false+",";
		//
		
		if (idCdM != -1){
			//VECCHIA GESTIONE
			//animale.setCausaMorte((LookupCMI)persistence.find(LookupCMI.class, idCdM));
			
			//NUOVA GESTIONE
			LookupCMI lcm = new LookupCMI();
			rs1 = st1.executeQuery("select * from lookup_cause_morte_iniziali where id="+idCdM);
			if (rs1.next()){
				lcm.setId(rs1.getInt("id"));
				lcm.setCausaNaturale(rs1.getBoolean("causa_naturale"));
				lcm.setDescription(rs1.getString("description"));
				lcm.setEnabled(rs1.getBoolean("enabled"));
				lcm.setLevel(rs1.getInt("level"));
				animale.setCausaMorte(lcm);
			}
			sql1+="deceduto_non_anagrafe_causa_morte,";
			sql2+=idCdM+",";	
			//
		}
		
		//NUOVA GESTIONE
		if(animale.getDataNascita()!=null){
			sql1+="data_nascita,";
			SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );			
			String s = sdf.format(animale.getDataNascita());
			sql2+="'"+s+"',";	
		}
		
		if(animale.getDataMorte()!=null){
			sql1+="deceduto_non_anagrafe_data_morte,";
			SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );			
			String s = sdf.format(animale.getDataMorte());
			sql2+="'"+s+"',";	
		}
		
		if(animale.getDataNascitaPresunta()==null){
			animale.setDataNascitaPresunta(false);
			sql1+="data_nascita_presunta,";
			sql2+=false+",";	
		}
		else{
			animale.setDataNascitaPresunta(true);
			sql1+="data_nascita_presunta,";
			sql2+=true+",";	
		}
		
		if(animale.getSesso()!=null){
			sql1+="sesso,";
			sql2+="'"+animale.getSesso()+"',";	
		}		
			
		if (sql1.endsWith(",")){
			sql1 = sql1.substring(0,sql1.length() - 1);
			sql1+=")";
		}
		if (sql2.endsWith(",")){
			sql2 = sql2.substring(0,sql2.length() - 1);
			sql2+=")";
		}
		String finalSQL = sql1+sql2;
		st1.executeUpdate(finalSQL);
		
		rs1 = st1.executeQuery("select id from animale where identificativo='"+animale.getIdentificativo()+"'");
		if(rs1.next()){
			animale.setId(rs1.getInt("id"));
		}
		connection3.close();
		GenericAction.aggiornaConnessioneChiusaSessione(req);
		//

//		VECCHIA GESTIONE
//		persistence.insert( animale );
//		persistence.commit();
		
		String redirect =  "vam.accettazione.ToAdd.us?idAnimale=" + animale.getId();
		if (stringaFromRequest("randagio")!=null){
			redirect += "&proprietarioNome=" + nomeSin;
			redirect += "&proprietarioTipo=Sindaco";
			redirect += "&randagio=" + stringaFromRequest("randagio");
			redirect += "&comuneSindaco=" + stringaFromRequest("comuneSindaco");

		}else{
			redirect += "&proprietarioNome=" + stringaFromRequest("proprietarioNome");
			redirect += "&proprietarioCognome=" + stringaFromRequest("proprietarioCognome");
			redirect += "&proprietarioTipo=" + stringaFromRequest("proprietarioTipo");
			redirect += "&proprietarioDocumento=" + stringaFromRequest("proprietarioDocumento");
			redirect += "&proprietarioCodiceFiscale=" + stringaFromRequest("proprietarioCodiceFiscale");
			redirect += "&proprietarioCap=" + stringaFromRequest("proprietarioCap");
			redirect += "&proprietarioTelefono=" + stringaFromRequest("proprietarioTelefono");
			redirect += "&proprietarioIndirizzo=" + stringaFromRequest("proprietarioIndirizzo");
			redirect += "&proprietarioComune=" + stringaFromRequest("proprietarioComune");
			redirect += "&proprietarioProvincia=" + stringaFromRequest("proprietarioProvincia");
		}
/*		if(stringaFromRequest("randagio")!=null)
		{
			redirect += "&randagio=" + stringaFromRequest("randagio");
			redirect += "&comuneSindaco=" + stringaFromRequest("comuneSindaco");
		} */
		if(stringaFromRequest("sterilizzato")!=null)
		{
			redirect += "&sterilizzato=" + stringaFromRequest("sterilizzato");
		}
		redirectTo( redirect );
	}

	
	
	public static String getRazzaSinantropo(LookupSpecieSinantropi specieSinantropi)
	{
		
		if(specieSinantropi.getUccelloZ() || specieSinantropi.getUccello())
			return "Uccello - " + specieSinantropi.getDescription();
		else if(specieSinantropi.getMammifero() || specieSinantropi.getMammiferoZ())
			return "Mammifero - " + specieSinantropi.getDescription();
		else if(specieSinantropi.getSelaci())
			return "Selaci Chondrichthlyes - " + specieSinantropi.getDescription();
		else if(specieSinantropi.getRettileAnfibio() || specieSinantropi.getRettileAnfibioZ())
			return "Rettile/Anfibio - " + specieSinantropi.getDescription();
		else if(specieSinantropi.getMammiferoCetaceo())
			return "Mammiferi/Cetacei - " + specieSinantropi.getDescription();
		else if(specieSinantropi.getRettileTestuggine())
			return "Rettile/Tetuggine - " + specieSinantropi.getDescription();
		else
			return "";
	}
}
