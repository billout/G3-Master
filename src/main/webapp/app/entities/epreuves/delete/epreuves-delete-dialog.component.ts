import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEpreuves } from '../epreuves.model';
import { EpreuvesService } from '../service/epreuves.service';

@Component({
  templateUrl: './epreuves-delete-dialog.component.html',
})
export class EpreuvesDeleteDialogComponent {
  epreuves?: IEpreuves;

  constructor(protected epreuvesService: EpreuvesService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.epreuvesService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
