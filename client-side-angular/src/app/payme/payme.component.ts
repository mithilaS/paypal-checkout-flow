import { Component, OnInit  , ViewChild , ElementRef} from '@angular/core';
import * as ord from './model';
import { Directive, HostListener } from '@angular/core';
import { Location } from '@angular/common';

declare var paypal;
@Component({
  selector: 'app-payme',
  templateUrl: './payme.component.html',
  styleUrls: ['./payme.component.css']
})
export class PaymeComponent implements OnInit {

  @ViewChild('paypal' , {static: true} ) paypalElement: ElementRef;


  public items: any[] =[{
    name: 'The Ottoman',
    quantity: '1',
    category: 'FURNITURE',
    img: 'assets/ottomon.jpg',
    description: 'Use it as a coffee table or as a footstool – for obvious reasons.',
    currency_code: 'USD',
    value: '10.00',
    tax: '3.00',
    total:'13.00'

  },
{
  name: 'The Armchair',
  quantity: '1',
  category: 'FURNITURE',
  img: 'assets/armchair.jpg',
  description: 'Enjoy this armchair which is almost like a luxury sofa!',
  currency_code: 'USD',
  value: '10.00',
  tax: '2.00',
  total:'12.00'

  }
]
 products = {
    purchase_units: {
      amount: {
          currency_code: 'USD',
          value: '25.00',

          breakdown: {
              item_total: {
                  currency_code: 'USD',
                  value: '20.00',
                  taxtotal:'5.00',
                  shippingtotal: '5.00',
                  discount: '5.00'
              }
          }
      },
      items: this.items
  }


   };

  paidFor = false;
  orderSummary :ord.OrderResp;

  ngOnInit(): void {
    paypal
    .Buttons({

      createOrder: (data,actions)=> {
        return fetch('http://localhost:9090/order/createPayment', {
          method: 'post',
          headers: {
          'Access-Control-Allow-Origin': '*',
         'Content-Type': 'application/json'
          },
          credentials : 'include',
          body: JSON.stringify(this.products)
      }).then(function(res) {
          return res.json();
      }).then(function(data) {
          return data.orderId
      });
      },
      onApprove: async (data, actions) => {
        const order = await actions.order.capture();
         let resp = new ord.OrderResp();
        resp.id=order.id;
        resp.createdate=order.create_time;
        resp.intent=order.intent;
        resp.status=order.status;
        resp.totalAmt =order.purchase_units[0].amount.value;
        resp.subtotalAmt =order.purchase_units[0].amount.breakdown.item_total.value;
        resp.totalAmtCurr= order.purchase_units[0].amount.currency_code;
        resp.totaldiscount = order.purchase_units[0].amount.breakdown.shipping_discount.value;
        resp.totaltax =order.purchase_units[0].amount.breakdown.tax_total.value;
        resp.totalShipAmt = order.purchase_units[0].amount.breakdown.shipping.value;


        resp.payeeemail=order.payer.email_address;
        resp.payeeaddl1=order.payer.country_code;
        resp.payername=order.payer.name.given_name + " "  + order.payer.name.surname;

        resp.shipname = order.purchase_units[0].shipping.name.full_name;
        resp.shipaddrl1=order.purchase_units[0].shipping.address.address_line_1;
        resp.shipaddrl2=order.purchase_units[0].shipping.address.address_line_2;
        resp.shipaddrl3=order.purchase_units[0].shipping.address.admin_area_1 + ' , ' +  order.purchase_units[0].shipping.address.admin_area_2;

        let arr =[];
        let itmresp = order.purchase_units[0].items;
        for(let i=0;i<itmresp.length;i++)
          {
            var itm = new ord.ItemsDetails();
            itm.name=itmresp[i].name;
            itm.quantity = itmresp[i].quantity;
            itm.amount = itmresp[i].unit_amount.value;
            itm.tax= itmresp[i].tax.value;
            arr.push(itm);
          }
          resp.items = arr;
          console.log('items : ' +  resp.items);
          this.paidFor = true;

        this.orderSummary=resp;

        console.log(order);

      },
      onError: err => {
        console.log(err);
      }


    })

    .render(this.paypalElement.nativeElement);

  }

  onBackClick() {
    console.log("on button click")
    this.paidFor=false;
    this.orderSummary = new ord.OrderResp();
  }


}

@Directive({
  selector: '[backButton]'
})
export class BackButtonDirective {
  constructor(private location: Location) { }

  @HostListener('click')
  onClick() {
      this.location.back();

  }
};

