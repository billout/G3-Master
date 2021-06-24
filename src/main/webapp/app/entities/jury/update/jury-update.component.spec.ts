jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { JuryService } from '../service/jury.service';
import { IJury, Jury } from '../jury.model';
import { IPersonnes } from 'app/entities/personnes/personnes.model';
import { PersonnesService } from 'app/entities/personnes/service/personnes.service';
import { IEpreuves } from 'app/entities/epreuves/epreuves.model';
import { EpreuvesService } from 'app/entities/epreuves/service/epreuves.service';

import { JuryUpdateComponent } from './jury-update.component';

describe('Component Tests', () => {
  describe('Jury Management Update Component', () => {
    let comp: JuryUpdateComponent;
    let fixture: ComponentFixture<JuryUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let juryService: JuryService;
    let personnesService: PersonnesService;
    let epreuvesService: EpreuvesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [JuryUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(JuryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(JuryUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      juryService = TestBed.inject(JuryService);
      personnesService = TestBed.inject(PersonnesService);
      epreuvesService = TestBed.inject(EpreuvesService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call president query and add missing value', () => {
        const jury: IJury = { id: 456 };
        const president: IPersonnes = { id: 82386 };
        jury.president = president;

        const presidentCollection: IPersonnes[] = [{ id: 36442 }];
        spyOn(personnesService, 'query').and.returnValue(of(new HttpResponse({ body: presidentCollection })));
        const expectedCollection: IPersonnes[] = [president, ...presidentCollection];
        spyOn(personnesService, 'addPersonnesToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ jury });
        comp.ngOnInit();

        expect(personnesService.query).toHaveBeenCalled();
        expect(personnesService.addPersonnesToCollectionIfMissing).toHaveBeenCalledWith(presidentCollection, president);
        expect(comp.presidentsCollection).toEqual(expectedCollection);
      });

      it('Should call membre1 query and add missing value', () => {
        const jury: IJury = { id: 456 };
        const membre1: IPersonnes = { id: 19494 };
        jury.membre1 = membre1;

        const membre1Collection: IPersonnes[] = [{ id: 7943 }];
        spyOn(personnesService, 'query').and.returnValue(of(new HttpResponse({ body: membre1Collection })));
        const expectedCollection: IPersonnes[] = [membre1, ...membre1Collection];
        spyOn(personnesService, 'addPersonnesToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ jury });
        comp.ngOnInit();

        expect(personnesService.query).toHaveBeenCalled();
        expect(personnesService.addPersonnesToCollectionIfMissing).toHaveBeenCalledWith(membre1Collection, membre1);
        expect(comp.membre1sCollection).toEqual(expectedCollection);
      });

      it('Should call membre2 query and add missing value', () => {
        const jury: IJury = { id: 456 };
        const membre2: IPersonnes = { id: 51696 };
        jury.membre2 = membre2;

        const membre2Collection: IPersonnes[] = [{ id: 98412 }];
        spyOn(personnesService, 'query').and.returnValue(of(new HttpResponse({ body: membre2Collection })));
        const expectedCollection: IPersonnes[] = [membre2, ...membre2Collection];
        spyOn(personnesService, 'addPersonnesToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ jury });
        comp.ngOnInit();

        expect(personnesService.query).toHaveBeenCalled();
        expect(personnesService.addPersonnesToCollectionIfMissing).toHaveBeenCalledWith(membre2Collection, membre2);
        expect(comp.membre2sCollection).toEqual(expectedCollection);
      });

      it('Should call membre3 query and add missing value', () => {
        const jury: IJury = { id: 456 };
        const membre3: IPersonnes = { id: 64298 };
        jury.membre3 = membre3;

        const membre3Collection: IPersonnes[] = [{ id: 88857 }];
        spyOn(personnesService, 'query').and.returnValue(of(new HttpResponse({ body: membre3Collection })));
        const expectedCollection: IPersonnes[] = [membre3, ...membre3Collection];
        spyOn(personnesService, 'addPersonnesToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ jury });
        comp.ngOnInit();

        expect(personnesService.query).toHaveBeenCalled();
        expect(personnesService.addPersonnesToCollectionIfMissing).toHaveBeenCalledWith(membre3Collection, membre3);
        expect(comp.membre3sCollection).toEqual(expectedCollection);
      });

      it('Should call corrige query and add missing value', () => {
        const jury: IJury = { id: 456 };
        const corrige: IEpreuves = { id: 45611 };
        jury.corrige = corrige;

        const corrigeCollection: IEpreuves[] = [{ id: 70663 }];
        spyOn(epreuvesService, 'query').and.returnValue(of(new HttpResponse({ body: corrigeCollection })));
        const expectedCollection: IEpreuves[] = [corrige, ...corrigeCollection];
        spyOn(epreuvesService, 'addEpreuvesToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ jury });
        comp.ngOnInit();

        expect(epreuvesService.query).toHaveBeenCalled();
        expect(epreuvesService.addEpreuvesToCollectionIfMissing).toHaveBeenCalledWith(corrigeCollection, corrige);
        expect(comp.corrigesCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const jury: IJury = { id: 456 };
        const president: IPersonnes = { id: 11777 };
        jury.president = president;
        const membre1: IPersonnes = { id: 28884 };
        jury.membre1 = membre1;
        const membre2: IPersonnes = { id: 58173 };
        jury.membre2 = membre2;
        const membre3: IPersonnes = { id: 36898 };
        jury.membre3 = membre3;
        const corrige: IEpreuves = { id: 86049 };
        jury.corrige = corrige;

        activatedRoute.data = of({ jury });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(jury));
        expect(comp.presidentsCollection).toContain(president);
        expect(comp.membre1sCollection).toContain(membre1);
        expect(comp.membre2sCollection).toContain(membre2);
        expect(comp.membre3sCollection).toContain(membre3);
        expect(comp.corrigesCollection).toContain(corrige);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const jury = { id: 123 };
        spyOn(juryService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ jury });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: jury }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(juryService.update).toHaveBeenCalledWith(jury);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const jury = new Jury();
        spyOn(juryService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ jury });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: jury }));
        saveSubject.complete();

        // THEN
        expect(juryService.create).toHaveBeenCalledWith(jury);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const jury = { id: 123 };
        spyOn(juryService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ jury });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(juryService.update).toHaveBeenCalledWith(jury);
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

      describe('trackEpreuvesById', () => {
        it('Should return tracked Epreuves primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackEpreuvesById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
