/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.allerte_new.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;

import org.aspcfs.modules.accounts.base.OrganizationHistory;
import org.aspcfs.modules.actionlist.base.ActionItemLog;
import org.aspcfs.modules.actionplans.base.ActionPlan;
import org.aspcfs.modules.actionplans.base.ActionPlanWorkList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.CustomFieldRecordList;
import org.aspcfs.modules.campioni.base.Analita;
import org.aspcfs.modules.contacts.base.ContactHistory;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.allerte_new.base.TicketLogList;
import org.aspcfs.modules.allerte_new.base.TicketLog;
import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.FileItemList;

public class Ticket extends org.aspcfs.modules.troubletickets.base.Ticket
{
	
	private java.util.logging.Logger logger =   java.util.logging.Logger.getLogger("MainLogger");
	public static final String INATTIVA = "<font color=\"red\">Inattiva</font>";
	public static final String ASL_NON_COINVOLTA = "---";
	public static final String CU_NON_PIANIFICATI = "C.U. Non Pianificati";

	private String isPregresso = "no";
	private String tipo_allerta ;
	private boolean inserita_da_sian ;
	private String denominazione_prodotto ;
	private String numero_lotto ;
	private String fabbricante_produttore ;
	private Timestamp data_scadenza_allerta ;
	private boolean view_allegato_asl ;
	private String reset_sequence_roll ; 
	private String curr_val_sequence ; 
	private Boolean flag_tipo_allerta ;
	private Boolean flag_produzione ;
	private Timestamp data_fine_pubblicazione ;
	private Timestamp data_inizio_pubblicazione ;
	private String tipo_rischio ;
	private String provvedimento_esito ;
	protected HashMap<Integer, String> matrici = new HashMap<Integer, String>();

	private String note_motivazione = null;
	
	private int tipo_notifica_allerta = -1;
	private String numero_notifica_allerta = null;
	
	public int getTipo_notifica_allerta() {
		return tipo_notifica_allerta;
	}

	public void setTipo_notifica_allerta(int tipo_notifica_allerta) {
		this.tipo_notifica_allerta = tipo_notifica_allerta;
	}

	public String getNumero_notifica_allerta() {
		return numero_notifica_allerta;
	}

	public void setNumero_notifica_allerta(String numero_notifica_allerta) {
		this.numero_notifica_allerta = numero_notifica_allerta;
	}

	public boolean isFlag_produzione() {
		return flag_produzione;
	}

	public void setFlag_produzione(boolean flag) {
		this.flag_produzione = flag;
	}

	public boolean isFlagTipoAllerta() {
		return flag_tipo_allerta;
	}

	public void setFlagTipoAllerta(boolean flag) {
		this.flag_tipo_allerta = flag;
	}

	public Timestamp getData_fine_pubblicazione() {
		return data_fine_pubblicazione;
	}

	public void setData_fine_pubblicazione(String data_fine_pubblicazione) {
		Timestamp scadenza = null ;
		if (data_fine_pubblicazione!= null && ! "".equals(data_fine_pubblicazione) )
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			try {
				scadenza = new Timestamp(sdf.parse(data_fine_pubblicazione).getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.data_fine_pubblicazione = scadenza;
	}
	
	public String getData_fine_pubblicazioneString() {
		String scadenza = null ;
		if (data_fine_pubblicazione!= null  )
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			scadenza =sdf.format(new Date (data_fine_pubblicazione.getTime()));
			
		}
		return scadenza ;
	}
	
	
	public Timestamp getData_inizio_pubblicazione() {
		return data_inizio_pubblicazione;
	}

	public void setData_inizio_pubblicazione(String data_inizio_pubblicazione) {
		Timestamp scadenza = null ;
		if (data_inizio_pubblicazione!= null && ! "".equals(data_inizio_pubblicazione) )
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			try {
				scadenza = new Timestamp(sdf.parse(data_inizio_pubblicazione).getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.data_inizio_pubblicazione = scadenza;
	}
	
	public String getData_inizio_pubblicazioneString() {
		String scadenza = null ;
		if (data_inizio_pubblicazione!= null  )
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			scadenza =sdf.format(new Date (data_inizio_pubblicazione.getTime()));
			
		}
		return scadenza ;
	}
	

	public String getTipo_rischio() {
		return tipo_rischio;
	}

	public HashMap<Integer, String> getMatrici() {
		return matrici;
	}

	public void setMatrici(HashMap<Integer, String> matrici) {
		this.matrici = matrici;
	}

	public void setTipo_rischio(String tipo_rischio) {
		this.tipo_rischio = tipo_rischio;
	}


	public String getProvvedimento_esito() {
		return provvedimento_esito;
	}


	public void setProvvedimento_esito(String provvedimento_esito) {
		this.provvedimento_esito = provvedimento_esito;
	}


	public String getCurr_val_sequence() {
		return curr_val_sequence;
	}


	public void setCurr_val_sequence() {
		
		if (tipo_allerta.equalsIgnoreCase("Entrata"))
		{
			if (inserita_da_sian==true)
			{
				reset_sequence_roll = "select setval('seq_identificativo_allerta_entrata_sian', (select last_value from seq_identificativo_allerta_entrata_sian)-1)"; 
				curr_val_sequence = "select last_value from seq_identificativo_allerta_entrata_sian";
			}
			else
			{
				reset_sequence_roll = "select setval('seq_identificativo_allerta_entrata_vet', (select last_value from seq_identificativo_allerta_entrata_vet)-1)"; 
				curr_val_sequence = "select last_value from seq_identificativo_allerta_entrata_vet";
			}
		}
		else
		{
			if (tipo_allerta.equalsIgnoreCase("Uscita"))
			{
				if (inserita_da_sian==true)
				{
					reset_sequence_roll = "select setval('seq_identificativo_allerta_uscita_sian', (select last_value from seq_identificativo_allerta_uscita_sian)-1)"; 
					curr_val_sequence = "select last_value from seq_identificativo_allerta_uscita_sian";
				}
				else
				{
					reset_sequence_roll = "select setval('seq_identificativo_allerta_uscita_vet', (select last_value from seq_identificativo_allerta_uscita_vet)-1)"; 
					curr_val_sequence = "select last_value from seq_identificativo_allerta_uscita_vet";

				}

			}

		}
	
	}


	public String getDenominazione_prodotto() {
		return denominazione_prodotto;
	}


	public void setDenominazione_prodotto(String denominazione_prodotto) {
		this.denominazione_prodotto = denominazione_prodotto;
	}


	public String getNumero_lotto() {
		return numero_lotto;
	}


	public void setNumero_lotto(String numero_lotto) {
		this.numero_lotto = numero_lotto;
	}


	public String getFabbricante_produttore() {
		return fabbricante_produttore;
	}


	public void setFabbricante_produttore(String fabbricante_produttore) {
		this.fabbricante_produttore = fabbricante_produttore;
	}


	public Timestamp getData_scadenza_allerta() {
		return data_scadenza_allerta;
	}


	public void setData_scadenza_allerta(String data_scadenza_allerta) {
		Timestamp scadenza = null ;
		if (data_scadenza_allerta!= null && ! "".equals(data_scadenza_allerta) )
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			try {
				scadenza = new Timestamp(sdf.parse(data_scadenza_allerta).getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.data_scadenza_allerta = scadenza;
	}


	public String getTipo_allerta() {
		return tipo_allerta;
	}


	public void setTipo_allerta(String tipo_allerta) {
		this.tipo_allerta = tipo_allerta;
	}


	public boolean isInserita_da_sian() {
		return inserita_da_sian;
	}


	public void setInserita_da_sian(boolean inserita_da_sian) {
		this.inserita_da_sian = inserita_da_sian;
	}
	public boolean chiusuraUfficio = false;
	public boolean isChiusuraUfficio() {
		return chiusuraUfficio;
	}


	public void updateIsPregresso(Connection db) throws SQLException{

		String sql = "update ticket set ispregresso = '"+isPregresso+"'";
		PreparedStatement pst = null ;
		pst = db.prepareStatement(sql);
		pst.execute();

	}


	public String getIsPregresso() {
		return isPregresso;
	}

	public void setIsPregresso(String isPregresso) {
		this.isPregresso = isPregresso;
	}

	public void setChiusuraUfficio(boolean chiusuraUfficio) {
		this.chiusuraUfficio = chiusuraUfficio;
	}
	/**
	 * 1
	 */
	public static final String ATTIVA				= "<font color=\"green\">Attiva</font>";
	/**
	 * 2
	 */
	public static final String CONTROLLI_IN_CORSO	= "<font color=\"#FF9933\">Controlli in Corso</font>";
	/**
	 * 3
	 */
	public static final String CONTROLLI_COMPLETATI	= "<font color=\"#0000FF\">Controlli Completati</font>";
	/**
	 * 4
	 */
	public static final String CHIUSA				= "<font color=\"red\">Chiusa</font>";
	/**
	 * 5
	 */
	public static final String RIPIANIFICAZIONE		= "<font color=\"#FF9933\">Controlli in Corso - R</font>";

	
	public static final String CONTROLLI_COMPLETATI_R	= "<font color=\"#0000FF\">Controlli Completati - R</font>";


	public Ticket()
	{
	
		
	}
	protected ArrayList<Analita> tipi_campioni = new ArrayList<Analita>();
	protected String dataAperturaTimeZone = null;
	protected HashMap<Integer, String> tipiCampioni=new HashMap<Integer, String>();
	protected HashMap<Integer, String> tipiAlimentiVegetali=new HashMap<Integer, String>();
	public HashMap<Integer, String> getTipiAlimentiVegetali() {
		return tipiAlimentiVegetali;
	}

	public void setTipiAlimentiVegetali(
			HashMap<Integer, String> tipiAlimentiVegetali) {
		this.tipiAlimentiVegetali = tipiAlimentiVegetali;
	}

	public ArrayList<Analita> getTipi_Campioni() {
		return tipi_campioni;
	}

	public void setTipi_Campioni(ArrayList<Analita> tipiCampioni) {
		this.tipi_campioni = tipiCampioni;
	}
	
	private int altrialimenti=-1;

	public int getAltrialimenti() {
		return altrialimenti;
	}

	public void setAltrialimenti(int altrialimenti) {
		this.altrialimenti = altrialimenti;
	}


	public String getDataAperturaTimeZone() {
		return dataAperturaTimeZone;
	}

	public void setDataAperturaTimeZone(String tmp) {
		this.dataAperturaTimeZone = tmp;
	}

	private boolean altriAlimenti=false;
	private boolean mangimi=false;
	private int unitaMisura;



	public int getUnitaMisura() {
		return unitaMisura;
	}


	public void setUnitaMisura(int unitaMisura) {
		this.unitaMisura = unitaMisura;
	}


	public boolean isMangimi() {
		return mangimi;
	}


	public void setMangimi(boolean mangimi) {
		this.mangimi = mangimi;
	}


	public boolean isAltriAlimenti() {
		return altriAlimenti;
	}

	public void setAltriAlimenti(boolean altriAlimenti) {
		this.altriAlimenti = altriAlimenti;
	}


	private int isvegetaletrasformato=-1;

	public int getIsvegetaletrasformato() {
		return isvegetaletrasformato;
	}

	public void setIsvegetaletrasformato(int isvegetaletrasformato) {
		this.isvegetaletrasformato = isvegetaletrasformato;
	}

	public void getTipoAlimentiVegetali(Connection db)throws SQLException{


		String sql="";



		sql="select code,description from tipoalimentioriginevegetale,lookup_alimenti_origine_vegetale_valori where code=idalimento and idticket=? ";

		PreparedStatement pst = db.prepareStatement(sql);
		pst.setInt(1, id);
		ResultSet rs=pst.executeQuery();
		int code=0;
		while(rs.next()){
			code=rs.getInt("code");
			String value=rs.getString("description");
			tipiAlimentiVegetali.put(code, value);




		}}




	public void insertAlimentiOrigineVegetale(Connection db,String[] tipi)throws SQLException{

		String sql="insert into tipoalimentioriginevegetale(idticket,idalimento) values (?,?) ";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setInt(1, id);
		if(tipi!=null){
			for(int i=0;i<tipi.length;i++){
				int idCamp=Integer.parseInt(tipi[i]);
				pst.setInt(2, idCamp);
				pst.execute();
			}}

	}

	private boolean dolciumi=false;
	private int specieAlimentoZootecnico = -1;
	private int tipologiaAlimentoZootecnico=-1;


	public int getSpecieAlimentoZootecnico() {
		return specieAlimentoZootecnico;
	}


	public void setSpecieAlimentoZootecnico(int specieAlimentoZootecnico) {
		this.specieAlimentoZootecnico = specieAlimentoZootecnico;
	}


	public int getTipologiaAlimentoZootecnico() {
		return tipologiaAlimentoZootecnico;
	}


	public void setTipologiaAlimentoZootecnico(int tipologiaAlimentoZootecnico) {
		this.tipologiaAlimentoZootecnico = tipologiaAlimentoZootecnico;
	}


	public boolean getDolciumi() {
		return dolciumi;
	}

	public void setDolciumi(boolean flag){
		dolciumi=flag;

	}


	private boolean gelati=false;

	public boolean getGelati() {
		return gelati;
	}

	public void setGelati(boolean flag){
		gelati=flag;

	}



	public void aggiornaAlimentiOrigineVegetale(Connection db,String[] tipi)throws SQLException{

		String sql="delete from tipoalimentioriginevegetale where idticket=? ";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setInt(1, id);
		pst.execute();
		insertAlimentiOrigineVegetale(db,tipi);



	}

	protected HashMap<Integer, String> tipiChimiciSelezionati=new HashMap<Integer, String>();
	public HashMap<Integer, String> getTipiChimiciSelezionati() {
		return tipiChimiciSelezionati;
	}

	public void setTipiChimiciSelezionati(
			HashMap<Integer, String> tipiChimiciSelezionati) {
		this.tipiChimiciSelezionati = tipiChimiciSelezionati;
	}


	private int tipoAcque=-1;
	private String descrizionetipoAcqua="";
	public String getDescrizionetipoAcqua() {
		return descrizionetipoAcqua;
	}

	public void setDescrizionetipoAcqua(Connection db) throws SQLException {
		try{

			String sql="select description from lookup_acqua where code="+tipoAcque;

			PreparedStatement pst=db.prepareStatement(sql);
			ResultSet rs=pst.executeQuery();
			if(rs.next())
				descrizionetipoAcqua=rs.getString(1);


		}catch(Exception e){

			e.printStackTrace();
		}
	}



	private int tipoCampione=-1;
	public int getTipoCampione() {
		return tipoCampione;
	}

	public void setTipoCampione(String tipoCampione) {
		try {
			this.tipoCampione = Integer.parseInt(tipoCampione);
		} catch (Exception e) {
			this.tipoCampione = -1;
		}
	}














	public int getAlimentiOrigineAnimaleNonTrasformatiValori() {
		return alimentiOrigineAnimaleNonTrasformatiValori;
	}

	public void setAlimentiOrigineAnimaleNonTrasformatiValori(int alimentiOrigineAnimaleNonTrasformatiValori) {
		this.alimentiOrigineAnimaleNonTrasformatiValori = alimentiOrigineAnimaleNonTrasformatiValori;
	}

	public void setAlimentiOrigineAnimaleNonTrasformati(String temp)
	{
		this.alimentiOrigineAnimaleNonTrasformati = Integer.parseInt(temp);
	}

	public void setAlimentiOrigineAnimaleTrasformati(String temp)
	{
		this.alimentiOrigineAnimaleTrasformati = Integer.parseInt(temp);
	}

	public void setAlimentiOrigineVegetaleValori(String temp)
	{
		this.alimentiOrigineVegetaleValori = Integer.parseInt(temp);
	}

	public void setAlimentiOrigineAnimaleNonTrasformatiValori(String temp)
	{
		this.alimentiOrigineAnimaleNonTrasformatiValori = Integer.parseInt(temp);
	}

	public void setAlimentiOrigineAnimale(String tmp){
		this.alimentiOrigineAnimale = DatabaseUtils.parseBoolean(tmp);	
	}

	public void setAlimentiComposti(String tmp){
		this.alimentiComposti = DatabaseUtils.parseBoolean(tmp);	
	}

	public void setAlimentiOrigineVegetale(String tmp){
		this.alimentiOrigineVegetale = DatabaseUtils.parseBoolean(tmp);	
	}


	private boolean alimentiAcqua=false;
	public boolean isAlimentiOrigineAnimale() {
		return alimentiOrigineAnimale;
	}

	public boolean isAlimentiAcqua() {
		return alimentiAcqua;
	}

	public boolean getAlimentiAcqua() {
		return alimentiAcqua;
	}

	public void setAlimentiAcqua(boolean alimentiAcqua) {
		this.alimentiAcqua = alimentiAcqua;
	}

	private boolean alimentiBevande=false;
	public boolean isAlimentiBevande() {
		return alimentiBevande;
	}
	public boolean getAlimentiBevande() {
		return alimentiBevande;
	}

	public void setAlimentiBevande(boolean alimentiBevande) {
		this.alimentiBevande = alimentiBevande;
	}

	private boolean alimentiAdditivi=false;

	public boolean isAlimentiAdditivi() {
		return alimentiAdditivi;
	}
	public boolean getAlimentiAdditivi() {
		return alimentiAdditivi;
	}

	public void setAlimentiAdditivi(boolean alimentiAdditivi) {
		this.alimentiAdditivi = alimentiAdditivi;
	}
	private boolean materialiAlimenti=false;

	public boolean isMaterialiAlimenti() {
		return materialiAlimenti;
	}
	public boolean getMaterialiAlimenti() {
		return materialiAlimenti;
	}

	public void setMaterialiAlimenti(boolean materialiAlimenti) {
		this.materialiAlimenti = materialiAlimenti;
	}

	public boolean getAlimentiOrigineAnimale(){
		return alimentiOrigineAnimale;
	}

	public void setAlimentiOrigineAnimale(boolean alimentiOrigineAnimale) {
		this.alimentiOrigineAnimale = alimentiOrigineAnimale;
	}

	public boolean isAlimentiOrigineVegetale() {
		return alimentiOrigineVegetale;
	}

	public boolean getAlimentiOrigineVegetale(){
		return alimentiOrigineVegetale;
	}

	public void setAlimentiOrigineVegetale(boolean alimentiOrigineVegetale) {
		this.alimentiOrigineVegetale = alimentiOrigineVegetale;
	}

	public boolean isAlimentiComposti() {
		return alimentiComposti;
	}

	public boolean getAlimentiComposti(){
		return alimentiComposti;
	}

	public void setAlimentiComposti(boolean alimentiComposti) {
		this.alimentiComposti = alimentiComposti;
	}

	public int getAlimentiOrigineAnimaleNonTrasformati() {
		return alimentiOrigineAnimaleNonTrasformati;
	}

	public void setAlimentiOrigineAnimaleNonTrasformati(
			int alimentiOrigineAnimaleNonTrasformati) {
		this.alimentiOrigineAnimaleNonTrasformati = alimentiOrigineAnimaleNonTrasformati;
	}

	public int getAlimentiOrigineAnimaleTrasformati() {
		return alimentiOrigineAnimaleTrasformati;
	}

	public void setAlimentiOrigineAnimaleTrasformati(
			int alimentiOrigineAnimaleTrasformati) {
		this.alimentiOrigineAnimaleTrasformati = alimentiOrigineAnimaleTrasformati;
	}

	public int getAlimentiOrigineVegetaleValori() {
		return alimentiOrigineVegetaleValori;
	}

	public void setAlimentiOrigineVegetaleValori(int alimentiOrigineVegetaleValori) {
		this.alimentiOrigineVegetaleValori = alimentiOrigineVegetaleValori;
	}














	public int getTipoAcque() {
		return tipoAcque;
	}

	public void setTipoAcque(int tipoAcque) {
		this.tipoAcque = tipoAcque;
	}

	private String noteAlimenti="";
	private String noteAnalisi="";

	/* aggiunti da d.dauria */
	private boolean alimentiOrigineAnimale = false;
	private boolean alimentiOrigineVegetale = false;
	private boolean alimentiComposti = false;
	private int alimentiOrigineAnimaleNonTrasformati = -1;
	private int alimentiOrigineAnimaleTrasformati = -1;
	private int alimentiOrigineVegetaleValori = -1;
	private int alimentiOrigineAnimaleNonTrasformatiValori = -1;

	protected int tipSpecie_latte=-1;
	protected int tipSpecie_uova=-1;
	protected String tipoSpecieLatte_descrizione="";
	protected String tipoSpecieUova_descrizione="";

	public int getTipSpecie_latte() {
		return tipSpecie_latte;
	}

	public void setTipSpecie_latte(int tipSpecie_latte) {
		this.tipSpecie_latte = tipSpecie_latte;
	}

	protected String testoAlimentoComposto="";

	public String getTestoAlimentoComposto() {
		return testoAlimentoComposto;
	}

	public void setTestoAlimentoComposto(String testoAlimentoComposto) {
		this.testoAlimentoComposto = testoAlimentoComposto;
	}


	public int getTipSpecie_uova() {
		return tipSpecie_uova;
	}

	public void setTipSpecie_uova(int tipSpecie_uova) {
		this.tipSpecie_uova = tipSpecie_uova;
	}

	public String getTipoSpecieLatte_descrizione() {
		return tipoSpecieLatte_descrizione;
	}

	public void setTipoSpecieLatte_descrizione(String tipoSpecieLatte_descrizione) {
		this.tipoSpecieLatte_descrizione = tipoSpecieLatte_descrizione;
	}

	public String getTipoSpecieUova_descrizione() {
		return tipoSpecieUova_descrizione;
	}


	public void setDescrizioneSpecieLatte(Connection db) throws SQLException{


		String sql="select description from lookup_alimenti_origine_animale_non_trasformati_specielatte where code="+tipSpecie_latte;
		PreparedStatement pst=db.prepareStatement(sql);
		ResultSet rs=pst.executeQuery();
		if(rs.next()){
			tipoSpecieLatte_descrizione=rs.getString(1);

		}


	}

	public void setViewAllegatoF(Connection db,String codiceAllerta,AslCoinvolte asl) throws SQLException{


		/*String sql=	"select count(ticketid) as num from ticket where trashed_date is null and tipologia = 3" +
					" and site_id ="+asl.getId_asl() +" and codice_allerta ilike ?";
		PreparedStatement pst=db.prepareStatement(sql);
		pst.setString(1, codiceAllerta);
		ResultSet rs=pst.executeQuery();
		int numcu = 0 ;
		if(rs.next()){
			numcu = rs.getInt("num");
			
		}
		asl.setNumCuEseguiti(numcu);
		*/
		
		if (asl.getCu_eseguiti()==0)
			asl.setView_allegato_asl(false);
		else
			asl.setView_allegato_asl(true);
		
	}


	public void setDescrizioneSpecieUova(Connection db) throws SQLException{


		String sql="select description from lookup_alimenti_origine_animale_non_trasformati_specieuova where code="+tipSpecie_uova;
		PreparedStatement pst=db.prepareStatement(sql);
		ResultSet rs=pst.executeQuery();
		if(rs.next()){
			tipoSpecieUova_descrizione=rs.getString(1);

		}
	}

	public void setTipoSpecieUova_descrizione(String tipoSpecieUova_descrizione) {
		this.tipoSpecieUova_descrizione = tipoSpecieUova_descrizione;
	}
	protected String tipo_richiesta = "";
	protected int tipologia = -1;
	//protected String dati_extra = "";
	protected String pippo = "";
	protected int provvedimenti = -1;
	protected int sanzioniAmministrative = -1;
	protected int sanzioniPenali = -1;
	protected int sequestri = -1;
	protected String descrizione1 = "";
	protected String descrizione2 = "";
	protected String descrizione3 = "";

	//aggiunto da d.dauria per gestire i sequestri
	private boolean tipoSequestro = false;
	private boolean tipoSequestroDue = false;
	private boolean tipoSequestroTre = false;
	private boolean tipoSequestroQuattro = false; 
	private String testoAppoggio = "";
	private String idAllerta = null;

	//aggiunto da d.dauria per gestire le allerte
	private java.sql.Timestamp dataApertura = null;
	private java.sql.Timestamp dataEventualeRevoca = null;
	private java.sql.Timestamp dataChiusura = null;
	protected int progressivoAllerta = -1;
	protected int tipoAlimento = -1;
	public String getDescrizioneBreve() {
		return descrizioneBreve;
	}

	public void setDescrizioneBreve(String descrizioneBreve) {
		this.descrizioneBreve = descrizioneBreve;
	}

	protected int origine = -1;
	protected int alimentoInteressato = -1;
	protected int nonConformita = -1;
	protected int listaCommercializzazione = -1;
	private Hashtable<String, AslCoinvolte> asl_coinvolte;
	private Hashtable<String, ImpreseCoinvolte> imprese_coinvolte;

	public Hashtable<String, ImpreseCoinvolte> getImprese_coinvolte() {
		return imprese_coinvolte;
	}


	public void setImprese_coinvolte(
			Hashtable<String, ImpreseCoinvolte> imprese_coinvolte) {
		this.imprese_coinvolte = imprese_coinvolte;
	}
	private String descrizioneBreve="";



	public Hashtable<String, AslCoinvolte> getAsl_coinvolte()
	{
		return asl_coinvolte;
	}

	public String getAsl_coinvolteAsString()
	{
		String ret = "";

		Enumeration<String> e = asl_coinvolte.keys(); 
		while( e.hasMoreElements() )
		{
			String key = e.nextElement();
			if( ret.length() > 0 )
			{
				ret += ", ";
			}
			ret += key;
		}

		return ret;
	}

	public void setAsl_coinvolte(Hashtable<String, AslCoinvolte> asl_coinvolte)
	{
		
		this.asl_coinvolte = asl_coinvolte;
	}

	private Hashtable<Integer, ListaDistribuzione> liste_distribuzione;
	public Hashtable<Integer, ListaDistribuzione> getListe_distribuzione()
	{
		return liste_distribuzione;
	}

	public void setListe_distribuzione(Hashtable<Integer, ListaDistribuzione> liste_distribuzione)
	{
		this.liste_distribuzione = liste_distribuzione;
	}

	public void setProgressivoAllerta(String progressivoAllerta) {
		try {
			this.progressivoAllerta = Integer.parseInt(progressivoAllerta);
		} catch (Exception e) {
			this.progressivoAllerta = -1;
		}
	}


	public void setTipoAlimento(String tipoAlimento) {
		try {
			this.tipoAlimento = Integer.parseInt(tipoAlimento);
		} catch (Exception e) {
			this.tipoAlimento = -1;
		}
	}

	public void setOrigine(String origine) {
		try {
			this.origine = Integer.parseInt(origine);
		} catch (Exception e) {
			this.origine = -1;
		}
	}

	public void setAlimentoInteressato(String alimentoInteressato) {
		try {
			this.alimentoInteressato = Integer.parseInt(alimentoInteressato);
		} catch (Exception e) {
			this.alimentoInteressato = -1;
		}
	}

	public void setTipoAcqueValue(int value){
		this.tipoAcque=value;

	}
	public String getNoteAnalisi() {
		return noteAnalisi;
	}

	public void setNoteAnalisi(String noteAnalisi) {
		this.noteAnalisi = noteAnalisi;
	}


	public HashMap<Integer, String> getTipiCampioni() {
		return tipiCampioni;
	}
	public String getNoteAlimenti() {
		return noteAlimenti;
	}

	public void setNoteAlimenti(String noteAlimenti) {
		this.noteAlimenti = noteAlimenti;
	}

	public void setNonConformita(String nonConformita) {
		try {
			this.nonConformita = Integer.parseInt(nonConformita);
		} catch (Exception e) {
			this.nonConformita = -1;
		}
	}

	public void setListaCommercializzazione(String listaCommercializzazione) {
		try {
			this.listaCommercializzazione = Integer.parseInt(listaCommercializzazione);
		} catch (Exception e) {
			this.listaCommercializzazione = -1;
		}
	}

	public java.sql.Timestamp getDataApertura() {
		return dataApertura;
	}

	public void setDataApertura(java.sql.Timestamp dataApertura) {
		this.dataApertura = dataApertura;
	}

	public void setDataApertura(String tmp) {
		this.dataApertura = DatabaseUtils.parseDateToTimestamp(tmp, Locale.ITALIAN);
	}

	public java.sql.Timestamp getDataEventualeRevoca() {
		return dataEventualeRevoca;
	}

	public void setDataEventualeRevoca(java.sql.Timestamp dataEventualeRevoca) {
		this.dataEventualeRevoca = dataEventualeRevoca;
	}

	public void setDataEventualeRevoca(String tmp) {
		this.dataEventualeRevoca = DatabaseUtils.parseDateToTimestamp(tmp, Locale.ITALIAN);
	}

	public java.sql.Timestamp getDataChiusura() {
		return dataChiusura;
	}

	public void setDataChiusura(java.sql.Timestamp dataChiusura) {
		this.dataChiusura = dataChiusura;
	}

	public void setDataChiusura(String tmp) {
		this.dataChiusura = DatabaseUtils.parseDateToTimestamp(tmp, Locale.ITALIAN);
	}

	public int getProgressivoAllerta() {
		return progressivoAllerta;
	}

	public void setProgressivoAllerta(int progressivoAllerta) {
		this.progressivoAllerta = progressivoAllerta;
	}

	public int getTipoAlimento() {
		return tipoAlimento;
	}

	public void setTipoAlimento(int tipoAlimento) {
		this.tipoAlimento = tipoAlimento;
	}

	private int origineAllerta=-1;

	public int getOrigineAllerta() {
		return origineAllerta;
	}

	public void setOrigineAllerta(int origineAllerta) {
		this.origineAllerta = origineAllerta;
	}

	public int getOrigine() {
		return origine;
	}

	public void setOrigine(int origine) {
		this.origine = origine;
	}

	public int getAlimentoInteressato() {
		return alimentoInteressato;
	}

	public void setAlimentoInteressato(int alimentoInteressato) {
		this.alimentoInteressato = alimentoInteressato;
	}

	public int getNonConformita() {
		return nonConformita;
	}

	public void setNonConformita(int nonConformita) {
		this.nonConformita = nonConformita;
	}

	public int getListaCommercializzazione() {
		return listaCommercializzazione;
	}

	public void setListaCommercializzazione(int listaCommercializzazione) {
		this.listaCommercializzazione = listaCommercializzazione;
	}

	public boolean getTipoSequestro() {
		return tipoSequestro;
	}

	public void setTipoSequestro(boolean tipoSequestro) {
		this.tipoSequestro = tipoSequestro;
	}

	public void setTipoSequestro(String temp) {
		this.tipoSequestro = DatabaseUtils.parseBoolean(temp);
	}

	public boolean getTipoSequestroDue() {
		return tipoSequestroDue;
	}

	public void setTipoSequestroDue(boolean tipoSequestroDue) {
		this.tipoSequestroDue = tipoSequestroDue;
	}

	public void setTipoSequestroDue(String temp) {
		this.tipoSequestroDue = DatabaseUtils.parseBoolean(temp);
	}

	public boolean getTipoSequestroTre() {
		return tipoSequestroTre;
	}

	public void setTipoSequestroTre(boolean tipoSequestroTre) {
		this.tipoSequestroTre = tipoSequestroTre;
	}

	public void setTipoSequestroTre(String temp) {
		this.tipoSequestroTre = DatabaseUtils.parseBoolean(temp);
	}

	public boolean getTipoSequestroQuattro() {
		return tipoSequestroQuattro;
	}

	public void setIdAllerta(String temp) {
		this.idAllerta = temp;
	}

	public String getIdAllerta() {
		return idAllerta;
	}

	public void setTipoSequestroQuattro(boolean tipoSequestroQuattro) {
		this.tipoSequestroQuattro = tipoSequestroQuattro;
	}

	public void setTipoSequestroQuattro(String temp) {
		this.tipoSequestroQuattro = DatabaseUtils.parseBoolean(temp);
	}



	public String getPippo() {
		return pippo;
	}

	public void setPippo(String pippo) {
		this.pippo = pippo;
	}

	public int getTipologia() {
		return tipologia;
	}

	public void setTipologia(int tipologia) {
		this.tipologia = tipologia;
	}

	public int getProvvedimenti() {
		return provvedimenti;
	}

	public void setProvvedimenti(String provvedimenti) {
		try {
			this.provvedimenti = Integer.parseInt(provvedimenti);
		} catch (Exception e) {
			this.provvedimenti = -1;
		}
	}

	public void setSanzioniAmministrative(String sanzioniAmministrative) {
		try {
			this.sanzioniAmministrative = Integer.parseInt(sanzioniAmministrative);
		} catch (Exception e) {
			this.sanzioniAmministrative = -1;
		}
	}

	public void setSanzioniPenali(String sanzioniPenali) {
		try {
			this.sanzioniPenali = Integer.parseInt(sanzioniPenali);
		} catch (Exception e) {
			this.sanzioniPenali = -1;
		}
	}

	public void setSequestri(String sequestri) {
		try {
			this.sequestri = Integer.parseInt(sequestri);
		} catch (Exception e) {
			this.sequestri = -1;
		}
	}

	public void setProvvedimenti(int provvedimenti) {
		this.provvedimenti = provvedimenti;
	}

	public int getSanzioniAmministrative() {
		return sanzioniAmministrative;
	}

	public void setSanzioniAmministrative(int sanzioniAmministrative) {
		this.sanzioniAmministrative = sanzioniAmministrative;
	}

	public int getSanzioniPenali() {
		return sanzioniPenali;
	}

	public void setSanzioniPenali(int sanzioniPenali) {
		this.sanzioniPenali = sanzioniPenali;
	}

	public int getSequestri() {
		return sequestri;
	}

	public void setSequestri(int sequestri) {
		this.sequestri = sequestri;
	}


	public String getTipo_richiesta() {
		return tipo_richiesta;
	}

	public void setTipo_richiesta(String tipo_richiesta) {
		this.tipo_richiesta = tipo_richiesta;
	}

	public Ticket(ResultSet rs) throws SQLException {
		
		buildRecord(rs);
	}

	public Ticket(Connection db, int id) throws SQLException {
		
		queryRecord(db, id);
	}

	public String getDescrizione1() {
		return descrizione1;
	}

	public void setDescrizione1(String descrizione1) {
		this.descrizione1 = descrizione1;
	}

	public String getDescrizione2() {
		return descrizione2;
	}

	public void setDescrizione2(String descrizione2) {
		this.descrizione2 = descrizione2;
	}
	public String getDescrizione3() {
		return descrizione3;
	}

	public void setDescrizione3(String descrizione3) {
		this.descrizione3 = descrizione3;
	}




	public void insertTipoCampione(Connection db,String[] tipi)throws SQLException{
		if(tipi!=null){
			String sql="insert into tipicampioni (idticket,idcampionetipo) values (?,?) ";
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setInt(1, id);
			for(int i=0;i<tipi.length;i++){
				int idCamp=Integer.parseInt(tipi[i]);
				pst.setInt(2, idCamp);
				pst.execute();
			}
		}


	}


	public void insertTipoChimico(Connection db,String[] tipi)throws SQLException{

		String sql="insert into tipocampionechimico (idticket,idesamechimicotipo) values (?,?) ";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setInt(1, id);
		if(tipi!=null){
			for(int i=0;i<tipi.length;i++){
				int idCamp=Integer.parseInt(tipi[i]);
				pst.setInt(2, idCamp);
				pst.execute();
			}}



	}




	public void aggiornatipiCampioni(Connection db,String[] tipi,String[] tipiChimici) throws SQLException{
		String remove="delete from tipicampioni where idticket="+id;
		PreparedStatement ps=db.prepareStatement(remove);
		ps.execute();
		this.insertTipoCampione(db, tipi);

		if(tipiChimici!=null){
			String remove1="delete from tipocampionechimico where idticket="+id;
			PreparedStatement ps1=db.prepareStatement(remove1);
			ps1.execute();


			this.insertTipoChimico(db, tipiChimici);
		}

	}



	




	public void queryRecord(Connection db, int id) throws SQLException {
		if (id == -1) {
			throw new SQLException("Invalid Ticket Number");
		}
		PreparedStatement pst = db.prepareStatement(
				"SELECT t.* ,al.tipo_esame," +
				"o.name AS orgname, " +
				"o.enabled AS orgenabled, " +
				"o.site_id AS orgsiteid " +
				"FROM ticket t " +
				"LEFT JOIN organization o ON (t.org_id = o.org_id) " +
		        "LEFT JOIN analiti_allerte al on al.id_allerta = t.ticketid "+
		"WHERE t.ticketid = ? AND t.tipologia = 700 "); //modificata la tipologia. Messa = 700
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			buildRecord(rs);
//			this.setDescrizionetipoAcqua(db);
//			this.getTipoCampioneSelezionato(db);
//			this.getTipoAlimentiVegetali(db);
//			this.setDescrizioneSpecieLatte(db);
//			this.setDescrizioneSpecieUova(db);
			this.getMatriceCampioneSelezionatoNuovaGestione(db);
			this.getTipoCampioneSelezionatoNuovaGestione(db);
		}
		rs.close();
		pst.close();
		if (this.id == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}
	
	
		Hashtable<Integer, ListaDistribuzione> liste_distribuzione = null;
		if (listaCommercializzazione==2)
			liste_distribuzione = ListaDistribuzione.getListeDistribuzioneSenza( id, idAllerta, db );
		else
			liste_distribuzione = ListaDistribuzione.getListeDistribuzione( id, idAllerta, db );
		
		Iterator<Integer> itkey2 = liste_distribuzione.keySet().iterator();
		while(itkey2.hasNext())
		{
			Integer key = itkey2.next();
			liste_distribuzione.put(key, liste_distribuzione.get(key));
			}
		this.setListe_distribuzione(liste_distribuzione);
		
	//DA CANCELLARE
//		Hashtable<String, AslCoinvolte> asl_coinvolte = AslCoinvolte.getAslConvolte( id,idAllerta,true, db );
//		Iterator<String> itkey = asl_coinvolte.keySet().iterator();
//		while(itkey.hasNext())
//		{
//			String key = itkey.next();
//			
//			setViewAllegatoF(db, this.idAllerta, asl_coinvolte.get(key));
//			asl_coinvolte.put(key, asl_coinvolte.get(key));
//			
//			
//		}
//		this.setAsl_coinvolte( asl_coinvolte);
//		this.setImprese_coinvolte(ImpreseCoinvolte.getImpreseCoinvoteAllAsl(db,id));
//		
		
		
	}

	public boolean insert(Connection db,ActionContext context) throws SQLException {
		StringBuffer sql = new StringBuffer();
		boolean commit = db.getAutoCommit();
		try {
			if (commit) {
				db.setAutoCommit(false);
			}
			
			
			UserBean user = (UserBean)context.getSession().getAttribute("User");
			int livello=1 ;
			if (user.getUserRecord().getGruppo_ruolo()==2)
				livello=2;
			id = DatabaseUtils.getNextInt( db, "ticket","ticketid",livello);
			
			sql.append("INSERT INTO ticket (problem,org_id,status_id, trashed_date,cause_id, state_id, site_id, ");
			sql.append("descrizionebreveallerta ,provvedimenti_prescrittivi , alimenti_origine_animale,alimenti_origine_vegetale,");
			sql.append(" alimenti_composti, alimenti_acqua,alimenti_bevande,alimenti_mangimi, alimenti_additivi,");
			sql.append(" alimenti_materiali_alimenti, alimenti_dolciumi, alimenti_gelati, altrialimenti_nonanimali,");
			sql.append("notesequestridi, ");
			sql.append("tipo_richiesta,  enteredBy, modifiedBy, tipologia, sanzioni_amministrative, sanzioni_penali, sequestri,  ");
			
			if (id > -1) {
				sql.append("ticketid, ");
			}
			if (flag_tipo_allerta != null) {
				sql.append("flag_tipo_allerta, ");
			}	
			if (flag_produzione != null) {
				sql.append("flag_pubblicazione_allerte, ");
			}
			if (data_inizio_pubblicazione != null && ! "".equals(data_inizio_pubblicazione)) {
				sql.append("data_inizio_pubblicazione_allerte, ");
			}
			if (data_fine_pubblicazione != null && ! "".equals(data_fine_pubblicazione)) {
				sql.append("data_fine_pubblicazione_allerte, ");
			}
			if (provvedimento_esito != null && ! "".equals(provvedimento_esito)) {
				sql.append("provvedimenti_esito_allerte, ");
			}
			if (tipo_rischio != null && ! "".equals(tipo_rischio)) {
				sql.append("tipo_rischio_allerte, ");
			}
			
			if (denominazione_prodotto != null && ! "".equals(denominazione_prodotto)) {
				sql.append("denominazione_prodotto, ");
			}
			if (numero_lotto != null && ! "".equals(numero_lotto)) {
				sql.append("numero_lotto, ");
			}
			if (fabbricante_produttore != null && ! "".equals(fabbricante_produttore)) {
				sql.append("fabbricante_produttore, ");
			}
			if (data_scadenza_allerta != null ) {
				sql.append("data_scadenza_allerta, ");
			}
			
			
			if (tipoAcque != -1) {
				sql.append("tipo_acqua, ");
			}
			sql.append("animali_non_alimentari_flag,");
			
			if (super.getAnimaliNonAlimentariCombo() > 0) {
				sql.append(", animali_non_alimentare");

			}
			

			if (unitaMisura != -1) {
				sql.append("unita_misura, ");
			}


			if(specieAlimentoZootecnico>-1)
			{
				sql.append("specie_alimento_zootecnico ,");
			}
			if(tipologiaAlimentoZootecnico>-1)
			{
				sql.append("tipologia_alimento_zootecnico ,");
			}
			if (alimentiOrigineAnimaleNonTrasformati > -1) {
				sql.append(" alimenti_origine_animale_non_trasformati,");

			}

			if(isvegetaletrasformato!=-1){
				sql.append("isvegetaletrasformato, ");
			}

			if(altrialimenti!=-1){
				sql.append("altrialimentinonanimali, ");

			}


			if (alimentiOrigineAnimaleTrasformati > -1) {
				sql.append(" alimenti_origine_animale_trasformati,");
				// note seuqestridi fa riferimento nel caso dei campioni alle note degli alimenti

			}
			if (alimentiOrigineVegetaleValori > -1) {
				sql.append(" alimenti_origine_vegetale_valori,");


			}
			if (alimentiOrigineAnimaleNonTrasformatiValori > -1) {
				sql.append(" alimenti_origine_animale_non_trasformati_valori,");
			}

			if(tipSpecie_latte!=-1){
				sql.append(" specie_latte,");


			}
			if(tipSpecie_uova!=-1){
				sql.append(" specie_uova,");


			}
			if (noteAnalisi != "") {
				sql.append("note_analisi,");
			}

			if (origineAllerta != -1) {
				sql.append("origine_allerta,");
			}



			if (entered != null) {
				sql.append("entered, ");
			}
			if (modified != null) {
				sql.append("modified, ");
			}


			if (!"".equals(descrizione1))
			{
				sql.append("descrizione1,");
			}
			if (!"".equals(descrizione2))
			{
				sql.append("descrizione2,");
			}
			if (!"".equals(descrizione3))
			{
				sql.append("descrizione3,");
			}

			if (!"".equals(numero_notifica_allerta))
			{
				sql.append("numero_notifica_allerta,");
			}
			if (tipo_notifica_allerta > 0)
			{
				sql.append("tipo_notifica_allerta,");
			}
			
			sql.append("  data_apertura,data_apertura_timezone,"); //ok
			sql.append(" data_eventuale_revoca,data_chiusura, progressivo_allerta, tipo_alimento,origine,"); //ok
			sql.append("  alimento_interessato,non_conformita_alimento,lista_commercializzazione, id_allerta");
			sql.append(")");
			sql.append("VALUES (?, ?, ?, ?, ?, ?, ?,");
			sql.append("?,?,?,?,?,?,?,?,?,?,?,?,?,");
			sql.append("?,");
			sql.append("?, ?, ?, 700, ?, ?, ?,");

			if (id > -1) 
			{
				sql.append("?,");
			}
			if (flag_tipo_allerta != null) {
				sql.append("?,");
			}
			if (flag_produzione != null) {
				sql.append("?,");
			}
			if (data_inizio_pubblicazione != null && ! "".equals(data_inizio_pubblicazione)) {
				sql.append("?,");
			}
			if (data_fine_pubblicazione != null && ! "".equals(data_fine_pubblicazione)) {
				sql.append("?,");
			}
			if (provvedimento_esito != null && ! "".equals(provvedimento_esito)) {
				sql.append("?,");
			}
			if (tipo_rischio != null && ! "".equals(tipo_rischio)) {
				sql.append("?,");
			}
			
			if (denominazione_prodotto != null && ! "".equals(denominazione_prodotto)) {
				sql.append("?,");
			}
			if (numero_lotto != null && ! "".equals(numero_lotto)) {
				sql.append("?,");
			}
			if (fabbricante_produttore != null && ! "".equals(fabbricante_produttore)) {
				sql.append("?,");
			}
			if (data_scadenza_allerta != null ) {
				sql.append("?,");
			}

			if (tipoAcque != -1) 
			{
				sql.append("?,");
			}
			sql.append("?,");
			if (super.getAnimaliNonAlimentariCombo() > 0) {
				sql.append("?,");

			}
			
			if (unitaMisura != -1) 
			{
				sql.append("?, ");
			}


			if(specieAlimentoZootecnico>-1)
			{
				sql.append("?,");
			}
			if(tipologiaAlimentoZootecnico>-1)
			{
				sql.append("?,");
			}


			if(alimentiOrigineAnimaleNonTrasformati > -1)
			{
				sql.append("?,");

			}
			if(isvegetaletrasformato!=-1)
			{
				sql.append("?,");
			}
			if(altrialimenti!=-1){
				sql.append("?, ");

			}



			if(alimentiOrigineAnimaleTrasformati > -1)
			{

				sql.append("?,");
			}
			if(alimentiOrigineVegetaleValori > -1)
			{
				sql.append("?,");
				//pst.setString(++i, noteAlimenti);
			}
			if(alimentiOrigineAnimaleNonTrasformatiValori > -1)
			{
				sql.append("?,");
			}
			if(tipSpecie_latte!=-1)
			{
				sql.append("?,");
			}
			if(tipSpecie_uova!=-1)
			{
				sql.append("?,");

			}
			if (noteAnalisi != "") 
			{
				sql.append("?,");
			}
			if (origineAllerta != -1) 
			{
				sql.append("?,");
			}


			if (entered != null)
			{
				sql.append("?, ");
			}
			if (modified != null) 
			{
				sql.append("?, ");
			}


			if (!"".equals(descrizione1))
			{
				sql.append("?, ");
			}
			if (!"".equals(descrizione2))
			{
				sql.append("?, ");
			}
			if (!"".equals(descrizione3))
			{
				sql.append("?, ");
			}
			if (!"".equals(numero_notifica_allerta))
			{
				sql.append("?, ");
			}
			if (tipo_notifica_allerta > 0)
			{
				sql.append("?, ");
			}

			sql.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  get_progressivo_allerta(?,?)) "); //ho aggiunto 2 punti interrogativi
			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			pst.setString(++i, this.getProblem());
			DatabaseUtils.setInt(pst, ++i, orgId);
			DatabaseUtils.setInt(pst, ++i, statusId);
			DatabaseUtils.setTimestamp(pst, ++i, trashedDate);
			DatabaseUtils.setInt(pst, ++i, causeId);
			DatabaseUtils.setInt(pst, ++i, this.getStateId());
			DatabaseUtils.setInt(pst, ++i, this.getSiteId());
			pst.setString(++i, descrizioneBreve);
			DatabaseUtils.setInt(pst, ++i, tipoCampione);
			pst.setBoolean(++i, this.getAlimentiOrigineAnimale());
			pst.setBoolean(++i, this.getAlimentiOrigineVegetale());
			pst.setBoolean(++i, this.getAlimentiComposti());
			pst.setBoolean(++i, this.isAlimentiAcqua());
			pst.setBoolean(++i, this.isAlimentiBevande());
			pst.setBoolean(++i, this.isMangimi());
			pst.setBoolean(++i, this.isAlimentiAdditivi());
			pst.setBoolean(++i, this.isMaterialiAlimenti());
			pst.setBoolean(++i, this.getDolciumi());
			pst.setBoolean(++i, this.getGelati());
			pst.setBoolean(++i, this.isAltriAlimenti());
			pst.setString(++i, noteAlimenti);		      
			pst.setString( ++i, this.getTipo_richiesta() );		       
			pst.setInt(++i, this.getEnteredBy());
			pst.setInt(++i, this.getModifiedBy());
			DatabaseUtils.setInt(pst, ++i, sanzioniAmministrative);
			DatabaseUtils.setInt(pst, ++i, sanzioniPenali);
			DatabaseUtils.setInt(pst, ++i, sequestri);

			if (id > -1) {
				pst.setInt(++i, id);
			}
			if (flag_tipo_allerta != null) {
				pst.setBoolean(++i, flag_tipo_allerta);
			}
			if (flag_produzione != null) {
				pst.setBoolean(++i, flag_produzione);
			}
			if (data_inizio_pubblicazione != null && ! "".equals(data_inizio_pubblicazione)) {
				pst.setTimestamp(++i, data_inizio_pubblicazione);
			}
			if (data_fine_pubblicazione != null && ! "".equals(data_fine_pubblicazione)) {
				pst.setTimestamp(++i, data_fine_pubblicazione);
			}
			if (provvedimento_esito != null && ! "".equals(provvedimento_esito)) {
				pst.setString(++i, provvedimento_esito);
			}
			if (tipo_rischio != null && ! "".equals(tipo_rischio)) {
				pst.setString(++i, tipo_rischio);			}

			if (denominazione_prodotto != null && ! "".equals(denominazione_prodotto)) {
				pst.setString(++i, denominazione_prodotto);
			}
			if (numero_lotto != null && ! "".equals(numero_lotto)) {
				pst.setString(++i, numero_lotto);
			}
			if (fabbricante_produttore != null && ! "".equals(fabbricante_produttore)) {
				pst.setString(++i, fabbricante_produttore);
			}
			if (data_scadenza_allerta != null ) {
				pst.setTimestamp(++i, data_scadenza_allerta);
			}
			if (tipoAcque != -1) {
				pst.setInt(++i, tipoAcque);
			}
			
			pst.setBoolean(++i,super.isAnimaliNonAlimentari());
			if (super.getAnimaliNonAlimentariCombo() > 0) {
				pst.setInt(++i, super.getAnimaliNonAlimentariCombo());


			}
			
			if (unitaMisura != -1) {
				pst.setInt(++i, unitaMisura);
			}



			if(specieAlimentoZootecnico>-1)
			{

				pst.setInt(++i , specieAlimentoZootecnico);
			}
			if(tipologiaAlimentoZootecnico>-1)
			{

				pst.setInt(++i , tipologiaAlimentoZootecnico);
			}

			if(alimentiOrigineAnimaleNonTrasformati > -1){
				pst.setInt(++i , alimentiOrigineAnimaleNonTrasformati);

			}

			if(isvegetaletrasformato!=-1){
				pst.setInt(++i , isvegetaletrasformato);
			}
			if(altrialimenti!=-1){
				pst.setInt(++i , altrialimenti);

			}



			if(alimentiOrigineAnimaleTrasformati > -1){

				pst.setInt(++i , alimentiOrigineAnimaleTrasformati);

			}
			if(alimentiOrigineVegetaleValori > -1){
				pst.setInt(++i , alimentiOrigineVegetaleValori);

			}
			if(alimentiOrigineAnimaleNonTrasformatiValori > -1){
				pst.setInt(++i , alimentiOrigineAnimaleNonTrasformatiValori);
			}
			if(tipSpecie_latte!=-1){
				pst.setInt(++i, tipSpecie_latte);


			}
			if(tipSpecie_uova!=-1){
				pst.setInt(++i, tipSpecie_uova);


			}
			if (noteAnalisi != "") {
				pst.setString(++i, noteAnalisi);
			}

			if (origineAllerta != -1) {
				pst.setInt(++i, origineAllerta);
			}





			if (entered != null) {
				pst.setTimestamp(++i, entered);
			}
			if (modified != null) {
				pst.setTimestamp(++i, modified);
			} 

			if (!"".equals(descrizione1))
			{
				pst.setString( ++i, this.getDescrizione1() );
			}
			if (!"".equals(descrizione2))
			{
				pst.setString( ++i, this.getDescrizione2() );
			}
			if (!"".equals(descrizione3))
			{
				pst.setString( ++i, this.getDescrizione3() );
			}
			if (!"".equals(numero_notifica_allerta))
			{
				pst.setString( ++i, this.getNumero_notifica_allerta() );
			}
			if (tipo_notifica_allerta > 0)
			{
				pst.setInt( ++i, this.getTipo_notifica_allerta());
			}

			pst.setTimestamp(++i, this.getDataApertura());
			pst.setString(++i, this.getDataAperturaTimeZone());
			pst.setTimestamp(++i, this.getDataEventualeRevoca());
			pst.setTimestamp(++i, this.getDataChiusura());
			pst.setInt(++i, this.getProgressivoAllerta());
			pst.setInt(++i, this.getTipoAlimento());
			pst.setInt(++i, this.getOrigine());
			pst.setInt(++i, this.getAlimentoInteressato());
			pst.setInt(++i, this.getNonConformita());
			pst.setInt(++i, this.getListaCommercializzazione());
			
			String tipoAll = "" ;
			if(tipo_allerta.equalsIgnoreCase("Entrata"))
			{
				tipoAll = "E";
			}
			else
			{
				tipoAll = "U"; 
			}
			pst.setString(++i, tipoAll);
			pst.setBoolean(++i, inserita_da_sian);
			
			
			
			pst.execute();
			pst.close();


			//Update the rest of the fields
			this.update(db, true);
			if (this.getEntered() == null) {
				this.updateEntry(db);
			}
			
			if (commit) {
				db.commit();
			}
		} catch (SQLException e) {
			if (commit) {
				db.rollback();
			}
			
		} finally {
			if (commit) {
				db.setAutoCommit(true);
			}
		}
		return true;
	}



	public void getTipoCampioneSelezionato(Connection db)throws SQLException{


		String sql="";


		if(tipoCampione==5)
		{
			sql="select idcampionetipo,description from tipicampioni,lookup_tipo_campione_chimicio where code=idcampionetipo and idticket=? ";

			PreparedStatement pst = db.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet rs=pst.executeQuery();
			int code=0;
			if(rs.next()){
				code=rs.getInt("idcampionetipo");
				String value=rs.getString("description");
				tipiCampioni.put(code, value);

			}
			String sql2="";
			if(code==1){
				sql2="select idesamechimicotipo,description from tipocampionechimico,lookup_tipo_campione_sottochimicio where code=idesamechimicotipo and idticket=? ";




			}
			else{
				if(code==2){

					sql2="select idesamechimicotipo,description from tipocampionechimico,lookup_tipo_campione_sottochimicio2 where code=idesamechimicotipo and idticket=? ";




				}else{

					if(code==3){
						sql2="select idesamechimicotipo,description from tipocampionechimico,lookup_tipo_campione_sottochimicio3 where code=idesamechimicotipo and idticket=? ";



					}else{
						if(code==4){
							sql2="select idesamechimicotipo,description from tipocampionechimico,lookup_tipo_campione_sottochimicio4 where code=idesamechimicotipo and idticket=? ";



						}else{
							if(code==5){
								sql2="select idesamechimicotipo,description from tipocampionechimico,lookup_tipo_campione_sottochimicio5 where code=idesamechimicotipo and idticket=? ";





							}

						}

					}
				}


			}

			if(!sql2.equals("")){
				PreparedStatement pst1 = db.prepareStatement(sql2);
				pst1.setInt(1, id);
				ResultSet rs1=pst1.executeQuery();

				while(rs1.next()){
					int code1= rs1.getInt("idesamechimicotipo");
					String value1=rs1.getString("description");
					tipiChimiciSelezionati.put(code1, value1);

				}
			}




		}

		else

		{

			if(tipoCampione==1 || tipoCampione==2 || tipoCampione==4 || tipoCampione==8){

				if(tipoCampione==1)
				{
					sql="select idcampionetipo,description from tipicampioni,lookup_tipo_campione_batteri where code=idcampionetipo and idticket=? ";
				}

				else{

					if(tipoCampione==2)
					{
						sql="select idcampionetipo,description from tipicampioni,lookup_tipo_campione_virus where code=idcampionetipo and idticket=? ";
					}else{

						if(tipoCampione==4)

						{
							sql="select idcampionetipo,description from tipicampioni,lookup_tipo_campione_parassiti where code=idcampionetipo and idticket=? ";
						}else{


							if(tipoCampione==8){
								sql="select idcampionetipo,description from tipicampioni,lookup_tipo_campione_fisico where code=idcampionetipo and idticket=? ";


							}

						}

					}}


				PreparedStatement pst = db.prepareStatement(sql);
				pst.setInt(1, id);
				ResultSet rs=pst.executeQuery();
				while(rs.next()){
					int code=rs.getInt("idcampionetipo");
					String value=rs.getString("description");
					tipiCampioni.put(code, value);

				}
			}
		}

	}

	protected void buildRecord(ResultSet rs) throws SQLException {
		//ticket table
		this.setId(rs.getInt("ticketid"));
		orgId = DatabaseUtils.getInt(rs, "org_id");
		contactId = DatabaseUtils.getInt(rs, "contact_id");
		problem = rs.getString("problem");
		entered = rs.getTimestamp("entered");
		enteredBy = rs.getInt("enteredby");
		modified = rs.getTimestamp("modified");
		modifiedBy = rs.getInt("modifiedby");
		closed = rs.getTimestamp("closed");
	    tipo_esame = rs.getString("tipo_esame");
		denominazione_prodotto = rs.getString("denominazione_prodotto");
		numero_lotto = rs.getString("numero_lotto");
		fabbricante_produttore = rs.getString("fabbricante_produttore");
		data_scadenza_allerta = rs.getTimestamp("data_scadenza_allerta");
		super.setAnimaliNonAlimentari(rs.getBoolean("animali_non_alimentari_flag"));
		super.setAnimaliNonAlimentariCombo(rs.getInt("animali_non_alimentare"));
		flag_tipo_allerta = rs.getBoolean("flag_tipo_allerta");
		flag_produzione = rs.getBoolean("flag_pubblicazione_allerte");
		data_fine_pubblicazione = rs.getTimestamp("data_fine_pubblicazione_allerte");
		data_inizio_pubblicazione = rs.getTimestamp("data_inizio_pubblicazione_allerte");
		tipo_rischio = rs.getString("tipo_rischio_allerte");
		provvedimento_esito = rs.getString("provvedimenti_esito_allerte");
		specieAlimentoZootecnico = rs.getInt("specie_alimento_zootecnico");
		tipologiaAlimentoZootecnico = rs.getInt("tipologia_alimento_zootecnico");
		chiusuraUfficio= rs.getBoolean("chiusura_ufficio");
		dolciumi=rs.getBoolean("alimenti_dolciumi");
		gelati=rs.getBoolean("alimenti_gelati");
		alimentiAcqua=rs.getBoolean("alimenti_acqua");
		alimentiBevande=rs.getBoolean("alimenti_bevande");
		mangimi = rs.getBoolean("alimenti_mangimi");
		alimentiAdditivi=rs.getBoolean("alimenti_additivi");
		materialiAlimenti=rs.getBoolean("alimenti_materiali_alimenti");
		origineAllerta=rs.getInt("origine_allerta");
		tipoAcque=rs.getInt("tipo_acqua");
		tipSpecie_latte=rs.getInt("specie_latte");
		tipSpecie_uova=rs.getInt("specie_uova");
		noteAlimenti=rs.getString("notesequestridi");
		tipoCampione = DatabaseUtils.getInt(rs, "provvedimenti_prescrittivi");
		altriAlimenti=rs.getBoolean("altrialimenti_nonanimali");
		isvegetaletrasformato=rs.getInt("isvegetaletrasformato");
		altrialimenti=rs.getInt("altrialimentinonanimali");
		alimentiOrigineAnimale = rs.getBoolean("alimenti_origine_animale");
		alimentiOrigineVegetale = rs.getBoolean("alimenti_origine_vegetale");
		alimentiComposti = rs.getBoolean("alimenti_composti");
		alimentiOrigineAnimaleNonTrasformati = DatabaseUtils.getInt(rs, "alimenti_origine_animale_non_trasformati");
		alimentiOrigineAnimaleTrasformati = DatabaseUtils.getInt(rs, "alimenti_origine_animale_trasformati");
		alimentiOrigineVegetaleValori= DatabaseUtils.getInt(rs, "alimenti_origine_vegetale_valori");
		alimentiOrigineAnimaleNonTrasformatiValori = DatabaseUtils.getInt(rs, "alimenti_origine_animale_non_trasformati_valori");
		noteAnalisi = rs.getString("note_analisi");
		isPregresso = rs.getString("ispregresso");
		descrizioneBreve=rs.getString("descrizionebreveallerta");
		//priorityCode = DatabaseUtils.getInt(rs, "pri_code");
		//levelCode = DatabaseUtils.getInt(rs, "level_code");
		//departmentCode = DatabaseUtils.getInt(rs, "department_code");
		//sourceCode = DatabaseUtils.getInt(rs, "source_code");
		//catCode = DatabaseUtils.getInt(rs, "cat_code", 0);
		//subCat1 = DatabaseUtils.getInt(rs, "subcat_code1", 0);
		//subCat2 = DatabaseUtils.getInt(rs, "subcat_code2", 0);
		//subCat3 = DatabaseUtils.getInt(rs, "subcat_code3", 0);
		//assignedTo = DatabaseUtils.getInt(rs, "assigned_to");
		//solution = rs.getString("solution");
		//severityCode = DatabaseUtils.getInt(rs, "scode");
		location = rs.getString("location");
		assignedDate = rs.getTimestamp("assigned_date");
		estimatedResolutionDate = rs.getTimestamp("est_resolution_date");
		resolutionDate = rs.getTimestamp("resolution_date");
		cause = rs.getString("cause");
		unitaMisura = rs.getInt("unita_misura");
		//contractId = DatabaseUtils.getInt(rs, "link_contract_id");
		//assetId = DatabaseUtils.getInt(rs, "link_asset_id");
		//productId = DatabaseUtils.getInt(rs, "product_id");
		//customerProductId = DatabaseUtils.getInt(rs, "customer_product_id");
		//expectation = DatabaseUtils.getInt(rs, "expectation");
		//projectTicketCount = rs.getInt("key_count");
		estimatedResolutionDateTimeZone = rs.getString(
		"est_resolution_date_timezone");
		assignedDateTimeZone = rs.getString("assigned_date_timezone");





		resolutionDateTimeZone = rs.getString("resolution_date_timezone");
		statusId = DatabaseUtils.getInt(rs, "status_id");
		trashedDate = rs.getTimestamp("trashed_date");
		//userGroupId = DatabaseUtils.getInt(rs, "user_group_id");
		//causeId = DatabaseUtils.getInt(rs, "cause_id");
		//resolutionId = DatabaseUtils.getInt(rs, "resolution_id");
		//defectId = DatabaseUtils.getInt(rs, "defect_id");
		//resolvable = rs.getBoolean("resolvable");
		//resolvedBy = rs.getInt("resolvedby");
		//resolvedByDeptCode = DatabaseUtils.getInt(rs, "resolvedby_department_code");
		stateId = DatabaseUtils.getInt(rs, "state_id");
		siteId = DatabaseUtils.getInt(rs, "site_id");
		tipo_richiesta = rs.getString( "tipo_richiesta" );
		pippo = rs.getString( "custom_data" );
		tipologia = rs.getInt( "tipologia" );
		provvedimenti = DatabaseUtils.getInt(rs, "provvedimenti_prescrittivi");
		sanzioniAmministrative = DatabaseUtils.getInt(rs, "sanzioni_amministrative");
		sanzioniPenali = DatabaseUtils.getInt(rs, "sanzioni_penali");
		sequestri = DatabaseUtils.getInt(rs, "sequestri");

		descrizione1 = rs.getString( "descrizione1" );
		descrizione2 = rs.getString( "descrizione2" );
		descrizione3 = rs.getString( "descrizione3" );

		tipoSequestro = rs.getBoolean("tipo_sequestro");
		tipoSequestroDue = rs.getBoolean("tipo_sequestro_due");
		tipoSequestroTre = rs.getBoolean("tipo_sequestro_tre");
		tipoSequestroQuattro = rs.getBoolean("tipo_sequestro_quattro");
		dataApertura = rs.getTimestamp("data_apertura");
		dataAperturaTimeZone = rs.getString("data_apertura_timezone");
		dataEventualeRevoca = rs.getTimestamp("data_eventuale_revoca");
		dataChiusura = rs.getTimestamp("data_chiusura");
		progressivoAllerta = rs.getInt("progressivo_allerta");
		tipoAlimento = rs.getInt("tipo_alimento");
		origine = rs.getInt("origine");
		alimentoInteressato = rs.getInt("alimento_interessato");
		nonConformita = rs.getInt("non_conformita_alimento");
		listaCommercializzazione = rs.getInt("lista_commercializzazione");
		idAllerta = rs.getString("id_allerta");
		//organization table
		companyName = rs.getString("orgname");
		orgSiteId = DatabaseUtils.getInt(rs, "orgsiteid");
		
		note_motivazione = rs.getString("note_motivazione");
		
		tipo_notifica_allerta = rs.getInt("tipo_notifica_allerta");
		numero_notifica_allerta = rs.getString("numero_notifica_allerta");


		//lookup_department table
		//departmentName = rs.getString("dept");
		//resolvedByDeptName = rs.getString("resolvedept");

		//ticket_priority table
		//priorityName = rs.getString("ticpri");

		//ticket_severity table
		//severityName = rs.getString("ticsev");

		//ticket_category table
		//categoryName = rs.getString("catname");
		//lookup_ticket_source table
		//sourceName = rs.getString("sourcename");

		//service_contract table
		/*serviceContractNumber = rs.getString("contractnumber");
		totalHoursRemaining = rs.getFloat("hoursremaining");
		contractStartDate = rs.getTimestamp("contractstartdate");
		contractEndDate = rs.getTimestamp("contractenddate");
		contractOnsiteResponseModel = DatabaseUtils.getInt(
				rs, "contractonsiteservicemodel");
*/
		//asset table
		/*assetSerialNumber = rs.getString("serialnumber");
		assetManufacturerCode = DatabaseUtils.getInt(rs, "assetmanufacturercode");
		assetVendorCode = DatabaseUtils.getInt(rs, "assetvendorcode");
		assetModelVersion = rs.getString("modelversion");
		assetLocation = rs.getString("assetlocation");
		assetOnsiteResponseModel = DatabaseUtils.getInt(
				rs, "assetonsiteservicemodel");
*/
		//product catalog
		//productSku = rs.getString("productsku");
		//productName = rs.getString("productname");

		//ticketlink_project
	//	projectId = DatabaseUtils.getInt(rs, "project_id");

		//projects
//		projectName = rs.getString("projectname");

		// user groups
	//	userGroupName = rs.getString("usergroupname");

		//from lookup_ticket_escalation table
		//escalationLevelName = rs.getString("escalationlevelname");

		//Calculations
	
	}

	public int update(Connection db, boolean override) throws SQLException {
		int resultCount = 0;
		PreparedStatement pst = null;
		StringBuffer sql = new StringBuffer();
		sql.append(
				"UPDATE ticket " +
				"SET  problem = ?, " +
				"status_id = ?, trashed_date = ?, site_id = ? , ");
		sql.append(" descrizionebreveallerta = ?, ");

		sql.append("alimenti_origine_animale = ?, alimenti_origine_vegetale = ?, alimenti_composti = ?,alimenti_acqua=?," +
				"alimenti_bevande=?,alimenti_mangimi=?,alimenti_additivi=?,alimenti_materiali_alimenti=?,alimenti_dolciumi=?," +
				"alimenti_gelati=?,altrialimenti_nonanimali=?, alimenti_origine_animale_non_trasformati = ?," +
				" alimenti_origine_animale_trasformati = ?, alimenti_origine_vegetale_valori = ?," +
				" alimenti_origine_animale_non_trasformati_valori = ?,"+
				"location = ?, assigned_date = ?, assigned_date_timezone = ?,cause = ?, state_id = ?,provvedimenti_prescrittivi = ?, ");

		sql.append( " tipo_richiesta = ?,sanzioni_amministrative = ?, sanzioni_penali = ?, sequestri = ?, descrizione1 = ?,descrizione2 = ?, descrizione3 = ?, " +
					" data_eventuale_revoca = ?, progressivo_allerta = ?, tipo_alimento = ?, origine = ?, alimento_interessato = ?, non_conformita_alimento = ?, lista_commercializzazione = ? ");
		sql.append(" ,chiusura_ufficio = ?, notesequestridi=?,note_analisi=?, ");
		

	
		if (orgId != -1) {
			sql.append(" org_id = ?, ");
		}
		if (dataApertura !=null) {
			sql.append(" data_apertura = ?, ");
		}
		
		if (flag_tipo_allerta != null) {
			sql.append("flag_tipo_allerta = ?, ");
		}
		if (flag_produzione != null) {
			sql.append("flag_pubblicazione_allerte = ?, ");
		}
		if (data_inizio_pubblicazione != null && ! "".equals(data_inizio_pubblicazione)) {
			sql.append("data_inizio_pubblicazione_allerte = ? , ");
		}
		if (data_fine_pubblicazione != null && ! "".equals(data_fine_pubblicazione)) {
			sql.append("data_fine_pubblicazione_allerte = ? , ");
		}
		if (provvedimento_esito != null && ! "".equals(provvedimento_esito)) {
			sql.append("provvedimenti_esito_allerte = ? , ");
		}
		if (tipo_rischio != null && ! "".equals(tipo_rischio)) {
			sql.append("tipo_rischio_allerte = ?, ");
		}
		
		if (denominazione_prodotto != null && ! "".equals(denominazione_prodotto)) {
			sql.append("denominazione_prodotto=?, ");
		}
		if (numero_lotto != null && ! "".equals(numero_lotto)) {
			sql.append("numero_lotto=?, ");
		}
		if (fabbricante_produttore != null && ! "".equals(fabbricante_produttore)) {
			sql.append("fabbricante_produttore=?, ");
		}
		if (data_scadenza_allerta != null ) {
			sql.append("data_scadenza_allerta=?, ");
		}

		if(dataChiusura!=null){
			sql.append("data_chiusura = ?, ");
		}
		if (unitaMisura != -1) {
			sql.append("unita_misura = ?, ");
		}

		if(tipSpecie_latte!=-1){
			sql.append(" specie_latte = ?, ");

		}
		if(isvegetaletrasformato!=-1){
			sql.append("isvegetaletrasformato = ?, ");
		}

		if(tipoAcque!=-1){
			sql.append(" tipo_acqua = ?, ");

		}

		if(tipSpecie_uova!=-1){
			sql.append(" specie_uova = ?, ");

		}
		if (origineAllerta != -1) {
			sql.append(" origine_allerta = ?, ");

		}


		if(altrialimenti!=-1){
			sql.append(" altrialimentinonanimali = ?, ");

		}

		if(specieAlimentoZootecnico>-1)
		{
			sql.append("specie_alimento_zootecnico = ?,");
		}
		if(tipologiaAlimentoZootecnico>-1)
		{
			sql.append("tipologia_alimento_zootecnico = ?,");
		}
		
		if(tipo_notifica_allerta>-1)
		{
			sql.append("tipo_notifica_allerta = ?,");
		}
		if(!"".equals(numero_notifica_allerta))
		{
			sql.append("numero_notifica_allerta = ?,");
		}

		sql.append( "modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", modifiedby = ?");
		sql.append(   "WHERE ticketid = ? AND tipologia = 700");




		int i = 0;
		pst = db.prepareStatement(sql.toString());



		pst.setString(++i, this.getProblem());
		DatabaseUtils.setInt(pst, ++i, this.getStatusId());
		DatabaseUtils.setTimestamp(pst, ++i, this.getTrashedDate());
		DatabaseUtils.setInt(pst, ++i, this.getSiteId());
		pst.setString(++i, descrizioneBreve);
		pst.setBoolean(++i, alimentiOrigineAnimale);
		pst.setBoolean(++i, alimentiOrigineVegetale);
		pst.setBoolean(++i, alimentiComposti);

		pst.setBoolean(++i, alimentiAcqua);

		pst.setBoolean(++i, alimentiBevande);
		pst.setBoolean(++i, mangimi);

		pst.setBoolean(++i, alimentiAdditivi);


		pst.setBoolean(++i, materialiAlimenti);
		pst.setBoolean(++i, dolciumi);
		pst.setBoolean(++i, gelati);
		pst.setBoolean(++i, altriAlimenti);

		pst.setInt(++i, alimentiOrigineAnimaleNonTrasformati);
		pst.setInt(++i, alimentiOrigineAnimaleTrasformati);
		pst.setInt(++i, alimentiOrigineVegetaleValori);
		pst.setInt(++i, alimentiOrigineAnimaleNonTrasformatiValori);
		pst.setString(++i, location);
		DatabaseUtils.setTimestamp(pst, ++i, assignedDate);
		pst.setString(++i, this.assignedDateTimeZone);

		pst.setString(++i, cause);


		DatabaseUtils.setInt(pst, ++i, this.getStateId());
		DatabaseUtils.setInt(pst, ++i, this.getTipoCampione());
		pst.setString( ++i, tipo_richiesta );

		DatabaseUtils.setInt(pst, ++i, sanzioniAmministrative);
		DatabaseUtils.setInt(pst, ++i, sanzioniPenali);
		DatabaseUtils.setInt(pst, ++i, sequestri);

		pst.setString(++i, descrizione1);
		pst.setString(++i, descrizione2);
		pst.setString(++i, descrizione3);


		
		pst.setTimestamp(++i, this.getDataEventualeRevoca());

		pst.setInt(++i, this.getProgressivoAllerta());
		pst.setInt(++i, this.getTipoAlimento());
		pst.setInt(++i, this.getOrigine());
		pst.setInt(++i, this.getAlimentoInteressato());
		pst.setInt(++i, this.getNonConformita());
		pst.setInt(++i, this.getListaCommercializzazione());
		pst.setBoolean(++i, chiusuraUfficio);
		pst.setString(++i, noteAlimenti);
		pst.setString(++i, noteAnalisi);
	

		
		if (orgId != -1) {
			DatabaseUtils.setInt(pst, ++i, orgId);
		}
		
		if (dataApertura !=null) {
			pst.setTimestamp(++i, this.getDataApertura());
		}
		
		if (flag_tipo_allerta != null) {
			pst.setBoolean(++i, flag_tipo_allerta);
		}
		if (flag_produzione != null) {
			pst.setBoolean(++i, flag_produzione);
		}
		if (data_inizio_pubblicazione != null && ! "".equals(data_inizio_pubblicazione)) {
			pst.setTimestamp(++i, data_inizio_pubblicazione);
		}
		if (data_fine_pubblicazione != null && ! "".equals(data_fine_pubblicazione)) {
			pst.setTimestamp(++i, data_fine_pubblicazione);
		}
		if (provvedimento_esito != null && ! "".equals(provvedimento_esito)) {
			pst.setString(++i, provvedimento_esito);
		}
		if (tipo_rischio != null && ! "".equals(tipo_rischio)) {
			pst.setString(++i, tipo_rischio);			}
		if (denominazione_prodotto != null && ! "".equals(denominazione_prodotto)) {
			pst.setString(++i, denominazione_prodotto);
		}
		if (numero_lotto != null && ! "".equals(numero_lotto)) {
			pst.setString(++i, numero_lotto);
		}
		if (fabbricante_produttore != null && ! "".equals(fabbricante_produttore)) {
			pst.setString(++i, fabbricante_produttore);
		}
		if (data_scadenza_allerta != null ) {
			pst.setTimestamp(++i, data_scadenza_allerta);
		}

		if(dataChiusura!=null)
			pst.setTimestamp(++i, dataChiusura);
		if (unitaMisura != -1) {
			pst.setInt(++i, unitaMisura);
		}


		if(tipSpecie_latte!=-1){
			DatabaseUtils.setInt(pst, ++i, tipSpecie_latte);
		}
		if(isvegetaletrasformato!=-1){
			DatabaseUtils.setInt(pst, ++i, isvegetaletrasformato);
		}


		if(tipoAcque!=-1){
			DatabaseUtils.setInt(pst, ++i, tipoAcque);

		}
		if(tipSpecie_uova!=-1){
			DatabaseUtils.setInt(pst, ++i, tipSpecie_uova);

		}
		if (origineAllerta != -1) {

			pst.setInt(++i, origineAllerta);
		}
		if(altrialimenti!=-1){DatabaseUtils.setInt(pst, ++i, altrialimenti);

		}
		if(specieAlimentoZootecnico>-1)
		{
			pst.setInt(++i, specieAlimentoZootecnico);
		}
		if(tipologiaAlimentoZootecnico>-1)
		{
			pst.setInt(++i,tipologiaAlimentoZootecnico);
		}
		
		if(tipo_notifica_allerta>-1)
		{
			pst.setInt(++i,tipo_notifica_allerta);
		}
		if(!"".equals(numero_notifica_allerta))
		{
			pst.setString(++i,numero_notifica_allerta);
		}

		pst.setInt(++i, this.getModifiedBy());
		pst.setInt(++i, id);
		
		
		resultCount = pst.executeUpdate();
		pst.close();

		return resultCount;
	}

	public int chiudi(Connection db) throws SQLException {
		int resultCount = 0;
		try {
			db.setAutoCommit(false);
			PreparedStatement pst = null;
			String sql =
				"UPDATE ticket " +
				"SET data_chiusura = ?, modified = " + DatabaseUtils.getCurrentTimestamp(
						db) + ", modifiedby = ? " +
						"WHERE ticketid = ? ";
			int i = 0;
			pst = db.prepareStatement(sql);
			pst.setTimestamp( ++i, new Timestamp( System.currentTimeMillis() ) );
			pst.setInt(++i, this.getModifiedBy());
			pst.setInt(++i, this.getId());
			resultCount = pst.executeUpdate();
			pst.close();
			this.setClosed((java.sql.Timestamp) null);
			
			db.commit();
		} catch (SQLException e) {
			db.rollback();
			throw new SQLException(e.getMessage());
		} finally {
			db.setAutoCommit(true);
		}
		return resultCount;
	}
	
	public int chiudi(Connection db, String data) throws SQLException, ParseException {
		int resultCount = 0;
		try {
			db.setAutoCommit(false);
			PreparedStatement pst = null;
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date parsedDate;
			Timestamp timestamp; 
			parsedDate = dateFormat.parse(data);
			timestamp = new java.sql.Timestamp(parsedDate.getTime()); 
			String sql =
				"UPDATE ticket " +
				"SET resolution_date = ?, modified = " + DatabaseUtils.getCurrentTimestamp(
						db) + ", modifiedby = ? , resolvedby = ? , chiusura_ufficio = ?, note_motivazione = ? " +
						"WHERE ticketid = ? ";
			int i = 0;
			pst = db.prepareStatement(sql);
			pst.setTimestamp( ++i, timestamp);
			pst.setInt(++i, this.getModifiedBy());
			pst.setInt(++i, this.getModifiedBy());
			pst.setBoolean(++i, this.isChiusuraUfficio());
			pst.setString(++i, this.getNote_motivazione());
			pst.setInt(++i, this.getId());
			resultCount = pst.executeUpdate();
			pst.close();
			this.setClosed((java.sql.Timestamp) null);
			
			db.commit();
		} catch (SQLException e) {
			db.rollback();
			throw new SQLException(e.getMessage());
		} finally {
			db.setAutoCommit(true);
		}
		return resultCount;
	}

	//controllo di
	public void updateEntry(Connection db) throws SQLException {
		
	}

	public boolean delete(Connection db, String baseFilePath)
	throws SQLException {
		if (this.getId() == -1) {
			throw new SQLException("Ticket ID not specified.");
		}
		boolean commit = db.getAutoCommit();
		try {
			if (commit) {
				db.setAutoCommit(false);
			}
			
			// Delete any documents
			FileItemList fileList = new FileItemList();
			fileList.setLinkModuleId(Constants.DOCUMENTS_TICKETS);
			fileList.setLinkItemId(this.getId());
			fileList.buildList(db);
			fileList.delete(db, getFileLibraryPath(baseFilePath, "tickets"));
			fileList = null;
			PreparedStatement pst = null ;

			// Delete the ticket
			pst = db.prepareStatement("DELETE FROM ticket WHERE ticketid = ?");
			pst.setInt(1, this.getId());
			pst.execute();
			pst.close();
			if (commit) {
				db.commit();
			}
		} catch (SQLException e) {
			if (commit) {
				db.rollback();
			}
			throw new SQLException(e.getMessage());
		} finally {
			if (commit) {
				db.setAutoCommit(true);
			}
		}
		return true;
	}

	public int reopen(Connection db) throws SQLException {
		int resultCount = 0;
		try {
			db.setAutoCommit(false);
			PreparedStatement pst = null;
			String sql = "UPDATE ticket " + "SET closed = ?, modified = "
			+ DatabaseUtils.getCurrentTimestamp(db)
			+ ", modifiedby = ? " + "WHERE ticketid = ? ";
			int i = 0;
			pst = db.prepareStatement(sql);
			pst.setNull(++i, java.sql.Types.TIMESTAMP);
			pst.setInt(++i, this.getModifiedBy());
			pst.setInt(++i, this.getId());
			resultCount = pst.executeUpdate();
			pst.close();
			this.setClosed((java.sql.Timestamp) null);
			// Update the ticket log
			
			db.commit();
		} catch (SQLException e) {
			db.rollback();
			throw new SQLException(e.getMessage());
		} finally {
			db.setAutoCommit(true);
		}
		return resultCount;
	}

	public int update(Connection db) throws SQLException {
		int i = -1;
		try {
			db.setAutoCommit(false);
			i = this.update(db, false);
			updateEntry(db);
			db.commit();
		} catch (SQLException e) {
			db.rollback();
			throw new SQLException(e.getMessage());
		} finally {
			db.setAutoCommit(true);
		}
		return i;
	}

	public String getTestoAppoggio() {
		return testoAppoggio;
	}

	public void setTestoAppoggio(String testoAppoggio) {
		this.testoAppoggio = testoAppoggio;
	}

	/*metodo di chiusura */

//	public boolean canSetCUPianificati( UserBean user )
//	{
//		boolean ret = false;
//		//allerte-allerte-cu
//
//		int user_asl = user.getSiteId();
//
//		AslCoinvolte ac = this.getAslCoinvolta( user_asl );
//
//		ret = ( (ac == null) ? (false) : (ac.getCu_pianificati() < 0) );
//
//		return ret;
//	}

//	public AslCoinvolte getAslCoinvolta(int user_asl)
//	{
//		AslCoinvolte ret = null;
//
//		Enumeration<AslCoinvolte> e = asl_coinvolte.elements();
//		while( e.hasMoreElements() && (ret == null) )
//		{
//			AslCoinvolte temp = e.nextElement();
//			ret = ( (temp.getId_asl() == user_asl) ? (temp) : (null) );
//		}
//
//		return ret;
//	}
//
//
//	public ImpreseCoinvolte getImpresaCoinvolta(int user_asl)
//	{
//		ImpreseCoinvolte ret = null;
//
//		Enumeration<ImpreseCoinvolte> e = imprese_coinvolte.elements();
//		while( e.hasMoreElements() && (ret == null) )
//		{
//			ImpreseCoinvolte temp = e.nextElement();
//			ret = ( (temp.getIdAsl() == user_asl) ? (temp) : (null) );
//		}
//
//		return ret;
//	}

	public String getStato()
	{
		String ret = null;

		//			Enumeration<AslCoinvolte> e = asl_coinvolte.elements();
		//				
		//			while( e.hasMoreElements() )
		//			{
		//				String temp = parseStato( e.nextElement() );
		//			}
		//completare
		return (dataChiusura == null) ? ATTIVA : INATTIVA;
	}

//	public String getStato( int id_asl )
//	{
//		String ret = ASL_NON_COINVOLTA;
//
//		AslCoinvolte ac = getAslCoinvolta( id_asl );
//		if( ac != null )
//		{
//			ret = parseStato( ac );
//		}
//
//		return ret;
//	}
//
//	private String parseStato(AslCoinvolte ac)
//	{
//		String ret = "";
//
//		if( ac.getData_chiusura() != null )
//		{
//			ret = CHIUSA;
//		}
//		else if( ac.getCu_pianificati() <= 0 )
//		{
//			ret = ATTIVA;
//		}
//		else if( ( ac.getCu_pianificati() - ac.getCu_eseguiti() ) > 0 && (ac.getFlag_ripianificazione()==false ) )
//		{
//			ret = CONTROLLI_IN_CORSO;
//		}
//		else if( ac.getFlag_ripianificazione()==true && ( ac.getCu_pianificati() - ac.getCu_eseguiti() ) > 0)
//		{
//			ret = RIPIANIFICAZIONE;
//		}
//		else
//		{
//			if (ac.getFlag_ripianificazione()==true)
//			{
//				ret = CONTROLLI_COMPLETATI_R ;
//			}
//			else ret = CONTROLLI_COMPLETATI;
//		}
//		
//		return ret;
//	}
//
//	public boolean isChiudibile( int asl )
//	{
//		boolean ret = true;
//
//		if( asl > 0 ) //chiusura allerta per la data asl
//		{
//			AslCoinvolte ac = getAslCoinvolta( asl );
//			ret = isRisolta(ac) && !isChiusa(ac);
//		}
//		else //chiusura globale dell'allerta
//		{
//			Enumeration<AslCoinvolte> e = asl_coinvolte.elements();
//			while( e.hasMoreElements() && ret )
//			{
//				ret = isChiusa( e.nextElement() );
//			}
//		}
//
//		return ret;
//	}

	private boolean isChiusa(AslCoinvolte ac)
	{
		return ( (ac.getData_chiusura() == null) ? (false) : (true) );
	}

	private boolean isRisolta(AslCoinvolte ac)
	{
		return ( (ac.getCu_pianificati() < 0 ) ? (false) : ( (ac.getCu_pianificati() - ac.getCu_eseguiti()) <= 0 ) );
	}


	public void ripristinaSequence(Connection db)
	{
		String reset_seq_1 = "ALTER SEQUENCE seq_identificativo_allerta_entrata_sian RESTART WITH 1";
		String reset_seq_2 = "ALTER SEQUENCE seq_identificativo_allerta_entrata_vet RESTART WITH 1";
		String reset_seq_3 = "ALTER SEQUENCE seq_identificativo_allerta_uscita_sian RESTART WITH 1";
		String reset_seq_4 = "ALTER SEQUENCE seq_identificativo_allerta_uscita_vet RESTART WITH 1";
		try
		{
			PreparedStatement pst = null;
			
			
					pst = db.prepareStatement(reset_seq_1);
					pst.execute();
					pst = db.prepareStatement(reset_seq_2);
					pst.execute();
					pst = db.prepareStatement(reset_seq_3);
					pst.execute();
					pst = db.prepareStatement(reset_seq_4);
					pst.execute();
		}catch(SQLException e)
		{
						e.printStackTrace();
		}
	}
	
	
	private String setIdentificativoAllerta(Connection db)
	{
		String id_allerta			= "";
		String id_allerta_corrente 	= "";
		String reset_seq_1 = "ALTER SEQUENCE seq_identificativo_allerta_entrata_sian RESTART WITH 1";
		String reset_seq_2 = "ALTER SEQUENCE seq_identificativo_allerta_entrata_vet RESTART WITH 1";
		String reset_seq_3 = "ALTER SEQUENCE seq_identificativo_allerta_uscita_sian RESTART WITH 1";
		String reset_seq_4 = "ALTER SEQUENCE seq_identificativo_allerta_uscita_vet RESTART WITH 1";
		try
		{
			String sel_last_allerta = "select DATE_PART('year',max(entered)),DATE_PART('year',current_date) from ticket where tipologia = 700 and trashed_Date is null ";
			PreparedStatement pst = db.prepareStatement(sel_last_allerta);
			ResultSet rs = pst.executeQuery();
			if (rs.next())
			{
				String anno_ultima_allerta 	= rs.getString(1)	;
				String anno_corrente 		= rs.getString(2)	;
				int anno_curr = Integer.parseInt(anno_corrente);
				int anno_alle = Integer.parseInt(anno_ultima_allerta);
				if (anno_curr>anno_alle)
				{
					pst = db.prepareStatement(reset_seq_1);
					pst.execute();
					pst = db.prepareStatement(reset_seq_2);
					pst.execute();
					pst = db.prepareStatement(reset_seq_3);
					pst.execute();
					pst = db.prepareStatement(reset_seq_4);
					pst.execute();
				}
			}
			if (tipo_allerta.equalsIgnoreCase("Entrata"))
			{
				if (inserita_da_sian==true)
				{
					id_allerta 			= "(Select trim('E/'|| nextval('seq_identificativo_allerta_entrata_sian')||'S'||'/'||DATE_PART('year',current_date)))";
					id_allerta_corrente = "(Select trim('E/'|| (select last_value from seq_identificativo_allerta_entrata_sian )||'S'||'/'||DATE_PART('year',current_date)))";
					reset_sequence_roll = "select setval('seq_identificativo_allerta_entrata_sian', (select last_value from seq_identificativo_allerta_entrata_sian)-1)"; 
					curr_val_sequence = "select last_value from seq_identificativo_allerta_entrata_sian";
				}
				else
				{
					id_allerta = "(Select  trim('E/'|| nextval('seq_identificativo_allerta_entrata_vet')||'/'||DATE_PART('year',current_date)))";
					id_allerta_corrente = "(Select  trim('E/'|| (select last_value from seq_identificativo_allerta_entrata_vet)||'/'||DATE_PART('year',current_date)))";
					reset_sequence_roll = "select setval('seq_identificativo_allerta_entrata_vet', (select last_value from seq_identificativo_allerta_entrata_vet)-1)"; 
					curr_val_sequence = "select last_value from seq_identificativo_allerta_entrata_vet";
				}
			}
			else
			{
				if (tipo_allerta.equalsIgnoreCase("Uscita"))
				{
					if (inserita_da_sian==true)
					{
						id_allerta = "(Select trim('U/'|| nextval('seq_identificativo_allerta_uscita_sian')||'S'||'/'||DATE_PART('year',current_date)))";
						id_allerta_corrente = "(Select trim('U/'|| (select last_value from seq_identificativo_allerta_uscita_sian)||'S'||'/'||DATE_PART('year',current_date)))";
						reset_sequence_roll = "select setval('seq_identificativo_allerta_uscita_sian', (select last_value from seq_identificativo_allerta_uscita_sian)-1)"; 
						curr_val_sequence = "select last_value from seq_identificativo_allerta_uscita_sian";
					}
					else
					{
						id_allerta = "(Select trim('U/'|| nextval('seq_identificativo_allerta_uscita_vet')||'/'||DATE_PART('year',current_date)))";
						id_allerta_corrente = "(Select trim('U/'|| (select last_value from seq_identificativo_allerta_uscita_vet)||'/'||DATE_PART('year',current_date)))";
						reset_sequence_roll = "select setval('seq_identificativo_allerta_uscita_vet', (select last_value from seq_identificativo_allerta_uscita_vet)-1)"; 
						curr_val_sequence = "select last_value from seq_identificativo_allerta_uscita_vet";

					}

				}

			}

		}
		catch(SQLException e)
		{
			e.printStackTrace();
			logger.severe("Errore nella generazione del sequence");
		}
		verificaEsistenzaCodice(db,id_allerta);
		return id_allerta.replaceFirst("nextval", "currval");

	}
	
	

	public String getReset_sequence_roll() {
		return reset_sequence_roll;
	}


	public void setReset_sequence_roll(String reset_sequence_roll) {
		this.reset_sequence_roll = reset_sequence_roll;
	}


	public void verificaEsistenzaCodice(Connection db,String sql)
	{
		
		try
		{
			
			
			PreparedStatement pst = db.prepareStatement("select * from ticket where tipologia = 700 and id_allerta ilike "+sql);
			ResultSet rs = pst.executeQuery();
			if (rs.next())
			{
				verificaEsistenzaCodice(db,sql);
			}
			
			
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		
		
		
	}
	
	public void insertMatrici(Connection db,int id,String path) throws SQLException
	{
		PreparedStatement pst=db.prepareStatement("insert into matrici_allerte (id_allerta , id_matrice,cammino,note) values (?,?,?,?)");
		pst.setInt(1, this.id);
		pst.setInt(2, id);
		pst.setString(3, path);
		pst.setString(4, "");
		pst.execute();
	}
	
	public void insertAnaliti(Connection db,int id,String path, String tipoEsame) throws SQLException
	{
		PreparedStatement pst=db.prepareStatement("insert into analiti_allerte (id_allerta , id_analita,cammino,note, tipo_esame) values (?,?,?,?,?)");
		pst.setInt(1, this.id);
		pst.setInt(2, id);
		pst.setString(3, path);
		pst.setString(4, "");
		pst.setString(5, tipoEsame);
		pst.execute();
	}
	
	
	
	public void updateMatrici(Connection db,int id,String path) throws SQLException
	{
		PreparedStatement pst=db.prepareStatement("update matrici_allerte set cammino = ?, note = ?, id_matrice = ? where id_allerta = ? ");
		pst.setString(1, path);
		pst.setString(2, "");
		pst.setInt(3, id);
		pst.setInt(4, this.id);
		pst.execute();
	}

	public void updateAnaliti(Connection db,int id,String path, String tipoEsame) throws SQLException
	{
		PreparedStatement pst=db.prepareStatement("delete from analiti_allerte where id_allerta = ? ");
		pst.setInt(1, this.id);
		pst.execute();		
		PreparedStatement pst1=db.prepareStatement("insert into analiti_allerte (id_allerta , id_analita,cammino,note, tipo_esame) values (?,?,?,?,?)");
		pst1.setInt(1, this.id);
		pst1.setInt(2, id);
		pst1.setString(3, path);
		pst1.setString(4, "");
		pst1.setString(5, tipoEsame);
		pst1.execute();
	}
	
	public void getMatriceCampioneSelezionatoNuovaGestione(Connection db) throws SQLException {

		String sql = "select * from matrici_allerte where id_allerta = ? ";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		while (rs.next())
		{
			int code = rs.getInt("id_matrice");
			String value = rs.getString("cammino");
			matrici.put(code, value);
		}
	}
	
	public void getTipoCampioneSelezionatoNuovaGestione(Connection db) throws SQLException {

		String sql = "select * from analiti_allerte where id_allerta = ? ";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		while (rs.next())
		{
			int code = rs.getInt("id_analita");
			String value = rs.getString("cammino");
			Analita a = new Analita();
			a.setIdAnalita(code);
			a.setDescrizione(value);
			tipi_campioni.add(a);
		}
	}

	public String getNote_motivazione() {
		return note_motivazione;
	}

	public void setNote_motivazione(String note_motivazione) {
		this.note_motivazione = note_motivazione;
	}

	
	public void updateNote(Connection db) throws SQLException {

		String sql = "update ticket set problem = ?, modified=?, modifiedby = ? where ticketid = ? ";
		PreparedStatement pst = db.prepareStatement(sql);
		
		pst.setString(1, problem);
		pst.setTimestamp(2, modified);
		pst.setInt(3, modifiedBy);
		pst.setInt(4, id);
		pst.executeUpdate();
	}		
	
	public boolean numeroNotificaDuplicato(Connection db) throws SQLException {
		String sql = "select ticketid from ticket where numero_notifica_allerta ilike trim(?) and ticketid <> ? ";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setString(1, numero_notifica_allerta);
		pst.setInt(2, id);
		ResultSet rs = pst.executeQuery();
		
		if (rs.next())
			return true;
		return false;
	}		
	
	public int updateNumeroNotifica(Connection db) throws SQLException {

		if (numeroNotificaDuplicato(db))
			return -1;
		
		String sql = "update ticket set numero_notifica_allerta = ?, modified=?, modifiedby = ? where ticketid = ? ";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setString(1, numero_notifica_allerta);
		pst.setTimestamp(2, modified);
		pst.setInt(3, modifiedBy);
		pst.setInt(4, id);
		pst.executeUpdate();
		return 1;
	}		
}





