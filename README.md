# Redis Connection Pool Benchmark
Sample Spring Boot application for benchmarking Redis connection pool capability. 

## Getting Started
To get started, you need to clone this project, modify the config and run as Spring boot application.

```
$ git clone https://github.com/kkravikumar/redis-connection-pool.git 
$ mvn spring-boot:run
```

### Prerequisites

Prior to running this application you need setup Redis or pick any Redis instance. This POC works only for [Standalone](https://redislabs.com/redis-enterprise/technology/redis-enterprise-cluster-architecture/) Simple or HA database model. 

### Frameworks

* [Spring Boot](https://spring.io/projects/spring-boot) 2.1.4.RELEASE 
* [Apache Common Pool](https://commons.apache.org/proper/commons-pool/) 2.5.0
* [Lettuce](https://lettuce.io/core/) 5.1.6.RELEASE

### Benchmark Numbers 

No Connection Pool

`redis.pool.enabled=false`

```
2021-01-22 19:11:09.681  INFO 35661 --- [ Redis-5] c.r.r.c.service.ConnectionBenchmark      : StopWatch 'Redis Operation BenchMark execution-4': running time (millis) = 18100
2021-01-22 19:11:09.681  INFO 35661 --- [ Redis-3] c.r.r.c.service.ConnectionBenchmark      : StopWatch 'Redis Operation BenchMark execution-2': running time (millis) = 18101
2021-01-22 19:11:09.781  INFO 35661 --- [ Redis-11] c.r.r.c.service.ConnectionBenchmark      : StopWatch 'Redis Read': running time (millis) = 12072
2021-01-22 19:11:09.781  INFO 35661 --- [ Redis-11] c.r.r.c.service.ConnectionBenchmark      : StopWatch 'Redis Operation BenchMark execution-10': running time (millis) = 18199

```

With Connection Pool 10 and 15 threads
 
`redis.pool.enabled=true`
`redis.pool.max-connection=10`

```
2021-01-22 19:35:48.141  INFO 37319 --- [ Redis-6] c.r.r.c.service.ConnectionBenchmark      : StopWatch 'Redis Operation BenchMark execution-5': running time (millis) = 23120
2021-01-22 19:35:48.141  INFO 37319 --- [ Redis-8] c.r.r.c.service.ConnectionBenchmark      : StopWatch 'Redis Operation BenchMark execution-7': running time (millis) = 23120
2021-01-22 19:35:48.239  INFO 37319 --- [ Redis-4] c.r.r.c.service.ConnectionBenchmark      : StopWatch 'Redis Read': running time (millis) = 11684
2021-01-22 19:35:48.240  INFO 37319 --- [ Redis-4] c.r.r.c.service.ConnectionBenchmark      : StopWatch 'Redis Operation BenchMark execution-3': running time (millis) = 23219

```

With Connection Pool 20 and 15 threads
 
`redis.pool.enabled=true`
`redis.pool.max-connection=20`

```
2021-01-22 19:40:55.717  INFO 37680 --- [ Redis-4] c.r.r.c.service.ConnectionBenchmark      : StopWatch 'Redis Read': running time (millis) = 11769
2021-01-22 19:40:55.717  INFO 37680 --- [ Redis-12] c.r.r.c.service.ConnectionBenchmark      : StopWatch 'Redis Operation BenchMark execution-11': running time (millis) = 19085
2021-01-22 19:40:55.717  INFO 37680 --- [ Redis-6] c.r.r.c.service.ConnectionBenchmark      : StopWatch 'Redis Operation BenchMark execution-5': running time (millis) = 19087
2021-01-22 19:40:55.718  INFO 37680 --- [ Redis-4] c.r.r.c.service.ConnectionBenchmark      : StopWatch 'Redis Operation BenchMark execution-3': running time (millis) = 19087

```

With Connection Pool 20 and 40 threads
 
`redis.pool.enabled=true`
`redis.pool.max-connection=20`

```
2021-01-22 19:46:08.139  INFO 38073 --- [ Redis-1] c.r.r.c.service.ConnectionBenchmark      : StopWatch 'Redis Operation BenchMark execution-36': running time (millis) = 24297
2021-01-22 19:46:08.139  INFO 38073 --- [ Redis-8] c.r.r.c.service.ConnectionBenchmark      : StopWatch 'Redis Operation BenchMark execution-30': running time (millis) = 24297
2021-01-22 19:46:08.349  INFO 38073 --- [ Redis-5] c.r.r.c.service.ConnectionBenchmark      : StopWatch 'Redis Read': running time (millis) = 12440
2021-01-22 19:46:08.349  INFO 38073 --- [ Redis-5] c.r.r.c.service.ConnectionBenchmark      : StopWatch 'Redis Operation BenchMark execution-38': running time (millis) = 24402

```

### Conclusion  

Based on the above logs and multiple test runs, when the parallelism (threads) goes beyond number of connection then latency increases. 
This concludes connection pool is not best option with above combinations of frameworks.   

## Authors

* **Ravikumar Kuppusamy** - *Initial work* - [RaviK](https://github.com/kkravikumar)

