import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IVoieAcces, VoieAcces } from '../voie-acces.model';
import { VoieAccesService } from '../service/voie-acces.service';
import { IConcours } from 'app/entities/concours/concours.model';
import { ConcoursService } from 'app/entities/concours/service/concours.service';

@Component({
  selector: 'jhi-voie-acces-update',
  templateUrl: './voie-acces-update.component.html',
})
export class VoieAccesUpdateComponent implements OnInit {
  isSaving = false;

  concoursSharedCollection: IConcours[] = [];

  editForm = this.fb.group({
    id: [],
    libelle: [],
    code: [],
    concours: [],
  });

  constructor(
    protected voieAccesService: VoieAccesService,
    protected concoursService: ConcoursService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ voieAcces }) => {
      this.updateForm(voieAcces);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const voieAcces = this.createFromForm();
    if (voieAcces.id !== undefined) {
      this.subscribeToSaveResponse(this.voieAccesService.update(voieAcces));
    } else {
      this.subscribeToSaveResponse(this.voieAccesService.create(voieAcces));
    }
  }

  trackConcoursById(index: number, item: IConcours): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVoieAcces>>): void {
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

  protected updateForm(voieAcces: IVoieAcces): void {
    this.editForm.patchValue({
      id: voieAcces.id,
      libelle: voieAcces.libelle,
      code: voieAcces.code,
      concours: voieAcces.concours,
    });

    this.concoursSharedCollection = this.concoursService.addConcoursToCollectionIfMissing(
      this.concoursSharedCollection,
      voieAcces.concours
    );
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
  }

  protected createFromForm(): IVoieAcces {
    return {
      ...new VoieAcces(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      code: this.editForm.get(['code'])!.value,
      concours: this.editForm.get(['concours'])!.value,
    };
  }
}
