import { IConcours } from 'app/entities/concours/concours.model';

export interface IVoieAcces {
  id?: number;
  libelle?: string | null;
  code?: string | null;
  concours?: IConcours | null;
}

export class VoieAcces implements IVoieAcces {
  constructor(public id?: number, public libelle?: string | null, public code?: string | null, public concours?: IConcours | null) {}
}

export function getVoieAccesIdentifier(voieAcces: IVoieAcces): number | undefined {
  return voieAcces.id;
}
