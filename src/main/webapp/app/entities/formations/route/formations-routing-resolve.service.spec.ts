jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IFormations, Formations } from '../formations.model';
import { FormationsService } from '../service/formations.service';

import { FormationsRoutingResolveService } from './formations-routing-resolve.service';

describe('Service Tests', () => {
  describe('Formations routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: FormationsRoutingResolveService;
    let service: FormationsService;
    let resultFormations: IFormations | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(FormationsRoutingResolveService);
      service = TestBed.inject(FormationsService);
      resultFormations = undefined;
    });

    describe('resolve', () => {
      it('should return IFormations returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFormations = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultFormations).toEqual({ id: 123 });
      });

      it('should return new IFormations if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFormations = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultFormations).toEqual(new Formations());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFormations = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultFormations).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
