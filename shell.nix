{ pkgs ? import <nixpkgs> {}
}:

pkgs.mkShell {
  name = "maze-nix-shell";
  version = "1.0.0";

  nativeBuildInputs = with pkgs; [
    # required to work with LibGDX
    xorg.libXxf86vm
    openal
  ];

  buildInputs = with pkgs; [
    maven
    jdk11
    jetbrains.idea-community
  ];

  LD_LIBRARY_PATH = "${pkgs.xorg.libXxf86vm}/lib:${pkgs.openal}/lib";

  shellHook = ''
    export JAVA_HOME=${pkgs.jdk11}/lib/openjdk
  '';
}
