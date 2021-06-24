import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EpreuvesComponent } from '../list/epreuves.component';
import { EpreuvesDetailComponent } from '../detail/epreuves-detail.component';
import { EpreuvesUpdateComponent } from '../update/epreuves-update.component';
import { EpreuvesRoutingResolveService } from './epreuves-routing-resolve.service';

const epreuvesRoute: Routes = [
  {
    path: '',
    component: EpreuvesComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EpreuvesDetailComponent,
    resolve: {
      epreuves: EpreuvesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EpreuvesUpdateComponent,
    resolve: {
      epreuves: EpreuvesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EpreuvesUpdateComponent,
    resolve: {
      epreuves: EpreuvesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(epreuvesRoute)],
  exports: [RouterModule],
})
export class EpreuvesRoutingModule {}
