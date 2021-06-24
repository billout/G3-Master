import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'niveaux',
        data: { pageTitle: 'g3MasterApp.niveaux.home.title' },
        loadChildren: () => import('./niveaux/niveaux.module').then(m => m.NiveauxModule),
      },
      {
        path: 'formations',
        data: { pageTitle: 'g3MasterApp.formations.home.title' },
        loadChildren: () => import('./formations/formations.module').then(m => m.FormationsModule),
      },
      {
        path: 'voie-acces',
        data: { pageTitle: 'g3MasterApp.voieAcces.home.title' },
        loadChildren: () => import('./voie-acces/voie-acces.module').then(m => m.VoieAccesModule),
      },
      {
        path: 'personnes',
        data: { pageTitle: 'g3MasterApp.personnes.home.title' },
        loadChildren: () => import('./personnes/personnes.module').then(m => m.PersonnesModule),
      },
      {
        path: 'candidats',
        data: { pageTitle: 'g3MasterApp.candidats.home.title' },
        loadChildren: () => import('./candidats/candidats.module').then(m => m.CandidatsModule),
      },
      {
        path: 'epreuves',
        data: { pageTitle: 'g3MasterApp.epreuves.home.title' },
        loadChildren: () => import('./epreuves/epreuves.module').then(m => m.EpreuvesModule),
      },
      {
        path: 'concours',
        data: { pageTitle: 'g3MasterApp.concours.home.title' },
        loadChildren: () => import('./concours/concours.module').then(m => m.ConcoursModule),
      },
      {
        path: 'jury',
        data: { pageTitle: 'g3MasterApp.jury.home.title' },
        loadChildren: () => import('./jury/jury.module').then(m => m.JuryModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
