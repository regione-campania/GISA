update comuni1 set cap = '80100' where id = 5279;
ALTER table opu_indirizzo add column entered timestamp default now(); 
update opu_indirizzo set entered = null;
ALTER TABLE public.opu_indirizzo ADD CONSTRAINT check_capna CHECK (btrim(cap::text) <> '80100'::text or entered  is null);