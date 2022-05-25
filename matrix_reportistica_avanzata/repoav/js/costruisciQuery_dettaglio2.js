/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function costruisciQuery_dettaglio(){

	var schema = '\"Analisi_dev\"'
	
	console.log("id_riga = " + id_r_new);
	console.log("id_colonnna = " + id_c_new);
	console.log("ANNO_GLOB =" + ANNO_GLOB);


	if(ANNO_GLOB == 'null' || ANNO_GLOB == null)
		ANNO_GLOB = anno_php
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

	if(id_c_new == 8 && dim1 == strutture){
		id_c_new = getStruttureRoot(ANNO_GLOB);
	}else if(id_r_new == 8 && dim2 == strutture){
		id_r_new = getStruttureRoot(ANNO_GLOB);
	}else if(id_3_new == 8 && dim3 == strutture){
		id_3_new = getStruttureRoot(ANNO_GLOB);
	}else if(id_4_new == 8 && dim4 == strutture){
		id_4_new = getStruttureRoot(ANNO_GLOB);
	}
	
	if(w_date == null)
		w_date = "1=1";

	id_p_ref = "";
	id_a_ref = "";

	var from1 = "";
	var on1 = "";
	var onCu1 = "";
	if(dim1 == piani){
		from1 = " matrix.vw_tree_nodes_piani_descr p ";
		on1 = " p.p_id = " + id_c_new;
		onCu1 = " p.id = cu.p_id_ref ";
		id_p_ref = id_c_new;
	}else if(dim1 == strutture){
		from1 = " matrix.vw_tree_nodes_asl_descr a ";
		on1 = " a.p_id = " + id_c_new;
		onCu1 = " a.id = cu.a_id_ref ";
		id_a_ref = id_c_new;
	}else if(dim1 == linee){
		from1 = " \"Analisi_dev\".macroarea vm ";
		on1 = " vm.parent  = " + id_c_new;
		onCu1 = " vm.id = cu.m_id_ref ";
	}else if(dim1 == date){
		on1 = " 1=1 ";
		onCu1 = " 1=1 ";
	}
	
	var from2 = "";
	var on2 = "";
	var onCu2 = "";
	if(dim2 == piani){
		from2 = " matrix.vw_tree_nodes_piani_descr p ";
		on2 = " p.p_id = " + id_r_new;
		onCu2 = " p.id = cu.p_id_ref ";
		id_p_ref = id_r_new;
	}else if(dim2 == strutture){
		from2 = " matrix.vw_tree_nodes_asl_descr a ";
		on2 = " a.p_id = " + id_r_new;
		onCu2 = " a.id = cu.a_id_ref ";
		id_a_ref = id_r_new;
	}else if(dim2 == linee){
		from2 = " \"Analisi_dev\".macroarea vm ";
		on2 = " vm.parent  = " + id_r_new;
		onCu2 = " vm.id = cu.m_id_ref ";
	}else if(dim2 == date){
		on2 = " 1=1 ";
		onCu2 = " 1=1 ";
	}
	
	var join = "";
	if(dim1 != date && dim2 != date)
		join = " join ";
	
	var onStrutture = " = " + id_4_new;  
	if(dim1 == strutture) 
		onStrutture = " in (select id from matrix.vw_tree_nodes_asl_descr where p_id = "+id_a_ref+")";
	else if(dim2 == strutture)
		onStrutture = " in (select id from matrix.vw_tree_nodes_asl_descr where p_id = "+id_a_ref+")";
	else if(dim3 == strutture)
		onStrutture = " = "+id_3_new+"";
	
	var onPiani = " = " + id_4_new;  
	if(dim1 == piani) 
		onPiani = " in (select id from matrix.vw_tree_nodes_piani_descr pd where pd.p_id = "+id_p_ref+")";
	else if(dim2 == piani)
		onPiani = " in (select id from matrix.vw_tree_nodes_piani_descr pd where pd.p_id = "+id_p_ref+")";
	else if(dim3 == piani)
		onPiani = " = "+id_3_new+"";

	
	var onLinee = " = " + id_4_new;  
	if(dim1 == linee) 
		onLinee = " in (select id from \"Analisi_dev\".macroarea where parent = "+id_c_new+")";
	else if(dim2 == linee)
		onLinee = " in (select id from \"Analisi_dev\".macroarea where parent = "+id_r_new+")";
	else if(dim3 == linee)
		onLinee = " = "+id_3_new+"";
	
	var id_linea = id_4_new;
	if(dim1 == linee) 
		 id_linea = id_c_new;
	else if(dim2 == linee)
		 id_linea = id_r_new;
	else if(dim3 == linee)
		 id_linea = id_3_new;

	var on = " on ";
	if(dim1 == date || dim2 == date)
		on = " where ";
	
	var join_where = join + from2 + on + on1 + " and " + on2;
	
	
	var _join = "";
	var _where = "";
	if(dim1 == date || dim2 == date)
		_where = on1 + " and " + on2 + "and";
	else
		_join = join + from2 + on + on1 + " and " + on2;
	
	
	if ((dim1 == strutture || dim2 == strutture)&&(dim1 == piani || dim2 == piani))
		var _join2 = " t.id_node_ref = p.id and a.id = t.id_struttura ";

	else if((dim1 != piani && dim2 != piani)||(dim1 == strutture || dim2 == strutture)){
		if(piani == dim3)
			_join2 = "a.id = t.id_struttura and  t.id_node_ref = "+id_3_new;
		else	
			_join2 = "a.id = t.id_struttura and  t.id_node_ref = "+id_4_new;
	}else if((dim1 != strutture && dim2 != strutture)||(dim1 == piani || dim2 == piani)){
		if(strutture == dim3)
			_join2 = "p.id = t.id_node_ref and  t.id_struttura = "+id_3_new;
		else	
			_join2 = "p.id = t.id_node_ref and  t.id_struttura = "+id_4_new;
	}
	if((dim1 != strutture && dim2 != strutture)&&(dim1 != piani && dim2 != piani)){
		if(dim3 == piani) //dim4 == strutture
			_join2 = " t.id_node_ref = "+id_3_new+" and  t.id_struttura = "+id_4_new;
		else //dim3 == strutture && dim4 == piani
			_join2 = " t.id_node_ref = "+id_4_new+" and  t.id_struttura = "+id_3_new;
	}

	
	if(from1 == '')
		from1 = from2;
	
	var leftLinee = " left ";
	if( (dim3 == linee && id_3_new != -1) || (dim4 == linee && id_4_new != -1)) //-1 id totale macroarea
		leftLinee = " ";
		
	var leftStrurrure = " left ";
	if( (dim3 == strutture && id_3_new != 8) || (dim4 == strutture && id_4_new != 8)) //-1 id totale macroarea
		leftStrurrure = " ";
		
	var order = " 1, 2";
	/*if(dim2 == piani)
		order = " p_path_ord, 1"
	else if(dim2 == piani)
		order = " p_path_ord, 2 "& */
	
	var macrSchema = "\"Analisi_dev\"";
	//if((dim1 == linee || dim2 == linee) || id_linea != -1)
	//	macrSchema = "matrix";
				  
				  
		qd = "select cu.id_controllo as id_controllo_ufficiale, id_campione, cu.data_inizio_controllo,	\
				pp.alias||' '||pp.descrizione as tipo_ispezione_o_audit, aa.descrizione_breve as unita_operativa, cu.codice_linea,	\
				asl.descrizione_breve as asl, upper(cu.ragione_sociale) as ragione_sociale \
				from  "+ from1 +"	\n\
				"+ _join +"\n\
				left join ( SELECT g.id_motivo,			\
				g.id_unita_operativa,			\
				g.codice_linea,	\
				g.id_controllo,	\
				p.id_ref as p_id_ref,	a.id_ref as a_id_ref,	mr.id_ref as m_id_ref,	\
				g.data_inizio_controllo, s.ragione_sociale	\
				FROM ra.vw_gisa_controlli_ufficiali g		\
				" + leftLinee + " join "+macrSchema+".mvw_macroarea_ref mr on mr.codice = split_part(g.codice_linea,'-', 3) and mr.id_ref  "+ onLinee +"	\
					  left join "+macrSchema+".mvw_macroarea_ref mr2 on mr2.parent =  mr.id and mr2.codice = split_part(g.codice_linea,'-', 2) \
					  left join "+macrSchema+".mvw_macroarea_ref mr3 on mr3.parent =  mr2.id and mr3.codice = split_part(g.codice_linea,'-', 1) \
					  join \"Analisi_dev\".vw_struttura_piani_ref_no_rend_new p on p.id_gisa = g.id_motivo and p.id_ref "+ onPiani +" \
					  " + leftStrurrure + " join matrix.struttura_asl_ref_new a on  g.id_unita_operativa = a.id_gisa and a.id_ref "+ onStrutture +" \
						left join \"Analisi_dev\".vw_dbi_get_all_stabilimenti__validi s on s.riferimento_id = g.riferimento_id and s.riferimento_id_nome_tab = g.riferimento_nome_tab	\
				  ) cu on "+ onCu1 +" and "+ onCu2 +"	\
				 join matrix.struttura_piani pp on pp.id_gisa = cu.id_motivo \
				 join matrix.struttura_asl aa on aa.id_gisa = cu.id_unita_operativa and aa.anno = "+ANNO_GLOB+"	\
				 join matrix.struttura_asl asl on aa.id_asl = asl.id_asl and asl.n_livello= 1 and asl.anno = "+ANNO_GLOB+"	\
				 left join ra.mvw_get_campioni_validi camp on camp.id_controllo = cu.id_Controllo and  cu.id_motivo =  camp.id_motivo \
				  where "+_where+" "+w_date+" and cu.data_inizio_controllo >= '"+ANNO_GLOB+"-01-01' group by 1,2,3,4,5,6,7,8 order by cu.id_controllo;";
				  
		
		
	return qd;
	
	
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
