import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OperatorShiftComponent } from './operator-shift.component';

describe('OperatorShiftComponent', () => {
  let component: OperatorShiftComponent;
  let fixture: ComponentFixture<OperatorShiftComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OperatorShiftComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OperatorShiftComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
