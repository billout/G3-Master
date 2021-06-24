import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFormations } from '../formations.model';
import { FormationsService } from '../service/formations.service';
import { FormationsDeleteDialogComponent } from '../delete/formations-delete-dialog.component';

@Component({
  selector: 'jhi-formations',
  templateUrl: './formations.component.html',
})
export class FormationsComponent implements OnInit {
  formations?: IFormations[];
  isLoading = false;

  constructor(protected formationsService: FormationsService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.formationsService.query().subscribe(
      (res: HttpResponse<IFormations[]>) => {
        this.isLoading = false;
        this.formations = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IFormations): number {
    return item.id!;
  }

  delete(formations: IFormations): void {
    const modalRef = this.modalService.open(FormationsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.formations = formations;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
