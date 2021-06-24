import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVoieAcces } from '../voie-acces.model';

@Component({
  selector: 'jhi-voie-acces-detail',
  templateUrl: './voie-acces-detail.component.html',
})
export class VoieAccesDetailComponent implements OnInit {
  voieAcces: IVoieAcces | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ voieAcces }) => {
      this.voieAcces = voieAcces;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
