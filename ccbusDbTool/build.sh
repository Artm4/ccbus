#!/usr/bin/env bash
mvn clean compile assembly:single && chmod +x target/ccbus-dbtool-* && mkdir target/extracted && cp target/ccbus-dbtool-* target/extracted &&
 cp target/ccbus-dbtool-* release/ &&
 cp target/ccbus-dbtool-* `echo release/ccbus-dbtool* | sed 's/-\([0-9][0-9]*\.\)\([0-9][0-9]*\.\)*/./'` &&
 cd target/extracted && jar xfv ccbus-dbtool*
