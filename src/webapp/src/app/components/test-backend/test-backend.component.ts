import { Component, OnInit } from '@angular/core';
import { TestBackendService } from '../../services/test-backend.service';
import { TestBackend } from '../../interfaces/TestBackend';

@Component({
  selector: 'app-test-backend',
  templateUrl: './test-backend.component.html',
  styleUrls: ['./test-backend.component.sass']
})
export class TestBackendComponent implements OnInit {
  results: TestBackend;

  constructor(private backendService: TestBackendService) { }

  ngOnInit() {
  }

  runTests() {
    this.backendService.testBackend().subscribe((results) => (this.results = results));
  }

}
