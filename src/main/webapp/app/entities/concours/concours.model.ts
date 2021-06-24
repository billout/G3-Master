import * as dayjs from 'dayjs';
import { IEpreuves } from 'app/entities/epreuves/epreuves.model';
import { IFormations } from 'app/entities/formations/formations.model';
import { IVoieAcces } from 'app/entities/voie-acces/voie-acces.model';
import { ICandidats } from 'app/entities/candidats/candidats.model';

export interface IConcours {
  id?: number;
  code?: string | null;
  libelle?: string | null;
  dtOuverture?: dayjs.Dayjs | null;
  dtCloture?: dayjs.Dayjs | null;
  estComposes?: IEpreuves[] | null;
  estPours?: IFormations[] | null;
  estPars?: IVoieAcces[] | null;
  candidats?: ICandidats[] | null;
}

export class Concours implements IConcours {
  constructor(
    public id?: number,
    public code?: string | null,
    public libelle?: string | null,
    public dtOuverture?: dayjs.Dayjs | null,
    public dtCloture?: dayjs.Dayjs | null,
    public estComposes?: IEpreuves[] | null,
    public estPours?: IFormations[] | null,
    public estPars?: IVoieAcces[] | null,
    public candidats?: ICandidats[] | null
  ) {}
}

export function getConcoursIdentifier(concours: IConcours): number | undefined {
  return concours.id;
}
