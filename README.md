## :envelope: 안드로이드를 이용한 RSA키 암복화 TCP 형식의 채팅 프로그램

### 👋 Introduction

<table>
    <tr>
        <th>프로젝트명</th>
        <th>채팅 프로그램</th>
        <th>개발 기간</th>
        <th>2018.09.25. ~ 2018.12.02.</th>
    </tr>
    <tr>
        <th>프로젝트 성격</th>
        <th>캡스톤 디자인 프로젝트</th>
        <th>개발 인원</th>
        <th>팀 / 3명<br>
          (<a href="">한시연</a>,
          김태중</a>,
          박경신</a>)
      </th>
    </tr>
      <tr>
        <th>프로젝트 개요</th>
        <th>안드로이드를 이용한 RSA키 암복화 TCP 형식의 채팅 프로그램</th>
        <th>개발 환경</th>
        <th>Windows10</th>
    </tr>
    <tr>
        <th colspan="4">사용한 Skill 또는 지식</th>
    </tr>  
    <tr>
        <th>개발언어</th>
        <th colspan="3">Java</th>
    </tr>
    <tr>
        <th>형상관리</th>
        <th colspan="3">Github, Git Bash, Source Tree</th>
    </tr>
    <tr>
        <th>개발도구</th>
        <th colspan="3">Android Studio</th>
    </tr>
    <tr>
        <th>사용기술</th>
        <th colspan="3">P2P, RSA 알고리즘, 쓰레드</th>
    </tr>
</table>

## :clipboard: 화면 설계도

<img src="https://user-images.githubusercontent.com/66079830/90410615-8fa04780-e0e5-11ea-984d-07f74d74c025.png"  width="650" height="1000">

## :ok_woman: 시연 화면 

### 클라이언트
1. APP을 실행시켰을시 화면.                           2. 서버의 Peer IP 입력 후 RSA 버튼클릭(=서버에게 클라이언트 공개키 전송).

<img style="margin-right:300px;" src="https://user-images.githubusercontent.com/66079830/85937685-ff455200-b940-11ea-95a0-63c0c341e2fd.png"  width="300" height="300">         <img src="https://user-images.githubusercontent.com/66079830/85937690-053b3300-b941-11ea-8357-71706e8bac66.png"  width="300" height="300">

3. 서버에서 보내온 서버 RSA 공개키 수신.

<img src="https://user-images.githubusercontent.com/66079830/85937702-1edc7a80-b941-11ea-83fc-5a4e1f1af4f5.png"  width="300" height="300">

4. 위 과정 이후 자유로운 채팅 교환.

<img src="https://user-images.githubusercontent.com/66079830/85937707-29970f80-b941-11ea-88a4-50b32ea1ea83.png"  width="300" height="300">

### 서버
1. 클라이언트에게서 공개키 수신 시 자동으로 IP가 입력됨.
<img src="https://user-images.githubusercontent.com/66079830/85937710-2d2a9680-b941-11ea-9a21-4460e673e8e7.png"  width="300" height="300">

2. 클라이언트가 서버의 공개키를 수신 한 이후 자유로운 채팅 교환.
<img src="https://user-images.githubusercontent.com/66079830/85937712-30be1d80-b941-11ea-9c2f-66afb2087933.png"  width="300" height="300">
