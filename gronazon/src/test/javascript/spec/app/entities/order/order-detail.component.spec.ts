import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GronazonTestModule } from '../../../test.module';
import { OrderDetailComponent } from 'app/entities/order/order-detail.component';
import { Order } from 'app/shared/model/order.model';

describe('Component Tests', () => {
  describe('Order Management Detail Component', () => {
    let comp: OrderDetailComponent;
    let fixture: ComponentFixture<OrderDetailComponent>;
    const route = ({ data: of({ order: new Order(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GronazonTestModule],
        declarations: [OrderDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(OrderDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OrderDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load order on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.order).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
