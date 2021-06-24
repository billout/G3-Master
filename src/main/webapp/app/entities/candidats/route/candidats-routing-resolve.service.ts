import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICandidats, Candidats } from '../candidats.model';
import { CandidatsService } from '../service/candidats.service';

@Injectable({ providedIn: 'root' })
export class CandidatsRoutingResolveService implements Resolve<ICandidats> {
  constructor(protected service: CandidatsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICandidats> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((candidats: HttpResponse<Candidats>) => {
          if (candidats.body) {
            return of(candidats.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Candidats());
  }
}
