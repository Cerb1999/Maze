{ pkgs ? import <nixpkgs> {}
}:

pkgs.mkShell {
  name = "maze-nix-shell";
  version = "1.0.0";

  buildInputs = with pkgs; [
    maven
    jdk11
    jetbrains.idea-community
  ];

  shellHook = ''
    export JAVA_HOME=${pkgs.jdk11}/lib/openjdk
  '';
}
