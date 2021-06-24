import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFormations, Formations } from '../formations.model';
import { FormationsService } from '../service/formations.service';

@Injectable({ providedIn: 'root' })
export class FormationsRoutingResolveService implements Resolve<IFormations> {
  constructor(protected service: FormationsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFormations> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((formations: HttpResponse<Formations>) => {
          if (formations.body) {
            return of(formations.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Formations());
  }
}
