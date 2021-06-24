jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IJury, Jury } from '../jury.model';
import { JuryService } from '../service/jury.service';

import { JuryRoutingResolveService } from './jury-routing-resolve.service';

describe('Service Tests', () => {
  describe('Jury routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: JuryRoutingResolveService;
    let service: JuryService;
    let resultJury: IJury | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(JuryRoutingResolveService);
      service = TestBed.inject(JuryService);
      resultJury = undefined;
    });

    describe('resolve', () => {
      it('should return IJury returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultJury = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultJury).toEqual({ id: 123 });
      });

      it('should return new IJury if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultJury = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultJury).toEqual(new Jury());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultJury = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultJury).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
