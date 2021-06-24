import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { VoieAccesComponent } from '../list/voie-acces.component';
import { VoieAccesDetailComponent } from '../detail/voie-acces-detail.component';
import { VoieAccesUpdateComponent } from '../update/voie-acces-update.component';
import { VoieAccesRoutingResolveService } from './voie-acces-routing-resolve.service';

const voieAccesRoute: Routes = [
  {
    path: '',
    component: VoieAccesComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VoieAccesDetailComponent,
    resolve: {
      voieAcces: VoieAccesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VoieAccesUpdateComponent,
    resolve: {
      voieAcces: VoieAccesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VoieAccesUpdateComponent,
    resolve: {
      voieAcces: VoieAccesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(voieAccesRoute)],
  exports: [RouterModule],
})
export class VoieAccesRoutingModule {}
