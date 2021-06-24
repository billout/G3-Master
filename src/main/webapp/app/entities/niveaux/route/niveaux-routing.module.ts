import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NiveauxComponent } from '../list/niveaux.component';
import { NiveauxDetailComponent } from '../detail/niveaux-detail.component';
import { NiveauxUpdateComponent } from '../update/niveaux-update.component';
import { NiveauxRoutingResolveService } from './niveaux-routing-resolve.service';

const niveauxRoute: Routes = [
  {
    path: '',
    component: NiveauxComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NiveauxDetailComponent,
    resolve: {
      niveaux: NiveauxRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NiveauxUpdateComponent,
    resolve: {
      niveaux: NiveauxRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NiveauxUpdateComponent,
    resolve: {
      niveaux: NiveauxRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(niveauxRoute)],
  exports: [RouterModule],
})
export class NiveauxRoutingModule {}
