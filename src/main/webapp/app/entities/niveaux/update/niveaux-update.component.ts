import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { INiveaux, Niveaux } from '../niveaux.model';
import { NiveauxService } from '../service/niveaux.service';

@Component({
  selector: 'jhi-niveaux-update',
  templateUrl: './niveaux-update.component.html',
})
export class NiveauxUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    libelle: [],
    code: [],
  });

  constructor(protected niveauxService: NiveauxService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ niveaux }) => {
      this.updateForm(niveaux);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const niveaux = this.createFromForm();
    if (niveaux.id !== undefined) {
      this.subscribeToSaveResponse(this.niveauxService.update(niveaux));
    } else {
      this.subscribeToSaveResponse(this.niveauxService.create(niveaux));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INiveaux>>): void {
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

  protected updateForm(niveaux: INiveaux): void {
    this.editForm.patchValue({
      id: niveaux.id,
      libelle: niveaux.libelle,
      code: niveaux.code,
    });
  }

  protected createFromForm(): INiveaux {
    return {
      ...new Niveaux(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      code: this.editForm.get(['code'])!.value,
    };
  }
}
