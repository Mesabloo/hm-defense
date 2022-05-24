{ pkgs ? import <nixpkgs> {}
  
}:

let
  gdx-setup-tool-jar = pkgs.fetchurl {
    url = "https://libgdx.com/assets/downloads/legacy_setup/gdx-setup_latest.jar";
    sha256 = "1w79rll5302inmysr2bav2y80117b86npgry23maiv6cnj5xa19l";
  };

  hiero-jar = pkgs.fetchurl {
    url = "https://libgdx-nightlies.s3.eu-central-1.amazonaws.com/libgdx-runnables/runnable-hiero.jar";
    sha256 = "1lvr4qa0qjs103nkbl2gyc0d0syza12ghf0qpwqcqqn1v3hiq765";
  };

  hiero = pkgs.stdenv.mkDerivation {
    name = "hiero";

    nativeBuildInputs = with pkgs; [
      wrapGAppsHook
      glib
      jdk11
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

  gdx-texture-packer = pkgs.stdenv.mkDerivation {
    name = "gdx-texture-packer";

    buildInputs = with pkgs; [
      jdk11
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

in
pkgs.mkShell {
  name = "hm-defense-shell";
  version = "0.0.1";

  nativeBuildInputs = with pkgs; [
    xorg.libXxf86vm
    openal

    jdk11
  ];

  buildInputs = with pkgs; [
    gradle

    kotlin

    jetbrains.idea-community

    gdx-texture-packer
    hiero

    (python3.withPackages (ps: with ps; [
      pillow
    ]))

    glib
    glib.dev
  ];

  LD_LIBRARY_PATH = "${pkgs.xorg.libXxf86vm}/lib:${pkgs.openal}/lib";
  GDX_SETUP = "java -jar ${gdx-setup-tool-jar}";
  JAVA_HOME = "${pkgs.jdk11}/lib/openjdk";
}
