![header](https://capsule-render.vercel.app/api?type=venom&color=ADD8E6&text=MSA%20AI%20PROJECT)

## 🐰 프로젝트 상세

> 물류 관리 및 배송 시스템을 위한 MSA 기반 플랫폼
- B2B 국내 물류 관리 및 배송 시스템을 개발한 프로젝트 입니다.
- 각 지역에 허브센터를 가지고 있으며 허브 센터는 여러 업체의 물건을 보관하는 구조 입니다.
- 업체는 생산 업체와 수령 업체로 구분되며 모든 상품은 특정 업체와 허브에 소속되어 있습니다.
- 배송 담당자는 공통 허브 이동 담당자와 업체 배송 담당자로 나뉘며, 두 타입 간의 업무는 명확히 구분됩니다.
- 공통 허브 이동 담당자는 업체 배송을 할 수 없으며, 업체 배송 담당자는 허브 이동을 담당할 수 없습니다.

### 기본 기능
- 전체 도메인에 대한 CRUD
- 검색 기능

### 추가 기능
- 허브 간 이동 정보는 선형 리스트 형태가 아닌 그래프 형태입니다.
- 허브를 생성 시 주소를 기반으로 위도와 경도가 자동 등록 됩니다.
- 허브 간 이동 생성 시 출발 허브와 목적 허브를 입력하면 이동 시간과 이동 거리가 자동으로 등록됩니다.
- 주문 생성 시 허브 간 이동 최적 경로(다익스트라 알고리즘 사용)가 계산되어 배송 경로 테이블에 자동 저장 됩니다.
- 슬랙 메시지를 통해 배송 현황을 알림으로 받을 수 있습니다.

### 모듈 구성
- api-gateway server
- eureka server
- auth Server
- common module
  - audit
  - config
  - customPage
  - dto
  - enum
  - exception
- user Service
  - user
- ai Service
  - gemini
- company-product service
  - company
  - product
- hub service
  - hub
  - hubPath
  - manager
- order-delivery service
  - order
  - delivery
- slack service
  - slack

## 🍏 Personal Role
| Name                                    | Role                          | Domain                                    |
|-----------------------------------------|-------------------------------|-------------------------------------------|
| [임예이(팀장)](https://github.com/coding-911)   | 백엔드 개발       | **BE:** `AI`, `HUB`, `HUBPATH`, `MANAGER` |
| [임수진](https://github.com/lsj104)    | 백엔드 개발                   | **BE:** `COMPANY`, `PRDUCT`, `ORDER`      |
| [김진선](https://github.com/kimzinsun)        | 백엔드 개발                   | **BE:** `DELIVERY`, `SLACK`               |
| [김례화](https://github.com/ryehwa)      | 백엔드 개발 | **BE:** `USER`, `AUTH`                    |

## 🐸 실행 방법

1. 아래의 환경 변수 설정 (프로젝트에 맞게 수정)
    ```dotenv
    # 아래는 예시입니다. 프로젝트에 맞게 수정해주세요.    
    JWT_SECRET_KEY={JWT_SECRET_KEY}
    ```
    - `구성 편집` -> `빌드 및 실행` -> `옵션 수정` -> `환경 변수` 선택 -> 환경 변수에 아래의 형식으로 작성
    - `키1=값1;키2=값2`
2. 프로젝트에 맞게 [docker-compose.yml](./docker-compose.yml) 수정
3. 도커 실행
4. 스프링 실행
    - `docker compose support` 라이브러리가 자동으로 컨테이너를 실행 및 종료합니다.

## 🐹 개발 환경

| 분류         | 상세                                  |
|------------|:------------------------------------|
| IDE        | IntelliJ                            |
| Language   | Java 17                             |
| Framework  | Spring Boot 3.3.3                   |
| Repository | PostgreSQL 16.4, H2 In-memory(Test) |
| Build Tool | Gradle 8.8                          |
| Infra      | EC2, Docker, Github Actions         |

## 👻 상세 개발 환경

### Dependencies

- Spring Validation
- Spring Security
- Spring Data Jpa
- Spring Data Redis
- spring Boot Starter Actuator
- jjwt 0.12.5
- QueryDSL 5.0.0
- Lombok
- JUnit
- Eureka
- OpenFeign
- Kafka
- Prometheus

### API
- Gemini API
- KakaoMap API
- KakaoNavi API
- Slack API

## 🐳 ERD

![ERD](./docs/images/sample-squirrel.jpg)

- [ErdCloud](https://www.google.co.kr/)

## 🐙 API docs

- [Swagger UI](https://www.google.co.kr/)

## 🐬 Infra
<img width="742" alt="스크린샷 2024-09-19 오전 6 10 00" src="https://github.com/user-attachments/assets/70d3603c-0beb-4551-a272-9a9c7e0df188">

