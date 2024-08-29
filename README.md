# Shaken
[과제 링크](https://www.notion.so/actionpower/AOS-Type-A-2fd0f57d32a149cebb770fb002501477)

## Build
`locol.properties` 파일에 다음을 추가해주세요.

 ```
 BACKEND_URL="https://www.thecocktaildb.com/api/json/v1/1/"
 ```


## TODO

### Common
- [ ] Update navigation graph
- [ ] Build guide
- [ ] App Bar
- [ ] 키보드 팝업 시 Status Bar 영역으로 컨텐츠가 보이는 현상
- [ ] REST API 에러 핸들링
- [ ] 다크모드 호환성
- [ ] Material Theme Builder
- [ ] core:data module test code
- [ ] core:database module test code

### Search
- [x] 알콜 성분 목록으로 기본화면 변경
- [x] 검색 시 시작단어 검색으로 API 변경
- [x] 카테고리
- [x] 썸네일
- [x] 즐겨찾기
- [x] 검색어 클리어 버튼
- [x] 즐겨 찾기 목록 조회 화면 추가
- [ ] 검색 결과 텍스트 하이라이트
- [ ] 첫 진입 시 로딩 인디케이터 추가

### Detail
- [x] 필수 정보
  - [x] 제품 세부 이미지
  - [x] 제품 이름, 카테고리
  - [x] 제품 상세 내용 최대한 표현
  - [x] 모든 외국어 명세 표현
- [x] 즐겨찾기 기능
- [ ] 바텀시트

### Video
- [x] 코드 정리
- [x] loading state
- [x] 텍스트 스타일링
- [ ] 텍스트 자동 스크롤



## Architecture

안드로이드 공식 문서에서 가이드하는 MVVM + Repository Pattern의 아키텍쳐를 최대한 따르고자 하였습니다.

### Layers

기본적으로 다음과 같은 세 가지 레이어를 가집니다.

![architecture-1-overall.png](docs%2Fimages%2Farchitecture-1-overall.png)


#### Data Layer

앱에서 사용되는 모든 데이터들을 온/오프라인 데이터 소스를 통해 가져오고, 접근하고, 저장할 수 있도록 기능들을 제공합니다.

현재 구현에서는 네트워크 데이터는 `Retrofit` + `OkHttp`를 통해 fetch 해오고 있으며, persistence를 위해 `Room`을, 스크립트 파일을 가져오기 위해 직접 구현한 `AssetDataSource`를 사용하고 있습니다.
위 데이터 소스들은 Repository를 통해 캡슐화 되어 도메인 레이어에 제공되며 data layer 외의 다른 계층에서는 해당 데이터가 캐싱되고 있는지 네트워크에서 가져왔는지 로컬에서 가져왔는지 등에 대해 신경 쓸 필요가 없도록 하는데에 의미가 있습니다.

필요한 경우 data 들은 구독 혹은 관찰 가능한 형태로(Flow) 제공되어 변경 사항 발생 시 손쉽게 업데이트 될 수 있도록 합니다.

현재 구현 상 사용하고 있지는 않지만 `SharedPreferences` 혹은 `DataStore`가 필요한 경우 마찬가지로 Repository 에서 해당 데이터들을 접근하는 책임을 갖도록 할 것입니다.


#### Domain Layer

현재 구현 상으로는 아직 UseCase를 채택하지는 않았습니다. 비즈니스 로직이 ViewModel에 존재하지만 ViewModel을 UI Layer로 본다면 현재는 앱에서 가장 추상화된 객체인 모델들만이 Domain Layer에 해당될 것 입니다.

추후 앱 스케일이 커지면서 ViewModel 구현부를 더 간단하게 만들고, 여러 ViewModel 간의 중복 로직을 제거하고자 하는 니즈가 늘어난다면 언제든지 UseCase 객체를 도입할 수 있겠습니다.


#### UI Layer

Compose UI와 ViewModel로 구성된 UI Layer입니다. ViewModel은 Repository와 직접적으로 통신하는 역할을 담당하고 있으며 비즈니스 로직을 다루고 있습니다. Compose 코드에서는 오직 각 State를 적절하게 렌더링 하는데에 집중합니다.

뷰모델은 State를 Flow를 통해 Observable한 스트림으로 제공하여 State가 변화하여도 유연하게 UI가 업데이트 되도록 합니다.



## Modularization

NowInAndroid 오픈 소스 프로젝트에서 적용하고 가이드하고 있는 프로젝트 Modularization을 적용하였습니다.

build-logic 모듈의 경우 NowInAndroid의 소스를 거의 그대로 가져왔으며 현 프로젝트에서 사용하지 않는 부분만 제거하였습니다.

* `app` module - 앱 레벨의 Scaffold와 Navigation이 구현되어 있는 앱의 최상단 시작점 역할입니다. 구현된 feature 모듈들을 앱에 포함시켜 navigation 구성에 따라 보여주게 됩니다.
* `feature:` modules - 각 기능에 해당하는 부분들입니다. 일반적으로 각 화면 기준으로 feature 모듈들이 구분됩니다. 한 화면에 포함된 기능들이 더 복잡해진다면 더 세부 기능 단위로 구분하여 feature 모듈을 만드는 것도 가능할 것입니다. core 모듈들에서 구현된 여러 공용 컴포넌트 중에 필요한 것들을 참조하여 구체적인 기능들을 구현하게 됩니다.
* `core:` modules - 프로젝트 내에 공용 라이브러리 역할을 하는 곳입니다. 디자인 컴포넌트 카탈로그앱 등과 같이 사이드킥 앱을 빌드할 때도 core 모듈들을 재사용할 수 있어 손쉽게 구현이 가능합니다.
* `build-logic` module - 새로운 기능 구현을 위해 새로운 모듈을 생성할 때 마다 디펜던시를 적절하게 구현하고 업데이트해주어야 한다면 모듈화를 진행한 의미가 다소 줄어들 수 있습니다. 이를 해결하기 위해 구현된 모듈이며 편하게 디펜던시를 포함할 수 있도록 하는 역할을 하고 있습니다.


### Modularization의 장점

- 관심사 분리를 단순히 개념적으로 잘해보자가 아닌, 코드 레벨에서 강하게 권장할 수 있습니다.
- 각 컴포넌트들 간의 커플링이 모듈 레벨에서 끊어지기 때문에, 추후 유지보수 시 변경 사항에 더 유연해집니다. 
- 빌드 속도가 크게 올라갑니다.
- 같은 프로젝트에서 협업을 하는 경우 흔히 발생할 수 있는 코드 컨플릭트를 줄여줍니다.
- (추후 필요해진다면) 구글 플레이에서 제공하는 Dynamic Delivery를 통해 앱 배포가 가능해집니다.
- (추후 필요해진다면) 여러 앱을 릴리즈하고 관리하는 팀과 조직에서 모듈 단위의 재사용이 가능해집니다.


### 각 모듈 설명

<table>
  <tr>
   <td><strong>이름</strong></td>
   <td><strong>역할 및 책임</strong></td>
   <td><strong>Example</strong></td>
  </tr>
  <tr>
   <td><code>app</code></td>
   <td>앱에 필요한 모든 것을 포함시켜 구현합니다.</td>
   <td></td>
  </tr>
  <tr>
   <td><code>feature:search</code></td>
   <td>칵테일 목록을 보여줍니다. 검색이 가능합니다.</td>
   <td></td>
  </tr>
  <tr>
   <td><code>feature:detail</code></td>
   <td>선택한 칵테일의 상세 정보를 보여줍니다.</td>
   <td></td>
  </tr>
  <tr>
   <td><code>feature:favorite</code></td>
   <td>즐겨찾기로 저장한 칵테일 목록을 보여줍니다.</td>
   <td></td>
  </tr>
  <tr>
   <td><code>feature:video</code></td>
   <td>유튜브 영상을 스트리밍하고 재생 시간에 따라 스크립트를 하이라이팅 합니다.</td>
   <td></td>
  </tr>
  <tr>
   <td><code>core:data</code></td>
   <td>데이터를 fetch하고 접근할 수 있게 제공하며, 필요한 데이터를 저장합니다.</td>
   <td><code>CocktailRepository</code><br>
   </td>
  </tr>
  <tr>
   <td><code>core:designsystem</code></td>
   <td>공용 UI 컴포넌트를 구현하고 제공합니다. 팀에서 디자인 시스템을 빌드업하고 있다면 그에 대한 구현체가 이 모듈에 들어갑니다. ViewBased 기반으로 가정하면 CustomView에 해당하는 컴포넌트들이 구현되는 곳입니다.</td>
   <td></td>
  </tr>
  <tr>
   <td><code>core:ui</code>
   </td>
   <td>앱을 개발하다보면 공통적으로 사용되는 Composite Composable들이 생기게 되는데 이러한 Composable들을 제공합니다. 해당 Composable은 designsystem 모듈 혹은 CustomView와는 달리 특정 모델에 dependent 할 수 있습니다.</td>
   <td></td>
  </tr>
  <tr>
   <td><code>core:common</code></td>
   <td>다른 모듈들에서 사용되는 공용 함수 혹은 클래스들</td>
   <td></td>
  </tr>
  <tr>
   <td><code>core:network</code>
   </td>
   <td>네트워크를 통해 데이터를 가져오는 역할을 합니다.</td>
   <td></td>
  </tr>
  <tr>
   <td><code>core:database</code></td>
   <td>로컬 데이터베이스를 통해 Persistence를 제공합니다.</td>
   <td></td>
  </tr>
  <tr>
   <td><code>core:model</code></td>
   <td>앱의 기반이 되는 도메인 모델입니다.</td>
   <td></td>
  </tr>
</table>
