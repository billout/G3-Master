jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IEpreuves, Epreuves } from '../epreuves.model';
import { EpreuvesService } from '../service/epreuves.service';

import { EpreuvesRoutingResolveService } from './epreuves-routing-resolve.service';

describe('Service Tests', () => {
  describe('Epreuves routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: EpreuvesRoutingResolveService;
    let service: EpreuvesService;
    let resultEpreuves: IEpreuves | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(EpreuvesRoutingResolveService);
      service = TestBed.inject(EpreuvesService);
      resultEpreuves = undefined;
    });

    describe('resolve', () => {
      it('should return IEpreuves returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEpreuves = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultEpreuves).toEqual({ id: 123 });
      });

      it('should return new IEpreuves if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEpreuves = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultEpreuves).toEqual(new Epreuves());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEpreuves = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultEpreuves).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
