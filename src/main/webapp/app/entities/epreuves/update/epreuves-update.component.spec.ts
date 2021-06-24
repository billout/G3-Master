jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { EpreuvesService } from '../service/epreuves.service';
import { IEpreuves, Epreuves } from '../epreuves.model';
import { IConcours } from 'app/entities/concours/concours.model';
import { ConcoursService } from 'app/entities/concours/service/concours.service';

import { EpreuvesUpdateComponent } from './epreuves-update.component';

describe('Component Tests', () => {
  describe('Epreuves Management Update Component', () => {
    let comp: EpreuvesUpdateComponent;
    let fixture: ComponentFixture<EpreuvesUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let epreuvesService: EpreuvesService;
    let concoursService: ConcoursService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [EpreuvesUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(EpreuvesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EpreuvesUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      epreuvesService = TestBed.inject(EpreuvesService);
      concoursService = TestBed.inject(ConcoursService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Concours query and add missing value', () => {
        const epreuves: IEpreuves = { id: 456 };
        const compose: IConcours = { id: 24762 };
        epreuves.compose = compose;

        const concoursCollection: IConcours[] = [{ id: 42570 }];
        spyOn(concoursService, 'query').and.returnValue(of(new HttpResponse({ body: concoursCollection })));
        const additionalConcours = [compose];
        const expectedCollection: IConcours[] = [...additionalConcours, ...concoursCollection];
        spyOn(concoursService, 'addConcoursToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ epreuves });
        comp.ngOnInit();

        expect(concoursService.query).toHaveBeenCalled();
        expect(concoursService.addConcoursToCollectionIfMissing).toHaveBeenCalledWith(concoursCollection, ...additionalConcours);
        expect(comp.concoursSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const epreuves: IEpreuves = { id: 456 };
        const compose: IConcours = { id: 75861 };
        epreuves.compose = compose;

        activatedRoute.data = of({ epreuves });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(epreuves));
        expect(comp.concoursSharedCollection).toContain(compose);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const epreuves = { id: 123 };
        spyOn(epreuvesService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ epreuves });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: epreuves }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(epreuvesService.update).toHaveBeenCalledWith(epreuves);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const epreuves = new Epreuves();
        spyOn(epreuvesService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ epreuves });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: epreuves }));
        saveSubject.complete();

        // THEN
        expect(epreuvesService.create).toHaveBeenCalledWith(epreuves);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const epreuves = { id: 123 };
        spyOn(epreuvesService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ epreuves });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(epreuvesService.update).toHaveBeenCalledWith(epreuves);
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
