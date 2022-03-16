# 올빼미

우리가 밤에도 빛나는 이유

---

개발기간: 2021.03 ~ 2021. 05
개발인원: 5인

- 기획/디자인: [abcdami](https://github.com/abcdami)
- 안드로이드 프론트엔드: [jsh4732](https://github.com/jsh4732), [lovetreeshine](https://github.com/lovetreeshine), [byhankim](https://github.com/byhankim)
- 백엔드: [h662]()

---

## 프로젝트 소개

**심야시간 활동 사용자들을 위한 커뮤니티 매핑 정보공유 서비스**
올빼미 앱은 활동 시간대가 주로 심야시간인 야행성 사용자들을 위한 서비스입니다. 관심사 카테고리화 및 지도 매핑을 통하여 심야시간대의 정보공유를 편리하게 이용할 수 있습니다.
올빼미 앱에서 제공하는 기능은 다음과 같습니다.

- 소셜로그인 / 휴대전화 인증
- 지도 마킹하여 글쓰기
- 기본적인 커뮤니티 기능
- 마이페이지 (책갈피, 좋아요, 작성 댓글 보기 등)

---

## 개발환경

- Frontend: Kotlin, Android
- Backend: Nest.js, Prisma, AWS S3

---

## 안드로이드 사용 라이브러리 / 오픈소스

- CircleImageView
- Glide
- OkHttp3
- Retrofit2
- gson
- gsonConverterFactory
- TedPermission
- Google Maps API

---

## 핵심 구현 내용

- 소셜로그인
  ```
  - 카카오 API 적용
  - 로그인 토큰 SharedPreference에 저장
  ```
- 지도 마킹
  ```
  - Google Maps API 적용
  - 지도에 Longclick 시 위도/경도 정보 포함하여 글 작성 기능
  ```
- 커뮤니티 목록
  ```
  - 책갈피, 좋아요 적용
  - 무한스크롤 기능
  ```
- 글/댓글
  ```
  - 글 작성시 카테고리 선택, 이미지 파일 첨부 기능
  - 댓글 및 대댓글 작성 기능
  ```
- 휴대폰 인증

---

## 사용 툴

- Android Studio
- VS Code
- Insomnia
- GitHub
- Notion
- Figma
- Slack
