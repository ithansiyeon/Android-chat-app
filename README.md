## :envelope: 안드로이드를 이용한 RSA키 암복화 TCP 형식의 채팅 프로그램
![메인](https://user-images.githubusercontent.com/66079830/91676554-2e638400-eb7b-11ea-99b9-36b133031a54.png)
### 👋 Introduction

<table>
      <tr>
          <th style="width:15%;">프로젝트명</th>
          <th style="width:35%;">채팅 프로그램</th>
          <th style="width:20%;">개발 기간</th>
          <th style="width:25%">2018.09.25. ~ 2018.12.02.</th>
      </tr>
      <tr>
          <th>프로젝트 성격</th>
          <th>캡스톤 디자인 프로젝트</th>
          <th>개발 인원</th>
          <th>팀 / 3명<br>
            (<a href="https://github.com/ithansiyeon">한시연</a>,
            김태중</a>,
            박경신</a>)
        </th>
      </tr>
        <tr>
          <th>프로젝트 개요</th>
          <th colspan="3">RSA알고리즘을 사용한 암호화된 통신 프로그램 구성</th>
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
          <th colspan="3">P2P, RSA 알고리즘, 쓰레드, TCP 소켓 통신</th>
      </tr>
  </table>

### :clipboard: 

<img src="https://user-images.githubusercontent.com/66079830/90410615-8fa04780-e0e5-11ea-984d-07f74d74c025.png"  width="650" height="1000">

### :ok_woman: 시연 화면 

### ♣ 클라이언트
1. APP을 실행시켰을시 화면.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2. 서버의 Peer IP 입력 후 RSA 버튼클릭.

<img style="margin-right:300px;" src="https://user-images.githubusercontent.com/66079830/85937685-ff455200-b940-11ea-95a0-63c0c341e2fd.png"  width="300" height="300">&nbsp;&nbsp;&nbsp;&nbsp;<img src="https://user-images.githubusercontent.com/66079830/85937690-053b3300-b941-11ea-8357-71706e8bac66.png"  width="300" height="300">

3. 서버에서 보내온 서버 RSA 공개키 수신.&nbsp;&nbsp;&nbsp;&nbsp;4. 위 과정 이후 자유로운 채팅 교환.

<img src="https://user-images.githubusercontent.com/66079830/85937702-1edc7a80-b941-11ea-83fc-5a4e1f1af4f5.png"  width="300" height="300">&nbsp;&nbsp;&nbsp;&nbsp;<img src="https://user-images.githubusercontent.com/66079830/85937707-29970f80-b941-11ea-88a4-50b32ea1ea83.png"  width="300" height="300">

### ♧ 서버

1. 클라이언트에게서 공개키 수신 시 자동으로 IP가 입력됨.

<img src="https://user-images.githubusercontent.com/66079830/85937710-2d2a9680-b941-11ea-9a21-4460e673e8e7.png"  width="300" height="300">

2. 클라이언트가 서버의 공개키를 수신 한 이후 자유로운 채팅 교환.

<img src="https://user-images.githubusercontent.com/66079830/85937712-30be1d80-b941-11ea-9c2f-66afb2087933.png"  width="300" height="300">


### :key: Code 
자바로 구현한 채팅 코드입니다.

<table>
    <tr>
        <th colspan="4">구현 코드</th>
    </tr>
    <tr>
        <th colspan="4">
            <img style="margin-left:-50px;" src = "https://user-images.githubusercontent.com/66079830/90662703-a8466400-e283-11ea-891d-b3509404ec0b.gif" width="600" height="400">
        </th>
    </tr>
</table>

### 📑 Role & Member
<table>
    <tr>
        <th>팀원</th>
        <th>역할</th>
        <th>공동</th>
    </tr>
    <tr>
        <th>한시연</th>
        <th>클라이언트 코딩1, 동영상 취합</th>
        <th rowspan="3">보고서 작성, TCP & RSA 학습, 설계도면 작성, <br>버그 수정</th>
    </tr>
    <tr>
        <th>박경신</th>
        <th>서버 코딩, 발표</th>
    </tr>
    <tr>
        <th>김태중</th>
        <th>클라이언트 코딩2</th>
    </tr>
</table>

### :mega: 프로젝트 소감

<table style="width:500px;">
    <tr>
        <th>한시연</th>
    </tr>
    <tr>
        <td>
            RSA 암호화 통신에 있어서 JAVA 언어 기반이므로 암호화된 메시지를 보내기 전 교환이 되어야 하는데 KEY를 패킷 형태로 송수신 할시 인식이 불가능 하여 메시지를
            암호화 하지 못해 통신이 완료되지 못지만 KEY를 패킷 형태로 보내는 것이 아니라 버퍼리더로 읽도록 송수신 과정을 새롭게 작성하여 이를 해결할 수 있었습니다.
            네크워크와 P2P채팅에 대해서 잘 이해할 수 있었으며 쓰레드와 RSA 알고리즘에 대해서 더 자세히 이해할 수 있었습니다. 추후에 보안성이 강화되도록 KEY 송수신 
            과정에 다른 암호 알고리즘을 사용하여 제 3자가 파악하기 힘들도록 이중 암호화 과정을 도입하는 방식으로 개선을 하고 싶습니다.
        </td>
    </tr>
</table>
