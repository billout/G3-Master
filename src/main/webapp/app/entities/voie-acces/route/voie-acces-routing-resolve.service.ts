import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IVoieAcces, VoieAcces } from '../voie-acces.model';
import { VoieAccesService } from '../service/voie-acces.service';

@Injectable({ providedIn: 'root' })
export class VoieAccesRoutingResolveService implements Resolve<IVoieAcces> {
  constructor(protected service: VoieAccesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVoieAcces> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((voieAcces: HttpResponse<VoieAcces>) => {
          if (voieAcces.body) {
            return of(voieAcces.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new VoieAcces());
  }
}
