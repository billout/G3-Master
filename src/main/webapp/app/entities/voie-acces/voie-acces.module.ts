import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { VoieAccesComponent } from './list/voie-acces.component';
import { VoieAccesDetailComponent } from './detail/voie-acces-detail.component';
import { VoieAccesUpdateComponent } from './update/voie-acces-update.component';
import { VoieAccesDeleteDialogComponent } from './delete/voie-acces-delete-dialog.component';
import { VoieAccesRoutingModule } from './route/voie-acces-routing.module';

@NgModule({
  imports: [SharedModule, VoieAccesRoutingModule],
  declarations: [VoieAccesComponent, VoieAccesDetailComponent, VoieAccesUpdateComponent, VoieAccesDeleteDialogComponent],
  entryComponents: [VoieAccesDeleteDialogComponent],
})
export class VoieAccesModule {}
