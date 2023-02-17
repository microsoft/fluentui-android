#!/bin/bash

# Check if environment variables have been defined for our team ID, AAD App ID, and AAD App Secret for automated workflows before requesting them interactively
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

./GetLocalizedFiles.sh -t $TDBUILD_TEAM_ID -u -a $TDBUILD_AAD_APPLICATION_CLIENT_ID -p $TDBUILD_AAD_APPLICATION_CLIENT_SECRET -f FluentUI.Demo/src/main/res/values -r demo -o FluentUI.Demo/src/main/res/values
./GetLocalizedFiles.sh -t $TDBUILD_TEAM_ID -u -a $TDBUILD_AAD_APPLICATION_CLIENT_ID -p $TDBUILD_AAD_APPLICATION_CLIENT_SECRET -f fluentui_calendar/src/main/res/values -r fluentui_calendar -o fluentui_calendar/src/main/res/values
./GetLocalizedFiles.sh -t $TDBUILD_TEAM_ID -u -a $TDBUILD_AAD_APPLICATION_CLIENT_ID -p $TDBUILD_AAD_APPLICATION_CLIENT_SECRET -f fluentui_ccb/src/main/res/values -r fluentui_ccb -o fluentui_ccb/src/main/res/values
./GetLocalizedFiles.sh -t $TDBUILD_TEAM_ID -u -a $TDBUILD_AAD_APPLICATION_CLIENT_ID -p $TDBUILD_AAD_APPLICATION_CLIENT_SECRET -f fluentui_controls/src/main/res/values -r fluentui_controls -o fluentui_controls/src/main/res/values
./GetLocalizedFiles.sh -t $TDBUILD_TEAM_ID -u -a $TDBUILD_AAD_APPLICATION_CLIENT_ID -p $TDBUILD_AAD_APPLICATION_CLIENT_SECRET -f fluentui_core/src/main/res/values -r fluentui_core -o fluentui_core/src/main/res/values
./GetLocalizedFiles.sh -t $TDBUILD_TEAM_ID -u -a $TDBUILD_AAD_APPLICATION_CLIENT_ID -p $TDBUILD_AAD_APPLICATION_CLIENT_SECRET -f fluentui_drawer/src/main/res/values -r fluentui_drawer -o fluentui_drawer/src/main/res/values
./GetLocalizedFiles.sh -t $TDBUILD_TEAM_ID -u -a $TDBUILD_AAD_APPLICATION_CLIENT_ID -p $TDBUILD_AAD_APPLICATION_CLIENT_SECRET -f fluentui_icons/src/main/res/values -r fluentui_icons -o fluentui_icons/src/main/res/values
./GetLocalizedFiles.sh -t $TDBUILD_TEAM_ID -u -a $TDBUILD_AAD_APPLICATION_CLIENT_ID -p $TDBUILD_AAD_APPLICATION_CLIENT_SECRET -f fluentui_listitem/src/main/res/values -r fluentui_listitem -o fluentui_listitem/src/main/res/values
./GetLocalizedFiles.sh -t $TDBUILD_TEAM_ID -u -a $TDBUILD_AAD_APPLICATION_CLIENT_ID -p $TDBUILD_AAD_APPLICATION_CLIENT_SECRET -f fluentui_menus/src/main/res/values -r fluentui_menus -o fluentui_menus/src/main/res/values
./GetLocalizedFiles.sh -t $TDBUILD_TEAM_ID -u -a $TDBUILD_AAD_APPLICATION_CLIENT_ID -p $TDBUILD_AAD_APPLICATION_CLIENT_SECRET -f fluentui_notification/src/main/res/values -r fluentui_notification -o fluentui_notification/src/main/res/values
./GetLocalizedFiles.sh -t $TDBUILD_TEAM_ID -u -a $TDBUILD_AAD_APPLICATION_CLIENT_ID -p $TDBUILD_AAD_APPLICATION_CLIENT_SECRET -f fluentui_others/src/main/res/values -r fluentui_others -o fluentui_others/src/main/res/values
./GetLocalizedFiles.sh -t $TDBUILD_TEAM_ID -u -a $TDBUILD_AAD_APPLICATION_CLIENT_ID -p $TDBUILD_AAD_APPLICATION_CLIENT_SECRET -f fluentui_peoplepicker/src/main/res/values -r fluentui_peoplepicker -o fluentui_peoplepicker/src/main/res/values
./GetLocalizedFiles.sh -t $TDBUILD_TEAM_ID -u -a $TDBUILD_AAD_APPLICATION_CLIENT_ID -p $TDBUILD_AAD_APPLICATION_CLIENT_SECRET -f fluentui_persona/src/main/res/values -r fluentui_persona -o fluentui_persona/src/main/res/values
./GetLocalizedFiles.sh -t $TDBUILD_TEAM_ID -u -a $TDBUILD_AAD_APPLICATION_CLIENT_ID -p $TDBUILD_AAD_APPLICATION_CLIENT_SECRET -f fluentui_progress/src/main/res/values -r fluentui_progress -o fluentui_progress/src/main/res/values
./GetLocalizedFiles.sh -t $TDBUILD_TEAM_ID -u -a $TDBUILD_AAD_APPLICATION_CLIENT_ID -p $TDBUILD_AAD_APPLICATION_CLIENT_SECRET -f fluentui_tablayout/src/main/res/values -r fluentui_tablayout -o fluentui_tablayout/src/main/res/values
./GetLocalizedFiles.sh -t $TDBUILD_TEAM_ID -u -a $TDBUILD_AAD_APPLICATION_CLIENT_ID -p $TDBUILD_AAD_APPLICATION_CLIENT_SECRET -f fluentui_topappbars/src/main/res/values -r fluentui_topappbars -o fluentui_topappbars/src/main/res/values
./GetLocalizedFiles.sh -t $TDBUILD_TEAM_ID -u -a $TDBUILD_AAD_APPLICATION_CLIENT_ID -p $TDBUILD_AAD_APPLICATION_CLIENT_SECRET -f fluentui_transients/src/main/res/values -r fluentui_transients -o fluentui_transients/src/main/res/values
