#HTTPie
- curl 을 대체할 만한 강력한 http client 
- 설치 => brew install httpie
- http {METHOD} {URL} {ITEM}
- https://www.hahwul.com/2020/11/29/httpie-this-is-a-great-http-tool/

#Apache Bench for Simple Load Testing
- https://www.petefreitag.com/item/689.cfm
- ab -n 100 -c 10 http://www.yahoo.com/
  - n: 전체 요청의 갯수
  - c(concurrency): 동시요청 수
  - http: 요청 주소
```text
Concurrency Level:      10
Time taken for tests:   2.565 seconds
Complete requests:      100
Failed requests:        0
Non-2xx responses:      100
Total transferred:      107300 bytes
HTML transferred:       800 bytes
Requests per second:    38.99 [#/sec] (mean) //초당 예상 처리 갯수(정확하지않음)
Time per request:       256.484 [ms] (mean) //한건의처리속도
Time per request:       25.648 [ms] (mean, across all concurrent requests)
Transfer rate:          40.85 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:      101  116  10.2    112     144
Processing:   102  116   6.8    117     140
Waiting:      102  116   6.9    116     140
Total:        203  232  13.5    231     263

Percentage of the requests served within a certain time (ms)
  50%    231
  66%    236
  75%    241
  80%    244
  90%    253
  95%    256
  98%    261
  99%    263 // 99%로의 모든 요청이 263 ms 에 끝난다.
 100%    263 (longest request)

```
#성능테스트
- JMeter
  - https://jmeter.apache.org/
  - Thread Group: 한 쓰레드 당 유저 한명
  - Sampler: 어떤 유저가 해야 하는 액션
  - Listener: 응답을 어떻게 처리할것인지
  - Configuration: Sampler 또는 Listener 가 사용할 설정 값(쿠키, JDBC 커넥션 등)
  - Assertion: 응답이 성공인지 확인하는 방법(응답코드, 본문 내용 등)
  -  /Users/injinjeong/jeong_workspace/apache-jmeter-5.4.3/bin/jmeter ; exit;
- nGrinder
  - https://naver.github.io/ngrinder/
- gatling
  - https://gatling.io/

#운영 이슈 테스트
  - Chaos Monkey
  - 카오스 엔지니어링
    - https://netflix.github.io/chaosmonkey/
    - https://www.baeldung.com/spring-boot-chaos-monkey
  - 카오스 멍키 스프링부트
    - https://codecentric.github.io/chaos-monkey-spring-boot/latest/
    - https://codecentric.github.io/chaos-monkey-spring-boot/

  - 스프링부트에 설치 적용(kotlin, gradle, yml)
  - 의존성 추가
    - 
  - build.gradle
    - implementation("de.codecentric:chaos-monkey-spring-boot:2.6.1")
    - implementation('org.springframework.boot:spring-boot-starter-actuator')
  - CM4SB 설정
    - yml 설정
  ```yaml
  spring:
  profiles:
    active: chaos-monkey
  chaos:
    monkey:
      enabled: true
      watcher:
        service: true
      assaults:
        latencyActive: true

  management:
  endpoint:
    chaosmonkey:
      enabled: true
    chaosmonkeyjmx:
      enabled: true

  endpoints:
    web:
      exposure:
        # include all endpoints
        #      include: "*"
        # include specific endpoints
        include:
          - health
          - info
          - chaosmonkey
  ```
- 응답지연
  - https://codecentric.github.io/chaos-monkey-spring-boot/latest/#_latency_assault
- Repository Watcher 활성화
  - chaos.monkey.watcher.repository=true
- 호출 ex
  - http get http://localhost:8080/book/loan
```text
카오스 멍키 활성화
http post localhost:8080/actuator/chaosmonkey/enable
카오스 멍키 활성화 확인
http localhost:8080/actuator/chaosmonkey/status
카오스 멍키 와처 확인
http localhost:8080/actuator/chaosmonkey/watchers
카오스 멍키 지연 공격 설정
http POST localhost:8080/actuator/chaosmonkey/assaults level=3 latencyRangeStart=2000 latencyRangeEnd=5000 latencyActive=true
```

#아키텍처 테스트
- archunit
  - https://www.archunit.org/
```text
//gradle
dependencies {
  testImplementation 'com.tngtech.archunit:archunit-junit5:0.23.1'
}
```