import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { NiveauxComponent } from './list/niveaux.component';
import { NiveauxDetailComponent } from './detail/niveaux-detail.component';
import { NiveauxUpdateComponent } from './update/niveaux-update.component';
import { NiveauxDeleteDialogComponent } from './delete/niveaux-delete-dialog.component';
import { NiveauxRoutingModule } from './route/niveaux-routing.module';

@NgModule({
  imports: [SharedModule, NiveauxRoutingModule],
  declarations: [NiveauxComponent, NiveauxDetailComponent, NiveauxUpdateComponent, NiveauxDeleteDialogComponent],
  entryComponents: [NiveauxDeleteDialogComponent],
})
export class NiveauxModule {}
