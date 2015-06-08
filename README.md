# kore-javamail
This is a fork of the java-mail api and javax.activation, which is made "Android ready" (java-mail api and javax.activation uses the java.awt package, which is not available in Android). And this project seems ot be not developed anymore and is not able to load MimeMultipart messages correct: https://code.google.com/p/javamail-android/

Get it via jitpack.io
```gradle
repositories {
	    maven {
	        url "https://jitpack.io"
	    }
	}
...

compile 'com.github.konradrenner:kore-javamail:[version]'
```
or in Maven:

```xml
<repository>
	    <id>jitpack.io</id>
	    <url>https://jitpack.io</url>
</repository>
...
<dependency> 
	<groupId>com.github.konradrenner</groupId> 
	<artifactId>kore-javamail</artifactId> 
	<version>[version]</version> 
</dependency>
``` 

[![Build Status](https://secure.travis-ci.org/konradrenner/kore-javamail.png?branch=master)](http://travis-ci.org/konradrenner/kore-javamail)

<a href="https://scan.coverity.com/projects/4758">
  <img alt="Coverity Scan Build Status"
       src="https://scan.coverity.com/projects/4758/badge.svg"/>
</a>

<a href="http://android-arsenal.com/details/1/1697">
  <img alt="Android Arsenal"
       src="https://img.shields.io/badge/Android%20Arsenal-kore--javamail-brightgreen.svg?style=flat)%5D(http://android-arsenal.com/details/1/1697"/>
</a>
