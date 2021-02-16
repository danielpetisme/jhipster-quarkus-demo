import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IOrder, Order } from 'app/shared/model/order.model';
import { OrderService } from './order.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product/product.service';

type SelectableEntity = ICustomer | IProduct;

@Component({
  selector: 'jhi-order-update',
  templateUrl: './order-update.component.html',
})
export class OrderUpdateComponent implements OnInit {
  isSaving = false;
  customers: ICustomer[] = [];
  products: IProduct[] = [];

  editForm = this.fb.group({
    id: [],
    status: [],
    dateAdded: [],
    dateModified: [],
    customerId: [],
    productId: [],
  });

  constructor(
    protected orderService: OrderService,
    protected customerService: CustomerService,
    protected productService: ProductService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ order }) => {
      if (!order.id) {
        const today = moment().startOf('day');
        order.dateAdded = today;
        order.dateModified = today;
      }

      this.updateForm(order);

      this.customerService.query().subscribe((res: HttpResponse<ICustomer[]>) => (this.customers = res.body || []));

      this.productService.query().subscribe((res: HttpResponse<IProduct[]>) => (this.products = res.body || []));
    });
  }

  updateForm(order: IOrder): void {
    this.editForm.patchValue({
      id: order.id,
      status: order.status,
      dateAdded: order.dateAdded ? order.dateAdded.format(DATE_TIME_FORMAT) : null,
      dateModified: order.dateModified ? order.dateModified.format(DATE_TIME_FORMAT) : null,
      customerId: order.customerId,
      productId: order.productId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const order = this.createFromForm();
    if (order.id !== undefined) {
      this.subscribeToSaveResponse(this.orderService.update(order));
    } else {
      this.subscribeToSaveResponse(this.orderService.create(order));
    }
  }

  private createFromForm(): IOrder {
    return {
      ...new Order(),
      id: this.editForm.get(['id'])!.value,
      status: this.editForm.get(['status'])!.value,
      dateAdded: this.editForm.get(['dateAdded'])!.value ? moment(this.editForm.get(['dateAdded'])!.value, DATE_TIME_FORMAT) : undefined,
      dateModified: this.editForm.get(['dateModified'])!.value
        ? moment(this.editForm.get(['dateModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      customerId: this.editForm.get(['customerId'])!.value,
      productId: this.editForm.get(['productId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrder>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
