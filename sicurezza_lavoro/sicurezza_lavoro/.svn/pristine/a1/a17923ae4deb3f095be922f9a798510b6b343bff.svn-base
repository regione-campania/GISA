import { Component, OnInit } from '@angular/core';
import { IspezioniService } from '../ispezioni.service';

@Component({
  selector: 'app-lista-macchinari',
  templateUrl: './lista-macchinari.component.html',
  styleUrls: ['./lista-macchinari.component.scss']
})
export class ListaMacchinariComponent implements OnInit {

  macchine: any

  constructor(private is: IspezioniService) { 
  }

  ngOnInit(): void {
    this.is.getMacchine().subscribe(res => {
      this.macchine = res
    })
  }

}
