import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IVoieAcces } from '../voie-acces.model';
import { VoieAccesService } from '../service/voie-acces.service';

@Component({
  templateUrl: './voie-acces-delete-dialog.component.html',
})
export class VoieAccesDeleteDialogComponent {
  voieAcces?: IVoieAcces;

  constructor(protected voieAccesService: VoieAccesService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.voieAccesService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
