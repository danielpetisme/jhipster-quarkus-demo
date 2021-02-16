import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'category',
        loadChildren: () => import('./category/category.module').then(m => m.GronazonCategoryModule),
      },
      {
        path: 'product',
        loadChildren: () => import('./product/product.module').then(m => m.GronazonProductModule),
      },
      {
        path: 'customer',
        loadChildren: () => import('./customer/customer.module').then(m => m.GronazonCustomerModule),
      },
      {
        path: 'order',
        loadChildren: () => import('./order/order.module').then(m => m.GronazonOrderModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class GronazonEntityModule {}
