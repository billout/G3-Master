import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEpreuves } from '../epreuves.model';
import { EpreuvesService } from '../service/epreuves.service';
import { EpreuvesDeleteDialogComponent } from '../delete/epreuves-delete-dialog.component';

@Component({
  selector: 'jhi-epreuves',
  templateUrl: './epreuves.component.html',
})
export class EpreuvesComponent implements OnInit {
  epreuves?: IEpreuves[];
  isLoading = false;

  constructor(protected epreuvesService: EpreuvesService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.epreuvesService.query().subscribe(
      (res: HttpResponse<IEpreuves[]>) => {
        this.isLoading = false;
        this.epreuves = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IEpreuves): number {
    return item.id!;
  }

  delete(epreuves: IEpreuves): void {
    const modalRef = this.modalService.open(EpreuvesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.epreuves = epreuves;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
