import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICandidats } from '../candidats.model';
import { CandidatsService } from '../service/candidats.service';
import { CandidatsDeleteDialogComponent } from '../delete/candidats-delete-dialog.component';

@Component({
  selector: 'jhi-candidats',
  templateUrl: './candidats.component.html',
})
export class CandidatsComponent implements OnInit {
  candidats?: ICandidats[];
  isLoading = false;

  constructor(protected candidatsService: CandidatsService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.candidatsService.query().subscribe(
      (res: HttpResponse<ICandidats[]>) => {
        this.isLoading = false;
        this.candidats = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ICandidats): number {
    return item.id!;
  }

  delete(candidats: ICandidats): void {
    const modalRef = this.modalService.open(CandidatsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.candidats = candidats;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
