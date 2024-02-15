### main : 배포용 develop : 통합용 feature : 각자 기능 개발

# 주요 기술

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

# 팀 노션 페이지(ERD)
https://teamsparta.notion.site/TestyPie-18f9ac07c30f420994e9f9c7d640df57

# API
Swagger


# Directory
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
