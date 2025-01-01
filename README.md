## 컴퓨터 환경에 따른 에러

- 환경 차이: 각 컴퓨터의 네트워크 설정이나 방화벽 규칙이 다를 수 있음, 특정 컴퓨터에서 API 서버에 대한 접근이 차단될 수 있움

- 인증 정보 차이: 각 사용자가 사용하는 인증 정보(예: API 키, 토큰)가 다를 수 있음 -> 일부 사용자는 인증되지 않거나 권한이 없는 요청을 보내게 됨

- 서버 설정 차이: API 서버의 설정이나 배포 환경에서 특정 사용자 또는 IP 주소에 대해 다른 권한 설정이 있을 수 있음

- 브라우저 캐시 및 쿠키: 브라우저의 캐시나 쿠키가 다르게 설정되어 있어, 로그인이 필요한 요청에서 차이가 발생할 수 있음

- API 서버 상태: API 서버가 특정 사용자에게만 제한된 기능을 제공하거나, 특정 조건에서만 요청을 허용하는 경우도 있을 수 있음

```
- 동일한 코드에서도 서로 다른 컴퓨터에서 HTTP 401 또는 403 오류가 발생하는 이유:

-> 주로 환경 설정, 인증 정보, 서버 설정 등의 차이에 기인
-> 각 환경의 설정을 점검하고, 사용하는 인증 정보가 올바른지 확인하는 것이 중요
```

> 전체 Security 관련 코드 수정 필수

> 전체 ReactContext -> Recoil 수정 필수

<br />

## 전체 서버 실행 순서

1. database 실행: MongoDB

2. backend server 실행: SpringBoot

3. frontend server 실행: react

<br />

## 코드 작성 순서

1. server: backend

```
- 관련 패키지 생성 -> domain, controller, service, repository 디렉토리 생성

- domain: collection과 맵핑될 클래스 생성

- repository: MongoRepository<domain객체, String>를 extends할 interface 생성(sql문 대신 메서드 호출)

  - 작성한 메서드 명에 따라 자동으로 내부 내용이 채워짐
    -> 해당 메서드를 해석하지 못하는 경우 직접 어노테이션 등으로 구체적인 내용 명시해줘야 함

- service: 비즈니스 로직 작성 및 repository 호출 -> 데이터 가공 처리 등

- controller: api 작성 및 service 호출 -> RESTful API를 직접적으로 사용
```

2. client: frontend

```
- src/pages 하위 경로에 관련 디렉토리, jsx 파일 생성

- App.js or index.js에 Route 등록: lazy loading 주의

- 페이지 구조 고려해 코드 작성

  - 분리, 반복되는 코드는 src/components 하위 경로로 관련 디렉토리 생성 후 컴포넌트 생성

* 데이터 불러오는 함수는 async-await 문법 사용(try-catch 구문 필수)
* 해당 컴포넌트가 생성될 때 데이터가 필요한 경우 useEffect 사용해 데이터 불러오는 함수 호출
```

#### 코드 순서는 Read -> Create -> Update -> Delete 순서로 작성하는 게 일반적

## 데이터 확인

- mongodb-compass 활용: 해당 앱에서 도큐먼트, 컬렉션 삭제할 경우 commit할 필요 없음

<br />

## 고차함수(Higher-Order Function)

> `함수를 인자로 받거나 함수를 반환`하는 함수 <br />
> 다른 함수를 매개변수로 받아서 그 함수를 호출하거나, 새로운 함수를 생성하여 반환하는 기능을 가짐

- 특징

  1. 함수를 인자로 받음: 고차 함수는 다른 함수를 인자로 받아서 사용할 수 있음

  ```js
  const numbers = [1, 2, 3, 4];
  const squares = numbers.map((num) => num * num); // map은 고차 함수
  console.log(squares); // [1, 4, 9, 16]
  ```

  2. 함수를 반환함: 고차 함수는 새로운 함수를 생성하여 반환할 수 있음 ->클로저(closure)와 함께 사용되어 상태를 유지하는 데 유용

  ```js
  function makeMultiplier(mul) {
    return function (x) {
      return x * mul;
    };
  }

  const double = makeMultiplier(2);
  console.log(double(5)); // 10
  ```

- 장점

  - 코드 재사용성: 반복되는 코드가 줄고, 다양한 상황에서 재사용 가능

  - 모듈성: 코드를 더 작은 단위로 나누어 관리할 수 있어 유지보수 용이

  - 함수형 프로그래밍 지원: 함수형 프로그래밍 패러다임을 지원하며, 부수효과(Side Effects)를 줄이고 불변성을 유지하는 데 도움

  > 고차함수는 일반적으로 JS와 같은 언어에서 많이 사용됨

- 코드 분석

  ```js
  const handleChange = (field) => (e) => {
    setUpdateVal({ ...updateVal, [field]: e.target.value });
  };

  const handleChange = function (field) {
    return function (e) {
      setUpdateVal({
        ...updateVal,
        [field]: e.target.value
      });
    };
  };
  ```

  - handleChange: 고차함수 -> field 매개변수를 받고 `다른 함수를 반환`함

  - 반환되는 함수: 이벤트 객체 e를 인자로 받아서 setUpdateVal 함수 호춣 -> (e) => { setUpdateVal({ ...updateVal, [field]: e.target.value }); }
