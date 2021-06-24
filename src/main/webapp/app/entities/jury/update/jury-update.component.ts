import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IJury, Jury } from '../jury.model';
import { JuryService } from '../service/jury.service';
import { IPersonnes } from 'app/entities/personnes/personnes.model';
import { PersonnesService } from 'app/entities/personnes/service/personnes.service';
import { IEpreuves } from 'app/entities/epreuves/epreuves.model';
import { EpreuvesService } from 'app/entities/epreuves/service/epreuves.service';

@Component({
  selector: 'jhi-jury-update',
  templateUrl: './jury-update.component.html',
})
export class JuryUpdateComponent implements OnInit {
  isSaving = false;

  presidentsCollection: IPersonnes[] = [];
  membre1sCollection: IPersonnes[] = [];
  membre2sCollection: IPersonnes[] = [];
  membre3sCollection: IPersonnes[] = [];
  corrigesCollection: IEpreuves[] = [];

  editForm = this.fb.group({
    id: [],
    libelle: [],
    president: [],
    membre1: [],
    membre2: [],
    membre3: [],
    corrige: [],
  });

  constructor(
    protected juryService: JuryService,
    protected personnesService: PersonnesService,
    protected epreuvesService: EpreuvesService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jury }) => {
      this.updateForm(jury);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const jury = this.createFromForm();
    if (jury.id !== undefined) {
      this.subscribeToSaveResponse(this.juryService.update(jury));
    } else {
      this.subscribeToSaveResponse(this.juryService.create(jury));
    }
  }

  trackPersonnesById(index: number, item: IPersonnes): number {
    return item.id!;
  }

  trackEpreuvesById(index: number, item: IEpreuves): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJury>>): void {
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

  protected updateForm(jury: IJury): void {
    this.editForm.patchValue({
      id: jury.id,
      libelle: jury.libelle,
      president: jury.president,
      membre1: jury.membre1,
      membre2: jury.membre2,
      membre3: jury.membre3,
      corrige: jury.corrige,
    });

    this.presidentsCollection = this.personnesService.addPersonnesToCollectionIfMissing(this.presidentsCollection, jury.president);
    this.membre1sCollection = this.personnesService.addPersonnesToCollectionIfMissing(this.membre1sCollection, jury.membre1);
    this.membre2sCollection = this.personnesService.addPersonnesToCollectionIfMissing(this.membre2sCollection, jury.membre2);
    this.membre3sCollection = this.personnesService.addPersonnesToCollectionIfMissing(this.membre3sCollection, jury.membre3);
    this.corrigesCollection = this.epreuvesService.addEpreuvesToCollectionIfMissing(this.corrigesCollection, jury.corrige);
  }

  protected loadRelationshipsOptions(): void {
    this.personnesService
      .query({ filter: 'jury-is-null' })
      .pipe(map((res: HttpResponse<IPersonnes[]>) => res.body ?? []))
      .pipe(
        map((personnes: IPersonnes[]) =>
          this.personnesService.addPersonnesToCollectionIfMissing(personnes, this.editForm.get('president')!.value)
        )
      )
      .subscribe((personnes: IPersonnes[]) => (this.presidentsCollection = personnes));

    this.personnesService
      .query({ filter: 'jury-is-null' })
      .pipe(map((res: HttpResponse<IPersonnes[]>) => res.body ?? []))
      .pipe(
        map((personnes: IPersonnes[]) =>
          this.personnesService.addPersonnesToCollectionIfMissing(personnes, this.editForm.get('membre1')!.value)
        )
      )
      .subscribe((personnes: IPersonnes[]) => (this.membre1sCollection = personnes));

    this.personnesService
      .query({ filter: 'jury-is-null' })
      .pipe(map((res: HttpResponse<IPersonnes[]>) => res.body ?? []))
      .pipe(
        map((personnes: IPersonnes[]) =>
          this.personnesService.addPersonnesToCollectionIfMissing(personnes, this.editForm.get('membre2')!.value)
        )
      )
      .subscribe((personnes: IPersonnes[]) => (this.membre2sCollection = personnes));

    this.personnesService
      .query({ filter: 'jury-is-null' })
      .pipe(map((res: HttpResponse<IPersonnes[]>) => res.body ?? []))
      .pipe(
        map((personnes: IPersonnes[]) =>
          this.personnesService.addPersonnesToCollectionIfMissing(personnes, this.editForm.get('membre3')!.value)
        )
      )
      .subscribe((personnes: IPersonnes[]) => (this.membre3sCollection = personnes));

    this.epreuvesService
      .query({ filter: 'jury-is-null' })
      .pipe(map((res: HttpResponse<IEpreuves[]>) => res.body ?? []))
      .pipe(
        map((epreuves: IEpreuves[]) => this.epreuvesService.addEpreuvesToCollectionIfMissing(epreuves, this.editForm.get('corrige')!.value))
      )
      .subscribe((epreuves: IEpreuves[]) => (this.corrigesCollection = epreuves));
  }

  protected createFromForm(): IJury {
    return {
      ...new Jury(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      president: this.editForm.get(['president'])!.value,
      membre1: this.editForm.get(['membre1'])!.value,
      membre2: this.editForm.get(['membre2'])!.value,
      membre3: this.editForm.get(['membre3'])!.value,
      corrige: this.editForm.get(['corrige'])!.value,
    };
  }
}
