import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FormationsComponent } from '../list/formations.component';
import { FormationsDetailComponent } from '../detail/formations-detail.component';
import { FormationsUpdateComponent } from '../update/formations-update.component';
import { FormationsRoutingResolveService } from './formations-routing-resolve.service';

const formationsRoute: Routes = [
  {
    path: '',
    component: FormationsComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FormationsDetailComponent,
    resolve: {
      formations: FormationsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FormationsUpdateComponent,
    resolve: {
      formations: FormationsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FormationsUpdateComponent,
    resolve: {
      formations: FormationsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(formationsRoute)],
  exports: [RouterModule],
})
export class FormationsRoutingModule {}
