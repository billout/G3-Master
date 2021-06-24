jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IPersonnes, Personnes } from '../personnes.model';
import { PersonnesService } from '../service/personnes.service';

import { PersonnesRoutingResolveService } from './personnes-routing-resolve.service';

describe('Service Tests', () => {
  describe('Personnes routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: PersonnesRoutingResolveService;
    let service: PersonnesService;
    let resultPersonnes: IPersonnes | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(PersonnesRoutingResolveService);
      service = TestBed.inject(PersonnesService);
      resultPersonnes = undefined;
    });

    describe('resolve', () => {
      it('should return IPersonnes returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPersonnes = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultPersonnes).toEqual({ id: 123 });
      });

      it('should return new IPersonnes if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPersonnes = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultPersonnes).toEqual(new Personnes());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPersonnes = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultPersonnes).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
