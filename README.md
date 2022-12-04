# OpenSource-Project# 1. Introduction
예쁜 옷들은 많습니다. 그러나 예쁘다고 해서 입었을 때 무조건 예쁘진 않습니다. 예쁜 옷이라고 해서 무작정 샀다가 옷장에 옷만 늘어나고 잘 입지 않는 옷들도 생겨납니다. 그렇게 되면 자주 안 입게 되고 처리하기가 곤란한 상황도 올 수 있어 우리는 이 시스템을 만들어 자기가 가지고 있는 옷을 날씨, 취향, 취미, 체형, 장소등 고려해서 나만의 옷장, 옷 추천 처리기능을 만들어 주려고 합니다.

## 1.1. Purpose of Document
이 프로젝트는 사용자에게 자신의 옷을 정리할 수 있는 옷장을 만들어주고 가진 옷을 조합해주고 자신의 옷을 SNS에 게시할 수 있게 하는 API의 역할을 수행합니다. 딥러닝을 통한 이미지 태깅과 UBCF를 활용해 사용자 맞춤 패션 서비스 제공 

## 1.2. Document Scope
이 프로젝트는 yolo v3 모델 , Deep lab V3+ 모델, Geolocation API 모델, OpenWeatherMap API 모델, Vuex 모델, Apache Solr 모델, Kakao open sdk 모델을 채택하고 있으면 인터페이스(UI)로는 Figma tool을 사용하려합니다.

## 1.3. Methodology, Tools, and Approach
데이터 모델 파트에서 날씨는 정형 데이터로 생각한다. 우리가 만들 시스템에서 날씨를 현 시점이 아닌 미래시점으로 예견해야 하기 때문이다.

## 1.4. Acronyms and Abbreviations
GUI		Graphical User Interface
SDD		System Design Document
E-R Diagram	Entity Relation Digram
SDD		Software Design Description
DFD		Data Flow Diagram
DBMS		Data Base Management System
SRS		Software Requirement Specification

## 1.5. Reference
([https://www.slideshare.net/peny_mg/sdd-software-des-sample](https://www.slideshare.net/peny_mg/sdd-software-des-sample))
김연희 / 데이터베이스 개론 : 기초 개념부터 빅 데이터까지 큰 흐름이 보이는 데이터베이스 교과서 / 한빛아케데미(주) / 2013

# 2. Design Overview

![image](https://user-images.githubusercontent.com/115726827/205491011-49f818e8-01ce-476f-a745-91612994c49a.png)
## 2-1. Background Information
Yolo v3 : 객체를 탐지함. 사진 뿐만 아니라 동영상에서 객체를 탐지하는 오픈소스, MIT 라이센스
Deep lab V3+ : 이미지를 인식하고, 이미지내에 있는 객체들을 의미있는 단위로 분류. apache 라이센스 2.0
Geolocation API : 위치 정보를 받아오는 API, MIT License
OpenWeatherMap API : 날씨정보를 제공하는 api, MIT License
Vuex 오픈소스 : 데이터를 한 곳에 모아 모든 컴포넌트들이 접근할 수 있게 해주는 라이브러리
데이터들을 종합적으로 관리하고 보내주기 위함. MIT License
Apache Solr : 요청을 통해 검색 기능을 제공. 검색 결과를 자동으로 조절하고 가중치를 줄 수 있음. 코디를 선별하기 위함. Apache License 2.0
KaKao Open SDK : 카카오 계정으로 로그인할 수 있도록 도움. 사용자의 데이터를 수집하기 위함.
Apache License 2.0, Eclipse Public License 1.0, Eclipse Public License 2.0, MIT License 
System Evolution Description
옷에 대한 프로그램 개발을 고민하던중에 옷을 추천받는 프로그램은 많았으나, 사용자의 개인 옷을 정리해서 추천해주는 프로그램은 없었음. 차별화를 두기 위해 사용자의 옷에 대한 데이터를 받아 프로그램을 개발하게 되었음.
## 2-2.Constraints
소프트 웨어는 오픈소스로 만들어야합니다. Yolo v3, DeepLab, Geolocation, OpenWeatherMap API, vuex는 MIT 라이선스를 사용합니다. Apache Solr는 Apache License 2.0을 사용합니다.
Kakao open sdk는 Apache License 2.0, Eclipse Public License 1.0, Eclipse Public License 2.0, MIT License를 사용합니다. 
## 2-3. Design Trade-offs
이 프로그램에서는 정보 데이터를 많이 이용합니다. 사용자의 정보 데이터, 날씨 데이터, 위치 데이터, 이미지 데이터가 있습니다. 이러한 데이터는 저장에 대한 부하가 증가하고, 데이터 베이스 관리자의 유지 관리가 늘어나게 됩니다. 또한, 어플리케이션을 실행하면서 추가적인 사용자의 요구 사항이 있을 경우 이에 대한 유지 관리비용을 생각해야합니다.

# 3. Logical Architecture

## 3.1. Project process(Software Design Description)

### 3.1.1 member
* Login
```
type : Kakao open sdk -> userAPI -> Login with Kakao Account
설명 : 로그인 기능을 수행한다.
기능 1. 로그인 화면 호출
기능 2. 로그인하기
흐름 : 로그인 창이 띄워진다 -> 카카오 앱으로 넘어간다 ->  카카오 계정 id , pwd 를 입력한다. -> 로그인 버튼을 클릭하여 계정 정보를 가져온다 -> 메인화면으로 넘어간다.
예외 흐름 : 회원가입 버튼을 누르면 회원가입 화면으로 넘어간다. 아이디/pw찾기 버튼을 누르면 카카오톡 앱으로 이동 후 수행한다.
```
* Join
```
type : Kakao open sdk -> userAPI -> add 	
설명 : app 처음 접속시 호출되는 화면으로 로그인 기능	 
기능 1 : 회원가입 화면 호출하기
기능 2 : 회원가입하기
기능 3 : 아이디 중복 확인하기
```
* Find
```
type : Kakao open sdk 
설명 : 특정 회원정보(email, name)을 통해 data base 에 저장된 id 또는 pwd를 찾는다
기능 1: 찾기 화면 호출
전제조건 : db에 회원정보가 저장되어있어야 한다.
흐름 : 로그인 화면에서 id/pw찾기 버튼을 클릭한다
기능 2 : 회원찾기
전제조건 : db에 회원정보가 저장되어있어야 한다.
흐름 :  찾으려는 계정의 email, name을 입력한다 -> 찾기 버튼을 클린한다. -> 이후 email 과 name 을 이용해 id, pwd 를 받아와 전송하고 텍스트로 집어 넣는다.	
예외흐름 : 공란 체크
```
* Edit
```
type : kakao open sdk
설명 : 현재 로그인하고 있는 계정에 대하여 정보를 수정한다.
기능 1: 내정보 수정 화면으로 이동하기
기능 2 : 내정보수정
기능 3 : 탈퇴
흐름 :  저장된 id 값을 이용해  db에서 계정 정보를 받아온 후 탈퇴를 실행한다.
이후 로그아웃을 리턴해 세션을 소멸시키고 로그인 화면으로 돌아감
```
* Main
```
type : Kakao open sdk + ui 
설명 : App 클릭시 처음 뜨는 화면으로 로그인, 회원가입,  myPage, Home , weather , closet 등 전체 시스템에 대한 내용 뒤에 추가로 기술한다.
기능 1 : 화면이동
흐름 : 원하는 frame 클릭한다.
```

### 3.1.2 Home
* communication (board)
```
type : example -> SNS
설명 : 게시글을 올려 서로의 패션을 공유하고 좋아요를 누르고 소통할 수 있는 공간
```
### 3.1.3 myPage
* closet
```
type : 
설명 : 내가 가진 옷들을 보여준다. 내가 좋아요한 옷을 보여준다. 추천해준 옷 조합 중 좋아요한 리스트를 보여준다. 내가 사진찍은 옷을 보여준다.
set_up
type : 
설명 : 전반적인 앱 설정도구를 모아둠.
```
* camera
```
type : Yolo v3 + DeepLabv3+
설명 : 촬영 기능.
```
### 3.1.4 Recommend clothes based on the weather
* weather
```
type : Geolocation API + OpenWeatherMap API
설명 : 사용자에 위치를 기반으로 현재 날씨를 알려준다.
기능 1 : 화면상에 오늘에 날씨 이모트를 보여준다.
기능 2 : 화면상에 온도를 알려준다.
```
* weather recommend
```
type : OpenWeatherMap API + Apache Solar 
설명 : 날씨에 맞춰 LTR 학습을 통해 옷을 추천해준다.
기능 1 : 화면 상에 날씨에 맞는 옷을 추천해줌
```
# 4. Physical Architecture

사용자의 스마트폰에서 회원가입을 통해 자신의 키,나이,몸무게, 취향을 입력합니다.
KaKao Open SDK에서 사용자 정보를 받아오고, 사용자 정보 데이터베이스에 저장합니다.
회원가입후 사용자가 올린 사진 정보를 Yolo v3 오픈소스가 확인하고, 옷을 탐지합니다. DeepLab v3+를 사용하여 옷의 이미지를 픽셀 수준으로 분류합니다. 이 과정에서 deep fashion2 dataset을 이용하여 옷 구분을 학습시킵니다. 이후, 분류한 이미지 내의 영역을 덩어리로 묶습니다. 그리고 옷장 데이터에 저장합니다. 사용자의 날씨에 따른 옷 분류를 위해 Geolocation에서 위치 데이터를 받아옵니다. 위치에 따른 날씨를 알기 위해 Weather API에서 데이터를 받아옵니다. 후에 데이터베이스에 저장하여 Vuex에 데이터를 전달합니다. 이 정보를 관리하여 Vuex는 Apache Solr에 전송하고 알고리즘을 이용하여 의류 데이터를 매칭 랭크에 따라 사용자에게 추천해줍니다. 후에 사용자는
스마트폰에서 데이터를 추천받습니다.

# 5. Data Model

## 5.1. Database Management System Files

### 5.1.1 요구사항 분석 명세서
```
1. 어플리케이션에 회원으로 가입하려면 카카오톡 아이디, 비밀번호, 사용자 정보를 입력해야 한다.  
2. 회원은 카카오톡 아이디로 식별한다.
3. 로그인이 완료된 후에 메인화면에는 홈, 날씨에 따른 옷 추천, mypage로 분류된다
4. 홈에서는 다른 사람들의 옷(게시글)을 볼 수 있고 게시글을 업로드 할 수 있다.
5. 날씨에 따른 옷 추천에서는 사용자의 위치를 받아서 날씨를 받고 날씨 정보와 사용자 정보를 조합해서 옷을 추천해주고 여러 리스트 중에서 마음에 드는게 있다면 저장 할 수 있다.
6. mypage에서는 카메라 기능을 이용해 옷장을 채울 수 있다.
7. 또한 저장했던 옷 조합도 볼 수 있다.
8. 회원은 회원정보를 수정하고 싶을 때 mypage에서 설정에 들어가 사용자 정보를 수정할 수 있으며 회원 탈퇴도 가능하다.
```
### 5.1.2. Conceptual Design
*****
DBMS 독립적인 개념적 구조 설계
*****
* 개체와 속성 추출
<img width="376" alt="image" src="https://user-images.githubusercontent.com/63581424/205449338-d87b49c0-91df-4407-a1b7-45a69db9ce9a.png">
*****
![image](https://user-images.githubusercontent.com/63581424/205449366-d393a5cf-bc86-44b8-aea0-0e2ca090b2ce.png)

### 5.1.3. E-R Diagram
![image](https://user-images.githubusercontent.com/63581424/205449379-23430c9d-b20c-41af-b950-cef1de0dff5d.png)

### 5.1.4. Logical Design
* Relation Schema
![image](https://user-images.githubusercontent.com/63581424/205449433-17cd2ab6-3e6a-4c72-b335-1d9d1c589071.png)

# 6. Detailed Design 
![image](https://user-images.githubusercontent.com/115726827/205491029-07dcc069-e083-4287-94ae-032e2319c264.png)
사용자의 애플리케이션 계층에서 보여주는 GUI입니다.
사용자는 어플을 실행하게 되면 왼쪽에 있는 화면을 볼 수 있습니다. 사용자는 어플리케이션의 기능을 살펴 볼 수 있는데, 맨 위의 부분에서 현재 시간과 데이터의 상태를 확인할 수 있습니다. GeoLocation API와 OpenWeatherMap API를 통한 현재 위치의 정보 값을 받아오고, 그 위치의 날씨를 확인하는 현재 날씨와 오늘의 기온을 확인할 수 있습니다.
이런 날씨를 참고해서 어플에서 일교차가 심한 날씨엔 겉옷 필수라는 문장을 보여줍니다. 이와 관련된 겉옷을 추천하는 목록도 있습니다. 이 목록은 vuex의 정보 관리를 통해 아파치 솔라에게 전달된 값을 바탕으로 LTR 알고리즘을 이용하여 관련도 높은 의류를 추천해줍니다. 마찬가지로 아래의 가을엔 역시 레이어드 룩이라는 문장의 목록도 같습니다. 하단의 맨 왼쪽의 집 모양의 버튼은 홈화면입니다. 사용자의 패션 코디를 게시하는 공간이며, SNS 기능으로 사용을 희망중에 있습니다. 하단의 중간에 위치한 구름모양의 버튼은 현재 날씨에 어울리는 코디를 추천해주며,  <보기>에 있는 화면을 보여줍니다. 하단의 오른쪽에 있는 카메라 버튼은 사용자의 사진과 영상에 나온 옷을 인식하여 해당 옷과 어울리는 코디를 추천해줍니다.
# 7. External Interface Design

## 7.1. Interface Architecture

* 하드웨어 인터페이스
IOS 앱 클라이언트
운영체제: iOS13
개발언어: swift
안드로이드 앱 클라이언트
운영체제: android13
개발언어: c, c++ , java, Rust

* 소프트웨어 인터페이스
API
```
OpenWeatherMAP API
Geolocation API
Apache sold (search n index API)
```	
라이브러리
```
vuex 
```
알고리즘
```
deeplab
LTR
```
kit
```
kakao open SDK
```	
dataset
```
DeepFashion2
```
# 8. Graphical User Interface (GUI)
 사용자가 제품과 편안하게 상호 작용할 수 있도록 하였습니다.
인터페이스와 관련이 없거나 거의 필요하지 않은 정보가 포함되어서는 사용자에게 혼란을 줄 수 있습니다. UI에 표시되는 모든 정보가 가치 있고, 관련성이 있는 방식으로 UI를 디자인하기 위해 노력했습니다.  인터페이스를 만들면서 모든 사용자들이 느끼는 화면을 구성하려고 노력했습니다.
홈 화면은 대부분의 어플에서와 같이 집과 같은 그림으로 표현해서 사용자의 패션 코디를 게시하는 공간으로 사용하려고 노력했습니다. 구름과 눈과 같은 그림은 날씨를 연상하게 도와주고, 카메라는 옷을 인식하기 위함을 도와줍니다. 또한 # 이라는 태그를 사용해 사용자들이 흔히 이용하는 인스타그램에서의 해시태그의 느낌을 주었습니다. 이러한 #의 표현은 사용자가 좀 더 어플을 사용함에 있어서, 편안한 느낌을 줄 것이라고 생각합니다. 
UI 구성함에 있어서, Input과 output도 고려해야했습니다. 사용자가 원하는 목록을 추천 받으려면 사용자의 데이터를 받아와야 했고, 이는 사용자의 스마트폰에서 회원가입을 통해 자신의 키,나이,몸무게, 취향을 입력합니다. 회원가입후 사용자가 올린 사진 정보, 사용자의 위치, 날씨를 확인합니다. 이렇게 입력받은 데이터를 바탕으로 UI에 결과 값을 보내줍니다.
DeepLab v3+를 사용하여 옷의 이미지를 픽셀 수준으로 분류합니다. 이 과정에서 deep fashion2 dataset을 이용하여 옷 구분을 학습시키고, 분류한 이미지 내의 영역을 덩어리로 묶습니다. 그러고 나서 이 데이터를 옷장 데이터에 저장합니다. 이 옷장 데이터는 UI의 집버튼에 구성되게 됩니다.  사용자의 날씨에 따른 옷 분류를 위해 Geolocation에서 위치 데이터를 받아옵니다. 위치에 따른 날씨를 알기 위해 Weather API에서 데이터를 받아옵니다. 후에 데이터베이스에 저장하여 Vuex에 데이터를 전달합니다. 이 정보를 관리하여 Vuex는 Apache Solr에 전송하고 알고리즘을 이용하여 의류 데이터를 매칭 랭크에 따라 사용자에게 추천해줍니다. 후에 사용자는 날씨와 관련된 옷의 정보를 구름모양의 버튼을 클릭해 UI에서 확인할 수 있습니다.

# 9. System Integrity Controls

- ## 데이터 무결성
### 1. 개체 무결성
한 엔터티에서 같은 기본키 를 가질 수 없다
기본 키의 속성이 NULL을 허용할 수 없다. 
릴레이션에 포함되어 있는 투플들을 유일하게 구별해주고 각 투플에 쉽게 접근 할 수 있도록 릴레이션마다 기본키를 지정하였다.
기본키를 구성하는 속성 전체나 일부가 널 값이 되면 투플의 유일성을 판단할 수 없기에 우리는 개체 무결성에 의미를 잘 생각하면서 시스템을 구축하겠다.

### 2. 참조 무결성
외래 키가 참조하는 다른 개체의 기본 키에 해당하는 값
기본 키 값이거나, NULL 이어야 한다.
외래키는 다른 릴레이션의 기본키를 참조하는 속성이고 릴레이션 간의 관계를 표현하는 역할을 한다. 
그런데 외래키가 자신이 참조하는 릴레이션의 기본키와 상관이 없는 값을 가지게 되면 두 릴레이션을 연관시킬 수 없으므 외래키 본래의 의미가 없어진다. 
그러므로 외래키는 자신이 참조하는 릴레이션에 기본키 값으로 존재하는 값으로 설정하고 구축하겠다.

### 3. 속성 무결성
속성의 값은 기본값, NULL 여부, 도메인이 지정된 규칙을 준수하겠다.

### 4. 사용자 무결성
사용자의 의미적 요구사항을 준수하겠다.

### 5. 키 무결성
한 릴레이션에 같은 키 값을 가진 튜블들을 허용할 수 없는 제약 조건을 준수 하겠다

