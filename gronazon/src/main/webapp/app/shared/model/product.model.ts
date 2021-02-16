import { IOrder } from 'app/shared/model/order.model';
import { ICategory } from 'app/shared/model/category.model';

export interface IProduct {
  id?: number;
  title?: string;
  description?: string;
  price?: number;
  orders?: IOrder[];
  categories?: ICategory[];
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string,
    public price?: number,
    public orders?: IOrder[],
    public categories?: ICategory[]
  ) {}
}
