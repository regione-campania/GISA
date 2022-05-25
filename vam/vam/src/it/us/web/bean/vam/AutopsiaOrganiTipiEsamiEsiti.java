/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam;

import it.us.web.bean.vam.lookup.LookupAutopsiaOrganiTipiEsamiEsiti;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "autopsia_organo_tipo_esame_esito", schema = "public")
@Where( clause = "trashed_date is null" )

public class AutopsiaOrganiTipiEsamiEsiti implements java.io.Serializable
{
		private static final long serialVersionUID = 5528525804289839766L;
		
		private int id;
		private Autopsia autopsia;		
		private LookupAutopsiaOrganiTipiEsamiEsiti lookupAutopsiaOrganiTipiEsamiEsiti = new LookupAutopsiaOrganiTipiEsamiEsiti();
		private String note;
		private String valore;
		
		public AutopsiaOrganiTipiEsamiEsiti()
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
		@JoinColumn(name="id_organo_tipo_esame_esito")
		public LookupAutopsiaOrganiTipiEsamiEsiti getLookupAutopsiaOrganiTipiEsamiEsiti() {
			return lookupAutopsiaOrganiTipiEsamiEsiti;
		}

		public void setLookupAutopsiaOrganiTipiEsamiEsiti(
				LookupAutopsiaOrganiTipiEsamiEsiti lookupAutopsiaOrganiTipiEsamiEsiti) {
			this.lookupAutopsiaOrganiTipiEsamiEsiti = lookupAutopsiaOrganiTipiEsamiEsiti;
		}

		@Column(name = "note")
		public String getNote() {
			return note;
		}
		public void setNote(String note) {
			this.note = note;
		}
		
		@Column(name = "valore")
		public String getValore() {
			return valore;
		}
		public void setValore(String valore) {
			this.valore = valore;
		}
		
		@Override
		public String toString()
		{
			return id+"";
		}
		
		@Transient
		public String getNomeEsame()
		{
			return "Sezione Dettaglio Esami";
		}

	}
