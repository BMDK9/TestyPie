### 프로젝트 기간
* 2024년 1월 4일 → 2024년 2월 8일
* 2024년 3월 1일 추가 작업 중단
  * 능력 부족으로 인한 개인 공부 시작

## 주요 기술

### BackEnd
* java 17
* Spring Boot 3.2.1
* thymeleaf
* validation
* JWT
* Spring Data JPA
* QureyDsl 5.0.0
* Spring Security
* OAuth 2
* Swagger 2.2.0

### FrontEnd
* HTML
* CSS
* Ajax

### DB
* MySQL

### Infra
* AWS
  * EC2 
  * RDS
  * S3
  * Route53
  * LoadBalancer
* ubuntu 22.04
* Docker
  * Docker
  * DockerHub
* Git
  * Git
  * GitHub
  * GitHub Action

## 팀 노션 페이지(ERD)
https://teamsparta.notion.site/TestyPie-18f9ac07c30f420994e9f9c7d640df57

## 기술 노트(Github Wiki)
https://github.com/BMDK9/TestyPie/wiki

## API
![testyPieSwagger1(new)](https://github.com/BMDK9/TestyPie/assets/144665614/0cec090f-090e-428a-b232-d704b19b5240)
![testyPieSwagger2](https://github.com/BMDK9/TestyPie/assets/144665614/d1dd1f99-5118-447b-87d0-2891abc3b362)
![testyPieSwagger3](https://github.com/BMDK9/TestyPie/assets/144665614/475db819-71aa-427f-84db-462e747dc33f)



## Directory
```bash
testypie
 ├─domain
 │  ├─bugreport
 │  │  ├─controller
 │  │  ├─dto
 │  │  │  ├─request
 │  │  │  └─response
 │  │  ├─entity
 │  │  ├─repository
 │  │  └─service
 │  ├─category
 │  │  ├─controller
 │  │  ├─dto
 │  │  │  ├─request
 │  │  │  └─response
 │  │  ├─entity
 │  │  ├─repository
 │  │  └─service
 │  ├─comment
 │  │  ├─constant
 │  │  ├─controller
 │  │  ├─dto
 │  │  │  ├─request
 │  │  │  └─response
 │  │  ├─entity
 │  │  ├─repository
 │  │  └─service
 │  ├─commentLike
 │  │  ├─constant
 │  │  ├─controller
 │  │  ├─dto
 │  │  │  └─response
 │  │  ├─entity
 │  │  ├─repository
 │  │  └─service
 │  ├─core
 │  │  └─service
 │  ├─feedback
 │  │  ├─controller
 │  │  ├─dto
 │  │  │  ├─request
 │  │  │  └─response
 │  │  ├─entity
 │  │  ├─repository
 │  │  └─service
 │  ├─product
 │  │  ├─constant
 │  │  ├─controller
 │  │  ├─dto
 │  │  │  ├─request
 │  │  │  └─response
 │  │  ├─entity
 │  │  ├─repository
 │  │  └─service
 │  ├─productLike
 │  │  ├─constant
 │  │  ├─controller
 │  │  ├─dto
 │  │  │  └─response
 │  │  ├─entity
 │  │  ├─repository
 │  │  └─service
 │  ├─reward
 │  │  ├─controller
 │  │  ├─dto
 │  │  │  ├─request
 │  │  │  └─response
 │  │  ├─entity
 │  │  └─service
 │  ├─scheduler
 │  ├─survey
 │  │  ├─controller
 │  │  ├─dto
 │  │  │  ├─request
 │  │  │  └─response
 │  │  ├─entity
 │  │  ├─repository
 │  │  └─service
 │  ├─user
 │  │  ├─constant
 │  │  ├─controller
 │  │  ├─dto
 │  │  │  ├─request
 │  │  │  └─response
 │  │  ├─entity
 │  │  ├─kakao
 │  │  │  ├─config
 │  │  │  ├─dto
 │  │  │  └─service
 │  │  ├─repository
 │  │  └─service
 │  └─util
 ├─global
 │  ├─config
 │  ├─exception
 │  ├─filter
 │  ├─jwt
 │  ├─security
 │  └─validator
 └─View
     └─controller
```
