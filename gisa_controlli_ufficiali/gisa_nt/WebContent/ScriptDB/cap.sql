-- View: public.cap

-- DROP VIEW public.cap;

CREATE OR REPLACE VIEW public.cap AS 
 SELECT tab_cap.dugt_cap AS toponimo,
    tab_cap.topo_cap AS indirizzo,
    tab_cap.nciv_cap AS numeri,
    tab_cap.capi_cap AS cap
   FROM tab_cap;

ALTER TABLE public.cap
  OWNER TO postgres;
