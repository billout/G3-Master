import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { CandidatsService } from '../service/candidats.service';

import { CandidatsComponent } from './candidats.component';

describe('Component Tests', () => {
  describe('Candidats Management Component', () => {
    let comp: CandidatsComponent;
    let fixture: ComponentFixture<CandidatsComponent>;
    let service: CandidatsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CandidatsComponent],
      })
        .overrideTemplate(CandidatsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CandidatsComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(CandidatsService);

      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [{ id: 123 }],
            headers,
          })
        )
      );
    });

    it('Should call load all on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.candidats?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
