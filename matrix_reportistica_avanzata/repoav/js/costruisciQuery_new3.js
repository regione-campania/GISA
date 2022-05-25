/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function costruisciQuery_new3(){
	
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

	var distinct_asl = "g.id_unita_operativa as id_unita_operativa,";
	if(dim1[0][ind1] == 'ASL' || dim2[0][ind2] == 'ASL')
		distinct_asl = "case when g.fonte not in ('camp',  'isp semp', 'a1', 'd1', 'audit') then a.id_asl else g.id_unita_operativa end as id_unita_operativa,"

	
	if(w_date == null)
		w_date = "1=1";

		var from1 = "";
	var on1 = "";
	var onCu1 = "";
	var ha_campioni = "";
	var case_campioni1= "";
	if(dim1 == piani){
		from1 = " matrix.vw_tree_nodes_piani_descr p ";
		on1 = " p.id not in (select id from matrix.struttura_piani where (id_gisa, anno) in (select r.id_piano, anno  from \"Analisi_dev\".piani_no_rend r)) and p.p_id = " + id_c_new;
		onCu1 = " p.id = cu.p_id_ref ";
		ha_campioni = ", ha_campioni";
		case_campioni1 = "||case when ha_campioni then ' [Campioni]' else '' end";
	}else if(dim1 == strutture){
		from1 = " matrix.vw_tree_nodes_asl_descr a ";
		on1 = " a.p_id = " + id_c_new;
		onCu1 = " a.id = cu.a_id_ref ";
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

	var case_campioni2 = "";
	if(dim2 == piani){
		from2 = " matrix.vw_tree_nodes_piani_descr p ";
		on2 = " p.id not in (select id from matrix.struttura_piani where (id_gisa, anno) in (select r.id_piano, anno)  from \"Analisi_dev\".piani_no_rend r)) and p.p_id = " + id_r_new ;
		onCu2 = " p.id = cu.p_id_ref ";
		ha_campioni = ", ha_campioni";
		case_campioni2 = "||case when ha_campioni then ' [Campioni]' else '' end";
	}else if(dim2 == strutture){
		from2 = " matrix.vw_tree_nodes_asl_descr a ";
		on2 = " a.p_id = " + id_r_new;
		onCu2 = " a.id = cu.a_id_ref ";
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
		onStrutture = " in (select id from matrix.vw_tree_nodes_asl_descr where p_id = "+id_c_new+")";
	else if(dim2 == strutture)
		onStrutture = " in (select id from matrix.vw_tree_nodes_asl_descr where p_id = "+id_r_new+")";
	else if(dim3 == strutture)
		onStrutture = " = "+id_3_new+"";
	
	var onPiani = " = " + id_4_new;  
	if(dim1 == piani) 
		onPiani = " in (select id from matrix.vw_tree_nodes_piani_descr pd where pd.p_id = "+id_c_new+")";
	else if(dim2 == piani)
		onPiani = " in (select id from matrix.vw_tree_nodes_piani_descr pd where pd.p_id = "+id_r_new+")";
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
		
	var order = " 1, 3";
	var group = "";
	var group2 = "";
	var ordinamento = "";
	if(dim2 == piani){
		order = " ordinamento, 3";
		group = ", 8";
		group2 = ", 5";
		ordinamento = "ordinamento, ";
	}else if(dim1 == piani){
		order = " ordinamento, 1 ";
		group = ", 8";
		group2 = ", 5";
		ordinamento = "ordinamento, ";
	}
	
	//var macrSchema = "\"Analisi_dev\"";
	//if((dim1 == linee || dim2 == linee) || id_linea != -1)
	//	macrSchema = "matrix";
	
	w_piani_no_rend = "";
	if(dim1 == piani || dim2 == piani)
		w_piani_no_rend = "and p.id not in (select r.id_piano from \"Analisi_dev\".piano_no_rend r)";

	var whereNorme = "";
	if(id_norma != "null"){
		whereNorme = " and g.id_norma = " + id_norma;
	}


		var q = "select riga"+case_campioni2+" as riga ,id_r, colonna"+case_campioni1+" as colonna, id_c, "+ordinamento+" sum(coalesce(valore,0)) as valore, sum(coalesce(target,0)) as target, sum(coalesce(target_ups,0)) as target_ups, sum(coalesce(target_uba,0)) as target_uba, sum(eseguiti_ups) as eseguiti_ups, sum(eseguiti_uba) as eseguiti_uba  "+ha_campioni+" from ( 	 	\n\
			select replace("+riga+", '.', '') as riga, "+id_r+" as id_r, replace("+colonna+", '.', '') as colonna, "+id_c+" as id_c, "+ordinamento+" coalesce(t.sum, 0) as target, coalesce(target_ups,0) as target_ups , coalesce(t.target_uba,0) as target_uba, sum(f_ups*eseguiti) as eseguiti_ups, sum(f_uba*eseguiti) as eseguiti_uba,  sum(eseguiti) as valore  "+ha_campioni+"  \n\
			from  "+ from1 +"	\n\
			"+ _join +"\n\
			left join matrix.vw_struttura_piano_target_tree_no_rend t \n\
			on "+_join2+" \n\
				left join ( \n\
					select cu2.id_motivo,	\n\
					cu2.a_id_ref as id_unita_operativa,	\n\
					date_part('year'::text, data_inizio_controllo::timestamp without time zone)::integer AS data_inizio_controllo_y,	\n\
					date_part('month'::text, data_inizio_controllo::timestamp without time zone)::integer AS data_inizio_controllo_m,	\n\
					'Trim '::text || btrim(to_char(date_part('quarter'::text,  data_inizio_controllo::timestamp without time zone)::integer, 'RN'::text)) AS data_inizio_controllo_q,	\n\
					date_part('day'::text, data_inizio_controllo::timestamp without time zone)::integer AS data_inizio_controllo_d,	\n\
					to_char(to_timestamp(date_part('month'::text, data_inizio_controllo::timestamp without time zone)::text, 'MM'::text), 'TMMonth'::text) AS data_inizio_controllo_m_text,	\n\
					sum(eseguiti) as eseguiti, \n\
					f_ups,  \n\
					f_uba, \n\
					p_id_ref, \n\
					a_id_ref, \n\
					m_id_ref, \n\
					data_inizio_controllo \n\
				from (	\n\
				SELECT distinct g.id_controllo, g.id_motivo,	\n\
				"+distinct_asl+"	\n\
				date_part('year'::text, data_inizio_controllo::timestamp without time zone)::integer AS data_inizio_controllo_y,  	\n\
				date_part('month'::text, data_inizio_controllo::timestamp without time zone)::integer AS data_inizio_controllo_m, 	\n\
				'Trim '::text || btrim(to_char(date_part('quarter'::text,  data_inizio_controllo::timestamp without time zone)::integer, 'RN'::text)) AS data_inizio_controllo_q,	\n\
				date_part('day'::text, data_inizio_controllo::timestamp without time zone)::integer AS data_inizio_controllo_d,		\n\
				to_char(to_timestamp(date_part('month'::text, data_inizio_controllo::timestamp without time zone)::text, 'MM'::text), 'TMMonth'::text) AS data_inizio_controllo_m_text,	\n\
				case when g.fonte = 'sorveglianze' then 1 else eseguiti end as eseguiti, 	\n\
				coalesce(fattore_ups,0) as f_ups, 	\n\
				coalesce(fattore_uba,0) as f_uba,	\n\
					p.id_ref as p_id_ref,	\n\
					a.id_ref as a_id_ref,	\n\
					mr.id_ref as m_id_ref,	\n\
					data_inizio_controllo	\n\
					 FROM ra.vw_gisa_controlli_ufficiali g	\n\
					" + leftLinee + " join \"Analisi_dev\".mvw_macroarea_ref mr on mr.codice = split_part(g.codice_linea,'-', 3) and mr.id_ref  "+ onLinee +"	\n\
					left join \"Analisi_dev\".mvw_macroarea_ref mr2 on mr2.id_gisa =  mr.parent and mr2.codice = split_part(g.codice_linea,'-', 2) \n\
					left join \"Analisi_dev\".mvw_macroarea_ref mr3 on mr3.id_gisa =  mr2.parent and mr3.codice = split_part(g.codice_linea,'-', 1) \n\
					join \"Analisi_dev\".vw_struttura_piani_ref_no_rend_new p on p.id_gisa = g.id_motivo and p.id_ref "+ onPiani +"			 \n\
					" + leftStrurrure + " join matrix.struttura_asl_ref_new a on  g.id_unita_operativa = a.id_gisa and a.id_ref "+ onStrutture +"\n\
					where data_inizio_controllo >= '"+ANNO_GLOB+"-01-01' "+ whereNorme +"\
				) cu2 GROUP BY 1,2,3,4,5,6,7,9,10,11,12,13,14	\n\
				) cu on "+ onCu1 +" and "+ onCu2 +"	\n\
				where "+_where+" "+w_date+" \n\
				group by 1,2,3,4,5,6,7"+group+""+ha_campioni+" \n\
		) a where colonna is not null and riga is not null group by 1,2,3,4 "+group2+""+ha_campioni+" 	order by "+ order +" ";
		
		
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
	console.log("getStruttureRoot.php?anno="+_anno+"&id="+_id);
	$.ajax({
		url: "getStruttureRoot.php?anno="+_anno+"&id="+_id,
		async: false,
		success: function(res){
			ret = res;
		}
	})
	return ret;
}


function getPianiRoot(_anno){
	console.log("getPianiRoot.php?anno="+_anno);
	$.ajax({
		url: "getPianiRoot.php?anno="+_anno,
		async: false,
		success: function(res){
			ret = res;
		}
	})
	return ret;
}
