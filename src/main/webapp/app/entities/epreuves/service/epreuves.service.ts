import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEpreuves, getEpreuvesIdentifier } from '../epreuves.model';

export type EntityResponseType = HttpResponse<IEpreuves>;
export type EntityArrayResponseType = HttpResponse<IEpreuves[]>;

@Injectable({ providedIn: 'root' })
export class EpreuvesService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/epreuves');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(epreuves: IEpreuves): Observable<EntityResponseType> {
    return this.http.post<IEpreuves>(this.resourceUrl, epreuves, { observe: 'response' });
  }

  update(epreuves: IEpreuves): Observable<EntityResponseType> {
    return this.http.put<IEpreuves>(`${this.resourceUrl}/${getEpreuvesIdentifier(epreuves) as number}`, epreuves, { observe: 'response' });
  }

  partialUpdate(epreuves: IEpreuves): Observable<EntityResponseType> {
    return this.http.patch<IEpreuves>(`${this.resourceUrl}/${getEpreuvesIdentifier(epreuves) as number}`, epreuves, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEpreuves>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEpreuves[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEpreuvesToCollectionIfMissing(epreuvesCollection: IEpreuves[], ...epreuvesToCheck: (IEpreuves | null | undefined)[]): IEpreuves[] {
    const epreuves: IEpreuves[] = epreuvesToCheck.filter(isPresent);
    if (epreuves.length > 0) {
      const epreuvesCollectionIdentifiers = epreuvesCollection.map(epreuvesItem => getEpreuvesIdentifier(epreuvesItem)!);
      const epreuvesToAdd = epreuves.filter(epreuvesItem => {
        const epreuvesIdentifier = getEpreuvesIdentifier(epreuvesItem);
        if (epreuvesIdentifier == null || epreuvesCollectionIdentifiers.includes(epreuvesIdentifier)) {
          return false;
        }
        epreuvesCollectionIdentifiers.push(epreuvesIdentifier);
        return true;
      });
      return [...epreuvesToAdd, ...epreuvesCollection];
    }
    return epreuvesCollection;
  }
}
