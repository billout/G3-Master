import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IConcours, Concours } from '../concours.model';
import { ConcoursService } from '../service/concours.service';
import { ICandidats } from 'app/entities/candidats/candidats.model';
import { CandidatsService } from 'app/entities/candidats/service/candidats.service';

@Component({
  selector: 'jhi-concours-update',
  templateUrl: './concours-update.component.html',
})
export class ConcoursUpdateComponent implements OnInit {
  isSaving = false;

  candidatsSharedCollection: ICandidats[] = [];

  editForm = this.fb.group({
    id: [],
    code: [],
    libelle: [],
    dtOuverture: [],
    dtCloture: [],
    candidats: [],
  });

  constructor(
    protected concoursService: ConcoursService,
    protected candidatsService: CandidatsService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ concours }) => {
      this.updateForm(concours);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const concours = this.createFromForm();
    if (concours.id !== undefined) {
      this.subscribeToSaveResponse(this.concoursService.update(concours));
    } else {
      this.subscribeToSaveResponse(this.concoursService.create(concours));
    }
  }

  trackCandidatsById(index: number, item: ICandidats): number {
    return item.id!;
  }

  getSelectedCandidats(option: ICandidats, selectedVals?: ICandidats[]): ICandidats {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConcours>>): void {
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

  protected updateForm(concours: IConcours): void {
    this.editForm.patchValue({
      id: concours.id,
      code: concours.code,
      libelle: concours.libelle,
      dtOuverture: concours.dtOuverture,
      dtCloture: concours.dtCloture,
      candidats: concours.candidats,
    });

    this.candidatsSharedCollection = this.candidatsService.addCandidatsToCollectionIfMissing(
      this.candidatsSharedCollection,
      ...(concours.candidats ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.candidatsService
      .query()
      .pipe(map((res: HttpResponse<ICandidats[]>) => res.body ?? []))
      .pipe(
        map((candidats: ICandidats[]) =>
          this.candidatsService.addCandidatsToCollectionIfMissing(candidats, ...(this.editForm.get('candidats')!.value ?? []))
        )
      )
      .subscribe((candidats: ICandidats[]) => (this.candidatsSharedCollection = candidats));
  }

  protected createFromForm(): IConcours {
    return {
      ...new Concours(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      dtOuverture: this.editForm.get(['dtOuverture'])!.value,
      dtCloture: this.editForm.get(['dtCloture'])!.value,
      candidats: this.editForm.get(['candidats'])!.value,
    };
  }
}
