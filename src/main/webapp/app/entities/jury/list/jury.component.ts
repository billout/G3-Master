import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IJury } from '../jury.model';
import { JuryService } from '../service/jury.service';
import { JuryDeleteDialogComponent } from '../delete/jury-delete-dialog.component';

@Component({
  selector: 'jhi-jury',
  templateUrl: './jury.component.html',
})
export class JuryComponent implements OnInit {
  juries?: IJury[];
  isLoading = false;

  constructor(protected juryService: JuryService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.juryService.query().subscribe(
      (res: HttpResponse<IJury[]>) => {
        this.isLoading = false;
        this.juries = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IJury): number {
    return item.id!;
  }

  delete(jury: IJury): void {
    const modalRef = this.modalService.open(JuryDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.jury = jury;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
