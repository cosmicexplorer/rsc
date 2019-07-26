#!/usr/bin/env zsh

set -euxo pipefail

sbt publish{Local,M2} \
  &> >(tee /dev/stderr) \
  | sed -rne 's#^.*rsc_2.12-(.*)\.pom.*#\1#gp' \
  | head -n1 \
         > ./RSC_VERSION

RSC_VERSION="$(cat ./RSC_VERSION)"

sed -r -i -e "s#(val rscVersion = )(.*$)#\\1\"${RSC_VERSION}\"#g" \
    ../bloop/project/Dependencies.scala
