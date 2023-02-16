# KokoaTalk


<br>

## 프로젝트 소개

Socket.io를 이용하여 실시간 양방향 통신 기능을 구현한 채팅 프로그램입니다.

<br>
## 개발 환경

- 개발 언어 및 기술: Java, Socket.io, Swing
<br>

## 기능

- 유저

  - 유저 로그인시 접속자 목록에 추가, 로그아웃시 접속자 목록에서 삭제 
  - 유저들 초대해 단톡방 생성
  - 단톡방에서 유저 나가면 채팅창, 채팅 목록 업데이트
  - 나와의 채팅방 구현

- 채팅방

  - 메세지, 사진, 이모티콘 전송
  - 프로필 사진, 이름, 보낸 시간 표시
  - 대화 내용 저장
  - 대화 내용 모두 삭제
  - 채팅방 배경색 변경

- 채팅 목록

  - 각 채팅방의 참여자, 마지막 메시지, 보낸 시간 출력

- 프로필

  - 프로필 사진 변경시 접속자 목록과 채팅방에 프로필 사진 업데이트
  - 프로필 사진 클릭 시 크게 보기

  <br>

## 프로토콜

| Protocol |        내용         |             방향             |
| :------: | :---------------: | :------------------------: |
|   100    |       Login       |     Client  -> Server      |
|   101    |   새로운 유저 리스트 받기   |      Server -> Client      |
|   210    |   단체 채팅 Message   | Client -> Server -> Client |
|   300    |       Image       | Client -> Server -> Client |
|   400    |      Logout       |      Client -> Server      |
|   500    |      채팅방 생성       |      Client -> Server      |
|   550    | 본인이 초대된 채팅방 정보 받기 |      Server -> Client      |
|   560    |  채팅방 나간 유저 정보 전송  | Client -> Server -> Client |
|   700    |      프로필 변경       |     Client  -> Server      |
|   750    | 변경된 프로필 유저들에게 알림  |      Server -> Client      |

<br>

## 시스템 흐름도
![시스템 흐름도](https://user-images.githubusercontent.com/71643491/219285081-6eaa26f0-b0a0-4bf6-8b5e-29d8b1e972da.png)



<br>

