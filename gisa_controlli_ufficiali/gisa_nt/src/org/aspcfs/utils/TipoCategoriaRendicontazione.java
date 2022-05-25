/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

public enum TipoCategoriaRendicontazione {


    Da_macello(1),
    Da_esportazione(2),
    Importati_per_allevamento(4),
    Altri_animali_trasportati(6);
 
    
    private int indice_categoria;
    
    private TipoCategoriaRendicontazione(int cat) {
            this.indice_categoria = cat;
    }

    public int getCategoria() {
            return indice_categoria;
    }

    public void setCategoria(int categoria) {
            this.indice_categoria = categoria;
    }
    
    
}
