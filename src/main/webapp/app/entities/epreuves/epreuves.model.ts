import { IConcours } from 'app/entities/concours/concours.model';
import { TypeEpreuve } from 'app/entities/enumerations/type-epreuve.model';

export interface IEpreuves {
  id?: number;
  code?: string | null;
  libelle?: string | null;
  type?: TypeEpreuve | null;
  compose?: IConcours | null;
}

export class Epreuves implements IEpreuves {
  constructor(
    public id?: number,
    public code?: string | null,
    public libelle?: string | null,
    public type?: TypeEpreuve | null,
    public compose?: IConcours | null
  ) {}
}

export function getEpreuvesIdentifier(epreuves: IEpreuves): number | undefined {
  return epreuves.id;
}
