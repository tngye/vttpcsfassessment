import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Order } from '../models';
import { PizzaService } from '../pizza.service';

const SIZES: string[] = [
  "Personal - 6 inches",
  "Regular - 9 inches",
  "Large - 12 inches",
  "Extra Large - 15 inches"
]

const PizzaToppings: string[] = [
    'chicken', 'seafood', 'beef', 'vegetables',
    'cheese', 'arugula', 'pineapple'
]

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  form!: FormGroup
  toppings!: FormArray
  // email!: FormControl
  emailstr!: string

  pizzaSize = SIZES[0]

  constructor(private fb: FormBuilder, private router: Router, private pSvc: PizzaService) {}

  ngOnInit(): void {
    this.form = this.createForm()
    // this.email = this.fb.control('', [Validators.email, Validators.required])
  }

  updateSize(size: string) {
    this.pizzaSize = SIZES[parseInt(size)]
  }

  onCheckboxChange(e: any) {
    const toppings: FormArray = this.form.get('toppings') as FormArray;
    if (e.target.checked) {
      toppings.push(new FormControl(e.target.value));
    } else {
      let i: number = 0;
      toppings.controls.forEach((item: any) => {
        if (item.value == e.target.value) {
          toppings.removeAt(i);
          return;
        }
        i++;
      });
    }
    console.log("toppings: ", toppings)
  }

  createForm(){
    this.toppings = this.fb.array([], [Validators.required, Validators.min(1)])
    return this.fb.group({
      name: this.fb.control<string>('', [Validators.required]),
      email: this.fb.control('', [Validators.email, Validators.required]),
      size: this.fb.control<number>(0, [Validators.required]),
      base: this.fb.control<string>('', [Validators.required]),
      sauce: this.fb.control<string>('classic', [Validators.required]),
      toppings: this.toppings,
      comments: this.fb.control<string>('')
    })
  }

  processOrder(){
    const o = this.form.value as Order
    console.info("Order: " ,  o)
    this.pSvc.createOrder(o)
    .then(res=>{
      this.router.navigate(['/orders/' + o.email])
    })
  }

  getList(){
    this.router.navigate(['/orders/' + this.emailstr])
  }

  checkemail(e: any){
    console.info("Order: " ,  this.form.get('email')?.invalid)
    this.emailstr = e.target.value
  
  }

  

}
