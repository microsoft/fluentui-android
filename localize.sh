#!/bin/bash

# =========================
# Logging / tracing
# =========================
set -x
export PS4='+ $(date -u +"%Y-%m-%dT%H:%M:%SZ") localize.sh:${LINENO} -> '

echo "========== localize.sh START =========="
echo "PWD: $(pwd)"
echo "Args: $*"
echo "Timestamp: $(date -u +"%Y-%m-%dT%H:%M:%SZ")"

# =========================
# Env visibility
# =========================
echo "---- Environment ----"
env | sort
echo "---------------------"

# =========================
# Git visibility (before)
# =========================
echo "---- Git branch ----"
git branch --show-current

echo "---- Git HEAD ----"
git rev-parse HEAD

echo "---- Git status BEFORE ----"
git status --porcelain=v1

# =========================
# Directory snapshot (before)
# =========================
echo "---- Directory tree (depth 3) BEFORE ----"
find . -maxdepth 3 -type d

echo "---- Localization files BEFORE ----"
find . -path "*res/values*" -type f

# =========================
# Existing logic (UNCHANGED)
# =========================
if [ -z $TDBUILD_TEAM_ID ]; then
  printf "Team ID: "
  read TDBUILD_TEAM_ID
fi

if [ -z $TDBUILD_AAD_APPLICATION_CLIENT_ID ]; then
  printf "Alias: "
  read TDBUILD_AAD_APPLICATION_CLIENT_ID
fi

if [ -z $TDBUILD_AAD_APPLICATION_CLIENT_SECRET ]; then
  stty -echo
  printf "Password: "
  read TDBUILD_AAD_APPLICATION_CLIENT_SECRET
  stty echo
  printf "\n"
fi

echo "Using Team ID: $TDBUILD_TEAM_ID"
echo "Using Alias: $TDBUILD_AAD_APPLICATION_CLIENT_ID"

start_ts=$(date +%s)

./GetLocalizedFiles.sh -t $TDBUILD_TEAM_ID -u \
  -a $TDBUILD_AAD_APPLICATION_CLIENT_ID \
  -p $TDBUILD_AAD_APPLICATION_CLIENT_SECRET \
  -f FluentUI.Demo/src/main/res/values \
  -r demo \
  -o FluentUI.Demo/src/main/res/values

./GetLocalizedFiles.sh -t $TDBUILD_TEAM_ID -u \
  -a $TDBUILD_AAD_APPLICATION_CLIENT_ID \
  -p $TDBUILD_AAD_APPLICATION_CLIENT_SECRET \
  -f fluentui_calendar/src/main/res/values \
  -r fluentui_calendar \
  -o fluentui_calendar/src/main/res/values

./GetLocalizedFiles.sh -t $TDBUILD_TEAM_ID -u \
  -a $TDBUILD_AAD_APPLICATION_CLIENT_ID \
  -p $TDBUILD_AAD_APPLICATION_CLIENT_SECRET \
  -f fluentui_ccb/src/main/res/values \
  -r fluentui_ccb \
  -o fluentui_ccb/src/main/res/values

./GetLocalizedFiles.sh -t $TDBUILD_TEAM_ID -u \
  -a $TDBUILD_AAD_APPLICATION_CLIENT_ID \
  -p $TDBUILD_AAD_APPLICATION_CLIENT_SECRET \
  -f fluentui_controls/src/main/res/values \
  -r fluentui_controls \
  -o fluentui_controls/src/main/res/values

./GetLocalizedFiles.sh -t $TDBUILD_TEAM_ID -u \
  -a $TDBUILD_AAD_APPLICATION_CLIENT_ID \
  -p $TDBUILD_AAD_APPLICATION_CLIENT_SECRET \
  -f fluentui_core/src/main/res/values \
  -r fluentui_core \
  -o fluentui_core/src/main/res/values

./GetLocalizedFiles.sh -t $TDBUILD_TEAM_ID -u \
  -a $TDBUILD_AAD_APPLICATION_CLIENT_ID \
  -p $TDBUILD_AAD_APPLICATION_CLIENT_SECRET \
  -f fluentui_drawer/src/main/res/values \
  -r fluentui_drawer \
  -o fluentui_drawer/src/main/res/values

./GetLocalizedFiles.sh -t $TDBUILD_TEAM_ID -u \
  -a $TDBUILD_AAD_APPLICATION_CLIENT_ID \
  -p $TDBUILD_AAD_APPLICATION_CLIENT_SECRET \
  -f fluentui_listitem/src/main/res/values \
  -r fluentui_listitem \
  -o fluentui_listitem/src/main/res/values

./GetLocalizedFiles.sh -t $TDBUILD_TEAM_ID -u \
  -a $TDBUILD_AAD_APPLICATION_CLIENT_ID \
  -p $TDBUILD_AAD_APPLICATION_CLIENT_SECRET \
  -f fluentui_menus/src/main/res/values \
  -r fluentui_menus \
  -o fluentui_menus/src/main/res/values

./GetLocalizedFiles.sh -t $TDBUILD_TEAM_ID -u \
  -a $TDBUILD_AAD_APPLICATION_CLIENT_ID \
  -p $TDBUILD_AAD_APPLICATION_CLIENT_SECRET \
  -f fluentui_notification/src/main/res/values \
  -r fluentui_notification \
  -o fluentui_notification/src/main/res/values

end_ts=$(date +%s)
echo "Localization execution time: $((end_ts - start_ts)) seconds"

# =========================
# Directory snapshot (after)
# =========================
echo "---- Localization files AFTER ----"
find . -path "*res/values*" -type f

echo "---- Checksums AFTER ----"
find . -path "*res/values*" -type f -print0 | sort -z | xargs -0 shasum

# =========================
# Git visibility (after)
# =========================
echo "---- Git status AFTER ----"
git status --porcelain=v1

echo "---- Git diff summary ----"
git diff --stat

echo "========== localize.sh END =========="
