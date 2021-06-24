import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IVoieAcces } from '../voie-acces.model';
import { VoieAccesService } from '../service/voie-acces.service';
import { VoieAccesDeleteDialogComponent } from '../delete/voie-acces-delete-dialog.component';

@Component({
  selector: 'jhi-voie-acces',
  templateUrl: './voie-acces.component.html',
})
export class VoieAccesComponent implements OnInit {
  voieAcces?: IVoieAcces[];
  isLoading = false;

  constructor(protected voieAccesService: VoieAccesService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.voieAccesService.query().subscribe(
      (res: HttpResponse<IVoieAcces[]>) => {
        this.isLoading = false;
        this.voieAcces = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IVoieAcces): number {
    return item.id!;
  }

  delete(voieAcces: IVoieAcces): void {
    const modalRef = this.modalService.open(VoieAccesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.voieAcces = voieAcces;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
