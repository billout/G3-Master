import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INiveaux, Niveaux } from '../niveaux.model';
import { NiveauxService } from '../service/niveaux.service';

@Injectable({ providedIn: 'root' })
export class NiveauxRoutingResolveService implements Resolve<INiveaux> {
  constructor(protected service: NiveauxService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INiveaux> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((niveaux: HttpResponse<Niveaux>) => {
          if (niveaux.body) {
            return of(niveaux.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Niveaux());
  }
}
