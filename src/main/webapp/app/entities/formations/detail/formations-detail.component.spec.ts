import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FormationsDetailComponent } from './formations-detail.component';

describe('Component Tests', () => {
  describe('Formations Management Detail Component', () => {
    let comp: FormationsDetailComponent;
    let fixture: ComponentFixture<FormationsDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [FormationsDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ formations: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(FormationsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FormationsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load formations on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.formations).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
