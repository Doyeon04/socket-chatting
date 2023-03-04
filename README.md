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


## 실행 화면

![KakaoTalk_20230304_200027291](https://user-images.githubusercontent.com/71643491/222896400-0f3884dd-70a9-44c8-a30c-4d92e329ed4f.png)

유저 이름을 입력해 입장합니다.  

![KakaoTalk_20230304_200141138](https://user-images.githubusercontent.com/71643491/222896402-e5837114-b048-4827-888e-a633646b5356.png)

입장하면 접속자 리스트에 추가됩니다.
프로필을 바뀌면 나 자신과 다른 사람 모두에게 바뀐 프로필이 보여집니다.  


![3](https://user-images.githubusercontent.com/71643491/222896084-2fef6705-4652-4d05-9619-1ff7fe382986.PNG)

프로필 사진을 확대하여 볼 수 있습니다.  


![5](https://user-images.githubusercontent.com/71643491/222896086-b0142075-d75c-4300-bdde-87fe830edae4.PNG)

단톡방에 사람들을 초대해 채팅방을 생성합니다.
채팅방에서 메세지와 이미지를 전송합니다.  


![6](https://user-images.githubusercontent.com/71643491/222896089-859ee76f-31f2-45dd-9a45-9bda89afd285.PNG)

이모티콘 버튼을 눌러 이모티콘을 전송합니다.  

![7](https://user-images.githubusercontent.com/71643491/222896091-ab683899-0f83-4036-99d0-c67b405ef091.PNG)

대화 내용 삭제 버튼을 누르면 내 채팅방 안의 채팅 내용들이 사라집니다.  

![8](https://user-images.githubusercontent.com/71643491/222896092-1c93d8b3-ad5d-4658-a0f3-7b8795587517.PNG)
![9](https://user-images.githubusercontent.com/71643491/222896095-cdaab9de-2329-4529-93aa-c065337225eb.PNG)

채팅방 나가기 버튼을 누르면 남은 유저들에게 나갔다는 문구가 출력되고, 나간 유저를 제외한 나머지 사람들의 이름만 보여집니다. 
나간 유저는 채팅창이 사라짐과 동시에 나간 채팅방이 채팅 목록에서 사라집니다.  

![10](https://user-images.githubusercontent.com/71643491/222896097-9ce89929-f69a-4af6-994f-b21561fe5297.PNG)

‘나와의 채팅’ 버튼을 클릭하면 나 자신만 있는 채팅방이 만들어집니다.  

![11](https://user-images.githubusercontent.com/71643491/222896098-c0f9c24a-f806-4e3a-b40b-879bee3f1b2e.PNG)

‘배경색 변경’ 버튼을 누르면 채팅방 색깔이 변경됩니다.  

![12](https://user-images.githubusercontent.com/71643491/222896099-6e05e4f8-e251-4f92-bba1-ecaee4a8e255.PNG)

로그아웃한 유저들은 리스트에서 사라집니다.


