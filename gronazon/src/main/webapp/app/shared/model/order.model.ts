import { Moment } from 'moment';
import { OrderStatus } from 'app/shared/model/enumerations/order-status.model';

export interface IOrder {
  id?: number;
  status?: OrderStatus;
  dateAdded?: Moment;
  dateModified?: Moment;
  customerEmail?: string;
  customerId?: number;
  productTitle?: string;
  productId?: number;
}

export class Order implements IOrder {
  constructor(
    public id?: number,
    public status?: OrderStatus,
    public dateAdded?: Moment,
    public dateModified?: Moment,
    public customerEmail?: string,
    public customerId?: number,
    public productTitle?: string,
    public productId?: number
  ) {}
}
