import { IConcours } from 'app/entities/concours/concours.model';
import { INiveaux } from 'app/entities/niveaux/niveaux.model';

export interface IFormations {
  id?: number;
  libelle?: string | null;
  code?: string | null;
  concours?: IConcours | null;
  niveaux?: INiveaux | null;
}

export class Formations implements IFormations {
  constructor(
    public id?: number,
    public libelle?: string | null,
    public code?: string | null,
    public concours?: IConcours | null,
    public niveaux?: INiveaux | null
  ) {}
}

export function getFormationsIdentifier(formations: IFormations): number | undefined {
  return formations.id;
}
