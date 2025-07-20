# 김영한의 실전 자바 - 고급 1편, 멀티스레드와 동시성

<br>

| 섹션 번호 | 초급 | 중급 | 고급 |
| --- | --- | --- | --- |
| 섹션 2 |  |  |  |
| 섹션 3 | 1 |  |  |
| 섹션 4 | 1 |  |  |
| 섹션 5 |  | 1 |  |
| 섹션 6 |  |  |  |
| 섹션 7 |  |  |  |
| 섹션 8 |  |  |  |
| 섹션 9 |  | 1 |  |
| 섹션 10 |  |  |  |
| 섹션 11 |  |  |  |
| 섹션 12 |  |  |  |
| 섹션 13 |  |  |  |
| 섹션 14 |  |  |  |

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

- `NumberGame` 클래스를 만들고 `Runnable` 인터페이스를 구현하세요
- 생성자로 `playerName`, `targetNumber`, `maxAttempts`를 받도록 하세요 (추가 및 변형 가능)
- `run()` 메서드에서 다음 게임을 진행하세요:
    - 1~100 사이의 랜덤한 숫자를 생성하여 targetNumber와 비교
    - 정답을 맞출 때까지 또는 maxAttempts만큼 시도
    - 각 시도마다 결과를 출력 (높음/낮음/정답)
    - 500ms~1500ms 사이의 랜덤한 시간 대기 (사고하는 시간)
    - 정답이 아닌 경우(높은/낮음) 해당 힌트를 바탕으로 랜덤한 숫자를 생성해야 함 (선택 로직)
- `GameResultCollector` 데몬 스레드를 만들어서:
    - 2초마다 "게임 진행 상황 체크..." 메시지 출력
    - 총 게임 시간을 추적하여 출력
- 메인 메서드에서:
    - 정답을 미리 설정 (예: 42)
    - 3명의 플레이어가 동시에 게임 진행 (예: `"Alice", "Bob", "Charlie"`)
    - 각각 최대 30번 시도
    - 첫 번째로 정답을 맞춘 플레이어가 우승
    - 모든 플레이어가 게임을 마치면 결과 요약 출력
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

### 문제상황

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

# 섹션 10. 생성자 소비자 문제2 (2문제)