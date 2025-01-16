{ pkgs ? import <nixpkgs> {} }:

let
	stdenv = pkgs.stdenv;

	cmake = pkgs.cmake;
	gcc = pkgs.gcc;
	qt = pkgs.libsForQt5.qt5;
  wrapQtAppsHook = qt.wrapQtAppsHook;
#	xorg = pkgs.xorg;
in

stdenv.mkDerivation rec {
	pname = "XMachOViewer";
	version = "0.04";

	src = fetchTarball {
		url = "https://github.com/horsicq/XMachOViewer/releases/download/${version}/xmachoviewer_sourcecode_${version}.tar.gz";
		sha256 = "sha256:0b1agb7pw0nlgvh8m850mcbjhfrk1yn8iq6y79mh0iljis9f42vd";
	};

#  dontWrapQtApps = true;

	nativeBuildInputs = [
		cmake
		gcc
    wrapQtAppsHook
	];

	buildInputs = [
		qt.qtbase
		qt.qtsvg
		qt.qtscript
	];

  # QT_QPA_PLATFORM_PLUGIN_PATH="${qt.qtbase.bin}/lib/qt-${qt.qtbase.version}/plugins/platforms";

  # cmakeFlags = [
  #   "-DCMAKE_ARCHIVE_OUTPUT_DIRECTORY=$out/lib"
  #   "-DCMAKE_LIBRARY_OUTPUT_DIRECTORY=$out/lib"
  #   "-DCMAKE_RUNTIME_OUTPUT_DIRECTORY=$out/bin"
  # ];
}
