/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam;

import it.us.web.bean.BUtente;
import it.us.web.bean.vam.lookup.LookupEsameObiettivoTipo;
import it.us.web.bean.vam.lookup.LookupAutopsiaOrgani;
import it.us.web.bean.vam.lookup.LookupAutopsiaPatologiePrevalenti;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "autopsia_organopatologie", schema = "public")
public class AutopsiaOrganoPatologie implements java.io.Serializable
{
		private static final long serialVersionUID = 5528525804289839766L;
		
		private int id;
		private Autopsia autopsia;		
		private LookupAutopsiaOrgani lookupOrganiAutopsia;					
		private Set<LookupAutopsiaPatologiePrevalenti> lookupPatologiePrevalentiAutopsias = new HashSet<LookupAutopsiaPatologiePrevalenti>(0);
		private String altro;
		private boolean esaminato;
		
		public AutopsiaOrganoPatologie()
		{
			
		}

		@Id
		@GeneratedValue( strategy = GenerationType.IDENTITY )
		@Column(name = "id", unique = true, nullable = false)
		@NotNull
		public int getId() {
			return this.id;
		}

		public void setId(int id) {
			this.id = id;
		}
		
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "autopsia")
		public Autopsia getAutopsia() {
			return autopsia;
		}

		public void setAutopsia(Autopsia autopsia) {
			this.autopsia = autopsia;
		}
		
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "organo")
		public LookupAutopsiaOrgani getLookupOrganiAutopsia() {
			return lookupOrganiAutopsia;
		}

		public void setLookupOrganiAutopsia(LookupAutopsiaOrgani lookupOrganiAutopsia) {
			this.lookupOrganiAutopsia = lookupOrganiAutopsia;
		}
		
		@ManyToMany(fetch = FetchType.LAZY)
		@JoinTable(name = "esame_organopatologie", schema = "public", joinColumns = { @JoinColumn(name = "esame", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "patologia", nullable = false, updatable = false) })
		public Set<LookupAutopsiaPatologiePrevalenti> getLookupPatologiePrevalentiAutopsias() {
			return lookupPatologiePrevalentiAutopsias;
		}

		public void setLookupPatologiePrevalentiAutopsias(
				Set<LookupAutopsiaPatologiePrevalenti> lookupPatologiePrevalentiAutopsias) {
			this.lookupPatologiePrevalentiAutopsias = lookupPatologiePrevalentiAutopsias;
		}

		@Column(name = "altro")
		@Type(type = "text")
		public String getAltro() {
			return altro;
		}
		public void setAltro(String altro) {
			this.altro = altro;
		}

		@Column(name = "esaminato")
		public boolean isEsaminato() {
			return esaminato;
		}

		public void setEsaminato(boolean esaminato) {
			this.esaminato = esaminato;
		}
		
		@Transient
		public String getDescrizioneReferto() 
		{
			String descrizione = "";
			String separatore = "";
			if(!esaminato)
				descrizione = "Normale";
			else
			{
				Iterator<LookupAutopsiaPatologiePrevalenti> iter = lookupPatologiePrevalentiAutopsias.iterator();
				while(iter.hasNext())
				{
					if(!descrizione.equals(""))
						separatore = ", ";
					descrizione = descrizione + separatore + iter.next().getDescription();
				}
			}
			
			return descrizione;
	    	         
		}
		
		@Transient
		public String getNoteReferto() 
		{
			String note = "";
			if(esaminato && !altro.trim().equals(""))
			{
				note = "Note: " + altro;
			}
			
			return note;
		}
		
		@Override
		public String toString()
		{
			return id+"";
		}
		
		
		@Transient
		public String getNomeEsame()
		{
			return "Esame morfologico degli organi";
		}
		
	}
