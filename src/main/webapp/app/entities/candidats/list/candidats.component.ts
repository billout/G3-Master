import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICandidats } from '../candidats.model';

import { ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { CandidatsService } from '../service/candidats.service';
import { CandidatsDeleteDialogComponent } from '../delete/candidats-delete-dialog.component';
import { ParseLinks } from 'app/core/util/parse-links.service';

@Component({
  selector: 'jhi-candidats',
  templateUrl: './candidats.component.html',
})
export class CandidatsComponent implements OnInit {
  candidats: ICandidats[];
  isLoading = false;
  itemsPerPage: number;
  links: { [key: string]: number };
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(protected candidatsService: CandidatsService, protected modalService: NgbModal, protected parseLinks: ParseLinks) {
    this.candidats = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.isLoading = true;

    this.candidatsService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<ICandidats[]>) => {
          this.isLoading = false;
          this.paginateCandidats(res.body, res.headers);
        },
        () => {
          this.isLoading = false;
        }
      );
  }

  reset(): void {
    this.page = 0;
    this.candidats = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ICandidats): number {
    return item.id!;
  }

  delete(candidats: ICandidats): void {
    const modalRef = this.modalService.open(CandidatsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.candidats = candidats;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.reset();
      }
    });
  }

  protected sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateCandidats(data: ICandidats[] | null, headers: HttpHeaders): void {
    this.links = this.parseLinks.parse(headers.get('link') ?? '');
    if (data) {
      for (const d of data) {
        this.candidats.push(d);
      }
    }
  }
}
