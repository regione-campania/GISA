import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { AbstractForm } from '../abstract-form';

@Component({
  selector: 'form-cantiere',
  templateUrl: './form-cantiere.component.html',
  styleUrls: ['./form-cantiere.component.scss']
})
export class FormCantiereComponent extends AbstractForm implements OnInit {
  
  name = 'formCantiere'

  constructor(private fb: FormBuilder) { 
    super()
  }

  ngOnInit(): void {
    console.log(this.data)
    this.form = this.fb.group({
      cantiere: this.fb.group(this.data.cantiere)
    })
    console.log(this.form)
  }

}
