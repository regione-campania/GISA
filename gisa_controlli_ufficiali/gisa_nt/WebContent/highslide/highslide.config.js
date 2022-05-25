/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/**
*	Site-specific configuration settings for Highslide JS
*/
hs.outlineType = 'rounded-white';
hs.wrapperClassName = 'draggable-header';
hs.preserveContent = false;
hs.graphicsDir = 'highslide/graphics/';
hs.outlineType = 'custom';
hs.captionEval = 'this.a.title';
//hs.registerOverlay({
//	html: '<div class="closebutton" onclick="return hs.close(this)" title="Chiudi"></div>',
//	position: 'top right',
//	useOnHtml: true,
//	fade: 2 // fading the semi-transparent overlay looks bad in IE
//});


// Italian language strings
hs.lang = {
	cssDirection: 'ltr',
	loadingText: 'Caricamento in corso',
	loadingTitle: 'Fare clic per annullare',
	focusTitle: 'Fare clic per portare in avanti',
	fullExpandTitle: 'Visualizza dimensioni originali',
	creditsText: 'Powered by <i>Highslide JS</i>',
	creditsTitle: 'Vai al sito Web di Highslide JS',
	previousText: 'Precedente',
	nextText: 'Successiva',
	moveText: 'Sposta',
	closeText: 'Chiudi',
	closeTitle: 'Chiudi (Esc)',
	resizeTitle: 'Ridimensiona',
	playText: 'Avvia',
	playTitle: 'Avvia slideshow (barra spaziatrice)',
	pauseText: 'Pausa',
	pauseTitle: 'Pausa slideshow (barra spaziatrice)',
	previousTitle: 'Precedente (freccia sinistra)',
	nextTitle: 'Successiva (freccia destra)',
	moveTitle: 'Sposta',
	fullExpandText: 'Dimensione massima',
	number: 'Immagine %1 di %2',
	restoreTitle: 'Fare clic per chiudere l\'immagine, trascina per spostare. Frecce andare avanti e indietro.'
};
