/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.farmacosorveglianza.base;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Timestamp;

public class RigaAllegatoII {
	private int id ;
	private int idAsl ;
	private String descrizioneAsl;
	private int anno ;
	private int  animali_reddito_art_4_5 ;
	private int animali_reddito_art_11 ;
	private int    animali_reddito_tot_a ;
	private int mangimi_medicati_prod_intermedi_art_3_4 ;
	private int    mangimi_medicati_prod_intermedi_art_16 ;
	private int   mangimi_medicati_prod_intermedi_tot_b ;
	private int scorte_vet_art_84 ;
	private int  scorte_vet_tot_c ;
	private int  scorte_allev_da_reddito ;
	private int scorte_allev_da_compagnia ;
	private int  scorte_allev_da_ippodromi ;
	private int scorte_allev_tot_d ;
	private int bovina ;
	private int suina ;
	private int avicola ;
	private int ovicaprina ; 
	private int cunicola ;
	private int equina ; 
	private int acquicoltura ; 
	private int apiario ;
	private int bufalina ;
	
	private int prescrizioneSuina ;
	private int prescrizioneAvicola ;
	private int prescrizioneOvicaprina ; 
	private int prescrizioneCunicola ;
	private int prescrizioneEquina ; 
	private int prescrizioneAcquicoltura ; 
	private int prescrizioneApiario ;
	private int prescrizioneBufalina ;
	
	private float mediaBovina ;
	private float mediaSuina ;
	private float mediaAvicola ;
	private float mediaOvicaprina ; 
	private float mediaCunicola ;
	private float mediaEquina ; 
	private float mediaAcquicoltura ; 
	private float mediaApiario ;
	private float mediaBufalina ;
	
	
	private int prescrizioneBovina ;
	public int getPrescrizioneBovina() {
		return prescrizioneBovina;
	}


	public void setPrescrizioneBovina(int prescrizioneBovina) {
		this.prescrizioneBovina = prescrizioneBovina;
	}


	public int getPrescrizioneSuina() {
		return prescrizioneSuina;
	}


	public void setPrescrizioneSuina(int prescrizioneSuina) {
		this.prescrizioneSuina = prescrizioneSuina;
	}


	public int getPrescrizioneAvicola() {
		return prescrizioneAvicola;
	}


	public void setPrescrizioneAvicola(int prescrizioneAvicola) {
		this.prescrizioneAvicola = prescrizioneAvicola;
	}


	public int getPrescrizioneOvicaprina() {
		return prescrizioneOvicaprina;
	}


	public void setPrescrizioneOvicaprina(int prescrizioneOvicaprina) {
		this.prescrizioneOvicaprina = prescrizioneOvicaprina;
	}


	public int getPrescrizioneCunicola() {
		return prescrizioneCunicola;
	}


	public void setPrescrizioneCunicola(int prescrizioneCunicola) {
		this.prescrizioneCunicola = prescrizioneCunicola;
	}


	public int getPrescrizioneEquina() {
		return prescrizioneEquina;
	}


	public void setPrescrizioneEquina(int prescrizioneEquina) {
		this.prescrizioneEquina = prescrizioneEquina;
	}


	public int getPrescrizioneAcquicoltura() {
		return prescrizioneAcquicoltura;
	}


	public void setPrescrizioneAcquicoltura(int prescrizioneAcquicoltura) {
		this.prescrizioneAcquicoltura = prescrizioneAcquicoltura;
	}


	public int getPrescrizioneApiario() {
		return prescrizioneApiario;
	}


	public void setPrescrizioneApiario(int prescrizioneApiario) {
		this.prescrizioneApiario = prescrizioneApiario;
	}


	public int getPrescrizioneBufalina() {
		return prescrizioneBufalina;
	}


	public void setPrescrizioneBufalina(int prescrizioneBufalina) {
		this.prescrizioneBufalina = prescrizioneBufalina;
	}


	public float getMediaBovina() {
		return mediaBovina;
	}


	public void setMediaBovina(float mediaBovina) {
		this.mediaBovina = mediaBovina;
	}


	public float getMediaSuina() {
		return mediaSuina;
	}


	public void setMediaSuina(float mediaSuina) {
		this.mediaSuina = mediaSuina;
	}


	public float getMediaAvicola() {
		return mediaAvicola;
	}


	public void setMediaAvicola(float mediaAvicola) {
		this.mediaAvicola = mediaAvicola;
	}


	public float getMediaOvicaprina() {
		return mediaOvicaprina;
	}


	public void setMediaOvicaprina(float mediaOvicaprina) {
		this.mediaOvicaprina = mediaOvicaprina;
	}


	public float getMediaCunicola() {
		return mediaCunicola;
	}

	public void setMediaCunicola(float mediaCunicola) {
		this.mediaCunicola = mediaCunicola;
	}


	public float getMediaEquina() {
		return mediaEquina;
	}


	public void setMediaEquina(float mediaEquina) {
		this.mediaEquina = mediaEquina;
	}


	public float getMediaAcquicoltura() {
		return mediaAcquicoltura;
	}


	public void setMediaAcquicoltura(float mediaAcquicoltura) {
		this.mediaAcquicoltura = mediaAcquicoltura;
	}


	public float getMediaApiario() {
		return mediaApiario;
	}


	public void setMediaApiario(float mediaApiario) {
		this.mediaApiario = mediaApiario;
	}


	public float getMediaBufalina() {
		return mediaBufalina;
	}

	public void setMediaBufalina(float mediaBufalina) {
		this.mediaBufalina = mediaBufalina;
	}
	
	private int stato = -1;
	private int modifiedby ;
	private Timestamp modified ;
	public int getModifiedby() {
		return modifiedby;
	}
	
	
	public int getStato() {
		return stato;
	}


	public void setStato(int stato) {
		this.stato = stato;
	}


	public void setModifiedby(int modifiedby) {
		this.modifiedby = modifiedby;
	}
	public Timestamp getModified() {
		return modified;
	}
	public void setModified(Timestamp modified) {
		this.modified = modified;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdAsl() {
		return idAsl;
	}
	public void setIdAsl(int idAsl) {
		this.idAsl = idAsl;
	}
	public String getDescrizioneAsl() {
		return descrizioneAsl;
	}
	public void setDescrizioneAsl(String descrizioneAsl) {
		this.descrizioneAsl = descrizioneAsl;
	}
	public int getAnno() {
		return anno;
	}
	public void setAnno(int anno) {
		this.anno = anno;
	}
	public int getAnimali_reddito_art_4_5() {
		return animali_reddito_art_4_5;
	}
	public void setAnimali_reddito_art_4_5(int animali_reddito_art_4_5) {
		this.animali_reddito_art_4_5 = animali_reddito_art_4_5;
	}
	public int getAnimali_reddito_art_11() {
		return animali_reddito_art_11;
	}
	public void setAnimali_reddito_art_11(int animali_reddito_art_11) {
		this.animali_reddito_art_11 = animali_reddito_art_11;
	}
	public int getAnimali_reddito_tot_a() {
		return animali_reddito_tot_a;
	}
	public void setAnimali_reddito_tot_a(int animali_reddito_tot_a) {
		this.animali_reddito_tot_a = animali_reddito_tot_a;
	}
	public int getMangimi_medicati_prod_intermedi_art_3_4() {
		return mangimi_medicati_prod_intermedi_art_3_4;
	}
	public void setMangimi_medicati_prod_intermedi_art_3_4(
			int mangimi_medicati_prod_intermedi_art_3_4) {
		this.mangimi_medicati_prod_intermedi_art_3_4 = mangimi_medicati_prod_intermedi_art_3_4;
	}
	public int getMangimi_medicati_prod_intermedi_art_16() {
		return mangimi_medicati_prod_intermedi_art_16;
	}
	public void setMangimi_medicati_prod_intermedi_art_16(
			int mangimi_medicati_prod_intermedi_art_16) {
		this.mangimi_medicati_prod_intermedi_art_16 = mangimi_medicati_prod_intermedi_art_16;
	}
	public int getMangimi_medicati_prod_intermedi_tot_b() {
		return mangimi_medicati_prod_intermedi_tot_b;
	}
	public void setMangimi_medicati_prod_intermedi_tot_b(
			int mangimi_medicati_prod_intermedi_tot_b) {
		this.mangimi_medicati_prod_intermedi_tot_b = mangimi_medicati_prod_intermedi_tot_b;
	}
	public int getScorte_vet_art_84() {
		return scorte_vet_art_84;
	}
	public void setScorte_vet_art_84(int scorte_vet_art_84) {
		this.scorte_vet_art_84 = scorte_vet_art_84;
	}
	public int getScorte_vet_tot_c() {
		return scorte_vet_tot_c;
	}
	public void setScorte_vet_tot_c(int scorte_vet_tot_c) {
		this.scorte_vet_tot_c = scorte_vet_tot_c;
	}
	public int getScorte_allev_da_reddito() {
		return scorte_allev_da_reddito;
	}
	public void setScorte_allev_da_reddito(int scorte_allev_da_reddito) {
		this.scorte_allev_da_reddito = scorte_allev_da_reddito;
	}
	public int getScorte_allev_da_compagnia() {
		return scorte_allev_da_compagnia;
	}
	public void setScorte_allev_da_compagnia(int scorte_allev_da_compagnia) {
		this.scorte_allev_da_compagnia = scorte_allev_da_compagnia;
	}
	public int getScorte_allev_da_ippodromi() {
		return scorte_allev_da_ippodromi;
	}
	public void setScorte_allev_da_ippodromi(int scorte_allev_da_ippodromi) {
		this.scorte_allev_da_ippodromi = scorte_allev_da_ippodromi;
	}
	public int getScorte_allev_tot_d() {
		return scorte_allev_tot_d;
	}
	public void setScorte_allev_tot_d(int scorte_allev_tot_d) {
		this.scorte_allev_tot_d = scorte_allev_tot_d;
	}
	
	
	public int getBovina() {
		return bovina;
	}
	public void setBovina(int bovina) {
		this.bovina = bovina;
	}
	public int getSuina() {
		return suina;
	}
	public void setSuina(int suina) {
		this.suina = suina;
	}
	public int getAvicola() {
		return avicola;
	}
	public void setAvicola(int avicola) {
		this.avicola = avicola;
	}
	public int getOvicaprina() {
		return ovicaprina;
	}
	public void setOvicaprina(int ovicaprina) {
		this.ovicaprina = ovicaprina;
	}
	public int getCunicola() {
		return cunicola;
	}
	public void setCunicola(int cunicola) {
		this.cunicola = cunicola;
	}
	public int getEquina() {
		return equina;
	}
	public void setEquina(int equina) {
		this.equina = equina;
	}
	public int getAcquicoltura() {
		return acquicoltura;
	}
	public void setAcquicoltura(int acquicoltura) {
		this.acquicoltura = acquicoltura;
	}
	public int getApiario() {
		return apiario;
	}
	public void setApiario(int apiario) {
		this.apiario = apiario;
	}
	public int getBufalina() {
		return bufalina;
	}
	public void setBufalina(int bufalina) {
		this.bufalina = bufalina;
	}
	
	public void update (Connection db) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, SQLException
	{
		String update = "update farmacosorveglianza_allegatoii set ";
		Field[] campi = RigaAllegatoII.class.getDeclaredFields();
		for(int i=0; i<campi.length; i++)
		{

		String nome_campo = campi[i].getName();
		if (!nome_campo.equalsIgnoreCase("id") && ! nome_campo.equalsIgnoreCase("idAsl") && ! nome_campo.equalsIgnoreCase("descrizioneAsl")
			&& ! nome_campo.equalsIgnoreCase("anno") )
		{
			 Method getMetodo = RigaAllegatoII.class.getMethod("get"+nome_campo.substring(0, 1).toUpperCase() + nome_campo.substring(1), null);
			 if (campi[i].getType()==Integer.class)
			 {
				 update += nome_campo+" = "+getMetodo.invoke(this,null); 
				 update += "," ;
			 }
			 else
			 {
				 update += nome_campo+" = '"+getMetodo.invoke(this,null)+"'"; 
				 update += "," ;
			 }
			
		}
			
		}
		update = update.substring(0,update.length()-1) + " where anno = "+anno + " and id_asl = "+idAsl;
		PreparedStatement pst = db.prepareStatement(update);
		pst.execute();
		
		
	}
	
	
	public void selectAllevamentiPerAsl (Connection db, int code) throws SQLException
	{
		
		String sql = " select specie_allev, count(*) from organization where tipologia = 2 and site_id = ? "+
					 " and trashed_date is null and specie_allev is not null group by specie_allev " +
			         " and site_id = ? and specie_allev ilike ? ";
				
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setInt(1, code);
		//pst.setString(2, specie);
		ResultSet rs = pst.executeQuery();
		
	}
	
	
	
}