#!/usr/bin/env zsh

set -euxo pipefail

sbt publish{Local,M2} \
  &> >(tee /dev/stderr) \
  | sed -rne 's#^.*rsc_2.12-(.*)\.pom.*#\1#gp' \
  | head -n1 \
         > ./RSC_VERSION

RSC_VERSION="$(cat ./RSC_VERSION)"

sed -r -i -e "s#(val rscVersion = )(.*$)#\\1\"${RSC_VERSION}\"#g" \
    ../bloop/project/Dependencies.scala \
    ../bloop/zinc/project/Dependencies.scala

sed -r -i -e "s#^(.*\"rsc\" +% +)(.*)\"\$#\\1\"${RSC_VERSION}\"#g" \
    ../scala/build.sbt

sed -r -i -e "s#(com.twitter:rsc_2.12:)(.*)(')#\\1${RSC_VERSION}\\3#g" \
    ../bloop/bloop-invoke/blp-server

mkdir -p ~/tools/scala/build/pack/lib

coursier fetch "com.twitter:rsc_2.12:${RSC_VERSION}" \
  | parallel -t cp {} ~/tools/scala/build/pack/lib

coursier fetch 'org.scala-lang:scala-compiler:2.12.8' \
  | grep -F 'scala-xml_2.12-1.0.6.jar' \
  | parallel -t cp {} ~/tools/scala/build/pack/lib
