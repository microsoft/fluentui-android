#!/bin/bash

# =========================
# Logging / tracing
# =========================
set -x
export PS4='+ $(date -u +"%Y-%m-%dT%H:%M:%SZ") GetLocalizedFiles.sh:${LINENO} -> '

echo "========== GetLocalizedFiles.sh START =========="
echo "PWD: $(pwd)"
echo "Timestamp: $(date -u +"%Y-%m-%dT%H:%M:%SZ")"

id=""
alias=""
password=""
outputDirectory=""
renameLanguageFolder=true
extension=""
parserId=246
isoauth=false
tokenServer="tdb-touchdownbuild-prod"

# =========================
# Argument parsing (UNCHANGED)
# =========================
function ParseArgs ()
{
  while getopts "nut:a:p:f:r:o:e:s:w:" arg
  do
    case "$arg" in
      n) renameLanguageFolder=false;;
      u) isoauth=true;;
      t) id=$OPTARG;;
      a) alias="$OPTARG";;
      p) password="$OPTARG";;
      f) filePath="$OPTARG";;
      r) relativeFilePath="$OPTARG";;
      o) outputDirectory="$OPTARG";;
      e) extension="$OPTARG";;
      s) parserId="$OPTARG";;
      w) tokenServer="$OPTARG";;
      -) break;;
    esac
  done
}

ParseArgs $*

echo "Team ID: $id"
echo "Alias: $alias"
echo "File Path: $filePath"
echo "Relative Path: $relativeFilePath"
echo "Parser Id: $parserId"
echo "Output Directory: $outputDirectory"
echo "Using OAuth: $isoauth"
echo "Token Server: $tokenServer"

# =========================
# OAuth token (UNCHANGED)
# =========================
function jsonValue ()
{
  KEY=$1
  num=$2
  jsonParseCmd=`awk -F\" '[,:}]' ' {for (i=1;i<=NF;i++) {if ($i~/'$KEY'\\042/) {print $(i+1)}}}' | tr -d '"' | sed -n ${num}p`
  echo $jsonParseCmd
}

function oauthToken ()
{
  tokenFetchCmd=`curl -v -sw "%{http_code}" \
    -X POST \
    -H "Content-Type: application/x-www-form-urlencoded" \
    -d "client_id=$alias&resource=https://microsoft.onmicrosoft.com/$tokenServer&client_secret=$password&grant_type=client_credentials" \
    "https://login.microsoftonline.com/microsoft.onmicrosoft.com/oauth2/token"`
  tokenValue=`echo $tokenFetchCmd | jsonValue access_token 1`
  echo $tokenValue
}

# =========================
# Existing logic (UNCHANGED)
# =========================
if [ -d $filePath ]; then
  echo "Getting all input files from $filePath"
  inputFiles=$filePath/*
  for file in $inputFiles
  do
    fileName="${file##*/}"
    if [[ $fileName != "strings.xml" ]]; then
      echo "Skipping $file"
      continue
    fi

    echo "Processing $file"
    relPath=$relativeFilePath/"$fileName"
    echo "Relative file path $relPath"

    start_ts=$(date +%s)

    if [ "$isoauth" = false ]; then
      curl -v --ntlm -u $alias:$password \
        -H "x-TDBuildWrapper: FluentUI-Android" \
        -X put \
        https://build.intlservices.microsoft.com/api/teams/$id/LocalizableFiles/ParserId/$parserId \
        --form "FilePath={\"FilePath\":\"$relPath\"};type=application/json" \
        --form "file=@$file;type=application/octet-stream" \
        -o "$fileName.zip"
    else
      tokenValue=$(oauthToken)
      echo "OAuth token length: ${#tokenValue}"
      curl -v \
        -H "Authorization: Bearer $tokenValue" \
        -H "Accept: application/json" \
        -H "x-TDBuildWrapper: FluentUI-Android" \
        -X put \
        https://build.intlservices.microsoft.com/api/teams/$id/LocalizableFiles/ParserId/$parserId \
        --form "FilePath={\"FilePath\":\"$relPath\"};type=application/json" \
        --form "file=@$file;type=application/octet-stream" \
        -o "$fileName.zip"
    fi

    end_ts=$(date +%s)
    echo "Upload+response time: $((end_ts - start_ts)) seconds"

    echo "Response file size:"
    ls -lh "$fileName.zip"

    echo "Response file type:"
    file "$fileName.zip"

    echo "Response file head:"
    head -c 200 "$fileName.zip" | cat
    echo

    unzip -o "$fileName.zip" -d $outputDirectory
    rm "$fileName.zip"
  done
fi

# =========================
# Rename logic (UNCHANGED)
# =========================
if [ "$renameLanguageFolder" = true ]; then
  echo "Renaming language folders in $outputDirectory"
  languageFolders=$outputDirectory/*/
  valuesDir=${outputDirectory%/}
  resDir=${valuesDir%/*}

  for folder in $languageFolders
  do
    folderWithoutTrailingSlash=${folder#${outputDirectory}/}

    if [ $(echo $folderWithoutTrailingSlash | grep -o "-" | wc -l) -gt "1" ]; then
      folderWithoutTrailingSlash=b+$(echo "$folderWithoutTrailingSlash" | tr - +)
    else
      folderWithoutTrailingSlash=$(echo "${folderWithoutTrailingSlash//-/"-r"}")
    fi

    echo "Renaming $folder -> values-$folderWithoutTrailingSlash"
    rm -rf "$resDir/values-$folderWithoutTrailingSlash"
    mv "$folder" "$outputDirectory/values-$folderWithoutTrailingSlash"
    mv "$valuesDir/values-$folderWithoutTrailingSlash" "$resDir"
  done
fi

echo "========== GetLocalizedFiles.sh END =========="
