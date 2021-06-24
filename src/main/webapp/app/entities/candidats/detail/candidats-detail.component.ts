import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICandidats } from '../candidats.model';

@Component({
  selector: 'jhi-candidats-detail',
  templateUrl: './candidats-detail.component.html',
})
export class CandidatsDetailComponent implements OnInit {
  candidats: ICandidats | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ candidats }) => {
      this.candidats = candidats;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
