import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { OrderSummary } from '../models';
import { PizzaService } from '../pizza.service';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {

  email!: string
  orderSum: OrderSummary[] = []

  constructor(private pSvc: PizzaService, private activatedRoute: ActivatedRoute,) { }

  ngOnInit(): void {
    this.email = this.activatedRoute.snapshot.params["email"]
    this.pSvc.getOrders(this.email)
    .then(result => {
      for(var os of result){
        this.orderSum.push(os as OrderSummary)
      }
    })
  }

}
