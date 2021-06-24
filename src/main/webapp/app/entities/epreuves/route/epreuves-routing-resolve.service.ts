import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEpreuves, Epreuves } from '../epreuves.model';
import { EpreuvesService } from '../service/epreuves.service';

@Injectable({ providedIn: 'root' })
export class EpreuvesRoutingResolveService implements Resolve<IEpreuves> {
  constructor(protected service: EpreuvesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEpreuves> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((epreuves: HttpResponse<Epreuves>) => {
          if (epreuves.body) {
            return of(epreuves.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Epreuves());
  }
}
