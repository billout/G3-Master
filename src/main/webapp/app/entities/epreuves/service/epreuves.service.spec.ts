import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { TypeEpreuve } from 'app/entities/enumerations/type-epreuve.model';
import { IEpreuves, Epreuves } from '../epreuves.model';

import { EpreuvesService } from './epreuves.service';

describe('Service Tests', () => {
  describe('Epreuves Service', () => {
    let service: EpreuvesService;
    let httpMock: HttpTestingController;
    let elemDefault: IEpreuves;
    let expectedResult: IEpreuves | IEpreuves[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(EpreuvesService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        code: 'AAAAAAA',
        libelle: 'AAAAAAA',
        type: TypeEpreuve.ORAL,
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

      it('should create a Epreuves', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Epreuves()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Epreuves', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            code: 'BBBBBB',
            libelle: 'BBBBBB',
            type: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Epreuves', () => {
        const patchObject = Object.assign(
          {
            code: 'BBBBBB',
            libelle: 'BBBBBB',
            type: 'BBBBBB',
          },
          new Epreuves()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Epreuves', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            code: 'BBBBBB',
            libelle: 'BBBBBB',
            type: 'BBBBBB',
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

      it('should delete a Epreuves', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addEpreuvesToCollectionIfMissing', () => {
        it('should add a Epreuves to an empty array', () => {
          const epreuves: IEpreuves = { id: 123 };
          expectedResult = service.addEpreuvesToCollectionIfMissing([], epreuves);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(epreuves);
        });

        it('should not add a Epreuves to an array that contains it', () => {
          const epreuves: IEpreuves = { id: 123 };
          const epreuvesCollection: IEpreuves[] = [
            {
              ...epreuves,
            },
            { id: 456 },
          ];
          expectedResult = service.addEpreuvesToCollectionIfMissing(epreuvesCollection, epreuves);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Epreuves to an array that doesn't contain it", () => {
          const epreuves: IEpreuves = { id: 123 };
          const epreuvesCollection: IEpreuves[] = [{ id: 456 }];
          expectedResult = service.addEpreuvesToCollectionIfMissing(epreuvesCollection, epreuves);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(epreuves);
        });

        it('should add only unique Epreuves to an array', () => {
          const epreuvesArray: IEpreuves[] = [{ id: 123 }, { id: 456 }, { id: 17235 }];
          const epreuvesCollection: IEpreuves[] = [{ id: 123 }];
          expectedResult = service.addEpreuvesToCollectionIfMissing(epreuvesCollection, ...epreuvesArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const epreuves: IEpreuves = { id: 123 };
          const epreuves2: IEpreuves = { id: 456 };
          expectedResult = service.addEpreuvesToCollectionIfMissing([], epreuves, epreuves2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(epreuves);
          expect(expectedResult).toContain(epreuves2);
        });

        it('should accept null and undefined values', () => {
          const epreuves: IEpreuves = { id: 123 };
          expectedResult = service.addEpreuvesToCollectionIfMissing([], null, epreuves, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(epreuves);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
