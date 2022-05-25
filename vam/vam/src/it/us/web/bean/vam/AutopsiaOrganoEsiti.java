/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam;

import it.us.web.bean.vam.lookup.LookupAutopsiaOrgani;
import it.us.web.bean.vam.lookup.LookupAutopsiaPatologiePrevalenti;
import it.us.web.bean.vam.lookup.LookupAutopsiaTipiEsami;

import java.util.HashSet;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "autopsia_organoesiti", schema = "public")
@Where( clause = "trashed_date is null" )
public class AutopsiaOrganoEsiti implements java.io.Serializable
{
		private static final long serialVersionUID = 5528525804289839766L;
		
		private int id;
		private Autopsia autopsia;		
		private LookupAutopsiaOrgani    lookupOrganiAutopsia;		
		private LookupAutopsiaTipiEsami lookupAutopsiaTipiEsami;	
		private Set<AutopsiaOrganiTipiEsamiEsiti> autopsiaOrganiTipiEsamiEsitis = new HashSet<AutopsiaOrganiTipiEsamiEsiti>(0);
		private String altro;
		
		public AutopsiaOrganoEsiti()
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
		
				
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "tipo_esame")
		public LookupAutopsiaTipiEsami getLookupAutopsiaTipiEsami() {
			return lookupAutopsiaTipiEsami;
		}

		public void setLookupAutopsiaTipiEsami(
				LookupAutopsiaTipiEsami lookupAutopsiaTipiEsami) {
			this.lookupAutopsiaTipiEsami = lookupAutopsiaTipiEsami;
		}

		@ManyToMany(fetch = FetchType.LAZY)
		@JoinTable(name = "esame_organoesiti", schema = "public", joinColumns = { @JoinColumn(name = "esame", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "esito", nullable = false, updatable = false) })
		public Set<AutopsiaOrganiTipiEsamiEsiti> getAutopsiaOrganiTipiEsamiEsitis() {
			return autopsiaOrganiTipiEsamiEsitis;
		}

		public void setAutopsiaOrganiTipiEsamiEsitis(
				Set<AutopsiaOrganiTipiEsamiEsiti> autopsiaOrganiTipiEsamiEsitis) {
			this.autopsiaOrganiTipiEsamiEsitis = autopsiaOrganiTipiEsamiEsitis;
		}

		@Column(name = "altro")
		public String getAltro() {
			return altro;
		}
		public void setAltro(String altro) {
			this.altro = altro;
		}
		
		@Override
		public String toString()
		{
			return id+"";
		}

	}
