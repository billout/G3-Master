import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEpreuves } from '../epreuves.model';

@Component({
  selector: 'jhi-epreuves-detail',
  templateUrl: './epreuves-detail.component.html',
})
export class EpreuvesDetailComponent implements OnInit {
  epreuves: IEpreuves | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ epreuves }) => {
      this.epreuves = epreuves;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
