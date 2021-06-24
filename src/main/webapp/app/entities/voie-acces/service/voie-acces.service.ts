import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IVoieAcces, getVoieAccesIdentifier } from '../voie-acces.model';

export type EntityResponseType = HttpResponse<IVoieAcces>;
export type EntityArrayResponseType = HttpResponse<IVoieAcces[]>;

@Injectable({ providedIn: 'root' })
export class VoieAccesService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/voie-acces');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(voieAcces: IVoieAcces): Observable<EntityResponseType> {
    return this.http.post<IVoieAcces>(this.resourceUrl, voieAcces, { observe: 'response' });
  }

  update(voieAcces: IVoieAcces): Observable<EntityResponseType> {
    return this.http.put<IVoieAcces>(`${this.resourceUrl}/${getVoieAccesIdentifier(voieAcces) as number}`, voieAcces, {
      observe: 'response',
    });
  }

  partialUpdate(voieAcces: IVoieAcces): Observable<EntityResponseType> {
    return this.http.patch<IVoieAcces>(`${this.resourceUrl}/${getVoieAccesIdentifier(voieAcces) as number}`, voieAcces, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVoieAcces>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVoieAcces[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addVoieAccesToCollectionIfMissing(
    voieAccesCollection: IVoieAcces[],
    ...voieAccesToCheck: (IVoieAcces | null | undefined)[]
  ): IVoieAcces[] {
    const voieAcces: IVoieAcces[] = voieAccesToCheck.filter(isPresent);
    if (voieAcces.length > 0) {
      const voieAccesCollectionIdentifiers = voieAccesCollection.map(voieAccesItem => getVoieAccesIdentifier(voieAccesItem)!);
      const voieAccesToAdd = voieAcces.filter(voieAccesItem => {
        const voieAccesIdentifier = getVoieAccesIdentifier(voieAccesItem);
        if (voieAccesIdentifier == null || voieAccesCollectionIdentifiers.includes(voieAccesIdentifier)) {
          return false;
        }
        voieAccesCollectionIdentifiers.push(voieAccesIdentifier);
        return true;
      });
      return [...voieAccesToAdd, ...voieAccesCollection];
    }
    return voieAccesCollection;
  }
}
