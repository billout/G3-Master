import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { NiveauxService } from '../service/niveaux.service';

import { NiveauxComponent } from './niveaux.component';

describe('Component Tests', () => {
  describe('Niveaux Management Component', () => {
    let comp: NiveauxComponent;
    let fixture: ComponentFixture<NiveauxComponent>;
    let service: NiveauxService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [NiveauxComponent],
      })
        .overrideTemplate(NiveauxComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NiveauxComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(NiveauxService);

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
      expect(comp.niveaux?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
