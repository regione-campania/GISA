/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function costruisciQuery(){
	
	var schema = '\"Analisi_dev\"'
	
	console.log("id_riga = " + id_r_new);
	console.log("id_colonnna = " + id_c_new);
	console.log("ANNO_GLOB =" + ANNO_GLOB);

	if(ANNO_GLOB == 'null' || ANNO_GLOB == null)
		ANNO_GLOB = anno_php;
	//ANNO_GLOB = 2019;
	//id_p e id_s sono gli id selezionati nei rispettivi alberi (piano e strutt)

	if(id_c_new == -1 && dim1 == piani){
		id_c_new = getPianiRoot(ANNO_GLOB);
	}else if(id_r_new == -1 && dim2 == piani){
		id_r_new = getPianiRoot(ANNO_GLOB);
	}else if(id_3_new == -1 && dim3 == piani){
		id_3_new = getPianiRoot(ANNO_GLOB);
	}else if(id_4_new == -1 && dim4 == piani){
		id_4_new = getPianiRoot(ANNO_GLOB);
	}

	if(id_c_new <= 8 && dim1 == strutture){
		id_c_new = getStruttureRoot(ANNO_GLOB,id_c_new);
	}else if(id_r_new <= 8 && dim2 == strutture){
		id_r_new = getStruttureRoot(ANNO_GLOB,id_r_new);
	}else if(id_3_new <= 8 && dim3 == strutture){
		id_3_new = getStruttureRoot(ANNO_GLOB,id_3_new);
	}else if(id_4_new <= 8 && dim4 == strutture){
		id_4_new = getStruttureRoot(ANNO_GLOB,id_4_new);
	}

		
	if(w_date == null)
		w_date = "1=1";

	var q = 'select replace('+riga+', \'.\', \'\') riga, a.id as id_r,  replace('+colonna+', \'.\', \'\') colonna, p.id as id_c, sum(coalesce(eseguiti,0)) eseguiti, (coalesce(t.sum, 0)) as sum	\n\
	 from matrix.vw_tree_nodes_piani_descr p	\n\
	 join matrix.struttura_asl a on a.n_livello = 1 and a.anno = '+ANNO_GLOB+' and p.p_id = '+id_c_new+' \n\
	 join matrix.struttura_piani sp2 on sp2.cod_raggruppamento = p.cod_raggruppamento and sp2.id_gisa in  (	\n\
	 			select id_gisa from ra.vw_struttura_piani_ref_adhoc where id_ref  in (select pd.id from matrix.vw_tree_nodes_piani_descr pd where pd.p_id = '+id_c_new+')	\n\
			)	\n\
	 left join matrix.vw_struttura_piano_target_tree_adhoc t 	\n\
	 on  t.id_node_ref = p.id and a.id = t.id_struttura and p.anno = t.anno 	\n\
	 left join(	\n\
		 select pr.id_ref, mi.* from 	\n\
		 ra.mvw_adhoc mi 	\n\
		 join matrix.struttura_piani i on i.cod_raggruppamento = mi.cod_raggruppamento	\n\
		 join "Analisi_dev".vw_struttura_piani_ref pr on pr.id_gisa = i.id_gisa and id_ref  in (select pd.id from matrix.vw_tree_nodes_piani_descr pd where pd.p_id = '+id_c_new+')	\n\
		 where mi.cod_raggruppamento in (	\n\
			 select cod_raggruppamento from	\n\
			 matrix.struttura_piani sp where id_gisa in (	\n\
				 select id_gisa from "Analisi_dev".vw_struttura_piani_ref where id_ref  in (select pd.id from matrix.vw_tree_nodes_piani_descr pd where pd.p_id = '+id_c_new+')	\n\
			)	\n\
		)	\n\
		and '+w_date+' and extract(year from data) = '+ANNO_GLOB+'	\n\
	)c on c.id_ref = sp2.id and c.id_asl = a.id_asl 	\n\
	 where not ((p.livello = 3 and p.cod_raggruppamento not in (select p_cod_raggruppamento from matrix.vw_tree_nodes_piani_descr pd2 where pd2.cod_raggruppamento in (	\n\
		 select cf.cod_raggruppamento from ra.config_adhoc cf)))	\n\
			 or (p.cod_raggruppamento not in (select cf.cod_raggruppamento from ra.config_adhoc cf) and p.livello = 4)	\n\
			 or (p.livello in (2) and eseguiti = 0) ) 	\n\
	 group by 1,2,3,4, 6, sp2.ordinamento order by sp2.ordinamento,1 ';
		
	return q;
	
}


function coalesce() {
    var len = arguments.length;
    for (var i=0; i<len; i++) {
        if (arguments[i] !== null && arguments[i] !== undefined) {
            return arguments[i];
        }
    }
	return null;
}

ret = null;
function getStruttureRoot(_anno,_id){
	sono_regione = true;
	console.log("../getStruttureRoot.php?anno="+_anno+"&id="+_id);
	$.ajax({
		url: "../getStruttureRoot.php?anno="+_anno+"&id="+_id,
		async: false,
		success: function(res){
			ret = res;
		}
	})
	return ret;
}


function getPianiRoot(_anno){
	console.log("../getPianiRoot.php?anno="+_anno);
	$.ajax({
		url: "../getPianiRoot.php?anno="+_anno,
		async: false,
		success: function(res){
			ret = res;
		}
	})
	return ret;
}
