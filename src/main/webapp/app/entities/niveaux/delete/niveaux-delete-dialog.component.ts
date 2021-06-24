import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { INiveaux } from '../niveaux.model';
import { NiveauxService } from '../service/niveaux.service';

@Component({
  templateUrl: './niveaux-delete-dialog.component.html',
})
export class NiveauxDeleteDialogComponent {
  niveaux?: INiveaux;

  constructor(protected niveauxService: NiveauxService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.niveauxService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
