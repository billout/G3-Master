import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { CandidatsComponent } from './list/candidats.component';
import { CandidatsDetailComponent } from './detail/candidats-detail.component';
import { CandidatsUpdateComponent } from './update/candidats-update.component';
import { CandidatsDeleteDialogComponent } from './delete/candidats-delete-dialog.component';
import { CandidatsRoutingModule } from './route/candidats-routing.module';

@NgModule({
  imports: [SharedModule, CandidatsRoutingModule],
  declarations: [CandidatsComponent, CandidatsDetailComponent, CandidatsUpdateComponent, CandidatsDeleteDialogComponent],
  entryComponents: [CandidatsDeleteDialogComponent],
})
export class CandidatsModule {}
