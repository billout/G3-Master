import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { INiveaux } from '../niveaux.model';
import { NiveauxService } from '../service/niveaux.service';
import { NiveauxDeleteDialogComponent } from '../delete/niveaux-delete-dialog.component';

@Component({
  selector: 'jhi-niveaux',
  templateUrl: './niveaux.component.html',
})
export class NiveauxComponent implements OnInit {
  niveaux?: INiveaux[];
  isLoading = false;

  constructor(protected niveauxService: NiveauxService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.niveauxService.query().subscribe(
      (res: HttpResponse<INiveaux[]>) => {
        this.isLoading = false;
        this.niveaux = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: INiveaux): number {
    return item.id!;
  }

  delete(niveaux: INiveaux): void {
    const modalRef = this.modalService.open(NiveauxDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.niveaux = niveaux;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
