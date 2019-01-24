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
#### Library Version
[![](https://jitpack.io/v/org.bitbucket.amitresearchdev/corelib.svg)](https://jitpack.io/#org.bitbucket.amitresearchdev/corelib)

```gradle
   dependencies {
        implementation 'org.bitbucket.amitresearchdev:corelib:x.y'
    }

```

## Usage ProgressButton
in xml file
```xml
    <include layout="@layout/button_progress"/>
```

in Activity class
```java
         ProgressButton btnAction = ProgressButton.newInstance(this, getActivityRootView(this))
                .setText("Send Request")
                .setBackground(R.drawable.progress_button_disable)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        executeTask();
                    }
                });

         //use this method to initiate progress bar while starting task in background
         btnAction.startProgress();

         // call this method when getting success response from background task
         btnAction.revertSuccessProgress(new ProgressButton.Listener() {
                          @Override
                          public void onAnimationCompleted() {
                          }
                      });
         // use this method when getting wrong response and revert the initial stage of button
         btnAction.revertProgress();

        public static View getActivityRootView(Activity activity) {
            return activity.getWindow().getDecorView().getRootView();
        }

```
## Usage ProgressManager
in xml file
```xml
    <RelativeLayout
        android:id="@+id/container"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
```

in Activity class
```java
         RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
        ProgressManager progressManager = ProgressManager.newInstance(container)
                .setLoadingMessage("Processing, Please wait...")
                .setLayoutType(ProgressManager.SCREEN_SMALL)
                .setProgressTheme(ProgressManager.THEME_DARK)
                .setErrorIcon(R.drawable.ic_internet_error)
                .setRetryListener(new RetryListener() {
                    @Override
                    public void onRetryClick() {
                        executeTask();
                    }
                })
                .build();

        //start progress
        progressManager.updateLoadingMessage("Downloading, Please wait.....");
        progressManager.startProgress();

        //stop progress with success
        progressManager.stopProgress();

        //stop progress with failure message
        progressManager.stopProgress("Internet error occurred");


```

## Usage Classes

### global
```xml
    BaseFragmentUtil
    DatePickerDialog
    PopupAlert
    PopupDialog
    TimePickerDialog
```

### interfaces
```xml
    OnItemClick
    TransitionListener
```

### model
```xml
    DefaultModel
    ErrorAdapter
```

### setup
```xml
    BaseUtil
    CoreLib
    NavDrawer
```

### viewcust
```xml
    EditTextWithClear
    NestedWebView
    TouchImageView
```
### util
```xml
    AppAnimation
    AppPreferences
    CoreLibConst
    DateTimeUtility
    FieldValidation
    FileUtils
    L
    ServiceTools
    ShareWere
    SimpleDrawingView
    Utility
```


