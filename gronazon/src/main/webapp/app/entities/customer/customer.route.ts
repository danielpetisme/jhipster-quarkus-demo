import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICustomer, Customer } from 'app/shared/model/customer.model';
import { CustomerService } from './customer.service';
import { CustomerComponent } from './customer.component';
import { CustomerDetailComponent } from './customer-detail.component';
import { CustomerUpdateComponent } from './customer-update.component';

@Injectable({ providedIn: 'root' })
export class CustomerResolve implements Resolve<ICustomer> {
  constructor(private service: CustomerService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICustomer> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((customer: HttpResponse<Customer>) => {
          if (customer.body) {
            return of(customer.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Customer());
  }
}

export const customerRoute: Routes = [
  {
    path: '',
    component: CustomerComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'gronazonApp.customer.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CustomerDetailComponent,
    resolve: {
      customer: CustomerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gronazonApp.customer.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CustomerUpdateComponent,
    resolve: {
      customer: CustomerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gronazonApp.customer.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CustomerUpdateComponent,
    resolve: {
      customer: CustomerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gronazonApp.customer.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
