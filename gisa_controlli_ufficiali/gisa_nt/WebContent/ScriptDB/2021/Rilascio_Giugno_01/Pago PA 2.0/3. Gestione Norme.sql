alter table lookup_norme add column codice_tariffa text;
alter table lookup_norme add column competenza_uod boolean;

update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.L.vo 4/8/08, n. 148', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.L.vo 2/2/01, n. 31', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.P.R. 24/5/88, n. 236', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('DECRETO LEGISLATIVO 15 febbraio 2016, n. 28', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('LEGGE   REGIONALE   N.  8  DEL  29 LUGLIO 2008', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.L.vo 8/10/11, n. 176', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.L.vo 25 gennaio 1992, n. 107', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('L. 16/8/62, n. 1354', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('R.D.L. 15/10/1925, n. 2033', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('LEGGE 12 dicembre 2016, n. 238', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('DECRETO-LEGGE 30 ottobre 1952 , n. 1322', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('DECRETO LEGISLATIVO 23 febbraio 2018 , n. 20', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('LEGGE REGIONALE N. 24 DEL 12-08-199', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.M. 9/4/09, n. 82', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('DPR 7 aprile 1999 , n. 128', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.L.VO 21 maggio 2004 , n. 169', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.L.VO 14 febbraio 2003 , n. 31', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.M. 7 ottobre 1998 , n. 519', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.M. 9 luglio 2012', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.M. 16/1/02', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.M. 18 giugno 2010', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.M. 23 febbraio 2006', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('DPR 19 gennaio 1998 , n. 131', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('DPR 20 marzo 2002 , n. 57', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('Reg CE n.
2081/92', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('L.R. 29/3/06 N. 7', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('L. 28/7/16, n. 154', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('REG (CE) N. 21/2004', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('REG (CE) N. 1760/2000', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('REG (CE) N. 1505/2006', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.P.R. 30/4/96, n. 317', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.L.vo 26/10/10, n. 200', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('D.L.vo 30 dicembre 1992, n. 529', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.M. 2 marzo 2018', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('REG.  (CE) N. 1099/2009', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('Reg UE 30.1.20, n. 691', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('Reg UE 28.4.20, n. 990', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('Reg UE 1.12.19, n. 688', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('Reg. (CE) n. 1/2005', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('DECRETO LEGISLATIVO 21 marzo 2005, n. 73', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.L.vo 26/3/2001, n. 146', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.L.vo 29/7/03, n. 267', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.L.vo 27/9/10 , n. 181', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.L.vo 7/7/11 , n. 122', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.L.vo 7/7/11 , n. 126', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('L. 14/10/85, n.623', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.L. 13.9.12, n. 158', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('LEGGE 30 ottobre 2014, n. 161', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('DECRETO LEGISLATIVO 21 maggio 2004, n. 151', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('L. 3/4/61, n. 286', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('DECRETO DEL PRESIDENTE DELLA REPUBBLICA 19 maggio 1958 , n. 719', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('L. 23/12/56, n. 1526', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('LEGGE 4 aprile 1964 , n. 171', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('Reg 543/2008', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('DECRETO MINISTERIALE 16 gennaio 2015', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('REG (CE) N. 1760/2000', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('Reg. (CE) n. 1825/00', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.L.vo 22/5/99, n. 196', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.L.vo 22/5/99, n. 196', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('L. 20 novembre 2017, n. 167', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('D.L.vo 12 giugno 2003, n. 178', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('L.R. 21.4.2020, n. 7', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('D.L.vo 31/03/1998, N. 114', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('Reg.  (CE)  n.  648/2004', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('L. 3/2/11 , n. 4', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('D.L.vo 15/9/2017, n. 145', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('REG (UE) N.  1169/2011', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('L. 3.8.04, n. 204', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('D.Lgs. 16/02/1993 n?? 77', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('D.M. 9 dicembre 2016', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('D.M. 16 novembre 2017', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.M. 17 luglio 2013', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('D.M. 26 luglio 2017', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('D.M. 27 marzo 2002', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('Reg CE n.   1924/2006', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.L.vo 6/4/2006, n. 193', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.L.vo 17.3.95, n. 110', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.L.vo 16.3.2006, n. 158', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('Reg CE 9.12.96, n. 338', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('D.M. 24.12.12', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('L. 11.2.92, n. 157', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.L.vo 14/8/12, n. 150', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('Reg. (CE) n. 1107/2009', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('Reg. (CE) n. 547/2011', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('DPR 23.4.01, n. 290', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.L.vo 17.3.95, n. 194', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('R.D.L. 15 ottobre 1925, n. 2033', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('R.D.L. 15/10/1925, n. 2033', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('L. 23/8/93, n. 352', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('DPR 14/7/95, n. 376', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('L.R. 24/7/07 N. 8', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.L. 13/9/2012, n. 158', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('L. 4/11/10, n. 201', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2003', competenza_uod = true where replace(description, ' ', '') ilike replace('L. 14.8.91, n. 281', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2003', competenza_uod = true where replace(description, ' ', '') ilike replace('L.R. 11.4.19, n. 3', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('art. 672 Codice Penale', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('D.L.vo 2.2.21, n. 24', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.L.vo 2.2.21, n. 23', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.L.vo 3.12.14, n. 199', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.L.vo 12.11.96, n. 633', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.L.VO 22 maggio 1999, n. 196', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('L. 03/5/1989, n. 169', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.L.vo 8/10/11 , n. 175', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('L. 11/4/74, n. 138', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.L. 13/9/2012, n. 158', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('L. 21/5/2019,  n.  44', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('Reg CE n. 1099/2009', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('Art. 264 R.D. 27/7/34, n. 1265', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('L. 9/6/64, n. 615', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('L. 2.6.88, n. 218', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.L.vo 19.8.05, n. 193', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.L.vo 25.1.10, n. 9', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.L.vo 22/5/99, n. 196', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.P.R. 8.2.54, n. 320', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.L.vo 20.2.04 n. 54', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.L.vo 3.12.14, n. 199', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('L. 4.6.10, n. 96', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('L. 15.2.63, n. 281', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.L.vo 24/2/97, n. 45', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.L.vo 13/4/99, n. 123', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('REG. CE n. 183/2005', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.L.vo 3.3.93, n. 90', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('Reg. CE 13.7.09, n. 767', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('L. 4/11/51, n. 1316', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('DPR 23/8/82, n. 777', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.L.vo 10/2/2017, n. 29', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('Reg.  (CE)  n.  1935/2004', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('Reg.  (CE)  n. 1895/2005', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('Reg.  (CE)  n. 2023/2006', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('Reg.  (CE)  n. 282/2008', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('Reg.  (CE)  n. 50/2009', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('Reg.  (CE)  n. 10/2011', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('D.L.vo 21/5/04, n.179', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('L. 24.12.04 n. 313', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('L.R. 29.3.06, n. 7', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('L. 28.7.16, n. 154', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('L. 11/4/2014, N. 116', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('D.L.vo 14.11.16, n. 227', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('Reg. (CE) n. 1829/03', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('Reg CE n. 1830/03', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('D.L.vo 8.7.03 , n. 224', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('R.D.L. 15/10/1925, n. 2033', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('L. 3/8/98, n. 313', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('Reg CE 29/12', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('Reg CE 2568/91', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('LEGGE 14 gennaio 2013, n. 9', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('L. 04/07/1967, n?? 580', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('DPR 9/2/01, n.187', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.P.R. 30/11/98, n. 502', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('L.R. 1/2/05, n. 2', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.L. 13/9/2012, n. 158', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('D.L.vo 9.1.12, n. 4', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('D.L.vo 20/2/04, n.50', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('L. 3/8/2004, n. 204', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('L. 28/7/2016, n. 154', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('R.D.L. 15/10/1925, n. 2033', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('Reg CE n.
1580/2007', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.l.vo 11/5/2018, n. 52', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = false where replace(description, ' ', '') ilike replace('D.L.vo 30.12.92, n. 529', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('L. 18/3/58, n. 325', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('Reg CE  n.  648/2004', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('DPR 327/80', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('Reg CE 178/02', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('Reg CE 852/04', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('Reg CE 853/04', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('Legge 116 del 11/08/14', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.L.vo 6.11.07, n. 193', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.L.vo 4.2.93, n. 64', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('L. 25/08/1991, N. 287', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('Reg CE 1069/09', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('Reg UE n. 142/2011', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('L.R. 22/11/2010, N. 14', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.Lvo 4.3.14, n. 26', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('L. 2.5.77, n. 264', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('DPR 29.5.79, n. 404', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('Reg CE n. 1/2005', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('Reg. (CE) N. 589/08', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '2040', competenza_uod = true where replace(description, ' ', '') ilike replace('D.L.vo 4/2/93, n. 65', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('R.D. 27.7.34, n. 1265 T.U.LL.SS', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('0', ' ', '') and enabled;
update lookup_norme set codice_tariffa = '0', competenza_uod = false where replace(description, ' ', '') ilike replace('D.L.vo 20/2/04, n. 51
', ' ', '') and enabled;

update lookup_norme set codice_tariffa = null where codice_tariffa = '0';


CREATE OR REPLACE FUNCTION public.is_previsto_pagopa(
    _id_sanzione integer)
  RETURNS boolean AS
$BODY$
DECLARE
previstoPagoPa boolean;
BEGIN
previstoPagoPa := false;
	select l.competenza_uod into previstoPagoPa from
norme_violate_sanzioni n 
join lookup_norme l on l.code = n.id_norma
where n.idticket = _id_sanzione;

	return previstoPagoPa;

 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.is_previsto_pagopa(integer)
  OWNER TO postgres;
