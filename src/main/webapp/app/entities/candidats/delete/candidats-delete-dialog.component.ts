import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICandidats } from '../candidats.model';
import { CandidatsService } from '../service/candidats.service';

@Component({
  templateUrl: './candidats-delete-dialog.component.html',
})
export class CandidatsDeleteDialogComponent {
  candidats?: ICandidats;

  constructor(protected candidatsService: CandidatsService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.candidatsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
