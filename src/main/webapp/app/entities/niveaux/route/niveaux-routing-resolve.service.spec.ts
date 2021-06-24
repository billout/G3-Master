jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { INiveaux, Niveaux } from '../niveaux.model';
import { NiveauxService } from '../service/niveaux.service';

import { NiveauxRoutingResolveService } from './niveaux-routing-resolve.service';

describe('Service Tests', () => {
  describe('Niveaux routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: NiveauxRoutingResolveService;
    let service: NiveauxService;
    let resultNiveaux: INiveaux | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(NiveauxRoutingResolveService);
      service = TestBed.inject(NiveauxService);
      resultNiveaux = undefined;
    });

    describe('resolve', () => {
      it('should return INiveaux returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultNiveaux = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultNiveaux).toEqual({ id: 123 });
      });

      it('should return new INiveaux if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultNiveaux = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultNiveaux).toEqual(new Niveaux());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultNiveaux = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultNiveaux).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
