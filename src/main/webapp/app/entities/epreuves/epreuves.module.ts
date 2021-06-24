import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { EpreuvesComponent } from './list/epreuves.component';
import { EpreuvesDetailComponent } from './detail/epreuves-detail.component';
import { EpreuvesUpdateComponent } from './update/epreuves-update.component';
import { EpreuvesDeleteDialogComponent } from './delete/epreuves-delete-dialog.component';
import { EpreuvesRoutingModule } from './route/epreuves-routing.module';

@NgModule({
  imports: [SharedModule, EpreuvesRoutingModule],
  declarations: [EpreuvesComponent, EpreuvesDetailComponent, EpreuvesUpdateComponent, EpreuvesDeleteDialogComponent],
  entryComponents: [EpreuvesDeleteDialogComponent],
})
export class EpreuvesModule {}
