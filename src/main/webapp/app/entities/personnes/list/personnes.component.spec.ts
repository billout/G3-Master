import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { PersonnesService } from '../service/personnes.service';

import { PersonnesComponent } from './personnes.component';

describe('Component Tests', () => {
  describe('Personnes Management Component', () => {
    let comp: PersonnesComponent;
    let fixture: ComponentFixture<PersonnesComponent>;
    let service: PersonnesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [PersonnesComponent],
      })
        .overrideTemplate(PersonnesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PersonnesComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(PersonnesService);

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
      expect(comp.personnes?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
