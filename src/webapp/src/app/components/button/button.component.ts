import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-button',
  templateUrl: './button.component.html',
  styleUrls: ['./button.component.sass']
})
export class ButtonComponent implements OnInit {

  @Input() text: string;
  @Output() btnClick;

  constructor() {
    this.btnClick = new EventEmitter();
    this.text = "";
  }

  ngOnInit() {
  }

  onClick() {
    this.btnClick.emit();
  }

}
