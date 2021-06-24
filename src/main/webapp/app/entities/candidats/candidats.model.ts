import { IPersonnes } from 'app/entities/personnes/personnes.model';
import { IConcours } from 'app/entities/concours/concours.model';
import { Statut } from 'app/entities/enumerations/statut.model';

export interface ICandidats {
  id?: number;
  identifiant?: string | null;
  etat?: Statut | null;
  est?: IPersonnes | null;
  concours?: IConcours[] | null;
}

export class Candidats implements ICandidats {
  constructor(
    public id?: number,
    public identifiant?: string | null,
    public etat?: Statut | null,
    public est?: IPersonnes | null,
    public concours?: IConcours[] | null
  ) {}
}

export function getCandidatsIdentifier(candidats: ICandidats): number | undefined {
  return candidats.id;
}
