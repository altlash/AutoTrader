export interface TestResult {
  test: string;
  result: string;
}

export interface TestBackend {
  overallResults: string;
  results: TestResult[];
}
