## Vuex

1. ##### Vuex에 들어가기 전 Vue.js에 대하여

   Vue.js는 데이터 바인딩과 화면 단위를 컴포넌트 형태로 제공하며, 관련 API를 지원하는 데에 목적이 있다. 그리고 양방향 데이터 바인딩을 제공한다.

   Vuex는 컴포넌트를 관리하기 위한 것인데 이때, 컴포넌트란 화면의 영역을 일정한 단위로 쪼개어 재활용 가능한 형태로 관리하는 것이 컴포넌트이다. 한마디로, button이나 list등의 작은 요소들이 컴포넌트가 되고 이런 컴포넌트들이 모여서 화면을 구성하게 된다. 그리고 컴포넌트는 부모 자식관계가 있다.

   하위 컴포넌프에 데이터 전송하는 것을 props

   상위 컴포넌트에 데이터 전송하는 것을 custom event

   멀리 있는 컴포넌트에 데이터 전송하는 것을 mitt

   

2. ##### Vuex는 어떤 동작을 하는 가?

   Vue.js의 상태관리를 위한 패턴이자 라이브러리이다.

   상태관리는 여러 컴포넌트 간의 데이터 전달과 이벤트 통신을 한 곳에서 관리하는 패턴을 의미한다.

   일반적으로 컴포넌트 프레임워크에서는 화면 구성을 위해 작은 구성 요소로 단위를 쪼갠다. 한 화면에서 많은 컴포넌트를 사용하게 됨에따라 다른 컴포넌트들과의 통신이나 데이터 전달이 어렵게 된다. 이러한 점을 개선하기 위해 나온 것이 Vuex이며, 여러 컴포넌트들의 관계를 한 곳에서 관리하기 쉽게 만든 것이다.

   정리하자면, vuex는 데이터를 한곳에 모아 모든 컴포넌트들이 접근할 수 있게 해주는라이브러리를 말한다. 따라서 컴포넌트 간의 통시이나 데이터 전달을 좀더 유기적으로 해준다. 또한 중앙 집중식 저장소 이므로 props, custom event, mitt에 얽매이지 않아도 된다. 따라서 컴포넌프가 복잡한 공유 Vuex를 통해 별도의 저장소에서 데이터를 관리할 수 있다.

   

3. ##### 어떻게 사용하는 가?

   Vuex의 핵심 기능은 4개이다.

   state, getters, mutations, actions

   

   중앙에서 관리하는 모든 상태 정보 : state

   state를 변경하기 위한 methods : mutations

   `store.comit('setData',payload)`

   비동기 작업이 포함될 수 있는(외부 API와의 소통 등) methods, state를 변경하는 것 외의 모든 로직 :  actions

   `store.dispatch('setData',payload)`

   state를 활용해 계산한 새로운 변수 값 : getters

   

   component에서 데이터를 조작하기 위한 데이터 흐름도

   component -> (actions) -> mutations -> state

   component에서 데이터를 사용하기 위한 데이터 흐름도

   state -> (getters) -> component

   

4. ##### 앱에서 어떤 동작을 수행하는 가?

   앞에서도 이야기 했듯, 일반적으로 앱의 규모가 커지면 뷰의 컴포넌프 방식인 props, event, emit 때문에 중간에 컨포넌트가 많아지거나 이를 피하기 위해 Event Bus를 사용하면 컴포넌트 간 데이터 흐름을 파악하기 어려워 진다.

   지금 만드는 의류 앱의 경우 자신이 있는 날씨데이터, 사용자 옷장에 있는 데이터와 사용자의 직업에 따른 옷 정보를 가지고 순위를 돌리는 프로그램에 데이터를 보내줘야 한다. 그에 따른 데이터 분류와 통합 그리고 데이터를 보내줘야 할 때 컴포넌트가 많아지므로 데이터 흐름을 구현하는데 어려움이 있다. 이때 Vuex 오픈소스를 통해 데이터를 쉽게 보내 줄 수 있다.

   

5. ##### License

   MIT License

   ![image-20221115170005114](C:\Users\dptmd\AppData\Roaming\Typora\typora-user-images\image-20221115170005114.png)
   
     
   
   git: [vuejs/vuex: 🗃️ Centralized State Management for Vue.js. (github.com)](https://github.com/vuejs/vuex)