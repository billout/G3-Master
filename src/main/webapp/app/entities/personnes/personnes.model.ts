import { ICandidats } from 'app/entities/candidats/candidats.model';

export interface IPersonnes {
  id?: number;
  nom?: string | null;
  prenom?: string | null;
  telephone?: string | null;
  email?: string | null;
  nationnalite?: string | null;
  aDossiers?: ICandidats[] | null;
}

export class Personnes implements IPersonnes {
  constructor(
    public id?: number,
    public nom?: string | null,
    public prenom?: string | null,
    public telephone?: string | null,
    public email?: string | null,
    public nationnalite?: string | null,
    public aDossiers?: ICandidats[] | null
  ) {}
}

export function getPersonnesIdentifier(personnes: IPersonnes): number | undefined {
  return personnes.id;
}
