jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { FormationsService } from '../service/formations.service';
import { IFormations, Formations } from '../formations.model';
import { IConcours } from 'app/entities/concours/concours.model';
import { ConcoursService } from 'app/entities/concours/service/concours.service';
import { INiveaux } from 'app/entities/niveaux/niveaux.model';
import { NiveauxService } from 'app/entities/niveaux/service/niveaux.service';

import { FormationsUpdateComponent } from './formations-update.component';

describe('Component Tests', () => {
  describe('Formations Management Update Component', () => {
    let comp: FormationsUpdateComponent;
    let fixture: ComponentFixture<FormationsUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let formationsService: FormationsService;
    let concoursService: ConcoursService;
    let niveauxService: NiveauxService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [FormationsUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(FormationsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FormationsUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      formationsService = TestBed.inject(FormationsService);
      concoursService = TestBed.inject(ConcoursService);
      niveauxService = TestBed.inject(NiveauxService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Concours query and add missing value', () => {
        const formations: IFormations = { id: 456 };
        const concours: IConcours = { id: 24713 };
        formations.concours = concours;

        const concoursCollection: IConcours[] = [{ id: 43513 }];
        spyOn(concoursService, 'query').and.returnValue(of(new HttpResponse({ body: concoursCollection })));
        const additionalConcours = [concours];
        const expectedCollection: IConcours[] = [...additionalConcours, ...concoursCollection];
        spyOn(concoursService, 'addConcoursToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ formations });
        comp.ngOnInit();

        expect(concoursService.query).toHaveBeenCalled();
        expect(concoursService.addConcoursToCollectionIfMissing).toHaveBeenCalledWith(concoursCollection, ...additionalConcours);
        expect(comp.concoursSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Niveaux query and add missing value', () => {
        const formations: IFormations = { id: 456 };
        const niveaux: INiveaux = { id: 4922 };
        formations.niveaux = niveaux;

        const niveauxCollection: INiveaux[] = [{ id: 63810 }];
        spyOn(niveauxService, 'query').and.returnValue(of(new HttpResponse({ body: niveauxCollection })));
        const additionalNiveaux = [niveaux];
        const expectedCollection: INiveaux[] = [...additionalNiveaux, ...niveauxCollection];
        spyOn(niveauxService, 'addNiveauxToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ formations });
        comp.ngOnInit();

        expect(niveauxService.query).toHaveBeenCalled();
        expect(niveauxService.addNiveauxToCollectionIfMissing).toHaveBeenCalledWith(niveauxCollection, ...additionalNiveaux);
        expect(comp.niveauxSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const formations: IFormations = { id: 456 };
        const concours: IConcours = { id: 12525 };
        formations.concours = concours;
        const niveaux: INiveaux = { id: 9355 };
        formations.niveaux = niveaux;

        activatedRoute.data = of({ formations });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(formations));
        expect(comp.concoursSharedCollection).toContain(concours);
        expect(comp.niveauxSharedCollection).toContain(niveaux);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const formations = { id: 123 };
        spyOn(formationsService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ formations });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: formations }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(formationsService.update).toHaveBeenCalledWith(formations);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const formations = new Formations();
        spyOn(formationsService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ formations });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: formations }));
        saveSubject.complete();

        // THEN
        expect(formationsService.create).toHaveBeenCalledWith(formations);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const formations = { id: 123 };
        spyOn(formationsService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ formations });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(formationsService.update).toHaveBeenCalledWith(formations);
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

      describe('trackNiveauxById', () => {
        it('Should return tracked Niveaux primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackNiveauxById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
