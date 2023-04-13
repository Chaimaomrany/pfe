import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserMasterDetailComponent } from './user-master-detail.component';

describe('UserMasterDetailComponent', () => {
  let component: UserMasterDetailComponent;
  let fixture: ComponentFixture<UserMasterDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserMasterDetailComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserMasterDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
