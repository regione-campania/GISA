
-- INSERT
select 
'insert into modello_pnaa_values(enteredby, entered, id_campione, numero_scheda, id_dpa, id_metodo_campionamento, id_luogo_prelievo, codice_luogo_prelievo, trattamento_mangime, 
ragione_sociale_ditta_produttrice, indirizzo_ditta_produttrice, id_metodo_produzione, nome_commerciale_mangime, responsabile_etichettatura, indirizzo_responsabile_etichettatura, paese_produzione,
data_scadenza, num_lotto, dimensione_lotto, ingredienti) values(6567,now(),'||v.id_campione||','''||coalesce(numscheda.valore_campione,'')||''', 
'||coalesce((select code from lookup_dpa where short_description = tipoalimento.valore_campione),-1)||', 
'||coalesce((select code from lookup_metodo_campionamento where short_description = metodocampionamento.valore_campione),-1)||', '||coalesce(luogoprelievo.valore_campione::int, -1)||', 
'''||coalesce(replace(codiceluogoprelievo.valore_campione,'''', ''''''),'')||''',
'''||coalesce(replace(trattamentomangime.valore_campione, '''', ''''''),'')||''', '''||coalesce(replace(ragionesocialedittaproduttrice.valore_campione,'''', ''''''),'')||''', '''||coalesce(replace(indirizzodittaproduttrice.valore_campione,'''', ''''''),'')||''',
'||coalesce((select code from lookup_circuito_pna where description ilike metodoproduzione.valore_campione),-1)||', 
'''||coalesce(replace(nomecommercialemangime.valore_campione,'''', ''''''),'')||''', '''||coalesce(replace(ragionesocialeetichettatura.valore_campione,'''', ''''''),'')||''',  '''||coalesce(replace(indirizzoetichettatura.valore_campione,'''', ''''''),'')||''', '''||coalesce(paeseproduzione.valore_campione,'')||''', 
'''||coalesce(datascadenza.valore_campione,'')||''', '''||coalesce(replace(numerolotto.valore_campione,'''', ''''''),'')||''',
'''||coalesce(replace(dimensionelotto.valore_campione,'''', ''''''),'')||''','''||coalesce(replace(ingredienti.valore_campione,'''', ''''''),'')||''');',
v.id_campione, 
numscheda.valore_campione,
(select code from lookup_dpa where short_description = tipoalimento.valore_campione),
(select code from lookup_metodo_campionamento where short_description = metodocampionamento.valore_campione),
luogoprelievo.valore_campione::int,
codiceluogoprelievo.valore_campione,
'', --matricecampione.valore_campione,
trattamentomangime.valore_campione,
ragionesocialedittaproduttrice.valore_campione,
indirizzodittaproduttrice.valore_campione,
(select code from lookup_circuito_pna where description ilike metodoproduzione.valore_campione),
nomecommercialemangime.valore_campione,
ragionesocialeetichettatura.valore_campione,
indirizzoetichettatura.valore_campione,
paeseproduzione.valore_campione,
datascadenza.valore_campione,
numerolotto.valore_campione,
dimensionelotto.valore_campione,
ingredienti.valore_campione

from (select distinct id_campione from campioni_fields_value where enabled and id_campioni_html_fields = 54 and (valore_campione <> '' or valore_campione is not null)) v 
left join ticket t on t.ticketid = v.id_campione and t.tipologia = 2 
left join campioni_fields_value numscheda on numscheda.id_campione = t.ticketid and numscheda.id_campioni_html_fields = 48 and numscheda.enabled
left join campioni_fields_value tipoalimento on tipoalimento.id_campione = t.ticketid and tipoalimento.id_campioni_html_fields = 54 and tipoalimento.enabled
left join campioni_fields_value metodocampionamento on metodocampionamento.id_campione = t.ticketid and metodocampionamento.id_campioni_html_fields = 50 and metodocampionamento.enabled
left join campioni_fields_value luogoprelievo on luogoprelievo.id_campione = t.ticketid and luogoprelievo.id_campioni_html_fields = 51 and luogoprelievo.enabled
left join campioni_fields_value codiceluogoprelievo on codiceluogoprelievo.id_campione = t.ticketid and codiceluogoprelievo.id_campioni_html_fields = 5 and codiceluogoprelievo.enabled
left join campioni_fields_value matricecampione on matricecampione.id_campione = t.ticketid and matricecampione.id_campioni_html_fields = 600 and matricecampione.enabled
left join campioni_fields_value trattamentomangime on trattamentomangime.id_campione = t.ticketid and trattamentomangime.id_campioni_html_fields = 6 and trattamentomangime.enabled
left join campioni_fields_value ragionesocialedittaproduttrice on ragionesocialedittaproduttrice.id_campione = t.ticketid and ragionesocialedittaproduttrice.id_campioni_html_fields = 7 and ragionesocialedittaproduttrice.enabled
left join campioni_fields_value indirizzodittaproduttrice on indirizzodittaproduttrice.id_campione = t.ticketid and indirizzodittaproduttrice.id_campioni_html_fields = 8 and indirizzodittaproduttrice.enabled
left join campioni_fields_value metodoproduzione on metodoproduzione.id_campione = t.ticketid and metodoproduzione.id_campioni_html_fields = 55 and metodoproduzione.enabled
left join campioni_fields_value nomecommercialemangime on nomecommercialemangime.id_campione = t.ticketid and nomecommercialemangime.id_campioni_html_fields = 9 and nomecommercialemangime.enabled
left join campioni_fields_value ragionesocialeetichettatura on ragionesocialeetichettatura.id_campione = t.ticketid and ragionesocialeetichettatura.id_campioni_html_fields = 601 and ragionesocialeetichettatura.enabled
left join campioni_fields_value indirizzoetichettatura on indirizzoetichettatura.id_campione = t.ticketid and indirizzoetichettatura.id_campioni_html_fields = 602 and indirizzoetichettatura.enabled
left join campioni_fields_value paeseproduzione on paeseproduzione.id_campione = t.ticketid and paeseproduzione.id_campioni_html_fields = 16 and paeseproduzione.enabled
left join campioni_fields_value datascadenza on datascadenza.id_campione = t.ticketid and datascadenza.id_campioni_html_fields = 53 and datascadenza.enabled
left join campioni_fields_value numerolotto on numerolotto.id_campione = t.ticketid and numerolotto.id_campioni_html_fields = 603 and numerolotto.enabled
left join campioni_fields_value dimensionelotto on dimensionelotto.id_campione = t.ticketid and dimensionelotto.id_campioni_html_fields = 45 and dimensionelotto.enabled
left join campioni_fields_value ingredienti on ingredienti.id_campione = t.ticketid and ingredienti.id_campioni_html_fields = 47 and ingredienti.enabled

where t.trashed_date is null; --and (tipoalimento.valore_campione <> '' or tipoalimento.valore_campione is not null);

-- UPDATE


select concat('update modello_pnaa_values set lista_specie_vegetale_dichiarata = ''', string_agg(distinct specievegetale.valore_campione, ',') , ',',  ''', lista_specie_alimento_destinato = ''', string_agg(distinct speciealimento.valore_campione, ',') , ',', ''', lista_stato_prodotto_prelievo = ''' , string_agg(distinct statoprodotto.valore_campione, ',') , ',',  ''' where id_campione = '|| v.id_campione ,';')

from (select distinct id_campione from campioni_fields_value where enabled and id_campioni_html_fields = 54 and valore_campione <> '') v 
left join ticket t on t.ticketid = v.id_campione and t.tipologia = 2 
left join campioni_fields_value specievegetale on specievegetale.id_campione = t.ticketid and specievegetale.id_campioni_html_fields = 604 and specievegetale.enabled
left join campioni_fields_value speciealimento on speciealimento.id_campione = t.ticketid and speciealimento.id_campioni_html_fields = 3 and speciealimento.enabled
left join campioni_fields_value statoprodotto on statoprodotto.id_campione = t.ticketid and statoprodotto.id_campioni_html_fields = 4 and statoprodotto.enabled

where t.trashed_date is null
group by v.id_campione

update modello_pnaa_values set 
lista_specie_vegetale_dichiarata = replace(lista_specie_vegetale_dichiarata, 'm2,', ''), 
lista_specie_alimento_destinato = replace(lista_specie_alimento_destinato, 'm2,', ''), 
lista_stato_prodotto_prelievo = replace(lista_stato_prodotto_prelievo, 'm2,', '');

update modello_pnaa_values set 
lista_specie_vegetale_dichiarata = replace(lista_specie_vegetale_dichiarata, 'm1,', '');

update modello_pnaa_values set 
lista_specie_vegetale_dichiarata = replace(lista_specie_vegetale_dichiarata, 'm3,', '');

update modello_pnaa_values set 
lista_specie_vegetale_dichiarata = replace(lista_specie_vegetale_dichiarata, 'm6,', '');

update modello_pnaa_values set 
lista_specie_vegetale_dichiarata = replace(lista_specie_vegetale_dichiarata, 'm4,', '');

update modello_pnaa_values set 
lista_specie_vegetale_dichiarata = replace(lista_specie_vegetale_dichiarata, 'm5,', '');