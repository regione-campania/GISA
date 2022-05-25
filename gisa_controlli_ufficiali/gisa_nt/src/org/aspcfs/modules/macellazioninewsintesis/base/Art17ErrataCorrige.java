/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.macellazioninewsintesis.base;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Vector;

import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;


public class Art17ErrataCorrige extends GenericBean{
	
	private Timestamp entered = null;
	private int id = -1;
	private int idMacello = -1;
	private int idPartita = -1;
	private int idUtente = -1;
	private String ipUtente = "";
	private String nomeUtente = "";
	private Timestamp dataMacellazione = null;
	private String sottoscritto = null;
	private String numeroErrato = null;
	private String numeroCorretto = null;
	private String mod4Errato = null;
	private String mod4Corretto=null;
	private String altroErrato=null;
	private String altroCorretto=null;
	private String motivo = null;
	private String riferimentoArt17 = null;
	
	private boolean numeroModificato = false;
	private boolean mod4Modificato = false;
	private boolean altroModificato = false;
		
	private String destinatario1nomeErrato = null;
	private String destinatario1nomeCorretto = null;
	private int destinatario1idErrato = -1;
	private int destinatario1idCorretto = -1;
	private boolean destinatario1inregioneErrato;
	private boolean destinatario1inregioneCorretto;
	private boolean destinatario1Modificato = false;
	
	private String destinatario2nomeErrato = null;
	private String destinatario2nomeCorretto = null;
	private int destinatario2idErrato = -1;
	private int destinatario2idCorretto = -1;
	private boolean destinatario2inregioneErrato;
	private boolean destinatario2inregioneCorretto;
	private boolean destinatario2Modificato = false;

	private String destinatario3nomeErrato = null;
	private String destinatario3nomeCorretto = null;
	private int destinatario3idErrato = -1;
	private int destinatario3idCorretto = -1;
	private boolean destinatario3inregioneErrato;
	private boolean destinatario3inregioneCorretto;
	private boolean destinatario3Modificato = false;

	private String destinatario4nomeErrato = null;
	private String destinatario4nomeCorretto = null;
	private int destinatario4idErrato = -1;
	private int destinatario4idCorretto = -1;
	private boolean destinatario4inregioneErrato;
	private boolean destinatario4inregioneCorretto;
	private boolean destinatario4Modificato = false;
	
	private String destinatario5nomeErrato = null;
	private String destinatario5nomeCorretto = null;
	private int destinatario5idErrato = -1;
	private int destinatario5idCorretto = -1;
	private boolean destinatario5inregioneErrato;
	private boolean destinatario5inregioneCorretto;
	private boolean destinatario5Modificato = false;
	
	private String destinatario6nomeErrato = null;
	private String destinatario6nomeCorretto = null;
	private int destinatario6idErrato = -1;
	private int destinatario6idCorretto = -1;
	private boolean destinatario6inregioneErrato;
	private boolean destinatario6inregioneCorretto;
	private boolean destinatario6Modificato = false;
	
	private String destinatario7nomeErrato = null;
	private String destinatario7nomeCorretto = null;
	private int destinatario7idErrato = -1;
	private int destinatario7idCorretto = -1;
	private boolean destinatario7inregioneErrato;
	private boolean destinatario7inregioneCorretto;
	private boolean destinatario7Modificato = false;
	
	private String destinatario8nomeErrato = null;
	private String destinatario8nomeCorretto = null;
	private int destinatario8idErrato = -1;
	private int destinatario8idCorretto = -1;
	private boolean destinatario8inregioneErrato;
	private boolean destinatario8inregioneCorretto;
	private boolean destinatario8Modificato = false;
	
	private String destinatario9nomeErrato = null;
	private String destinatario9nomeCorretto = null;
	private int destinatario9idErrato = -1;
	private int destinatario9idCorretto = -1;
	private boolean destinatario9inregioneErrato;
	private boolean destinatario9inregioneCorretto;
	private boolean destinatario9Modificato = false;
	
	private String destinatario10nomeErrato = null;
	private String destinatario10nomeCorretto = null;
	private int destinatario10idErrato = -1;
	private int destinatario10idCorretto = -1;
	private boolean destinatario10inregioneErrato;
	private boolean destinatario10inregioneCorretto;
	private boolean destinatario10Modificato = false;
	
	private String destinatario11nomeErrato = null;
	private String destinatario11nomeCorretto = null;
	private int destinatario11idErrato = -1;
	private int destinatario11idCorretto = -1;
	private boolean destinatario11inregioneErrato;
	private boolean destinatario11inregioneCorretto;
	private boolean destinatario11Modificato = false;
	
	private String destinatario12nomeErrato = null;
	private String destinatario12nomeCorretto = null;
	private int destinatario12idErrato = -1;
	private int destinatario12idCorretto = -1;
	private boolean destinatario12inregioneErrato;
	private boolean destinatario12inregioneCorretto;
	private boolean destinatario12Modificato = false;
	
	private String destinatario13nomeErrato = null;
	private String destinatario13nomeCorretto = null;
	private int destinatario13idErrato = -1;
	private int destinatario13idCorretto = -1;
	private boolean destinatario13inregioneErrato;
	private boolean destinatario13inregioneCorretto;
	private boolean destinatario13Modificato = false;
	
	private String destinatario14nomeErrato = null;
	private String destinatario14nomeCorretto = null;
	private int destinatario14idErrato = -1;
	private int destinatario14idCorretto = -1;
	private boolean destinatario14inregioneErrato;
	private boolean destinatario14inregioneCorretto;
	private boolean destinatario14Modificato = false;
	
	private String destinatario15nomeErrato = null;
	private String destinatario15nomeCorretto = null;
	private int destinatario15idErrato = -1;
	private int destinatario15idCorretto = -1;
	private boolean destinatario15inregioneErrato;
	private boolean destinatario15inregioneCorretto;
	private boolean destinatario15Modificato = false;
	
	private String destinatario16nomeErrato = null;
	private String destinatario16nomeCorretto = null;
	private int destinatario16idErrato = -1;
	private int destinatario16idCorretto = -1;
	private boolean destinatario16inregioneErrato;
	private boolean destinatario16inregioneCorretto;
	private boolean destinatario16Modificato = false;
	
	private String destinatario17nomeErrato = null;
	private String destinatario17nomeCorretto = null;
	private int destinatario17idErrato = -1;
	private int destinatario17idCorretto = -1;
	private boolean destinatario17inregioneErrato;
	private boolean destinatario17inregioneCorretto;
	private boolean destinatario17Modificato = false;
	
	private String destinatario18nomeErrato = null;
	private String destinatario18nomeCorretto = null;
	private int destinatario18idErrato = -1;
	private int destinatario18idCorretto = -1;
	private boolean destinatario18inregioneErrato;
	private boolean destinatario18inregioneCorretto;
	private boolean destinatario18Modificato = false;
	
	private String destinatario19nomeErrato = null;
	private String destinatario19nomeCorretto = null;
	private int destinatario19idErrato = -1;
	private int destinatario19idCorretto = -1;
	private boolean destinatario19inregioneErrato;
	private boolean destinatario19inregioneCorretto;
	private boolean destinatario19Modificato = false;
	
	private String destinatario20nomeErrato = null;
	private String destinatario20nomeCorretto = null;
	private int destinatario20idErrato = -1;
	private int destinatario20idCorretto = -1;
	private boolean destinatario20inregioneErrato;
	private boolean destinatario20inregioneCorretto;
	private boolean destinatario20Modificato = false;
	
	private String veterinario1Errato = null;
	private String veterinario1Corretto = null;
	private boolean veterinario1Modificato = false;
	private String veterinario2Errato = null;
	private String veterinario2Corretto = null;
	private boolean veterinario2Modificato = false;
	private String veterinario3Errato = null;
	private String veterinario3Corretto = null;
	private boolean veterinario3Modificato = false;
	
	
	public void setIdMacello(String idMacello) {
		if (idMacello!=null && !idMacello.equals(""))
			this.idMacello = Integer.parseInt(idMacello);
	}
	
	public void setIdPartita(String idPartita) {
		if (idPartita!=null && !idPartita.equals(""))
			this.idPartita = Integer.parseInt(idPartita);
	}
	
	public void setDataMacellazione(String data) {
		this.dataMacellazione = DateUtils.parseDateStringNew(data, "dd/MM/yyyy");
	}
	
	public String getVeterinario1Errato() {
		return veterinario1Errato;
	}

	public void setVeterinario1Errato(String veterinario1Errato) {
		this.veterinario1Errato = veterinario1Errato;
	}

	public String getVeterinario1Corretto() {
		return veterinario1Corretto;
	}

	public void setVeterinario1Corretto(String veterinario1Corretto) {
		this.veterinario1Corretto = veterinario1Corretto;
	}

	public boolean isVeterinario1Modificato() {
		return veterinario1Modificato;
	}

	public void setVeterinario1Modificato(boolean veterinario1Modificato) {
		this.veterinario1Modificato = veterinario1Modificato;
	}

	public String getVeterinario2Errato() {
		return veterinario2Errato;
	}

	public void setVeterinario2Errato(String veterinario2Errato) {
		this.veterinario2Errato = veterinario2Errato;
	}

	public String getVeterinario2Corretto() {
		return veterinario2Corretto;
	}

	public void setVeterinario2Corretto(String veterinario2Corretto) {
		this.veterinario2Corretto = veterinario2Corretto;
	}

	public boolean isVeterinario2Modificato() {
		return veterinario2Modificato;
	}

	public void setVeterinario2Modificato(boolean veterinario2Modificato) {
		this.veterinario2Modificato = veterinario2Modificato;
	}

	public String getVeterinario3Errato() {
		return veterinario3Errato;
	}

	public void setVeterinario3Errato(String veterinario3Errato) {
		this.veterinario3Errato = veterinario3Errato;
	}

	public String getVeterinario3Corretto() {
		return veterinario3Corretto;
	}

	public void setVeterinario3Corretto(String veterinario3Corretto) {
		this.veterinario3Corretto = veterinario3Corretto;
	}

	public boolean isVeterinario3Modificato() {
		return veterinario3Modificato;
	}

	public void setVeterinario3Modificato(boolean veterinario3Modificato) {
		this.veterinario3Modificato = veterinario3Modificato;
	}

	public int getIdMacello() {
		return idMacello;
	}

	public void setIdMacello(int idMacello) {
		this.idMacello = idMacello;
	}

	public int getIdPartita() {
		return idPartita;
	}

	public void setIdPartita(int idPartita) {
		this.idPartita = idPartita;
	}

	public int getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}

	public String getIpUtente() {
		return ipUtente;
	}

	public void setIpUtente(String ipUtente) {
		this.ipUtente = ipUtente;
	}

	public Timestamp getDataMacellazione() {
		return dataMacellazione;
	}

	public void setDataMacellazione(Timestamp dataMacellazione) {
		this.dataMacellazione = dataMacellazione;
	}

	public String getSottoscritto() {
		return sottoscritto;
	}

	public void setSottoscritto(String sottoscritto) {
		this.sottoscritto = sottoscritto;
	}

	public String getNumeroErrato() {
		return numeroErrato;
	}

	public void setNumeroErrato(String numeroErrato) {
		this.numeroErrato = numeroErrato;
	}

	public String getNumeroCorretto() {
		return numeroCorretto;
	}

	public void setNumeroCorretto(String numeroCorretto) {
		this.numeroCorretto = numeroCorretto;
	}

	public String getMod4Errato() {
		return mod4Errato;
	}

	public void setMod4Errato(String mod4Errato) {
		this.mod4Errato = mod4Errato;
	}

	public String getMod4Corretto() {
		return mod4Corretto;
	}

	public void setMod4Corretto(String mod4Corretto) {
		this.mod4Corretto = mod4Corretto;
	}

	public String getAltroErrato() {
		return altroErrato;
	}

	public void setAltroErrato(String altroErrato) {
		this.altroErrato = altroErrato;
	}

	public String getAltroCorretto() {
		return altroCorretto;
	}

	public void setAltroCorretto(String altroCorretto) {
		this.altroCorretto = altroCorretto;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public boolean isNumeroModificato() {
		return numeroModificato;
	}

	public void setNumeroModificato(boolean numeroModificato) {
		this.numeroModificato = numeroModificato;
	}

	public boolean isMod4Modificato() {
		return mod4Modificato;
	}

	public void setMod4Modificato(boolean mod4Modificato) {
		this.mod4Modificato = mod4Modificato;
	}

	public boolean isAltroModificato() {
		return altroModificato;
	}

	public void setAltroModificato(boolean altroModificato) {
		this.altroModificato = altroModificato;
	}

	public String getDestinatario1nomeErrato() {
		return destinatario1nomeErrato;
	}

	public void setDestinatario1nomeErrato(String destinatario1nomeErrato) {
		this.destinatario1nomeErrato = destinatario1nomeErrato;
	}

	public String getDestinatario1nomeCorretto() {
		return destinatario1nomeCorretto;
	}

	public void setDestinatario1nomeCorretto(String destinatario1nomeCorretto) {
		this.destinatario1nomeCorretto = destinatario1nomeCorretto;
	}

	public int getDestinatario1idErrato() {
		return destinatario1idErrato;
	}

	public void setDestinatario1idErrato(int destinatario1idErrato) {
		this.destinatario1idErrato = destinatario1idErrato;
	}

	public int getDestinatario1idCorretto() {
		return destinatario1idCorretto;
	}

	public void setDestinatario1idCorretto(int destinatario1idCorretto) {
		this.destinatario1idCorretto = destinatario1idCorretto;
	}

	public boolean isDestinatario1inregioneErrato() {
		return destinatario1inregioneErrato;
	}

	public void setDestinatario1inregioneErrato(boolean destinatario1inregioneErrato) {
		this.destinatario1inregioneErrato = destinatario1inregioneErrato;
	}

	public boolean isDestinatario1inregioneCorretto() {
		return destinatario1inregioneCorretto;
	}

	public void setDestinatario1inregioneCorretto(boolean destinatario1inregioneCorretto) {
		this.destinatario1inregioneCorretto = destinatario1inregioneCorretto;
	}

	public boolean isDestinatario1Modificato() {
		return destinatario1Modificato;
	}

	public void setDestinatario1Modificato(boolean destinatario1Modificato) {
		this.destinatario1Modificato = destinatario1Modificato;
	}

	public String getDestinatario2nomeErrato() {
		return destinatario2nomeErrato;
	}

	public void setDestinatario2nomeErrato(String destinatario2nomeErrato) {
		this.destinatario2nomeErrato = destinatario2nomeErrato;
	}

	public String getDestinatario2nomeCorretto() {
		return destinatario2nomeCorretto;
	}

	public void setDestinatario2nomeCorretto(String destinatario2nomeCorretto) {
		this.destinatario2nomeCorretto = destinatario2nomeCorretto;
	}

	public int getDestinatario2idErrato() {
		return destinatario2idErrato;
	}

	public void setDestinatario2idErrato(int destinatario2idErrato) {
		this.destinatario2idErrato = destinatario2idErrato;
	}

	public int getDestinatario2idCorretto() {
		return destinatario2idCorretto;
	}

	public void setDestinatario2idCorretto(int destinatario2idCorretto) {
		this.destinatario2idCorretto = destinatario2idCorretto;
	}

	public boolean isDestinatario2inregioneErrato() {
		return destinatario2inregioneErrato;
	}

	public void setDestinatario2inregioneErrato(boolean destinatario2inregioneErrato) {
		this.destinatario2inregioneErrato = destinatario2inregioneErrato;
	}

	public boolean isDestinatario2inregioneCorretto() {
		return destinatario2inregioneCorretto;
	}

	public void setDestinatario2inregioneCorretto(boolean destinatario2inregioneCorretto) {
		this.destinatario2inregioneCorretto = destinatario2inregioneCorretto;
	}

	public boolean isDestinatario2Modificato() {
		return destinatario2Modificato;
	}

	public void setDestinatario2Modificato(boolean destinatario2Modificato) {
		this.destinatario2Modificato = destinatario2Modificato;
	}

	public String getDestinatario3nomeErrato() {
		return destinatario3nomeErrato;
	}

	public void setDestinatario3nomeErrato(String destinatario3nomeErrato) {
		this.destinatario3nomeErrato = destinatario3nomeErrato;
	}

	public String getDestinatario3nomeCorretto() {
		return destinatario3nomeCorretto;
	}

	public void setDestinatario3nomeCorretto(String destinatario3nomeCorretto) {
		this.destinatario3nomeCorretto = destinatario3nomeCorretto;
	}

	public int getDestinatario3idErrato() {
		return destinatario3idErrato;
	}

	public void setDestinatario3idErrato(int destinatario3idErrato) {
		this.destinatario3idErrato = destinatario3idErrato;
	}

	public int getDestinatario3idCorretto() {
		return destinatario3idCorretto;
	}

	public void setDestinatario3idCorretto(int destinatario3idCorretto) {
		this.destinatario3idCorretto = destinatario3idCorretto;
	}

	public boolean isDestinatario3inregioneErrato() {
		return destinatario3inregioneErrato;
	}

	public void setDestinatario3inregioneErrato(boolean destinatario3inregioneErrato) {
		this.destinatario3inregioneErrato = destinatario3inregioneErrato;
	}

	public boolean isDestinatario3inregioneCorretto() {
		return destinatario3inregioneCorretto;
	}

	public void setDestinatario3inregioneCorretto(boolean destinatario3inregioneCorretto) {
		this.destinatario3inregioneCorretto = destinatario3inregioneCorretto;
	}

	public boolean isDestinatario3Modificato() {
		return destinatario3Modificato;
	}

	public void setDestinatario3Modificato(boolean destinatario3Modificato) {
		this.destinatario3Modificato = destinatario3Modificato;
	}

	public String getDestinatario4nomeErrato() {
		return destinatario4nomeErrato;
	}

	public void setDestinatario4nomeErrato(String destinatario4nomeErrato) {
		this.destinatario4nomeErrato = destinatario4nomeErrato;
	}

	public String getDestinatario4nomeCorretto() {
		return destinatario4nomeCorretto;
	}

	public void setDestinatario4nomeCorretto(String destinatario4nomeCorretto) {
		this.destinatario4nomeCorretto = destinatario4nomeCorretto;
	}

	public int getDestinatario4idErrato() {
		return destinatario4idErrato;
	}

	public void setDestinatario4idErrato(int destinatario4idErrato) {
		this.destinatario4idErrato = destinatario4idErrato;
	}

	public int getDestinatario4idCorretto() {
		return destinatario4idCorretto;
	}

	public void setDestinatario4idCorretto(int destinatario4idCorretto) {
		this.destinatario4idCorretto = destinatario4idCorretto;
	}

	public boolean isDestinatario4inregioneErrato() {
		return destinatario4inregioneErrato;
	}

	public void setDestinatario4inregioneErrato(boolean destinatario4inregioneErrato) {
		this.destinatario4inregioneErrato = destinatario4inregioneErrato;
	}

	public boolean isDestinatario4inregioneCorretto() {
		return destinatario4inregioneCorretto;
	}

	public void setDestinatario4inregioneCorretto(boolean destinatario4inregioneCorretto) {
		this.destinatario4inregioneCorretto = destinatario4inregioneCorretto;
	}

	public boolean isDestinatario4Modificato() {
		return destinatario4Modificato;
	}

	public void setDestinatario4Modificato(boolean destinatario4Modificato) {
		this.destinatario4Modificato = destinatario4Modificato;
	}

	public String getDestinatario5nomeErrato() {
		return destinatario5nomeErrato;
	}

	public void setDestinatario5nomeErrato(String destinatario5nomeErrato) {
		this.destinatario5nomeErrato = destinatario5nomeErrato;
	}

	public String getDestinatario5nomeCorretto() {
		return destinatario5nomeCorretto;
	}

	public void setDestinatario5nomeCorretto(String destinatario5nomeCorretto) {
		this.destinatario5nomeCorretto = destinatario5nomeCorretto;
	}

	public int getDestinatario5idErrato() {
		return destinatario5idErrato;
	}

	public void setDestinatario5idErrato(int destinatario5idErrato) {
		this.destinatario5idErrato = destinatario5idErrato;
	}

	public int getDestinatario5idCorretto() {
		return destinatario5idCorretto;
	}

	public void setDestinatario5idCorretto(int destinatario5idCorretto) {
		this.destinatario5idCorretto = destinatario5idCorretto;
	}

	public boolean isDestinatario5inregioneErrato() {
		return destinatario5inregioneErrato;
	}

	public void setDestinatario5inregioneErrato(boolean destinatario5inregioneErrato) {
		this.destinatario5inregioneErrato = destinatario5inregioneErrato;
	}

	public boolean isDestinatario5inregioneCorretto() {
		return destinatario5inregioneCorretto;
	}

	public void setDestinatario5inregioneCorretto(boolean destinatario5inregioneCorretto) {
		this.destinatario5inregioneCorretto = destinatario5inregioneCorretto;
	}

	public boolean isDestinatario5Modificato() {
		return destinatario5Modificato;
	}

	public void setDestinatario5Modificato(boolean destinatario5Modificato) {
		this.destinatario5Modificato = destinatario5Modificato;
	}

	public String getDestinatario6nomeErrato() {
		return destinatario6nomeErrato;
	}

	public void setDestinatario6nomeErrato(String destinatario6nomeErrato) {
		this.destinatario6nomeErrato = destinatario6nomeErrato;
	}

	public String getDestinatario6nomeCorretto() {
		return destinatario6nomeCorretto;
	}

	public void setDestinatario6nomeCorretto(String destinatario6nomeCorretto) {
		this.destinatario6nomeCorretto = destinatario6nomeCorretto;
	}

	public int getDestinatario6idErrato() {
		return destinatario6idErrato;
	}

	public void setDestinatario6idErrato(int destinatario6idErrato) {
		this.destinatario6idErrato = destinatario6idErrato;
	}

	public int getDestinatario6idCorretto() {
		return destinatario6idCorretto;
	}

	public void setDestinatario6idCorretto(int destinatario6idCorretto) {
		this.destinatario6idCorretto = destinatario6idCorretto;
	}

	public boolean isDestinatario6inregioneErrato() {
		return destinatario6inregioneErrato;
	}

	public void setDestinatario6inregioneErrato(boolean destinatario6inregioneErrato) {
		this.destinatario6inregioneErrato = destinatario6inregioneErrato;
	}

	public boolean isDestinatario6inregioneCorretto() {
		return destinatario6inregioneCorretto;
	}

	public void setDestinatario6inregioneCorretto(boolean destinatario6inregioneCorretto) {
		this.destinatario6inregioneCorretto = destinatario6inregioneCorretto;
	}

	public boolean isDestinatario6Modificato() {
		return destinatario6Modificato;
	}

	public void setDestinatario6Modificato(boolean destinatario6Modificato) {
		this.destinatario6Modificato = destinatario6Modificato;
	}

	public String getDestinatario7nomeErrato() {
		return destinatario7nomeErrato;
	}

	public void setDestinatario7nomeErrato(String destinatario7nomeErrato) {
		this.destinatario7nomeErrato = destinatario7nomeErrato;
	}

	public String getDestinatario7nomeCorretto() {
		return destinatario7nomeCorretto;
	}

	public void setDestinatario7nomeCorretto(String destinatario7nomeCorretto) {
		this.destinatario7nomeCorretto = destinatario7nomeCorretto;
	}

	public int getDestinatario7idErrato() {
		return destinatario7idErrato;
	}

	public void setDestinatario7idErrato(int destinatario7idErrato) {
		this.destinatario7idErrato = destinatario7idErrato;
	}

	public int getDestinatario7idCorretto() {
		return destinatario7idCorretto;
	}

	public void setDestinatario7idCorretto(int destinatario7idCorretto) {
		this.destinatario7idCorretto = destinatario7idCorretto;
	}

	public boolean isDestinatario7inregioneErrato() {
		return destinatario7inregioneErrato;
	}

	public void setDestinatario7inregioneErrato(boolean destinatario7inregioneErrato) {
		this.destinatario7inregioneErrato = destinatario7inregioneErrato;
	}

	public boolean isDestinatario7inregioneCorretto() {
		return destinatario7inregioneCorretto;
	}

	public void setDestinatario7inregioneCorretto(boolean destinatario7inregioneCorretto) {
		this.destinatario7inregioneCorretto = destinatario7inregioneCorretto;
	}

	public boolean isDestinatario7Modificato() {
		return destinatario7Modificato;
	}

	public void setDestinatario7Modificato(boolean destinatario7Modificato) {
		this.destinatario7Modificato = destinatario7Modificato;
	}

	public String getDestinatario8nomeErrato() {
		return destinatario8nomeErrato;
	}

	public void setDestinatario8nomeErrato(String destinatario8nomeErrato) {
		this.destinatario8nomeErrato = destinatario8nomeErrato;
	}

	public String getDestinatario8nomeCorretto() {
		return destinatario8nomeCorretto;
	}

	public void setDestinatario8nomeCorretto(String destinatario8nomeCorretto) {
		this.destinatario8nomeCorretto = destinatario8nomeCorretto;
	}

	public int getDestinatario8idErrato() {
		return destinatario8idErrato;
	}

	public void setDestinatario8idErrato(int destinatario8idErrato) {
		this.destinatario8idErrato = destinatario8idErrato;
	}

	public int getDestinatario8idCorretto() {
		return destinatario8idCorretto;
	}

	public void setDestinatario8idCorretto(int destinatario8idCorretto) {
		this.destinatario8idCorretto = destinatario8idCorretto;
	}

	public boolean isDestinatario8inregioneErrato() {
		return destinatario8inregioneErrato;
	}

	public void setDestinatario8inregioneErrato(boolean destinatario8inregioneErrato) {
		this.destinatario8inregioneErrato = destinatario8inregioneErrato;
	}

	public boolean isDestinatario8inregioneCorretto() {
		return destinatario8inregioneCorretto;
	}

	public void setDestinatario8inregioneCorretto(boolean destinatario8inregioneCorretto) {
		this.destinatario8inregioneCorretto = destinatario8inregioneCorretto;
	}

	public boolean isDestinatario8Modificato() {
		return destinatario8Modificato;
	}

	public void setDestinatario8Modificato(boolean destinatario8Modificato) {
		this.destinatario8Modificato = destinatario8Modificato;
	}

	public String getDestinatario9nomeErrato() {
		return destinatario9nomeErrato;
	}

	public void setDestinatario9nomeErrato(String destinatario9nomeErrato) {
		this.destinatario9nomeErrato = destinatario9nomeErrato;
	}

	public String getDestinatario9nomeCorretto() {
		return destinatario9nomeCorretto;
	}

	public void setDestinatario9nomeCorretto(String destinatario9nomeCorretto) {
		this.destinatario9nomeCorretto = destinatario9nomeCorretto;
	}

	public int getDestinatario9idErrato() {
		return destinatario9idErrato;
	}

	public void setDestinatario9idErrato(int destinatario9idErrato) {
		this.destinatario9idErrato = destinatario9idErrato;
	}

	public int getDestinatario9idCorretto() {
		return destinatario9idCorretto;
	}

	public void setDestinatario9idCorretto(int destinatario9idCorretto) {
		this.destinatario9idCorretto = destinatario9idCorretto;
	}

	public boolean isDestinatario9inregioneErrato() {
		return destinatario9inregioneErrato;
	}

	public void setDestinatario9inregioneErrato(boolean destinatario9inregioneErrato) {
		this.destinatario9inregioneErrato = destinatario9inregioneErrato;
	}

	public boolean isDestinatario9inregioneCorretto() {
		return destinatario9inregioneCorretto;
	}

	public void setDestinatario9inregioneCorretto(boolean destinatario9inregioneCorretto) {
		this.destinatario9inregioneCorretto = destinatario9inregioneCorretto;
	}

	public boolean isDestinatario9Modificato() {
		return destinatario9Modificato;
	}

	public void setDestinatario9Modificato(boolean destinatario9Modificato) {
		this.destinatario9Modificato = destinatario9Modificato;
	}

	public String getDestinatario10nomeErrato() {
		return destinatario10nomeErrato;
	}

	public void setDestinatario10nomeErrato(String destinatario10nomeErrato) {
		this.destinatario10nomeErrato = destinatario10nomeErrato;
	}

	public String getDestinatario10nomeCorretto() {
		return destinatario10nomeCorretto;
	}

	public void setDestinatario10nomeCorretto(String destinatario10nomeCorretto) {
		this.destinatario10nomeCorretto = destinatario10nomeCorretto;
	}

	public int getDestinatario10idErrato() {
		return destinatario10idErrato;
	}

	public void setDestinatario10idErrato(int destinatario10idErrato) {
		this.destinatario10idErrato = destinatario10idErrato;
	}

	public int getDestinatario10idCorretto() {
		return destinatario10idCorretto;
	}

	public void setDestinatario10idCorretto(int destinatario10idCorretto) {
		this.destinatario10idCorretto = destinatario10idCorretto;
	}

	public boolean isDestinatario10inregioneErrato() {
		return destinatario10inregioneErrato;
	}

	public void setDestinatario10inregioneErrato(boolean destinatario10inregioneErrato) {
		this.destinatario10inregioneErrato = destinatario10inregioneErrato;
	}

	public boolean isDestinatario10inregioneCorretto() {
		return destinatario10inregioneCorretto;
	}

	public void setDestinatario10inregioneCorretto(boolean destinatario10inregioneCorretto) {
		this.destinatario10inregioneCorretto = destinatario10inregioneCorretto;
	}

	public boolean isDestinatario10Modificato() {
		return destinatario10Modificato;
	}

	public void setDestinatario10Modificato(boolean destinatario10Modificato) {
		this.destinatario10Modificato = destinatario10Modificato;
	}

	public String getDestinatario11nomeErrato() {
		return destinatario11nomeErrato;
	}

	public void setDestinatario11nomeErrato(String destinatario11nomeErrato) {
		this.destinatario11nomeErrato = destinatario11nomeErrato;
	}

	public String getDestinatario11nomeCorretto() {
		return destinatario11nomeCorretto;
	}

	public void setDestinatario11nomeCorretto(String destinatario11nomeCorretto) {
		this.destinatario11nomeCorretto = destinatario11nomeCorretto;
	}

	public int getDestinatario11idErrato() {
		return destinatario11idErrato;
	}

	public void setDestinatario11idErrato(int destinatario11idErrato) {
		this.destinatario11idErrato = destinatario11idErrato;
	}

	public int getDestinatario11idCorretto() {
		return destinatario11idCorretto;
	}

	public void setDestinatario11idCorretto(int destinatario11idCorretto) {
		this.destinatario11idCorretto = destinatario11idCorretto;
	}

	public boolean isDestinatario11inregioneErrato() {
		return destinatario11inregioneErrato;
	}

	public void setDestinatario11inregioneErrato(boolean destinatario11inregioneErrato) {
		this.destinatario11inregioneErrato = destinatario11inregioneErrato;
	}

	public boolean isDestinatario11inregioneCorretto() {
		return destinatario11inregioneCorretto;
	}

	public void setDestinatario11inregioneCorretto(boolean destinatario11inregioneCorretto) {
		this.destinatario11inregioneCorretto = destinatario11inregioneCorretto;
	}

	public boolean isDestinatario11Modificato() {
		return destinatario11Modificato;
	}

	public void setDestinatario11Modificato(boolean destinatario11Modificato) {
		this.destinatario11Modificato = destinatario11Modificato;
	}

	public String getDestinatario12nomeErrato() {
		return destinatario12nomeErrato;
	}

	public void setDestinatario12nomeErrato(String destinatario12nomeErrato) {
		this.destinatario12nomeErrato = destinatario12nomeErrato;
	}

	public String getDestinatario12nomeCorretto() {
		return destinatario12nomeCorretto;
	}

	public void setDestinatario12nomeCorretto(String destinatario12nomeCorretto) {
		this.destinatario12nomeCorretto = destinatario12nomeCorretto;
	}

	public int getDestinatario12idErrato() {
		return destinatario12idErrato;
	}

	public void setDestinatario12idErrato(int destinatario12idErrato) {
		this.destinatario12idErrato = destinatario12idErrato;
	}

	public int getDestinatario12idCorretto() {
		return destinatario12idCorretto;
	}

	public void setDestinatario12idCorretto(int destinatario12idCorretto) {
		this.destinatario12idCorretto = destinatario12idCorretto;
	}

	public boolean isDestinatario12inregioneErrato() {
		return destinatario12inregioneErrato;
	}

	public void setDestinatario12inregioneErrato(boolean destinatario12inregioneErrato) {
		this.destinatario12inregioneErrato = destinatario12inregioneErrato;
	}

	public boolean isDestinatario12inregioneCorretto() {
		return destinatario12inregioneCorretto;
	}

	public void setDestinatario12inregioneCorretto(boolean destinatario12inregioneCorretto) {
		this.destinatario12inregioneCorretto = destinatario12inregioneCorretto;
	}

	public boolean isDestinatario12Modificato() {
		return destinatario12Modificato;
	}

	public void setDestinatario12Modificato(boolean destinatario12Modificato) {
		this.destinatario12Modificato = destinatario12Modificato;
	}

	public String getDestinatario13nomeErrato() {
		return destinatario13nomeErrato;
	}

	public void setDestinatario13nomeErrato(String destinatario13nomeErrato) {
		this.destinatario13nomeErrato = destinatario13nomeErrato;
	}

	public String getDestinatario13nomeCorretto() {
		return destinatario13nomeCorretto;
	}

	public void setDestinatario13nomeCorretto(String destinatario13nomeCorretto) {
		this.destinatario13nomeCorretto = destinatario13nomeCorretto;
	}

	public int getDestinatario13idErrato() {
		return destinatario13idErrato;
	}

	public void setDestinatario13idErrato(int destinatario13idErrato) {
		this.destinatario13idErrato = destinatario13idErrato;
	}

	public int getDestinatario13idCorretto() {
		return destinatario13idCorretto;
	}

	public void setDestinatario13idCorretto(int destinatario13idCorretto) {
		this.destinatario13idCorretto = destinatario13idCorretto;
	}

	public boolean isDestinatario13inregioneErrato() {
		return destinatario13inregioneErrato;
	}

	public void setDestinatario13inregioneErrato(boolean destinatario13inregioneErrato) {
		this.destinatario13inregioneErrato = destinatario13inregioneErrato;
	}

	public boolean isDestinatario13inregioneCorretto() {
		return destinatario13inregioneCorretto;
	}

	public void setDestinatario13inregioneCorretto(boolean destinatario13inregioneCorretto) {
		this.destinatario13inregioneCorretto = destinatario13inregioneCorretto;
	}

	public boolean isDestinatario13Modificato() {
		return destinatario13Modificato;
	}

	public void setDestinatario13Modificato(boolean destinatario13Modificato) {
		this.destinatario13Modificato = destinatario13Modificato;
	}

	public String getDestinatario14nomeErrato() {
		return destinatario14nomeErrato;
	}

	public void setDestinatario14nomeErrato(String destinatario14nomeErrato) {
		this.destinatario14nomeErrato = destinatario14nomeErrato;
	}

	public String getDestinatario14nomeCorretto() {
		return destinatario14nomeCorretto;
	}

	public void setDestinatario14nomeCorretto(String destinatario14nomeCorretto) {
		this.destinatario14nomeCorretto = destinatario14nomeCorretto;
	}

	public int getDestinatario14idErrato() {
		return destinatario14idErrato;
	}

	public void setDestinatario14idErrato(int destinatario14idErrato) {
		this.destinatario14idErrato = destinatario14idErrato;
	}

	public int getDestinatario14idCorretto() {
		return destinatario14idCorretto;
	}

	public void setDestinatario14idCorretto(int destinatario14idCorretto) {
		this.destinatario14idCorretto = destinatario14idCorretto;
	}

	public boolean isDestinatario14inregioneErrato() {
		return destinatario14inregioneErrato;
	}

	public void setDestinatario14inregioneErrato(boolean destinatario14inregioneErrato) {
		this.destinatario14inregioneErrato = destinatario14inregioneErrato;
	}

	public boolean isDestinatario14inregioneCorretto() {
		return destinatario14inregioneCorretto;
	}

	public void setDestinatario14inregioneCorretto(boolean destinatario14inregioneCorretto) {
		this.destinatario14inregioneCorretto = destinatario14inregioneCorretto;
	}

	public boolean isDestinatario14Modificato() {
		return destinatario14Modificato;
	}

	public void setDestinatario14Modificato(boolean destinatario14Modificato) {
		this.destinatario14Modificato = destinatario14Modificato;
	}

	public String getDestinatario15nomeErrato() {
		return destinatario15nomeErrato;
	}

	public void setDestinatario15nomeErrato(String destinatario15nomeErrato) {
		this.destinatario15nomeErrato = destinatario15nomeErrato;
	}

	public String getDestinatario15nomeCorretto() {
		return destinatario15nomeCorretto;
	}

	public void setDestinatario15nomeCorretto(String destinatario15nomeCorretto) {
		this.destinatario15nomeCorretto = destinatario15nomeCorretto;
	}

	public int getDestinatario15idErrato() {
		return destinatario15idErrato;
	}

	public void setDestinatario15idErrato(int destinatario15idErrato) {
		this.destinatario15idErrato = destinatario15idErrato;
	}

	public int getDestinatario15idCorretto() {
		return destinatario15idCorretto;
	}

	public void setDestinatario15idCorretto(int destinatario15idCorretto) {
		this.destinatario15idCorretto = destinatario15idCorretto;
	}

	public boolean isDestinatario15inregioneErrato() {
		return destinatario15inregioneErrato;
	}

	public void setDestinatario15inregioneErrato(boolean destinatario15inregioneErrato) {
		this.destinatario15inregioneErrato = destinatario15inregioneErrato;
	}

	public boolean isDestinatario15inregioneCorretto() {
		return destinatario15inregioneCorretto;
	}

	public void setDestinatario15inregioneCorretto(boolean destinatario15inregioneCorretto) {
		this.destinatario15inregioneCorretto = destinatario15inregioneCorretto;
	}

	public boolean isDestinatario15Modificato() {
		return destinatario15Modificato;
	}

	public void setDestinatario15Modificato(boolean destinatario15Modificato) {
		this.destinatario15Modificato = destinatario15Modificato;
	}

	public String getDestinatario16nomeErrato() {
		return destinatario16nomeErrato;
	}

	public void setDestinatario16nomeErrato(String destinatario16nomeErrato) {
		this.destinatario16nomeErrato = destinatario16nomeErrato;
	}

	public String getDestinatario16nomeCorretto() {
		return destinatario16nomeCorretto;
	}

	public void setDestinatario16nomeCorretto(String destinatario16nomeCorretto) {
		this.destinatario16nomeCorretto = destinatario16nomeCorretto;
	}

	public int getDestinatario16idErrato() {
		return destinatario16idErrato;
	}

	public void setDestinatario16idErrato(int destinatario16idErrato) {
		this.destinatario16idErrato = destinatario16idErrato;
	}

	public int getDestinatario16idCorretto() {
		return destinatario16idCorretto;
	}

	public void setDestinatario16idCorretto(int destinatario16idCorretto) {
		this.destinatario16idCorretto = destinatario16idCorretto;
	}

	public boolean isDestinatario16inregioneErrato() {
		return destinatario16inregioneErrato;
	}

	public void setDestinatario16inregioneErrato(boolean destinatario16inregioneErrato) {
		this.destinatario16inregioneErrato = destinatario16inregioneErrato;
	}

	public boolean isDestinatario16inregioneCorretto() {
		return destinatario16inregioneCorretto;
	}

	public void setDestinatario16inregioneCorretto(boolean destinatario16inregioneCorretto) {
		this.destinatario16inregioneCorretto = destinatario16inregioneCorretto;
	}

	public boolean isDestinatario16Modificato() {
		return destinatario16Modificato;
	}

	public void setDestinatario16Modificato(boolean destinatario16Modificato) {
		this.destinatario16Modificato = destinatario16Modificato;
	}

	public String getDestinatario17nomeErrato() {
		return destinatario17nomeErrato;
	}

	public void setDestinatario17nomeErrato(String destinatario17nomeErrato) {
		this.destinatario17nomeErrato = destinatario17nomeErrato;
	}

	public String getDestinatario17nomeCorretto() {
		return destinatario17nomeCorretto;
	}

	public void setDestinatario17nomeCorretto(String destinatario17nomeCorretto) {
		this.destinatario17nomeCorretto = destinatario17nomeCorretto;
	}

	public int getDestinatario17idErrato() {
		return destinatario17idErrato;
	}

	public void setDestinatario17idErrato(int destinatario17idErrato) {
		this.destinatario17idErrato = destinatario17idErrato;
	}

	public int getDestinatario17idCorretto() {
		return destinatario17idCorretto;
	}

	public void setDestinatario17idCorretto(int destinatario17idCorretto) {
		this.destinatario17idCorretto = destinatario17idCorretto;
	}

	public boolean isDestinatario17inregioneErrato() {
		return destinatario17inregioneErrato;
	}

	public void setDestinatario17inregioneErrato(boolean destinatario17inregioneErrato) {
		this.destinatario17inregioneErrato = destinatario17inregioneErrato;
	}

	public boolean isDestinatario17inregioneCorretto() {
		return destinatario17inregioneCorretto;
	}

	public void setDestinatario17inregioneCorretto(boolean destinatario17inregioneCorretto) {
		this.destinatario17inregioneCorretto = destinatario17inregioneCorretto;
	}

	public boolean isDestinatario17Modificato() {
		return destinatario17Modificato;
	}

	public void setDestinatario17Modificato(boolean destinatario17Modificato) {
		this.destinatario17Modificato = destinatario17Modificato;
	}

	public String getDestinatario18nomeErrato() {
		return destinatario18nomeErrato;
	}

	public void setDestinatario18nomeErrato(String destinatario18nomeErrato) {
		this.destinatario18nomeErrato = destinatario18nomeErrato;
	}

	public String getDestinatario18nomeCorretto() {
		return destinatario18nomeCorretto;
	}

	public void setDestinatario18nomeCorretto(String destinatario18nomeCorretto) {
		this.destinatario18nomeCorretto = destinatario18nomeCorretto;
	}

	public int getDestinatario18idErrato() {
		return destinatario18idErrato;
	}

	public void setDestinatario18idErrato(int destinatario18idErrato) {
		this.destinatario18idErrato = destinatario18idErrato;
	}

	public int getDestinatario18idCorretto() {
		return destinatario18idCorretto;
	}

	public void setDestinatario18idCorretto(int destinatario18idCorretto) {
		this.destinatario18idCorretto = destinatario18idCorretto;
	}

	public boolean isDestinatario18inregioneErrato() {
		return destinatario18inregioneErrato;
	}

	public void setDestinatario18inregioneErrato(boolean destinatario18inregioneErrato) {
		this.destinatario18inregioneErrato = destinatario18inregioneErrato;
	}

	public boolean isDestinatario18inregioneCorretto() {
		return destinatario18inregioneCorretto;
	}

	public void setDestinatario18inregioneCorretto(boolean destinatario18inregioneCorretto) {
		this.destinatario18inregioneCorretto = destinatario18inregioneCorretto;
	}

	public boolean isDestinatario18Modificato() {
		return destinatario18Modificato;
	}

	public void setDestinatario18Modificato(boolean destinatario18Modificato) {
		this.destinatario18Modificato = destinatario18Modificato;
	}

	public String getDestinatario19nomeErrato() {
		return destinatario19nomeErrato;
	}

	public void setDestinatario19nomeErrato(String destinatario19nomeErrato) {
		this.destinatario19nomeErrato = destinatario19nomeErrato;
	}

	public String getDestinatario19nomeCorretto() {
		return destinatario19nomeCorretto;
	}

	public void setDestinatario19nomeCorretto(String destinatario19nomeCorretto) {
		this.destinatario19nomeCorretto = destinatario19nomeCorretto;
	}

	public int getDestinatario19idErrato() {
		return destinatario19idErrato;
	}

	public void setDestinatario19idErrato(int destinatario19idErrato) {
		this.destinatario19idErrato = destinatario19idErrato;
	}

	public int getDestinatario19idCorretto() {
		return destinatario19idCorretto;
	}

	public void setDestinatario19idCorretto(int destinatario19idCorretto) {
		this.destinatario19idCorretto = destinatario19idCorretto;
	}

	public boolean isDestinatario19inregioneErrato() {
		return destinatario19inregioneErrato;
	}

	public void setDestinatario19inregioneErrato(boolean destinatario19inregioneErrato) {
		this.destinatario19inregioneErrato = destinatario19inregioneErrato;
	}

	public boolean isDestinatario19inregioneCorretto() {
		return destinatario19inregioneCorretto;
	}

	public void setDestinatario19inregioneCorretto(boolean destinatario19inregioneCorretto) {
		this.destinatario19inregioneCorretto = destinatario19inregioneCorretto;
	}

	public boolean isDestinatario19Modificato() {
		return destinatario19Modificato;
	}

	public void setDestinatario19Modificato(boolean destinatario19Modificato) {
		this.destinatario19Modificato = destinatario19Modificato;
	}

	public String getDestinatario20nomeErrato() {
		return destinatario20nomeErrato;
	}

	public void setDestinatario20nomeErrato(String destinatario20nomeErrato) {
		this.destinatario20nomeErrato = destinatario20nomeErrato;
	}

	public String getDestinatario20nomeCorretto() {
		return destinatario20nomeCorretto;
	}

	public void setDestinatario20nomeCorretto(String destinatario20nomeCorretto) {
		this.destinatario20nomeCorretto = destinatario20nomeCorretto;
	}

	public int getDestinatario20idErrato() {
		return destinatario20idErrato;
	}

	public void setDestinatario20idErrato(int destinatario20idErrato) {
		this.destinatario20idErrato = destinatario20idErrato;
	}

	public int getDestinatario20idCorretto() {
		return destinatario20idCorretto;
	}

	public void setDestinatario20idCorretto(int destinatario20idCorretto) {
		this.destinatario20idCorretto = destinatario20idCorretto;
	}

	public boolean isDestinatario20inregioneErrato() {
		return destinatario20inregioneErrato;
	}

	public void setDestinatario20inregioneErrato(boolean destinatario20inregioneErrato) {
		this.destinatario20inregioneErrato = destinatario20inregioneErrato;
	}

	public boolean isDestinatario20inregioneCorretto() {
		return destinatario20inregioneCorretto;
	}

	public void setDestinatario20inregioneCorretto(boolean destinatario20inregioneCorretto) {
		this.destinatario20inregioneCorretto = destinatario20inregioneCorretto;
	}

	public boolean isDestinatario20Modificato() {
		return destinatario20Modificato;
	}

	public void setDestinatario20Modificato(boolean destinatario20Modificato) {
		this.destinatario20Modificato = destinatario20Modificato;
	}

	
	public void setDestinatario1idErrato(String destinatario1idErrato) {
		try{ this.destinatario1idErrato = Integer.parseInt(destinatario1idErrato);} catch (Exception e)	{}
	}
	public void setDestinatario1idCorretto(String destinatario1idCorretto) {
		try{this.destinatario1idCorretto = Integer.parseInt(destinatario1idCorretto);} catch (Exception e){}
	}
	
	public void setDestinatario1inregioneErrato(String destinatario1inregioneErrato) {
		try{this.destinatario1inregioneErrato = Boolean.parseBoolean(destinatario1inregioneErrato);} catch (Exception e){}
	}
	public void setDestinatario1inregioneCorretto(String destinatario1inregioneCorretto) {
		try{this.destinatario1inregioneCorretto = Boolean.parseBoolean(destinatario1inregioneCorretto);} catch (Exception e){}
	}
		
	public void setDestinatario2idErrato(String destinatario2idErrato) {
		try{this.destinatario2idErrato = Integer.parseInt(destinatario2idErrato);} catch (Exception e){}
	}
	public void setDestinatario2idCorretto(String destinatario2idCorretto) {
		try{this.destinatario2idCorretto = Integer.parseInt(destinatario2idCorretto);} catch (Exception e){}
	}
	
	public void setDestinatario2inregioneErrato(String destinatario2inregioneErrato) {
		try{this.destinatario2inregioneErrato = Boolean.parseBoolean(destinatario2inregioneErrato);} catch (Exception e){}
	}
	public void setDestinatario2inregioneCorretto(String destinatario2inregioneCorretto) {
		try{this.destinatario2inregioneCorretto = Boolean.parseBoolean(destinatario2inregioneCorretto);} catch (Exception e){}
	}
	
	public void setDestinatario3idErrato(String destinatario3idErrato) {
		try{this.destinatario3idErrato = Integer.parseInt(destinatario3idErrato);} catch (Exception e){}
	}
	public void setDestinatario3idCorretto(String destinatario3idCorretto) {
		try{this.destinatario3idCorretto = Integer.parseInt(destinatario3idCorretto);} catch (Exception e){}
	}
	
	public void setDestinatario3inregioneErrato(String destinatario3inregioneErrato) {
		try{this.destinatario3inregioneErrato = Boolean.parseBoolean(destinatario3inregioneErrato);} catch (Exception e){}
	}
	public void setDestinatario3inregioneCorretto(String destinatario3inregioneCorretto) {
		try{this.destinatario3inregioneCorretto = Boolean.parseBoolean(destinatario3inregioneCorretto);} catch (Exception e){}
	}
	
	public void setDestinatario4idErrato(String destinatario4idErrato) {
		try{this.destinatario4idErrato = Integer.parseInt(destinatario4idErrato);} catch (Exception e){}
	}
	public void setDestinatario4idCorretto(String destinatario4idCorretto) {
		try{this.destinatario4idCorretto = Integer.parseInt(destinatario4idCorretto);} catch (Exception e){}
	}
	
	public void setDestinatario4inregioneErrato(String destinatario4inregioneErrato) {
		try{this.destinatario4inregioneErrato = Boolean.parseBoolean(destinatario4inregioneErrato);} catch (Exception e){}
	}
	public void setDestinatario4inregioneCorretto(String destinatario4inregioneCorretto) {
		try{this.destinatario4inregioneCorretto = Boolean.parseBoolean(destinatario4inregioneCorretto);} catch (Exception e){}
	}
	
	public void setDestinatario5idErrato(String destinatario5idErrato) {
		try{this.destinatario5idErrato = Integer.parseInt(destinatario5idErrato);} catch (Exception e){}
	}
	public void setDestinatario5idCorretto(String destinatario5idCorretto) {
		try{this.destinatario5idCorretto = Integer.parseInt(destinatario5idCorretto);} catch (Exception e){}
	}
	
	public void setDestinatario5inregioneErrato(String destinatario5inregioneErrato) {
		try{this.destinatario5inregioneErrato = Boolean.parseBoolean(destinatario5inregioneErrato);} catch (Exception e){}
	}
	public void setDestinatario5inregioneCorretto(String destinatario5inregioneCorretto) {
		try{this.destinatario5inregioneCorretto = Boolean.parseBoolean(destinatario5inregioneCorretto);} catch (Exception e){}
	}
	
	public void setDestinatario6idErrato(String destinatario6idErrato) {
		try{this.destinatario6idErrato = Integer.parseInt(destinatario6idErrato);} catch (Exception e){}
	}
	public void setDestinatario6idCorretto(String destinatario6idCorretto) {
		try{this.destinatario6idCorretto = Integer.parseInt(destinatario6idCorretto);} catch (Exception e){}
	}
	
	public void setDestinatario6inregioneErrato(String destinatario6inregioneErrato) {
		try{this.destinatario6inregioneErrato = Boolean.parseBoolean(destinatario6inregioneErrato);} catch (Exception e){}
	}
	public void setDestinatario6inregioneCorretto(String destinatario6inregioneCorretto) {
		try{this.destinatario6inregioneCorretto = Boolean.parseBoolean(destinatario6inregioneCorretto);} catch (Exception e){}
	}
	
	public void setDestinatario7idErrato(String destinatario7idErrato) {
		try{this.destinatario7idErrato = Integer.parseInt(destinatario7idErrato);} catch (Exception e){}
	}
	public void setDestinatario7idCorretto(String destinatario7idCorretto) {
		try{this.destinatario7idCorretto = Integer.parseInt(destinatario7idCorretto);} catch (Exception e){}
	}
	
	public void setDestinatario7inregioneErrato(String destinatario7inregioneErrato) {
		try{this.destinatario7inregioneErrato = Boolean.parseBoolean(destinatario7inregioneErrato);} catch (Exception e){}
	}
	public void setDestinatario7inregioneCorretto(String destinatario7inregioneCorretto) {
		try{this.destinatario7inregioneCorretto = Boolean.parseBoolean(destinatario7inregioneCorretto);} catch (Exception e){}
	}
	
	public void setDestinatario8idErrato(String destinatario8idErrato) {
		try{this.destinatario8idErrato = Integer.parseInt(destinatario8idErrato);} catch (Exception e){}
	}
	public void setDestinatario8idCorretto(String destinatario8idCorretto) {
		try{this.destinatario8idCorretto = Integer.parseInt(destinatario8idCorretto);} catch (Exception e){}
	}
	
	public void setDestinatario8inregioneErrato(String destinatario8inregioneErrato) {
		try{this.destinatario8inregioneErrato = Boolean.parseBoolean(destinatario8inregioneErrato);} catch (Exception e){}
	}
	public void setDestinatario8inregioneCorretto(String destinatario8inregioneCorretto) {
		try{this.destinatario8inregioneCorretto = Boolean.parseBoolean(destinatario8inregioneCorretto);} catch (Exception e){}
	}
	
	public void setDestinatario9idErrato(String destinatario9idErrato) {
		try{this.destinatario9idErrato = Integer.parseInt(destinatario9idErrato);} catch (Exception e){}
	}
	public void setDestinatario9idCorretto(String destinatario9idCorretto) {
		try{this.destinatario9idCorretto = Integer.parseInt(destinatario9idCorretto);} catch (Exception e){}
	}
	
	public void setDestinatario9inregioneErrato(String destinatario9inregioneErrato) {
		try{this.destinatario9inregioneErrato = Boolean.parseBoolean(destinatario9inregioneErrato);} catch (Exception e){}
	}
	public void setDestinatario9inregioneCorretto(String destinatario9inregioneCorretto) {
		try{this.destinatario9inregioneCorretto = Boolean.parseBoolean(destinatario9inregioneCorretto);} catch (Exception e){}
	}
	
	public void setDestinatario10idErrato(String destinatario10idErrato) {
		try{this.destinatario10idErrato = Integer.parseInt(destinatario10idErrato);} catch (Exception e){}
	}
	public void setDestinatario10idCorretto(String destinatario10idCorretto) {
		try{this.destinatario10idCorretto = Integer.parseInt(destinatario10idCorretto);} catch (Exception e){}
	}
	
	public void setDestinatario10inregioneErrato(String destinatario10inregioneErrato) {
		try{this.destinatario10inregioneErrato = Boolean.parseBoolean(destinatario10inregioneErrato);} catch (Exception e){}
	}
	public void setDestinatario10inregioneCorretto(String destinatario10inregioneCorretto) {
		try{this.destinatario10inregioneCorretto = Boolean.parseBoolean(destinatario10inregioneCorretto);} catch (Exception e){}
	}
	
	public void setDestinatario11idErrato(String destinatario11idErrato) {
		try{this.destinatario11idErrato = Integer.parseInt(destinatario11idErrato);} catch (Exception e){}
	}
	public void setDestinatario11idCorretto(String destinatario11idCorretto) {
		try{this.destinatario11idCorretto = Integer.parseInt(destinatario11idCorretto);} catch (Exception e){}
	}
	
	public void setDestinatario11inregioneErrato(String destinatario11inregioneErrato) {
		try{this.destinatario11inregioneErrato = Boolean.parseBoolean(destinatario11inregioneErrato);} catch (Exception e){}
	}
	public void setDestinatario11inregioneCorretto(String destinatario11inregioneCorretto) {
		try{this.destinatario11inregioneCorretto = Boolean.parseBoolean(destinatario11inregioneCorretto);} catch (Exception e){}
	}
	
	public void setDestinatario12idErrato(String destinatario12idErrato) {
		try{this.destinatario12idErrato = Integer.parseInt(destinatario12idErrato);} catch (Exception e){}
	}
	public void setDestinatario12idCorretto(String destinatario12idCorretto) {
		try{this.destinatario12idCorretto = Integer.parseInt(destinatario12idCorretto);} catch (Exception e){}
	}
	
	public void setDestinatario12inregioneErrato(String destinatario12inregioneErrato) {
		try{this.destinatario12inregioneErrato = Boolean.parseBoolean(destinatario12inregioneErrato);} catch (Exception e){}
	}
	public void setDestinatario12inregioneCorretto(String destinatario12inregioneCorretto) {
		try{this.destinatario12inregioneCorretto = Boolean.parseBoolean(destinatario12inregioneCorretto);} catch (Exception e){}
	}
	
	public void setDestinatario13idErrato(String destinatario13idErrato) {
		try{this.destinatario13idErrato = Integer.parseInt(destinatario13idErrato);} catch (Exception e){}
	}
	public void setDestinatario13idCorretto(String destinatario13idCorretto) {
		try{this.destinatario13idCorretto = Integer.parseInt(destinatario13idCorretto);} catch (Exception e){}
	}
	
	public void setDestinatario13inregioneErrato(String destinatario13inregioneErrato) {
		try{this.destinatario13inregioneErrato = Boolean.parseBoolean(destinatario13inregioneErrato);} catch (Exception e){}
	}
	public void setDestinatario13inregioneCorretto(String destinatario13inregioneCorretto) {
		try{this.destinatario13inregioneCorretto = Boolean.parseBoolean(destinatario13inregioneCorretto);} catch (Exception e){}
	}
	
	public void setDestinatario14idErrato(String destinatario14idErrato) {
		try{this.destinatario14idErrato = Integer.parseInt(destinatario14idErrato);} catch (Exception e){}
	}
	public void setDestinatario14idCorretto(String destinatario14idCorretto) {
		try{this.destinatario14idCorretto = Integer.parseInt(destinatario14idCorretto);} catch (Exception e){}
	}
	
	public void setDestinatario14inregioneErrato(String destinatario14inregioneErrato) {
		try{this.destinatario14inregioneErrato = Boolean.parseBoolean(destinatario14inregioneErrato);} catch (Exception e){}
	}
	public void setDestinatario14inregioneCorretto(String destinatario14inregioneCorretto) {
		try{this.destinatario14inregioneCorretto = Boolean.parseBoolean(destinatario14inregioneCorretto);} catch (Exception e){}
	}
	
	public void setDestinatario15idErrato(String destinatario15idErrato) {
		try{this.destinatario15idErrato = Integer.parseInt(destinatario15idErrato);} catch (Exception e){}
	}
	public void setDestinatario15idCorretto(String destinatario15idCorretto) {
		try{this.destinatario15idCorretto = Integer.parseInt(destinatario15idCorretto);} catch (Exception e){}
	}
	
	public void setDestinatario15inregioneErrato(String destinatario15inregioneErrato) {
		try{this.destinatario15inregioneErrato = Boolean.parseBoolean(destinatario15inregioneErrato);} catch (Exception e){}
	}
	public void setDestinatario15inregioneCorretto(String destinatario15inregioneCorretto) {
		try{this.destinatario15inregioneCorretto = Boolean.parseBoolean(destinatario15inregioneCorretto);} catch (Exception e){}
	}
	
	public void setDestinatario16idErrato(String destinatario16idErrato) {
		try{this.destinatario16idErrato = Integer.parseInt(destinatario16idErrato);} catch (Exception e){}
	}
	public void setDestinatario16idCorretto(String destinatario16idCorretto) {
		try{this.destinatario16idCorretto = Integer.parseInt(destinatario16idCorretto);} catch (Exception e){}
	}
	
	public void setDestinatario16inregioneErrato(String destinatario16inregioneErrato) {
		try{this.destinatario16inregioneErrato = Boolean.parseBoolean(destinatario16inregioneErrato);} catch (Exception e){}
	}
	public void setDestinatario16inregioneCorretto(String destinatario16inregioneCorretto) {
		try{this.destinatario16inregioneCorretto = Boolean.parseBoolean(destinatario16inregioneCorretto);} catch (Exception e){}
	}
	
	public void setDestinatario17idErrato(String destinatario17idErrato) {
		try{this.destinatario17idErrato = Integer.parseInt(destinatario17idErrato);} catch (Exception e){}
	}
	public void setDestinatario17idCorretto(String destinatario17idCorretto) {
		try{this.destinatario17idCorretto = Integer.parseInt(destinatario17idCorretto);} catch (Exception e){}
	}
	
	public void setDestinatario17inregioneErrato(String destinatario17inregioneErrato) {
		try{this.destinatario17inregioneErrato = Boolean.parseBoolean(destinatario17inregioneErrato);} catch (Exception e){}
	}
	public void setDestinatario17inregioneCorretto(String destinatario17inregioneCorretto) {
		try{this.destinatario17inregioneCorretto = Boolean.parseBoolean(destinatario17inregioneCorretto);} catch (Exception e){}
	}
	
	public void setDestinatario18idErrato(String destinatario18idErrato) {
		try{this.destinatario18idErrato = Integer.parseInt(destinatario18idErrato);} catch (Exception e){}
	}
	public void setDestinatario18idCorretto(String destinatario18idCorretto) {
		try{this.destinatario18idCorretto = Integer.parseInt(destinatario18idCorretto);} catch (Exception e){}
	}
	
	public void setDestinatario18inregioneErrato(String destinatario18inregioneErrato) {
		try{this.destinatario18inregioneErrato = Boolean.parseBoolean(destinatario18inregioneErrato);} catch (Exception e){}
	}
	public void setDestinatario18inregioneCorretto(String destinatario18inregioneCorretto) {
		try{this.destinatario18inregioneCorretto = Boolean.parseBoolean(destinatario18inregioneCorretto);} catch (Exception e){}
	}
	
	public void setDestinatario19idErrato(String destinatario19idErrato) {
		try{this.destinatario19idErrato = Integer.parseInt(destinatario19idErrato);} catch (Exception e){}
	}
	public void setDestinatario19idCorretto(String destinatario19idCorretto) {
		try{this.destinatario19idCorretto = Integer.parseInt(destinatario19idCorretto);} catch (Exception e){}
	}
	
	public void setDestinatario19inregioneErrato(String destinatario19inregioneErrato) {
		try{this.destinatario19inregioneErrato = Boolean.parseBoolean(destinatario19inregioneErrato);} catch (Exception e){}
	}
	public void setDestinatario19inregioneCorretto(String destinatario19inregioneCorretto) {
		try{this.destinatario19inregioneCorretto = Boolean.parseBoolean(destinatario19inregioneCorretto);} catch (Exception e){}
	}
	
	public void setDestinatario20idErrato(String destinatario20idErrato) {
		try{this.destinatario20idErrato = Integer.parseInt(destinatario20idErrato);} catch (Exception e){}
	}
	public void setDestinatario20idCorretto(String destinatario20idCorretto) {
		try{this.destinatario20idCorretto = Integer.parseInt(destinatario20idCorretto);} catch (Exception e){}
	}
	
	public void setDestinatario20inregioneErrato(String destinatario20inregioneErrato) {
		try{this.destinatario20inregioneErrato = Boolean.parseBoolean(destinatario20inregioneErrato);} catch (Exception e){}
	}
	public void setDestinatario20inregioneCorretto(String destinatario20inregioneCorretto) {
		try{this.destinatario20inregioneCorretto = Boolean.parseBoolean(destinatario20inregioneCorretto);} catch (Exception e){}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public Art17ErrataCorrige (){ 
		
	}
	
	public Art17ErrataCorrige (ActionContext context){
		setIdMacello(context.getRequest().getParameter("idMacello"));
		setIdPartita(context.getRequest().getParameter("idPartita"));
		this.sottoscritto = context.getRequest().getParameter("sottoscritto");
		this.motivo = context.getRequest().getParameter("motivo");
		this.riferimentoArt17 = context.getRequest().getParameter("riferimentoArt17");
		setDataMacellazione(context.getRequest().getParameter("dataMacellazione"));
		
		if (context.getRequest().getParameter("cb_numero")!=null && context.getRequest().getParameter("cb_numero").equals("on")){
			setNumeroModificato(true);
			setNumeroErrato(context.getRequest().getParameter("numero_errato"));
			setNumeroCorretto(context.getRequest().getParameter("numero"));
		}
		
		if (context.getRequest().getParameter("cb_mod4")!=null && context.getRequest().getParameter("cb_mod4").equals("on")){
			setMod4Modificato(true);
			setMod4Errato(context.getRequest().getParameter("mod4_errato"));
			setMod4Corretto(context.getRequest().getParameter("mod4"));
		}
		
		if (context.getRequest().getParameter("cb_veterinario_1")!=null && context.getRequest().getParameter("cb_veterinario_1").equals("on")){
			setVeterinario1Modificato(true);
			setVeterinario1Errato(context.getRequest().getParameter("veterinari_cd1_errato"));
			setVeterinario1Corretto(context.getRequest().getParameter("veterinari_cd1_nome"));
		}
		if (context.getRequest().getParameter("cb_veterinario_2")!=null && context.getRequest().getParameter("cb_veterinario_2").equals("on")){
			setVeterinario2Modificato(true);
			setVeterinario2Errato(context.getRequest().getParameter("veterinari_cd2_errato"));
			setVeterinario2Corretto(context.getRequest().getParameter("veterinari_cd2_nome"));
		}
		if (context.getRequest().getParameter("cb_veterinario_3")!=null && context.getRequest().getParameter("cb_veterinario_3").equals("on")){
			setVeterinario3Modificato(true);
			setVeterinario3Errato(context.getRequest().getParameter("veterinari_cd3_errato"));
			setVeterinario3Corretto(context.getRequest().getParameter("veterinari_cd3_nome"));
		}
		
		
		if (context.getRequest().getParameter("cb_destinatari_1")!=null && context.getRequest().getParameter("cb_destinatari_1").equals("on")){
			setDestinatario1Modificato(true);
			setDestinatario1idErrato(context.getRequest().getParameter("destinatario_1_id_errato"));
			setDestinatario1idCorretto(context.getRequest().getParameter("destinatario_1_id"));
			setDestinatario1nomeErrato(context.getRequest().getParameter("destinatario_1_nome_errato"));
			setDestinatario1nomeCorretto(context.getRequest().getParameter("destinatario_1_nome"));
			setDestinatario1inregioneErrato(context.getRequest().getParameter("destinatario_1_in_regione_errato"));
			setDestinatario1inregioneCorretto(context.getRequest().getParameter("destinatario_1_in_regione"));
		}
		
		if (context.getRequest().getParameter("cb_destinatari_2")!=null && context.getRequest().getParameter("cb_destinatari_2").equals("on")){
			setDestinatario2Modificato(true);
			setDestinatario2idErrato(context.getRequest().getParameter("destinatario_2_id_errato"));
			setDestinatario2idCorretto(context.getRequest().getParameter("destinatario_2_id"));
			setDestinatario2nomeErrato(context.getRequest().getParameter("destinatario_2_nome_errato"));
			setDestinatario2nomeCorretto(context.getRequest().getParameter("destinatario_2_nome"));
			setDestinatario2inregioneErrato(context.getRequest().getParameter("destinatario_2_in_regione_errato"));
			setDestinatario2inregioneCorretto(context.getRequest().getParameter("destinatario_2_in_regione"));
		}
		if (context.getRequest().getParameter("cb_destinatari_3")!=null && context.getRequest().getParameter("cb_destinatari_3").equals("on")){
			setDestinatario3Modificato(true);
			setDestinatario3idErrato(context.getRequest().getParameter("destinatario_3_id_errato"));
			setDestinatario3idCorretto(context.getRequest().getParameter("destinatario_3_id"));
			setDestinatario3nomeErrato(context.getRequest().getParameter("destinatario_3_nome_errato"));
			setDestinatario3nomeCorretto(context.getRequest().getParameter("destinatario_3_nome"));
			setDestinatario3inregioneErrato(context.getRequest().getParameter("destinatario_3_in_regione_errato"));
			setDestinatario3inregioneCorretto(context.getRequest().getParameter("destinatario_3_in_regione"));
		}
		if (context.getRequest().getParameter("cb_destinatari_4")!=null && context.getRequest().getParameter("cb_destinatari_4").equals("on")){
			setDestinatario4Modificato(true);
			setDestinatario4idErrato(context.getRequest().getParameter("destinatario_4_id_errato"));
			setDestinatario4idCorretto(context.getRequest().getParameter("destinatario_4_id"));
			setDestinatario4nomeErrato(context.getRequest().getParameter("destinatario_4_nome_errato"));
			setDestinatario4nomeCorretto(context.getRequest().getParameter("destinatario_4_nome"));
			setDestinatario4inregioneErrato(context.getRequest().getParameter("destinatario_4_in_regione_errato"));
			setDestinatario4inregioneCorretto(context.getRequest().getParameter("destinatario_4_in_regione"));
		}
		if (context.getRequest().getParameter("cb_destinatari_5")!=null && context.getRequest().getParameter("cb_destinatari_5").equals("on")){
			setDestinatario5Modificato(true);
			setDestinatario5idErrato(context.getRequest().getParameter("destinatario_5_id_errato"));
			setDestinatario5idCorretto(context.getRequest().getParameter("destinatario_5_id"));
			setDestinatario5nomeErrato(context.getRequest().getParameter("destinatario_5_nome_errato"));
			setDestinatario5nomeCorretto(context.getRequest().getParameter("destinatario_5_nome"));
			setDestinatario5inregioneErrato(context.getRequest().getParameter("destinatario_5_in_regione_errato"));
			setDestinatario5inregioneCorretto(context.getRequest().getParameter("destinatario_5_in_regione"));
		}
		if (context.getRequest().getParameter("cb_destinatari_6")!=null && context.getRequest().getParameter("cb_destinatari_6").equals("on")){
			setDestinatario6Modificato(true);
			setDestinatario6idErrato(context.getRequest().getParameter("destinatario_6_id_errato"));
			setDestinatario6idCorretto(context.getRequest().getParameter("destinatario_6_id"));
			setDestinatario6nomeErrato(context.getRequest().getParameter("destinatario_6_nome_errato"));
			setDestinatario6nomeCorretto(context.getRequest().getParameter("destinatario_6_nome"));
			setDestinatario6inregioneErrato(context.getRequest().getParameter("destinatario_6_in_regione_errato"));
			setDestinatario6inregioneCorretto(context.getRequest().getParameter("destinatario_6_in_regione"));
		}
		if (context.getRequest().getParameter("cb_destinatari_7")!=null && context.getRequest().getParameter("cb_destinatari_7").equals("on")){
			setDestinatario7Modificato(true);
			setDestinatario7idErrato(context.getRequest().getParameter("destinatario_7_id_errato"));
			setDestinatario7idCorretto(context.getRequest().getParameter("destinatario_7_id"));
			setDestinatario7nomeErrato(context.getRequest().getParameter("destinatario_7_nome_errato"));
			setDestinatario7nomeCorretto(context.getRequest().getParameter("destinatario_7_nome"));
			setDestinatario7inregioneErrato(context.getRequest().getParameter("destinatario_7_in_regione_errato"));
			setDestinatario7inregioneCorretto(context.getRequest().getParameter("destinatario_7_in_regione"));
		}
		if (context.getRequest().getParameter("cb_destinatari_8")!=null && context.getRequest().getParameter("cb_destinatari_8").equals("on")){
			setDestinatario8Modificato(true);
			setDestinatario8idErrato(context.getRequest().getParameter("destinatario_8_id_errato"));
			setDestinatario8idCorretto(context.getRequest().getParameter("destinatario_8_id"));
			setDestinatario8nomeErrato(context.getRequest().getParameter("destinatario_8_nome_errato"));
			setDestinatario8nomeCorretto(context.getRequest().getParameter("destinatario_8_nome"));
			setDestinatario8inregioneErrato(context.getRequest().getParameter("destinatario_8_in_regione_errato"));
			setDestinatario8inregioneCorretto(context.getRequest().getParameter("destinatario_8_in_regione"));
		}
		if (context.getRequest().getParameter("cb_destinatari_9")!=null && context.getRequest().getParameter("cb_destinatari_9").equals("on")){
			setDestinatario9Modificato(true);
			setDestinatario9idErrato(context.getRequest().getParameter("destinatario_9_id_errato"));
			setDestinatario9idCorretto(context.getRequest().getParameter("destinatario_9_id"));
			setDestinatario9nomeErrato(context.getRequest().getParameter("destinatario_9_nome_errato"));
			setDestinatario9nomeCorretto(context.getRequest().getParameter("destinatario_9_nome"));
			setDestinatario9inregioneErrato(context.getRequest().getParameter("destinatario_9_in_regione_errato"));
			setDestinatario9inregioneCorretto(context.getRequest().getParameter("destinatario_9_in_regione"));
		}
		if (context.getRequest().getParameter("cb_destinatari_10")!=null && context.getRequest().getParameter("cb_destinatari_10").equals("on")){
			setDestinatario10Modificato(true);
			setDestinatario10idErrato(context.getRequest().getParameter("destinatario_10_id_errato"));
			setDestinatario10idCorretto(context.getRequest().getParameter("destinatario_10_id"));
			setDestinatario10nomeErrato(context.getRequest().getParameter("destinatario_10_nome_errato"));
			setDestinatario10nomeCorretto(context.getRequest().getParameter("destinatario_10_nome"));
			setDestinatario10inregioneErrato(context.getRequest().getParameter("destinatario_10_in_regione_errato"));
			setDestinatario10inregioneCorretto(context.getRequest().getParameter("destinatario_10_in_regione"));
		}
		if (context.getRequest().getParameter("cb_destinatari_11")!=null && context.getRequest().getParameter("cb_destinatari_11").equals("on")){
			setDestinatario11Modificato(true);
			setDestinatario11idErrato(context.getRequest().getParameter("destinatario_11_id_errato"));
			setDestinatario11idCorretto(context.getRequest().getParameter("destinatario_11_id"));
			setDestinatario11nomeErrato(context.getRequest().getParameter("destinatario_11_nome_errato"));
			setDestinatario11nomeCorretto(context.getRequest().getParameter("destinatario_11_nome"));
			setDestinatario11inregioneErrato(context.getRequest().getParameter("destinatario_11_in_regione_errato"));
			setDestinatario11inregioneCorretto(context.getRequest().getParameter("destinatario_11_in_regione"));
		}
		if (context.getRequest().getParameter("cb_destinatari_12")!=null && context.getRequest().getParameter("cb_destinatari_12").equals("on")){
			setDestinatario12Modificato(true);
			setDestinatario12idErrato(context.getRequest().getParameter("destinatario_12_id_errato"));
			setDestinatario12idCorretto(context.getRequest().getParameter("destinatario_12_id"));
			setDestinatario12nomeErrato(context.getRequest().getParameter("destinatario_12_nome_errato"));
			setDestinatario12nomeCorretto(context.getRequest().getParameter("destinatario_12_nome"));
			setDestinatario12inregioneErrato(context.getRequest().getParameter("destinatario_12_in_regione_errato"));
			setDestinatario12inregioneCorretto(context.getRequest().getParameter("destinatario_12_in_regione"));
		}
		if (context.getRequest().getParameter("cb_destinatari_13")!=null && context.getRequest().getParameter("cb_destinatari_13").equals("on")){
			setDestinatario13Modificato(true);
			setDestinatario13idErrato(context.getRequest().getParameter("destinatario_13_id_errato"));
			setDestinatario13idCorretto(context.getRequest().getParameter("destinatario_13_id"));
			setDestinatario13nomeErrato(context.getRequest().getParameter("destinatario_13_nome_errato"));
			setDestinatario13nomeCorretto(context.getRequest().getParameter("destinatario_13_nome"));
			setDestinatario13inregioneErrato(context.getRequest().getParameter("destinatario_13_in_regione_errato"));
			setDestinatario13inregioneCorretto(context.getRequest().getParameter("destinatario_13_in_regione"));
		}
		if (context.getRequest().getParameter("cb_destinatari_14")!=null && context.getRequest().getParameter("cb_destinatari_14").equals("on")){
			setDestinatario14Modificato(true);
			setDestinatario14idErrato(context.getRequest().getParameter("destinatario_14_id_errato"));
			setDestinatario14idCorretto(context.getRequest().getParameter("destinatario_14_id"));
			setDestinatario14nomeErrato(context.getRequest().getParameter("destinatario_14_nome_errato"));
			setDestinatario14nomeCorretto(context.getRequest().getParameter("destinatario_14_nome"));
			setDestinatario14inregioneErrato(context.getRequest().getParameter("destinatario_14_in_regione_errato"));
			setDestinatario14inregioneCorretto(context.getRequest().getParameter("destinatario_14_in_regione"));
		}
		if (context.getRequest().getParameter("cb_destinatari_15")!=null && context.getRequest().getParameter("cb_destinatari_15").equals("on")){
			setDestinatario15Modificato(true);
			setDestinatario15idErrato(context.getRequest().getParameter("destinatario_15_id_errato"));
			setDestinatario15idCorretto(context.getRequest().getParameter("destinatario_15_id"));
			setDestinatario15nomeErrato(context.getRequest().getParameter("destinatario_15_nome_errato"));
			setDestinatario15nomeCorretto(context.getRequest().getParameter("destinatario_15_nome"));
			setDestinatario15inregioneErrato(context.getRequest().getParameter("destinatario_15_in_regione_errato"));
			setDestinatario15inregioneCorretto(context.getRequest().getParameter("destinatario_15_in_regione"));
		}
		if (context.getRequest().getParameter("cb_destinatari_16")!=null && context.getRequest().getParameter("cb_destinatari_16").equals("on")){
			setDestinatario16Modificato(true);
			setDestinatario16idErrato(context.getRequest().getParameter("destinatario_16_id_errato"));
			setDestinatario16idCorretto(context.getRequest().getParameter("destinatario_16_id"));
			setDestinatario16nomeErrato(context.getRequest().getParameter("destinatario_16_nome_errato"));
			setDestinatario16nomeCorretto(context.getRequest().getParameter("destinatario_16_nome"));
			setDestinatario16inregioneErrato(context.getRequest().getParameter("destinatario_16_in_regione_errato"));
			setDestinatario16inregioneCorretto(context.getRequest().getParameter("destinatario_16_in_regione"));
		}
		if (context.getRequest().getParameter("cb_destinatari_17")!=null && context.getRequest().getParameter("cb_destinatari_17").equals("on")){
			setDestinatario17Modificato(true);
			setDestinatario17idErrato(context.getRequest().getParameter("destinatario_17_id_errato"));
			setDestinatario17idCorretto(context.getRequest().getParameter("destinatario_17_id"));
			setDestinatario17nomeErrato(context.getRequest().getParameter("destinatario_17_nome_errato"));
			setDestinatario17nomeCorretto(context.getRequest().getParameter("destinatario_17_nome"));
			setDestinatario17inregioneErrato(context.getRequest().getParameter("destinatario_17_in_regione_errato"));
			setDestinatario17inregioneCorretto(context.getRequest().getParameter("destinatario_17_in_regione"));
		}
		if (context.getRequest().getParameter("cb_destinatari_18")!=null && context.getRequest().getParameter("cb_destinatari_18").equals("on")){
			setDestinatario18Modificato(true);
			setDestinatario18idErrato(context.getRequest().getParameter("destinatario_18_id_errato"));
			setDestinatario18idCorretto(context.getRequest().getParameter("destinatario_18_id"));
			setDestinatario18nomeErrato(context.getRequest().getParameter("destinatario_18_nome_errato"));
			setDestinatario18nomeCorretto(context.getRequest().getParameter("destinatario_18_nome"));
			setDestinatario18inregioneErrato(context.getRequest().getParameter("destinatario_18_in_regione_errato"));
			setDestinatario18inregioneCorretto(context.getRequest().getParameter("destinatario_18_in_regione"));
		}
		if (context.getRequest().getParameter("cb_destinatari_19")!=null && context.getRequest().getParameter("cb_destinatari_19").equals("on")){
			setDestinatario19Modificato(true);
			setDestinatario19idErrato(context.getRequest().getParameter("destinatario_19_id_errato"));
			setDestinatario19idCorretto(context.getRequest().getParameter("destinatario_19_id"));
			setDestinatario19nomeErrato(context.getRequest().getParameter("destinatario_19_nome_errato"));
			setDestinatario19nomeCorretto(context.getRequest().getParameter("destinatario_19_nome"));
			setDestinatario19inregioneErrato(context.getRequest().getParameter("destinatario_19_in_regione_errato"));
			setDestinatario19inregioneCorretto(context.getRequest().getParameter("destinatario_19_in_regione"));
		}
		if (context.getRequest().getParameter("cb_destinatari_20")!=null && context.getRequest().getParameter("cb_destinatari_20").equals("on")){
			setDestinatario20Modificato(true);
			setDestinatario20idErrato(context.getRequest().getParameter("destinatario_20_id_errato"));
			setDestinatario20idCorretto(context.getRequest().getParameter("destinatario_20_id"));
			setDestinatario20nomeErrato(context.getRequest().getParameter("destinatario_20_nome_errato"));
			setDestinatario20nomeCorretto(context.getRequest().getParameter("destinatario_20_nome"));
			setDestinatario20inregioneErrato(context.getRequest().getParameter("destinatario_20_in_regione_errato"));
			setDestinatario20inregioneCorretto(context.getRequest().getParameter("destinatario_20_in_regione"));
		}
		
		if (context.getRequest().getParameter("cb_altro")!=null && context.getRequest().getParameter("cb_altro").equals("on")){
			setAltroModificato(true);
			setAltroErrato(context.getRequest().getParameter("altro_errato"));
			setAltroCorretto(context.getRequest().getParameter("altro_corretto"));
		}
		
	}
	
	public int insert( Connection db ) throws SQLException{
		
		//int id = DatabaseUtils.getNextSeq(db, "macelli_art17_errata_corrige_id_seq");
		StringBuffer sql = new StringBuffer();
		
		boolean doCommit = false;
		
		try {
			if (doCommit = db.getAutoCommit()) {
				db.setAutoCommit(false);
			}
			
		sql.append("INSERT INTO macelli_art17_errata_corrige_ovicaprini(id_macello, id_partita, id_utente, ip_utente, nome_utente, data_macellazione, sottoscritto, motivo, riferimento_art_17, entered ");
		
		if (numeroModificato)
			sql.append(", numero_modificato, numero_errato, numero_corretto ");
		if (mod4Modificato)
			sql.append(", mod4_modificato, mod4_errato, mod4_corretto ");
		if (altroModificato)
			sql.append(", altro_modificato, altro_errato, altro_corretto ");
		if (veterinario1Modificato)
			sql.append(", veterinario_1_modificato, veterinario_1_errato, veterinario_1_corretto ");
		if (veterinario2Modificato)
			sql.append(", veterinario_2_modificato, veterinario_2_errato, veterinario_2_corretto ");
		if (veterinario3Modificato)
			sql.append(", veterinario_3_modificato, veterinario_3_errato, veterinario_3_corretto ");
		if (destinatario1Modificato)
			sql.append(", destinatario_1_modificato, destinatario_1_nome_errato, destinatario_1_nome_corretto, destinatario_1_id_errato, destinatario_1_id_corretto, destinatario_1_in_regione_errato, destinatario_1_in_regione_corretto ");
		if (destinatario2Modificato)
			sql.append(", destinatario_2_modificato, destinatario_2_nome_errato, destinatario_2_nome_corretto, destinatario_2_id_errato, destinatario_2_id_corretto, destinatario_2_in_regione_errato, destinatario_2_in_regione_corretto ");
		if (destinatario3Modificato)
			sql.append(", destinatario_3_modificato, destinatario_3_nome_errato, destinatario_3_nome_corretto, destinatario_3_id_errato, destinatario_3_id_corretto, destinatario_3_in_regione_errato, destinatario_3_in_regione_corretto ");
		if (destinatario4Modificato)
			sql.append(", destinatario_4_modificato, destinatario_4_nome_errato, destinatario_4_nome_corretto, destinatario_4_id_errato, destinatario_4_id_corretto, destinatario_4_in_regione_errato, destinatario_4_in_regione_corretto ");
		if (destinatario5Modificato)
			sql.append(", destinatario_5_modificato, destinatario_5_nome_errato, destinatario_5_nome_corretto, destinatario_5_id_errato, destinatario_5_id_corretto, destinatario_5_in_regione_errato, destinatario_5_in_regione_corretto ");
		if (destinatario6Modificato)
			sql.append(", destinatario_6_modificato, destinatario_6_nome_errato, destinatario_6_nome_corretto, destinatario_6_id_errato, destinatario_6_id_corretto, destinatario_6_in_regione_errato, destinatario_6_in_regione_corretto ");
		if (destinatario7Modificato)
			sql.append(", destinatario_7_modificato, destinatario_7_nome_errato, destinatario_7_nome_corretto, destinatario_7_id_errato, destinatario_7_id_corretto, destinatario_7_in_regione_errato, destinatario_7_in_regione_corretto ");
		if (destinatario8Modificato)
			sql.append(", destinatario_8_modificato, destinatario_8_nome_errato, destinatario_8_nome_corretto, destinatario_8_id_errato, destinatario_8_id_corretto, destinatario_8_in_regione_errato, destinatario_8_in_regione_corretto ");
		if (destinatario9Modificato)
			sql.append(", destinatario_9_modificato, destinatario_9_nome_errato, destinatario_9_nome_corretto, destinatario_9_id_errato, destinatario_9_id_corretto, destinatario_9_in_regione_errato, destinatario_9_in_regione_corretto ");
		if (destinatario10Modificato)
			sql.append(", destinatario_10_modificato, destinatario_10_nome_errato, destinatario_10_nome_corretto, destinatario_10_id_errato, destinatario_10_id_corretto, destinatario_10_in_regione_errato, destinatario_10_in_regione_corretto ");
		if (destinatario11Modificato)
			sql.append(", destinatario_11_modificato, destinatario_11_nome_errato, destinatario_11_nome_corretto, destinatario_11_id_errato, destinatario_11_id_corretto, destinatario_11_in_regione_errato, destinatario_11_in_regione_corretto ");
		if (destinatario12Modificato)
			sql.append(", destinatario_12_modificato, destinatario_12_nome_errato, destinatario_12_nome_corretto, destinatario_12_id_errato, destinatario_12_id_corretto, destinatario_12_in_regione_errato, destinatario_12_in_regione_corretto ");
		if (destinatario13Modificato)
			sql.append(", destinatario_13_modificato, destinatario_13_nome_errato, destinatario_13_nome_corretto, destinatario_13_id_errato, destinatario_13_id_corretto, destinatario_13_in_regione_errato, destinatario_13_in_regione_corretto ");
		if (destinatario14Modificato)
			sql.append(", destinatario_14_modificato, destinatario_14_nome_errato, destinatario_14_nome_corretto, destinatario_14_id_errato, destinatario_14_id_corretto, destinatario_14_in_regione_errato, destinatario_14_in_regione_corretto ");
		if (destinatario15Modificato)
			sql.append(", destinatario_15_modificato, destinatario_15_nome_errato, destinatario_15_nome_corretto, destinatario_15_id_errato, destinatario_15_id_corretto, destinatario_15_in_regione_errato, destinatario_15_in_regione_corretto ");
		if (destinatario16Modificato)
			sql.append(", destinatario_16_modificato, destinatario_16_nome_errato, destinatario_16_nome_corretto, destinatario_16_id_errato, destinatario_16_id_corretto, destinatario_16_in_regione_errato, destinatario_16_in_regione_corretto ");
		if (destinatario17Modificato)
			sql.append(", destinatario_17_modificato, destinatario_17_nome_errato, destinatario_17_nome_corretto, destinatario_17_id_errato, destinatario_17_id_corretto, destinatario_17_in_regione_errato, destinatario_17_in_regione_corretto ");
		if (destinatario18Modificato)
			sql.append(", destinatario_18_modificato, destinatario_18_nome_errato, destinatario_18_nome_corretto, destinatario_18_id_errato, destinatario_18_id_corretto, destinatario_18_in_regione_errato, destinatario_18_in_regione_corretto ");
		if (destinatario19Modificato)
			sql.append(", destinatario_19_modificato, destinatario_19_nome_errato, destinatario_19_nome_corretto, destinatario_19_id_errato, destinatario_19_id_corretto, destinatario_19_in_regione_errato, destinatario_19_in_regione_corretto ");
		if (destinatario20Modificato)
			sql.append(", destinatario_20_modificato, destinatario_20_nome_errato, destinatario_20_nome_corretto, destinatario_20_id_errato, destinatario_20_id_corretto, destinatario_20_in_regione_errato, destinatario_20_in_regione_corretto ");
		
		
		sql.append(") values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, now()");
		
		if (numeroModificato)
			sql.append(",true, ?, ? ");
		if (mod4Modificato)
			sql.append(",true,  ?,  ? ");
		if (altroModificato)
			sql.append(",true,  ?,  ? ");
		if (veterinario1Modificato)
			sql.append(", true, ?, ? ");
		if (veterinario2Modificato)
			sql.append(", true, ?, ? ");
		if (veterinario3Modificato)
			sql.append(", true, ?, ? ");
		if (destinatario1Modificato)
			sql.append(", true, ?, ?, ?, ?, ?, ? ");
		if (destinatario2Modificato)
			sql.append(", true, ?, ?, ?, ?, ?, ? ");
		if (destinatario3Modificato)
			sql.append(", true, ?, ?, ?, ?, ?, ? ");
		if (destinatario4Modificato)
			sql.append(", true, ?, ?, ?, ?, ?, ? ");
		if (destinatario5Modificato)
			sql.append(", true, ?, ?, ?, ?, ?, ? ");
		if (destinatario6Modificato)
			sql.append(", true, ?, ?, ?, ?, ?, ? ");
		if (destinatario7Modificato)
			sql.append(", true, ?, ?, ?, ?, ?, ? ");
		if (destinatario8Modificato)
			sql.append(", true, ?, ?, ?, ?, ?, ? ");
		if (destinatario9Modificato)
			sql.append(", true, ?, ?, ?, ?, ?, ? ");
		if (destinatario10Modificato)
			sql.append(", true, ?, ?, ?, ?, ?, ? ");
		if (destinatario11Modificato)
			sql.append(", true, ?, ?, ?, ?, ?, ? ");
		if (destinatario12Modificato)
			sql.append(", true, ?, ?, ?, ?, ?, ? ");
		if (destinatario13Modificato)
			sql.append(", true, ?, ?, ?, ?, ?, ? ");
		if (destinatario14Modificato)
			sql.append(", true, ?, ?, ?, ?, ?, ? ");
		if (destinatario15Modificato)
			sql.append(", true, ?, ?, ?, ?, ?, ? ");
		if (destinatario16Modificato)
			sql.append(", true, ?, ?, ?, ?, ?, ? ");
		if (destinatario17Modificato)
			sql.append(", true, ?, ?, ?, ?, ?, ? ");
		if (destinatario18Modificato)
			sql.append(", true, ?, ?, ?, ?, ?, ? ");
		if (destinatario19Modificato)
			sql.append(", true, ?, ?, ?, ?, ?, ? ");
		if (destinatario20Modificato)
			sql.append(", true, ?, ?, ?, ?, ?, ? ");
		

		sql.append(")");
		
		int i = 0;
		PreparedStatement pst = db.prepareStatement(sql.toString());

		//pst.setInt(++i, id);
		pst.setInt(++i, idMacello);
		pst.setInt(++i, idPartita);
		pst.setInt(++i, idUtente);
		pst.setString(++i, ipUtente);
		pst.setString(++i, nomeUtente);
		pst.setTimestamp(++i, dataMacellazione);
		pst.setString(++i, sottoscritto);
		pst.setString(++i, motivo);
		pst.setString(++i, riferimentoArt17);

		if (numeroModificato)
			{
			pst.setString(++i, numeroErrato);
			pst.setString(++i, numeroCorretto);
			}
		
		if (mod4Modificato)
		{
			pst.setString(++i, mod4Errato);
			pst.setString(++i, mod4Corretto);
			}
		if (altroModificato)
		{
			pst.setString(++i, altroErrato);
			pst.setString(++i, altroCorretto);
			}
		
		if (veterinario1Modificato){
			pst.setString(++i, veterinario1Errato);
			pst.setString(++i, veterinario1Corretto);
		}
		if (veterinario2Modificato){
			pst.setString(++i, veterinario2Errato);
			pst.setString(++i, veterinario2Corretto);
		}
		if (veterinario3Modificato){
			pst.setString(++i, veterinario3Errato);
			pst.setString(++i, veterinario3Corretto);
		}
		
		if (destinatario1Modificato){
			pst.setString(++i, destinatario1nomeErrato);
			pst.setString(++i, destinatario1nomeCorretto);
			pst.setInt(++i, destinatario1idErrato);
			pst.setInt(++i, destinatario1idCorretto);
			pst.setBoolean(++i, destinatario1inregioneErrato);
			pst.setBoolean(++i, destinatario1inregioneCorretto);
		}
			
		if (destinatario2Modificato){
			pst.setString(++i, destinatario2nomeErrato);
			pst.setString(++i, destinatario2nomeCorretto);
			pst.setInt(++i, destinatario2idErrato);
			pst.setInt(++i, destinatario2idCorretto);
			pst.setBoolean(++i, destinatario2inregioneErrato);
			pst.setBoolean(++i, destinatario2inregioneCorretto);
		}
		if (destinatario3Modificato){
			pst.setString(++i, destinatario3nomeErrato);
			pst.setString(++i, destinatario3nomeCorretto);
			pst.setInt(++i, destinatario3idErrato);
			pst.setInt(++i, destinatario3idCorretto);
			pst.setBoolean(++i, destinatario3inregioneErrato);
			pst.setBoolean(++i, destinatario3inregioneCorretto);
		}
		if (destinatario4Modificato){
			pst.setString(++i, destinatario4nomeErrato);
			pst.setString(++i, destinatario4nomeCorretto);
			pst.setInt(++i, destinatario4idErrato);
			pst.setInt(++i, destinatario4idCorretto);
			pst.setBoolean(++i, destinatario4inregioneErrato);
			pst.setBoolean(++i, destinatario4inregioneCorretto);
		}
		if (destinatario5Modificato){
			pst.setString(++i, destinatario5nomeErrato);
			pst.setString(++i, destinatario5nomeCorretto);
			pst.setInt(++i, destinatario5idErrato);
			pst.setInt(++i, destinatario5idCorretto);
			pst.setBoolean(++i, destinatario5inregioneErrato);
			pst.setBoolean(++i, destinatario5inregioneCorretto);
		}
		if (destinatario6Modificato){
			pst.setString(++i, destinatario6nomeErrato);
			pst.setString(++i, destinatario6nomeCorretto);
			pst.setInt(++i, destinatario6idErrato);
			pst.setInt(++i, destinatario6idCorretto);
			pst.setBoolean(++i, destinatario6inregioneErrato);
			pst.setBoolean(++i, destinatario6inregioneCorretto);
		}
		if (destinatario7Modificato){
			pst.setString(++i, destinatario7nomeErrato);
			pst.setString(++i, destinatario7nomeCorretto);
			pst.setInt(++i, destinatario7idErrato);
			pst.setInt(++i, destinatario7idCorretto);
			pst.setBoolean(++i, destinatario7inregioneErrato);
			pst.setBoolean(++i, destinatario7inregioneCorretto);
		}
		if (destinatario8Modificato){
			pst.setString(++i, destinatario8nomeErrato);
			pst.setString(++i, destinatario8nomeCorretto);
			pst.setInt(++i, destinatario8idErrato);
			pst.setInt(++i, destinatario8idCorretto);
			pst.setBoolean(++i, destinatario8inregioneErrato);
			pst.setBoolean(++i, destinatario8inregioneCorretto);
		}
		if (destinatario9Modificato){
			pst.setString(++i, destinatario9nomeErrato);
			pst.setString(++i, destinatario9nomeCorretto);
			pst.setInt(++i, destinatario9idErrato);
			pst.setInt(++i, destinatario9idCorretto);
			pst.setBoolean(++i, destinatario9inregioneErrato);
			pst.setBoolean(++i, destinatario9inregioneCorretto);
		}
		if (destinatario10Modificato){
			pst.setString(++i, destinatario10nomeErrato);
			pst.setString(++i, destinatario10nomeCorretto);
			pst.setInt(++i, destinatario10idErrato);
			pst.setInt(++i, destinatario10idCorretto);
			pst.setBoolean(++i, destinatario10inregioneErrato);
			pst.setBoolean(++i, destinatario10inregioneCorretto);
		}

		if (destinatario11Modificato){
			pst.setString(++i, destinatario11nomeErrato);
			pst.setString(++i, destinatario11nomeCorretto);
			pst.setInt(++i, destinatario11idErrato);
			pst.setInt(++i, destinatario11idCorretto);
			pst.setBoolean(++i, destinatario11inregioneErrato);
			pst.setBoolean(++i, destinatario11inregioneCorretto);
		}
			
		if (destinatario12Modificato){
			pst.setString(++i, destinatario12nomeErrato);
			pst.setString(++i, destinatario12nomeCorretto);
			pst.setInt(++i, destinatario12idErrato);
			pst.setInt(++i, destinatario12idCorretto);
			pst.setBoolean(++i, destinatario12inregioneErrato);
			pst.setBoolean(++i, destinatario12inregioneCorretto);
		}
		if (destinatario13Modificato){
			pst.setString(++i, destinatario13nomeErrato);
			pst.setString(++i, destinatario13nomeCorretto);
			pst.setInt(++i, destinatario13idErrato);
			pst.setInt(++i, destinatario13idCorretto);
			pst.setBoolean(++i, destinatario13inregioneErrato);
			pst.setBoolean(++i, destinatario13inregioneCorretto);
		}
		if (destinatario14Modificato){
			pst.setString(++i, destinatario14nomeErrato);
			pst.setString(++i, destinatario14nomeCorretto);
			pst.setInt(++i, destinatario14idErrato);
			pst.setInt(++i, destinatario14idCorretto);
			pst.setBoolean(++i, destinatario14inregioneErrato);
			pst.setBoolean(++i, destinatario14inregioneCorretto);
		}
		if (destinatario15Modificato){
			pst.setString(++i, destinatario15nomeErrato);
			pst.setString(++i, destinatario15nomeCorretto);
			pst.setInt(++i, destinatario15idErrato);
			pst.setInt(++i, destinatario15idCorretto);
			pst.setBoolean(++i, destinatario15inregioneErrato);
			pst.setBoolean(++i, destinatario15inregioneCorretto);
		}
		if (destinatario16Modificato){
			pst.setString(++i, destinatario16nomeErrato);
			pst.setString(++i, destinatario16nomeCorretto);
			pst.setInt(++i, destinatario16idErrato);
			pst.setInt(++i, destinatario16idCorretto);
			pst.setBoolean(++i, destinatario16inregioneErrato);
			pst.setBoolean(++i, destinatario16inregioneCorretto);
		}
		if (destinatario17Modificato){
			pst.setString(++i, destinatario17nomeErrato);
			pst.setString(++i, destinatario17nomeCorretto);
			pst.setInt(++i, destinatario17idErrato);
			pst.setInt(++i, destinatario17idCorretto);
			pst.setBoolean(++i, destinatario17inregioneErrato);
			pst.setBoolean(++i, destinatario17inregioneCorretto);
		}
		if (destinatario18Modificato){
			pst.setString(++i, destinatario18nomeErrato);
			pst.setString(++i, destinatario18nomeCorretto);
			pst.setInt(++i, destinatario18idErrato);
			pst.setInt(++i, destinatario18idCorretto);
			pst.setBoolean(++i, destinatario18inregioneErrato);
			pst.setBoolean(++i, destinatario18inregioneCorretto);
		}
		if (destinatario19Modificato){
			pst.setString(++i, destinatario19nomeErrato);
			pst.setString(++i, destinatario19nomeCorretto);
			pst.setInt(++i, destinatario19idErrato);
			pst.setInt(++i, destinatario19idCorretto);
			pst.setBoolean(++i, destinatario19inregioneErrato);
			pst.setBoolean(++i, destinatario19inregioneCorretto);
		}
		if (destinatario20Modificato){
			pst.setString(++i, destinatario20nomeErrato);
			pst.setString(++i, destinatario20nomeCorretto);
			pst.setInt(++i, destinatario20idErrato);
			pst.setInt(++i, destinatario20idCorretto);
			pst.setBoolean(++i, destinatario20inregioneErrato);
			pst.setBoolean(++i, destinatario20inregioneCorretto);
		}	
		
		
		pst.execute();
		pst.close();
		
		this.id = DatabaseUtils.getCurrVal(db, "macelli_art17_errata_corrige_ovicaprini_id_seq", id);
		if (doCommit) {
			db.commit();
		}
		}catch (SQLException e) {
			if (doCommit) {
				db.rollback();
			}throw new SQLException(e.getMessage());
		} 
		return 1;
	}
	
	private void buildRecord(ResultSet rs) throws SQLException {

		  this.id = rs.getInt("id");
		  this.idMacello = rs.getInt("id_macello");
		  this.idPartita = rs.getInt("id_partita");
		  this.idUtente = rs.getInt("id_utente");
		  this.ipUtente = rs.getString("ip_utente");
		  this.dataMacellazione = rs.getTimestamp("data_macellazione");
		  this.sottoscritto = rs.getString("sottoscritto");
		  this.motivo = rs.getString("motivo");
		  this.riferimentoArt17 = rs.getString("riferimento_art_17");
		  this.numeroErrato = rs.getString("numero_errato");
		  this.numeroCorretto = rs.getString("numero_corretto");
		  this.mod4Errato = rs.getString("mod4_errato");
		  this.mod4Corretto = rs.getString("mod4_corretto");
		  this.altroErrato = rs.getString("altro_errato");
		  this.altroCorretto = rs.getString("altro_corretto");
		  this.numeroModificato= rs.getBoolean("numero_modificato");
		  this.altroModificato = rs.getBoolean("altro_modificato");
		  this.mod4Modificato = rs.getBoolean("mod4_modificato");
		  this.nomeUtente = rs.getString("nome_utente");
		  this.entered = rs.getTimestamp("entered");
		  
		  this.veterinario1Modificato = rs.getBoolean("veterinario_1_modificato");
		  this.veterinario2Modificato = rs.getBoolean("veterinario_2_modificato");
		  this.veterinario3Modificato = rs.getBoolean("veterinario_3_modificato");
		  this.veterinario1Errato = rs.getString("veterinario_1_errato");
		  this.veterinario2Errato = rs.getString("veterinario_2_errato");
		  this.veterinario3Errato = rs.getString("veterinario_3_errato");
		  this.veterinario1Corretto = rs.getString("veterinario_1_corretto");
		  this.veterinario2Corretto = rs.getString("veterinario_2_corretto");
		  this.veterinario3Corretto = rs.getString("veterinario_3_corretto");
				  
		  this.destinatario1Modificato = rs.getBoolean("destinatario_1_modificato");
		  this.destinatario1idErrato = rs.getInt("destinatario_1_id_errato");
		  this.destinatario1idCorretto = rs.getInt("destinatario_1_id_corretto");
		  this.destinatario1nomeErrato = rs.getString("destinatario_1_nome_errato");
		  this.destinatario1nomeCorretto = rs.getString("destinatario_1_nome_corretto");
		  this.destinatario1inregioneErrato = rs.getBoolean("destinatario_1_in_regione_errato");
		  this.destinatario1inregioneCorretto = rs.getBoolean("destinatario_1_in_regione_corretto");

		  this.destinatario2Modificato = rs.getBoolean("destinatario_2_modificato");
		  this.destinatario2idErrato = rs.getInt("destinatario_2_id_errato");
		  this.destinatario2idCorretto = rs.getInt("destinatario_2_id_corretto");
		  this.destinatario2nomeErrato = rs.getString("destinatario_2_nome_errato");
		  this.destinatario2nomeCorretto = rs.getString("destinatario_2_nome_corretto");
		  this.destinatario2inregioneErrato = rs.getBoolean("destinatario_2_in_regione_errato");
		  this.destinatario2inregioneCorretto = rs.getBoolean("destinatario_2_in_regione_corretto");

		  this.destinatario3Modificato = rs.getBoolean("destinatario_3_modificato");
		  this.destinatario3idErrato = rs.getInt("destinatario_3_id_errato");
		  this.destinatario3idCorretto = rs.getInt("destinatario_3_id_corretto");
		  this.destinatario3nomeErrato = rs.getString("destinatario_3_nome_errato");
		  this.destinatario3nomeCorretto = rs.getString("destinatario_3_nome_corretto");
		  this.destinatario3inregioneErrato = rs.getBoolean("destinatario_3_in_regione_errato");
		  this.destinatario3inregioneCorretto = rs.getBoolean("destinatario_3_in_regione_corretto");

		  this.destinatario4Modificato = rs.getBoolean("destinatario_4_modificato");
		  this.destinatario4idErrato = rs.getInt("destinatario_4_id_errato");
		  this.destinatario4idCorretto = rs.getInt("destinatario_4_id_corretto");
		  this.destinatario4nomeErrato = rs.getString("destinatario_4_nome_errato");
		  this.destinatario4nomeCorretto = rs.getString("destinatario_4_nome_corretto");
		  this.destinatario4inregioneErrato = rs.getBoolean("destinatario_4_in_regione_errato");
		  this.destinatario4inregioneCorretto = rs.getBoolean("destinatario_4_in_regione_corretto");

		  this.destinatario5Modificato = rs.getBoolean("destinatario_5_modificato");
		  this.destinatario5idErrato = rs.getInt("destinatario_5_id_errato");
		  this.destinatario5idCorretto = rs.getInt("destinatario_5_id_corretto");
		  this.destinatario5nomeErrato = rs.getString("destinatario_5_nome_errato");
		  this.destinatario5nomeCorretto = rs.getString("destinatario_5_nome_corretto");
		  this.destinatario5inregioneErrato = rs.getBoolean("destinatario_5_in_regione_errato");
		  this.destinatario5inregioneCorretto = rs.getBoolean("destinatario_5_in_regione_corretto");

		  this.destinatario6Modificato = rs.getBoolean("destinatario_6_modificato");
		  this.destinatario6idErrato = rs.getInt("destinatario_6_id_errato");
		  this.destinatario6idCorretto = rs.getInt("destinatario_6_id_corretto");
		  this.destinatario6nomeErrato = rs.getString("destinatario_6_nome_errato");
		  this.destinatario6nomeCorretto = rs.getString("destinatario_6_nome_corretto");
		  this.destinatario6inregioneErrato = rs.getBoolean("destinatario_6_in_regione_errato");
		  this.destinatario6inregioneCorretto = rs.getBoolean("destinatario_6_in_regione_corretto");

		  this.destinatario7Modificato = rs.getBoolean("destinatario_7_modificato");
		  this.destinatario7idErrato = rs.getInt("destinatario_7_id_errato");
		  this.destinatario7idCorretto = rs.getInt("destinatario_7_id_corretto");
		  this.destinatario7nomeErrato = rs.getString("destinatario_7_nome_errato");
		  this.destinatario7nomeCorretto = rs.getString("destinatario_7_nome_corretto");
		  this.destinatario7inregioneErrato = rs.getBoolean("destinatario_7_in_regione_errato");
		  this.destinatario7inregioneCorretto = rs.getBoolean("destinatario_7_in_regione_corretto");

		  this.destinatario8Modificato = rs.getBoolean("destinatario_8_modificato");
		  this.destinatario8idErrato = rs.getInt("destinatario_8_id_errato");
		  this.destinatario8idCorretto = rs.getInt("destinatario_8_id_corretto");
		  this.destinatario8nomeErrato = rs.getString("destinatario_8_nome_errato");
		  this.destinatario8nomeCorretto = rs.getString("destinatario_8_nome_corretto");
		  this.destinatario8inregioneErrato = rs.getBoolean("destinatario_8_in_regione_errato");
		  this.destinatario8inregioneCorretto = rs.getBoolean("destinatario_8_in_regione_corretto");

		  this.destinatario9Modificato = rs.getBoolean("destinatario_9_modificato");
		  this.destinatario9idErrato = rs.getInt("destinatario_9_id_errato");
		  this.destinatario9idCorretto = rs.getInt("destinatario_9_id_corretto");
		  this.destinatario9nomeErrato = rs.getString("destinatario_9_nome_errato");
		  this.destinatario9nomeCorretto = rs.getString("destinatario_9_nome_corretto");
		  this.destinatario9inregioneErrato = rs.getBoolean("destinatario_9_in_regione_errato");
		  this.destinatario9inregioneCorretto = rs.getBoolean("destinatario_9_in_regione_corretto");

		  this.destinatario10Modificato = rs.getBoolean("destinatario_10_modificato");
		  this.destinatario10idErrato = rs.getInt("destinatario_10_id_errato");
		  this.destinatario10idCorretto = rs.getInt("destinatario_10_id_corretto");
		  this.destinatario10nomeErrato = rs.getString("destinatario_10_nome_errato");
		  this.destinatario10nomeCorretto = rs.getString("destinatario_10_nome_corretto");
		  this.destinatario10inregioneErrato = rs.getBoolean("destinatario_10_in_regione_errato");
		  this.destinatario10inregioneCorretto = rs.getBoolean("destinatario_10_in_regione_corretto");

		  this.destinatario11Modificato = rs.getBoolean("destinatario_11_modificato");
		  this.destinatario11idErrato = rs.getInt("destinatario_11_id_errato");
		  this.destinatario11idCorretto = rs.getInt("destinatario_11_id_corretto");
		  this.destinatario11nomeErrato = rs.getString("destinatario_11_nome_errato");
		  this.destinatario11nomeCorretto = rs.getString("destinatario_11_nome_corretto");
		  this.destinatario11inregioneErrato = rs.getBoolean("destinatario_11_in_regione_errato");
		  this.destinatario11inregioneCorretto = rs.getBoolean("destinatario_11_in_regione_corretto");

		  this.destinatario12Modificato = rs.getBoolean("destinatario_12_modificato");
		  this.destinatario12idErrato = rs.getInt("destinatario_12_id_errato");
		  this.destinatario12idCorretto = rs.getInt("destinatario_12_id_corretto");
		  this.destinatario12nomeErrato = rs.getString("destinatario_12_nome_errato");
		  this.destinatario12nomeCorretto = rs.getString("destinatario_12_nome_corretto");
		  this.destinatario12inregioneErrato = rs.getBoolean("destinatario_12_in_regione_errato");
		  this.destinatario12inregioneCorretto = rs.getBoolean("destinatario_12_in_regione_corretto");

		  this.destinatario13Modificato = rs.getBoolean("destinatario_13_modificato");
		  this.destinatario13idErrato = rs.getInt("destinatario_13_id_errato");
		  this.destinatario13idCorretto = rs.getInt("destinatario_13_id_corretto");
		  this.destinatario13nomeErrato = rs.getString("destinatario_13_nome_errato");
		  this.destinatario13nomeCorretto = rs.getString("destinatario_13_nome_corretto");
		  this.destinatario13inregioneErrato = rs.getBoolean("destinatario_13_in_regione_errato");
		  this.destinatario13inregioneCorretto = rs.getBoolean("destinatario_13_in_regione_corretto");

		  this.destinatario14Modificato = rs.getBoolean("destinatario_14_modificato");
		  this.destinatario14idErrato = rs.getInt("destinatario_14_id_errato");
		  this.destinatario14idCorretto = rs.getInt("destinatario_14_id_corretto");
		  this.destinatario14nomeErrato = rs.getString("destinatario_14_nome_errato");
		  this.destinatario14nomeCorretto = rs.getString("destinatario_14_nome_corretto");
		  this.destinatario14inregioneErrato = rs.getBoolean("destinatario_14_in_regione_errato");
		  this.destinatario14inregioneCorretto = rs.getBoolean("destinatario_14_in_regione_corretto");

		  this.destinatario15Modificato = rs.getBoolean("destinatario_15_modificato");
		  this.destinatario15idErrato = rs.getInt("destinatario_15_id_errato");
		  this.destinatario15idCorretto = rs.getInt("destinatario_15_id_corretto");
		  this.destinatario15nomeErrato = rs.getString("destinatario_15_nome_errato");
		  this.destinatario15nomeCorretto = rs.getString("destinatario_15_nome_corretto");
		  this.destinatario15inregioneErrato = rs.getBoolean("destinatario_15_in_regione_errato");
		  this.destinatario15inregioneCorretto = rs.getBoolean("destinatario_15_in_regione_corretto");

		  this.destinatario16Modificato = rs.getBoolean("destinatario_16_modificato");
		  this.destinatario16idErrato = rs.getInt("destinatario_16_id_errato");
		  this.destinatario16idCorretto = rs.getInt("destinatario_16_id_corretto");
		  this.destinatario16nomeErrato = rs.getString("destinatario_16_nome_errato");
		  this.destinatario16nomeCorretto = rs.getString("destinatario_16_nome_corretto");
		  this.destinatario16inregioneErrato = rs.getBoolean("destinatario_16_in_regione_errato");
		  this.destinatario16inregioneCorretto = rs.getBoolean("destinatario_16_in_regione_corretto");

		  this.destinatario17Modificato = rs.getBoolean("destinatario_17_modificato");
		  this.destinatario17idErrato = rs.getInt("destinatario_17_id_errato");
		  this.destinatario17idCorretto = rs.getInt("destinatario_17_id_corretto");
		  this.destinatario17nomeErrato = rs.getString("destinatario_17_nome_errato");
		  this.destinatario17nomeCorretto = rs.getString("destinatario_17_nome_corretto");
		  this.destinatario17inregioneErrato = rs.getBoolean("destinatario_17_in_regione_errato");
		  this.destinatario17inregioneCorretto = rs.getBoolean("destinatario_17_in_regione_corretto");

		  this.destinatario18Modificato = rs.getBoolean("destinatario_18_modificato");
		  this.destinatario18idErrato = rs.getInt("destinatario_18_id_errato");
		  this.destinatario18idCorretto = rs.getInt("destinatario_18_id_corretto");
		  this.destinatario18nomeErrato = rs.getString("destinatario_18_nome_errato");
		  this.destinatario18nomeCorretto = rs.getString("destinatario_18_nome_corretto");
		  this.destinatario18inregioneErrato = rs.getBoolean("destinatario_18_in_regione_errato");
		  this.destinatario18inregioneCorretto = rs.getBoolean("destinatario_18_in_regione_corretto");

		  this.destinatario19Modificato = rs.getBoolean("destinatario_19_modificato");
		  this.destinatario19idErrato = rs.getInt("destinatario_19_id_errato");
		  this.destinatario19idCorretto = rs.getInt("destinatario_19_id_corretto");
		  this.destinatario19nomeErrato = rs.getString("destinatario_19_nome_errato");
		  this.destinatario19nomeCorretto = rs.getString("destinatario_19_nome_corretto");
		  this.destinatario19inregioneErrato = rs.getBoolean("destinatario_19_in_regione_errato");
		  this.destinatario19inregioneCorretto = rs.getBoolean("destinatario_19_in_regione_corretto");

		  this.destinatario20Modificato = rs.getBoolean("destinatario_20_modificato");
		  this.destinatario20idErrato = rs.getInt("destinatario_20_id_errato");
		  this.destinatario20idCorretto = rs.getInt("destinatario_20_id_corretto");
		  this.destinatario20nomeErrato = rs.getString("destinatario_20_nome_errato");
		  this.destinatario20nomeCorretto = rs.getString("destinatario_20_nome_corretto");
		  this.destinatario20inregioneErrato = rs.getBoolean("destinatario_20_in_regione_errato");
		  this.destinatario20inregioneCorretto = rs.getBoolean("destinatario_20_in_regione_corretto");
		  
		
	}
	

public Vector load(Connection db){
	ResultSet rs = null;
	Vector ecList = new Vector();
	PreparedStatement pst;
	try {
		
		String query ="SELECT * FROM macelli_art17_errata_corrige_ovicaprini where id_partita = ? and id_macello = ? and trashed_date is null";
		pst = db.prepareStatement(query);
		int i = 0;
		pst.setInt(++i, idPartita);
		pst.setInt(++i, idMacello);
		rs = DatabaseUtils.executeQuery(db, pst); 
		 while (rs.next()){
				 Art17ErrataCorrige ec = new Art17ErrataCorrige(rs);
				 ecList.add(ec);
				 
			 }
	rs.close();
	pst.close();
	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return ecList;
}

public Art17ErrataCorrige (ResultSet rs) throws SQLException{ 
	buildRecord(rs);
}

public Art17ErrataCorrige ( Connection db, int id){
	ResultSet rs = null;
	PreparedStatement pst;
	try {
		
		String query ="SELECT * FROM macelli_art17_errata_corrige_ovicaprini where id = ?";
		pst = db.prepareStatement(query);
		int i = 0;
		pst.setInt(++i, id);
		
		rs = DatabaseUtils.executeQuery(db, pst); 
		 if (rs.next()){
				 buildRecord(rs);
				 
			 }
	rs.close();
	pst.close();
	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}
	public Timestamp getEntered() {
		return entered;
	}
	public void setEntered(Timestamp entered) {
		this.entered = entered;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNomeUtente() {
		return nomeUtente;
	}
	public void setNomeUtente(String nomeUtente) {
		this.nomeUtente = nomeUtente;
	}
	
public int aggiornaPartita( Connection db ) throws SQLException{
		//int id = DatabaseUtils.getNextSeq(db, "macelli_art17_errata_corrige_id_seq");
		StringBuffer sql = new StringBuffer();
boolean doCommit = false;
		
		try {
			if (doCommit = db.getAutoCommit()) {
				db.setAutoCommit(false);
			}
				
		sql.append("UPDATE m_partite SET modified=now(), modified_by= ? ");
		
		if (numeroModificato)
			sql.append(", cd_partita = ?");
		if (mod4Modificato)
			sql.append(", cd_mod4 = ? ");
		
		if (veterinario1Modificato)
			sql.append(", cd_veterinario_1 = ? ");
		if (veterinario2Modificato)
			sql.append(", cd_veterinario_2 = ? ");
		if (veterinario3Modificato)
			sql.append(", cd_veterinario_3 = ? ");
		
		
		if (destinatario1Modificato)
			sql.append(", destinatario_1_id = ?,  destinatario_1_nome = ?,  destinatario_1_in_regione = ? ");
		if (destinatario2Modificato)
			sql.append(", destinatario_2_id = ?,  destinatario_2_nome = ?,  destinatario_2_in_regione = ? ");
		if (destinatario3Modificato)
			sql.append(", destinatario_3_id = ?,  destinatario_3_nome = ?,  destinatario_3_in_regione = ? ");
		if (destinatario4Modificato)
			sql.append(", destinatario_4_id = ?,  destinatario_4_nome = ?,  destinatario_4_in_regione = ? ");
		if (destinatario5Modificato)
			sql.append(", destinatario_5_id = ?,  destinatario_5_nome = ?,  destinatario_5_in_regione = ? ");
		if (destinatario6Modificato)
			sql.append(", destinatario_6_id = ?,  destinatario_6_nome = ?,  destinatario_6_in_regione = ? ");
		if (destinatario7Modificato)
			sql.append(", destinatario_7_id = ?,  destinatario_7_nome = ?,  destinatario_7_in_regione = ? ");
		if (destinatario8Modificato)
			sql.append(", destinatario_8_id = ?,  destinatario_8_nome = ?,  destinatario_8_in_regione = ? ");
		if (destinatario9Modificato)
			sql.append(", destinatario_9_id = ?,  destinatario_9_nome = ?,  destinatario_9_in_regione = ? ");
		if (destinatario10Modificato)
			sql.append(", destinatario_10_id = ?,  destinatario_10_nome = ?,  destinatario_10_in_regione = ? ");
		if (destinatario11Modificato)
			sql.append(", destinatario_11_id = ?,  destinatario_11_nome = ?,  destinatario_11_in_regione = ? ");
		if (destinatario12Modificato)
			sql.append(", destinatario_12_id = ?,  destinatario_12_nome = ?,  destinatario_12_in_regione = ? ");
		if (destinatario13Modificato)
			sql.append(", destinatario_13_id = ?,  destinatario_13_nome = ?,  destinatario_13_in_regione = ? ");
		if (destinatario14Modificato)
			sql.append(", destinatario_14_id = ?,  destinatario_14_nome = ?,  destinatario_14_in_regione = ? ");
		if (destinatario15Modificato)
			sql.append(", destinatario_15_id = ?,  destinatario_15_nome = ?,  destinatario_15_in_regione = ? ");
		if (destinatario16Modificato)
			sql.append(", destinatario_16_id = ?,  destinatario_16_nome = ?,  destinatario_16_in_regione = ? ");
		if (destinatario17Modificato)
			sql.append(", destinatario_17_id = ?,  destinatario_17_nome = ?,  destinatario_17_in_regione = ? ");
		if (destinatario18Modificato)
			sql.append(", destinatario_18_id = ?,  destinatario_18_nome = ?,  destinatario_18_in_regione = ? ");
		if (destinatario19Modificato)
			sql.append(", destinatario_19_id = ?,  destinatario_19_nome = ?,  destinatario_19_in_regione = ? ");
		if (destinatario20Modificato)
			sql.append(", destinatario_20_id = ?,  destinatario_20_nome = ?,  destinatario_20_in_regione = ? ");
		
		sql.append(", errata_corrige_generati = errata_corrige_generati + 1  where id = ? ");
		
		int i = 0;
		PreparedStatement pst = db.prepareStatement(sql.toString());

		//pst.setInt(++i, id);
		pst.setInt(++i, idUtente);
	
		if (numeroModificato)
			{
			pst.setString(++i, numeroCorretto);
			}
		
		if (mod4Modificato)
		{
			pst.setString(++i, mod4Corretto);
			}
		
		if (veterinario1Modificato)
		{
			pst.setString(++i, veterinario1Corretto);
			}
		if (veterinario2Modificato)
		{
			pst.setString(++i, veterinario2Corretto);
			}
		if (veterinario3Modificato)
		{
			pst.setString(++i, veterinario3Corretto);
			}
		
		if (destinatario1Modificato)
		{
			pst.setInt(++i, destinatario1idCorretto);
			pst.setString(++i, destinatario1nomeCorretto);
			pst.setBoolean(++i, destinatario1inregioneCorretto);
			}
		if (destinatario2Modificato)
		{
			pst.setInt(++i, destinatario2idCorretto);
			pst.setString(++i, destinatario2nomeCorretto);
			pst.setBoolean(++i, destinatario2inregioneCorretto);
			}
		if (destinatario3Modificato)
		{
			pst.setInt(++i, destinatario3idCorretto);
			pst.setString(++i, destinatario3nomeCorretto);
			pst.setBoolean(++i, destinatario3inregioneCorretto);
			}
		if (destinatario4Modificato)
		{
			pst.setInt(++i, destinatario4idCorretto);
			pst.setString(++i, destinatario4nomeCorretto);
			pst.setBoolean(++i, destinatario4inregioneCorretto);
			}
		if (destinatario5Modificato)
		{
			pst.setInt(++i, destinatario5idCorretto);
			pst.setString(++i, destinatario5nomeCorretto);
			pst.setBoolean(++i, destinatario5inregioneCorretto);
			}
		if (destinatario6Modificato)
		{
			pst.setInt(++i, destinatario6idCorretto);
			pst.setString(++i, destinatario6nomeCorretto);
			pst.setBoolean(++i, destinatario6inregioneCorretto);
			}
		if (destinatario7Modificato)
		{
			pst.setInt(++i, destinatario7idCorretto);
			pst.setString(++i, destinatario7nomeCorretto);
			pst.setBoolean(++i, destinatario7inregioneCorretto);
			}
		if (destinatario8Modificato)
		{
			pst.setInt(++i, destinatario8idCorretto);
			pst.setString(++i, destinatario8nomeCorretto);
			pst.setBoolean(++i, destinatario8inregioneCorretto);
			}
		if (destinatario9Modificato)
		{
			pst.setInt(++i, destinatario9idCorretto);
			pst.setString(++i, destinatario9nomeCorretto);
			pst.setBoolean(++i, destinatario9inregioneCorretto);
			}
		if (destinatario10Modificato)
		{
			pst.setInt(++i, destinatario10idCorretto);
			pst.setString(++i, destinatario10nomeCorretto);
			pst.setBoolean(++i, destinatario10inregioneCorretto);
			}
		if (destinatario11Modificato)
		{
			pst.setInt(++i, destinatario11idCorretto);
			pst.setString(++i, destinatario11nomeCorretto);
			pst.setBoolean(++i, destinatario11inregioneCorretto);
			}
		if (destinatario12Modificato)
		{
			pst.setInt(++i, destinatario12idCorretto);
			pst.setString(++i, destinatario12nomeCorretto);
			pst.setBoolean(++i, destinatario12inregioneCorretto);
			}
		if (destinatario13Modificato)
		{
			pst.setInt(++i, destinatario13idCorretto);
			pst.setString(++i, destinatario13nomeCorretto);
			pst.setBoolean(++i, destinatario13inregioneCorretto);
			}
		if (destinatario14Modificato)
		{
			pst.setInt(++i, destinatario14idCorretto);
			pst.setString(++i, destinatario14nomeCorretto);
			pst.setBoolean(++i, destinatario14inregioneCorretto);
			}
		if (destinatario15Modificato)
		{
			pst.setInt(++i, destinatario15idCorretto);
			pst.setString(++i, destinatario15nomeCorretto);
			pst.setBoolean(++i, destinatario15inregioneCorretto);
			}
		if (destinatario16Modificato)
		{
			pst.setInt(++i, destinatario16idCorretto);
			pst.setString(++i, destinatario16nomeCorretto);
			pst.setBoolean(++i, destinatario16inregioneCorretto);
			}
		if (destinatario17Modificato)
		{
			pst.setInt(++i, destinatario17idCorretto);
			pst.setString(++i, destinatario17nomeCorretto);
			pst.setBoolean(++i, destinatario17inregioneCorretto);
			}
		if (destinatario18Modificato)
		{
			pst.setInt(++i, destinatario18idCorretto);
			pst.setString(++i, destinatario18nomeCorretto);
			pst.setBoolean(++i, destinatario18inregioneCorretto);
			}
		if (destinatario19Modificato)
		{
			pst.setInt(++i, destinatario19idCorretto);
			pst.setString(++i, destinatario19nomeCorretto);
			pst.setBoolean(++i, destinatario19inregioneCorretto);
			}
		if (destinatario20Modificato)
		{
			pst.setInt(++i, destinatario20idCorretto);
			pst.setString(++i, destinatario20nomeCorretto);
			pst.setBoolean(++i, destinatario20inregioneCorretto);
			}
		
		pst.setInt(++i, idPartita);
		
		pst.executeUpdate();
		pst.close();
		if (doCommit) {
			db.commit();
		}
		}catch (SQLException e) {
			if (doCommit) {
				db.rollback();
			}throw new SQLException(e.getMessage());
		} 
		return 2;
	}

public int aggiornaArt17( Connection db ) throws SQLException{
	//int id = DatabaseUtils.getNextSeq(db, "macelli_art17_errata_corrige_id_seq");
	StringBuffer sql = new StringBuffer();
boolean doCommit = false;
	
	try {
		if (doCommit = db.getAutoCommit()) {
			db.setAutoCommit(false);
		}
		
	if (destinatario1Modificato)
		sql.append("UPDATE m_art17 SET id_esercente = ?, nome_esercente = ?  where id_esercente = ? and id_macello = ? and data_modello = ? and trashed_date is null;");
	if (destinatario2Modificato)
		sql.append("UPDATE m_art17 SET id_esercente = ?, nome_esercente = ?  where id_esercente = ? and id_macello = ? and data_modello = ? and trashed_date is null;");
	if (destinatario3Modificato)
		sql.append("UPDATE m_art17 SET id_esercente = ?, nome_esercente = ?  where id_esercente = ? and id_macello = ? and data_modello = ? and trashed_date is null;");
	if (destinatario4Modificato)
		sql.append("UPDATE m_art17 SET id_esercente = ?, nome_esercente = ?  where id_esercente = ? and id_macello = ? and data_modello = ? and trashed_date is null;");
	if (destinatario5Modificato)
		sql.append("UPDATE m_art17 SET id_esercente = ?, nome_esercente = ?  where id_esercente = ? and id_macello = ? and data_modello = ? and trashed_date is null;");
	if (destinatario6Modificato)
		sql.append("UPDATE m_art17 SET id_esercente = ?, nome_esercente = ?  where id_esercente = ? and id_macello = ? and data_modello = ? and trashed_date is null;");
	if (destinatario7Modificato)
		sql.append("UPDATE m_art17 SET id_esercente = ?, nome_esercente = ?  where id_esercente = ? and id_macello = ? and data_modello = ? and trashed_date is null;");
	if (destinatario8Modificato)
		sql.append("UPDATE m_art17 SET id_esercente = ?, nome_esercente = ?  where id_esercente = ? and id_macello = ? and data_modello = ? and trashed_date is null;");
	if (destinatario9Modificato)
		sql.append("UPDATE m_art17 SET id_esercente = ?, nome_esercente = ?  where id_esercente = ? and id_macello = ? and data_modello = ? and trashed_date is null;");
	if (destinatario10Modificato)
		sql.append("UPDATE m_art17 SET id_esercente = ?, nome_esercente = ?  where id_esercente = ? and id_macello = ? and data_modello = ? and trashed_date is null;");
	if (destinatario11Modificato)
		sql.append("UPDATE m_art17 SET id_esercente = ?, nome_esercente = ?  where id_esercente = ? and id_macello = ? and data_modello = ? and trashed_date is null;");
	if (destinatario12Modificato)
		sql.append("UPDATE m_art17 SET id_esercente = ?, nome_esercente = ?  where id_esercente = ? and id_macello = ? and data_modello = ? and trashed_date is null;");
	if (destinatario13Modificato)
		sql.append("UPDATE m_art17 SET id_esercente = ?, nome_esercente = ?  where id_esercente = ? and id_macello = ? and data_modello = ? and trashed_date is null;");
	if (destinatario14Modificato)
		sql.append("UPDATE m_art17 SET id_esercente = ?, nome_esercente = ?  where id_esercente = ? and id_macello = ? and data_modello = ? and trashed_date is null;");
	if (destinatario15Modificato)
		sql.append("UPDATE m_art17 SET id_esercente = ?, nome_esercente = ?  where id_esercente = ? and id_macello = ? and data_modello = ? and trashed_date is null;");
	if (destinatario16Modificato)
		sql.append("UPDATE m_art17 SET id_esercente = ?, nome_esercente = ?  where id_esercente = ? and id_macello = ? and data_modello = ? and trashed_date is null;");
	if (destinatario17Modificato)
		sql.append("UPDATE m_art17 SET id_esercente = ?, nome_esercente = ?  where id_esercente = ? and id_macello = ? and data_modello = ? and trashed_date is null;");
	if (destinatario18Modificato)
		sql.append("UPDATE m_art17 SET id_esercente = ?, nome_esercente = ?  where id_esercente = ? and id_macello = ? and data_modello = ? and trashed_date is null;");
	if (destinatario19Modificato)
		sql.append("UPDATE m_art17 SET id_esercente = ?, nome_esercente = ?  where id_esercente = ? and id_macello = ? and data_modello = ? and trashed_date is null;");
	if (destinatario20Modificato)
		sql.append("UPDATE m_art17 SET id_esercente = ?, nome_esercente = ?  where id_esercente = ? and id_macello = ? and data_modello = ? and trashed_date is null;");
	
	int i = 0;
	PreparedStatement pst = db.prepareStatement(sql.toString());

	
	if (destinatario1Modificato)
	{
		pst.setInt(++i, destinatario1idCorretto);
		pst.setString(++i, destinatario1nomeCorretto);
		pst.setInt(++i, destinatario1idErrato);
		pst.setInt(++i, idMacello);
		pst.setTimestamp(++i, dataMacellazione);
		}
	if (destinatario2Modificato)
	{
		pst.setInt(++i, destinatario2idCorretto);
		pst.setString(++i, destinatario2nomeCorretto);
		pst.setInt(++i, destinatario2idErrato);
		pst.setInt(++i, idMacello);
		pst.setTimestamp(++i, dataMacellazione);
		}
	if (destinatario3Modificato)
	{
		pst.setInt(++i, destinatario3idCorretto);
		pst.setString(++i, destinatario3nomeCorretto);
		pst.setInt(++i, destinatario3idErrato);
		pst.setInt(++i, idMacello);
		pst.setTimestamp(++i, dataMacellazione);
		}
	if (destinatario4Modificato)
	{
		pst.setInt(++i, destinatario4idCorretto);
		pst.setString(++i, destinatario4nomeCorretto);
		pst.setInt(++i, destinatario4idErrato);
		pst.setInt(++i, idMacello);
		pst.setTimestamp(++i, dataMacellazione);
		}
	if (destinatario5Modificato)
	{
		pst.setInt(++i, destinatario5idCorretto);
		pst.setString(++i, destinatario5nomeCorretto);
		pst.setInt(++i, destinatario5idErrato);
		pst.setInt(++i, idMacello);
		pst.setTimestamp(++i, dataMacellazione);
		}
	if (destinatario6Modificato)
	{
		pst.setInt(++i, destinatario6idCorretto);
		pst.setString(++i, destinatario6nomeCorretto);
		pst.setInt(++i, destinatario6idErrato);
		pst.setInt(++i, idMacello);
		pst.setTimestamp(++i, dataMacellazione);
		}
	if (destinatario7Modificato)
	{
		pst.setInt(++i, destinatario7idCorretto);
		pst.setString(++i, destinatario7nomeCorretto);
		pst.setInt(++i, destinatario7idErrato);
		pst.setInt(++i, idMacello);
		pst.setTimestamp(++i, dataMacellazione);
		}
	if (destinatario8Modificato)
	{
		pst.setInt(++i, destinatario8idCorretto);
		pst.setString(++i, destinatario8nomeCorretto);
		pst.setInt(++i, destinatario8idErrato);
		pst.setInt(++i, idMacello);
		pst.setTimestamp(++i, dataMacellazione);
		}
	if (destinatario9Modificato)
	{
		pst.setInt(++i, destinatario9idCorretto);
		pst.setString(++i, destinatario9nomeCorretto);
		pst.setInt(++i, destinatario9idErrato);
		pst.setInt(++i, idMacello);
		pst.setTimestamp(++i, dataMacellazione);
		}
	if (destinatario10Modificato)
	{
		pst.setInt(++i, destinatario10idCorretto);
		pst.setString(++i, destinatario10nomeCorretto);
		pst.setInt(++i, destinatario10idErrato);
		pst.setInt(++i, idMacello);
		pst.setTimestamp(++i, dataMacellazione);
		}
	if (destinatario11Modificato)
	{
		pst.setInt(++i, destinatario11idCorretto);
		pst.setString(++i, destinatario11nomeCorretto);
		pst.setInt(++i, destinatario11idErrato);
		pst.setInt(++i, idMacello);
		pst.setTimestamp(++i, dataMacellazione);
		}
	if (destinatario12Modificato)
	{
		pst.setInt(++i, destinatario12idCorretto);
		pst.setString(++i, destinatario12nomeCorretto);
		pst.setInt(++i, destinatario12idErrato);
		pst.setInt(++i, idMacello);
		pst.setTimestamp(++i, dataMacellazione);
		}
	if (destinatario13Modificato)
	{
		pst.setInt(++i, destinatario13idCorretto);
		pst.setString(++i, destinatario13nomeCorretto);
		pst.setInt(++i, destinatario13idErrato);
		pst.setInt(++i, idMacello);
		pst.setTimestamp(++i, dataMacellazione);
		}
	if (destinatario14Modificato)
	{
		pst.setInt(++i, destinatario14idCorretto);
		pst.setString(++i, destinatario14nomeCorretto);
		pst.setInt(++i, destinatario14idErrato);
		pst.setInt(++i, idMacello);
		pst.setTimestamp(++i, dataMacellazione);
		}
	if (destinatario15Modificato)
	{
		pst.setInt(++i, destinatario15idCorretto);
		pst.setString(++i, destinatario15nomeCorretto);
		pst.setInt(++i, destinatario15idErrato);
		pst.setInt(++i, idMacello);
		pst.setTimestamp(++i, dataMacellazione);
		}
	if (destinatario16Modificato)
	{
		pst.setInt(++i, destinatario16idCorretto);
		pst.setString(++i, destinatario16nomeCorretto);
		pst.setInt(++i, destinatario16idErrato);
		pst.setInt(++i, idMacello);
		pst.setTimestamp(++i, dataMacellazione);
		}
	if (destinatario17Modificato)
	{
		pst.setInt(++i, destinatario17idCorretto);
		pst.setString(++i, destinatario17nomeCorretto);
		pst.setInt(++i, destinatario17idErrato);
		pst.setInt(++i, idMacello);
		pst.setTimestamp(++i, dataMacellazione);
		}
	if (destinatario18Modificato)
	{
		pst.setInt(++i, destinatario18idCorretto);
		pst.setString(++i, destinatario18nomeCorretto);
		pst.setInt(++i, destinatario18idErrato);
		pst.setInt(++i, idMacello);
		pst.setTimestamp(++i, dataMacellazione);
		}
	if (destinatario19Modificato)
	{
		pst.setInt(++i, destinatario19idCorretto);
		pst.setString(++i, destinatario19nomeCorretto);
		pst.setInt(++i, destinatario19idErrato);
		pst.setInt(++i, idMacello);
		pst.setTimestamp(++i, dataMacellazione);
		}
	if (destinatario20Modificato)
	{
		pst.setInt(++i, destinatario20idCorretto);
		pst.setString(++i, destinatario20nomeCorretto);
		pst.setInt(++i, destinatario20idErrato);
		pst.setInt(++i, idMacello);
		pst.setTimestamp(++i, dataMacellazione);
		}
	
	if (sql!=null && !sql.equals(""))	
		pst.executeUpdate();
	
	pst.close();
	if (doCommit) {
		db.commit();
	}
	}catch (SQLException e) {
		if (doCommit) {
			db.rollback();
		}throw new SQLException(e.getMessage());
	} 
	return 3;
}

public String getRiferimentoArt17() {
	return riferimentoArt17;
}

public void setRiferimentoArt17(String riferimentoArt17) {
	this.riferimentoArt17 = riferimentoArt17;
}
}
