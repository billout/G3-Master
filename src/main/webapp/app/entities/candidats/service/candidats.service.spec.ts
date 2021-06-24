import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { Statut } from 'app/entities/enumerations/statut.model';
import { ICandidats, Candidats } from '../candidats.model';

import { CandidatsService } from './candidats.service';

describe('Service Tests', () => {
  describe('Candidats Service', () => {
    let service: CandidatsService;
    let httpMock: HttpTestingController;
    let elemDefault: ICandidats;
    let expectedResult: ICandidats | ICandidats[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CandidatsService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        identifiant: 'AAAAAAA',
        etat: Statut.CANDIDATS,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Candidats', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Candidats()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Candidats', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            identifiant: 'BBBBBB',
            etat: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Candidats', () => {
        const patchObject = Object.assign(
          {
            etat: 'BBBBBB',
          },
          new Candidats()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Candidats', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            identifiant: 'BBBBBB',
            etat: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Candidats', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCandidatsToCollectionIfMissing', () => {
        it('should add a Candidats to an empty array', () => {
          const candidats: ICandidats = { id: 123 };
          expectedResult = service.addCandidatsToCollectionIfMissing([], candidats);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(candidats);
        });

        it('should not add a Candidats to an array that contains it', () => {
          const candidats: ICandidats = { id: 123 };
          const candidatsCollection: ICandidats[] = [
            {
              ...candidats,
            },
            { id: 456 },
          ];
          expectedResult = service.addCandidatsToCollectionIfMissing(candidatsCollection, candidats);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Candidats to an array that doesn't contain it", () => {
          const candidats: ICandidats = { id: 123 };
          const candidatsCollection: ICandidats[] = [{ id: 456 }];
          expectedResult = service.addCandidatsToCollectionIfMissing(candidatsCollection, candidats);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(candidats);
        });

        it('should add only unique Candidats to an array', () => {
          const candidatsArray: ICandidats[] = [{ id: 123 }, { id: 456 }, { id: 86343 }];
          const candidatsCollection: ICandidats[] = [{ id: 123 }];
          expectedResult = service.addCandidatsToCollectionIfMissing(candidatsCollection, ...candidatsArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const candidats: ICandidats = { id: 123 };
          const candidats2: ICandidats = { id: 456 };
          expectedResult = service.addCandidatsToCollectionIfMissing([], candidats, candidats2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(candidats);
          expect(expectedResult).toContain(candidats2);
        });

        it('should accept null and undefined values', () => {
          const candidats: ICandidats = { id: 123 };
          expectedResult = service.addCandidatsToCollectionIfMissing([], null, candidats, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(candidats);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
