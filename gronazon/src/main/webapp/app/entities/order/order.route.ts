import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IOrder, Order } from 'app/shared/model/order.model';
import { OrderService } from './order.service';
import { OrderComponent } from './order.component';
import { OrderDetailComponent } from './order-detail.component';
import { OrderUpdateComponent } from './order-update.component';

@Injectable({ providedIn: 'root' })
export class OrderResolve implements Resolve<IOrder> {
  constructor(private service: OrderService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOrder> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((order: HttpResponse<Order>) => {
          if (order.body) {
            return of(order.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Order());
  }
}

export const orderRoute: Routes = [
  {
    path: '',
    component: OrderComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gronazonApp.order.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OrderDetailComponent,
    resolve: {
      order: OrderResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gronazonApp.order.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OrderUpdateComponent,
    resolve: {
      order: OrderResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gronazonApp.order.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OrderUpdateComponent,
    resolve: {
      order: OrderResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gronazonApp.order.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
