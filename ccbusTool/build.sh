#!/usr/bin/env bash
mvn clean compile assembly:single && chmod +x target/ccbus-tool-* && mkdir target/extracted && cp target/ccbus-tool-* target/extracted &&
 cp target/ccbus-tool-* release/ &&
 cp target/ccbus-tool-* `echo release/ccbus-tool-* | sed 's/-\([0-9][0-9]*\.\)\([0-9][0-9]*\.\)*/./'` &&
 cd target/extracted && jar xfv ccbus-tool-*
