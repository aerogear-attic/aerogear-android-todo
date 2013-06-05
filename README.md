AeroGear Android Prototype
==========================

This is a prototype application that connects to the AeroGear TODO server
at http://todo-aerogear.rhcloud.com/.

Prerequisites
-------------

* Android Maven SDK Deployer

Note that to build this project with Maven, you'll need to use the
Android Maven SDK Deployer
(https://github.com/mosabua/maven-android-sdk-deployer) in order to
install the Google extension APIs into your local Maven repository.
To do this, just do

```bash
$ cd <wherever you installed the android-sdk-deployer>
$ mvn install -P 4.1 //or mvn install -P 4.2 if you are using the latest SDK
```

from the maven-android-sdk-deployer root directory.

NOTE: make sure you've already installed all the relevant Android
packages to your local SDK installation via the "android" tool, as
described in the documentation at
https://github.com/mosabua/maven-android-sdk-deployer/blob/master/README.markdown.

Also be sure to follow these instructions : https://github.com/aerogear/aerogear-android#setup-maven-android-sdk-deployer




Building
--------

After the above, running "mvn install" from the root directory
of this project should successfully build everything, run the
tests, and generate you a shiny new APK all set to install on
your phone.

NOTE: if you are using the latest SDK (4.2-17) you will need to do 2 additionals things :

* Change the platform version to ```17``` in the maven-android-sdk-deployer plugin section https://github.com/aerogear/aerogear-android-todo/blob/master/pom.xml#L122

* Apply the workaround due to bug in the latest maven-android-sdk-deployer : https://github.com/aerogear/aerogear-android#if-your-build-fails-with-could-not-find-tool-aapt
