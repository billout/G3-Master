import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IFormations, Formations } from '../formations.model';
import { FormationsService } from '../service/formations.service';
import { IConcours } from 'app/entities/concours/concours.model';
import { ConcoursService } from 'app/entities/concours/service/concours.service';
import { INiveaux } from 'app/entities/niveaux/niveaux.model';
import { NiveauxService } from 'app/entities/niveaux/service/niveaux.service';

@Component({
  selector: 'jhi-formations-update',
  templateUrl: './formations-update.component.html',
})
export class FormationsUpdateComponent implements OnInit {
  isSaving = false;

  concoursSharedCollection: IConcours[] = [];
  niveauxSharedCollection: INiveaux[] = [];

  editForm = this.fb.group({
    id: [],
    libelle: [],
    code: [],
    concours: [],
    niveaux: [],
  });

  constructor(
    protected formationsService: FormationsService,
    protected concoursService: ConcoursService,
    protected niveauxService: NiveauxService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ formations }) => {
      this.updateForm(formations);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const formations = this.createFromForm();
    if (formations.id !== undefined) {
      this.subscribeToSaveResponse(this.formationsService.update(formations));
    } else {
      this.subscribeToSaveResponse(this.formationsService.create(formations));
    }
  }

  trackConcoursById(index: number, item: IConcours): number {
    return item.id!;
  }

  trackNiveauxById(index: number, item: INiveaux): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFormations>>): void {
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

  protected updateForm(formations: IFormations): void {
    this.editForm.patchValue({
      id: formations.id,
      libelle: formations.libelle,
      code: formations.code,
      concours: formations.concours,
      niveaux: formations.niveaux,
    });

    this.concoursSharedCollection = this.concoursService.addConcoursToCollectionIfMissing(
      this.concoursSharedCollection,
      formations.concours
    );
    this.niveauxSharedCollection = this.niveauxService.addNiveauxToCollectionIfMissing(this.niveauxSharedCollection, formations.niveaux);
  }

  protected loadRelationshipsOptions(): void {
    this.concoursService
      .query()
      .pipe(map((res: HttpResponse<IConcours[]>) => res.body ?? []))
      .pipe(
        map((concours: IConcours[]) =>
          this.concoursService.addConcoursToCollectionIfMissing(concours, this.editForm.get('concours')!.value)
        )
      )
      .subscribe((concours: IConcours[]) => (this.concoursSharedCollection = concours));

    this.niveauxService
      .query()
      .pipe(map((res: HttpResponse<INiveaux[]>) => res.body ?? []))
      .pipe(map((niveaux: INiveaux[]) => this.niveauxService.addNiveauxToCollectionIfMissing(niveaux, this.editForm.get('niveaux')!.value)))
      .subscribe((niveaux: INiveaux[]) => (this.niveauxSharedCollection = niveaux));
  }

  protected createFromForm(): IFormations {
    return {
      ...new Formations(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      code: this.editForm.get(['code'])!.value,
      concours: this.editForm.get(['concours'])!.value,
      niveaux: this.editForm.get(['niveaux'])!.value,
    };
  }
}
