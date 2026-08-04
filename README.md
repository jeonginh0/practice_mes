# MES Practice

MyBatis 학습을 목적으로 만든 간단한 MES(제조실행시스템) 연습 프로젝트입니다.
품목(Item) · 설비(Equipment) · 작업지시(WorkOrder) · 생산실적(ProductionResult) 네 개 도메인의 CRUD/조회 API와,
이를 브라우저에서 바로 테스트해볼 수 있는 대시보드 페이지로 구성되어 있습니다.

## 기술 스택

| 영역 | 사용 기술 |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 4.1.0 (spring-boot-starter-web, validation) |
| Persistence | MyBatis (mybatis-spring-boot-starter 4.1.0) |
| DB | MySQL |
| 기타 | Lombok, Gradle |
| 화면 | 정적 HTML/JS 1장 (`src/main/resources/static/index.html`), 별도 프론트엔드 빌드 없음 |

> Spring Boot 4.x를 쓸 때는 `mybatis-spring-boot-starter`도 **같은 메이저 버전(4.x)** 으로 맞춰야 합니다.
> 3.x 버전을 그대로 쓰면 `MybatisAutoConfiguration`이 `DataSourceAutoConfiguration`보다 먼저 평가되어
> `@Mapper` 인터페이스들이 빈으로 전혀 등록되지 않는 문제가 있었습니다 (트러블슈팅 로그 참고).

## 프로젝트 구조

도메인별 패키지 안에 계층을 두는 구조입니다 (`entity` / `dto` / `repository` / `service` / `controller`).

```
com.inho.mespractice
├── item              품목
├── equipment         설비
├── workorder         작업지시
└── productionresult  생산실적
```

각 도메인은 `Controller → Service → Repository(interface) → RepositoryImpl → Mapper(MyBatis) → XML` 흐름을 따릅니다.
DTO는 **컨트롤러 경계에서만** 사용합니다 — Service/Repository/Mapper는 계속 엔티티를 주고받습니다.
도메인이 이 정도 규모일 때 계층마다 DTO를 따로 두는 건 과설계라고 판단했습니다.

## 실행 방법

```bash
# MySQL에 mes_system 데이터베이스와 아래 스키마의 테이블이 이미 있어야 합니다.
./gradlew bootRun
```

기본 접속 정보는 `application.properties`에 있습니다 (`localhost:3306/mes_system`, `root/root`).
뜬 뒤 브라우저에서 `http://localhost:8080` 을 열면 대시보드가 보입니다.

### 데이터베이스 스키마 (참고용)

저장소에 `schema.sql`을 따로 두지 않았습니다 (`spring.sql.init.mode=always`이지만 실행할 스크립트가 없어 현재는 아무 효과가 없습니다).
아래는 각 XML 매퍼가 참조하는 컬럼을 기준으로 재구성한 DDL입니다 — 로컬에 새로 세팅할 때 참고하세요.

```sql
CREATE TABLE item (
    item_id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    item_code  VARCHAR(50)  NOT NULL,
    item_name  VARCHAR(100) NOT NULL,
    unit       VARCHAR(20)
);

CREATE TABLE equipment (
    equipment_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    equipment_code VARCHAR(50)  NOT NULL,
    equipment_name VARCHAR(100) NOT NULL,
    status         VARCHAR(20)  NOT NULL  -- IDLE / RUNNING / STOPPED / MAINTENANCE
);

CREATE TABLE work_order (
    work_order_id  BIGINT AUTO_INCREMENT PRIMARY KEY,
    work_order_no  VARCHAR(50) NOT NULL,
    item_id        BIGINT NOT NULL REFERENCES item(item_id),
    equipment_id   BIGINT NOT NULL REFERENCES equipment(equipment_id),
    plan_qty       INT NOT NULL,
    start_date     DATE,
    end_date       DATE,
    status         VARCHAR(20) NOT NULL,  -- PLANNED / IN_PROGRESS / COMPLETED / CANCELLED
    created_at     DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE production_result (
    result_id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    work_order_id BIGINT NOT NULL REFERENCES work_order(work_order_id),
    prod_qty      INT NOT NULL,
    defect_qty    INT NOT NULL DEFAULT 0,
    result_date   DATE,
    worker_name   VARCHAR(50),
    created_at    DATETIME DEFAULT CURRENT_TIMESTAMP
);
```

## API 명세

### Item — `/api/items`

| Method | Path | Request Body | 설명 |
|---|---|---|---|
| POST | `/api/items` | `{ itemCode, itemName, unit }` | 품목 생성, 생성된 `itemId` 반환 |
| GET | `/api/items/{id}` | - | 단건 조회 |
| GET | `/api/items` | - | 전체 목록 조회 |
| PUT | `/api/items/{id}` | `{ itemName, unit }` | 품목명/단위만 수정 (`itemCode`는 수정 불가 — DB에도 수정 SQL이 없음) |
| DELETE | `/api/items/{id}` | - | 삭제 |

### Equipment — `/api/equipments`

| Method | Path | Request Body | 설명 |
|---|---|---|---|
| POST | `/api/equipments` | `{ equipmentCode, equipmentName }` | 설비 생성. 상태는 항상 서버에서 `IDLE`로 시작 (요청으로 지정 불가) |
| GET | `/api/equipments/{id}` | - | 단건 조회 |
| GET | `/api/equipments` | - | 전체 목록 조회 |
| PUT | `/api/equipments/{id}` | `{ status }` | 상태 변경. `status`는 `IDLE\|RUNNING\|STOPPED\|MAINTENANCE` enum — 다른 값이면 역직렬화 단계에서 자동 400 |
| DELETE | `/api/equipments/{id}` | - | 삭제 |

### WorkOrder (작업지시) — `/api/work-orders`

| Method | Path | Request | 설명 |
|---|---|---|---|
| POST | `/api/work-orders` | `{ workOrderNo, itemId, equipmentId, planQty, startDate, endDate }` | 작업지시 생성. `itemId`/`equipmentId`는 실존해야 함(없으면 예외). 상태는 항상 `PLANNED`로 시작 |
| GET | `/api/work-orders/{id}` | - | 단건 조회 (품목명/설비명 조인 포함) |
| GET | `/api/work-orders` | 쿼리 파라미터: `status, itemId, equipmentId, fromDate, toDate` (모두 선택) | 조건 검색. 파라미터 없이 호출하면 전체 목록 |
| PUT | `/api/work-orders/{id}/status` | 쿼리 파라미터 `status` | 상태 변경. `PLANNED\|IN_PROGRESS\|COMPLETED\|CANCELLED` |
| DELETE | `/api/work-orders/{id}` | - | 삭제 |

### ProductionResult (생산실적) — `/api/production-result`

CRUD 형태가 아니라 "생성 + 조회 2종"만 있는 API입니다 (전체 목록/단건 조회/수정/삭제 없음).

| Method | Path | Request | 설명 |
|---|---|---|---|
| POST | `/api/production-result` | `{ workOrderId, prodQty, defectQty, resultDate, workerName }` | 실적 1건 등록 |
| POST | `/api/production-result/batch` | `ProductionResult[]` | 여러 건 한 번에 등록 |
| GET | `/api/production-result/work-order/{workOrderId}` | - | 특정 작업지시의 실적 전체 조회 |
| GET | `/api/production-result/daily-summary` | 쿼리 파라미터 `fromDate, toDate` (필수) | 일자별 생산수량/불량수량 합계 |

## 대시보드

`src/main/resources/static/index.html` 하나로 되어 있고 Spring Boot의 기본 정적 리소스 서빙(`/`)으로 뜹니다.
Item/Equipment/WorkOrder는 생성 폼 + 목록 + 상태 변경/삭제, ProductionResult는 API 모양에 맞춰 등록/작업지시별 조회/일별 집계 3개 섹션,
그리고 위 API를 자유롭게 호출해볼 수 있는 API Console(method/path/body 직접 입력)이 있습니다. 별도 빌드 도구·의존성 없이 바닐라 JS로만 작성했습니다.

---

## MyBatis 정리

이 프로젝트를 진행하며 실제로 부딪히고 정리한 MyBatis 문법/개념입니다.

### `@Mapper`

인터페이스에 붙이면 mybatis-spring-boot-starter가 이 인터페이스를 스캔해서 프록시 구현체를 스프링 빈으로 등록합니다.
인터페이스 자체엔 구현이 없고, 메서드는 XML의 `<select>/<insert>/<update>/<delete>` 의 `id`와 이름으로 매칭됩니다.
`@Mapper`가 없으면 이걸 주입받는 `RepositoryImpl`이 "No qualifying bean" 에러로 뜨자마자 실패합니다.

매퍼가 많아지면 인터페이스마다 `@Mapper`를 붙이는 대신, 메인 클래스에 `@MapperScan("com.inho.mespractice")`를
붙여서 패키지 전체를 한 번에 스캔하는 방법도 있습니다.

### XML `<mapper>` 기본 구조

```xml
<mapper namespace="com.inho.mespractice.item.repository.ItemMapper">
  <!-- namespace는 반드시 매퍼 인터페이스의 FQCN과 일치해야 함 -->
  <!-- 각 태그의 id는 인터페이스 메서드 이름과 정확히 일치해야 함 -->
</mapper>
```

`mybatis.mapper-locations=classpath:mybatis/mapper/**/*.xml` 설정으로 이 프로젝트는
`src/main/resources/mybatis/mapper/{도메인}/{Domain}Mapper.xml` 경로에 매퍼 XML을 둡니다.

### `parameterType`은 생략 가능, `resultType`/`resultMap`은 생략 불가

- **입력(parameterType)**: 매퍼 인터페이스 메서드의 파라미터를 리플렉션으로 이미 알고 있어서 생략해도 자동 추론됩니다. insert/update/select/delete 전부 동일합니다.
- **출력(resultType / resultMap)**: XML 매퍼는 인터페이스의 리턴 타입을 자동으로 보고 추론해주지 않습니다. `<select>`에 SQL 결과를 어떤 클래스로 만들지 반드시 명시해야 합니다.
  - 컬럼명과 프로퍼티명이 단순 매칭되면 `resultType="패키지.ClassName"` 한 줄로 충분합니다.
  - 조인 결과, 값 매핑을 세밀하게 제어해야 하거나 PK를 명시하고 싶으면 `resultMap`을 씁니다.

```xml
<resultMap id="itemResultMap" type="com.inho.mespractice.item.entity.Item">
    <id property="itemId" column="item_id"/>        <!-- PK만 <id> -->
    <result property="itemCode" column="item_code"/> <!-- 나머지는 <result> -->
</resultMap>
```

`<id>`를 PK가 아닌 컬럼에도 붙이면 MyBatis의 객체 동일성 캐시가 그 값들까지 키로 묶어서 예상과 다르게 동작할 수 있습니다.

### `map-underscore-to-camel-case`

`application.properties`에 `mybatis.configuration.map-underscore-to-camel-case=true`를 켜두면
`item_code` 같은 snake_case 컬럼이 `itemCode` 프로퍼티로 자동 매핑됩니다. 이 프로젝트의 테이블들은 전부 단순 1:1 매핑이라,
사실 `resultMap`을 안 쓰고 `resultType`만 써도 되는 경우가 대부분입니다 (`resultMap`은 조인 결과가 있는 `WorkOrder`, `ProductionResult` 쪽에만 실질적 의미가 있음).

### `record`를 resultType으로 바로 매핑하기

`ProductionResultMapper.xml`의 `getDailySummary`는 `resultType="...dto.DailySummary"` (Java record)로 바로 매핑합니다.
`resultMap` 없이도, SQL의 컬럼 별칭(`AS resultDate` 등)이 record 컴포넌트 이름과 일치하면 MyBatis가 canonical 생성자를 통해 자동으로 채워줍니다.

### `#{}` 바인딩과 `@Param`

파라미터가 하나면 `#{프로퍼티명}` 그대로 쓰면 되지만, 파라미터가 여러 개면 인터페이스에서 `@Param("이름")`으로
바인딩 이름을 정해줘야 합니다. XML의 `#{이름}`이 이 이름과 정확히 일치해야 하며, 안 맞으면
`there is no getter for property named 'xxx'` 예외가 런타임에 납니다 (컴파일 타임엔 안 잡힙니다 — XML은 자바 컴파일러가 검사하지 않으므로).

```java
Item findById(@Param("itemId") Long itemId);
```
```xml
<select id="findById" resultMap="itemResultMap">
    SELECT ... WHERE item_id = #{itemId}
</select>
```

### 동적 SQL — `<if>`, `<where>`

조건부 검색(WorkOrder의 `search`)처럼 파라미터 유무에 따라 WHERE 절이 달라져야 할 때 씁니다.

```xml
<select id="search" resultMap="workOrderResultMap">
    SELECT ...
    <where>
        <if test="status != null">AND wo.status = #{status}</if>
        <if test="itemId != null">AND wo.item_id = #{itemId}</if>
    </where>
</select>
```

`<where>`는 내부에 조건이 하나도 안 붙으면 `WHERE` 자체를 안 넣고, 맨 앞에 남는 `AND`도 알아서 지워줍니다.
`test` 안의 이름은 파라미터 객체(DTO)의 필드명이어야 하고, `#{...}` 바인딩 이름과 반드시 같은 소스를 가리켜야 합니다 —
이름이 어긋나면 `<if>` 조건은 통과했는데 `#{}` 바인딩에서 없는 프로퍼티를 찾다가 예외가 나는, 헷갈리기 쉬운 실패 패턴이 나옵니다.

### `<foreach>` — 배치 insert

`ProductionResult`의 `insertResultsBatch`처럼 리스트를 한 번의 INSERT 문으로 묶을 때 씁니다.

```xml
<insert id="insertResultsBatch">
    INSERT INTO production_result (work_order_id, prod_qty, defect_qty, result_date, worker_name)
    VALUES
    <foreach collection="results" item="r" separator=",">
        (#{r.workOrderId}, #{r.prodQty}, #{r.defectQty}, #{r.resultDate}, #{r.workerName})
    </foreach>
</insert>
```

인터페이스 쪽에서 `@Param("results")`로 이름을 지정해야 `collection="results"`가 그 리스트를 가리킵니다.

### `useGeneratedKeys` / `keyProperty` — AUTO_INCREMENT PK 채우기

```xml
<insert id="insertItem" useGeneratedKeys="true" keyProperty="itemId">
    INSERT INTO item (item_code, item_name, unit) VALUES (#{itemCode}, #{itemName}, #{unit})
</insert>
```

이 두 속성이 있으면 INSERT 실행 후 DB가 생성한 PK 값을 파라미터로 넘긴 엔티티 객체(`item.itemId`)에 자동으로 채워줍니다.
그래서 `ItemRepositoryImpl.save()`가 `insert` 직후 바로 `item.getItemId()`를 리턴할 수 있습니다.

### enum 프로퍼티는 별도 설정 없이 자동 매핑됨

`Equipment.status`, `WorkOrder.status`를 `String`이 아니라 Java enum(`EquipmentStatus`, `WorkOrderStatus`)으로 선언해도
XML은 전혀 안 바꿔도 됩니다. MyBatis는 프로퍼티 타입이 enum이면 기본적으로 `EnumTypeHandler`를 자동 적용해서
`#{status}` 바인딩 시 `enum.name()`으로, 컬럼 값을 읽을 때 `Enum.valueOf()`로 자동 변환합니다. 잘못된 값이 오면
컨트롤러 진입 전(Jackson 역직렬화 단계)에서 걸러지므로, `String` + 수동 검증보다 이쪽이 훨씬 안전합니다.

---

## 트러블슈팅 로그

개발하면서 실제로 만났던 문제와 원인입니다. 대부분 "컴파일은 되는데 런타임에만 터지는" MyBatis/XML 특유의 실패 패턴이라 기록해둡니다.

| 증상 | 원인 | 교훈 |
|---|---|---|
| 앱을 띄우자마자 바로 꺼짐 (포트 리스닝 로그 없음) | `spring-boot-starter-web` 대신 `spring-boot-starter`만 있어서 내장 톰캣이 안 뜨는 비-웹 애플리케이션이었음 | Spring Boot 웹 앱은 `-web` 스타터가 필수 |
| `No qualifying bean of type 'EquipmentMapper'` | `mybatis-spring-boot-starter:3.0.3`(Spring Boot 3.x용)과 Spring Boot `4.1.0`의 버전 불일치로 `MybatisAutoConfiguration`이 `DataSourceAutoConfiguration`보다 먼저 평가됨 | 스타터 라이브러리도 Spring Boot 메이저 버전에 맞춰야 함. `--debug`로 컨디션 평가 리포트를 보면 원인이 바로 보임 |
| `changeStatus` 호출 시 `Invalid bound statement (not found)` | XML의 `<update id="updateStats">`가 인터페이스 메서드명 `updateStatus`와 오타로 어긋남 | XML `id`와 인터페이스 메서드명은 문자열이 정확히 같아야 하고, 이건 컴파일 타임에 절대 안 잡힘 |
| `findById` 호출 시 "no getter for property" 류 예외 위험 | `WHERE item_id = #{item_id}`인데 인터페이스는 `@Param("itemId")` | `#{}` 안의 이름은 `@Param` 이름과 정확히 일치해야 함 |
| 앱 기동 시 SqlSessionFactory 생성 단계에서 죽음 | `<select id="findById">`에 `resultType`/`resultMap`을 아예 안 씀 (정의해둔 `resultMap`이 어디서도 참조 안 됨) | `<select>`는 출력 타입 명시가 필수 — 정의만 해두고 참조를 깜빡하기 쉬움 |
| 작업지시 등록 시 데이터가 이상하게 들어감 | INSERT 컬럼 목록 `(work_order_id, ...)`에 `#{workOrderNo}` 값이 매칭됨 (컬럼명 오타) | 컬럼 목록과 VALUES 목록의 순서·개수가 어긋나도 SQL 문법 자체는 유효해서 눈으로 스캔해도 잘 안 보임 |
| PUT으로 `itemCode`를 바꿔도 반영 안 됨 | 컨트롤러가 엔티티를 그대로 요청/응답으로 썼는데, 실제 UPDATE SQL은 `item_name`/`unit`만 갱신함 | API 요청/응답 DTO를 영속성 엔티티와 분리하면, "SQL이 실제로 갱신하는 필드"와 "API가 받는 필드"를 일치시켜 이런 계약 불일치를 막을 수 있음 |
