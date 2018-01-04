#!/bin/bash

git=$(sh /etc/profile; which git)
number_of_commits=$("$git" rev-list HEAD --count)
git_release_version=$("$git" describe --tags --always --abbrev=0)

target_plist="$TARGET_BUILD_DIR/$INFOPLIST_PATH"
dsym_plist="$DWARF_DSYM_FOLDER_PATH/$DWARF_DSYM_FILE_NAME/Contents/Info.plist"

echo $number_of_commits
echo ${git_release_version#*v}

echo "VERSION_CODE=$number_of_commits\nVERSION_NAME=${git_release_version#*v}" > version.properties
