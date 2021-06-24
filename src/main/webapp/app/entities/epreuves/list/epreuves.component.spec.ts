import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { EpreuvesService } from '../service/epreuves.service';

import { EpreuvesComponent } from './epreuves.component';

describe('Component Tests', () => {
  describe('Epreuves Management Component', () => {
    let comp: EpreuvesComponent;
    let fixture: ComponentFixture<EpreuvesComponent>;
    let service: EpreuvesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [EpreuvesComponent],
      })
        .overrideTemplate(EpreuvesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EpreuvesComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(EpreuvesService);

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
      expect(comp.epreuves?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
