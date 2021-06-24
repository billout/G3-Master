import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { INiveaux, Niveaux } from '../niveaux.model';

import { NiveauxService } from './niveaux.service';

describe('Service Tests', () => {
  describe('Niveaux Service', () => {
    let service: NiveauxService;
    let httpMock: HttpTestingController;
    let elemDefault: INiveaux;
    let expectedResult: INiveaux | INiveaux[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(NiveauxService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        libelle: 'AAAAAAA',
        code: 'AAAAAAA',
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

      it('should create a Niveaux', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Niveaux()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Niveaux', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            libelle: 'BBBBBB',
            code: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Niveaux', () => {
        const patchObject = Object.assign(
          {
            code: 'BBBBBB',
          },
          new Niveaux()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Niveaux', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            libelle: 'BBBBBB',
            code: 'BBBBBB',
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

      it('should delete a Niveaux', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addNiveauxToCollectionIfMissing', () => {
        it('should add a Niveaux to an empty array', () => {
          const niveaux: INiveaux = { id: 123 };
          expectedResult = service.addNiveauxToCollectionIfMissing([], niveaux);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(niveaux);
        });

        it('should not add a Niveaux to an array that contains it', () => {
          const niveaux: INiveaux = { id: 123 };
          const niveauxCollection: INiveaux[] = [
            {
              ...niveaux,
            },
            { id: 456 },
          ];
          expectedResult = service.addNiveauxToCollectionIfMissing(niveauxCollection, niveaux);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Niveaux to an array that doesn't contain it", () => {
          const niveaux: INiveaux = { id: 123 };
          const niveauxCollection: INiveaux[] = [{ id: 456 }];
          expectedResult = service.addNiveauxToCollectionIfMissing(niveauxCollection, niveaux);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(niveaux);
        });

        it('should add only unique Niveaux to an array', () => {
          const niveauxArray: INiveaux[] = [{ id: 123 }, { id: 456 }, { id: 3217 }];
          const niveauxCollection: INiveaux[] = [{ id: 123 }];
          expectedResult = service.addNiveauxToCollectionIfMissing(niveauxCollection, ...niveauxArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const niveaux: INiveaux = { id: 123 };
          const niveaux2: INiveaux = { id: 456 };
          expectedResult = service.addNiveauxToCollectionIfMissing([], niveaux, niveaux2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(niveaux);
          expect(expectedResult).toContain(niveaux2);
        });

        it('should accept null and undefined values', () => {
          const niveaux: INiveaux = { id: 123 };
          expectedResult = service.addNiveauxToCollectionIfMissing([], null, niveaux, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(niveaux);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
