import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFormations, getFormationsIdentifier } from '../formations.model';

export type EntityResponseType = HttpResponse<IFormations>;
export type EntityArrayResponseType = HttpResponse<IFormations[]>;

@Injectable({ providedIn: 'root' })
export class FormationsService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/formations');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(formations: IFormations): Observable<EntityResponseType> {
    return this.http.post<IFormations>(this.resourceUrl, formations, { observe: 'response' });
  }

  update(formations: IFormations): Observable<EntityResponseType> {
    return this.http.put<IFormations>(`${this.resourceUrl}/${getFormationsIdentifier(formations) as number}`, formations, {
      observe: 'response',
    });
  }

  partialUpdate(formations: IFormations): Observable<EntityResponseType> {
    return this.http.patch<IFormations>(`${this.resourceUrl}/${getFormationsIdentifier(formations) as number}`, formations, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFormations>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFormations[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addFormationsToCollectionIfMissing(
    formationsCollection: IFormations[],
    ...formationsToCheck: (IFormations | null | undefined)[]
  ): IFormations[] {
    const formations: IFormations[] = formationsToCheck.filter(isPresent);
    if (formations.length > 0) {
      const formationsCollectionIdentifiers = formationsCollection.map(formationsItem => getFormationsIdentifier(formationsItem)!);
      const formationsToAdd = formations.filter(formationsItem => {
        const formationsIdentifier = getFormationsIdentifier(formationsItem);
        if (formationsIdentifier == null || formationsCollectionIdentifiers.includes(formationsIdentifier)) {
          return false;
        }
        formationsCollectionIdentifiers.push(formationsIdentifier);
        return true;
      });
      return [...formationsToAdd, ...formationsCollection];
    }
    return formationsCollection;
  }
}
