/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.base;

import javax.servlet.http.HttpServletRequest;

import org.aspcfs.modules.campioni.base.Ticket;

import com.darkhorseventures.framework.actions.ActionContext;

public class GenericControlliUfficiali {
	public static void setParameterTipiAlimenti(ActionContext context,HttpServletRequest multiPart,Object ticket,String tipologiaObject)
	{
	
		if(tipologiaObject.equalsIgnoreCase("allerte"))
		{
			org.aspcfs.modules.allerte.base.Ticket newTic = (org.aspcfs.modules.allerte.base.Ticket)ticket;
			
			String noteAlimenti="";
			if(multiPart.getParameter("alimentiAcqua")!=null)
				if(multiPart.getParameter("alimentiAcqua").equals("on")){
					newTic.setAlimentiAcqua(true);


					if(!multiPart.getParameter("noteacqua").equals("")){

						noteAlimenti=multiPart.getParameter("noteacqua");
					}


					if(!multiPart.getParameter("acque").equals("-1")){

						newTic.setTipoAcqueValue(Integer.parseInt(multiPart.getParameter("acque")));
					}

				}

			if(multiPart.getParameter("alimentinonAnomali")!=null)
				if(multiPart.getParameter("alimentinonAnomali").equals("on")){
					newTic.setAltriAlimenti(true);
					newTic.setAltrialimenti(Integer.parseInt(multiPart.getParameter("altrialimenti")));

					if(!multiPart.getParameter("descrizionenonAnimali").equals("")){

						noteAlimenti=multiPart.getParameter("descrizionenonAnimali");
					}


				}


			if(multiPart.getParameter("alimentiComposti")!=null)
				if(multiPart.getParameter("alimentiComposti").equals("on")){
					newTic.setAlimentiComposti(true);
					noteAlimenti=multiPart.getParameter("testoAlimentoComposto");			
				}


			if(multiPart.getParameter("alimentiBevande")!=null)
				if(multiPart.getParameter("alimentiBevande").equals("on")){
					newTic.setAlimentiBevande(true);


					if(!multiPart.getParameter("notebevande").equals("")){

						noteAlimenti=multiPart.getParameter("notebevande");
					}

				}

			if(multiPart.getParameter("alimentiAdditivi")!=null)
				if(multiPart.getParameter("alimentiAdditivi").equals("on")){
					newTic.setAlimentiAdditivi(true);


					if(!multiPart.getParameter("noteadditivi").equals("")){

						noteAlimenti=multiPart.getParameter("noteadditivi");
					}


				}
			if(multiPart.getParameter("materialiAlimenti")!=null)
				if(multiPart.getParameter("materialiAlimenti").equals("on")){
					newTic.setMaterialiAlimenti(true);


					if(!multiPart.getParameter("notematerialialimenti").equals("")){

						noteAlimenti=multiPart.getParameter("notematerialialimenti");
					}

				}

			if(multiPart.getParameter("mangimi")!=null)
				if(multiPart.getParameter("mangimi").equals("on")){
					newTic.setMangimi(true);


					if(!multiPart.getParameter("notealimenti").equals("")){

						noteAlimenti=multiPart.getParameter("notealimenti");

					}
					newTic.setSpecieAlimentoZootecnico(Integer.parseInt(multiPart.getParameter("lookupSpecieAlimento")));
					newTic.setTipologiaAlimentoZootecnico(Integer.parseInt(multiPart.getParameter("lookupSpecieAlimento")));

				}


			if(multiPart.getParameter("alimentiOrigineAnimale")!=null)
				if(multiPart.getParameter("alimentiOrigineAnimale").equalsIgnoreCase("on")){
					newTic.setAlimentiOrigineAnimale(true);
					String tipo =multiPart.getParameter("tipoAlimentiAnimali");
					newTic.setTipoAlimento(Integer.parseInt(tipo));
					newTic.setTipoAlimento(tipo);
					if(tipo.equals("1")){ // non trasformati

						String nonTrasf=multiPart.getParameter("alimentiOrigineAnimaleNonTrasformati");
						newTic.setAlimentiOrigineAnimaleNonTrasformati(Integer.parseInt(nonTrasf));
						newTic.setAlimentiOrigineAnimaleNonTrasformati(nonTrasf);

						if(nonTrasf.equals("8")){ //latte 

							String latte=	multiPart.getParameter("TipoSpecie_latte");
							newTic.setTipSpecie_latte(Integer.parseInt(latte));
						}else{

							if(nonTrasf.equals("9")){ //uova
								String uova=		multiPart.getParameter("TipoSpecie_uova");

								newTic.setTipSpecie_uova(Integer.parseInt(uova));

							}else{

								if(nonTrasf.equals("1") || nonTrasf.equals("2") || nonTrasf.equals("3") || nonTrasf.equals("4")){

									String specie=	multiPart.getParameter("alimentiOrigineAnimaleNonTrasformatiValori");
									newTic.setAlimentiOrigineAnimaleNonTrasformatiValori(Integer.parseInt(specie));
									newTic.setAlimentiOrigineAnimaleNonTrasformatiValori(specie);
								}
								else
								{
									if (nonTrasf.equals("6"))
									{
										String molluschi = multiPart.getParameter("molluschi");
										if(molluschi!=null){
										newTic.setAlimentiOrigineAnimaleNonTrasformatiValori(Integer.parseInt(molluschi));
										newTic.setAlimentiOrigineAnimaleNonTrasformatiValori(molluschi);
										}
									}
								}
								//specie

							}

						}

					}
					else{
						if(tipo.equals("2")){ //  trasformati
							String trasf=multiPart.getParameter("alimentiOrigineAnimaleTrasformati");
							newTic.setAlimentiOrigineAnimaleTrasformati(Integer.parseInt(trasf));
							newTic.setAlimentiOrigineAnimaleTrasformati(trasf);



						}


					}



				}
				else
					if(context.getRequest().getParameter("animalinonalimentari")!=null && context.getRequest().getParameter("animalinonalimentari").equalsIgnoreCase("on")){
						
						newTic.setAnimaliNonAlimentari(true);
						newTic.setAnimaliNonAlimentariCombo(Integer.parseInt(context.getRequest().getParameter("animalinonalimentarivalue")));
					}
			newTic.setNoteAlimenti(noteAlimenti);
		}
		else if(tipologiaObject.equalsIgnoreCase("allerteNew"))
		{
			org.aspcfs.modules.allerte_new.base.Ticket newTic = (org.aspcfs.modules.allerte_new.base.Ticket)ticket;
			
			String noteAlimenti="";
			if(multiPart.getParameter("alimentiAcqua")!=null)
				if(multiPart.getParameter("alimentiAcqua").equals("on")){
					newTic.setAlimentiAcqua(true);


					if(!multiPart.getParameter("noteacqua").equals("")){

						noteAlimenti=multiPart.getParameter("noteacqua");
					}


					if(!multiPart.getParameter("acque").equals("-1")){

						newTic.setTipoAcqueValue(Integer.parseInt(multiPart.getParameter("acque")));
					}

				}

			if(multiPart.getParameter("alimentinonAnomali")!=null)
				if(multiPart.getParameter("alimentinonAnomali").equals("on")){
					newTic.setAltriAlimenti(true);
					newTic.setAltrialimenti(Integer.parseInt(multiPart.getParameter("altrialimenti")));

					if(!multiPart.getParameter("descrizionenonAnimali").equals("")){

						noteAlimenti=multiPart.getParameter("descrizionenonAnimali");
					}


				}


			if(multiPart.getParameter("alimentiComposti")!=null)
				if(multiPart.getParameter("alimentiComposti").equals("on")){
					newTic.setAlimentiComposti(true);
					noteAlimenti=multiPart.getParameter("testoAlimentoComposto");			
				}


			if(multiPart.getParameter("alimentiBevande")!=null)
				if(multiPart.getParameter("alimentiBevande").equals("on")){
					newTic.setAlimentiBevande(true);


					if(!multiPart.getParameter("notebevande").equals("")){

						noteAlimenti=multiPart.getParameter("notebevande");
					}

				}

			if(multiPart.getParameter("alimentiAdditivi")!=null)
				if(multiPart.getParameter("alimentiAdditivi").equals("on")){
					newTic.setAlimentiAdditivi(true);


					if(!multiPart.getParameter("noteadditivi").equals("")){

						noteAlimenti=multiPart.getParameter("noteadditivi");
					}


				}
			if(multiPart.getParameter("materialiAlimenti")!=null)
				if(multiPart.getParameter("materialiAlimenti").equals("on")){
					newTic.setMaterialiAlimenti(true);


					if(!multiPart.getParameter("notematerialialimenti").equals("")){

						noteAlimenti=multiPart.getParameter("notematerialialimenti");
					}

				}

			if(multiPart.getParameter("mangimi")!=null)
				if(multiPart.getParameter("mangimi").equals("on")){
					newTic.setMangimi(true);


					if(!multiPart.getParameter("notealimenti").equals("")){

						noteAlimenti=multiPart.getParameter("notealimenti");

					}
					newTic.setSpecieAlimentoZootecnico(Integer.parseInt(multiPart.getParameter("lookupSpecieAlimento")));
					newTic.setTipologiaAlimentoZootecnico(Integer.parseInt(multiPart.getParameter("lookupSpecieAlimento")));

				}


			if(multiPart.getParameter("alimentiOrigineAnimale")!=null)
				if(multiPart.getParameter("alimentiOrigineAnimale").equalsIgnoreCase("on")){
					newTic.setAlimentiOrigineAnimale(true);
					String tipo =multiPart.getParameter("tipoAlimentiAnimali");
					newTic.setTipoAlimento(Integer.parseInt(tipo));
					newTic.setTipoAlimento(tipo);
					if(tipo.equals("1")){ // non trasformati

						String nonTrasf=multiPart.getParameter("alimentiOrigineAnimaleNonTrasformati");
						newTic.setAlimentiOrigineAnimaleNonTrasformati(Integer.parseInt(nonTrasf));
						newTic.setAlimentiOrigineAnimaleNonTrasformati(nonTrasf);

						if(nonTrasf.equals("8")){ //latte 

							String latte=	multiPart.getParameter("TipoSpecie_latte");
							newTic.setTipSpecie_latte(Integer.parseInt(latte));
						}else{

							if(nonTrasf.equals("9")){ //uova
								String uova=		multiPart.getParameter("TipoSpecie_uova");

								newTic.setTipSpecie_uova(Integer.parseInt(uova));

							}else{

								if(nonTrasf.equals("1") || nonTrasf.equals("2") || nonTrasf.equals("3") || nonTrasf.equals("4")){

									String specie=	multiPart.getParameter("alimentiOrigineAnimaleNonTrasformatiValori");
									newTic.setAlimentiOrigineAnimaleNonTrasformatiValori(Integer.parseInt(specie));
									newTic.setAlimentiOrigineAnimaleNonTrasformatiValori(specie);
								}
								else
								{
									if (nonTrasf.equals("6"))
									{
										String molluschi = multiPart.getParameter("molluschi");
										if(molluschi!=null){
										newTic.setAlimentiOrigineAnimaleNonTrasformatiValori(Integer.parseInt(molluschi));
										newTic.setAlimentiOrigineAnimaleNonTrasformatiValori(molluschi);
										}
									}
								}
								//specie

							}

						}

					}
					else{
						if(tipo.equals("2")){ //  trasformati
							String trasf=multiPart.getParameter("alimentiOrigineAnimaleTrasformati");
							newTic.setAlimentiOrigineAnimaleTrasformati(Integer.parseInt(trasf));
							newTic.setAlimentiOrigineAnimaleTrasformati(trasf);



						}


					}



				}
				else
					if(context.getRequest().getParameter("animalinonalimentari")!=null && context.getRequest().getParameter("animalinonalimentari").equalsIgnoreCase("on")){
						
						newTic.setAnimaliNonAlimentari(true);
						newTic.setAnimaliNonAlimentariCombo(Integer.parseInt(context.getRequest().getParameter("animalinonalimentarivalue")));
					}
			newTic.setNoteAlimenti(noteAlimenti);
		}
		else
		{
	org.aspcfs.modules.campioni.base.Ticket newTic = (org.aspcfs.modules.campioni.base.Ticket)ticket;
			
			String noteAlimenti="";
			if(context.getRequest().getParameter("alimentiAcqua")!=null)
				if(context.getRequest().getParameter("alimentiAcqua").equals("on")){
					newTic.setAlimentiAcqua(true);


					if(!context.getRequest().getParameter("noteacqua").equals("")){

						noteAlimenti=context.getRequest().getParameter("noteacqua");
					}


					if(!context.getRequest().getParameter("acque").equals("-1")){

						newTic.setTipoAcqueValue(Integer.parseInt(context.getRequest().getParameter("acque")));
					}

				}
			
			if(context.getRequest().getParameter("checkMatriciCanili")!=null)
				if(context.getRequest().getParameter("checkMatriciCanili").equals("on")){
					newTic.setCheckMatriciCanili(true);


					if(!context.getRequest().getParameter("noteMatriciCanili").equals("")){

						noteAlimenti=context.getRequest().getParameter("noteMatriciCanili");
					}


					if(!context.getRequest().getParameter("matriciCanili").equals("-1")){

						newTic.setTipoMatriciCanili(Integer.parseInt(context.getRequest().getParameter("matriciCanili")));
					}
					
					if(!context.getRequest().getParameter("microchipMatriciCanili").equals("")){

						newTic.setMicrochip( context.getRequest().getParameter("microchipMatriciCanili") );
					}

				}
			

			if(context.getRequest().getParameter("alimentinonAnomali")!=null)
				if(context.getRequest().getParameter("alimentinonAnomali").equals("on")){
					newTic.setAltriAlimenti(true);
					newTic.setAltrialimenti(Integer.parseInt(context.getRequest().getParameter("altrialimenti")));

					if(!context.getRequest().getParameter("descrizionenonAnimali").equals("")){

						noteAlimenti=context.getRequest().getParameter("descrizionenonAnimali");
					}


				}


			if(context.getRequest().getParameter("alimentiComposti")!=null)
				if(context.getRequest().getParameter("alimentiComposti").equals("on")){
					newTic.setAlimentiComposti(true);
					noteAlimenti=context.getRequest().getParameter("testoAlimentoComposto");			
				}


			if(context.getRequest().getParameter("alimentiBevande")!=null)
				if(context.getRequest().getParameter("alimentiBevande").equals("on")){
					newTic.setAlimentiBevande(true);


					if(!context.getRequest().getParameter("notebevande").equals("")){

						noteAlimenti=context.getRequest().getParameter("notebevande");
					}

				}

			if(context.getRequest().getParameter("alimentiAdditivi")!=null)
				if(context.getRequest().getParameter("alimentiAdditivi").equals("on")){
					newTic.setAlimentiAdditivi(true);


					if(!context.getRequest().getParameter("noteadditivi").equals("")){

						noteAlimenti=context.getRequest().getParameter("noteadditivi");
					}


				}
			if(context.getRequest().getParameter("materialiAlimenti")!=null)
				if(context.getRequest().getParameter("materialiAlimenti").equals("on")){
					newTic.setMaterialiAlimenti(true);


					if(!context.getRequest().getParameter("notematerialialimenti").equals("")){

						noteAlimenti=context.getRequest().getParameter("notematerialialimenti");
					}

				}

			if(context.getRequest().getParameter("mangimi")!=null)
				if(context.getRequest().getParameter("mangimi").equals("on")){
					newTic.setMangimi(true);


					if(!context.getRequest().getParameter("notealimenti").equals("")){

						noteAlimenti=context.getRequest().getParameter("notealimenti");

					}
					newTic.setSpecieAlimentoZootecnico(Integer.parseInt(context.getRequest().getParameter("lookupSpecieAlimento")));
					newTic.setTipologiaAlimentoZootecnico(Integer.parseInt(context.getRequest().getParameter("lookupSpecieAlimento")));

				}


			if(context.getRequest().getParameter("alimentiOrigineAnimale")!=null)
				if(context.getRequest().getParameter("alimentiOrigineAnimale").equalsIgnoreCase("on")){
					newTic.setAlimentiOrigineAnimale(true);
					String tipo =context.getRequest().getParameter("tipoAlimentiAnimali");
					newTic.setTipoAlimento(Integer.parseInt(tipo));
					newTic.setTipoAlimento(tipo);
					if(tipo.equals("1")){ // non trasformati

						String nonTrasf=context.getRequest().getParameter("alimentiOrigineAnimaleNonTrasformati");
						newTic.setAlimentiOrigineAnimaleNonTrasformati(Integer.parseInt(nonTrasf));
						newTic.setAlimentiOrigineAnimaleNonTrasformati(nonTrasf);

						if(nonTrasf.equals("8")){ //latte 

							String latte=	context.getRequest().getParameter("TipoSpecie_latte");
							newTic.setTipSpecie_latte(Integer.parseInt(latte));
						}else{

							if(nonTrasf.equals("9")){ //uova
								String uova=		context.getRequest().getParameter("TipoSpecie_uova");

								newTic.setTipSpecie_uova(Integer.parseInt(uova));

							}else{

								if(nonTrasf.equals("1") || nonTrasf.equals("2") || nonTrasf.equals("3") || nonTrasf.equals("4")){

									String specie=	context.getRequest().getParameter("alimentiOrigineAnimaleNonTrasformatiValori");
									newTic.setAlimentiOrigineAnimaleNonTrasformatiValori(Integer.parseInt(specie));
									newTic.setAlimentiOrigineAnimaleNonTrasformatiValori(specie);
								}
								else
								{
									if (nonTrasf.equals("6"))
									{
										String molluschi = context.getRequest().getParameter("molluschi");
										newTic.setAlimentiOrigineAnimaleNonTrasformatiValori(Integer.parseInt(molluschi));
										newTic.setAlimentiOrigineAnimaleNonTrasformatiValori(molluschi);
									}
								}
								//specie

							}

						}

					}
					else{
						if(tipo.equals("2")){ //  trasformati
							String trasf=context.getRequest().getParameter("alimentiOrigineAnimaleTrasformati");
							newTic.setAlimentiOrigineAnimaleTrasformati(Integer.parseInt(trasf));
							newTic.setAlimentiOrigineAnimaleTrasformati(trasf);



						}


					}



				}
				else
					if(context.getRequest().getParameter("animalinonalimentari")!=null && context.getRequest().getParameter("animalinonalimentari").equalsIgnoreCase("on")){
						
						newTic.setAnimaliNonAlimentari(true);
						newTic.setAnimaliNonAlimentariCombo(Integer.parseInt(context.getRequest().getParameter("animalinonalimentarivalue")));
					}
			
			
		}
	
		
	}

}
