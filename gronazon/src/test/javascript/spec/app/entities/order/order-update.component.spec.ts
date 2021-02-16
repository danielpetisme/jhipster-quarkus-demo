import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GronazonTestModule } from '../../../test.module';
import { OrderUpdateComponent } from 'app/entities/order/order-update.component';
import { OrderService } from 'app/entities/order/order.service';
import { Order } from 'app/shared/model/order.model';

describe('Component Tests', () => {
  describe('Order Management Update Component', () => {
    let comp: OrderUpdateComponent;
    let fixture: ComponentFixture<OrderUpdateComponent>;
    let service: OrderService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GronazonTestModule],
        declarations: [OrderUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(OrderUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrderUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrderService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Order(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Order();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
