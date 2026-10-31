# Overview

<div style="text-align: center">

[![Java CI with Gradle](https://github.com/astrapi69/modjenesis/actions/workflows/gradle.yml/badge.svg)](https://github.com/astrapi69/modjenesis/actions/workflows/gradle.yml)
[![Coverage Status](https://codecov.io/gh/astrapi69/modjenesis/branch/develop/graph/badge.svg)](https://codecov.io/gh/astrapi69/modjenesis)
[![Open Issues](https://img.shields.io/github/issues/astrapi69/modjenesis.svg?style=flat)](https://github.com/astrapi69/modjenesis/issues)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.astrapi69/modjenesis)](https://central.sonatype.com/artifact/io.github.astrapi69/modjenesis)
[![Javadocs](http://www.javadoc.io/badge/io.github.astrapi69/modjenesis.svg)](http://www.javadoc.io/doc/io.github.astrapi69/modjenesis)
[![MIT License](http://img.shields.io/badge/license-MIT-brightgreen.svg?style=flat)](http://opensource.org/licenses/MIT)
[![Donate](https://img.shields.io/badge/donate-❤-ff2244.svg)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=GVBTWLRAZ7HB8)
[![Hits Of Code](https://hitsofcode.com/github/astrapi69/modjenesis?branch=develop)](https://hitsofcode.com/github/astrapi69/modjenesis/view?branch=develop)

</div>

Modjenesis is a module for create java objects with reflection as java module.
Initiate as a clone of objenesis and then modified to be deployed as a java module with JDK version 17.

> Please support this project by simply putting a Github <a class="github-button" href="https://github.com/astrapi69/modjenesis" data-icon="octicon-star" aria-label="Star astrapi69/modjenesis on GitHub">
> Star ⭐</a>
>
> Share this library with friends on Twitter and everywhere else you can
>
> If you love this project
> [![Donation](https://img.shields.io/badge/donate-❤-ff2244.svg)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=GVBTWLRAZ7HB8)

## Note

No animals were harmed in the making of this library.

## License

The source code comes under the liberal MIT License, making modjenesis great for all types of applications.

## gradle dependency

Replace the variable ${latestVersion} with the current latest version: [![Maven Central](https://img.shields.io/maven-central/v/io.github.astrapi69/modjenesis)](https://central.sonatype.com/artifact/io.github.astrapi69/modjenesis)

You can first define the version in the ext section and add than the following gradle dependency to
your project `build.gradle` if you want to import the core functionality of modjenesis:

define version in file gradle.properties

```
modjenesisVersion=${latestVersion}
```

or in build.gradle ext area

```
    modjenesisVersion = "${latestVersion}"
```

then add the dependency to the dependencies area

```
    implementation("io.github.astrapi69:modjenesis:$modjenesisVersion")
```

## 📸 Snapshots

This section describes how to import snapshot versions into your project.
Add the following code snippet to your gradle file in the repositories section:
```
repositories {
   //...
```
```groovy
    maven {
        name "Central Portal Snapshots"
        url "https://central.sonatype.com/repository/maven-snapshots/"
        mavenContent {
            snapshotsOnly()
        }
    }
```
```
}
```

## Maven dependency

The library is on Maven Central. Check out [Maven Central](https://central.sonatype.com/artifact/io.github.astrapi69/modjenesis) for the latest release.

Add the following maven dependency to your project `pom.xml` if you want to import the core
functionality of modjenesis:

Then you can add the dependency to your dependencies:

    <properties>
        ...
```xml
        <!-- modjenesis version -->
        <modjenesis.version>${latestVersion}</modjenesis.version>
```
        ...
    </properties>
        ...
        <dependencies>
        ...
```xml
            <!-- modjenesis DEPENDENCY -->
            <dependency>
                <groupId>io.github.astrapi69</groupId>
                <artifactId>modjenesis</artifactId>
                <version>${modjenesis.version}</version>
            </dependency>
```
        ...
        </dependencies>

## Support this project

If modjenesis is useful to you, a star on [GitHub](https://github.com/astrapi69/modjenesis) helps others find it.
Sharing it or reporting a bug helps just as much.

If you would like to contribute financially, the channels are on the repository's Sponsor button, which
reads [.github/FUNDING.yml](.github/FUNDING.yml): GitHub Sponsors, Liberapay, Ko-fi and PayPal.

## Semantic Versioning

The versions of modjenesis are maintained with the Semantic Versioning guidelines.

Release version numbers will be incremented in the following format:

`<major>.<minor>.<patch>`

For detailed information on versioning you can visit the [wiki page](https://github.com/lightblueseas/mvn-parent-projects/wiki/Semantic-Versioning).

## What can i do to support this project

You can donate or contribute solve issues or pull request. Every support are welcome.

## Want to Help and improve it? ###

The source code for modjenesis are on GitHub. Please feel free to fork and send pull requests!

Create your own fork of [astrapi69/modjenesis/fork](https://github.com/astrapi69/modjenesis/fork)

To share your changes, [submit a pull request](https://github.com/astrapi69/modjenesis/pull/new/develop).

Don't forget to add new units tests on your changes.

## Contacting the Developers

Do not hesitate to contact the modjenesis developers with your questions, concerns, comments, bug reports, or feature requests.
- Feature requests, questions and bug reports can be reported at the [issues page](https://github.com/astrapi69/modjenesis/issues).

## Similar projects

## Credits
