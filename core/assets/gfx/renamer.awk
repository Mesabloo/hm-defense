#!/usr/bin/env -S awk -f

BEGIN {
  SCRIPT_NAME = "organize_images";
  SH_SCRIPT = SCRIPT_NAME ".sh";
  PS_SCRIPT = SCRIPT_NAME ".ps1";

  print "#!/usr/bin/env bash" > SH_SCRIPT;
}

function extract_basepath(full_path) {
  full_path = gensub("/+", "/", "g", full_path);
  # Replace multiple consecutive "/" with a single "/".
  # This is not absolutely needed and can be removed.
  size = split(full_path, components, "/");

  output = "."
  for (i = 1; i < size; i++) {
    output = output "/" components[i];
  }

  return output
}

match($0, /^- (.*?) → (.*?)$/, gr) {
  $1 = gensub(/`(.*?)`/, "\\1", "g", gr[1]);
  if (gr[2] != "❌") {
    $2 = gensub(/`(.*?)`/, "\\1", "g", gr[2]);
    
    dir = extract_basepath($2);

    # Generate bash script
    print "mkdir -p '" dir "' >/dev/null || true" > SH_SCRIPT;
    print "cp '" $1 "' '" $2 "'" > SH_SCRIPT;
    # Generate batch script
    print "mkdir \"" dir "\" -erroraction silentlycontinue >$null" > PS_SCRIPT;
    print "cp \"" $1 "\" \"" $2 "\"" > PS_SCRIPT;
  } 
  # Generate bash script
  # print "rm '" $1 "' || true" > SH_SCRIPT;
  # print "rm \"" $1 "\" -erroraction silentlycontinue" > PS_SCRIPT; 
}
