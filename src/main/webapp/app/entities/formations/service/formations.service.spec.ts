import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFormations, Formations } from '../formations.model';

import { FormationsService } from './formations.service';

describe('Service Tests', () => {
  describe('Formations Service', () => {
    let service: FormationsService;
    let httpMock: HttpTestingController;
    let elemDefault: IFormations;
    let expectedResult: IFormations | IFormations[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(FormationsService);
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

      it('should create a Formations', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Formations()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Formations', () => {
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

      it('should partial update a Formations', () => {
        const patchObject = Object.assign({}, new Formations());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Formations', () => {
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

      it('should delete a Formations', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addFormationsToCollectionIfMissing', () => {
        it('should add a Formations to an empty array', () => {
          const formations: IFormations = { id: 123 };
          expectedResult = service.addFormationsToCollectionIfMissing([], formations);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(formations);
        });

        it('should not add a Formations to an array that contains it', () => {
          const formations: IFormations = { id: 123 };
          const formationsCollection: IFormations[] = [
            {
              ...formations,
            },
            { id: 456 },
          ];
          expectedResult = service.addFormationsToCollectionIfMissing(formationsCollection, formations);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Formations to an array that doesn't contain it", () => {
          const formations: IFormations = { id: 123 };
          const formationsCollection: IFormations[] = [{ id: 456 }];
          expectedResult = service.addFormationsToCollectionIfMissing(formationsCollection, formations);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(formations);
        });

        it('should add only unique Formations to an array', () => {
          const formationsArray: IFormations[] = [{ id: 123 }, { id: 456 }, { id: 61430 }];
          const formationsCollection: IFormations[] = [{ id: 123 }];
          expectedResult = service.addFormationsToCollectionIfMissing(formationsCollection, ...formationsArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const formations: IFormations = { id: 123 };
          const formations2: IFormations = { id: 456 };
          expectedResult = service.addFormationsToCollectionIfMissing([], formations, formations2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(formations);
          expect(expectedResult).toContain(formations2);
        });

        it('should accept null and undefined values', () => {
          const formations: IFormations = { id: 123 };
          expectedResult = service.addFormationsToCollectionIfMissing([], null, formations, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(formations);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
