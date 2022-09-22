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