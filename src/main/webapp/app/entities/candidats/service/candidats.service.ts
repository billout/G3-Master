import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICandidats, getCandidatsIdentifier } from '../candidats.model';

export type EntityResponseType = HttpResponse<ICandidats>;
export type EntityArrayResponseType = HttpResponse<ICandidats[]>;

@Injectable({ providedIn: 'root' })
export class CandidatsService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/candidats');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(candidats: ICandidats): Observable<EntityResponseType> {
    return this.http.post<ICandidats>(this.resourceUrl, candidats, { observe: 'response' });
  }

  update(candidats: ICandidats): Observable<EntityResponseType> {
    return this.http.put<ICandidats>(`${this.resourceUrl}/${getCandidatsIdentifier(candidats) as number}`, candidats, {
      observe: 'response',
    });
  }

  partialUpdate(candidats: ICandidats): Observable<EntityResponseType> {
    return this.http.patch<ICandidats>(`${this.resourceUrl}/${getCandidatsIdentifier(candidats) as number}`, candidats, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICandidats>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICandidats[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCandidatsToCollectionIfMissing(
    candidatsCollection: ICandidats[],
    ...candidatsToCheck: (ICandidats | null | undefined)[]
  ): ICandidats[] {
    const candidats: ICandidats[] = candidatsToCheck.filter(isPresent);
    if (candidats.length > 0) {
      const candidatsCollectionIdentifiers = candidatsCollection.map(candidatsItem => getCandidatsIdentifier(candidatsItem)!);
      const candidatsToAdd = candidats.filter(candidatsItem => {
        const candidatsIdentifier = getCandidatsIdentifier(candidatsItem);
        if (candidatsIdentifier == null || candidatsCollectionIdentifiers.includes(candidatsIdentifier)) {
          return false;
        }
        candidatsCollectionIdentifiers.push(candidatsIdentifier);
        return true;
      });
      return [...candidatsToAdd, ...candidatsCollection];
    }
    return candidatsCollection;
  }
}
