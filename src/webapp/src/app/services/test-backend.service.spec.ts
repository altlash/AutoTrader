import { TestBed } from '@angular/core/testing';

import { TestBackendService } from './test-backend.service';

describe('TestBackendService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: TestBackendService = TestBed.get(TestBackendService);
    expect(service).toBeTruthy();
  });
});
