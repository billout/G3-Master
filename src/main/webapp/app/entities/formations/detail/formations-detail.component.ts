import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFormations } from '../formations.model';

@Component({
  selector: 'jhi-formations-detail',
  templateUrl: './formations-detail.component.html',
})
export class FormationsDetailComponent implements OnInit {
  formations: IFormations | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ formations }) => {
      this.formations = formations;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
