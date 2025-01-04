{ pkgs ? import
    (builtins.fetchTarball {
      name = "nixpkgs-pinned";
      url = "https://github.com/nixos/nixpkgs/archive/de1864217bfa9b5845f465e771e0ecb48b30e02d.tar.gz";
      sha256 = "sha256:0q7j2ar7i7ylgr1zrpas9yh1vy2dmszlrr7x6jiz25vbmp3fgisi";
    })
    { }

}:

let
  gdx-setup-tool-jar = pkgs.fetchurl {
    url = "https://libgdx-nightlies.s3.amazonaws.com/libgdx-runnables/gdx-setup.jar";
    sha256 = "sha256-vIb8qI45rEuRDn7doLkZdwtOFjfEDX1fejL0gltzmGQ=";
  };

  gdx-lift-off-jar = pkgs.fetchurl {
    url = "https://github.com/libgdx/gdx-liftoff/releases/download/v1.13.0.2/gdx-liftoff-1.13.0.2.jar";
    sha256 = "sha256-yaJF5EnfDqgCdq7JhqY6uLPA+t+rVWjHnMA538X7WBI=";
  };

  gdx-lift-off = pkgs.stdenv.mkDerivation {
    name = "gdx-lift-off";

    nativeBuildInputs = with pkgs; [
      wrapGAppsHook
      glib
      jdk17
    ];

    src = null;

    unpackPhase = ":";

    installPhase = ''
      mkdir -p $out/bin

      cat >$out/bin/gdx-lift-off <<-'EOF'
      #!/usr/bin/env bash

      java -jar ${gdx-lift-off-jar}
      EOF
      chmod +x $out/bin/gdx-lift-off
    '';
  };

  hiero-jar = pkgs.fetchurl {
    url = "https://libgdx-nightlies.s3.eu-central-1.amazonaws.com/libgdx-runnables/runnable-hiero.jar";
    sha256 = "sha256-SjpYkZEK5vl7/i5a/wkJDnQZadcIMU5gUG5p0LZRkm0=";
  };

  hiero = pkgs.stdenv.mkDerivation {
    name = "hiero";

    nativeBuildInputs = with pkgs; [
      wrapGAppsHook
      glib
      jdk8
    ];

    src = null;

    unpackPhase = ":";

    installPhase = ''
      mkdir -p $out/bin

      cat >$out/bin/hiero <<-'EOF'
      #!/usr/bin/env bash

      java -jar ${hiero-jar}
      EOF
      chmod +x $out/bin/hiero
    '';
  };

  gdx-skin-composer-jar = pkgs.fetchurl {
    url = "https://github.com/raeleus/skin-composer/releases/download/50/SkinComposer.jar";
    sha256 = "1jgcns5k4a3ps0zfps30mp7j9vmqi3wnnwhr9pd4fi56jlbnaw2k";
  };

  skin-composer = pkgs.stdenv.mkDerivation {
    name = "skin-composer";

    nativeBuildInputs = with pkgs; [
      autoPatchelfHook
      wrapGAppsHook
      glib
      jdk17
    ];

    src = null;

    unpackPhase = ":";

    installPhase = ''
      mkdir -p $out/bin

      cat >$out/bin/skin-composer <<-'EOF'
      #!/bin/bash

      java -jar ${gdx-skin-composer-jar} "$@"
      EOF
      chmod +x $out/bin/skin-composer
    '';
  };

  gdx-texture-packer = pkgs.stdenv.mkDerivation {
    name = "gdx-texture-packer";

    buildInputs = with pkgs; [
      jdk17
      unzip
    ];

    setSourceRoot = "sourceRoot=`pwd`";

    src = pkgs.fetchurl {
      url = "https://github.com/crashinvaders/gdx-texture-packer-gui/releases/download/4.10.2/gdx-texturepacker-4.10.2.zip";
      sha256 = "12a3myzzsyild8xg6vpxibp5vhaxfkz7k2p9f2nl9lcgx3ahjy46";
    };

    installPhase = ''
      mkdir -p $out/bin

      cp gdx-texturepacker.jar $out/bin
      cp launcher_linux.sh $out/bin/gdx-texture-packer.sh

      chmod +x $out/bin/gdx-texture-packer.sh
    '';
  };

  # androidSdk = (pkgs.androidenv.composeAndroidPackages {
  #   # cmdLineToolsVersion = "8.0";
  #   toolsVersion = "26.1.1";
  #   platformToolsVersion = "33.0.2";
  #   buildToolsVersions = [ "33.0.0" ];
  #   includeEmulator = false;
  #   emulatorVersion = "30.3.4";
  #   platformVersions = [ "31" "33" ];
  #   includeSources = false;
  #   includeSystemImages = false;
  #   systemImageTypes = [ "google_apis_playstore" ];
  #   abiVersions = [ "armeabi-v7a" "arm64-v8a" ];
  #   cmakeVersions = [ "3.10.2" ];
  #   includeNDK = true;
  #   ndkVersions = ["22.0.7026061"];
  #   useGoogleAPIs = false;
  #   useGoogleTVAddOns = false;
  #   includeExtras = [
  #     "extras;google;gcm"
  #   ];
  # }).androidsdk;

in
pkgs.mkShell {
  name = "hm-defense-shell";
  version = "0.0.1";

  nativeBuildInputs = with pkgs; [
    openal
    # glfw2
    # mesa
    xorg.libXxf86vm
    libGL

    jdk17
  ];

  buildInputs = with pkgs; [
    gradle

    jetbrains.idea-community-bin

    # androidSdk
    #androidStudioPackages

    gdx-texture-packer
    hiero
    gdx-lift-off
    skin-composer

    (python3.withPackages (ps: with ps; [
      pillow
    ]))

    glib
    glib.dev

    visualvm

    graphviz

    glxinfo
  ];

  # LD_LIBRARY_PATH = "${pkgs.openal}/lib:${pkgs.glfw3}/lib:${pkgs.xorg.libXxf86vm}/lib:${pkgs.mesa}/lib";
  LD_LIBRARY_PATH = "${pkgs.libGL}/lib:${pkgs.xorg.libXxf86vm}/lib:${pkgs.openal}/lib";
  GDX_SETUP = "java -jar ${gdx-setup-tool-jar}";
  JAVA_HOME = "${pkgs.jdk17}/lib/openjdk";
  GRAPHVIZ_DOT = "${pkgs.graphviz}/bin/dot";
  # ANDROID_SDK = "${androidSdk}";
}
