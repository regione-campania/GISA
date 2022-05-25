/*************************************************************************/
/*************************************************************************/
/********** SCHEDA VALUTAZIONE BENESSERE ANIMALE (18/11/2015) ************/
/*************************************************************************/
/*************************************************************************/

ALTER TABLE public.iuv_campioni_valutazione_comportamentale ADD adeguatezza_box boolean;
ALTER TABLE public.iuv_campioni_valutazione_comportamentale ADD adeguatezza_sgambamento boolean;
ALTER TABLE public.iuv_campioni_valutazione_comportamentale ADD segni_di_malessere  boolean;
ALTER TABLE public.iuv_campioni_valutazione_comportamentale ADD atti_ripetitivi  boolean;
ALTER TABLE public.iuv_campioni_valutazione_comportamentale ADD atti_ripetitivi_campione  boolean;
ALTER TABLE public.iuv_campioni_valutazione_comportamentale ADD aggressivita  boolean;
ALTER TABLE public.iuv_campioni_valutazione_comportamentale ADD paura character varying(20);
ALTER TABLE public.iuv_campioni_valutazione_comportamentale ADD socievolezza character varying(20);
ALTER TABLE public.iuv_campioni_valutazione_comportamentale ADD guinzaglio  boolean;
ALTER TABLE public.iuv_campioni_valutazione_comportamentale ADD manipolazioni  boolean;
ALTER TABLE public.iuv_campioni_valutazione_comportamentale ADD prelievo_ematico  boolean;
ALTER TABLE public.iuv_campioni_valutazione_comportamentale ADD bcs character varying(20);
ALTER TABLE public.iuv_campioni_valutazione_comportamentale ADD pulizia boolean;
ALTER TABLE public.iuv_campioni_valutazione_comportamentale ADD zoppia boolean;
ALTER TABLE public.iuv_campioni_valutazione_comportamentale ADD tosse boolean;