import { TestBed } from '@angular/core/testing';

import { OperatorShiftService } from './operator-shift.service';

describe('OperatorShiftService', () => {
  let service: OperatorShiftService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OperatorShiftService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
