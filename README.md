# WAS 만들기 study

## 시작 전 준비 

1. Base repository 에 `본인 github 아이디`로 자신의 브랜치를 생성한다.
2. fork 를 통해 자신의 저장소에 복사한다. (fork 시 `copy the main branch only` 를 체크한다.)
3. fork 한 저장소를 local pc 에 clone 한다.

```shell
git clone -b https://github.com/{본인_아이디}/{저장소_아이디}
git remote add upstream {저장소_아이디}
```

4. 본인 이름의 브랜치를 생성한다
```shell
git checkout -b 브랜치이름
```

5. 기능 구현을 완료하고 add, commit 후 본인 브랜치에 push 한다.
6. github에서 pr을 생성한다.
7. 해당 차시 스터디 진행후 해당 브랜치에 merge 한다. (admin이 진행)
8. 5번부터 반복한다.
