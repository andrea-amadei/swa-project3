import http from 'k6/http';

export const options = {
  duration: "5m",
  vus: 50,
  summaryTrendStats: ["avg", "med", "p(95)", "p(99)"],
  summaryTimeUnit: 'ms'
}

export default function () {
  http.get('http://localhost/api/threads');
}