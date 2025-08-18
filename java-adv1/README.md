# 김영한의 실전 자바 - 고급 1편, 멀티스레드와 동시성

<br>

| 섹션 번호 | 섹션 명 | 초급 | 중급 | 고급 |
| --- | --- | --- | --- | --- |
| 섹션 2 | 프로세스와 스레드 소개 |  |  |  |
| 섹션 3 | 스레드 생성 및 실행 | 1 |  |  |
| 섹션 4 | 스레드 제어와 생명 주기1 | 1 |  |  |
| 섹션 5 | 스레드 제어와 생명 주기2 |  | 1 |  |
| 섹션 6 | 메모리 가시성 |  |  |  |
| 섹션 7 | 동기화: synchronized |  |  |  |
| 섹션 8 | 고급 동기화: concurrent.Lock |  |  |  |
| 섹션 9 | 생성자 소비자 문제1 |  |  | 1 |
| 섹션 10 | 생성자 소비자 문제2 |  |  | 1 |
| 섹션 11 | CAS: 동기화와 원자적 연산 |  | 1 |  |
| 섹션 12 | 동시성 컬렉션 |  |  | 1 |
| 섹션 13 | 스레드 풀과 Executor 프레임워크1 |  |  | 2 |
| 섹션 14 | 스레드 풀과 Executor 프레임워크2 |  |  | 2 |

<br>

# 섹션 3. 스레드 생성 및 실행 (1문제)

## 문제 1: 파일 다운로드 시뮬레이션 (초급)

### 요구사항
- `FileDownloader` 클래스를 만들고 `Runnable` 인터페이스를 구현하세요
- 생성자로 `fileName`, `fileSize`(MB)를 받도록 하세요
- `run()` 메서드에서 파일 다운로드를 시뮬레이션하세요:
    - 1MB당 200ms가 걸린다고 가정
    - 다운로드 진행률을 10%씩 출력하세요
    - 완료되면 "다운로드 완료" 메시지 출력
- 메인 메서드에서 3개의 파일을 동시에 다운로드하세요:
    - video.mp4 (50MB)
    - music.mp3 (30MB)
    - document.pdf (10MB)

### 예상 출력

```
[Thread-0] video.mp4 다운로드 시작 (50MB)
[Thread-1] music.mp3 다운로드 시작 (30MB)
[Thread-2] document.pdf 다운로드 시작 (10MB)
[Thread-2] document.pdf 10% 완료
[Thread-1] music.mp3 10% 완료
[Thread-0] video.mp4 10% 완료
...
[Thread-2] document.pdf 다운로드 완료!
```

<br><br><br>

# 섹션 4. 스레드 제어와 생명 주기1 (1문제)

## 문제 1: 순차적 데이터 처리 (초급)

### 요구사항

- 세 개의 스레드가 각각 다른 배열의 합을 계산하는 프로그램을 작성하세요.
- Thread1: [1, 2, 3, 4, 5]의 합
- Thread2: [6, 7, 8, 9, 10]의 합
- Thread3: [11, 12, 13, 14, 15]의 합
- 각 스레드에서 합을 더할 때 마다 1초간 대기 후 계산 수행
- 모든 스레드가 완료된 후 전체 합을 출력

### 예상 출력

```
[thread-0] 작업 완료 result = 15
[thread-1] 작업 완료 result = 40
[thread-2] 작업 완료 result = 65
[main] 전체 합계: 120
```

<br><br><br>

# 섹션 5. 스레드 제어와 생명 주기2 (1문제)

## 문제 1: 멀티스레드 숫자 게임 (중급)

### 요구사항

- `NumberGame` 클래스 (`Runnable` 인터페이스 구현)
	- 생성자로 `playerName`, `targetNumber`, `maxAttempts`를 받도록 하세요 (추가 및 변형 가능)
	- `run()` 메서드에서 다음 게임을 진행하세요:
	    - 1~100 사이의 랜덤한 숫자를 생성하여 targetNumber(ex. 42)와 비교
	    - 정답을 맞출 때까지 또는 maxAttempts(ex. 30)만큼 시도
	    - 각 시도마다 결과를 출력 (높음/낮음/정답)
	    - 500ms~1500ms 사이의 랜덤한 시간 대기 (사고하는 시간)
	    - 정답이 아닌 경우(높은/낮음) 해당 힌트를 바탕으로 랜덤한 숫자를 생성해야 함 (필수 아님)
        - 이미 맞춘 플레이어가 있다면 더 이상 진행하지 않아도 됨 (필수 아님)
- `GameResultCollector` 데몬 스레드
    - 2초마다 "게임 진행 상황 체크... 경과 시간 N초" 메시지 출력
    - 총 게임 시간을 추적하여 출력
- 메인 메서드
    - 정답을 미리 설정 (ex. 42)
    - 3명의 플레이어가 동시에 게임 진행 (ex. `"Alice", "Bob", "Charlie"`)
    - 각각 최대 30번 시도
    - 첫 번째로 정답을 맞춘 플레이어가 우승
    - 모든 플레이어가 게임을 마치면 결과 요약 출력 (예상 출력 참고)
    - 최대 횟수 이내로 아무도 성공하지 못하면 ‘최대 도전 횟수까지 맞춘 인원이 없습니다.’ 출력

### 예상 출력

```
[main] 숫자 게임 시작! 정답: 42
[Thread-0] GameResultCollector 시작
[Thread-1] [Alice] 시도 1: 73 -> 너무 높음
[Thread-2] [Bob] 시도 1: 25 -> 너무 낮음
[Thread-3] [Charlie] 시도 1: 50 -> 너무 높음
[Thread-0] 게임 진행 상황 체크... 경과 시간: 2초
[Thread-2] [Bob] 시도 2: 45 -> 너무 높음
[Thread-3] [Charlie] 시도 2: 40 -> 너무 낮음
[Thread-1] [Alice] 시도 2: 35 -> 너무 낮음
[Thread-3] [Charlie] 시도 3: 42 -> 정답!
[main] 우승자: Charlie (3번 시도)
```

<br><br><br>

# 섹션 9. 생성자 소비자 문제1 (1문제)

## 문제 1: 배달 주문 처리 시스템 (고급)

### 문제 설명

배달 앱에서 주문이 들어오면 주방에서 요리를 준비하고, 배달원이 픽업하여 배달하는 시스템을 생산자-소비자 패턴으로 구현하세요. **BoundedQueue를 wait()/notify() 메커니즘을 사용하여 직접 구현**해야 합니다.

### 요구사항

### 1. 기본 클래스 구현

**Order 클래스**

- 필드: `int orderId`, `String menu`, `String address`
- 모든 필드는 final로 선언하여 불변 객체로 구현
- 생성자에서 모든 필드를 초기화
- toString() 메서드는 `"Order{orderId=1, menu='치킨버거', address='강남구'}"` 형식으로 출력

**BoundedQueue 클래스**

- ArrayDeque를 사용하여 `BoundedQueue<Order>`를 생성 (maxSize = 5)
- 큐 최대 사이즈는 5로 생성자에서 초기화
- `put()` 메서드: Order 추가하기. 큐가 가득 찬 경우 while 루프와 wait()로 대기, notify()로 대기 스레드 깨우기
- `take()` 메서드: Order 반환하기. 큐가 비어있는 경우 while 루프와 wait()로 대기, notify()로 대기 스레드 깨우기
- 모든 메서드는 synchronized로 동기화 보장
- `put()` 메서드 로그 참조: "[put] 큐가 가득 참, 생산자 대기", "[put] 생산자 깨어남", "[put] 생산자 데이터 저장, notify() 호출"**,**
- `take()` 메서드 로그 참조: "[take] 큐에 데이터 없음, 소비자 대기"**,** "[take] 소비자 깨어남"**,** "[take] 소비자 데이터 획득, notify() 호출"

### 2. 태스크 클래스 구현

**OrderTask (생산자 역할)**

- 생성자 초기화 필드: `BoundedQueue kitchenQueue`, `int orderCount`
- 메뉴명은 배열에서 랜덤 선택: menus = {"치킨버거", "피자", "파스타", "돈까스", "김치찌개"}
- 주소는 배열에서 랜덤 선택: addresses = {"강남구", "서초구", "중구", "마포구", "용산구"}
- `run()` 메서드에서 지정된 개수만큼 주문을 생성하여 kitchenQueue에 `put()`
- 각 주문은 순차적으로 ID 부여 (1, 2, 3, ...) → AtomicInteger 사용 추천 (이후 강의 내용으로 구현 필수X)
- 주문 생성 간격: 1초 딜레이
- 주문 생성 완료 로그 (예상 출력 참고)

**ChefTask (중간 처리자 역할)**

- 생성자 초기화 필드: `BoundedQueue kitchenQueue`, `BoundedQueue pickupQueue`
- `run()` 메서드는 무한 루프로 구현
- kitchenQueue에서 `take()`로 주문을 가져옴
- 요리 시간: 2초 딜레이
- 요리 완료 후 pickupQueue에 `put()`
- 스레드 인터럽트 발생 시 루프 종료
- 요리 완료 로그 (예상 출력 참고)

**DeliveryTask (소비자 역할)**

- 생성자 초기화 필드: `BoundedQueue pickupQueue`
- `run()` 메서드는 무한 루프로 구현
- pickupQueue에서 `take()`로 완성된 주문을 가져옴
- 배달 시간: 2초 딜레이
- 스레드 인터럽트 발생 시 루프 종료
- 배달 완료 로그 (예상 출력 참고)

### 3. 메인 클래스 구현

**DeliveryMain 클래스**

- kitchenQueue: 최대 크기 3인 BoundedQueue<Order> 인스턴스
- pickupQueue: 최대 크기 2인 BoundedQueue<Order> 인스턴스

**스레드 구성:**

- OrderTask 스레드 2개: "주문접수1", "주문접수2" (스레드 각각 4개씩 주문 생성)
- ChefTask 스레드 1개: "요리사1"
- DeliveryTask 스레드 2개: "배달원1", "배달원2"

**실행 흐름:**

1. 큐 인스턴스 생성 및 초기화
2. 모든 스레드 생성 및 시작
3. OrderTask 스레드들이 총 8개 주문 완료될 때까지 대기 (`join()` 사용)
4. 모든 주문이 배달 완료될 때까지 20초 대기 시간
5. ChefTask와 DeliveryTask 스레드들을 `interrupt()`로 종료
6. 시스템 시작 및 종료 로그 (예상 출력 참고)
7. 마지막에 ‘배달까지 완료된 주문: X개’ 출력 → AtomicInteger 사용 추천 (이후 강의 내용으로 구현 필수X) → 하드코딩 해도 됨

### 예상 출력

```
=== 배달 주문 처리 시스템 시작 ===

[주문접수1] 주문 생성: Order{orderId=1, menu='치킨버거', address='강남구'}
[put] 생산자 데이터 저장, notify() 호출
[take] 소비자 데이터 획득, notify() 호출
[요리사1] 요리 시작: Order{orderId=1, menu='치킨버거'}

[주문접수2] 주문 생성: Order{orderId=2, menu='피자', address='서초구'}
[put] 생산자 데이터 저장, notify() 호출

[주문접수1] 주문 생성: Order{orderId=3, menu='파스타', address='중구'}
[put] 생산자 데이터 저장, notify() 호출

[요리사1] 요리 완료: Order{orderId=1, menu='치킨버거'}
[put] 생산자 데이터 저장, notify() 호출
[take] 소비자 데이터 획득, notify() 호출
[배달원1] 배달 시작: Order{orderId=1, menu='치킨버거', address='강남구'}

[take] 소비자 데이터 획득, notify() 호출
[요리사1] 요리 시작: Order{orderId=2, menu='피자'}

[주문접수2] 주문 생성: Order{orderId=4, menu='돈까스', address='마포구'}
[put] 큐가 가득 참, 생산자 대기

[배달원1] 배달 완료: Order{orderId=1, menu='치킨버거'}

[요리사1] 요리 완료: Order{orderId=2, menu='피자'}
[put] 생산자 데이터 저장, notify() 호출
[take] 소비자 데이터 획득, notify() 호출
[배달원2] 배달 시작: Order{orderId=2, menu='피자', address='서초구'}

[take] 소비자 데이터 획득, notify() 호출
[요리사1] 요리 시작: Order{orderId=3, menu='파스타'}
[put] 생산자 깨어남
[put] 생산자 데이터 저장, notify() 호출

[주문접수1] 주문 생성: Order{orderId=5, menu='김치찌개', address='용산구'}
[put] 큐가 가득 참, 생산자 대기

[배달원2] 배달 완료: Order{orderId=2, menu='피자'}

[요리사1] 요리 완료: Order{orderId=3, menu='파스타'}
[put] 큐가 가득 참, 생산자 대기

[take] 큐에 데이터가 없음, 소비자 대기

[put] 생산자 깨어남
[put] 생산자 데이터 저장, notify() 호출
[take] 소비자 깨어남
[take] 소비자 데이터 획득, notify() 호출
[배달원1] 배달 시작: Order{orderId=3, menu='파스타', address='중구'}

...

[배달원2] 배달 완료: Order{orderId=8, menu='김치찌개'}

=== 시스템 종료 ===
배달까지 완료된 주문: 8개
```

<br><br><br>

# 섹션 10. 생성자 소비자 문제2 (1문제)

## 문제1: 온라인 주문 처리 시스템 구현(고급)

### 문제 설명

대형 온라인 쇼핑몰에서 주문이 폭주하는 상황입니다. 주문 접수 속도가 처리 속도보다 빠를 수 있으므로, 주문을 임시 저장하고 여러 직원이 나누어 처리하는 시스템이 필요합니다.

### 요구사항

### 1. Order 클래스 구현

- orderId (String)
- customerName (String)
- productName (String)
- quantity (int)
- price (int)
- orderTime (LocalDateTime)
- 생성자: 전체 필드
- `getTotalPrice()` 메서드: price * quantity
- `toString()` 메서드: orderId, customerName, productName (예상 출력 참고)

### 2. BoundedQueue 클래스 구현

- `ReentrantLock`과 `Condition`을 사용하여 직접 구현
- 내부 자료구조는 `ArrayDeque` 사용
- 생산자 전용 Condition과 소비자 전용 Condition 분리
- `void put(Order order)` : 큐에 아이템 추가 (큐가 가득 차면 대기)
- `Order take()` : 큐에서 아이템 제거 (큐가 비어있으면 대기)
- `String toString()` : 큐에 대한 toString() 메서드 사용

### 3. ProducerTask 클래스 구현 (Runnable 인터페이스 구현)

- 생성자: `ProducerTask(BoundedQueue<Order> queue, int orderCount)`
- 지정된 개수만큼 주문을 생성하여 큐에 추가
- 주문 ID는 "ORD" + 3자리 숫자 형태로 생성
- 소비자와 상품 이름 예시를 참고하여 랜덤으로 주문을 생성
- 소비자 이름(7개) : `customerNames = {"세종대왕", "이이", "김정희", "정약용", "정도전", "송시열", "이황"}`
- 상품 이름(7개) : `productNames = {"클렌징오일", "마라탕", "전자레인지", "무선이어폰", "그램", "선글라스", "선풍기"}`
- 주문 수량: 1~100 랜덤
- 가격: 1~10000 랜덤
- 생성 날짜: 현재 생성 시간
- Task 관련 메시지 출력 (예상 출력 참고)

### 4. ConsumerTask 클래스 구현 (Runnable 인터페이스 구현)

- 생성자: `ConsumerTask(BoundedQueue<Order> queue, OnlineOrderSystem system)`
- 큐에서 주문을 꺼내어 처리
- 시스템이 종료되고 큐가 비어있을 때까지 계속 처리
- 각 주문 처리 시간은 1초
- `interrupted()` 메서드 사용
- Task 관련 메시지 출력 (예상 출력 참고)

### 5. OnlineOrderSystem 클래스 구현

- totalRevenue (double)
- processedOrders (int)
- 동시성 문제 방지를 위한 장치 필요
- `void processOrder(Order order)` : 개별 주문 처리(매출 누적, 완료 주문수 증가)
- `int getTotalRevenue()` : 총 매출액 반환
- `int getProcessedOrders()` : 처리된 주문 수 반환

### 6. ShoppingMain 클래스에서 다음 시나리오 테스트

- BoundedQueue<Order> 생성 (최대 용량: 15개)
- OnlineOrderSystem 인스턴스 생성
- 5개의 생산자 스레드 생성
    - 각각 ProducerTask로 8개의 주문을 0.3초 간격으로 생성
- 3개의 소비자 스레드 생성
    - 각각 ConsumerTask로 주문 처리
- 모든 생산자 스레드 완료 대기
- 15초 후, 소비자 강제 종료
- 최종 결과 출력 (총 주문 수, 총 매출액)

### 핵심 구현 포인트

- Task 패턴 사용: ProducerTask, ConsumerTask로 분리
- BoundedQueue에서 `lock.lock()`, `condition.await()`, `condition.signal()`, `lock.unlock()` 사용
- 생산자용 Condition과 소비자용 Condition 분리
- 예외 처리 및 스레드 안전성 보장

### 예상 출력

```
[main] === 온라인 주문 처리 시스템 시작 ===
[producer-1] 주문 생성: Order{orderId='ORD001', customerName='김철수', productName='노트북'}
[producer-2] 주문 생성: Order{orderId='ORD002', customerName='이영희', productName='마우스'}
[consumer-2] 주문 처리 시작: Order{orderId='ORD001', customerName='김철수', productName='노트북'}
[consumer-2] 주문 처리 완료: Order{orderId='ORD001', customerName='김철수', productName='노트북'}
...
[main] === 주문 처리 완료 ===
[main] 총 처리 주문: 40개
[main] 총 매출액: 5750000원
```

<br><br><br>

# 섹션 11. CAS: 동기화와 원자적 연산 (1문제)

## 문제1: 온라인 쇼핑몰 재고 관리 시스템(중급)

### 문제 설명

온라인 쇼핑몰에서 여러 고객이 동시에 같은 상품을 주문할 때 재고 관리를 안전하게 처리해야 합니다. 멀티스레드 환경에서 동시 접근이 발생하는 온라인 쇼핑몰의 재고 관리 시스템을 구현합니다. 여러 고객이 동시에 같은 상품을 주문할 때 발생할 수 있는 Race Condition을 방지하고, 원자적 연산(Atomic Operations)을 통해 안전한 재고 관리를 구현하는 것이 목표입니다.

### 요구사항

1. `Product` 클래스
    - 필드: 상품ID, 상품명, 재고량
    - attemptPurchase(int quantity) 메서드: CAS를 사용한 구매 시도 및 결과 출력 (예상 출력 참고)
    - getStock() 메서드: 현재 재고 수량 반환
2. `CustomerThread` 클래스(Runnable 구현)
    - 고객의 주문 처리를 시뮬레이션 진행
    - 각 고객은 4개의 수량을 주문 시도
3. ShoppingMain 클래스
    - 상품 1개를 생성 (ex. 상품ID: 1, 상품명: 자전거, 재고량 30)
    - 초기 재고는 30개로 설정
    - 10명의 고객이 동시에 주문하는 상황

### 참고사항

- 재고 감소는 원자적으로 처리
- CAS 연산을 할 때는 `compareAndSet`메서드를 고려
- 30개 재고 중 10명이 4개씩 재고를 사용하므로, 정확히 7명만 재고를 줄일 수 있고 3명은 실패합니다. 또한, 남은 최종 재고는 2개가 됩니다.

### 예상 출력

```
[     main] === 온라인 쇼핑몰 재고 관리 시스템 ===
[     main] 초기 재고: 30개
[customer-1] 4개 주문 시도 -> 성공! 남은 재고: 26개
[customer-7] 4개 주문 시도 -> 성공! 남은 재고: 2개
[customer-2] 4개 주문 시도 -> 성공! 남은 재고: 22개
[customer-4] 4개 주문 시도 -> 성공! 남은 재고: 14개
[customer-5] 4개 주문 시도 -> 성공! 남은 재고: 10개
[customer-8] 4개 주문 시도 -> 실패! 재고 부족 (현재 재고:2개)
[customer-10] 4개 주문 시도 -> 실패! 재고 부족 (현재 재고:2개)
[customer-9] 4개 주문 시도 -> 실패! 재고 부족 (현재 재고:2개)
[customer-6] 4개 주문 시도 -> 성공! 남은 재고: 6개
[customer-3] 4개 주문 시도 -> 성공! 남은 재고: 18개
[     main] === 주문 처리 완료 ===
[     main] 최종 재고: 2개
```

<br><br><br>

# 섹션 12. 동시성 컬렉션 (1문제)

<br><br><br>

# 섹션 13. 스레드 풀과 Executor 프레임워크1 (2문제)

## 문제1: 증권회사 주식 가격 조회 시스템(고급)

### 문제 설명

증권회사에서 여러 주식의 실시간 가격을 동시에 조회하는 시스템을 구현해야 합니다. 각 주식의 가격 조회는 시간이 걸리는 작업이므로, ExecutorService를 사용하여 병렬로 처리하고, Callable과 Future를 활용하여 결과를 수집해야 합니다.

### 요구사항

1. **Stock 클래스 구현**
    - `String symbol` (주식 코드)
    - `String name` (회사명)
    - 생성자와 getter 메서드
2. **StockPrice 클래스 구현**
    - `String symbol` (주식 코드)
    - `double price` (가격)
    - `LocalDateTime timestamp` (조회 시각)
    - 생성자, getter 메서드, toString() 오버라이드
3. **StockPriceService 클래스 구현**
    - `StockPrice getPrice(Stock stock)` 메서드
    - 실제 API 호출을 시뮬레이션하기 위해 1~3초 랜덤 지연
    - 가격은 1000-50000 사이의 랜덤 값
4. PriceSearchCallabe 클래스 (Callable 구현)
    - Stock 정보를 바탕으로 stockPriceService를 통해 정보 조회
    - 조회 중 관련 로그 제공 (예상 출력 참고)
5. **StockTradingSystem 클래스 구현**
    - `ExecutorService executor` 필드 (FixedThreadPool 사용, 스레드 풀 크기: 5)
    - `StockPriceService priceService` 필드
    - `List<StockPrice> getAllStockPrices(List<Stock> stocks)` 메서드
        - 모든 주식 가격을 병렬로 조회
        - Callable과 Future 사용
        - 모든 작업 완료 후 결과 반환
    - `void shutdown()` 메서드로 ExecutorService 종료
6. **Main 클래스에서 테스트**
    - 최소 7개의 서로 다른 주식 생성 (ex. 삼성전자, LG전자, SK하이닉스, NAVER, 카카오, 현대차, 포스코)
    
    ```java
    List<Stock> stocks = List.of(
            new Stock("005930", "삼성전자"),
            new Stock("000660", "SK하이닉스"),
            new Stock("035420", "NAVER"),
            new Stock("035720", "카카오"),
            new Stock("005380", "현대차"),
            new Stock("066570", "LG전자"),
            new Stock("005490", "POSCO홀딩스")
    );
    ```
    
    - StockTradingSystem을 사용하여 모든 주식 가격 조회
    - 시작 시간과 종료 시간을 측정하여 전체 소요 시간 출력 (예상 출력 참고)
    - 시스템 종료
    - 시작, 시스템 결과, 종료 관련 로그 출력 (예상 출력 참고)

### 예상 출력

```java
[     main] === 증권회사 주식 가격 조회 시스템 ===
[pool-1-thread-3] NAVER 조회 중...
[pool-1-thread-5] 현대차 조회 중...
[pool-1-thread-1] 삼성전자 조회 중...
[pool-1-thread-4] 카카오 조회 중...
[pool-1-thread-2] SK하이닉스 조회 중...
[pool-1-thread-3] LG전자 조회 중...
[pool-1-thread-2] POSCO홀딩스 조회 중...
[     main] === 증권회사 주식 가격 조회 시스템 결과 ===
[     main] StockPrice{symbol='005930', price=22911.7447284399, timestamp=2025-08-17T22:20:43.161538}
[     main] StockPrice{symbol='000660', price=5628.202074875279, timestamp=2025-08-17T22:20:42.565242}
[     main] StockPrice{symbol='035420', price=7990.671465882071, timestamp=2025-08-17T22:20:42.183205}
[     main] StockPrice{symbol='035720', price=49811.58433055176, timestamp=2025-08-17T22:20:42.991665}
[     main] StockPrice{symbol='005380', price=31627.981760164017, timestamp=2025-08-17T22:20:43.967199}
[     main] StockPrice{symbol='066570', price=46087.63838877258, timestamp=2025-08-17T22:20:44.185276}
[     main] StockPrice{symbol='005490', price=12415.551752912213, timestamp=2025-08-17T22:20:45.070769}
[     main] [총 소요 시간: 4.05초]
[     main] === 증권회사 주식 가격 조회 시스템 종료 ===
```

<br>

## 문제2: 증권회사 대량 주문 처리 시스템과 긴급 취소 기능(고급)

### 문제 설명

증권회사에서 대량 매수/매도 주문을 처리하는 시스템을 운영 중입니다. 시장 상황이 급변할 때 진행 중인 주문들을 긴급히 취소해야 하는 경우가 있습니다. 이때 Future.cancel()의 `mayInterruptIfRunning` 파라미터에 따른 동작 차이를 이해하고 구현해야 합니다.

### 문제 설명 상세

- 일반 취소 (false): 아직 시작되지 않은 주문만 취소 (실행 중인 주문은 완료까지 대기)
- 강제 취소 (true): 실행 중인 주문도 강제로 중단 (InterruptedException 발생)

### 요구사항

1. TradeOrder 클래스 구현
    - `String orderId` (주문 ID)
    - `String symbol` (주식 코드)
    - `int quantity` (수량)
    - `TradeOrderType orderType` ("BUY" 또는 "SELL"로 ENUM 제작)
    - 생성자, getter, toString() 메서드
2. TradeResult 클래스 구현
    - `String orderId` (주문 ID)
    - `TradeResultStatus status` ("COMPLETED", "CANCELLED", "INTERRUPTED", "FAILED"로 ENUM 제작)
    - `String message` (결과 메시지)
    - `String errorType` (오류 타입 - 예외 발생 시)
    - 생성자, getter, toString() 메서드
3. OrderProcessor 클래스 구현 (Callable<TradeResult> 구현)
    - `TradeOrder order` 필드
    - 생성자로 order 한 개 받기
    - `call()` 메서드 구현:
        - TradeOrder의 ID와 ‘처리 중…’ 출력 (예상 출력 참고)
        - 주문 처리 시뮬레이션 : Thread.sleep() 사용 및 4~8초 랜덤 소요
        - 예외 상황 시뮬레이션: 주문 ID가 "ERROR"로 시작하면 RuntimeException 발생
            - FAILED 상태인 TradeResult 반환
            - 메시지: "시스템 오류로 인한 주문 실패"
        - 네트워크 오류 시뮬레이션: 주문 ID가 "NETWORK"로 시작하면 IOException 발생
            - FAILED 상태인 TradeResult 반환
            - 메시지: "네트워크 오류로 인한 주문 실패"
        - InterruptedException 발생 시 TradeOrder의 ID와 ‘실행 중인 주문 강제 종료…’ 출력 (예상 출력 참고)
        - 정상 완료 시 COMPLETED" 상태인 TradeResult 반환
            - 메시지: “주문이 성공적으로 완료되었습니다”
4. TradingSystem 클래스 구현
    - `ExecutorService executor` (FixedThreadPool, 크기: 3)
    - `Map<String, Future<TradeResult>> runningOrders` (실행 중인 주문 관리)
    - `submitOrder(TradeOrder order)` 메서드: 주문 제출 후 Future를 Map에 저장
    - `cancelOrder(String orderId, boolean forceCancel)` 메서드:
        - TradeOrder의 ID와 ‘취소 중…’ 출력 (예상 출력 참고)
        - forceCancel이 true면 cancel(true) 호출
        - forceCancel이 false면 cancel(false) 호출
    - `waitForAllOrders()` 메서드: runningOrders의 모든 주문 완료 대기 및 결과 수집
        - Future.get() 예외 처리 필수:
            - 각 예외별로 적절한 TradeResult 생성
            - `CancellationException`: 취소된 작업에 대한 get() 호출 시 발생 (CANCLLED 상태인 TradeResult 반환)
            - `ExecutionException`: 작업 중 발생한 예외 처리 (getCause()로 원본 예외 확인)
            - `InterruptedException`: get() 대기 중 현재 스레드가 interrupt된 경우 예외 처리
            - Callable의 예외 처리, Future.get()의 예외 처리, main 스레드의 예외 처리를 고려하여 작성
    - `shutdown()` 메서드
5. Main 클래스에서 시나리오 테스트
    - 시나리오 1: 5개 주문 제출 후 정상 작동 테스트
        
        ```java
        tradingSystem.submitOrder(new TradeOrder("BUY001", "AAPL", 100, TradeOrderType.BUY));
        tradingSystem.submitOrder(new TradeOrder("SELL002", "GOOG", 50, TradeOrderType.SELL));
        tradingSystem.submitOrder(new TradeOrder("BUY003", "MSFT", 200, TradeOrderType.BUY));
        tradingSystem.submitOrder(new TradeOrder("SELL004", "TSLA", 10, TradeOrderType.SELL));
        tradingSystem.submitOrder(new TradeOrder("BUY005", "AMZN", 30, TradeOrderType.BUY));
        ```
        
    - **시나리오 2**: 5개 주문 제출 후 2초 뒤 일반 취소(false) 테스트
        - 제출한 주문 중 음과 마지막 주문 취소
        
        ```java
        tradingSystem.submitOrder(new TradeOrder("BUY001", "AAPL", 100, TradeOrderType.BUY));
        tradingSystem.submitOrder(new TradeOrder("SELL002", "GOOG", 50, TradeOrderType.SELL));
        tradingSystem.submitOrder(new TradeOrder("BUY003", "MSFT", 200, TradeOrderType.BUY));
        tradingSystem.submitOrder(new TradeOrder("SELL004", "TSLA", 10, TradeOrderType.SELL));
        tradingSystem.submitOrder(new TradeOrder("BUY005", "AMZN", 30, TradeOrderType.BUY));
        ```
        
    - **시나리오 3**: 5개 주문 제출 후 3초 뒤 강제 취소(true) 테스트
        - 제출한 주문 중 처음과 마지막 주문 취소
        
        ```java
        tradingSystem.submitOrder(new TradeOrder("BUY001", "AAPL", 100, TradeOrderType.BUY));
        tradingSystem.submitOrder(new TradeOrder("SELL002", "GOOG", 50, TradeOrderType.SELL));
        tradingSystem.submitOrder(new TradeOrder("BUY003", "MSFT", 200, TradeOrderType.BUY));
        tradingSystem.submitOrder(new TradeOrder("SELL004", "TSLA", 10, TradeOrderType.SELL));
        tradingSystem.submitOrder(new TradeOrder("BUY005", "AMZN", 30, TradeOrderType.BUY));
        ```
        
    - **시나리오 4**: 예외 발생 주문들을 포함한 테스트
        - "ERROR001", "NETWORK002" 등 예외를 유발하는 주문 ID 사용
        - Future.get() 호출 시 발생하는 다양한 예외 상황 테스트
        
        ```java
        tradingSystem.submitOrder(new TradeOrder("SELL002", "GOOG", 50, TradeOrderType.SELL));
        tradingSystem.submitOrder(new TradeOrder("BUY003", "MSFT", 200, TradeOrderType.BUY));
        tradingSystem.submitOrder(new TradeOrder("ERROR_SELL004", "TSLA", 10, TradeOrderType.SELL));
        tradingSystem.submitOrder(new TradeOrder("BUY005", "AMZN", 30, TradeOrderType.BUY));
        tradingSystem.submitOrder(new TradeOrder("NETWORK_BUY006", "AAPL", 150, TradeOrderType.BUY));
        tradingSystem.submitOrder(new TradeOrder("SELL007", "NVDA", 25, TradeOrderType.SELL));
        tradingSystem.submitOrder(new TradeOrder("ERROR_BUY008", "META", 75, TradeOrderType.BUY));
        tradingSystem.submitOrder(new TradeOrder("SELL009", "NFLX", 40, TradeOrderType.SELL));
        ```
        

### 예상 출력

- 시나리오1

```java
[     main] === 증권회사 대량 주문 처리 시스템 ===
[pool-1-thread-2] SELL002 처리 중...
[pool-1-thread-3] BUY003 처리 중...
[pool-1-thread-1] BUY001 처리 중...
[pool-1-thread-2] SELL004 처리 중...
[pool-1-thread-1] BUY005 처리 중...
[     main] TradeResult{orderId='SELL002', status=COMPLETED, message='주문이 성공적으로 완료되었습니다', errorType='null'}
[     main] TradeResult{orderId='SELL004', status=COMPLETED, message='주문이 성공적으로 완료되었습니다', errorType='null'}
[     main] TradeResult{orderId='BUY005', status=COMPLETED, message='주문이 성공적으로 완료되었습니다', errorType='null'}
[     main] TradeResult{orderId='BUY003', status=COMPLETED, message='주문이 성공적으로 완료되었습니다', errorType='null'}
[     main] TradeResult{orderId='BUY001', status=COMPLETED, message='주문이 성공적으로 완료되었습니다', errorType='null'}
[     main] === 증권회사 대량 주문 처리 시스템 종료 ===
```

- 시나리오2

```java
[     main] === 증권회사 대량 주문 처리 시스템 ===
[pool-1-thread-2] SELL002 처리 중...
[pool-1-thread-1] BUY001 처리 중...
[pool-1-thread-3] BUY003 처리 중...
[     main] SELL002 취소 중...
[     main] BUY005 취소 중...
[pool-1-thread-3] SELL004 처리 중...
[     main] TradeResult{orderId='SELL002', status=CANCELLED, message='주문이 취소되었습니다', errorType='CancellationException'}
[     main] TradeResult{orderId='SELL004', status=COMPLETED, message='주문이 성공적으로 완료되었습니다', errorType='null'}
[     main] TradeResult{orderId='BUY005', status=CANCELLED, message='주문이 취소되었습니다', errorType='CancellationException'}
[     main] TradeResult{orderId='BUY003', status=COMPLETED, message='주문이 성공적으로 완료되었습니다', errorType='null'}
[     main] TradeResult{orderId='BUY001', status=COMPLETED, message='주문이 성공적으로 완료되었습니다', errorType='null'}
[     main] === 증권회사 대량 주문 처리 시스템 종료 ===
```

- 시나리오3

```java
[     main] === 증권회사 대량 주문 처리 시스템 ===
[pool-1-thread-3] BUY003 처리 중...
[pool-1-thread-2] SELL002 처리 중...
[pool-1-thread-1] BUY001 처리 중...
[     main] SELL002 취소 중...
[     main] BUY005 취소 중...
[pool-1-thread-2] SELL002 실행 중인 주문 강제 종료...
[pool-1-thread-2] SELL004 처리 중...
[     main] TradeResult{orderId='SELL002', status=CANCELLED, message='주문이 취소되었습니다', errorType='CancellationException'}
[     main] TradeResult{orderId='SELL004', status=COMPLETED, message='주문이 성공적으로 완료되었습니다', errorType='null'}
[     main] TradeResult{orderId='BUY005', status=CANCELLED, message='주문이 취소되었습니다', errorType='CancellationException'}
[     main] TradeResult{orderId='BUY003', status=COMPLETED, message='주문이 성공적으로 완료되었습니다', errorType='null'}
[     main] TradeResult{orderId='BUY001', status=COMPLETED, message='주문이 성공적으로 완료되었습니다', errorType='null'}
[     main] === 증권회사 대량 주문 처리 시스템 종료 ===
```

- 시나리오4

```java
[     main] === 증권회사 대량 주문 처리 시스템 ===
[pool-1-thread-3] ERROR_SELL004 처리 중...
[pool-1-thread-1] SELL002 처리 중...
[pool-1-thread-2] BUY003 처리 중...
[     main] SELL002 취소 중...
[pool-1-thread-1] SELL002 실행 중인 주문 강제 종료...
[     main] BUY005 취소 중...
[pool-1-thread-1] BUY005 처리 중...
[pool-1-thread-1] BUY005 실행 중인 주문 강제 종료...
[pool-1-thread-1] NETWORK_BUY006 처리 중...
[pool-1-thread-3] SELL007 처리 중...
[pool-1-thread-2] ERROR_BUY008 처리 중...
[pool-1-thread-3] SELL009 처리 중...
[     main] TradeResult{orderId='SELL007', status=COMPLETED, message='주문이 성공적으로 완료되었습니다', errorType='null'}
[     main] TradeResult{orderId='SELL009', status=COMPLETED, message='주문이 성공적으로 완료되었습니다', errorType='null'}
[     main] TradeResult{orderId='SELL002', status=CANCELLED, message='주문이 취소되었습니다', errorType='CancellationException'}
[     main] TradeResult{orderId='ERROR_BUY008', status=FAILED, message='주문이 실패하였습니다', errorType='RuntimeException'}
[     main] TradeResult{orderId='ERROR_SELL004', status=FAILED, message='주문이 실패하였습니다', errorType='RuntimeException'}
[     main] TradeResult{orderId='BUY005', status=CANCELLED, message='주문이 취소되었습니다', errorType='CancellationException'}
[     main] TradeResult{orderId='NETWORK_BUY006', status=FAILED, message='주문이 실패하였습니다', errorType='IOException'}
[     main] TradeResult{orderId='BUY003', status=COMPLETED, message='주문이 성공적으로 완료되었습니다', errorType='null'}
[     main] === 증권회사 대량 주문 처리 시스템 종료 ===
```

<br><br><br>

# 섹션 14. 스레드 풀과 Executor 프레임워크2 (2문제)