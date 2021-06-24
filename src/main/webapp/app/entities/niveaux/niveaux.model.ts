import { IFormations } from 'app/entities/formations/formations.model';

export interface INiveaux {
  id?: number;
  libelle?: string | null;
  code?: string | null;
  formations?: IFormations[] | null;
}

export class Niveaux implements INiveaux {
  constructor(public id?: number, public libelle?: string | null, public code?: string | null, public formations?: IFormations[] | null) {}
}

export function getNiveauxIdentifier(niveaux: INiveaux): number | undefined {
  return niveaux.id;
}
