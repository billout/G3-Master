import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { FormationsComponent } from './list/formations.component';
import { FormationsDetailComponent } from './detail/formations-detail.component';
import { FormationsUpdateComponent } from './update/formations-update.component';
import { FormationsDeleteDialogComponent } from './delete/formations-delete-dialog.component';
import { FormationsRoutingModule } from './route/formations-routing.module';

@NgModule({
  imports: [SharedModule, FormationsRoutingModule],
  declarations: [FormationsComponent, FormationsDetailComponent, FormationsUpdateComponent, FormationsDeleteDialogComponent],
  entryComponents: [FormationsDeleteDialogComponent],
})
export class FormationsModule {}
