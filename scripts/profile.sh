#!/usr/bin/env bash

# 쉬고 있는 profile 찾기 : DB1이 사용 중이라면 DB2가 쉬고 있고, 반대면 DB1이 쉬고 있음
function find_idle_profile()
{
  RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost/profile)

  if [ ${RESPONSE_CODE} -ge 400 ] # 400 보다 크면 (즉, 40x/50x 에러 모두 포함)
  then
    CURRENT_PROFILE=DB2
  else
    CURRENT_PROFILE=$(curl -s http://localhost/profile)
  fi

  if [ ${CURRENT_PROFILE} == DB1 ]
  then
    IDLE_PROFILE=DB2
  else
    IDLE_PROFILE=DB1
  fi

  echo "${IDLE_PROFILE}"
}

#쉬고 있는 profile의 port 찾기
function find_idle_port()
{
  IDLE_PROFILE=$(find_idle_profile)

  if [ ${IDLE_PROFILE} == DB1 ]
  then
    echo "8081"
  else
    echo "8082"
  fi
}

# $(curl -s -o /dev/null -w "%{http_code}" http://localhost/profile)
# 현재 엔진엑스가 바라보고 있는 스프링 부트가 정상적으로 수행중인지 확인. 응답값을 HttpStatus 로 받는다. 정상이면 200, 오류면 400~503 사이로 발생하니 400 이상은 모두 예외로 보고 DB2를 현재 profile로 사용한다.

# IDLE_PROFILE
# 엔진엑스와 연결되지 않은 profile. 스프링 부트 프로젝트를 이 profile로 연결하기 위해 반환한다.

# echo "${IDLE_PROFILE}"
# bash 라는 스크립트는 값을 반환하는 기능이 없다. 그래서 제일 마지막 줄에 echo로 결과 출력 후 클라이언트에서 그 값을 잡아서 사용한다. 중간에 echo를 사용하면 안된다.