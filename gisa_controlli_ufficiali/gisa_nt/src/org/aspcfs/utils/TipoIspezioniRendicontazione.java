/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

public enum TipoIspezioniRendicontazione {


    Durante_il_trasporto(69),
    Al_luogo_arrivo(74),
    Al_mercato(71),
    Al_luogo_partenza(73),
    Ai_punti_di_sosta(70),
    Ai_punti_trasferimento(72),
    Controlli_documentali(75);
 
    
    private int indiceTipoIspezione;
    
    private TipoIspezioniRendicontazione(int tipo) {
            this.indiceTipoIspezione = tipo;
    }

    public int getIndiceTipoIspezione() {
            return indiceTipoIspezione;
    }

    public void setTipoIspezione(int tipo) {
            this.indiceTipoIspezione = tipo;
    }
    
    
}
