import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IEpreuves, Epreuves } from '../epreuves.model';
import { EpreuvesService } from '../service/epreuves.service';
import { IConcours } from 'app/entities/concours/concours.model';
import { ConcoursService } from 'app/entities/concours/service/concours.service';

@Component({
  selector: 'jhi-epreuves-update',
  templateUrl: './epreuves-update.component.html',
})
export class EpreuvesUpdateComponent implements OnInit {
  isSaving = false;

  concoursSharedCollection: IConcours[] = [];

  editForm = this.fb.group({
    id: [],
    code: [],
    libelle: [],
    type: [],
    compose: [],
  });

  constructor(
    protected epreuvesService: EpreuvesService,
    protected concoursService: ConcoursService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ epreuves }) => {
      this.updateForm(epreuves);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const epreuves = this.createFromForm();
    if (epreuves.id !== undefined) {
      this.subscribeToSaveResponse(this.epreuvesService.update(epreuves));
    } else {
      this.subscribeToSaveResponse(this.epreuvesService.create(epreuves));
    }
  }

  trackConcoursById(index: number, item: IConcours): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEpreuves>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(epreuves: IEpreuves): void {
    this.editForm.patchValue({
      id: epreuves.id,
      code: epreuves.code,
      libelle: epreuves.libelle,
      type: epreuves.type,
      compose: epreuves.compose,
    });

    this.concoursSharedCollection = this.concoursService.addConcoursToCollectionIfMissing(this.concoursSharedCollection, epreuves.compose);
  }

  protected loadRelationshipsOptions(): void {
    this.concoursService
      .query()
      .pipe(map((res: HttpResponse<IConcours[]>) => res.body ?? []))
      .pipe(
        map((concours: IConcours[]) => this.concoursService.addConcoursToCollectionIfMissing(concours, this.editForm.get('compose')!.value))
      )
      .subscribe((concours: IConcours[]) => (this.concoursSharedCollection = concours));
  }

  protected createFromForm(): IEpreuves {
    return {
      ...new Epreuves(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      type: this.editForm.get(['type'])!.value,
      compose: this.editForm.get(['compose'])!.value,
    };
  }
}
