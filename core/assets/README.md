## Assets redistribution policy

I have contacted the original studio to ask them if they wanted to give me the original assets (images, sounds, etc), but I
still have not received an answer to this day.
While I managed to gather all those assets from an official `.ipa` file installed on one of my apple devices, I will not be
redistributing them until I get an official grant for this (this README will be updated if it happens).

Because reverse-engineering an Android/Desktop app is pretty easy, leading to assets stealing, I will not put publicly available
releases on github, unless I encrypt those assets (the source code is public, so reversing this could also be very easy).

## Extracting original assets

The steps underneath require you have an unsigned `.ipa` file.
Unfortunately, those are hard to get for this game (I highly struggled with it on the Internet) and the only real solution is to
extract it from an original installation.
You may use [Clutch](https://github.com/KJCracks/Clutch) to extract unsigned IPA files from installed applications.

An IPA file is very similar to an archive, and can be opened/extracted using [7Zip](https://www.7-zip.org/).
After extracting the IPA file, you should get a folder structure similar to this:
```bash
.
├── iTunesArtwork
└── Payload
    └── MachDefense.app
        ├── BaseUpgrade.tble
        ├── BG_01_01.jpg
        ├── BG_01_02.jpg
        ├── BG_02_01.jpg
        ┆
        ├── _CodeSignature
        │   └── CodeResources
        ├── Description_kr.tble
        ├── Description_us.tble
        ├── DmgPlasma.wav
        ┆
        └── Wpn_EnMissile.wav
```
All assets that are willing to extract are located in the `Payload/MachDefense.app` folder.

### Extracting images and sounds

There are *many* asset files in this game.
Some of them like sounds are easy to get: simply copy and paste them following the structure at the end of this document.

For images, it is a little bit more complicated.
JPEG files can be copied and pasted as such, just like sounds.
However, PNG files are compressed using Apple's toolchain, which makes them incompatible with some image viewer (in fact, many of them).
This is because the compressing tool insert some non-standard weird chunks in the PNG files
(see [this Cocos2D issue](https://github.com/cocos2d/cocos2d-x/issues/15199)), which cannot be parsed by `libpng`.

The solution here is to use the tool named [pngdefry](http://www.jongware.com/pngdefry.html), whose goal is to reverse those modifications
to get a perfectly valid PNG file.<br>
This tool may surely be found for Windows (I haven't been able to), and a Linux version is hosted online
[here](https://github.com/bumaociyuan/ios-ipa-server/raw/master/pngdefry-linux).

After calling `./pngdefry -o Somewhere file.png` on every PNG file (replacing `file` and `Somewhere` respectively with the name of the file
and where you want the defried images copied), all usable PNG files will be located inside `Somewhere`.
 
### Extracting stat files

Extracting imaegs and sounds is very easy compared to this, because those files are encrypted using an unknown home-made algorithmi (the `.tble` files).
I haven't managed to get the hold on this algorithm, however I managed to extract the unencrypted tables anyway, by abusing the
Objective-C runtime with GDB.

In order to retrieve each of them, you will need to follow this steps:
- Make sure that your iOS device is jailbroken, and that the app is already installed on it.
- Have a SSH tunnel opened so that you can connect to the device on your computer.
  Note that this is absolutely not necessary, but makes life way easier than having to go on MTerminal or similar apps directly on the device.

  All commands need to be performed in the terminal (may it be an app like MTerminal or the SSH tunnel) as of now.
- Install GDB on your device (for example the one from the radare repo).

  Warning: if executing `gdb` yields the error `Invalid instruction 4`, you will need to patch it yourself using the following commands
  (you will need `ldid` and `sed` installed from Cydia):
  ```bash
  sed -i'' 's/\x00\x30\x93\xe4/\x00\x30\x93\xe5/g;s/\x00\x30\xd3\xe4/\x00\x30\xd3\xe5/g;' /usr/bin/gdb
  ldid -s /usr/bin/gdb
  ```
- Copy the file [`./reverse/create_and_dump_qtable.gdb`] to your device, somewhere you can easily find it (we'll name this folder `ROOT` underneath).
- Launch the game on the device.
- Find out what PID it as. One can execute the command `ps ax | grep 'MachDefense'`, which should return something along those lines:
  ```bash
  13114   ??  Ss     0:00.80 /var/containers/Bundle/Application/AB7A3DD2-9F9C-4801-8744-7F322A8B82C6/MachDefense.app/MachDefense
  13117 s000  R+     0:00.01 grep MachDefense
  ```
  The second line is not of interest.
  It is in fact the process filtering the output of `ps ax`.

  The number we are interested in is the very first one on the line: `13114` (this will vary depending on many factors).
  Write it down.
- Start GDB on this process and give it the command file to execute:
  ```bash
  gdb -p 13114 --command=ROOT/create_and_dump_qtable.gdb
  ```
  This may take some time to load, but don't worry, you should see the GDB prompt at some point (it says `(gdb)`).

  The app will be frozen for as long as we stay in GDB. This is the expected behavior.
- For each stat file in the list below, execute this command in GDB (you might be able to use loops?):
  ```bash
  create_and_dump_qtable FILE
  ```

  This will print some stuff similar to:
  ```bash
  $1 = (void *) 0x1549bc
  $2 = (void *) 0x3ba4a520
  $3 = (void *) 0x2464f4a0
  $4 = (void *) 0x30f648a2
  $5 = (void *) 0x246558d3
  $6 = (void *) 0x2465eb1f
  $7 = (void *) 0x166aa590
  $8 = (void *) 0x16643c40
  $9 = (void *) 0x88763a0
  $10 = (void *) 0x166aa590
  Outputting dictionary of <QTable 0x166aa590> to file FILE.log
  ```

  The most important part is that the whole stat dictionary has been written down to the file `FILE.log`.

  -----------

  Table file names list:
  ```
  BaseUpgrade        ESpawn_22     ESpawn_46     ESpawn_70 
  Description_kr     ESpawn_23     ESpawn_47     ESpawn_71 
  Description_us     ESpawn_24     ESpawn_48     ESpawn_72 
  ESpawn_01          ESpawn_25     ESpawn_49     ESpawn_73 
  ESpawn_02          ESpawn_26     ESpawn_50     ESpawn_74 
  ESpawn_03          ESpawn_27     ESpawn_51     ESpawn_75 
  ESpawn_04          ESpawn_28     ESpawn_52     ESpawn_76 
  ESpawn_05          ESpawn_29     ESpawn_53     ESpawn_77 
  ESpawn_06          ESpawn_30     ESpawn_54     ESpawn_78 
  ESpawn_07          ESpawn_31     ESpawn_55     ESpawn_79 
  ESpawn_08          ESpawn_32     ESpawn_56     ESpawn_80 
  ESpawn_09          ESpawn_33     ESpawn_57     ItemInfo 
  ESpawn_10          ESpawn_34     ESpawn_58     ItemRegen 
  ESpawn_11          ESpawn_35     ESpawn_59     MachBuild_Name 
  ESpawn_12          ESpawn_36     ESpawn_60     MachBuild_Parts 
  ESpawn_13          ESpawn_37     ESpawn_61     MachList 
  ESpawn_14          ESpawn_38     ESpawn_62     MachRegen 
  ESpawn_15          ESpawn_39     ESpawn_63     MapList 
  ESpawn_16          ESpawn_40     ESpawn_64     PartsList 
  ESpawn_17          ESpawn_41     ESpawn_65     SpAttackList 
  ESpawn_18          ESpawn_42     ESpawn_66     TileList 
  ESpawn_19          ESpawn_43     ESpawn_67     WeaponInfo 
  ESpawn_20          ESpawn_44     ESpawn_68     
  ESpawn_21          ESpawn_45     ESpawn_69     
  ```
- Convert the output format to JSON, following these rules:
  - Remove all `XXX@` in keys (where `XXX` is any number)
  - TODO

## How do I still play the game?

This folder will need to be populated with all the assets needed as described below.

Each entry is formatted as `filename.ext     # what this is`.<br>
The part `# what this is` is only a comment on what the file represents and should not be present in the filename. (TODO)
```bash
.
└
```

Once done, a simple compilation using the `gradle` tool (as described in the root README) should work and generate either an
APK file (for Android) or a Desktop executable.
