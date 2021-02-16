import { IProduct } from 'app/shared/model/product.model';

export interface ICategory {
  id?: number;
  title?: string;
  description?: string;
  products?: IProduct[];
}

export class Category implements ICategory {
  constructor(public id?: number, public title?: string, public description?: string, public products?: IProduct[]) {}
}
