# 특강 신청 서비스

## Description

- 특강 신청 서비스를 구현해봅니다.
- 토요일에 열리는 한 특강을 신청할 수 있는 서비스를 개발합니다.
- 특강 신청 및 신청자 목록 관리를 RDBMS를 이용해 관리할 방법을 고민합니다.

## Requirements

- 아래 두 가지 API를 구현합니다.
    - 특강 신청 API
    - 특강 신청 여부 조회 API
- 다수의 인스턴스로 어플리케이션이 동작하더라도 기능에 문제가 없도록 작성하도록 합니다.
- 동시성 이슈를 고려하여 구현합니다.

### API Requirements

1. **특강 신청 API**
    - 특정 `userId` 로 선착순으로 제공되는 특강을 신청하는 API 를 작성합니다.
    - 동일한 신청자는 한 번의 수강 신청만 성공할 수 있습니다.
    - 특강은 **4월 20일 토요일 1시** 에 열리며, **선착순 30명**만 신청 가능합니다.
    - 이미 신청자가 30명이 초과되면 이후 신청자는 요청을 실패합니다.

2. **특강 신청 완료 여부 조회 API**
    - 특정 userId 로 특강 신청 완료 여부를 조회하는 API 를 작성합니다.
    - 특강 신청에 성공한 사용자는 성공했음을, 특강 등록자 명단에 없는 사용자는 실패했음을 반환합니다.

## API Specs

1. **특강 신청 API**
    - Endpoint: `/api/lectures/register`
    - Method: `POST`
    - Request body
        ```json 
        {
            "userId": 1,    
            "lectureId": 2  
        }
        ```
    - Response
        ```json
        // TODO: 작성 예정
        ```
2. **특강 신청 완료 여부 조회 API**
    - Endpoint: `/api/lectures/{lectureId}/check-registrations`
    - Method: `GET`
    - Request parameters
        - `userId`
    - Response
        ```json
        {
          "isRegistered": true
        }
        ```

## ERD

![hpp-chap2-erd](https://i.ibb.co/TYztvxW/hpp-chap2-erd-3.png)
