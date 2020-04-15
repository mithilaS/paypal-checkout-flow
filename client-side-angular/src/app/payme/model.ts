export class ItemsDetails{
  name: string;
  description:string;
  quantity:  string;
  amount: string;
  tax: string;

}

export class OrderResp {
  id: string;
  intent: string;
  status: string;
  payername : string;
  payeremail: string;
  payeeaddl1: string;
  payeeaddl2: string;
  payeeaddl3: string;
  subtotalAmt: string;
  totalAmt: string;
  totalAmtCurr: string;
  totaldiscount:string;
  totaltax:string;
  totalShipAmt: string;

  createdate: string;
  shipname:string;
  shipaddrl1:string;
  shipaddrl2:string;
  shipaddrl3:string;
  shipcountry:string;

  billname:string;
  billaddrl1:string;
  billaddrl2:string;
  billaddrl3:string;
  items: ItemsDetails[];

  payeeemail:string;
  paymentStatus: string;
  paymentifd:string;


}
