select * from opu_indirizzo where comune = 5279 and substring(cap from 1 for 2) <> '80'
order by via

update opu_indirizzo set cap = '80122', 
note_internal_use_only = concat(note_internal_use_only, '.  ' || now() || ' - Flusso 162: aggiornato cap da N.D.  a 80122') 
where comune = 5279 and substring(cap from 1 for 2) <> '80' and via ilike '%vittorio emanuele%';
