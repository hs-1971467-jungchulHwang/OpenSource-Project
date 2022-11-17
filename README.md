# OpenSource-Project
# IP Geolocation API

geolocation API 는 지오코딩 요청을 위한 무료 서비스입니다.
지리적 위치 지정 국가에 대한 추가 정보가 제공되는 CloudFlare IP Geolocation을 통해 요청됨.


## 왜 Geolocation API를 선택했나?
프로젝트를 구성하면서 패션 추천 사이트에 날씨에 따른 기능을 추가하게 되었고, 이 기능에 필요한 구조로 user의 위치에 따른 날씨를 나타내줘야했다. 따라서 날씨API와 위치API로 유저의 현재 위치에 따른 기온을 요청받아 이름 참고하여 옷을 추천해주기로 결정했다.

## 간략한 MIT 라이센스 한국어 요약
1. 이 소프트웨어를 누구라도 무상으로 제한없이 취급이 가능하나, 저작권 표시 및 허가 표시를 소프트 웨어의 복제물 또는 중요한 부분에 기재해야한다.
2. 저자 또는 저작권자는 소프트웨어에 관해 아무런 책임이 없다.
3. 이 라이센스는 GPL등과는 달리 카피 레프트는 아니고, 오픈 소스 여부에 따라 관계없이 재사용을 인정함.
4. 다른 라이선스에 비해 제한이 느슨하다.
## Stack

IP Geolocation API는 Starlette를 기반으로 구축되어 처리량도 높고, 초당 수천 건의 요청을 비동기식으로 처리할 수 있음. 사용자의 위치에 따른 요청을 빠르게 처리 가능함.

#### Libraries used
사용 라이브러리
* [스타렛](https://www.starlette.io/)
비동기 웹 서비스를 구축하는데 이상적인 프레임워크/툴킷 라이브러리
* [유비콘](https://www.uvicorn.org/)
Python용 웹서버 구현용 라이브러리
* [uvloop](https://github.com/MagicStack/uvloop)
내장된 asyncio 이벤트 루프를 빠르게 대체하는 라이브러리
* [울트라JSON](https://github.com/esnme/ultrajson)


## Development
#### Install packages
```shell 
pipenv install
```

#### Running the server
```shell
gunicorn app:app -k uvicorn.workers.UvicornWorker --reload
```

## Contributing
Thanks for your interest in the project! All pull requests are welcome from developers of all skill levels. To get started, simply fork the master branch on GitHub to your personal account and then clone the fork into your development environment.

Madis Väin (madisvain on Github, Twitter) is the original creator of the IP Geolocation API framework.

## License
MIT