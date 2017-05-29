---
layout: default
title: CAS - Apache Cassandra Authentication
---

# Apache Cassandra Authentication

Verify and authenticate credentials using [Apache Cassandra](http://cassandra.apache.org/).

Support is enabled by including the following dependency in the WAR overlay:

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-cassandra-authentication</artifactId>
  <version>${cas.version}</version>
</dependency>
```