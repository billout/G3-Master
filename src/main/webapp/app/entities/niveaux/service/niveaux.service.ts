import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INiveaux, getNiveauxIdentifier } from '../niveaux.model';

export type EntityResponseType = HttpResponse<INiveaux>;
export type EntityArrayResponseType = HttpResponse<INiveaux[]>;

@Injectable({ providedIn: 'root' })
export class NiveauxService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/niveaux');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(niveaux: INiveaux): Observable<EntityResponseType> {
    return this.http.post<INiveaux>(this.resourceUrl, niveaux, { observe: 'response' });
  }

  update(niveaux: INiveaux): Observable<EntityResponseType> {
    return this.http.put<INiveaux>(`${this.resourceUrl}/${getNiveauxIdentifier(niveaux) as number}`, niveaux, { observe: 'response' });
  }

  partialUpdate(niveaux: INiveaux): Observable<EntityResponseType> {
    return this.http.patch<INiveaux>(`${this.resourceUrl}/${getNiveauxIdentifier(niveaux) as number}`, niveaux, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INiveaux>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INiveaux[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addNiveauxToCollectionIfMissing(niveauxCollection: INiveaux[], ...niveauxToCheck: (INiveaux | null | undefined)[]): INiveaux[] {
    const niveaux: INiveaux[] = niveauxToCheck.filter(isPresent);
    if (niveaux.length > 0) {
      const niveauxCollectionIdentifiers = niveauxCollection.map(niveauxItem => getNiveauxIdentifier(niveauxItem)!);
      const niveauxToAdd = niveaux.filter(niveauxItem => {
        const niveauxIdentifier = getNiveauxIdentifier(niveauxItem);
        if (niveauxIdentifier == null || niveauxCollectionIdentifiers.includes(niveauxIdentifier)) {
          return false;
        }
        niveauxCollectionIdentifiers.push(niveauxIdentifier);
        return true;
      });
      return [...niveauxToAdd, ...niveauxCollection];
    }
    return niveauxCollection;
  }
}
