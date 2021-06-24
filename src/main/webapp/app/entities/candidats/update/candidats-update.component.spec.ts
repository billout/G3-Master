jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CandidatsService } from '../service/candidats.service';
import { ICandidats, Candidats } from '../candidats.model';
import { IPersonnes } from 'app/entities/personnes/personnes.model';
import { PersonnesService } from 'app/entities/personnes/service/personnes.service';

import { CandidatsUpdateComponent } from './candidats-update.component';

describe('Component Tests', () => {
  describe('Candidats Management Update Component', () => {
    let comp: CandidatsUpdateComponent;
    let fixture: ComponentFixture<CandidatsUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let candidatsService: CandidatsService;
    let personnesService: PersonnesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CandidatsUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CandidatsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CandidatsUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      candidatsService = TestBed.inject(CandidatsService);
      personnesService = TestBed.inject(PersonnesService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Personnes query and add missing value', () => {
        const candidats: ICandidats = { id: 456 };
        const est: IPersonnes = { id: 66830 };
        candidats.est = est;

        const personnesCollection: IPersonnes[] = [{ id: 45200 }];
        spyOn(personnesService, 'query').and.returnValue(of(new HttpResponse({ body: personnesCollection })));
        const additionalPersonnes = [est];
        const expectedCollection: IPersonnes[] = [...additionalPersonnes, ...personnesCollection];
        spyOn(personnesService, 'addPersonnesToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ candidats });
        comp.ngOnInit();

        expect(personnesService.query).toHaveBeenCalled();
        expect(personnesService.addPersonnesToCollectionIfMissing).toHaveBeenCalledWith(personnesCollection, ...additionalPersonnes);
        expect(comp.personnesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const candidats: ICandidats = { id: 456 };
        const est: IPersonnes = { id: 43178 };
        candidats.est = est;

        activatedRoute.data = of({ candidats });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(candidats));
        expect(comp.personnesSharedCollection).toContain(est);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const candidats = { id: 123 };
        spyOn(candidatsService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ candidats });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: candidats }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(candidatsService.update).toHaveBeenCalledWith(candidats);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const candidats = new Candidats();
        spyOn(candidatsService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ candidats });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: candidats }));
        saveSubject.complete();

        // THEN
        expect(candidatsService.create).toHaveBeenCalledWith(candidats);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const candidats = { id: 123 };
        spyOn(candidatsService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ candidats });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(candidatsService.update).toHaveBeenCalledWith(candidats);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackPersonnesById', () => {
        it('Should return tracked Personnes primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackPersonnesById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
