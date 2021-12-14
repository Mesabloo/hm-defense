## Assets redistribution policy

I have contacted the original studio to ask them if they wanted to give me the original assets (images, sounds, etc), but I
still have not received an answer to this day.
While I managed to gather all those assets from an official `.ipa` file installed on one of my apple devices, I will not be
redistributing them until I get an official grant for this (this README will be updated if it happens).

Because reverse-engineering an Android/Desktop app is pretty easy, leading to assets stealing, I will not put publicly available
releases on github, unless I encrypt those assets (the source code is public, so reversing this could also be very easy).

### How do I still play the game?

This folder will need to be populated with all the assets needed as described below.

Each entry is formatted as `filename.ext     # what this is`.<br>
The part `# what this is` is only a comment on what the file represents and should not be present in the filename. (TODO)
```bash
.
└
```

Once done, a simple compilation using the `gradle` tool (as described in the root README) should work and generate either an
APK file or a Desktop executable.

## Note for those people getting the real assets from the original game

If you extracted an `.ipa` file from a working iOS device, you will be facing a little issue coming from the fact that
the PNG are modified in a non-standard by the iOS toolchain (to add some extra metadata), which leads to most image
viewers on Linux not being able to preview the images 
([libpng does not handle the extra non-standard chunks they put in it](https://github.com/cocos2d/cocos2d-x/issues/15199)).

The solution to retrieve the original viewable PNG is to use [pngdefry](http://www.jongware.com/pngdefry.html) which 
processes the images to remove those non-standard chunks (without breaking the image completely).<br>
I am not sure if it is available for Windows, however a Linux version can be found 
[here](https://github.com/bumaociyuan/ios-ipa-server/raw/master/pngdefry-linux).