import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IVoieAcces, VoieAcces } from '../voie-acces.model';

import { VoieAccesService } from './voie-acces.service';

describe('Service Tests', () => {
  describe('VoieAcces Service', () => {
    let service: VoieAccesService;
    let httpMock: HttpTestingController;
    let elemDefault: IVoieAcces;
    let expectedResult: IVoieAcces | IVoieAcces[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(VoieAccesService);
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

      it('should create a VoieAcces', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new VoieAcces()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a VoieAcces', () => {
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

      it('should partial update a VoieAcces', () => {
        const patchObject = Object.assign(
          {
            code: 'BBBBBB',
          },
          new VoieAcces()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of VoieAcces', () => {
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

      it('should delete a VoieAcces', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addVoieAccesToCollectionIfMissing', () => {
        it('should add a VoieAcces to an empty array', () => {
          const voieAcces: IVoieAcces = { id: 123 };
          expectedResult = service.addVoieAccesToCollectionIfMissing([], voieAcces);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(voieAcces);
        });

        it('should not add a VoieAcces to an array that contains it', () => {
          const voieAcces: IVoieAcces = { id: 123 };
          const voieAccesCollection: IVoieAcces[] = [
            {
              ...voieAcces,
            },
            { id: 456 },
          ];
          expectedResult = service.addVoieAccesToCollectionIfMissing(voieAccesCollection, voieAcces);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a VoieAcces to an array that doesn't contain it", () => {
          const voieAcces: IVoieAcces = { id: 123 };
          const voieAccesCollection: IVoieAcces[] = [{ id: 456 }];
          expectedResult = service.addVoieAccesToCollectionIfMissing(voieAccesCollection, voieAcces);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(voieAcces);
        });

        it('should add only unique VoieAcces to an array', () => {
          const voieAccesArray: IVoieAcces[] = [{ id: 123 }, { id: 456 }, { id: 41935 }];
          const voieAccesCollection: IVoieAcces[] = [{ id: 123 }];
          expectedResult = service.addVoieAccesToCollectionIfMissing(voieAccesCollection, ...voieAccesArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const voieAcces: IVoieAcces = { id: 123 };
          const voieAcces2: IVoieAcces = { id: 456 };
          expectedResult = service.addVoieAccesToCollectionIfMissing([], voieAcces, voieAcces2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(voieAcces);
          expect(expectedResult).toContain(voieAcces2);
        });

        it('should accept null and undefined values', () => {
          const voieAcces: IVoieAcces = { id: 123 };
          expectedResult = service.addVoieAccesToCollectionIfMissing([], null, voieAcces, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(voieAcces);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
