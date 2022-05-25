/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
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
