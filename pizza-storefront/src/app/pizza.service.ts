// Implement the methods in PizzaService for Task 3

import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { firstValueFrom, map } from "rxjs";
import { Order, OrderSummary } from "./models";

// Add appropriate parameter and return type 
@Injectable()
export class PizzaService {

  constructor(private http: HttpClient) { }

  // POST /api/order
  // Add any required parameters or return type
  createOrder(o: Order) {
    console.log("process")
    return firstValueFrom(
      this.http.post<any>('/api/order', o)
    ).then(res => {
      console.info("res", res)
    })
  }

  // GET /api/order/<email>/all
  // Add any required parameters or return type
  getOrders(email: string): Promise<OrderSummary[]> {
    console.info("getall:")
    return firstValueFrom(
      this.http.get<any>(`/api/order/${email}/all`)
      .pipe(
        map(result => {
            const data = JSON.parse(result.data)
            return data.map((v: any) => v as OrderSummary)
        })
    )
    )
  }

}
