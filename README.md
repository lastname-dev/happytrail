# 🛫 HappyTrail

## 💁‍♂️ 프로젝트 소개

HappyTrail은 여행 계획을 공유 할 수 있는 웹 서비스 입니다.

**기간** : 2023.05.12 ~ 2023.05.25

**인원** : 2명 (최종명, 임단비)

---

## 🎬 시연 영상

https://youtu.be/w7pCXWuZtfo

---

## ⚙️ 개발 환경

### Front-end

<img src="https://img.shields.io/badge/Vue.js-4FC08D?style=flat-square&logo=Vue.js&logoColor=white"/></a>

### Back-end

<img src="https://img.shields.io/badge/springboot-6DB33F?style=flat-square&logo=springboot&logoColor=white"/></a>
<img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white"/></a>

### ETC

<img src="https://img.shields.io/badge/jupyter-F37626?style=flat-square&logo=jupyter&logoColor=white"/></a>
<img src="https://img.shields.io/badge/kakaoAPI-FFCD00?style=flat-square&logo=kakao&logoColor=white"/></a> <img src="https://img.shields.io/badge/googleAPI-4285F4?style=flat-square&logo=google&logoColor=white"/></a>

---

## 🎯 프로젝트 목표

### Front-end

-   사용자 인터페이스 및 경험 (UI/UX) 개선
-   Vue.js의 다양한 기능 활용 (Vuex를 통한 전역 상태 관리, 컴포넌트 재사용, Axios를 이용한 비동기 통신)

### Back-end

-   공공 데이터 및 외부 API 활용
-   프로젝트 기획 및 문서화
-   데이터베이스 활용의 이해와 구현

### 공통

-   RESTful API 구현
-   풀스택 개발을 통한 전체 시스템 이해 및 구축

---

## 📋 USECASE

### 회원 관리

<img width="400" alt="유저관리 usecase" src="https://github.com/lastname-dev/happytrail/assets/81070080/8ddc15ba-5872-4021-ba10-142f5fd3d548">

### 게시판

<img width="400" alt="게시판 usecase" src="https://github.com/lastname-dev/happytrail/assets/81070080/145cb71a-d546-49e4-a54c-f8d351a7fd2f">

### 여행 계획

<img width="400" alt="플랜 usecase" src="https://github.com/lastname-dev/happytrail/assets/81070080/98154941-31f2-4c22-8269-b9583f93ddef">

### 핫플레이스

<img width="400" alt="핫플 usecase" src="https://github.com/lastname-dev/happytrail/assets/81070080/a14d4c37-6291-4476-84ef-b0e04a04a38c">

### 기타

<img width="400" alt="기타 usecase" src="https://github.com/lastname-dev/happytrail/assets/81070080/5855a50b-2352-40fd-8442-f4a26533c5f5">

---

## 📋 ERD

![image](https://github.com/lastname-dev/happytrail/assets/81070080/a6090488-1c4e-4330-9aa9-64084a0d92c7)

---

## 🖥 화면구성

### 메인

> 장소 검색(`search`)을 제외한 모든 기능은 로그인 시 이용 가능 합니다.

<img width="600" alt="main 오전 12 51 29" src="https://github.com/lastname-dev/happytrail/assets/81070080/9538b860-030f-4719-b90a-4b208fccabc5">

<br>

### 회원가입, 로그인

> ID, PASSWORD, NICKNAME 등 사용자의 정보를 입력 하여 회원가입을 진행 할 수 있으며 회원가입이 완료되면 해당 정보로 로그인이 가능합니다.

<div>
<img width="600" alt="signin" src="https://github.com/lastname-dev/happytrail/assets/81070080/e2f4faa8-c07d-4fdb-b4e0-277966110208">
<img width="600" alt="signup" src="https://github.com/lastname-dev/happytrail/assets/81070080/88749a84-308e-48e5-aced-4aff44dc83fd">
</div>

<br>

### 마이페이지

> 마이페이지에서는 자신의 정보를 수정할 수 있으며, 자신이 등록 및 공유하고 있는 여행계획과 여행후기를 한 눈에 확인할 수 있습니다.

<img width="600" alt="mypage 오전 12 51 29" src="https://github.com/lastname-dev/happytrail/assets/81070080/33bce2d1-1529-4c75-b5c5-1845705e858c">
<img width="600" alt="mypage-edit" src="https://github.com/lastname-dev/happytrail/assets/81070080/3af9049b-e9ab-494a-8320-ac1e3e2ceac4">
<img width="600" alt="mypage-hotplace" src="https://github.com/lastname-dev/happytrail/assets/81070080/d5085c57-152c-4d0d-bc6d-26fe20d53f20">

<br>

### 게시판

> 사용자들 간 여행에 관한 정보를 공유할 수 있습니다.

<img width="600" alt="main 오전 12 51 29" src="https://github.com/lastname-dev/happytrail/assets/81070080/b40ecdf1-2921-4a1e-a441-0c693a4c0466">

<div>
<img width="600" alt="main 오전 12 51 29" src="https://github.com/lastname-dev/happytrail/assets/81070080/3c23981a-b337-4b28-9288-25626ba48666">
<img width="600" alt="main 오전 12 51 29" src="https://github.com/lastname-dev/happytrail/assets/81070080/88704304-c84e-495b-8f77-9f445c9a6969">
</div>
<img width="600" alt="main 오전 12 51 29" src="https://github.com/lastname-dev/happytrail/assets/81070080/f7d43115-57c7-4ad2-bec9-1164ceda2bdd">
<img width="600" alt="main 오전 12 51 29" src="https://github.com/lastname-dev/happytrail/assets/81070080/0dce70f9-9faf-41ae-858f-4f83ee1abb6b">

<br>

### 페스티벌

> 현재 열리고 있는 지역 축제 정보를 확인할 수 있으며, 해당 축제 방문 계획을 작성할 수 있습니다.

<img width="600" alt="festival" src="https://github.com/lastname-dev/happytrail/assets/81070080/fb76be0e-25da-4712-a05c-ee34f560d2e1">
<img width="600" alt="festival detail" src="https://github.com/lastname-dev/happytrail/assets/81070080/bd151ce5-bdf2-461d-9736-5da9a1008cd5">

<br>

### 핫플레이스

> 다녀온 여행 후기들을 한눈에 확인할 수 있으며, 자신이 팔로우한 사용자의 여행 후기만도 확인 가능합니다.

<img width="600" alt="hotplace1" src="https://github.com/lastname-dev/happytrail/assets/81070080/dec5aeb0-16de-4bec-9edb-807a2083cb2d">
<img width="600" alt="hotplacedetail" src="https://github.com/lastname-dev/happytrail/assets/81070080/f11426f3-a1ba-4a0c-a94b-db9ee325b9fa">
<img width="600" alt="hotplaceregist" src="https://github.com/lastname-dev/happytrail/assets/81070080/00c0aed0-9b77-4fa8-8d2e-93a791c51c4c">

<br>

### 여행 계획

> 날짜, 장소, 예산 등을 입력하여 여행 계획을 작성할 수 있고, 친구를 초대하여 함께 여행 계획을 공유할 수도 있습니다.

<img width="600" alt="planregist1" src="https://github.com/lastname-dev/happytrail/assets/81070080/964718c6-5469-4703-beeb-2680e8287723">
<img width="600" alt="planregist2" src="https://github.com/lastname-dev/happytrail/assets/81070080/ddf0d16b-1620-402c-944f-977f20efe5b4">
<img width="600" alt="planregist3" src="https://github.com/lastname-dev/happytrail/assets/81070080/c7ef2183-197d-467d-b6e5-bda4f2d1f7fb">
<img width="600" alt="plandetail1" src="https://github.com/lastname-dev/happytrail/assets/81070080/d7b985de-7436-41e8-be97-4d521bad00ad">
<img width="600" alt="plandetail2" src="https://github.com/lastname-dev/happytrail/assets/81070080/319d1bee-a811-4ff3-b5e0-fce3c3790853">

<br>

### 여행지 검색

> 가보고 싶은 여행지를 검색하고, 해당 정보를 확인할 수 있습니다.

<img width="600" alt="search" src="https://github.com/lastname-dev/happytrail/assets/81070080/10af3e8d-82d5-4364-9d4f-d0487bbaabeb">
