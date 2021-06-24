import { IPersonnes } from 'app/entities/personnes/personnes.model';
import { IEpreuves } from 'app/entities/epreuves/epreuves.model';

export interface IJury {
  id?: number;
  libelle?: string | null;
  president?: IPersonnes | null;
  membre1?: IPersonnes | null;
  membre2?: IPersonnes | null;
  membre3?: IPersonnes | null;
  corrige?: IEpreuves | null;
}

export class Jury implements IJury {
  constructor(
    public id?: number,
    public libelle?: string | null,
    public president?: IPersonnes | null,
    public membre1?: IPersonnes | null,
    public membre2?: IPersonnes | null,
    public membre3?: IPersonnes | null,
    public corrige?: IEpreuves | null
  ) {}
}

export function getJuryIdentifier(jury: IJury): number | undefined {
  return jury.id;
}
