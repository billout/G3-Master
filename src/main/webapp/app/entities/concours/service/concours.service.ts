import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IConcours, getConcoursIdentifier } from '../concours.model';

export type EntityResponseType = HttpResponse<IConcours>;
export type EntityArrayResponseType = HttpResponse<IConcours[]>;

@Injectable({ providedIn: 'root' })
export class ConcoursService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/concours');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(concours: IConcours): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(concours);
    return this.http
      .post<IConcours>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(concours: IConcours): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(concours);
    return this.http
      .put<IConcours>(`${this.resourceUrl}/${getConcoursIdentifier(concours) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(concours: IConcours): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(concours);
    return this.http
      .patch<IConcours>(`${this.resourceUrl}/${getConcoursIdentifier(concours) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IConcours>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IConcours[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addConcoursToCollectionIfMissing(concoursCollection: IConcours[], ...concoursToCheck: (IConcours | null | undefined)[]): IConcours[] {
    const concours: IConcours[] = concoursToCheck.filter(isPresent);
    if (concours.length > 0) {
      const concoursCollectionIdentifiers = concoursCollection.map(concoursItem => getConcoursIdentifier(concoursItem)!);
      const concoursToAdd = concours.filter(concoursItem => {
        const concoursIdentifier = getConcoursIdentifier(concoursItem);
        if (concoursIdentifier == null || concoursCollectionIdentifiers.includes(concoursIdentifier)) {
          return false;
        }
        concoursCollectionIdentifiers.push(concoursIdentifier);
        return true;
      });
      return [...concoursToAdd, ...concoursCollection];
    }
    return concoursCollection;
  }

  protected convertDateFromClient(concours: IConcours): IConcours {
    return Object.assign({}, concours, {
      dtOuverture: concours.dtOuverture?.isValid() ? concours.dtOuverture.format(DATE_FORMAT) : undefined,
      dtCloture: concours.dtCloture?.isValid() ? concours.dtCloture.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dtOuverture = res.body.dtOuverture ? dayjs(res.body.dtOuverture) : undefined;
      res.body.dtCloture = res.body.dtCloture ? dayjs(res.body.dtCloture) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((concours: IConcours) => {
        concours.dtOuverture = concours.dtOuverture ? dayjs(concours.dtOuverture) : undefined;
        concours.dtCloture = concours.dtCloture ? dayjs(concours.dtCloture) : undefined;
      });
    }
    return res;
  }
}
