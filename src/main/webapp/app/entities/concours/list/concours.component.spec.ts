import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { ConcoursService } from '../service/concours.service';

import { ConcoursComponent } from './concours.component';

describe('Component Tests', () => {
  describe('Concours Management Component', () => {
    let comp: ConcoursComponent;
    let fixture: ComponentFixture<ConcoursComponent>;
    let service: ConcoursService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ConcoursComponent],
      })
        .overrideTemplate(ConcoursComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConcoursComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(ConcoursService);

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
      expect(comp.concours?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
