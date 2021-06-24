import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { JuryService } from '../service/jury.service';

import { JuryComponent } from './jury.component';

describe('Component Tests', () => {
  describe('Jury Management Component', () => {
    let comp: JuryComponent;
    let fixture: ComponentFixture<JuryComponent>;
    let service: JuryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [JuryComponent],
      })
        .overrideTemplate(JuryComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(JuryComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(JuryService);

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
      expect(comp.juries?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
