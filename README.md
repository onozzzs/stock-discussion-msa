# stock-discussion-msa

## 소개
#### 주가 정보 및 주식 종목 토론 게시판 제공
주가 별 일봉 데이터를 조회할 수 있으며 종목 별 종목 토론 게시글을 등록할 수 있습니다.

## Install
``` ```

---

## Stacks
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=Spring&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white)
![amazons3](https://img.shields.io/badge/amazons3-569A31?style=for-the-badge&logo=amazons3&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=Redis&logoColor=white)

---

## 주요 기능
### 게시글 등록 기능
- 게시글 등록 가능
- 댓글, 대댓글 작성 가능
### 종목 조회
- 종목 별 일봉 데이터 조회 가능
- 종목 별 이동평균값 조회 가능

---

## 아키텍쳐
### 디렉토리 구조
```
├─activity-service
│  └─src
│      ├─main
│      │  ├─java
│      │  │  └─com
│      │  │      └─example
│      │  │          └─activity
│      │  │              ├─api
│      │  │              ├─config
│      │  │              ├─controller
│      │  │              ├─dto
│      │  │              │  ├─board
│      │  │              │  ├─comment
│      │  │              │  └─post
│      │  │              ├─model
│      │  │              ├─repository
│      │  │              └─service
│      │  └─resources
├─batch-service
│  ├─csv
│  └─src
│      ├─main
│      │  ├─java
│      │  │  └─com
│      │  │      └─example
│      │  │          └─batch
│      │  │              ├─config
│      │  │              ├─csv
│      │  │              ├─dailyStock
│      │  │              ├─dto
│      │  │              ├─listener
│      │  │              ├─model
│      │  │              ├─repository
│      │  │              ├─schedule
│      │  │              └─stockIndicator
│      │  └─resources
├─discovery-service
│  └─src
│      ├─main
│      │  ├─java
│      │  │  └─com.example.discovery
│      │  └─resources
├─gateway-service
│  └─src
│      ├─main
│      │  ├─java
│      │  │  └─com
│      │  │      └─example
│      │  │          └─apigateway
│      │  │              ├─config
│      │  │              ├─filter
│      │  │              └─util
│      │  └─resources
├─newsfeed-service
│  └─src
│      ├─main
│      │  ├─java
│      │  │  └─com
│      │  │      └─example
│      │  │          └─newsfeed
│      │  │              ├─api
│      │  │              ├─controller
│      │  │              ├─dto
│      │  │              ├─model
│      │  │              ├─repository
│      │  │              └─service
│      │  └─resources
├─stock-service
│  └─src
│      ├─main
│      │  ├─java
│      │  │  └─com
│      │  │      └─example
│      │  │          └─stock
│      │  │              ├─config
│      │  │              ├─controller
│      │  │              ├─dto
│      │  │              ├─model
│      │  │              ├─repository
│      │  │              └─service
│      │  └─resources
└─user-service
    └─src
        ├─main
        │  ├─java
        │  │  └─com
        │  │      └─example
        │  │          └─user
        │  │              ├─api
        │  │              ├─config
        │  │              ├─controller
        │  │              │  └─auth
        │  │              ├─dto
        │  │              ├─model
        │  │              ├─repository
        │  │              ├─security
        │  │              ├─service
        │  │              └─util
        │  └─resources
```
