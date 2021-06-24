import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ICandidats, Candidats } from '../candidats.model';
import { CandidatsService } from '../service/candidats.service';
import { IPersonnes } from 'app/entities/personnes/personnes.model';
import { PersonnesService } from 'app/entities/personnes/service/personnes.service';

@Component({
  selector: 'jhi-candidats-update',
  templateUrl: './candidats-update.component.html',
})
export class CandidatsUpdateComponent implements OnInit {
  isSaving = false;

  personnesSharedCollection: IPersonnes[] = [];

  editForm = this.fb.group({
    id: [],
    identifiant: [],
    etat: [],
    est: [],
  });

  constructor(
    protected candidatsService: CandidatsService,
    protected personnesService: PersonnesService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ candidats }) => {
      this.updateForm(candidats);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const candidats = this.createFromForm();
    if (candidats.id !== undefined) {
      this.subscribeToSaveResponse(this.candidatsService.update(candidats));
    } else {
      this.subscribeToSaveResponse(this.candidatsService.create(candidats));
    }
  }

  trackPersonnesById(index: number, item: IPersonnes): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICandidats>>): void {
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

  protected updateForm(candidats: ICandidats): void {
    this.editForm.patchValue({
      id: candidats.id,
      identifiant: candidats.identifiant,
      etat: candidats.etat,
      est: candidats.est,
    });

    this.personnesSharedCollection = this.personnesService.addPersonnesToCollectionIfMissing(this.personnesSharedCollection, candidats.est);
  }

  protected loadRelationshipsOptions(): void {
    this.personnesService
      .query()
      .pipe(map((res: HttpResponse<IPersonnes[]>) => res.body ?? []))
      .pipe(
        map((personnes: IPersonnes[]) =>
          this.personnesService.addPersonnesToCollectionIfMissing(personnes, this.editForm.get('est')!.value)
        )
      )
      .subscribe((personnes: IPersonnes[]) => (this.personnesSharedCollection = personnes));
  }

  protected createFromForm(): ICandidats {
    return {
      ...new Candidats(),
      id: this.editForm.get(['id'])!.value,
      identifiant: this.editForm.get(['identifiant'])!.value,
      etat: this.editForm.get(['etat'])!.value,
      est: this.editForm.get(['est'])!.value,
    };
  }
}
