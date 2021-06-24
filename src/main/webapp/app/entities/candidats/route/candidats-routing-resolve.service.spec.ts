jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ICandidats, Candidats } from '../candidats.model';
import { CandidatsService } from '../service/candidats.service';

import { CandidatsRoutingResolveService } from './candidats-routing-resolve.service';

describe('Service Tests', () => {
  describe('Candidats routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: CandidatsRoutingResolveService;
    let service: CandidatsService;
    let resultCandidats: ICandidats | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(CandidatsRoutingResolveService);
      service = TestBed.inject(CandidatsService);
      resultCandidats = undefined;
    });

    describe('resolve', () => {
      it('should return ICandidats returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCandidats = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCandidats).toEqual({ id: 123 });
      });

      it('should return new ICandidats if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCandidats = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultCandidats).toEqual(new Candidats());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCandidats = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCandidats).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
