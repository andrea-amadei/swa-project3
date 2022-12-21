# Messaging
The aim of the project is to develop a full web application (frontend and backend) to create threads and submit replies to those threads.
The whole application features:
- Single-page frontend application to browse threads and replies, containing a timestamp of when they were created.
- Real-time updates to threads and replies when new ones are added.
- Session-based authentication to keep track of submissions while retaining anonymity.
- Modular, fully containerized and auto-scaling structure.

The project is composed of:
- PostgreSQL (with Cloud Native PG)
  
  Database to store threads, replies and user sessions.

- Spring Boot (Java)
  
  REST backend to handle web requests on dynamic content and database interactions.

- React (JavaScript)

  Frontend framework to build the UI as a static single-page application.

- Nginx
  
  Static pages server that distributes the frontend.

- Kubernetes

  Container orchestration system to deploy and autoscale all the technologies listed above.


## Running the application

### Preliminary steps
In order to run the application correctly the following preliminary stepes are required:
- Docker is installed and running
- Minikube is installed and running
- Minikube is able to access local images:
   
  On Linux:
  ```sh
  eval $(minikube docker-env)
  ```
   
  On Windows:
  ```powershell
  minikube docker-env
  minikube docker-env | Invoke-Expression
  ```

- Minikube's *Ingress* and *Metrics-Server* addons are installed:
  
  ```sh
  minikube addons enable ingress
  minikube addons enable metrics-server
  ```

- Operator *Cloud Native PG* is installed:

  ```sh
  kubectl apply -f https://raw.githubusercontent.com/cloudnative-pg/cloudnative-pg/release-1.18/releases/cnpg-1.18.0.yaml
  ```

### Building the images
To build the *messaging-api* and the *messaging-web* docker images:

  ```sh
  docker build -t messaging-api ./messaging-api
  docker build -t messaging-web ./messaging-web
  ```

### Deploying the application
To deploy the application simply run:

  ```sh
  kubectl apply -f ./kubernetes
  ```

and wait for all components to start. 
After it's done, create a new tunnel to open an external connection:

  ```sh
  minikube tunnel
  ```

It should now be possible to access the frontend on http://localhost or http://127.0.0.1.

### Removing the deployment
To remove the deployed application simply close the tunnel and run:

  ```sh
  kubectl delete -f ./kubernetes
  ```

## Running the benchmarks
Once the application is rurnning, tests can be performed with k6 by running in the *test* folder:
```sh
k6 run <testfile.js>
```

Tests refer to:
- Get homepage: *test_homepage.js*:     
  GET request to http://\<host\>/
- Get all threads: *test_API.js*:
  GET request to http://\<host\>/api/threads

|           | Homepage |        |           |           | API     |        |           |           |
|-----------|----------|--------|-----------|-----------|---------|--------|-----------|-----------|
|           | Average  | Median | 95th perc | 99th perc | Average | Median | 95th perc | 99th perc |
| request   | 27,38    | 5,48   | 88,43     | 91,90     | 150,72  | 143,25 | 285,82    | 337,12    |
| sending   | 0,01     | 0,00   | 0,00      | 0,54      | 0,01    | 0,00   | 0,00      | 0,53      |
| waiting   | 27,31    | 5,45   | 88,36     | 91,82     | 150,64  | 143,15 | 285,81    | 336,91    |
| receiving | 0,05     | 0,00   | 0,53      | 0,55      | 0,07    | 0,00   | 0,53      | 0,55      |

All tests were executed with 50 VUs for 5 minutes and are expressed in milliseconds (ms).

## Lighthouse
Google Lighthouse's Chrome extension shows the following results for the main page:

| Performance | Accessibility | Best Practices | SEO |
|-------------|---------------|----------------|-----|
| 100         | 78            | 100            | 100 |

Average score is 95.

## Conclusions and future improvements
As shown by the data, Nginx is serving static files at incredibly high speeds, mostly thanks to the Single-page application that allows caching. On the other hand, API calls are far slower, mostly because all requests must be authenticated before being executed, requiring an additional database interaction. In general, running the backend on a Kubernetes cluster introduces overheads and should slow down every part of the application, however the ability to scale horizontally automatically is well worth the additional cost.

The frontend uses a polling system to refresh threads and replies, however this could be vastly improved thanks to web-sockets and more granular requests to certain elements instead of refreshing every element on the page. Infinite-page scrolling could also be implemented easily in a future iteration of the project, since most limits that impose pages to be 20 threads long were artificially introduced to meet standards and can be removed anytime.
