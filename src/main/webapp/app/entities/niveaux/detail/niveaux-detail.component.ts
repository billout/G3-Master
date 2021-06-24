import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INiveaux } from '../niveaux.model';

@Component({
  selector: 'jhi-niveaux-detail',
  templateUrl: './niveaux-detail.component.html',
})
export class NiveauxDetailComponent implements OnInit {
  niveaux: INiveaux | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ niveaux }) => {
      this.niveaux = niveaux;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
