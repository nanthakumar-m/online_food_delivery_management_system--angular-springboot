import { TestBed } from '@angular/core/testing';

import { RestaurantMenuItemService } from './menu-item.service';

describe('MenuItemService', () => {
  let service: RestaurantMenuItemService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RestaurantMenuItemService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
