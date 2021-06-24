import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpreuvesDetailComponent } from './epreuves-detail.component';

describe('Component Tests', () => {
  describe('Epreuves Management Detail Component', () => {
    let comp: EpreuvesDetailComponent;
    let fixture: ComponentFixture<EpreuvesDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [EpreuvesDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ epreuves: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(EpreuvesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EpreuvesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load epreuves on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.epreuves).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
