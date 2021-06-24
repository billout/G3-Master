jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { VoieAccesService } from '../service/voie-acces.service';
import { IVoieAcces, VoieAcces } from '../voie-acces.model';
import { IConcours } from 'app/entities/concours/concours.model';
import { ConcoursService } from 'app/entities/concours/service/concours.service';

import { VoieAccesUpdateComponent } from './voie-acces-update.component';

describe('Component Tests', () => {
  describe('VoieAcces Management Update Component', () => {
    let comp: VoieAccesUpdateComponent;
    let fixture: ComponentFixture<VoieAccesUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let voieAccesService: VoieAccesService;
    let concoursService: ConcoursService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [VoieAccesUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(VoieAccesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VoieAccesUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      voieAccesService = TestBed.inject(VoieAccesService);
      concoursService = TestBed.inject(ConcoursService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Concours query and add missing value', () => {
        const voieAcces: IVoieAcces = { id: 456 };
        const concours: IConcours = { id: 44105 };
        voieAcces.concours = concours;

        const concoursCollection: IConcours[] = [{ id: 7614 }];
        spyOn(concoursService, 'query').and.returnValue(of(new HttpResponse({ body: concoursCollection })));
        const additionalConcours = [concours];
        const expectedCollection: IConcours[] = [...additionalConcours, ...concoursCollection];
        spyOn(concoursService, 'addConcoursToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ voieAcces });
        comp.ngOnInit();

        expect(concoursService.query).toHaveBeenCalled();
        expect(concoursService.addConcoursToCollectionIfMissing).toHaveBeenCalledWith(concoursCollection, ...additionalConcours);
        expect(comp.concoursSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const voieAcces: IVoieAcces = { id: 456 };
        const concours: IConcours = { id: 27030 };
        voieAcces.concours = concours;

        activatedRoute.data = of({ voieAcces });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(voieAcces));
        expect(comp.concoursSharedCollection).toContain(concours);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const voieAcces = { id: 123 };
        spyOn(voieAccesService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ voieAcces });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: voieAcces }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(voieAccesService.update).toHaveBeenCalledWith(voieAcces);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const voieAcces = new VoieAcces();
        spyOn(voieAccesService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ voieAcces });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: voieAcces }));
        saveSubject.complete();

        // THEN
        expect(voieAccesService.create).toHaveBeenCalledWith(voieAcces);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const voieAcces = { id: 123 };
        spyOn(voieAccesService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ voieAcces });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(voieAccesService.update).toHaveBeenCalledWith(voieAcces);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackConcoursById', () => {
        it('Should return tracked Concours primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackConcoursById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
