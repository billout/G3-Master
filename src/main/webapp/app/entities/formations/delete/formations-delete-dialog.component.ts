import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFormations } from '../formations.model';
import { FormationsService } from '../service/formations.service';

@Component({
  templateUrl: './formations-delete-dialog.component.html',
})
export class FormationsDeleteDialogComponent {
  formations?: IFormations;

  constructor(protected formationsService: FormationsService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.formationsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
