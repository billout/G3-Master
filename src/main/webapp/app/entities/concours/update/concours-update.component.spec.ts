jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ConcoursService } from '../service/concours.service';
import { IConcours, Concours } from '../concours.model';
import { ICandidats } from 'app/entities/candidats/candidats.model';
import { CandidatsService } from 'app/entities/candidats/service/candidats.service';

import { ConcoursUpdateComponent } from './concours-update.component';

describe('Component Tests', () => {
  describe('Concours Management Update Component', () => {
    let comp: ConcoursUpdateComponent;
    let fixture: ComponentFixture<ConcoursUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let concoursService: ConcoursService;
    let candidatsService: CandidatsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ConcoursUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ConcoursUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConcoursUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      concoursService = TestBed.inject(ConcoursService);
      candidatsService = TestBed.inject(CandidatsService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Candidats query and add missing value', () => {
        const concours: IConcours = { id: 456 };
        const candidats: ICandidats[] = [{ id: 74769 }];
        concours.candidats = candidats;

        const candidatsCollection: ICandidats[] = [{ id: 81097 }];
        spyOn(candidatsService, 'query').and.returnValue(of(new HttpResponse({ body: candidatsCollection })));
        const additionalCandidats = [...candidats];
        const expectedCollection: ICandidats[] = [...additionalCandidats, ...candidatsCollection];
        spyOn(candidatsService, 'addCandidatsToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ concours });
        comp.ngOnInit();

        expect(candidatsService.query).toHaveBeenCalled();
        expect(candidatsService.addCandidatsToCollectionIfMissing).toHaveBeenCalledWith(candidatsCollection, ...additionalCandidats);
        expect(comp.candidatsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const concours: IConcours = { id: 456 };
        const candidats: ICandidats = { id: 78492 };
        concours.candidats = [candidats];

        activatedRoute.data = of({ concours });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(concours));
        expect(comp.candidatsSharedCollection).toContain(candidats);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const concours = { id: 123 };
        spyOn(concoursService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ concours });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: concours }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(concoursService.update).toHaveBeenCalledWith(concours);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const concours = new Concours();
        spyOn(concoursService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ concours });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: concours }));
        saveSubject.complete();

        // THEN
        expect(concoursService.create).toHaveBeenCalledWith(concours);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const concours = { id: 123 };
        spyOn(concoursService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ concours });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(concoursService.update).toHaveBeenCalledWith(concours);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackCandidatsById', () => {
        it('Should return tracked Candidats primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackCandidatsById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });

    describe('Getting selected relationships', () => {
      describe('getSelectedCandidats', () => {
        it('Should return option if no Candidats is selected', () => {
          const option = { id: 123 };
          const result = comp.getSelectedCandidats(option);
          expect(result === option).toEqual(true);
        });

        it('Should return selected Candidats for according option', () => {
          const option = { id: 123 };
          const selected = { id: 123 };
          const selected2 = { id: 456 };
          const result = comp.getSelectedCandidats(option, [selected2, selected]);
          expect(result === selected).toEqual(true);
          expect(result === selected2).toEqual(false);
          expect(result === option).toEqual(false);
        });

        it('Should return option if this Candidats is not selected', () => {
          const option = { id: 123 };
          const selected = { id: 456 };
          const result = comp.getSelectedCandidats(option, [selected]);
          expect(result === option).toEqual(true);
          expect(result === selected).toEqual(false);
        });
      });
    });
  });
});
