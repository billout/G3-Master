jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { NiveauxService } from '../service/niveaux.service';
import { INiveaux, Niveaux } from '../niveaux.model';

import { NiveauxUpdateComponent } from './niveaux-update.component';

describe('Component Tests', () => {
  describe('Niveaux Management Update Component', () => {
    let comp: NiveauxUpdateComponent;
    let fixture: ComponentFixture<NiveauxUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let niveauxService: NiveauxService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [NiveauxUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(NiveauxUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NiveauxUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      niveauxService = TestBed.inject(NiveauxService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const niveaux: INiveaux = { id: 456 };

        activatedRoute.data = of({ niveaux });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(niveaux));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const niveaux = { id: 123 };
        spyOn(niveauxService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ niveaux });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: niveaux }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(niveauxService.update).toHaveBeenCalledWith(niveaux);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const niveaux = new Niveaux();
        spyOn(niveauxService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ niveaux });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: niveaux }));
        saveSubject.complete();

        // THEN
        expect(niveauxService.create).toHaveBeenCalledWith(niveaux);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const niveaux = { id: 123 };
        spyOn(niveauxService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ niveaux });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(niveauxService.update).toHaveBeenCalledWith(niveaux);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
