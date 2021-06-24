import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NiveauxDetailComponent } from './niveaux-detail.component';

describe('Component Tests', () => {
  describe('Niveaux Management Detail Component', () => {
    let comp: NiveauxDetailComponent;
    let fixture: ComponentFixture<NiveauxDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [NiveauxDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ niveaux: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(NiveauxDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NiveauxDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load niveaux on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.niveaux).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
