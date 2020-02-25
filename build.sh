#!/bin/sh
mvn clean install
mvn site
mvn exec:java