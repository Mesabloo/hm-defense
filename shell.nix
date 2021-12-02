{ pkgs ? import <nixpkgs> {}
  
}:

let
  gdx-setup-tool-jar = pkgs.fetchurl {
    url = "https://libgdx.com/assets/downloads/legacy_setup/gdx-setup_latest.jar";
    sha256 = "1w79rll5302inmysr2bav2y80117b86npgry23maiv6cnj5xa19l";
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
  ];

  LD_LIBRARY_PATH = "${pkgs.xorg.libXxf86vm}/lib:${pkgs.openal}/lib";
  GDX_SETUP = "java -jar ${gdx-setup-tool-jar}";
  JAVA_HOME = "${pkgs.jdk11}/lib/openjdk";
}
