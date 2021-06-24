import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CandidatsComponent } from '../list/candidats.component';
import { CandidatsDetailComponent } from '../detail/candidats-detail.component';
import { CandidatsUpdateComponent } from '../update/candidats-update.component';
import { CandidatsRoutingResolveService } from './candidats-routing-resolve.service';

const candidatsRoute: Routes = [
  {
    path: '',
    component: CandidatsComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CandidatsDetailComponent,
    resolve: {
      candidats: CandidatsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CandidatsUpdateComponent,
    resolve: {
      candidats: CandidatsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CandidatsUpdateComponent,
    resolve: {
      candidats: CandidatsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(candidatsRoute)],
  exports: [RouterModule],
})
export class CandidatsRoutingModule {}
