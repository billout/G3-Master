import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CandidatsDetailComponent } from './candidats-detail.component';

describe('Component Tests', () => {
  describe('Candidats Management Detail Component', () => {
    let comp: CandidatsDetailComponent;
    let fixture: ComponentFixture<CandidatsDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [CandidatsDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ candidats: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(CandidatsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CandidatsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load candidats on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.candidats).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
