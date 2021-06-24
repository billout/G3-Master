import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IConcours } from '../concours.model';
import { ConcoursService } from '../service/concours.service';
import { ConcoursDeleteDialogComponent } from '../delete/concours-delete-dialog.component';

@Component({
  selector: 'jhi-concours',
  templateUrl: './concours.component.html',
})
export class ConcoursComponent implements OnInit {
  concours?: IConcours[];
  isLoading = false;

  constructor(protected concoursService: ConcoursService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.concoursService.query().subscribe(
      (res: HttpResponse<IConcours[]>) => {
        this.isLoading = false;
        this.concours = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IConcours): number {
    return item.id!;
  }

  delete(concours: IConcours): void {
    const modalRef = this.modalService.open(ConcoursDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.concours = concours;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
