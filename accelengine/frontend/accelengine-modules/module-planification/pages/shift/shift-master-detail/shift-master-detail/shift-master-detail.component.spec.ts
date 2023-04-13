import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShiftMasterDetailComponent } from './shift-master-detail.component';

describe('ShiftMasterDetailComponent', () => {
  let component: ShiftMasterDetailComponent;
  let fixture: ComponentFixture<ShiftMasterDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ShiftMasterDetailComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ShiftMasterDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
