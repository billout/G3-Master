jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { EpreuvesService } from '../service/epreuves.service';

import { EpreuvesDeleteDialogComponent } from './epreuves-delete-dialog.component';

describe('Component Tests', () => {
  describe('Epreuves Management Delete Component', () => {
    let comp: EpreuvesDeleteDialogComponent;
    let fixture: ComponentFixture<EpreuvesDeleteDialogComponent>;
    let service: EpreuvesService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [EpreuvesDeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(EpreuvesDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EpreuvesDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(EpreuvesService);
      mockActiveModal = TestBed.inject(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.close).not.toHaveBeenCalled();
        expect(mockActiveModal.dismiss).toHaveBeenCalled();
      });
    });
  });
});
