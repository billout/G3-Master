import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VoieAccesDetailComponent } from './voie-acces-detail.component';

describe('Component Tests', () => {
  describe('VoieAcces Management Detail Component', () => {
    let comp: VoieAccesDetailComponent;
    let fixture: ComponentFixture<VoieAccesDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [VoieAccesDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ voieAcces: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(VoieAccesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VoieAccesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load voieAcces on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.voieAcces).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
