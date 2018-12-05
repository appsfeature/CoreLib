# CoreLib

## Setup


Add this to your project build.gradle
``` gradle
allprojects {
    repositories {
        maven {
            credentials {
                username 'amitresearchdev'
                password 'fQugzTVKdZTgkVJe2Jk4'
            }
            url 'https://jitpack.io'
        }
    }
}
```
Add this to your module build.gradle

```gradle
   dependencies {
        implementation 'org.bitbucket.amitresearchdev:corelib:1.0'
    }

```

Usage

```
	//add in your Application class
    CoreLib.newInstance(APPLICATION_KEYWORD)
                  .setTestVersion("0.1")
                  .setBaseUrl("http://dennislab.com");

```