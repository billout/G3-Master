import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { VoieAccesService } from '../service/voie-acces.service';

import { VoieAccesComponent } from './voie-acces.component';

describe('Component Tests', () => {
  describe('VoieAcces Management Component', () => {
    let comp: VoieAccesComponent;
    let fixture: ComponentFixture<VoieAccesComponent>;
    let service: VoieAccesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [VoieAccesComponent],
      })
        .overrideTemplate(VoieAccesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VoieAccesComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(VoieAccesService);

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
      expect(comp.voieAcces?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
