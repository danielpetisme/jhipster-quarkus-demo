import { IOrder } from 'app/shared/model/order.model';

export interface ICustomer {
  id?: number;
  firstName?: string;
  lastName?: string;
  email?: string;
  telephone?: string;
  orders?: IOrder[];
}

export class Customer implements ICustomer {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public email?: string,
    public telephone?: string,
    public orders?: IOrder[]
  ) {}
}
